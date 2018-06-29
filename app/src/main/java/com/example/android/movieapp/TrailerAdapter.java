package com.example.android.movieapp;
import android.content.Intent;
import android.util.Log;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.movieapp.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.Collections;
import java.util.List;
import android.net.Uri;
import android.content.Intent;
/**
 * Created by SG on 6/3/2018.
 */

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.MyViewHolder>  {


    private final LayoutInflater mInflator;
    private Context mContext;
    List<String> mData = Collections.emptyList();

    public TrailerAdapter(Context mContext, List<String> mData ) {
        this.mInflator =LayoutInflater.from(mContext);
        this.mContext = mContext;
        this.mData = mData;

    }



    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflator.inflate(R.layout.trailer_items,parent,false);
        TrailerAdapter.MyViewHolder holder =new TrailerAdapter.MyViewHolder(view);
        return holder;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.TrailerNumber.setText("Trailer: "+ ++position);

final int pos = position;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              Intent i=  new Intent(Intent.ACTION_VIEW, Uri.parse(mData.get(pos).toString()));
               mContext.startActivity(i);
            }
        });


    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView TrailerNumber;

        public MyViewHolder(View itemView) {
            super(itemView);
            TrailerNumber = (TextView) itemView.findViewById(R.id.MovieTrialerNumber);
        }



    }


}
