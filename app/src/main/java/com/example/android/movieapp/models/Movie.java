package com.example.android.movieapp.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by SG on 3/2/2018.
 */

public class Movie implements Parcelable {

    private String mTitle;
    private String mImage;
    private int mUserRating;
    private String mReleaseDate;
    private String mPlotSynopsis;




    public Movie(String mTitle, String mImage, int mUserRating, String mReleaseDate, String mPlotSynopsis) {
        this.mTitle = mTitle;
        this.mImage = mImage;
        this.mUserRating = mUserRating;
        this.mReleaseDate = mReleaseDate;
        this.mPlotSynopsis = mPlotSynopsis;
    }

    public Movie() {
        mTitle = "";
        mImage ="";
        mReleaseDate="";
        mUserRating=0;
        mPlotSynopsis="";
    }

    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getImage() {
        return mImage;
    }

    public void setImage(String mImage) {
        this.mImage = mImage;
    }

    public int getUserRating() {
        return mUserRating;
    }

    public void setUserRating(int mUserRating) {
        this.mUserRating = mUserRating;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public void setReleaseDate(String mReleaseDate) {
        this.mReleaseDate = mReleaseDate;
    }

    public String getPlotSynopsis() {
        return mPlotSynopsis;
    }

    public void setPlotSynopsis(String mPlotSynopsis) {
        this.mPlotSynopsis = mPlotSynopsis;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitle);
        dest.writeString(this.mImage);
        dest.writeInt(this.mUserRating);
        dest.writeString(this.mReleaseDate);
        dest.writeString(this.mPlotSynopsis);



    }

    protected Movie(Parcel in) {
        this.mTitle = in.readString();
        this.mImage = in.readString();
        this.mUserRating=in.readInt();
        this.mReleaseDate = in.readString();
        this.mPlotSynopsis =in.readString();


    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };






}
