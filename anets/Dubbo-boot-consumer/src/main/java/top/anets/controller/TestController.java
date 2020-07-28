/**
 * 
 */
package top.anets.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;

import top.anets.service.TestService;

/**
 * @author Administrator
 *
 */
@Controller
public class TestController {
	@Reference(loadbalance = "leastactive",url = "dubbo://192.168.64.1:20880/top.anets.service.TestService") 
	private TestService texService;
	
	@RequestMapping("/invoke")
	@ResponseBody
	String invokeService() {
		System.out.println("调用开始");
		return texService.testDubbo();
	}
   
}
