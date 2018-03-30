package com.example.android.movieapp.utils;

import com.android.volley.VolleyError;

/**
 * Created by SG on 3/24/2018.
 */

public interface VolleyCallBack {

    void onSuccess(String result);

    void onError(VolleyError error);


}
