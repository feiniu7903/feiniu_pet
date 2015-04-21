package com.lvmama.clutter.web.user;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.lvmama.clutter.service.IClientUserService;
import com.alipay.api.internal.util.StringUtils;
import com.lvmama.clutter.model.MobileUser;
import com.lvmama.clutter.model.MobileWeixinNewItem;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.utils.JSONUtil;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 用户处理Action
 * 
 * @author YangGan
 * 
 */
@Results({
		@Result(name = "weixinResetPassword", location = "/WEB-INF/pages/user/weixinResetPassword.html", type = "freemarker"),
		@Result(name = "weixinForgetPassword", location = "/WEB-INF/pages/user/weixinForgetPassword.html", type = "freemarker"),
		@Result(name = "weixinBindSuccess", location = "/WEB-INF/pages/user/weixinBindSuccess.html", type = "freemarker"),
		@Result(name = "weixinBindFail", location = "/WEB-INF/pages/user/weixinBindFail.html", type = "freemarker"),
		@Result(name = "weixinRegister", location = "/WEB-INF/pages/user/weixinRegister.html", type = "freemarker"),
		@Result(name = "weixinLogin", location = "/WEB-INF/pages/user/weixinLogin.html", type = "freemarker"),
		@Result(name = "login", location = "/WEB-INF/pages/user/login.html", type = "freemarker"),
		@Result(name = "login_activity", location = "/WEB-INF/pages/user/login.html", type = "freemarker"),
		@Result(name = "reg", location = "/WEB-INF/pages/user/register.html", type = "freemarker"),
		@Result(name = "mylv", location = "/WEB-INF/pages/user/mylv.html", type = "freemarker"),
		@Result(name = "find_passworld", location = "/WEB-INF/pages/user/find_passworld.html", type = "freemarker"),
		@Result(name = "wap_to_lvmama", location = "http://www.lvmama.com", type = "redirect") })
@Namespace("/mobile")
public class LoginAction extends BaseAction {

	private static final long serialVersionUID = 1L;

	/**
	 * 用户相关服务.
	 */
	protected IClientUserService mobileUserService;

	protected UserUserProxy userUserProxy;

	/* 登录之前的URL */
	private String service;
	private boolean absolutePath;//判断service是绝对路径还是相对路径
	private String username;
	private String password;
	private String mobileOrEMail;// 手机号
	private String authenticationCode; // 校验码
	private String openId;// 微信ID
	private static String HTTPCLIENT_ERROR = "{\"errorText\":\"服务器错误!\",\"success\":false}";

	// 登录，注册统计代码
	private String activityChannel;

	/**
	 * 进入用户登录页面
	 * 
	 * @return 登录页面Result
	 */
	@Action("login")
	public String login() {
		UserUser userUser = this.getUser();
		//
		if (null != userUser && StringUtils.isEmpty(activityChannel)) {
			return "mylv";
		}
		if(!StringUtils.isEmpty(service) && service.indexOf("http://")>=0){//区分绝对和相对路径的service
			absolutePath=true;
		}
		// 落地活动 12580统计代码
		// initActivityLosc();

		username = ServletUtil.getCookieValue(getRequest(), "mb_u");
		if (!StringUtils.isEmpty(username)) {
			try {
				username = URLDecoder.decode(username, "utf-8");
			} catch (UnsupportedEncodingException e) {
				log.info("super_clutter login error");
				e.printStackTrace();
				username = "";
			}
		}
		password = ServletUtil.getCookieValue(getRequest(), "mb_p");
		String udid = ServletUtil.getCookieValue(getRequest(), "mb_udid");
		if (null == udid || udid.isEmpty()) {
			udid = UUID.randomUUID().toString();
			ServletUtil.addCookie(getResponse(), "mb_udid", udid, 30, false);// 默认保存30天
		}
		getRequest().setAttribute(Constant.LV_SESSION_ID,
				ServletUtil.getLvSessionId(getRequest(), getResponse()));

		// 统计多少人到达该页面
		if (!StringUtils.isEmpty(activityChannel)) {
			return "login_activity";
		}
		return "login";
	}

