package com.example.anik.shop;

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

import com.example.anik.shop.adapters.OfferListViewAdapter;
import com.example.anik.shop.helpers.AppConstant;
import com.example.anik.shop.helpers.AppStorage;
import com.example.anik.shop.helpers.Offer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class OfferActivity extends ActionBarActivity {

    TextWatcher offerSearchListener = new TextWatcher() {
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
    private ListView listView;
    private EditText offerSearchBox;
    private String offer = "";
    private List<Offer> offerList = new ArrayList<>();
    ListView.OnItemClickListener offerItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView textView = (TextView) view.findViewById(R.id.offerIdForListView);
            String offerId = textView.getText().toString();
            for (Offer offer : offerList) {
                if (offer.getId().equals(offerId)) {
                    Intent intent = new Intent(OfferActivity.this, ShowOffer.class);
                    intent.putExtra("offer", offer);
                    startActivity(intent);
                    return;
                }
            }
        }
    };
    private OfferListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int statusCode = intent.getIntExtra("statusCode", 0);
        if (statusCode >= 400) {
            Toast.makeText(OfferActivity.this, AppConstant.MESSAGE_DATA_PARSE_ERROR, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (intent.hasExtra("response")) {
            offer = intent.getStringExtra("response");
            AppStorage.setOffers(offer);
        } else {
            offer = AppStorage.getOffers();
        }
        prepare_offer();
        show_offer();
    }

    private void prepare_offer() {
        try {
            JSONObject object = new JSONObject(offer);
            JSONArray data = object.getJSONArray("data");
            for (int i = 0; i < data.length(); ++i) {
                JSONObject offer = data.getJSONObject(i);
                String id = offer.getString("id");
                String title = offer.getString("title");
                String description = offer.getString("description");
                String price = offer.getString("price");
                String percentage = offer.getString("percentage");
                String startDate = offer.getString("start_date");
                String endDate = offer.getString("end_date");
                String link = offer.has("link") ? offer.getString("link") : "";
                String youtube = offer.has("youtube") ? offer.getString("youtube") : "";
                String image_link = offer.has("image_link") ? offer.getString("image_link") : "";
                offerList.add(new Offer(id, title, description, price, percentage, startDate, endDate, link, youtube, image_link));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void show_offer() {
        if (this.offerList.size() == 0) {
            setContentView(R.layout.layout_empty_data);
            TextView textView = (TextView) findViewById(R.id.textViewMessageEmptyData);
            textView.setText("No offer is available");
            return;
        }
        setContentView(R.layout.activity_offer);
        offerSearchBox = (EditText) findViewById(R.id.offerSearchBox);
        offerSearchBox.addTextChangedListener(offerSearchListener);

        listView = (ListView) findViewById(R.id.offerListView);
        adapter = new OfferListViewAdapter(OfferActivity.this, offerList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(offerItemClickListener);
    }

}
