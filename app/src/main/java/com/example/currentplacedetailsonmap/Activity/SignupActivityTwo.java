package com.example.currentplacedetailsonmap.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.currentplacedetailsonmap.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivityTwo extends AppCompatActivity {
    @BindView(R.id.btn_signup_next_two)
    Button signupButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_two);
        ButterKnife.bind(this);

        // Setup next screen
        final Intent intent = new Intent(this, DriversLicActivity.class);

        // Step one button
        signupButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(intent);
                finish();
            }
        });
    }
}
