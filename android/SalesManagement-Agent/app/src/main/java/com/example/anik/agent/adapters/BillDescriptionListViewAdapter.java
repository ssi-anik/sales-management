package com.example.anik.agent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.anik.agent.R;
import com.example.anik.agent.database.Bill;
import com.example.anik.agent.helpers.AppHelper;

import java.util.List;

/**
 * Created by Anik on 15-Aug-15, 015.
 */
public class BillDescriptionListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Bill> billList;
    private String remarks = "";

    public BillDescriptionListViewAdapter(Context context, List<Bill> billList) {
        this.context = context;
        this.billList = billList;
    }

    public String getRemarks() {
        return remarks;
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
            convertView = inflater.inflate(R.layout.layout_for_bill_description, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Bill bill = billList.get(position);
        viewHolder.productNameForBillDescription.setText(bill.getProductName());
        viewHolder.productPriceForBillDescription.setText("Price: " + bill.getPrice());
        viewHolder.productTaxForBillDescription.setText("Tax: " + AppHelper.taxDeCalculator(bill.getPrice(), bill.getQuantity(), bill.getTotalPrice()) + "%");
        viewHolder.productQuantityForBillDescription.setText("Quantity: " + bill.getQuantity());
        viewHolder.totalPriceForBillDescription.setText("Total price: " + bill.getTotalPrice());
        if (remarks.isEmpty()) {
            remarks = bill.getBillRemarks();
        }
        return convertView;
    }

    private class ViewHolder {
        TextView productNameForBillDescription;
        TextView productPriceForBillDescription;
        TextView productTaxForBillDescription;
        TextView productQuantityForBillDescription;
        TextView totalPriceForBillDescription;

        public ViewHolder(View view) {
            productNameForBillDescription = (TextView) view.findViewById(R.id.productNameForBillDescription);
            productPriceForBillDescription = (TextView) view.findViewById(R.id.productPriceForBillDescription);
            productTaxForBillDescription = (TextView) view.findViewById(R.id.productTaxForBillDescription);
            productQuantityForBillDescription = (TextView) view.findViewById(R.id.productQuantityForBillDescription);
            totalPriceForBillDescription = (TextView) view.findViewById(R.id.totalPriceForBillDescription);
        }
    }
}
