package com.example.szermil.start;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.beardedhen.androidbootstrap.BootstrapLabel;
import com.example.szermil.R;
import com.example.szermil.login.RegisterActivity;
import com.example.szermil.login.UserDB;
import com.example.szermil.login.model.User;
import com.example.szermil.restaurant.mark.MarkActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;

public class StartActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    private UserDB usersDB;
    private long maxId = 0;
    private FirebaseAuth mAuth;
    private CallbackManager mCallbackManager;
    private GoogleApiClient mGoogleApiClient;
    private GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        checkIfUserLogged();
        initializeDatabase();
        initializeFacebookElements();
        initializeGoogleElements();
        setButtonListeners();
    }

    public void initializeDatabase() {
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

    private void checkIfUserLogged() {

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();

        if(mUser != null ) {
            Intent intent = new Intent(getApplicationContext(), MarkActivity.class);
            startActivity(intent);
        }
        else {
        }
    }

    private void setButtonListeners() {
        BootstrapLabel registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
            startActivity(intent);
        });
    }

    private void updateUI() {
        Intent intent = new Intent(getApplicationContext(), MarkActivity.class);
        startActivity(intent);
    }
    //  ------ facebook sign in logic ------

    private void initializeFacebookElements() {
        mCallbackManager = CallbackManager.Factory.create();
        LoginButton facebookLoginButton = findViewById(R.id.facebookLoginBtn);
        facebookLoginButton.setPermissions("email", "public_profile");
        facebookLoginButton.setPermissions(Arrays.asList("email", "public_profile"));
    }

    public void buttonClickLoginFb(View v) {

        LoginManager.getInstance().registerCallback(mCallbackManager,
                new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        Toast.makeText(getApplicationContext(), "success logged in!", Toast.LENGTH_LONG).show();
                        handleFacebookAccessToken(loginResult.getAccessToken());
                        updateUI();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(getApplicationContext(), "user cancelled it", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void onError(FacebookException exception) {
                        Toast.makeText(getApplicationContext(), "error " + exception.toString() , Toast.LENGTH_LONG).show();

                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                    } else {
                        Toast.makeText(getApplicationContext(), "sign in filed", Toast.LENGTH_LONG).show();

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
            }
        } else {
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }


// ------  Google sign in logic ------

    private void initializeGoogleElements() {
        SignInButton signInButton = findViewById(R.id.googleSignInButton);
        signInButton.setOnClickListener(this);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getApplicationContext(), "logged in!",
                                Toast.LENGTH_LONG).show();
                        updateUI();
                    } else {
                        Toast.makeText(getApplicationContext(), "error", Toast.LENGTH_LONG).show();
                    }
                });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.googleSignInButton:
                signIn();
                break;
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(getApplicationContext(), "error!",
                Toast.LENGTH_LONG).show();
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    // ------ password email auth ------


    public void initializeEmailPasswordLogin(String email, String password) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password);
    }

    public void signOutUser() {
        FirebaseAuth.getInstance().signOut();
    }
}