package com.example.currentplacedetailsonmap.tool;

import android.support.v4.internal.view.SupportMenu;
import android.support.v4.view.MotionEventCompat;

import com.example.currentplacedetailsonmap.Data.BLECommon;


public class CRC16 {
    static byte[] crc16_tab_h = new byte[]{(byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 0, (byte) -63, (byte) -127, (byte) 64, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 1, (byte) -64, Byte.MIN_VALUE, (byte) 65, (byte) 0, (byte) -63, (byte) -127, (byte) 64};
    static byte[] crc16_tab_l = new byte[]{(byte) 0, (byte) -64, (byte) -63, (byte) 1, (byte) -61, (byte) 3, (byte) 2, (byte) -62, (byte) -58, (byte) 6, (byte) 7, (byte) -57, (byte) 5, (byte) -59, (byte) -60, (byte) 4, (byte) -52, (byte) 12, (byte) 13, (byte) -51, (byte) 15, (byte) -49, (byte) -50, (byte) 14, (byte) 10, (byte) -54, (byte) -53, (byte) 11, (byte) -55, (byte) 9, (byte) 8, (byte) -56, (byte) -40, (byte) 24, (byte) 25, (byte) -39, (byte) 27, (byte) -37, (byte) -38, (byte) 26, (byte) 30, (byte) -34, (byte) -33, (byte) 31, (byte) -35, (byte) 29, (byte) 28, (byte) -36, BLECommon.bAddrSetCruise, (byte) -44, (byte) -43, BLECommon.bAddrSetGear, (byte) -41, (byte) 23, (byte) 22, (byte) -42, (byte) -46, BLECommon.bAddrSetBuzzer, (byte) 19, (byte) -45, BLECommon.bAddrSetLight, (byte) -47, (byte) -48, BLECommon.bAddrSetLock, (byte) -16, BLECommon.bAddrUnit, (byte) 49, (byte) -15, (byte) 51, (byte) -13, (byte) -14, (byte) 50, (byte) 54, (byte) -10, (byte) -9, (byte) 55, (byte) -11, (byte) 53, (byte) 52, (byte) -12, (byte) 60, (byte) -4, (byte) -3, (byte) 61, (byte) -1, (byte) 63, (byte) 62, (byte) -2, (byte) -6, (byte) 58, (byte) 59, (byte) -5, (byte) 57, (byte) -7, (byte) -8, (byte) 56, (byte) 40, (byte) -24, (byte) -23, (byte) 41, (byte) -21, (byte) 43, (byte) 42, (byte) -22, (byte) -18, (byte) 46, (byte) 47, (byte) -17, (byte) 45, (byte) -19, (byte) -20, (byte) 44, (byte) -28, (byte) 36, (byte) 37, (byte) -27, (byte) 39, (byte) -25, (byte) -26, (byte) 38, (byte) 34, (byte) -30, (byte) -29, (byte) 35, (byte) -31, (byte) 33, (byte) 32, (byte) -32, (byte) -96, (byte) 96, (byte) 97, (byte) -95, (byte) 99, (byte) -93, (byte) -94, (byte) 98, BLECommon.mReceiveHead1, (byte) -90, (byte) -89, (byte) 103, (byte) -91, (byte) 101, (byte) 100, (byte) -92, (byte) 108, (byte) -84, (byte) -83, (byte) 109, (byte) -81, (byte) 111, (byte) 110, (byte) -82, (byte) -86, (byte) 106, (byte) 107, (byte) -85, (byte) 105, (byte) -87, (byte) -88, (byte) 104, (byte) 120, (byte) -72, (byte) -71, (byte) 121, (byte) -69, (byte) 123, (byte) 122, (byte) -70, (byte) -66, (byte) 126, Byte.MAX_VALUE, (byte) -65, (byte) 125, (byte) -67, (byte) -68, (byte) 124, (byte) -76, (byte) 116, (byte) 117, (byte) -75, (byte) 119, (byte) -73, (byte) -74, (byte) 118, (byte) 114, (byte) -78, (byte) -77, (byte) 115, BLECommon.mReceiveHead2, (byte) 113, (byte) 112, (byte) -80, (byte) 80, (byte) -112, (byte) -111, (byte) 81, (byte) -109, (byte) 83, (byte) 82, (byte) -110, (byte) -106, (byte) 86, (byte) 87, (byte) -105, (byte) 85, (byte) -107, (byte) -108, (byte) 84, (byte) -100, (byte) 92, (byte) 93, (byte) -99, (byte) 95, (byte) -97, (byte) -98, (byte) 94, (byte) 90, (byte) -102, (byte) -101, (byte) 91, (byte) -103, (byte) 89, (byte) 88, (byte) -104, (byte) -120, (byte) 72, (byte) 73, (byte) -119, (byte) 75, (byte) -117, (byte) -118, (byte) 74, (byte) 78, (byte) -114, (byte) -113, (byte) 79, (byte) -115, (byte) 77, (byte) 76, (byte) -116, (byte) 68, (byte) -124, (byte) -123, (byte) 69, (byte) -121, (byte) 71, (byte) 70, (byte) -122, (byte) -126, (byte) 66, (byte) 67, (byte) -125, (byte) 65, (byte) -127, Byte.MIN_VALUE, (byte) 64};

    public static int calcCrc16(byte[] data) {
        return calcCrc16(data, 0, data.length);
    }

    public static int calcCrc16(byte[] data, int offset, int len) {
        return calcCrc16(data, offset, len, SupportMenu.USER_MASK);
    }

    public static int calcCrc16(byte[] data, int offset, int len, int preval) {
        int ucCRCHi = (MotionEventCompat.ACTION_POINTER_INDEX_MASK & preval) >> 8;
        int ucCRCLo = preval & 255;
        for (int i = 0; i < len; i++) {
            int iIndex = (data[offset + i] ^ ucCRCLo) & 255;
            ucCRCLo = ucCRCHi ^ crc16_tab_h[iIndex];
            ucCRCHi = crc16_tab_l[iIndex];
        }
        return ((ucCRCHi & 255) << 8) | ((ucCRCLo & 255) & SupportMenu.USER_MASK);
    }

    public static byte[] toByteArray(int iSource, int iArrayLen) {
        byte[] bLocalArr = new byte[iArrayLen];
        int i = 0;
        while (i < 4 && i < iArrayLen) {
            bLocalArr[i] = (byte) ((iSource >> (i * 8)) & 255);
            i++;
        }
        return bLocalArr;
    }
}
