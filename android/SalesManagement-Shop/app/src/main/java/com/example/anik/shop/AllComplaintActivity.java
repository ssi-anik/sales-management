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

import com.example.anik.shop.adapters.ComplaintsListViewAdapter;
import com.example.anik.shop.database.Complaint;

import java.util.ArrayList;
import java.util.List;


public class AllComplaintActivity extends ActionBarActivity {

    TextWatcher searchTextChangeListener = new TextWatcher() {
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

    ListView.OnItemClickListener complaintListViewClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            String complaintId = ((TextView) view.findViewById(R.id.complaintIdForListView)).getText().toString();
            //startActivity(new Intent(AllComplaintActivity.this, ShowComplaint.class).putExtra("id", complaintId));
            String complaintTrackingNumber = ((TextView) view.findViewById(R.id.complaintTrackingNumberForListView)).getText().toString();
            startActivity(new Intent(AllComplaintActivity.this, ShowComplaint.class).putExtra("tracking_number", complaintTrackingNumber));
        }
    };
    List<Complaint> complaintList = new ArrayList<>();
    ListView complaintsListView;
    EditText searchBoxForComplaint;
    ComplaintsListViewAdapter adapter;
    private Complaint complaint;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getAllComplaints();
        showCorrespondingView();
    }

    private void getAllComplaints() {
        complaint = new Complaint(AllComplaintActivity.this);
        List<Complaint> list = complaint.all();
        if (null != list) {
            complaintList.addAll(list);
        }
    }

    private void showCorrespondingView() {
        if (complaintList.isEmpty()) {
            setContentView(R.layout.layout_empty_data);
            ((TextView) findViewById(R.id.textViewMessageEmptyData)).setText("No complaint is enlisted.");
            return;
        }

        setContentView(R.layout.activity_all_complaint);
        searchBoxForComplaint = (EditText) findViewById(R.id.searchBoxForComplaint);
        searchBoxForComplaint.addTextChangedListener(searchTextChangeListener);
        complaintsListView = (ListView) findViewById(R.id.listViewForAllComplaints);
        adapter = new ComplaintsListViewAdapter(AllComplaintActivity.this, complaintList);
        complaintsListView.setAdapter(adapter);
        complaintsListView.setOnItemClickListener(complaintListViewClickListener);
    }
}
