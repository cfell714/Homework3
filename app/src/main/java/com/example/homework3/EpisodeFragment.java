package com.example.homework3;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class EpisodeFragment extends Fragment {

    View view;
    private TextView textView_nameEpisode;
    private TextView textView_airDate;
    private TextView textView_episodeEpisode;
    private Button button_information;


    private RecyclerView recyclerView_episode;
    private ArrayList<Episode> episodes;
    private List<String> all_ep;


    protected static final String api_url = "https://rickandmortyapi.com/api/episode/";
    protected static AsyncHttpClient client = new AsyncHttpClient();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_episode, container, false);

        textView_nameEpisode = view.findViewById(R.id.textView_nameEpisode);
        textView_airDate = view.findViewById(R.id.textView_airDate);
        textView_episodeEpisode = view.findViewById(R.id.textView_episodeEpisode);
        button_information = view.findViewById(R.id.button_information);

        recyclerView_episode = view.findViewById(R.id.recyclerView_episode);
        episodes = new ArrayList<>();
        all_ep = new ArrayList<String>();


        EpisodeAdapter adapter = new EpisodeAdapter(episodes);
        recyclerView_episode.setAdapter(adapter);
        recyclerView_episode.setLayoutManager(new LinearLayoutManager(getActivity()));


        client.addHeader("Accept", "application/json");
        client.get(api_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody));
                try{
                    JSONObject json = new JSONObject(new String(responseBody));

                    JSONObject count = json.getJSONObject("info");
                    String count_1 = count.getString("count");
                    int count_num = Integer.parseInt(count_1);

                    Random randomGenerator = new Random();
                    int randomInt = randomGenerator.nextInt(count_num) + 1;

                    String api_url_new = "https://rickandmortyapi.com/api/episode/" + randomInt;
                    settingTextEp(api_url_new);


                }catch(JSONException e){
                    e.printStackTrace();
                }

            }
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });

        return view;
    }

    public void settingTextEp(String api){
        client.addHeader("Accept", "application/json");
        client.get(api, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody));

                try {
                    JSONObject json = new JSONObject(new String(responseBody));

                    String name = json.getString("name");
                    textView_nameEpisode.setText("Name: " + name);

                    String episode_text = json.getString("episode");
                    textView_episodeEpisode.setText("Episode: "+ episode_text);

                    String airDate = json.getString("air_date");
                    textView_airDate.setText("Air Date: " + airDate);



                    JSONArray characters = json.getJSONArray("characters");

                    for(int x = 0; x < characters.length(); x ++){
                        String temp = characters.getString(x);
                        all_ep.add(temp);
                    }
                    getRecycler(all_ep);


                    String url_temp = "https://rickandmorty.fandom.com/wiki/" + name;

                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url_temp));
                    PendingIntent pendingIntent = PendingIntent.getActivity(getActivity(), 1, intent, PendingIntent.FLAG_UPDATE_CURRENT);

                    Log.d("Apps/Activities", intent.resolveActivity(getActivity().getPackageManager()).toString());

                    NotificationCompat.Builder builder = new NotificationCompat.Builder(getActivity(), "example")
                            .setSmallIcon(R.drawable.greenlightsaber)
                            .setContentTitle(episode_text + ": " + name)
                            .setStyle(new NotificationCompat.BigTextStyle().bigText("To read more information about Episode" + episode_text + ", please visit: https://rickandmorty.fandom.com/wiki/" + name))
                            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                            .setContentIntent(pendingIntent)
                            .setAutoCancel(true);

                    NotificationManagerCompat notificationManager = NotificationManagerCompat.from(getActivity());

                    button_information.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            System.out.println("Clicked");
                            notificationManager.notify(100, builder.build());
                        }
                    });

                }catch(JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
            }
        });
    }



    public void getRecycler(List<String> new_list){

        for(int k = 0; k < new_list.size(); k++){


            client.addHeader("Accept", "application/json");
            client.get(new_list.get(k), new AsyncHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                    Log.d("api response", new String(responseBody));
                    try {
                        JSONObject json = new JSONObject(new String(responseBody));
                        System.out.println("IM TRYING SOMETHING " + json.getString("image"));
                        String image = json.getString("image");
                        System.out.println("THIS IS ME WOKRING ON GETTING THE IMAGE " + image);
                        Episode episode = new Episode(image);
                        episodes.add(episode);
                        EpisodeAdapter adapter = new EpisodeAdapter(episodes);
                        recyclerView_episode.setAdapter(adapter);
                        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
                        recyclerView_episode.setLayoutManager(linearLayoutManager);
                        System.out.println("ME TRYING TO FIGURE THIS OUT" + episodes);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                }
            });
        }

    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "name";
            String description = "dscription";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("example", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getActivity().getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
