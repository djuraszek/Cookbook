package com.cookbook.android.cookbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.cookbook.android.cookbook.classes.Ingredient;

import java.util.ArrayList;

public class AddRecipeActivity extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    ImageView addImgIV;
    EditText  nameEditText, portionEditText, preparationEditText, ingredientName, ingredientCapacity;
    ListView ingredientsListView;
    Button addRecipeBtn;
    ImageButton addIngredientIB;

    ArrayList<Ingredient> addedIngredients;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_recipe);
        databaseHelper = new DatabaseHelper(this);
        initVals();
    }

    public void initVals(){
        addImgIV = (ImageView)findViewById(R.id.addImgIV);
        nameEditText = (EditText)findViewById(R.id.nameEditText);
        portionEditText = (EditText)findViewById(R.id.portionEditText);
        preparationEditText = (EditText)findViewById(R.id.preparationEditText);
        ingredientName = (EditText)findViewById(R.id.ingredientName);
        ingredientCapacity  = (EditText)findViewById(R.id.ingredientCapacity);
        ingredientsListView = (ListView)findViewById(R.id.ingredientsListView);
        addRecipeBtn = (Button)findViewById(R.id.addRecipeBtn);
        addIngredientIB = (ImageButton)findViewById(R.id.addIngredientIB);
        buttonListeners();
    }

    public void buttonListeners(){
        addImgIV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });



        //add ingredient to ListView
        addIngredientIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //if this than add Recipe
                if(nameEditText.getText() != null && portionEditText.getText() != null &&
                preparationEditText.getText() != null && addedIngredients.size() != 0 ){

                }
            }
        });
    }
}
