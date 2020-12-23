package com.example.myfavoriteplaces;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;
import java.util.List;

@Dao
public interface FPlaceDAO {

    @Insert
    void insertFPlace(FavoriteLocation... favoriteLocations);

    @Query("Select * From FavoriteLocations")
    LiveData<List<FavoriteLocation>>getAll();

    @Query("SELECT * FROM FavoriteLocations WHERE id IN (:userIds)")
    List<FavoriteLocation> loadlistByid(int[] userIds);

    @Query("SELECT * FROM FavoriteLocations WHERE id=(:userId)")
    FavoriteLocation getById(int userId);

    @Query("SELECT id FROM FavoriteLocations")
    int[] getArrayofIds();

    @Delete
    int delete(FavoriteLocation favoriteLocations);

    @Update
    int updateNotes(FavoriteLocation favoriteLocations);

}
