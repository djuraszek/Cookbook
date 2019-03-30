package com.cookbook.android.cookbook;

import android.util.Log;

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
            recipeList.get(imageList.get(i).getRecipeId()-1).setImage(imageList.get(i));
        }
        //add ingredients to recipe
        for(int i=0; i<ingredientList.size();i++){
            recipeList.get(ingredientList.get(i).getRecipeId()-1).addIngredient(ingredientList.get(i));
        }
    }

    public List<Recipe> getRecipeList() {
//        Log.e("RecipesBookDB","getRecipeList().size() "+recipeList.size());
//        System.out.println(recipeList);
        return recipeList;
    }
    public Recipe getRecipe(int position) {
        return recipeList.get(position);
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
