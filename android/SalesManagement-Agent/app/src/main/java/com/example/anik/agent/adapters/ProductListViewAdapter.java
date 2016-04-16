package com.example.anik.agent.adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.anik.agent.R;
import com.example.anik.agent.SelectProductActivity;
import com.example.anik.agent.helpers.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anik on 15-Aug-15, 015.
 */
public class ProductListViewAdapter extends BaseAdapter {

    private Context context;
    private List<Product> productList = new ArrayList<>();

    public ProductListViewAdapter(Context context, List<Product> productList) {
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
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_for_product_listview, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Product product = productList.get(position);

        viewHolder.productName.setText(product.getName());
        viewHolder.productPrice.setText("Tk. : " + product.getProductPrice());
        viewHolder.productListId.setText(String.valueOf(position));
        if( position == SelectProductActivity.SELECTED_ROW){
            convertView.setBackgroundColor(Color.rgb(167, 233, 255));
        } else{
            convertView.setBackgroundColor(Color.TRANSPARENT);
        }
        return convertView;
    }

    private class ViewHolder {
        TextView productName;
        TextView productPrice;
        TextView productListId;

        private ViewHolder(View view) {
            productName = (TextView) view.findViewById(R.id.productNameInProductListView);
            productPrice = (TextView) view.findViewById(R.id.productPriceInProductListView);
            productListId = (TextView) view.findViewById(R.id.productListIdInProductListView);
        }
    }
}
