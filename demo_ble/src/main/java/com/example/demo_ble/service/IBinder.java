package com.example.demo_ble.service;

/**
 * Created by zy on 2017/4/28.
 */

public interface IBinder {
    // 蓝牙扫描
    public void scanBleDevice(boolean isScan);

    //  连接蓝牙
    public boolean connBle(String address);

    //  检索设备的服务
    public void searchServices();
    // 蓝牙开灯/关灯
    public void openCloseLamp(String instructions);
    // 蓝牙信息
    public void bleInfo();
}
