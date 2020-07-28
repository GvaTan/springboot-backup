/**
 * 
 */
package top.anets.service;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import top.anets.adapter.MessageAdapter;

/**
 * @author Administrator
 *
 */
@Service
public class EmailService implements MessageAdapter{

	@Override
	public void sendMsg(JSONObject body) {
		//发送邮件
	}

}
