package com.example.myfavoriteplaces;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {FavoriteLocation.class}, version = 1,exportSchema = false)
public abstract class FavoritePlaceDB extends RoomDatabase {

        public static final String DATABASE_NAME = "FavoritePlaces_db";

        private static FavoritePlaceDB instance;

        static FavoritePlaceDB getInstance(final Context context){
            if(instance == null){
                instance = Room.databaseBuilder(
                        context.getApplicationContext(),
                        FavoritePlaceDB.class,
                        DATABASE_NAME
                ).build();
            }
            return instance;
        }
        public abstract FPlaceDAO getFPDao();
}
