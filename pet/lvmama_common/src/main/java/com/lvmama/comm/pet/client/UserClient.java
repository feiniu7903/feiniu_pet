package com.lvmama.comm.pet.client;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.pet.po.email.EmailContent;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.mark.MarkMembershipCardDiscount;
import com.lvmama.comm.pet.po.pub.ComEmailTemplate;
import com.lvmama.comm.pet.po.shop.ShopCooperationCoupon;
import com.lvmama.comm.pet.po.shop.ShopOrder;
import com.lvmama.comm.pet.po.shop.ShopProduct;
import com.lvmama.comm.pet.po.shop.ShopProductCoupon;
import com.lvmama.comm.pet.po.user.UserCertCode;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.mark.MarkMembershipCardDiscountService;
import com.lvmama.comm.pet.service.pub.ComEmailTemplateService;
import com.lvmama.comm.pet.service.pub.ComSmsTemplateService;
import com.lvmama.comm.pet.service.shop.ShopCooperationCouponService;
import com.lvmama.comm.pet.service.shop.ShopOrderService;
import com.lvmama.comm.pet.service.shop.ShopProductService;
import com.lvmama.comm.pet.service.shop.ShopProductZhuantiService;
import com.lvmama.comm.pet.service.shop.ShopUserService;
import com.lvmama.comm.pet.service.sms.SmsRemoteService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.pet.vo.AfterUserRegisterInfo;
import com.lvmama.comm.pet.vo.ShopRemoteCallResult;
import com.lvmama.comm.pet.vo.ShopUserInfo;
import com.lvmama.comm.pet.vo.mark.MarkMembershipCardDiscountDetails;
import com.lvmama.comm.utils.FileUtil;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UUIDGenerator;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.EMAIL_SSO_TEMPLATE;
import com.lvmama.comm.vo.Constant.SMS_SSO_TEMPLATE;

/**
 * 此类是解决多次远程远程调用，且代码段会被重复使用所设计。
* 项目名称：pet_lvmama_comm   
* 类名称：UserClient   
* 类描述：暂无 
* 创建人：Brian   
* 创建时间：2012-8-15 上午10:35:57   
* 修改人：Brian   
* 修改时间：2012-8-15 上午10:35:57   
* 修改备注： 
* @version
 */
public final class UserClient {
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(UserClient.class);
	private ComEmailTemplateService comEmailTemplateService;
	private ComSmsTemplateService comSmsTemplateRemoteService;
	//private FSClient fsClient;
	private EmailClient emailClient;
	private SmsRemoteService smsRemoteService;
	private UserUserProxy userUserProxy;
	private MarkMembershipCardDiscountService markMembershipCardDiscountService;
	private ShopOrderService shopOrderService;
	private ShopProductService shopProductService;
	private ShopUserService shopUserService;
	private ShopCooperationCouponService shopCooperationCouponService;
	private MarkCouponService markCouponService;
	private ShopProductZhuantiService shopProductZhuantiService;
	
	public String sendAuthenticationCode(final USER_IDENTITY_TYPE type,
			final UserUser user, final String key) {
		String authenticationCode = null;
		if (!type.equals(USER_IDENTITY_TYPE.MOBILE)
				&& !type.equals(USER_IDENTITY_TYPE.EMAIL)) {
			throw new UnsupportedOperationException("不支持的类型");
		}
		if (type.equals(USER_IDENTITY_TYPE.MOBILE)) {
			authenticationCode = UserUserUtil.authenticationCodeGenerator();
			if (LOG.isDebugEnabled()) {
				LOG.debug("生成校验码为" + authenticationCode);
			}
			sendAuthenticationCodeByMobile(user, authenticationCode, key);
		}
		if (type.equals(USER_IDENTITY_TYPE.EMAIL)) {
			if(Constant.EMAIL_SSO_TEMPLATE.EMAIL_NUMBER_AUTHENTICATE_CODE.name().equals(key) || Constant.EMAIL_SSO_TEMPLATE.MOBILE_EMAIL_NUMBER_AUTHENTICATE_CODE.name().equals(key))
			{
				//EMAIL数字码邮件, 发送数字验证码
				authenticationCode = UserUserUtil.authenticationCodeGenerator();
				if (LOG.isDebugEnabled()) {
					LOG.debug("生成校验码为" + authenticationCode);
				}
			}
			else
			{
				try {
					authenticationCode = new MD5().code(user.getEmail()+Constant.AUTHENTICATION_CODE_SUFFIX);
				} catch (NoSuchAlgorithmException nsae) {
					nsae.printStackTrace();
				}
			}
			sendAuthenticationCodeByMail(user, authenticationCode, key);
		}
		return authenticationCode;
	}
	
