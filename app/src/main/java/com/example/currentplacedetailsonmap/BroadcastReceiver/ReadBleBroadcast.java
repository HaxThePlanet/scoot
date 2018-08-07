package com.example.currentplacedetailsonmap.BroadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.os.Process;

import com.example.currentplacedetailsonmap.Data.SendDataAnalysis;
import com.example.currentplacedetailsonmap.base.BleBase;

public class ReadBleBroadcast {
    public static final int myPid = Process.myPid();
    public static final String ACTION_CONNECT = ("R_ACTION_CONNECT" + myPid);
    public static final String ACTION_CONNECT_BleBase = ("R_ACTION_CONNECT_ADDRESS" + myPid);
    public static final String ACTION_DISCONNECT = ("R_ACTION_DISCONNECT" + myPid);
    public static final String ACTION_ReName = ("R_ACTION_ReName" + myPid);
    public static final String ACTION_ReName_Name = ("R_ACTION_ReName_Name" + myPid);
    public static final String ACTION_WriteData = ("R_ACTION_WriteData" + myPid);
    public static final String ACTION_WriteData_data = ("R_ACTION_WriteData_data" + myPid);

    public static void disconnect(Context context) {
        Intent intent = new Intent();
        intent.setAction(ACTION_DISCONNECT);
        context.sendBroadcast(intent);
    }

    public static void connect(Context context, BleBase mBleBase) {
        Intent intent = new Intent();
        intent.setAction(ACTION_CONNECT);
        intent.putExtra(ACTION_CONNECT_BleBase, mBleBase);
        context.sendBroadcast(intent);
    }

    public static void rename(Context context, String name) {
        Intent intent = new Intent();
        intent.setAction(ACTION_ReName);
        intent.putExtra(ACTION_ReName_Name, name);
        context.sendBroadcast(intent);
    }

    public static void rePW(Context context, String pw) {
        Intent intent = new Intent();
        intent.setAction(ACTION_WriteData);
        intent.putExtra(ACTION_WriteData_data, SendDataAnalysis.rePw(pw));
        context.sendBroadcast(intent);
    }

    public static void VerifyPW(Context context, String pw) {
        Intent intent = new Intent();
        intent.setAction(ACTION_WriteData);
        intent.putExtra(ACTION_WriteData_data, SendDataAnalysis.SendPw(pw));
        context.sendBroadcast(intent);
    }

    public static void UpTime(Context context) {
        Intent intent = new Intent();
        intent.setAction(ACTION_WriteData);
        intent.putExtra(ACTION_WriteData_data, SendDataAnalysis.SendUpTime());
        context.sendBroadcast(intent);
    }

    public static void ReadUp(Context context, byte bAddr) {
        Intent intent = new Intent();
        intent.setAction(ACTION_WriteData);
        intent.putExtra(ACTION_WriteData_data, SendDataAnalysis.ReadUp(bAddr));
        context.sendBroadcast(intent);
    }

    public static void SettingUp(Context context, byte bAddr, Boolean isswitch) {
        Intent intent = new Intent();
        intent.setAction(ACTION_WriteData);
        intent.putExtra(ACTION_WriteData_data, SendDataAnalysis.SendSetUp(bAddr, isswitch));
        context.sendBroadcast(intent);
    }

    public static void SettingUp(Context context, byte bAddr, int data) {
        Intent intent = new Intent();
        intent.setAction(ACTION_WriteData);
        intent.putExtra(ACTION_WriteData_data, SendDataAnalysis.SendSetUp(bAddr, data));
        context.sendBroadcast(intent);
    }
}
