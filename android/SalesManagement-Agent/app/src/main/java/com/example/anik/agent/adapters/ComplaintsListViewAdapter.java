package com.example.anik.agent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.anik.agent.R;
import com.example.anik.agent.database.Complaint;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anik on 15-Aug-15, 015.
 */
public class ComplaintsListViewAdapter extends BaseAdapter implements Filterable {

    private List<Complaint> originalComplaintList = new ArrayList<>();
    private List<Complaint> filteredComplaintList = new ArrayList<>();
    private Context context;

    public ComplaintsListViewAdapter(Context context, List<Complaint> complaintList) {
        this.context = context;
        this.originalComplaintList = complaintList;
        this.filteredComplaintList = complaintList;
    }

    @Override
    public int getCount() {
        return this.filteredComplaintList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredComplaintList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_for_complaint_listview, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Complaint complaint = filteredComplaintList.get(position);
        String title = complaint.getTitle();
        String id = complaint.getId();
        String trackingNumber = complaint.getTrackingNumber();

        viewHolder.complaintTitle.setText(title.isEmpty() ? "Photo complaint was submitted" : title);
        viewHolder.complaintId.setText(id);
        viewHolder.complaintTrackingNumber.setText(trackingNumber);

        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchingFor = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();
                if (searchingFor.isEmpty()) {
                    results.values = ComplaintsListViewAdapter.this.originalComplaintList;
                    results.count = ComplaintsListViewAdapter.this.originalComplaintList.size();
                    return results;
                }
                List<Complaint> filteredResultSet = new ArrayList<>();
                for (Complaint complaint : ComplaintsListViewAdapter.this.originalComplaintList) {
                    if (complaint.getTitle().toLowerCase().contains(searchingFor)) {
                        filteredResultSet.add(complaint);
                    }
                }

                results.values = filteredResultSet;
                results.count = filteredResultSet.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                ComplaintsListViewAdapter.this.filteredComplaintList = (List<Complaint>) results.values;
                ComplaintsListViewAdapter.this.notifyDataSetChanged();
            }
        };
    }

    private class ViewHolder {
        TextView complaintTitle;
        TextView complaintId;
        TextView complaintTrackingNumber;

        public ViewHolder(View view) {
            complaintTitle = (TextView) view.findViewById(R.id.complaintTitleForListView);
            complaintId = (TextView) view.findViewById(R.id.complaintIdForListView);
            complaintTrackingNumber = (TextView) view.findViewById(R.id.complaintTrackingNumberForListView);
        }

    }


}
