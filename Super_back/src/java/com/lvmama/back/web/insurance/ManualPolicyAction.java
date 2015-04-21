package com.lvmama.back.web.insurance;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.insurance.InsPolicyInfo;
import com.lvmama.comm.bee.service.insurance.PolicyInfoService;
import com.lvmama.comm.bee.service.insurance.PolicyOperationLogService;
import com.lvmama.comm.pet.po.ins.InsPolicyOperationLog;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;

public class ManualPolicyAction extends BaseAction {
	private static final long serialVersionUID = 700240534204137582L;
	
	private PolicyInfoService policyInfoService = (PolicyInfoService) SpringBeanProxy.getBean("policyInfoService");
	private PolicyOperationLogService policyOperationLogService = (PolicyOperationLogService) SpringBeanProxy.getBean("policyOperationLogService");
	
	private String policyId;
	private String policySerial;
	private String policyNo;
	private String policyValidateCode;
	
	/**
	 * 人工投保
	 */
	public void submit() {
		if (null == policySerial || 
				"".equals(policySerial.trim()) || 
				null == policyNo || 
				"".equals(policyNo.trim()) ||
				null == policyValidateCode ||
				"".equals(policyValidateCode.trim())) {
			ZkMessage.showError("保单的流水号，保单号和验证码，任何一项都不能为空!");
			return;
		}
		
		InsPolicyInfo insPolicyInfo = policyInfoService.queryByPK(new Long(policyId));
		insPolicyInfo.setPolicySerial(policySerial.trim());
		insPolicyInfo.setPolicyNo(policyNo.trim());
		insPolicyInfo.setValidateCode(policyValidateCode.trim());
		insPolicyInfo.setPolicyResult(Constant.POLICY_RESULT.REQUEST_SUCCESS.name());
		insPolicyInfo.setPolicyStatus(Constant.POLICY_STATUS.REQUESTED.name());
		insPolicyInfo.setManual("Y");
		policyInfoService.update(insPolicyInfo);
		
		InsPolicyOperationLog log = new InsPolicyOperationLog();
		log.setPolicyId(insPolicyInfo.getPolicyId());
		log.setType(Constant.POLICY_LOG_STATUS.MANUAL_REQUEST.name());
		log.setOperator(getSessionUserRealName());
		policyOperationLogService.insert(log);
		
		ZkMessage.showError("人工投保已经成功!");
		this.refreshParent("refreshParent");
		this.closeWindow();
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getPolicySerial() {
		return policySerial;
	}

	public void setPolicySerial(String policySerial) {
		this.policySerial = policySerial;
	}

	public String getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	public String getPolicyValidateCode() {
		return policyValidateCode;
	}

	public void setPolicyValidateCode(String policyValidateCode) {
		this.policyValidateCode = policyValidateCode;
	}	

}
