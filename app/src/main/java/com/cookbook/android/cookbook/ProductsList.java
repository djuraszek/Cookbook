package com.cookbook.android.cookbook;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class ProductsList extends AppCompatActivity {

    TextView categoryTV;

    //todo this activity shows all produts availble
    //todo fragment manager?
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_products_list);
    }




}
