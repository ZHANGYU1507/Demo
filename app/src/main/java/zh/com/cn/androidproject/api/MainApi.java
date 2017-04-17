package zh.com.cn.androidproject.api;


import retrofit2.http.GET;
import rx.Observable;
import zh.com.cn.androidproject.entity.MainEntity;

/**
 * Created by Administrator on 2017/4/13.
 */

public interface MainApi {
    @GET("random/data/Android/1")
    Observable<MainEntity> getMainData();
}
