package com.lvmama.back.job;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.client.HttpClient;

import com.lvmama.back.web.utils.insurance.dazhong.DaZhongPolicy;
import com.lvmama.comm.bee.po.insurance.InsPolicyInfo;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.service.InsurantService;
import com.lvmama.comm.bee.service.insurance.PolicyInfoService;
import com.lvmama.comm.bee.service.insurance.PolicyOperationLogService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.ins.InsInsurant;
import com.lvmama.comm.pet.po.ins.InsPolicyOperationLog;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.utils.ord.DaZhongPolicyUtils;
import com.lvmama.comm.vo.Constant;

/**
 * 大众保险对接的Job 
 * 项目名称：super_back 
 * 类名称：DaZhongPolicyJob 
 * 类描述：暂无 
 * 创建人：Brian
 * 创建时间：2012-9-25 下午6:49:53 
 * 修改人：Brian 
 * 修改时间：2012-9-25 下午6:49:53 
 * 修改备注：
 * 
 * @version
 */
public class DaZhongPolicyJob extends PoliceJob {
	private static final Log LOG = LogFactory.getLog(DaZhongPolicyJob.class);
	
	private static final String POLICY_REQUEST_TEMPLATE = "DAZHONG_POLICY_REQUEST";
	private static final String POLICY_CANCEL_TEMPLATE  = "POLICY_CANCEL";
	
	private static final int SUPPLIER_ID = 6054;
	
	private InsurantService insurantService;
	private PolicyInfoService policyInfoService;
	private OrderService orderServiceProxy;
	private PolicyOperationLogService policyOperationLogService;
	
	public void execute() {
			HttpClient httpClient = HttpsUtil.createHttpClient();
			ArrayList<String> cookies = DaZhongPolicyUtils.createSession(httpClient);
			
			Map<String, Object> parameters = new HashMap<String, Object>();
			parameters.put("policyStatus", Constant.POLICY_STATUS.REQUESTED.name());
			parameters.put("policyResultIsNull", true);
			parameters.put("supplierId", SUPPLIER_ID);
			List<InsPolicyInfo> policies = policyInfoService.query(parameters);
			for (InsPolicyInfo policy : policies) {
				OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(policy.getOrderId());
				request(httpClient, cookies, policy, order);
			}
			
			parameters.clear();
			parameters.put("policyStatus", Constant.POLICY_STATUS.CANCELLED.name());
			parameters.put("policyResultIsNull", true);
			parameters.put("supplierId", SUPPLIER_ID);
			policies = policyInfoService.query(parameters);
			for (InsPolicyInfo policy : policies) {
				OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(policy.getOrderId());
				//OrdOrder order = null;
				cancel(httpClient, cookies, policy, order);
			}
			
			DaZhongPolicyUtils.deleteSession(httpClient, cookies);
	}
	
	private void request(final HttpClient httpClient, final ArrayList<String> cookies,  final InsPolicyInfo policy, final OrdOrder order) {
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
		Calendar endTime = Calendar.getInstance();
		endTime.add(Calendar.DATE, policy.getInsuranceDay() - 1);
		endTime.set(Calendar.HOUR_OF_DAY, 23);
		endTime.set(Calendar.MINUTE, 59);
		endTime.set(Calendar.SECOND, 59);
	
		SimpleDateFormat SDF = new SimpleDateFormat("yyyy-MM-dd");
		if (SDF.format(new Date(System.currentTimeMillis())).equalsIgnoreCase(SDF.format(beginTime.getTime()))) {
			beginTime.setTime(new Date(System.currentTimeMillis()));
			beginTime.set(Calendar.MINUTE, 30);
		}
		
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
		
		DaZhongPolicy policyInfo = new DaZhongPolicy(policy.getProductIdSupplier(), beginTime.getTime(), endTime.getTime(), insurants);		
		InsPolicyOperationLog request_log = new InsPolicyOperationLog();
		request_log.setPolicyId(policy.getPolicyId());
		request_log.setType(Constant.POLICY_LOG_STATUS.REQUEST.name());
		request_log.setContent(policyInfo.toJSONString().getBytes());
		policyOperationLogService.insert(request_log);
		byte[] policyJsonData = policyInfo.toJSONString().getBytes();
		String response = DaZhongPolicyUtils.postPolicies(httpClient, cookies, policyJsonData);
		
		if (null != response) {
			InsPolicyOperationLog response_log = new InsPolicyOperationLog();
			response_log.setPolicyId(policy.getPolicyId());
			response_log.setType(Constant.POLICY_LOG_STATUS.REQUEST_RESPONSE.name());
			response_log.setContent(response.getBytes());
			policyOperationLogService.insert(response_log);	
		
			if ("true".equals(JSONObject.fromObject(response).getString("success"))) {
				policy.setPolicyResult(Constant.POLICY_RESULT.REQUEST_SUCCESS.name());
				String policyNo = JSONObject.fromObject(response).getString("PolicyNumber");
				if (null != policyNo && policyNo.startsWith("[") && policyNo.endsWith("]")) {
					policyNo = policyNo.substring(1);
					policyNo = policyNo.substring(0, policyNo.length() - 1);
				}
				policy.setPolicyNo(policyNo);
				policy.setPaRsltMesg("生成保单成功");
			} else {
				policy.setPolicyResult(Constant.POLICY_RESULT.REQUEST_FAILURE.name());
				policy.setPaRsltMesg(JSONObject.fromObject(response).getString("msg"));
			}
			
			policy.setPolicyStatus(Constant.POLICY_STATUS.REQUESTED.name());
			policy.setManual("N");
			policyInfoService.update(policy);			
		}
		
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
	private void cancel(final HttpClient httpClient, final ArrayList<String> cookies,  final InsPolicyInfo policy, final OrdOrder orderas) {
		if (StringUtils.isBlank(policy.getPolicyNo())) {
			return;
		}
		String orgPolicyNo = policy.getPolicyNo();
		

		InsPolicyOperationLog request_log = new InsPolicyOperationLog();
		request_log.setPolicyId(policy.getPolicyId());
		request_log.setType(Constant.POLICY_LOG_STATUS.CANCEL.name());
		request_log.setContent("".getBytes());
		policyOperationLogService.insert(request_log);

		Object rtnContent = DaZhongPolicyUtils.cancelPolicy(httpClient, cookies, orgPolicyNo);

		if (null == rtnContent) {		
			policy.setPolicyResult(Constant.POLICY_RESULT.CANCEL_FAIL	.name());
			policy.setPaRsltMesg("废保请求无响应");
			policy.setManual("N");
			policyInfoService.update(policy);
			
			LOG.error("废保请求无响应，需要手工废保!");
			return;
		}

		InsPolicyOperationLog response_log = new InsPolicyOperationLog();
		response_log.setPolicyId(policy.getPolicyId());
		response_log.setType(Constant.POLICY_LOG_STATUS.CANCEL_RESPONSE.name());
		response_log.setContent(rtnContent.toString().getBytes());
		policyOperationLogService.insert(response_log);

		try {
			if ("true".equals(JSONObject.fromObject(rtnContent).getString("success"))) {
				policy.setPolicyNo("");
				policy.setPolicyResult(Constant.POLICY_RESULT.CANCEL_SUCCESS
						.name());
				policy.setPaRsltMesg("注销成功");
			} else {
				policy.setPolicyResult(Constant.POLICY_RESULT.CANCEL_FAIL	.name());
				policy.setPaRsltMesg(JSONObject.fromObject(rtnContent).getString("msg"));
			}
			LOG.info("保单取消执行完成!");
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
