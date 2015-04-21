package com.lvmama.comm.pet.po.pay;

import java.io.Serializable;
import java.util.Date;

/**
 * 支付 收款公司
 * @author ZHANG Nan
 *
 */
public class PayReceivingCompany implements Serializable {

	/**
	 * @author ZHANG Nan
	 */
	private static final long serialVersionUID = 5297123302676193854L;
	/**
	 * 主键
	 */
	private Long receivingCompanyId;
	/**
	 * 收款公司名称
	 */
	private String receivingCompanyName;
	/**
	 * 使用状态（禁用=FORBIDDEN、启动=ENABLE）
	 */
	private String status;
	/**
	 * 创建时间
	 */
	private Date createTime;
	public long getReceivingCompanyId() {
		return receivingCompanyId;
	}
	public void setReceivingCompanyId(long receivingCompanyId) {
		this.receivingCompanyId = receivingCompanyId;
	}
	public String getReceivingCompanyName() {
		return receivingCompanyName;
	}
	public void setReceivingCompanyName(String receivingCompanyName) {
		this.receivingCompanyName = receivingCompanyName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
}
