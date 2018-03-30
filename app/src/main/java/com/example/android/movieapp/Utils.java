package com.example.android.movieapp;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.android.movieapp.models.Movie;
import com.example.android.movieapp.utils.VolleyCallBack;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by SG on 3/2/2018.
 */

public class Utils

{

    String jsonString;
    private  Context context;
    private  static Utils instance;

    public  static String ApiKey ;

   public static  String originalTitle ;
    public static  String userRating  ;
    public static  String releaseDate ;
    public static  String plotSynopsis;
    public static  String moviePoster;
    public static   String siteURL ;



    private Utils(Context context)
    {

        this.context = context;


         ApiKey = context.getResources().getString(R.string.ApiKey);

  originalTitle = context.getResources().getString(R.string.originalTitle);
      userRating  =    context.getResources().getString(R.string.userRating);
        releaseDate =  context.getResources().getString(R.string.releaseDate);
         plotSynopsis=  context.getResources().getString(R.string.plotSynopsis);
        moviePoster=  context.getResources().getString(R.string.moviePoster);
        siteURL = "https://image.tmdb.org/t/p/w500";


    }

    public static synchronized Utils getInstance(Context context)
    {
        if(instance == null)
        {
            instance = new Utils(context);
        }

        return instance;
    }




    public  ArrayList<Movie> GetMovies(String option)
    {

      final ArrayList<Movie> movies = new ArrayList<Movie>();
      String Url = GetURL(option);


        GetJsonObject(Url, context, new VolleyCallBack() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONObject initial = new JSONObject(result);

                    JSONArray moviesArray = initial.optJSONArray("results");
                    for (int i = 0; i < moviesArray.length(); i++)
                    {
                        JSONObject currentMovie = moviesArray.optJSONObject(i);
                        String title = currentMovie.optString(originalTitle);
                        int rating = currentMovie.optInt(userRating);
                        String date = currentMovie.optString(releaseDate);
                        String synopsis  =currentMovie.optString(plotSynopsis);
                        String imageUrl = siteURL+currentMovie.optString(moviePoster);
                        Movie movie  = new Movie(title,imageUrl,rating,date,synopsis);
                        movies.add(movie);
                    }
                }
                catch (JSONException ex)
                {
                    Log.v("Extract Features","",ex);
                }

            }

            @Override
            public void onError(VolleyError error) {

            }
        });

        return movies;


    }



    public static String GetURL(String option)

    {
        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(option)
                .appendQueryParameter("api_key", ApiKey)
                .appendQueryParameter("language", "en-US");

        String myUrl = builder.build().toString();
        return myUrl;
    }


    public static void GetJsonObject(String url, Context context,final VolleyCallBack callback)

    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                callback.onSuccess(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });



        volley.getInstance(context).addToRequestQueue(stringRequest);
    }







}
