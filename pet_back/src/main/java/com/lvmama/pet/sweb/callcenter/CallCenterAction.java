package com.lvmama.pet.sweb.callcenter; 

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.service.SmsService;
import com.lvmama.comm.pet.client.UserClient;
import com.lvmama.comm.pet.po.conn.ConnRecord;
import com.lvmama.comm.pet.po.conn.ConnType;
import com.lvmama.comm.pet.po.conn.LvccChannel;
import com.lvmama.comm.pet.po.conn.LvccPromotionActivity;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComMobileArea;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.po.pub.ComSms;
import com.lvmama.comm.pet.po.pub.ComSmsTemplate;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.conn.ConnRecordService;
import com.lvmama.comm.pet.service.conn.LvccChannelService;
import com.lvmama.comm.pet.service.conn.LvccPromotionActivityService;
import com.lvmama.comm.pet.service.pub.ComMobileAreaService;
import com.lvmama.comm.pet.service.pub.ComSmsTemplateService;
import com.lvmama.comm.pet.service.pub.PlaceCityService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.user.UserUserProxy.USER_IDENTITY_TYPE;
import com.lvmama.comm.pet.vo.AfterUserRegisterInfo;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UserUserUtil;
import com.lvmama.comm.vo.CallUserInfo;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SMS_TEMPLATE_TYPE;

@ParentPackage("json-default")
@Results( { 
		@Result(name = "callCenterIndex", location = "/WEB-INF/pages/back/conn/index.jsp"),
		@Result(name = "orderFeedBackList", location = "/WEB-INF/pages/back/conn/orderFeedBackList.jsp"),
	    @Result(name = "toCallCenter", location = "/call/callCenter.do", type = "redirect",
			params={"callerid","%{callerid}","agentname","%{agentname}",
				"isCallBack","%{isCallBack}","userName","%{userName}",
				"callerEmail","%{callerEmail}","message","%{message}","memberShipCard","%{memberShipCard}"}),
		@Result(type = "json", name = "json") })
/** 
 * 来电弹屏.		
 */
public class CallCenterAction extends BackBaseAction {
	private static final long serialVersionUID = 1992859771997204710L;
	/**
	 * log.
	 */
	private static Log log = LogFactory.getLog(CallCenterAction.class);
	/**
	 * 来电、去电弹屏所显示的通话记录最大记录数.
	 */
	private static final Long MAX_RESULTS = 50L;
	/**
	 * 用户电话交流分类接口.
	 */
	private ConnRecordService connRecordService;
	/**
	 * 
	 */
	private ComMobileAreaService comMobileAreaService;
	
	private ComSmsTemplateService comSmsTemplateRemoteService;
	
	private SmsService smsService;

	/**
	 * 城市集合.
	 */
	private List<ComCity> comCityList = new ArrayList<ComCity>();
	/**
	 * 显示用户信息.
	 */
	private CallUserInfo callUserInfo = new CallUserInfo();
	/**
	 * 注册用户接口.
	 */
	private UserUserProxy userUserProxy;
	private UserClient userClient;
	
	/**
	 * 省份接口.
	 */
	private PlaceCityService placeCityService;
	/**
	 * 来电弹屏操作人.
	 */
	private String agentname = "";
	/**
	 * 来电弹屏手机号(与对方系统电话号码命名相同).
	 */
	private String callerid = "";
	/**
	 * 是否来电去电.'Y'表示来电,'N'表示去电.
	 */
	private String isCallBack = "Y";
	/**
	 * 会员名.
	 */
	private String userName = "";

	/**
	 * 会员邮箱.
	 */
	private String callerEmail = "";
	/**
	 * 会员卡号.
	 */
	private String memberShipCard;
	/**
	 * 输出消息提示.
	 */
	private String message = "";
	/**
	 * 是否为会员
	 */
	private boolean member=false;
	private ConnRecord connRecord;
	private ConnType connType;
	private Page<ConnRecord> connRecordPage;
	private List<ConnType> connTypeList;
	private UserUser userUser;
	private String callBack = "false";
	private String ROUTE_LIST[]={"目的地自由行","短途跟团游","长途跟团游","长途自由行","出境跟团游","出境自由行"};
	private ComMobileArea comMobileArea;
	private DecimalFormat f = new DecimalFormat("0000");
	
