/**
 * 
 */
package top.anets.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator
 *
 */
@FeignClient(name="user-provider")
public interface UserFeignClient {
	@RequestMapping("/user/login")
	public String login();

}
