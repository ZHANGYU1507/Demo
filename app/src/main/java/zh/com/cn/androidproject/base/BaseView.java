package zh.com.cn.androidproject.base;

/**
 * Created by Administrator on 2017/4/12.
 */

public interface BaseView {
    void onRequestStart();
    void onRequestError(String msg);
    void onRequestEnd();
    void onInternetError();
}
