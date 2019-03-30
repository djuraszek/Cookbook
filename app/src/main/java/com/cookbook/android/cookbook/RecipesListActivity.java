package com.cookbook.android.cookbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.List;

public class RecipesListActivity extends AppCompatActivity {

    ListView recipesListView;
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

        recipesListAdaper = new RecipesListAdaper(this, recipesBookDB.getRecipeList());

//        Log.e("RecipesListActivity","recipes: " +recipesBookDB.getRecipeList());

        recipesListView = (ListView)findViewById(R.id.recipesList);
        recipesListView.setAdapter(recipesListAdaper);


    }


}
