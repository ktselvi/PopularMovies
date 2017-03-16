package app.p1.udacity.com.popularmovies.parser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.p1.udacity.com.popularmovies.datamodels.ReviewItem;
import app.p1.udacity.com.popularmovies.datamodels.Reviews;

/**
 * Created by HOME on 30-04-2016.
 */
public class ReviewParser {
    private final String LOG_TAG = ReviewParser.class.getSimpleName();
    //List of all the keys used to fetch the values
    private final String ID_KEY = "id";
    private final String PAGE_KEY = "page";
    private final String RESULTS_KEY = "results";
    private final String TOTALPAGES_KEY = "total_pages";
    private final String TOTALRESULTS_KEY = "total_results";
    private final String AUTHOR_KEY = "author";
    private final String CONTENT_KEY = "content";
    private final String URL_KEY = "url";

    public Reviews parseData(String data){
        if(data != null){
            try{
                JSONObject jsonresult = new JSONObject(data);
                List<ReviewItem> reviewsList = new ArrayList<>();

                Integer objId = jsonresult.getInt(ID_KEY);
                Integer page = jsonresult.getInt(PAGE_KEY);
                JSONArray resultsArray = jsonresult.getJSONArray(RESULTS_KEY);
                Integer totalPages = jsonresult.getInt(TOTALPAGES_KEY);
                Integer totalresults = jsonresult.getInt(TOTALRESULTS_KEY);

                if(resultsArray != null) {

                    for(int i=0;i<resultsArray.length();i++) {
                        JSONObject reviewData = resultsArray.getJSONObject(i);
                        String reviewId = reviewData.getString(ID_KEY);
                        String author = reviewData.getString(AUTHOR_KEY);
                        String content = reviewData.getString(CONTENT_KEY);
                        String url = reviewData.getString(URL_KEY);
                        ReviewItem review = new ReviewItem(reviewId,author,content,url);
                        reviewsList.add(review);
                    }
                }

                return new Reviews(objId, page, totalPages, totalresults, reviewsList);
            }catch(JSONException exception) {
                Log.e(LOG_TAG, exception.getMessage());
            }
        }
        return null;
    }
}
