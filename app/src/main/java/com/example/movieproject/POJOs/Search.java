package com.example.movieproject.POJOs;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Search {
    @SerializedName("Search")
    private List<MovieSearch> search;
    @SerializedName("TotalResults")
    private String totalResults;
    @SerializedName("Response")
    private String response;

    public List<MovieSearch> getSearch() {
        return search;
    }

    public String getTotalResults() {
        return totalResults;
    }

    public String getResponse() {
        return response;
    }
}
