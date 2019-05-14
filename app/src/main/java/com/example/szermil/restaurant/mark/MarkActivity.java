package com.example.szermil.restaurant.mark;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.szermil.R;
import com.example.szermil.restaurant.mark.model.Mark;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MarkActivity extends AppCompatActivity {
    Mark mark;
    Button photoButton;
    Button sendButton;
    ImageView imageView;
    EditText mealName;
    EditText rating;
    EditText comment;
    private DatabaseReference databaseReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mark_activity);
        mark = new Mark();
        getPhoto();
        sendMark();
    }

    private void getPhoto(){
        photoButton = findViewById(R.id.photoButton);
        imageView = findViewById(R.id.photo);

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent,0);
            }
        });
    }

    private void sendMark(){
        mealName = findViewById(R.id.mealName);
        rating = findViewById(R.id.rate);
        comment = findViewById(R.id.comment);
        sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMark(mealName,rating,comment);
                transferMark(mark);
            }
        });
    }

    private void transferMark(Mark mark) {
        setDatabase();
        databaseReference.child("category").push().setValue(mark);
        finish();
    }

    private void setDatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void setMark(EditText mealName, EditText rating, EditText comment) {
        mark.setMealName(mealName.getText().toString());
        mark.setRaiting(Integer.parseInt(rating.getText().toString()));
        mark.setComment(comment.getText().toString());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap)data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
        mark.setPhoto(bitmap);
    }
}