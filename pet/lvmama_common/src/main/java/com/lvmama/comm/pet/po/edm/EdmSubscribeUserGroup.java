package com.lvmama.comm.pet.po.edm;
/**
 * desc:EDM用户组POJO
 * author:尚正元
 * createDate:20120207
 */
import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

public class EdmSubscribeUserGroup implements Serializable {

	private static final long serialVersionUID = 2314761545904638851L;
	
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	/**
	 * 用户组编号
	 */
	private Long userGroupId;
	/**
	 * 用户组名称
	 */
	private String userGroupName;
	/**
	 * 过滤用户类型
	 */
	private String filterType;
	/**
	 * 用户组状态
	 */
	private String userGroupStatus;
	/**
	 * 用户组所属省份
	 */
	private String userAddress;
	/**
	 * 用户订阅类型
	 */
	private String userSubscribeType;
	/**
	 * 用户邮箱是否已验证
	 */
	private String emailIsValid;
	/**
	 * 手机是否验证
	 */
	private String mobileIsValid;
	/**
	 * 是否根据手机号或邮箱筛选
	 */
	private String emailOrMobile = "N";
	/**
	 * 最后登录时间开始点
	 */
	private Date lastLoginFrom;
	/**
	 * 最后登录时间结束点
	 */
	private Date lastLoginTo;
	/**
	 * 用户注册时间开始点
	 */
	private Date registerDateFrom;
	/**
	 * 用户注册时间结束点
	 */
	private Date registerDateTo;
	/**
	 * 用户登录时间开始点
	 */
	private Date loginDateFrom;
	/**
	 * 用户登录时间结束点
	 */
	private Date loginDateTo;
	/**
	 * 下单开始时间点
	 */
	private Date orderCreateFrom;
	/**
	 * 下单结束时间点
	 */
	private Date orderCreateTo;
	/**
	 * 触发式用户过滤条件SQL
	 */
	private String userGroupTrigger;
	/**
	 * 创建时间
	 */
	private String createDate;
	/**
	 * 创建人
	 */
	private String createUser;
	/**
	 * 修改时间
	 */
	private String updateDate;
	/**
	 * 修改人
	 */
	private String updateUser;
	
	/**
	 * 地域名称
	 */
	private String chUserAddress;
	/**
	 * 订阅类型名称
	 */
	private String chUserSubscribeType;

	/**
	 * 敏感价值起始值
	 */
	private String sensitiveFrom;
	/**
	 * 敏感价值结束值
	 */
	private String sensitiveTo;
	/**
	 * 用户类型
	 */
	private String UserType;
	
	private Integer startRow;
	private Integer endRow;
	public String[] getProvinceArray(){
		if(null != userAddress){
			return	userAddress.split(";");
		}
		return null;
	}

	public String getFormatRegisterDateFrom(){
		return toFormatDate(registerDateFrom);
	}
	public String getFormatRegisterDateTo(){
		return toFormatDate(registerDateTo);
	}
	public String getFormatLoginDateFrom(){
		return toFormatDate(loginDateFrom);
	}
	public String getFormatLoginDateTo(){
		return toFormatDate(loginDateTo);
	}
	public String getFormatLastLoginFrom(){
		return toFormatDate(lastLoginFrom);
	}
	public String getFormatLastLoginTo(){
		return toFormatDate(lastLoginTo);
	}
	public String getFormatOrderCreateFrom(){
		return toFormatDate(orderCreateFrom);
	}
	public String getFormatOrderCreateTo(){
		return toFormatDate(orderCreateTo);
	}
	public String getChEmailIsValidate(){
		return transValidate(emailIsValid);
	}
	public String getChMobileIsValidate(){
		return transValidate(mobileIsValid);
	}
	
	public String getChEmailOrMobile(){
	    if(null != emailOrMobile){
		if(Constant.EDM_STATUS_TYPE.Y.name().equals(emailOrMobile)){
			return "手机筛选";
		}else{
			return "邮箱筛选";
		}
	    }
	    return "";
	}
	private String toFormatDate(Date date){
		if(null != date){
			return DateUtil.getFormatDate(date, DATE_FORMAT);
		}
		return null;
	}
	private String transValidate(final String isValidate){
	    if(null != isValidate){
		if(Constant.EDM_STATUS_TYPE.Y.name().equals(isValidate)){
			return "已验证";
		}else{
			return "未验证";
		}
	    }
	    return "";
	}

	public Long getUserGroupId() {
	    return userGroupId;
	}

	public void setUserGroupId(Long userGroupId) {
	    this.userGroupId = userGroupId;
	}

	public String getUserGroupName() {
	    return userGroupName;
	}

	public void setUserGroupName(String userGroupName) {
	    this.userGroupName = userGroupName;
	}

	public String getFilterType() {
	    return filterType;
	}

