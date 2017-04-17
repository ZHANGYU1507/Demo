package zh.com.cn.androidproject;

import rx.Subscriber;
import zh.com.cn.androidproject.entity.MainEntity;

/**
 * Created by Administrator on 2017/4/13.
 */

public class MainPresenter extends MainContract.Presenter{

    @Override
    public void getData() {
        mRxManager.add(mModel.getTempData().subscribe(new Subscriber<MainEntity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(MainEntity mainEntity) {
                mView.show(mainEntity);
            }
        }));
    }
}
