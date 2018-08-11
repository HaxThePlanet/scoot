package com.xen.BluetoothData;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SendDataAnalysis {
    public static byte[] SendPw(String pw) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        buffer.write(BLECommon.GetHead(), 0, BLECommon.GetHead().length);
        buffer.write(pw.length() + 0);
        buffer.write(1);
        for (int i = 0; i < pw.length(); i++) {
            buffer.write(pw.substring(i, i + 1).getBytes(), 0, pw.substring(i, i + 1).getBytes().length);
        }
        return BLECommon.addCrcAndEnd(buffer);
    }

    public static byte[] rePw(String pw) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        buffer.write(BLECommon.GetHead(), 0, BLECommon.GetHead().length);
        buffer.write(pw.length() + 0);
        buffer.write(0);
        for (int i = 0; i < pw.length(); i++) {
            buffer.write(pw.substring(i, i + 1).getBytes(), 0, pw.substring(i, i + 1).getBytes().length);
        }
        return BLECommon.addCrcAndEnd(buffer);
    }

    public static byte[] ReadUp(byte bAddr) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        buffer.write(BLECommon.GetHead(), 0, BLECommon.GetHead().length);
        buffer.write(64);
        buffer.write(bAddr);
        return BLECommon.addCrcAndEnd(buffer);
    }

    public static byte[] SendSetUp(byte bAddr, Boolean isswitch) {
        int i = 0;
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        buffer.write(BLECommon.GetHead(), 0, BLECommon.GetHead().length);
        buffer.write(129);
        buffer.write(bAddr);
        if (isswitch.booleanValue()) {
            i = 1;
        }
        buffer.write(i);
        return BLECommon.addCrcAndEnd(buffer);
    }

    public static byte[] SendSetUp(byte bAddr, int data) {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        buffer.write(BLECommon.GetHead(), 0, BLECommon.GetHead().length);
        buffer.write(129);
        buffer.write(bAddr);
        buffer.write(data);
        return BLECommon.addCrcAndEnd(buffer);
    }

    public static byte[] SendUpTime() {
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        buffer.write(BLECommon.GetHead(), 0, BLECommon.GetHead().length);
        byte[] time_b = new byte[]{(byte) getH(), (byte) getM()};
        buffer.write(time_b.length + 192);
        buffer.write(-2);
        buffer.write(time_b, 0, time_b.length);
        return BLECommon.addCrcAndEnd(buffer);
    }

    private static int getH() {
        return Integer.valueOf(new SimpleDateFormat("HH").format(new Date(System.currentTimeMillis()))).intValue();
    }

    private static int getM() {
        return Integer.valueOf(new SimpleDateFormat("mm").format(new Date(System.currentTimeMillis()))).intValue();
    }
}
