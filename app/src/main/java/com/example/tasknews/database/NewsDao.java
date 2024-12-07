package com.example.tasknews.database;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Delete;

import com.example.tasknews.model.NewsArticle;

import java.util.List;

@Dao
public interface NewsDao {
    @Insert
    void addBookmark(NewsArticle article);

    @Delete
    void removeBookmark(NewsArticle article);

    @Query("SELECT * FROM NewsArticle")
    LiveData<List<NewsArticle>> getAllBookmarks();
}

