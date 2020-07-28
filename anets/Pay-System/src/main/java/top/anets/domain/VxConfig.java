/**
 * 
 */
package top.anets.domain;

import javax.net.ssl.SSLContext;

/**
 * 微信支付的一些相关配置
 * @author Administrator
 * 
 */
public class VxConfig {
	 /**
     * 公众号appId
     */
    private String appId="wx4648a3be714ccd3f";

    /**
     * 公众号appSecret
     */
    private String appSecret="6307a227fae2d9a3e3b3179c05bc4686";

    /**
     * 小程序appId
     */
    private String miniAppId;

    /**
     * app应用appid
     */
    private String appAppId;

    /**
     * 商户号
     */
    private String mchId="1548853501";

    /**
     * 商户密钥
     */
    private String mchKey="souainettop2019hellnets2020anets";

    /**
     * 商户证书路径
     */
    private String keyPath;

    /**
     * 证书内容
     */
    private SSLContext sslContext;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getAppSecret() {
		return appSecret;
	}

	public void setAppSecret(String appSecret) {
		this.appSecret = appSecret;
	}

	public String getMiniAppId() {
		return miniAppId;
	}

	public void setMiniAppId(String miniAppId) {
		this.miniAppId = miniAppId;
	}

	public String getAppAppId() {
		return appAppId;
	}

	public void setAppAppId(String appAppId) {
		this.appAppId = appAppId;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getMchKey() {
		return mchKey;
	}

	public void setMchKey(String mchKey) {
		this.mchKey = mchKey;
	}

	public String getKeyPath() {
		return keyPath;
	}

	public void setKeyPath(String keyPath) {
		this.keyPath = keyPath;
	}

	public SSLContext getSslContext() {
		return sslContext;
	}

	public void setSslContext(SSLContext sslContext) {
		this.sslContext = sslContext;
	}

   
}
