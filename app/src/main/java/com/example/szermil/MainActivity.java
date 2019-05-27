package com.example.szermil;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.example.szermil.restaurant.mark.MarkActivity;
import com.example.szermil.restaurant_search.RestaurantSearchActivity;
import com.example.szermil.start.StartActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            Intent intent = new Intent(getApplicationContext(), MarkActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(getApplicationContext(), StartActivity.class);
            startActivity(intent);
        }
        createToolbar();
    }

    public void createToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigationDrawerOpen, R.string.navigationDrawerClose);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

//        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
//        ft.replace(new LoginFragment())
//        ft.commit();

    }

    @Override
    public void onBackPressed() {
        if(drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.nav_restaurant:
                intent = new Intent(getApplicationContext(), RestaurantSearchActivity.class);
                startActivity(intent);
                drawer.closeDrawer(GravityCompat.START);
                break;
            case R.id.nav_search:
                intent = new Intent(getApplicationContext(), RestaurantSearchActivity.class);
                drawer.closeDrawer(GravityCompat.START);
                startActivity(intent);
                break;
            case R.id.nav_signOut:
                FirebaseAuth.getInstance().signOut();
                intent = new Intent(getApplicationContext(), StartActivity.class);
                drawer.closeDrawer(GravityCompat.START);
                startActivity(intent);
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
