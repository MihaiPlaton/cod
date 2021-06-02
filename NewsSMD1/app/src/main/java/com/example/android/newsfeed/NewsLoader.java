package com.example.android.newsfeed;

import androidx.loader.content.AsyncTaskLoader;
import android.content.Context;

import com.example.android.newsfeed.utils.Utils;

import java.util.List;


public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private static final String LOG_TAG = NewsLoader.class.getName();
    private String Url;

    
    public NewsLoader(Context context, String url) {
        super(context);
        Url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<News> loadInBackground() {
        if (Url == null) {
            return null;
        }

        List<News> newsData = Utils.fetchNewsData(Url);
        return newsData;
    }
}
