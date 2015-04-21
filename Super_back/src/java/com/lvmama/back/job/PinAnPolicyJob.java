package com.lvmama.back.job;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.back.web.utils.insurance.pinan.EaiAhsXml;
import com.lvmama.back.web.utils.insurance.pinan.PinAnOperator;
import com.lvmama.comm.bee.po.insurance.InsPolicyInfo;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.InsurantService;
import com.lvmama.comm.bee.service.insurance.PolicyInfoService;
import com.lvmama.comm.bee.service.insurance.PolicyOperationLogService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.ins.InsInsurant;
import com.lvmama.comm.pet.po.ins.InsPolicyOperationLog;
import com.lvmama.comm.vo.Constant;

/**
 * 平安保险对接的Job 项目名称：branch_super_back 类名称：PinAnPolicyJob 类描述：暂无 创建人：Brian
 * 创建时间：2012-9-25 下午6:49:53 修改人：Brian 修改时间：2012-9-25 下午6:49:53 修改备注：
 * 
 * @version
 */
public class PinAnPolicyJob extends PoliceJob {
	private static final Log LOG = LogFactory.getLog(PinAnPolicyJob.class);

	private static final String POLICY_REQUEST_TEMPLATE = "PINAN_POLICY_REQUEST";
	private static final String POLICY_CANCEL_TEMPLATE = "POLICY_CANCEL";

	private static final int SUPPLIER_ID = 21;

	private InsurantService insurantService;
	private PolicyInfoService policyInfoService;
	private OrderService orderServiceProxy;
	private PolicyOperationLogService policyOperationLogService;
	private PinAnOperator pinAnOperator;

	public void execute() {
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("policyStatus", Constant.POLICY_STATUS.REQUESTED.name());
		parameters.put("policyResultIsNull", true);
		parameters.put("supplierId", SUPPLIER_ID);
		List<InsPolicyInfo> policies = policyInfoService.query(parameters);
		for (InsPolicyInfo policy : policies) {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(policy
					.getOrderId());
			request(policy, order);
		}

		parameters.clear();
		parameters.put("policyStatus", Constant.POLICY_STATUS.CANCELLED.name());
		parameters.put("policyResultIsNull", true);
		parameters.put("supplierId", SUPPLIER_ID);
		policies = policyInfoService.query(parameters);
		for (InsPolicyInfo policy : policies) {
			OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(policy
					.getOrderId());
			cancel(policy, order);
		}
	}

