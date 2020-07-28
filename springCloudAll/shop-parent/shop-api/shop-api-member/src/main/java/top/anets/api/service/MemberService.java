/**
 * 
 */
package top.anets.api.service;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import top.anets.base.Result;
import top.anets.entity.User;

/**
 * @author Administrator
 *
 */
@RequestMapping("/member")
public interface MemberService {
//   使用userId查找用户信息
	@RequestMapping("/findUserById")
	 Result findUserById(Long userId); 
	
	Result regUser(@RequestBody User user);
}
