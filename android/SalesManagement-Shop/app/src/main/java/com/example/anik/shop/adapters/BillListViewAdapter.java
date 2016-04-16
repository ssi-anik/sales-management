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
 * Created by Anik on 16-Aug-15, 016.
 */
public class BillListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Map<String, String>> billList = new ArrayList<>();

    public BillListViewAdapter(Context context, List<Map<String, String>> billList) {
        this.context = context;
        this.billList = billList;
    }

    @Override
    public int getCount() {
        return billList.size();
    }

    @Override
    public Object getItem(int position) {
        return billList.get(position);
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
            convertView = inflater.inflate(R.layout.layout_for_bill_information, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Map<String, String> row = billList.get(position);
        viewHolder.totalItemsForBillInformation.setText(row.get("items"));
        viewHolder.totalPricesForBillInformation.setText(row.get("total"));
        viewHolder.billKeyForBillInformation.setText(row.get("billKey"));
        String status = "";
        if (row.get("submission_status").equals("0")) {
            status = "Not submitted";
        } else {
            status = "Submitted";
        }
        viewHolder.companyNameForBillInformation.setText(String.format("%s", status));
        return convertView;
    }

    private class ViewHolder {
        TextView totalItemsForBillInformation;
        TextView totalPricesForBillInformation;
        TextView billKeyForBillInformation;
        TextView companyNameForBillInformation;

        public ViewHolder(View view) {
            totalItemsForBillInformation = (TextView) view.findViewById(R.id.totalItemsForBillInformation);
            totalPricesForBillInformation = (TextView) view.findViewById(R.id.totalPricesForBillInformation);
            billKeyForBillInformation = (TextView) view.findViewById(R.id.billKeyForBillInformation);
            companyNameForBillInformation = (TextView) view.findViewById(R.id.companyNameForForBillInformation);
        }
    }
}
