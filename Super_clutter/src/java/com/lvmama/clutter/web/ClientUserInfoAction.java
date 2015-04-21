package com.lvmama.clutter.web;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.utils.DateUtil;
import com.lvmama.clutter.web.place.AppBaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.pet.po.mobile.MobilePersistanceLog;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.mobile.MobileClientService;
import com.lvmama.comm.pet.service.user.UserActionCollectionService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.InternetProtocol;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UUIDGenerator;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.comm.vo.Constant;

public class ClientUserInfoAction extends AppBaseAction {
	private static final Log log = LogFactory.getLog(ClientUserInfoAction.class);
	/**
	 * 
	 */
	private static final long serialVersionUID = -3429984523385020004L;

	private static final String LT_MOBILE_AUTH_WORLD = "lvtu_mobile_auth_send_authcode";
	
	/**
	 * 产品服务
	 */
	protected UserUserProxy userUserProxy;
	private MobileClientService mobileClientService;
	private String sign;
	
	/**
	 * 上传的文件 
	 */
	private File  file;

	
	/**
	 *  上传的文件名称 .
	 */
	private String  fileName;
	
	/**
	 * 更新用户头像 
	 */
	@Action("/client/updateUserImage")
	public void updateUserImg() {
		Map<String, Object> resultMap = super.resultMapCreator();
		Map<String, Object> map = new HashMap<String, Object>();
		/**
    	 * 如果非法请求 
    	 */
    	if(!isLegalRequest()) {
    		this.putErrorMessage(resultMap, "非法访问");
			this.sendResult(resultMap, null, false);
			return ;
    	}
    	
		try{
			if(null != this.getFile()){
				log.info("....fileName=="+fileName);
				String t_fileName = new UUIDGenerator().generate().toString() + getSuffixName(fileName);
					
				/*if(UploadCtrl.checedImgWidthAndHeight(file[i], 180, 180)) {
					String msg = "图片尺寸最大为180*180";
				}
				
				// 1M
				if(UploadCtrl.checkImgSize(file[i], 1024)){
					
				}*/
					
				String imageUrl = postToRemote(this.getFile(), t_fileName);
				map.put("imgUrl", imageUrl);
				map.put("debugImgUrl", "http://192.168.0.245/pics" + imageUrl);
				
				// 更新用户信息 
				UserUser user = this.getUser();
		        if(null == user) {
		        	this.putErrorMessage(resultMap, "该用户还未登陆");
		        } else {
		        	if (!"null".equalsIgnoreCase(imageUrl) && StringUtils.isNotBlank(imageUrl)) {
		        		user.setImageUrl(imageUrl);
		        		userUserProxy.update(user);
		        		putSession(Constant.SESSION_FRONT_USER, user);
		        	}
		        }
			} else {
				log.info("....updateUserImage..the img is null...");
			}
		}catch(Exception e){
			e.printStackTrace();
			map.put("imageUrl", "");
			this.putErrorMessage(resultMap, "上传图片出错");
		}
		
		this.sendResult(resultMap,map, false);
		
	}
	
	
	/**
	 * 修改用户基本信息 . 
	 */
	@Action("/client/updateUserBaseInfo")
	public void updateUserBaseInfo() {
		Map<String, Object> resultMap = super.resultMapCreator();
    	String gender = this.getRequest().getParameter("gender");
    	String realName = this.getRequest().getParameter("realName");
    	
    	/**
    	 * 如果非法请求 
    	 */
    	if(!isLegalRequest()) {
    		this.putErrorMessage(resultMap, "非法访问");
			this.sendResult(resultMap, null, false);
			return ;
    	}
		
    	UserUser user = this.getUser();
        if(null == user) {
        	this.putErrorMessage(resultMap, "该用户还未登陆");
        } else {
        	// 性别 
        	if (!"null".equalsIgnoreCase(gender) && StringUtils.isNotBlank(gender) &&  ("M".equals(gender) || "F".equals(gender))) {
        		user.setGender(gender);
        	}
        	// 真实姓名 
        	if (!"null".equalsIgnoreCase(realName) && StringUtils.isNotBlank(realName)) {
        		user.setRealName(realName);
        	}
        	
        	userUserProxy.update(user);
        	putSession(Constant.SESSION_FRONT_USER, user);
        }
        this.sendResult(resultMap, null, false);
	}
	
