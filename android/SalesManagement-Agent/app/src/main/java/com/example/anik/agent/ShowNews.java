package com.example.anik.agent;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.anik.agent.helpers.AppHelper;
import com.example.anik.agent.helpers.AppStorage;
import com.example.anik.agent.helpers.News;
import com.example.anik.agent.httpService.ImageDownloader;

import java.io.File;


public class ShowNews extends ActionBarActivity {

    TextView newsTitle, newsDescription, newsLink, newsYoutubeLink;
    ImageView imageViewForNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_news);
        Intent intent = getIntent();
        News news = (News) intent.getSerializableExtra("news");
        setTitle(news.getTitle());

        imageViewForNews = (ImageView) findViewById(R.id.imageViewForNews);
        newsTitle = (TextView) findViewById(R.id.newsTitle);
        newsDescription = (TextView) findViewById(R.id.newsDescription);
        newsLink = (TextView) findViewById(R.id.newsLink);
        newsYoutubeLink = (TextView) findViewById(R.id.newsYoutubeLink);

        newsTitle.setText(String.format("Title: %s", news.getTitle()));
        newsDescription.setText(String.format("Description: %s", news.getDescription()));

        if (!news.getLink().isEmpty()) {
            newsLink.setText(Html.fromHtml(String.format("Link: <a href=\"%s\">%s</a>", news.getLink(), "Click here to see news")));
            newsLink.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            newsLink.setVisibility(View.GONE);
        }

        String newsImageUrl = news.getImageLink();
        if (!newsImageUrl.isEmpty()) {
            final String image_name = newsImageUrl.substring(newsImageUrl.lastIndexOf("/") + 1);

            File file = new File(AppStorage.getFilesDirectory(), image_name);
            if (file.exists()) {
                AppHelper.setImageToImageView(imageViewForNews, file);
            } else {
                ImageDownloader.pushJob(newsImageUrl, imageViewForNews).execute();
            }
        } else {
            imageViewForNews.setVisibility(View.GONE);
        }

        if (!news.getYoutube().isEmpty()) {
            newsYoutubeLink.setText(Html.fromHtml(String.format("Youtube Link: <a href=\"%s\">%s</a>", news.getYoutube(), "Click here to see video")));
            newsYoutubeLink.setMovementMethod(LinkMovementMethod.getInstance());
        } else {
            newsYoutubeLink.setVisibility(View.GONE);
        }
    }

}
