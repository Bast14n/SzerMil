package com.example.szermil.user.mark;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.beardedhen.androidbootstrap.AwesomeTextView;
import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.example.szermil.R;
import com.example.szermil.restaurant.mark.model.Mark;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class UserMarksActivity extends AppCompatActivity {

    private RecyclerView markList;
    private DatabaseReference databaseReference;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_marks);
        setUpDatabase();
        getMarkList();
    }

    private void getMarkList() {
        markList = findViewById(R.id.userMarksView);
        markList.setHasFixedSize(true);
        markList.setLayoutManager(new LinearLayoutManager(this));
    }

    private void setUpDatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference().child("marks");
        databaseReference.keepSynced(true);
    }

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseRecyclerAdapter<Mark,MarkViewHolder>firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<Mark, MarkViewHolder>
                (Mark.class,R.layout.marks_row,MarkViewHolder.class,databaseReference) {
            @Override
            protected void populateViewHolder(MarkViewHolder viewHolder, Mark model, int position) {
                viewHolder.setMeal(model.getMealName());
                viewHolder.setComment(model.getComment());
                viewHolder.setRating(model.getRating());
                viewHolder.setPhoto(model.getPhotoBase64());

            }
        };
        markList.setAdapter(firebaseRecyclerAdapter);
    }

    public static class MarkViewHolder extends RecyclerView.ViewHolder{
        View view;
        public  MarkViewHolder(View itemView){
            super(itemView);
            view=itemView;
        }

        public void setMeal(String mealName) {
            BootstrapLabel meal = view.findViewById(R.id.userMealName);
            meal.setText(mealName);
        }

        public void setComment(String comment) {
            AwesomeTextView meal = view.findViewById(R.id.userComment);
            meal.setText(comment);
        }

        public void setRating(int rating) {
            RatingBar userRating = view.findViewById(R.id.userRatingValue);
            userRating.setRating(Float.parseFloat(Integer.toString(rating)));
        }

        public void setPhoto(String photoBase64) {
            byte[] decodedString = Base64.decode(photoBase64,Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
            ImageView image = view.findViewById(R.id.userImage);
            image.setImageBitmap(decodedByte);
        }
    }
}
