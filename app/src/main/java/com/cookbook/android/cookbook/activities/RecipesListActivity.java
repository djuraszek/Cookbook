package com.cookbook.android.cookbook.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.cookbook.android.cookbook.DatabaseHelper;
import com.cookbook.android.cookbook.R;
import com.cookbook.android.cookbook.RecipesBookDB;
import com.cookbook.android.cookbook.adapters.RecipesListAdaper;
import com.cookbook.android.cookbook.classes.Recipe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecipesListActivity extends AppCompatActivity {

    ListView recipesListView, recipesListView2;
    TextView textView, textViewEmptyMessage;
    RecipesBookDB recipesBookDB;
    RecipesListAdaper recipesListAdaper;

    //todo this app shows recipes specified by user by all
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("RecipesListActivity","onCreate");
        setContentView(R.layout.activity_recipes_list);
        DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        recipesBookDB = new RecipesBookDB(dbHelper);
        Intent intent = getIntent();
        Boolean showFilteredList = getIntent().getExtras().getBoolean("showFilteredList");
        if(showFilteredList) {
            ArrayList<Integer> chosenIngredients = intent.getIntegerArrayListExtra("chosenIngredients");
            List<Recipe> recipes = recipesBookDB.getRecipesByIngredients(chosenIngredients);
            if(recipes.size() > 0) {
                recipes = sortList(recipes);
                recipesListAdaper = new RecipesListAdaper(this, recipes);
                recipesListView = (ListView)findViewById(R.id.recipesList);
                recipesListView.setAdapter(recipesListAdaper);
                List<Recipe> otherRecipes = recipesBookDB.getOtherRecipes();
                if(otherRecipes.size() > 0) {
                    otherRecipes = sortList(otherRecipes);
                    textView = (TextView)findViewById(R.id.recipesText2);
                    textView.setVisibility(View.VISIBLE);
                    recipesListView2 = (ListView)findViewById(R.id.recipesList2);
                    recipesListAdaper = new RecipesListAdaper(this, otherRecipes);
                    recipesListView2.setAdapter(recipesListAdaper);
                    recipesListView2.setVisibility(View.VISIBLE);
                }
            }
            else {
                textViewEmptyMessage = (TextView) findViewById(R.id.emptyMessage);
                textViewEmptyMessage.setVisibility(View.VISIBLE);
            }
        }
        else {
            List<Recipe> recipes = sortList(recipesBookDB.getRecipeList());
            recipesListAdaper = new RecipesListAdaper(this, recipes);
            recipesListView = (ListView)findViewById(R.id.recipesList);
            recipesListView.setAdapter(recipesListAdaper);
        }
    }

    public List<Recipe> sortList(List<Recipe> recipes) {
        Collections.sort(recipes, new Comparator<Recipe>() {
            public int compare(Recipe r1, Recipe r2) {
                return r1.getName().compareTo(r2.getName());
            }
        });
        return recipes;
    }
}