package com.lvmama.clutter.web;

import java.io.File;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.model.MobileUser;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.utils.DateUtil;
import com.lvmama.clutter.utils.JSONUtil;
import com.lvmama.clutter.web.place.AppBaseAction;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.mobile.MobilePersistanceLog;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.mobile.MobileClientService;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.pet.service.user.UserCooperationUserService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.comm.vo.Constant;

public class ClientLoginAction extends AppBaseAction {
	private static final Log log = LogFactory.getLog(ClientLoginAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -3429984523385020004L;

	private static final String LT_FIND_PASS_WORLD = "lvtu_find_passworld_send_authcode";
	
	/**
	 * 产品服务
	 */
	protected ProductSearchInfoService productSearchInfoService;
	protected UserCooperationUserService userCooperationUserService;
	protected UserUserProxy userUserProxy;
	private MarkCouponService markCouponService;
	private MobileClientService mobileClientService;
	private String sign;

	

	@Action("/client/login")
	public void login() {
		String userName = this.getRequest().getParameter("userName");
		String password = this.getRequest().getParameter("password");
		String lvSessionId = this.getRequest().getParameter(
				Constant.LV_SESSION_ID);
		String requestSign = this.getRequest().getParameter("sign");
		String signKey = ClutterConstant.getMobileSignKey();
		String serverSign = String.format("%s%s%s%s", userName, password,
				lvSessionId, signKey);
		Map<String, Object> resultMap = super.resultMapCreator();
		log.info("userName:"+userName+" pwd:"+password+" server Sign:"+MD5.md5(serverSign)+" requestSign"+requestSign);
		if (!MD5.md5(serverSign).equalsIgnoreCase(requestSign)) {
			this.putErrorMessage(resultMap, "非法访问");
			this.sendResult(resultMap, null, false);
		}

		String loginUrl = ClutterConstant.getLoginURL() + "?mobileOrEMail="
				+ userName + "&password=" + password + "&"
				+ Constant.LV_SESSION_ID + "=" + lvSessionId + "&loginType="
				+ Constant.LOGIN_TYPE.MOBILE.name()+"&firstChannel="+getFirstChannel()+"&secondChannel="+getSecondChannel();;
		this.getRequest().setAttribute(Constant.LV_SESSION_ID, lvSessionId);
		String json = HttpsUtil.proxyRequestGet(loginUrl,InternetProtocol.getRemoteAddr(getRequest()));

		JSONObject jsonObj = JSONObject.fromObject(json);

		Map<String, Object> map = (Map<String, Object>) jsonObj;

		if (Boolean.valueOf(map.get("success").toString()) == true) {
			// 登录后 返回用户信息
			// 账号信息
			MobileUser mu = new MobileUser();
			UserUser user = super.getUser();

			CashAccountVO moneyAccount = cashAccountService
					.queryMoneyAccountByUserNo(super.getUser().getUserNo());

			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("userId", user.getId());
			List<UserCooperationUser> cooperationUseres = userCooperationUserService
					.getCooperationUsers(parameters);
			if (cooperationUseres != null && !cooperationUseres.isEmpty()) {
				UserCooperationUser ucu = cooperationUseres.get(0);
				mu.setLoginChannel(ucu.getCooperation());
			}

			String firstChannel = this.getRequest()
					.getParameter("firstChannel");
			String secondChannel = this.getRequest().getParameter(
					"secondChannel");
			String channel = firstChannel + "_" + secondChannel;
			if ((Constant.MOBILE_PLATFORM.ANDROID.name() + "_" + Constant.CHANNEL.LIANTONG
					.name()).equals(channel)) {// android联通渠道,送5元优惠券
				if (null != user) {
					this.giveCoupon(user);
				}
			}

			// 首次登录，客户端赠送100元优惠券。
			//first.login.client.coupon=4213,4214,4215,4216
			//first.login.expire.date=2014-12-31
			String strExpireDate = Constant.getInstance().getValue("first.login.expire.date");
			if(!StringUtils.isEmpty(strExpireDate)){
				try {
					Date expireDate = DateUtils.parseDate(strExpireDate.trim(), "yyyy-MM-dd");
					if(Calendar.getInstance().getTime().before(expireDate)){
						boolean isSend = this.sendCoupon4ClientFirstLogin();
					}
				} catch (ParseException e) {
					throw new LogicException("首次登录格式化日期失败");
				}
			}
			
			// 客户端v5首次登录送880元优惠券 ，
			this.sendCoupon4ClientV5FirstLogin(this.getRequest().getParameter("lvversion"),firstChannel);
			
			this.sendResult(resultMap,
					com.lvmama.clutter.utils.MobileCopyPropertyUtils
							.copyUserUser2MobileUser(super.getUser(),
									new MobileUser(), moneyAccount), false);
		} else {
			this.putErrorMessage(resultMap, (String) map.get("errorText"));
			this.sendResult(resultMap, map, false);
		}

	}
	
    /**
     * V5客户端登陆赠送880元优惠券 
     * @param lvversion
     */
	private void sendCoupon4ClientV5FirstLogin(String lvversion,String firstChannel) {
		// 大于等于5.0.0 ；如5.0.1；5.0.0 
		try {
			if(!StringUtils.isEmpty(lvversion) 
					&& (lvversion.compareTo("5.0.0") >= 0
					    ||(Constant.MOBILE_PLATFORM.WP8.name().equals(firstChannel) 
					       && lvversion.compareTo("1.0.0") >= 0)
					  )) {

				// 如果活动没有结束 
				if(System.currentTimeMillis() < DateUtil.getDateTime(ClutterConstant.getFirstLogin4V4EndDate())) {
						UserUser user = this.getUser();
						if(null == user) {
							log.error("..sendCoupon4ClientV5FirstLogin..user not login..."); 
							return;
						}
						HttpServletRequest request = this.getRequest();
						String deviceId = request.getParameter("udid");
						if(!StringUtils.isEmpty(deviceId)) {
							// 判断是否首次登录
							List<MobilePersistanceLog> mobliePersistanceLogs  = mobileClientService.selectListByDeviceIdAndObjectType(deviceId,ClutterConstant.FIRST_LOG_IN4V5);
							// 如果是首次登录
							if(null == mobliePersistanceLogs || mobliePersistanceLogs.size() < 1) {
								// 保存用户第一次登录信息  
								this.saveUserFirstLoginInfo(deviceId,request,user,ClutterConstant.FIRST_LOG_IN4V5);
								// 赠送880元优惠券 
								this.sendCoupon4ClientV5(user);
							}
						}
					
				}
			} else {
				log.error("..sendCoupon4ClientV5FirstLogin..lvversion="+lvversion); 
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("..sendCoupon4ClientV5FirstLogin..error...."); 
		}
	}

	@Action("/client/refreshSessionId")
	public void refreshSessionId() {
		Map<String, Object> resultMap = super.resultMapCreator();
		this.sendResultV3(resultMap, null);
	}

	@Action("/client/getSessionId")
	public void requestSessionId() {
		Map<String, Object> resultMap = super.resultMapCreator();
		String lvsessionId = UUID.randomUUID().toString();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put(Constant.LV_SESSION_ID, lvsessionId);
		this.sendResult(resultMap, map, false);
	}
	
	/**
	 * 发送校验码
	 */
	@Action("/client/sendAuthCode")
	public void sendAuthCode() {
		try {
			String mobile = this.getRequest().getParameter("mobile");
			String sign = this.getRequest().getParameter("sign");
			String lvsessionId = this.getRequest().getParameter(Constant.LV_SESSION_ID);
			String signKey = ClutterConstant.getMobileSignKey();
			String serverSign = String.format("%s%s", mobile, signKey);
			if (MD5.md5(serverSign).equalsIgnoreCase(sign)) {
				String url = ClutterConstant.getNssoUrl()
						+ "/mobileAjax/sendMessage.do?mobile=" + mobile + "&"
						+ Constant.LV_SESSION_ID + "=" + lvsessionId;
				this.getRequest().setAttribute(Constant.LV_SESSION_ID,
						lvsessionId);
				String json = HttpsUtil.requestGet(url);
				json = json.replace("无法找到所需的用户信息", "该手机号码尚未注册！");
				this.sendAjaxResult(json);
			} else {
				this.sendAjaxResult("{\"errorText\":\"非法请求!\",\"success\":false}");
			}
		} catch (Exception e) {
			this.sendAjaxResult("{\"errorText\":\"" + e.getMessage()
					+ "\",\"success\":false}");
		}
	}

	/**
	 * 找回密码-发送校验码
	 */
	@Action("/client/sendMessage")
	public void sendMessage() {
		try {
			String mobile = this.getRequest().getParameter("mobile");
			String lvsessionId = ServletUtil.getLvSessionId(getRequest(),
					getResponse());

			if (StringUtil.validMobileNumber(mobile)) {
				// 每个用户一分钟内只能发送一次 短信凭证
				Object object = MemcachedUtil.getInstance().get(
						LT_FIND_PASS_WORLD + mobile);
				if (null != object) {
					long c_long = com.lvmama.clutter.utils.DateUtil
							.getCurrentTimeLong(); // 当前日期秒
					long h_long = Long.parseLong(object.toString()); // 历史日期 秒
					if (c_long - h_long < 55) {
						this.sendAjaxResult("{\"errorText\":\"一分钟只能发送一次\",\"success\":false}");
						return;
					}
				}

				String url = ClutterConstant.getNssoUrl()
						+ "/mobileAjax/sendMessage.do?mobile=" + mobile + "&"
						+ Constant.LV_SESSION_ID + "=" + lvsessionId;
				this.getRequest().setAttribute(Constant.LV_SESSION_ID,
						lvsessionId);
				String json = HttpsUtil.requestGet(url);
				json = json.replace("无法找到所需的用户信息", "该手机号码尚未注册！");
				// 发送完成后 用户信息保存在memcache中
				MemcachedUtil.getInstance().set(LT_FIND_PASS_WORLD + mobile,
						55,
						com.lvmama.clutter.utils.DateUtil.getCurrentTimeLong());
				this.sendAjaxResult(json);
			} else {
				this.sendAjaxResult("{\"errorText\":\"请输入正确的手机号\",\"success\":false}");
			}
		} catch (Exception e) {
			this.sendAjaxResult("{\"errorText\":\"" + e.getMessage()
					+ "\",\"success\":false}");
		}
	}

	
	
	/**
	 * 积分兑换优惠券.
	 * 
	 * @param params
	 *            branchId
	 */
	@Action("/client/pointChangeCoupon")
	public void pointChangeCoupon() {
		String lvsessionId = ServletUtil.getLvSessionId(getRequest(),
				getResponse());
		;
		String branchId = this.getRequest().getParameter("branchId");
		if (StringUtils.isEmpty(branchId)) {
			this.sendAjaxResult("{\"errorMessage\": \"参数(branchId)不能为空\",\"data\":{},\"code\":\"-1\"}");
			return;
		}
		ProdBranchSearchInfo psi = productSearchInfoService
				.getProdBranchSearchInfoByBranchId(Long.valueOf(branchId));
		if (null == psi || StringUtils.isEmpty(psi.getSubProductType())) {
			this.sendAjaxResult("{\"message\": \"产品类别找不到\",\"data\":{},\"code\":\"-1\"}");
			return;
		}

		String subProductType = psi.getSubProductType();
		String couponId = this.getRequest().getParameter("couponId");
		if (StringUtils.isEmpty(subProductType)) {
			this.sendAjaxResult("{\"errorMessage\": \"参数(subProductType)不能为空\",\"data\":{},\"code\":\"-1\"}");
			return;
		}
		if (StringUtils.isEmpty(couponId)) {
			this.sendAjaxResult("{\"errorMessage\":\"参数(couponId)不能为空\",  \"data\": {},  \"code\": \"-1\" }");
			return;
		}
		String pCCUrl = ClutterConstant.getNssoUrl()
				+ "/ajax/pointChangeCoupon.do?subProductType=" + subProductType
				+ "&couponId=" + couponId + "&" + Constant.LV_SESSION_ID + "="
				+ lvsessionId;
		try {
			this.getRequest().setAttribute(Constant.LV_SESSION_ID, lvsessionId);
			String jsons = HttpsUtil.requestGet(pCCUrl);
			JSONObject obj = JSONUtil.getObject(jsons);
			// 订单应付金额(以分为单位)
			if (null != obj.get("success")
					&& "true".equals(obj.get("success").toString())) {
				// UserUser user = userpro
				UserUser tempUser = getUser();
				UserUser user = userUserProxy.getUserUserByPk(tempUser.getId());
				this.sendAjaxResult("{\"message\": \"兑换成功\",  \"data\": {\"code\":\""
						+ obj.get("errorText")
						+ "\",\"point\":"
						+ user.getPoint() + "},  \"code\": \"1\" }");
			} else {
				this.sendAjaxResult("{\"message\":\"" + obj.get("errorText")
						+ "\",\"data\":{},\"code\":\"-1\"}");
			}

			this.sendAjaxResult(jsons);
		} catch (Exception e) {
			e.printStackTrace();
			this.sendAjaxResult("{  \"errorMessage\":\"" + e.getMessage()
					+ "\",\"data\":{},\"code\":\"-1\"}");
		}
	}
	
	/**
	 * 客户端首次登录赠送100元优惠券 
	 */
	public boolean sendCoupon4ClientFirstLogin() {
		try {
			UserUser user = this.getUser();
			if(null == user) {
				return false;
			}
			HttpServletRequest request = this.getRequest();
			String deviceId = request.getParameter("udid");
			if(!StringUtils.isEmpty(deviceId)) {
				// 判断是否首次登录
				List<MobilePersistanceLog> mobliePersistanceLogs  = mobileClientService.selectListByDeviceIdAndObjectType(deviceId,Constant.COM_LOG_OBJECT_TYPE.LOGIN.name());
				// 如果是首次登录
				if(null == mobliePersistanceLogs || mobliePersistanceLogs.size() < 1) {
					// 保存用户第一次登录信息  
					this.saveUserFirstLoginInfo(deviceId,request,user,"");
					// 赠送180元优惠券 
					return this.sendCoupon4Client(user);
				}
			}
		}catch(Exception e) {
			e.printStackTrace();
			log.error("..sendCoupon4ClientFirstLogin..error...11..."); 
		}
		return false;
	} 
	
	/**
	 * 保存用户信息.
	 * @param deviceId
	 * @param request
	 * @param user
	 */
	public void saveUserFirstLoginInfo(String deviceId,HttpServletRequest request,UserUser user,String actionType) {
		String firstChannel = request.getParameter("firstChannel");
		String secondChannel = request.getParameter("secondChannel");
		String osVersion = request.getParameter("osVersion");
		String userAgent = request.getParameter("userAgent");
		String lvversion = request.getParameter("lvversion");
		Long appVersion = 0L;
		if (!StringUtils.isEmpty(lvversion)) {
			appVersion = Long.valueOf(lvversion.replaceAll("[.]", ""));
		}
		Long objectId = user.getId();
		String objectType = Constant.COM_LOG_OBJECT_TYPE.LOGIN.name();
		if(!StringUtils.isEmpty(actionType)) {
			objectType = actionType;
		}
		mobileClientService.insertMobilePersistanceLog(firstChannel, secondChannel, deviceId, appVersion, objectId, objectType,osVersion, userAgent);
	}
	
	/**
	 * 绑定用户优惠码
	 * @param user
	 */
	public boolean sendCoupon4Client(UserUser user) {
		// 生成优惠券
		String couponIdStr = Constant.getInstance().getValue("first.login.client.coupon");
        if(!StringUtils.isEmpty(couponIdStr)) {
        	String[] couponIdStrs = couponIdStr.trim().split(",");
        	for(String strCouponId:couponIdStrs){
        		long couponId = Long.valueOf(strCouponId);
            	MarkCouponCode markCouponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(couponId);
            	if(!StringUtils.isEmpty(markCouponCode.getCouponCode())) {
            		@SuppressWarnings("unused")
					Long id = markCouponService.bindingUserAndCouponCode(user, markCouponCode.getCouponCode());
            	}
        	}
        	log.info("..客户端首次登录赠送180元优惠券...userId="+user.getId());
        	return true;
        }
        return false;
	}
	
	/**
	 * 绑定用户优惠码
	 * @param user
	 */
	public boolean sendCoupon4ClientV5(UserUser user) {
		// 生成优惠券
		String couponIdStr = Constant.getInstance().getValue("client.v5.firstLogin.coupons");
        if(!StringUtils.isEmpty(couponIdStr)) {
        	String[] couponIdStrs = couponIdStr.trim().split(",");
        	for(String strCouponId:couponIdStrs){
        		long couponId = Long.valueOf(strCouponId);
            	MarkCouponCode markCouponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(couponId);
            	if(!StringUtils.isEmpty(markCouponCode.getCouponCode())) {
            		@SuppressWarnings("unused")
					Long id = markCouponService.bindingUserAndCouponCode(user, markCouponCode.getCouponCode());
            	}
        	}
        	// 首发渠道多送 
        	boolean b = this.isExtChannel(user);
        	if(!b) {
        		log.info("..客户端V5首次登录赠送880元优惠券...userId="+user.getId());
        	}
        	return true;
        }
        return false;
	}
	
	
	
	/**
	 * 首发渠道多送200元优惠券 
	 * @param user
	 * @return
	 */
	public boolean isExtChannel(UserUser user) {
		String firstChannel = this.getRequest().getParameter("firstChannel");
		String secondChannel = this.getRequest().getParameter("secondChannel");
		String channel = firstChannel + "_" + secondChannel;
		// 首发渠道多送200元优惠券 
		if (ClientUtils.isExtChannel(channel)) {
			try {
				String[] couponIdStrs = {"4315","4314","4314"};
	        	for(String strCouponId:couponIdStrs){
	        		long couponId = Long.valueOf(strCouponId);
	            	MarkCouponCode markCouponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(couponId);
	            	if(!StringUtils.isEmpty(markCouponCode.getCouponCode())) {
	            		@SuppressWarnings("unused")
						Long id = markCouponService.bindingUserAndCouponCode(user, markCouponCode.getCouponCode());
	            	}
	        	}
	        	log.info("..客户端V5首发渠道首次登录赠送1080元优惠券...userId="+user.getId());
			}catch(Exception e) {
				e.printStackTrace();
				log.info("..客户端V5首发渠道首次登录赠送1080元优惠券失败...userId="+user.getId());
			}
			
		}
		
		return false;
	}
	
	/**
	 * 送优惠券
	 * 
	 * @param user
	 */
	private void giveCoupon(UserUser user) {
		boolean isGivedCoupon = saveMobileLog(user);
		if(!isGivedCoupon){
			// 生成优惠券
			String strCouponIds = Constant.getInstance().getValue(
					"coupon.client.coupon.id");
	
			long couponId = this.getFirstCouponId(strCouponIds);
	
			MarkCouponCode markCouponCode = markCouponService
					.generateSingleMarkCouponCodeByCouponId(couponId);
			markCouponService.bindingUserAndCouponCode(user,
					markCouponCode.getCouponCode());
		}
	}

	/**
	 * 标识该设备已经送过优惠券
	 * @param user
	 */
	private boolean saveMobileLog(UserUser user) {
		
		HttpServletRequest request = this.getRequest();
		String deviceId = request.getParameter("udid");
		String firstChannel = request.getParameter("firstChannel");
		String secondChannel = request.getParameter("secondChannel");
		String channel = firstChannel+"_"+secondChannel;
		List<MobilePersistanceLog> mobliePersistanceLogs = mobileClientService.selectListbyPersistanceByDeviceId(deviceId, channel);
		if(null==mobliePersistanceLogs||mobliePersistanceLogs.size()==0){
			String osVersion = request.getParameter("osVersion");
			String userAgent = request.getParameter("userAgent");
			String lvversion = request.getParameter("lvversion");
			Long appVersion = 0L;
			if (!StringUtils.isEmpty(lvversion)) {
				appVersion = Long.valueOf(lvversion.replaceAll("[.]", ""));
			}
			Long objectId = user.getId();
			String objectType = Constant.CHANNEL.LIANTONG.name();
			mobileClientService.insertMobilePersistanceLog(firstChannel,
					secondChannel, deviceId, appVersion, objectId, objectType,
					osVersion, userAgent);
			return false;
		}else{
			return true;
		}
		
	}

	
	/**
	 * 从配置所有优惠券ID中随机生成一个优惠券ID
	 * 
	 * @param strCouponIds
	 * @return
	 */
	private long getFirstCouponId(String strCouponIds) {
		if (StringUtils.isEmpty(strCouponIds)) {
			throw new LogicException("优惠券ID不能为空!");
		}
		String[] strcouponIdArr = strCouponIds.split(",");
		return Long.parseLong(strcouponIdArr[0]);
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setProductSearchInfoService(
			ProductSearchInfoService productSearchInfoService) {
		this.productSearchInfoService = productSearchInfoService;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setUserCooperationUserService(
			UserCooperationUserService userCooperationUserService) {
		this.userCooperationUserService = userCooperationUserService;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public void setMobileClientService(MobileClientService mobileClientService) {
		this.mobileClientService = mobileClientService;
	}
	
}
