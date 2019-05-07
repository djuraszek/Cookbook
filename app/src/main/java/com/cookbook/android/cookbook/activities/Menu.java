package com.cookbook.android.cookbook.activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.cookbook.android.cookbook.DatabaseHelper;
import com.cookbook.android.cookbook.R;
import com.cookbook.android.cookbook.classes.Image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Menu extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    Button allRecipesBtn, findRecipeBtn, addRecipeBtn;

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

        addPhotos();

        System.out.println(databaseHelper.getDatabaseName());
//        getSupportActionBar().hide();
        values();
    }

    public void values(){
        allRecipesBtn = (Button)findViewById(R.id.buttonShowAllRecipes);
        findRecipeBtn = (Button)findViewById(R.id.buttonSelectProducts);
        addRecipeBtn = (Button)findViewById(R.id.buttonAddRecipe);

        allRecipesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RecipesListActivity.class);
                intent.putExtra("showFilteredList", false);
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
        addRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), AddRecipeActivity.class);
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

    public void addPhotos() {
        int[] lista = {};

        // int 16 wywala czyli ciasto29
        int j=databaseHelper.getAllImagesList().size();
        int imageNo = 29;
        for(int i=0; i<lista.length;i++){

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), lista[i]);
            Image image = new Image(imageNo, bitmap, imageNo);
            databaseHelper.addImage(image);
            j++;
            imageNo++;
//        }
        }
    }


}
