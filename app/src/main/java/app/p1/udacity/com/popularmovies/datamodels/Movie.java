package app.p1.udacity.com.popularmovies.datamodels;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by HOME on 30-04-2016.
 */
public class Movie implements Parcelable{

    private String poster_path;
    private String overview;
    private String release_date;
    private String title;
    private String vote_average;
    private String backdrop_path;
    private String id;

    public String getPoster_path() {
        return poster_path;
    }

    public void setPoster_path(String poster_path) {
        this.poster_path = poster_path;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getRelease_date() {
        return release_date;
    }

    public void setRelease_date(String release_date) {
        this.release_date = release_date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getVote_average() {
        return vote_average;
    }

    public void setVote_average(String vote_average) {
        this.vote_average = vote_average;
    }

    public String getBackdrop_path() {
        return backdrop_path;
    }

    public void setBackdrop_path(String backdrop_path) {
        this.backdrop_path = backdrop_path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public Movie(String poster_path, String overview, String release_date, String title, String vote_average, String backdrop_path, String id) {
        this.poster_path = poster_path;
        this.overview = overview;
        this.release_date = release_date;
        this.title = title;
        this.vote_average = vote_average;
        this.backdrop_path = backdrop_path;
        this.id = id;
    }

    public Movie(Parcel in) {
        String[] data = new String[7];
        in.readStringArray(data);
        poster_path = data[0];
        overview = data[1];
        release_date = data[2];
        title = data[3];
        vote_average = data[4];
        backdrop_path = data[5];
        id = data[6];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[] {this.poster_path, this.overview, this.release_date, this.title, this.vote_average, this.backdrop_path, this.id});
    }
}
