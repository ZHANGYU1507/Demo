package zh.com.cn.demo_rxjavaretrofit.progress;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Handler;
import android.os.Message;

/**
 * Created by Administrator on 2016/11/30.
 */
public class ProgressDialogHandler extends Handler implements DialogInterface.OnCancelListener {

    public static final int SHOW_PROGRESS_DIALOG = 1;// 显示dialog
    public static final int DISSMISS_PROGRESS_DIALOG = 0;// 隐藏dialog

    private Context context;
    private boolean cancelable;
    private ProgressDialog progressDialog;
    private ProgressCancelListener progressCancelListener;

    public ProgressDialogHandler(Context context, boolean cancelable, ProgressCancelListener progressCancelListener) {
        this.context = context;
        this.cancelable = cancelable;
        this.progressCancelListener = progressCancelListener;
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
        switch (msg.what) {
            case SHOW_PROGRESS_DIALOG:
                showProgressDialog();
                break;
            case DISSMISS_PROGRESS_DIALOG:
                dismissProgressDialog();
                break;
        }
    }

    // 隐藏进度条
    private void dismissProgressDialog() {
        if (progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
    }

    // 显示进度条
    private void showProgressDialog() {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setCancelable(cancelable);
            if (cancelable) {
                progressDialog.setOnCancelListener(this);
            }

            if (!progressDialog.isShowing()) {
                progressDialog.show();
            }
        }
    }

    @Override
    public void onCancel(DialogInterface dialog) {
        progressCancelListener.CancelProgress();
    }
}
