package com.example.tasknews;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.tasknews.adapter.BookmarksAdapter;
import com.example.tasknews.adapter.NewsAdapter;
import com.example.tasknews.model.NewsArticle;
import com.example.tasknews.viewmodel.BookmarksViewModel;

import java.util.ArrayList;
import java.util.List;

public class BookmarksFragment extends Fragment {
    private BookmarksViewModel viewModel;
    private BookmarksAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bookmarks, container, false);


        RecyclerView recyclerView = view.findViewById(R.id.recyclerView);

        adapter = new BookmarksAdapter(new ArrayList<>(), article -> {
            openArticleDetails(article);
        }, getContext());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);

        viewModel = new ViewModelProvider(this).get(BookmarksViewModel.class);


        viewModel.getBookmarks().observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) {
                Log.d("BookmarksFragment", "Received " + articles.size() + " bookmarks.");
                adapter.updateArticles(articles);
            } else {
                Log.d("BookmarksFragment", "No bookmarks available.");
            }
        });



        return view;
    }

    private void openArticleDetails(NewsArticle article) {
        Intent intent= new Intent(getContext(),ShowNewsActivity.class);
        intent.putExtra("url", article.getUrl());
        startActivity(intent);
    }
}
