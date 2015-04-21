package com.lvmama.back.web.ord.eContract;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;

public class OrdEcontractLogAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6009362741813575637L;
	
	private Long orderId;
	
	private List<ComLog> comLogList = new ArrayList<ComLog>();

	/**
	 * 日志接口
	 */
	private ComLogService comLogService;
	protected void doBefore() throws Exception {
		comLogList = comLogService.queryByParentId("ORD_ECONTRACT",orderId);
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public List<ComLog> getComLogList() {
		return comLogList;
	}
	public void setComLogList(List<ComLog> comLogList) {
		this.comLogList = comLogList;
	}
	public ComLogService getComLogService() {
		return comLogService;
	}
	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
}
