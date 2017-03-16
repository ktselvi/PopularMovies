package app.p1.udacity.com.popularmovies.parser;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import app.p1.udacity.com.popularmovies.datamodels.TrailerItem;
import app.p1.udacity.com.popularmovies.datamodels.Trailers;

/**
 * Created by HOME on 30-04-2016.
 */
public class TrailerParser {
    private final String LOG_TAG = TrailerParser.class.getSimpleName();
    //List of all the keys used to fetch the values
    private final String ARRAY_NAME = "results";
    private final String ID_KEY = "id";
    private final String ISO_639_1_KEY = "iso_639_1";
    private final String ISO_3166_KEY = "iso_3166_1";
    private final String KEY = "key";
    private final String NAME_KEY = "name";
    private final String SITE_KEY = "site";
    private final String SIZE_KEY = "size";
    private final String TYPE_KEY = "type";

    public Trailers parseData(String data){
        if(data != null){
            try{
                JSONObject jsonresult = new JSONObject(data);
                JSONArray resultsArray = jsonresult.getJSONArray(ARRAY_NAME);
                Integer objId = jsonresult.getInt(ID_KEY);
                List<TrailerItem> trailersList = new ArrayList<>();

                for(int i=0;i<resultsArray.length();i++) {
                    JSONObject movieData = resultsArray.getJSONObject(i);

                    String id = movieData.getString(ID_KEY);
                    String iso639 = movieData.getString(ISO_639_1_KEY);
                    String iso3166 = movieData.getString(ISO_3166_KEY);
                    String keyValue = movieData.getString(KEY);
                    Integer size = movieData.getInt(SIZE_KEY);
                    String name = movieData.getString(NAME_KEY);
                    String site = movieData.getString(SITE_KEY);
                    String type = movieData.getString(TYPE_KEY);

                    TrailerItem trailer = new TrailerItem(id, iso639, iso3166, keyValue, name, site, size, type);
                    trailersList.add(trailer);
                }

                return new Trailers(objId, trailersList);
            }catch(JSONException exception) {
                Log.e(LOG_TAG, exception.getMessage());
            }
        }
        return null;
    }
}
