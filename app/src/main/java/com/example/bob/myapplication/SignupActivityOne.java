package com.example.bob.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivityOne extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "MESSAGE";
    @BindView(R.id.btn_signup_next_one)
    Button signupButton;

    @BindView(R.id.et_email)
    EditText et_email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_one);
        ButterKnife.bind(this);

        // Setup next screen
        final Intent intent = new Intent(this, SignupActivityTwo.class);
        String message = et_email.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);

        // Step one button
        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
                finish();
            }
        });
    }
}
