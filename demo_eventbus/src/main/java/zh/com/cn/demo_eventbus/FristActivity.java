package zh.com.cn.demo_eventbus;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/4.
 */
public class FristActivity extends AppCompatActivity {
    @Bind(R.id.tv)
    TextView tv;
    @Bind(R.id.bt)
    Button bt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frist);
        ButterKnife.bind(this);
        EventBus.getDefault().register(this);
    }

    @Subscribe
    public void onEventMainThread(Person p) {
        Log.i("TAG","fristActivity..."+p.toString());
        tv.setText(p.toString());
    }

    @OnClick(R.id.bt)
    public void onClick() {
        startActivity(new Intent(FristActivity.this, Second.class));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
