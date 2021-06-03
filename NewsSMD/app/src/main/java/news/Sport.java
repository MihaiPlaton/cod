package news;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.loader.content.Loader;
import android.util.Log;


import java.util.List;


public class Sport extends ArticlesFragments {
    private static final String LOG_TAG = Sport.class.getName();

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        String sportUrl = NewsPreferences.getPreferredUrl(getContext(), getString(R.string.sport));
        Log.e(LOG_TAG, sportUrl);
        return new NewsLoader(getActivity(), sportUrl);
    }
}
