package com.example.movieproject;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.example.movieproject.databinding.ActivityMainBinding;

import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static String type;
    private static final String[] spinner_values = new String[]{"Movie", "Game", "Series"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        ArrayAdapter<String> aa = new ArrayAdapter<String>(this, R.layout.support_simple_spinner_dropdown_item, spinner_values);
        binding.spinnerType.setAdapter(aa);

        binding.spinnerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = parent.getItemAtPosition(position).toString();
                if (selection.equals("")) {
                    onNothingSelected(parent);
                } else {
                    type = selection;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        binding.bSearch.setOnClickListener(c -> {
            try {
                MyListViewAdapter aa2 = new MyListViewAdapter(binding.lvResults.getContext(),
                        R.layout.list_view,
                        new RetrieveMovieInfoTask().execute("search", binding.etQuery.getText().toString(), type).get());
                binding.lvResults.setAdapter(aa2);
            } catch (InterruptedException e) {
                Log.e(TAG, e.getMessage());
            } catch (ExecutionException e) {
                Log.e(TAG, e.getMessage());
            }
        });
    }
}