package zh.com.cn.demo_rxjavaretrofit.rxbus;

import com.trello.rxlifecycle.ActivityLifecycleProvider;
import com.trello.rxlifecycle.FragmentLifecycleProvider;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;
/**
 * Created by Administrator on 2016/12/5.
 */
public class RxBus {
    private static RxBus rxBus;
    // 这个对象即是被观察者也是观察者
    private final Subject<Object, Object> subject = new SerializedSubject<Object, Object>(PublishSubject.create());
    private RxBus(){

    }
    // 双重判断。保证rxBus对象的唯一性
    public static RxBus getInstance(){
        if(rxBus == null){
            synchronized (RxBus.class){
                if(rxBus == null){
                    rxBus = new RxBus();
                }
            }
        }
        return rxBus;
    }
    // 发送数据，观察者调用onnext方法。
    public void send(Object object){
        subject.onNext(object);
    }
    public void send(@Events.EventCode int code, Object object){
        Events<Object> event = new Events<>();
        event.content = object;
        event.code = code;
        send(event);
    }
    public Observable<Object> getObservable(){
        return subject;
    }

    public static class SubscriberBuilder{
        private FragmentLifecycleProvider fragmentLifecycleProvider;
        private ActivityLifecycleProvider activityLifecycleProvider;
        public SubscriberBuilder(FragmentLifecycleProvider fragmentLifecycleProvider){
            this.fragmentLifecycleProvider = fragmentLifecycleProvider;
        }
        public SubscriberBuilder(ActivityLifecycleProvider activityLifecycleProvider){
            this.activityLifecycleProvider = activityLifecycleProvider;
        }
    }
}
