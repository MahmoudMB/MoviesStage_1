package com.example.android.movieapp.utils;

import com.android.volley.VolleyError;
import com.example.android.movieapp.models.Movie;

import java.util.ArrayList;

/**
 * Created by SG on 3/24/2018.
 */

public interface VolleyCallBack {

   void  onSuccess( ArrayList<Movie> movies);

    void onError(VolleyError error);

}
