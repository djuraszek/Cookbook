package com.cookbook.android.cookbook;

import android.util.Log;

import com.cookbook.android.cookbook.classes.Image;
import com.cookbook.android.cookbook.classes.Ingredient;
import com.cookbook.android.cookbook.classes.Product;
import com.cookbook.android.cookbook.classes.Recipe;

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

    public Ingredient getIngredient(int ingredientID){
        for(int i=0;i<ingredientList.size();i++){
            if(ingredientList.get(i).getIngedientID() == ingredientID)
                return ingredientList.get(i);
        }
        Log.e("RecipesBookDb","getIngredient -> ingredient not found");
        return null;
    }



    public List<Recipe> getRecipeList() {
//        Log.e("RecipesBookDB","getRecipeList().size() "+recipeList.size());
//        System.out.println(recipeList);
        return recipeList;
    }
//    public Recipe getRecipe(int position) {
//        return recipeList.get(position);
//    }

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
