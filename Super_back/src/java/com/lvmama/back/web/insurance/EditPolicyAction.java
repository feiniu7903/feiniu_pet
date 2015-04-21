package com.lvmama.back.web.insurance;

import java.util.List;
import java.util.Map;

import com.lvmama.back.utils.ZkMessage;
import com.lvmama.back.utils.ZkMsgCallBack;
import com.lvmama.back.web.BaseAction;
import com.lvmama.comm.bee.po.insurance.InsPolicyInfo;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderMemo;
import com.lvmama.comm.bee.service.InsurantService;
import com.lvmama.comm.bee.service.insurance.PolicyInfoService;
import com.lvmama.comm.bee.service.insurance.PolicyOperationLogService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.ins.InsInsurant;
import com.lvmama.comm.pet.po.ins.InsPolicyOperationLog;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.utils.IdentityCardUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.CERTIFICATE_TYPE;

public class EditPolicyAction extends BaseAction {
	private static final long serialVersionUID = -6487454373437856150L;

	protected OrderService orderServiceProxy = (OrderService) SpringBeanProxy
			.getBean("orderServiceProxy");
	protected InsurantService insurantService = (InsurantService) SpringBeanProxy
			.getBean("insurantService");
	protected PolicyInfoService policyInfoService = (PolicyInfoService) SpringBeanProxy.getBean("policyInfoService");
	protected PolicyOperationLogService policyOperationLogService = (PolicyOperationLogService) SpringBeanProxy.getBean("policyOperationLogService");
	protected TopicMessageProducer policyMessageProducer = (TopicMessageProducer) SpringBeanProxy
			.getBean("policyMessageProducer");

	protected String orderId;
	protected String policyId;
	protected OrdOrder orderDetail;
	protected List<OrdOrderMemo> orderMemos;
	protected InsPolicyInfo policy;
	protected List<InsInsurant> insInsurants;

	@Override
	public void doBefore() {
		if (orderId != null)
			orderId = orderId.trim();
		this.orderDetail = orderServiceProxy.queryOrdOrderByOrderId(new Long(
				orderId));
		this.orderMemos = orderServiceProxy
				.queryMemoByOrderId(new Long(orderId));
		if (policyId != null)
			policyId = policyId.trim();
		this.policy = policyInfoService.queryByPK(new Long(policyId));
		this.insInsurants = insurantService.queryInsurantsByPolicyId(new Long(
				policyId));
	}

	/**
	 * 查询保单相关人员
	 */
	public void doSearch() {
		this.policy = policyInfoService.queryByPK(new Long(policyId));
		this.insInsurants = insurantService.queryInsurantsByPolicyId(new Long(
				policyId));
		canAddInsurant();
	}
	
	public void showAddInsurant(String uri, Map params) throws Exception {
		policyId = (String) params.get("policyId");
		if (null == policyId) {
			return;
		}
		this.policy = policyInfoService.queryByPK(new Long(policyId));
		this.insInsurants = insurantService.queryInsurantsByPolicyId(new Long(policyId));
		if (!canAddInsurant()) {
			ZkMessage.showError("被投保人数量已经达到规定数额，不能再进行添加被投保人!");
			return;
		}
		super.showWindow(uri, params);
	}
	
	private boolean canAddInsurant() {
		if (insInsurants.size() >= policy.getMetaProductQuantity() + 1) {
			refreshComponent("disableAddInsurant");
			return false;
		} else {
			refreshComponent("enableAddInsurant");
			return true;
		}
	}

	/**
	 * 删除被保险人
	 * 
	 * @param parameters
	 */
	public void del(Map<String, Object> parameters) {
		if (null != parameters.get("personType")
				&& Constant.POLICY_PERSON.APPLICANT.name().equalsIgnoreCase(
						(String) parameters.get("personType"))) {
			ZkMessage.showError("投保人不能被删除，只能被修改!");
			return;
		}
		final Long insurantId = (Long) parameters.get("insurantId");
		if (null != insurantId) {
			ZkMessage.showQuestion("您确定需要删除此被保险人吗?", new ZkMsgCallBack() {
				public void execute() {
					insurantService.deleteByPK(insurantId);	
					refreshComponent("refresh");
				}
			}, new ZkMsgCallBack() {
				public void execute() {
				}
			});
		}
	}

	/**
	 * 请求保单
	 */
	public void request() {
		this.insInsurants = insurantService.queryInsurantsByPolicyId(new Long(
				policyId));
		for (InsInsurant insurant : insInsurants) {
			if (null == insurant.getName() || "".equals(insurant.getName().trim())) {
				ZkMessage.showError("存在姓名为空的数据，请修改后再进行投保!");
				return;
			}
			if (null == insurant.getBirthday() || new java.util.Date().before(insurant.getBirthday())) {
				ZkMessage.showError("存在非法的出生日期，请修改后再进行投保!");
				return;
			}
			if (null == insurant.getMobileNumber() || "".equals(insurant.getMobileNumber().trim())) {
				ZkMessage.showError("存在联系电话为空的数据，请修改后再进行投保!");
				return;
			}
			if (null == insurant.getCertNo() || "".equals(insurant.getCertNo().trim())) {
				ZkMessage.showError("存在证件号码为空的数据，请修改后再进行投保!");
				return;
			}
			if (null == insurant.getCertType()) {
				ZkMessage.showError("存在证件类型为空的数据，请修改后再进行投保!");
				return;
			}
			if (null == insurant.getSex()) {
				ZkMessage.showError("请选择性别!");
				return;
			}
			if (CERTIFICATE_TYPE.ID_CARD.name().equalsIgnoreCase(insurant.getCertType()) && !IdentityCardUtil.verify(insurant.getCertNo())) {
				ZkMessage.showError("存在非法的身份证号码，请修改后再进行投保!");
				return ;
			}
		}
		
		policy = policyInfoService.queryByPK(new Long(policyId));
		policy.setPolicyStatus(Constant.POLICY_STATUS.REQUESTED.name());
		policy.setPolicyResult(null);
		policy.setManual("N");
		policyInfoService.update(policy);
		
		InsPolicyOperationLog log = new InsPolicyOperationLog();
		log.setPolicyId(policy.getPolicyId());
		log.setType(Constant.POLICY_LOG_STATUS.APPROVED.name());
		log.setOperator(getSessionUserRealName());
		policyOperationLogService.insert(log);
		
		ZkMessage.showInfo("保单已成功提交，请稍后去\"保单管理\"查看提交结果");
		this.refreshParent("search");
		this.closeWindow();
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public void setOrderDetail(OrdOrder orderDetail) {
		this.orderDetail = orderDetail;
	}

	public String getOrderId() {
		return orderId;
	}

	public OrdOrder getOrderDetail() {
		return orderDetail;
	}

	public List<OrdOrderMemo> getOrderMemos() {
		return orderMemos;
	}

	public List<InsInsurant> getInsInsurants() {
		return insInsurants;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public InsPolicyInfo getPolicy() {
		return policy;
	}

}
