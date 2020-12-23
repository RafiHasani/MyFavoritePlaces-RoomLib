package com.example.myfavoriteplaces;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "FavoriteLocations")

public class FavoriteLocation implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    @ColumnInfo(name = "Lat")
    private Double latitude;
    @NonNull
    @ColumnInfo(name = "Lng")
    private Double longitute;
    @NonNull
    @ColumnInfo(name = "Address")
    private String address;
    @ColumnInfo(name = "TimeStamp")
    private String timestamp;
    @ColumnInfo(name = "City")
    private String city;
    @ColumnInfo(name = "Country")
    private String country;

    public FavoriteLocation(Double latitude, Double longitute, String address, String timestamp, String city, String country) {
        this.latitude = latitude;
        this.longitute = longitute;
        this.address = address;
        this.timestamp = timestamp;
        this.city = city;
        this.country = country;
    }
    @Ignore
    public FavoriteLocation()
    {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitute() {
        return longitute;
    }

    public void setLongitute(Double longitute) {
        this.longitute = longitute;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }



}
