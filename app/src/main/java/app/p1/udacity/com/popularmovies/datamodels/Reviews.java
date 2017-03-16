package app.p1.udacity.com.popularmovies.datamodels;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HOME on 11-06-2016.
 */
public class Reviews {

    private Integer id;
    private Integer page;
    private Integer total_pages;
    private Integer total_results;
    private List<ReviewItem> results = new ArrayList<>();

    public List<ReviewItem> getResults() {
        return results;
    }

    public void setResults(List<ReviewItem> results) {
        this.results = results;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getTotal_pages() {
        return total_pages;
    }

    public void setTotal_pages(Integer total_pages) {
        this.total_pages = total_pages;
    }

    public Integer getTotal_results() {
        return total_results;
    }

    public void setTotal_results(Integer total_results) {
        this.total_results = total_results;
    }

    public Reviews(Integer id, Integer page, Integer total_pages, Integer total_results, List<ReviewItem> results) {
        this.id = id;
        this.page = page;
        this.total_pages = total_pages;
        this.total_results = total_results;
        this.results = results;
    }
}
