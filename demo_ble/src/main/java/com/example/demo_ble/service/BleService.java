package com.example.demo_ble.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothGattCallback;
import android.bluetooth.BluetoothGattCharacteristic;
import android.bluetooth.BluetoothGattDescriptor;
import android.bluetooth.BluetoothGattService;
import android.bluetooth.BluetoothProfile;
import android.content.Intent;
import android.os.*;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.util.Log;

import com.example.demo_ble.Utils.Utils;
import com.example.demo_ble.bean.BleInfo;
import com.example.demo_ble.contract.BleStateInterface;

import org.greenrobot.eventbus.EventBus;

import java.util.List;
import java.util.UUID;

/**
 * Created by zy on 2017/4/28.
 */

public class BleService extends Service{
    private final String TAG = BleService.class.getSimpleName();
    private BluetoothAdapter mBluetoothAdapter;
    // 10秒后停止查找搜索.
    private static final long SCAN_PERIOD = 10000;
    // 是否正在扫描
    private boolean mScanning;
    private Handler mHandler = null;

    private String mBleAdrress = null;// 保存连接的地址
    private BluetoothGatt mBluetoothGatt;
    private BluetoothGattService mBluetoothGattService;

    private BleInfo mBleInfo = new BleInfo();

    private int mConnectionState = BleStateInterface.STATE_DISCONNECTED;// 默认断开的状态

    @SuppressLint("NewApi")
    private BluetoothGattCallback mGattCallback = new BluetoothGattCallback() {
        //当连接上设备或者失去连接时会回调该函数
        @Override
        public void onConnectionStateChange(BluetoothGatt gatt, int status, int newState) {
            super.onConnectionStateChange(gatt, status, newState);
            Log.i("mBluetoothDevice", "newState:"+newState);
            if(newState == BluetoothProfile.STATE_CONNECTED){// 连接成功
                mConnectionState = BleStateInterface.STATE_CONNECTED;
                EventBus.getDefault().post(mConnectionState);
            }else if(newState == BluetoothProfile.STATE_DISCONNECTED){// 连接失败
                mConnectionState = BleStateInterface.STATE_DISCONNECTED;
                EventBus.getDefault().post(mConnectionState);
            }
        }

        // 当设备是否找到服务时，
        @Override
        public void onServicesDiscovered(BluetoothGatt gatt, int status) {
            super.onServicesDiscovered(gatt, status);
            if(status == BluetoothGatt.GATT_SUCCESS){// 找到服务
                List<BluetoothGattService> services = getSupportedGattServices();
                // 根据uuid，获取自己需要的服务
                mBluetoothGattService = mBluetoothGatt.getService(UUID.fromString(Utils.ServiceUUID));
                BluetoothGattCharacteristic read = mBluetoothGattService.getCharacteristic(UUID.fromString(Utils.ReadUUID));
                // 是否开启设备通知
                setCharacteristicNotification(read , true);
                EventBus.getDefault().post(BleStateInterface.STATE_SEARCH);

            }else{
                EventBus.getDefault().post(BleStateInterface.STATE_DISSEARCH);
            }
        }

        // 当读取设备时回调该函数
        @Override
        public void onCharacteristicRead(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicRead(gatt, characteristic, status);
            Log.i(TAG, "onCharacteristicRead status:"+status+":characteristic:"+characteristic.toString());
            // 获取到版本号
            String version = characteristic.getStringValue(0);
            mBleInfo.setVersion(version);
            mBleInfo.setType(BleStateInterface.INFO_STATE);
            EventBus.getDefault().post(mBleInfo);
        }

        // 当向设备写数据时，回调该函数
        @Override
        public void onCharacteristicWrite(BluetoothGatt gatt, BluetoothGattCharacteristic characteristic, int status) {
            super.onCharacteristicWrite(gatt, characteristic, status);
            Log.i(TAG, "onCharacteristicWrite status:"+status);
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

    // 开启设备通知
    @SuppressLint("NewApi")
    private void setCharacteristicNotification(BluetoothGattCharacteristic read, boolean enabled) {
        if(mBluetoothGatt == null || mBluetoothAdapter == null){
            Log.i(TAG, "没有初始化");
            return;
        }
        mBluetoothGatt.setCharacteristicNotification(read, enabled);
        // 通知的uuid对象
        BluetoothGattDescriptor descriptor =  read.getDescriptor(UUID.fromString(Utils.NotifyUUID));
        if(descriptor != null){
            if (enabled) {
                descriptor.setValue(BluetoothGattDescriptor.ENABLE_NOTIFICATION_VALUE);
            } else {
                descriptor.setValue(BluetoothGattDescriptor.DISABLE_NOTIFICATION_VALUE);
            }
            mBluetoothGatt.writeDescriptor(descriptor);
        }

    }

    // 获取服务列表
    @SuppressLint("NewApi")
    public List<BluetoothGattService> getSupportedGattServices() {
        if (mBluetoothGatt == null)
            return null;
        return mBluetoothGatt.getServices();   //此处返回获取到的服务列表
    }
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
        //开关灯
        @Override
        public void openCloseLamp(String instructions) {


        }


        // 获取蓝牙信息
        @Override
        public void bleInfo() {
            getBleInfo();
        }
    }

    // 获取蓝牙的设备信息
    @SuppressLint("NewApi")
    public void getBleInfo(){
        // 根据设备信息通道的uuid，获取通道的服务
        BluetoothGattService infoService = mBluetoothGatt.getService(UUID.fromString(Utils.BluetoothDeviceInfo));
        // 根据固件版本的UUID，获取BluetoothGattCharacteristic对象
        BluetoothGattCharacteristic firmwareVersion = infoService.getCharacteristic(UUID.fromString(Utils.BluetoothFirmwareVersion));
        boolean flag =  mBluetoothGatt.readCharacteristic(firmwareVersion);
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
     * 向设备中写数据
     * @param data
     */
    @SuppressLint("NewApi")
    private void writeDataToBle(String data){

        BluetoothGattCharacteristic writerChara = mBluetoothGattService.getCharacteristic(UUID.fromString(Utils.WriteUUID));
        mBluetoothGatt.setCharacteristicNotification(writerChara, true);// 设置通知监听
        boolean flag = mBluetoothGatt.writeCharacteristic(writerChara);
        // 当数据传递到蓝牙之后
        // 会回调BluetoothGattCallback里面的write方法
        writerChara.setWriteType(BluetoothGattCharacteristic.WRITE_TYPE_DEFAULT);

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
