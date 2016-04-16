package com.example.anik.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.anik.shop.adapters.ActivityLogListViewAdapter;
import com.example.anik.shop.database.ActivityLog;

import java.util.ArrayList;
import java.util.List;


public class ActivityLogActivity extends ActionBarActivity {

    private ListView listViewForActivityLog;
    private ActivityLog activityLog;
    private List<ActivityLog> activityLogList = new ArrayList<>();
    private ActivityLogListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityLog = new ActivityLog(ActivityLogActivity.this);
        activityLogList = activityLog.all();
        if (null == activityLogList) {
            setContentView(R.layout.layout_empty_data);
            ((TextView) findViewById(R.id.textViewMessageEmptyData)).setText("No activity found");
            return;
        }
        setContentView(R.layout.activity_activity_log);
        listViewForActivityLog = (ListView) findViewById(R.id.listViewForActivityLog);
        adapter = new ActivityLogListViewAdapter(ActivityLogActivity.this, activityLogList);
        listViewForActivityLog.setAdapter(adapter);
        listViewForActivityLog.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String reference_id = ((TextView) view.findViewById(R.id.textViewForActivityLogReferenceId)).getText().toString();
                String reference_type = ((TextView) view.findViewById(R.id.textViewForActivityLogReferenceType)).getText().toString();
                Intent intent = null;
                if (reference_type.equals("bill")) {
                    intent = new Intent(ActivityLogActivity.this, BillDescriptionActivity.class);
                    intent.putExtra("billKey", reference_id);
                } else if (reference_type.equals("order")) {
                    intent = new Intent(ActivityLogActivity.this, OrderDescriptionActivity.class);
                    intent.putExtra("batch", reference_id);
                } else if (reference_type.equals("complaint")) {
                    intent = new Intent(ActivityLogActivity.this, ShowComplaint.class);
                    intent.putExtra("tracking_number", reference_id);
                }
                if (null != intent) {
                    startActivity(intent);
                }
            }
        });

    }
}
