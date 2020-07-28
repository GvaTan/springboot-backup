package com.jt.controller;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.dubbo.config.annotation.Reference;
import com.jt.pojo.User;
import com.jt.service.DubboUserService;
import com.jt.vo.SysResult;

import redis.clients.jedis.JedisCluster;

//该controller需要实现页面跳转
@Controller
@RequestMapping("/user")
public class UserController {
	
	
	//注入dubbo接口
	@Reference(check = false)	//默认采用随机算法.
	private DubboUserService userService;   //RPC调用
	@Autowired
	private JedisCluster jedisCluster;
	
	
	
	//http://www.jt.com/user/login.html
	//http://www.jt.com/user/register.html
	@RequestMapping("/{moduleName}")
	public String module(@PathVariable String moduleName) {
		
		return moduleName;
	}
	
	/**
	 * 用户注册
	 * 1.url地址: 	http://www.jt.com/user/doRegister
	 * 2.请求参数:   username/password/phone
	 * 3.返回值结果:  SysResult对象
	 */
	@RequestMapping("/doRegister")
	@ResponseBody
	public SysResult doRegister(User user) {
		
		userService.saveUser(user);
		return SysResult.success();
	}
	
	/**
	 * 用户登录实现
	 * 1.url地址:/user/doLogin
	 * 2.参数:username/password
	 * 3.返回值: SysResult对象
	 * 
	 * Cookie介绍:
	 * 		默认条件下Cookie只会记录当前网址的信息.由于业务需要如果Cookie
	 * 需要共享数据时,则必须设定相关属性
	 * 
	 */
	@RequestMapping("/doLogin")
	@ResponseBody
	public SysResult doLogin(User user,HttpServletResponse response) {
		
		//1.校验用户信息是否有效
		String uuid = userService.findUserByUP(user);
		
		//2.校验uuid是否有效
		if(StringUtils.isEmpty(uuid)) {
			//说明用户名或密码错误
			return SysResult.fail();
		}else {
			//如果成功,需要将数据保存到cookie中  cookieName="JT_TICKET"
			Cookie cookie = new Cookie("JT_TICKET", uuid);
			cookie.setMaxAge(7 * 24 * 3600); 	//7天超时
			//域名共享 
			cookie.setDomain("jt.com");
			//设定cookie有效性范围
			cookie.setPath("/");
			response.addCookie(cookie);
			return SysResult.success();
		}
	}
	
	
	/**
	 * 用户登出操作
	 * 1.url地址:http://www.jt.com/user/logout.html
	 * 2.参数: null
	 * 3.返回值: 重定向到系统首页  String
	 * 业务需求:    1.删除redis   2.删除cookie
	 */
	@RequestMapping("/logout")
	public String logout(HttpServletRequest request,HttpServletResponse response) {
		//1.获取JT_TICKET的Cookie的值
		Cookie[] cookies = request.getCookies();
		//判断cookie是否有值
		if(cookies !=null && cookies.length>0) {
			for (Cookie cookie : cookies) {
				String name = cookie.getName();
				if("JT_TICKET".equals(name)) {
					//说明当前Cookie存在
					String ticket = cookie.getValue();
					//2.删除redis需要key
					jedisCluster.del(ticket);
					//3.删除cookie   只能修改超时时间
					//由于cookie只能获取key-value所以需要自定义参数
					cookie.setMaxAge(0);//0 立即删除   -1 关闭会话之后删除
					cookie.setDomain("jt.com");
					cookie.setPath("/");
					response.addCookie(cookie);
					break;
				}
			}
		}
		return "redirect:/";
	}
	
	
	
	
	
	
}
