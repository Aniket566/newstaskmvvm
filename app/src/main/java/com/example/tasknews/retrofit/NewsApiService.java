package com.example.tasknews.retrofit;

import com.example.tasknews.model.NewsResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface NewsApiService {
    @GET("top-headlines")
    Call<NewsResponse> getTopHeadlines(@Query("topic") String topic, @Query("apikey") String apiKey);


    @GET("search")
    Call<NewsResponse> searchNews(@Query("q") String query, @Query("apikey") String apiKey);
}

