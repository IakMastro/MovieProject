package com.example.movieproject.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.movieproject.POJOs.MovieId;
import com.example.movieproject.R;
import com.example.movieproject.Stuff;
import com.example.movieproject.databinding.ActivityMovieBinding;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.movieproject.activities.SearchActivity.api;

public class MovieActivity extends AppCompatActivity {
    private static final String TAG = SearchActivity.class.getSimpleName();
    private static MovieId info;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie);
        ActivityMovieBinding binding = ActivityMovieBinding.bind(findViewById(R.id.root_movie));

        String id = getIntent().getStringExtra("id");
        Call<MovieId> call = api.getMovieFromId(Stuff.OMDB_API_KEY, id);
        call.enqueue(new Callback<MovieId>() {
            @Override
            public void onResponse(Call<MovieId> call, Response<MovieId> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Unsuccessful response");
                    return;
                }
                info = response.body();
                binding.tvTitleMovie.setText(info.getTitle());
                binding.tvYearMovie.setText(info.getYear());
                Picasso.get().load(info.getPoster()).into(binding.imagePoster);
                binding.tvPlotInfo.setText(info.getPlot());
                binding.tvRatingImdb.setText("IMDB Rating: " + info.getImdbRating());
                binding.tvRatingMetascore.setText(" Metascore: " + info.getMetascore());
                try {
                    binding.tvRatingMetascore.setTextColor
                            (Integer.parseInt(info.getMetascore()) > 60 ? Color.GREEN //positive
                                    : (Integer.parseInt(info.getMetascore()) >= 40) ? Color.parseColor("#ffcd34") //mixed
                                    : Color.RED); //negative
                } catch (NumberFormatException exc) {
                    binding.tvRatingMetascore.setTextColor(Color.GRAY);
                }
                binding.tvGenresInfo.setText(info.getGenre());
            }

            @Override
            public void onFailure(Call<MovieId> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });

        binding.bWatched.setOnClickListener(c -> {
            addToWatched(info);
        });

        binding.fabWatched.setOnClickListener(c -> {
            addToWatched(info);
        });

        binding.bToWatch.setOnClickListener(c -> {
            addToPlanned(info);
        });
    }

    //TODO static method of class that interacts with the database
    private void addToPlanned(MovieId info) {

    }

    //TODO static method of class that interacts with the database
    private void addToWatched(MovieId info) {
    }
}