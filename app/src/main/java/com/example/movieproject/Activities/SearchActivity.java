package com.example.movieproject.Activities;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.movieproject.MyListViewAdapter;
import com.example.movieproject.OmdbApi;
import com.example.movieproject.POJOs.MovieSearch;
import com.example.movieproject.POJOs.Search;
import com.example.movieproject.R;
import com.example.movieproject.databinding.ActivitySearchBinding;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.movieproject.Stuff.API_KEY;
//TODO tv series years can be XXXX-XXXX, make it so it can fit on the activity, somehow
public class SearchActivity extends AppCompatActivity {
    private static final String TAG = SearchActivity.class.getSimpleName();
    private static final String[] spinner_values = new String[]{"Movie", "Game", "Series"};
    public static OmdbApi api;
    private static int pageCount, pageMax;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivitySearchBinding binding = ActivitySearchBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinner_values);
        binding.spinnerType.setAdapter(aa);

        initApi();

        binding.etQuery.setOnEditorActionListener((view, id, event)->{
            if(id == EditorInfo.IME_ACTION_DONE||id == EditorInfo.IME_ACTION_GO||id== EditorInfo.IME_ACTION_NEXT){
                pageCount = 1;
                searchOnClick(binding.lvResults,
                        binding.etQuery.getText().toString().replace(' ', '+'),
                        binding.spinnerType.getSelectedItem().toString());
                binding.ibPrevious.setVisibility(View.INVISIBLE);
                binding.ibNext.setVisibility(View.VISIBLE);
                hideKeyboard(this);
                return true;
            }
            return false;
        });

        binding.bSearch.setOnClickListener(c -> {
            pageCount = 1;
            searchOnClick(binding.lvResults,
                    binding.etQuery.getText().toString().replace(' ', '+'),
                    binding.spinnerType.getSelectedItem().toString());
            binding.ibPrevious.setVisibility(View.INVISIBLE);
            binding.ibNext.setVisibility(View.VISIBLE);
            hideKeyboard(this);
        });

        binding.ibPrevious.setOnClickListener(c -> {
            if (pageCount > 1) {
                pageCount--;
                searchOnClick(binding.lvResults,
                        binding.etQuery.getText().toString().replace(' ', '+'),
                        binding.spinnerType.getSelectedItem().toString());
                binding.ibNext.setVisibility(View.VISIBLE);
            }
            if (pageCount == 1) binding.ibPrevious.setVisibility(View.INVISIBLE);
        });

        binding.ibNext.setOnClickListener(c -> {
            if (pageCount < pageMax) {
                pageCount++;
                searchOnClick(binding.lvResults,
                        binding.etQuery.getText().toString().replace(' ', '+'),
                        binding.spinnerType.getSelectedItem().toString());
                binding.ibPrevious.setVisibility(View.VISIBLE);
            }
            if (pageCount == pageMax) binding.ibNext.setVisibility(View.INVISIBLE);
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
        Call<Search> call = api.getSearch(API_KEY, query, type, pageCount);
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

                pageMax = (int) Math.ceil(Integer.parseInt(response.body().getTotalResults()) / 10.0);
            }

            @Override
            public void onFailure(Call<Search> call, Throwable t) {
                Log.e(TAG, t.getMessage());
            }
        });
    }

    //source https://gist.github.com/lopspower/6e20680305ddfcb11e1e
    private static void hideKeyboard(Activity activity) {
        View view = activity.findViewById(android.R.id.content);
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}