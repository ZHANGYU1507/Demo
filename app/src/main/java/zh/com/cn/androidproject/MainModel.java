package zh.com.cn.androidproject;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import zh.com.cn.androidproject.api.NetWorks;
import zh.com.cn.androidproject.entity.MainEntity;
import zh.com.cn.androidproject.rx.RxSchedulerHelper;

/**
 * Created by Administrator on 2017/4/13.
 */

public class MainModel implements MainContract.Model {
    /**
     *
     * @return
     */
    @Override
    public Observable<MainEntity> getTempData() {

        return NetWorks.getInstance().getMainApi()
                .getMainData().compose(RxSchedulerHelper.<MainEntity>applySchedulers());
    }

//@Override
//public Observable<MainEntity> getTempData() {
//
//    return NetWorks.getInstance().getMainApi()
//            .getMainData().subscribeOn(Schedulers.io())
//            // 消费线程
//            .observeOn(AndroidSchedulers.mainThread());
//}
//
//    protected  <T> Observable<T>   get(Observable<T> observable){
//
//        return observable.subscribeOn(Schedulers.io())
//                // 消费线程
//                .observeOn(AndroidSchedulers.mainThread());;
//    }
}
