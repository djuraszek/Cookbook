package com.cookbook.android.cookbook.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cookbook.android.cookbook.DatabaseHelper;
import com.cookbook.android.cookbook.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Menu extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    Button allRecipesBtn, findRecipeBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        databaseHelper = new DatabaseHelper(this);
        String storedDBPath = databaseHelper.getReadableDatabase().getPath();
        File file = new File(storedDBPath);
//        try {
//            copyDatabase(file);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }


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

    String DB_NAME = "cookbook.db";

    private void copyDatabase(File dbFile) throws IOException {
        InputStream is = getAssets().open(DB_NAME);
        OutputStream os = new FileOutputStream(dbFile);



        byte[] buffer = new byte[1024];
        while (is.read(buffer) > 0) {
            os.write(buffer);
        }

        os.flush();
        os.close();
        is.close();
    }

}
