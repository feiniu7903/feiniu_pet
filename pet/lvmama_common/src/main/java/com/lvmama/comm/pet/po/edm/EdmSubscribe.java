package com.lvmama.comm.pet.po.edm;

/**
 * 邮件订阅用户
 */
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.utils.StringUtil;

public class EdmSubscribe  implements Serializable {

	private static final long serialVersionUID = 501356392641090410L;
	/**
	 * 订阅用户ID号
	 */
	private Long id;
	/**
	 * 订阅邮箱
	 */
	private String email;
	/**
	 *用户省份CODE
	 */
	private String province;
	/**
	 * 用户城市CODE
	 */
	private String city;
	/**
	 * 用户创建时间
	 */
	private Date createDate;
	
	/**
	 * 用户订阅邮件类型集合名
	 */
	private String typeName;
	
	/**
	 * 用户订阅邮件类型
	 */
	private List<EdmSubscribeInfo> infoList;
	
	/**
	 * 会员编号
	 */
	private String userId;
	/**
	 * 出游时间
	 */
	private String travelTime;//以逗号分割
	/**
	 * 最想去的地方
	 */
	private String mustWantedTravel;//以逗号分割
	
	/**
	 * 订阅来源
	 */
	private String channel;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		if(!StringUtil.isEmptyString(email)){
			email = email.toLowerCase();
		}
		return email;
	}
	public void setEmail(String email) {
		if(null!=email){
			this.email = email.toLowerCase();
		}
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public List<EdmSubscribeInfo> getInfoList() {
		return infoList;
	}
	public void setInfoList(List<EdmSubscribeInfo> infoList) {
		this.infoList = infoList;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTravelTime() {
		return travelTime;
	}
	public void setTravelTime(String travelTime) {
		this.travelTime = travelTime;
	}
	public String getMustWantedTravel() {
		return mustWantedTravel;
	}
	public void setMustWantedTravel(String mustWantedTravel) {
		this.mustWantedTravel = mustWantedTravel;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
}
