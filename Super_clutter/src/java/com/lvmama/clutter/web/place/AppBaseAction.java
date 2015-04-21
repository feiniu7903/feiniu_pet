package com.lvmama.clutter.web.place;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import net.sf.json.util.PropertyFilter;

import org.apache.commons.lang3.time.DateUtils;
import org.python.antlr.PythonParser.file_input_return;

import com.lvmama.clutter.service.AppService;
import com.lvmama.clutter.utils.ClutterConstant;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.pet.po.client.ClientUser;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.client.ComClientService;
import com.lvmama.comm.pet.service.comment.CmtCommentService;
import com.lvmama.comm.pet.service.money.CashAccountService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MD5;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.UserLevelUtils;
import com.lvmama.comm.vo.CashAccountVO;
import com.lvmama.comm.vo.Constant;
//import com.lvmama.clutter.utils.HttpClientUtil;

public class AppBaseAction extends BaseAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5194716061021111974L;
	String userId;
	Long page;
	String pageSize;
	String orderId;
	String email;
	String content;
	String productType;
	Long placeId;
	String stage;
	String firstChannel;
	String secondChannel;
	String shareChannel;
	String shareTarget;
	String validateCode;
	String idCard;
	String lvSessionId;
	/**
	 * 入园日期
	 */
	String visitTime;
	
	boolean isTodayOrder; 
	
	/**
	 * 
	 */
	Long productId;
	Long branchId;
	String udid;
	String mobile;
	String code;
	String userName;
	/**
	 * 分享人的昵称
	 */
	String screenName;



	String password;
	String loginChannel;
	//第三方登录唯一识别码
	String uid;
			
	Queue<Object> requiredArgList  = new LinkedList<Object>();
	CmtCommentService cmtCommentService;
	/**
	 * 现金账户服务
	 */
	protected CashAccountService cashAccountService;

	AppService appService;
	
    ComClientService comClientService;
    /**
	 * sso服务
	 */
    UserUserProxy userUserProxy;
    
    String lvversion;
    
    protected ProdProductBranchService prodProductBranchService;
    
	protected static final Map<String,String> fieldFilterMap = new HashMap<String,String>();
	
	static {
		fieldFilterMap.put("api.com.search.shakePlace", "cmtNum,description,googleLatitude,googleLongitude,hasFreeness,imageList,importantTips,orderNotice,placeCmtScoreList,recommendReason");
		fieldFilterMap.put("api.com.recommend.getRouteToDest", "url,id,debugImageUrl,objectId,objectType,recommendContent,absoluteRecommendImageUrl");
		fieldFilterMap.put("api.com.product.getViewJourneyList", 
				"trafficDesc,placeDesc,prodTargetId,PlaceList,journeyId,pageId,traffic,productId,journeyTipsList,pictureId" +
				",pictureObjectId,pictureName,pictureObjectType,isNew,absolute580x290Url,absoluteUrl");
		
	}
	
	
	protected ClientUser getUserByMap(UserUser user, ClientUser cu) {
		cu.setUserId(user.getUserId());
		cu.setEmail(user.getEmail());
		cu.setImageUrl(Constant.PIC_HOST + user.getImageUrl());
		cu.setMobileNumber(StringUtil.trimNullValue(user.getMobileNumber()));
		cu.setNickName(StringUtil.trimNullValue(user.getNickName()));
		cu.setRealName(StringUtil.trimNullValue(user.getRealName()));
		cu.setUserName(StringUtil.trimNullValue(user.getUserName()));
		cu.setPoint(user.getPoint());
		cu.setLastLoginTime(DateUtil.getFormatDate(user.getLastLoginDate(), "yyyy-MM-dd HH:mm:ss"));
		Long point = 0L;
		if (user.getPoint() != null) {
			point = user.getPoint();
		}
		cu.setLevel(UserLevelUtils.getLevel(point));
		
		cu.setWithdraw(user.getWithdraw() == null ? "0元" : PriceUtil
				.convertToYuan(user.getWithdraw())
				+ "元");
	
		CashAccountVO moneyAccount = cashAccountService.queryMoneyAccountByUserNo(user.getUserNo());
		
		if(moneyAccount!=null) {
			cu.setAwardBalance(moneyAccount.getBonusBalanceYuan()+"元");
		} else {
			cu.setAwardBalance("0元");
		}
		cu.setCashBalance(moneyAccount.getTotalMoneyYuan() + "元");
		return cu;
	}
	
	protected void sendResult(Map<String,Object> resultMap,Object object,boolean isList){
		if(isList){
			resultMap.put("datas", object==null||"".equals(object)?new ArrayList<Object>():object);
		} else {
			resultMap.put("data",  object==null||"".equals(object)?new HashMap<String,String>():object);
		}
		

		if(this.getRequest().getParameter("fields")!=null){
			JSONObject jsonObj = this.setFields(resultMap, this.getRequest().getParameter("fields"));
			this.sendAjaxResult(jsonObj.toString());
		} else {
			JSONObject json = JSONObject.fromObject(resultMap); 
			String responseJson = json.toString();
			this.sendAjaxResult(responseJson);
		}
		
	}
	
	protected void sendResultV3(Map<String,Object> resultMap,Object object) {
		this.sendResultV3(resultMap, object, false);
	}
	protected void sendResultV3(Map<String,Object> resultMap,Object object,boolean isCheckVersion){

		if (object instanceof Map) {
			Map<String,Object> map = (Map<String, Object>)object;
			if (map.get("datas")!=null) {
				this.copyMap(resultMap, map);
			} else {
				resultMap.put("data", object==null?new HashMap<String, Object>():object);
			}

		} else {
			if(object instanceof List){
				resultMap.put("datas", object==null?new ArrayList<String>():object);
			} else {
				resultMap.put("data",  object==null?new HashMap<String, Object>():object);
			}
		}

		if(this.getRequest().getParameter("fields")!=null){
			//设置过滤的属性值
			JSONObject jsonObj = this.setFields(resultMap, this.getRequest().getParameter("fields"));
			this.sendAjaxResult(jsonObj.toString());
		} else {
			//将data里面的version 移动到最外面
			if(resultMap.get("data") instanceof Map){
				Map<String,Object> reslutMapData = (Map<String,Object>)resultMap.get("data");
				if(reslutMapData!=null&&reslutMapData.get("version")!=null){
					resultMap.put("version", reslutMapData.get("version"));
				}
			}
			
			String responseJson ="";
			if(fieldFilterMap.get(this.getRequest().getParameter("method"))!=null){
				JSONObject jsonObj = this.setFields(resultMap, fieldFilterMap.get(this.getRequest().getParameter("method")));
				 responseJson = jsonObj.toString();
			} else {
				JSONObject jsonObj = JSONObject.fromObject(resultMap);
				 responseJson = jsonObj.toString();
			}
			
			if(isCheckVersion){
				Map<String,Object> r = super.resultMapCreator();
//				try {
//					r.put("version", MD5.encode(responseJson));
//				} catch (NoSuchAlgorithmException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
				if(resultMap.get("data") instanceof Map){
					Map<String,Object> data = (Map<String,Object>)resultMap.get("data");
					//**兼容老版本version
					if(data!=null&&data.get("version")!=null){
						Map<String,Object> tempMap = new HashMap<String,Object>();
						tempMap.put("version", data.get("version"));
						r.put("data",tempMap);
						r.put("version",  data.get("version"));
					} 
				}
				JSONObject jsonObj = JSONObject.fromObject(r);
				 responseJson = jsonObj.toString();
				this.sendAjaxResult(responseJson);
			}  else {
				this.sendAjaxResult(responseJson);
			}
		}
		
	}
	
	protected String objectToXml(Map<String,Object> resultMap,Object object) {
	
        return "";
	}
	
	protected void copyMap(Map<String,Object> targetMap,Map<String,Object> sourceMap){
		Iterator<Map.Entry<String,Object>> it = sourceMap.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry<String,Object> entry = (Map.Entry<String,Object>) it.next();
			String key = entry.getKey();
			Object value = entry.getValue();
			targetMap.put(key, value);
		} 
	}
	

	private JSONObject setFields(Map<String,Object> resultMap,final String fields){
		JsonConfig jsonConfig = new JsonConfig();
		jsonConfig.setJsonPropertyFilter(new PropertyFilter(){  

			public boolean apply(Object source/* 属性的拥有者 */ , String name /*属性名字*/ , Object value/* 属性值 */ ){  
				if(fields.contains(name)){
					return true;
				}
				return false;
			}  
			});
		JSONObject json = JSONObject.fromObject(resultMap,jsonConfig); 
		return json;
	}
	

	
	protected boolean canOrderToDay(Long productId,Map<String,Object> resultMap,String visitTime) throws ParseException{
		List<ProdProductBranch> prodBranchList = prodProductBranchService.getProductBranchByProductId(productId);
		if(prodBranchList.size() == 1){
			if(DateUtils.isSameDay(new Date(), DateUtils.parseDate(visitTime, "yyyy-MM-dd"))){
				ProdProductBranch defaultBranch = prodBranchList.get(0);
				boolean flag = prodProductBranchService.checkPhoneOrderTime(defaultBranch.getProdBranchId());
				if (!flag) {
					this.putErrorMessage(resultMap, "已经超过了可预订时间");
					return false;
				}
			}
		
		}
		return true;
	}
	
	
	
	public Long getProductId() {
		return productId;
	}
	
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
	public void setAppService(AppService appService) {
		this.appService = appService;
	}
	public Long getPage() {
		return page;
	}
	public void setPage(Long page) {
		this.page = page;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public Long getPlaceId() {
		return placeId;
	}
	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}


	public Long getBranchId() {
		return branchId;
	}


	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}


	public String getStage() {
		return stage;
	}


	public void setStage(String stage) {
		this.stage = stage;
	}
	


	public String getVisitTime() {
		return visitTime;
	}


	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}


	public String getUdid() {
		return udid;
	}


	public void setUdid(String udid) {
		this.udid = udid;
	}


	public void setCmtCommentService(CmtCommentService cmtCommentService) {
		this.cmtCommentService = cmtCommentService;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getFirstChannel() {
		return firstChannel;
	}


	public void setFirstChannel(String firstChannel) {
		this.firstChannel = firstChannel;
	}


	public void setComClientService(ComClientService comClientService) {
		this.comClientService = comClientService;
	}


	public String getMobile() {
		return mobile;
	}


	public void setMobile(String mobile) {
		this.mobile = mobile;
	}


	public String getCode() {
		return code;
	}


	public void setCode(String code) {
		this.code = code;
	}

	public void setCashAccountService(CashAccountService cashAccountService) {
		this.cashAccountService = cashAccountService;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public void setLoginChannel(String loginChannel) {
		this.loginChannel = loginChannel;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getUid() {
		return uid;
	}


	public void setUid(String uid) {
		this.uid = uid;
	}


	public String getScreenName() {
		return screenName;
	}


	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

	
	public String getUserName() {
		return userName;
	}


	public String getSecondChannel() {
		return secondChannel;
	}


	public void setSecondChannel(String secondChannel) {
		this.secondChannel = secondChannel;
	}


	public String getLoginChannel() {
		return loginChannel;
	}


	public String getShareChannel() {
		return shareChannel;
	}


	public void setShareChannel(String shareChannel) {
		this.shareChannel = shareChannel;
	}


	public String getShareTarget() {
		return shareTarget;
	}


	public void setShareTarget(String shareTarget) {
		this.shareTarget = shareTarget;
	}


	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}


	public String getIdCard() {
		return idCard;
	}


	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}


	public String getValidateCode() {
		return validateCode;
	}


	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}


	public String getLvversion() {
		return lvversion;
	}


	public void setLvversion(String lvversion) {
		this.lvversion = lvversion;
	}


	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}


	public boolean isTodayOrder() {
		return isTodayOrder;
	}


	public void setTodayOrder(boolean isTodayOrder) {
		this.isTodayOrder = isTodayOrder;
	}


	public String getLvSessionId() {
		return lvSessionId;
	}


	public void setLvSessionId(String lvSessionId) {
		this.lvSessionId = lvSessionId;
	}
}