	/**
	 * 更加设备号判断是否首次登录 . 
	 */
	@Action("/client/firstLogIn")
	public void firstLogIn() {
		Map<String, Object> resultMap = super.resultMapCreator();
		Map<String,Object> map = new HashMap<String,Object>();
    	String deviceId = this.getRequest().getParameter("udid");
    	String lvSessionId = this.getRequest().getParameter(Constant.LV_SESSION_ID);
    	// 如果是登录状态的话 ；则valida false ;code -1
    	if(!StringUtils.isEmpty(lvSessionId)) {
    		map.put("valid", false);
    		this.putErrorMessage(resultMap, "已经登录 ");
    		this.sendResult(resultMap, null, false);
    		return;
    	}
    	
    	if(StringUtils.isEmpty(deviceId)) {
    		map.put("valid", false);
    		this.putErrorMessage(resultMap, "无效的设备号 ");
    		this.sendResult(resultMap, null, false);
    		return;
    	}
    	// 活动截止日期 。 
    	if(!(System.currentTimeMillis() < DateUtil.getDateTime(ClutterConstant.getFirstLogin4V4EndDate()))) {
    		map.put("valid", false);
    		this.putErrorMessage(resultMap, "活动已结束 ");
    		this.sendResult(resultMap, null, false);
    		return;
    	}
    	
    	// 判断是否首次登录
    	List<MobilePersistanceLog> mobliePersistanceLogs  = mobileClientService.selectListByDeviceIdAndObjectType(deviceId,ClutterConstant.FIRST_LOG_IN4V5);
        if(null != mobliePersistanceLogs && mobliePersistanceLogs.size() > 0) {
        	map.put("valid", true);
        	this.putErrorMessage(resultMap, "已经领取过了 ");
        } else {
        	map.put("valid", false);
        	
        	String price = this.getSendCouponsPrice();
        	map.put("content", "注册登录立即送"+price+"元优惠券\n同一手机仅此一次机会哦");
        	map.put("price", "¥"+price);
        	map.put("button", "登录领取");
        	map.put("title", "点击领取#"+price+"元#优惠券");
        }
        
        this.sendResult(resultMap, map, false);
	}
	
	/**
	 * 首发渠道赠送1080元优惠券 
	 * @return
	 */
	public String getSendCouponsPrice() {
		String firstChannel = this.getRequest().getParameter("firstChannel");
		String secondChannel = this.getRequest().getParameter("secondChannel");
		String channel = firstChannel + "_" + secondChannel;
		// 首发渠道多送200元优惠券 
		if (ClientUtils.isExtChannel(channel)) {
			return "1080";
		} else  {
			return "880";
		}
	}
	/**
	 * 修改密码 . 
	 */
	@Action("/client/updateUserPassWorld")
	public void updateUserPassWorld() {
		Map<String, Object> resultMap = super.resultMapCreator();
    	String orgPassword = this.getRequest().getParameter("orgPassword");
    	String newPassword = this.getRequest().getParameter("newPassword");
    	
    	/**
    	 * 如果非法请求 
    	 */
    	if(!isLegalRequest()) {
    		this.putErrorMessage(resultMap, "非法访问");
			this.sendResult(resultMap, null, false);
			return ;
    	}
    	
    	// 密码校验 
    	if(!ClientUtils.validateStrByLength(newPassword,6,16)) {
    		this.putErrorMessage(resultMap, "密码在6-16个字符内");
    		this.sendResult(resultMap, null, false);
    		return ;
    	}
    	
    	UserUser user = this.getUser();
        if(null == user) {
        	this.putErrorMessage(resultMap, "该用户还未登陆");
        } else {
        	try {
				String md5Password = UserUserUtil.encodePassword(orgPassword);
				if (user.getUserPassword().equals(md5Password)) {
					// 新密码不能与原密码一致，请重试
					if(orgPassword.equals(newPassword)) {
						this.putErrorMessage(resultMap, "新密码不能与原密码一致，请重试");
			    		this.sendResult(resultMap, null, false);
			    		return ;
					}
					
					user.setRealPass(newPassword);
					user.setUserPassword(UserUserUtil.encodePassword(newPassword));
					userUserProxy.update(user);
					putSession(Constant.SESSION_FRONT_USER, user);
					// 记录用户修改用户信息的行为（修改密码/用户名/手机号/EMAIL）
					collectModifyUserInfoAction(user,"modifyPassword", orgPassword+"->"+newPassword); 
					syncBBS(user); // 同步bbs 
				} else {
					this.putErrorMessage(resultMap, "原密码输入错误，请重试");
				}			
			} catch (Exception e) {
				e.printStackTrace();
				this.putErrorMessage(resultMap, "修改密码失败");
			}
        }
        this.sendResult(resultMap, null, false);
	}
	
