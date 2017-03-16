package app.p1.udacity.com.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import app.p1.udacity.com.popularmovies.datamodels.Movie;

public class DetailActivity extends AppCompatActivity {

    public static final String MOVIE_DATA = "moviedata";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        Movie movie = intent.getParcelableExtra(DetailActivity.MOVIE_DATA);

        if(getSupportActionBar() != null){
            //Enabling the Up navigation button
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);

            //Changing the title to display name of the movie
            if(movie != null)
                getSupportActionBar().setTitle(movie.getTitle());
        }

        DetailActivityFragment details = new DetailActivityFragment();
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        details.setMovie(movie);
        ft.replace(R.id.detail_container, details);
        ft.commit();
    }
}
