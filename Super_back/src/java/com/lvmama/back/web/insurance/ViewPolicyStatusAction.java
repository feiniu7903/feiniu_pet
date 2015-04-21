package com.lvmama.back.web.insurance;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.insurance.InsPolicyInfo;
import com.lvmama.comm.bee.service.InsurantService;
import com.lvmama.comm.bee.service.insurance.PolicyInfoService;
import com.lvmama.comm.pet.po.ins.InsInsurant;
import com.lvmama.comm.spring.SpringBeanProxy;

public class ViewPolicyStatusAction extends BaseAction {
	private static final long serialVersionUID = 1013116938217387512L;
	private String orderId;
	private String prodId;
	
	private InsPolicyInfo policy;
	private List<InsInsurant> insInsurants;
	
	private InsurantService insurantService = (InsurantService) SpringBeanProxy.getBean("insurantService");
	private PolicyInfoService policyInfoService = (PolicyInfoService) SpringBeanProxy.getBean("policyInfoService");
	
	@Override
	public void doBefore() {
		if (null != orderId) {
			orderId = orderId.trim();
		}
		if (null != prodId) {
			prodId = prodId.trim();
		}
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("orderId", new Long(orderId));
		parameters.put("metaProductId", new Long(prodId));
		this.policy = policyInfoService.query(parameters).get(0);
		this.insInsurants = insurantService.queryInsurantsByPolicyId(policy.getPolicyId());
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getProdId() {
		return prodId;
	}

	public void setProdId(String prodId) {
		this.prodId = prodId;
	}

	public InsPolicyInfo getPolicy() {
		return policy;
	}

	public List<InsInsurant> getInsInsurants() {
		return insInsurants;
	}
}
