package com.lvmama.comm.pet.po.mark;

import java.util.Date;

public class MarkChannel implements java.io.Serializable {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = 2532834339743069784L;
	
	private Long channelId;
	private String channelName;
	private String channelCode;
	private Date createTime;
	private Long fatherId; // 父ID
	private String valid; // 是否可用
	private Integer layer; // 渠道层级
	private String channelComment; 
	private String range = "INNER";  //站内还是站外
	private String profitSharing = "false"; //是否分成
	
	private String checked; //是否选中，用于下拉框显示
	
	private String channelApplicationType; //渠道类型 只有3级渠道有
	
	/**
	 * 返回中文的渠道范围
	 * @return
	 */
	public String getZhRange() {
		if ("INNER".equals(range)) {
			return  "站内";
		} 
		if ("OUTTER".equals(range)) {
			return  "站外";
		} 
		return "";
	}
	
	/**
	 * 返回是否参与分成
	 * @return
	 */
	public String getZhProfitSharing() {
		if ("false".equals(profitSharing)) {
			return "免费使用";
		} else {
			return "参与分成";
		}
	}
	
	
	public Long getChannelId() {
		return channelId;
	}
	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getChannelCode() {
		return channelCode;
	}
	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Long getFatherId() {
		return fatherId;
	}
	public void setFatherId(Long fatherId) {
		this.fatherId = fatherId;
	}
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid;
	}
	public Integer getLayer() {
		return layer;
	}
	public void setLayer(Integer layer) {
		this.layer = layer;
	}
	public String getChannelComment() {
		return channelComment;
	}
	public void setChannelComment(String channelComment) {
		this.channelComment = channelComment;
	}
	public String getRange() {
		return range;
	}
	public void setRange(String range) {
		this.range = range;
	}
	public String getProfitSharing() {
		return profitSharing;
	}
	public void setProfitSharing(String profitSharing) {
		this.profitSharing = profitSharing;
	}

	public String getChecked() {
		return checked;
	}

	public void setChecked(String checked) {
		this.checked = checked;
	}

	public String getChannelApplicationType() {
		return channelApplicationType;
	}

	public void setChannelApplicationType(String channelApplicationType) {
		this.channelApplicationType = channelApplicationType;
	}
	
}