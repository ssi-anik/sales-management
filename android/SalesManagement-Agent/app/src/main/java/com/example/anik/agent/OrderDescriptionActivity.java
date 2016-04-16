package com.example.anik.agent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anik.agent.adapters.OrderDescriptionListViewAdapter;
import com.example.anik.agent.database.Order;

import java.util.ArrayList;
import java.util.List;


public class OrderDescriptionActivity extends ActionBarActivity {

    Order order;
    List<Order> orderList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_description);
        ListView listView = (ListView) findViewById(R.id.listViewForOrderDescription);

        Intent intent = getIntent();
        String batch = intent.getStringExtra("batch");

        order = new Order(OrderDescriptionActivity.this);
        orderList = order.where("batch", batch);
        TextView orderSubmissionStatusForOrderDescription = (TextView) findViewById(R.id.orderSubmissionStatusForOrderDescription);
        String submissionStatus = orderList.get(0).getIsSubmitted().equals("0") ? "Order is not submitted" : "Order is submitted online";
        orderSubmissionStatusForOrderDescription.setText(submissionStatus);
        OrderDescriptionListViewAdapter adapter = new OrderDescriptionListViewAdapter(OrderDescriptionActivity.this, orderList);
        listView.setAdapter(adapter);

    }

}
