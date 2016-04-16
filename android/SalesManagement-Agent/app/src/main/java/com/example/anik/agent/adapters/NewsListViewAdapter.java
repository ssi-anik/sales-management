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
import com.example.anik.agent.helpers.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Anik on 14-Aug-15, 014.
 */
public class NewsListViewAdapter extends BaseAdapter implements Filterable {

    private Context context;
    private List<News> originalNewsList = new ArrayList<>();
    private List<News> filteredNewsList = new ArrayList<>();

    public NewsListViewAdapter(Context context, List<News> newsList) {
        this.context = context;
        this.originalNewsList = newsList;
        this.filteredNewsList = newsList;
    }

    @Override
    public int getCount() {
        return this.filteredNewsList.size();
    }

    @Override
    public Object getItem(int position) {
        return this.filteredNewsList.get(position);
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
            convertView = inflater.inflate(R.layout.layout_for_single_news, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        News news = this.filteredNewsList.get(position);
        String title = news.getTitle();
        String newsId = news.getId();
        viewHolder.title.setText(title);
        viewHolder.newsId.setText(newsId);
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
                    results.values = NewsListViewAdapter.this.originalNewsList;
                    return results;
                }
                final List<News> newsList = NewsListViewAdapter.this.originalNewsList;
                int count = newsList.size();
                final ArrayList<News> listFiltered = new ArrayList<>(count);
                for (int i = 0; i < count; ++i) {
                    News news = newsList.get(i);
                    String title = news.getTitle();
                    if (title.toLowerCase().contains(filterString)) {
                        listFiltered.add(news);
                    }
                }
                results.values = listFiltered;
                results.count = listFiltered.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                NewsListViewAdapter.this.filteredNewsList = (List<News>) results.values;
                NewsListViewAdapter.this.notifyDataSetChanged();
            }
        };
    }

    private class ViewHolder {
        TextView title;
        TextView newsId;

        ViewHolder(View view) {
            this.title = (TextView) view.findViewById(R.id.newsTitle);
            this.newsId = (TextView) view.findViewById(R.id.newsIdForListView);
        }
    }
}
