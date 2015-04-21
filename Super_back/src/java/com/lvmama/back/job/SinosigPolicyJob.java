package com.lvmama.back.job;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.back.web.utils.insurance.sinosig.SinosigPolicyCancel;
import com.lvmama.back.web.utils.insurance.sinosig.SinosigPolicyRequest;
import com.lvmama.comm.bee.po.insurance.InsPolicyInfo;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.InsurantService;
import com.lvmama.comm.bee.service.insurance.PolicyInfoService;
import com.lvmama.comm.bee.service.insurance.PolicyOperationLogService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.ins.InsInsurant;
import com.lvmama.comm.pet.po.ins.InsPolicyOperationLog;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class SinosigPolicyJob extends PoliceJob {
	private static final Log LOG = LogFactory.getLog(DaZhongPolicyJob.class);
	
	private static final int SUPPLIER_ID = 6761;
	
	private InsurantService insurantService;
	private PolicyInfoService policyInfoService;
	private OrderService orderServiceProxy;
	private PolicyOperationLogService policyOperationLogService;
	
	public void execute() {		
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("policyStatus", Constant.POLICY_STATUS.REQUESTED.name());
			parameters.put("policyResultIsNull", true);
			parameters.put("supplierId", SUPPLIER_ID);
			List<InsPolicyInfo> policies = policyInfoService.query(parameters);
			for (InsPolicyInfo policy : policies) {
				OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(policy.getOrderId());
				request(policy, order);
			}
			
			parameters.clear();
			parameters.put("policyStatus", Constant.POLICY_STATUS.CANCELLED.name());
			parameters.put("policyResultIsNull", true);
			parameters.put("supplierId", SUPPLIER_ID);
			policies = policyInfoService.query(parameters);
			for (InsPolicyInfo policy : policies) {
				OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(policy.getOrderId());
				cancel(policy, order);
			}
	}
	
	private void request(final InsPolicyInfo policy, final OrdOrder order) {
		if (Constant.POLICY_STATUS.REQUESTED.name().equalsIgnoreCase(policy.getPolicyStatus()) &&
				Constant.POLICY_RESULT.REQUEST_SUCCESS.name().equalsIgnoreCase(policy.getPolicyResult())) {
			if (LOG.isDebugEnabled()) {
				LOG.debug("保单已经成功申报，无需再次申报!");
			}
			return;
		}
		
		List<InsInsurant> insurants = insurantService.queryInsurantsByPolicyId(policy.getPolicyId());
		
		Calendar beginTime = Calendar.getInstance();
		beginTime.setTime((Date) order.getVisitTime().clone());
		Calendar endTime = (Calendar) beginTime.clone();
		endTime.add(Calendar.DATE, policy.getInsuranceDay() - 1);
		endTime.set(Calendar.HOUR_OF_DAY, 23);
		endTime.set(Calendar.MINUTE, 59);
		endTime.set(Calendar.SECOND, 59);
			
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
		
		SinosigPolicyRequest policyInfo = new SinosigPolicyRequest(order.getOrderId(), insurants, beginTime.getTime(), endTime.getTime(), policy.getInsuranceDay(), policy.getProductIdSupplier(), policy.getProductTypeSupplier(), PriceUtil.convertToYuan(policy.getActualSettlementPrice()));	
		InsPolicyOperationLog request_log = new InsPolicyOperationLog();
		request_log.setPolicyId(policy.getPolicyId());
		request_log.setType(Constant.POLICY_LOG_STATUS.REQUEST.name());
		request_log.setContent(policyInfo.toString().getBytes());
		policyOperationLogService.insert(request_log);
		
		String response = policyInfo.request(policyInfo.toString(), false);
		
		if (null != response) {
			InsPolicyOperationLog response_log = new InsPolicyOperationLog();
			response_log.setPolicyId(policy.getPolicyId());
			response_log.setType(Constant.POLICY_LOG_STATUS.REQUEST_RESPONSE.name());
			response_log.setContent(response.getBytes());
			policyOperationLogService.insert(response_log);	
		
			if (response.indexOf("RETURN=\"true\"") != -1) {
				policy.setPolicyResult(Constant.POLICY_RESULT.REQUEST_SUCCESS.name());
				
				String policyNo = order.getOrderId() + "";
				
				policy.setPolicyNo(policyNo);
				policy.setPaRsltMesg("生成保单成功");
			} else {
				policy.setPolicyResult(Constant.POLICY_RESULT.REQUEST_FAILURE.name());
				String msg = null;
				int start = response.indexOf("INFO=\"");
				int end = response.substring(start + "INFO=\"".length()).indexOf("\"");
				if (-1 != start && -1 != end) {
					msg = response.substring(start + "INFO=\"".length() , start + "INFO=\"".length() + end);
				}
				policy.setPaRsltMesg(msg);
			}
			
			policy.setPolicyStatus(Constant.POLICY_STATUS.REQUESTED.name());
			policy.setManual("N");
			policyInfoService.update(policy);			
		}
		
//		if (policy.getPolicyStatus().equals(
//				Constant.POLICY_STATUS.REQUESTED.name())
//				&& policy.getPolicyResult().equalsIgnoreCase(
//						Constant.POLICY_RESULT.REQUEST_SUCCESS.name())) {
//			sendPolicyRequestSMS(POLICY_REQUEST_TEMPLATE, policy, insurants);
//		}
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

		SinosigPolicyCancel taiPinPensionPolicyCancel = new SinosigPolicyCancel(policy.getPolicyNo());
		String content = taiPinPensionPolicyCancel.toString();
		
		InsPolicyOperationLog request_log = new InsPolicyOperationLog();
		request_log.setPolicyId(policy.getPolicyId());
		request_log.setType(Constant.POLICY_LOG_STATUS.CANCEL.name());
		request_log.setContent(content.getBytes());
		policyOperationLogService.insert(request_log);

		String rtnContent = taiPinPensionPolicyCancel.request(content,true);

		if (null == rtnContent) {		
			policy.setPolicyResult(Constant.POLICY_RESULT.CANCEL_FAIL.name());
			policy.setPaRsltMesg("废保请求无响应");
			policy.setManual("N");
			policyInfoService.update(policy);
			
			LOG.error("废保请求无响应，需要手工废保!");
			return;
		} else {
			InsPolicyOperationLog response_log = new InsPolicyOperationLog();
			response_log.setPolicyId(policy.getPolicyId());
			response_log.setType(Constant.POLICY_LOG_STATUS.CANCEL_RESPONSE.name());
			response_log.setContent(rtnContent.toString().getBytes());
			policyOperationLogService.insert(response_log);
			
			if (rtnContent.indexOf("RETURN=\"true\"") != -1) {
				policy.setPolicyNo("");
				policy.setPolicyResult(Constant.POLICY_RESULT.CANCEL_SUCCESS
						.name());
				policy.setPaRsltMesg("注销成功");
			} else {
				policy.setPolicyResult(Constant.POLICY_RESULT.CANCEL_FAIL.name());
				
				int start = rtnContent.indexOf("INFO=\"");
				int end = rtnContent.substring(start + "INFO=\"".length()).indexOf("\"");
				if (-1 != start && -1 != end) {
					policy.setPaRsltMesg(rtnContent.substring(start + "INFO=\"".length() , start + "INFO=\"".length() + end));
				}
				
			}
			LOG.info("保单取消执行完成!");
			
			policy.setPolicyStatus(Constant.POLICY_STATUS.CANCELLED.name());
			policy.setManual("N");
			policyInfoService.update(policy);
		}

//		if (Constant.POLICY_STATUS.CANCELLED.name().equalsIgnoreCase(
//				policy.getPolicyStatus())
//				&& Constant.POLICY_RESULT.CANCEL_SUCCESS.name()
//						.equalsIgnoreCase(policy.getPolicyResult())) {
//			sendPolicyCancelSMS(POLICY_CANCEL_TEMPLATE, policy, orgPolicyNo,
//					insurantService.queryInsurantsByPolicyId(policy
//							.getPolicyId()));
//		}
	}	

	public void setInsurantService(InsurantService insurantService) {
		this.insurantService = insurantService;
	}

	public void setPolicyInfoService(PolicyInfoService policyInfoService) {
		this.policyInfoService = policyInfoService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setPolicyOperationLogService(
			PolicyOperationLogService policyOperationLogService) {
		this.policyOperationLogService = policyOperationLogService;
	}
}