	/**
	 * 修改用户名 . 
	 */
	@Action("/client/submitModifyUserName")
	public void submitModifyUserName() {
		Map<String, Object> resultMap = super.resultMapCreator();
    	String newUserName = this.getRequest().getParameter("newUserName");
		
    	/**
    	 * 如果非法请求 
    	 */
    	if(!isLegalRequest()) {
    		this.putErrorMessage(resultMap, "非法访问");
			this.sendResult(resultMap, null, false);
			return ;
    	}
    	
		UserUser user = getUser();
		if (null == user || StringUtils.isBlank(newUserName)) {
			this.putErrorMessage(resultMap, "无效的用户名");
		} else if(!ClientUtils.validateStrByLength(newUserName,16)) {
			this.putErrorMessage(resultMap, "用户名在4-16个字符内");
		} else if(newUserName.length() != ClientUtils.StringFilter(newUserName.toLowerCase()).length()) {
			this.putErrorMessage(resultMap, "有非法字符");
		} /* else if ("Y".equalsIgnoreCase(user.getNameIsUpdate())) {
			this.putErrorMessage(resultMap, "用户名已经更新过一次，不能再更新");
		} */ else {
			try {
				String oldName = user.getNickName();
				// 过滤特殊字符 
				user.setNickName(ClientUtils.StringFilter(newUserName.toLowerCase()));//用户名统一小写
				user.setNameIsUpdate("Y");
				userUserProxy.update(user);
				putSession(Constant.SESSION_FRONT_USER, user);
				collectModifyUserInfoAction(user,"modifyUserName", oldName+"->"+newUserName);
				syncBBS(user);
				
				/*UserUser alreadyExistUser = this.userUserProxy.getUsersByMobOrNameOrEmailOrCard(newUserName);
				if (alreadyExistUser == null) {
					String oldName = user.getNickName();
					// 过滤特殊字符 
					user.setNickName(ClientUtils.StringFilter(newUserName.toLowerCase()));//用户名统一小写
					user.setNameIsUpdate("Y");
					userUserProxy.update(user);
					putSession(Constant.SESSION_FRONT_USER, user);
					collectModifyUserInfoAction(user,"modifyUserName", oldName+"->"+newUserName);
					syncBBS(user);
				} else {
					//新用户名已存在不能修改
					this.putErrorMessage(resultMap, "新用户名已存在不能修改");
				}		*/	
			} catch (Exception e) {
				e.printStackTrace();
				this.putErrorMessage(resultMap, "修改用户名错误");
			}
		}
        this.sendResult(resultMap, null, false);
	}
	
	
	/**
	 * 修改手机号 - 已绑定 和 未绑定   . phone_send
	 */
	@Action("/client/updateUserPhone")
	public void updateUserPhone() {
		Map<String, Object> resultMap = super.resultMapCreator();
    	String mobile = this.getRequest().getParameter("mobile");
    	String authenticationCode = this.getRequest().getParameter("authenticationCode");
    	
    	/**
    	 * 如果非法请求 
    	 */
    	if(!isLegalRequest()) {
    		this.putErrorMessage(resultMap, "非法访问");
			this.sendResult(resultMap, null, false);
			return ;
    	}
    	
    	if (StringUtils.isBlank(mobile)) {
    		this.putErrorMessage(resultMap, "手机号码不能为空");
    		this.sendResult(resultMap, null, false);
			return ;
    	} 
    	
		 UserUser user = this.getUser();
		 mobile=mobile.replaceAll(" ", "");
		 if(null == user) {
			 this.putErrorMessage(resultMap, "该用户还未登陆");
		 } else {
			/******* 手机号已绑定  *******/
			 boolean isSuccess = false;
			 // 手机已经绑定过，需要修改手机  
			 if ("Y".equals(user.getIsMobileChecked())) {
				 if (!userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.MOBILE, mobile)){
					    this.putErrorMessage(resultMap, "该手机号已被注册，请更换其它手机号，或用此手机号登录");
						this.sendResult(resultMap, null, false);
						return;
				 }  else if(!userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, authenticationCode, mobile)) {
					    this.putErrorMessage(resultMap, "请输入正确的验证码");
						this.sendResult(resultMap, null, false);
						return;
				 }
				 
				String oldMobile = mobile;
				user.setMobileNumber(mobile);
				userUserProxy.update(user);
				putSession(Constant.SESSION_FRONT_USER, user);
				collectModifyUserInfoAction(user,"modifyMobile", oldMobile+"->"+mobile);
				isSuccess = true;
			} 
			
