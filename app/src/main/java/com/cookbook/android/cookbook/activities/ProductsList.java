package com.cookbook.android.cookbook.activities;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.cookbook.android.cookbook.R;
import com.cookbook.android.cookbook.adapters.ProductsListAdaper;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

public class ProductsList extends AppCompatActivity {

    TextView categoryTV;
    ListView productLV;


    //todo this activity shows all produts availble
    //todo fragment manager?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);

        //todo get all categories

        //todo get PageViewer

    }

    public void setVals(){
        categoryTV = (TextView)findViewById(R.id.categoryTV);
        productLV = (ListView)findViewById(R.id.productLV);
        //TODO get list of products for category
        ProductsListAdaper adapter = new ProductsListAdaper(getApplicationContext(),null);
        productLV.setAdapter(adapter);
    }

}
