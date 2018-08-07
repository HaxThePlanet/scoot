package com.example.currentplacedetailsonmap.Activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGattCharacteristic;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.currentplacedetailsonmap.BroadcastReceiver.ReadBleBroadcast;
import com.example.currentplacedetailsonmap.BroadcastReceiver.SendBleReceiver;
import com.example.currentplacedetailsonmap.Data.BLECommon;
import com.example.currentplacedetailsonmap.Data.ReadDataAnalysis;
import com.example.currentplacedetailsonmap.Data.ReadDataAnalysisListener;
import com.example.currentplacedetailsonmap.R;
import com.example.currentplacedetailsonmap.base.BleBase;
import com.example.currentplacedetailsonmap.base.BleStatus;
import com.example.currentplacedetailsonmap.main.BluetoothLeService;
import com.example.currentplacedetailsonmap.main.SampleGattAttributes;
import com.example.currentplacedetailsonmap.tool.BleTool;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.places.GeoDataClient;
import com.google.android.gms.location.places.PlaceDetectionClient;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.zxing.integration.android.IntentIntegrator;

/**
 * An activity that displays a map showing the place at the device's current location.
 */
public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private GoogleMap mMap;
    private CameraPosition mCameraPosition;

    // The entry points to the Places API.
    private GeoDataClient mGeoDataClient;
    private PlaceDetectionClient mPlaceDetectionClient;

    // The entry point to the Fused Location Provider.
    private FusedLocationProviderClient mFusedLocationProviderClient;

    // A default location (Sydney, Australia) and default zoom to use when location permission is
    // not granted.
    private final LatLng mDefaultLocation = new LatLng(-373382, 121.8863);
    private static final int DEFAULT_ZOOM = 13;
    private static final int PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1;
    private static final int REQUEST_CAMERA_RESULT = 1;
    private boolean mLocationPermissionGranted;

    // The geographical location where the device is currently located. That is, the last-known
    // location retrieved by the Fused Location Provider.
    private Location mLastKnownLocation;
    private ReadDataAnalysis mReadDataAnalysis;
    // Keys for storing activity state.
    private static final String KEY_CAMERA_POSITION = "camera_position";
    private static final String KEY_LOCATION = "location";
    public BleBase mBase;
    private BleTool mBleTool;
    private SendBleReceiver mSendBleReceiver;
    private BleStatus mState = new BleStatus();
    private BleStatus mBleStatus = new BleStatus();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Retrieve location and camera position from saved instance state.
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
            mCameraPosition = savedInstanceState.getParcelable(KEY_CAMERA_POSITION);
        }

        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_main);

        // Construct a GeoDataClient.
        mGeoDataClient = Places.getGeoDataClient(this, null);

        // Construct a PlaceDetectionClient.
        mPlaceDetectionClient = Places.getPlaceDetectionClient(this, null);

        // Construct a FusedLocationProviderClient.
        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        // Build the map.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //Start BT service
        startService(new Intent(this, BluetoothLeService.class));

        initUI();
        enableBT();
    }

    private void enableBT() {
        BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (!mBluetoothAdapter.isEnabled()) {
            mBluetoothAdapter.disable();
        }
    }
    private void initUI() {
        TextView scanButton = findViewById(R.id.ride_button);
        scanButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //initiate scan with our custom scan activity
                new IntentIntegrator(MainActivity.this).setCaptureActivity(ScannerActivity.class).initiateScan();
            }
        });

        TextView leftButton = findViewById(R.id.button2);
        leftButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                mState.setState(4);
                ReadBleBroadcast.SettingUp(getApplication(), (byte) BLECommon.bAddrSetGear, 2);
            }
        });

        TextView rightButton = findViewById(R.id.button3);
        rightButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                if (mState == null || !(mState.getState() == 3 || mState.getState() == 4)) {
//                    Toast.makeText(getApplicationContext(), "CONNECT BLE", Toast.LENGTH_LONG).show();
//                    return;
//                }
                mState.setState(4);
                ReadBleBroadcast.SettingUp(MainActivity.this, (byte) BLECommon.bAddrSetLock, 1);


                ReadBleBroadcast.SettingUp(getApplicationContext(), (byte) BLECommon.bAddrSetLight, true);
            }
        });


