/**
 * 
 */
package top.anets.adapter;

import com.alibaba.fastjson.JSONObject;

/**
 * @author Administrator
 *
 */
//统一发送消息 
public interface MessageAdapter {
   void sendMsg(JSONObject body);
}
