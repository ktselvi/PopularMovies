package app.p1.udacity.com.popularmovies.movieapi;

import java.util.List;

import app.p1.udacity.com.popularmovies.datamodels.TrailerItem;

/**
 * Created by HOME on 11-06-2016.
 * Added this interface so that the response can be sent back to the main activity
 */
public interface TrailerDataProvider {
    void handleResponse(List<TrailerItem> response);
}
