package com.example.demo_ble;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import java.util.List;

/**
 * Created by zy on 2017/4/28.
 */

public class BleAdapter extends BaseAdapter{
    private List<BluetoothDevice> list;
    private LayoutInflater inflater;
    public BleAdapter(List<BluetoothDevice> list, Context context){
        this.list = list;
        inflater = LayoutInflater.from(context);
    }
    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView == null){
            vh = new ViewHolder();
            convertView = inflater.inflate(R.layout.item, parent,false);
            vh.tv_address = (TextView) convertView.findViewById(R.id.address);
            vh.tv_name = (TextView) convertView.findViewById(R.id.name);
            convertView.setTag(vh);
        }else {
            vh = (ViewHolder) convertView.getTag();
        }
        BluetoothDevice bluetoothDevice = list.get(position);
        vh.tv_name.setText("设备名字："+bluetoothDevice.getName());
        vh.tv_address.setText("设备地址："+bluetoothDevice.getAddress());
        return convertView;
    }
    class ViewHolder{
        TextView tv_name, tv_address;
    }
}
