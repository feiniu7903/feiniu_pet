package com.lvmama.comm.pet.po.comment;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.pet.po.place.Place;

/**
 * @author liuyi
 *
 * 商户用户类，两种商户用户：普通商家，驴妈妈商家
 *
 */
public class CmtBusinessUser implements java.io.Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 170490860411597865L;

	/**
	 * 用户ID
	 */
	private long cmtBusinessUserID;
	
	/**
	 * 用户标识
	 */
	private String userID;
	
	/**
	 * 用户密码
	 */
	private String password;
	
	/**
	 * 用户类型
	 */
	private String userType;
	
	/**
	 * 用户是否有效
	 */
	private String isValid = "Y";
	
	/**
	 * 普通商户所关联的地标列表
	 */
	private List<Place> placeList = new ArrayList<Place>();
	
    /**
     * 用户账号创建日期
     */
	private Date createDate;
	
	/**
	 * 用户账号更新日期
	 */
	private Date updateDate;
	
	/**
	 * 用户名称
	 */
	private String userName;
	
	/**
	 * 标的名称列表字符串 name1,name2,...
	 */
	private String placeNameListString;
	
	/**
	 * 标的ID列表字符串 id1,id2,...
	 */
	private String placeIDListString;
	
	
	public void setCmtBusinessUserID(long cmtBusinessUserID) {
		this.cmtBusinessUserID = cmtBusinessUserID;
	}
	public long getCmtBusinessUserID() {
		return cmtBusinessUserID;
	}

	public void setUserID(String userID) {
		this.userID = userID;
	}
	public String getUserID() {
		return userID;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public void setUserType(String userType) {
		this.userType = userType;
	}
	public String getUserType() {
		return userType;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setPlaceList(List<Place> placeList) {
		this.placeList = placeList;
		setPlacesNameStringAndPlacesIDString(placeList);
	}
	public List<Place> getPlaceList() {
		return placeList;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}
	public Date getUpdateDate() {
		return updateDate;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public void setPlaceNameListString(String placeNameListString) {
		this.placeNameListString = placeNameListString;
	}
	public String getPlaceNameListString() {
		return placeNameListString;
	}
	public void setPlaceIDListString(String placeIDListString) {
		this.placeIDListString = placeIDListString;
	}
	public String getPlaceIDListString() {
		return placeIDListString;
	}
	
	public void setPlacesNameStringAndPlacesIDString(List<Place> placeList)
	{
		String placesID = "";
		String placesName = "";
		if(placeList!= null)
		{
			for(int i = 0; i < placeList.size(); i++)
			{
				String tempPlaceName = ((Place)(placeList.get(i))).getName();
				long tempPlaceID = ((Place)(placeList.get(i))).getPlaceId();
				if(tempPlaceName!= null && tempPlaceName != "")
				{
					placesName += tempPlaceName + ",";
				}
				
				placesID += tempPlaceID + ",";
			}
			if(placesName.endsWith(","))
			{
				placesName = placesName.substring(0, placesName.length()-1); // remove ,
			}
			
			if(placesID.endsWith(","))
			{
				placesID = placesID.substring(0, placesID.length()-1); // remove ,
			}
		}
		
		this.setPlaceNameListString(placesName);
		this.setPlaceIDListString(placesID);
	}
}
