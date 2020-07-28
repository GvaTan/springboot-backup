/**
 * 
 */
package top.anets.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Administrator
 *自定义注解类的使用
 *@Retention(RetentionPolicy.RUNTIME)//声明注解的生命周期
 *@Target(ElementType.METHOD)//生命注解的作用域
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {
	int seconds();
//  允许最大访问数
	int maxCount();
//	是否需要登录
	boolean needLogin() default true;
}
