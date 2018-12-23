package com.example.moses.eshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.VideoView;

import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PlayerActivity extends AppCompatActivity {
    public static final String TAG = "$PlayerActivity$";
    private VideoView mVideoView;
    private ImageView mAlbumCover;
    private TextView mName;
    private TextView mArtist;
    private TextView mAlbumName;
    private TextView mGenere;
    private TextView mPrice;
    private ProgressBar mProgressBar;
    private Button mPlay;
    private Button mClip;
    private RecyclerView mReviews;

    Song mSong;
    String mKey;
    User mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG, "onCreate() >>");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        initViews();
        Intent intent = getIntent();
        mSong = intent.getParcelableExtra(SongAdapter.SongViewHolder.SONG_EXTRA_MESSAGE);
        mUser = intent.getParcelableExtra(SongAdapter.SongViewHolder.USER_EXTRA_MESSAGE);
        mKey = intent.getStringExtra(SongAdapter.SongViewHolder.KEY_EXTRA_MESSAGE);
        updateUI();
        Log.e(TAG, "onCreate() <<");
    }

    public void PlayOnClick(View view) {
        Log.e(TAG, "buyPlay.onClick() >> file=" + mSong.getName());
            Log.e(TAG, "buyPlay.onClick() >> Purchase the song");
            try{
                Log.e(TAG, mUser.getSongList().toString());
                mUser.getSongList().add(mKey);
                Log.e(TAG, mKey.toString());
                Log.e(TAG, mUser.getSongList().toString());
                DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("Users");
                userRef.child(mUser.getId()).setValue(mUser);
            }
            catch (Exception ex){
                Log.e(TAG, ex.getMessage());
            }

        Log.e(TAG, "playSong.onClick() <<");
    }

    private void updateUI() {
        Log.e(TAG,"updateUI");
        mName.setText(mSong.getName());
        mArtist.setText(mSong.getArtist());
        mAlbumName.setText(mSong.getAlbum());
        mGenere.setText(mSong.getGenre());
        mPrice.setText(mSong.getPrice().toString());
        Log.e(TAG,"updateUI");
    }

    private void initViews(){
        Log.e(TAG,"initViews() >>");
        mVideoView = findViewById(R.id.videoView);
        mAlbumCover = findViewById(R.id.albumCoverImageView);
        mName = findViewById(R.id.nameTextView);
        mArtist = findViewById(R.id.atristTextView);
        mAlbumName = findViewById(R.id.albumTextView);
        mGenere = findViewById(R.id.genereTextView);
        mPrice = findViewById(R.id.priceTextView);
        mProgressBar = findViewById(R.id.progressBar);
        mPlay = findViewById(R.id.playButton);
        mClip = findViewById(R.id.clipButton);
        mReviews = findViewById(R.id.review_list);
        Log.e(TAG,"initViews() <<");
    }
}
