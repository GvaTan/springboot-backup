/**
 * 
 */
package top.anets.pay.controller;

import static org.hamcrest.CoreMatchers.nullValue;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;

import lombok.extern.slf4j.Slf4j;
import top.anets.domain.Payinfo;
import top.anets.pay.service.PayService;

/**
 * @author Administrator
 *
 */
@Controller
//@RequestMapping("/pay")
@RequestMapping("/soai") /*这个不能变，因为我在微信后台设置的安全域名是souainet.top/soai*/
@Slf4j
public class PayController {
   @Autowired
   private PayService payService;
	
   /**
    * 微信小程序-jsapi支付
   * code参数需要前端获取
   * 已经上线
   * 测试步骤
   * 访问https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx4648a3be714ccd3f&redirect_uri=http://souainet.top/soai/wx/code&response_type=code&scope=snsapi_base&state=STATE#wechat_redirect
   * 访问http://souainet.top/soai/wx/jsapi?orderId=5555&amount=0.1
   * 注意：订单号每次都要不一样才行
   * 
   *@return
    */
   @RequestMapping("/wx/jsapi")
   public ModelAndView wx_jsapi(HttpServletRequest request,String orderId,BigDecimal amount) {
	   log.info("orderId",orderId);
	   log.info("amount",amount);
	   HashMap<String, Object> map = new HashMap<>();
//	   获取到用户点击的code
	   String code = (String) request.getSession().getAttribute("code");
	   if(StringUtils.isEmpty(code)||StringUtils.isEmpty(orderId)||amount==null) {
		   map.put("success", false);
		   ModelAndView view = new ModelAndView("wxjsapi/pay.jsp");
		   view.addObject("payInfo", map);
		   return view;
	   }
	   PayResponse response = payService.create_WxPay_Jsapi(code,orderId, amount, BestPayTypeEnum.WXPAY_MP);
	   
	   System.out.println(response.getBody());
	   
	   map.put("success", true);
	   
	   
//	   需要返回一些数据，由前端js调用支付
	   map.put("appId", response.getAppId());
	   map.put("timeStamp", response.getTimeStamp());
	   map.put("nonceStr", response.getNonceStr());
	   map.put("packages", response.getPackAge());
	   map.put("signType",response.getSignType());
	   map.put("paySign",response.getPaySign());
	   
	   
	   ModelAndView view = new ModelAndView("wxjsapi/pay.jsp");
	   view.addObject("payInfo", map);
	   
	   
	   return view;
   }
   /**
    * 获取code，这步是为了确定是用户的行为
   * 
   *@param code
   *@param orderId
   *@param amount
   *@return
    */
   @RequestMapping("/wx/code")
   @ResponseBody
   public String code(String code,HttpServletRequest request) {
	   request.getSession().setAttribute("code", code);
	   return "success";
   }
   
   

   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   
   @GetMapping("/create")
   @ResponseBody
   public String create(HttpServletResponse responsez ,String orderId,BigDecimal amount,BestPayTypeEnum bestPayTypeEnum) throws IOException {
	   
	   if(StringUtils.isEmpty(orderId)||StringUtils.isEmpty(bestPayTypeEnum)||amount==null) {
		   
		   return "参数orderId,amount,bestPayTypeEnum为空";
	   }
	   
	   
	   PayResponse response = payService.create(orderId, amount, bestPayTypeEnum);
	   
	   HashMap<String, Object> map = new  HashMap<>();
	   
	   //支付方式不同，渲染就不同，微信native使用code_url生成二维码、支付宝PC使用body字段
	   if(bestPayTypeEnum==BestPayTypeEnum.WXPAY_NATIVE) {		   
//		   map.put("code_url", response.getCodeUrl());	   
		   
		   return response.getCodeUrl();
//		   return new ModelAndView("createForWxNative",map);
	   }else if (bestPayTypeEnum==BestPayTypeEnum.ALIPAY_PC) {
		   
//		   map.put("body", response.getBody());
		   return response.getBody();
//		   return new ModelAndView("createForAlipayPc",map);
	   } else {
//		   throw new RuntimeException("暂不支持的支付类型");
		   return "暂时只支持类型ALIPAY_PC";
	   }
	   
	  
   }
   
   @PostMapping("/notify")
   @ResponseBody
   public String asyncNotify(@RequestBody String  notifyData) {
	   System.out.println("收到支付的通知啦====================");
	   String string = payService.asynNotify(notifyData);
	   return string;
   }
   
   
   @GetMapping("/query")
   @ResponseBody
   public Payinfo queryOrder(String  orderId) {
	   Payinfo payinfo = payService.queryOrder(orderId);
	   return payinfo;
   }
   
   public static void main(String[] args) {
	   System.out.println(MyEnum.ALIPAY_PC.getCode());
   }
}
