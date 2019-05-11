package com.example.szermil;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.firebase.database.DatabaseReference;

public class UsersDB extends AppCompatActivity {

    DatabaseReference mDatabase;

    public UsersDB(DatabaseReference mDatabase) {
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

    public void signIn(User user) {
        mDatabase.push().setValue(user)
                .addOnFailureListener(e -> Toast.makeText(getApplication().getBaseContext(),
                        R.string.failure_send, Toast.LENGTH_SHORT).show())
                .addOnSuccessListener(aVoid -> Toast.makeText(getApplication().getBaseContext(),
                        R.string.success_send, Toast.LENGTH_SHORT).show());
    }

}
