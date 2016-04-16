package com.example.anik.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anik.shop.adapters.BillListViewAdapter;
import com.example.anik.shop.database.Bill;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class BillListActivity extends ActionBarActivity {

    private Bill bill;
    private List<Map<String, String>> billList;
    private ListView listView;
    private BillListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        billList = new ArrayList<>();
        bill = new Bill(BillListActivity.this);
        billList = bill.groupBy();
        if (null == billList) {
            setContentView(R.layout.layout_empty_data);
            ((TextView) findViewById(R.id.textViewMessageEmptyData)).setText("No bill is enlisted yet");
            return;
        }
        setContentView(R.layout.activity_bill_information);
        listView = (ListView) findViewById(R.id.listViewForBillInformation);
        adapter = new BillListViewAdapter(BillListActivity.this, billList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String billKey = ((TextView) view.findViewById(R.id.billKeyForBillInformation)).getText().toString();
                Intent intent = new Intent(BillListActivity.this, BillDescriptionActivity.class);
                intent.putExtra("billKey", billKey);
                startActivity(intent);
            }
        });
    }

}
