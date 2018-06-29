package com.example.android.movieapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

/**
 * Created by SG on 6/4/2018.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.MyViewHolder>  {

    private final LayoutInflater mInflator;
    private Context mContext;
    List<String> mData = Collections.emptyList();

    public ReviewAdapter(Context mContext, List<String> mData ) {
        this.mInflator =LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mData = mData;

    }



    @Override
    public ReviewAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflator.inflate(R.layout.review_items,parent,false);
        ReviewAdapter.MyViewHolder holder =new ReviewAdapter.MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(ReviewAdapter.MyViewHolder holder, int position) {
        holder.Review.setText(mData.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView Review;

        public MyViewHolder(View itemView) {
            super(itemView);
            Review = (TextView) itemView.findViewById(R.id.MovieReview);
        }



    }


}
