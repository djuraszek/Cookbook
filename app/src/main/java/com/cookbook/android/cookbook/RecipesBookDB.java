package com.cookbook.android.cookbook;

import android.util.Log;
import com.cookbook.android.cookbook.classes.Image;
import com.cookbook.android.cookbook.classes.Ingredient;
import com.cookbook.android.cookbook.classes.Product;
import com.cookbook.android.cookbook.classes.Recipe;
import java.util.ArrayList;
import java.util.List;

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

    public RecipesBookDB(DatabaseHelper databaseHelper) {
        this.databaseHelper = databaseHelper;
        getDataFromDB();
    }

    private void getDataFromDB(){
        recipeList = databaseHelper.getAllRecipeList();
        ingredientList = databaseHelper.getAllIngredientList();
        productList = databaseHelper.getAllProductsList();
        imageList = databaseHelper.getAllImagesList();

        //add images to recipe
        for(int i=0; i<imageList.size();i++){
            getRecipe(imageList.get(i).getRecipeId()).setImage(imageList.get(i));
        }
        //add ingredients to recipe
        for(int i=0; i<ingredientList.size();i++){
            System.out.println(i + " -> r:" + ingredientList.get(i).getRecipeId());
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

    public Recipe getRecipeByName(String recipeName){
        for(int i = 0; i<recipeList.size(); i++){
            if(recipeList.get(i).getName().equals(recipeName))
                return recipeList.get(i);
        }
        Log.e("RecipesBookDb","getRecipeByName("+recipeName+") -> recipe not found");
        return null;
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
        return recipeListByIngredients;
    }

    public List<Recipe> getOtherRecipes() {
        return otherRecipes;
    }

//    public List<Recipe> getRecipesByIngredients(List<Integer> ingredients) {
//        recipeListByIngredients = new ArrayList<>();
//        int size = ingredients.size();
//        int percent = (int) 0.7*size;
//        for(int i = 0; i < recipeList.size(); i++) {
//            int counter = 0;
//            Recipe currentRecipe = recipeList.get(i);
//            boolean productContain = false;
//            int j = 0;
//            do {
//                int k = 0;
//                do {
//                    if(currentRecipe.getIngredients().get(k).getProduct().getProductID() == ingredients.get(j)) {
//                        productContain = true;
//                        counter++;
//                    }
//                    k++;
//                } while((k < currentRecipe.getIngredients().size()) && !productContain);
//                j++;
//            }while(productContain && j < ingredients.size());
//            if(productContain && (counter == size)) {
//                Recipe r = new Recipe(currentRecipe.getRecipeID(),
//                        currentRecipe.getName(), currentRecipe.getRating(),
//                        currentRecipe.getPortion(), currentRecipe.getPreparation());
//                recipeListByIngredients.add(r);
//            }
//        }
//        return recipeListByIngredients;
//    }

    public Ingredient getIngredient(int ingredientID){
        for(int i=0;i<ingredientList.size();i++){
            if(ingredientList.get(i).getIngedientID() == ingredientID)
                return ingredientList.get(i);
        }
        Log.e("RecipesBookDb","getIngredient -> ingredient not found");
        return null;
    }

    public List<Recipe> getRecipeList() {
        return recipeList;
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
}