	/**
	 * 绑定会员卡和会员
	 * @param userId
	 * @param code
	 * @return
	 */
	public boolean bindingUserAndMembershipCardCode(final Long userId, final String code) {
		if (userUserProxy.bindingUserAndMembershipCardCode2(userId, code)) {
			UserUser user = userUserProxy.getUserUserByPk(userId);
			
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("cardCode", code); 
			List<MarkMembershipCardDiscountDetails> discounts = markMembershipCardDiscountService.query(parameters);
			for (MarkMembershipCardDiscount discount : discounts) {
				MarkCouponCode couponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(discount.getCouponId());
				LOG.debug("generate coupon code for member card "+code+","+couponCode.getCouponCode());
				markCouponService.bindingUserAndCouponCode(user, couponCode.getCouponCode());
			}			
			return true;
		} else {
			return false;
		}		
	}
	
	public ShopRemoteCallResult createShopOrder(final Long userId, final Long productId,
			final int quantity, final ShopUserInfo info) {
		
		ShopProduct product = shopProductService.queryByPk(productId);
		
		//对于合作商户优惠券，我们数据库里订单固化优惠券信息字段不能存太多，所以临时做判断不能一次兑换过多
		if (Constant.SHOP_PRODUCT_TYPE.COOPERATION_COUPON.name().equals(product.getProductType())) {
			Map<String, Object> parameters = new HashMap<String,Object>();
			parameters.put("productId", productId);
			parameters.put("_endRow", 2);
			parameters.put("_startRow", 1);
			List<ShopCooperationCoupon> shopCooperationCouponList = shopCooperationCouponService.query(parameters);
			if(shopCooperationCouponList.size() != 0)
			{
				String couponInfo = shopCooperationCouponList.get(0).getCouponInfo();
				int maxCouponGetSize = 1900/(couponInfo.length()+4);   //数据库中最多只能存4000, +4是指各优惠券间分隔符/r/n的长度，且考虑中文问题
				if(maxCouponGetSize < quantity)
				{
					return new ShopRemoteCallResult(false, ShopProductService.PRODUCT_TOO_MUCH, 
							"您本次兑换的优惠券张数过多，请减少兑换优惠券数。建议一次兑换数量小于"+maxCouponGetSize+"张");
				}
			}
		}
		
		ShopRemoteCallResult callResult = shopOrderService.checkPointToChangeProduct(userId, productId, quantity);

		if (callResult.isResult()) {
			ShopOrder order = new ShopOrder();
			
			//创建订单信息
			Long payPoint = shopOrderService.getPayPoint(userId, product, quantity);
				
			LOG.info("SHOP商城用户:" + userId + " /下单产品ID:" + productId + " /数量:"
					+ quantity + " /消耗积分:" + payPoint);
			order.setActualPay(payPoint);
			order.setOughtPay(payPoint);
			order.setUserId(userId);
			order.setProductId(productId);
			order.setProductName(product.getProductName());
			order.setProductType(product.getProductType());
			order.setQuantity(quantity);
			order.setAddress(info.getAddress());
			order.setMobile(info.getMobile());
			order.setName(info.getName());
			order.setZip(info.getZip());
			
			//订单状态处理
			if (Constant.SHOP_PRODUCT_TYPE.PRODUCT.name().equals(product.getProductType())) {
				order.setOrderStatus(Constant.ORDER_STATUS.UNCONFIRM.name());
			} else if (Constant.SHOP_PRODUCT_TYPE.COUPON.name().equals(product.getProductType())) {
				order.setOrderStatus(Constant.ORDER_STATUS.UNCONFIRM.name());
			}  else if (Constant.SHOP_PRODUCT_TYPE.COOPERATION_COUPON.name().equals(product.getProductType())) {
				order.setOrderStatus(Constant.ORDER_STATUS.UNCONFIRM.name());//合作优惠券是自动发货的
			}else {
				order.setOrderStatus(Constant.ORDER_STATUS.NORMAL.name());
			}

			//减库存,新增订单,写日志
			shopProductService.reduceStocks(productId, quantity);
			Long orderId = shopOrderService.insert(order);
			order.setOrderId(orderId);
			callResult.setObject(orderId);

			//分配优惠券
			if (Constant.SHOP_PRODUCT_TYPE.COUPON.name().equals(product.getProductType())) {
				bindingCouponAndUser(userId, order, ((ShopProductCoupon) product).getCouponId());
			}
			
			if (Constant.SHOP_PRODUCT_TYPE.COOPERATION_COUPON.name().equals(product.getProductType())) {
				bindingCoopCouponAndUser(userId, order, productId);
			}
			
			shopUserService.reducePoint(userId, "POINT_FOR_CHANGE_PRODUCT",
					Long.valueOf(0 - payPoint), String.valueOf(orderId));
			
			shopOrderService.deleteShopProductZhuanti(userId, product, quantity);
			
		}
		return callResult;
	}
	
