package com.example.android.newsfeed.fragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.newsfeed.EmptyRecyclerView;
import com.example.android.newsfeed.News;
import com.example.android.newsfeed.NewsLoader;
import com.example.android.newsfeed.NewsPreferences;
import com.example.android.newsfeed.R;
import com.example.android.newsfeed.NewsAdapter;
import com.example.android.newsfeed.utils.Constants;

import java.util.ArrayList;
import java.util.List;


public class ArticlesFragments extends Fragment
        implements LoaderManager.LoaderCallbacks<List<News>>{

    private static final int NEWS_LOADER_ID = 1;
    private static final String LOG_TAG = ArticlesFragments.class.getName();
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView emptyTextView;
    private View loadingIndicator;
    private NewsAdapter newsAdapter;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        EmptyRecyclerView mRecyclerView = rootView.findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(layoutManager);

        swipeRefreshLayout = rootView.findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.swipe_color_1),
                getResources().getColor(R.color.swipe_color_2),
                getResources().getColor(R.color.swipe_color_3),
                getResources().getColor(R.color.swipe_color_4));

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");
                initiateRefresh();
                Toast.makeText(getActivity(), getString(R.string.updated_just_now),
                        Toast.LENGTH_SHORT).show();
            }
        });

        loadingIndicator = rootView.findViewById(R.id.loading_indicator);

        emptyTextView = rootView.findViewById(R.id.empty_view);
        mRecyclerView.setEmptyView(emptyTextView);
        newsAdapter = new NewsAdapter(getActivity(), new ArrayList<News>());
        mRecyclerView.setAdapter(newsAdapter);
        initializeLoader(isConnected());
        return rootView;
    }

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {

        Uri.Builder uriBuilder = NewsPreferences.getPreferredUri(getContext());
        Log.d(LOG_TAG,uriBuilder.toString());
        return new NewsLoader(getActivity(), uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(@NonNull Loader<List<News>> loader, List<News> newsData) {
        
        loadingIndicator.setVisibility(View.GONE);
        emptyTextView.setText(R.string.no_news);
        newsAdapter.clearAll();
        if (newsData != null && !newsData.isEmpty()) {
            newsAdapter.addAll(newsData);
        }
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<List<News>> loader) {
        newsAdapter.clearAll();
    }

    private void initializeLoader(boolean isConnected) {
        if (isConnected) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);
        } else {
            loadingIndicator.setVisibility(View.GONE);
            emptyTextView.setText(R.string.no_internet_connection);
            emptyTextView.setCompoundDrawablesWithIntrinsicBounds(Constants.DEFAULT_NUMBER,
                    R.drawable.ic_network_check,Constants.DEFAULT_NUMBER,Constants.DEFAULT_NUMBER);
        }
    }
    private boolean isConnected() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }


    private void restartLoader(boolean isConnected) {
        if (isConnected) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.restartLoader(NEWS_LOADER_ID, null, this);
        } else {
            loadingIndicator.setVisibility(View.GONE);
            emptyTextView.setText(R.string.no_internet_connection);
            emptyTextView.setCompoundDrawablesWithIntrinsicBounds(Constants.DEFAULT_NUMBER,
                    R.drawable.ic_network_check,Constants.DEFAULT_NUMBER,Constants.DEFAULT_NUMBER);
            swipeRefreshLayout.setVisibility(View.GONE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        restartLoader(isConnected());
    }

    private void initiateRefresh() {
        restartLoader(isConnected());
    }
}
