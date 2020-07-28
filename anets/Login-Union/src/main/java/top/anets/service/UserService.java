/**
 * 
 */
package top.anets.service;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 *
 */
@RestController
public interface UserService {
//	根据openid查用户
	@RequestMapping("/user/getUserByToken")
   User findUserByOpenid(String openid);
	
   @RequestMapping("/user/qqLogin")
   void qqLogin() ;
}
