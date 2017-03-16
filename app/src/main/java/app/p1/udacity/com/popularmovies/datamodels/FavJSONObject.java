package app.p1.udacity.com.popularmovies.datamodels;

import java.util.List;

/**
 * Created by tkumares on 12-Jun-16.
 */
public class FavJSONObject {
    private List<Movie> favMoviesList;

    public List<Movie> getFavMoviesList() {
        return favMoviesList;
    }

    public void setFavMoviesList(List<Movie> favMoviesList) {
        this.favMoviesList = favMoviesList;
    }
}
