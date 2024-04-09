package com.example.final_project;

public class Item {
    private String title;
    private String description;
    private String link;
    private String media;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
    }

    public Item(String title, String description, String link, String media) {
        this.title = title;
        this.description = description;
        this.link = link;
        this.media = media;
    }
}