	private Map<String,String> smsTypeMap;
	
	private String templateType;
	private String templateId;
	private Long orderId;
	private String mobilePhoneNo;
	private String smsContent;
	private Long userId;
	private String ipccUrl;
	
	private LvccPromotionActivityService lvccPromotionActivityService;
	private LvccPromotionActivity activity;
	private LvccChannelService lvccChannelService;
	private List<LvccChannel> channelList;
	
	/**
	 * 展示来电详情展示页面.
	 * 
	 * @return
	 */
	@Action(value = "/call/callCenter")
	public String callCenter() {
		if(null == getSessionUser()){
			try {
				getResponse().sendRedirect("/pet_back/login.do");
				return null;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		initUsersInfo();
		ipccUrl = Constant.getInstance().getProperty("ipcc.url");
		//短信类型
		smsTypeMap=new HashMap<String,String>();     
		for (SMS_TEMPLATE_TYPE type : Constant.SMS_TEMPLATE_TYPE.values()) {
			smsTypeMap.put(type.name(), type.getCnName());
		}
		
		return this.initCallCenter();
	}
	/**
	 * 短信列表
	 */
	@Action(value = "/callCenter/getSmsTemplatesByType")
	public void getSmsTemplatesByType() {
		JSONArray jsonArr=new JSONArray();
		List<ComSmsTemplate> templateList=comSmsTemplateRemoteService.selectAllSmsTempalteList(templateType);
		for (ComSmsTemplate comSmsTemplate : templateList) {
			JSONObject obj=new JSONObject();
			obj.put("id", comSmsTemplate.getTemplateId());
			obj.put("name", comSmsTemplate.getTemplateName());
			jsonArr.add(obj);
		}
		sendAjaxResultByJson(jsonArr.toString());
	}
	
	/**
	 * 获取渲染后的短信内容
	 * @throws Exception
	 */
	@Action(value = "/callCenter/getSmsTemplateContent")
	public void getSmsTemplateContent() throws Exception {
		JSONObject jsonResult=new JSONObject();
		PermUser user=this.getSessionUser();
		if(null==user){
			getResponse().sendError(403);
			return;
		}
		if(StringUtils.isBlank(templateId)){
			jsonResult.put("result", "error");
			jsonResult.put("msg", "请选择模板");
			sendAjaxResultByJson(jsonResult.toString());
			return;
		}
		ComSmsTemplate comSmsTemplate = comSmsTemplateRemoteService.selectSmsTemplateByPrimaryKey(templateId);
		String content=comSmsTemplate.getContent();
		if(content.contains("${orderId}")&&orderId==null){
			jsonResult.put("result", "error");
			jsonResult.put("msg", "请选择处理订单");
			sendAjaxResultByJson(jsonResult.toString());
			return;
		}
		Map<String, Object> parameters=new HashMap<String, Object>();
		parameters.put("orderId", String.valueOf(orderId));
		
		String employeeNum=user.getExtensionNumber();//分机号
		if(StringUtils.isBlank(employeeNum)){//取员工号后四位，如cs0521-->0521
			String en=user.getEmployeeNum();
			if(StringUtils.isNotBlank(en)&&en.length()>=4){
				employeeNum=en.substring(en.length() - 4);
			}
		}
		parameters.put("employeeNum", employeeNum);
		content = StringUtil.composeMessage(content, parameters);
		jsonResult.put("result", "success");
		jsonResult.put("msg",content);
		sendAjaxResultByJson(jsonResult.toString());
	}
	
	
	/**
	 * 发送短信，并保存沟通记录
	 * @throws Exception 
	 */
	@Action(value = "/callCenter/saveSmsConnRecord")
	public void saveSmsConnRecord() throws Exception  {
		JSONObject jsonResult=new JSONObject();
		PermUser user=this.getSessionUser();
		if(null==user){
			getResponse().sendError(403);
			return;
		}
		if(StringUtils.isBlank(mobilePhoneNo)){
			jsonResult.put("result", "error");
			jsonResult.put("msg", "请输入手机号码");
			sendAjaxResultByJson(jsonResult.toString());
			return;
		}
		if(StringUtils.isBlank(templateId)){
			jsonResult.put("result", "error");
			jsonResult.put("msg", "请选择模板");
			sendAjaxResultByJson(jsonResult.toString());
			return;
		}
		if(StringUtils.isBlank(smsContent)){
			jsonResult.put("result", "error");
			jsonResult.put("msg", "请输入短信内容");
			sendAjaxResultByJson(jsonResult.toString());
			return;
		}
		
		//发送短信
		ComSms sms = new ComSms();
		sms.setMobile(mobilePhoneNo);
		sms.setContent(smsContent);
		sms.setObjectId(orderId);
		sms.setObjectType(templateId);
		sms.setMms("false");
		smsService.sendSms(sms);
		
		//记录沟通记录
		ComSmsTemplate comSmsTemplate = comSmsTemplateRemoteService.selectSmsTemplateByPrimaryKey(templateId);
		ConnRecord connRecord=new ConnRecord();
		connRecord.setUserId(userId);
		connRecord.setMobile(mobilePhoneNo);
		connRecord.setFeedbackTime(new Date());
		connRecord.setCreateDate(new Date());
		connRecord.setMemo(smsContent);
		connRecord.setServiceType("短信");
		connRecord.setSubServiceType(comSmsTemplate.getTemplateName());
		connRecord.setOperatorUserId(getSessionUserName());
		connRecordService.saveConnRecord(connRecord);
		
		jsonResult.put("result", "success");
		jsonResult.put("msg", "发送成功");
		
		sendAjaxResultByJson(jsonResult.toString());
	}
	
	
	
	/**
	 * 保存沟通记录.
	 * 
	 * @return
	 */
	@Action(value = "/callCenter/saveConnRecord")
	public String saveConnRecord() {
		connRecord.setFeedbackTime(new Date());
		connRecord.setCreateDate(new Date());
		connRecord.setMemo(replace(connRecord.getMemo()));
		connRecord.setOperatorUserId(getSessionUserName());
		if(!(connRecord.hasCallBack()&&ArrayUtils.contains(ROUTE_LIST, connRecord.getBusinessType()))){
				connRecord.setProductZone(null);
		}
		
		this.connRecordService.saveConnRecord(connRecord);
		
		if(StringUtils.isNotEmpty(connRecord.getMobile())){
			this.connRecordPage = connRecordService.queryConnRecordPage(connRecord.getMobile(),MAX_RESULTS, 1L);
		}
		return "orderFeedBackList";
	}
	/**
	 * 对回车换行符清除掉，方便数据的导出
	 * @param str
	 * @return
	 */
	private String replace(String str){
		if(StringUtils.isEmpty(str)){
			return str;
		}
		//清除掉回车符
		return str.replaceAll("\r|\n", "");
	}

	/**
	 * 加载用户.
	 */
	public void initUsersInfo() {
		try {
//			if(StringUtils.isNotEmpty(userName)){
//				//userName=UniformResourceLocator.decode(userName);
//			}
			callUserInfo.setMobileNumber(this.callerid);
			if (StringUtils.isNotBlank(callerid)) {
				userUser = this.userUserProxy.getUsersByIdentity(this.callerid,USER_IDENTITY_TYPE.MOBILE);
			}			
			if (userUser == null && StringUtils.isNotBlank(userName)) {
				//userName=UniformResourceLocator.decode(userName);
				userUser = this.userUserProxy.getUsersByIdentity(userName,USER_IDENTITY_TYPE.USER_NAME);
			}
			if (userUser == null && StringUtils.isNotBlank(callerEmail)) {
				userUser = this.userUserProxy.getUsersByIdentity(this.callerEmail,USER_IDENTITY_TYPE.EMAIL);
			}
			if (userUser == null && StringUtils.isNotBlank(memberShipCard)) {
				userUser = this.userUserProxy.getUsersByIdentity(this.memberShipCard,USER_IDENTITY_TYPE.MEMBERSHIPCARD);
			}
			if(userId!=null){
				userUser=this.userUserProxy.getUserUserByPk(userId);
			}
			
			if (userUser != null) {
				callUserInfo.setIsUsers("Y");
				this.userName = userUser.getUserName();
				this.callerid = userUser.getMobileNumber();
				this.callerEmail = userUser.getEmail();
				this.memberShipCard = userUser.getMemberShipCard();
				this.member=true;
				callUserInfo.setIsUsers("Y");
				callUserInfo.setId(userUser.getId());
				BeanUtils.copyProperties(callUserInfo, userUser);
				callUserInfo.setCreateDateStr(DateUtil.getDateTime("yyyy-MM-dd HH:mm:ss", userUser.getCreatedDate()));
				if (StringUtils.isNotEmpty(userUser.getCityId())) {
					ComCity cc = this.placeCityService.selectCityByPrimaryKey(userUser.getCityId());
					if(cc!=null){
						comCityList=this.placeCityService.getCityListByProvinceId(cc.getProvinceId());
						callUserInfo.setProvinceId(cc.getProvinceId());
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
//		message = UniformResourceLocator.decode(message);
		writeUserCookie(callUserInfo.getUserId(), callUserInfo.getUserName());
	}
	 

	/**
	 * 更新用户及顾客详细信息.
	 * 
	 * @return
	 */
	@Action(value = "/call/updateUsersInfo")
	public String updateUsersInfo() {
		if ("Y".equals(callUserInfo.getIsUsers())) {
			try {
				UserUser user =  this.userUserProxy.getUserUserByPk(callUserInfo.getId());
			    user = returnUserInfo(callUserInfo,user);
				this.userUserProxy.update(user);
				this.setMessage("会员修改成功!");				
			} catch (Exception e) {
				log.error(e);
				this.setMessage("警告：会员更新失败!");
			}
		} 
		this.setCallerid(this.callUserInfo.getMobileNumber());
		writeUserCookie(callUserInfo.getUserId(), callUserInfo.getUserName());
//		userName=UniformResourceLocator.encode(userName);
		return "toCallCenter";
	}

	/**
	 * 会员注册.
	 * 
	 * @return
	 */
	@Action(value = "/call/saveUsrUsers")
	public String saveUsrUsers() {
		PermUser userPerm = (PermUser) ServletUtil.getSession(this.getRequest(), this.getResponse(), Constant.SESSION_BACK_USER);
		String mobileNumber=this.getRequest().getParameter("moblieNumber"); 
		if(StringUtils.isBlank(mobileNumber)){ 
			mobileNumber = this.callUserInfo.getMobileNumber(); 
		} 
		this.setCallerid(mobileNumber); 
		this.callUserInfo.setMobileNumber(mobileNumber);
		if (StringUtil.validMobileNumber(mobileNumber)) {
			if (mobileNumber.charAt(0) == '0') {
				callUserInfo.setMobileNumber(mobileNumber.substring(1, mobileNumber.length()));
			}
			try {
				UserUser user = UserUserUtil.genDefaultUserByMobile(callUserInfo.getMobileNumber());
				user = returnUserInfo(callUserInfo, user);
				user.setGroupId("GP_PHONE");
				
				//userUserProxy.register(user);
				
				verifyUser();
				
				if (member) {
					this.setMessage("驴妈妈会员注册失败! 该用户已经是会员!");
					this.member=false;					
				} else {
					user = userClient.register(AfterUserRegisterInfo.createAfterUserSilentRegisterInfo(user));
					this.setMessage("驴妈妈会员注册成功! ");
					this.member=true;
				}
				getRequest().setAttribute("member",this.member);
			} catch (Exception e) {
				log.error(e);
				this.setMessage(e.getMessage());
			}
		} else {
			this.setMessage("警告：会员注册失败,请输入合法的手机号,座机号请注册为顾客！");
		}
		writeUserCookie(callUserInfo.getUserId(), callUserInfo.getUserName());
		userPerm = (PermUser) ServletUtil.getSession(this.getRequest(), this.getResponse(), Constant.SESSION_BACK_USER);
		return "toCallCenter";
	}
 
	
	/**
	 * get AgentName is CreatedUserId
	 * 
	 * @return
	 */
	private String getAgentName(PermUser permU) {
		try{
			String agentname = this.getRequest().getParameter("agentname");
			if (StringUtils.isNotBlank(agentname)) {
				if (agentname.indexOf("@") > -1) {
					agentname = agentname.substring(0, agentname.indexOf("@"));
					if(agentname != null){
						String fix = agentname.substring(0, 2);
						agentname = agentname.replace(fix, "");
						agentname = fix+f.format(Long.valueOf(agentname));
					}
				}
			} else {
				agentname = "";
			}
		}catch(NumberFormatException e){
			if(permU!=null){
				agentname = permU.getUserName();
			}
		}catch(Exception e){
			if(permU!=null){
				agentname = permU.getUserName();
			}
			log.error(e);
		}
		return agentname;
	}
		 
	/**
	 * 
	 * @param callUserInfo
	 * @return
	 */
	private UserUser returnUserInfo(CallUserInfo callUserInfo,UserUser user) {
		if (StringUtils.isNotBlank(callUserInfo.getRealName())) {
			user.setRealName(callUserInfo.getRealName());
		}
		if (StringUtils.isNotBlank(callUserInfo.getAddress())) {
			user.setAddress(callUserInfo.getAddress());
		}
		if (StringUtils.isNotBlank(callUserInfo.getZipCode())) {
			user.setZipCode(callUserInfo.getZipCode());
		}
		if (StringUtils.isNotBlank(callUserInfo.getCityId())) {
			user.setCityId(callUserInfo.getCityId());
		}
		if (StringUtils.isNotBlank(callUserInfo.getGender())) {
			user.setGender(callUserInfo.getGender());
		}
		if (StringUtils.isNotBlank(callUserInfo.getMemo())) {
			user.setMemo(callUserInfo.getMemo());
		}
		if (StringUtils.isNotBlank(callUserInfo.getIdNumber())) {
			user.setIdNumber(callUserInfo.getIdNumber());
		}
		return user;
	}

	/**
	 * init call center
	 * 
	 * @return
	 */
	public String initCallCenter() {
		String mobile = getRequest().getParameter("callerid");
		if("Y".equals(this.isCallBack)){
			callBack = "true";
		}
		connTypeList = connRecordService.queryConnTypeCallBack(callBack);
		
 		Long currentPage = 1L;
 		if(null!=userId){
 			this.connRecordPage = connRecordService.queryConnRecordPage(userId,MAX_RESULTS, currentPage);
 		}
		if(userId==null&&StringUtils.isNotEmpty(mobile)){
			this.connRecordPage = connRecordService.queryConnRecordPage(mobile,MAX_RESULTS, currentPage);
		}
		PermUser permU = this.getSessionUser();

		comMobileArea = new ComMobileArea();
		comMobileArea.setMobileNumber(getMobileAreaNumber(mobile));
		comMobileArea = comMobileAreaService.findMobileArea(comMobileArea);
		getRequest().setAttribute("mobile", mobile);
		getRequest().setAttribute("operatorUserName", getAgentName(permU));
		getRequest().setAttribute("member",this.member);
		if(StringUtils.isNotEmpty(message)){
			message=StringUtil.toUTF8(message);
		}
		if(StringUtils.isNotEmpty(userName)){
			if (userUser == null) {
				userName=StringUtil.toUTF8(userName);
			}
		}
		activity = lvccPromotionActivityService.selectCurrentActivity();
		if(activity != null) {
			String channel = activity.getChannel();
			String[] channelIdStr = channel.split(",");
			Long[] channelIds = new Long[channelIdStr.length];
			for (int i = 0; i < channelIdStr.length; i++) {
				channelIds[i] = Long.parseLong(channelIdStr[i]);
			} 
			channelList = lvccChannelService.selectByIds(channelIds);
		}
		return "callCenterIndex";
	}

	/**
	 * 获得手机号的前七位
	 * 
	 * @param mobile
	 * @return
	 */
	private String getMobileAreaNumber(String mobile) {
		if (StringUtil.validMobileNumber(mobile)) {
			return (mobile.charAt(0)==0) ? mobile.substring(1,8) : mobile.substring(0,7);
		} else {
			return mobile;
		}
	}
	/**
	 * 核实账号.
	 */
	@Action(value = "/call/verifyUser")
	public String verifyUser() {
		try {

			if (StringUtils.isNotBlank(callerid)) {
				callUserInfo.setMobileNumber(this.callerid);
				userUser = (UserUser) this.userUserProxy.getUsersByIdentity(callerid,USER_IDENTITY_TYPE.MOBILE);
			}
			if (userUser == null && StringUtils.isNotBlank(userName)) {
				userUser = (UserUser) this.userUserProxy.getUsersByIdentity(userName,USER_IDENTITY_TYPE.USER_NAME);
				//userName=de(userName);
			}
			if (userUser == null && StringUtils.isNotBlank(callerEmail)) {
				userUser = (UserUser) this.userUserProxy.getUsersByIdentity(callerEmail,USER_IDENTITY_TYPE.EMAIL);
			}
			if (userUser == null && StringUtils.isNotBlank(memberShipCard)) {
				userUser = (UserUser) this.userUserProxy.getUsersByIdentity(memberShipCard,USER_IDENTITY_TYPE.MEMBERSHIPCARD);
			}
			if(userId!=null){
				userUser = (UserUser) this.userUserProxy.getUserUserByPk(userId);
			}
			if (userUser != null) {
				callUserInfo.setIsUsers("Y");
				this.userName = userUser.getUserName();
				this.callerid = userUser.getMobileNumber();
				this.callerEmail = userUser.getEmail();
				this.memberShipCard = userUser.getMemberShipCard();
				BeanUtils.copyProperties(callUserInfo, userUser);
				this.setMessage("该用户已经是会员!");
				member=true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		writeUserCookie(callUserInfo.getUserId(), callUserInfo.getUserName());
		return "toCallCenter";
	}
	
	/**
	 * 将用户信息写入cookie，在后台下单时自动获取
	 * */
	private void writeUserCookie(String userId, String userName) {
		HttpServletResponse res = getResponse();
		if(!StringUtils.isEmpty(userId)) {
			Cookie cookie = new Cookie("phone_order_userId", userId);
			cookie.setPath("/");
			res.addCookie(cookie);
		}
		if(!StringUtils.isEmpty(userName)) {
			Cookie cookie;
			try {
				cookie = new Cookie("phone_order_userName", java.net.URLEncoder.encode(userName,"UTF-8"));
				cookie.setPath("/");
				res.addCookie(cookie);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * get ChildCallTypeNode with parentId
	 * 
	 * @return
	 */
	@Action(value = "/callCenter/getChildConnType")
	public String getChildConnType() {
		this.connTypeList = connRecordService.quueryConnTypeByConnType(this.connType);
		return "json";
	}
	
	public String getCallerid() {
		return callerid;
	}

	public void setCallerid(String callerid) {
		this.callerid = callerid;
	}

	public String getIsCallBack() {
		return isCallBack;
	}

	public void setIsCallBack(String isCallBack) {
		this.isCallBack = isCallBack;
	}
  
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
  
	public void setAgentname(String agentname) {
		this.agentname = agentname;
	}

	public String getAgentname() {
		return agentname;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getCallerEmail() {
		return callerEmail;
	}

	public void setCallerEmail(String callerEmail) {
		this.callerEmail = callerEmail;
	}
 

	public String getMemberShipCard() {
		return memberShipCard;
	}

	public void setMemberShipCard(String memberShipCard) {
		this.memberShipCard = memberShipCard;
	}

	public boolean isMember() {
		return member;
	}

	public void setMember(boolean member) {
		this.member = member;
	}

	public void setConnRecordService(ConnRecordService connRecordService) {
		this.connRecordService = connRecordService;
	}

	public Page<ConnRecord> getConnRecordPage() {
		return connRecordPage;
	}
	 
	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public List<ConnType> getConnTypeList() {
		return connTypeList;
	}

	public ConnType getConnType() {
		return connType;
	}

	public List<ComProvince> getComProvinceList() {
		return placeCityService.getProvinceList();
	}

	public void setPlaceCityService(PlaceCityService placeCityService) {
		this.placeCityService = placeCityService;
	}

	public List<ComCity> getComCityList() {
		return comCityList;
	}

	public CallUserInfo getCallUserInfo() {
		return callUserInfo;
	}
	
	public void setConnType(ConnType connType) {
		this.connType = connType;
	}
	
	public UserUser getUserUser() {
		return userUser;
	}

	public void setCallUserInfo(CallUserInfo callUserInfo) {
		this.callUserInfo = callUserInfo;
	}
	public void setUserUser(UserUser userUser) {
		this.userUser = userUser;
	}
	
	public ConnRecord getConnRecord() {
		return connRecord;
	}

	public void setConnRecord(ConnRecord connRecord) {
		this.connRecord = connRecord;
	}

	public String getCallBack() {
		return callBack;
	}

	public void setCallBack(String callBack) {
		this.callBack = callBack;
	}
	public ComMobileArea getComMobileArea() {
		return comMobileArea;
	}

	public void setComMobileAreaService(
			ComMobileAreaService comMobileAreaService) {
		this.comMobileAreaService = comMobileAreaService;
	}
	public void setUserClient(UserClient userClient) {
		this.userClient = userClient;
	}


	public Map<String, String> getSmsTypeMap() {
		return smsTypeMap;
	}


	public void setSmsTypeMap(Map<String, String> smsTypeMap) {
		this.smsTypeMap = smsTypeMap;
	}
	

	public void setComSmsTemplateRemoteService(
			ComSmsTemplateService comSmsTemplateRemoteService) {
		this.comSmsTemplateRemoteService = comSmsTemplateRemoteService;
	}

	public String getTemplateType() {
		return templateType;
	}

	public void setTemplateType(String templateType) {
		this.templateType = templateType;
	}

	public String getTemplateId() {
		return templateId;
	}

	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getMobilePhoneNo() {
		return mobilePhoneNo;
	}

	public void setMobilePhoneNo(String mobilePhoneNo) {
		this.mobilePhoneNo = mobilePhoneNo;
	}

	public String getSmsContent() {
		return smsContent;
	}

	public void setSmsContent(String smsContent) {
		this.smsContent = smsContent;
	}

	public void setSmsService(SmsService smsService) {
		this.smsService = smsService;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getIpccUrl() {
		return ipccUrl;
	}

	public void setIpccUrl(String ipccUrl) {
		this.ipccUrl = ipccUrl;
	}
	public LvccPromotionActivity getActivity() {
		return activity;
	}
	public void setActivity(LvccPromotionActivity activity) {
		this.activity = activity;
	}
	public void setLvccChannelService(LvccChannelService lvccChannelService) {
		this.lvccChannelService = lvccChannelService;
	}
	public List<LvccChannel> getChannelList() {
		return channelList;
	}
	public void setLvccPromotionActivityService(
			LvccPromotionActivityService lvccPromotionActivityService) {
		this.lvccPromotionActivityService = lvccPromotionActivityService;
	}
	
	
	
}
