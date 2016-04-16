package com.example.anik.agent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anik.agent.adapters.ProductGridViewAdapter;
import com.example.anik.agent.helpers.AppConstant;
import com.example.anik.agent.helpers.AppHelper;
import com.example.anik.agent.helpers.AppStorage;
import com.example.anik.agent.helpers.Product;
import com.example.anik.agent.httpService.HttpService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ProductListActivity extends ActionBarActivity {

    GridView.OnItemClickListener productItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (!AppHelper.isNetworkAvailable()) {
                Toast.makeText(ProductListActivity.this, AppConstant.MESSAGE_NO_INTERNET, Toast.LENGTH_SHORT).show();
                return;
            }
            String productId = ((TextView) view.findViewById(R.id.productId)).getText().toString();
            Map<String, String> intentExtraValues = new HashMap<>();
            intentExtraValues.put("fromActivity", "ProductListActivity");
            HttpService httpService = new HttpService(ProductListActivity.this);
            httpService.onUrl(AppConstant.LINK_PRODUCTS, productId)
                    .withMethod(HttpService.HTTP_GET)
                    .nextIntent(ShowProduct.class)
                    .putExtraForIntent(intentExtraValues)
                    .execute();
        }
    };

    TextWatcher productSearchTextChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            adapter.getFilter().filter(s);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    String products;
    List<Product> productList = new ArrayList<>();
    private GridView gridView;
    private EditText editText;
    private ProductGridViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_product_list);
        Intent intent = getIntent();
        int statusCode = intent.getIntExtra("statusCode", 0);
        if (statusCode >= 400) {
            Toast.makeText(ProductListActivity.this, AppConstant.MESSAGE_DATA_PARSE_ERROR, Toast.LENGTH_SHORT).show();
            finish();
        }
        if (intent.hasExtra("response")) {
            products = intent.getStringExtra("response");
            AppStorage.setProductDetails(products);
        } else {
            products = AppStorage.getProductDetails();
        }
        prepare_products();
        build_view();
    }

    private void build_view() {
        if (productList.size() == 0) {
            setContentView(R.layout.layout_empty_data);
            TextView textView = (TextView) findViewById(R.id.textViewMessageEmptyData);
            textView.setText("Product is not available");
            return;
        }
        // if any product is enlisted
        setContentView(R.layout.activity_product_list);
        editText = (EditText) findViewById(R.id.productSearchBoxInProductListActivity);
        editText.addTextChangedListener(productSearchTextChanged);

        gridView = (GridView) findViewById(R.id.productGridViewInProductListActivity);
        adapter = new ProductGridViewAdapter(ProductListActivity.this, this.productList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(productItemClickListener);
    }

    private void prepare_products() {
        try {
            JSONObject response = new JSONObject(products);
            JSONObject data = response.getJSONObject("data");
            JSONArray products = data.getJSONArray("products");
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
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
