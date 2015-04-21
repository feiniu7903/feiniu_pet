package com.lvmama.comm.bee.vo.ord;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

public class OrdOrderPerformResourceVO implements java.io.Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2321923230925773289L;
	// 订单号：
	// 取票人
	// 手机号：
	// 预计游玩日期：注，今日预定的需要精确到时分 eg：2012年11月11日08:20
	// 订单状态：code
	// 订单状态:zh 中文的（orderViewStatu）

	/*** 订单ID. */
	private Long orderId;
	/*** 取票人. */
	private String contactName;
	/*** 手机号. */
	private String mobileNumber;
	/*** 游玩时间. */
	private Date visitTime;
	/*** 订单显示状态. */
	private String orderViewStatus;
	/*** 订单状态. */
	private String orderStatus = Constant.ORDER_STATUS.NORMAL.name();
	private String zhOrderViewStatus;
	private String payTo;
	private String addCodeStatus;
	private String validTime;
	private String invalidTime;
	private String addCode;
	/**是否为不定期*/
	private String isAperiodic = "false";
	private String invalidDate;
	private String invalidDateMemo;
		
	private List<OrdOrderItemMeta> ordOrderItemMetaList = new ArrayList<OrdOrderItemMeta>();
	private List<OrdPerson> ordPersonList = new ArrayList<OrdPerson>();

	public String getClientVisitTime(){
		return DateUtil.getDateTime("yyyy年MM月dd日 HH:mm", this.visitTime);
	}
	
	public String getZhOrderViewStatus() {
		return Constant.ORDER_VIEW_STATUS.getCnName(orderViewStatus);
	}

	public void setZhOrderViewStatus(String zhOrderViewStatus) {
		this.zhOrderViewStatus = zhOrderViewStatus;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}



	public OrdPerson getContactPerson() {
		if (this.ordPersonList != null) {
			for (int i = 0; i < this.ordPersonList.size(); i++) {
				OrdPerson person = this.ordPersonList.get(i);
				if (Constant.ORD_PERSON_TYPE.CONTACT.name().equals(person.getPersonType())) {
					return person;
				}
			}
		}
		return null;
	}
	
	
	

	public String getContactName() {
		if(StringUtils.isEmpty(contactName)){
			this.contactName = getContactPerson().getName();
		}
		
		return contactName;
	}

	public void setContactName(String contactName) {
		this.contactName = contactName;
	}

	public String getMobileNumber() {
		if(StringUtils.isEmpty(this.mobileNumber)){
			this.mobileNumber = getContactPerson().getMobile();
		}
		return mobileNumber;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Date getVisitTime() {
		return visitTime;
	}

	public void setVisitTime(Date visitTime) {
		this.visitTime = visitTime;
	}

	public String getOrderViewStatus() {
		return orderViewStatus;
	}

	public void setOrderViewStatus(String orderViewStatus) {
		this.orderViewStatus = orderViewStatus;
	}

	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public List<OrdOrderItemMeta> getOrdOrderItemMetaList() {
		return ordOrderItemMetaList;
	}

	public void setOrdOrderItemMetaList(List<OrdOrderItemMeta> ordOrderItemMetaList) {
		this.ordOrderItemMetaList = ordOrderItemMetaList;
	}

	public List<OrdPerson> getOrdPersonList() {
		return ordPersonList;
	}

	public void setOrdPersonList(List<OrdPerson> ordPersonList) {
		this.ordPersonList = ordPersonList;
	}


	public String getPayTo() {
		return payTo;
	}

	public void setPayTo(String payTo) {
		this.payTo = payTo;
	}

	public String getAddCodeStatus() {
		return addCodeStatus;
	}
	public String getZhAddCodeStatus() {
		return Constant.PASSCODE_USE_STATUS.getCnName(getAddCodeStatus());
	}
	public void setAddCodeStatus(String addCodeStatus) {
		this.addCodeStatus = addCodeStatus;
	}

	public String getValidTime() {
		if(IsAperiodic()) {
			return validTime.replace(" 00:00:00", "") + "," +invalidTime.replace(" 00:00:00", "");
		}
		return validTime;
	}

	public void setValidTime(String validTime) {
		this.validTime = validTime;
	}

	public String getInvalidTime() {
		return invalidTime;
	}

	public void setInvalidTime(String invalidTime) {
		this.invalidTime = invalidTime;
	}

	public String getAddCode() {
		return addCode;
	}

	public void setAddCode(String addCode) {
		this.addCode = addCode;
	}

	public String getIsAperiodic() {
		return isAperiodic;
	}

	public void setIsAperiodic(String isAperiodic) {
		this.isAperiodic = isAperiodic;
	}
	
	public boolean IsAperiodic() {
		return "true".equalsIgnoreCase(isAperiodic)?true:false;
	}

	public String getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(String invalidDate) {
		this.invalidDate = invalidDate;
	}

	/**
	 * 针对不定期订单,校验不可游玩日期 add by shihui
	 * 
	 * true:校验通过
	 * 
	 * false:今日不可游玩
	 * */
	public boolean validateInvalidDate() {
		if(StringUtils.isNotEmpty(invalidDate)) {
			Date nowDate = DateUtil.getDayStart(new Date());
			String[] dateStrs = invalidDate.split(",");
			for (int i = 0; i < dateStrs.length; i++) {
				String[] dStrs = dateStrs[i].split("-");
				Date dStart = DateUtil.toDate(dStrs[0], "yyMMdd");
				if(dStrs.length == 1) {
					if(DateUtils.isSameDay(nowDate, dStart)) {
						return false;
					}
				} else if(dStrs.length> 1){
					Date dEnd = DateUtil.toDate(dStrs[dStrs.length - 1], "yyMMdd");
					if(!nowDate.before(dStart) && !nowDate.after(dEnd)) {
						return false;
					}
				}
			}
		}
		return true;
	}

	public String getInvalidDateMemo() {
		return invalidDateMemo;
	}

	public void setInvalidDateMemo(String invalidDateMemo) {
		this.invalidDateMemo = invalidDateMemo;
	}
}
