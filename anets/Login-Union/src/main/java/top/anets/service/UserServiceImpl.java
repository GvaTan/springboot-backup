/**
 * 
 */
package top.anets.service;

import org.springframework.util.StringUtils;

/**
 * @author Administrator
 *
 */
public class UserServiceImpl implements UserService{

	@Override
	public User findUserByOpenid(String openid) {
		//前提: 用户表要有openid这个字段
//		1验证参数，判断是否为空
		if(StringUtils.isEmpty(openid)) return null; 
//		2使用openid查询数据库user表对应的数据信息 
//		    -如果查询不到，返回 token 已经过期 
//	        -如果查询到了，就自动登录
		    
		
//		3自动登录
//		和之前的登录一样，生成token，把user对象存redis啊等等.....
		return null;
	}

	@Override
	public void qqLogin() {
		//用户点击扣扣登录后，没绑定账号的需要绑定账号
		
	}

}
