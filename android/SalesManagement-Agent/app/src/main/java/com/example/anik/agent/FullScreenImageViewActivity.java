package com.example.anik.agent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.widget.Toast;

import com.example.anik.agent.adapters.FullScreenImageAdapter;
import com.example.anik.agent.helpers.AppConstant;
import com.example.anik.agent.helpers.Product;


public class FullScreenImageViewActivity extends ActionBarActivity {

    private ViewPager viewPager;
    private FullScreenImageAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_screen_image_view);
        Intent intent = getIntent();
        Product product = null;
        if (intent.hasExtra("product")) {
            product = (Product) intent.getSerializableExtra("product");
        }
        if (null == product) {
            Toast.makeText(FullScreenImageViewActivity.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        int position = intent.getIntExtra("position", 0);
        viewPager = (ViewPager) findViewById(R.id.viewPagerForFullScreen);
        adapter = new FullScreenImageAdapter(FullScreenImageViewActivity.this, product.getProductImageUrls());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
    }
}
