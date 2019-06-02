package com.cookbook.android.cookbook;

import android.util.Log;
import android.widget.Toast;

import com.cookbook.android.cookbook.classes.Image;
import com.cookbook.android.cookbook.classes.Ingredient;
import com.cookbook.android.cookbook.classes.Product;
import com.cookbook.android.cookbook.classes.Rating;
import com.cookbook.android.cookbook.classes.Recipe;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static java.security.AccessController.getContext;

/**
 * class to store all the data from DB
 */
public class RecipesBookDB {

    private DatabaseHelper databaseHelper;

    private List<Recipe> recipeList;
    private List<Ingredient> ingredientList;
    private List<Product> productList;
    private List<Image> imageList;
    private List<Recipe> recipeListByIngredients;
    private List<Recipe> otherRecipes;
    private List<Recipe> topRecipes;
    private List<Rating> ratings;

    public RecipesBookDB(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
        getDataFromDB();
    }

    private void getDataFromDB(){
        recipeList = databaseHelper.getAllRecipeList();
        ingredientList = databaseHelper.getAllIngredientList();
        productList = databaseHelper.getAllProductsList();
        imageList = databaseHelper.getAllImagesList();
        ratings = databaseHelper.getRatings();
        //add images to recipe
        for(int i=0; i<imageList.size();i++){
            getRecipe(imageList.get(i).getRecipeId()).setImage(imageList.get(i));
        }
        //add ingredients to recipe
        for(int i=0; i<ingredientList.size();i++){
//            System.out.println(i + " -> r:" + ingredientList.get(i).getRecipeId());
            if(ingredientList.get(i).getRecipeId()>0) {
                getRecipe(ingredientList.get(i).getRecipeId()).addIngredient(ingredientList.get(i));
            }
        }
    }

    public Recipe getRecipe(int recipeId){
        for(int i=0;i<recipeList.size();i++){
            if(recipeList.get(i).getRecipeID() == recipeId)
                return recipeList.get(i);
        }

        Log.e("RecipesBookDb","getRecipe("+recipeId+") -> recipe not found");
        return null;
    }

    public List<Recipe> getRecipesByName(String recipeName){
        List<Recipe> recipes = new ArrayList<>();
        for(int i = 0; i<recipeList.size(); i++){
            if(recipeList.get(i).getName().contains(recipeName))
                recipes.add(recipeList.get(i));
        }
        return sortList(recipes);
    }

    public Recipe getRecipesById(int recipeId){
        Recipe r = new Recipe();
        for(int i = 0; i<recipeList.size(); i++){
            if(recipeList.get(i).getRecipeID() == recipeId)
                return r = new Recipe(recipeList.get(i));
        }
        return r;
    }

    public List<Recipe> getRecipesByIngredients(List<Integer> ingredients) {
        recipeListByIngredients = new ArrayList<>();
        otherRecipes = new ArrayList<>();
        int size = ingredients.size();
        double percentDouble = 0.7*size;
        int percent = (int) percentDouble;
        for(int i = 0; i < recipeList.size(); i++) {
            int counter = 0;
            Recipe currentRecipe = recipeList.get(i);
            int j = 0;
            do {
                int k = 0;
                do {
                    if(currentRecipe.getIngredients().get(k).getProduct().getProductID() == ingredients.get(j)) counter++;
                    k++;
                } while(k < currentRecipe.getIngredients().size());
                j++;
            }while(j < ingredients.size());
            if(counter == size) {
                Recipe r = new Recipe(currentRecipe.getRecipeID(),
                        currentRecipe.getName(), currentRecipe.getRating(),
                        currentRecipe.getPortion(), currentRecipe.getPreparation());
                recipeListByIngredients.add(r);
            }
            else if(counter >= percent) {
                Recipe r = new Recipe(currentRecipe.getRecipeID(),
                        currentRecipe.getName(), currentRecipe.getRating(),
                        currentRecipe.getPortion(), currentRecipe.getPreparation());
                otherRecipes.add(r);
            }
        }
        return sortList(recipeListByIngredients);
    }

    public double getRatingByRecipe(int recipeId){
        List<Rating> list = new ArrayList<>();
        double sum = 0.0;
        for(int i = 0; i<ratings.size(); i++){
            if(ratings.get(i).getRecipeId() == recipeId) list.add(ratings.get(i));
        }
        for(Rating r : list) sum = sum + r.getRating();
        double finalRating = sum / list.size();
        return finalRating;
    }

    public List<Rating> getRatings() { return ratings;}

    public List<Recipe> getOtherRecipes() {
        return sortList(otherRecipes);
    }

    public Ingredient getIngredient(int ingredientID){
        for(int i=0;i<ingredientList.size();i++){
            if(ingredientList.get(i).getIngedientID() == ingredientID)
                return ingredientList.get(i);
        }
        Log.e("RecipesBookDb","getIngredient -> ingredient not found");
        return null;
    }

    public void addRating(Rating r) {
        System.out.println("gowno=="+r.getRatingID());
        databaseHelper.addRating(r);
        double finalRating = getRatingByRecipe(r.getRecipeId());
        databaseHelper.updateRecipeRating(finalRating, r.getRecipeId());
    }

    public List<Recipe> getTopRecipes() {
        topRecipes = new ArrayList<>();
        List<Recipe> tempList = sortListByRating(this.recipeList);
        Collections.reverse(tempList);
        for(int i = 0; i < 10; i++) {
            topRecipes.add(tempList.get(i));
        }
        return topRecipes;
    }

    public List<Recipe> getRecipeList() {
        return sortList(recipeList);
    }

    public List<Ingredient> getIngredientList() {
        return ingredientList;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public List<Image> getImageList() {
        return imageList;
    }

    public List<Recipe> sortList(List<Recipe> recipes) {
        Collections.sort(recipes, new Comparator<Recipe>() {
            public int compare(Recipe r1, Recipe r2) {
                return r1.getName().compareTo(r2.getName());
            }
        });
        return recipes;
    }

    public List<Recipe> sortListByRating(List<Recipe> recipes) {
        Collections.sort(recipes, new Comparator<Recipe>() {
            public int compare(Recipe r1, Recipe r2) {
                return new Double(r1.getRating()).compareTo(r2.getRating());
            }
        });
        return recipes;
    }
}