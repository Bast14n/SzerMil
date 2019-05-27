package com.example.szermil.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import com.example.szermil.R;
import com.example.szermil.login.model.User;
import com.example.szermil.restaurant.mark.MarkActivity;
import com.example.szermil.start.StartActivity;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity{

    private EditText email, firstName, lastName, password;
    private UserDB usersDB;
    private FirebaseAuth mAuth;
    private String userFirstName, userLastName, userEmail, userPassword, id;
    private long maxId = 0;
    private static final String TAG = "SignInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AppEventsLogger.activateApp(getApplication());
        setContentView(R.layout.activity_register);
        bindDefaultElements();
        initialize();
    }

    private void initialize() {
        mAuth = FirebaseAuth.getInstance();
        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference("users");
        usersDB = new UserDB(mDatabase);
        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    maxId=(dataSnapshot.getChildrenCount() + 1);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void bindDefaultElements() {
        email = findViewById(R.id.mail);
        password = findViewById(R.id.password);
        lastName = findViewById(R.id.lastName);
        firstName = findViewById(R.id.firstName);
        Button registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(view -> createUserFromFormData());
    }

    private void createUserFromFormData() {
        boolean isEmptyFlag = true;

        if(firstName.getText().toString().isEmpty() || !isEmptyFlag ) {
            Log.d(TAG, "Wpisz imię!");
            isEmptyFlag = false;
        } else {
            userFirstName = firstName.getText().toString();
        }
        if(lastName.getText().toString().isEmpty() || !isEmptyFlag ) {
            Log.d(TAG, "Wpisz imię!");
            isEmptyFlag = false;
        } else {
            userLastName = lastName.getText().toString();
        }
        if(firstName.getText().toString().isEmpty() || !isEmptyFlag ) {
            Log.d(TAG, "Wpisz imię!");
            isEmptyFlag = false;
        } else {
            userEmail = email.getText().toString();
        }
        if(firstName.getText().toString().isEmpty() || !isEmptyFlag ) {
            Log.d(TAG, "Wpisz imię!");
            isEmptyFlag = false;
        } else {
            userPassword = password.getText().toString();
        }

        if(isEmptyFlag) {
            try {
                StartActivity sa = new StartActivity();
                mAuth.createUserWithEmailAndPassword(userEmail, userPassword);
                sa.initializeEmailPasswordLogin(userEmail,userPassword );
                updateUI();
            }
            catch (Exception e) {
                Log.d(TAG, "sign up filed: " + e.toString());
            }
        }
    }

    private void updateUI() {
        Intent intent = new Intent(RegisterActivity.this, MarkActivity.class);
        startActivity(intent);
    }
}