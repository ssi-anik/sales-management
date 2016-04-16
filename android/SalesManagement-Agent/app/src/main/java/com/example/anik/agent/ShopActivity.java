package com.example.anik.agent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anik.agent.adapters.ShopListViewAdapter;
import com.example.anik.agent.helpers.AppCart;
import com.example.anik.agent.helpers.AppConstant;
import com.example.anik.agent.helpers.AppHelper;
import com.example.anik.agent.helpers.AppStorage;
import com.example.anik.agent.helpers.Shop;
import com.example.anik.agent.httpService.HttpService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ShopActivity extends ActionBarActivity {

    EditText shopSearchBox;
    ListView listView;
    ShopListViewAdapter adapter;
    List<Shop> shopList = new ArrayList<>();
    private String shops = "";
    private String next = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int statusCode = intent.getIntExtra("statusCode", 0);
        if (statusCode >= 400) {
            Toast.makeText(ShopActivity.this, AppConstant.MESSAGE_APP_ERROR, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        shops = intent.getStringExtra("response");
        next = AppStorage.getNextIntent();

        process_response();

        if (this.shopList.isEmpty()) {
            setContentView(R.layout.layout_empty_data);
            ((TextView) findViewById(R.id.textViewMessageEmptyData)).setText("No shop is for under you");
            finish();
            return;
        }

        setContentView(R.layout.activity_shop);
        String title = String.format("Select shop for %s", next);
        setTitle(title);
        listView = (ListView) findViewById(R.id.listViewForShops);
        adapter = new ShopListViewAdapter(ShopActivity.this, shopList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (!AppHelper.isNetworkAvailable()) {
                    Toast.makeText(ShopActivity.this, AppConstant.MESSAGE_NO_INTERNET, Toast.LENGTH_SHORT).show();
                    return;
                }
                String mobileNumber = ((TextView) view.findViewById(R.id.mobileNumberForShop)).getText().toString();
                for (Shop shop : shopList) {
                    if (shop.getMobileNumber().equals(mobileNumber)) {
                        AppCart.setShop(shop);
                        break;
                    }
                }

                Map<String, String> data = new HashMap<String, String>();
                data.put("next", next);

                Class nextClass = null;
                if (next.equals("quick order")) {
                    nextClass = QuickOrderActivity.class;
                } else {
                    nextClass = SelectProductActivity.class;
                }

                new HttpService(ShopActivity.this)
                        .onUrl(AppConstant.LINK_PRODUCTS_ORDER, AppCart.getShop().getMobileNumber())
                        .withMethod(HttpService.HTTP_GET)
                        .nextIntent(nextClass)
                        .execute();

            }
        });

        shopSearchBox = (EditText) findViewById(R.id.shopSearchBox);
        shopSearchBox.addTextChangedListener(new TextWatcher() {
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
        });
    }

    private void process_response() {
        try {
            JSONObject object = new JSONObject(shops);
            JSONArray data = object.getJSONArray("data");
            for (int i = 0; i < data.length(); ++i) {
                JSONObject shop = data.getJSONObject(i);
                String id = shop.getString("id");
                String companyName = shop.getString("company_name");
                String ownerName = shop.getString("own_name");
                String mobileNumber = shop.getString("phone");
                shopList.add(new Shop(id, companyName, ownerName, mobileNumber));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
