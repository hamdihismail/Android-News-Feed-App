package com.example.final_project;

import android.database.Cursor;

public class FavouriteRVModal {
    private String title;
    private String favouriteMedia;
    private String date;

    private String description;
    private String link;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(String link) {
        int id = 0;
        try {
            String numerics = link.replaceAll("[^0-9]", "");
            id = Integer.valueOf(numerics);
        }catch (NumberFormatException e){
            e.printStackTrace();
        }catch (NullPointerException e){
            e.printStackTrace();
        }
//        if((link !=null)&&(link !="")){
//            String numerics = link.replaceAll("[^0-9]", "");
//            id = Integer.valueOf(numerics);
//        }
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
        setId(link);
    }


    public FavouriteRVModal(Cursor cursor) {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFavouriteMedia() {
        return favouriteMedia;
    }

    public void setFavouriteMedia(String favouriteMedia) {
        this.favouriteMedia = favouriteMedia;
    }

    public FavouriteRVModal(String title, String favouriteMedia, String date, String description, String link) {
        this.title = title;
        this.favouriteMedia = favouriteMedia;
        this.date = date;
        this.description = description;
        setLink(link);
    }
}
