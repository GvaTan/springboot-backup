/**
 * 
 */
package top.anets.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import top.anets.annotation.AccessLimit;

/**
 * @author Administrator
 * 防刷限流模拟
 * 主要是学习对注释的使用
 */
@Controller
public class UserController {
	@AccessLimit(maxCount = 10,needLogin = true,seconds = 100)
    @RequestMapping("/test")
    @ResponseBody
    public String test() {
    	return "hellow";
    }
}
