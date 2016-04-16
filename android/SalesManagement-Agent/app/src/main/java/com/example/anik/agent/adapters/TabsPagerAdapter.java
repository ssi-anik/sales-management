package com.example.anik.agent.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.anik.agent.fragments.ProductFromCategoryFragment;
import com.example.anik.agent.fragments.SubCategoryFragment;
import com.example.anik.agent.helpers.Product;
import com.example.anik.agent.helpers.SubCategory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anik on 12-Aug-15, 012.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
    private List<SubCategory> subCategoryList = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();
    private List<String> tabs = new ArrayList<>();

    public TabsPagerAdapter(FragmentManager fm, List<String> tabs, List<SubCategory> subCategories, List<Product> products) {
        super(fm);
        this.tabs.addAll(tabs);
        this.subCategoryList.addAll(subCategories);
        this.productList.addAll(products);
    }

    @Override
    public Fragment getItem(int index) {
        switch (index) {
            case 0:
                SubCategoryFragment subCategoryFragment = new SubCategoryFragment();
                subCategoryFragment.setSubCategoryList(subCategoryList);
                return subCategoryFragment;
            case 1:
                ProductFromCategoryFragment productFromCategoryFragment = new ProductFromCategoryFragment();
                productFromCategoryFragment.setProductList(productList);
                return productFromCategoryFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return tabs.size();
    }
}
