package zh.com.cn.demo_rxjava;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/11/18.
 */
public interface GetWeatherAPI {
    @GET("exp/index")
    Call<WeathBean> getWeather(@Query("com") String com, @Query("no") String no,@Query("key") String key);
}
