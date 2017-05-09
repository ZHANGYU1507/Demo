package com.example.demo_ble;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.demo_ble.bean.BleInfo;
import com.example.demo_ble.contract.BleStateInterface;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BleOperation extends AppCompatActivity {

    private TextView tv_info;
    private boolean isService = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_blthoperation);
        tv_info = (TextView) findViewById(R.id.tv_info);
        isService = getIntent().getBooleanExtra("isService", false);
        EventBus.getDefault().register(this);
    }
    public void infoClick(View view){
        if(isService == false){
            Toast.makeText(BleOperation.this, "获取失败..", Toast.LENGTH_SHORT).show();
            return;
        }
        EventBus.getDefault().post(BleStateInterface.INFO_STATE);

    }
    public void lampClick(View view){
        if(isService == false){
            Toast.makeText(BleOperation.this, "灯失败..", Toast.LENGTH_SHORT).show();
            return;
        }
        Button bt = (Button) view;
        String str = bt.getText().toString().trim();
        if("开灯".equals(str)){
            EventBus.getDefault().post(BleStateInterface.LAMP_OPEN_STATE);
            bt.setText("关灯");
        }else{
            EventBus.getDefault().post(BleStateInterface.LAMP_CLOSE_STATE);
            bt.setText("开灯");
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN, priority = 100)
    public void getbleInfo(BleInfo mBleInfo){
        switch (mBleInfo.getType()){
            case BleStateInterface.INFO_STATE:// 版本信息
                tv_info.setText(mBleInfo.getVersion());
                break;
        }
    }
}
