package com.example.anik.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.anik.shop.adapters.TabsPagerAdapter;
import com.example.anik.shop.helpers.AppConstant;
import com.example.anik.shop.helpers.AppStorage;
import com.example.anik.shop.helpers.Product;
import com.example.anik.shop.helpers.SubCategory;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SubCategoryListActivity extends AppCompatActivity implements ActionBar.TabListener {

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            actionBar.setSelectedNavigationItem(position);
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private ViewPager viewPager;
    private ActionBar actionBar;
    private List<String> tabs = new ArrayList<>();
    private List<SubCategory> subCategoryList = new ArrayList<>();
    private List<Product> productList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub_category);
        Intent intent = getIntent();
        int statusCode = intent.getIntExtra("statusCode", 0);
        if (statusCode >= 400) {
            Toast.makeText(SubCategoryListActivity.this, AppConstant.MESSAGE_DATA_PARSE_ERROR, Toast.LENGTH_SHORT).show();
            finish();
        }
        String sub_categories_json;
        if (intent.hasExtra("response")) {
            sub_categories_json = intent.getStringExtra("response");
            AppStorage.setSubCategories(sub_categories_json);
        } else {
            sub_categories_json = AppStorage.getSubCategories();
        }
        tabs.add("Sub category");
        tabs.add("Products");
        process_response(sub_categories_json);
        build_view();
    }

    private void process_response(String response) {
        try {
            List<JSONArray> productsJSONArray = new ArrayList<>();

            JSONObject object = new JSONObject(response);
            JSONObject data = object.getJSONObject("data");
            JSONArray sub_categories = data.getJSONArray("subcategories");
            JSONArray productsUnderCategory = data.getJSONArray("products");

            productsJSONArray.add(productsUnderCategory);

            for (int i = 0; i < sub_categories.length(); ++i) {
                JSONObject sub_category = sub_categories.getJSONObject(i);
                String sub_category_id = sub_category.getString("id");
                String sub_category_name = sub_category.getString("name");
                String sub_category_photo_url = sub_category.getString("sub_category_photo");
                subCategoryList.add(new SubCategory(sub_category_id, sub_category_name, sub_category_photo_url));
                JSONArray productsUnderSubCategory = sub_category.getJSONArray("products");
                productsJSONArray.add(productsUnderSubCategory);
            }
            for(JSONArray products : productsJSONArray) {
                for (int i = 0; i < products.length(); ++i) {
                    JSONObject product = products.getJSONObject(i);
                    String product_id = product.getString("id");
                    String product_name = product.getString("name");
                    List<String> images = new ArrayList<>();
                    JSONObject image = product.getJSONObject("single_image");
                    String link = image.getString("image_link");
                    images.add(link);
                    productList.add(new Product(product_id, product_name, images));
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    private void build_view() {
        viewPager = (ViewPager) findViewById(R.id.viewPager);
        actionBar = getSupportActionBar();
        TabsPagerAdapter mAdapter = new TabsPagerAdapter(getSupportFragmentManager(), this.tabs, this.subCategoryList, this.productList);

        viewPager.setAdapter(mAdapter);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        for (String tab_name : tabs) {
            actionBar.addTab(actionBar.newTab().setText(tab_name).setTabListener(this));
        }
        viewPager.setOnPageChangeListener(onPageChangeListener);
    }

    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
