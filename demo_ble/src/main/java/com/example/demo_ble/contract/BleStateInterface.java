package com.example.demo_ble.contract;

/**
 * Created by zy on 2017/4/28.
 */

public interface BleStateInterface {

    public static final int STATE_DISCONNECTED = 0;// 断开
    public static final int STATE_CONNECTING = 1;// 连接中
    public static final int STATE_CONNECTED = 2;// 连接成功
}