	/**
	 * 发送邮件
	 * @param user
	 * @param subject
	 */
	public void sendMail(UserUser user, String subject, HashMap<String, Object> parameters)
	{
		ComEmailTemplate emailTemplate = comEmailTemplateService.getComEmailTemplateByTemplateName(subject);
		if (null == emailTemplate || null == emailTemplate.getContentTemplateFile()) {
			debug("Cann't find email template(templateName = ')" + subject + "')");
			return;
		}

		File file = ResourceUtil.getResourceFile(emailTemplate.getContentTemplateFile());
		if(file != null)
		{
			byte[] fileData;
			try {
				fileData = FileUtil.getBytesFromFile(file);
			} catch (Exception e2) {
				LOG.error("get mail content error "+e2);
				return;
			}
			String content = "";
			try {
				content = new String(fileData, "UTF-8");
			} catch (UnsupportedEncodingException e1) {
				// TODO Auto-generated catch block
				LOG.error("encoding mail content error "+e1);
				return;
			}
			EmailContent email = new EmailContent();
			email.setFromName("驴妈妈旅游网");
			email.setToAddress(user.getEmail());
			try {
				email.setSubject(StringUtil.composeMessage(emailTemplate.getSubjectTemplate(), parameters));
				email.setContentText(StringUtil.composeMessage(content, parameters));
				emailClient.sendEmail(email);
			} catch (Exception e) {
				LOG.error("replace email content error!");
			}
		}
		else
		{
			LOG.error("get fs file error :"+emailTemplate.getContentTemplateFile());
		}
	}
	
	
	/**
	 * 创建优惠券类产品
	 * @param userId 用户标识
	 * @param order 订单
	 * @param couponId 优惠券号
	 */
	private void bindingCouponAndUser(final Long userId, final ShopOrder order, final Long couponId) {
		if (null == userId || null == order || null == couponId) {
			return;
		}
		for (int i = 0; i < order.getQuantity(); i++) {
			MarkCouponCode couponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(couponId);
			if (LOG.isDebugEnabled()) {
				LOG.debug("生成优惠券号码:" + couponCode.getCouponCode());
			}
			UserUser user = userUserProxy.getUserUserByPk(userId);
			if (null != user) {
				markCouponService.bindingUserAndCouponCode(user, couponCode.getCouponCode());
			}
		}
		order.setOrderStatus(Constant.ORDER_STATUS.FINISHED.name());
		shopOrderService.updata(order);
	}	
	
	/**
	 * 创建合作商家优惠券类产品
	 * @param userId 用户标识
	 * @param order 订单
	 * @param couponId 优惠券号
	 */
	private void bindingCoopCouponAndUser(final Long userId, final ShopOrder order, final Long productId) {
		if (null == userId || null == order || null == productId) {
			return;
		}
		StringBuffer couponInfo = new StringBuffer();
		List<String> infoList = shopCooperationCouponService.subtractStock(productId,order.getQuantity());
		for(int i=0;i<infoList.size();i++){
			couponInfo.append(infoList.get(i));
			if(i<infoList.size()-1){
				couponInfo.append("\r\n");
			}
		}
		if(infoList.size()!=order.getQuantity()){
			LOG.info("user "+userId+" get product_"+productId+" cooperate coupon quantity is bug quantity="+order.getQuantity()+" result="+infoList.size());
		}
		order.setProductInfo(couponInfo.toString());
		order.setOrderStatus(Constant.ORDER_STATUS.FINISHED.name());
		shopOrderService.updata(order);
		return;
	}	
	
