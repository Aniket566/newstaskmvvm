package com.example.tasknews.repo;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.tasknews.model.NewsArticle;
import com.example.tasknews.model.NewsResponse;
import com.example.tasknews.retrofit.NewsApiService;
import com.example.tasknews.retrofit.RetrofitClient;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    private static final String API_KEY = "d159657cbef656c4437d3b953b65dcd4";
    private final NewsApiService apiService;

    public NewsRepository() {
        apiService = RetrofitClient.getClient("https://gnews.io/api/v4/").create(NewsApiService.class);
    }

    public LiveData<List<NewsArticle>> getTopHeadlines() {
        MutableLiveData<List<NewsArticle>> data = new MutableLiveData<>();
        apiService.getTopHeadlines("world", API_KEY).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                data.setValue(response.body().getArticles());
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }

    public LiveData<List<NewsArticle>> searchNews(String query) {
        MutableLiveData<List<NewsArticle>> data = new MutableLiveData<>();
        apiService.searchNews(query, API_KEY).enqueue(new Callback<NewsResponse>() {
            @Override
            public void onResponse(Call<NewsResponse> call, Response<NewsResponse> response) {
                data.setValue(response.body().getArticles());
            }

            @Override
            public void onFailure(Call<NewsResponse> call, Throwable t) {
                data.setValue(null);
            }
        });
        return data;
    }
}
