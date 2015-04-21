package com.lvmama.clutter.xml.lv.po;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

/**
 * 请求的body信息
 * @author dengcheng
 *
 */
public class RequestBody {
	/**
	 * 标的ID
	 */
	private String placeId;
	/**
	 * 标的类型
	 */
	private String stage;
	/**
	 * 产品ID
	 */
	private String productId;
	/**
	 * 每页分页条数
	 */
	private String pageSize;
	/**
	 * 请求获得第几页数据
	 */
	private String page;

	/**
	 * 
	 * 用户名
	 */
	private String userName;
	/**
	 * 密码
	 */
	private String password;
	
	/**
	 * 用户手机号码
	 */
	private String mobile;
	/**
	 * 验证码
	 */
	private String validateCode;
	/**
	 * 选择的日期
	 */
	private String date;
	/**
	 * 提交订单请求信息
	 */
	private List<RequestOrderItem> orderItemList;
	
	/**
	 * 用户ID
	 * 
	 */
	private String userId;
	/**
	 * 意见反馈
	 */
	private String content;
	/**
	 * email
	 */
	private String email;
	/**
	 * 订单id
	 */
	private String orderId;
	
	/**
	 * 游玩时间
	 */
	private String visitTime;
	/**
	 * 离开时间
	 */
	private String leaveTime;
	/**
	 * 支付对象
	 */
	private String payTarget;
	/**
	 * 优惠券
	 */
	private String couponCode;
	/**
	 * 产品类型
	 */
	private String productType;
	/**
	 * 关键字
	 */
	private String keyWord;
	
	private float latitude;
	
	private float longitude;
	
	/**
	 * v2.1arguments
	 */
	private String arguments;
	/**
	 * 要查询的优惠券状态
	 */
	private String couponState;
	/**
	 * 参与活动标志位
	 */
	private String promotionEnabled;
	/**
	 * 类别id
	 */
	private String branchId;
	/**
	 * 设备号
	 */
	
	private String udid = "";
	
	/**
	 * 需要过滤的产品map
	 */
	
	/**
	 * 用户登陆渠道
	 */
	private String loginChannel;
	/**
	 * 第三方uid
	 */
	private String cooperationUid;
	
	/**
	 * 第三方登陆昵称
	 */
	private String screenName;
	
	public String getLoginChannel() {
		return loginChannel;
	}

	public void setLoginChannel(String loginChannel) {
		this.loginChannel = loginChannel;
	}

	public String getCooperationUid() {
		return cooperationUid;
	}

	public void setCooperationUid(String cooperationUid) {
		this.cooperationUid = cooperationUid;
	}

	private Map<String,String> filterProductMap;
	
	public Map<String, String> getFilterProductMap() {
		return filterProductMap;
	}

	public void setFilterProductMap(Map<String, String> filterProductMap) {
		this.filterProductMap = filterProductMap;
	}

	public String getDeviceUserAgent() {
		return deviceUserAgent;
	}

	public void setDeviceUserAgent(String deviceUserAgent) {
		this.deviceUserAgent = deviceUserAgent;
	}

	/**
	 * 设备userAgnet 信息
	 */
	private String deviceUserAgent;
	
	
	public boolean paramterIsError(){
		if(paramters.isEmpty()){
			return false;
		}
		return true;
	}
	
	public List<String> paramters = new ArrayList<String>();
	
	public RequestBody(){
	}