	/**
	 * 发送激活绑定邮件的邮件
	 * @param user 用户信息
	 * @param code 激活绑定码
	 * @param subject 主题
	 */
	private void sendAuthenticationCodeByMail(final UserUser user,
			final String code, final String subject) {
		if (StringUtils.isBlank(subject)) {
			return;
		}
		
		HashMap<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("userName", user.getUserName());
		parameters.put("authenticationCode", code);
		parameters.put("userEmail", user.getEmail());
		parameters.put("type", subject);//邮件类型，验证邮件OR绑定邮件OR修改邮件OR。。。
		
		//发送邮件
		sendMail(user, subject, parameters);
		
		UserCertCode emailCode = new UserCertCode();
		emailCode.setIdentityTarget(user.getEmail());
		emailCode.setUserId(user.getId());
		emailCode.setType(USER_IDENTITY_TYPE.EMAIL.name());
		emailCode.setCode(code);
		userUserProxy.saveUserCertCode(emailCode);

	}
	/**
	 * 通过短信形式发送验证码
	 *
	 * @param mobile 手机号
	 * @param authenticationCode  验证码
	 * @param type 短信模板标识
	 * 
	 */
	private void sendAuthenticationCodeByMobile(final UserUser user,
			final String authenticationCode, final String type) {
		String mobile = user.getMobileNumber();
		if(StringUtils.isNotEmpty(mobile) && checkMobileSendFrequencyTooHigh(mobile)){
			LOG.error("send frequency too high: "+mobile);
			return;
		}
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("code", authenticationCode);
		String smsContent = comSmsTemplateRemoteService.getSmsContent(type,
				parameters);
		if (!StringUtil.isEmptyString(smsContent) && null != mobile) {
			try {
				smsRemoteService.sendSms(smsContent, mobile);
				LOG.info("发送手机" + mobile + "的短信内容为\"" + smsContent + "\"");
			} catch (Exception e) {
				LOG.error("发送手机" + mobile + "的短信内容为\"" + smsContent
						+ "\"失败.错误原因:" + e.getMessage());
			}
		} else {
			LOG.error("短信内容或者用户(手机)为空，无法发送验证码.");
		}
		
		UserCertCode mobileCode = new UserCertCode();
		mobileCode.setIdentityTarget(mobile);
		mobileCode.setUserId(user.getId());
		mobileCode.setType(USER_IDENTITY_TYPE.MOBILE.name());
		mobileCode.setCode(authenticationCode);
		userUserProxy.saveUserCertCode(mobileCode);
	}
	
