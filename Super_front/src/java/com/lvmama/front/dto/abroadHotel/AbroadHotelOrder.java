package com.lvmama.front.dto.abroadHotel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.httpclient.util.DateUtil;

public class AbroadHotelOrder implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7039432535092781571L;
	
	/**
	 * 订单编号
	 */
	private String orderId;

	/**
	 * 订单所属人
	 */
	private String userId;
	/**
	 * 订单对应的酒店
	 */
	private AbroadHotelProduct product = new AbroadHotelProduct();
	
	/**
	 * 订单对应的房间列表
	 */
	private List<AbroadHotelRoom> rooms = new ArrayList<AbroadHotelRoom>();
	
	/**
	 * 订单对应的入住人列表
	 */
	private List<AbroadHotelPerson> persons = new ArrayList<AbroadHotelPerson>();
	
	/**
	 * 订单对应联系人
	 */
	private AbroadHotelPerson contact;
	
	/**
	 * 订单备注信息
	 */
	private String remark;
	
	/**
	 * 支付金额
	 */
	private Integer actualPay;
	
	/**
	 * 创建时间
	 */
	private Date createDate;
	
	/**
	 * 最晚取消时间
	 */
	private Date cancelDate;
	
	/**
	 * 取得成人数
	 * @return
	 */
	public Integer getAdults(){
		return getPersonsByParam(Boolean.FALSE);
	}
	
	/**
	 * 取得儿童数
	 * @return
	 */
	public Integer getChilds(){
		 return getPersonsByParam(Boolean.TRUE);
	}
	
	/**
	 * 取得联系人信息
	 * @return
	 */
	public AbroadHotelPerson getContact() {
		if(contact == null && null != persons){
			for(AbroadHotelPerson person:persons){
				if(null != person.getClientEmail()){
					contact = person;
				}
			}
		}
		if(null == contact){
			contact = new AbroadHotelPerson();
		}
		return contact;
	}
	
	/**
	 * 取得中文显示的最晚取消时间
	 * @return
	 */
	public String getCancelDateZh(){
		//一天
		final long ONE_DAY = 1000*60*60*24;
		//时间格式
		final String ZH_DATE_FORMAT = "yyyy年MM月dd日 HH:mm";
		if(null == cancelDate){
			if(null != createDate){
				cancelDate = new Date(createDate.getTime()+ONE_DAY);
			}else{
				cancelDate = new Date((new Date()).getTime()+ONE_DAY);
			}
		}
		return DateUtil.formatDate(cancelDate,ZH_DATE_FORMAT);
	}
	/**
	 * 根据是否儿童取得入住人数
	 * @param isChild
	 * @return
	 */
	private Integer getPersonsByParam(boolean isChild){
		Integer count = 0;
		if(null != persons){
			for(AbroadHotelPerson person: persons){
				if(isChild == person.getIsChild()){
					count +=1;
				}
			}
		}
		return count;
	}
	
	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public AbroadHotelProduct getProduct() {
		return product;
	}

	public void setProduct(AbroadHotelProduct product) {
		this.product = product;
	}

	public List<AbroadHotelRoom> getRooms() {
		return rooms;
	}

	public void setRooms(List<AbroadHotelRoom> rooms) {
		this.rooms = rooms;
	}

	public List<AbroadHotelPerson> getPersons() {
		return persons;
	}

	public void setPersons(List<AbroadHotelPerson> persons) {
		this.persons = persons;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Integer getActualPay() {
		return actualPay;
	}

	public void setActualPay(Integer actualPay) {
		this.actualPay = actualPay;
	}

	public Date getCancelDate() {
		return cancelDate;
	}

	public void setCancelDate(Date cancelDate) {
		this.cancelDate = cancelDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}
}
