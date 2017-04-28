package com.example.demo_ble.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.*;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

import com.example.demo_ble.Utils.Utils;
import com.example.demo_ble.contract.BleStateInterface;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zy on 2017/4/28.
 */

public class BleService extends Service{
    private BluetoothAdapter mBluetoothAdapter;
    // 10秒后停止查找搜索.
    private static final long SCAN_PERIOD = 10000;
    // 是否正在扫描
    private boolean mScanning;
    private Handler mHandler = null;

    private String mBleAdrress = null;// 保存连接的地址
    private BluetoothGatt mBluetoothGatt;

    private int mConnectionState = BleStateInterface.STATE_DISCONNECTED;// 默认断开的状态

    @SuppressLint("NewApi")
    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        //当连接上设备或者失去连接时会回调该函数
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            if(newState == BluetoothProfile.STATE_CONNECTED){// 连接成功
                mConnectionState = BleStateInterface.STATE_CONNECTED;
                EventBus.getDefault().post(mConnectionState);
            }else if(newState == BluetoothProfile.STATE_DISCONNECTED){// 连接成功
                mConnectionState = BleStateInterface.STATE_DISCONNECTED;
                EventBus.getDefault().post(mConnectionState);
            }
        }

        // 当设备是否找到服务时，
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
        }

        // 当读取设备时回调该函数
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
        }

        // 当向设备写数据时，回调该函数
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
        }

        // 设备发出通知时，回调该函数
        @Override
        public void onCharacteristicChanged(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic) {
            super.onCharacteristicChanged(gatt, characteristic);
        }


        @Override
        public void onReadRemoteRssi(BluetoothGatt gatt, int rssi, int status) {
        }

    };
    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    private void init() {
        mBluetoothAdapter = Utils.getBLEAdapter(getApplicationContext());
        mHandler = new Handler();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return new ServiceBinder();
    }

    class ServiceBinder extends Binder implements com.example.demo_ble.service.IBinder{
        // 蓝牙扫描
        @Override
        public void scanBleDevice(boolean isScan) {
            goScanLbe(isScan);
        }

        // 连接蓝牙
        @Override
        public boolean connBle(String address) {
            return goConnBle(address);
        }

        // 检索设备服务
        @SuppressLint("NewApi")
        @Override
        public void searchServices() {
           if(mBluetoothGatt != null){
               // 检索的结果会在BluetoothGattCallback的onServicesDiscovered显示
               mBluetoothGatt.discoverServices();
           }
        }
    }

    /**
     * 连接蓝牙
     * @param address 蓝牙地址
     */
    @SuppressLint("NewApi")
    private boolean goConnBle(String address) {

        if(mBluetoothAdapter == null || TextUtils.isEmpty(address)){
            return false;
        }
        // 先前连接的设备。 尝试重新连接
        if(mBleAdrress != null && address.equals(mBleAdrress) && mBluetoothGatt != null){
            if(mBluetoothGatt.connect()){
                mConnectionState = BleStateInterface.STATE_CONNECTING;
                return true;
            }else{
                return false;
            }
        }
        BluetoothDevice device = mBluetoothAdapter.getRemoteDevice(address);
        if(device == null){
            return false;
        }
        mBluetoothGatt = device.connectGatt(this, false, mGattCallback);
        mBleAdrress = address;
        mConnectionState = BleStateInterface.STATE_CONNECTING;
        return true;
    }


    /**
     * 是否扫描
     * @param isScan
     */
    // 蓝牙扫描
    @SuppressLint("NewApi")
    private void goScanLbe(boolean isScan) {
        if(isScan){
            mHandler.postDelayed(new Runnable() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
                @Override
                public void run() {
                    mScanning = false;
                    mBluetoothAdapter.stopLeScan(mLeScanCallback);
                }
            }, SCAN_PERIOD);
            mScanning = true;
            mBluetoothAdapter.startLeScan(mLeScanCallback);
        }else {
            mScanning = false;
            mBluetoothAdapter.stopLeScan(mLeScanCallback);
        }
    }
    @SuppressLint("NewApi")
   private BluetoothAdapter.LeScanCallback mLeScanCallback = new BluetoothAdapter.LeScanCallback(){

       @Override
       public void onLeScan(BluetoothDevice device, int rssi, byte[] scanRecord) {
           EventBus.getDefault().post(device);
       }
   };
}
