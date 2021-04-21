package com.example.movieproject;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movieproject.MovieClasses.MovieInfo;
import com.example.movieproject.MovieClasses.MovieJSON;
import com.example.movieproject.databinding.MovieActivityBinding;
import com.squareup.picasso.Picasso;

import java.net.URISyntaxException;
import java.util.concurrent.ExecutionException;

public class MovieActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_activity);
        MovieActivityBinding binding = MovieActivityBinding.bind(findViewById(R.id.root_movie));

        String id = getIntent().getStringExtra("id");
        try {
            MovieInfo info = new RetrieveMovieInfoTask()
                .execute("id", id)
                .get()
                .get(0);
            binding.tvTitleMovie.setText(info.title);
            binding.tvYearMovie.setText(info.year);
            Picasso.get().load(info.poster).into(binding.imagePoster);
            binding.tvPlotInfo.setText(info.get("Plot"));
            binding.tvRating.setText("IMDB Rating: "+info.get("imdbRating")
                    +" Metascore: "+info.get("Metascore"));
        } catch (ExecutionException e) {
            Log.e(TAG,"Plot info: "+e.getMessage());
        } catch (InterruptedException e) {
            Log.e(TAG,"Plot info: "+e.getMessage());
        }
    }
}
