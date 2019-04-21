package com.cookbook.android.cookbook.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.cookbook.android.cookbook.classes.Product;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager manager) {
        super(manager);
    }

    public String getTitle(Fragment fragment){
        for(int i=0;i<mFragmentList.size();i++){
            if(mFragmentList.get(i) == fragment) return mFragmentTitleList.get(i);
        }
        return null;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
        ProductsFragment newFragment = (ProductsFragment) fragment;
    }

//    public List<Product> getCheckedProducts(){
//        List<Product> list = new ArrayList<>();
//        for(int i=0;i<mFragmentList.size();i++){
//            ProductsFragment fragment = (ProductsFragment) mFragmentList.get(i);
//            list.addAll(fragment.getCheckedProducts());
//        }
//        return list;
//    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
//            super.destroyItem(container, position, object);
        //when its not destroying it wont create fragments every time you swipe right or left
    }
}
