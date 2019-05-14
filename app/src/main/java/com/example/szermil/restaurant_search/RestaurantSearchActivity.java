package com.example.szermil.restaurant_search;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.SearchView;
import android.widget.TextView;

import com.example.szermil.R;
import com.example.szermil.restaurant_search.model.Restaurant;
import com.example.szermil.utils.JsonUtils;

import org.json.JSONException;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

import static com.example.szermil.utils.ConfigUtils.USER_KEY;

public class RestaurantSearchActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_search);

        textView = findViewById(R.id.textView1);

        SearchView searchView = findViewById(R.id.restaurantsSearchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                String url = "https://developers.zomato.com/api/v2.1/search?entity_id=255&entity_type=city&q=" + query;
                ZomatoSearchHttpHandler zomatoSearchHttpHandler = new ZomatoSearchHttpHandler();
                zomatoSearchHttpHandler.execute(url);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        String url = "https://developers.zomato.com/api/v2.1/search?entity_id=255&entity_type=city";
        ZomatoHttpHandler zomatoHttpHandler = new ZomatoHttpHandler();
        zomatoHttpHandler.execute(url);
    }


    /**
     * Class used to create request to Zomato API to get list of all restaurants when starting activity with search box
     * doInBackground() calls API for response (list of all restaurants in json)
     * in onPostExecute() json with list of restaurants is modelled to List of type Restaurant
     * which can be used to display data in ui
     */
    public class ZomatoHttpHandler extends AsyncTask<String, Void, String> {
        private OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {
            String urlFromParams = params[0];
            Request request = new Request.Builder()
                    .header("Accept", "application/json")
                    .header("user-key", USER_KEY)
                    .get()
                    .url(urlFromParams)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                ResponseBody body = response.body();
                return body == null ? null : body.string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            try {
                List<Restaurant> restaurants = JsonUtils.getListOfRestaurantsFromJson(json);

                restaurants.forEach(restaurant ->
                        textView.append("\n" +
                                restaurant.getName() + ", " +
                                restaurant.getLocality()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Class used to create request to Zomato API when searching for restaurants using search box
     * doInBackground() calls API for response (list of restaurants searched using params from search box in json)
     * in onPostExecute() json with list of restaurants is modelled to List of type Restaurant
     * which can be used to display data in ui
     */
    public class ZomatoSearchHttpHandler extends AsyncTask<String, Void, String> {
        private OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {
            String urlFromParams = params[0];
            Request request = new Request.Builder()
                    .header("Accept", "application/json")
                    .header("user-key", USER_KEY)
                    .get()
                    .url(urlFromParams)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                ResponseBody body = response.body();
                return body == null ? null : body.string();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String json) {
            super.onPostExecute(json);
            try {
                List<Restaurant> restaurants = JsonUtils.getListOfRestaurantsFromJson(json);

                textView.setText("");
                restaurants.forEach(restaurant -> textView.append("\n" +
                        restaurant.getName() + ", " +
                        restaurant.getLocality()));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
