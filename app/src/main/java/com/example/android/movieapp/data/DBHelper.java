package com.example.android.movieapp.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.android.movieapp.data.contract.Favourite;
import com.example.android.movieapp.models.Movie;
import java.util.ArrayList;

import android.net.Uri;
/**
 * Created by SG on 6/4/2018.
 */





public class DBHelper extends SQLiteOpenHelper {


    private static final String DATABASE_NAME = "data.db";

    private Context c;
    private static final int DATABASE_VERSION = 1;

    SQLiteDatabase sql;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        c=context;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {


        String SQL_CREATE_CUSTOMERS_TABLE =  "CREATE TABLE " + Favourite.TABLE_NAME + " ("
                + Favourite.COLUMN_Favourite_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + Favourite.COLUMN_Favourite_Title + " TEXT NOT NULL, "
                + Favourite.COLUMN_Favourite_Rating + " INTEGER NOT NULL, "
                + Favourite.COLUMN_Favourite_Date + " TEXT NOT NULL, "
                + Favourite.COLUMN_Favourite_Synopsis + " TEXT, "
                + Favourite.COLUMN_Favourite_ImageUrl + " TEXT);";



        db.execSQL(SQL_CREATE_CUSTOMERS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }






public ArrayList<Movie> getAllMovies(Cursor cursor){




    ArrayList<Movie> movies = new ArrayList<Movie>();

//    sql = getReadableDatabase();
 //   String query = "select * from " + Favourite.TABLE_NAME;

   // Cursor cursor = sql.rawQuery(query,null);

    if (cursor!=null) {
        cursor.moveToFirst();


        while (!cursor.isAfterLast())
        {
            Movie e = new Movie(cursor.getString(1),cursor.getString(5),cursor.getInt(2),cursor.getString(3),cursor.getString(4));

            e.setId(cursor.getString(0));
            movies.add(e);
            cursor.moveToNext();
        }



    }


        return movies;



}




public boolean CheckForFavourite(Movie movie)

{
boolean r = false;
    sql = getReadableDatabase();

    String query = "select * from " + Favourite.TABLE_NAME + " where " + Favourite.COLUMN_Favourite_ID + " = "  + "'"+movie.getId()+"'";

    Cursor cursor = sql.rawQuery(query,null);



    if (cursor.getCount()!=0) {
        cursor.moveToFirst();

sql.close();
        return true;
    }


sql.close();
    return false;

}
}
