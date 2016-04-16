package com.example.anik.shop.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.example.anik.shop.R;
import com.example.anik.shop.helpers.Offer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anik on 14-Aug-15, 014.
 */
public class OfferListViewAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<Offer> originalOfferList = new ArrayList<>();
    private List<Offer> filteredOfferList = new ArrayList<>();

    public OfferListViewAdapter(Context context, List<Offer> offerList) {
        this.context = context;
        this.originalOfferList = offerList;
        this.filteredOfferList = offerList;
    }

    @Override
    public int getCount() {
        return this.filteredOfferList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.filteredOfferList.get(position);
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
            convertView = inflater.inflate(R.layout.layout_for_single_offer, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Offer offer = this.filteredOfferList.get(position);
        String title = offer.getTitle();
        String offerId = offer.getId();
        viewHolder.title.setText(title);
        viewHolder.offerId.setText(offerId);
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
                    results.values = OfferListViewAdapter.this.originalOfferList;
                    return results;
                }
                final List<Offer> offerList = OfferListViewAdapter.this.originalOfferList;
                int count = offerList.size();
                final ArrayList<Offer> listFiltered = new ArrayList<>(count);
                for (int i = 0; i < count; ++i) {
                    Offer offer = offerList.get(i);
                    String title = offer.getTitle();
                    if (title.toLowerCase().contains(filterString)) {
                        listFiltered.add(offer);
                    }
                }
                results.values = listFiltered;
                results.count = listFiltered.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                OfferListViewAdapter.this.filteredOfferList = (List<Offer>) results.values;
                OfferListViewAdapter.this.notifyDataSetChanged();
            }
        };
    }

    private class ViewHolder {
        TextView title;
        TextView offerId;

        ViewHolder(View view) {
            this.title = (TextView) view.findViewById(R.id.offerTitle);
            this.offerId = (TextView) view.findViewById(R.id.offerIdForListView);
        }
    }
}
