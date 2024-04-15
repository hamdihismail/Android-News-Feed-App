package com.example.final_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.ListAdapter;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsLVAdapter extends BaseAdapter {
    private ArrayList<Item> itemArrayList;
    private Context context;

    public NewsLVAdapter(ArrayList<Item> itemArrayList, Context context) {
        this.itemArrayList = itemArrayList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return itemArrayList.size();
    }

    @Override
    public Item getItem(int position) {
        return itemArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View old, ViewGroup parent) {
        View newsLVItem = old;

//        View newsLVItem;

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        if(newsLVItem == null){
            newsLVItem = inflater.inflate(R.layout.news_lv_item, parent,false);
        }

        ImageView lvImage = newsLVItem.findViewById(R.id.idIVNews);
        TextView lvHeading = newsLVItem.findViewById(R.id.idTVNewsHeading);
        TextView lvSubHeading = newsLVItem.findViewById(R.id.idTVNewsSubHeading);

        Item i = this.getItem(position);

        Picasso.get().load(i.getMedia()).into(lvImage);
        lvHeading.setText(i.getTitle());
        lvSubHeading.setText(i.getDescription());

        return newsLVItem;
    }
}
