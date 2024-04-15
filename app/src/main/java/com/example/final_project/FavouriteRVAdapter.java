package com.example.final_project;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FavouriteRVAdapter extends RecyclerView.Adapter<FavouriteRVAdapter.ViewHolder> {
    private ArrayList<FavouriteRVModal> favouriteRVModals;
    private Context context;
    private FavClickInterface favClickInterface;
    private FavDB db;

    public FavouriteRVAdapter(ArrayList<FavouriteRVModal> favouriteRVModals, Context context, FavClickInterface favClickInterface) {
        this.favouriteRVModals = favouriteRVModals;
        this.context = context;
        this.favClickInterface = favClickInterface;
    }

    @NonNull
    @Override
    public FavouriteRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        db = new FavDB(context);
//        create table on first
        SharedPreferences prefs = context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart",true);
        if(firstStart){
            createTableOnFirstStart();
            System.out.println("Table created");
        } else {
            System.out.println("Table exists");
        }
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.favourites_rv_item,parent,false);
        return new FavouriteRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavouriteRVAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        FavouriteRVModal favouriteRVModal = favouriteRVModals.get(position);
        holder.favTV.setText(favouriteRVModal.getTitle());
        if (favouriteRVModal.getFavouriteMedia() != null){
            Picasso.get().load(favouriteRVModal.getFavouriteMedia()).into(holder.favIV);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                favClickInterface.onFavClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return favouriteRVModals.size();
    }
    public interface FavClickInterface {
        void onFavClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView favTV;
        private ImageView favIV;
        private ImageButton favBtn;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            favTV = itemView.findViewById(R.id.idTVFav);
            favIV = itemView.findViewById(R.id.idIVFav);
            favBtn = itemView.findViewById(R.id.idBtnFav);

//            favBtn.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//
//                }
//            });
        }
    }

    private void createTableOnFirstStart(){
        db.insertEmpty();

        SharedPreferences prefs = context.getSharedPreferences("prefs",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean("firstStart",false);
        editor.apply();
    }
}
