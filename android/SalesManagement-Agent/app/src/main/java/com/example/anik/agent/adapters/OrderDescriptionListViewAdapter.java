package com.example.anik.agent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.anik.agent.R;
import com.example.anik.agent.database.Order;
import com.example.anik.agent.helpers.AppHelper;

import java.util.List;

/**
 * Created by Anik on 15-Aug-15, 015.
 */
public class OrderDescriptionListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Order> orderList;

    public OrderDescriptionListViewAdapter(Context context, List<Order> orderList) {
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
            convertView = inflater.inflate(R.layout.layout_for_order_description, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Order order = orderList.get(position);
        viewHolder.productNameForOrderDescription.setText(order.getProductName());
        viewHolder.productPriceForOrderDescription.setText("Price: " + order.getPrice());
        viewHolder.productTotalTaxForOrderDescription.setText("Tax: " + AppHelper.taxDeCalculator(order.getPrice(), order.getQuantity(), order.getTotalPrice()) + "%");
        viewHolder.productQuantityForOrderDescription.setText("Quantity: " + order.getQuantity());
        viewHolder.totalPriceForOrderDescription.setText("Total price: " + order.getTotalPrice());
        return convertView;
    }

    private class ViewHolder {
        TextView productNameForOrderDescription;
        TextView productPriceForOrderDescription;
        TextView productTotalTaxForOrderDescription;
        TextView productQuantityForOrderDescription;
        TextView totalPriceForOrderDescription;

        public ViewHolder(View view) {
            productNameForOrderDescription = (TextView) view.findViewById(R.id.productNameForOrderDescription);
            productPriceForOrderDescription = (TextView) view.findViewById(R.id.productPriceForOrderDescription);
            productTotalTaxForOrderDescription = (TextView) view.findViewById(R.id.productTotalTaxForOrderDescription);
            productQuantityForOrderDescription = (TextView) view.findViewById(R.id.productQuantityForOrderDescription);
            totalPriceForOrderDescription = (TextView) view.findViewById(R.id.totalPriceForOrderDescription);
        }
    }
}