	public void setFilterType(String filterType) {
	    this.filterType = filterType;
	}

	public String getUserGroupStatus() {
	    return userGroupStatus;
	}

	public void setUserGroupStatus(String userGroupStatus) {
	    this.userGroupStatus = userGroupStatus;
	}

	public String getUserAddress() {
	    return userAddress;
	}

	public void setUserAddress(String userAddress) {
	    this.userAddress = userAddress;
	}

	public String getUserSubscribeType() {
	    return userSubscribeType;
	}

	public void setUserSubscribeType(String userSubscribeType) {
	    this.userSubscribeType = userSubscribeType;
	}

	public String getEmailIsValid() {
	    return emailIsValid;
	}

	public void setEmailIsValid(String emailIsValid) {
	    this.emailIsValid = emailIsValid;
	}

	public String getMobileIsValid() {
	    return mobileIsValid;
	}

	public void setMobileIsValid(String mobileIsValid) {
	    this.mobileIsValid = mobileIsValid;
	}

	public String getEmailOrMobile() {
	    return emailOrMobile;
	}

	public void setEmailOrMobile(String emailOrMobile) {
	    this.emailOrMobile = emailOrMobile;
	}

	public Date getLastLoginFrom() {
	    return lastLoginFrom;
	}

	public void setLastLoginFrom(Date lastLoginFrom) {
	    this.lastLoginFrom = lastLoginFrom;
	}

	public Date getLastLoginTo() {
	    return lastLoginTo;
	}

	public void setLastLoginTo(Date lastLoginTo) {
	    this.lastLoginTo = lastLoginTo;
	}

	public Date getRegisterDateFrom() {
	    return registerDateFrom;
	}

	public void setRegisterDateFrom(Date registerDateFrom) {
	    this.registerDateFrom = registerDateFrom;
	}

	public Date getRegisterDateTo() {
	    return registerDateTo;
	}

	public void setRegisterDateTo(Date registerDateTo) {
	    this.registerDateTo = registerDateTo;
	}

	public Date getLoginDateFrom() {
	    return loginDateFrom;
	}

	public void setLoginDateFrom(Date loginDateFrom) {
	    this.loginDateFrom = loginDateFrom;
	}

	public Date getLoginDateTo() {
	    return loginDateTo;
	}

	public void setLoginDateTo(Date loginDateTo) {
	    this.loginDateTo = loginDateTo;
	}

	public Date getOrderCreateFrom() {
	    return orderCreateFrom;
	}

	public void setOrderCreateFrom(Date orderCreateFrom) {
	    this.orderCreateFrom = orderCreateFrom;
	}

	public Date getOrderCreateTo() {
	    return orderCreateTo;
	}

	public void setOrderCreateTo(Date orderCreateTo) {
	    this.orderCreateTo = orderCreateTo;
	}

	public String getUserGroupTrigger() {
	    return userGroupTrigger;
	}

	public void setUserGroupTrigger(String userGroupTrigger) {
	    this.userGroupTrigger = userGroupTrigger;
	}

	public String getCreateDate() {
	    return createDate;
	}

	public void setCreateDate(String createDate) {
	    this.createDate = createDate;
	}

	public String getCreateUser() {
	    return createUser;
	}

	public void setCreateUser(String createUser) {
	    this.createUser = createUser;
	}

	public String getUpdateDate() {
	    return updateDate;
	}

	public void setUpdateDate(String updateDate) {
	    this.updateDate = updateDate;
	}

	public String getUpdateUser() {
	    return updateUser;
	}

	public void setUpdateUser(String updateUser) {
	    this.updateUser = updateUser;
	}

	public String getChUserAddress() {
	    return chUserAddress;
	}

	public void setChUserAddress(String chUserAddress) {
	    this.chUserAddress = chUserAddress;
	}

	public String getChUserSubscribeType() {
	    return chUserSubscribeType;
	}

	public void setChUserSubscribeType(String chUserSubscribeType) {
	    this.chUserSubscribeType = chUserSubscribeType;
	}

	public String getSensitiveFrom() {
		return sensitiveFrom;
	}

	public void setSensitiveFrom(String sensitiveFrom) {
		this.sensitiveFrom = sensitiveFrom;
	}

	public String getSensitiveTo() {
		return sensitiveTo;
	}

	public void setSensitiveTo(String sensitiveTo) {
		this.sensitiveTo = sensitiveTo;
	}

	public String getUserType() {
		return UserType;
	}

	public void setUserType(String userType) {
		UserType = userType;
	}

	public Integer getStartRow() {
		return startRow;
	}

	public void setStartRow(Integer startRow) {
		this.startRow = startRow;
	}

	public Integer getEndRow() {
		return endRow;
	}

	public void setEndRow(Integer endRow) {
		this.endRow = endRow;
	}

}
