package com.example.szermil.utils;

import com.example.szermil.restaurant_search.model.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private final static int MAX_SIZE = 20;

    public static List<Restaurant> getListOfRestaurantsFromJson(String json) throws JSONException {
        List<Restaurant> restaurants = new ArrayList<>(MAX_SIZE);

        if (json != null) {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray restaurantsArray = jsonObject.getJSONArray("restaurants");
            for (int i = 0; i < MAX_SIZE && i < restaurantsArray.length(); i++) {
                JSONObject nextJsonElement = restaurantsArray.getJSONObject(i);
                JSONObject restaurant = nextJsonElement.getJSONObject("restaurant");
                JSONObject location = restaurant.getJSONObject("location");
                restaurants.add(new Restaurant(
                        restaurant.getLong("id"),
                        restaurant.getString("name"),
                        location.getString("locality")));
            }
        }
        return restaurants;
    }
}
