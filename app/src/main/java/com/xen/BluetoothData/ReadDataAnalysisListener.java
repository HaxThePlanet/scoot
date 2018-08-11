package com.xen.BluetoothData;

import com.xen.BluetoothBase.BleBase;
import com.xen.BluetoothBase.BleStatus;

public interface ReadDataAnalysisListener {
    void Changes(BleBase bleBase);

    void Changes(BleStatus bleStatus);

    void VerificationPw(Boolean bool);
}
