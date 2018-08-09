package com.example.currentplacedetailsonmap.BluetoothData;

import com.example.currentplacedetailsonmap.BluetoothBase.BleBase;
import com.example.currentplacedetailsonmap.BluetoothBase.BleStatus;

public interface ReadDataAnalysisListener {
    void Changes(BleBase bleBase);

    void Changes(BleStatus bleStatus);

    void VerificationPw(Boolean bool);
}
