package com.example.anik.agent.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.anik.agent.R;
import com.example.anik.agent.helpers.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anik on 27-Aug-15, 027.
 */
public class QuickOrderSelectedProductListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Product> productList = new ArrayList<>();

    public QuickOrderSelectedProductListViewAdapter(Context context, List<Product> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
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
            convertView = inflater.inflate(R.layout.layout_for_quick_order_selected_product, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Product product = productList.get(position);
        viewHolder.productNameForQuickOrderSelectedProduct.setText(product.getName());
        viewHolder.productQuantityForQuickOrderSelectedProduct.setText(String.valueOf(product.getQuantity()));
        return convertView;
    }

    private class ViewHolder {
        TextView productNameForQuickOrderSelectedProduct;
        TextView productQuantityForQuickOrderSelectedProduct;

        private ViewHolder(View view) {
            productNameForQuickOrderSelectedProduct = (TextView) view.findViewById(R.id.productNameForQuickOrderSelectedProduct);
            productQuantityForQuickOrderSelectedProduct = (TextView) view.findViewById(R.id.productQuantityForQuickOrderSelectedProduct);
        }
    }
}
