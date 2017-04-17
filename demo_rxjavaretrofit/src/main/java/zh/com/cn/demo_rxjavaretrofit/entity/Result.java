package zh.com.cn.demo_rxjavaretrofit.entity;

import java.util.List;

/**
 * Created by Administrator on 2016/11/29.
 */
public class Result<T> {
    private T data;
    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
