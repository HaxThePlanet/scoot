package com.xen.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.xen.R;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivityOne extends AppCompatActivity {
    private static final String TAG = SignupActivityOne.class.getSimpleName();

    public static final String EXTRA_MESSAGE = "MESSAGE";
    @BindView(R.id.btn_signup_next_one)
    Button signupButton;

    // Facebook
    CallbackManager callbackManager;
    private static final String EMAIL = "email";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_one);
        ButterKnife.bind(this);

//        try {
//            PackageInfo info = getPackageManager().getPackageInfo("com.xen", PackageManager.GET_SIGNATURES);
//            for (Signature signature : info.signatures) {
//                MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
//            }
//        } catch (PackageManager.NameNotFoundException e) {
//
//
//        }

        callbackManager = CallbackManager.Factory.create();

        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
                    @Override
                    public void onSuccess(LoginResult loginResult) {
                        // App code
                        Log.i(TAG, "FB onSuccess()");
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

        // Setup next screen
        final Intent intent = new Intent(this, SignupActivityTwo.class);
//        String message = et_email.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);

        // Step one button
        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
                finish();
            }
        });
    }

    // Facebook callback
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
