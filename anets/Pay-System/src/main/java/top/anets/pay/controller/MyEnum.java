/**
 * 
 */
package top.anets.pay.controller;

import static com.lly835.bestpay.enums.BestPayPlatformEnum.ALIPAY;
import static com.lly835.bestpay.enums.BestPayPlatformEnum.WX;

import com.lly835.bestpay.enums.BestPayPlatformEnum;

/**
 * @author Administrator
 *
 */
public enum MyEnum {
	
	ALIPAY_PC("alipay_pc","支付宝pc"),
	WXPAY_NATIVE("NATIVE", "微信Native支付"),;
	
	private String code;

    private String desc;
	
	/**
	 * @param string
	 * @param string2
	 */
	MyEnum(String code, String desc) {
		this.code=code;
		this.desc=desc;
	}
	
	public String getCode() {
		return this.code;
	}
	
	public String getDesc() {
		return this.desc;
	}

	
	
}
