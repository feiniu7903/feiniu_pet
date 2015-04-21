/**
 * 
 */
package com.lvmama.sso.web.redirect;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.Cookie;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.lvmama.comm.jms.SSOMessage;
import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserCooperationUserService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.utils.CpsUtil;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.comm.vo.Constant.SSO_EVENT;
import com.lvmama.comm.vo.Constant.SSO_SUB_EVENT;
import com.lvmama.sso.web.AbstractLoginAction;

import edu.yale.its.tp.cas.util.DomainConstant;

/**
 * QQ彩贝的跳转页面。
 * 根据对方的合作文档，记录一些参数供用户下单后回调。如果用户要求联合登录，则执行静默注册
 * @author liuyi
 *
 */
@Results({
	@Result(name = "success", location = "${Url}", type = "redirect")
})
public class QQCbRedirectAction extends AbstractLoginAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2986486294475057792L;

	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(QQCbRedirectAction.class);
	
	/**
	 * 指定的QQ彩贝网站标识
	 */
	private static final String SPECIFIED_CHANNEL_ID = "qqcb";
	
	/**
	 * Cookie生命期
	 */
	private static final int LIFETIME = -1;
	
	/**
	 * 用户服务
	 */
	private UserUserProxy userUserProxy;
	/**
	 * 第三方合作服务
	 */
	private UserCooperationUserService userCooperationUserService;

	String Acct;
	
	String OpenId;
	
	String LoginFrom;
	
	String ViewInfo;
	
	String Url;
	
	String Ts;
	
	String Vkey;
	
	String Attach;
	
	String ClubInfo;
	

	@Action(value = "/tuiguang/QQCbRedirect")
	@Override
	public String execute() throws Exception {
		UserUser user = null;
		//默认站点为主站
		if (StringUtils.isEmpty(Url)) {
			Url = "http://www.lvmama.com";
		}
		
		if(StringUtils.isEmpty(Acct) || StringUtils.isEmpty(Attach) || StringUtils.isEmpty(ClubInfo) || StringUtils.isEmpty(LoginFrom) ||
				StringUtils.isEmpty(OpenId) || StringUtils.isEmpty(Ts) || StringUtils.isEmpty(Url) || StringUtils.isEmpty(ViewInfo)){
			LOG.info("cai bei paramter is error");
			//参数保护
			return SUCCESS;
		}

		if(!checkParameterValid()){
			LOG.info("qq cb paramter is error");
			//参数不符合规范
			return SUCCESS;
		}
		String userName = URLDecoder.decode(this.splitViewInfo("NickName"),"utf-8");
		if (null != userName && userName.length() > 40) {
			userName =userName.substring(0, 40);
		}
		if (!userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.USER_NAME, userName)) {
			userName = "From qq caibei ("+OpenId+")";
		}	
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cooperationUserAccount", OpenId);
		parameters.put("cooperation", SPECIFIED_CHANNEL_ID);
		List<UserCooperationUser> cooperationUseres = userCooperationUserService
				.getCooperationUsers(parameters);
		if (null == cooperationUseres || cooperationUseres.isEmpty()) {
			LOG.info(SPECIFIED_CHANNEL_ID + "company's identification " + OpenId
					+ "hav't binding user");
			user = UserUserUtil.genDefaultUser();
			user.setChannel(SPECIFIED_CHANNEL_ID);
			if (!StringUtils.isEmpty(userName)
					&& userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.USER_NAME, userName)) {
				user.setUserName(userName);
			}
			if (!StringUtils.isEmpty(email)
					&& userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.EMAIL, email)) {
				user.setEmail(email);
			}

			user = userUserProxy.registerUserCooperationUser(user,
					new UserCooperationUser(SPECIFIED_CHANNEL_ID, null, OpenId));

			ssoMessageProducer.sendMsg(
					new SSOMessage(SSO_EVENT.REGISTER, SSO_SUB_EVENT.NORMAL, user.getId()));
		} else {
			LOG.info(SPECIFIED_CHANNEL_ID + "company's identification " + OpenId
					+ "has binded user:" + cooperationUseres.get(0).getUserId());
			user = userUserProxy.getUserUserByPk(cooperationUseres.get(0).getUserId());
		}

		if (null == user) {
			LOG.info("cann't find identification, redirectly");
			return SUCCESS;
		}
		generalLogin(user);
		
		if (null == this.getCookieValue("orderFromChannel")
				|| SPECIFIED_CHANNEL_ID.equals(this.getCookieValue("orderFromChannel"))) {
			LOG.info("save qq cbs uid: "+OpenId+", tracking_code: "+Attach);
			writeCookie("orderFromChannel", SPECIFIED_CHANNEL_ID);
			writeCookie("cpsuid", OpenId);
			writeCookie("tracking_code", Attach);
			String HeadShow = splitViewInfo("HeadShow");
			String NickName = URLDecoder.decode(splitViewInfo("NickName"),"utf-8");
			if (StringUtils.isNotBlank(HeadShow)) {
				writeCookie("HeadShow", HeadShow);
				
				writeCookie("unUserName",URLEncoder.encode(NickName,"utf-8"));
			}	
		}
		return SUCCESS;
	}
	
	String splitViewInfo(String key) {
		if (StringUtils.isNotBlank(ViewInfo)) {
			String[] infos = ViewInfo.split("&");
			for (String info : infos) {
				if (info.startsWith(key)) {
					return info.substring(key.length() + 1);
				}
			}
		}
		return null;
	}
	
	
	
	/**
	 * 写Cookie
	 * @param name cookie名
	 * @param value cookie值
	 */
	void writeCookie(final String name, final String value) {
		if (StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(value)) {
			Cookie tck = new Cookie(name, value);
			tck.setSecure(false);
			tck.setMaxAge(LIFETIME);
			tck.setDomain(DomainConstant.DOMAIN);
			tck.setPath("/");
			getResponse().addCookie(tck);
		}
	}
	
	
	private boolean checkParameterValid(){
		if(!"caibei".equals(LoginFrom)){
			LOG.info("not from cai bei");
			return false;
		}else if(StringUtils.isEmpty(Url) || Url.indexOf("www.lvmama.com") == -1){
			LOG.info("cai bei forward url is not www.lvmama.com");
			return false;
		}
		String generateVkey = Acct+Attach+ClubInfo+LoginFrom+OpenId+Ts+Url+ViewInfo;
		LOG.info("generate key "+generateVkey);
		return CpsUtil.getInstance().checkQQCbVkey(generateVkey, Vkey);
	}


	public void setAcct(String acct) {
		Acct = acct;
	}


	public void setOpenId(String openId) {
		OpenId = openId;
	}


	public void setLoginFrom(String loginFrom) {
		LoginFrom = loginFrom;
	}


	public void setViewInfo(String viewInfo) {
		ViewInfo = viewInfo;
	}


	public String getUrl() {
		return Url;
	}



	public void setUrl(String url) {
		Url = url;
	}


	public void setTs(String ts) {
		Ts = ts;
	}


	public void setVkey(String vkey) {
		Vkey = vkey;
	}


	public void setAttach(String attach) {
		Attach = attach;
	}
	
	public void setClubInfo(String clubInfo) {
		ClubInfo = clubInfo;
	}
	
	public void setUserUserProxy(final UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setUserCooperationUserService(
			final UserCooperationUserService userCooperationUserService) {
		this.userCooperationUserService = userCooperationUserService;
	}
}
