package com.example.final_project;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteConstraintException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.io.File;

public class NewsDetailActivity extends AppCompatActivity {

    String title,desc,date,link,media;
    int id;
    private TextView tvTitle,tvDesc,tvDate;
    private ImageView ivMedia;
    private Button readBtn;
    private ImageButton favBtn;
//    private File path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS+"/FavNews");
    private FavDB db;
    Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

//        if(!path.exists()) {
//            path.mkdirs();
//        }

        title = getIntent().getStringExtra("title");
        desc = getIntent().getStringExtra("desc");
        date = getIntent().getStringExtra("date");
        link = getIntent().getStringExtra("link");
        media = getIntent().getStringExtra("media");
        id = getIntent().getIntExtra("id",0);
        tvTitle = findViewById(R.id.idTVNewsHeading);
        tvDesc = findViewById(R.id.idTVNewsSubHeading);
        tvDate = findViewById(R.id.idTVNewsDate);
        ivMedia = findViewById(R.id.idIVNews);
        readBtn = findViewById(R.id.idBtnRead);
        favBtn = findViewById(R.id.idBtnFav);
        tvTitle.setText(title);
        tvDesc.setText(desc);
        tvDate.setText("Date Published: "+date);
        Picasso.get().load(media).into(ivMedia);
        readBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(link));
                startActivity(i);
            }
        });

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                db = new FavDB(NewsDetailActivity.this);
                System.out.println("Fav DB count from Detail: "+db.select_all_fav_list().getCount());

                try {
                        db.insertIntoDB(title,desc,date,media,link,"1",id);
                        db.remove_fav(666);
                        favBtn.setImageResource(android.R.drawable.btn_star_big_on);
                        Toast.makeText(NewsDetailActivity.this, "Saved to Favourites", Toast.LENGTH_SHORT).show();
                        Thread.sleep(30);
                        Intent i = new Intent(NewsDetailActivity.this, MainActivity.class);
                        finish();
                        overridePendingTransition(1, 1);
                        startActivity(i);
                        overridePendingTransition(1, 1);

                }catch (SQLiteConstraintException e){
                    e.printStackTrace();
                    Toast.makeText(NewsDetailActivity.this, "Removed from favourites list", Toast.LENGTH_SHORT).show();
                    db.remove_row(id);
                    favBtn.setImageResource(android.R.drawable.btn_star_big_off);
                    System.out.println("Fav DB count from Detail Catch: "+db.select_all_fav_list().getCount());
                    if(db.select_all_fav_list().getCount()==0){
                        db.add_fav(666);
                    }
                    Intent i = new Intent(NewsDetailActivity.this, MainActivity.class);
                    finish();
                    overridePendingTransition(1, 1);
                    startActivity(i);
                    overridePendingTransition(1, 1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }

//                db.deleteTable();
//                db.close();

            }
        });

    }
}