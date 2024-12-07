package com.example.tasknews.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.tasknews.model.NewsArticle;
import com.example.tasknews.repo.BookmarksRepository;

import java.util.List;

public class BookmarksViewModel extends AndroidViewModel {
    private final BookmarksRepository repository;

    public BookmarksViewModel(Application application) {
        super(application);
        repository = new BookmarksRepository(application);
    }

    public LiveData<List<NewsArticle>> getBookmarks() {
        return repository.getBookmarks();
    }

    public void addBookmark(NewsArticle article) {
        repository.addBookmark(article);
    }

    public void removeBookmark(NewsArticle article) {
        repository.removeBookmark(article);
    }
}
