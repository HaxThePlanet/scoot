package com.example.currentplacedetailsonmap.base;

import android.os.Parcel;
import android.os.Parcelable;

public class BleStatus implements Parcelable {
    public static final Creator<BleStatus> CREATOR = new C05761();
    public static final int state_DataUp = 4;
    public static final int state_ServicesDiscovered = 2;
    public static final int state_authenticated = 3;
    public static final int state_authentication_failed = -2;
    public static final int state_connected = 1;
    public static final int state_connecting = 0;
    public static final int state_disconnect = -1;
    public int BattPercent = 0;
    public int bODOMeterDec = 0;
    public int bTRIPMeterDec = 0;
    public Boolean buzzer = Boolean.valueOf(false);
    public Boolean cruise = Boolean.valueOf(false);
    public int currentLevel = 1;
    public Boolean error_abs = Boolean.valueOf(false);
    public Boolean error_controller = Boolean.valueOf(false);
    public Boolean error_governing = Boolean.valueOf(false);
    public Boolean error_motor = Boolean.valueOf(false);
    public Boolean light = Boolean.valueOf(false);
    public Boolean lock = Boolean.valueOf(false);
    public int powerLevel = 0;
    public Boolean sixKMPH = Boolean.valueOf(false);
    public int state = -1;
    public int unit = 0;
    public int wODOMeterInt = 0;
    public int wSpeed = 0;
    public int wSpeedAvg = 0;
    public int wTRIPMeterInt = 0;

    static class C05761 implements Creator<BleStatus> {
        C05761() {
        }

        public BleStatus createFromParcel(Parcel in) {
            return new BleStatus();
        }

        public BleStatus[] newArray(int size) {
            return new BleStatus[size];
        }
    }

    public BleStatus() {
        Boolean bool;
        Boolean bool2 = null;
        boolean z = true;

//        this.state = in.readInt();
//        byte tmpLock = in.readByte();
//        if (tmpLock == (byte) 0) {
//            bool = null;
//        } else {
//            bool = Boolean.valueOf(tmpLock == (byte) 1);
//        }
//        this.lock = bool;
//        byte tmpLight = in.readByte();
//        if (tmpLight == (byte) 0) {
//            bool = null;
//        } else {
//            bool = Boolean.valueOf(tmpLight == (byte) 1);
//        }
//        this.light = bool;
//        byte tmpBuzzer = in.readByte();
//        if (tmpBuzzer == (byte) 0) {
//            bool = null;
//        } else {
//            bool = Boolean.valueOf(tmpBuzzer == (byte) 1);
//        }
//        this.buzzer = bool;
//        byte tmpSixKMPH = in.readByte();
//        if (tmpSixKMPH == (byte) 0) {
//            bool = null;
//        } else {
//            bool = Boolean.valueOf(tmpSixKMPH == (byte) 1);
//        }
//        this.sixKMPH = bool;
//        byte tmpCruise = in.readByte();
//        if (tmpCruise == (byte) 0) {
//            bool = null;
//        } else {
//            bool = Boolean.valueOf(tmpCruise == (byte) 1);
//        }
//        this.cruise = bool;
//        this.currentLevel = in.readInt();
//        byte tmpError_motor = in.readByte();
//        if (tmpError_motor == (byte) 0) {
//            bool = null;
//        } else {
//            bool = Boolean.valueOf(tmpError_motor == (byte) 1);
//        }
//        this.error_motor = bool;
//        byte tmpError_controller = in.readByte();
//        if (tmpError_controller == (byte) 0) {
//            bool = null;
//        } else {
//            bool = Boolean.valueOf(tmpError_controller == (byte) 1);
//        }
//        this.error_controller = bool;
//        byte tmpError_abs = in.readByte();
//        if (tmpError_abs == (byte) 0) {
//            bool = null;
//        } else {
//            bool = Boolean.valueOf(tmpError_abs == (byte) 1);
//        }
//        this.error_abs = bool;
//        byte tmpError_governing = in.readByte();
//        if (tmpError_governing != (byte) 0) {
//            if (tmpError_governing != (byte) 1) {
//                z = false;
//            }
//            bool2 = Boolean.valueOf(z);
//        }
//        this.error_governing = bool2;
//        this.powerLevel = in.readInt();
//        this.BattPercent = in.readInt();
//        this.wSpeed = in.readInt();
//        this.wSpeedAvg = in.readInt();
//        this.wODOMeterInt = in.readInt();
//        this.wTRIPMeterInt = in.readInt();
//        this.bODOMeterDec = in.readInt();
//        this.bTRIPMeterDec = in.readInt();
//        this.unit = in.readInt();
    }

