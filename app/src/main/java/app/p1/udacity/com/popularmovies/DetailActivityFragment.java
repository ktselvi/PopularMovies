package app.p1.udacity.com.popularmovies;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.p1.udacity.com.popularmovies.adapters.ReviewAdapter;
import app.p1.udacity.com.popularmovies.adapters.TrailerAdapter;
import app.p1.udacity.com.popularmovies.datamodels.Movie;
import app.p1.udacity.com.popularmovies.datamodels.ReviewItem;
import app.p1.udacity.com.popularmovies.datamodels.TrailerItem;
import app.p1.udacity.com.popularmovies.db.FavouriteMovieHelper;
import app.p1.udacity.com.popularmovies.movieapi.ReviewDataProvider;
import app.p1.udacity.com.popularmovies.movieapi.ReviewsFetcher;
import app.p1.udacity.com.popularmovies.movieapi.TrailerDataProvider;
import app.p1.udacity.com.popularmovies.movieapi.TrailerFetcher;

/**
 * A placeholder fragment containing a detail view.
 */
public class DetailActivityFragment extends Fragment {

    private Movie movie;
    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    private ReviewAdapter reviewAdapter;
    private TrailerAdapter trailerAdapter;
    private View rootView;
    FavouriteMovieHelper favHelper;

    public DetailActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_detail, container, false);
        if(movie != null) {
            final RecyclerView listReviews = (RecyclerView)rootView.findViewById(R.id.reviews_listview);
            reviewAdapter = new ReviewAdapter(new ArrayList<ReviewItem>());
            listReviews.setAdapter(reviewAdapter);
            listReviews.setLayoutManager(new LinearLayoutManager(getActivity().getApplicationContext()));

            final RecyclerView listTrailers = (RecyclerView)rootView.findViewById(R.id.trailers_listview);
            trailerAdapter = new TrailerAdapter(getActivity(), new ArrayList<TrailerItem>());
            listTrailers.setAdapter(trailerAdapter);
            listTrailers.setLayoutManager(new LinearLayoutManager(getActivity()));

            TextView titleHolder = (TextView)rootView.findViewById(R.id.titleText);
            TextView releaseDateHolder = (TextView)rootView.findViewById(R.id.releaseDate);
            TextView votesHolder = (TextView)rootView.findViewById(R.id.votes);
            TextView overviewHolder = (TextView)rootView.findViewById(R.id.overviewText);
            ImageView posterHolder = (ImageView) rootView.findViewById(R.id.posterHolder);

            final Button favButton = (Button) rootView.findViewById(R.id.favourite_holder);
            final SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getActivity().getApplicationContext());
            boolean isFavourite = prefs.getBoolean(movie.getId().toString(), false);
            if(isFavourite) {
                favButton.setText(getString(R.string.fav_remove));
            }
            else {
                favButton.setText(R.string.fav_add);
            }
            favHelper = FavouriteMovieHelper.getHelperObject(getActivity().getApplicationContext());
            favButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = prefs.edit();


                    CharSequence text = "";
                    int duration = Toast.LENGTH_LONG;

                    if(favButton.getText().equals(getString(R.string.fav_add))) {
                        editor.putBoolean(movie.getId().toString(), true);
                        favHelper.addFavourite(movie);
                        text = "Movie added to favourites";
                        favButton.setText(getString(R.string.fav_remove));
                    }
                    else {
                        editor.putBoolean(movie.getId().toString(), false);
                        favHelper.removeFavourite(movie);
                        text = "Movie removed from favourites";
                        favButton.setText(getString(R.string.fav_add));
                    }
                    Toast toast = Toast.makeText(getActivity(), text, duration);
                    toast.show();
                    editor.commit();
                }
            });

            //Displaying the movie details
            titleHolder.setText(movie.getTitle());
            releaseDateHolder.setText(movie.getRelease_date());
            votesHolder.setText(movie.getVote_average().substring(0, 3) + "/10");
            Picasso.with(getActivity()).load(movie.getPoster_path()).fit().into(posterHolder);
            overviewHolder.setText(movie.getOverview());

            //getting the trailers of the movie
            TrailerFetcher asyncObject_trailer = new TrailerFetcher(new TrailerDataProvider() {
                @Override
                public void handleResponse(List<TrailerItem> response) {
                    if(response != null && response.size() > 0){
                        trailerAdapter.clear();
                        trailerAdapter.addData(response);
                    }
                    else {
                        //if there are no trailers, remove the trailer section
                        TextView trailerSection = (TextView) rootView.findViewById(R.id.trailer_section);
                        ((ViewGroup)trailerSection.getParent()).removeView(trailerSection);
                        ((ViewGroup)listTrailers.getParent()).removeView(listTrailers);
                    }
                }
            });
            asyncObject_trailer.execute(movie.getId());

            //getting the reviews of the movie
            ReviewsFetcher asyncObject_video = new ReviewsFetcher(new ReviewDataProvider() {
                @Override
                public void handleResponse(List<ReviewItem> response) {
                    if(response != null && response.size() > 0){
                        reviewAdapter.clear();
                        reviewAdapter.addData(response);
                    }
                    else {
                        //if there are no reiews for the movies yet, we will display just a text view with the message "No reviews yet"
                        TextView msgHolder = new TextView(getActivity());
                        msgHolder.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                                ViewGroup.LayoutParams.WRAP_CONTENT));
                        msgHolder.setText(R.string.no_reviews);
                        LinearLayout layout = (LinearLayout)rootView.findViewById(R.id.detail_layout);
                        layout.addView(msgHolder);
                    }
                }
            });
            asyncObject_video.execute(movie.getId());
        }

        return rootView;
    }
}
