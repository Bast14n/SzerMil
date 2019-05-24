package com.example.szermil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.example.szermil.login.LoginActivity;
import com.example.szermil.restaurant_search.RestaurantSearchActivity;
import com.example.szermil.user.mark.UserMarksActivity;

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
            // TODO: implement login button
        });

        BootstrapLabel facebookLoginButton = findViewById(R.id.facebookLoginButton);
        facebookLoginButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });
        BootstrapLabel registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RestaurantSearchActivity.class);
            startActivity(intent);
        });

            BootstrapLabel markButton = findViewById(R.id.userMarksButton);
            markButton.setOnClickListener(v -> {
                Intent intent = new Intent(getApplicationContext(), UserMarksActivity.class);
                startActivity(intent);
            });

    }


}
