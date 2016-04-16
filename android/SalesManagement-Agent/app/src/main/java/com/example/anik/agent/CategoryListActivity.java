package com.example.anik.agent;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anik.agent.adapters.CategoryGridViewAdapter;
import com.example.anik.agent.helpers.AppConstant;
import com.example.anik.agent.helpers.AppHelper;
import com.example.anik.agent.helpers.AppStorage;
import com.example.anik.agent.helpers.Category;
import com.example.anik.agent.helpers.CustomProgressDialog;
import com.example.anik.agent.httpService.HttpService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CategoryListActivity extends AppCompatActivity {

    GridView.OnItemClickListener categoryItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (!AppHelper.isNetworkAvailable()) {
                Toast.makeText(CategoryListActivity.this, AppConstant.MESSAGE_NO_INTERNET, Toast.LENGTH_SHORT).show();
                return;
            }
            String categoryId = ((TextView) view.findViewById(R.id.categoryId)).getText().toString();
            HttpService httpService = new HttpService(CategoryListActivity.this);
            httpService.onUrl(AppConstant.LINK_CATEGORIES, categoryId)
                    .withMethod(HttpService.HTTP_GET)
                    .nextIntent(SubCategoryListActivity.class)
                    .execute();

        }
    };
    TextWatcher categorySearchTextChanged = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            gridViewAdapter.getFilter().filter(s);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private GridView gridView;
    private EditText categorySearchBox;
    private String categories = "";
    private List<Category> categoriesList = new ArrayList<>();
    private CategoryGridViewAdapter gridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
        Intent intent = getIntent();
        int statusCode = intent.getIntExtra("statusCode", 0);
        if (statusCode >= 400) {
            Toast.makeText(CategoryListActivity.this, AppConstant.MESSAGE_DATA_PARSE_ERROR, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (intent.hasExtra("response")) {
            categories = intent.getStringExtra("response");
            AppStorage.setCategories(categories);
        } else {
            categories = AppStorage.getCategories();
        }
        categorySearchBox = (EditText) findViewById(R.id.categorySearchBox);
        categorySearchBox.addTextChangedListener(categorySearchTextChanged);
        gridView = (GridView) findViewById(R.id.categoryGridView);
        buildGridView();
    }

    private void buildGridView() {
        ProgressDialog progressDialog = CustomProgressDialog.builder(CategoryListActivity.this);
        progressDialog.show();
        try {
            JSONObject jsonObject = new JSONObject(categories);
            JSONArray data = jsonObject.getJSONArray("data");
            for (int i = 0; i < data.length(); ++i) {
                JSONObject category = data.getJSONObject(i);
                int category_id = category.getInt("id");
                String category_name = category.getString("name");
                String category_image_url = category.getString("category_photo");
                categoriesList.add(new Category(category_id, category_name, category_image_url));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        gridViewAdapter = new CategoryGridViewAdapter(CategoryListActivity.this, categoriesList);
        gridView.setAdapter(gridViewAdapter);
        gridView.setOnItemClickListener(categoryItemClickListener);
        progressDialog.dismiss();
    }
}
