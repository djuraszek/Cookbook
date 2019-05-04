package com.cookbook.android.cookbook.activities;

import android.support.v4.app.INotificationSideChannel;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cookbook.android.cookbook.DatabaseHelper;
import com.cookbook.android.cookbook.R;
import com.cookbook.android.cookbook.RecipesBookDB;
import com.cookbook.android.cookbook.classes.Ingredient;
import com.cookbook.android.cookbook.classes.Recipe;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RecipeActivity extends AppCompatActivity {
    Recipe recipe ;
//    ListView ingredientsLV,
//    ListView preparationsLV;
    LinearLayout ingredientsLV,preparationsLV;
    TextView recipeName, portionTV;
    ImageView image;
    RatingBar ratingBar;

    RecipesBookDB recipesBookDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        DatabaseHelper dbHelper = new DatabaseHelper(this);
        recipesBookDB = new RecipesBookDB(dbHelper);
        intentContent();
        setListView();
    }

    public void intentContent(){
        int recipeId = getIntent().getIntExtra("recipe",-1);
        recipe = recipesBookDB.getRecipe(recipeId);
//        Log.e("RecipeActivity","recipe: "+recipe.toString());

//        ingredientsLV = (ListView) findViewById(R.id.ingredientsListView);
        preparationsLV= (LinearLayout)findViewById(R.id.preparationsListView);
        ingredientsLV = (LinearLayout) findViewById(R.id.ingredientsListView);
        recipeName = (TextView)findViewById(R.id.recipeName);
        image = (ImageView)findViewById(R.id.recipeImg);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        portionTV = (TextView) findViewById(R.id.portionTV);

        if(recipe!=null) {
            recipeName.setText(recipe.getName());
            ratingBar.setRating((float) recipe.getRating());
            String portion = "" + recipe.getPortion();
            portionTV.setText(portion);

            if(recipe.getImage()!= null)image.setImageBitmap(recipe.getImage());
            else image.setVisibility(View.GONE);
        }
        else
            Log.e("RecipeActivity","recipe == null");


    }

    public void setListView(){
        RelativeLayout.LayoutParams relativeParams = new RelativeLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        relativeParams.setMargins(15,10,0,0);

//        List<Ingredient> ingredients = recipe.getIngredients();
//        Collections.sort(ingredients, new Comparator<Recipe>() {
//            public int compare(I r1, Recipe r2) {
//                return r1.getName().compareTo(r2.getName());
//            }
//        });
        for(int i=0; i<recipe.getIngredients().size();i++){
            TextView child = new TextView(this);
            child.setLayoutParams(relativeParams);
            List<Ingredient> ingredients = sortIngredients(recipe.getIngredients());
            String text = ingredients.get(i).ingredientInfo();
            child.setTextSize(18);
            child.setText(text);
            ingredientsLV.addView(child);
        }

//        for(int i=0; i<recipe.getPreparations().length;i++){
//            TextView child = new TextView(this);
//            child.setLayoutParams(relativeParams);
//            String text = recipe.getPreparations()[i];
//            System.out.println(text);
//            child.setText(text);
//            preparationsLV.addView(child);
//        }
        TextView child = new TextView(this);
        child.setLayoutParams(relativeParams);
        child.setTextSize(18);
        String text = recipe.getPreparation();
        child.setText(text);
        preparationsLV.addView(child);
    }

    public List<Ingredient> sortIngredients(List<Ingredient> ingredients) {
        Collections.sort(ingredients, new Comparator<Ingredient>() {
            public int compare(Ingredient i1, Ingredient i2) {
                return i1.getProduct().getName().compareTo(i2.getProduct().getName());
            }
        });
        return ingredients;
    }
}
