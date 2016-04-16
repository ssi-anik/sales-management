package com.example.anik.shop;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.anik.shop.adapters.NewsListViewAdapter;
import com.example.anik.shop.helpers.AppConstant;
import com.example.anik.shop.helpers.AppStorage;
import com.example.anik.shop.helpers.News;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class NewsActivity extends ActionBarActivity {

    TextWatcher newsSearchListener = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            adapter.getFilter().filter(s);
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };
    private ListView listView;
    private EditText newsSearchBox;
    private String news = "";
    private List<News> newsList = new ArrayList<>();
    ListView.OnItemClickListener newsItemClickListener = new AdapterView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            TextView textView = (TextView) view.findViewById(R.id.newsIdForListView);
            String newsId = textView.getText().toString();
            for (News news : newsList) {
                if (news.getId().equals(newsId)) {
                    Intent intent = new Intent(NewsActivity.this, ShowNews.class);
                    intent.putExtra("news", news);
                    startActivity(intent);
                    return;
                }
            }
        }
    };
    private NewsListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int statusCode = intent.getIntExtra("statusCode", 0);
        if (statusCode >= 400) {
            Toast.makeText(NewsActivity.this, AppConstant.MESSAGE_DATA_PARSE_ERROR, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        if (intent.hasExtra("response")) {
            news = intent.getStringExtra("response");
            AppStorage.setNews(news);
        } else {
            news = AppStorage.getNews();
        }
        prepare_news();
        show_news();
    }

    private void show_news() {
        if (this.newsList.size() == 0) {
            setContentView(R.layout.layout_empty_data);
            TextView textView = (TextView) findViewById(R.id.textViewMessageEmptyData);
            textView.setText("No news is available");
            return;
        }
        setContentView(R.layout.activity_news);
        newsSearchBox = (EditText) findViewById(R.id.newsSearchBox);
        newsSearchBox.addTextChangedListener(newsSearchListener);
        listView = (ListView) findViewById(R.id.newsListView);
        adapter = new NewsListViewAdapter(NewsActivity.this, this.newsList);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(newsItemClickListener);
    }

    private void prepare_news() {
        try {
            JSONObject object = new JSONObject(news);
            JSONArray data = object.getJSONArray("data");
            for (int i = 0; i < data.length(); ++i) {
                JSONObject news = data.getJSONObject(i);
                String id = news.getString("id");
                String title = news.getString("title");
                String description = news.getString("description");
                String link = news.has("link") ? news.getString("link") : "";
                String youtube = news.has("youtube") ? news.getString("youtube") : "";
                String image_link = news.has("image_link") ? news.getString("image_link") : "";
                newsList.add(new News(id, title, description, link, youtube, image_link));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
