package com.example.android.movieapp;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movieapp.models.Movie;
import com.squareup.picasso.Picasso;

public class Details extends AppCompatActivity {
    ActionBar actionBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Movie movie = (Movie) getIntent().getExtras().getParcelable(getResources().getString(R.string.MovieObject));

        ImageView image = (ImageView)findViewById(R.id.image_detail);
        TextView title = (TextView) findViewById(R.id.movie_title);
        TextView overFlow = (TextView) findViewById(R.id.movie_Overflow);
        TextView releaseDate = (TextView) findViewById(R.id.movie_date);
        TextView userRating = (TextView) findViewById(R.id.movie_rating);


        title.setText(movie.getTitle());
       overFlow.setText(movie.getPlotSynopsis());
       releaseDate.setText(movie.getReleaseDate());
       userRating.setText(String.valueOf(movie.getUserRating()));
        Picasso.with(this).load(movie.getImage()).fit().into(image);

        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(movie.getTitle());



    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;

        }
        return super.onOptionsItemSelected(item);


    }
}
