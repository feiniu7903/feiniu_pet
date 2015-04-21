package com.lvmama.back.web.insurance;

import java.util.List;

import com.lvmama.comm.bee.po.insurance.InsPolicyInfo;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderMemo;
import com.lvmama.comm.bee.service.insurance.PolicyInfoService;
import com.lvmama.comm.bee.service.insurance.PolicyOperationLogService;
import com.lvmama.comm.pet.po.ins.InsInsurant;
import com.lvmama.comm.pet.po.ins.InsPolicyOperationLog;
import com.lvmama.comm.spring.SpringBeanProxy;

public class ViewPolicyAction extends EditPolicyAction {
	private static final long serialVersionUID = -7514704781668763497L;


	private PolicyInfoService policyInfoService = (PolicyInfoService) SpringBeanProxy
			.getBean("policyInfoService");
	private PolicyOperationLogService policyOperationLogService = (PolicyOperationLogService) SpringBeanProxy.getBean("policyOperationLogService");

	private InsPolicyInfo policy;
	
	private List<InsPolicyOperationLog> policyOperationLogs;


	@Override
	public void doBefore() {
		if (policyId != null)
			policyId = policyId.trim();
		policy = policyInfoService.queryByPK(new Long(policyId));
		if (null != policy) {
			orderId = policy.getOrderId().toString();
			this.orderDetail = orderServiceProxy.queryOrdOrderByOrderId(policy
					.getOrderId());
			this.orderMemos = orderServiceProxy.queryMemoByOrderId(policy
					.getOrderId());
			this.policyOperationLogs = policyOperationLogService.queryLogByPolicyId(new Long(policyId));
		}
		this.policy = policyInfoService.queryByPK(new Long(policyId));
		this.insInsurants = insurantService.queryInsurantsByPolicyId(new Long(
				policyId));
	}
	
	
	public void doParentSearch() {
		this.refreshParent("search");
		this.closeWindow();
	}	


	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public OrdOrder getOrderDetail() {
		return orderDetail;
	}

	public void setOrderDetail(OrdOrder orderDetail) {
		this.orderDetail = orderDetail;
	}

	public List<OrdOrderMemo> getOrderMemos() {
		return orderMemos;
	}

	public void setOrderMemos(List<OrdOrderMemo> orderMemos) {
		this.orderMemos = orderMemos;
	}

	public List<InsInsurant> getInsInsurants() {
		return insInsurants;
	}

	public void setInsInsurants(List<InsInsurant> insInsurants) {
		this.insInsurants = insInsurants;
	}

	public InsPolicyInfo getPolicy() {
		return policy;
	}

	public void setPolicy(InsPolicyInfo policy) {
		this.policy = policy;
	}


	public List<InsPolicyOperationLog> getPolicyOperationLogs() {
		return policyOperationLogs;
	}


	public void setPolicyOperationLogs(
			List<InsPolicyOperationLog> policyOperationLogs) {
		this.policyOperationLogs = policyOperationLogs;
	}

}
