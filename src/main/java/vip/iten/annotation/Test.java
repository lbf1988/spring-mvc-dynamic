package vip.iten.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Test
 *
 * @author Brant Liu <br> 邮箱：<br>lbf1988@qq.com <br>日期：<br>2018/2/13
 * @version 1.0.0
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Test {
}
