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
import com.example.anik.shop.helpers.SubCategory;
import com.example.anik.shop.httpService.ImageDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SubCategoryGridViewAdapter extends BaseAdapter implements Filterable {

    private List<SubCategory> originalSubCategoriesList = new ArrayList<SubCategory>();
    private List<SubCategory> filteredSubCategoriesList = new ArrayList<SubCategory>();
    private Context context;
    private Activity activity;

    public SubCategoryGridViewAdapter(Context context, List<SubCategory> originalSubCategoriesList) {
        this.context = context;
        this.originalSubCategoriesList.addAll(originalSubCategoriesList);
        this.filteredSubCategoriesList.addAll(originalSubCategoriesList);
    }

    public SubCategoryGridViewAdapter(Activity activity, List<SubCategory> originalSubCategoriesList) {
        this(activity.getApplicationContext(), originalSubCategoriesList);
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return filteredSubCategoriesList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredSubCategoriesList.get(position);
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
            convertView = inflater.inflate(R.layout.layout_for_sub_category_gridview, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        SubCategory subCategory = filteredSubCategoriesList.get(position);
        String subCategoryName = subCategory.getName();
        String subCategoryId = String.valueOf(subCategory.getId());
        String subCategoryImageUrl = subCategory.getImageUrl();
        final String image_name = subCategoryImageUrl.substring(subCategoryImageUrl.lastIndexOf("/") + 1);

        File file = new File(AppStorage.getFilesDirectory(), image_name);
        if (file.exists()) {
            AppHelper.setImageToImageView(viewHolder.subCategoryImage, file);
        } else {
            ImageDownloader.pushJob(subCategoryImageUrl, viewHolder.subCategoryImage).execute();
        }

        viewHolder.subCategoryId.setText(subCategoryId);
        viewHolder.subCategoryName.setText(subCategoryName);
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
                    results.values = SubCategoryGridViewAdapter.this.originalSubCategoriesList;
                    return results;
                }
                final List<SubCategory> subCategories = SubCategoryGridViewAdapter.this.originalSubCategoriesList;
                int count = subCategories.size();
                final ArrayList<SubCategory> listFiltered = new ArrayList<>(count);
                for (int i = 0; i < count; ++i) {
                    SubCategory subCategory = subCategories.get(i);
                    String subCategoryName = subCategory.getName();
                    if (subCategoryName.toLowerCase().contains(filterString)) {
                        listFiltered.add(subCategory);
                    }
                }
                results.values = listFiltered;
                results.count = listFiltered.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                SubCategoryGridViewAdapter.this.filteredSubCategoriesList = (List<SubCategory>) results.values;
                SubCategoryGridViewAdapter.this.notifyDataSetChanged();
            }
        };
    }

    private class ViewHolder {
        ImageView subCategoryImage;
        TextView subCategoryName;
        TextView subCategoryId;

        public ViewHolder(View view) {
            subCategoryImage = (ImageView) view.findViewById(R.id.subCategoryImage);
            subCategoryName = (TextView) view.findViewById(R.id.subCategoryName);
            subCategoryId = (TextView) view.findViewById(R.id.subCategoryId);
        }
    }

}
