package news;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.loader.content.Loader;
import android.util.Log;


import java.util.List;


public class Culture extends ArticlesFragments {
    private static final String LOG_TAG = Culture.class.getName();

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        String cultureUrl = NewsPreferences.getPreferredUrl(getContext(), getString(R.string.culture));
        Log.e(LOG_TAG, cultureUrl);
        return new NewsLoader(getActivity(), cultureUrl);
    }
}