	/**
	 * 申请
	 * 
	 * @param policy
	 * @param order
	 */
	@SuppressWarnings("deprecation")
	private void request(final InsPolicyInfo policy, final OrdOrder order) {
		if (Constant.POLICY_STATUS.REQUESTED.name().equalsIgnoreCase(
				policy.getPolicyStatus())
				&& Constant.POLICY_RESULT.REQUEST_SUCCESS.name()
						.equalsIgnoreCase(policy.getPolicyResult())) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("Policy has requested successfully, need't request again!");
			}
			return;
		}

		List<InsInsurant> insurants = insurantService
				.queryInsurantsByPolicyId(policy.getPolicyId());
		
		if (null == insurants || insurants.isEmpty()) {
			LOG.debug("Cann't find insurants, return...");
			return;
		}

		//当不存在投保人时，补充一个投保人
		boolean isExistsApplicant = false;
		for (InsInsurant insurant : insurants) {
			if (Constant.POLICY_PERSON.APPLICANT.name().equalsIgnoreCase(insurant.getPersonType())) {
				isExistsApplicant = true;
				break;
			}
		}
		if (!isExistsApplicant) {
			InsInsurant insInsurant = new InsInsurant();
			InsInsurant fistInsurant = insurants.get(0); 
			insInsurant.setOrderId(fistInsurant.getOrderId());
			insInsurant.setName(fistInsurant.getName());
			insInsurant.setCertType(fistInsurant.getCertType());
			insInsurant.setCertNo(fistInsurant.getCertNo());
			insInsurant.setMobileNumber(fistInsurant.getMobileNumber());
			insInsurant.setPersonType(Constant.POLICY_PERSON.APPLICANT.name());
			
			insurants.add(insInsurant);
		}

		Date beginTime = (Date) order.getVisitTime().clone();
		Date endTime = (Date) beginTime.clone();
		endTime.setDate(endTime.getDate() + policy.getInsuranceDay() - 1);

		EaiAhsXml eaiAhsXml = null;
		try {
			eaiAhsXml = EaiAhsXml.getInstanceForRequest(
					policy.getActualSettlementPrice() / 100.0, beginTime,
					endTime, String.valueOf(policy.getInsuranceDay()),
					policy.getProductIdSupplier(), insurants);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		if (null == eaiAhsXml) {
			LOG.error("request content generated fail!");
			return;
		}

		InsPolicyOperationLog request_log = new InsPolicyOperationLog();
		request_log.setPolicyId(policy.getPolicyId());
		request_log.setType(Constant.POLICY_LOG_STATUS.REQUEST.name());
		request_log.setContent(eaiAhsXml.toXMLString().getBytes());
		policyOperationLogService.insert(request_log);

		Object rtnContent = pinAnOperator.request(eaiAhsXml);
		
		if (null == rtnContent) {
			LOG.error("request content cann't receive response!");
			return;			
		}

		InsPolicyOperationLog response_log = new InsPolicyOperationLog();
		response_log.setPolicyId(policy.getPolicyId());
		response_log
				.setType(Constant.POLICY_LOG_STATUS.REQUEST_RESPONSE.name());
		response_log.setContent(rtnContent.toString().getBytes());
		policyOperationLogService.insert(response_log);

		try {
			Map<String, Object> rtn = pinAnOperator
					.getRtnDataForRequest(rtnContent);
			if (null != rtn.get("policySerial")) {
				policy.setPolicySerial((String) rtn.get("policySerial"));
			}
			if (null != rtn.get("paRsltMesg")) {
				policy.setPaRsltMesg((String) rtn.get("paRsltMesg"));
			}
			if (null != rtn.get("policyNo")) {
				policy.setPolicyNo((String) rtn.get("policyNo"));
			}
			if (null != rtn.get("validateCode")) {
				policy.setValidateCode((String) rtn.get("validateCode"));
			}
			if ("999999".equals(rtn.get("PA_RSLT_CODE"))) {
				policy.setPolicyResult(Constant.POLICY_RESULT.REQUEST_SUCCESS
						.name());
			} else {
				policy.setPolicyResult(Constant.POLICY_RESULT.REQUEST_FAILURE
						.name());
			}

			LOG.debug("Reqeust complete!");
		} catch (Exception e) {
			policy.setPolicyStatus(Constant.POLICY_RESULT.REQUEST_FAILURE
					.name());
			LOG.error(e.getMessage());
		}
		policy.setPolicyStatus(Constant.POLICY_STATUS.REQUESTED.name());
		policy.setManual("N");
		policyInfoService.update(policy);

		if (policy.getPolicyStatus().equals(
				Constant.POLICY_STATUS.REQUESTED.name())
				&& policy.getPolicyResult().equalsIgnoreCase(
						Constant.POLICY_RESULT.REQUEST_SUCCESS.name())) {
			sendPolicyRequestSMS(POLICY_REQUEST_TEMPLATE, policy, insurants);
		}
	}

	/**
	 * 取消
	 * 
	 * @param policy
	 * @param order
	 */
	private void cancel(final InsPolicyInfo policy, final OrdOrder order) {
		if (StringUtils.isBlank(policy.getPolicyNo())) {
			return;
		}
		String orgPolicyNo = policy.getPolicyNo();
		EaiAhsXml eaiAhsXml = EaiAhsXml.getInstanceForCancel("101083",
				policy.getPolicySerial(), policy.getPolicyNo(),
				policy.getValidateCode());

		InsPolicyOperationLog request_log = new InsPolicyOperationLog();
		request_log.setPolicyId(policy.getPolicyId());
		request_log.setType(Constant.POLICY_LOG_STATUS.CANCEL.name());
		request_log.setContent(eaiAhsXml.toXMLString().getBytes());
		policyOperationLogService.insert(request_log);

		Object rtnContent = pinAnOperator.request(eaiAhsXml);

		if (null == rtnContent) {
			LOG.error("Cancel policy cann't receive response, pls by manual!");
			return;
		}

		InsPolicyOperationLog response_log = new InsPolicyOperationLog();
		response_log.setPolicyId(policy.getPolicyId());
		response_log.setType(Constant.POLICY_LOG_STATUS.CANCEL_RESPONSE.name());
		response_log.setContent(rtnContent.toString().getBytes());
		policyOperationLogService.insert(response_log);

		try {
			Map<String, Object> rtn = pinAnOperator
					.getRtnDataForCancel(rtnContent);
			if (null != rtn.get("paRsltMesg")) {
				policy.setPaRsltMesg((String) rtn.get("paRsltMesg"));
			}
			if ("999999".equals(rtn.get("PA_RSLT_CODE"))) {
				policy.setPolicyNo("");
				policy.setPolicySerial("");
				policy.setValidateCode("");
				policy.setPolicyResult(Constant.POLICY_RESULT.CANCEL_SUCCESS
						.name());
			} else {
				policy.setPolicyResult(Constant.POLICY_RESULT.CANCEL_FAIL
						.name());
			}
			LOG.debug("Cancel policy completed!");
		} catch (Exception e) {
			policy.setPolicyResult(Constant.POLICY_RESULT.CANCEL_FAIL.name());
			LOG.error(e.getMessage());
		}
		policy.setPolicyStatus(Constant.POLICY_STATUS.CANCELLED.name());
		policy.setManual("N");
		policyInfoService.update(policy);

		if (Constant.POLICY_STATUS.CANCELLED.name().equalsIgnoreCase(
				policy.getPolicyStatus())
				&& Constant.POLICY_RESULT.CANCEL_SUCCESS.name()
						.equalsIgnoreCase(policy.getPolicyResult())) {
			sendPolicyCancelSMS(POLICY_CANCEL_TEMPLATE, policy, orgPolicyNo,
					insurantService.queryInsurantsByPolicyId(policy
							.getPolicyId()));
		}
	}

	// setter
	public void setInsurantService(final InsurantService insurantService) {
		this.insurantService = insurantService;
	}

	public void setPolicyInfoService(final PolicyInfoService policyInfoService) {
		this.policyInfoService = policyInfoService;
	}

	public void setOrderServiceProxy(final OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setPolicyOperationLogService(
			final PolicyOperationLogService policyOperationLogService) {
		this.policyOperationLogService = policyOperationLogService;
	}

	public void setPinAnOperator(final PinAnOperator pinAnOperator) {
		this.pinAnOperator = pinAnOperator;
	}
}
