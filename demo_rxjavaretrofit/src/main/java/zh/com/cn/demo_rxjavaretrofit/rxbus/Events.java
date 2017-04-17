package zh.com.cn.demo_rxjavaretrofit.rxbus;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by Administrator on 2016/12/5.
 */
public class Events<T> {
    public static final int TAP = 1;// 点击事件
    public static final int OTHER = 21;// 其它事件
    // 使用注解annotation 来定义一个枚举
    @IntDef({TAP, OTHER})
    @Retention(RetentionPolicy.SOURCE)
    public @interface EventCode{// 这是定义了一个annotation内部类

    }
    public @Events.EventCode int code;// code的值必须是1,21;

    public T content;

    public static <O> Events<O> setContent(O t) {
        Events<O> events = new Events<>();
        events.content = t;
        return events;
    }

    public <T> T getContent(){
        return (T) content;
    }
}
