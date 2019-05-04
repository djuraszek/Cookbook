package com.cookbook.android.cookbook.adapters;


import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

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
    public static List<Integer> checkedProduct = new ArrayList<>();

    public ProductsListAdapter(Context context, List<Product> productList) {
        Log.e("SeansListAdapter","set Adapter od Screening List");
        this.context = context;
        this.productList = productList;
        mChecked= new boolean[productList.size()];
        for(int i=0; i<mChecked.length; i++){
            mChecked[i]=false;
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
        final CheckBox productCheckBox = (CheckBox)listView.findViewById(R.id.categoryCheckBox);
        String name = product.getName();
        productCheckBox.setText(name);
        productCheckBox.setTag(Integer.valueOf(position)); // set the tag so we can identify the correct row in the listener
        productCheckBox.setChecked(mChecked[position]); // set the status as we stored it        
        productCheckBox.setOnCheckedChangeListener(mListener); // set the listener
        return listView;
    }

    public List<Integer> getSelectedItems(){
        return checkedProduct;
    }

    CompoundButton.OnCheckedChangeListener mListener = new CompoundButton.OnCheckedChangeListener() {
        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
            int id = (Integer)buttonView.getTag();
            mChecked[id] = isChecked;
            if (isChecked) {
//                Toast.makeText(context, "DODANO" + productList.get(id).getName() + " size-" + checkedProduct.size(), Toast.LENGTH_LONG).show();
                checkedProduct.add(productList.get(id).getProductID());
            }
            else {
//                Toast.makeText(context, "USUNIETO" + productList.get(id).getName() + " size-" + checkedProduct.size(), Toast.LENGTH_LONG).show();
                deleteItem(productList.get(id).getProductID());
            }
        }
    };

    public void deleteItem(int id) {
        for(int i = 0; i < checkedProduct.size(); i++) {
            if(checkedProduct.get(i) == id) {
                checkedProduct.remove(i);
                return;
            }
        }
    }
}