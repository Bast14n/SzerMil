package com.example.szermil.user_profile;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.example.szermil.R;
import com.example.szermil.restaurant_search.RestaurantSearchActivity;
import com.example.szermil.user.mark.UserMarksActivity;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        setButtonListeners();
    }

    private void setButtonListeners() {
        BootstrapLabel registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RestaurantSearchActivity.class);
            startActivity(intent);
        });
        BootstrapLabel yourRatingButton = findViewById(R.id.userRatingButton);
        yourRatingButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), UserMarksActivity.class);
            startActivity(intent);
        });
    }
}
