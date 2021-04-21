package com.example.movieproject.MovieClasses;

import android.util.Log;

import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

public class MovieInfo extends MovieJSON{
    private static final String TAG = MovieInfo.class.getSimpleName();
    public String title;
    public String year;
    public String imdbId;
    public String type;
    public String poster;

    public MovieInfo(){
        super();
        setup();
    }

    public MovieInfo(JSONObject obj) {
        super(obj);
        setup();
    }

    private void setup(){
        this.title = get("Title");
        this.year = get("Year");
        this.imdbId = get("imdbID");
        this.type = get("Type");
        this.poster = get("Poster");
    }

    @Override
    public String toString() {
        return "MovieInfo{" +
                "title='" + title + '\'' +
                ", year='" + year + '\'' +
                ", imdbId='" + imdbId + '\'' +
                ", type='" + type + '\'' +
                ", poster='" + poster + '\'' +
                '}';
    }
}