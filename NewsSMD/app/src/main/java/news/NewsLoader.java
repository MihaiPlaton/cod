package news;

import androidx.annotation.RequiresApi;
import androidx.loader.content.AsyncTaskLoader;
import android.content.Context;
import android.os.Build;

import java.util.List;


public class NewsLoader extends AsyncTaskLoader<List<News>> {
    private static final String LOG_TAG = NewsLoader.class.getName();
    private final String Url;

    
    public NewsLoader(Context context, String url) {
        super(context);
        Url = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public List<News> loadInBackground() {
        if (Url == null) {
            return null;
        }

        return Utils.fetchNewsData(Url);
    }
}
