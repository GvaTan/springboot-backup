/**
 * 
 */
package top.anets.mq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;

import lombok.extern.slf4j.Slf4j;
import top.anets.adapter.MessageAdapter;
import top.anets.constants.Constants;
import top.anets.service.EmailService;
/**
 * @author Administrator
 */
@Slf4j
@Component
public class ConsumerDistribute {
	@Autowired
	private EmailService emailService;
	private MessageAdapter messageAdapter;
	@JmsListener(destination = "messages_queue")
//	监听消息
	public void distribute(String json) {
		log.info("####ConsumerDistribute###distribute() 消息服务平台接受 json参数:" + json);
		if (StringUtils.isEmpty(json)) {
			return;
		}
		JSONObject jsonObecjt = new JSONObject().parseObject(json);
		JSONObject header = jsonObecjt.getJSONObject("header");
		String interfaceType = header.getString("interfaceType");

		if (StringUtils.isEmpty(interfaceType)) {
			return;
		}
		if (interfaceType.equals(Constants.SMS_MAIL)) {
			messageAdapter = emailService;
		}
		if (messageAdapter == null) {
			return;
		}
		JSONObject body = jsonObecjt.getJSONObject("content");
		 messageAdapter.sendMsg(body);

	}

}