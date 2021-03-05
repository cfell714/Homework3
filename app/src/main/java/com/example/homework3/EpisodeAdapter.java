package com.example.homework3;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.ViewHolder>{

    private List<Episode> episodes;

    public EpisodeAdapter(List<Episode> episodes){
        this.episodes = episodes;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View episodeView = inflater.inflate(R.layout.item_episode, parent, false);
        ViewHolder viewHolder = new ViewHolder(episodeView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeAdapter.ViewHolder holder, int position) {
        //Log.d("message", episodes);

        Episode episode = episodes.get(position);
        Picasso.get().load(episode.getImage()).into(holder.imageView_episode);

        Log.d("message", episode.toString());


    }

    @Override
    public int getItemCount() {
        return episodes.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        ImageView imageView_episode;

        public ViewHolder(View itemView){
            super(itemView);

            imageView_episode = itemView.findViewById(R.id.imageView_episode);

        }

    }

}