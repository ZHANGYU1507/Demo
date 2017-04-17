package zh.com.cn.demo_rxjava.zh.com.cn.httpservice;



import com.google.gson.Gson;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;


import java.util.List;

import okhttp3.Call;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;
import zh.com.cn.demo_rxjava.zh.com.cn.mode.Bean;
import zh.com.cn.demo_rxjava.zh.com.cn.mode.ListBean;

/**
 * Created by Administrator on 2017/3/20.
 */

public class  HttpApi {

    public static  void getData(final Subscriber subscriber){
        final Gson gson = new Gson();
        HttpMethod httpMethod = HttpMethod.getInstance();
        String url = "http://www.520m.com.cn/api/register/journalism-list";
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e, int id) {
            }
            @Override
            public void onResponse(String response, int id) {
                Bean bean = new Bean();
                bean = gson.fromJson(response, bean.getClass());
                parseClass(bean, subscriber);
            }
        });
    }
    private static void parseClass(Bean bean, Subscriber subscriber){

        List<ListBean.PagedataBean> list = bean.getData().getPagedata();
        Observable.from(list).map(new Func1<ListBean.PagedataBean, String>() {
            @Override
            public String call(ListBean.PagedataBean pagedataBean) {
                return pagedataBean.toString();
            }
        }).subscribe(subscriber);

    }
}
