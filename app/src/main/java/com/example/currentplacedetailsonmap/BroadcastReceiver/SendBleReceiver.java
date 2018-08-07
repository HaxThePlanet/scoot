package com.example.currentplacedetailsonmap.BroadcastReceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.example.currentplacedetailsonmap.base.BleBase;
import com.example.currentplacedetailsonmap.base.BleStatus;
import com.google.android.gms.maps.model.LatLng;

public class SendBleReceiver extends BroadcastReceiver {
    public Context context;
    private Boolean isregister = Boolean.valueOf(false);
    public BLESendListener mListener;
    private BLELocationListener mLocation;

    public interface BLELocationListener {
        void onLocation(LatLng latLng);
    }

    public interface BLESendListener {
        void Changes(BleBase bleBase, BleStatus bleStatus);

        void settingUp(int i, Boolean bool);
    }

    public SendBleReceiver(Context context, BLESendListener mListener) {
        this.mListener = mListener;
        this.context = context;
    }

    public void registerReceiver() {
        if (!this.isregister.booleanValue()) {
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(SendBleBroadcast.ACTION_Changes);
            intentFilter.addAction(SendBleBroadcast.ACTION_ReName);
            this.context.registerReceiver(this, intentFilter);
            this.isregister = Boolean.valueOf(true);
        }
    }

    public void registerReceiver(BLELocationListener Location) {
        if (!this.isregister.booleanValue()) {
            this.mLocation = Location;
            IntentFilter intentFilter = new IntentFilter();
            intentFilter.addAction(SendBleBroadcast.ACTION_Changes);
            intentFilter.addAction(SendBleBroadcast.ACTION_ReName);
            intentFilter.addAction(SendBleBroadcast.ACTION_Location);
            this.context.registerReceiver(this, intentFilter);
            this.isregister = Boolean.valueOf(true);
        }
    }

    public void onDestroy() {
        if (this.isregister.booleanValue()) {
            this.context.unregisterReceiver(this);
            this.isregister = Boolean.valueOf(false);
        }
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(SendBleBroadcast.ACTION_Changes)) {
            this.mListener.Changes((BleBase) intent.getParcelableExtra(SendBleBroadcast.ACTION_Changes_base), (BleStatus) intent.getParcelableExtra(SendBleBroadcast.ACTION_Changes_state));
        } else if (intent.getAction().equals(SendBleBroadcast.ACTION_ReName)) {
            this.mListener.settingUp(intent.getIntExtra(SendBleBroadcast.ACTION_ReName_code, -1), Boolean.valueOf(intent.getBooleanExtra(SendBleBroadcast.ACTION_ReName_state, false)));
        } else if (intent.getAction().equals(SendBleBroadcast.ACTION_Location) && this.mLocation != null) {
            this.mLocation.onLocation((LatLng) intent.getParcelableExtra(SendBleBroadcast.ACTION_Location_latlng));
        }
    }
}
