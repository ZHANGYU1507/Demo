package zh.com.cn.demo_rxjava.zh.com.cn.mode;

/**
 * Created by Administrator on 2017/3/20.
 */

public class Bean{

    private int code;
    private String msg;
    private ListBean data;

    public ListBean getData() {
        return data;
    }

    public void setData(ListBean data) {
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }


    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "Bean{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
