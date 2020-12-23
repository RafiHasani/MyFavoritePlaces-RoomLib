package com.example.myfavoriteplaces;

import android.os.AsyncTask;

public class InsertFPlace extends AsyncTask<FavoriteLocation,Void,Void> {

    private FPlaceDAO mFPlaceDao;

    public InsertFPlace(FPlaceDAO dao) {
        mFPlaceDao = dao;
    }

    protected Void doInBackground(FavoriteLocation... favoriteLocations) {
        mFPlaceDao.insertFPlace(favoriteLocations);
        return null;
    }
}
