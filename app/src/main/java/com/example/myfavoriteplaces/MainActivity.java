package com.example.myfavoriteplaces;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;

import java.io.Serializable;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    Button add;
    RecyclerView recyclerView;
    List<FavoriteLocation> favoriteLocationList;
    FPlacesAdapter adapter;

    private View.OnClickListener onItemClickListener=new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            RecyclerView.ViewHolder viewHolder=(RecyclerView.ViewHolder)v.getTag();
            int position = viewHolder.getAdapterPosition();
            FavoriteLocation favoriteLocation=favoriteLocationList.get(position);
            Intent intent=new Intent(getApplicationContext(),MapFragment.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("Flocation", (Serializable) favoriteLocation);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add=findViewById(R.id.addfavoritplace);
        recyclerView=findViewById(R.id.rvlist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        adapter=new FPlacesAdapter();
        adapter.setOnItemClickListener(onItemClickListener);
        recyclerView.setAdapter(adapter);
        FPlacesRepo fPlacesRepo = new FPlacesRepo(this);
        fPlacesRepo.retrieveFPlacesTask().observe(this, new Observer<List<FavoriteLocation>>() {
            @Override
            public void onChanged(List<FavoriteLocation> favoriteLocations) {
                adapter.setFavoriteLocations(favoriteLocations);
              favoriteLocationList=favoriteLocations;
            }
        });
    }

    public void addplace(View v)
    {
        Intent intent=new Intent(this,MapFragment.class);
        startActivity(intent);
    }
}
