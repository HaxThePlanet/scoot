package com.xen.Activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.xen.R;
import com.stripe.android.model.Card;
import com.stripe.android.view.CardInputWidget;

import butterknife.ButterKnife;


public class PaymentsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payments);
        ButterKnife.bind(this);

        CardInputWidget mCardInputWidget = (CardInputWidget) findViewById(R.id.card_input_widget);

        Card cardToSave = mCardInputWidget.getCard();
        if (cardToSave == null) {

        }
    }
}
