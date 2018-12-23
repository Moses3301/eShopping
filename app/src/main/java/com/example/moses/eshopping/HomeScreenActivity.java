package com.example.moses.eshopping;

import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class HomeScreenActivity extends AppCompatActivity {
    public static final String TAG = "$HomeScreenActivity$";

    private FirebaseAuth mAuth;
    FirebaseDatabase mDatabase;
    DatabaseReference mSongsRef;

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mSongsAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private LinkedHashMap<String, Song> mSongList = new LinkedHashMap<String, Song>();
    private User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG,"onCreate() >>");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        Intent intent = getIntent();
        mUser = intent.getParcelableExtra(SignInActivity.EXTRA_USER);
        initView();
        Log.e(TAG,"onCreate() <<");
    }

    @Override
    public void onStart() {
        Log.e(TAG,"onStart() >>");
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        //updateUI(currentUser);
        Log.e(TAG,"onStart() <<");

    }

    private void initView(){
        Log.e(TAG,"initView() >>");
        mAuth = FirebaseAuth.getInstance();

        mDatabase = FirebaseDatabase.getInstance();
        mSongsRef = mDatabase.getReference("songs");
        /*
        mSongsRef.addValueEventListener(new ValueEventListener() {
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
        */
        recyclerInit();
        Log.e(TAG,"initView() <<");
    }

    private void recyclerInit() {
        Log.e(TAG,"recyclerInit() >>");

        mRecyclerView = (RecyclerView) findViewById(R.id.songs_list);

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());

        getAllSongs();
        Log.e(TAG,"recyclerInit() <<");
    }

    private void getAllSongs() {
        Log.e(TAG,"getAllSongs() >>");
        mSongList.clear();
        mSongsAdapter = new SongAdapter(mSongList,mUser);
        mRecyclerView.setAdapter(mSongsAdapter);

        //getAllSongsUsingValueListenrs();
        getAllSongsUsingChildListenrs();
        Log.e(TAG,"getAllSongs() <<");
    }

    private void getAllSongsUsingChildListenrs() {
        Log.e(TAG,"getAllSongsUsingChildListenrs() >>");
        mSongsRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChildName){
                Log.e(TAG, "onChildAdded(Songs) >> " + snapshot.getKey());
                mSongList.put(snapshot.getKey(),snapshot.getValue(Song.class));
                mRecyclerView.getAdapter().notifyDataSetChanged();
                Log.e(TAG, "onChildAdded(Songs) <<");

            }
            @Override
            public void onChildChanged(DataSnapshot snapshot, String previousChildName){

                Log.e(TAG, "onChildChanged(Songs) >> " + snapshot.getKey());

                Song song =snapshot.getValue(Song.class);
                String key = snapshot.getKey();
                mSongList.put(key,song);
                mRecyclerView.getAdapter().notifyDataSetChanged();
                Log.e(TAG, "onChildChanged(Songs) <<");

            }
            @Override
            public void onChildMoved(DataSnapshot snapshot, String previousChildName){

                Log.e(TAG, "onChildMoved(Songs) >> " + snapshot.getKey());

                Log.e(TAG, "onChildMoved(Songs) << Doing nothing");

            }
            @Override
            public void onChildRemoved(DataSnapshot snapshot){

                Log.e(TAG, "onChildRemoved(Songs) >> " + snapshot.getKey());
                String key =snapshot.getKey();
                mSongList.remove(key);
                mRecyclerView.getAdapter().notifyDataSetChanged();
                Log.e(TAG, "onChildRemoved(Songs) <<");

            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

                Log.e(TAG, "onCancelled(Songs) >>" + databaseError.getMessage());
            }
        });
        Log.e(TAG,"getAllSongsUsingChildListenrs() <<");
    }
    /*
    private void updateUI(FirebaseUser i_CurrUser){
        Log.e(TAG,"updateUI() >>");
        if (i_CurrUser.isAnonymous()) {
            mUser = null;
        } else {
            DatabaseReference myUserRef = FirebaseDatabase.getInstance().getReference("Users/" + i_CurrUser.getUid());

            myUserRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    Log.e(TAG, "onDataChange(User) >> " + snapshot.getKey());
                    mUser = snapshot.getValue(User.class);
                    Log.e(TAG, "onDataChange(User) <<");
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                    Log.e(TAG, "onCancelled(Users) >>" + databaseError.getMessage());
                }
            });
        }
        Log.e(TAG,"updateUI() <<");
    }
*/
    public void SetSignOutOnClick(View v){
        //Song creep = new Song("Creep","Pablo Honey","gs://eshopping-fe847.appspot.com/Radiohead - Creep.mp3","Radiohead",1.99,"gs://eshopping-fe847.appspot.com/Radiohead - Creep.mp4");
        //mSongsRef.push().setValue(creep);
        Log.e(TAG,"SetSignOutOnClick() <<");
    }
}
