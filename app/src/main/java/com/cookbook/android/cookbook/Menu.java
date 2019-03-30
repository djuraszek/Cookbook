package com.cookbook.android.cookbook;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    Button allRecipesBtn, findRecipeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        databaseHelper = new DatabaseHelper(this);
        System.out.println(databaseHelper.getDatabaseName());
//        getSupportActionBar().hide();
        values();
    }

    public void values(){
        allRecipesBtn = (Button)findViewById(R.id.buttonShowAllRecipes);
        findRecipeBtn = (Button)findViewById(R.id.buttonSelectProducts);

        allRecipesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RecipesListActivity.class);
                startActivity(intent);
            }
        });
        findRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), ProductsList.class);
                startActivity(intent);
            }
        });
    }

}
