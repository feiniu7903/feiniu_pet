package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.vo.Constant;

/**
 * 不定期订单关联类
 * 
 * @author shihui
 * */
public class OrdOrderItemMetaAperiodic implements Serializable {

	private static final long serialVersionUID = 3313730352142263515L;

	private Long aperiodicId;

	/**
	 * 订单子子项id
	 * */
	private Long orderItemMetaId;

	/**
	 * 密码券
	 * */
	private String passwordCertificate;

	/**
	 * 激活状态
	 * */
	private String activationStatus;

	/**
	 * 有效开始日期
	 * */
	private Date validBeginTime;

	/**
	 * 有效结束日期
	 * */
	private Date validEndTime;
	
	private Long orderId;
	
	/**
	 * 使用时间
	 * */
	private Date usedTime;
	
	/**
	 * 不可游玩日期
	 * */
	private String invalidDate;
	/**
	 * 不可游玩日期描述信息
	 * */
	private String invalidDateMemo;
	
	public Long getAperiodicId() {
		return aperiodicId;
	}

	public void setAperiodicId(Long aperiodicId) {
		this.aperiodicId = aperiodicId;
	}

	public String getPasswordCertificate() {
		return passwordCertificate;
	}

	public void setPasswordCertificate(String passwordCertificate) {
		this.passwordCertificate = passwordCertificate;
	}

	public Date getValidBeginTime() {
		return validBeginTime;
	}

	public void setValidBeginTime(Date validBeginTime) {
		this.validBeginTime = validBeginTime;
	}

	public Date getValidEndTime() {
		return validEndTime;
	}

	public void setValidEndTime(Date validEndTime) {
		this.validEndTime = validEndTime;
	}

	public String getZhActivationStatus() {
		return Constant.APERIODIC_ACTIVATION_STATUS.getCnName(this.activationStatus);
	}

	public String getActivationStatus() {
		return activationStatus;
	}

	public void setActivationStatus(String activationStatus) {
		this.activationStatus = activationStatus;
	}

	public Long getOrderItemMetaId() {
		return orderItemMetaId;
	}

	public void setOrderItemMetaId(Long orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Date getUsedTime() {
		return usedTime;
	}

	public void setUsedTime(Date usedTime) {
		this.usedTime = usedTime;
	}

	public String getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(String invalidDate) {
		this.invalidDate = invalidDate;
	}
	
	/**
	 * 校验不可游玩日期
	 * 
	 * true:校验通过
	 * 
	 * false:今日不可游玩
	 * */
	public boolean validateInvalidDate(String visitTime) {
		if(StringUtils.isNotEmpty(invalidDate)) {
			Date visitDate = DateUtil.getDayStart(new Date());
			if(StringUtils.isNotEmpty(visitTime)) {
				visitDate = DateUtil.toDate(visitTime, "yyyy-MM-dd");
			}
			String[] dateStrs = invalidDate.split(",");
			for (int i = 0; i < dateStrs.length; i++) {
				String[] dStrs = dateStrs[i].split("-");
				Date dStart = DateUtil.toDate(dStrs[0], "yyMMdd");
				if(dStrs.length == 1) {
					if(DateUtils.isSameDay(visitDate, dStart)) {
						return false;
					}
				} else if(dStrs.length> 1){
					Date dEnd = DateUtil.toDate(dStrs[dStrs.length - 1], "yyMMdd");
					if(!visitDate.before(dStart) && !visitDate.after(dEnd)) {
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