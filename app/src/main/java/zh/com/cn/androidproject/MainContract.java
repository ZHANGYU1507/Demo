package zh.com.cn.androidproject;

import rx.Observable;
import zh.com.cn.androidproject.base.BaseModel;
import zh.com.cn.androidproject.base.BasePresenter;
import zh.com.cn.androidproject.base.BaseView;
import zh.com.cn.androidproject.entity.MainEntity;

/**
 * Created by Administrator on 2017/4/13.
 */

public interface MainContract {

    interface Model extends BaseModel {
         Observable<MainEntity> getTempData();
    }
    interface View extends BaseView {
        void show(MainEntity entity);
    }

    abstract class Presenter extends BasePresenter<View, Model>{
         abstract void getData();
    }

}
