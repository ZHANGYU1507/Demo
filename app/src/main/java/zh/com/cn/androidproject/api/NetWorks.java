package zh.com.cn.androidproject.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrator on 2017/4/13.
 */

public class NetWorks {

    private static final int DEFAULT_TIMEOUT = 5;

    private static Retrofit retrofit;
    private static NetWorks mNetWorks;

    private static MainApi mMainApi;


    private NetWorks(){};
    public static NetWorks getInstance(){

        if(mNetWorks == null){
            mNetWorks =  new NetWorks();
        }
        return mNetWorks;
    }

    public  MainApi getMainApi(){
        return mMainApi == null ?configRetrofit(MainApi.class):mMainApi;
    }
    private <T> T configRetrofit(Class<T> service){
        retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/api/")
                .client(configClinet())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit.create(service);
    }

    private OkHttpClient configClinet(){
        OkHttpClient.Builder okHttpClient = new OkHttpClient.Builder().connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        return okHttpClient.build();
    }
}
