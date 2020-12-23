package com.example.myfavoriteplaces;
import android.content.Context;

import androidx.lifecycle.LiveData;

import java.util.List;

public class FPlacesRepo {

    private FavoritePlaceDB mfpdb;

    public FPlacesRepo(Context context) {
        mfpdb = FavoritePlaceDB.getInstance(context);
    }

    public LiveData<List<FavoriteLocation>> retrieveFPlacesTask() {
        return (LiveData<List<FavoriteLocation>>) mfpdb.getFPDao().getAll();
    }

    public List<FavoriteLocation> getlistbyids() {
        return (List<FavoriteLocation>) mfpdb.getFPDao().loadlistByid(mfpdb.getFPDao().getArrayofIds());
    }

    public FavoriteLocation getbyid(int id) {
        return (FavoriteLocation) mfpdb.getFPDao().getById(id);
    }


    public void insertFPlacesTask(FavoriteLocation favoriteLocation){
        new InsertFPlace(mfpdb.getFPDao()).execute(favoriteLocation);
    }

}