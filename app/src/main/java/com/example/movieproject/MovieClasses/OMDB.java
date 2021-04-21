package com.example.movieproject.MovieClasses;

import android.util.Log;

import com.example.movieproject.MovieClasses.MovieInfo;
import com.example.movieproject.MovieClasses.MovieJSON;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.ConnectException;
import java.util.ArrayList;

public class OMDB {
    private static final String TAG = OMDB.class.getSimpleName();
    private final String key;

    public OMDB(String key) {
        this.key = key;
    }

    public MovieInfo getMovieByID(String id) throws IllegalArgumentException, ConnectException, UnirestException {
        HttpResponse<JsonNode> response = Unirest.get("https://www.omdbapi.com/?i={id}&apikey={key}")
                .routeParam("id", id)
                .routeParam("key", key)
                .asJson();

        switch (response.getStatus()) {
            case 200:
                return new MovieInfo(response.getBody().getObject());
            case 502:
                throw new ConnectException("502 Bad Gateway");
            default:
                Log.e(TAG, response.getStatus() + " " + response.getStatusText());
                throw new IllegalArgumentException();
        }
    }

    public MovieJSON getMovieByTitle(String title) throws IllegalArgumentException, ConnectException, UnirestException {
        HttpResponse<JsonNode> response = Unirest.get("https://www.omdbapi.com/?t={title}&apikey={key}")
                .routeParam("title", title)
                .routeParam("key", key)
                .asJson();

        switch (response.getStatus()) {
            case 200:
                return new MovieJSON(response.getBody().getObject());
            case 502:
                throw new ConnectException("502 Bad Gateway");
            default:
                Log.e(TAG, response.getStatus() + " " + response.getStatusText());
                throw new IllegalArgumentException();
        }
    }

    public ArrayList<MovieInfo> searchAsMovieInfo(String query, String type) throws IllegalArgumentException, ConnectException, UnirestException, JSONException {
        query.replace(' ', '+');
        HttpResponse<JsonNode> response = Unirest.get("https://www.omdbapi.com/?s={query}&apikey={key}&type={type}")
                .routeParam("query", query)
                .routeParam("key", key)
                .routeParam("type", type)
                .asJson();

        switch (response.getStatus()) {
            case 200:
                JSONArray obj = response.getBody().getObject().getJSONArray("Search");
                ArrayList<MovieInfo> movies = new ArrayList<>();
                for (int i = 0; i < obj.length(); i++) {
                    movies.add(new MovieInfo((JSONObject) obj.get(i)));
                }

                return movies;
            case 502:
                throw new ConnectException("502 Bad Gateway");
            default:
                Log.e(TAG, response.getStatus() + " " + response.getStatusText());
                throw new IllegalArgumentException();
        }
    }
}