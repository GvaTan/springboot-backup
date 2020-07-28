/**
 * 
 */
package top.anets.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 *
 */
@RestController
public class UserService {
	private Logger log=LoggerFactory.getLogger(UserService.class);
	@RequestMapping("/user/login")
    public String login() {
		 log.info("access provide 7781");
    	 return "the user has been verified!";
    }
}
