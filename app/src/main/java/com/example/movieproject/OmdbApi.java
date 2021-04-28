package com.example.movieproject;


import com.example.movieproject.POJOs.MovieId;
import com.example.movieproject.POJOs.Search;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface OmdbApi {
    @GET("/")
    Call<Search> getSearch(@Query("apikey") String apikey,
                           @Query("s") String query,
                           @Query("type") String type,
                           @Query("page") int page);

    @GET("/")
    Call<MovieId> getMovieFromId(@Query("apikey") String apikey,
                                 @Query("i") String id);
}
