package com.example.moses.eshopping;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v4.util.Pair;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class SongAdapter extends RecyclerView.Adapter<SongAdapter.SongViewHolder> {
    public static final String TAG = "$SongAdapter$";
    private LinkedHashMap<String, Song> mSongList = new LinkedHashMap<String, Song>();
    private User mUser;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class SongViewHolder extends RecyclerView.ViewHolder {
        public static final String TAG = "$SongViewHolder$";
        public static final String SONG_EXTRA_MESSAGE = "com.example.moses.eshopping.SongAdapter.SONG";
        public static final String USER_EXTRA_MESSAGE = "com.example.moses.eshopping.SongAdapter.USER";
        public static final String KEY_EXTRA_MESSAGE = "com.example.moses.eshopping.SongAdapter.KEY";

        private CardView mSongCardView;
        private Context mContext;
        private Song mSong;
        private String mSongKey;

        private ImageView thumbImage;
        private TextView name;
        private TextView artist;
        private TextView genre;
        private TextView price;
        private TextView reviewsCount;
        private String songFile;
        private String thumbFile;
        
        private RatingBar rating;
        private Button play;

        public SongViewHolder(final Context mContext, View view) {
            super(view);

            Log.e(TAG,"SongViewHolder() >>");

            mSongCardView = (CardView) view.findViewById(R.id.card_view_song);
            thumbImage = (ImageView) view.findViewById(R.id.song_thumb_image);
            name = (TextView) view.findViewById(R.id.song_name);
            artist = (TextView) view.findViewById(R.id.song_reviewer_mail);
            rating = (RatingBar) view.findViewById(R.id.song_rating);
            play = (Button) view.findViewById(R.id.playButton);
            genre =  (TextView) view.findViewById(R.id.song_genre);
            price =  (TextView) view.findViewById(R.id.song_price);

            this.mContext = mContext;

            mSongCardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.e(TAG, "CardView.onClick() >> name=" + mSong.getName());
                    Context context = view.getContext();
                    Intent intent = new Intent(context, PlayerActivity.class);
                    intent.putExtra(SONG_EXTRA_MESSAGE, mSong);
                    Log.e(TAG, "user=" + mUser.getId());
                    intent.putExtra(USER_EXTRA_MESSAGE, mUser);
                    Log.e(TAG, mSongKey);
                    intent.putExtra(KEY_EXTRA_MESSAGE, mSongKey);
                    context.startActivity(intent);
                }
            });

            play.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Log.e(TAG, "CardView.play.onClick() >> name=" + mSong.getName());
                }
            });
            Log.e(TAG,"SongViewHolder() <<");
        }

        public TextView getPrice() {
            return price;
        }

        public TextView getName() {
            return name;
        }

        public ImageView getThumbImage() {
            return thumbImage;
        }

        public void setSongFile(String file) {
            this.songFile = file;
        }

        public TextView getArtist() {
            return artist;
        }

        public TextView getGenre() { return genre; }

        public Context getContext() {
            return mContext;
        }

        public RatingBar getRating() {
            return rating;
        }

        public TextView getReviewsCount() {return reviewsCount;}

        public void setSong(String ikey, Song iSong) {
            Log.e(TAG,"setSong: "+ ikey);
            mSong = iSong;
            mSongKey = ikey;
            name.setText(iSong.getName());
            artist.setText(iSong.getArtist());
            genre.setText(iSong.getGenre());
            price.setText(iSong.getPrice().toString());
            try{
                Log.e(TAG,iSong.getThumbImage());
                StorageReference thumbRef = FirebaseStorage
                        .getInstance()
                        .getReference()
                        .child(iSong.getThumbImage());
                // Load the image using Glide
                Glide.with(this.mContext)
                        .load(Uri.fromFile(new File(iSong.getThumbImage())))
                        .into(thumbImage);
                Log.e(TAG,"setSong() <<");
            }
            catch (Exception e){
                Log.e(TAG,e.getMessage());
            }
        }

        public void setToPlayable() {
            price.setVisibility(View.GONE);
            play.setVisibility(View.VISIBLE);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public SongAdapter(LinkedHashMap<String, Song> iSongsList, User iUser) {
        Log.e(TAG,"SongAdapter()>>");
        mSongList = iSongsList;
        mUser = iUser;
        Log.e(TAG,iUser.getId());
        Log.e(TAG,"SongAdapter()<<");
    }

    @Override
    public SongAdapter.SongViewHolder onCreateViewHolder(ViewGroup parent,
                                                     int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_song, parent, false);
        SongViewHolder vh = new SongViewHolder(parent.getContext(),v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(SongViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Map.Entry<String,Song> songWithKey =  (Map.Entry<String,Song>) mSongList.entrySet().toArray()[position];
        String key = songWithKey.getKey();
        Log.e(TAG,key);
        Song song = songWithKey.getValue();
        holder.setSong(key,song);
        if (mUser != null){
            if (mUser.getSongList().contains(key)){
                holder.setToPlayable();
            }
        }
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mSongList.size();
    }
}