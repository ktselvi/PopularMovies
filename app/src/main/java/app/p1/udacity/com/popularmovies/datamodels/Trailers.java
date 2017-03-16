package app.p1.udacity.com.popularmovies.datamodels;

/**
 * Created by HOME on 11-06-2016.
 */

import java.util.List;
import java.util.ArrayList;

public class Trailers {

    private Integer id;
    private List<TrailerItem> results = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<TrailerItem> getResults() {
        return results;
    }

    public void setResults(List<TrailerItem> results) {
        this.results = results;
    }

    public Trailers(Integer id, List<TrailerItem> results) {
        this.id = id;
        this.results = results;
    }
}

