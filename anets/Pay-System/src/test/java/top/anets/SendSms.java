package top.anets;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
/*
pom.xml
<dependency>
  <groupId>com.aliyun</groupId>
  <artifactId>aliyun-java-sdk-core</artifactId>
  <version>4.5.0</version>
</dependency>
*/
public class SendSms {
	 public static void main(String[] args) {
	        DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI4GDbtQQGQ47NjB9srKFm", "gsbHbqyzsOc6blxwZLSaHbCwn0DgmK");
	        IAcsClient client = new DefaultAcsClient(profile);

	        CommonRequest request = new CommonRequest();
	        request.setSysMethod(MethodType.POST);
	        request.setSysDomain("dysmsapi.aliyuncs.com");
	        request.setSysVersion("2017-05-25");
	        request.setSysAction("SendSms");
	        request.putQueryParameter("RegionId", "cn-hangzhou");
	        request.putQueryParameter("PhoneNumbers", "17601571791");
	        request.putQueryParameter("SignName", "anets");
	        request.putQueryParameter("TemplateCode", "SMS_194051876");
	        request.putQueryParameter("TemplateParam", "{'code':'hhhhh'}");
	        try {
	            CommonResponse response = client.getCommonResponse(request);
	            System.out.println(response.getData());
	        } catch (ServerException e) {
	            e.printStackTrace();
	        } catch (ClientException e) {
	            e.printStackTrace();
	        }
	    }
}
