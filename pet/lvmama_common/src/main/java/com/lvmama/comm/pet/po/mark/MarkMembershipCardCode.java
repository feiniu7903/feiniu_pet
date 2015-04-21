package com.lvmama.comm.pet.po.mark;

import java.io.Serializable;
import java.util.Date;

/**
 * 会员卡号码
 * @author Brian
 *
 */
public class MarkMembershipCardCode implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -5394457725978312461L;
	//会员卡号
	private String cardCode;
	//会员卡批次标识
	private Long cardId;
	//是否使用
	private String used;
	//创建日期
	private Date createTime;
	

	public String getCardCode() {
		return cardCode;
	}
	public void setCardCode(String cardCode) {
		this.cardCode = cardCode;
	}
	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	public String getUsed() {
		return used;
	}
	public void setUsed(String used) {
		this.used = used;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
	
}
