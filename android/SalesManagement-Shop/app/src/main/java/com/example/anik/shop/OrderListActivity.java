package com.example.anik.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anik.shop.adapters.OrderListViewAdapter;
import com.example.anik.shop.database.Order;

import java.util.List;
import java.util.Map;


public class OrderListActivity extends ActionBarActivity {

    Order order;
    ListView listView;
    OrderListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        order = new Order(OrderListActivity.this);
        List<Map<String, String>> orderList = order.groupBy();
        if (null == orderList) {
            setContentView(R.layout.layout_empty_data);
            ((TextView) findViewById(R.id.textViewMessageEmptyData)).setText("No order is placed yet.");
            return;
        }
        setContentView(R.layout.activity_order_information);
        listView = (ListView) findViewById(R.id.listViewForOrderInformation);
        adapter = new OrderListViewAdapter(OrderListActivity.this, orderList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String batch = ((TextView) view.findViewById(R.id.batchId)).getText().toString();
                Intent intent = new Intent(OrderListActivity.this, OrderDescriptionActivity.class);
                intent.putExtra("batch", batch);
                startActivity(intent);
            }
        });
    }

}
