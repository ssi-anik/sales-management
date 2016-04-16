package com.example.anik.agent.adapters;

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

import com.example.anik.agent.R;
import com.example.anik.agent.helpers.AppHelper;
import com.example.anik.agent.helpers.AppStorage;
import com.example.anik.agent.helpers.Category;
import com.example.anik.agent.httpService.ImageDownloader;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class CategoryGridViewAdapter extends BaseAdapter implements Filterable {

    private List<Category> originalCategoriesList = new ArrayList<Category>();
    private List<Category> filteredCategoriesList = new ArrayList<Category>();
    private Context context;
    private Activity activity;

    public CategoryGridViewAdapter(Context context, List<Category> originalCategoriesList) {
        this.context = context;
        this.originalCategoriesList.addAll(originalCategoriesList);
        this.filteredCategoriesList.addAll(originalCategoriesList);
    }

    public CategoryGridViewAdapter(Activity activity, List<Category> originalCategoriesList) {
        this(activity.getApplicationContext(), originalCategoriesList);
        this.activity = activity;
    }

    @Override
    public int getCount() {
        return filteredCategoriesList.size();
    }

    @Override
    public Object getItem(int position) {
        return filteredCategoriesList.get(position);
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
            convertView = inflater.inflate(R.layout.layout_for_category_gridview, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Category category = filteredCategoriesList.get(position);
        String categoryName = category.getCategoryName();
        String categoryId = String.valueOf(category.getCategoryId());
        String categoryImageUrl = category.getCategoryImageUrl();
        final String image_name = categoryImageUrl.substring(categoryImageUrl.lastIndexOf("/") + 1);

        File file = new File(AppStorage.getFilesDirectory(), image_name);
        if (file.exists()) {
            AppHelper.setImageToImageView(viewHolder.categoryImage, file);
        } else {
            ImageDownloader.pushJob(categoryImageUrl, viewHolder.categoryImage).execute();
        }

        viewHolder.categoryId.setText(categoryId);
        viewHolder.categoryName.setText(categoryName);
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
                    results.values = CategoryGridViewAdapter.this.originalCategoriesList;
                    return results;
                }
                final List<Category> categories = CategoryGridViewAdapter.this.originalCategoriesList;
                int count = categories.size();
                final ArrayList<Category> listFiltered = new ArrayList<>(count);
                for (int i = 0; i < count; ++i) {
                    Category category = categories.get(i);
                    String category_name = category.getCategoryName();
                    if (category_name.toLowerCase().contains(filterString)) {
                        listFiltered.add(category);
                    }
                }
                results.values = listFiltered;
                results.count = listFiltered.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                CategoryGridViewAdapter.this.filteredCategoriesList = (List<Category>) results.values;
                CategoryGridViewAdapter.this.notifyDataSetChanged();
            }
        };
    }

    private class ViewHolder {
        ImageView categoryImage;
        TextView categoryName;
        TextView categoryId;

        public ViewHolder(View view) {
            categoryImage = (ImageView) view.findViewById(R.id.categoryImage);
            categoryName = (TextView) view.findViewById(R.id.categoryName);
            categoryId = (TextView) view.findViewById(R.id.categoryId);
        }
    }

}
