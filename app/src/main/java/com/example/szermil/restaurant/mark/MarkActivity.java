package com.example.szermil.restaurant.mark;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.example.szermil.R;
import com.example.szermil.restaurant.mark.model.Mark;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.ByteArrayOutputStream;

public class MarkActivity extends AppCompatActivity {
    Mark mark;
    Button photoButton;
    Button sendButton;
    ImageView imageView;
    EditText mealName;
    RatingBar rating;
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

    private void getPhoto() {
        photoButton = findViewById(R.id.photoButton);
        imageView = findViewById(R.id.photo);

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(intent, 0);
            }
        });
    }

    private void sendMark() {
        mealName = findViewById(R.id.mealName);
        rating = findViewById(R.id.rate);
        comment = findViewById(R.id.comment);
        sendButton = findViewById(R.id.sendButton);

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setMark(mealName, rating, comment);
                boolean isCompleted = verifyMark(mark);
                transferMarkOrSendAlert(isCompleted);
            }
        });
    }

    private void transferMarkOrSendAlert(boolean isCompleted) {
        if (isCompleted) {
            transferMark(mark);
        } else {
            sendAlert();
        }
    }

    private void sendAlert() {
        new AlertDialog.Builder(this)
                .setTitle("Uwaga!")
                .setMessage("Nie uzupełniono wszystkich pól")

                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                            }
                        })
                .show();
    }

    private boolean verifyMark(Mark mark) {
        boolean flag = false;
        if (!mark.getMealName().trim().isEmpty() && !mark.getComment().trim().isEmpty() && mark.getRaiting() != 0)
            flag = true;

        return flag;
    }

    private void transferMark(Mark mark) {
        setDatabase();
        databaseReference.child("marks").push().setValue(mark);
        finish();
    }

    private void setDatabase() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    private void setMark(EditText mealName, RatingBar rating, EditText comment) {
        mark.setMealName(mealName.getText().toString());
        mark.setRaiting((int) rating.getRating());
        mark.setComment(comment.getText().toString());
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
}