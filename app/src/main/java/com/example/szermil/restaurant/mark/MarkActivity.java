package com.example.szermil.restaurant.mark;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.beardedhen.androidbootstrap.BootstrapButton;
import com.beardedhen.androidbootstrap.BootstrapEditText;
import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.example.szermil.R;
import com.example.szermil.restaurant.mark.model.Mark;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

import static com.example.szermil.utils.StringUtils.RESTAURANT_ID_PARAMETER;
import static com.example.szermil.utils.StringUtils.RESTAURANT_NAME_PARAMETER;

public class MarkActivity extends AppCompatActivity {
    private Mark mark;
    private BootstrapButton photoButton;
    private BootstrapButton sendButton;
    private ImageView imageView;
    private BootstrapEditText mealName;
    private RatingBar rating;
    private BootstrapEditText comment;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mark_activity);
        getSupportActionBar().setTitle("Oceny");
        String restaurantName = getIntent().getStringExtra(RESTAURANT_NAME_PARAMETER);
        BootstrapLabel bootstrapLabel = findViewById(R.id.restaurantNameLabel);
        bootstrapLabel.setText(restaurantName);
        mark = new Mark();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        mark.setUserId(mAuth.getCurrentUser().getUid());
        getPhoto();
        sendMark();
    }

    private void getPhoto() {
        photoButton = findViewById(R.id.photoButton);
        imageView = findViewById(R.id.photo);

        photoButton.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 0);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);
        setPhoto(bitmap);
    }

    private void setPhoto(Bitmap bitmap) {
        String encoded = convertBitmapToBase64(bitmap);
        mark.setPhotoBase64(encoded);
    }

    private String convertBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
        byte[] bytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(bytes, Base64.DEFAULT);
    }

    private void sendMark() {
        mealName = findViewById(R.id.mealName);
        rating = findViewById(R.id.rate);
        comment = findViewById(R.id.comment);
        sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(v -> {
            setMark(mealName, rating, comment);
            boolean isCompleted = verifyMark(mark);
            boolean isPhotoTaken = verifyPhoto(mark);
            transferMarkOrSendAlert(isCompleted, isPhotoTaken);
        });
    }

    private void setMark(BootstrapEditText mealName, RatingBar rating, BootstrapEditText comment) {
        mark.setMealName(mealName.getText().toString());
        mark.setRating((int) rating.getRating());
        mark.setComment(comment.getText().toString());
        long restaurantId = getIntent().getLongExtra(RESTAURANT_ID_PARAMETER, 0);
        mark.setRestaurantId(restaurantId);
    }

    private boolean verifyPhoto(Mark mark) {
        boolean isPhotoTaken = false;
        if (mark.getPhotoBase64() != null) isPhotoTaken = true;

        return isPhotoTaken;
    }

    private boolean verifyMark(Mark mark) {
        boolean flag = false;
        if (!mark.getMealName().trim().isEmpty() && !mark.getComment().trim().isEmpty() && mark.getRating() != 0)
            flag = true;

        return flag;
    }

    private void transferMarkOrSendAlert(boolean isCompleted, boolean isPhotoTaken) {
        if (isCompleted && isPhotoTaken) {
            transferMark(mark);
        } else if (!isCompleted && isPhotoTaken) {
            setAlert("Nie uzupełniono wszystkich pól");
        } else if (isCompleted && !isPhotoTaken) {
            setAlert("Nie zrobiono zdjęcia");
        } else {
            setAlert("Nie zrobiono zdjęcia i nie wypełniono pól");
        }
    }

    private void setAlert(String s) {
        new AlertDialog.Builder(this)
                .setTitle("Uwaga!")
                .setMessage(s)

                .setPositiveButton("OK",
                        (dialog, which) -> dialog.cancel())
                .show();
    }

    private void transferMark(Mark mark) {
        setDatabase();
        databaseReference.child("marks").push().setValue(mark);
        finish();
    }

    private void setDatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }
}