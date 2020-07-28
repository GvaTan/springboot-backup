package top.anets.config;

import java.util.List;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import top.anets.interceptor.AccessIntercaptor;





/**
 * @author Administrator
 *
 */
@Configuration
public class SpringWebConfig extends WebMvcConfigurerAdapter{
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new AccessIntercaptor()).addPathPatterns("/*");
	}
}
