package com.example.movieproject;

import android.os.AsyncTask;
import android.util.Log;

import com.example.movieproject.MovieClasses.MovieInfo;
import com.example.movieproject.MovieClasses.MovieJSON;
import com.example.movieproject.MovieClasses.OMDB;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONException;

import java.net.ConnectException;
import java.util.ArrayList;

import static com.example.movieproject.Stuff.API_KEY;

public class RetrieveMovieInfoTask extends AsyncTask<String, Void, ArrayList<MovieInfo>> {
    private static final String TAG = RetrieveMovieInfoTask.class.getSimpleName();
    final OMDB omdb = new OMDB(API_KEY);

    @Override
    protected ArrayList<MovieInfo> doInBackground(String... strings) {
        ArrayList<MovieInfo> movies;
        try {
            switch(strings[0]){
                case "search":
                    if(strings.length<3)
                        movies = omdb.searchAsMovieInfo(strings[1], "");
                    else
                        movies = omdb.searchAsMovieInfo(strings[1], strings[2]);
                    return movies;
                case "id":
                    MovieJSON movie = omdb.getMovieByID(strings[1]);
                    //System.out.println("Title "+movie.get("Title"));
                    //movies.add(movie);
                    break;
            }
        } catch (ConnectException e) {
            Log.e(TAG,e.getMessage());
        } catch (UnirestException e) {//Cant Access Internet
            Log.e(TAG,e.getMessage());
        } catch (JSONException e) {//Nothing found
            Log.w(TAG,e.getMessage());
            return new ArrayList<MovieInfo>(){{
                add(new MovieInfo());
            }};
        }
        return null;
    }
}