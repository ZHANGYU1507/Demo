package zh.com.cn.androidproject.base;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.ButterKnife;
import butterknife.Unbinder;
import zh.com.cn.androidproject.R;
import zh.com.cn.androidproject.Utils.TUtils;

/**
 * Created by Administrator on 2017/4/11.
 */

public abstract class BaseActivity<T extends BasePresenter, M extends BaseModel> extends AppCompatActivity implements BaseView{
    public Toolbar mToolbar;
    public TextView tv_back, tv_title, tv_confirm;
    protected Unbinder unbinder;
    protected T mPresenter;
    protected M mModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initContentView(savedInstanceState);

        mPresenter = TUtils.getT(this, 0);
        mModel = TUtils.getT(this, 1);
        unbinder =  ButterKnife.bind(this);

        // 判断是否集成了baseview接口，并且使用了mvp
        if(mPresenter != null &&  this instanceof BaseView){
            mPresenter.attachVM(this, mModel);
        }

        // 设置沉浸式
        setTranslucentStatus(true);
        // 设置toolbar
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        if (null != mToolbar) {
            initToolbar();
            setSupportActionBar(mToolbar);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
        initData();
        initEvent();
    }

    /**
     * 初始化toolbar
     */
    protected void initToolbar() {
        tv_back = (TextView) findViewById(R.id.toobal_back);
        tv_title = (TextView) findViewById(R.id.toobal_title);
        tv_confirm = (TextView) findViewById(R.id.toobal_confirm);
        if (null != tv_back) {
            tv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }
    }
    // 设置toolbar的标题
    public void setmToolbarTitle(@Nullable String title) {
        if (null != tv_title && !TextUtils.isEmpty(title)) {
            tv_title.setText(title);
        }
    }
    // 设置toolbar的返回按钮或者确定按钮，可能是文字
    public void setmToolbarBack(@Nullable String title, TextView tv) {
        if (null != tv && !TextUtils.isEmpty(title)) {
            tv.setText(title);
            tv.setCompoundDrawables(null, null, null, null);
        }
    }
    // 设置toolbar的返回按钮或者确定按钮，可能是图片
    public void setmToolbarBack(int resourceId, TextView tv, boolean isLeft) {
        if(null != tv){
            Drawable nav_up = getResources().getDrawable(resourceId);
            nav_up.setBounds(0, 0, nav_up.getMinimumWidth(), nav_up.getMinimumHeight());
            tv.setText("");
            if (isLeft) {
                tv.setCompoundDrawables(null, null, null, nav_up);
            } else {
                tv.setCompoundDrawables(null, null, nav_up, null);
            }
        }
    }

    /**
     * 设置沉浸式
     *
     * @param on
     */
    protected void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window win = getWindow();
            WindowManager.LayoutParams winParams = win.getAttributes();
            //  透明状态栏
            final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
            if (on) {
                winParams.flags |= bits;
            } else {
                winParams.flags &= ~bits;
            }
            win.setAttributes(winParams);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(null != unbinder){
            unbinder.unbind();
        }

        if(null != mPresenter){
            mPresenter.detachVM();
            mPresenter = null;
        }

    }

    //Toast显示
    protected void showShortToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_SHORT).show();
    }


    protected void showLongToast(String string) {
        Toast.makeText(this, string, Toast.LENGTH_LONG).show();
    }

    /**
     * 界面跳转
     *
     * @param clazz 目标Activity
     */
    protected void readyGo(Class<?> clazz) {
        readyGo(clazz, null);
    }

    /**
     * 跳转界面，  传参
     *
     * @param clazz  目标Activity
     * @param bundle 数据
     */
    protected void readyGo(Class<?> clazz, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle)
            intent.putExtras(bundle);
        startActivity(intent);
    }

    /**
     * 跳转界面并关闭当前界面
     *
     * @param clazz 目标Activity
     */
    protected void readyGoThenKill(Class<?> clazz) {
        readyGoThenKill(clazz, null);
    }

    /**
     * @param clazz  目标Activity
     * @param bundle 数据
     */
    protected void readyGoThenKill(Class<?> clazz, Bundle bundle) {
        readyGo(clazz, bundle);
        finish();
    }

    /**
     * startActivityForResult
     *
     * @param clazz       目标Activity
     * @param requestCode 发送判断值
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode) {
        Intent intent = new Intent(this, clazz);
        startActivityForResult(intent, requestCode);
    }

    /**
     * startActivityForResult with bundle
     *
     * @param clazz       目标Activity
     * @param requestCode 发送判断值
     * @param bundle      数据
     */
    protected void readyGoForResult(Class<?> clazz, int requestCode, Bundle bundle) {
        Intent intent = new Intent(this, clazz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivityForResult(intent, requestCode);
    }

    /**
     * 替代onCreate的使用
     */
    protected abstract void initContentView(android.os.Bundle bundle);

    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化监听方法
     */
    protected abstract void initEvent();

    @Override
    public void onInternetError() {

    }

    @Override
    public void onRequestEnd() {

    }

    @Override
    public void onRequestError(String msg) {

    }

    @Override
    public void onRequestStart() {

    }
}
