package news;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.loader.content.Loader;
import android.util.Log;

import com.example.android.newsfeed.R;

import java.util.List;


public class Society extends ArticlesFragments {
    private static final String LOG_TAG = Society.class.getName();

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        String societyUrl = NewsPreferences.getPreferredUrl(getContext(), getString(R.string.society));
        Log.e(LOG_TAG, societyUrl);
        return new NewsLoader(getActivity(), societyUrl);
    }
}
