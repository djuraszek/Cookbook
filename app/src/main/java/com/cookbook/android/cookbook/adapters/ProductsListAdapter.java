package com.cookbook.android.cookbook.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.cookbook.android.cookbook.DatabaseHelper;
import com.cookbook.android.cookbook.R;
import com.cookbook.android.cookbook.classes.Product;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.LAYOUT_INFLATER_SERVICE;

public class ProductsListAdapter extends BaseAdapter {

    boolean showComments=false;
    boolean showLogs=false;

    private Product product;
    private Context context;
    private LayoutInflater inflater;
    private List<Product> productList;
    private boolean[] mChecked;

    // sparse boolean array for checking the state of the items
    private SparseBooleanArray itemStateArray = new SparseBooleanArray();

    public ProductsListAdapter(Context context, List<Product> productList) {
        Log.e("SeansListAdapter","set Adapter od Screening List");
        this.context = context;
        this.productList = productList;
//        checkedProducts = new ArrayList<>();
        mChecked= new boolean[productList.size()];
        for(int i=0; i<mChecked.length; i++){
            mChecked[i]=false;
            //Toast.makeText(con, checked.toString(),Toast.LENGTH_SHORT).show();
        }
        if(showLogs)Log.v("ListAdapter","productsList: "+ productList.size());
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

        CheckBox productCheckBox = (CheckBox)listView.findViewById(R.id.categoryCheckBox);
        String name = product.getName();
        productCheckBox.setText(name);
        productCheckBox.setTag(Integer.valueOf(position)); // set the tag so we can identify the correct row in the listener
        productCheckBox.setChecked(mChecked[position]); // set the status as we stored it        
        productCheckBox.setOnCheckedChangeListener(mListener); // set the listener


        return listView;
    }

    CompoundButton.OnCheckedChangeListener mListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            mChecked[(Integer)buttonView.getTag()] = isChecked; // get the tag so we know the row and store the status
        }
    };

}


//productCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            @Override
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                if(isChecked){
//                    buttonView.setChecked(true);
//                }
//                else{
//                    buttonView.setChecked(false);
//                }
//            }
//        });
//
//        listView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                int adapterPosition = position;
//                if (!itemStateArray.get(adapterPosition, false)) {
//                    productCheckBox.setChecked(true);
//                    itemStateArray.put(adapterPosition, true);
//                }
//                else  {
//                    productCheckBox.setChecked(false);
//                    itemStateArray.put(adapterPosition, false);
//                }
////                if(productCheckBox.isChecked()){
////                    productCheckBox.setChecked(false);
////                    checkedProducts.remove(product);
////                }
////                else {
////                    productCheckBox.setChecked(true);
////                    checkedProducts.add(product);
////                }
//                notifyDataSetChanged();
//            }
//        });