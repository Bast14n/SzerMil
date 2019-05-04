package com.example.szermil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.szermil.restaurant_search.RestaurantSearch;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button button = findViewById(R.id.testButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RestaurantSearch.class);
                startActivity(intent);
            }
        });

/*        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("szermil");
                User user = new User("Marian", "marian@gmail.com");
                mDatabase.child("users").child("id1").setValue(user);

                //                myRef.setValue("Hello, World!");

//                String restaurantId = myRef.push().getKey();
//                Restaurant restaurant = new Restaurant("Testowa restauracja u abdula");
//                myRef.child(restaurantId).setValue(restaurant);
            }
        });*/
    }


}
