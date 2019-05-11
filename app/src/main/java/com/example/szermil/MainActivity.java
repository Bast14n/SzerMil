package com.example.szermil;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button writeButton = findViewById(R.id.button_write);
        Button readButton = findViewById(R.id.button_read);

        writeButton.setOnClickListener(view -> {
            Intent intent = new Intent(this,WriteActivity.class);
            startActivity(intent);
        });

        readButton.setOnClickListener(view -> {
            Intent intent = new Intent(this,ReadActivity.class);
            startActivity(intent);
        });
    }
}
