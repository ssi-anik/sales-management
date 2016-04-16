package com.example.anik.agent.adapters;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.anik.agent.R;
import com.example.anik.agent.helpers.AppCart;
import com.example.anik.agent.helpers.AppStorage;
import com.example.anik.agent.helpers.Product;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anik on 15-Aug-15, 015.
 */
public class CheckoutListViewAdapter extends BaseAdapter {
    private List<Product> productList = new ArrayList<>();
    private Context context;

    public CheckoutListViewAdapter(Context context, List<Product> productsInCart) {
        this.context = context;
        this.productList.addAll(productsInCart);
    }

    public CheckoutListViewAdapter(Context context) {
        this.context = context;
        this.productList = AppCart.getCart();
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
            convertView = inflater.inflate(R.layout.layout_for_checkout, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Product product = productList.get(position);

        viewHolder.productNameForCheckout.setText(product.getName());
        viewHolder.productQuantityForCheckout.setText(String.format("Items: %d", product.getQuantity()));
        viewHolder.productUniqueId.setText(product.getProductUniqueId());

        if (AppStorage.getNextIntent().equals("quick order")) {
            viewHolder.totalPriceForCheckout.setText(String.format("Tk. %s", product.getTotalPriceWithoutTax()));
            viewHolder.totalPriceForCheckout.setVisibility(View.GONE);

            viewHolder.totalTaxForCheckout.setVisibility(View.GONE);
            viewHolder.productQuantityForCheckout.setGravity(Gravity.RIGHT);
        } else {
            viewHolder.totalPriceForCheckout.setText(String.format("Tk. %s", product.getTotalPriceWithTax()));
            viewHolder.totalTaxForCheckout.setText(String.format("Taxes: %s%%", product.getTotalTax()));
        }

        return convertView;
    }

    private class ViewHolder {
        TextView productNameForCheckout;
        TextView productQuantityForCheckout;
        TextView totalPriceForCheckout;
        TextView totalTaxForCheckout;
        TextView productUniqueId;

        public ViewHolder(View view) {
            productNameForCheckout = (TextView) view.findViewById(R.id.productNameForCheckout);
            productQuantityForCheckout = (TextView) view.findViewById(R.id.productQuantityForCheckout);
            totalPriceForCheckout = (TextView) view.findViewById(R.id.totalPriceForCheckout);
            totalTaxForCheckout = (TextView) view.findViewById(R.id.productTotalTaxForCheckout);
            productUniqueId = (TextView) view.findViewById(R.id.productUniqueIdForCheckout);
        }
    }
}
