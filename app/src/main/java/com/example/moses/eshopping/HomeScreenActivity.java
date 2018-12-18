package com.example.moses.eshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeScreenActivity extends AppCompatActivity {
    public static final String TAG = "$HomeScreenActivity$";
    private FirebaseAuth mAuth;
    FirebaseDatabase database;
    DatabaseReference songsRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG,"onCreate() >>");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initView();
    }
    @Override
    public void onStart() {
        Log.e(TAG,"onStart() >>");
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
        Log.e(TAG,"onStart() <<");
    }

    private void initView(){
        Log.e(TAG,"initView() >>");
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();

        songsRef = database.getReference("songs");
        songsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // This method is called once with the initial value and again
                // whenever data at this location is updated.
                //String value = dataSnapshot.getValue(String.class);
                Log.e(TAG, "Value is: " + dataSnapshot.toString());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.e(TAG, "Failed to read value.", error.toException());
            }
        });

        Log.e(TAG,"initView() <<");
    }

    private void updateUI(FirebaseUser i_CurrUser){
        Log.e(TAG,"updateUI() >>");

        Log.e(TAG,"updateUI() <<");
    }

    public void SetSignOutOnClick(View v){
        Song creep = new Song("Creep","Pablo Honey","gs://eshopping-fe847.appspot.com/Radiohead - Creep.mp3","Radiohead",1.99,"gs://eshopping-fe847.appspot.com/Radiohead - Creep.mp4");
        songsRef.push().setValue(creep);
        Log.e(TAG,"SetSignOutOnClick() <<");
    }
}
