package app.p1.udacity.com.popularmovies.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import app.p1.udacity.com.popularmovies.MainActivity;
import app.p1.udacity.com.popularmovies.R;
import app.p1.udacity.com.popularmovies.datamodels.Movie;

/**
 * Created by HOME on 30-04-2016.
 */
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private List<Movie> moviesData;
    private final Context context;

    public MovieAdapter(Context context, List<Movie> data) {
        moviesData = new ArrayList<>(data);
        this.context = context;
    }

    public void addData(List<Movie> movies) {
        moviesData.addAll(movies);
        notifyDataSetChanged();
    }

    public void clear() {
        moviesData.clear();
        notifyDataSetChanged();
    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycler_movies_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(MovieAdapter.ViewHolder holder, int position) {
        final Movie movie = moviesData.get(position);
        Picasso.with(context).load(movie.getPoster_path()).fit().into(holder.movieImageView);
        holder.movieImageView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                if(context instanceof MainActivity) {
                    ((MainActivity)context).handleClickEvent(movie);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return moviesData.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ImageView movieImageView;
        public ViewHolder(View itemView) {
            super(itemView);
            movieImageView = (ImageView) itemView.findViewById(R.id.movie_poster);
        }
    }
}
