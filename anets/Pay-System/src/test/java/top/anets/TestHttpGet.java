/**
 * 
 */
package top.anets;

import org.junit.Test;

import top.anets.utils.HttpUtils;

/**
 * @author Administrator
 *
 */
public class TestHttpGet {
    
	public static void main(String[] args) {
		String string = HttpUtils.get("https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx4648a3be714ccd3f&secret=6307a227fae2d9a3e3b3179c05bc4686&code=001ZH1cY1ebeIX09cb9Y1u8TbY1ZH1cm&grant_type=authorization_code");
	    System.out.println(string);
	}
}
