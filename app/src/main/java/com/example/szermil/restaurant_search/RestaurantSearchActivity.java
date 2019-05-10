package com.example.szermil.restaurant_search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.szermil.R;
import com.example.szermil.restaurant_search.model.Restaurant;
import com.example.szermil.restaurant_search.service.RestaurantService;
import com.example.szermil.restaurant_search.service.impl.RestaurantServiceImpl;

import java.util.List;

public class RestaurantSearchActivity extends AppCompatActivity {
    private RestaurantService restaurantService = new RestaurantServiceImpl();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_search);
        LinearLayout linearLayout = findViewById(R.id.restaurantsScrollViewLinearLayout);
        List<Restaurant> restaurants = restaurantService.getAllRestaurants();
        for (final Restaurant restaurant : restaurants) {
            TextView textView = new TextView(getApplicationContext());
            textView.setTextSize(30);
            textView.setText(restaurant.getName());
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    System.out.println("Kliknieto przycisk o id: " + restaurant.getId());
                }
            });
            linearLayout.addView(textView);
        }
    }
}
