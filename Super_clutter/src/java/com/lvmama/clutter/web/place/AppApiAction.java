package com.lvmama.clutter.web.place;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.util.StringUtils;

import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.comm.pet.client.UserClient;
import com.lvmama.comm.pet.po.client.ClientGroupon2;
import com.lvmama.comm.pet.po.client.ClientPlace;
import com.lvmama.comm.pet.po.client.ClientProduct;
import com.lvmama.comm.pet.po.client.ClientTimePrice;
import com.lvmama.comm.pet.po.client.ClientUser;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.money.CashAccount;
import com.lvmama.comm.pet.po.user.UserCooperationUser;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.service.user.UserActionCollectionService;
import com.lvmama.comm.pet.service.user.UserCooperationUserService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.ClientConstants;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UserLevelUtils;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.comment.DicCommentLatitudeVO;

/**
 * 普通客户端api 无需用户登陆
 * @author dengcheng
 *
 */
public class AppApiAction extends AppBaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String keyword;

	
	
	
	/**
	 * UserClient
	 */
	private UserClient userClient;
	
	private MarkCouponService markCouponService;
	
	
	/**
	 * 第三方用户服务
	 */
	private UserCooperationUserService userCooperationUserService;
	
	/**
	 * 获得place 信息
	 */
	@Action("/client/api/v2/getPlaceDetails")
	public void getPlaceDetails(){
		Map<String,Object> resultMap = super.resultMapCreator();
		requiredArgList.add(placeId);
		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",false);
			return;
		}
		
		ClientPlace cp = null;

		try {
		  cp = super.appService.getPlaceDetails(placeId);
		
		}catch(Exception ex){
			ex.printStackTrace();
			this.setExceptionResult(resultMap);
		} finally{
			this.sendResult(resultMap, cp,false);
		}
	}
	
	@Action("/client/api/v2/getMainProdTimePrice")
	public void getMainProdTimePrice () {
		
		Map<String,Object> resultMap = super.resultMapCreator();
		requiredArgList.add(productId);

		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",true);
			return;
		}
		List<ClientTimePrice> list = null;
		try {
			list = appService.getMainProdTimePrice(productId, branchId);
			if(list.isEmpty()){
				this.putErrorMessage(resultMap, "产品库存不足");
			}
		}catch(Exception ex){
			ex.printStackTrace();
			this.setExceptionResult(resultMap);
		} finally{
			this.sendResult(resultMap, list,true);
		}
	}
	
	@Action("/client/api/v2/queryGroupProductList")
	public void queryGroupProductList () {
		Map<String,Object> resultMap = super.resultMapCreator();
		requiredArgList.add(placeId);
		requiredArgList.add(stage);
		
		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",false);
			return;
		}
		Map<String,Object> map = null;
		try {
			map = appService.queryGroupProductList(placeId, stage);
			
		}catch(Exception ex){
			ex.printStackTrace();
			this.setExceptionResult(resultMap);
		} finally{
			this.sendResult(resultMap, map,false);
		}
	}
	
	@Action("/client/api/v2/queryGroupOnProductListForSh")
	public void queryGroupOnProductListForSh () {
		Map<String,Object> resultMap = super.resultMapCreator();
		
		List<ClientProduct> list= null;
		try {
			list = appService.queryGroupOnListForSh();
		}catch(Exception ex){
			ex.printStackTrace();
			this.setExceptionResult(resultMap);
		} finally{
			this.sendResult(resultMap, list,true);
		}
	}
	
	@Action("/client/api/v2/getProductList")
	public void getProductList(){
		Map<String,Object> resultMap = super.resultMapCreator();
		requiredArgList.add(placeId);
		requiredArgList.add(stage);
		
		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",true);
			return;
		}
		List<?>  list = null;
		try {
			list = appService.getProductList(placeId, stage);
		}catch(Exception ex){
			ex.printStackTrace();
			this.setExceptionResult(resultMap);
		} finally{
			this.sendResult(resultMap, list,true);
		}
	}
	
	@Action("/client/api/v2/getProductDetails")
	public void getProductDetails(){
		Map<String,Object> resultMap = super.resultMapCreator();
		requiredArgList.add(productId);
		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",false);
			return;
		}
		ClientProduct cp  = null;
		try {
		 cp  = appService.getProductDetails(productId);
	
		} catch(Exception ex){
				ex.printStackTrace();
				this.setExceptionResult(resultMap);
		} finally{
				this.sendResult(resultMap, cp,false);
		}
	}
	
	@Action("/client/api/v2/getNameByLocation")
	public void  getNameByLocation () {
		Map<String,Object> resultMap = super.resultMapCreator();
		requiredArgList.add(keyword);
		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",false);
			return;
		}
		ClientPlace cp  = null;
		try {
		 cp = appService.getPlaceByName(keyword);
		} catch(Exception ex){
			ex.printStackTrace();
			this.setExceptionResult(resultMap);
	} finally{
			this.sendResult(resultMap, cp,false);
	}
	}
	
	@Action("/client/api/v2/queryCommentListByPlaceId")
	public void queryCommentListByPlaceId () {
		
		Map<String,Object> resultMap = super.resultMapCreator();
		requiredArgList.add(placeId);
		requiredArgList.add(page);

		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",true);
			return;
		}
