package com.example.currentplacedetailsonmap.BluetoothData;

import com.example.currentplacedetailsonmap.Utility.CRC16;

import java.io.ByteArrayOutputStream;

public class BLECommon {
    public static final byte bAddrPW = (byte) 1;
    public static final byte bAddrSetBuzzer = (byte) 18;
    public static final byte bAddrSetCruise = (byte) 20;
    public static final byte bAddrSetGear = (byte) 21;
    public static final byte bAddrSetLight = (byte) 17;
    public static final byte bAddrSetLock = (byte) 16;
    public static final byte bAddrUnit = (byte) 48;
    public static final byte bAddrUptime = (byte) -2;
    public static final byte bAddrrePW = (byte) 0;
    public static final byte mMode0 = (byte) 0;
    public static final byte mMode1 = (byte) 1;
    public static final byte mMode2 = (byte) 2;
    public static final byte mMode3 = (byte) 3;
    public static final byte mReceiveHead1 = (byte) 102;
    public static final byte mReceiveHead2 = (byte) -79;
    public static final byte mSendEnd1 = (byte) -95;
    public static final byte mSendEnd2 = (byte) 85;
    public static final byte mSendHead1 = (byte) 85;
    public static final byte mSendHead2 = (byte) -95;

    public static byte[] GetHead() {
        return new byte[]{(byte) 85, (byte) -95};
    }

    public static byte[] addCrcAndEnd(ByteArrayOutputStream data) {
        byte[] crc = CRC16.toByteArray(CRC16.calcCrc16(data.toByteArray()), 2);
        data.write(crc[0]);
        data.write(crc[1]);
        data.write(-95);
        data.write(85);
        return data.toByteArray();
    }
}
