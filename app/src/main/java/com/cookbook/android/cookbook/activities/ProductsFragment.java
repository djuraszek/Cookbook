package com.cookbook.android.cookbook.activities;

import android.annotation.SuppressLint;
import android.content.Context;
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
import com.cookbook.android.cookbook.adapters.ProductsListAdaper;
import com.cookbook.android.cookbook.classes.Product;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
    boolean showComments=true;
    boolean showLogs=true;
    private DatabaseHelper databaseHelper;
    private View v;
    private ListView productsList;
    private TextView categoryTextView;
    private ProductsListAdaper listAdapter;
    private List<Product>categoryProductList;
    private String categoryName;
    private FragmentActivity myContext;

    public ProductsFragment() {
        // Required empty public constructor
    }

    @SuppressLint("ValidFragment")
    public ProductsFragment(DatabaseHelper wd, List<Product> productList, String categoryName){
        this.categoryProductList = productList;
        this.databaseHelper = wd;
        this.categoryName = categoryName;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(showLogs) Log.d("->FragmentRepertory", "onCreate");

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if(showLogs)Log.d("->FragmentRepertory", "onCreateView");

        v = container;
        v = inflater.inflate(R.layout.fragment_products, container, false);

        productsList = (ListView) v.findViewById(R.id.productLV);      //listView with product from category
        List<Product> categoryProducts = categoryProductList;
        categoryTextView = (TextView) v.findViewById(R.id.categoryTV);
        categoryTextView.setText(categoryName);

        if(categoryProducts.size() == 0){
            Log.e("FragmentRepertory","categoryProducts.size() = "+categoryProducts.size());
        }

        listAdapter = new ProductsListAdaper(getContext(), categoryProducts);
        productsList.setAdapter(listAdapter);

        if (showComments) System.out.println("productsList " + productsList.getCount());

        productsList.getCount();
        return v;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
