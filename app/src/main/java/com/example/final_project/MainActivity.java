package com.example.final_project;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements FavouriteRVAdapter.FavClickInterface {
//    private Context context;

    private RecyclerView favRV;
    private ListView newsLV;
    private ProgressBar loadingPB;
    private ArrayList<Item> itemArrayList;
    private ArrayList<FavouriteRVModal> favouriteRVModalArrayList;
    private FavouriteRVAdapter favouriteRVAdapter;
    private NewsLVAdapter newsLVAdapter;
    private FavDB db;
    private ImageButton help;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        help = findViewById(R.id.idLVHelp);
        newsLV = findViewById(R.id.idLVNews);
        favRV = findViewById(R.id.idRVFavourites);
        loadingPB = findViewById(R.id.idPBLoading);
        itemArrayList = new ArrayList<>();
        favouriteRVModalArrayList = new ArrayList<>();
        newsLVAdapter = new NewsLVAdapter(itemArrayList,this);
        favouriteRVAdapter = new FavouriteRVAdapter(favouriteRVModalArrayList,this,this::onFavClick);
        newsLV.setAdapter(newsLVAdapter);
        favRV.setAdapter(favouriteRVAdapter);
        db = new FavDB(MainActivity.this);
        getFavourites();
        try {
            getNews();
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }

        help.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
                alertDialogBuilder.setTitle(R.string.dialogHelpTitle)
                        .setMessage(R.string.mainHelpDialog)
                        .setPositiveButton(R.string.dialogClose, (click, arg) -> {
                            recreate();
                        }).create().show();
            }
        });
        newsLV.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Item items = itemArrayList.get(position);
                Intent i = new Intent(MainActivity.this,NewsDetailActivity.class);
                i.putExtra("title",items.getTitle());
                i.putExtra("desc",items.getDescription());
                i.putExtra("date",items.getDate());
                i.putExtra("link",items.getLink());
                i.putExtra("media",items.getMedia());
                i.putExtra("id",items.getId());
                startActivity(i);
            }
        });
    }

    private void getFavourites(){
        SharedPreferences prefs = getSharedPreferences("prefs",Context.MODE_PRIVATE);
        boolean firstStart = prefs.getBoolean("firstStart",true);
        if(!firstStart){
            db = new FavDB(MainActivity.this);
//            if (db.select_all_fav_list().getCount());
            System.out.println("Fav DB count: "+db.select_all_fav_list().getCount());
//            for(int i=0;i<db.select_all_fav_list().getCount();i++){
            Cursor results = db.select_all_fav_list();
//                favouriteRVModalArrayList.add(new FavouriteRVModal(db.read_all_data(String.valueOf(i))));
                int titleIndex = results.getColumnIndex(FavDB.ITEM_TITLE);
                int descIndex = results.getColumnIndex(FavDB.ITEM_DESCRIPTION);
                int dateIndex = results.getColumnIndex(FavDB.ITEM_DATE);
                int imageIndex = results.getColumnIndex(FavDB.ITEM_IMAGE);
                int linkIndex = results.getColumnIndex(FavDB.ITEM_LINK);
//                System.out.println("Get Fav Value: "+results.getString(title));
                while(results.moveToNext()){
                    String title = results.getString(titleIndex);
                    String desc = results.getString(descIndex);
                    String date = results.getString(dateIndex);
                    String image = results.getString(imageIndex);
                    String link = results.getString(linkIndex);
                    System.out.println("Title: "+title);
                    favouriteRVModalArrayList.add(new FavouriteRVModal(title,image, date, desc, link));

                }
//            }
        } else {
//            TextView tvFav = findViewById(R.id.idTVFav);
//            ImageView ivFav = findViewById(R.id.idIVFav);

        favouriteRVModalArrayList.add(new FavouriteRVModal(MainActivity.this.getString(R.string.favourites),null, null, null, null));
//        favouriteRVModalArrayList.add(new FavouriteRVModal("Boom times for US green energy as federal cash flows in","https://ichef.bbci.co.uk/ace/standard/240/cpsprodpb/4C54/production/_133104591_avnos_getty.jpg"));
        }
        favouriteRVAdapter.notifyDataSetChanged();

    }

    private void getNews() throws MalformedURLException {
        loadingPB.setVisibility(View.VISIBLE);
        itemArrayList.clear();
        new MyHTTPRequest().execute("https://feeds.bbci.co.uk/news/world/us_and_canada/rss.xml");
        newsLVAdapter.notifyDataSetChanged();

    }

    @Override
    public void onFavClick(int position) {
        FavouriteRVModal items = favouriteRVModalArrayList.get(position);
        Intent i = new Intent(MainActivity.this,NewsDetailActivity.class);

        if((items.getDescription()!=null)&&(items.getDate()!=null)&&(items.getLink()!=null)){
            i.putExtra("title",items.getTitle());
            i.putExtra("desc",items.getDescription());
            i.putExtra("date",items.getDate());
            i.putExtra("link",items.getLink());
            i.putExtra("media",items.getFavouriteMedia());
            i.putExtra("id",items.getId());
            startActivity(i);
        }else{
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setTitle(R.string.favourites)
                    .setMessage(R.string.mainNoFavDialog)
                    .setPositiveButton(R.string.dialogClose, (click, arg) -> {
                        recreate();
                    }).create().show();
        }

    }

    public class MyHTTPRequest extends AsyncTask<String,Integer, String>{
        @Override
        protected String doInBackground(String... params) {
            HttpURLConnection urlConnection = null;
            Item item = new Item();
            String text = null;

            try {
                URL url = new URL(params[0]);
                urlConnection = (HttpURLConnection) url.openConnection();

                int code = urlConnection.getResponseCode();
                if (code !=  200) {
                    System.out.println("Invalid response from server: " + code);
                } else{
                    System.out.println("Valid response from server: " + code);
                }

                BufferedReader rd = new BufferedReader(new InputStreamReader(
                        urlConnection.getInputStream()));
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                XmlPullParser parser = factory.newPullParser();
                parser.setInput(rd);

                int eventType = parser.getEventType();
                while(eventType != XmlPullParser.END_DOCUMENT){

                    String tag = parser.getName();
                    switch (eventType){
                        case XmlPullParser.START_TAG:
                            if(tag.equalsIgnoreCase("item")){
                                item = new Item();
                            }else {
                                break;
                            }
                            break;

                        case XmlPullParser.TEXT:
                            text = parser.getText();
                            break;

                        case XmlPullParser.END_TAG:
                            if((tag.equalsIgnoreCase("item"))){
                                itemArrayList.add(new Item(item.getTitle(),
                                        item.getDescription(),
                                        item.getDate(),
                                        item.getLink(),
                                        item.getMedia()));
                            }else if(tag.equalsIgnoreCase("title")){
                                item.setTitle(text);
                            }else if(tag.equalsIgnoreCase("description")){
                                item.setDescription(text);
                            }else if(tag.equalsIgnoreCase("pubDate")){
                                item.setDate(text);
                            }else if(tag.equalsIgnoreCase("link")){
                                item.setLink(text);
                            }else if(tag.equalsIgnoreCase("media:thumbnail")){
                                item.setMedia(parser.getAttributeValue(null,"url"));
                            }
                            break;
                        default:
                            break;

                    }

                    eventType = parser.next();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (urlConnection != null) {
                    urlConnection.disconnect();
                }
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            newsLVAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onPostExecute(String result){
            super.onPostExecute(result);
            loadingPB.setVisibility(View.GONE);
        }
    }
}