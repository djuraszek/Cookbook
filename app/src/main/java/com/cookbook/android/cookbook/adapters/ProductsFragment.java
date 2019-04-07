package com.cookbook.android.cookbook.adapters;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.cookbook.android.cookbook.DatabaseHelper;
import com.cookbook.android.cookbook.R;
import com.cookbook.android.cookbook.activities.Menu;
import com.cookbook.android.cookbook.classes.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ProductsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ProductsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ProductsFragment extends Fragment {
    boolean showComments=false;
    boolean showLogs=false;
    private DatabaseHelper databaseHelper;
    private View v;
    private ListView productsList;
    private TextView categoryTextView;
    private ProductsListAdapter listAdapter;
    private List<Product>categoryProductList;
    private String categoryName;
    private FragmentActivity myContext;

    public ProductsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ProductsFragment(DatabaseHelper db, String categoryName){
//        this.categoryProductList = productList;
        this.databaseHelper = db;
        this.categoryName = categoryName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(showLogs) Log.d("->FragmentRepertory", "onCreate");
        if(databaseHelper == null){
            Log.e("->FragmentRepertory", "null databaseHelper");
            databaseHelper = new DatabaseHelper(getActivity().getApplicationContext());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(showLogs) Log.d("->FragmentRepertory", "onCreateView");

        v = container;
        v = inflater.inflate(R.layout.fragment_products, container, false);

        productsList = (ListView) v.findViewById(R.id.productLV);      //listView with product from category
        categoryProductList = getProducts();
        categoryTextView = (TextView) v.findViewById(R.id.categoryTV);
        categoryTextView.setText(categoryName);

        if (categoryProductList.size() == 0) {
            Log.e("FragmentRepertory", "categoryProducts.size() = " + categoryProductList.size());
        } else {
            listAdapter = new ProductsListAdapter(getContext(), categoryProductList);
            productsList.setAdapter(listAdapter);
//            Log.d("ProductsFragment", "checked " + listAdapter.getCheckedProducts().size());
            if (showComments)
                Log.e("FragmentRepertory", "productsList " + productsList.getCount());
        }
//        Log.d("ProductsFragment", "productsList.getCount(): "+productsList.getCount());

        return v;
    }

    public interface OnFragmentInteractionListener{
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    public List<Product> getProducts(){
        List<Product> allProductList = databaseHelper.getAllProductsList();
        Log.d("ProductsFragment","category: "+categoryName);
        System.out.println("ProductsFragment\n "+allProductList);
         List<Product> productList = new ArrayList<>();

         for(int i=0; i<allProductList.size();i++){
             if(allProductList.get(i).getName()!= null) {
                 String productCategory = allProductList.get(i).getCategory();
             System.out.println(productCategory+ " "+ categoryName+ " ");

                 if (productCategory.compareTo(categoryName) == 0) {
                     productList.add(allProductList.get(i));
                 }
             }
         }
        return productList;
    }

//    public List<Product> getCheckedProducts(){
//        ProductsListAdapter adapter = (ProductsListAdapter)productsList.getAdapter();
//        return adapter.getCheckedProducts();
//    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Intent intent = new Intent(getContext(), Menu.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }




}