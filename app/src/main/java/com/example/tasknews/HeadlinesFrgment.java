package com.example.tasknews;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import com.example.tasknews.adapter.NewsAdapter;
import com.example.tasknews.adapter.SuggestionAdapter;
import com.example.tasknews.model.NewsArticle;
import com.example.tasknews.viewmodel.NewsViewModel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HeadlinesFrgment extends Fragment {
    private NewsViewModel viewModel;
    private NewsAdapter adapter;
    private SuggestionAdapter suggestionAdapter;
    private List<String> suggestions = new ArrayList<>();


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_headlines_frgment, container, false);

        RecyclerView newsRecyclerView = view.findViewById(R.id.recyclerView);
        RecyclerView suggestionsRecyclerView = view.findViewById(R.id.suggestionsRecyclerView);
        EditText searchBar = view.findViewById(R.id.searchBar);

        newsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new NewsAdapter(new ArrayList<>(), article -> openArticleDetails(article),getContext());
        newsRecyclerView.setAdapter(adapter);

        suggestionsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        suggestionAdapter = new SuggestionAdapter(suggestions, suggestion -> {
            searchBar.setText(suggestion);
            fetchSearchResults(suggestion);
            suggestionsRecyclerView.setVisibility(View.GONE);
        });
        suggestionsRecyclerView.setAdapter(suggestionAdapter);

        viewModel = new ViewModelProvider(this).get(NewsViewModel.class);
        viewModel.getTopHeadlines().observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) {
                adapter.updateArticles(articles);
            }
        });

        searchBar.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String query = s.toString().trim();
                if (!query.isEmpty()) {
                    showSuggestions(query);
                    suggestionsRecyclerView.setVisibility(View.VISIBLE);
                } else {
                    suggestionsRecyclerView.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        return view;
    }

    private void showSuggestions(String query) {
        suggestions.clear();
        List<String> allSuggestions = Arrays.asList("headline", "health", "technology", "sports" , "trading","financial");
        for (String term : allSuggestions) {
            if (term.toLowerCase().startsWith(query.toLowerCase())) {
                suggestions.add(term + " news");
            }
        }

        suggestionAdapter.notifyDataSetChanged();
    }



    private void fetchSearchResults(String query) {
        viewModel.searchNews(query).observe(getViewLifecycleOwner(), articles -> {
            if (articles != null) {
                adapter.updateArticles(articles);
            } else {
                adapter.updateArticles(new ArrayList<>());
            }
        });
    }

    private void openArticleDetails(NewsArticle article) {
        Intent intent= new Intent(getContext(),ShowNewsActivity.class);
        intent.putExtra("url", article.getUrl());
        startActivity(intent);
    }
}