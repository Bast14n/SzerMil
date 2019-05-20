package com.example.szermil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;


import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.example.szermil.login.LoginActivity;
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
        BootstrapLabel loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), MarkActivity.class);
            startActivity(intent);
        });

        BootstrapLabel facebookLoginButton = findViewById(R.id.facebookLoginButton);
        facebookLoginButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RestaurantSearchActivity.class);
            startActivity(intent);
        });
        BootstrapLabel registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RestaurantSearchActivity.class);
            startActivity(intent);
        });

        Button loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        });
    }


}
