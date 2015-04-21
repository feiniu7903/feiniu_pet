package com.lvmama.comm.bee.po.ebooking;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.lvmama.comm.vo.Constant;

/**
 * 传真任务发送对象. <br/>
 * 每一次的任务发送动作对应此类的一个实例.
 */
public class OrdFaxSend implements Serializable {

	private static final long serialVersionUID = 3478588536498369741L;
	// 发送对象ID.
	private Long faxSendId;
	// 凭证对象ID.
	private Long targetId;
	// 创建时间.
	private Date createTime;
	// 传真服务器回调时间.
	private Date sendTime;
	// 供应商接收传真件的部门名.
	private String toName;
	// 传真号.
	private String fax;
	// 电话号.
	private String tel;
	// 发送状态.
	// 取值为com.lvmama.common.vo.Constant.CODE_TYPE=FAX_STATUS,即COM_CODE表中SET_CODE=FAX_STATUS记录列表.
	private String sendStatus;
	// 操作者姓名.
	private String operatorName;
	// 传真任务列表.
	private List<OrdFaxTask> faxTaskList = new ArrayList<OrdFaxTask>();

	public Long getFaxSendId() {
		return faxSendId;
	}

	public void setFaxSendId(Long faxSendId) {
		this.faxSendId = faxSendId;
	}

	public Long getTargetId() {
		return targetId;
	}

	public void setTargetId(Long targetId) {
		this.targetId = targetId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	/**
	 * 设置传真发送时间. <br/>
	 * 注:<br/>
	 * 自动发送的发送时间为传真服务器发送完毕后的回调super后台的时间; <br/>
	 * 人工发送的发送时间为下载传真模板的时间.
	 * 
	 * @param sendTime
	 */
	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getToName() {
		return toName;
	}

	public void setToName(String toName) {
		this.toName = toName;
	}

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax == null ? null : fax.trim();
	}

	public String getTel() {
		return tel;
	}

	public void setTel(String tel) {
		this.tel = tel == null ? null : tel.trim();
	}

	public String getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(String sendStatus) {
		this.sendStatus = sendStatus == null ? null : sendStatus.trim();
	}

	public List<OrdFaxTask> getFaxTaskList() {
		return faxTaskList;
	}

	public void setFaxTaskList(List<OrdFaxTask> faxTaskList) {
		this.faxTaskList = faxTaskList;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getZhSendStatus() {
		return Constant.FAX_STATUS.getCnName(sendStatus);
	}
}