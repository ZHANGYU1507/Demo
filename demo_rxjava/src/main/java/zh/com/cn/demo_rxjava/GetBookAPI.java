package zh.com.cn.demo_rxjava;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Administrator on 2016/11/18.
 */
public interface GetBookAPI {
    @GET("book/recommend.from")
    Call<BookBean> getBook(@Query("key") String key, @Query("cat") int cat, @Query("ranks") int ranks);
}
