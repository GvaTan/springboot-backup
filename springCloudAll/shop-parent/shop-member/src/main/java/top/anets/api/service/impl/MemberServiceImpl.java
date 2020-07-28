/**
 * 
 */
package top.anets.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import top.anets.api.service.MemberService;
import top.anets.base.Result;
import top.anets.dao.MemberDao;
import top.anets.entity.User;
import top.anets.utils.MD5Util;

/**
 * @author Administrator
 *
 */
@RestController
public class MemberServiceImpl implements MemberService{
    @Autowired
    private MemberDao memberDao;
    
	@Override
	public Result findUserById(Long userId) {
		User user = memberDao.findByID(userId);
		return new Result(200, "ok",user);
	}

	@Override
	public Result regUser(@RequestBody User user) {
		String passWord=user.getPassword();
		String newPassWord=MD5Util.MD5(passWord);
		user.setPassword(newPassWord);
		Integer insertUser = memberDao.insertUser(user);
		if (insertUser <= 0) {
			return new Result(500, "注册失败",null);
		}
		return new Result(200, "成功",null);

	}

}
