package zh.com.cn.demo_eventbus;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import org.greenrobot.eventbus.EventBus;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2016/12/4.
 */
public class Second extends AppCompatActivity {
    @Bind(R.id.bt)
    Button bt;
    private Person p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        ButterKnife.bind(this);
        initData();
    }

    private void initData() {
        p = new Person("小明",12);

    }

    @OnClick(R.id.bt)
    public void onClick() {
        EventBus.getDefault().post(p);
    }
}
