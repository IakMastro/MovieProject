package com.example.movieproject;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.movieproject.Activities.MovieActivity;
import com.example.movieproject.POJOs.MovieSearch;
import com.example.movieproject.databinding.GridViewBinding;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MyGridViewAdapter extends ArrayAdapter<MovieSearch> {
    private int layout;
    private List<MovieSearch> list;
    private LayoutInflater inflater;

    public MyGridViewAdapter(@NonNull Context context, int resource, @NonNull List<MovieSearch> objects) {
        super(context, resource, objects);
        this.layout = resource;
        this.list = objects;

        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(layout, parent, false);
        }

        GridViewBinding binding = GridViewBinding.bind(convertView);
        GridViewHolder gvh = new GridViewHolder();
        try {
            String title_year;
            MovieSearch info = list.get(position);

            title_year = info.getTitle() + " (" + info.getYear() + ")";

            binding.tvTitleYear.setText(title_year);
            Picasso.get()
                    .load(info.getPoster())
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_placeholder)
                    .fit()
                    .tag(info.getPoster())
                    .into(binding.imgGvPoster);

            gvh.title_year = binding.tvTitleYear;
            gvh.image = binding.imgGvPoster;
            gvh.info = info;

            convertView.setOnClickListener((c) -> {
                Intent i = new Intent(this.getContext(), MovieActivity.class);
                i.setAction(Intent.ACTION_VIEW);
                i.putExtra("id", gvh.info.getImdbId());
                this.getContext().startActivity(i);
            });
        } catch (Exception exc) {
            binding.tvTitleYear.setText("No results found!");
            binding.imgGvPoster.setImageResource(R.drawable.ic_placeholder);
            gvh.image = binding.imgGvPoster;
        }

        convertView.setTag(gvh);
        return convertView;
    }
}

class GridViewHolder {
    TextView title_year;
    ImageView image;
    MovieSearch info;
}
