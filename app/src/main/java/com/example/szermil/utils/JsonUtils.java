package com.example.szermil.utils;

import com.example.szermil.restaurant_search.model.Restaurant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {
    private final static int MAX_SIZE = 20;

    public static List<Restaurant> getListOfRestaurantsFromJson(String json)
            throws JSONException {
        List<Restaurant> restaurants = new ArrayList<>(MAX_SIZE);

        if (json != null) {
            JSONObject jsonObject = new JSONObject(json);
            JSONArray restaurantsArray = jsonObject.getJSONArray("restaurants");
            for (int i = 0; i < MAX_SIZE && i < restaurantsArray.length(); i++) {
                Restaurant restaurant = getRestaurantFromJsonArray(restaurantsArray, i);
                restaurants.add(restaurant);
            }
        }
        return restaurants;
    }

    private static Restaurant getRestaurantFromJsonArray(
            JSONArray restaurants,
            int index)
            throws JSONException {
        JSONObject nextJsonObject = restaurants.getJSONObject(index);
        JSONObject restaurant = nextJsonObject.getJSONObject("restaurant");
        JSONObject location = restaurant.getJSONObject("location");
        return new Restaurant(
                restaurant.getLong("id"),
                restaurant.getString("name"),
                location.getString("locality"));
    }
}
