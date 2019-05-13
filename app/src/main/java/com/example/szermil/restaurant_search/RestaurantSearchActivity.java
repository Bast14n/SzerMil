package com.example.szermil.restaurant_search;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
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

public class RestaurantSearchActivity extends AppCompatActivity {
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_search);

        textView = findViewById(R.id.textView1);

        String url = "https://developers.zomato.com/api/v2.1/search?entity_id=255&entity_type=city";
        MatomoHttpHandler matomoHttpHandler = new MatomoHttpHandler();
        matomoHttpHandler.execute(url);
    }

    public class MatomoHttpHandler extends AsyncTask<String, Void, String> {
        public final static int MAX_SIZE = 20;

        private OkHttpClient client = new OkHttpClient();

        @Override
        protected String doInBackground(String... params) {
            String urlFromParams = params[0];
            Request request = new Request.Builder()
                    .header("Accept", "application/json")
                    .header("user-key", "eaca7b8652fdfb93ad8d46bde2f4986b")
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
            Log.d("jsonPrint", json);
        }

    }
}
