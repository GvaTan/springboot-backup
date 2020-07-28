/**
 * 
 */
package top.anets.filter;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.springframework.cloud.netflix.zuul.filters.support.FilterConstants;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

/**
 * @author Administrator
 *
 */
@Component
public class PreFilter extends ZuulFilter{

	@Override
	public boolean shouldFilter() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Object run() {
//		得到请求环境对象
		RequestContext requestContext = RequestContext.getCurrentContext();
//		得到http请求对象
		HttpServletRequest request = requestContext.getRequest();
//		得到token，验证用户
		String token = request.getHeader("token");
		
		if(StringUtils.isBlank(token)) {
//			给网关发送响应，false代表  禁止，没必要进行路由转发了
			requestContext.setSendZuulResponse(false);
//			给客户端一个交代,401错误代表用户没有访问权限，需要进行身份认证
			requestContext.setResponseStatusCode(401);
			requestContext.setResponseBody("\"msg\":\"没有权限\"");
			
			return "access denied!";
		}else {
			return "pass";
		}
		
	}

	@Override
	public String filterType() {
//		自定义的什么类型就返回啥
//		return "pre";
		return FilterConstants.PRE_TYPE;
	}

	@Override
	public int filterOrder() {
		//返回的值越小优先级越高
		return 0;
	}

}
