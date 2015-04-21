package com.lvmama.back.web.insurance;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.utils.ZkMsgCallBack;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.insurance.InsPolicyInfo;
import com.lvmama.comm.bee.service.insurance.PolicyInfoService;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant.POLICY_STATUS;

public class ListUnApprovedPolicyAction extends BaseAction {
	private static final long serialVersionUID = 5848870783655773582L;

	private PolicyInfoService policyInfoService = (PolicyInfoService) SpringBeanProxy
			.getBean("policyInfoService");
	
	private Map<String,Object> searchConds = new HashMap<String,Object>();
	private List<InsPolicyInfo> policyInfos = new ArrayList<InsPolicyInfo>();;
	
	public List<InsPolicyInfo> getPolicyInfos() {
		return policyInfos;
	}

	@Override
	public void doBefore() {
		searchConds.put("valid", "Y");
	}
	
	public void loadDataList() {
		searchConds.put("policyStatus", POLICY_STATUS.UNVERIFIED.name());
		//不显示已取消订单
		searchConds.put("notCancelOrder","true");
		searchConds = initialPageInfoByMap(policyInfoService.countInsPolicyInfo(searchConds), searchConds);
		policyInfos = policyInfoService.query(searchConds);
	}
	
	public void changeValid(String value) {
		searchConds.remove("valid");
		if (!StringUtils.isEmpty(value)) {
			searchConds.put("valid", value);
		}
	}
	
	@SuppressWarnings("rawtypes")
	public void setPolicyValid(Map parameters) {
		Long policyId = (Long) parameters.get("policyId");
		if (null == policyId) {
			ZkMessage.showError("无法找到相关的保单!");
			return;
		}
		InsPolicyInfo policy = policyInfoService.queryByPK(policyId);
		if (null == policy) {
			ZkMessage.showError("无法找到相关的保单!");
			return;
		}
		if (null != parameters.get("valid")) {
			policy.setValid((String) parameters.get("valid"));
		}
		policyInfoService.update(policy);
		this.refreshComponent("search");
	}
	
	/**
	 * 删除此保单
	 * @param parameters
	 */
	@SuppressWarnings("rawtypes")
	public void delete(Map parameters) {
		final Long policyId = (Long) parameters.get("policyId");
		if (null == policyId) {
			ZkMessage.showError("无法找到相关的保单!");
			return;
		}
		ZkMessage.showQuestion("您确定需要永久删除此保单吗?", new ZkMsgCallBack() {
			public void execute() {
				policyInfoService.deleteByPK(policyId);
				refreshComponent("search");
			}
		}, new ZkMsgCallBack() {
			public void execute() {
			}
		});
	}

	public Map<String, Object> getSearchConds() {
		return searchConds;
	}

	public void setSearchConds(Map<String, Object> searchConds) {
		this.searchConds = searchConds;
	}


	
	
}
