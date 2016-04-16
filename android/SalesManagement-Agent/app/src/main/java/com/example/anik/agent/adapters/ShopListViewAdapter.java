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
import com.example.anik.agent.helpers.Shop;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anik on 15-Aug-15, 015.
 */
public class ShopListViewAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<Shop> originalShopList = new ArrayList<>();
    private List<Shop> filteredShopList = new ArrayList<>();

    public ShopListViewAdapter(Context context, List<Shop> shops) {
        this.context = context;
        this.originalShopList = shops;
        this.filteredShopList = shops;
    }

    @Override
    public int getCount() {
        return filteredShopList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredShopList.get(position);
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
            convertView = inflater.inflate(R.layout.layout_for_shop_listview, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Shop shop = filteredShopList.get(position);
        String companyName = shop.getCompanyName();
        String ownerName = shop.getOwnerName();
        String mobileNumber = shop.getMobileNumber();

        viewHolder.companyName.setText(companyName);
        viewHolder.ownerName.setText(ownerName);
        viewHolder.mobileNumber.setText(mobileNumber);
        return convertView;
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                String searchingFor = constraint.toString().trim().toLowerCase();
                FilterResults results = new FilterResults();
                if (searchingFor.isEmpty()) {
                    results.values = ShopListViewAdapter.this.originalShopList;
                    results.count = ShopListViewAdapter.this.originalShopList.size();
                    return results;
                }
                final List<Shop> filtered = new ArrayList<>();
                for (Shop shop : originalShopList) {
                    if (shop.getCompanyName().toLowerCase().contains(searchingFor) || shop.getOwnerName().toLowerCase().contains(searchingFor) || shop.getMobileNumber().toLowerCase().contains(searchingFor)) {
                        filtered.add(shop);
                    }
                }
                results.values = filtered;
                results.count = filtered.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                ShopListViewAdapter.this.filteredShopList = (List<Shop>) results.values;
                ShopListViewAdapter.this.notifyDataSetChanged();
            }
        };
    }

    private class ViewHolder {
        TextView companyName;
        TextView ownerName;
        TextView mobileNumber;

        public ViewHolder(View view) {
            companyName = (TextView) view.findViewById(R.id.companyNameForShop);
            ownerName = (TextView) view.findViewById(R.id.ownerNameForShop);
            mobileNumber = (TextView) view.findViewById(R.id.mobileNumberForShop);
        }
    }
}
