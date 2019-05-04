package com.example.szermil.restaurant_search.service.impl;

import com.example.szermil.restaurant_search.service.RestaurantService;
import com.example.szermil.restaurant_search.service.model.Restaurant;

import java.util.Arrays;
import java.util.List;

public class RestaurantServiceImpl implements RestaurantService {

    @Override
    public List<Restaurant> getAllRestaurants() {
        return Arrays.asList(
                new Restaurant(1, "Restauracja jeden"),
                new Restaurant(1, "Restauracja dwa"),
                new Restaurant(1, "Restauracja trzy"),
                new Restaurant(1, "Restauracja cztery"),
                new Restaurant(1, "Restauracja pięć")
        );
    }
}
