package com.xen.Service;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import com.xen.BroadcastReceivers.ReadBleBroadcast;
import com.xen.BroadcastReceivers.ReadBleReceiver;
import com.xen.BroadcastReceivers.SendBleBroadcast;
import com.xen.BluetoothData.BLECommon;
import com.xen.BluetoothData.ReadDataAnalysis;
import com.xen.BluetoothData.ReadDataAnalysisListener;
import com.xen.BluetoothBase.BleBase;
import com.xen.BluetoothBase.BleStatus;
import com.xen.BluetoothData.SampleGattAttributes;
import com.xen.BluetoothData.SendTimer;
import com.xen.Util.BleSharedPreferences;
import com.xen.Util.BleTool;
import com.xen.Util.WriteDataQueue;

import java.util.List;

public class BluetoothLeService extends Service implements ReadBleReceiver.BLEReadListener, WriteDataQueue.QueueListener {
    private static final String TAG = BluetoothLeService.class.getSimpleName();
    private final IBinder mBinder = new LocalBinder();
    @SuppressLint({"NewApi"})
    private final BluetoothGattCallback mGattCallback = new C05771();
    public ReadBleReceiver mReadReceiver;
    //    private BleLocation bleLocation;
    BluetoothDevice device;
    private BleBase mBleBase = new BleBase(null);
    private BleStatus mBleStatus = new BleStatus();
    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothGatt mBluetoothGatt;
    private BluetoothManager mBluetoothManager;
    private WriteDataQueue mQueue;
    private ReadDataAnalysis mReadDataAnalysis;
    private SendTimer mSendTimer;
    private BleSharedPreferences mShared;

    @SuppressLint({"NewApi"})
    @TargetApi(18)
    private void broadcastUpdate(BluetoothGattCharacteristic characteristic) {
        if (SampleGattAttributes.HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
            int format;
            if ((characteristic.getProperties() & 1) != 0) {
                format = 18;
                Log.d(TAG, "Heart rate format UINT16.");
            } else {
                format = 17;
                Log.d(TAG, "Heart rate format UINT8.");
            }
            int heartRate = characteristic.getIntValue(format, 1).intValue();
            Log.d(TAG, String.format("Received heart rate: %d", new Object[]{Integer.valueOf(heartRate)}));
            return;
        }
        byte[] data = characteristic.getValue();
        byte[] Mydata = new byte[data.length];
        this.mReadDataAnalysis.ReadData(data);
        this.mQueue.hasData(data);
        Log.e(TAG, "data=" + BleTool.ByteToString(data));
    }

    public IBinder onBind(Intent intent) {
        return this.mBinder;
    }

    public void onCreate() {
        super.onCreate();
        Log.d(TAG, "onCreate() ");
//        this.bleLocation = new BleLocation(this);
        this.mReadDataAnalysis = new ReadDataAnalysis(this, new C08452());
        this.mReadReceiver = new ReadBleReceiver(this, this);
        this.mReadReceiver.registerReceiver();
        this.mSendTimer = new SendTimer(this);
        this.mQueue = new WriteDataQueue(this);
        initialize();
        this.mShared = new BleSharedPreferences(this);
    }

    public void onDestroy() {
        Log.d(TAG, "onCreate() ");
        disconnect();
        close();
        this.mReadReceiver.onDestroy();
        this.mSendTimer.closeAll();
        this.mQueue.close();
        super.onDestroy();
    }

    public boolean onUnbind(Intent intent) {
        close();
        return super.onUnbind(intent);
    }

