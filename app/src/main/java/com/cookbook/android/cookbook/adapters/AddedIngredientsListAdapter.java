package com.cookbook.android.cookbook.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import com.cookbook.android.cookbook.R;
import com.cookbook.android.cookbook.classes.Ingredient;
import com.cookbook.android.cookbook.classes.Product;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class AddedIngredientsListAdapter extends BaseAdapter {

    public List<Ingredient> ingredients ;
    Context context;
    LayoutInflater inflater;


    public AddedIngredientsListAdapter(Context context, List<Ingredient> ingredientList){
        this.ingredients = ingredientList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return ingredients.size();
    }

    @Override
    public Object getItem(int position) {
        return ingredients.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listView = convertView;
        if (convertView == null) {
            inflater = (LayoutInflater) context.getSystemService(LAYOUT_INFLATER_SERVICE);
            listView = inflater.inflate(R.layout.layout_add_ingredients_list, null);
        }
        Product product = ingredients.get(position).getProduct();
        String name = product.getName();
        String quantity = ingredients.get(position).getQuantity();


        //get layout parts
        TextView prodNameTV = (TextView)listView.findViewById(R.id.ingredientProductName);
        TextView quantTV = (TextView)listView.findViewById(R.id.ingredientQuantity);
        ImageButton delteButton = (ImageButton) listView.findViewById(R.id.deleteImageButton);

        Log.e("AddedIngredListAdapter","listView == null -> "+(listView==null));
        Log.e("AddedIngredListAdapter","prodNameTV == null -> "+(prodNameTV==null));
        Log.e("AddedIngredListAdapter","quantTV == null -> "+(quantTV==null));
        //set texts
        prodNameTV.setText(name);
        quantTV.setText(quantity);

        //add Listener to deleteButton
        delteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //delete added ingredient
                Log.i("AddRecipeAdapter","position "+position);
                Log.i("AddRecipeAdapter","ingredients.size():  "+ingredients.size());
                ingredients.remove(position);
                notifyDataSetChanged();
            }
        });


        return listView;
    }
}
