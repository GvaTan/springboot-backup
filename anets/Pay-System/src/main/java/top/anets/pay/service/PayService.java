/**
 * 
 */
package top.anets.pay.service;

import java.math.BigDecimal;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayResponse;

import top.anets.domain.Payinfo;

/**
 * @author Administrator
 *
 */
public interface PayService {
    /**
     * 创建/发起支付
     * @return 
     */
	PayResponse create(String orderId, BigDecimal amount, BestPayTypeEnum bestPayTypeEnum);

	/**
	* 
	*异步通知
	*/
	String asynNotify(String notifyData);

	/**
	* 
	*@param orderId
	*@return 
	*/
	Payinfo queryOrder(String orderId);

	/**
	* 微信jspai支付
	*@param orderId
	*@param amount
	*@param wxpayMini
	*@return 
	*/
	PayResponse create_WxPay_Jsapi(String code,String orderId, BigDecimal amount, BestPayTypeEnum type);

}