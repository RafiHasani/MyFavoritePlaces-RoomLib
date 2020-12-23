package com.example.myfavoriteplaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.Observer;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.DrawableWrapper;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.LocationSource;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class MapFragment extends AppCompatActivity implements OnMapReadyCallback,GoogleMap.OnMapLongClickListener {
    GoogleMap gmap;
    SupportMapFragment mapFragment;
    LocationManager locationManager;
    LocationListener locationListener;
    Geocoder info;
    ImageView imageButton;
    List<FavoriteLocation> FPlacelist;


    public void onRequestPermissionsResult(int requestCode,String[] permissions,int []grantResults){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED)
        {
            if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED);
            {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_fragment);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        gmap=googleMap;
        gmap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        gmap.clear();

        Intent intent=getIntent();
        if(intent.hasExtra("Flocation"))
        {
        FavoriteLocation favoriteLocation=new FavoriteLocation();
            Bundle bundle = intent.getExtras();
            favoriteLocation = (FavoriteLocation) bundle.getSerializable("Flocation");
            LatLng latLng=new LatLng(favoriteLocation.getLatitude(),favoriteLocation.getLongitute());
            MarkerOptions marker=new MarkerOptions();
            marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.hearth));
            marker.position(latLng);
            gmap.addMarker(marker);
            gmap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10));
        }
        locationManager=(LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        gmap.setMyLocationEnabled(true);
        info=new Geocoder(getApplicationContext(), Locale.getDefault());
        imageButton=findViewById(R.id.imgb);
        imageButton.setVisibility(View.VISIBLE);
        gmap.setOnMapLongClickListener((GoogleMap.OnMapLongClickListener) this);

        locationListener= new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                try {
                    LatLng userlocation = new LatLng(location.getLatitude(), location.getLongitude());
                    gmap.addCircle(new CircleOptions().center(new LatLng(location.getLatitude(), location.getLongitude())).radius(500).strokeColor(android.R.color.black).fillColor(android.R.color.background_light).strokeWidth(5));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            };
        };
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }
        else if(ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)== PackageManager.PERMISSION_GRANTED)
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,0,0,locationListener);
        }
    }

    @Override
    public void onMapLongClick(LatLng latLng) {
        MarkerOptions marker=new MarkerOptions();
        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.hearth));
        marker.position(latLng);
        gmap.addMarker(marker);
        addtofavorite(latLng);
    }

    public void addtofavorite(LatLng latLng) {
       try {
           String []add={null};
           List<Address> addresses = info.getFromLocation(latLng.latitude, latLng.longitude, 1);
           if(addresses.get(0).getAddressLine(0)!=null) {
               FavoriteLocation temp=new FavoriteLocation();
               temp.setLatitude(latLng.latitude);
               temp.setLongitute(latLng.longitude);
               if(addresses.get(0).getCountryName()!=null) {
                   temp.setCountry(addresses.get(0).getCountryName());
               }
               if(addresses.get(0).getAdminArea()!=null) {
                   temp.setCity(addresses.get(0).getAdminArea());
               }
               if(addresses.get(0).getAddressLine(0)!=null) {
                   temp.setAddress(addresses.get(0).getAddressLine(0));
               }
               temp.setTimestamp(String.valueOf(new Date().getTime()));
               AddFPlacestoDB(temp);

           }
       }catch (Exception e)
       {
           e.printStackTrace();
       }
    }

    public void AddFPlacestoDB(FavoriteLocation favoriteLocation) {
        FPlacesRepo fPlacesRepo = new FPlacesRepo(this);
        fPlacesRepo.insertFPlacesTask(favoriteLocation);
        Toast.makeText(this,favoriteLocation.getCity().toString(),Toast.LENGTH_SHORT).show();
    }

    public void loadAllFPlaces(View view)
    {
        RetriveFPlace retriveFPlace = new RetriveFPlace(new FPlacesRepo(this));
        retriveFPlace.execute();
    }


    public class RetriveFPlace extends AsyncTask<FavoriteLocation,Void, List<FavoriteLocation>> {
        private FPlacesRepo mFPlaceRepo;
        boolean clicked;

        public RetriveFPlace(FPlacesRepo Repo) {
            mFPlaceRepo = Repo;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected List<FavoriteLocation> doInBackground(FavoriteLocation... favoriteLocations) {
            return mFPlaceRepo.getlistbyids();
        }

        @Override
        protected void onPostExecute(List<FavoriteLocation> favoriteLocations) {
            super.onPostExecute(favoriteLocations);
            FPlacesRepo fPlacesRepo = new FPlacesRepo(getApplicationContext());
            List<FavoriteLocation> listFPlaces = favoriteLocations;
            LatLng latLng;
            for (int i = 0; i < listFPlaces.size(); i++) {
                   latLng = new LatLng(listFPlaces.get(i).getLatitude(), listFPlaces.get(i).getLongitute());
                    MarkerOptions marker=new MarkerOptions();
                    marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.hearth));
                    marker.position(latLng);
                    gmap.addMarker(marker);
            }
        }
    }
}
