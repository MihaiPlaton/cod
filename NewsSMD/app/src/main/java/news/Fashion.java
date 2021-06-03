package news;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.loader.content.Loader;
import android.util.Log;


import java.util.List;


public class Fashion extends ArticlesFragments {
    private static final String LOG_TAG = Fashion.class.getName();

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        String fashionUrl = NewsPreferences.getPreferredUrl(getContext(), getString(R.string.fashion));
        Log.e(LOG_TAG, fashionUrl);
        return new NewsLoader(getActivity(), fashionUrl);
    }
}
