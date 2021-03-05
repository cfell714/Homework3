package com.example.homework3;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class LocationFragment extends Fragment {

    View view;
    private RecyclerView recyclerView;
    private ArrayList<Location> locations;
    private List<String> all_name;
    private List<String> all_type;
    private List<String> all_dimension;

    protected static final String api_url = "https://rickandmortyapi.com/api/location";
    protected static AsyncHttpClient client = new AsyncHttpClient();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_location, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        locations = new ArrayList<>();
        all_name = new ArrayList<String>();
        all_type = new ArrayList<String>();
        all_dimension = new ArrayList<String>();

        LinearLayoutManager llm = new LinearLayoutManager(getActivity());
        llm.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(llm);
        LocationAdapter adapter = new LocationAdapter(locations);
        recyclerView.setAdapter(adapter);


        client.addHeader("Accept", "application/json");
        client.get(api_url, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                Log.d("api response", new String(responseBody));

                try{
                    JSONObject json = new JSONObject(new String(responseBody));
                    JSONArray arr_json = json.getJSONArray("results");

                    for(int i = 0; i < arr_json.length(); i++){
                        JSONObject obj = arr_json.getJSONObject(i);

                        String name = obj.getString("name");
                        all_name.add(name);

                        String type = obj.getString("type");
                        all_type.add(type);

                        String dimension = obj.getString("dimension");
                        all_dimension.add(dimension);
                    }


                    for (int k = 0; k < arr_json.length(); k++){
                        Location location = new Location(all_name.get(k),
                                all_type.get(k),
                                all_dimension.get(k));
                        locations.add(location);
                    }

                    LocationAdapter adapter = new LocationAdapter(locations);
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

                }catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("api error", new String(responseBody));
            }
        });
        return view;
    }

}