	public String getPlaceId() {
		if (placeId == null || "".equals(StringUtils.trimAllWhitespace(placeId))) {
			paramters.add("placeId");
		}
		return placeId;
	}
	public void setPlaceId(String placeId) {
		this.placeId = placeId;
	}
	public String getStage() {
		if (stage == null || "".equals(StringUtils.trimAllWhitespace(stage))) {
			paramters.add("stage");
		}
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getProductId() {
		if (productId == null || "".equals(StringUtils.trimAllWhitespace(productId))) {
			paramters.add("productId");
		}
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	
	public String getPageSize() {
		if (pageSize == null || "".equals(StringUtils.trimAllWhitespace(pageSize))) {
			paramters.add("pageSize");
		}
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getPage() {
		if (page == null || "".equals(StringUtils.trimAllWhitespace(page))) {
			paramters.add("page");
		}
		return page;
	}
	
	public void setPage(String page) {
		this.page = page;
	}
	
	public String getUserName() {
		if (userName == null || "".equals(StringUtils.trimAllWhitespace(userName))) {
			paramters.add("userName");
		}
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getPassword() {
		if (password == null || "".equals(StringUtils.trimAllWhitespace(password))) {
			paramters.add("password");
		}
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getMobile() {
		if (mobile == null || "".equals(StringUtils.trimAllWhitespace(mobile))) {
			paramters.add("mobile");
		}
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getValidateCode() {
		if (validateCode == null || "".equals(StringUtils.trimAllWhitespace(validateCode))) {
			paramters.add("validateCode");
		}
		return validateCode;
	}
	public void setValidateCode(String validateCode) {
		this.validateCode = validateCode;
	}

	public List<String> getParamters() {
		return paramters;
	}

	public void setParamters(List<String> paramters) {
		this.paramters = paramters;
	}

	public String getDate() {
		if (date == null || "".equals(StringUtils.trimAllWhitespace(date))) {
			paramters.add("date");
		}
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public List<RequestOrderItem> getOrderItemList() {
		return orderItemList;
	}

	public void setOrderItemList(List<RequestOrderItem> orderItemList) {
		this.orderItemList = orderItemList;
	}

	public String getUserId() {
		if (userId == null || "".equals(StringUtils.trimAllWhitespace(userId))) {
			paramters.add("userId");
		}
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getContent() {
		if (content == null || "".equals(StringUtils.trimAllWhitespace(content))) {
			paramters.add("content");
		}
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getOrderId() {
		if (orderId == null || "".equals(StringUtils.trimAllWhitespace(orderId))) {
			paramters.add("userId");
		}
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getVisitTime() {
		if (visitTime == null || "".equals(StringUtils.trimAllWhitespace(visitTime))) {
			paramters.add("visitTime");
		}
		return visitTime;
	}

	public void setVisitTime(String visitTime) {
		this.visitTime = visitTime;
	}

	public String getLeaveTime() {
		if (leaveTime == null || "".equals(StringUtils.trimAllWhitespace(leaveTime))) {
			paramters.add("leaveTime");
		}
		return leaveTime;
	}

	public void setLeaveTime(String leaveTime) {
		this.leaveTime = leaveTime;
	}

	public String getPayTarget() {
		if (payTarget == null || "".equals(StringUtils.trimAllWhitespace(payTarget))) {
			paramters.add("payTarget");
		}
		return payTarget;
	}

	public void setPayTarget(String payTarget) {
		this.payTarget = payTarget;
	}

	public String getCouponCode() {
		return couponCode;
	}

	public void setCouponCode(String couponCode) {
		this.couponCode = couponCode;
	}

	public String getProductType() {
		if (productType == null || "".equals(StringUtils.trimAllWhitespace(productType))) {
			paramters.add("productType");
		}
		return productType;
	}

	public void setProductType(String productType) {
		this.productType = productType;
	}

	public String getKeyWord() {
		if (keyWord == null || "".equals(StringUtils.trimAllWhitespace(keyWord))) {
			paramters.add("keyWord");
		}
		return keyWord;
	}

	public void setKeyWord(String keyWord) {
		this.keyWord = keyWord;
	}
	
	public float getLatitude() {
		if (latitude == 0f) {
			paramters.add("latitude");
		}
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		if (longitude == 0f) {
			paramters.add("longitude");
		}
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}


	public String getArguments() {
		return arguments;
	}
	public void setArguments(String arguments) {
		this.arguments = arguments;
	}

	public String getCouponState() {
		if (couponState == null || "".equals(StringUtils.trimAllWhitespace(couponState))) {
			paramters.add("couponState");
		}
		return couponState;
	}

	public void setCouponState(String couponState) {
		this.couponState = couponState;
	}

	public String getPromotionEnabled() {
		return promotionEnabled;
	}

	public void setPromotionEnabled(String promotionEnabled) {
		this.promotionEnabled = promotionEnabled;
	}

	public String getBranchId() {
		return branchId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public String getUdid() {
		return udid;
	}

	public void setUdid(String udid) {
		this.udid = udid;
	}

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}
	
	
}
