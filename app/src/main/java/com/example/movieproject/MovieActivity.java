package com.example.movieproject;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movieproject.MovieClasses.MovieInfo;
import com.example.movieproject.databinding.MovieActivityBinding;

public class MovieActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        MovieActivityBinding binding = MovieActivityBinding.inflate(getLayoutInflater());

        ViewHolder vh = (ViewHolder) getIntent().getExtras().get("viewholder");
        MovieInfo info = vh.info;
        binding.tvTitleMovie.setText(info.title);
    }
}
