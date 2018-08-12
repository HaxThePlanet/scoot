package com.xen.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import com.xen.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivityOne extends AppCompatActivity {
    @BindView(R.id.btn_signup_next_one)
    Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_one);
        ButterKnife.bind(this);

        // Setup next screen
        final Intent intent = new Intent(this, SignupActivityTwo.class);

        // Step one button
        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
                finish();
            }
        });
    }
}
