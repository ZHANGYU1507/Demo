package zh.com.cn.androidproject;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import zh.com.cn.androidproject.base.BaseActivity;
import zh.com.cn.androidproject.entity.MainEntity;

public class MainActivity extends BaseActivity<MainPresenter, MainModel> implements MainContract.View{

    @BindView(R.id.show)
    TextView show;

    @Override
    protected void initContentView(Bundle bundle) {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void initData() {
        setmToolbarTitle("我的标题");
        setmToolbarBack("销毁", tv_back);
        setmToolbarBack("可以了", tv_confirm);
        mPresenter.getData();
    }

    @Override
    protected void initEvent() {
        if (null != tv_confirm) {
            tv_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showShortToast("click");
                }
            });
        }
    }

    @Override
    public void show(MainEntity entity) {
        show.setText(entity.toString());
    }

}
