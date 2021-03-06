package com.example.szermil.login;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.szermil.R;
import com.example.szermil.login.model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class UserDB extends AppCompatActivity {

    DatabaseReference mDatabase;

    public UserDB(DatabaseReference mDatabase) {
        this.mDatabase = mDatabase;
    }

    public void signInViaGoogleAccount(GoogleSignInAccount account, long maxId) {
        String id = Long.toString(maxId);
        String userFirstName = account.getGivenName();
        String userLastName = account.getFamilyName();
        String userEmail = account.getEmail();
        User user = new User(id, userFirstName,userLastName, userEmail);
        signIn(user);
    }

    public void signInViaFacebook(FirebaseUser account, long maxId) {
        String id = Long.toString(maxId);
        String userFullname = account.getDisplayName();
        String userEmail = account.getEmail();
        User user = new User(id, userFullname, userEmail);
        signIn(user);
    }

    public void signIn(User user) {
        mDatabase.push().setValue(user);
    }

}

