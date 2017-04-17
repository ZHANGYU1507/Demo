package zh.com.cn.demo_rxjavaretrofit.subscribers;

import android.app.usage.UsageEvents;
import android.content.Context;
import android.os.Message;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import zh.com.cn.demo_rxjavaretrofit.progress.ProgressCancelListener;
import zh.com.cn.demo_rxjavaretrofit.progress.ProgressDialogHandler;

/**
 * Created by Administrator on 2016/11/29.
 */
public class ProgressSubscriber<T> extends Subscriber<T> implements ProgressCancelListener{
    private List<T> list;
    private Context context;
    private ProgressDialogHandler progressDialogHandler;
    public ProgressSubscriber(Context context, boolean cancelable){
        this.context = context;
        progressDialogHandler = new ProgressDialogHandler(context, cancelable, this);
    }
    @Override
    public void onStart() {
        super.onStart();
        showProgressDialog();
        list = new ArrayList<T>();
    }

    // 显示进度的dialog
    private void showProgressDialog() {
        if(progressDialogHandler != null){
            progressDialogHandler.obtainMessage(ProgressDialogHandler.SHOW_PROGRESS_DIALOG).sendToTarget();
        }
    }

    @Override
    public void onCompleted() {
        dismissProgressDialog();
        EventBus.getDefault().post(list);
    }

    // 隐藏进度dialog
    private void dismissProgressDialog() {
        if(progressDialogHandler != null){
            progressDialogHandler.obtainMessage(ProgressDialogHandler.DISSMISS_PROGRESS_DIALOG).sendToTarget();
            progressDialogHandler = null;
        }
    }

    @Override
    public void onError(Throwable e) {
        if(e instanceof SocketTimeoutException){
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        }else if(e instanceof ConnectException){
            Toast.makeText(context, "网络中断，请检查您的网络状态", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "error:" + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        dismissProgressDialog();
    }
    @Override
    public void onNext(T t) {

    }

    /*
    * 取消dialog同时取消网络请求  list.add(t);
    * */
    @Override
    public void CancelProgress() {
        if(!this.isUnsubscribed()){
            this.unsubscribe();
        }
    }
}
