package com.example.homework3;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private Button button_character;
    private Button button_episode;
    private Button button_location;
    private TextView textView_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button_character = findViewById(R.id.button_character);
        button_episode = findViewById(R.id.button_episode);
        button_location = findViewById(R.id.button_location);
        textView_title = findViewById(R.id.textView_title);

        button_character.setOnClickListener(v -> loadFragment(new CharacterFragment(), R.id.fragContainer));

        button_episode.setOnClickListener(v -> loadFragment(new EpisodeFragment(), R.id.fragContainer));

        button_location.setOnClickListener(v -> loadFragment(new LocationFragment(), R.id.fragContainer));

    }

    public void loadFragment(Fragment fragment, int id){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(id, fragment);
        fragmentTransaction.commit();
    }
}