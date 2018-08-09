package com.example.currentplacedetailsonmap.BluetoothData;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Handler;
import android.os.Message;

import com.example.currentplacedetailsonmap.BroadcastReceivers.ReadBleBroadcast;
import com.google.android.gms.common.ConnectionResult;

public class SendTimer {
    //    private int uptime_Delayed = AMapException.CODE_AMAP_SERVICE_TABLEID_NOT_EXIST;
    private final int what_pw = 0;
    private final int what_uptime = 1;
    @SuppressLint({"HandlerLeak"})
    Handler Mytimer = new C05781();
    private Context context;
    private int pw_Delayed = ConnectionResult.DRIVE_EXTERNAL_STORAGE_REQUIRED;

    public SendTimer(Context context) {
        this.context = context;
    }

    public void sendPw(String Password) {
        Message msg = new Message();
        msg.what = 0;
        msg.obj = Password;
        this.Mytimer.sendMessageDelayed(msg, 0);
    }

    public void sendUpTime() {
        closeUptime();
        Message msg = new Message();
        msg.what = 1;
        this.Mytimer.sendMessageDelayed(msg, (long) 10000);
    }

    public void closeUptime() {
        if (this.Mytimer.hasMessages(1)) {
            this.Mytimer.removeMessages(1);
        }
    }

    public void closePw() {
        if (this.Mytimer.hasMessages(0)) {
            this.Mytimer.removeMessages(0);
        }
    }

    public void closeAll() {
        closePw();
        closeUptime();
    }

    class C05781 extends Handler {
        C05781() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    String Password = (String) msg.obj;
                    if (Password != null) {
                        ReadBleBroadcast.VerifyPW(SendTimer.this.context, Password);
                        Message msg_pw = new Message();
                        msg_pw.what = 0;
                        msg_pw.obj = Password;
                        SendTimer.this.Mytimer.sendMessageDelayed(msg_pw, (long) SendTimer.this.pw_Delayed);
                        return;
                    }
                    return;
                case 1:
                    ReadBleBroadcast.UpTime(SendTimer.this.context);
                    SendTimer.this.sendUpTime();
                    return;
                default:
                    return;
            }
        }
    }
}
