package zh.com.cn.demo_rxjavaretrofit.entity;


/**
 * Created by Administrator on 2016/11/29.
 */
public class LoveBean<T> {
    private int error_code;
    private String reason;
    private Result<T>  result;

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
        this.error_code = error_code;
    }

    public Result<T> getResult() {
        return result;
    }

    public void setResult(Result<T> result) {
        this.result = result;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
