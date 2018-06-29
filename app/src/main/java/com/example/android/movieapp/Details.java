package com.example.android.movieapp;
import android.content.ContentValues;
import android.util.Log;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Button;
import com.android.volley.VolleyError;
import com.example.android.movieapp.data.DBHelper;
import com.example.android.movieapp.data.contract;
import com.example.android.movieapp.models.Movie;
import com.example.android.movieapp.utils.VolleyCallBack;
import com.squareup.picasso.Picasso;
import android.support.v7.widget.LinearLayoutManager;
import java.util.List;
import java.util.ArrayList;


import android.net.Uri;
public class Details extends AppCompatActivity implements VolleyCallBack {
    ActionBar actionBar;

    private DBHelper helper;
    private List<String> reviews;
    private List<String> trailers;

    private RecyclerView mTrailerRecycler;
    private RecyclerView mReviewRecycler;

    View loadingIndicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        final Movie movie = (Movie) getIntent().getExtras().getParcelable(getResources().getString(R.string.MovieObject));
        helper = new DBHelper(getApplicationContext());
        ImageView image = (ImageView)findViewById(R.id.image_detail);
        TextView title = (TextView) findViewById(R.id.movie_title);
        TextView overFlow = (TextView) findViewById(R.id.movie_Overflow);
        TextView releaseDate = (TextView) findViewById(R.id.movie_date);
        TextView userRating = (TextView) findViewById(R.id.movie_rating);



        loadingIndicator  = findViewById(R.id.loading_indicator1);
        loadingIndicator.setVisibility(View.VISIBLE);



        title.setText(movie.getTitle());
       overFlow.setText(movie.getPlotSynopsis());
       releaseDate.setText(movie.getReleaseDate());
       userRating.setText(String.valueOf(movie.getUserRating()));
        Picasso.with(this).load(movie.getImage()).fit().into(image);

        actionBar = getSupportActionBar();
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(movie.getTitle());



        mTrailerRecycler = (RecyclerView)findViewById(R.id.TrailerRecycler);
        mTrailerRecycler.setLayoutManager(new LinearLayoutManager(this));
        mTrailerRecycler.setHasFixedSize(true);


        mReviewRecycler = (RecyclerView)findViewById(R.id.ReviewRecycler);
        mReviewRecycler.setLayoutManager(new LinearLayoutManager(this));
        mReviewRecycler.setHasFixedSize(true);



Utils.getInstance(getApplicationContext()).GetTrailsAndReviews(movie.getId(),Details.this);




final Button fav = (Button)findViewById(R.id.FavBut);

 boolean r = helper.CheckForFavourite(movie);

if(r)
fav.setText("remove Favourite");
        else
    fav.setText("Add Favourite");
fav.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {

        boolean check = helper.CheckForFavourite(movie);
        if(check){
            Uri uri = contract.Favourite.CONTENT_URI;
            uri = uri.buildUpon().appendPath(movie.getId()).build();
            Log.v("URI",""+uri.toString());
            getContentResolver().delete(uri,null,null);
            fav.setText("Add Favourite");
            check = false;
        }
            else {
            ContentValues values = new ContentValues();
            values.put(contract.Favourite.COLUMN_Favourite_ID,movie.getId());
            values.put(contract.Favourite.COLUMN_Favourite_Title,movie.getTitle());
            values.put(contract.Favourite.COLUMN_Favourite_Rating,movie.getUserRating());
            values.put(contract.Favourite.COLUMN_Favourite_Date,movie.getReleaseDate());
            values.put(contract.Favourite.COLUMN_Favourite_Synopsis,movie.getPlotSynopsis());
            values.put(contract.Favourite.COLUMN_Favourite_ImageUrl,movie.getImage());


            Uri uri = getContentResolver().insert(contract.Favourite.CONTENT_URI,values);

            Log.v("URI",""+uri.toString());
            fav.setText("remove Favourite");
            check = true;
        }

    }
});

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

    @Override
    public void onSuccess(ArrayList<Movie> movies) {

    }

    @Override
    public void ReturnReviewsAndTrails(List<String> reviews, List<String> Trails) {

        this.trailers = Trails;
        this.reviews = reviews;
        TrailerAdapter adapter  = new TrailerAdapter(Details.this, trailers);
        mTrailerRecycler.setAdapter(adapter);




      //  Log.v("DetailsReview",reviews.get(0).toString());
        ReviewAdapter reviewAdapter  = new ReviewAdapter(Details.this, reviews);
        mReviewRecycler.setAdapter(reviewAdapter);

        loadingIndicator.setVisibility(View.GONE);

    }

    @Override
    public void onError(VolleyError error) {

    }
}
