package com.example.moses.eshopping;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class LogInActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    public static final String TAG = "$LogInActivity$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG,"onCreate() >>");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        initViews();
        Log.e(TAG,"onCreate() <<");
    }

    private void initViews(){
        Log.e(TAG,"initViews() >>");
        mAuth = FirebaseAuth.getInstance();
        Log.e(TAG,"initViews() <<");
    }

    public void SetCreateAccountOnClick(View v){
        Log.e(TAG,"SetCreateAccountOnClick() >>");
        Intent intent = new Intent(this,CreateAccountActivity.class);
        startActivity(intent);
        Log.e(TAG,"SetCreateAccountOnClick() <<");
    }

    public void SetSignInOnClick(View v){
        Log.e(TAG,"SetSignInOnClick() >>");
        Intent intent = new Intent(this,SignInActivity.class);
        startActivity(intent);
        Log.e(TAG,"SetSignInOnClick() <<");
    }

    @Override
    public void onStart() {
        Log.e(TAG,"onStart() >>");
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        Log.e(TAG,"UserLogin: " + currentUser);
        if ( currentUser != null){
            Intent intent = new Intent(this,HomeScreenActivity.class);
            startActivity(intent);
            finish();
        }
        Log.e(TAG,"onStart() <<");
    }
}
