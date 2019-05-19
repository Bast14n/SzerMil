package com.example.szermil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.example.szermil.restaurant.mark.MarkActivity;
import com.example.szermil.restaurant_search.RestaurantSearchActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setButtonListeners();
    }

    private void setButtonListeners() {
        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MarkActivity.class);
            startActivity(intent);
        });

        Button facebookLoginButton = findViewById(R.id.facebookLoginButton);
        facebookLoginButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RestaurantSearchActivity.class);
            startActivity(intent);
        });
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RestaurantSearchActivity.class);
            startActivity(intent);
        });
    }
}
