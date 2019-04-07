package com.cookbook.android.cookbook.activities;

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
    private DatabaseHelper db ;

    //todo this activity shows all produts availble
    //todo fragment manager?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
        db = new DatabaseHelper(getApplicationContext());
        //todo get all categories
        categoriesList = db.getDistinctCategories();
        Log.e("ProductsList","caegoriesList.size() "+categoriesList.size());

        //todo get PageViewer
        viewPager = (ViewPager)findViewById(R.id.productListVP);
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        for(int i=0; i<categoriesList.size(); i++) {
            String categoryName = categoriesList.get(i);
            ProductsFragment fragment = new ProductsFragment(db, categoryName);
            adapter.addFragment(fragment, categoryName);

        }
        viewPager.setAdapter(adapter);
        viewPager.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ViewPager thisVP = (ViewPager)v;
//                ViewPagerAdapter adapter = (ViewPagerAdapter)thisVP.getAdapter();
//                List<Product> prod = adapter.getCheckedProducts();
//                if(prod.size()>0){
//                    Log.e("ProductsList","checked list \n " + prod);
//                }
            }
        });
    }

    public void setVals(){
        categoryTV = (TextView)findViewById(R.id.categoryTV);
        productLV = (ListView)findViewById(R.id.productLV);
        //TODO get list of products for category
        ProductsListAdapter adapter = new ProductsListAdapter(getApplicationContext(),null);
        productLV.setAdapter(adapter);
    }

}
