package com.example.tasknews.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.tasknews.model.NewsArticle;
import com.example.tasknews.repo.NewsRepository;

import java.util.List;

public class NewsViewModel extends ViewModel {
    private final NewsRepository repository;

    public NewsViewModel() {
        repository = new NewsRepository();
    }

    public LiveData<List<NewsArticle>> getTopHeadlines() {
        return repository.getTopHeadlines();
    }

    public LiveData<List<NewsArticle>> searchNews(String query) {
        return repository.searchNews(query);
    }
}

