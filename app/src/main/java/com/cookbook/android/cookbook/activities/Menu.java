package com.cookbook.android.cookbook.activities;

import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.cookbook.android.cookbook.DatabaseHelper;
import com.cookbook.android.cookbook.R;
import com.cookbook.android.cookbook.classes.Image;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Locale;

public class Menu extends AppCompatActivity {
    DatabaseHelper databaseHelper;
    Button allRecipesBtn, findRecipeBtn, addRecipeBtn, topTenBtn;
    Button language;

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

//        addPhotos();

        System.out.println(databaseHelper.getDatabaseName());
//        getSupportActionBar().hide();
        values();
    }

    public void values(){
        allRecipesBtn = (Button)findViewById(R.id.buttonShowAllRecipes);
        findRecipeBtn = (Button)findViewById(R.id.buttonSelectProducts);
        addRecipeBtn = (Button)findViewById(R.id.buttonAddRecipe);
        topTenBtn = (Button)findViewById(R.id.buttonTopTen);
        language = (Button)findViewById(R.id.language);

        topTenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), RecipesListActivity.class);
                intent.putExtra("showTopRecipes", true);
                startActivity(intent);
            }
        });
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
        language.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(language.getText().toString());
                Configuration conf = new Configuration(getResources().getConfiguration());
                switch(language.getText().toString()){
                    case "EN":
                        //change to english
                        System.out.println(getApplicationContext().getResources().getConfiguration().getLocales().toLanguageTags());
                        conf.locale = Locale.ENGLISH;
//                        conf.setLocale(Locale.US);
                        getApplicationContext().getResources().getConfiguration().setLocale(Locale.ENGLISH);
                        System.out.println("conf: "+conf.getLocales().toLanguageTags());

                        break;
                    default:
                        System.out.println("default");
                        conf.setLocale(Locale.forLanguageTag("pl-PL"));
                        getApplicationContext().getResources().getConfiguration().setLocale(Locale.forLanguageTag("pl-PL"));
                        System.out.println("conf: "+conf.getLocales().toLanguageTags());
                        break;
                }
                getResources().updateConfiguration(conf,getResources().getDisplayMetrics());
                System.out.println(getApplicationContext().getResources().getConfiguration().getLocales().toLanguageTags());
                finish();
                startActivity(getIntent());
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

        int imageNo = 50;
        for(int i=0; i<lista.length;i++){

            Bitmap bitmap = BitmapFactory.decodeResource(getResources(), lista[i]);
            Image image = new Image(imageNo, bitmap, imageNo);
            databaseHelper.addImage(image);
            imageNo++;
//        }
        }
    }

    @SuppressWarnings("deprecation")
    private void setLocale(Locale locale){
//        SharedPrefUtils.saveLocale(locale); // optional - Helper method to save the selected language to SharedPreferences in case you might need to attach to activity context (you will need to code this)
        Resources resources = getResources();
        Configuration configuration = resources.getConfiguration();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1){
            configuration.setLocale(locale);
        } else{
            configuration.locale=locale;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N){
            getApplicationContext().createConfigurationContext(configuration);
        } else {
            resources.updateConfiguration(configuration,displayMetrics);
        }
    }

}
