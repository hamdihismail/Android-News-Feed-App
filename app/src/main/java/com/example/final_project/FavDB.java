package com.example.final_project;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class FavDB extends SQLiteOpenHelper {
    protected final static int DB_VERSION = 1;
    protected  final static String DATABASE_NAME = "NewsDB";
    private final static String TABLE_NAME = "favouriteTable";
    public final static String KEY_ID = "articleId";
    public final static String ITEM_TITLE = "itemTitle";
    public final static String ITEM_DESCRIPTION = "itemDesc";
    public final static String ITEM_DATE = "itemDate";
    public final static String ITEM_IMAGE = "itemImage";
    public final static String ITEM_LINK = "itemLink";
    public final static String FAVOURITE_STATUS = "fStatus";

    private static String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "(_id INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_ID+ " UNIQUE, INTEGER,"
            + ITEM_TITLE+ " UNIQUE, TEXT," + ITEM_DESCRIPTION+ " TEXT," + ITEM_DATE+ " TEXT,"
            + ITEM_IMAGE + " TEXT," + ITEM_LINK + " TEXT," + FAVOURITE_STATUS+" TEXT)";

    public FavDB(Context context) { super(context,DATABASE_NAME, null,DB_VERSION);}
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

//    create empty table
    public void insertEmpty(){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
//        enter you value
            cv.put(FAVOURITE_STATUS,"1");
            cv.put(ITEM_TITLE,"Favourites");
            cv.put(KEY_ID,666);
            db.insert(TABLE_NAME,null,cv);

//        for (int x = 1; x < 11; x++) {
//        }
    }

//    insert data into db
    public void insertIntoDB (String item_title, String item_desc, String item_date, String item_image, String item_link, String fav_status,int id) {
        SQLiteDatabase db;
        db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(ITEM_TITLE, item_title);
        cv.put(ITEM_IMAGE, item_image);
        cv.put(ITEM_LINK, item_link);
        cv.put(ITEM_DESCRIPTION, item_desc);
        cv.put(ITEM_DATE, item_date);
        cv.put(KEY_ID, id);
        cv.put(FAVOURITE_STATUS, fav_status);
        db.insertOrThrow(TABLE_NAME, null, cv);
        Log.d("FavDB Status",item_title +", status - "+fav_status+" -." + cv);
    }

//    read all data
    public Cursor read_all_data(String title){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "select * from " + TABLE_NAME + " where " + ITEM_TITLE +"="+title;
        return db.rawQuery(sql,null,null);
    }

//    remove line from db
    public void remove_fav(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE "+ TABLE_NAME + " SET "+FAVOURITE_STATUS+"='0' WHERE "+ KEY_ID+"="+id+"";
        db.execSQL(sql);
        Log.d("remove","Default fav removed");
    }
    public void add_fav(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "UPDATE "+ TABLE_NAME + " SET "+FAVOURITE_STATUS+"='1' WHERE "+ KEY_ID+"="+id+"";
        db.execSQL(sql);
        Log.d("remove","Default fav removed");
    }
    public void remove_row(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM "+ TABLE_NAME + " WHERE "+ KEY_ID+"="+id+"";
        db.execSQL(sql);
        Log.d("remove","Row removed");
    }

//    select all fav list
    public Cursor select_all_fav_list(){
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM "+TABLE_NAME+" WHERE "+FAVOURITE_STATUS+"='1'";
        return db.rawQuery(sql,null,null);
    }

    public void deleteTable() {

        // on below line we are creating
        // a variable to write our database.
        SQLiteDatabase db = this.getWritableDatabase();

        // on below line we are calling a method to delete our
        // course and we are comparing it with our course name.
        String sql = "DROP TABLE IF EXISTS "+ TABLE_NAME;
        db.execSQL(sql);
//        db.close();
    }

}