			//有手机但没有绑定 
			 if (!"Y".equals(user.getIsMobileChecked())
						&& StringUtils.isNotBlank(user.getMobileNumber())){
					if(!userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, authenticationCode, user.getMobileNumber())) {
						this.putErrorMessage(resultMap, "请输入正确的验证码");
						this.sendResult(resultMap, null, false);
						return;
				    }
					
					//验证手机
					mobile = user.getMobileNumber();
					user.setIsMobileChecked("Y");
					userUserProxy.update(user);
					putSession(Constant.SESSION_FRONT_USER, user);
					userUserProxy.addUserPoint(user.getId(), "POINT_FOR_MOBILE_AUTHENTICATION", null, null);
					collectModifyUserInfoAction(user,"authMobile", mobile);
					isSuccess = true;
			 }
				
			
			/******* 手机绑定 *******/
			//邮箱,手机都无信息
			if ( StringUtils.isBlank(user.getMobileNumber())){
				if (!userUserProxy.isUserRegistrable(USER_IDENTITY_TYPE.MOBILE, mobile)){
				    this.putErrorMessage(resultMap, "该手机号已被注册，请更换其它手机号，或用此手机号登录");
					this.sendResult(resultMap, null, false);
					return;
				 }  else if(!userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, authenticationCode, mobile)) {
					    this.putErrorMessage(resultMap, "请输入正确的验证码");
						this.sendResult(resultMap, null, false);
						return;
				 }
				
				CashAccountVO cashAccount = cashAccountService.queryMoneyAccountByUserId(user.getId());
				Long orderCounts=orderServiceProxy.queryUserOrderVisitTimeGreaterCounts(user.getUserNo());
				//若该用户的存款账户余额为0，且没有订单的游玩日期晚于（当下时刻-168小时）,若不满足以上条件，则需要进行订单信息验证，在通过订单信息验证和手机校验码验证之后，方绑定手机成功。
				if(!(cashAccount.getTotalMoney()==0&&orderCounts==0)){
					OrdOrder firstOrder=orderServiceProxy.queryUserFirstOrder(user.getUserNo());
					String firstOrderCtMobile = this.getRequest().getParameter("firstOrderCtMobile");
					if(!firstOrderCtMobile.equals(firstOrder.getContact().getMobile())){
						this.putErrorMessage(resultMap, "首笔订单信息校验失败!");
						this.sendResult(resultMap, null, false);
						return ;
					}
				}
				
				//保存原始的手机是否验证的状态
				String oldIsMobileChecked = user.getIsMobileChecked();
				
				//绑定手机
				user.setMobileNumber(mobile);
				user.setIsMobileChecked("Y");
				userUserProxy.update(user);
				putSession(Constant.SESSION_FRONT_USER, user);
				
				//如果用户验证过的手机注销过，则不再发送手机验证的奖励积分
				if (!"F".equalsIgnoreCase(oldIsMobileChecked)) {
					userUserProxy.addUserPoint(user.getId(), "POINT_FOR_MOBILE_AUTHENTICATION", null, null);
				}
				collectModifyUserInfoAction(user,"bindMobile", mobile);	
				isSuccess = true;
			}
			
			// 如果手机修改失败 》 
			if(!isSuccess) {
				this.putErrorMessage(resultMap, "手机修改失败或校验码失效");
			}
		 }
    	 this.sendResult(resultMap, null, false);
	}
	

	/**
	 * 统一手机验证 - 发送校验码 V5.0
	 */
	@Action("/client/sendMobileAuthMessage")
	public void sendMobileAuthMessage() {
		try {
			/**
	    	 * 如果非法请求 
	    	 */
	    	if(!isLegalRequest()) {
	    		this.sendAjaxResult("{\"errorText\":\"非法访问\",\"success\":false}");
				return ;
	    	}
	    	String userNo = this.getRequest().getParameter("userNo");
			String mobile = this.getRequest().getParameter("mobile");
			String checkOldMobile = this.getRequest().getParameter("checkMobileExits");
			String lvsessionId = this.getRequest().getParameter(Constant.LV_SESSION_ID);
			if (StringUtil.validMobileNumber(mobile)) {
				// 每个用户一分钟内只能发送一次 短信凭证
				Object object = MemcachedUtil.getInstance().get(LT_MOBILE_AUTH_WORLD + mobile);
				if (null != object) {
					long c_long = com.lvmama.clutter.utils.DateUtil.getCurrentTimeLong(); // 当前日期秒
					long h_long = Long.parseLong(object.toString()); // 历史日期 秒
					if (c_long - h_long < 55) {
						this.sendAjaxResult("{\"errorText\":\"一分钟只能发送一次\",\"success\":false}");
						return;
					}
				}

				String url = ClutterConstant.getNssoUrl()
						+ "/mobileAjax/sendMobileAuthMessage.do?mobile=" + mobile + "&"
						+ Constant.LV_SESSION_ID + "=" + lvsessionId+"&userNo="+userNo + "&checkOldMobile="+checkOldMobile;
				this.getRequest().setAttribute(Constant.LV_SESSION_ID,lvsessionId);
				String json = HttpsUtil.requestGet(url);
				// 发送成功后才会缓存 。 
				if(json.indexOf("true") != -1) {
					// 发送完成后 用户信息保存在memcache中
					MemcachedUtil.getInstance().set(LT_MOBILE_AUTH_WORLD + mobile, 55,com.lvmama.clutter.utils.DateUtil.getCurrentTimeLong());
				}
				this.sendAjaxResult(json);
			} else {
				this.sendAjaxResult("{\"errorText\":\"请输入正确的手机号\",\"success\":false}");
			}
		} catch (Exception e) {
			this.sendAjaxResult("{\"errorText\":\"" + e.getMessage()+ "\",\"success\":false}");
		}
	}
	
	/**
	 * 统一手机验证 -手机号校验码校验
	 */
	@Action("/client/validateAuthenticationCode")
	public void validateAuthenticationCode() {
		Map<String, Object> resultMap = super.resultMapCreator();
		try {
			String mobile = this.getRequest().getParameter("mobile");
			String authenticationCode = this.getRequest().getParameter("authenticationCode");
			
			// 手机号是否正确 
			if (StringUtil.validMobileNumber(mobile)) {
				if(StringUtils.isEmpty(authenticationCode)) {
					this.putErrorMessage(resultMap, "请输入正确的校验码");
					this.sendResult(resultMap, null, false);
					return;
				}
				// 校验
				boolean b = userUserProxy.validateAuthenticationCode(USER_IDENTITY_TYPE.MOBILE, authenticationCode,mobile);
				if(b) {
					this.sendResult(resultMap, null, false);
				} else {
					this.putErrorMessage(resultMap, "请输入正确的验证码");
				}
				
			} else {
				this.putErrorMessage(resultMap, "手机号码不能为空");
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.putErrorMessage(resultMap, e.getMessage());
		}
		this.sendResult(resultMap, null, false);
	}
	
	
	/**
	 * 修改密码需要同步到bbs中 
	 * @param user
	 */
	protected void syncBBS(UserUser user) {
		try {
			if (LOG.isDebugEnabled()) {
				LOG.debug("update BBS account from client");
			}		
			String bbsUrl = Constant.getInstance().getBBSUrl();
			if(StringUtils.isNotEmpty(bbsUrl)) {
				LOG.info("sync bbs:"+user.getUserId());
				StringBuffer sb = new StringBuffer(bbsUrl+"/api/sync.php?action=update");
				sb.append("&username=").append(URLEncoder.encode(user.getUserName(), "utf-8"))
					.append("&password=").append(user.getRealPass()).append("&user_id=").append(user.getUserId())
					.append("&ip=").append("");
				if (LOG.isDebugEnabled()) {
					LOG.debug("submit data：" + sb.toString() + "   from client");
				}
				HttpClient httpClient = new HttpClient();
				httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
				httpClient.getHttpConnectionManager().getParams().setSoTimeout(5000);
				GetMethod getMethod = new GetMethod(sb.toString());
				httpClient.executeMethod(getMethod);
			} else {
				LOG.error("bbs url is null   from client");
			}

		} catch (Exception ioe) {
			LOG.error(ioe.getMessage());
		}
	}
	
	/**
	 * 是否合法请求 . 
	 * @return
	 */
	public boolean isLegalRequest() {
		String lvSessionId = this.getRequest().getParameter(Constant.LV_SESSION_ID);
    	String requestSign = this.getRequest().getParameter("sign");
		String signKey = ClutterConstant.getMobileSignKey();
		String serverSign = String.format("%s%s",lvSessionId, signKey);
		log.info(" server Sign:"+MD5.md5(serverSign)+" requestSign"+requestSign);
		return MD5.md5(serverSign).equalsIgnoreCase(requestSign);
	}
	
	/**
	 * 记录用户修改用户信息的行为（修改密码/用户名/手机号/EMAIL）
	 * @param user
	 * @param action
	 * @param memo
	 */
	protected void collectModifyUserInfoAction(UserUser user,  String action, String memo){
		UserActionCollectionService userActionCollectionService = getUserActionCollectionService();
		if (null != userActionCollectionService) {
			userActionCollectionService.save(user.getId(), InternetProtocol.getRemoteAddr(getRequest()),InternetProtocol.getRemotePort(getRequest()) ,action, memo);
		}
	}
	
	/**
	 * 获取用户信息收集服务
	 * @return
	 */
	private UserActionCollectionService getUserActionCollectionService() {
		return (UserActionCollectionService) SpringBeanProxy.getBean("userActionCollectionService");
	}
	
	/**
	 * 把指定的文件上传到专用的静态文件服务器上，返回URL
	 * @param file
	 * @return String
	 */
	private String postToRemote(File f, String fileName) {
		try {
			// uploadurl=http://pic.lvmama.com/upaction
			String filePath = Constant.getInstance().getUploadUrl();
			PostMethod filePost = new PostMethod(filePath);
			log.info("...upload user ico paht ="+filePath);
			String path = "/uploads/header/" + fileName;
			Part[] parts = { new StringPart("fileName", path),new FilePart("ufile", f) };
			filePost.setRequestEntity(new MultipartRequestEntity(parts,filePost.getParams()));
			HttpClient client = new HttpClient();
			int status = client.executeMethod(filePost);
			if (status == 200) {
				return path;
			} else {
				log.error("ERROR, return: " + status);
			}
		} catch (IOException e) {
			
		}
		return null;
	}
	
	/**
	 * 获取后缀名
	 * @param filename 文件名 
	 * @return String
	 */
	private String getSuffixName(final String filename) {
		if (null != filename && filename.indexOf(".") != -1) {
			return filename.substring(filename.lastIndexOf("."));
		} else {
			return "";
		}
	}
	
	
	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public void setMobileClientService(MobileClientService mobileClientService) {
		this.mobileClientService = mobileClientService;
	}
	
	public File  getFile() {
		return file;
	}

	public void setFile(File  file) {
		this.file = file;
	}

	public String  getFileName() {
		return fileName;
	}

	public void setFileName(String  fileFileName) {
		this.fileName = fileFileName;
	}
}