//        mSendBleReceiver = new SendBleReceiver(this, new C08523());
//        mSendBleReceiver.registerReceiver();
        mReadDataAnalysis = new ReadDataAnalysis(this, new C08452());
    }

    class C08452 implements ReadDataAnalysisListener {
        C08452() {
        }

        public void VerificationPw(Boolean ispw) {
        }

        public void Changes(BleStatus mStatus) {
            mBleStatus = mStatus;

        }

        public void Changes(BleBase base) {
//            mBleBase = base;
        }
    }

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
        Log.e(TAG, "data=" + BleTool.ByteToString(data));
    }

    /**
     * Saves the state of the map when the activity is paused.
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_CAMERA_POSITION, mMap.getCameraPosition());
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    /**
     * Manipulates the map when it's available.
     * This callback is triggered when the map is ready to be used.
     */
    @Override
    public void onMapReady(GoogleMap map) {
        mMap = map;

        // Prompt the user for permission.
        getLocationPermission();

        // Turn on the My Location layer and the related control on the map.
        updateLocationUI();

        // Get the current location of the device and set the position of the map.
        getDeviceLocation();

        getCameraPermission();
    }

    /**
     * Gets the current location of the device, and positions the map's camera.
     */
    private void getDeviceLocation() {
        /*
         * Get the best and most recent location of the device, which may be null in rare
         * cases when a location is not available.
         */
        try {
            if (mLocationPermissionGranted) {
                Task<Location> locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        if (task.isSuccessful()) {
                            // Set the map's camera position to the current location of the device.
                            mLastKnownLocation = task.getResult();
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(
                                    new LatLng(mLastKnownLocation.getLatitude(),
                                            mLastKnownLocation.getLongitude()), DEFAULT_ZOOM));
                        } else {
                            Log.d(TAG, "Current location is null. Using defaults.");
                            Log.e(TAG, "Exception: %s", task.getException());
                            mMap.moveCamera(CameraUpdateFactory
                                    .newLatLngZoom(mDefaultLocation, DEFAULT_ZOOM));
                            mMap.getUiSettings().setMyLocationButtonEnabled(false);
                        }
                    }
                });
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getCameraPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[] {android.Manifest.permission.CAMERA},REQUEST_CAMERA_RESULT);
        }
    }

    /**
     * Prompts the user for permission to use the device location.
     */
    private void getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                android.Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            mLocationPermissionGranted = true;
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION);
        }
    }

//    class C08523 implements SendBleReceiver.BLESendListener {
//        C08523() {
//        }
//
//        public void Changes(BleBase mBleBase, BleStatus mBleStatus) {
//            switch (mBleStatus.getState()) {
//                case -1:
////                    MainActivity.this.setConnect(Boolean.valueOf(false));
//                    mState = new BleStatus();
////                    MainActivity.this.setData(MainActivity.this.mState);
//                    break;
//                case 3:
////                    ReadBleBroadcast.SettingUp(MainActivity.this, (byte) BLECommon.bAddrSetLock, 1);
//                    break;
//                case 4:
////                    MainActivity.this.setConnect(Boolean.valueOf(true));
////                    MainActivity.this.setData(mBleStatus);
//                    break;
//            }
//            mState = mBleStatus;
//            mBase = mBleBase;
////            MainActivity.this.getMyApplication().mBase = MainActivity.this.mBase;
////            MainActivity.this.getMyApplication().mState = MainActivity.this.mState;
////            MainActivity.this.hornView.setmState(MainActivity.this.mState);
//        }
//
//        public void settingUp(int code, Boolean isstate) {
//        }
//    }

    /**
     * Handles the result of the request for location permissions.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[],
                                           @NonNull int[] grantResults) {
        mLocationPermissionGranted = false;
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mLocationPermissionGranted = true;
                }
            }
        }
        updateLocationUI();
    }

    /**
     * Updates the map's UI settings based on whether the user has granted location permission.
     */
    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mLocationPermissionGranted) {
                mMap.setMyLocationEnabled(true);
                mMap.getUiSettings().setMyLocationButtonEnabled(true);
            } else {
                mMap.setMyLocationEnabled(false);
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mLastKnownLocation = null;
                getLocationPermission();
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }
}
