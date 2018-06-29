package com.example.android.movieapp.data;

/**
 * Created by SG on 6/4/2018.
 */
import android.net.Uri;
public final class contract {
public static final String AUTHORITY="com.example.android.movieapp";
public static final Uri BASE_CONTENT_URI=Uri.parse("content://"+AUTHORITY);

public static final String PATH_Favourite ="Favourite";

    public contract() {

    }


    public static final class Favourite{

public static final Uri CONTENT_URI= BASE_CONTENT_URI.buildUpon().appendPath(PATH_Favourite).build();

        public final static String TABLE_NAME = "Favourite";
        public final static String COLUMN_Favourite_ID= "ID"; //0
        public final static String COLUMN_Favourite_Title ="Title";  //1
        public final static String COLUMN_Favourite_Rating = "Rating"; //2
        public final static String COLUMN_Favourite_Date= "Date"; //3
        public final static String COLUMN_Favourite_Synopsis= "Synopsis"; //4
        public final static String COLUMN_Favourite_ImageUrl= "ImageUrl"; //5
    }



}
