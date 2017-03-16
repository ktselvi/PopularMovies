package app.p1.udacity.com.popularmovies.parser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.p1.udacity.com.popularmovies.datamodels.Movie;

/**
 * Created by HOME on 30-04-2016.
 */
public class DataParser {
    private final String LOG_TAG = DataParser.class.getSimpleName();
    //List of all the keys used to fetch the values
    private final String ARRAY_NAME = "results";
    private final String POSTER_KEY = "poster_path";
    private final String OVERVIEW_KEY = "overview";
    private final String RELEASEDATE_KEY = "release_date";
    private final String ID_KEY = "id";
    private final String TITLE_KEY = "title";
    private final String BACKDROP_KEY = "backdrop_path";
    private final String VOTE_KEY = "vote_average";
    private final String imageUrl = "http://image.tmdb.org/t/p/original";

    public List<Movie> parseData(String data){
        if(data != null){
            List<Movie> moviesList = new ArrayList<>();

            try{
                JSONObject jsonresult = new JSONObject(data);
                JSONArray resultsArray = jsonresult.getJSONArray(ARRAY_NAME);

                for(int i=0;i<resultsArray.length();i++) {
                    JSONObject movieData = resultsArray.getJSONObject(i);

                    String posterPath = movieData.getString(POSTER_KEY);
                    String overview = movieData.getString(OVERVIEW_KEY);
                    String releaseDate = movieData.getString(RELEASEDATE_KEY);
                    String id = movieData.getString(ID_KEY);
                    String title = movieData.getString(TITLE_KEY);
                    String backdropPath = movieData.getString(BACKDROP_KEY);
                    String vote = movieData.getString(VOTE_KEY);
                    String actualPosterPath = imageUrl+posterPath;
                    String actualBackdropPath = imageUrl+backdropPath;

                    Movie movie = new Movie(actualPosterPath, overview, releaseDate, title, vote, actualBackdropPath, id);
                    moviesList.add(movie);
                }

                return moviesList;
            }catch(JSONException exception) {
                Log.e(LOG_TAG, exception.getMessage());
            }
        }
        return null;
    }
}
