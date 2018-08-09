package com.example.currentplacedetailsonmap.BluetoothBase;

import android.os.Parcel;
import android.os.Parcelable;

public class BleBase implements Parcelable {
    public static final Creator<BleBase> CREATOR = new C05751();
    public String Address;
    public String Name;
    public String PassWord;
    public int rssi;

    public BleBase(Parcel in) {
        try {
            this.Name = in.readString();
            this.Address = in.readString();
            this.PassWord = in.readString();
            this.rssi = in.readInt();
        } catch (Exception e) {
        }
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.Name);
        dest.writeString(this.Address);
        dest.writeString(this.PassWord);
        dest.writeInt(this.rssi);
    }

    public int describeContents() {
        return 0;
    }

    public String getPassWord() {
        return this.PassWord;
    }

    public void setPassWord(String passWord) {
        this.PassWord = passWord;
    }

    public String getName() {
        return this.Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public String getAddress() {
        return this.Address;
    }

    public void setAddress(String address) {
        this.Address = address;
    }

    public int getRssi() {
        return this.rssi;
    }

    public void setRssi(int rssi) {
        this.rssi = rssi;
    }

    static class C05751 implements Creator<BleBase> {
        C05751() {
        }

        public BleBase createFromParcel(Parcel in) {
            return new BleBase(in);
        }

        public BleBase[] newArray(int size) {
            return new BleBase[size];
        }
    }
}