    @SuppressLint({"NewApi"})
    public boolean initialize() {
        if (this.mBluetoothManager == null) {
            this.mBluetoothManager = (BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
            if (this.mBluetoothManager == null) {
                Log.e(TAG, "Unable to initialize BluetoothManager.");
                return false;
            }
        }
        this.mBluetoothAdapter = this.mBluetoothManager.getAdapter();
        if (this.mBluetoothAdapter != null) {
            return true;
        }
        Log.e(TAG, "Unable to obtain a BluetoothAdapter.");
        return false;
    }

    @SuppressLint({"NewApi"})
    @TargetApi(18)
    public boolean connect(BleBase mBase) {
        Log.w(TAG, "connect");
        if (this.mBluetoothAdapter == null || mBase == null || mBase.getAddress() == null) {
            Log.w(TAG, "BluetoothAdapter not initialized or unspecified address.");
            return false;
        } else if (this.mBleBase.getAddress() == null || !mBase.getAddress().equals(this.mBleBase.getAddress()) || this.mBluetoothGatt == null) {
            try {
                this.device = this.mBluetoothAdapter.getRemoteDevice(mBase.getAddress());
                if (this.device == null) {
                    Log.w(TAG, "Device not found.  Unable to connect.");
                    return false;
                }
                this.mBluetoothGatt = this.device.connectGatt(this, false, this.mGattCallback);
                Log.d(TAG, "Trying to create a new connection.");
                this.mBleBase = mBase;
                this.mBleStatus.setState(0);
                SendBleBroadcast.Changes(this, this.mBleBase, this.mBleStatus);
                return true;
            } catch (Exception e) {
                return false;
            }
        } else {
            Log.d(TAG, "Trying to use an existing mBluetoothGatt for connection.");
            if (!this.mBluetoothGatt.connect()) {
                return false;
            }
            this.mBleBase = mBase;
            this.mBleStatus.setState(0);
            SendBleBroadcast.Changes(this, this.mBleBase, this.mBleStatus);
            return true;
        }
    }

    public void disconnect() {
        Log.w(TAG, "disconnect");
        if (this.mBluetoothAdapter == null || this.mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return;
        }
        this.mShared.remoBleBase();
        this.mBluetoothGatt.disconnect();
    }

    public void close() {
        Log.w(TAG, "close");
        if (this.mBluetoothGatt != null) {
            this.mBluetoothGatt.close();
            this.mBluetoothGatt = null;
        }
    }

    @SuppressLint({"NewApi"})
    public void readCharacteristic(BluetoothGattCharacteristic characteristic) {
        if (this.mBluetoothAdapter == null || this.mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
        } else {
            this.mBluetoothGatt.readCharacteristic(characteristic);
        }
    }

    public Boolean setCharacteristicNotification(BluetoothGattCharacteristic characteristic, boolean enabled) {
        if (this.mBluetoothAdapter == null || this.mBluetoothGatt == null) {
            Log.w(TAG, "BluetoothAdapter not initialized");
            return Boolean.valueOf(false);
        }
        Boolean isNotification = Boolean.valueOf(this.mBluetoothGatt.setCharacteristicNotification(characteristic, enabled));
        if (SampleGattAttributes.HEART_RATE_MEASUREMENT.equals(characteristic.getUuid())) {
            BluetoothGattDescriptor descriptor = characteristic.getDescriptor(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG);
            descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            this.mBluetoothGatt.writeDescriptor(descriptor);
        }
        if (!SampleGattAttributes.NotifyCharacteristicUUID.equals(characteristic.getUuid())) {
            return isNotification;
        }
//        descriptor = characteristic.getDescriptor(SampleGattAttributes.CLIENT_CHARACTERISTIC_CONFIG);
//        descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
//        this.mBluetoothGatt.writeDescriptor(descriptor);
        Log.d("biao", "enable_lost");
        return isNotification;
    }

    @SuppressLint({"NewApi"})
    @TargetApi(18)
    public List<BluetoothGattService> getSupportedGattServices() {
        if (this.mBluetoothGatt == null) {
            return null;
        }
        return this.mBluetoothGatt.getServices();
    }

    public void renameBLE(String name) {
        BluetoothGattService alertService = this.mBluetoothGatt.getService(SampleGattAttributes.RNameServiceUUID);
        if (alertService == null) {
            Log.d(TAG, "Immediate Alert service not found!");
            return;
        }
        BluetoothGattCharacteristic alertLevel = alertService.getCharacteristic(SampleGattAttributes.RNameCharacteristicUUID);
        if (alertLevel == null) {
            Log.d(TAG, "Immediate Alert Level charateristic not found!");
            return;
        }
        int storedLevel = alertLevel.getWriteType();
        Log.d(TAG, "storedLevel() - name=" + name);
        Log.d(TAG, "storedLevel() - storedLevel=" + storedLevel);
        alertLevel.setValue(name);
        alertLevel.setWriteType(1);
        boolean status = this.mBluetoothGatt.writeCharacteristic(alertLevel);
        Log.d(TAG, "renameBLE() - status=" + status);
        if (status) {
            this.mBleBase.setName(name);
            this.mShared.setBleBase(this.mBleBase);
            SendBleBroadcast.Changes(this, this.mBleBase, this.mBleStatus);
        }
        SendBleBroadcast.settingUp(this, SendBleBroadcast.code_reName, Boolean.valueOf(status));
    }

    @SuppressLint({"NewApi"})
    public void LostWriteData(byte[] pwm_data_buf) {
        this.mQueue.enQueue(pwm_data_buf);
    }

    public void deQueue(byte[] pwm_data_buf) {
        if (pwm_data_buf != null && this.mBluetoothGatt != null) {
            Log.i(TAG, "storedLevel()=" + BleTool.ByteToString(pwm_data_buf));
            BluetoothGattService alertService = this.mBluetoothGatt.getService(SampleGattAttributes.WriteServiceUUID);
            if (alertService == null) {
                Log.d(TAG, "Immediate Alert service not found!");
                return;
            }
            BluetoothGattCharacteristic alertLevel = alertService.getCharacteristic(SampleGattAttributes.WriteCharacteristicUUID);
            if (alertLevel == null) {
                Log.i(TAG, "Immediate Alert Level charateristic not found!");
                return;
            }
            Log.d(TAG, "storedLevel() - storedLevel=" + alertLevel.getWriteType());
            alertLevel.setValue(pwm_data_buf);
            alertLevel.setWriteType(1);
            Log.d(TAG, "writeIasAlertLevel() - status=" + this.mBluetoothGatt.writeCharacteristic(alertLevel));
        }
    }

    public void enableLostNoti() {
        Log.w(TAG, "0");
        BluetoothGattService nableService = this.mBluetoothGatt.getService(SampleGattAttributes.NotifyServiceUUID);
        if (nableService == null) {
            Log.w(TAG, "1");
            return;
        }
        BluetoothGattCharacteristic TxPowerLevel = nableService.getCharacteristic(SampleGattAttributes.NotifyCharacteristicUUID);
        if (TxPowerLevel == null) {
            Log.w(TAG, "2");
        } else if (setCharacteristicNotification(TxPowerLevel, true).booleanValue()) {
            Log.w(TAG, "3");
            this.mSendTimer.sendPw(this.mBleBase.getPassWord());
        }
    }

    class C05771 extends BluetoothGattCallback {
        Boolean lj = Boolean.valueOf(true);

        C05771() {
        }

        @SuppressLint({"NewApi"})
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            if (newState == 2 && this.lj.booleanValue()) {
                BluetoothLeService.this.mBleStatus.setState(1);
                SendBleBroadcast.Changes(BluetoothLeService.this, BluetoothLeService.this.mBleBase, BluetoothLeService.this.mBleStatus);
                Log.i(BluetoothLeService.TAG, "Connected to GATT server.");
                Log.i(BluetoothLeService.TAG, "Attempting to start service discovery:" + BluetoothLeService.this.mBluetoothGatt.discoverServices());
                BluetoothLeService.this.mQueue.start();
            } else if (newState == 0) {
                BluetoothLeService.this.mSendTimer.closeAll();
                BluetoothLeService.this.mQueue.clear();
                BluetoothLeService.this.mQueue.close();
                BluetoothLeService.this.close();
                this.lj = Boolean.valueOf(true);
                BluetoothLeService.this.mBleStatus = new BleStatus();
                BluetoothLeService.this.mBleStatus.setState(-1);
                SendBleBroadcast.Changes(BluetoothLeService.this, BluetoothLeService.this.mBleBase, BluetoothLeService.this.mBleStatus);
                Log.i(BluetoothLeService.TAG, "Disconnected from GATT server.");
//                BluetoothLeService.this.bleLocation.stop();
            }
        }

        public void onDescriptorWrite(BluetoothGatt gatt, BluetoothGattDescriptor descriptor, int status) {
            super.onDescriptorWrite(gatt, descriptor, status);
            Log.e(BluetoothLeService.TAG, "onDescriptorWrite=" + descriptor.getUuid() + "------" + status);
        }

        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            if (status == 0) {
                Log.w(BluetoothLeService.TAG, "发现服务");
                BluetoothLeService.this.enableLostNoti();
                BluetoothLeService.this.mBleStatus.setState(2);
                SendBleBroadcast.Changes(BluetoothLeService.this, BluetoothLeService.this.mBleBase, BluetoothLeService.this.mBleStatus);
                this.lj = Boolean.valueOf(false);
                return;
            }
            Log.w(BluetoothLeService.TAG, "onServicesDiscovered received: " + status);
        }

        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            if (status == 0) {
                BluetoothLeService.this.broadcastUpdate(characteristic);
            }
        }

        @SuppressLint({"NewApi"})
        @TargetApi(18)
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            if (SampleGattAttributes.NotifyCharacteristicUUID.equals(characteristic.getUuid())) {
                BluetoothLeService.this.broadcastUpdate(characteristic);
            }
        }
    }

    public class LocalBinder extends Binder {
        public BluetoothLeService getService() {
            return BluetoothLeService.this;
        }
    }

    class C08452 implements ReadDataAnalysisListener {
        C08452() {
        }

        public void VerificationPw(Boolean ispw) {
            BluetoothLeService.this.mSendTimer.closePw();
            if (ispw.booleanValue()) {
                BluetoothLeService.this.mShared.setBleBase(BluetoothLeService.this.mBleBase);
                ReadBleBroadcast.ReadUp(BluetoothLeService.this, BLECommon.bAddrUnit);
                ReadBleBroadcast.SettingUp(BluetoothLeService.this, (byte) BLECommon.bAddrSetLock, 1);
                BluetoothLeService.this.mBleStatus.setState(3);
                BluetoothLeService.this.mSendTimer.sendUpTime();
//                BluetoothLeService.this.bleLocation.start();
            } else {
                BluetoothLeService.this.mBleStatus.setState(-2);
                BluetoothLeService.this.disconnect();
            }
            SendBleBroadcast.Changes(BluetoothLeService.this, BluetoothLeService.this.mBleBase, BluetoothLeService.this.mBleStatus);
        }

        public void Changes(BleStatus mStatus) {
            BluetoothLeService.this.mBleStatus = mStatus;
            SendBleBroadcast.Changes(BluetoothLeService.this, BluetoothLeService.this.mBleBase, BluetoothLeService.this.mBleStatus);
        }

        public void Changes(BleBase base) {
            BluetoothLeService.this.mBleBase = base;
            SendBleBroadcast.Changes(BluetoothLeService.this, BluetoothLeService.this.mBleBase, BluetoothLeService.this.mBleStatus);
        }
    }
}
