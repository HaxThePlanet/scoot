package com.xen.Util;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

public class BleTool {
    public static final int requestCode = 8989;
    public BluetoothAdapter adapter;
    public Context mContext;

    public BleTool(Context mContext) {
        this.mContext = mContext;
        this.adapter = ((BluetoothManager) mContext.getSystemService("bluetooth")).getAdapter();
    }

    public static String ByteToString(byte[] data) {
        if (data == null || data.length == 0) {
            return "";
        }
        StringBuilder stringBuilder = new StringBuilder(data.length);
        int length = data.length;
        for (int i = 0; i < length; i++) {
            stringBuilder.append(String.format("%02X ", new Object[]{Byte.valueOf(data[i])}));
        }
        return stringBuilder.toString();
    }

    public boolean hasBleOpen() {
        return this.mContext.getPackageManager().hasSystemFeature("android.hardware.bluetooth_le");
    }

    public boolean isBleOpen() {
        if (this.adapter == null) {
            return false;
        }
        return this.adapter.isEnabled();
    }

    public boolean openBLE() {
        if (this.adapter == null) {
            return false;
        }
        return this.adapter.enable();
    }

    public void sysOpenBLE(AppCompatActivity activity) {
        activity.startActivityForResult(new Intent("android.bluetooth.adapter.action.REQUEST_ENABLE"), requestCode);
    }
}
