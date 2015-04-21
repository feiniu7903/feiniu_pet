package com.lvmama.sso.auth.provider;

import java.security.NoSuchAlgorithmException;
import java.util.Date;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;

import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.jms.SSOMessageProducer;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserActionCollectionService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SSO_EVENT;
import com.lvmama.comm.vo.Constant.SSO_SUB_EVENT;


/**
 * 验证用户用户名和密码的实现类
 * @author Brian
 *
 */
public class LvmamaPasswordHandler extends WatchfulPasswordHandler {
	/**
	 * Log类
	 */
	private static final Logger LOG = Logger.getLogger(LvmamaPasswordHandler.class);
	

	/**
	 * 验证合法用户
	 * @param request http请求对象
	 * @param username 用户名
	 * @param password 密码
	 * @return 合法用户
	 */
	public UserUser authenticate(final ServletRequest request, final ServletResponse response, final String username, final String password,final String channel) {
		String md5Password = "";

		if (LOG.isDebugEnabled()) {
			LOG.debug("account:" + username + " password:" + password + " want to login");
		}
		try {
			md5Password = null == password ? "" : UserUserUtil.encodePassword(password);
		} catch (NoSuchAlgorithmException e) {
			LOG.error("password:" + password + " cann't encode!" + e.getMessage());
		}
		// LOG.info("account:" + username + " password:" + password + " want to login" + "...md5Password="+md5Password);
		UserUser user = this.getUserServiceProxy().queryForLogin(username,md5Password);

		if (null == user) {
			LOG.warn("Account:" + username + " cann't find user.");
			return null;
		}

		if (!md5Password.equals(user.getUserPassword())) {
			LOG.warn("account:" + username + " password:" + password + " login fail!");
			return null;
		}
		if (null != user) {
			afterLogin((HttpServletRequest)request, (HttpServletResponse)response, user,channel);
		}
		return user;
	}

	/**
	 * 用户登录成功后的处理
	 * @param request http请求对象
	 * @param user 用户对象
	 */
	private void afterLogin(final HttpServletRequest request, HttpServletResponse response, final UserUser user,final String channel) {
		
		
		ServletUtil.putSession(request, response, Constant.SESSION_FRONT_USER, user);
		user.setLastLoginDate(new Date());
		this.getUserServiceProxy().update(user);//设置上次登录时间
		UserActionCollectionService userActionCollectionService = getUserActionCollectionService();
		if (null != userActionCollectionService) {
			//标识驴妈妈访问者的唯一标识
			String uid = "";
			if (null != request) {
				Cookie[] cookies = request.getCookies();
				if (cookies!=null && cookies.length!=0) {
					for (Cookie cookie : cookies) {
						if (null != cookie && "uid".equalsIgnoreCase(cookie.getName()) && StringUtils.isNotEmpty(cookie.getValue())) {
							uid = cookie.getValue();
						}
					}
				}
				
			}
			userActionCollectionService.save(user.getId(), InternetProtocol.getRemoteAddr(request),InternetProtocol.getRemotePort(request), "LOGIN", decodeUID(uid),user.getLoginType(),channel,request.getHeader("Referer"));
		}
		SSOMessageProducer producer = (SSOMessageProducer)SpringBeanProxy.getBean("ssoMessageProducer");
		producer.sendMsg(new SSOMessage(SSO_EVENT.LOGIN, SSO_SUB_EVENT.NORMAL, user.getId()));
	}
	
	/**
	 * cookie里面存的是base64加密的值，nginx日志中的是hex值，需要进行解码替换后才能一一对应
	 * @param uid cookie里面存放的uid的值
	 * @return 对应nginx中的uid
	 */
	public static String decodeUID(final String uid) {
		if (StringUtils.isNotBlank(uid)) {
			byte[] bytes = Base64.decodeBase64(uid);
			//高位低位互换
			for (int i = 0 ; i < bytes.length ;  i = i + 4) {
				byte temp = bytes[i];
				bytes[i] = bytes[i + 3];
				bytes[i + 3] = temp;
				temp = bytes[i + 1];
				bytes[i + 1] = bytes[i + 2];
				bytes[i + 2] = temp;
			}
			return Hex.encodeHexString(bytes).toUpperCase();
		}
		return uid;
	}
	
   /**
    * 获取用户代理服务
    * @return 用户代理服务
    */
	private UserUserProxy getUserServiceProxy() {
		return (UserUserProxy) SpringBeanProxy.getBean("userUserProxy");
	}

	/**
	 * 获取用户信息收集服务
	 * @return
	 */
	private UserActionCollectionService getUserActionCollectionService() {
		return (UserActionCollectionService) SpringBeanProxy.getBean("userActionCollectionService");
	}
	
	public static void main(String[] args) {
		System.out.println(decodeUID("wKgA81GLhVIjB3y2AwSCAg=="));
	}
}
