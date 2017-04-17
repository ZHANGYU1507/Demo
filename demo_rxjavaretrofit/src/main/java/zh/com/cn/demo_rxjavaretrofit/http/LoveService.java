package zh.com.cn.demo_rxjavaretrofit.http;

import java.util.List;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;
import zh.com.cn.demo_rxjavaretrofit.entity.ContentBean;
import zh.com.cn.demo_rxjavaretrofit.entity.LoveBean;

/**
 * Created by Administrator on 2016/11/29.
 */
public interface LoveService {
    public static final String baseUrl = "http://japi.juhe.cn/";
    @FormUrlEncoded
    @POST("love/list.from")
    Observable<LoveBean<List<ContentBean>>> getLoveData(@Field("key") String key, @Field("count") int count, @Field("cat") int cat);
}
