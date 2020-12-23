package com.example.myfavoriteplaces;

import android.os.AsyncTask;

public class DeleteFPlace extends AsyncTask<FavoriteLocation,Void,Void> {
    private FPlaceDAO mFPlaceDao;

    public void DeleteFPlace(FPlaceDAO dao) {
        mFPlaceDao = dao;
    }

    @Override
    protected Void doInBackground(FavoriteLocation... favoriteLocations) {
// do in background
        return null;
    }
}
