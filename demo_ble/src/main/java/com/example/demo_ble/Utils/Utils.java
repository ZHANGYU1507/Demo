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

    public static final String ServiceUUID = "00003f00-0000-1000-8000-00805f9b34fb";
    public static final String ReadUUID = "00003f01-0000-1000-8000-00805f9b34fb";
    public static final String WriteUUID = "00003f02-0000-1000-8000-00805f9b34fb";
    public static final String BluetoothDeviceInfo = "0000180a-0000-1000-8000-00805f9b34fb"; // 设备的版本号通道
    public static final String BluetoothFirmwareVersion = "00002a26-0000-1000-8000-00805f9b34fb"; // 版本号通道中的固件版本号UUID
    public static final String NotifyUUID = "00002902-0000-1000-8000-00805f9b34fb";// 设备通知

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

    /**
     * 16进制表示的字符串转换为字节数组
     *
     * @param s 16进制表示的字符串
     * @return byte[] 字节数组
     */
    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] b = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            // 两位一组，表示一个字节,把这样表示的16进制字符串，还原成一个字节
            b[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character
                    .digit(s.charAt(i + 1), 16));
        }
        return b;
    }
}
