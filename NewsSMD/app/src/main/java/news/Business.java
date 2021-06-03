package news;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.loader.content.Loader;
import android.util.Log;


import java.util.List;


public class Business extends ArticlesFragments {
    private static final String LOG_TAG = Business.class.getName();

    @NonNull
    @Override
    public Loader<List<News>> onCreateLoader(int i, Bundle bundle) {
        String businessUrl = NewsPreferences.getPreferredUrl(getContext(), getString(R.string.business));
        Log.e(LOG_TAG, businessUrl);
        return new NewsLoader(getActivity(),businessUrl);
    }
}
