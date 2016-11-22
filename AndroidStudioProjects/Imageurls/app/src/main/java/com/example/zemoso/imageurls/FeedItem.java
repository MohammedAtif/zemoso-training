package com.example.zemoso.imageurls;

/**
 * Created by zemoso on 16/11/16.
 */

public class FeedItem {
    private String url;
    private String thumbnail;

    public FeedItem(String url) {
        this.url = url;
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String title) {
        this.url = title;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }
}