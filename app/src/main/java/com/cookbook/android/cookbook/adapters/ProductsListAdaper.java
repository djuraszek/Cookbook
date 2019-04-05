package com.cookbook.android.cookbook.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;

import com.cookbook.android.cookbook.DatabaseHelper;
import com.cookbook.android.cookbook.R;
import com.cookbook.android.cookbook.classes.Product;

import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ProductsListAdaper extends BaseAdapter {

    boolean showComments=false;
    boolean showLogs=false;

    private Product product;
    private DatabaseHelper databaseHelper;
    private Context context;
    private LayoutInflater inflater;
    private List<Product> productList;

    public ProductsListAdaper(Context context, List<Product> productList) {
        Log.e("SeansListAdapter","set Adapter od Screening List");
        this.context = context;
        this.productList = productList;

        if(showLogs)Log.v("ListAdapter","seansow w dniu: "+ productList.size());
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
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

            listView = inflater.inflate(R.layout.layout_product_list_item, null);
        }

        product = productList.get(position);

        final CheckBox productCheckBox = (CheckBox)listView.findViewById(R.id.categoryCheckBox);
        String name = product.getName();
        productCheckBox.setText(name);

        listView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(productCheckBox.isChecked())
                    productCheckBox.setChecked(false);
                else
                    productCheckBox.setChecked(true);

            }
        });

        return listView;
    }
}
