package com.example.android.movieapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.android.movieapp.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;

/**
 * Created by SG on 3/2/2018.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {


    private final LayoutInflater mInflator;
    private Context mContext;
    List<Movie> mData = Collections.emptyList();


    public MovieAdapter(Context mContext, List<Movie> mData) {
        this.mInflator =LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mData = mData;

    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = mInflator.inflate(R.layout.list_item,parent,false);
        MyViewHolder holder =new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

       final Movie currrentMovie = mData.get(position);
        Log.v("Movie1",currrentMovie.getTitle());
        Picasso.with(mContext).load(currrentMovie.getImage()).into(holder.movieImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, Details.class);
                intent.putExtra(mContext.getResources().getString(R.string.MovieObject),currrentMovie);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

    ImageView movieImageView;



    public MyViewHolder(View itemView) {
        super(itemView);

        movieImageView = (ImageView)itemView.findViewById(R.id.thumbImage);

    }



}





}
