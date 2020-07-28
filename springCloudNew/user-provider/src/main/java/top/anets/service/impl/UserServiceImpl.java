/**
 * 
 */
package top.anets.service.impl;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 *
 */
@RestController
public class UserServiceImpl {
	@RequestMapping("/user/login")
    public String login() {
		return "anets";
	}
}
