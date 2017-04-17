package zh.com.cn.demo_rxjava;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

/**
 * Created by Administrator on 2016/11/18.
 */
public class NetWork {
    private static final String hostUrl = "http://japi.juhe.cn/";
    private static GetWeatherAPI getWeatherAPI ;
    private static GetBookAPI mBookAPI;
    public static GetWeatherAPI getWeatherAPI() {
        if (getWeatherAPI == null) {
            Retrofit retrofit = new Retrofit.Builder().baseUrl(hostUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            getWeatherAPI = retrofit.create(GetWeatherAPI.class);
        }
        return getWeatherAPI;
    }

    public static GetBookAPI getBookAPI(){
        if(mBookAPI == null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://japi.juhe.cn/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
            mBookAPI = retrofit.create(GetBookAPI.class);
        }
        return mBookAPI;
    }
}
