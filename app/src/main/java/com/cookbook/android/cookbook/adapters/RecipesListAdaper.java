package com.cookbook.android.cookbook.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cookbook.android.cookbook.DatabaseHelper;
import com.cookbook.android.cookbook.R;
import com.cookbook.android.cookbook.activities.RecipeActivity;
import com.cookbook.android.cookbook.classes.Image;
import com.cookbook.android.cookbook.classes.Recipe;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class RecipesListAdaper extends BaseAdapter {

    boolean showComments=false;
    boolean showLogs=false;

    private Recipe recipe;
    private DatabaseHelper databaseHelper;
    private Context context;
    private LayoutInflater inflater;
    private List<Recipe> recipeList;
    private List<Image> imageList;

    public RecipesListAdaper(Context context, List<Recipe> recipeList) {
        Log.e("SeansListAdapter","set Adapter od Screening List");
        this.context = context;
        this.recipeList = recipeList;
        this.imageList = new ArrayList<>();

        for(int i=0;i<recipeList.size();i++){
            imageList.add(recipeList.get(i).getImage());
        }

        if(showLogs)Log.v("ListAdapter","seansow w dniu: "+recipeList.size());
    }

    @Override
    public int getCount() {
        return recipeList.size();
    }

    @Override
    public Object getItem(int position) {
        return recipeList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            listView = inflater.inflate(R.layout.layout_recipe_list, null);
        }
        recipe = recipeList.get(position);
//        imageList.add(position,recipe.getImage());

        TextView recipeName = (TextView)listView.findViewById(R.id.nameRecipeList);
        String name = recipe.getName();
        recipeName.setText(name);

        TextView ratingName = (TextView)listView.findViewById(R.id.ratingRecipeList);
        String rating = "Ocena: "+recipe.getRating();
        ratingName.setText(rating);

        if(recipe.getBitmapImage()!= null) {
            ImageView recipeImg = (ImageView) listView.findViewById(R.id.imgRecipeList);
            recipeImg.setImageBitmap(imageList.get(position).getBitmap());
        }
        else{
            ImageView recipeImg = (ImageView) listView.findViewById(R.id.imgRecipeList);
            Drawable drawable = context.getResources().getDrawable(R.drawable.no_image_icon);
            recipeImg.setImageDrawable(drawable);
        }

        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent recipeIntent = new Intent(context, RecipeActivity.class);
                int recipeID = recipeList.get(position).getRecipeID();
                recipeIntent.putExtra("recipe", recipeID);
                context.startActivity(recipeIntent);
            }
        });
        return listView;
    }
}
