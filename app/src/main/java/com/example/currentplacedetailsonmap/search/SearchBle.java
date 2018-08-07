package com.example.currentplacedetailsonmap.search;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter.LeScanCallback;
import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.example.currentplacedetailsonmap.base.BleBase;
import com.example.currentplacedetailsonmap.tool.BleTool;


public class SearchBle {
    private String hanKey = "mBle";
    @SuppressLint({"HandlerLeak"})
    Handler hanLeScan = new C05822();
    public BleTool mBleTool;
    public SearchListener mSearchListener;
    private int minrssi = -100;
    LeScanCallback scanCallback = new C05811();

    class C05811 implements LeScanCallback {
        C05811() {
        }

        public void onLeScan(BluetoothDevice bluetoothDevice, int rssi, byte[] scanRecord) {
            if (rssi >= SearchBle.this.minrssi && !TextUtils.isEmpty(bluetoothDevice.getName())) {
                Bundle bun = new Bundle();
                BleBase mBle = new BleBase(null);
                mBle.setName(bluetoothDevice.getName());
                mBle.setAddress(bluetoothDevice.getAddress());
                mBle.setRssi(rssi);
                bun.putParcelable(SearchBle.this.hanKey, mBle);
                Message msg = new Message();
                msg.setData(bun);
                SearchBle.this.hanLeScan.sendMessage(msg);
            }
        }
    }

    class C05822 extends Handler {
        C05822() {
        }

        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            BleBase mBle = (BleBase) msg.getData().getParcelable(SearchBle.this.hanKey);
            if (mBle != null && SearchBle.this.mSearchListener != null) {
                SearchBle.this.mSearchListener.onLeScan(mBle);
            }
        }
    }

    public SearchBle(Context mContext) {
        this.mBleTool = new BleTool(mContext);
    }

    public void setListener(SearchListener SearchListener) {
        this.mSearchListener = SearchListener;
    }

    public boolean search() {
        if (!this.mBleTool.hasBleOpen() || !this.mBleTool.isBleOpen()) {
            return false;
        }
        this.mBleTool.adapter.stopLeScan(this.scanCallback);
        return this.mBleTool.adapter.startLeScan(this.scanCallback);
    }

    public void stop() {
        this.mBleTool.adapter.stopLeScan(this.scanCallback);
    }
}
