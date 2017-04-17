package zh.com.cn.demo_rxjava.zh.com.cn.httpservice;

import com.zhy.http.okhttp.OkHttpUtils;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

/**
 * Created by Administrator on 2017/3/20.
 */

public class HttpMethod {

    private static OkHttpClient okHttpClient;

    private HttpMethod() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(5000, TimeUnit.MILLISECONDS)
                .readTimeout(5000, TimeUnit.MILLISECONDS)
                .build();
        OkHttpUtils.initClient(okHttpClient);
    }

    private static class SingleInstance{
        private static HttpMethod httpMethod = new HttpMethod();

    }

    public static HttpMethod getInstance() {
        return SingleInstance.httpMethod;
    }

    public  OkHttpClient getOkHttpClient(){
        return okHttpClient;
    }
}
