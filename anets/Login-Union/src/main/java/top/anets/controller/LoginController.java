/**
 * 
 */
package top.anets.controller;

/**
 * @author Administrator
 *
 */
public class LoginController {
	@RequestMapping("/qqLoginCallback")
	public String qqLoginCallback(HttpServletRequest request, HttpServletResponse response, HttpSession httpSession)
			throws QQConnectException {
		AccessToken accessTokenObj = new Oauth().getAccessTokenByRequest(request);
		if (accessTokenObj == null) {	
			request.setAttribute("error", "qq授权失败!");
			return ERROR;
		}
		String accessToken = accessTokenObj.getAccessToken();
		if (StringUtils.isEmpty(accessToken)) {
			request.setAttribute("error", "qq授权失败!");
			return ERROR;
		}
		// 获取openid
		OpenID openIdObj = new OpenID(accessToken);
		String userOpenID = openIdObj.getUserOpenID();
		ResponseBase openIdUser = memberServiceFegin.findByOpenIdUser(userOpenID);
		// 用戶沒有关联QQ账号
		if (openIdUser.getRtnCode().equals(Constants.HTTP_RES_CODE_201)) {
			// 跳转到管理账号
			httpSession.setAttribute("qqOpenid", userOpenID);
			return RELATION;
		}
		// 如果用户关联账号 直接登录
		LinkedHashMap dataMap = (LinkedHashMap) openIdUser.getData();
		return cookieLogin(request, response, dataMap);
	}
}
