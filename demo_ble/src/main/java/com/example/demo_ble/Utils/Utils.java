package com.example.demo_ble.Utils;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresApi;

/**
 * Created by zy on 2017/4/27.
 */

public class Utils {
    private static BluetoothAdapter mBluetoothAdapter = null;
    // 是否支持BLE在设备上运行
    public static boolean isBLE(Context context){
        return  context.getPackageManager().hasSystemFeature(PackageManager.FEATURE_BLUETOOTH_LE);
    }
    //BluetoothAdapter 对象。

    /**
     * 获取bluetoothadapter有两中方法
     * 1.final BluetoothManager bluetoothManager =(BluetoothManager) getSystemService(Context.BLUETOOTH_SERVICE);
     *  BluetoothAdapter mBluetoothAdapter = bluetoothManager.getAdapter();
     * 2.BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
     *
     * @param context
     * @return
     */
    @SuppressLint("NewApi")
    public static BluetoothAdapter getBLEAdapter(Context context){
        // 如果不支持，则直接返回null
        if(!isBLE(context)){
            return null;
        }
        BluetoothManager bluetoothManager =(BluetoothManager) context.getSystemService(Context.BLUETOOTH_SERVICE);
        if(mBluetoothAdapter == null){
            mBluetoothAdapter = bluetoothManager.getAdapter();// 如果获取到的值为null，则说明此设别不支持蓝牙
        }
        return mBluetoothAdapter;
    }
}
