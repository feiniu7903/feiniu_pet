package com.lvmama.finance.settlement.ibatis.po;

import java.text.SimpleDateFormat;
import java.util.Date;

public class SettlementTarget {
	// 对象名称
	private String targetName;
	// 结算周期
	private String settlementPeriod;
	// 创建时间
	private Date createTime;
	public String getTargetName() {
		return targetName;
	}
	public void setTargetName(String targetName) {
		this.targetName = targetName;
	}
	public String getSettlementPeriod() {
		return settlementPeriod;
	}
	public void setSettlementPeriod(String settlementPeriod) {
		this.settlementPeriod = settlementPeriod;
	}
	public String getCreateTime() {
		String df = "";
		if(null != createTime && !"".equals(createTime)){
			df = new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").format(createTime);					
		}
		return df;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	

}
