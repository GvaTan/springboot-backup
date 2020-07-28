/**
 * 
 */
package top.anets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.anets.service.UserFeignClient;

/**
 * @author Administrator
 *
 */
@RestController
public class UserController {
	@Autowired
	private UserFeignClient userFeignClient;
	@RequestMapping("/user/login")
	public String toLogin(String usernameString ,String password) {
//		逻辑判断...
//		调用服务检验
		for(int i=0;i<30;i++) {
			String info = userFeignClient.login();
		}
		return "负载均衡测试：调用完成，请在日志查看输出效果";
	}
}
