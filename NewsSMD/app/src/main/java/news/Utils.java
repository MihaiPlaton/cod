package news;

import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;


//JSON STUFF


public class Utils {
    private static final String LOG_TAG = Utils.class.getSimpleName();
    private Utils() {
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static List<News> fetchNewsData(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        return extractFeatureFromJSON(jsonResponse);
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Problem building the URL.", e);
        }
        return url;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(Constants.READ_TIMEOUT );
            urlConnection.setConnectTimeout(Constants.CONNECT_TIMEOUT );
            urlConnection.setRequestMethod(Constants.REQUEST_METHOD_GET);
            urlConnection.connect();
            
            if (urlConnection.getResponseCode() == Constants.SUCCESS_RESPONSE_CODE) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static List<News> extractFeatureFromJSON(String newsJSON) {
        
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }
        
        List<News> newsList = new ArrayList<>();
        try {
            JSONObject baseJsonResponse = new JSONObject(newsJSON);
            JSONObject responseJsonObject = baseJsonResponse.getJSONObject(Constants.JSON_KEY_RESPONSE);
            JSONArray resultsArray = responseJsonObject.getJSONArray(Constants.JSON_KEY_RESULTS);

            for (int i = 0; i < resultsArray.length(); i++) {
                JSONObject currentNews = resultsArray.getJSONObject(i);
                String webTitle = currentNews.getString(Constants.JSON_KEY_WEB_TITLE);
                String sectionName = currentNews.getString(Constants.JSON_KEY_SECTION_NAME);
                String webPublicationDate = currentNews.getString(Constants.JSON_KEY_WEB_PUBLICATION_DATE);
                String webUrl = currentNews.getString(Constants.JSON_KEY_WEB_URL);
                
                String author = null;
                if (currentNews.has(Constants.JSON_KEY_TAGS)) {
                    JSONArray tagsArray = currentNews.getJSONArray(Constants.JSON_KEY_TAGS);
                    if (tagsArray.length() != 0) {
                        JSONObject firstTagsItem = tagsArray.getJSONObject(0);
                        author = firstTagsItem.getString(Constants.JSON_KEY_WEB_TITLE);
                    }
                }

                String thumbnail = null;
                String trailText = null;
                if (currentNews.has(Constants.JSON_KEY_FIELDS)) {
                    JSONObject fieldsObject = currentNews.getJSONObject(Constants.JSON_KEY_FIELDS);
                    if (fieldsObject.has(Constants.JSON_KEY_THUMBNAIL)) {
                        thumbnail = fieldsObject.getString(Constants.JSON_KEY_THUMBNAIL);
                    }
                    if (fieldsObject.has(Constants.JSON_KEY_TRAIL_TEXT)) {
                        trailText = fieldsObject.getString(Constants.JSON_KEY_TRAIL_TEXT);
                    }
                }

                News news = new News(webTitle, sectionName, author, webPublicationDate, webUrl, thumbnail, trailText);
                newsList.add(news);
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the news JSON results", e);
        }

        return newsList;
    }
}
