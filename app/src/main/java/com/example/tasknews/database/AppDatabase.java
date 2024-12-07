package com.example.tasknews.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.tasknews.model.NewsArticle;

@Database(entities = {NewsArticle.class}, version = 2)
public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase instance;

    public abstract NewsDao newsDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    "news_database"
            ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}


