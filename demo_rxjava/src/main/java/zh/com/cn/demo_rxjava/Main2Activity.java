package zh.com.cn.demo_rxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import rx.Subscriber;
import zh.com.cn.demo_rxjava.zh.com.cn.httpservice.HttpApi;

public class Main2Activity extends AppCompatActivity {

    //
    @Bind(R.id.tv2)
    TextView tv2;
    @Bind(R.id.bt2)
    Button bt2;
    private StringBuilder sb = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

    }
   Subscriber<String> subscriber = new Subscriber<String>() {
       @Override
       public void onCompleted() {
           tv2.setText(sb.toString());
       }

       @Override
       public void onError(Throwable e) {
           tv2.setText(e.getMessage());
       }

       @Override
       public void onNext(String s) {
            sb.append(s);
       }
   };

    @OnClick(R.id.bt2)
    public void onClick() {
        HttpApi.getData(subscriber);

    }
}
