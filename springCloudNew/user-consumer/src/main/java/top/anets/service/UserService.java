/**
 * 
 */
package top.anets.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator
 *
 */
@FeignClient(name = "user-provider")
public interface UserService {
    @RequestMapping("/user/login")
    public String login();
}
