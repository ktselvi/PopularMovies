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
import app.p1.udacity.com.popularmovies.datamodels.Movie;
import app.p1.udacity.com.popularmovies.parser.DataParser;

/**
 * Created by HOME on 30-04-2016.
 * Async class for performing API calls and fetching the data
 */
public class DataFetcher extends AsyncTask<String, Void, List<Movie>> {

    private final String LOG_TAG = DataFetcher.class.getSimpleName();
    private String resultJsonStr;
    private ResponseProvider movieResponseProvider;

    public DataFetcher(ResponseProvider responseProvider){
        movieResponseProvider = responseProvider;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        if (params.length == 0) {
            return null;
        }

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        try {
            // Construct the URL for the MoviesDB query
            final String FORECAST_BASE_URL = "http://api.themoviedb.org/3/movie/";
            final String SORT_TYPE = params[0];
            final String APPID_PARAM = "api_key";

            Uri builtUri = Uri.parse(FORECAST_BASE_URL+SORT_TYPE).buildUpon()
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
            return new DataParser().parseData(resultJsonStr);
        } catch (Exception e) {
            Log.e(LOG_TAG, e.getMessage(), e);
            e.printStackTrace();
        }

        // This will only happen if there was an error getting or parsing the movies data.
        return null;
    }

    @Override
    protected void onPostExecute(List<Movie> result) {
        movieResponseProvider.handleResponse(result);
    }
}
