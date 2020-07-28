/**
 * 
 */
package top.anets.msg;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Administrator
 *
 */
@Slf4j
@Controller
@RequestMapping("/msg")
public class MsgController {
	@RequestMapping("/sendCode")
	@ResponseBody
	ConcurrentHashMap sendMsgCode(String key,String phone,String code){
    	ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
    	if(!key.equals("anets.top")) {
    		map.put("success",false);
    		map.put("msg","签名验证失败");
    		return map;
    	}
    	
    	if(StringUtils.isEmpty(phone)||StringUtils.isEmpty(code)) {
    		map.put("success",false);
    		map.put("msg","the parameters (phone,code) is necessary!");
    		return map;
    	}
    	
//    	发短信
    	DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GDbtQQGQ47NjB9srKFm", "gsbHbqyzsOc6blxwZLSaHbCwn0DgmK");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setSysMethod(MethodType.POST);
        request.setSysDomain("dysmsapi.aliyuncs.com");
        request.setSysVersion("2017-05-25");
        request.setSysAction("SendSms");
        request.putQueryParameter("RegionId", "cn-hangzhou");
        request.putQueryParameter("PhoneNumbers", phone);
        request.putQueryParameter("SignName", "anets");
        request.putQueryParameter("TemplateCode", "SMS_194051876");
        request.putQueryParameter("TemplateParam", "{'code':"+code+"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            String data = response.getData();
            Map result = JSON.parseObject(data, Map.class);
            
            if(result.get("Code").equals("OK")) {
            	map.put("success",true);
            }else {
            	map.put("success",false);
            	map.put("msg",result.get("Code"));
            }
    		
    		return map;
        } catch (Exception e) {
            e.printStackTrace();
            log.info("发短信出错");
            map.put("success",false);
    		map.put("msg",e.getMessage());
    		return map;
        } 
    }
}
