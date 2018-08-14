package com.xen.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.shobhitpuri.custombuttons.GoogleSignInButton;
import com.xen.R;

import butterknife.ButterKnife;

public class SignupActivityOne extends AppCompatActivity {
    private static final String TAG = SignupActivityOne.class.getSimpleName();
    private static final int FACEBOOK_CALLBACK = 0;
    private static final int GOOGLE_CALLBACK = 1;

    // Facebook
    CallbackManager facebookCallbackManager;

    // Google
    GoogleSignInClient mGoogleSignInClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_one);
        ButterKnife.bind(this);

        setupGoogleSignin();

        facebookCallbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(facebookCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Log.i(TAG, "FB onSuccess()");

                startActivity(new Intent(getApplicationContext(), SignupActivityTwo.class));
                finish();
            }

            @Override
            public void onCancel() {
                // App code
                Log.i(TAG, "FB onCancel()");
            }

            @Override
            public void onError(FacebookException exception) {
                // App code
                Log.i(TAG, "FB onError()");
            }
        });
    }

    private void setupGoogleSignin() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        // Set the dimensions of the sign-in button.
        GoogleSignInButton signInButton = findViewById(R.id.sign_in_button);
        findViewById(R.id.sign_in_button).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent signInIntent = mGoogleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, GOOGLE_CALLBACK);
            }
        });
    }

    // Google signin
    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);
            // Signed in successfully.

            startActivity(new Intent(getApplicationContext(), SignupActivityTwo.class));
            finish();

            Log.i(TAG, "google login");
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.getStatusCode());

        }
    }

    // Facebook callback
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        facebookCallbackManager.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == GOOGLE_CALLBACK) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);

            Log.i(TAG, "google login");
        }
    }
}
