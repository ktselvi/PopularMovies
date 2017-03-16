package app.p1.udacity.com.popularmovies.db;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;

import app.p1.udacity.com.popularmovies.datamodels.FavJSONObject;
import app.p1.udacity.com.popularmovies.datamodels.Movie;

/**
 * Created by tkumares on 12-Jun-16.
 */
public class FavouriteMovieHelper {
    private static final String MOVIE_DATABASE = "favourites_db";
    private static final String FAV_KEY = "favourites";

    private Context context;
    private static FavouriteMovieHelper instanceFMH;
    private List<Movie> favMoviesList;

    public Context getContext() {
        return context;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    FavouriteMovieHelper(Context context) {
        this.context = context;
        SharedPreferences preferences = context.getSharedPreferences(MOVIE_DATABASE, Context.MODE_PRIVATE);
            String json = preferences.getString(FAV_KEY, "");
        if (json.length() == 0) {
            favMoviesList = new ArrayList<>();
        } else {
            FavJSONObject wrapper = new Gson().fromJson(json, FavJSONObject.class);
            favMoviesList = wrapper.getFavMoviesList();
        }
    }

    public static FavouriteMovieHelper getHelperObject(Context context) {
        if(instanceFMH == null) {
            instanceFMH = new FavouriteMovieHelper(context);
        }
        return instanceFMH;
    }

    public void applyChanges() {
        SharedPreferences preferences = context.getSharedPreferences(MOVIE_DATABASE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        FavJSONObject wrapper = new FavJSONObject();
        wrapper.setFavMoviesList(favMoviesList);
        String json = new Gson().toJson(wrapper);
        editor.putString(FAV_KEY, json);
        editor.apply();
    }

    public void removeFavourite(Movie movie) {
        int delIndex = -1;
        for(int i=0;i<favMoviesList.size();i++){
            if(favMoviesList.get(i).getId().equals(movie.getId())) {
                delIndex = i;
                break;
            }
        }
        if(delIndex != -1)
        {
            favMoviesList.remove(delIndex);
        }
        applyChanges();
    }

    public void addFavourite(Movie movie) {
        favMoviesList.add(movie);
        applyChanges();
    }

    public List<Movie> getFavourites() {
        return favMoviesList;
    }
}