//		UserUser user = this.getUser();

//		
//			
//		if(userId ==null){
//				userId = user.getUserId();
//		}
		List<?> list = null;
		try {
		Map<String,Object> map = appService.queryPlaceComments(userId, placeId, page);
		list = (List<?>)map.get("list");
		resultMap.put("isLastPage", map.get("isLastPage"));
		} catch(Exception ex){
			ex.printStackTrace();
			this.setExceptionResult(resultMap);
		} finally{
			this.sendResult(resultMap, list,true);
		}
		
	}
	
	@Action("/client/api/v2/userShareLog")
	public void userShareLog () {
		Map<String,Object> resultMap = super.resultMapCreator();
		requiredArgList.add(uid);
		requiredArgList.add(screenName);
		requiredArgList.add(shareChannel);
		requiredArgList.add(shareTarget);
		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",false);
			return;
		}
		try {
			appService.insertShareLog(uid, screenName, shareChannel, shareTarget);
		} catch(Exception ex){
			ex.printStackTrace();
			this.setExceptionResult(resultMap);
		} finally{
			this.sendResult(resultMap, null,false);
		}
	}
	
	
	@Action("/client/api/v2/validateCode")
	public void validateCode(){
		Map<String,Object> resultMap = super.resultMapCreator();
		requiredArgList.add(mobile);
		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",false);
			return;
		}
		boolean b = StringUtil.validMobileNumber(mobile);
		String message="";
			if (!b) {
			message = "mobileValidateError";
			} else {
				try {
					boolean flag = userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.MOBILE,mobile);
						if (flag) {
							UserUser userUser = new UserUser();
							userUser.setMobileNumber(mobile);
							String code = userClient.sendAuthenticationCode(UserUserProxy.USER_IDENTITY_TYPE.MOBILE, userUser, com.lvmama.comm.vo.Constant.SMS_SSO_TEMPLATE.SMS_REGIST_AUTHENTICATION_CODE.name());
							if (code != null) {
								message = "";
							} else {
								message = "error";
							}

				} else {
					message = "mobile_in_users";
				}
			} catch (Exception e) {
				this.setExceptionResult(resultMap);
				e.printStackTrace();
			} finally{
				if(!StringUtil.isEmptyString(message)) {
					this.sendResult(this.putErrorMessage(resultMap, ClientConstants.getErrorInfo().get(message)), "",false);
				} else {
					this.sendResult(resultMap, "",false);
				}
			}
			
		}
	}
	
	
	@Action("/client/api/v2/reg")
	public void subRegist(){
		Map<String,Object> resultMap = super.resultMapCreator();
		requiredArgList.add(mobile);
		requiredArgList.add(validateCode);
		requiredArgList.add(firstChannel);
		requiredArgList.add(password);
		
		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",false);
			return;
		}
		
		String message = "";
		ClientUser cu = new ClientUser();
		UserUser user = null;
		try {
		boolean b = validateAuthenticationCode(mobile, StringUtils.trimAllWhitespace(validateCode), firstChannel);
		boolean isUserRegistrable=false;
		isUserRegistrable = userUserProxy.isUserRegistrable(UserUserProxy.USER_IDENTITY_TYPE.MOBILE, mobile);
		log.error("b: " + b + ", isUserRegistrable: " + isUserRegistrable);
		
		if (b&&isUserRegistrable) {
			user = UserUserUtil.genDefaultUserByMobile(mobile);
		    user.setRealPass(password);
		    user.setGroupId(firstChannel);
		    user.setChannel(firstChannel);
		    try {
				user.setUserPassword(UserUserUtil.encodePassword(user.getRealPass()));
			} catch (NoSuchAlgorithmException e1) {
				log.error(this.getClass(),e1);
			}
		    try {
	    	user.setRegisterIp(InternetProtocol.getRemoteAddr(getRequest()));
			user.setRegisterPort(InternetProtocol.getRemotePort(getRequest()));
		     user = this.userUserProxy.register(user);
		    } catch (Exception e) {
		    	e.printStackTrace();
		    }
		    cu.setPoint(100L);
			if (user != null) {
				super.getUserByMap(user, cu);
				String couponCode = this.subCouponCodeByRegister(cu.getUserId());
				userUserProxy.addUserPoint(user.getId(), "POINT_FOR_NORMAL_REGISTER", null, "客户端注册");
				if(isIos()){
					if (couponCode != null) {
						cu.setCouponInfo("8");
					} 
				}
				
				
				message = "";
			} else {
				message = "mobileValidateError";
			}

		} else {
			if(!isUserRegistrable){
				message = "mobileIsUsed";
			} else {
				message = "validateCodeError";
			}
		}
		}catch (Exception e) {
			this.setExceptionResult(resultMap);
			e.printStackTrace();
		} finally{
			if(!StringUtil.isEmptyString(message)) {
				this.sendResult(this.putErrorMessage(resultMap, ClientConstants.getErrorInfo().get(message)), "",false);
			} else {
				this.regClientBugServerFixModel(cu, resultMap);
			}
		}

	}
	
	private void regClientBugServerFixModel (ClientUser cu ,Map<String,Object> resultMap){
		Long version = 0L;
		if (!StringUtil.isEmptyString(lvversion)) {
			 version =  Long.valueOf(lvversion.replace(".", ""));
		}
		
		if(!StringUtil.isEmptyString(cu.getCouponInfo())){
			if(!isIos()){
				cu.setCouponInfo("1。获得优惠券,价值8元 \n 2.获得100积分");
			} else if(isIos()&&version>=261L){
				cu.setCouponInfo("1。获得优惠券,价值8元 \n 2.获得100积分");
			}
		} else {
			if(!isIos()){
				cu.setCouponInfo("1.获得100积分");
			}  else if(isIos()&&version>=261L){
				cu.setCouponInfo("1.获得100积分");
			}
			
		}
		
		this.sendResult(resultMap, cu,false);
	}
	
	private UserUser registByCooperationInfo(UserUser users) {
		UserUser user = UserUserUtil.genDefaultUser();
		user.setChannel(firstChannel);
		if(isAndroid()){
			try {
				screenName = new String(screenName.getBytes("iso-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		user.setUserName("From "+loginChannel.toLowerCase()+" weibo " + (null !=screenName ? screenName : "") + "("
				+ uid + ")");
		UserCooperationUser cu = new UserCooperationUser();
		cu.setCooperation(loginChannel);
		cu.setCooperationUserAccount(uid);
		userUserProxy.registerUserCooperationUser(user, cu);
		users = userUserProxy.getUserUserByUserNo(user.getUserNo());
		return users;
	}
	
	private List<UserCooperationUser> getCooperationUserByChannelUID (String channel,String uid) {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("cooperationUserAccount", uid);
		parameters.put("cooperation", channel);
		List<UserCooperationUser> cooperationUseres = userCooperationUserService.getCooperationUsers(parameters);
		return cooperationUseres;
	}
	
	private UserUser registByUser(UserUser users) {
		UserUser user = UserUserUtil.genDefaultUser();
		user.setChannel(firstChannel);
		if(isAndroid()){
			try {
				screenName = new String(screenName.getBytes("iso-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		user.setUserName("From "+loginChannel.toLowerCase()+" weibo " + (null != screenName ? screenName : "") + "("
				+ uid + ")");		
		user.setRegisterIp(InternetProtocol.getRemoteAddr(getRequest()));
		user.setRegisterPort(InternetProtocol.getRemotePort(getRequest()));
		userUserProxy.register(user);
		users = userUserProxy.getUserUserByUserNo(user.getUserNo());
		return users;
	}
	
	 
	private ClientUser loginByCooperationChanel(){
		try {
		if ("SINA".equals(loginChannel)||"TENCENT".equals(loginChannel)) {
			UserUser users = null;
			List<UserCooperationUser> cooperationUseres = this.getCooperationUserByChannelUID(loginChannel,uid);
			if (null == cooperationUseres || cooperationUseres.isEmpty()) {
				users = this.registByCooperationInfo(users);
			} else {
				UserCooperationUser ucu = cooperationUseres.get(0);
				users = userUserProxy.getUserUserByPk(ucu.getUserId());
				if("N".equals(users.getIsValid())) {
		
					users = this.registByUser(users);
					ucu.setUserId(users.getId());
					userCooperationUserService.update(ucu);
				}
			}
			
			ClientUser cu = new ClientUser();
			
			cu.setUserId(users.getUserId());
			cu.setEmail(users.getEmail());
			cu.setImageUrl(Constant.PIC_HOST + users.getImageUrl());
			cu.setMobileNumber(StringUtil.trimNullValue(users.getMobileNumber()));
			cu.setNickName(StringUtil.trimNullValue(users.getNickName()));
			cu.setRealName(StringUtil.trimNullValue(users.getRealName()));
			cu.setUserName(StringUtil.trimNullValue(users.getUserName()));
			cu.setPoint(users.getPoint());
			cu.setLastLoginTime(DateUtil.getFormatDate(users.getLastLoginDate(), "yyyy-MM-dd HH:mm:ss"));
			Long point = 0L;
			if (users.getPoint() != null) {
				point = users.getPoint();
			}
			
			this.checkUserLoginDate(users, cu);
			cu.setLevel(UserLevelUtils.getLevel(point));
			
			cu.setWithdraw(users.getWithdraw() == null ? "0元" : PriceUtil
					.convertToYuan(users.getWithdraw())
					+ "元");
			
	
			CashAccount ba =  cashAccountService.queryCashAccountByUserId(users.getId());
			if(ba!=null) {
				cu.setAwardBalance(ba.getBonusBalanceFloat()+"元");
			} else {
				cu.setAwardBalance("0元");
			}
			
			cu.setCashBalance(ba.getTotalCashBalanceYuan() + "元");
			return cu;
			
		}
		} catch(Exception ex){
			ex.printStackTrace();
			return null;
		}
		return null;
	}
	
	
	private void checkUserLoginDate (UserUser user,ClientUser cu) {
		Date lastLoginDate = user.getLastLoginDate();
		Date currentDate = new Date();
		if (lastLoginDate!=null&&com.lvmama.comm.utils.DateUtil.compareDateLessOneDayMore(currentDate, lastLoginDate)) {
			user.setLastLoginDate(new Date());
			userUserProxy.update(user);//设置上次登录时间
			UserActionCollectionService userActionCollectionService = (UserActionCollectionService) SpringBeanProxy.getBean("userActionCollectionService");
			if (null != userActionCollectionService) {
				userActionCollectionService.save(user.getId(), InternetProtocol.getRemoteAddr(getRequest()),InternetProtocol.getRemotePort(getRequest()),  "LOGIN", null);
			}
			userUserProxy.addUserPoint(user.getId(), "POINT_FOR_LOGIN", null, "客户端登陆");
//			SSOMessageProducer producer = (SSOMessageProducer)SpringBeanProxy.getBean("ssoMessageProducer");
//			producer.sendMsg(new SSOMessage(SSO_EVENT.LOGIN, SSO_SUB_EVENT.NORMAL, user.getId()));
			cu.setPoint(5L);
		} else {
			cu.setPoint(0L);
		}
	}

	
	@Action("/client/api/v2/login")
	public void login(){
		Map<String,Object> resultMap = super.resultMapCreator();
		ClientUser cu = new ClientUser();
		UserUser user = null;
		try {
		if (!StringUtil.isEmptyString(loginChannel)) {
				requiredArgList.add(uid);
				requiredArgList.add(screenName);
				this.checkArgs(requiredArgList, resultMap);
				if(!resultMap.get("code").equals("1")){
					super.sendResult(resultMap, "",false);
					return;
				}	
			   cu = this.loginByCooperationChanel();
		}  else {
			requiredArgList.add(userName);
			requiredArgList.add(password);
			this.checkArgs(requiredArgList, resultMap);
			
			if(!resultMap.get("code").equals("1")){
				super.sendResult(resultMap, "",false);
				return;
			}
			user = userUserProxy.login(StringUtils.trimAllWhitespace(userName), StringUtils.trimAllWhitespace(password));
			if (user != null) {
				this.getUserByMap(user, cu);
			} else {
				this.putErrorMessage(resultMap, "用户名或者密码错误");
			}
		}
		} catch (Exception ex){
			this.setExceptionResult(resultMap);
			ex.printStackTrace();
		} finally{
			if(cu!=null){
				//检测是否是今天第一次 登陆并添加积分
				if (user != null) {
					this.checkUserLoginDate(user, cu);
				}
				this.sendResult(resultMap, cu,false);
			}
		}
	}
	
	@Action("/client/api/v2/proxyLogin")
	public void proxySsoLogin(){
		Map<String,Object> resultMap = super.resultMapCreator();
		requiredArgList.add(userName);
		requiredArgList.add(password);
		requiredArgList.add(lvSessionId);
		requiredArgList.add(udid);
		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",false);
			return;
		}
		String jsons = "{}";
		try {
			//jsons = HttpsUtil.requestGet(ClutterConstant.getLoginURL()+"?mobileOrEMail="+userName+"&password="+password+"&"+Constant.LV_SESSION_ID+"="+lvSessionId);
			getRequest().setAttribute(Constant.LV_SESSION_ID, lvSessionId);
			this.getResponse().sendRedirect(ClutterConstant.getLoginURL()+"?mobileOrEMail="+userName+"&password="+password+"&"+Constant.LV_SESSION_ID+"="+lvSessionId);
		} catch (Exception ex){
			
			ex.printStackTrace();
		} finally{
			
		}
		
	}
	

	@Action("/client/api/v2/changeDest")
	public void changeDest() {
		String url  = ClutterConstant.getSearchHost()+"/search/clientSearch!getChinaTreeByHasProduct.do?fromPage=isClient&productType=hasTicket";
		try {
			this.getResponse().sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Action("/client/api/v2/hotelCities")
	public void hotelCities () {
		String url = ClutterConstant.getSearchHost()+"/search/clientSearch!getCityListByHasProduct.do?"+this.getRequest().getQueryString();
		try {
			this.getResponse().sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	@Action("/client/api/v2/getAutoCompleteHotelSpot")
	public void getAutoCompleteHotelSpot(){
		String url = ClutterConstant.getSearchHost()+"/search/clientSearch!getAutoCompleteHotelSpot.do?"+this.getRequest().getQueryString();
		try {
			this.getResponse().sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Action("/client/api/v2/getAutoCompletePlace")
	public void  getAutoCompletePlace(){
		String url = ClutterConstant.getSearchHost()+"/search/clientSearch!getAutoCompletePlace.do?"+this.getRequest().getQueryString();
		 try {
				this.getResponse().sendRedirect(url);
			} catch (IOException e) {
				e.printStackTrace();
			}
	}
	
	@Action("/client/api/v2/getClientSubjectByCity")
	public void  getClientSubjectByCity(){
		String url = ClutterConstant.getSearchHost()+"/search/clientSearch!getClientSubjectByCity.do?"+this.getRequest().getQueryString();
		try {
			this.getResponse().sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Action("/client/api/v2/getAutoCompleteHotelCity")
	public void getAutoCompleteHotelCity () {
		String url = ClutterConstant.getSearchHost()+"/search/clientSearch!getAutoCompleteHotelCity.do?"+this.getRequest().getQueryString();
		try {
			this.getResponse().sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Action("/client/api/v2/routeSearch")
	public void routeSearch(){
		String url = ClutterConstant.getSearchHost()+"/search/clientRouteSearch!routeSearch.do?"+this.getRequest().getQueryString();
		try {
			this.getResponse().sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Action("/client/api/v2/getRouteSubject")
	public void  getRouteSubject(){
		String url = ClutterConstant.getSearchHost()+"/search/clientRouteSearch!getRouteSubject.do?"+this.getRequest().getQueryString();
		try {
			this.getResponse().sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Action("/client/api/v2/nearSearch")
	public void nearSearch(){
		String url = ClutterConstant.getSearchHost()+"/search/clientSearch!nearSearch.do?"+this.getRequest().getQueryString();
		try {
			this.getResponse().sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Action("/client/api/v2/placeSearch")
	public void placeSearch(){
		String url = ClutterConstant.getSearchHost()+"/search/clientSearch!placeSearch.do?"+this.getRequest().getQueryString();
		try {
			this.getResponse().sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Action("/client/api/v2/getAutoComplete")
	public void getAutoComplete(){
		String url = ClutterConstant.getSearchHost()+"/search/clientRouteSearch!getAutoComplete.do?"+this.getRequest().getQueryString();
		try {
			this.getResponse().sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	@Action("/client/api/v2/queryGrouponList")
	public void queryGrouponList() {
		Map<String,Object> resultMap = super.resultMapCreator();
		List<ClientGroupon2> list = null;
		try {
		   list = appService.queryGrouponList();
		} catch (Exception ex) {
			ex.printStackTrace();
			this.setExceptionResult(resultMap);
		} finally {
			this.sendResult(resultMap, list,true);
		}
	}
	
	@Action("/client/api/v2/getCommentLatitudeInfos")
	public void getCommentLatitudeInfos () {
		Map<String,Object> resultMap = super.resultMapCreator();
		if(StringUtil.isEmptyString(orderId)){
			requiredArgList.add(placeId);
		}
	
		if(placeId==null){
			requiredArgList.add(orderId);
		}

		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",true);
			return;
		}
		List<DicCommentLatitudeVO> list = null;
		try {
		   list = appService.getCommentLatitudeInfos(placeId, orderId);
		} catch (Exception ex) {
			ex.printStackTrace();
			this.setExceptionResult(resultMap);
		} finally {
			this.sendResult(resultMap, list,true);
		}
	}
	
	//重发短信凭证信息
			
	
	private String subCouponCodeByRegister(String userId){
		UserUser user = userUserProxy.getUserUserByUserNo(userId);
		String couponId = ClutterConstant.getClientRegisterCouponId();
		MarkCoupon mc = markCouponService.selectMarkCouponByPk(Long.valueOf(couponId));
		if(!mc.isOverDue()) {
		MarkCouponCode markCouponCode = markCouponService.generateSingleMarkCouponCodeByCouponId(Long.valueOf(couponId));
		markCouponService.bindingUserAndCouponCode(user, markCouponCode.getCouponCode());
		return markCouponCode.getCouponCode();
		}
		return null;
	}

	@Action("/client/api/v2/feedback")
	public void commitFeedBack () {
		Map<String,Object> resultMap = super.resultMapCreator();
		
		/**
		 * 处理android 乱码
		 */
		if(isAndroid()){
			try {
				content = new String(content.getBytes("iso-8859-1"), "utf-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		requiredArgList.add(content);
		requiredArgList.add(email);
		requiredArgList.add(firstChannel);
		this.checkArgs(requiredArgList, resultMap);
		
		if(!resultMap.get("code").equals("1")){
			super.sendResult(resultMap, "",false);
			return;
		}
		try {
			appService.addfeedBack(content, email, userId, firstChannel);
		}	catch(Exception ex){
			ex.printStackTrace();
			this.setExceptionResult(resultMap);
		} finally {
			super.sendResult(resultMap, "",false);
		}
	}
	
	private boolean validateAuthenticationCode(String mobile, String code, String channel){
		 boolean flag = userUserProxy.validateAuthenticationCode(UserUserProxy.USER_IDENTITY_TYPE.MOBILE, code, mobile);
		  if(flag){
		    return true;
		  }
		  return false;
	}
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public void setUserClient(UserClient userClient) {
		this.userClient = userClient;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	public void setUserCooperationUserService(
			UserCooperationUserService userCooperationUserService) {
		this.userCooperationUserService = userCooperationUserService;
	}


}