	/**
	 * 进入用户登录页面
	 * 
	 * @return 登录页面Result
	 */
	@Action("weixinLogin")
	public String weixinLogin() {
		UserUser userUser = this.getUser();
		if (null != userUser) {
			return "mylv";
		}
		username = ServletUtil.getCookieValue(getRequest(), "mb_u");
		if (!StringUtils.isEmpty(username)) {
			try {
				username = URLDecoder.decode(username, "utf-8");
			} catch (UnsupportedEncodingException e) {
				log.info("super_clutter login error");
				e.printStackTrace();
				username = "";
			}
		}
		password = ServletUtil.getCookieValue(getRequest(), "mb_p");
		String udid = ServletUtil.getCookieValue(getRequest(), "mb_udid");
		if (null == udid || udid.isEmpty()) {
			udid = UUID.randomUUID().toString();
			ServletUtil.addCookie(getResponse(), "mb_udid", udid, 30, false);// 默认保存30天
		}
		getRequest().setAttribute(Constant.LV_SESSION_ID,
				ServletUtil.getLvSessionId(getRequest(), getResponse()));
		return "weixinLogin";
	}

	@Action("t_login")
	public void to_login() {
		String lvsessionId = ServletUtil.getLvSessionId(getRequest(),
				getResponse());
		String udid = ServletUtil.getCookieValue(getRequest(), "mb_udid");
		if (null == udid || udid.isEmpty()) {
			udid = UUID.randomUUID().toString();
			ServletUtil.addCookie(getResponse(), "mb_udid", udid, 30, false);// 默认保存30天
		}

		// 判断是否需要验证码
		/*
		 * boolean b =
		 * LoginUtil.needVerifyCode4wap(RequestUtil.getIpAddr(getRequest()),
		 * lvsessionId); if(b) { System.out.println("需要验证码......."); }
		 */
		String loginUrl = ClutterConstant.getLoginURL() + "?mobileOrEMail="
				+ username + "&password=" + password +"&firstChannel="+Constant.CHANNEL.TOUCH.name()+"&secondChannel=LVMM"+ "&"
				+ Constant.LV_SESSION_ID + "=" + lvsessionId;
		this.getRequest().setAttribute(Constant.LV_SESSION_ID, lvsessionId);
		// String jsons = HttpsUtil.requestGet(loginUrl);
		String jsons = HttpsUtil.proxyRequestGet(loginUrl,
				InternetProtocol.getRemoteAddr(getRequest()));
		try {
			JSONObject jo = JSONUtil.getObject(jsons);
			String res = jo.getString("success");
			if ("true".equals(res)) {// 登录成功，保存密码和用户名，uuid至cookie
				HttpServletResponse response = getResponse();
				// TODO 用户名密码需要加密
				ServletUtil.addCookie(response, "mb_u",
						URLEncoder.encode(username, "utf-8"), 30, false);// 默认保存30天
				ServletUtil.addCookie(response, "mb_p", "", 30, false);// 默认保存30天
				// USERID
				jo.put("userId", this.getUser().getUserId());
			} else { // 登录失败
				// LoginUtil.addLogInCount(RequestUtil.getIpAddr(getRequest()),
				// lvsessionId);
			}
			// 如果是活动，需要处理活动相关逻辑
			jo.put("activityChannel", activityChannel);
			jo.put("lvsessionId", lvsessionId);
			this.weixinLogin(getResponse());
			this.sendAjaxResultByJson(jo.toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.sendAjaxResultByJson(HTTPCLIENT_ERROR);
		}
	}

	@Action("t_weixin_login")
	public void t_weixin_login() {
		HttpServletRequest request = getRequest();
		HttpServletResponse response = getResponse();
		String lvsessionId = ServletUtil.getLvSessionId(request, response);
		String udid = ServletUtil.getCookieValue(getRequest(), "mb_udid");
		if (null == udid || udid.isEmpty()) {
			udid = UUID.randomUUID().toString();
			ServletUtil.addCookie(response, "mb_udid", udid, 30, false);// 默认保存30天
		}

		String loginUrl = ClutterConstant.getLoginURL() + "?mobileOrEMail="
				+ username + "&password=" + password + "&firstChannel="+Constant.CHANNEL.TOUCH.name()+"&secondChannel=LVMM"+"&"
				+ Constant.LV_SESSION_ID + "=" + lvsessionId;
		request.setAttribute(Constant.LV_SESSION_ID, lvsessionId);
		// String jsons = HttpsUtil.requestGet(loginUrl);
		String jsons = HttpsUtil.proxyRequestGet(loginUrl,
				InternetProtocol.getRemoteAddr(getRequest()));

		LOG.info(jsons);

		try {
			JSONObject jo = JSONUtil.getObject(jsons);
			String res = jo.getString("success");
			String uid = null;
			if ("true".equals(res)) {// 登录成功，保存密码和用户名，uuid至cookie
				// TODO 用户名密码需要加密
				ServletUtil.addCookie(response, "mb_u",
						URLEncoder.encode(username, "utf-8"), 30, false);// 默认保存30天
				ServletUtil.addCookie(response, "mb_p", "", 30, false);// 默认保存30天
				uid = getUser().getUserNo();
			} else { // 登录失败
				// LoginUtil.addLogInCount(RequestUtil.getIpAddr(getRequest()),
				// lvsessionId);
			}

			String bindUrl = Constant.getInstance().getValue(
					"weixin.bind.lvmama.user.url");
			String openid = ServletUtil.getCookieValue(request, "openid");
			jo.put("bindUrl", bindUrl);
			jo.put("openid", openid);
			jo.put("uid", uid);
			this.sendAjaxResultByJson(jo.toString());
			// response.sendRedirect(MessageFormat.format("{0}?openid={1}&uid={2}",
			// bindUrl, openid, uid));
			this.weixinLogin(getResponse());
		} catch (Exception e) {
			e.printStackTrace();
			this.sendAjaxResultByJson(HTTPCLIENT_ERROR);
		}
	}

	
	/**
	 * 处理微信登录相关
	 * @param response
	 */
	private void weixinLogin(HttpServletResponse response) {
		try {
			// 删除cookies
			ServletUtil.addCookie(response, "wx_4_logout", "false",1,false);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 我的首页.
	 * 
	 * @return str
	 */
	@Action("mylv")
	public String mylv() {
		this.setImagePrefix();
		getRequest().setAttribute("nssoUrl", ClutterConstant.getNssoUrl());
		getRequest().setAttribute("needCmtAmout", 0);
		try {
			// 如果登陆
			if (null != getUser()) {
				Map<String, Object> parmas = new HashMap<String, Object>();
				parmas.put("userNo", getUser().getUserNo());
				Map<String, Object> resultMap = mobileUserService
						.queryCmtWaitForOrder(parmas);
				if (null != resultMap && null != resultMap.get("count")) {
					getRequest().setAttribute("needCmtAmout",
							resultMap.get("count"));
				}
				Map<String, String> pointParmas = new HashMap<String, String>();
				pointParmas.put("userNo", getUser().getUserNo());
				MobileUser user = mobileUserService.getUser(pointParmas);
				Long userPoint = 0L;
				if (user != null && user.getPoint() > 0L) {
					userPoint = user.getPoint();
				}
				getRequest().setAttribute("userPoint", userPoint);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return "mylv";
	}

	/**
	 * 进入用户注册页面
	 * 
	 * @return 注册页面Result
	 */
	@Action("reg")
	public String reg() {
		String mb = ServletUtil.getCookieValue(getRequest(), "mb_mb_em");// 默认保存30天
		if (null == mb || "null".equals(mb)) {
			mb = "";
		}
		getRequest().setAttribute("mobile", null == mb ? "" : mb);
		getRequest().setAttribute("nssoUrl", ClutterConstant.getNssoUrl());

		// 落地活动 12580统计代码
		// initActivityLosc();

		return "reg";
	}

	/**
	 * 
	 * 
	 * @return 登录页面Result
	 */
	@Action("getuseInfo")
	public void getUserInfo() {
		UserUser user = this.getUser();
		JSONObject jsonObj = new JSONObject();
		if (user != null) {
			jsonObj.put("nickName", user.getNickName());
			jsonObj.put("userName", user.getUserName());
			jsonObj.put("createdDate", getDateToString(user.getCreatedDate()));
		}
		this.sendAjaxResult(jsonObj.toString());
	}
	/**
	 * 时间类型转换
	 * @param date
	 * @return
	 */
	public String getDateToString(Date date){
		if(date!=null){
			String newDateString=null;
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
			
			newDateString=dateFormat.format(date);
			return newDateString;
		}else{
			return "";
		}
	}

	/**
	 * 进入用户注册页面
	 * 
	 * @return 注册页面Result
	 */
	@Action("find_passworld")
	public String findPassworld() {
		getRequest().setAttribute("nssoUrl", ClutterConstant.getNssoUrl());
		return "find_passworld";
	}

	/**
	 * 进入微信用户注册页面
	 * 
	 * @return 注册页面Result
	 */
	@Action("weixinRegister")
	public String weixinRegister() {
		String mb = ServletUtil.getCookieValue(getRequest(), "mb_mb_em");// 默认保存30天
		if (null == mb || "null".equals(mb)) {
			mb = "";
		}
		getRequest().setAttribute("mobile", null == mb ? "" : mb);
		getRequest().setAttribute("nssoUrl", ClutterConstant.getNssoUrl());
		return "weixinRegister";
	}

	/**
	 * 进入绑定成功页面
	 * 
	 * @return
	 */
	@Action("weixinBindSuccess")
	public String weixinBindSuccess() {
		return "weixinBindSuccess";
	}

	/**
	 * 进入找回密码页面
	 * 
	 * @return
	 */
	@Action("weixinForgetPassword")
	public String weixinForgetPassword() {
		getRequest().setAttribute("nssoUrl", ClutterConstant.getNssoUrl());
		return "weixinForgetPassword";
	}

	/**
	 * 进入重置密码页面
	 * 
	 * @return
	 */
	@Action("weixinResetPassword")
	public String weixinResetPassword() {
		HttpServletRequest request = getRequest();
		request.setAttribute("mobile", request.getParameter("mobile"));
		request.setAttribute("nssoUrl", ClutterConstant.getNssoUrl());
		return "weixinResetPassword";
	}

	/**
	 * 进入重置密码页面
	 * 
	 * @return
	 */
	@Action("wxcRedirectUrl")
	public String wxcRedirectUrl() {
		log.info("before weixin invoke wxcRedirectUrl...");

		HttpServletRequest request = getRequest();
		@SuppressWarnings("unchecked")
		Map<String, Object> parameterMap = request.getParameterMap();
		for (Entry<String, Object> entry : parameterMap.entrySet()) {
			log.info("parameterKey=" + entry.getKey());
			String[] paramArray = (String[]) entry.getValue();
			for (int i = 0; i < paramArray.length; i++) {
				log.info("parameterValue=" + paramArray[i]);
			}
		}
		// String q = this.getWeixinParam(request, "q");// 查询的关键词
		// String openid = this.getWeixinParam(request, "openid");
		String subscribeStr = this.getWeixinParam(request, "subscribe");

		if (!StringUtils.isEmpty(subscribeStr)
				&& Integer.parseInt(subscribeStr) == 1) {
			return "weixinBindSuccess";
		}
		return "weixinBindFail";
	}

	private String getWeixinParam(HttpServletRequest request,
			String parameterKey) {
		Object parameterValueObj = request.getParameter(parameterKey);
		String parameterValue = null;
		if (parameterValueObj instanceof String[]) {
			String[] parameterValueArr = (String[]) parameterValueObj;
			parameterValue = parameterValueArr[0];
		} else {
			parameterValue = (String) parameterValueObj;
		}
		return parameterValue;
	}

	/**
	 * 自定义回复/欢迎词/优惠券
	 * 
	 * @return
	 */
	@Action("getJson")
	public String getJson() {
		log.info("before weixin invoke getJson...");

		HttpServletRequest request = getRequest();
		@SuppressWarnings("unchecked")
		Map<String, Object> parameterMap = request.getParameterMap();
		for (Entry<String, Object> entry : parameterMap.entrySet()) {
			log.info("parameterKey=" + entry.getKey());
			String[] paramArray = (String[]) entry.getValue();
			for (int i = 0; i < paramArray.length; i++) {
				log.info("parameterValue=" + paramArray[i]);
			}
		}
		String q = this.getWeixinParam(request, "q");// 查询的关键词
		String openid = this.getWeixinParam(request, "openid");

		String giveCouponUrl = Constant.getInstance().getValue(
				"weixin.give.coupon.url");

		/*
		 * 调用驴妈妈发送优惠券接口 1.如果该用户已经绑定驴妈妈用户，发送优惠券，返回{data:success}
		 * 2.如果用户尚未绑定驴妈妈用户，返回{data:fail}
		 */
		String couponJsonStr = HttpsUtil
				.requestGet(MessageFormat.format(
						"{0}?mbwechatId={1}&mbSubscribe={2}", giveCouponUrl,
						openid, 1));
		log.info(MessageFormat.format("giveCoupon.do return json is [{0}]",
				couponJsonStr));
		JSONObject couponJsonObj = JSONObject.fromObject(couponJsonStr);
		boolean isGivenCoupon = "success".equals(couponJsonObj
				.getString("data")) ? true : false;
		String username = null;
		if (isGivenCoupon) {
			username = couponJsonObj.getString("userName");
		}

		Map<String, Object> welcomeMap = new HashMap<String, Object>();
		welcomeMap.put("error", 0);
		welcomeMap.put("msg", "welcome to lvmama!");
		String networkPath = this.getNetworkPath(request);
		welcomeMap.put("data",
				this.getData(networkPath, isGivenCoupon, username, q, openid));

		log.info(JSONObject.fromObject(welcomeMap).toString());
		this.sendAjaxResultByJson(JSONObject.fromObject(welcomeMap).toString());
		log.info("after weixin invoke getJson...");
		return null;
	}

	private String getNetworkPath(HttpServletRequest request) {
		String url = request.getRequestURL().toString();
		String regex = "(.*/clutter/)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(url);
		String realPath = null;
		if (matcher.find()) {
			realPath = matcher.group();
		}
		return realPath;
	}

	private Map<String, Object> getData(String networkPath,
			boolean isGivenCoupon, String username, String q, String openid) {
		String picLvmama = "http://pic.lvmama.com/img/mobile/touch/img/weixin/";
		Map<String, Object> dataMap = new HashMap<String, Object>();
		if (isGivenCoupon) {
			List<MobileWeixinNewItem> news = new ArrayList<MobileWeixinNewItem>();
			MobileWeixinNewItem newItem1 = new MobileWeixinNewItem(
					"亲，恭喜您获得380元优惠券！", null, MessageFormat.format(
							"{0}user/myconpon.htm?openid={1}", networkPath,
							openid), picLvmama + "coupon.jpg");
			news.add(newItem1);

			MobileWeixinNewItem newItem2 = new MobileWeixinNewItem(
					"【订门票】10000家景区折扣门票，1张起订", null, MessageFormat.format(
							"{0}ticket?losc={1}&openid={2}", networkPath,
							"030621", openid), picLvmama + "2.png");
			news.add(newItem2);
			MobileWeixinNewItem newItem3 = new MobileWeixinNewItem(
					"【订自由行】门票、酒店、交通，一步到位", null, MessageFormat.format(
							"{0}route?losc={1}&openid={2}", networkPath,
							"030623", openid), picLvmama + "3.png");
			news.add(newItem3);
			MobileWeixinNewItem newItem4 = new MobileWeixinNewItem(
					"【旅游团购】花费更少！玩得更好", null, MessageFormat.format(
							"{0}groupbuy?losc={1}&openid={2}", networkPath,
							"031520", openid), picLvmama + "7.png");
			news.add(newItem4);

			MobileWeixinNewItem newItem5 = new MobileWeixinNewItem(
					"【查订单】再也不用担心误删短信凭证了", null, MessageFormat.format(
							"{0}order/myorder.htm?losc={1}&openid={2}",
							networkPath, "031525", openid), picLvmama + "5.png");
			news.add(newItem5);

			// MobileWeixinNewItem newItem5 = new MobileWeixinNewItem(
			// "【活动】点赞送新年祈福双人套餐名单公布",
			// null,
			// MessageFormat
			// .format("http://pic.weibopie.com/weixin/material/2013/12/30/52c0ca5390b6f.html?openid={0}",
			// openid), picLvmama + "1314.jpg");
			// news.add(newItem5);

			dataMap.put("type", "news");
			dataMap.put("text", "");
			dataMap.put("news", news);
		} else {
			if ("lvmama_test".equals(q)) {
				List<MobileWeixinNewItem> news = new ArrayList<MobileWeixinNewItem>();
				MobileWeixinNewItem newItem1 = new MobileWeixinNewItem(
						"自助游天下  就找驴妈妈", null, MessageFormat.format(
								"{0}my?openid={1}", networkPath, openid),
						picLvmama + "biaoti.jpg");
				news.add(newItem1);

				// MobileWeixinNewItem newItem1 = new MobileWeixinNewItem(
				// "0元抽ipad Air最终名单揭晓",
				// null,
				// MessageFormat
				// .format("http://m.lvmama.com/activity/index.php?s=/Weixin/getGuanZhuCode.html&losc={0}&openId={1}",
				// "032084", openid), picLvmama
				// + "biaoti.jpg");
				// news.add(newItem1);
				dataMap.put("type", "news");
				dataMap.put("text", "");
				dataMap.put("news", news);
			} else {
				List<MobileWeixinNewItem> news = new ArrayList<MobileWeixinNewItem>();
				MobileWeixinNewItem newItem1 = new MobileWeixinNewItem(
						"驴妈妈优惠券,省钱有道", null, MessageFormat.format(
								"{0}weixinLogin.htm?losc={1}&openid={2}",
								networkPath, "030628", openid), picLvmama
								+ "biaoti.jpg");
				news.add(newItem1);

				// MobileWeixinNewItem newItem1 = new MobileWeixinNewItem(
				// "0元抽ipad Air最终名单揭晓",
				// null,
				// MessageFormat
				// .format("http://m.lvmama.com/activity/index.php?s=/Weixin/getGuanZhuCode.html&losc={0}&openId={1}",
				// "032084", openid), picLvmama
				// + "biaoti.jpg");
				// news.add(newItem1);

				// MobileWeixinNewItem newItem6 = new MobileWeixinNewItem(
				// "【领优惠券】人人都能领！1个ID限领一次", null, MessageFormat.format(
				// "{0}weixinLogin.htm?losc={1}&openid={2}", networkPath,
				// "030628", openid),
				// picLvmama + "6.png");
				// news.add(newItem6);

				MobileWeixinNewItem newItem2 = new MobileWeixinNewItem(
						"【订门票】10000家景区折扣门票，1张起订", null, MessageFormat.format(
								"{0}ticket?losc={1}&openid={2}", networkPath,
								"030621", openid), picLvmama + "2.png");
				news.add(newItem2);
				MobileWeixinNewItem newItem3 = new MobileWeixinNewItem(
						"【订自由行】门票、酒店、交通，一步到位", null, MessageFormat.format(
								"{0}route?losc={1}&openid={2}", networkPath,
								"030623", openid), picLvmama + "3.png");
				news.add(newItem3);
				MobileWeixinNewItem newItem4 = new MobileWeixinNewItem(
						"【旅游团购】花费更少！玩得更好", null, MessageFormat.format(
								"{0}groupbuy?losc={1}&openid={2}", networkPath,
								"031520", openid), picLvmama + "7.png");
				news.add(newItem4);

				MobileWeixinNewItem newItem5 = new MobileWeixinNewItem(
						"【查订单】再也不用担心误删短信凭证了", null, MessageFormat.format(
								"{0}order/myorder.htm?losc={1}&openid={2}",
								networkPath, "031525", openid), picLvmama
								+ "5.png");
				news.add(newItem5);

				// MobileWeixinNewItem newItem5 = new MobileWeixinNewItem(
				// "【活动】点赞送新年祈福双人套餐名单公布",
				// null,
				// MessageFormat
				// .format("http://pic.weibopie.com/weixin/material/2013/12/30/52c0ca5390b6f.html?openid={0}",
				// openid), picLvmama + "1314.jpg");
				// news.add(newItem5);

				dataMap.put("type", "news");
				dataMap.put("text", "");
				dataMap.put("news", news);
			}
		}
		return dataMap;
	}

	/**
	 * 跳转到驴妈妈网站.
	 * 
	 * @return 注册页面Result
	 */
	@Action("wap_to_lvmama")
	public String toLvmama() {
		ServletUtil.addCookie(getResponse(), Constant.WAP_TO_LVMAMA, "true", 1,
				true);// 默认保存30天
		return "wap_to_lvmama";
	}

	/**
	 * 根据微信ID查询用户是否绑定过用户
	 */
	@Action("getWechatStatus")
	public void getWechatStatus() {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("wechatId", openId);

		List<UserUser> userList = userUserProxy.getUsers(param);
		Long bindStatus;
		if (userList != null && userList.size() > 0) {
			bindStatus = 1L;
		} else {
			bindStatus = 0L;
		}
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("bindStatus", bindStatus);
		this.sendAjaxResult(jsonObj.toString());
	}

	/**
	 * 初始化活动losc
	 */
	public void initActivityLosc() {
		// 落地活动 12580统计代码
		if (!StringUtils.isEmpty(activityChannel)) {
			ServletUtil.addCookie(getResponse(),
					ClutterConstant.MOBILE_ACTIVITY_CHANNEL, activityChannel,
					1, false);// 默认保存1天
		}
	}

	public String getService() {
		return service;
	}

	public void setService(String service) {
		this.service = service;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getMobileOrEMail() {
		return mobileOrEMail;
	}

	public void setMobileOrEMail(String mobileNo) {
		this.mobileOrEMail = mobileNo;
	}

	public String getAuthenticationCode() {
		return authenticationCode;
	}

	public void setAuthenticationCode(String authenticationCode) {
		this.authenticationCode = authenticationCode;
	}

	public void setMobileUserService(IClientUserService mobileUserService) {
		this.mobileUserService = mobileUserService;
	}

	public String getActivityChannel() {
		return null == activityChannel ? "" : activityChannel.trim();
	}

	public void setActivityChannel(String activityChannel) {
		this.activityChannel = activityChannel;
	}

	public UserUserProxy getUserUserProxy() {
		return userUserProxy;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public boolean isAbsolutePath() {
		return absolutePath;
	}

	public void setAbsolutePath(boolean absolutePath) {
		this.absolutePath = absolutePath;
	}
	
}
