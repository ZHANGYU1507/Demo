package com.example.demo_ble;

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.example.demo_ble.Utils.Utils;

public class MainActivity extends AppCompatActivity {

    // 开启蓝牙的RequestCode
    private static final int REQUEST_ENABLE_BT = 1;

    private ListView listView;
    private BluetoothAdapter mBluetoothAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
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
            }else{// 蓝牙已经开启，进行扫描
                scanLeDevice();
            }
        }
    }

    // 搜索蓝牙设备
    private void scanLeDevice() {

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_ENABLE_BT){
            switch (resultCode){
                case Activity.RESULT_OK:// 蓝牙成功开启
                    // 进行扫描
                    scanLeDevice();
                    break;
                case Activity.RESULT_CANCELED:// 蓝牙开启失败
                    Toast.makeText(this, "蓝牙开启失败。。", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    }
}
