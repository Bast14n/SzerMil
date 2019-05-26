package com.example.szermil.restaurant.details;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.example.szermil.R;
import com.example.szermil.restaurant.mark.MarkActivity;
import com.example.szermil.restaurant.mark.model.Mark;
import com.example.szermil.restaurant_search.model.Restaurant;
import com.example.szermil.user.mark.UserMarksActivity;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.szermil.utils.StringUtils.RESTAURANT_ID_PARAMETER;
import static com.example.szermil.utils.StringUtils.RESTAURANT_NAME_PARAMETER;

public class RestaurantDetailsActivity extends AppCompatActivity {
    private Restaurant restaurant;
    private DatabaseReference databaseReference;
    private RecyclerView markList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_restaurant_details);
        getSupportActionBar().setTitle("Restauracje");
        initialize();
    }

    private void initialize() {
        setRestaurantModelFromPreviousActivity();
        setButtonOnClickListeners();
        initializeDatabase();
        setRestaurantNameOnUi();
        setLayoutForMarks();
    }

    private void setButtonOnClickListeners() {
        BootstrapLabel addMarkButton = findViewById(R.id.addMarkButton);
        addMarkButton.setOnClickListener(v -> {
                    Intent addMarkIntent = new Intent(getApplicationContext(), MarkActivity.class);
                    addMarkIntent.putExtra(RESTAURANT_ID_PARAMETER, restaurant.getId());
                    addMarkIntent.putExtra(RESTAURANT_NAME_PARAMETER,restaurant.getName());
                    startActivity(addMarkIntent);
                }
        );
    }

    private void initializeDatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("marks");
        databaseReference.keepSynced(true);
    }

    private void setLayoutForMarks() {
        markList = findViewById(R.id.restaurantDetailsView);
        markList.setHasFixedSize(true);
        markList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setRestaurantNameOnUi() {
        TextView restaurantInfoTextView = findViewById(R.id.restaurantName);
        restaurantInfoTextView.setText(restaurant.getName());
    }


    private void setRestaurantModelFromPreviousActivity() {
        Intent previousIntent = getIntent();
        long restaurantId = previousIntent.getLongExtra(RESTAURANT_ID_PARAMETER, 0);
        String restaurantName = previousIntent.getStringExtra(RESTAURANT_NAME_PARAMETER);
        restaurant = new Restaurant();
        restaurant.setId(restaurantId);
        restaurant.setName(restaurantName);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Mark, UserMarksActivity.MarkViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Mark, UserMarksActivity.MarkViewHolder>
                (Mark.class, R.layout.marks_row, UserMarksActivity.MarkViewHolder.class, databaseReference.orderByChild("restaurantId").equalTo(restaurant.getId())) {
            @Override
            protected void populateViewHolder(UserMarksActivity.MarkViewHolder viewHolder, Mark model, int position) {
                viewHolder.setMeal(model.getMealName());
                viewHolder.setComment(model.getComment());
                viewHolder.setRating(model.getRating());
                viewHolder.setPhoto(model.getPhotoBase64());

            }
        };
        markList.setAdapter(firebaseRecyclerAdapter);
    }
}
