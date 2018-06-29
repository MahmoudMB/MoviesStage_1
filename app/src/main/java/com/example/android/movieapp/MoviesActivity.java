package com.example.android.movieapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ProgressBar;

import android.database.Cursor;

import com.android.volley.VolleyError;
import com.example.android.movieapp.data.DBHelper;
import com.example.android.movieapp.data.contract;
import com.example.android.movieapp.models.Movie;
import com.example.android.movieapp.utils.VolleyCallBack;

import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity implements VolleyCallBack {



    private GridLayoutManager mGridLayoutManager;
   private RecyclerView mRecyclerView;
   private MovieAdapter movieAdapter;
   ArrayList<Movie> movies;
    private TextView mNoInternet;
    private DBHelper helper;
private Spinner spinner;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        helper = new DBHelper(getApplicationContext());

        final String TopRated_Value=  getResources().getString(R.string.TopRated_Value);
        final String Popular_value=  getResources().getString(R.string.Popular_value);

        final ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        mNoInternet = (TextView)findViewById(R.id.TextNoInternet);
        mRecyclerView = (RecyclerView)findViewById(R.id.recylerView);
        mGridLayoutManager = new GridLayoutManager(getApplicationContext(),3,GridLayoutManager.VERTICAL,false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        spinner = (Spinner)findViewById(R.id.Moviespinner);
        ShowPrograssBar();

        if(savedInstanceState == null || !savedInstanceState.containsKey("movies"))
            Utils.getInstance(getApplicationContext()).GetMovies(Popular_value,MoviesActivity.this);

 else {

                movies = savedInstanceState.getParcelableArrayList("movies");
                SetRecyclerView(movies);
            }

        if (networkInfo != null && networkInfo.isConnected())
        {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String option = spinner.getSelectedItem().toString();

                if (option.equals(getResources().getString(R.string.Popular)))
                {
                    ShowPrograssBar();
                   Utils.getInstance(getApplicationContext()).GetMovies(Popular_value,MoviesActivity.this);


                }
                if (option.equals(getResources().getString(R.string.TopRated)))
                {  ShowPrograssBar();
                   Utils.getInstance(getApplicationContext()).GetMovies(TopRated_Value,MoviesActivity.this);
                }


                if (option.equals("Favourite"))
                {  ShowPrograssBar();

            Cursor moviesCursor= getContentResolver().query(contract.Favourite.CONTENT_URI,null,null,null,null);
                    movies = helper.getAllMovies(moviesCursor);
              SetRecyclerView(movies);

                }



            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        }

        else{

           View loadingIndicator = findViewById(R.id.loading_indicator);
           loadingIndicator.setVisibility(View.GONE);
            mNoInternet.setText(getResources().getText(R.string.NoInternet));
        }

    }


    public void SetRecyclerView( List<com.example.android.movieapp.models.Movie> movies)
    {
        mRecyclerView.setVisibility(View.VISIBLE);
        movieAdapter = new MovieAdapter(MoviesActivity.this, movies);
        mRecyclerView.setAdapter(movieAdapter);
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        mNoInternet.setVisibility(View.GONE);

    }


    @Override
    protected void onResume() {


        String option = spinner.getSelectedItem().toString();
        if (option.equals("Favourite")) {
            Cursor moviesCursor = getContentResolver().query(contract.Favourite.CONTENT_URI, null, null, null, null);
            movies = helper.getAllMovies(moviesCursor);
            SetRecyclerView(movies);

        }

            super.onResume();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        outState.putParcelableArrayList("movies", movies);
    }

    public void ShowPrograssBar(){

             mRecyclerView.setVisibility(View.GONE);
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.VISIBLE);


    }
    public void HidePrograssBar(){
        mRecyclerView.setVisibility(View.VISIBLE);
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
    }



    @Override
    public void onSuccess(ArrayList<Movie> movies) {
this.movies = movies;
        movieAdapter = new MovieAdapter(MoviesActivity.this, movies);
        mRecyclerView.setAdapter(movieAdapter);
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        mNoInternet.setVisibility(View.GONE);
        mRecyclerView.setVisibility(View.VISIBLE);
    }


    @Override
    public void ReturnReviewsAndTrails(List<String> reviews, List<String> Trails) {

    }



    @Override
    public void onError(VolleyError error) {

    }
}
