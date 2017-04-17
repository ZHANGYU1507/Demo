package zh.com.cn.demo_rxjavaretrofit.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import zh.com.cn.demo_rxjavaretrofit.R;
import zh.com.cn.demo_rxjavaretrofit.entity.ContentBean;
import zh.com.cn.demo_rxjavaretrofit.http.HttpMethod;
import zh.com.cn.demo_rxjavaretrofit.subscribers.ProgressSubscriber;

public class MainActivity extends AppCompatActivity {
    @Bind(R.id.tv)
    public TextView tv;
    private StringBuffer sb;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Subscribe
    public void onEventMainThread(List<ContentBean> list) {
        sb = new StringBuffer();
       for(ContentBean contentBean : list){
           sb.append(contentBean.toString()+"\n");
       }
        tv.setText(sb.toString());
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @OnClick(R.id.bt)
    public void click(){
        getData();
    }
    private void getData() {
        String key = "5de077233f3b5b2f8478fb18ee145d11";
        HttpMethod.getInstance().getLoveData(key, 10, 7,new ProgressSubscriber<ContentBean>(MainActivity.this, true));
    }
}
