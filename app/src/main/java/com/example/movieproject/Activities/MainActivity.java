package com.example.movieproject.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movieproject.Stuff;
import com.example.movieproject.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    public static Stuff stuff;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        stuff = new Stuff(this.getBaseContext());

        binding.bLogin.setOnClickListener(c -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });

        binding.bSignUp.setOnClickListener(c -> {
            Intent intent = new Intent(getApplicationContext(), SignUpActivity.class);
            startActivity(intent);
        });

        binding.bPlanList.setOnClickListener(c -> {
            //TODO new intent that leads to the planned (to watch) list
        });

        binding.bWatchedList.setOnClickListener(c -> {
            //TODO new intent that leads to the watched list
        });

        binding.bSearchMain.setOnClickListener(c -> {
            Intent i = new Intent(this, SearchActivity.class);
            i.setAction(Intent.ACTION_VIEW);
            this.startActivity(i);
        });
    }
}
