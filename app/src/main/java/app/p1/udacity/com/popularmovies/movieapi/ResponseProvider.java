package app.p1.udacity.com.popularmovies.movieapi;

import java.util.List;

import app.p1.udacity.com.popularmovies.datamodels.Movie;

/**
 * Created by HOME on 30-04-2016.
 * Added this interface so that the response can be sent back to the main activity
 */
public interface ResponseProvider {
    void handleResponse(List<Movie> response);
}
