package com.example.tasknews.repo;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.LiveData;

import com.example.tasknews.database.AppDatabase;
import com.example.tasknews.database.NewsDao;
import com.example.tasknews.model.NewsArticle;

import java.util.List;

public class BookmarksRepository {
    private final NewsDao newsDao;

    public BookmarksRepository(Context context) {
        AppDatabase db = AppDatabase.getInstance(context);
        newsDao = db.newsDao();
    }

    public LiveData<List<NewsArticle>> getBookmarks() {
        LiveData<List<NewsArticle>> bookmarks = newsDao.getAllBookmarks();
        bookmarks.observeForever(articles -> {
            if (articles != null) {
                Log.d("BookmarksRepository", "Fetched " + articles.size() + " bookmarks from database.");
            } else {
                Log.d("BookmarksRepository", "No bookmarks found in database.");
            }
        });
        return bookmarks;
    }


    public void addBookmark(NewsArticle article) {
        new Thread(() -> newsDao.addBookmark(article)).start();
    }

    public void removeBookmark(NewsArticle article) {
        new Thread(() -> newsDao.removeBookmark(article)).start();
    }
}


