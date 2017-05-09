package com.example.demo_ble.contract;

/**
 * Created by zy on 2017/4/28.
 */

public interface BleStateInterface {

    public static final int STATE_DISCONNECTED = 0;// 断开
    public static final int STATE_CONNECTING = 1;// 连接中
    public static final int STATE_CONNECTED = 2;// 连接成功
    public static final int STATE_SEARCH = 3;// 检索到设备服务
    public static final int STATE_DISSEARCH = 4;// 没有检索到设备服务

    public static final int LAMP_OPEN_STATE = 5;// 开灯
    public static final int INFO_STATE = 6;// 获取蓝牙信息
    public static final int LAMP_CLOSE_STATE = 7;// 关灯

}
