package com.example.currentplacedetailsonmap.tool;

public class RssiType {
    public static int getStatic(int Rssi) {
        if (Rssi >= -55) {
            return 0;
        }
        if (Rssi >= -75) {
            return 1;
        }
        if (Rssi >= -85) {
            return 2;
        }
        if (Rssi >= -95) {
            return 3;
        }
        return 4;
    }
}
