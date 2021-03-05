package com.example.homework3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.ViewHolder> {

    private List<Location> locations;

    public LocationAdapter(List<Location> locations){
        this.locations = locations;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View locationView = inflater.inflate(R.layout.item_location, parent, false);
        ViewHolder viewHolder = new ViewHolder(locationView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        Location location = locations.get(position);
        holder.textView_nameLocation.setText(location.getName());
        holder.textView_typeLocation.setText("Type: " + location.getType());
        holder.textView_dimensionLocation.setText("Dimension: " + location.getDescription());

    }

    @Override
    public int getItemCount() {
        return locations.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textView_nameLocation;
        TextView textView_typeLocation;
        TextView textView_dimensionLocation;

        public ViewHolder(View itemView){
            super(itemView);

            textView_nameLocation = itemView.findViewById(R.id.textView_nameLocation);
            textView_typeLocation = itemView.findViewById(R.id.textView_typeLocation);
            textView_dimensionLocation = itemView.findViewById(R.id.textView_dimensionLocation);

        }

    }

}
