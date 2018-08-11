package com.xen.BroadcastReceivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.xen.BluetoothBase.BleBase;

public class ReadBleReceiver extends BroadcastReceiver {
    public Context context;
    public BLEReadListener mListener;

    public ReadBleReceiver(Context context, BLEReadListener mListener) {
        this.mListener = mListener;
        this.context = context;
    }

    public void registerReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ReadBleBroadcast.ACTION_CONNECT);
        intentFilter.addAction(ReadBleBroadcast.ACTION_DISCONNECT);
        intentFilter.addAction(ReadBleBroadcast.ACTION_WriteData);
        intentFilter.addAction(ReadBleBroadcast.ACTION_ReName);
        this.context.registerReceiver(this, intentFilter);
    }

    public void onDestroy() {
        try {
            this.context.unregisterReceiver(this);
        } catch (Exception e) {
        }
    }

    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(ReadBleBroadcast.ACTION_CONNECT)) {
            this.mListener.connect((BleBase) intent.getParcelableExtra(ReadBleBroadcast.ACTION_CONNECT_BleBase));
        } else if (intent.getAction().equals(ReadBleBroadcast.ACTION_DISCONNECT)) {
            this.mListener.disconnect();
        } else if (intent.getAction().equals(ReadBleBroadcast.ACTION_ReName)) {
            this.mListener.renameBLE(intent.getStringExtra(ReadBleBroadcast.ACTION_ReName_Name));
        } else if (intent.getAction().equals(ReadBleBroadcast.ACTION_WriteData)) {
            this.mListener.LostWriteData(intent.getByteArrayExtra(ReadBleBroadcast.ACTION_WriteData_data));
        }
    }

    public interface BLEReadListener {
        void LostWriteData(byte[] bArr);

        boolean connect(BleBase bleBase);

        void disconnect();

        void renameBLE(String str);
    }
}
