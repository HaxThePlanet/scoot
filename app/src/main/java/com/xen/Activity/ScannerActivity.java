package com.xen.Activity;

import android.os.Bundle;
import android.os.Parcel;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.SurfaceView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.zxing.ResultPoint;
import com.google.zxing.client.android.BeepManager;
import com.journeyapps.barcodescanner.BarcodeCallback;
import com.journeyapps.barcodescanner.BarcodeResult;
import com.journeyapps.barcodescanner.DecoratedBarcodeView;
import com.xen.BluetoothBase.BleBase;
import com.xen.BluetoothBase.BleStatus;
import com.xen.BluetoothData.SearchBle;
import com.xen.BluetoothData.SearchListener;
import com.xen.BroadcastReceivers.ReadBleBroadcast;
import com.xen.BroadcastReceivers.SendBleReceiver;
import com.xen.R;

import java.util.HashMap;
import java.util.List;

public class ScannerActivity extends AppCompatActivity implements SearchListener {

    private static final String TAG = ScannerActivity.class.getSimpleName();
    private static final int MY_PERMISSIONS_REQUEST_CAMERA = 26;
    private final int han_what = 0;
    private final int han_what_scan = 1;
    private DecoratedBarcodeView barcodeView;
    private BeepManager beepManager;
    private String lastText;
    private HashMap<String, BleBase> bleHas = new HashMap();
    //    private MyHandler han;
    private int han_time = 20000;
    private boolean isHasSurface = false;
    private boolean isOpenCamera = false;
    private BleBase mBase;
    private SearchBle mSearch;
    private SendBleReceiver mSendBleReceiver;

    private String macid = "";
    //    private MainHandler mainHandler;
    private Boolean mispw = Boolean.valueOf(false);
    private RelativeLayout rl_myinfo;
    private RelativeLayout scanContainer;
    private RelativeLayout scanCropView;
    private ImageView scanLine;
    private SurfaceView scanPreview;
    private BleStatus mState = new BleStatus();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scoot_scanner);

        init();

        //Initialize barcode scanner view
        beepManager = new BeepManager(this);
        barcodeView = findViewById(R.id.zxing_barcode_scanner);
        barcodeView.decodeContinuous(callback);

        mSendBleReceiver = new SendBleReceiver(this, new C08611());
        mSendBleReceiver.registerReceiver();
    }

    @Override
    protected void onResume() {
        super.onResume();
        barcodeView.resume();

        mSendBleReceiver.registerReceiver();
    }

    @Override
    protected void onPause() {
        super.onPause();
        barcodeView.pause();

        unregisterReceiver(mSendBleReceiver);
    }

    private void connect(String mac) {
        macid = "";
        mBase = bleHas.get(mac);

        ReadBleBroadcast.connect(this, mBase);

//        this.finish();
    }

    private void init() {
        this.mSearch = new SearchBle(this);
        this.mSearch.setListener(this);
        this.mSendBleReceiver = new SendBleReceiver(this, new C08611());
    }

    public void onLeScan(BleBase mBle) {
        if (!bleHas.containsKey(mBle.getAddress())) {
            Parcel p = null;
            BleBase Base = new BleBase(p);
            Base.setName(mBle.getName());
            Base.setAddress(mBle.getAddress());
            Base.setPassWord("000000");
            bleHas.put(Base.getAddress(), Base);
            if (bleHas.containsKey(macid) && !TextUtils.isEmpty(macid)) {
//                this.han.removeMessages(1);
                connect(macid);
            }
        }
    }

    private BarcodeCallback callback = new BarcodeCallback() {
        @Override
        public void barcodeResult(BarcodeResult result) {
            if (result.getText() == null || result.getText().equals(lastText)) {
                // Prevent duplicate scans
                return;
            }

            lastText = result.getText();
//            barcodeView.setStatusText(result.getText());
            beepManager.playBeepSoundAndVibrate();
            beepManager.playBeepSoundAndVibrate();
            beepManager.playBeepSoundAndVibrate();

            Log.e(TAG, "result=" + result);

            if (result.toString().split("/").length > 1) {
                String mac = result.toString().split("/")[1].replaceAll(".{2}(?!$)", "$0:").toUpperCase();

                if (bleHas.containsKey(mac)) {
                    connect(mac);
                    return;
                }

                macid = mac;
                mSearch.search();

                return;
            }
        }

        @Override
        public void possibleResultPoints(List<ResultPoint> resultPoints) {
        }
    };



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    class C08611 implements SendBleReceiver.BLESendListener {
        C08611() {
        }

        public void Changes(BleBase mBleBase, BleStatus mBleStatus) {
            Log.i("CHAD", String.valueOf(mBleStatus.getState()));

            switch (mBleStatus.getState()) {
                case -2:
                    mBase = null;
                    return;
                case -1:
                    if (mBase != null) {
//                        ReadBleBroadcast.connect(ScannerActivity.this, ScannerActivity.this.mBase);
                        return;
//                    } else if (ScannerActivity.this.mWaitDialog.isShowing()) {
//                        ScannerActivity.this.mWaitDialog.cancel();
//                        return;
                    } else {
                        return;
                    }
                case 3:
                    ScannerActivity.this.mispw = Boolean.valueOf(true);
//                    ScannerActivity.this.mWaitDialog.cancel();
//                    ScannerActivity.this.GoToActivity(TestActivity.class, Boolean.valueOf(true));
                    return;
                default:
                    return;
            }
        }

        public void settingUp(int code, Boolean isstate) {
        }
    }

}