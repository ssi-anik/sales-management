package com.example.anik.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anik.shop.adapters.BillDescriptionListViewAdapter;
import com.example.anik.shop.database.Bill;

import java.util.ArrayList;
import java.util.List;


public class BillDescriptionActivity extends ActionBarActivity {

    Bill bill;
    List<Bill> billList = new ArrayList<>();
    TextView textViewForBillRemarks;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_description);
        ListView listView = (ListView) findViewById(R.id.listViewForBillDescription);
        textViewForBillRemarks = (TextView) findViewById(R.id.textViewForBillRemarks);
        Intent intent = getIntent();
        String billKey = intent.getStringExtra("billKey");

        bill = new Bill(BillDescriptionActivity.this);
        billList = bill.where("bill_key", billKey);

        TextView textViewForBillSubmissionStatus = (TextView) findViewById(R.id.textViewForBillSubmissionStatus);
        String submissionStatus = billList.get(0).getIsSubmitted().equals("0") ? "Bill is not submitted online" : "Bill is submiited";
        textViewForBillSubmissionStatus.setText(submissionStatus);

        BillDescriptionListViewAdapter adapter = new BillDescriptionListViewAdapter(BillDescriptionActivity.this, billList);
        listView.setAdapter(adapter);
        String billRemarks = billList.get(0).getBillRemarks();
        if (!billRemarks.isEmpty()) {
            textViewForBillRemarks.setText(String.format("Remarks: %s", billRemarks));
            textViewForBillRemarks.setVisibility(View.VISIBLE);
        }

    }

}
