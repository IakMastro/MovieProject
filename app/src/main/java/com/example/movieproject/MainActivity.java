package com.example.movieproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.movieproject.POJOs.MovieSearch;
import com.example.movieproject.POJOs.Search;
import com.example.movieproject.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.movieproject.Stuff.API_KEY;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String[] spinner_values = new String[]{"Movie", "Game", "Series"};
    public static OmdbApi api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinner_values);
        binding.spinnerType.setAdapter(aa);

        initApi();

        binding.bSearch.setOnClickListener(c -> {
            searchOnClick(binding.lvResults,
                    binding.etQuery.getText().toString().replace(' ', '+'),
                    binding.spinnerType.getSelectedItem().toString());
        });
    }

    private void initApi() {
        String baseUrl = "https://www.omdbapi.com/";
        Retrofit r = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        api = r.create(OmdbApi.class);
    }

    private void searchOnClick(ListView lv, String query, String type) {
        Call<Search> call = api.getSearch(API_KEY, query, type);
        call.enqueue(new Callback<Search>() {
            @Override
            public void onResponse(Call<Search> call, Response<Search> response) {
                if (!response.isSuccessful()) {
                    Log.e(TAG, "Unsuccessful response");
                    return;
                }
                List<MovieSearch> MovieSearches = response.body().getSearch();
                if (MovieSearches == null) {
                    //add an item so that a message can be added to the list view
                    MovieSearches = new ArrayList<>();
                    MovieSearches.add(new MovieSearch());
                }
                MyListViewAdapter aa2 = new MyListViewAdapter(lv.getContext(),
                        R.layout.list_view,
                        MovieSearches);
                lv.setAdapter(aa2);
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }
}