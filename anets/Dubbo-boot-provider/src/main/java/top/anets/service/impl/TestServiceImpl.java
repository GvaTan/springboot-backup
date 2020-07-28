/**
 * 
 */
package top.anets.service.impl;

import com.alibaba.dubbo.config.annotation.Service;

import top.anets.service.TestService;

/**
 * @author Administrator
 *
 */
@Service()
public class TestServiceImpl implements TestService{

	@Override
	public String testDubbo() {
		// TODO Auto-generated method stub
		return "dubbo测试成功";
	}

}
