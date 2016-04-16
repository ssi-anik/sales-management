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

import com.example.anik.agent.ProductListActivity;
import com.example.anik.agent.R;
import com.example.anik.agent.adapters.SubCategoryGridViewAdapter;
import com.example.anik.agent.helpers.AppConstant;
import com.example.anik.agent.helpers.AppHelper;
import com.example.anik.agent.helpers.SubCategory;
import com.example.anik.agent.httpService.HttpService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anik on 11-Aug-15, 011.
 */
public class SubCategoryFragment extends Fragment {

    GridView.OnItemClickListener subCategoryItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            if (!AppHelper.isNetworkAvailable()) {
                Toast.makeText(getActivity(), AppConstant.MESSAGE_NO_INTERNET, Toast.LENGTH_SHORT).show();
                return;
            }
            String subCategoryId = ((TextView) view.findViewById(R.id.subCategoryId)).getText().toString();
            HttpService httpService = new HttpService(getActivity());
            httpService.onUrl(AppConstant.LINK_SUB_CATEGORIES, subCategoryId)
                    .withMethod(HttpService.HTTP_GET)
                    .nextIntent(ProductListActivity.class)
                    .execute();
        }
    };

    TextWatcher subCategorySearchTextChanged = new TextWatcher() {
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

    private List<SubCategory> subCategoryList = new ArrayList<>();
    private GridView gridView;
    private EditText editText;
    private SubCategoryGridViewAdapter adapter;

    public void setSubCategoryList(List<SubCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view;
        if (this.subCategoryList.size() == 0) {
            view = inflater.inflate(R.layout.layout_empty_data, container, false);
            TextView textView = (TextView) view.findViewById(R.id.textViewMessageEmptyData);
            textView.setText("Sub category is not available");
            return view;
        } else {
            view = inflater.inflate(R.layout.fragment_sub_category, container, false);
        }
        editText = (EditText) view.findViewById(R.id.subCategorySearchBox);
        editText.addTextChangedListener(subCategorySearchTextChanged);
        gridView = (GridView) view.findViewById(R.id.subCategoryGridView);
        adapter = new SubCategoryGridViewAdapter(getActivity(), this.subCategoryList);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(subCategoryItemClickListener);
        return view;
    }


}
