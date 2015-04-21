package com.lvmama.clutter.web.hotel;

import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.clutter.exception.LogicException;
import com.lvmama.clutter.exception.NotFoundException;
import com.lvmama.clutter.service.IClientHotelService;
import com.lvmama.clutter.web.Router.CLIENT_ERROR_CODE;
import com.lvmama.clutter.web.place.AppBaseAction;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.service.user.UserUserProxy;

public class HotelAction extends AppBaseAction {
	private static final long serialVersionUID = -617285270812328041L;

	protected IClientHotelService clientHotelService;
	/** 城市ID **/
	private String hotelId;
	/** 房型ID **/
	private String roomTypeId;
	/** 产品ID **/
	private String ratePlanId;
	/** 房间数 **/
	private String numberOfRooms;
	/** 入住客人数 **/
	private String numberOfCustomers;
	/** 客户端ip **/
	private String customerIPAddress;
	/** 总价格 **/
	private String totalPrice;
	/** 到店日期 **/
	private String arrivalDate;
	/** 离店日期 **/
	private String departureDate;
	/** 房间保留至选项 如：20:00 **/
	private String option;

	/** 联系人姓名 **/
	private String contactName;
	/** 联系人手机号码 **/
	private String contactMobile;
	/** 入住人姓名，多个半角逗号","隔开 **/
	private String customerNames;
	/** 用户国籍 **/
	private String customerType;

	/** 信用卡号 **/
	private String ccNo;
	/** 信用卡验证码 **/
	private String ccCvv;
	/** 有效期-年 **/
	private String ccExpirationYear;
	/** 有效期-月 **/
	private String ccExpirationMonth;
	/** 持卡人姓名 **/
	private String ccHolderName;
	/** 证件类型 **/
	private String ccIdType;
	/** 证件号 **/
	private String ccIdNo;
	private String saveCardFlag;
	
	private String firstChannel;//一级渠道
	
	private String secondChannel;//二级级渠道
	
	@Autowired
	protected UserUserProxy userUserProxy;

	@Action(value = "/hotel/create")
	public void create() {
		Map<String, Object> resultMap = super.resultMapCreator();
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("hotelId", hotelId);
		param.put("roomTypeId", roomTypeId);
		param.put("ratePlanId", ratePlanId);
		param.put("numberOfRooms", numberOfRooms);
		param.put("numberOfCustomers", numberOfCustomers);
		param.put("customerIPAddress", customerIPAddress);
		param.put("totalPrice", totalPrice);
		param.put("arrivalDate", arrivalDate);
		param.put("departureDate", departureDate);
		param.put("option", option);
		param.put("contactName", contactName);
		param.put("contactMobile", contactMobile);
		param.put("customerNames", customerNames);
		param.put("customerType", customerType);
		param.put("ccNo", ccNo);
		param.put("ccCvv", ccCvv);
		param.put("ccExpirationYear", ccExpirationYear);
		param.put("ccExpirationMonth", ccExpirationMonth);
		param.put("ccHolderName", ccHolderName);
		param.put("ccIdType", ccIdType);
		param.put("ccIdNo", ccIdNo);
		param.put("saveCardFlag", saveCardFlag);
		param.put("firstChannel", firstChannel);
		param.put("secondChannel", secondChannel);
		UserUser user = this.getUser();
		Map<String, Object> result = null;
		if (null != user) {
			param.put("userNo", user.getUserNo());
			try {
				result = clientHotelService.orderCreate(param);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				// TODO Auto-generated catch block
				String msg = e.getMessage();
				if (e instanceof LogicException) {
					super.putLogicExceptionMessage(resultMap, msg);

				} else {
					this.putExceptionMessage(resultMap, msg);
				}
				e.printStackTrace();
			}
		}else{
			this.putErrorMessage(resultMap, "用户尚未登录");
		}
//		if ("-1".equals(resultMap.get("code"))) {
//			resultMap.put("message", "订单提交失败");
//		}
		this.sendResultV3(resultMap, result);
	}

