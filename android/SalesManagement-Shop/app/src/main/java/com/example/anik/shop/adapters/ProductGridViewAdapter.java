package com.example.anik.shop.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anik.shop.R;
import com.example.anik.shop.helpers.AppHelper;
import com.example.anik.shop.helpers.AppStorage;
import com.example.anik.shop.helpers.Product;
import com.example.anik.shop.httpService.ImageDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class ProductGridViewAdapter extends BaseAdapter implements Filterable {

    private List<Product> originalProductsList = new ArrayList<Product>();
    private List<Product> filteredProductsList = new ArrayList<Product>();
    private Context context;
    private Activity activity;

    public ProductGridViewAdapter(Context context, List<Product> originalProductsList) {
        this.context = context;
        this.originalProductsList.addAll(originalProductsList);
        this.filteredProductsList.addAll(originalProductsList);
    }

    public ProductGridViewAdapter(Activity activity, List<Product> originalProductsList) {
        this(activity.getApplicationContext(), originalProductsList);
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return filteredProductsList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredProductsList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ViewHolder viewHolder;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.layout_for_product_gridview, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Product product = filteredProductsList.get(position);
        String productName = product.getName();
        String productId = String.valueOf(product.getId());
        String productImageUrl = product.getFirstImageUrl();
        final String image_name = productImageUrl.substring(productImageUrl.lastIndexOf("/") + 1);

        File file = new File(AppStorage.getFilesDirectory(), image_name);
        if (file.exists()) {
            AppHelper.setImageToImageView(viewHolder.productImage, file);
        } else {
            ImageDownloader.pushJob(productImageUrl, viewHolder.productImage).execute();
        }

        viewHolder.productId.setText(productId);
        viewHolder.productName.setText(productName);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String filterString = constraint.toString().toLowerCase();
                FilterResults results = new FilterResults();
                if (filterString.trim().length() == 0) {
                    results.values = ProductGridViewAdapter.this.originalProductsList;
                    results.count = ProductGridViewAdapter.this.originalProductsList.size();
                    return results;
                }
                final List<Product> products = ProductGridViewAdapter.this.originalProductsList;
                int count = products.size();
                final ArrayList<Product> listFiltered = new ArrayList<>(count);
                for (int i = 0; i < count; ++i) {
                    Product product = products.get(i);
                    String productName = product.getName();
                    if (productName.toLowerCase().contains(filterString)) {
                        listFiltered.add(product);
                    }
                }
                results.values = listFiltered;
                results.count = listFiltered.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                ProductGridViewAdapter.this.filteredProductsList = (List<Product>) results.values;
                ProductGridViewAdapter.this.notifyDataSetChanged();
            }
        };
    }

    private class ViewHolder {
        ImageView productImage;
        TextView productName;
        TextView productId;

        public ViewHolder(View view) {
            productImage = (ImageView) view.findViewById(R.id.productImage);
            productName = (TextView) view.findViewById(R.id.productName);
            productId = (TextView) view.findViewById(R.id.productId);
        }
    }

}
