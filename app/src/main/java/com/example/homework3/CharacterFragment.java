package com.example.homework3;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cz.msebera.android.httpclient.Header;

public class CharacterFragment extends Fragment {

    View view;
    private TextView textView_name;
    private TextView textView_status;
    private TextView textView_species;
    private TextView textView_gender;
    private TextView textView_originName;
    private TextView textView_locationName;
    private ImageView imageView_character;
    private TextView textView_listEpisodes;
    private List<String> episode;


    protected static final String api_url = "https://rickandmortyapi.com/api/character/";
    protected static AsyncHttpClient client = new AsyncHttpClient();
 //   protected static AsyncHttpClient client_2 = new AsyncHttpClient();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_character, container, false);

        textView_name = view.findViewById(R.id.textView_name);
        textView_status = view.findViewById(R.id.textView_status);
        textView_species = view.findViewById(R.id.textView_species);
        textView_gender = view.findViewById(R.id.textView_gender);
        textView_originName = view.findViewById(R.id.textView_originName);
        textView_locationName = view.findViewById(R.id.textView_locationName);
        imageView_character = view.findViewById(R.id.imageView_character);
        textView_listEpisodes = view.findViewById(R.id.textView_listEpisodes);

        client.addHeader("Accept", "application/json");
        client.get(api_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody));

                try{
                    JSONObject json = new JSONObject(new String(responseBody));

                    // is this considered a static number
                    // like it will update if the api updates
                    // so is this cool???
                    // another question to ask prof chen

                    JSONObject count = json.getJSONObject("info");
                    String count_1 = count.getString("count");
                    int count_num = Integer.parseInt(count_1);

                    Random randomGenerator = new Random();
                    int randomInt = randomGenerator.nextInt(count_num) + 1;

                    String api_url_new = "https://rickandmortyapi.com/api/character/" + randomInt;
                    settingTextChar(api_url_new);

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

    public void settingTextChar(String api){

        client.addHeader("Accept", "application/json");
        client.get(api, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    JSONObject json = new JSONObject(new String(responseBody));

                    String name = json.getString("name");
                    textView_name.setText("Name: " + name);

                    String status = json.getString("status");
                    textView_status.setText("Status: " + status);

                    String species = json.getString("species");
                    textView_species.setText("Species: " + species);

                    String gender = json.getString("gender");
                    textView_gender.setText("Gender: " + gender);

                    JSONObject obj_origin = json.getJSONObject("origin");
                    String origin = obj_origin.getString("name");
                    textView_originName.setText("Orgin: " + origin);

                    JSONObject obj_location = json.getJSONObject("location");
                    String location = obj_location.getString("name");
                    textView_locationName.setText("Location: " + location);

                    String image = json.getString("image");
                    Picasso.get().load(image).into(imageView_character);

                    episode = new ArrayList<String>();

                    JSONArray arr_ep = json.getJSONArray("episode");
                    for (int i = 0; i < arr_ep.length(); i++) {
                        String obj = arr_ep.getString(i);
                        String obj_1 = obj.replace("https://rickandmortyapi.com/api/episode/", "");
                        episode.add(obj_1);
                    }

                    String episode_string = episode.toString();
                    episode_string = episode_string.substring(1, episode_string.length() - 1);
                    textView_listEpisodes.setText("Episode(s): " + episode_string);
                }catch(JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
    }
}
