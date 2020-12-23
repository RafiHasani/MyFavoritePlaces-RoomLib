package com.example.myfavoriteplaces;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class FPlacesAdapter extends RecyclerView.Adapter<FPlacesAdapter.FPHolder>{

    @Nullable
    private List<FavoriteLocation> favoriteLocations;
    @Nullable
    private View.OnClickListener onItemClickListener;

    public FPlacesAdapter(){
    }
    @NonNull
    @Override
    public FPHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView=LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view_temp,parent,false);
        return new FPHolder(itemView);
    }

    public void setFavoriteLocations(List<FavoriteLocation> fp)
    {
        this.favoriteLocations=fp;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(@NonNull FPHolder holder, int position) {
        holder.title.setText(favoriteLocations.get(position).getAddress());
        holder.desc.setText(favoriteLocations.get(position).getCity());
        holder.imgv.setImageResource(R.drawable.hearth);
    }

    @Override
    public int getItemCount() {
        int i = 0;
        if(favoriteLocations!=null){ i=favoriteLocations.size();
        }
        return i;
    }

    public void setOnItemClickListener(View.OnClickListener onItemClick)
    {
        onItemClickListener=onItemClick;
    }


    class FPHolder extends RecyclerView.ViewHolder {
        private TextView title;
        private TextView desc;
        private ImageView imgv;

        public FPHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            desc=itemView.findViewById(R.id.desc);
            imgv=itemView.findViewById(R.id.img);
            itemView.setTag(this);
            itemView.setOnClickListener(onItemClickListener);
        }
    }
}
