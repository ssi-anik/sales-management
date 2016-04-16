package com.example.anik.shop.helpers;

import java.io.Serializable;

/**
 * Created by Anik on 14-Aug-15, 014.
 */
public class News implements Serializable {
    private String id;
    private String title;
    private String description;
    private String link;
    private String youtube;
    private String imageLink;

    public News(String id, String title, String description, String link, String youtube, String imageLink) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.link = link;
        this.youtube = youtube;
        this.imageLink = imageLink;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getLink() {
        return link;
    }

    public String getYoutube() {
        return youtube;
    }

    public String getImageLink() {
        return imageLink;
    }
}
