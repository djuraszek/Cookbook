package com.cookbook.android.cookbook;

import com.cookbook.android.cookbook.classes.Ingredient;
import com.cookbook.android.cookbook.classes.Product;
import com.cookbook.android.cookbook.classes.Recipe;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class classesTest {


    List<Recipe> recipes;
    List <Ingredient> ingredients;

    @Test
    public void addNewRecipe() {
        recipes = new ArrayList<>();

        Recipe recipe1 = new Recipe(1, "Babka piaskowa", 3,"20 kawalkow", "COSCOSCOSCOSCOSCOSCOSCOSCOSCOSCOSCOS");
        Recipe recipe2 = new Recipe(2, "Drożdzowy", 3,"20 kawalkow", "COSCOSCOSCOSCOSCOSCOSCOSCOSCOSCOSCOS");
        Recipe recipe3 = new Recipe(3, "Mazurek", 5,"20 kawalkow", "COSCOSCOSCOSCOSCOSCOSCOSCOSCOSCOSCOS");
        Recipe recipe4 = new Recipe(4, "Makowiec", 4,"20 kawalkow", "COSCOSCOSCOSCOSCOSCOSCOSCOSCOSCOSCOS");
        Recipe recipe5 = new Recipe(5, "Wuzetka", 5,"20 kawalkow", "COSCOSCOSCOSCOSCOSCOSCOSCOSCOSCOSCOS");


        recipes.add(recipe1);
        recipes.add(recipe2);
        recipes.add(recipe3);
        recipes.add(recipe4);
        recipes.add(recipe5);


        assertEquals(5, recipes.size());
    }

    @Test
    public void addNewIngredient() {
        ingredients = new ArrayList<>();

        Recipe recipe = new Recipe(1, "Babka piaskowa", 3,"20 kawalkow", "COSCOSCOSCOSCOSCOSCOSCOSCOSCOSCOSCOS");
        Product product = new Product(1,"Cukier", "Slodzik");
        Ingredient ingredient = new Ingredient(1,product,"20kg",recipe.getRecipeID());

        ingredients.add(ingredient);

        assertEquals("Cukier", ingredients.get(0).getProduct().getName());
        assertEquals("Slodzik", ingredients.get(0).getProduct().getCategory());

    }




}

