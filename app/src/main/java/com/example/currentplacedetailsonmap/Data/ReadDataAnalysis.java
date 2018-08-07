package com.example.currentplacedetailsonmap.Data;

import android.content.Context;
import android.util.Log;

import com.example.currentplacedetailsonmap.BroadcastReceiver.SendBleBroadcast;
import com.example.currentplacedetailsonmap.base.BleBase;
import com.example.currentplacedetailsonmap.base.BleStatus;
import com.example.currentplacedetailsonmap.tool.BleSharedPreferences;
import com.example.currentplacedetailsonmap.tool.BleTool;

import java.io.ByteArrayOutputStream;

public class ReadDataAnalysis {
    private static final String TAG = ReadDataAnalysis.class.getSimpleName();
    private ByteArrayOutputStream Mode0_Data = new ByteArrayOutputStream();
    private Context context;
    private ReadDataAnalysisListener mListener;
    private BleSharedPreferences mShared;

    public ReadDataAnalysis(Context context, ReadDataAnalysisListener mListener) {
        this.context = context;
        this.mListener = mListener;
        this.mShared = new BleSharedPreferences(context);
    }

    public void ReadData(byte[] data) {
        if (data != null && data.length >= 2) {
            if (data[0] == BLECommon.mReceiveHead1 && data[1] == BLECommon.mReceiveHead2) {
                this.Mode0_Data.reset();
                switch ((data[2] & 192) >> 6) {
                    case 0:
                        this.Mode0_Data.write(data, 0, data.length);
                        return;
                    case 1:
                    case 2:
                        return;
                    case 3:
                        ErrCode(data);
                        return;
                    default:
                        return;
                }
            } else if (data[data.length - 2] == (byte) -95 && data[data.length - 1] == (byte) 85 && this.Mode0_Data.size() > 0) {
                this.Mode0_Data.write(data, 0, data.length);
                BleTool.ByteToString(this.Mode0_Data.toByteArray());
                Mde0Data(this.Mode0_Data.toByteArray());
            }
        }
    }

    private void Mde0Data(byte[] data) {
        boolean z = true;
        Log.d(TAG, "Mode0_Data=" + BleTool.ByteToString(data));
        if (data.length >= 26) {
            boolean z2;
            BleStatus mStatus = new BleStatus();
            mStatus.setState(4);
            if (Unsigned(data[3]) == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            mStatus.setLock(Boolean.valueOf(z2));
            if (Unsigned(data[4]) == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            mStatus.setLight(Boolean.valueOf(z2));
            if (Unsigned(data[5]) == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            mStatus.setBuzzer(Boolean.valueOf(z2));
            if (Unsigned(data[6]) == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            mStatus.setSixKMPH(Boolean.valueOf(z2));
            if (Unsigned(data[7]) == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            mStatus.setCruise(Boolean.valueOf(z2));
            mStatus.setCurrentLevel(Unsigned(data[8]));
            if ((Unsigned(data[9]) & 1) == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            mStatus.setError_motor(Boolean.valueOf(z2));
            if (((Unsigned(data[9]) >> 1) & 1) == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            mStatus.setError_controller(Boolean.valueOf(z2));
            if (((Unsigned(data[9]) >> 2) &

                    1) == 1) {
                z2 = true;
            } else {
                z2 = false;
            }
            mStatus.setError_abs(Boolean.valueOf(z2));
            if (((Unsigned(data[9]) >> 3) & 1) != 1) {
                z = false;
            }
            mStatus.setError_governing(Boolean.valueOf(z));
            mStatus.setPowerLevel(Unsigned(data[10]));
            mStatus.setBattPercent(Unsigned(data[11]));
            mStatus.setwSpeed((Unsigned(data[13]) * 256) + Unsigned(data[12]));
            mStatus.setwSpeedAvg((Unsigned(data[15]) * 256) + Unsigned(data[14]));
            mStatus.setwODOMeterInt((Unsigned(data[17]) * 256) + Unsigned(data[16]));
            mStatus.setwTRIPMeterInt((Unsigned(data[19]) * 256) + Unsigned(data[18]));
            mStatus.setbODOMeterDec(Unsigned(data[20]));
            mStatus.setbTRIPMeterDec(Unsigned(data[21]));
            this.mListener.Changes(mStatus);
        }
    }

    public int Unsigned(byte b) {
        return b & 255;
    }

    private void ErrCode(byte[] data) {
        if ((data[2] & 63) > 0) {
            switch (data[3]) {
                case (byte) 0:
                    RePwCode(data);
                    return;
                case (byte) 1:
                    PwCode(data);
                    return;
                default:
                    return;
            }
        }
    }

    private void RePwCode(byte[] data) {
        switch (data[4]) {
            case (byte) 0:
                BleBase base = this.mShared.GetBleBase();
                base.setPassWord(this.mShared.GetEditPW());
                this.mShared.setBleBase(base);
                this.mListener.Changes(base);
                SendBleBroadcast.settingUp(this.context, SendBleBroadcast.code_rePW, Boolean.valueOf(true));
                return;
            default:
                SendBleBroadcast.settingUp(this.context, SendBleBroadcast.code_rePW, Boolean.valueOf(false));
                return;
        }
    }

    private void PwCode(byte[] data) {
        switch (data[4]) {
            case (byte) 0:
                this.mListener.VerificationPw(Boolean.valueOf(true));
                return;
            default:
                this.mListener.VerificationPw(Boolean.valueOf(false));
                return;
        }
    }
}
