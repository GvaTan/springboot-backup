/**
 * 
 */
package top.anets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import top.anets.service.UserService;

/**
 * @author Administrator
 *
 */
@RestController
public class UserController {
    @Autowired
    private UserService userService;
    
    
    @RequestMapping("/user/login")
    public String toLogin() {
    	return userService.login();
    }
}
