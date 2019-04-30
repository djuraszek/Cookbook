package com.cookbook.android.cookbook.activities;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.cookbook.android.cookbook.DatabaseHelper;
import com.cookbook.android.cookbook.R;
import com.cookbook.android.cookbook.adapters.ProductsFragment;
import com.cookbook.android.cookbook.adapters.ProductsListAdapter;
import com.cookbook.android.cookbook.adapters.ViewPagerAdapter;
import com.cookbook.android.cookbook.classes.Product;

import java.util.List;

public class ProductsList extends AppCompatActivity {

    TextView categoryTV;
    ListView productLV;
    ViewPager viewPager;
    private List<String> categoriesList;
    public DatabaseHelper db ;

    public ViewPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
        db = new DatabaseHelper(getApplicationContext());

        categoriesList = db.getDistinctCategories();
        Log.e("ProductsList","caegoriesList.size() "+categoriesList.size());

        viewPager = (ViewPager)findViewById(R.id.productListVP);
        adapter = new ViewPagerAdapter(getSupportFragmentManager());


        for(int i=0; i<categoriesList.size(); i++) {
            Bundle args = new Bundle();

            String categoryName = categoriesList.get(i);
            args.putString("categoryName", categoryName);
            ProductsFragment fragment = new ProductsFragment(db, categoryName);
            fragment.setArguments(args);

            adapter.addFragment(fragment, categoryName);

        }
        viewPager.setAdapter(adapter);

    }
}
