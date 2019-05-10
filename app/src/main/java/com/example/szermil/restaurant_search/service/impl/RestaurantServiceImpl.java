package com.example.szermil.restaurant_search.service.impl;

import com.example.szermil.restaurant_search.model.Restaurant;
import com.example.szermil.restaurant_search.service.RestaurantService;

import java.util.Arrays;
import java.util.List;

public class RestaurantServiceImpl implements RestaurantService {

    @Override
    public List<Restaurant> getAllRestaurants() {
        return Arrays.asList(
                new Restaurant(1, "Restauracja jeden"),
                new Restaurant(2, "Restauracja dwa"),
                new Restaurant(3, "Restauracja trzy"),
                new Restaurant(4, "Restauracja cztery"),
                new Restaurant(5, "Restauracja pięć")
        );
    }
}