	/**
	 * 手机发送频率检测（同一手机号发送频率不能太高）
	 * @param mobile
	 * @return
	 */
	private boolean checkMobileSendFrequencyTooHigh(String mobile){
		Map<String, Object> parameters = new HashMap<String, Object>();
		Date startDate =new Date(System.currentTimeMillis()/86400000*86400000-(23-Calendar.ZONE_OFFSET)*3600000);  
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, 1);
		SimpleDateFormat  format = new SimpleDateFormat("yyyy-MM-dd");
		parameters.put("startDate", format.format(startDate));
		parameters.put("endDate", format.format(calendar.getTime()));
		parameters.put("mobile", mobile);
		long count = smsRemoteService.count(parameters);
		if(count > 50 ){
			return true;
		}else{
			return false;
		}
	}


	/**
	 * 打印调试信息
	 * @param message
	 */
	private void debug(final String message) {
		if (!StringUtils.isEmpty(message) && LOG.isDebugEnabled()) {
			LOG.debug(message);
		}
	}

	public final void setComEmailTemplateService(
			ComEmailTemplateService comEmailTemplateService) {
		this.comEmailTemplateService = comEmailTemplateService;
	}

	/**
	 * 用户注册
	 * @param afterUserRegisterInfo
	 * @return
	 */
	public final UserUser register(final AfterUserRegisterInfo afterUserRegisterInfo) {
		if (null != afterUserRegisterInfo && null != afterUserRegisterInfo.getUser()) {
			UserUser u = this.userUserProxy.register(afterUserRegisterInfo.getUser());
			if (null != u && null != u.getMobileNumber() && afterUserRegisterInfo.isNeedSmsRemind()) {
				sendSms(u, afterUserRegisterInfo.isCustomedSmsContent(), afterUserRegisterInfo.getSmsTemplateId(), afterUserRegisterInfo.getCustomedSmsContent());
			}
			if (null != u && null != u.getEmail() && afterUserRegisterInfo.isNeedEmailRemind()) {
				sendMail(u, afterUserRegisterInfo.isCustomedEmailContent(), afterUserRegisterInfo.getEmailTemplateId(), afterUserRegisterInfo.getCustomedEmailContent());
			}

			synchBBS(u);
			
			if (null != u.getMemberShipCard()) {
				bindingUserAndMembershipCardCode(u.getId(), u.getMemberShipCard());
			}
			
			return u;
		} else {
			return null;
		}
	}
	
	/**
	 * 批量用户注册的远程接口
	 * @param mobileNumber 手机号
	 * @param email 电子邮箱
	 * @param realName 正式姓名
	 * @param gender 性别
	 * @param smsContent 注册成功的短信内容
	 * @param mailContent 注册成功的邮件内容
	 * @param cityId 城市标识
	 * @param channel 渠道标识
	 * @return 用户标识
	 * @throws Exception
	 */
	public Long batchRegisterWithChannel(String mobileNumber, String email,
			String realName, String gender, String smsContent,
			String mailContent, String cityId, String channel) throws Exception {
		UserUser users = null;
		if (StringUtils.isNotEmpty(mobileNumber)
				&& userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.MOBILE,mobileNumber)) {
			users = UserUserUtil.genDefaultUserByMobile(mobileNumber);			
			if (StringUtils.isNotEmpty(email)
					&& userUserProxy.isUserRegistrable(
							USER_IDENTITY_TYPE.EMAIL, email)) {
				users.setEmail(email);
			}
		} else {
			if (StringUtils.isNotEmpty(email)
					&& userUserProxy.isUserRegistrable(
							USER_IDENTITY_TYPE.EMAIL, email)) {
				users = UserUserUtil.genDefaultUser();
				users.setEmail(email);
			}
		}
		if (null == users) {
			return null;
		}
		if (StringUtils.isNotEmpty(realName)) {
			users.setRealName(realName);
		}
		if (StringUtils.isNotEmpty(gender)) {
			users.setGender(gender);
		}
		if (StringUtils.isNotEmpty(cityId)) {
			users.setCityId(cityId);
		}
		if (StringUtils.isNotEmpty(channel)) {
			users.setChannel(channel);
		}
		
		users.setGroupId(Constant.USER_CHANNEL.GP_TECH.name());

		
		users = register(AfterUserRegisterInfo.createAfterUserBatchRegisterInfo(users, smsContent, mailContent));

		if (null != users) {
			return users.getId();
		} else {
			return null;
		}
	}	
	
	/**
	 * 旅程马甲批量注册接口
	 * @param realName 用户名
	 * @param password 密码
	 * @param registerIp 注册ip
	 * @param channel  渠道标识
	 * @return  用户标识
	 * @throws Exception
	 */
	public Long batchRegisterWithChannel(String realName, String password,String registerIp, String channel) throws Exception {
		if (StringUtils.isNotEmpty(realName)&& userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.USER_NAME, realName)) {
			UserUser user = new UserUser();
			if (StringUtils.isNotEmpty(realName)) {
				user.setRealName(realName);
				user.setUserName(realName);
				user.setNickName(user.getUserName());
			}
			if (StringUtils.isNotEmpty(password)) {
				user.setRealPass(password);
				user.setUserPassword(UserUserUtil.encodePassword(user.getRealPass()));
			}
			if (StringUtils.isNotEmpty(registerIp)) {
				user.setRegisterIp(registerIp);
			}
			if (StringUtils.isNotEmpty(channel)) {
				user.setChannel(channel);
			}
			String userNo = new UUIDGenerator().generate().toString();
			user.setUserNo(userNo);
			user.setGroupId(Constant.USER_CHANNEL.GP_TECH.name());
			user.setIsEmailChecked("N");
			user.setIsMobileChecked("N");
			user.setIsLocked("N");
			user.setIsValid("Y");
			user.setCreatedDate(new Date());
			user.setPoint(0L);
			user.setUpdatedDate(new Date());
			user.setNameIsUpdate("N");//默认初次注册后的登录用户名可以修改一次
			user.setImageUrl("/uploads/header/default-photo.gif");
			user = register(AfterUserRegisterInfo.createAfterUserBatchRegisterInfo(user, "", ""));
			if (null != user) {
				return user.getId();
			} else {
				return null;
			}
	    }else{
	    	return null;
	    }
	}	
	
	/**
	 * 注册成功后发送的短信
	 * @param user 用户
	 * @param isCustomedSmsContent 是否已定义短信内容
	 * @param smsTemplateId 短信模板标识
	 * @param customedSmsContent 自定义短信内容
	 */
	private void sendSms(final UserUser user, final boolean isCustomedSmsContent, final SMS_SSO_TEMPLATE smsTemplateId, final String customedSmsContent) {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("username", user.getUserName());
			parameters.put("password", user.getRealPass());
			parameters.put("userId", user.getUserId());
			
			if (isCustomedSmsContent) {
				String smsContent = StringUtil.composeMessage(customedSmsContent, parameters);;
				debug("generate register sms:" + smsContent);
				smsRemoteService.sendSms(smsContent, user.getMobileNumber());
			} else {
				smsRemoteService.sendSms(comSmsTemplateRemoteService.getSmsContent(smsTemplateId.toString(), parameters), user.getMobileNumber());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 注册成功后发送的邮件
	 * @param user 用户
	 * @param isCustomedEmailContent 是否自定义邮件内容
	 * @param emailTemplateId 邮件模板标识
	 * @param customedEmailContent 自定义邮件内容
	 */
	private void sendMail(final UserUser user, final boolean isCustomedEmailContent, final EMAIL_SSO_TEMPLATE emailTemplateId, final String customedEmailContent) {
		try {
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("username", user.getUserName());
			parameters.put("password", user.getRealPass());
			parameters.put("userId", user.getUserId());
			
			/**
			 * @ TODO
			 */
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * 和论坛同步
	 * @param user
	 */
	private void synchBBS(final UserUser user) {
		try {
			StringBuffer sb = new StringBuffer("http://bbs.lvmama.com/api/sync.php?action=update");
			sb.append("&username=").append(URLEncoder.encode(user.getUserName(), "utf-8"))
				.append("&password=").append(user.getRealPass()).append("&user_id=").append(user.getUserId())
				.append("&ip=").append("");
			if (LOG.isDebugEnabled()) {
				LOG.debug("submit data：" + sb.toString());
			}
//			HttpClient httpClient = new HttpClient();
//			httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
//			httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);
//			GetMethod getMethod = new GetMethod(sb.toString());
//			httpClient.executeMethod(getMethod);
			HttpsUtil.requestGet(sb.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 客户端发送注册成功短信 - 世界杯活动
	 * @param mobile 手机号
	 * @param key    模板id
	 * @param luckyCode  幸运码 
	 * @return
	 */
	public boolean sendRegSuccessCode4FiFa(final String key,final String mobile, final String luckyCode) {
		boolean b = false;
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("code", luckyCode);
		String smsContent = comSmsTemplateRemoteService.getSmsContent(key,parameters);
		if (!StringUtil.isEmptyString(smsContent) && null != mobile) {
			try {
				smsRemoteService.sendSms(smsContent, mobile);
				LOG.info("发送手机" + mobile + "的短信内容为\"" + smsContent + "\"");
				b = true;
			} catch (Exception e) {
				LOG.error("发送手机" + mobile + "的短信内容为\"" + smsContent
						+ "\"失败.错误原因:" + e.getMessage());
			}
		} else {
			LOG.error("短信内容或者用户(手机)为空，无法发送2014世界杯注册成功短信.");
		}
		return b;
	}
	
	
	public final void setEmailClient(EmailClient emailClient) {
		this.emailClient = emailClient;
	}

	public final void setSmsRemoteService(SmsRemoteService smsRemoteService) {
		this.smsRemoteService = smsRemoteService;
	}

	public final void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setComSmsTemplateRemoteService(
			ComSmsTemplateService comSmsTemplateRemoteService) {
		this.comSmsTemplateRemoteService = comSmsTemplateRemoteService;
	}

	public void setMarkMembershipCardDiscountService(
			MarkMembershipCardDiscountService markMembershipCardDiscountService) {
		this.markMembershipCardDiscountService = markMembershipCardDiscountService;
	}

	public void setShopOrderService(ShopOrderService shopOrderService) {
		this.shopOrderService = shopOrderService;
	}

	public void setShopProductService(ShopProductService shopProductService) {
		this.shopProductService = shopProductService;
	}

	public void setShopUserService(ShopUserService shopUserService) {
		this.shopUserService = shopUserService;
	}

	public void setShopCooperationCouponService(
			ShopCooperationCouponService shopCooperationCouponService) {
		this.shopCooperationCouponService = shopCooperationCouponService;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public void setShopProductZhuantiService(
			ShopProductZhuantiService shopProductZhuantiService) {
		this.shopProductZhuantiService = shopProductZhuantiService;
	}
	
}
