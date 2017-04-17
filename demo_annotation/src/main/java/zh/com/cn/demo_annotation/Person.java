package zh.com.cn.demo_annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Target;

/**
 * Created by Administrator on 2016/12/5.
 */
@Target(ElementType.METHOD)// 用于描述方法
public @interface Person {
}
