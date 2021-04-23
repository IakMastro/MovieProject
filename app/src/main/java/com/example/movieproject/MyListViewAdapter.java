package com.example.movieproject;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.movieproject.POJOs.MovieSearch;
import com.example.movieproject.databinding.ListViewBinding;

import java.util.List;

public class MyListViewAdapter extends ArrayAdapter<MovieSearch> {
    private int layout;
    private List<MovieSearch> list;

    public MyListViewAdapter(@NonNull Context context, int resource, @NonNull List<MovieSearch> objects) {
        super(context, resource, objects);
        this.layout = resource;
        this.list = objects;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(this.getContext());
            convertView = inflater.inflate(layout, parent, false);
            String title, year;
            MovieSearch info = list.get(position);
            if (info.getTitle() == null) {
                title = "No results found!";
                year = "";
            } else {
                title = info.getTitle();
                year = info.getYear();
            }

            ViewHolder vh = new ViewHolder();
            ListViewBinding binding = ListViewBinding.bind(convertView);
            binding.tvTitle.setText(title);
            binding.tvYear.setText(year);
            vh.title = binding.tvTitle;
            vh.year = binding.tvYear;
            vh.info = info;

            convertView.setTag(vh);

            convertView.setOnClickListener((c) -> {
                Intent i = new Intent(this.getContext(), MovieActivity.class);
                i.setAction(Intent.ACTION_VIEW);
                i.putExtra("id", vh.info.getImdbId());
                this.getContext().startActivity(i);
            });
        } else {

        }
        return convertView;
    }
}

class ViewHolder {
    TextView title;
    TextView year;
    MovieSearch info;
}