	@Action(value = "/hotel/getCreditCard")
	public void getCreditCard() {
		Map<String, Object> resultMap = super.resultMapCreator();
		Map<String, Object> param = new HashMap<String, Object>();
		Map<String, Object> result = null;
		UserUser user = this.getUser();
		if (null != user) {
			param.put("userNo", user.getUserNo());
			try {
				result = clientHotelService.getUserCreditCards(param);
			} catch (Exception e) {
				String msg = e.getMessage();
				if (e instanceof LogicException) {
					super.putLogicExceptionMessage(resultMap, msg);

				} else {
					this.putExceptionMessage(resultMap, msg);
				}
				e.printStackTrace();
			}
		}else{
			this.putErrorMessage(resultMap, "用户尚未登录");
		}
		if ("-1".equals(resultMap.get("code"))) {
			resultMap.put("message", "获取信用卡信息失败");
		}
		/**
		 * 直接从数据库取用户信息，避免缓存未刷新 数据部一致的问题。
		 */
		user = userUserProxy.getUserUserByUserNo(user.getUserNo());
		resultMap.put("saveCreditCard", "Y".equals(user.getSaveCreditCard())?true:false);
		this.sendResultV3(resultMap, result);
	}

	public void setClientHotelService(IClientHotelService clientHotelService) {
		this.clientHotelService = clientHotelService;
	}

	public void setHotelId(String hotelId) {
		this.hotelId = hotelId;
	}

	public void setRoomTypeId(String roomTypeId) {
		this.roomTypeId = roomTypeId;
	}

	public void setRatePlanId(String ratePlanId) {
		this.ratePlanId = ratePlanId;
	}

	public void setNumberOfRooms(String numberOfRooms) {
		this.numberOfRooms = numberOfRooms;
	}

	public void setNumberOfCustomers(String numberOfCustomers) {
		this.numberOfCustomers = numberOfCustomers;
	}

	public void setCustomerIPAddress(String customerIPAddress) {
		this.customerIPAddress = customerIPAddress;
	}

	public void setTotalPrice(String totalPrice) {
		this.totalPrice = totalPrice;
	}

	public void setArrivalDate(String arrivalDate) {
		this.arrivalDate = arrivalDate;
	}

	public void setDepartureDate(String departureDate) {
		this.departureDate = departureDate;
	}

	public void setOption(String option) {
		this.option = option;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public void setContactMobile(String contactMobile) {
		this.contactMobile = contactMobile;
	}

	public void setCustomerNames(String customerNames) {
		this.customerNames = customerNames;
	}

	public void setCcNo(String ccNo) {
		this.ccNo = ccNo;
	}

	public void setCcCvv(String ccCvv) {
		this.ccCvv = ccCvv;
	}

	public void setCcExpirationYear(String ccExpirationYear) {
		this.ccExpirationYear = ccExpirationYear;
	}

	public void setCcExpirationMonth(String ccExpirationMonth) {
		this.ccExpirationMonth = ccExpirationMonth;
	}

	public void setCcHolderName(String ccHolderName) {
		this.ccHolderName = ccHolderName;
	}

	public void setCcIdType(String ccIdType) {
		this.ccIdType = ccIdType;
	}

	public void setCcIdNo(String ccIdNo) {
		this.ccIdNo = ccIdNo;
	}

	public void setSaveCardFlag(String saveCardFlag) {
		this.saveCardFlag = saveCardFlag;
	}

	public void setCustomerType(String customerType) {
		this.customerType = customerType;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	public String getFirstChannel() {
		return firstChannel;
	}

	public void setFirstChannel(String firstChannel) {
		this.firstChannel = firstChannel;
	}

	public String getSecondChannel() {
		return secondChannel;
	}

	public void setSecondChannel(String secondChannel) {
		this.secondChannel = secondChannel;
	}
}
