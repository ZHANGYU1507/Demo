package com.example.demo_ble;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.demo_ble.Utils.Utils;
import com.example.demo_ble.contract.BleStateInterface;
import com.example.demo_ble.service.BleService;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {

    private final String TAG = MainActivity.class.getSimpleName();
    // 开启蓝牙的RequestCode
    private static final int REQUEST_ENABLE_BT = 1;

    private ListView listView;
    private BluetoothAdapter mBluetoothAdapter;
    private ServiceConnection conn;
    private com.example.demo_ble.service.IBinder mIBinder;

    private List<BluetoothDevice> list;
    private BleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 注册eventBus
        EventBus.getDefault().register(this);
        listView = (ListView) findViewById(R.id.listview);
        list = new ArrayList<>();
        listView.setOnItemClickListener(this);
    }
    // 打开蓝牙进行扫描
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void openClick(View view){

        //首先获取bluetoothadapter
        mBluetoothAdapter = Utils.getBLEAdapter(this);
        if(mBluetoothAdapter == null){// 说明设备不支持蓝牙，或者不支持BLE
            Toast.makeText(this, "此设备不支持蓝牙，或者不支持BLE", Toast.LENGTH_SHORT).show();
        }else{
            if (!mBluetoothAdapter.isEnabled()){// 如果蓝牙没有开启，就开启蓝牙
                //也可以直接调用mBluetoothAdapter.enable() mBluetoothAdapter.disable()来启用禁用、关闭蓝牙。不过这种方式不会弹出询问对话框
                Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                startActivityForResult(enableBtIntent, REQUEST_ENABLE_BT);
            }else{// 蓝牙已经开启，开启服务进行扫描
                startBleService();
            }
        }
    }

    // 开启服务进行扫描
    private void startBleService() {
        conn = new ServiceConnection(){
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                mIBinder = (com.example.demo_ble.service.IBinder) service;
                if (mIBinder != null){
                    mIBinder.scanBleDevice(true);
                }
            }

            @Override
            public void onServiceDisconnected(ComponentName name) {

            }
        };
        bindService(new Intent(this, BleService.class), conn, Context.BIND_AUTO_CREATE);
    }

    // 这里为了获取扫描到的蓝牙设备
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onDataSynEvent(BluetoothDevice mBluetoothDevice){
        if(list.indexOf(mBluetoothDevice)== -1){
            list.add(mBluetoothDevice);
            setBleAdapter();
        }
    }

    // 这里为了获取蓝牙的连接状态
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void onConnState(Integer state){
        switch (state){
            case BleStateInterface.STATE_CONNECTED:// 连接成功
                Toast.makeText(this, "蓝牙连接成功..", Toast.LENGTH_SHORT).show();
                break;
            case BleStateInterface.STATE_DISCONNECTED:// 连接失败
                Toast.makeText(this, "蓝牙连接失败..", Toast.LENGTH_SHORT).show();
                break;
        }
    }

    private void setBleAdapter(){
        if (adapter == null){
            adapter = new BleAdapter(list, this);
            listView.setAdapter(adapter);
        }else{
            adapter.notifyDataSetChanged();
        }


    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ENABLE_BT){
            switch (resultCode){
                case Activity.RESULT_OK:// 蓝牙成功开启
                    // 开启服务进行扫描
                    startBleService();
                    break;
                case Activity.RESULT_CANCELED:// 蓝牙开启失败
                    Toast.makeText(this, "蓝牙开启失败。。", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }

    // listview的点击事件，连接蓝牙
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        String address = list.get(position).getAddress();
        if(mIBinder != null){
            boolean flag = mIBinder.connBle(address);
            if(!flag){// 返回false一定是蓝牙连接失败，返回true也不一定成功。。因为最终是否连接成功看连接的返回状态，异步的，这里只是减少一些可能性
                Toast.makeText(this, "蓝牙连接失败..", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
