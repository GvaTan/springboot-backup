/**
 * 
 */
package top.anets.api.service;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator
 *
 */
@RequestMapping("/member")
public interface TestApiService {
	@RequestMapping("/test")
   public Map<String,Object> test(Integer id,String name);
}
