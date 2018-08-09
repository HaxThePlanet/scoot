package com.example.currentplacedetailsonmap.Util;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;

import com.example.currentplacedetailsonmap.BluetoothBase.BleBase;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;

public class BleSharedPreferences {
    public static final String SETTING_INFOS = "BleSharedPreferences";
    private Gson gson;
    private SharedPreferences settings;
    private String shared_Address = "shared_Address";
    private String shared_Location = "shared_Location";
    private String shared_Name = "shared_Name";
    private String shared_PassWord = "shared_PassWord";
    private String shared_Rssi = "shared_Rssi";
    private String shared_edit_PassWord = "shared_edit_PassWord";

    public BleSharedPreferences(Context context) {
        this.settings = context.getSharedPreferences(SETTING_INFOS, 0);
        this.gson = new Gson();
    }

    public void setEditPW(String pw) {
        this.settings.edit().putString(this.shared_edit_PassWord, pw).apply();
    }

    public void remoEditPW() {
        this.settings.edit().remove(this.shared_edit_PassWord).apply();
    }

    public String GetEditPW() {
        return this.settings.getString(this.shared_edit_PassWord, "");
    }

    public void setBleBase(BleBase mBase) {
        this.settings.edit().putString(this.shared_Address, mBase.getAddress()).putString(this.shared_Name, mBase.getName()).putString(this.shared_PassWord, mBase.getPassWord()).putInt(this.shared_Rssi, mBase.getRssi()).commit();
    }

    public void remoBleBase() {
        this.settings.edit().remove(this.shared_Address).remove(this.shared_Name).remove(this.shared_PassWord).remove(this.shared_Rssi).commit();
    }

    public BleBase GetBleBase() {
        Parcel p = null;
        BleBase mBleBase = new BleBase(p);
        mBleBase.setAddress(this.settings.getString(this.shared_Address, ""));
        mBleBase.setName(this.settings.getString(this.shared_Name, ""));
        mBleBase.setPassWord(this.settings.getString(this.shared_PassWord, ""));
        mBleBase.setRssi(this.settings.getInt(this.shared_Rssi, 0));
        return mBleBase;
    }

//    public void SetLocation(double latitude, double longitude) {
//        Object mBleLocationBean = new BleLocationBean();
//        mBleLocationBean.setLatitude(latitude);
//        mBleLocationBean.setLongitude(longitude);
//        this.settings.edit().putString(this.shared_Location, this.gson.toJson(mBleLocationBean)).apply();
//    }

    public LatLng GetLocation() {
//        BleLocationBean mBleLocationBean = (BleLocationBean) this.gson.fromJson(this.settings.getString(this.shared_Location, ""), BleLocationBean.class);
//        if (mBleLocationBean != null) {
//            return new LatLng(mBleLocationBean.getLatitude(), mBleLocationBean.getLongitude());
//        }
        return new LatLng(0.0d, 0.0d);
    }

    public void remoLocation() {
        this.settings.edit().remove(this.shared_Location).apply();
    }
}
