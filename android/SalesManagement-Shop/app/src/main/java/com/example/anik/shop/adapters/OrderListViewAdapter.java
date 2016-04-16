package com.example.anik.shop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.anik.shop.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Anik on 15-Aug-15, 015.
 */
public class OrderListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String, String>> orderList = new ArrayList<>();

    public OrderListViewAdapter(Context context, List<Map<String, String>> orderList) {
        this.context = context;
        this.orderList = orderList;
    }

    @Override
    public int getCount() {
        return orderList.size();
    }

    @Override
    public Object getItem(int position) {
        return orderList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (null == convertView) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_for_order_information, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Map<String, String> row = orderList.get(position);
        viewHolder.totalItems.setText(row.get("items"));
        viewHolder.totalPrices.setText(row.get("total"));
        viewHolder.batchId.setText(row.get("batch"));
        String submission_status = row.get("submission_status");
        String status = "";
        if (submission_status.equals("0")) {
            status = "Not submitted";
        } else {
            status = "Submitted";
        }
        viewHolder.companyNameForOrderInformation.setText(String.format("%s", status));
        return convertView;
    }

    private class ViewHolder {
        TextView totalItems;
        TextView totalPrices;
        TextView batchId;
        TextView companyNameForOrderInformation;

        public ViewHolder(View view) {
            totalItems = (TextView) view.findViewById(R.id.totalItems);
            totalPrices = (TextView) view.findViewById(R.id.totalPrices);
            batchId = (TextView) view.findViewById(R.id.batchId);
            companyNameForOrderInformation = (TextView) view.findViewById(R.id.companyNameForOrderInformation);
        }
    }
}
