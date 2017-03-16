package app.p1.udacity.com.popularmovies.movieapi;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import app.p1.udacity.com.popularmovies.BuildConfig;
import app.p1.udacity.com.popularmovies.datamodels.TrailerItem;
import app.p1.udacity.com.popularmovies.parser.TrailerParser;

/**
 * Created by HOME on 11-06-2016.
 */
public class TrailerFetcher extends AsyncTask<String, Void, List<TrailerItem>> {

    private final String LOG_TAG = TrailerFetcher.class.getSimpleName();
    private String resultJsonStr;
    private TrailerDataProvider trailersResponseProvider;

    public TrailerFetcher(TrailerDataProvider trailersResponseProvider){
        this.trailersResponseProvider = trailersResponseProvider;
    }

    @Override
    protected List<TrailerItem> doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            // Construct the URL for the MoviesDB query
            final String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie/";
            final String MOVIE_ID = params[0];
            final String ACTION_DATA = "/videos";
            final String APPID_PARAM = "api_key";

            Uri builtUri = Uri.parse(MOVIE_BASE_URL+MOVIE_ID+ACTION_DATA).buildUpon()
                    .appendQueryParameter(APPID_PARAM, BuildConfig.MOVIES_DB_API_KEY)
                    .build();

            URL url = new URL(builtUri.toString());

            // Create the request to MoviesDB, and open the connection
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // Read the input stream into a String
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));

            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }

            if (buffer.length() == 0) {
                // Stream was empty.  No point in parsing.
                return null;
            }

            resultJsonStr = buffer.toString();

        } catch (IOException e) {
            Log.e(LOG_TAG, "Error ", e);
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.e(LOG_TAG, "Error closing stream", e);
                }
            }
        }

        try {
            return new TrailerParser().parseData(resultJsonStr).getResults();
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        // This will only happen if there was an error getting or parsing the movies data.
        return null;
    }

    @Override
    protected void onPostExecute(List<TrailerItem> result) {
        trailersResponseProvider.handleResponse(result);
    }
}
