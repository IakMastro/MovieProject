package com.example.movieproject.MovieClasses;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MovieJSON {
    private static final String TAG = MovieJSON.class.getSimpleName();
    public final JSONObject obj;

    public MovieJSON(){
        JSONObject obj1;

        try {
            JSONObject temp = new JSONObject();
            temp.put("Title","Nothing Found");
            temp.put("Year","");
            obj1 = temp;
        } catch (JSONException e) {//surely this can never trigger
            Log.e(TAG, "Error during the creation of placeholder JSON.");
            obj1 = null;
        }

        this.obj = obj1;
    }

    public MovieJSON(JSONObject obj) {
        this.obj = obj;
    }

    public String get(String value) {
        try{
            Object s = this.obj.get(value);
            return s == null ? "null" : s.toString();
        } catch (JSONException e) {
            Log.w(TAG, e.getMessage());
            return "null";
        }
    }

    public ArrayList<String> find(String value) throws JSONException {
        JSONArray ja = obj.getJSONArray("Search");
        ArrayList<String> list = new ArrayList<>();

        for(int i=0; i<ja.length(); i++) list.add(get(value, (JSONObject)ja.get(i)));

        return list;
    }

    private String get(String value, JSONObject obj) {
        try{
            Object s = obj.get(value);
            return s == null ? "null" : s.toString();
        } catch (JSONException e) {
            Log.w(TAG, e.getMessage());
            return "null";
        }
    }
}