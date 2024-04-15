package com.example.final_project;

import java.util.ArrayList;

public class NewsModal {
    private int totalResults;
    private String status;
    private ArrayList<Item> items;

    public int getTotalResults() {
        return totalResults;
    }

    public void setTotalResults(int totalResults) {
        this.totalResults = totalResults;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public NewsModal(int totalResults, String status, ArrayList<Item> items) {
        this.totalResults = totalResults;
        this.status = status;
        this.items = items;
    }
}
