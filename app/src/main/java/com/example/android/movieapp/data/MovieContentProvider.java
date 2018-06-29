package com.example.android.movieapp.data;

/**
 * Created by SG on 6/27/2018.
 */

import android.content.ContentProvider;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.content.UriMatcher;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.content.ContentUris;
public class MovieContentProvider extends ContentProvider {

    private DBHelper mDBHelper;



    public static final int Movies=100;
    public static final int Movies_With_Id=101;

private static final UriMatcher sUriMatcher = buildUriMatcher();
    public static UriMatcher buildUriMatcher(){
        UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(contract.AUTHORITY,contract.PATH_Favourite,Movies);

        uriMatcher.addURI(contract.AUTHORITY,contract.PATH_Favourite+"/#",Movies_With_Id);
return uriMatcher;
    }


    @Override
    public boolean onCreate() {

        Context context  = getContext();

        mDBHelper = new DBHelper(context);


        return true;
    }

    @Nullable
    @Override
    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        final SQLiteDatabase db = mDBHelper.getReadableDatabase();

        int match  = sUriMatcher.match(uri);

        Cursor retCursor;
        switch (match)
        {

            case Movies:
retCursor  = db.query(contract.Favourite.TABLE_NAME,projection,selection,selectionArgs,null,null,sortOrder);
                break;

            default:
                throw  new UnsupportedOperationException("Unkonw Uri "+uri);

        }

retCursor.setNotificationUri(getContext().getContentResolver(),uri);


        return retCursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        final SQLiteDatabase db = mDBHelper.getWritableDatabase();

        int match  = sUriMatcher.match(uri);

        Uri returnedUri;
        switch (match)
        {

            case Movies:
long id = db.insert(contract.Favourite.TABLE_NAME,null,values);
if (id>0)
{
returnedUri = ContentUris.withAppendedId(contract.Favourite.CONTENT_URI,id);
}
else
throw new android.database.sqlite.SQLiteException("Failed To insert Row into"+uri);
                break;

default:
    throw  new UnsupportedOperationException("Unkonw Uri "+uri);

        }

        getContext().getContentResolver().notifyChange(uri,null);
        return returnedUri;


    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        final SQLiteDatabase db = mDBHelper.getWritableDatabase();

        int match = sUriMatcher.match(uri);

        int moviesDeleted;

        switch (match) {

            case Movies_With_Id:

                String id = uri.getPathSegments().get(1);

                moviesDeleted = db.delete(contract.Favourite.TABLE_NAME, "Id=?", new String[]{id});
                break;
            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        if (moviesDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }

        return moviesDeleted;

    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