    public void writeToParcel(Parcel dest, int flags) {
        int i = 0;
        dest.writeInt(this.state);
        int i2 = this.lock == null ? 0 : this.lock.booleanValue() ? 1 : 2;
        dest.writeByte((byte) i2);
        i2 = this.light == null ? 0 : this.light.booleanValue() ? 1 : 2;
        dest.writeByte((byte) i2);
        i2 = this.buzzer == null ? 0 : this.buzzer.booleanValue() ? 1 : 2;
        dest.writeByte((byte) i2);
        i2 = this.sixKMPH == null ? 0 : this.sixKMPH.booleanValue() ? 1 : 2;
        dest.writeByte((byte) i2);
        i2 = this.cruise == null ? 0 : this.cruise.booleanValue() ? 1 : 2;
        dest.writeByte((byte) i2);
        dest.writeInt(this.currentLevel);
        i2 = this.error_motor == null ? 0 : this.error_motor.booleanValue() ? 1 : 2;
        dest.writeByte((byte) i2);
        i2 = this.error_controller == null ? 0 : this.error_controller.booleanValue() ? 1 : 2;
        dest.writeByte((byte) i2);
        i2 = this.error_abs == null ? 0 : this.error_abs.booleanValue() ? 1 : 2;
        dest.writeByte((byte) i2);
        if (this.error_governing != null) {
            i = this.error_governing.booleanValue() ? 1 : 2;
        }
        dest.writeByte((byte) i);
        dest.writeInt(this.powerLevel);
        dest.writeInt(this.BattPercent);
        dest.writeInt(this.wSpeed);
        dest.writeInt(this.wSpeedAvg);
        dest.writeInt(this.wODOMeterInt);
        dest.writeInt(this.wTRIPMeterInt);
        dest.writeInt(this.bODOMeterDec);
        dest.writeInt(this.bTRIPMeterDec);
        dest.writeInt(this.unit);
    }

    public int describeContents() {
        return 0;
    }

    public int getUnit() {
        return this.unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getBattPercent() {
        return this.BattPercent;
    }

    public void setBattPercent(int battPercent) {
        this.BattPercent = battPercent;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public Boolean getLock() {
        return this.lock;
    }

    public void setLock(Boolean lock) {
        this.lock = lock;
    }

    public Boolean getLight() {
        return this.light;
    }

    public void setLight(Boolean light) {
        this.light = light;
    }

    public Boolean getBuzzer() {
        return this.buzzer;
    }

    public void setBuzzer(Boolean buzzer) {
        this.buzzer = buzzer;
    }

    public Boolean getSixKMPH() {
        return this.sixKMPH;
    }

    public void setSixKMPH(Boolean sixKMPH) {
        this.sixKMPH = sixKMPH;
    }

    public Boolean getCruise() {
        return this.cruise;
    }

    public void setCruise(Boolean cruise) {
        this.cruise = cruise;
    }

    public int getCurrentLevel() {
        return this.currentLevel;
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public Boolean getError_motor() {
        return this.error_motor;
    }

    public void setError_motor(Boolean error_motor) {
        this.error_motor = error_motor;
    }

    public Boolean getError_controller() {
        return this.error_controller;
    }

    public void setError_controller(Boolean error_controller) {
        this.error_controller = error_controller;
    }

    public Boolean getError_abs() {
        return this.error_abs;
    }

    public void setError_abs(Boolean error_abs) {
        this.error_abs = error_abs;
    }

    public Boolean getError_governing() {
        return this.error_governing;
    }

    public void setError_governing(Boolean error_governing) {
        this.error_governing = error_governing;
    }

    public int getPowerLevel() {
        return this.powerLevel;
    }

    public void setPowerLevel(int powerLevel) {
        this.powerLevel = powerLevel;
    }

    public int getwSpeed() {
        return this.wSpeed;
    }

    public void setwSpeed(int wSpeed) {
        this.wSpeed = wSpeed / 10;
    }

    public int getwSpeedAvg() {
        return this.wSpeedAvg;
    }

    public void setwSpeedAvg(int wSpeedAvg) {
        this.wSpeedAvg = wSpeedAvg / 10;
    }

    public int getwODOMeterInt() {
        return this.wODOMeterInt;
    }

    public void setwODOMeterInt(int wODOMeterInt) {
        this.wODOMeterInt = wODOMeterInt;
    }

    public int getwTRIPMeterInt() {
        return this.wTRIPMeterInt;
    }

    public void setwTRIPMeterInt(int wTRIPMeterInt) {
        this.wTRIPMeterInt = wTRIPMeterInt / 10;
    }

    public int getbODOMeterDec() {
        return this.bODOMeterDec;
    }

    public void setbODOMeterDec(int bODOMeterDec) {
        this.bODOMeterDec = bODOMeterDec;
    }

    public int getbTRIPMeterDec() {
        return this.bTRIPMeterDec;
    }

    public void setbTRIPMeterDec(int bTRIPMeterDec) {
        this.bTRIPMeterDec = bTRIPMeterDec;
    }
}

