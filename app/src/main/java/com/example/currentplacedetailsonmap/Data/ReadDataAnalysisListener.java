package com.example.currentplacedetailsonmap.Data;

import com.example.currentplacedetailsonmap.base.BleBase;
import com.example.currentplacedetailsonmap.base.BleStatus;

public interface ReadDataAnalysisListener {
    void Changes(BleBase bleBase);

    void Changes(BleStatus bleStatus);

    void VerificationPw(Boolean bool);
}
