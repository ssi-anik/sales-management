package com.example.anik.agent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.anik.agent.R;
import com.example.anik.agent.database.ActivityLog;
import com.example.anik.agent.helpers.AppHelper;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anik on 24-Aug-15, 024.
 */
public class ActivityLogListViewAdapter extends BaseAdapter {

    private Context context;
    private List<ActivityLog> activityLogList = new ArrayList<>();

    public ActivityLogListViewAdapter(Context context, List<ActivityLog> activityLogList) {
        this.context = context;
        this.activityLogList = activityLogList;
    }

    @Override
    public int getCount() {
        return activityLogList.size();
    }

    @Override
    public Object getItem(int position) {
        return activityLogList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_for_activity_log, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ActivityLog activityLog = activityLogList.get(position);
        String id = activityLog.getId();
        String referenceId = activityLog.getReferenceId();
        String referenceType = activityLog.getReferenceType();

        viewHolder.textViewForActivityLogId.setText(id);
        viewHolder.textViewForActivityLogReferenceId.setText(referenceId);
        viewHolder.textViewForActivityLogReferenceType.setText(referenceType);
        viewHolder.textViewForActivityLogPostedOnDate.setText(AppHelper.formatDate(activityLog.getPostedOn()));

        if (referenceType.equals("bill"))
            viewHolder.textViewForActivityLogTitle.setText("A bill was placed");
        else if (referenceType.equals("order"))
            viewHolder.textViewForActivityLogTitle.setText("An order was placed");
        else if (referenceType.equals("complaint"))
            viewHolder.textViewForActivityLogTitle.setText("A complaint was submitted");

        return convertView;
    }

    private class ViewHolder {
        private TextView textViewForActivityLogTitle;
        private TextView textViewForActivityLogId;
        private TextView textViewForActivityLogReferenceId;
        private TextView textViewForActivityLogReferenceType;
        private TextView textViewForActivityLogPostedOnDate;

        public ViewHolder(View view) {
            textViewForActivityLogTitle = (TextView) view.findViewById(R.id.textViewForActivityLogTitle);
            textViewForActivityLogId = (TextView) view.findViewById(R.id.textViewForActivityLogId);
            textViewForActivityLogReferenceId = (TextView) view.findViewById(R.id.textViewForActivityLogReferenceId);
            textViewForActivityLogReferenceType = (TextView) view.findViewById(R.id.textViewForActivityLogReferenceType);
            textViewForActivityLogPostedOnDate = (TextView) view.findViewById(R.id.textViewForActivityLogPostedOnDate);
        }
    }
}
