package com.example.szermil.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.szermil.R;
import com.example.szermil.login.model.User;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
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
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener, View.OnClickListener {

    UserDB usersDB;
    EditText email;
    EditText firstName;
    EditText lastName;
    EditText password;
    Button registerButton;
    Button signOutButton;
    GoogleSignInOptions gso;
    SignInButton signInButton;
    DatabaseReference mDatabase;
    CallbackManager callbackManager;
    // LoginButton facebookLoginButton;
    GoogleApiClient mGoogleApiClient;
    GoogleSignInClient mGoogleSignInClient;

    private long maxId = 0;
    private static final int RC_SIGN_IN = 9001;
    private static final String TAG = "SignInActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
       // bindFacebookElements();
        bindDefaultElements();
        bindGoogleElements();
        bindDatabaseElements();
        bindServicesAuthenticationButtons();

        mDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    maxId=(dataSnapshot.getChildrenCount());
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    public void bindDatabaseElements() {
        mDatabase = FirebaseDatabase.getInstance().getReference("users");
        usersDB = new UserDB(mDatabase);

        // moze byc potrzeba wywolywania pozniej
        callbackManager = CallbackManager.Factory.create();
    }

    public void bindGoogleElements() {
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    public void bindFacebookElements() {
        FacebookSdk.sdkInitialize(getApplicationContext());
        AppEventsLogger.activateApp(this);
    }

    public void bindServicesAuthenticationButtons() {
        signInButton = findViewById(R.id.googleSignInButton);
        signOutButton = findViewById(R.id.signOutButton);
        // facebookLoginButton = findViewById(R.id.facebookLoginButton);
        signInButton.setOnClickListener(this);
        signOutButton.setOnClickListener(this);
        registerButton.setOnClickListener(view -> createUserFromFormData());
        // facebookLoginButton.setPermissions(Arrays.asList("email"));
    }

    public void bindDefaultElements() {
        email = findViewById(R.id.mail);
        password = findViewById(R.id.password);
        lastName = findViewById(R.id.lastName);
        firstName = findViewById(R.id.firstName);
        registerButton = findViewById(R.id.registerButton);
    }

    public void buttonClickLoginFacebook(View v) {
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                //handleFacebookToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "User canceled it", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            usersDB.signInViaGoogleAccount(account, maxId);
        } catch (ApiException e) {
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.googleSignInButton:
                signIn();
                break;
            case R.id.signOutButton:
                signOut();
                break;
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(status ->
                System.out.println("Sign out status: " + status.toString()));
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Log.d(TAG, "onConnectionFailed" + connectionResult);
    }

    private void createUserFromFormData() {
        String id = Long.toString(maxId);
        String userFirstName = firstName.getText().toString();
        String userLastName = lastName.getText().toString();
        String userEmail = email.getText().toString();
        String userPassword = password.getText().toString();
        User user = new User(id, userFirstName,userLastName, userEmail, userPassword);

        usersDB.signIn(user);
    }
}
