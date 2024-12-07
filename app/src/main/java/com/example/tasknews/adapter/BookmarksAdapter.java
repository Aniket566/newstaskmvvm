package com.example.tasknews.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.tasknews.R;
import com.example.tasknews.model.NewsArticle;
import com.example.tasknews.repo.BookmarksRepository;

import java.util.ArrayList;
import java.util.List;

public class BookmarksAdapter extends RecyclerView.Adapter<BookmarksAdapter.BookmarksViewHolder> {
    private final List<NewsArticle> articles;
    private final OnNewsClickListener listener;
    private final BookmarksRepository bookmarksRepository;

    public BookmarksAdapter(List<NewsArticle> articles, OnNewsClickListener listener, Context context) {
        this.articles = new ArrayList<>(articles);
        this.listener = listener;
        this.bookmarksRepository = new BookmarksRepository(context);  // Initialize repository
    }

    public void updateArticles(List<NewsArticle> newArticles) {
        articles.clear();
        articles.addAll(newArticles);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public BookmarksViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bookmark, parent, false);
        return new BookmarksViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BookmarksViewHolder holder, int position) {
        NewsArticle article = articles.get(position);
        holder.title.setText(article.getTitle());
        holder.date.setText(article.getPublishedAt());
        Glide.with(holder.itemView.getContext()).load(article.getImage()).into(holder.image);

        holder.itemView.setOnClickListener(v -> listener.onNewsClick(article));

        holder.bookmarkButton.setImageResource(R.drawable.ic_bookmarks);
        holder.bookmarkButton.setOnClickListener(v -> {
            removeBookmark(article, holder);
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    private void removeBookmark(NewsArticle article, BookmarksViewHolder holder) {
        bookmarksRepository.removeBookmark(article);
        articles.remove(article);
        notifyDataSetChanged();
    }

    public interface OnNewsClickListener {
        void onNewsClick(NewsArticle article);
    }

    static class BookmarksViewHolder extends RecyclerView.ViewHolder {
        TextView title, date;
        ImageView image, bookmarkButton;

        public BookmarksViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            date = itemView.findViewById(R.id.tvDate);
            image = itemView.findViewById(R.id.ivImage);
            bookmarkButton = itemView.findViewById(R.id.bookmarkButton);
        }
    }
}

