package com.example.moses.eshopping;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {
    public static final String TAG = "$SignInActivity$";
    private static final int RC_SIGN_IN = 1001;
    private FirebaseAuth mAuth;
    private GoogleSignInClient mGoogleSignInClient;
    TextView m_Email;
    TextView m_Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.e(TAG,"onCreate() >>");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        initViews();
        Log.e(TAG,"onCreate() <<");

    }

    private void initViews(){
        Log.e(TAG,"initViews() >>");
        mAuth = FirebaseAuth.getInstance();
        m_Email = findViewById(R.id.emailEditText);
        m_Password = findViewById(R.id.passwordEditText);
        //m_GoogleSignIn = findViewById(R.id.googleSignInButton);
        googleSigninInit();
        Log.e(TAG,"initViews() <<");
    }

    private void googleSigninInit() {
        Log.e(TAG, "googleSigninInit() >>" );
        GoogleSignInOptions gso = new GoogleSignInOptions
                .Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestProfile()
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        findViewById(R.id.googleSignInButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onGooglesignIn();
            }
        });
        Log.e(TAG, "googleSigninInit() <<" );
    }

    public void onGooglesignIn(){
        Log.e(TAG,"onGooglesignIn() >>");
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
        Log.e(TAG,"onGooglesignIn() <<");
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.e(TAG,"onActivityResult() >>");
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        else{
            Log.e(TAG,"!!!!else!!!!");
            mCallbackManager.onActivityResult(requestCode, resultCode, data);
        }

        Log.e(TAG,"onActivityResult() <<");
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        Log.e(TAG,"handleSignInResult() >>");
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully, show authenticated UI.
            Log.e(TAG,account.getEmail().toString());
            firebaseAuthWithGoogle(account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.e(TAG, "signInResult:failed code=" + e.getStatusCode());
        }
        Log.e(TAG,"handleSignInResult() <<");
    }

    public void SetSignInOnClick(View v){
        Log.e(TAG,"SetSignInOnClick() >>");
        String email = m_Email.getText().toString();
        String password = m_Password.getText().toString();
        if (m_Email.getText().toString().isEmpty()){
            Toast.makeText(SignInActivity.this, R.string.please_enter_email,
                    Toast.LENGTH_SHORT).show();
        }
        if (m_Password.getText().toString().isEmpty()){
            Toast.makeText(SignInActivity.this, R.string.please_enter_password,
                    Toast.LENGTH_SHORT).show();
        }
        else {
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.e(TAG, "signInWithEmail:success");
                                finish();
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.e(TAG, "signInWithEmail:failure", task.getException());
                                FirebaseAuthException error = (FirebaseAuthException)task.getException();
                                String errorCode = error.getErrorCode();
                                String message = getResources().getString(R.string.authentication_failed);
                                switch (errorCode){
                                    case "ERROR_WRONG_PASSWORD":
                                        message = getResources().getString(R.string.wrong_password);
                                        break;
                                    case "ERROR_USER_NOT_FOUND":
                                        message = getResources().getString(R.string.user_not_found);
                                        break;
                                }
                                Toast.makeText(SignInActivity.this, message,
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        }
        Log.e(TAG,"SetSignInOnClick() <<");
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e(TAG, "signInWithCredential:success");
                            finish();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.e(TAG, "signInWithCredential:failure", task.getException());
                        }
                    }
                });
    }

}
