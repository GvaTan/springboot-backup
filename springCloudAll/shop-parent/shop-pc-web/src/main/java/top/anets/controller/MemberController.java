/**
 * 
 */
package top.anets.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import top.anets.base.Result;
import top.anets.entity.User;
import top.anets.feign.MemberFeign;

/**
 * @author Administrator
 *
 */
@Controller
public class MemberController {
    @Autowired
    private MemberFeign memberFeign;
    
    @RequestMapping("/member/register")
    public Result register(User user) {
    	Result regUser = memberFeign.regUser(user);
		return regUser;
    }
    
    @RequestMapping("/member/get")
    public Result register(Long id) {
    	Result regUser = memberFeign.findUserById(id);
		return regUser;
    }
}
