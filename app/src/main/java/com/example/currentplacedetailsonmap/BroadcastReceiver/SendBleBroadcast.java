package com.example.currentplacedetailsonmap.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.os.Process;

import com.example.currentplacedetailsonmap.base.BleBase;
import com.example.currentplacedetailsonmap.base.BleStatus;
import com.google.android.gms.maps.model.LatLng;

public class SendBleBroadcast {
    public static final int myPid = Process.myPid();
    public static final String ACTION_Changes = ("S_ACTION_Changes" + myPid);
    public static final String ACTION_Changes_base = ("S_ACTION_Changes_base" + myPid);
    public static final String ACTION_Changes_state = ("S_ACTION_Changes_state" + myPid);
    public static final String ACTION_Location = ("S_ACTION_Location" + myPid);
    public static final String ACTION_Location_latlng = ("S_ACTION_Location_latlng" + myPid);
    public static final String ACTION_ReName = ("S_ACTION_ReName" + myPid);
    public static final String ACTION_ReName_code = ("S_ACTION_ReName_code" + myPid);
    public static final String ACTION_ReName_state = ("S_ACTION_ReName_state" + myPid);
    public static int code_reName = 1;
    public static int code_rePW = 2;

    public static void Changes(Context context, BleBase mBleBase, BleStatus mBleStatus) {
        Intent intent = new Intent();
        intent.setAction(ACTION_Changes);
        intent.putExtra(ACTION_Changes_base, mBleBase);
        intent.putExtra(ACTION_Changes_state, mBleStatus);
        context.sendBroadcast(intent);
    }

    public static void settingUp(Context context, int Code, Boolean isstate) {
        Intent intent = new Intent();
        intent.setAction(ACTION_ReName);
        intent.putExtra(ACTION_ReName_code, Code);
        intent.putExtra(ACTION_ReName_state, isstate);
        context.sendBroadcast(intent);
    }

    public static void Location(Context context, LatLng mlatlng) {
        Intent intent = new Intent();
        intent.setAction(ACTION_Location);
        intent.putExtra(ACTION_Location_latlng, mlatlng);
        context.sendBroadcast(intent);
    }
}
