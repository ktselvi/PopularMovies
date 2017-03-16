package app.p1.udacity.com.popularmovies;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import app.p1.udacity.com.popularmovies.adapters.MovieAdapter;
import app.p1.udacity.com.popularmovies.datamodels.Movie;
import app.p1.udacity.com.popularmovies.db.FavouriteMovieHelper;
import app.p1.udacity.com.popularmovies.movieapi.DataFetcher;
import app.p1.udacity.com.popularmovies.movieapi.ResponseProvider;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    private MovieAdapter movieAdapter;

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragment_root =  inflater.inflate(R.layout.fragment_main, container, false);
        RecyclerView moviesRecyclerView = (RecyclerView)fragment_root.findViewById(R.id.movie_recycler_view);
        moviesRecyclerView.setHasFixedSize(true);

        movieAdapter = new MovieAdapter(getActivity(), new ArrayList<Movie>());
        moviesRecyclerView.setAdapter(movieAdapter);
        moviesRecyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 2));
        return fragment_root;
    }

    @Override
    public void onStart(){
        super.onStart();

		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this.getActivity().getApplicationContext()); // Change to getApplicationContext()
		String sortOrderPref = prefs.getString(getString(R.string.sort_order_key),
				getString(R.string.sort_order_popular));

		//for favourites, no api calls, offline data
		if(sortOrderPref.equals("Favourites")) {

			FavouriteMovieHelper helper = FavouriteMovieHelper.getHelperObject(getActivity().getApplicationContext());
			movieAdapter.clear();
			List<Movie> favMovies = helper.getFavourites();

			if(favMovies.size() > 0) {
				movieAdapter.addData(favMovies);
			}
			else {
				Toast toast = Toast.makeText(getActivity(), "No favourites marked !!!", Toast.LENGTH_LONG);
				toast.show();
			}
        }
		else {
			//Trying to fetch the data and display it
			DataFetcher asyncObject = new DataFetcher(new ResponseProvider() {
				@Override
				public void handleResponse(List<Movie> response) {
					movieAdapter.clear();
					movieAdapter.addData(response);
				}
			});

			//only execute api call if there is internet connectivity
			ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
			NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
			if(activeNetworkInfo != null && activeNetworkInfo.isConnected()){
				asyncObject.execute(sortOrderPref);
			}
			else{
				//Display error message as a Toast if there is no connectivity
				CharSequence text = "No internet connection";
				int duration = Toast.LENGTH_SHORT;
				Toast toast = Toast.makeText(getActivity(), text, duration);
				toast.show();

			}
		}
    }
}

