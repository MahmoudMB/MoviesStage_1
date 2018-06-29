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
import java.util.List;
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




    public  void GetMovies(String option,final VolleyCallBack callback)
    {

      final ArrayList<Movie> movies = new ArrayList<Movie>();
      String Url = GetURL(option);




        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                try {
                    JSONObject initial = new JSONObject(response);

                    JSONArray moviesArray = initial.optJSONArray("results");
                    for (int i = 0; i < moviesArray.length(); i++)
                    {
                        JSONObject currentMovie = moviesArray.optJSONObject(i);
                        String title = currentMovie.optString(originalTitle);
                        int rating = currentMovie.optInt(userRating);
                        String date = currentMovie.optString(releaseDate);
                        String synopsis  =currentMovie.optString(plotSynopsis);
                        String imageUrl = siteURL+currentMovie.optString(moviePoster);




                    String movieId =  currentMovie.optString("id");




                        Movie movie  = new Movie(title,imageUrl,rating,date,synopsis);
                        movie.setId(movieId);
                        movies.add(movie);

                    }
                }
                catch (JSONException ex)
                {
                    Log.v("Extract Features","",ex);
                }


                callback.onSuccess(movies);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        volley.getInstance(context).addToRequestQueue(stringRequest);

    }








    public  void GetTrailsAndReviews(String MovieId,final VolleyCallBack callback)
    {


        String Url ="http://api.themoviedb.org/3/movie/"+MovieId+"?api_key=37d390e3d5e8ee678ad4b99cebe05ad5&append_to_response=reviews,videos";


final List<String> Reviews = new ArrayList<>();
        final List<String> Trailers = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.GET, Url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {

                try {
                    JSONObject initial = new JSONObject(response);


JSONObject ReviewResults = initial.getJSONObject("reviews");

                    JSONArray moviesreviews = ReviewResults.optJSONArray("results");
                    for (int i = 0; i < moviesreviews.length(); i++)
                    {
                        JSONObject currentReview = moviesreviews.optJSONObject(i);
                        String Author = currentReview.optString("author");
                        String content = currentReview.optString("content");


                     //   String imageUrl = siteURL+currentMovie.optString(moviePoster);



                        Reviews.add(content);

                    }


                 //   Log.v("UtlisReviews",Reviews.get(0));
                    JSONObject TrailersResults = initial.getJSONObject("videos");
                    JSONArray moviesTrailers = TrailersResults.optJSONArray("results");
                    for (int i = 0; i < moviesTrailers.length(); i++)
                    {
                        JSONObject currentTrailer = moviesTrailers.optJSONObject(i);

                        String content = currentTrailer.optString("key");


                          String YoutubeUrl = "https://www.youtube.com/watch?v="+content;

                        Log.v("UtlisTrails",content);
                        Trailers.add(YoutubeUrl);

                    }


                }
                catch (JSONException ex)
                {
                    Log.v("Extract Features","",ex);
                }


                callback.ReturnReviewsAndTrails(Reviews,Trailers);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });

        volley.getInstance(context).addToRequestQueue(stringRequest);


    }

    public static String GetReviewsURL(String movieID){



        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(movieID)
                .appendPath("reviews")
                .appendQueryParameter("api_key", ApiKey)
                .appendQueryParameter("language", "en-US");
        String myUrl = builder.build().toString();
        return myUrl;

    }


    public static String GetTrailerURL(String movieID)
    {

        Uri.Builder builder = new Uri.Builder();
        builder.scheme("https")
                .authority("api.themoviedb.org")
                .appendPath("3")
                .appendPath("movie")
                .appendPath(movieID)
                .appendPath("videos")
                .appendQueryParameter("api_key", ApiKey)
                .appendQueryParameter("language", "en-US");

        String myUrl = builder.build().toString();
        return myUrl;
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

    }







}
