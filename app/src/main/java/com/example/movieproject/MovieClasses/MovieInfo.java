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
    public URL poster;

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
        try {
            this.poster = new URL(get("Poster"));
        } catch (MalformedURLException e) {
            Log.w(TAG, "No poster URL, poster will be null.");
            this.poster = null;
        }
    }

    private String[] getAsArray(){
        return new String[]{title, };
    }
}