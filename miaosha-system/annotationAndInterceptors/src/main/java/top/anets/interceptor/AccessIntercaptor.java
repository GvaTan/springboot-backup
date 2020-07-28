/**
 * 
 */
package top.anets.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import top.anets.annotation.AccessLimit;


/**
 * @author Administrator
 *
 */
public class AccessIntercaptor extends HandlerInterceptorAdapter{
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		if(handler instanceof HandlerMethod) {
			HandlerMethod hm = (HandlerMethod)handler;
//			获取方法上的AccessLimit注释
			AccessLimit accessLimit = hm.getMethodAnnotation(AccessLimit.class);
//			如果没有注释，放行
			if(accessLimit==null) {
				return true;
			}
			int seconds = accessLimit.seconds();
			int maxCount = accessLimit.maxCount();
			boolean needLogin = accessLimit.needLogin();
			
			System.out.println("注释上的信息=:seconds:"+seconds+";maxConut:"+maxCount+";needLogin:"+needLogin);
			
			
		}
		
		
		
		
		
		
		
		System.out.println("在controller之前执行，前置初始化操作或者是对当前请求的一个预处理，比如说获取cookie的值或者判断是否已经登录");
		
//		true为放行，如果返回为false 时，表示请求结束，后续的Interceptor 和Controller 都不会再执行
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
         System.out.println("Controller方法调用之后执行,在DispatcherServlet视图返回渲染之前,可以在这个方法中设置cookie，返回给前端");
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		System.out.println("整个请求结束之后，也就是在DispatcherServlet 渲染了对应的视图之后执行。这个方法的主要作用是用于进行资源清理工作的");
	}

}