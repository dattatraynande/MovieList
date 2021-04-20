package com.example.movielist.retrofit;


import com.example.movielist.model.MovieData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("?")
    Call<MovieData> getMovies(@Query("s") String s, @Query("apikey") String apikey);

}
