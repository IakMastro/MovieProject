package com.example.movieproject;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.Properties;

public class Stuff {
    private static final String TAG = Stuff.class.getSimpleName();
    private static final String FILENAME = "data.txt";
    public static String OMDB_API_KEY;

    public Stuff(Context ctx) {
        Properties p = new Properties();
        try {
            p.load(ctx.getAssets().open(FILENAME));
            OMDB_API_KEY = p.getProperty("movie_api_key");
        } catch (IOException e) {
            Log.e(TAG, "No such file " + FILENAME);
        }
    }
}
