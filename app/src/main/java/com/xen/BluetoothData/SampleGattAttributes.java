package com.xen.BluetoothData;

import java.util.UUID;

public class SampleGattAttributes {
    public static final UUID NotifyCharacteristicUUID = UUID.fromString("0000FFE4-0000-1000-8000-00805f9b34fb");
    public static final UUID NotifyServiceUUID = UUID.fromString("0000FFE0-0000-1000-8000-00805f9b34fb");
    public static final UUID RNameCharacteristicUUID = UUID.fromString("0000FF91-0000-1000-8000-00805f9b34fb");
    public static final UUID RNameServiceUUID = UUID.fromString("0000FF90-0000-1000-8000-00805f9b34fb");
    public static final UUID WriteCharacteristicUUID = UUID.fromString("0000FFE9-0000-1000-8000-00805f9b34fb");
    public static final UUID WriteServiceUUID = UUID.fromString("0000FFE5-0000-1000-8000-00805f9b34fb");
    public static UUID CLIENT_CHARACTERISTIC_CONFIG = UUID.fromString("00002902-0000-1000-8000-00805f9b34fb");
    public static UUID HEART_RATE_MEASUREMENT = UUID.fromString("00002a37-0000-1000-8000-00805f9b34fb");
}
