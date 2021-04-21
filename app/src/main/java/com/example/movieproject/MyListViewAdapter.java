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

import com.example.movieproject.MovieClasses.MovieInfo;
import com.example.movieproject.databinding.ListViewBinding;

import java.util.List;

public class MyListViewAdapter extends ArrayAdapter<MovieInfo> {
    private int layout;
    private List<MovieInfo> list;

    public MyListViewAdapter(@NonNull Context context, int resource, @NonNull List<MovieInfo> objects) {
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
            MovieInfo info = list.get(position);
            ViewHolder vh = new ViewHolder();
            ListViewBinding binding = ListViewBinding.bind(convertView);
            binding.tvTitle.setText(info.title);
            binding.tvYear.setText(info.year);
            vh.title = binding.tvTitle;
            vh.year = binding.tvYear;
            vh.info = info;

            convertView.setTag(vh);

            //if we need the click animation, put it on list view in MainActivity
            convertView.setOnClickListener((c) -> {
                //TODO Change view to movie
                //Toast.makeText(this,""+ ,Toast.LENGTH_SHORT).show();
                System.out.println(vh.info.imdbId);
                Intent i = new Intent(this.getContext(), MovieActivity.class);
                i.setAction(Intent.ACTION_VIEW);
                i.putExtra("id", vh.info.imdbId);
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
    MovieInfo info;
}