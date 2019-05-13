package com.example.szermil.utils;

import com.example.szermil.restaurant_search.model.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.example.szermil.restaurant_search.RestaurantSearchActivity.MatomoHttpHandler.MAX_SIZE;

public class JsonUtils {
    public static List<Restaurant> getListOfRestaurantsFromJson(String json) throws JSONException {
        List<Restaurant> restaurants = new ArrayList<>(MAX_SIZE);

        JSONObject jsonObject = new JSONObject(json);
        JSONArray restaurantsArray = jsonObject.getJSONArray("restaurants");
        for (int i = 0; i < MAX_SIZE; i++) {
            JSONObject nextJsonElement = restaurantsArray.getJSONObject(i);
            JSONObject restaurant = nextJsonElement.getJSONObject("restaurant");
            JSONObject location = restaurant.getJSONObject("location");
            restaurants.add(new Restaurant(
                    restaurant.getLong("id"),
                    restaurant.getString("name"),
                    location.getString("locality")));
        }
        return restaurants;
    }
}
