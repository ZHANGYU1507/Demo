package zh.com.cn.demo_rxjavaretrofit.http;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;
import zh.com.cn.demo_rxjavaretrofit.entity.ContentBean;
import zh.com.cn.demo_rxjavaretrofit.entity.LoveBean;
import zh.com.cn.demo_rxjavaretrofit.entity.Result;

/**
 * Created by Administrator on 2016/11/29.
 */
public class HttpMethod {
    private static final int DEFAULT_TIMEOUT = 5;
    private Retrofit retrofit;
    private LoveService loveService;
    private HttpMethod(){
       // 手动创建一个okhttp请求并设置超时时间
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        retrofit = new Retrofit.Builder()
                .client(builder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(LoveService.baseUrl)
                .build();
        loveService = retrofit.create(LoveService.class);
    }
    private static class SingleInstance{
        private static HttpMethod httpMetod = new HttpMethod();
    }
    public static HttpMethod getInstance(){
        return SingleInstance.httpMetod;
    }

    public <T> void getLoveData(String key, int count, int cat, Subscriber<ContentBean> subscriber){
        Observable<ContentBean> contentBeanObservable = loveService.getLoveData(key, count, cat)
                .map(new HttpResultMapFunc<List<ContentBean>>())
                .flatMap(new HttpResultFlatMapFunc<List<ContentBean>, ContentBean>());
        toSubscrible(contentBeanObservable, subscriber);
    }
    private <T> void toSubscrible(Observable<T> listObservable, Subscriber<T> subscriber) {
        listObservable.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber);
    }
    private class HttpResultMapFunc<T> implements Func1<LoveBean<T>,Result<T>>{
        @Override
        public Result<T> call(LoveBean<T> tLoveBean) {
            if(tLoveBean.getError_code() != 0){
                throw new ApiException(100);
            }
            return tLoveBean.getResult();
        }
    }
    private class HttpResultFlatMapFunc<T,B> implements  Func1<Result<T>, Observable<B>>{
        @Override
        public Observable<B> call(Result<T> tResult) {
            return (Observable<B>) Observable.from((Iterable<? extends T>) tResult.getData());
        }
    }
}
