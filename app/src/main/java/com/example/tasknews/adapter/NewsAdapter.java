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

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsViewHolder> {
    private final List<NewsArticle> articles;
    private final OnNewsClickListener listener;
    private final BookmarksRepository bookmarksRepository;

    public NewsAdapter(List<NewsArticle> articles, OnNewsClickListener listener, Context context) {
        this.articles = new ArrayList<>(articles);
        this.listener = listener;
        this.bookmarksRepository = new BookmarksRepository(context);
    }

    public void updateArticles(List<NewsArticle> newArticles) {
        articles.clear();
        articles.addAll(newArticles);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_news, parent, false);
        return new NewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        NewsArticle article = articles.get(position);
        holder.title.setText(article.getTitle());
        holder.date.setText(article.getPublishedAt());
        Glide.with(holder.itemView.getContext()).load(article.getImage()).into(holder.image);

        checkIfBookmarked(article, holder);

        holder.itemView.setOnClickListener(v -> listener.onNewsClick(article));


        holder.bookmarkButton.setOnClickListener(v -> {
            if (isBookmarked(article)) {
                removeBookmark(article, holder);
            } else {
                addBookmark(article, holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    private void checkIfBookmarked(NewsArticle article, NewsViewHolder holder) {
        bookmarksRepository.getBookmarks().observeForever(bookmarks -> {
            boolean isBookmarked = false;
            for (NewsArticle bookmarkedArticle : bookmarks) {
                if (bookmarkedArticle.getTitle().equals(article.getTitle())) {
                    isBookmarked = true;
                    break;
                }
            }

            if (isBookmarked) {
                holder.bookmarkButton.setImageResource(R.drawable.ic_bookmarks);
            } else {
                holder.bookmarkButton.setImageResource(R.drawable.ic_bookmark_border);
            }
        });
    }

    private void addBookmark(NewsArticle article, NewsViewHolder holder) {
        bookmarksRepository.addBookmark(article);
        holder.bookmarkButton.setImageResource(R.drawable.ic_bookmarks);
    }

    private void removeBookmark(NewsArticle article, NewsViewHolder holder) {
        bookmarksRepository.removeBookmark(article);
        holder.bookmarkButton.setImageResource(R.drawable.ic_bookmark_border);
    }

    private boolean isBookmarked(NewsArticle article) {
        final boolean[] isBookmarked = {false};
        bookmarksRepository.getBookmarks().observeForever(bookmarks -> {
            for (NewsArticle bookmarkedArticle : bookmarks) {
                if (bookmarkedArticle.getTitle().equals(article.getTitle())) {
                    isBookmarked[0] = true;
                    break;
                }
            }
        });
        return isBookmarked[0];
    }

    public interface OnNewsClickListener {
        void onNewsClick(NewsArticle article);
    }

    static class NewsViewHolder extends RecyclerView.ViewHolder {
        TextView title, date;
        ImageView image, bookmarkButton;

        public NewsViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tvTitle);
            date = itemView.findViewById(R.id.tvDate);
            image = itemView.findViewById(R.id.ivImage);
            bookmarkButton = itemView.findViewById(R.id.bookmarkButton);
        }
    }
}
