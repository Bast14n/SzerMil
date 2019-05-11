package com.example.szermil;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ReadActivity extends AppCompatActivity {

    DatabaseReference mDatabase;
    ChildEventListener listener;
    ListView messagesList;
    ArrayList messages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_read);

        messages = new ArrayList();
        messagesList = findViewById(R.id.messages_list);
        ArrayAdapter adapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,android.R.id.text1,messages);
        messagesList.setAdapter(adapter);
        mDatabase = null;
        //mDatabase.child("messages").getRef();
        listener = null;
        //mDatabase.child("messages").getRef();
    }
    @Override
    protected void onDestroy(){
        super.onDestroy();
    }
}