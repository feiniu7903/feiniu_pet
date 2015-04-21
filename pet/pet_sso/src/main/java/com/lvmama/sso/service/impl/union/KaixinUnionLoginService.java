package com.lvmama.sso.service.impl.union;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kx.www.include.kxplatformapi.KxPlatform_Api;
import com.kx.www.include.kxplatformapi.KxPlatform_WebRequest;
import com.kx.www.util.KxUtil;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.sso.service.UnionLoginService;
import com.lvmama.sso.utils.UnionLoginUtil;

/**
 * 开心网联合登录跳转实现类
 *
 * @author panzhiyi
 *
 */
public class KaixinUnionLoginService implements UnionLoginService {
	/**
	 * Log类
	 */
	private static final Log LOG = LogFactory
			.getLog(KaixinUnionLoginService.class);
	/**
	 * 请求URL
	 */
	private static final String ACTION_URL = "http://www.kaixin001.com/rest/rest.php";
	/**
	 * 第三合作方唯一标识符
	 */
	private String theThirdCoopUUID;
	/**
	 * 用户名
	 */
	private String userName;

	@Override
	public String getThirdCooperationUUID(final HttpServletRequest request,
			final HttpServletResponse response) {
		getMessage(request);
		if (theThirdCoopUUID != null) {
			return theThirdCoopUUID;
		}
		return null;
	}

	@Override
	public UserUser generateUsers(final HttpServletRequest request) {
		getMessage(request);
		if (theThirdCoopUUID != null && userName != null) {
			UserUser users = UserUserUtil.genDefaultUser();
			if (userName != null) {
				users.setUserName("From kaixing" + userName + "(" + theThirdCoopUUID + ")");
			}
			return users;
		}
		return null;
	}

	/**
	 * 读取cookie取得sessionKey
	 *
	 * @param request request
	 * @return sessionKey 在用户请求时获得，每次都不同
	 */
	private String getSessionKey(final HttpServletRequest request) {
		String sessionKey = null;
		Cookie[] cookies = request.getCookies();
		for (Cookie cookie : cookies) {
			LOG.info("cookie: " + cookie);
			if (cookie.getName().equalsIgnoreCase("kaixin_session_key")) {
				sessionKey = cookie.getValue();
			}
		}
		LOG.info("kx_session_key: " + sessionKey);
		return sessionKey;
	}

	/**
	 * 获得开心网用户名和用户唯一标识
	 *
	 * @param request request
	 */
	private synchronized void getMessage(final HttpServletRequest request) {
		if (theThirdCoopUUID != null) {
			return;
		}

		String sessionKey = getSessionKey(request);

		if (sessionKey != null) {
			Map<String, String> kxplatformConfig = getKxMap(sessionKey);
			KxPlatform_Api kxplatformApi = new KxPlatform_Api(
					kxplatformConfig);

			String kaixinId = getKaixinUserId(kxplatformApi);
			String kaixinName = null;
			if (kaixinId != null && !kaixinId.equals("")) {
				kaixinName = getKaixinUserName(kxplatformApi, kaixinId);
				this.setTheThirdCoopUUID(kaixinId);
				this.setUserName(kaixinName);
			} else {
				LOG.error("error identification!");
			}
			LOG.info("kaixinId=" + this.getTheThirdCoopUUID());
			LOG.info("kaixinName=" + this.getUserName());

		} else {
			LOG.error("cookie kx_session_key is null");
		}
	}

	/**
	 * 获取用户ID
	 * @param kxplatformApi kxplatformApi
	 * @return 用户ID
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String getKaixinUserId(final KxPlatform_Api kxplatformApi) {
		Map param = getRequestMap("users.getLoggedInUser");
		String query = kxplatformApi.buildQuery(param);

		String result = KxPlatform_WebRequest.postRequest(ACTION_URL, query);
		LOG.info("kaixin userid: " + result);
		if (result != null && !result.equals("")) {
			JSONObject jsonObj = JSONObject.fromObject(result);
			String userid = jsonObj.getString("result");
			if (userid != null) {
				return userid;
			}
		}
		return result;
	}

	/**
	 * 获取用户名
	 * @param kxplatformApi kxplatformApi
	 * @param uid 用户ID
	 * @return 用户名
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private String getKaixinUserName(final KxPlatform_Api kxplatformApi, final String uid) {
		Map param = getRequestMap("users.getInfo");
		param.put("uids", uid); // 用户唯一标识，用于得到用户信息

		String query = kxplatformApi.buildQuery(param);
		// log.info("username query: "+query);
		String result = KxPlatform_WebRequest.postRequest(ACTION_URL, query);
		Object resultDecode = KxUtil.jsonDecode(result);
		if (resultDecode instanceof Map) {
			Map map = (Map) resultDecode;
			if (map.containsKey("error")) { // 开心网查询用户信息有错误
				Map errorObj = (Map) map.get("error");
				LOG.info("error code:" + errorObj.get("code").toString());
				LOG.info("error message:" + errorObj.get("msg").toString());
			} else {
				LOG.info("返回值是Map类型");
				return (String) map.get("name");
			}
		} else if (resultDecode instanceof List) {
			List ls = (List) resultDecode;
			if (ls != null && !ls.isEmpty()) {
				Map map = (Map) ls.get(0);
				return (String) map.get("name");
			}
		}
		return result;
	}

	/**
	 * 根据传入参数生成一个请求map并返回
	 *
	 * @param method
	 *            请求需获取什么信息，具体查看开心网API
	 * @return 参数MAP
	 */
	private Map<String, Object> getRequestMap(final String method) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("method", method);
		param.put("format", "json");
		param.put("mode", "0");
		return param;
	}

	/**
	 * 开心Map结构
	 * @param sessionKey sessionKey
	 * @return Map参数结构
	 */
	private Map<String, String> getKxMap(final String sessionKey) {
		Map<String, String> kxplatformConfig = new HashMap<String, String>();
		String keyName = "kaixin.consumer.key";
		String secretName = "kaixin.consumer.secret";
		String version = "kaixin.version";
		UnionLoginUtil util = UnionLoginUtil.getInstance();

		kxplatformConfig.put("session_key", sessionKey);
		kxplatformConfig.put("api_key", util.getValue(keyName));
		kxplatformConfig.put("secret", util.getValue(secretName));
		kxplatformConfig.put("v", util.getValue(version));
		return kxplatformConfig;
	}

	public String getTheThirdCoopUUID() {
		return theThirdCoopUUID;
	}

	public void setTheThirdCoopUUID(final String theThirdCoopUUID) {
		this.theThirdCoopUUID = theThirdCoopUUID;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(final String userName) {
		this.userName = userName;
	}
}
