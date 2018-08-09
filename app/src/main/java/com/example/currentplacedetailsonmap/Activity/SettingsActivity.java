package com.example.currentplacedetailsonmap.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.currentplacedetailsonmap.R;

import butterknife.ButterKnife;


public class SettingsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

    }
}
