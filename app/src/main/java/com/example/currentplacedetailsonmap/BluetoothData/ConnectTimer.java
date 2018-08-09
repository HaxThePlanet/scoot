package com.example.currentplacedetailsonmap.BluetoothData;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;

import com.example.currentplacedetailsonmap.BroadcastReceivers.ReadBleBroadcast;
import com.example.currentplacedetailsonmap.BluetoothBase.BleBase;

public class ConnectTimer {
    private BleBase mBle;
    private BroadcastReceiver mStatusReceive = new C05791();
    private Context mcontext;
    private MyHandler mhan;

    public ConnectTimer(Context context) {
        this.mcontext = context;
        this.mhan = new MyHandler(context);
        this.mcontext.registerReceiver(this.mStatusReceive, new IntentFilter("android.bluetooth.adapter.action.STATE_CHANGED"));
    }

    public void onDestroy() {
        this.mcontext.unregisterReceiver(this.mStatusReceive);
    }

    public void startConnect(BleBase mBle) {
        this.mBle = mBle;
        if (!TextUtils.isEmpty(mBle.getAddress())) {
            this.mhan.startConnect(mBle);
        }
    }

    public void stop() {
        this.mhan.stop();
    }

    protected static class MyHandler {
        @SuppressLint({"HandlerLeak"})
        Handler handler = new C05802();
        private int han_what = 0;
        private BleBase mBleBase;
        private SearchBle mSearchBle;
        private Context mcontext;
        private int searTime = 10000;

        public MyHandler(Context context) {
            this.mcontext = context;
            this.mSearchBle = new SearchBle(this.mcontext);
            this.mSearchBle.setListener(new C08461());
        }

        public void startConnect(BleBase mBle) {
            stop();
            if (mBle != null && !TextUtils.isEmpty(mBle.getAddress())) {
                Log.e("abc", "startConnect=" + mBle.getName());
                Log.e("abc", "startConnect=" + mBle.getAddress());
                Log.e("abc", "startConnect=" + mBle.getPassWord());
                this.mBleBase = mBle;
                this.handler.sendEmptyMessageDelayed(this.han_what, 0);
            }
        }

        public void stop() {
            if (this.handler.hasMessages(this.han_what)) {
                this.mSearchBle.stop();
                this.handler.removeMessages(this.han_what);
            }
        }

        class C05802 extends Handler {
            C05802() {
            }

            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.e("abc", "startConnect");
                MyHandler.this.mSearchBle.search();
                sendEmptyMessageDelayed(MyHandler.this.han_what, (long) MyHandler.this.searTime);
            }
        }

        class C08461 implements SearchListener {
            C08461() {
            }

            public void onLeScan(BleBase mBle) {
                if (mBle.getAddress().equals(MyHandler.this.mBleBase.getAddress())) {
                    ReadBleBroadcast.connect(MyHandler.this.mcontext, MyHandler.this.mBleBase);
                    MyHandler.this.stop();
                }
            }
        }
    }

    class C05791 extends BroadcastReceiver {
        C05791() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            int i = -1;
            switch (action.hashCode()) {
                case -1530327060:
                    if (action.equals("android.bluetooth.adapter.action.STATE_CHANGED")) {
                        i = 0;
                        break;
                    }
                    break;
            }
            switch (i) {
                case 0:
                    switch (intent.getIntExtra("android.bluetooth.adapter.extra.STATE", 0)) {
                        case 10:
                            return;
                        case 12:
                            ConnectTimer.this.startConnect(ConnectTimer.this.mBle);
                            return;
                        default:
                            return;
                    }
                default:
                    return;
            }
        }
    }
}
