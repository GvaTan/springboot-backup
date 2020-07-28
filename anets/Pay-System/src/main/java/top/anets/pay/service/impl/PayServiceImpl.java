/**
 * 
 */
package top.anets.pay.service.impl;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.Console;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.lly835.bestpay.enums.BestPayPlatformEnum;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.service.BestPayService;
import lombok.extern.slf4j.Slf4j;
import top.anets.dao.PayInfoDao;
import top.anets.domain.Payinfo;
import top.anets.domain.VxConfig;
import top.anets.pay.service.PayService;
import top.anets.utils.HttpUtils;

/**
 * @author Administrator
 *
 */
@Slf4j
@Transactional
@Service
public class PayServiceImpl implements PayService{
    @Autowired
    private BestPayService bestPayService;
	
    @Autowired
    private PayInfoDao payInfoDao;
	@Override

	public PayResponse create(String orderId, BigDecimal amount,BestPayTypeEnum bestPayTypeEnum) {
		
//		写入数据库
		Payinfo payinfo=new Payinfo();
		payinfo.setOrderId(orderId);
		payinfo.setAmount(amount.doubleValue());
		payinfo.setPayType(bestPayTypeEnum.name());
		payinfo.setCreateTime(new Date());
		payinfo.setStatus((byte)0);
		payInfoDao.insert(payinfo);
		
//		支付请求
		PayRequest payRequest = new PayRequest();
		payRequest.setOrderName("uid-anets");
		payRequest.setOrderId(orderId);
		payRequest.setOrderAmount(amount.doubleValue());
		payRequest.setPayTypeEnum(bestPayTypeEnum);
		
		PayResponse payResponse = bestPayService.pay(payRequest);
		
//		log.info("response={}",payResponse);
		return payResponse;
	}
	
	
	@Override
	public String asynNotify( String  notifyData) {
		System.out.println("支付结果通知");
//		签名校验
		PayResponse payResponse = bestPayService.asyncNotify(notifyData);
		log.info("notifyData={}",payResponse);
		
		
		
//		金额校验（从数据库查订单)
		String orderId = payResponse.getOrderId();
		List<Payinfo> orders=payInfoDao.selectByOrderId(orderId);
		Payinfo payinfo = null;
		if(orders!=null&&orders.size()!=0)  payinfo = orders.get(0);
		
//		修改订单状态
		if(payinfo!=null&&payResponse.getOrderAmount().equals(payinfo.getAmount())) {
			log.info("修改订单状态");
			payinfo.setStatus((byte) 1);
			payinfo.setFinishTime(new Date());
            payInfoDao.update(payinfo);			
		}
		

		
//		告诉支付宝/微信不要再重复通知了
		if(payResponse.getPayPlatformEnum()==BestPayPlatformEnum.WX) {
			return "<xml>\n"+
		               " <return_code><![CDATA[SUCCESS]]</return_code>\n"+
				       " <return_msg><![CDATA[OK]]</return_msg>\n"+
		               "</xml>";
		}else if (payResponse.getPayPlatformEnum()==BestPayPlatformEnum.ALIPAY) {
			return "success";
		}
		
			throw new RuntimeException("异步通知中错误的支付平台");
		
		
	}


	@Override
	public Payinfo queryOrder(String orderId) {
		List<Payinfo> list = payInfoDao.selectByOrderId(orderId);
		if(list==null||list.size()==0) return null;
		
		return list.get(0);
	}


	@Override
	public PayResponse create_WxPay_Jsapi(String code ,String orderId, BigDecimal amount, BestPayTypeEnum type) {
//		支付请求
		PayRequest payRequest = new PayRequest();
		payRequest.setOrderName("uid-anets");
		payRequest.setOrderId(orderId);
		payRequest.setOrderAmount(amount.doubleValue());
		payRequest.setPayTypeEnum(type);
		
		payRequest.setOpenid(this.getOpenid(code));
		PayResponse payResponse = bestPayService.pay(payRequest);
		
//		log.info("response={}",payResponse);
		return payResponse;
	}


	/**
	 * openid是用户标识，证明是用户点击的，贼麻烦
	* jsapi支付必须获取openid,前端必须用户手动点击传递到服务器 code
	* 获取步骤:
	* 1引导前端用户点击：
	*  https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=SCOPE&state=STATE#wechat_redirect
	*  比如我的就是https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx4648a3be714ccd3f&redirect_uri=http://souainet.top/soai/wx/jsapi&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect
	*  注意，域名必须在微信后台配置好
	*@return 
	* 
	*/
	private String getOpenid(String code) {
		VxConfig config = new VxConfig();
		
		String urlstr="https://api.weixin.qq.com/sns/oauth2/access_token?appid="+config.getAppId()+"&secret="+config.getAppSecret()+"&code="+code+"&grant_type=authorization_code";
		System.out.println("获取openid的url："+urlstr);
		
		
		String string = HttpUtils.get(urlstr);
		//ServletActionContext.getResponse().getWriter().write("getResult:"+string+"|||");
		System.out.println("获得openid"+string);
		
		
		Gson gson = new Gson();
		Map map = gson.fromJson(string, Map.class);
		String openid = (String) map.get("openid");
		//测试用
		if(openid==null){
			//ServletActionContext.getResponse().getWriter().write("[openid为空]");
		    openid="souaitoysyha";
		}else{
			//ServletActionContext.getResponse().getWriter().write("[openid不为空]"+ openid);
		}
		return openid;
	}

}