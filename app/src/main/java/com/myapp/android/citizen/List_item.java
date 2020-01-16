package com.myapp.android.citizen;

public class List_item {
    private String url;
    private String image_url;
    private String name;

    public List_item(String name) {
        this.name = name;
    }

    public List_item(String url, String image_url, String name) {
        this.url = url;
        this.image_url = image_url;
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
