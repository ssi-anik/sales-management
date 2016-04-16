package com.example.anik.agent.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anik.agent.R;
import com.example.anik.agent.ShowProduct;
import com.example.anik.agent.adapters.ProductGridViewAdapter;
import com.example.anik.agent.helpers.AppConstant;
import com.example.anik.agent.helpers.AppHelper;
import com.example.anik.agent.helpers.Product;
import com.example.anik.agent.httpService.HttpService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Anik on 11-Aug-15, 011.
 */
public class ProductFromCategoryFragment extends Fragment {

    GridView.OnItemClickListener productItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (!AppHelper.isNetworkAvailable()) {
                Toast.makeText(getActivity(), AppConstant.MESSAGE_NO_INTERNET, Toast.LENGTH_SHORT).show();
                return;
            }
            String productId = ((TextView) view.findViewById(R.id.productId)).getText().toString();
            HttpService httpService = new HttpService(getActivity());
            Map<String, String> intentExtraValues = new HashMap<>();
            intentExtraValues.put("fromActivity", "SubCategoryListActivity");
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

    private List<Product> productList = new ArrayList<>();
    private GridView gridView;
    private EditText editText;
    private ProductGridViewAdapter adapter;

    public void setProductList(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        if (this.productList.size() == 0) {
            view = inflater.inflate(R.layout.layout_empty_data, container, false);
            TextView textView = (TextView) view.findViewById(R.id.textViewMessageEmptyData);
            textView.setText("Product is not available");
            return view;
        } else {
            view = inflater.inflate(R.layout.fragment_product, container, false);
        }
        editText = (EditText) view.findViewById(R.id.productSearchBox);
        editText.addTextChangedListener(productSearchTextChanged);
        gridView = (GridView) view.findViewById(R.id.productGridView);
        adapter = new ProductGridViewAdapter(getActivity(), this.productList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(productItemClickListener);
        return view;
    }
}
