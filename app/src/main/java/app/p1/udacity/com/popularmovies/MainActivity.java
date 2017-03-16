package app.p1.udacity.com.popularmovies;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import app.p1.udacity.com.popularmovies.datamodels.Movie;

public class MainActivity extends AppCompatActivity implements OpenDetailInterface {

    private final String APP_TITLE = " Movies";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //Changing the app title according to the type of movies displayed
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String sortOrderPref = prefs.getString(getString(R.string.sort_order_key),
                getString(R.string.sort_order_popular));
        if(getSupportActionBar() != null && sortOrderPref != null){
            String title = "";
            if(sortOrderPref.equals(getString(R.string.sort_order_popular))) {
                title = getString(R.string.popular) + APP_TITLE;
            }
            else if(sortOrderPref.equals(getString(R.string.sort_order_toprated))) {
                title = getString(R.string.toprated) + APP_TITLE;
            }
            else {
                title = "Favourite" + APP_TITLE;
            }
            getSupportActionBar().setTitle(title);View fragmentContainer = findViewById(R.id.fragment_container);
            if (fragmentContainer != null) {
                Fragment existingFragment = getSupportFragmentManager().findFragmentByTag("detailsfragment");
                if(existingFragment != null) {
                    getSupportFragmentManager().beginTransaction().remove(existingFragment).commit();
                }
            }
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void handleClickEvent(Movie movie) {
        View fragmentContainer = findViewById(R.id.fragment_container);
        if (fragmentContainer != null) {
            DetailActivityFragment details = new DetailActivityFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            details.setMovie(movie);
            ft.replace(R.id.fragment_container, details, "detailsfragment");
            ft.addToBackStack(null);
            ft.commit();
        } else {
            Intent displayintent = new Intent(this, DetailActivity.class);
            displayintent.putExtra(DetailActivity.MOVIE_DATA,movie);
            startActivity(displayintent);
        }
    }
}
