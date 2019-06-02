package com.cookbook.android.cookbook;


import android.support.test.InstrumentationRegistry;

import com.cookbook.android.cookbook.classes.Ingredient;
import com.cookbook.android.cookbook.classes.Product;
import com.cookbook.android.cookbook.classes.Recipe;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class DatabaseHelperTest{

    private DatabaseHelper databaseHelper;
    private RecipesBookDB recipesBookDB;

    @Before
    public void setUp() throws Exception{
        databaseHelper = new DatabaseHelper(InstrumentationRegistry.getTargetContext());

    }

    @After
    public void tearDown(){
        databaseHelper.close();
    }

    @Test
    public void testAddNewRecipe(){
        Recipe recipe = new Recipe(1, "Babka piaskowa", 3,"20 kawalkow", "Przygotowanie");
        databaseHelper.addRecipe(recipe);

        assertEquals("Babka piaskowa", databaseHelper.getAllRecipeList().get(0).getName());
        assertEquals("20 kawalkow", databaseHelper.getAllRecipeList().get(0).getPortion());
    }

    @Test
    public void testAddNewIngredient(){
        Recipe recipe = new Recipe(1, "Babka piaskowa", 3,"20 kawalkow", "Przygotowanie");
        databaseHelper.addRecipe(recipe);
        Product product = new Product(1,"Cukier", "Slodzik");
        databaseHelper.addProduct(product);
        Ingredient ingredient = new Ingredient(1,product,"20kg",recipe.getRecipeID());
        databaseHelper.addIngredient(ingredient);

        assertEquals("20kg", databaseHelper.getAllIngredientList().get(0).getQuantity());
        assertEquals(1, databaseHelper.getAllIngredientList().get(0).getRecipeId());
    }

    @Test
    public void testAddNewProduct(){
        Product product = new Product(1,"Cukier", "Slodzik");
        databaseHelper.addProduct(product);

        assertEquals("Cukier", databaseHelper.getAllIngredientList().get(0).getProduct().getName());
        assertEquals("Slodzik", databaseHelper.getAllIngredientList().get(0).getProduct().getCategory());
    }


    @Test
    public void testCountRecipes(){
        Recipe recipe1 = new Recipe(1, "Babka piaskowa", 3,"20 kawalkow", "Przygotowanie");
        Recipe recipe2 = new Recipe(2, "Drożdzowy", 3,"20 kawalkow", "Przygotowanie");
        Recipe recipe3 = new Recipe(3, "Mazurek", 5,"20 kawalkow", "Przygotowanie");
        Recipe recipe4 = new Recipe(4, "Makowiec", 4,"20 kawalkow", "Przygotowanie");
        Recipe recipe5 = new Recipe(5, "Wuzetka", 5,"20 kawalkow", "Przygotowanie");
        databaseHelper.addRecipe(recipe1);
        databaseHelper.addRecipe(recipe2);
        databaseHelper.addRecipe(recipe3);
        databaseHelper.addRecipe(recipe4);
        databaseHelper.addRecipe(recipe5);

        assertEquals(5, databaseHelper.getAllRecipeList().size());
        assertEquals("Wuzetka",databaseHelper.getAllRecipeList().get(databaseHelper.getLastRecipeID()-1).getName());
    }

    @Test
    public void testTopRecipes(){
        List <Recipe> topRecipes;
        Boolean ifInRecipeList = false;

        Recipe recipe1 = new Recipe(1, "Babka piaskowa", 5,"20 kawalkow", "Przygotowanie");
        Recipe recipe2 = new Recipe(2, "Drożdzowy", 5,"20 kawalkow", "Przygotowanie");
        Recipe recipe3 = new Recipe(3, "Mazurek", 5,"20 kawalkow", "Przygotowanie");
        Recipe recipe4 = new Recipe(4, "Makowiec", 5,"20 kawalkow", "Przygotowanie");
        Recipe recipe5 = new Recipe(5, "Wuzetka", 5,"20 kawalkow", "Przygotowanie");
        Recipe recipe6 = new Recipe(6, "Keks", 3,"20 kawalkow", "Przygotowanie");
        Recipe recipe7 = new Recipe(7, "Tarta z rabarbarem", 3,"20 kawalkow", "Przygotowanie");
        Recipe recipe8 = new Recipe(8, "Tarta z truskawkami", 5,"20 kawalkow", "Przygotowanie");
        Recipe recipe9 = new Recipe(9, "Tarta z agrestem", 4,"20 kawalkow", "Przygotowanie");
        Recipe recipe10 = new Recipe(10, "Tarta z borówkami", 5,"20 kawalkow", "Przygotowanie");
        Recipe recipe11 = new Recipe(11, "Ptasie mleczko z truskawkami", 3,"20 kawalkow", "Przygotowanie");
        Recipe recipe12 = new Recipe(12, "Ptasie mleczko z borówkami", 3,"20 kawalkow", "Przygotowanie");
        Recipe recipe13 = new Recipe(13, "Ptasie mleczko z wiśniami", 5,"20 kawalkow", "Przygotowanie");
        Recipe recipe14 = new Recipe(14, "Sernik", 5,"20 kawalkow", "Przygotowanie");
        Recipe recipe15 = new Recipe(15, "Browne", 5,"20 kawalkow", "Przygotowanie");
        databaseHelper.addRecipe(recipe1);
        databaseHelper.addRecipe(recipe2);
        databaseHelper.addRecipe(recipe3);
        databaseHelper.addRecipe(recipe4);
        databaseHelper.addRecipe(recipe5);
        databaseHelper.addRecipe(recipe6);
        databaseHelper.addRecipe(recipe7);
        databaseHelper.addRecipe(recipe8);
        databaseHelper.addRecipe(recipe9);
        databaseHelper.addRecipe(recipe10);
        databaseHelper.addRecipe(recipe11);
        databaseHelper.addRecipe(recipe12);
        databaseHelper.addRecipe(recipe13);
        databaseHelper.addRecipe(recipe14);
        databaseHelper.addRecipe(recipe15);

        recipesBookDB = new RecipesBookDB(databaseHelper);

        topRecipes = recipesBookDB.getTopRecipes();

        for(int i=0; i<topRecipes.size()-1;i++){
            if (topRecipes.get(i).getName().equals("Wuzetka")){
                ifInRecipeList = true;
            }
        }

        assertEquals(10, topRecipes.size());
        assertTrue(ifInRecipeList);
    }

    @Test
    public void testProductByCategory(){
        int sumCategorySlodzik = 0;

        Product product1 = new Product(1,"Cukier", "Slodzik");
        Product product2 = new Product(2,"Mąka", "Sypkie");
        Product product3 = new Product(3,"Olej", "Tłuszcz");
        Product product4 = new Product(4,"Proszek do pieczenia", "Sypkie");
        Product product5 = new Product(5,"Miód", "Slodzik");
        databaseHelper.addProduct(product1);
        databaseHelper.addProduct(product2);
        databaseHelper.addProduct(product3);
        databaseHelper.addProduct(product4);
        databaseHelper.addProduct(product5);

        for (int i = 0; i<databaseHelper.getAllProductsList().size(); i++){
            if (databaseHelper.getAllProductsList().get(i).getCategory().equals("Slodzik"))
                sumCategorySlodzik ++;
        }

        assertEquals(2,sumCategorySlodzik);

    }

    @Test
    public void testRecipeByName(){
        List<Recipe> recipeByName;

        Recipe recipe1 = new Recipe(16, "Zebra biala", 3,"20 kawalkow", "Przygotowanie");
        Recipe recipe2 = new Recipe(17, "Zebra czarna", 3,"20 kawalkow", "Przygotowanie");
        Recipe recipe3 = new Recipe(18, "Zebra", 5,"20 kawalkow", "Przygotowanie");
        Recipe recipe4 = new Recipe(19, "Babka piaskowa", 4,"20 kawalkow", "Przygotowanie");
        Recipe recipe5 = new Recipe(20, "Wuzetka", 5,"20 kawalkow", "Przygotowanie");
        databaseHelper.addRecipe(recipe1);
        databaseHelper.addRecipe(recipe2);
        databaseHelper.addRecipe(recipe3);
        databaseHelper.addRecipe(recipe4);
        databaseHelper.addRecipe(recipe5);


        recipesBookDB = new RecipesBookDB(databaseHelper);

        recipeByName = recipesBookDB.getRecipesByName("Zebra");

        assertEquals(3, recipeByName.size());
    }




}
