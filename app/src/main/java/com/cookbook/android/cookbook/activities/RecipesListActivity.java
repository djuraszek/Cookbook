package com.cookbook.android.cookbook.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.cookbook.android.cookbook.DatabaseHelper;
import com.cookbook.android.cookbook.R;
import com.cookbook.android.cookbook.RecipesBookDB;
import com.cookbook.android.cookbook.adapters.RecipesListAdaper;
import com.cookbook.android.cookbook.classes.Recipe;

import java.util.ArrayList;
import java.util.List;

public class RecipesListActivity extends AppCompatActivity {

    ListView recipesListView, recipesListView2;
    TextView startText, textView, textViewEmptyMessage;
    RecipesBookDB recipesBookDB;
    RecipesListAdaper recipesListAdaper;
    SearchView searchView;
    //todo this app shows recipes specified by user by all
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("RecipesListActivity","onCreate");
        setContentView(R.layout.activity_recipes_list);
        final DatabaseHelper dbHelper = new DatabaseHelper(getApplicationContext());
        recipesBookDB = new RecipesBookDB(dbHelper);

        recipesListView = (ListView)findViewById(R.id.recipesList);
        recipesListView2 = (ListView)findViewById(R.id.recipesList2);
        searchView = (SearchView)findViewById(R.id.searchView);
        textView = (TextView)findViewById(R.id.recipesText2);
        textViewEmptyMessage = (TextView) findViewById(R.id.emptyMessage);
        startText = (TextView) findViewById(R.id.startText);

        Boolean showFilteredList = getIntent().getExtras().getBoolean("showFilteredList");
        Boolean showTopRecipes = getIntent().getExtras().getBoolean("showTopRecipes");
        if(showFilteredList) loadSelectedRecipes();
        else if(showTopRecipes) loadTopRecipes();
        else loadAllRecipes();
    }

    public void loadAllRecipes() {
        List<Recipe> recipes = recipesBookDB.getRecipeList();
        recipesListAdaper = new RecipesListAdaper(this, recipes);
        recipesListView = (ListView)findViewById(R.id.recipesList);
        recipesListView.setAdapter(recipesListAdaper);
        searchView.setVisibility(View.VISIBLE);
        searchViewEvent();
    }

    public void searchViewEvent() {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String name) {
                searchRecipes(name);
                return false;
            }
            @Override
            public boolean onQueryTextChange(String name) {
                searchRecipes(name);
                return false;
            }
        });
    }

    public void loadSelectedRecipes() {
        Intent intent = getIntent();
        ArrayList<Integer> chosenIngredients = intent.getIntegerArrayListExtra("chosenIngredients");
        List<Recipe> recipes = recipesBookDB.getRecipesByIngredients(chosenIngredients);
        List<Recipe> otherRecipes = recipesBookDB.getOtherRecipes();
        if(recipes.size() > 0) {
            recipesListAdaper = new RecipesListAdaper(this, recipes);
            recipesListView.setAdapter(recipesListAdaper);
            if(otherRecipes.size() > 0) {
                textView.setVisibility(View.VISIBLE);
                recipesListAdaper = new RecipesListAdaper(this, otherRecipes);
                recipesListView2.setAdapter(recipesListAdaper);
                recipesListView2.setVisibility(View.VISIBLE);
            }
        }
        else if (otherRecipes.size() > 0) {
            startText.setText("Pozostałe przepisy:");
            recipesListAdaper = new RecipesListAdaper(this, otherRecipes);
            recipesListView.setAdapter(recipesListAdaper);
        }
        else {
            recipesListAdaper = new RecipesListAdaper(this, new ArrayList<Recipe>());
            recipesListView.setAdapter(recipesListAdaper);
            textViewEmptyMessage.setVisibility(View.VISIBLE);
        }
    }

    public void loadTopRecipes() {
        List<Recipe> recipes = recipesBookDB.getTopRecipes();
        startText.setText("Top 10 przepisów:");
        recipesListAdaper = new RecipesListAdaper(this, recipes);
        recipesListView.setAdapter(recipesListAdaper);
    }

    public void searchRecipes(String name) {
        List<Recipe> recipes;
        if(name.length() == 0) recipes = recipesBookDB.getRecipeList();
        else recipes = recipesBookDB.getRecipesByName(name);
        if(recipes.size() > 0) {
            textViewEmptyMessage.setVisibility(View.INVISIBLE);
            recipesListAdaper = new RecipesListAdaper(getApplicationContext(), recipes);
            recipesListView.setAdapter(recipesListAdaper);
            recipesListView.setVisibility(View.VISIBLE);
        }
        else textViewEmptyMessage.setVisibility(View.VISIBLE);
    }
}