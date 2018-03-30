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

import com.example.android.movieapp.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class MoviesActivity extends AppCompatActivity {



    private GridLayoutManager mGridLayoutManager;
   private RecyclerView mRecyclerView;
   private MovieAdapter movieAdapter;
   ArrayList<Movie> movies;
    private TextView mNoInternet;
private Spinner spinner;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final String TopRated_Value=  getResources().getString(R.string.TopRated_Value);
        final String Popular_value=  getResources().getString(R.string.Popular_value);

        ConnectivityManager connMgr = (ConnectivityManager)getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        mNoInternet = (TextView)findViewById(R.id.TextNoInternet);
        mRecyclerView = (RecyclerView)findViewById(R.id.recylerView);
        mGridLayoutManager = new GridLayoutManager(getApplicationContext(),3,GridLayoutManager.VERTICAL,false);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(mGridLayoutManager);

        spinner = (Spinner)findViewById(R.id.Moviespinner);

        if(savedInstanceState == null || !savedInstanceState.containsKey("movies")) {

            movies= Utils.getInstance(getApplicationContext()).GetMovies(Popular_value);
        SetRecyclerView(movies);


        if (networkInfo != null && networkInfo.isConnected())
        {

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String option = spinner.getSelectedItem().toString();

                if (option.equals(getResources().getString(R.string.Popular)))
                {
                    movies= Utils.getInstance(getApplicationContext()).GetMovies(Popular_value);
                    SetRecyclerView(movies);

                }
                if (option.equals(getResources().getString(R.string.TopRated)))
                {
                    movies= Utils.getInstance(getApplicationContext()).GetMovies(TopRated_Value);
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
        else {

            movies = savedInstanceState.getParcelableArrayList("movies");
            SetRecyclerView(movies);
        }


    }


    public void SetRecyclerView( List<com.example.android.movieapp.models.Movie> movies)
    {
        movieAdapter = new MovieAdapter(MoviesActivity.this, movies);
        mRecyclerView.setAdapter(movieAdapter);
       View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
        mNoInternet.setVisibility(View.GONE);

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        outState.putParcelableArrayList("movies", movies);
    }

    public void ShowPrograssBar(){

             mRecyclerView.setVisibility(View.GONE);
       ProgressBar progressBar = (ProgressBar)findViewById(R.id.loading_indicator);


    }
    public void HidePrograssBar(){

        mRecyclerView.setVisibility(View.VISIBLE);
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);
    }




}