package news;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.loader.content.Loader;
import android.util.Log;


import java.util.List;


public class World extends ArticlesFragments {
    private static final String LOG_TAG = World.class.getName();

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        String worldUrl = NewsPreferences.getPreferredUrl(getContext(), getString(R.string.world));
        Log.e(LOG_TAG, worldUrl);
        return new NewsLoader(getActivity(), worldUrl);
    }
}
