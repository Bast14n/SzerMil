package com.example.szermil.restaurant.mark.details;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import static com.example.szermil.utils.StringUtils.MEAL_NAME_PARAMETER;
import static com.example.szermil.utils.StringUtils.USER_COMMENT_PARAMETER;
import static com.example.szermil.utils.StringUtils.USER_RATING_PARAMETER;
import static com.example.szermil.utils.StringUtils.USER_PHOTO_BASE64_PARAMETER;

import com.example.szermil.R;

public class MarkDetails extends AppCompatActivity {
    TextView mealName;
    RatingBar ratingBar;
    ImageView imageView;
    TextView comment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mark_detail);
        getFields();
        setDetailModelFromPreviousActivity();
    }
    private void getFields(){
        mealName = (TextView)findViewById(R.id.mealNameDetails);
        ratingBar = (RatingBar)findViewById(R.id.userRatingDetails);
        imageView = (ImageView)findViewById(R.id.photoDetails);
        comment = (TextView)findViewById(R.id.commentDetails);
    }

    private void setDetailModelFromPreviousActivity(){
        Intent previousIntent = getIntent();
        String previousMealName = previousIntent.getStringExtra(MEAL_NAME_PARAMETER);
        int previousRating = previousIntent.getIntExtra(USER_RATING_PARAMETER,0);
        String previousPhotoBase64 = previousIntent.getStringExtra(USER_PHOTO_BASE64_PARAMETER);
        String previousComment = previousIntent.getStringExtra(USER_COMMENT_PARAMETER);

        ratingBar.setRating(previousRating);
        mealName.setText(previousMealName);

        byte[] decodedString = Base64.decode(previousPhotoBase64,Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString,0,decodedString.length);
        imageView.setImageBitmap(decodedByte);

        comment.setText(previousComment);
    }
}
