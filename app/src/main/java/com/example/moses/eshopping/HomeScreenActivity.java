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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeScreenActivity extends AppCompatActivity {
    public static final String TAG = "$HomeScreenActivity$";
    FirebaseAuth mAuth;

    ImageView m_Avatar;
    Button m_SignOut;
    TextView m_Info;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG,"onCreate() >>");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);
        initView();
        Log.e(TAG,"onCreate() <<");
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
        m_Avatar = findViewById(R.id.avatarImageView);
        m_SignOut = findViewById(R.id.signOutButton);
        m_Info = findViewById(R.id.infoTextView);
        Log.e(TAG,"initView() <<");
    }

    private void updateUI(FirebaseUser i_CurrUser){
        Log.e(TAG,"updateUI() >>");
        String info = i_CurrUser.isAnonymous() ? "SIGNED OUT\n" : "SIGNED IN\n";
        info += i_CurrUser.getEmail() == null ? "" : (i_CurrUser.getEmail() + "\n");
        info += i_CurrUser.getDisplayName() == null ? "" : (i_CurrUser.getDisplayName() + "\n");
        info += i_CurrUser.getPhoneNumber() == null ? "" : (i_CurrUser.getPhoneNumber() + "\n");
        info += i_CurrUser.getUid() == null ? "" : (i_CurrUser.getUid() + "\n");
        m_Info.setText(info);
        if (i_CurrUser.getPhotoUrl() != null){
            Glide.with(this)
                    .load(i_CurrUser.getPhotoUrl().toString())
                    .into(m_Avatar);
        }
        Log.e(TAG,"updateUI() <<");
    }

    public void SetSignOutOnClick(View v){
        mAuth.signOut();
        Intent intent = new Intent(this, LogInActivity.class);
        startActivity(intent);
        finish();
    }
}
