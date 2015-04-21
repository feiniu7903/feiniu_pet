package com.lvmama.back.sweb.offlinepay;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pay.PayPayment;
import com.lvmama.comm.pet.po.pay.PayPaymentDetail;
import com.lvmama.comm.pet.po.pay.PayPaymentGateway;
import com.lvmama.comm.pet.po.pay.PayPaymentGatewayElement;
import com.lvmama.comm.pet.po.pay.PayReceivingBank;
import com.lvmama.comm.pet.po.pay.PayReceivingCompany;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pay.PayPaymentGatewayElementService;
import com.lvmama.comm.pet.service.pay.PayPaymentGatewayService;
import com.lvmama.comm.pet.service.pay.PayPaymentService;
import com.lvmama.comm.pet.service.pay.PayReceivingBankService;
import com.lvmama.comm.pet.service.pay.PayReceivingCompanyService;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.vo.Constant;

@Results({
		@Result(name = "otherPay", location = "/WEB-INF/pages/back/offlinePay/otherPay.jsp"),
		@Result(name = "otherPayItem", location = "/WEB-INF/pages/back/offlinePay/otherPayItem.jsp") })
public class OtherPayAction extends BaseAction {

	private static final long serialVersionUID = -3962401361268817193L;

	/**
	 * 订单号
	 */
	private Long orderId;

	/**
	 * 支付网关
	 */
	private String paymentGateway;

	/**
	 * 对账流水号
	 */
	private String paymentTradeNo;

	/**
	 * 退款流水号
	 */
	private String refundSerial;

	/**
	 * 网关交易号
	 */
	private String gatewayTradeNo;

	/**
	 * 实付金额
	 */
	private float actualPayFloat;

	/**
	 * 支付交易时间
	 */
	private Date callbackTime;

	/**
	 * 备注
	 */
	private String memo;

	/**
	 * 支付对象
	 */
	private PayPayment payment;

	/**
	 * 支付金额
	 */
	private Long amount;

	/**
	 * 支付主键
	 */
	private Long paymentId;

	/**
	 * 收款公司ID
	 */
	private String receivingCompanyId;

	/**
	 * 收款人
	 */
	private String receivingPerson;

	/**
	 * 我方收款银行卡号
	 */
	private String receivingBankCardNo;

	/**
	 * 支付方户名
	 */
	private String paymentAccount;

	/**
	 * 支付方银行名称
	 */
	private String paymentBankName;

	/**
	 * 支付方银行卡号
	 */
	private String paymentBankCardNo;
	/**
	 * 所属中心
	 */
	private String belongCenter;
	/**
	 * 所属部门
	 */
	private String belongDepartment;
	/**
	 * 名称（对应pay_payment_detail）
	 */
	private String detailName;
	/**
	 * 摘要
	 */
	private String summary;

	/**
	 * 其它支付-非现金网关审核状态(未确认=UNCONFIRMED、已确认=CONFIRM)
	 */
	private String otherAuditStatus;

	private List<PayPaymentGateway> payPaymentGatewayList;

	protected TopicMessageProducer resourceMessageProducer;

	private PayPaymentService payPaymentService;

	private ComLogService comLogService;

	private PayPaymentGatewayService payPaymentGatewayService;

	private PayPaymentGatewayElementService payPaymentGatewayElementService;

	// 是否为admin
	private String isAdmin;

	/**
	 * 控制线下-其它支付可输入项接口
	 */
	private PayPaymentGatewayElement payPaymentGatewayElement;
	/**
	 * 收款公司接口
	 */
	private PayReceivingCompanyService payReceivingCompanyService;
	/**
	 * 收款银行接口
	 */
	private PayReceivingBankService payReceivingBankService;

	private List<PayReceivingCompany> payReceivingCompanyList;

	private PermUserService permUserService;
	private String search;

	@Action(value = "/offlinePay/otherPay")
	public String doOtherPay() {
		Map<String, String> paramMap = new HashMap<String, String>();
		paramMap.put("gatewayType", Constant.PAYMENT_GATEWAY_TYPE.OTHER.name());
		paramMap.put("gatewayStatus",
				Constant.PAYMENT_GATEWAY_STATUS.ENABLE.name());
		paramMap.put("orderby", "PAYMENT_GATEWAY_ID");
		paramMap.put("order", "asc");
		payPaymentGatewayList = payPaymentGatewayService
				.selectPayPaymentGatewayByParamMap(paramMap);
		if (payPaymentGatewayList != null && payPaymentGatewayList.size() > 0) {
			PayPaymentGateway payPaymentGateway = payPaymentGatewayList.get(0);
			payPaymentGatewayElement = payPaymentGatewayElementService
					.selectPaymentGatewayElementByGateway(payPaymentGateway
							.getGateway());
		}
		// 判断用户是否为管理员
		PermUser permUser = (PermUser) ServletUtil.getSession(
				(HttpServletRequest) this.getRequest(),
				(HttpServletResponse) this.getResponse(),
				Constant.SESSION_BACK_USER);
		if (null != permUser && permUser.isAdministrator()) {
			isAdmin = "0";
		}
		this.setActualPayFloat(PriceUtil.convertToYuan(amount));
		return "otherPay";
	}

	@Action(value = "/offlinePay/gatewayChange")
	public String gatewayChange() {
		payPaymentGatewayElement = payPaymentGatewayElementService
				.selectPaymentGatewayElementByGateway(paymentGateway);
		// 初始化收款公司
		initReceivingCompany();
		return "otherPayItem";
	}

	/**
	 * 保存支付.
	 * 
	 * @return String
	 */
	@Action(value = "/offlinePay/otherPaySave")
	public void doSave() {
		JSONResult result = new JSONResult();
		try {
			// 检查参数
			boolean checkResult = checkParam();
			if (!checkResult) {
				return;
			}

			PayPayment payment = new PayPayment();
			payment.setBizType(Constant.PAYMENT_BIZ_TYPE.SUPER_ORDER.name());
			payment.setObjectId(orderId);
			payment.setObjectType("SUPER_ORDER");
			payment.setPaymentType(Constant.PAYMENT_OPERATE_TYPE.PAY.name());
			payment.setPaymentGateway(paymentGateway);
			payment.setAmount(PriceUtil.convertToFen(actualPayFloat));
			payment.setSerial(payment.geneSerialNo());
			payment.setCreateTime(new Date());
			payment.setPaymentTradeNo(paymentTradeNo);
			payment.setStatus(Constant.PAYMENT_SERIAL_STATUS.SUCCESS.name());
			payment.setGatewayTradeNo(gatewayTradeNo);
			payment.setRefundSerial(refundSerial);
			payment.setCallbackTime(callbackTime);
			payment.setCallbackInfo("[线下支付]" + memo);
			payment.setOperator(getOperatorName());

			// 封装支付详情数据
			PayPaymentDetail payPaymentDetail = processPayPaymentDetail();

			// 通过PaymentTradeNo判断支付记录是否已存在
			boolean isExists = payPaymentService
					.isExistsByPaymentTradeNo(payment.getPaymentTradeNo());
			if (!isExists) {
				paymentId = payPaymentService.savePaymentAndDetail(payment,
						payPaymentDetail);
				if (payment != null && payment.isSuccess()) {
					ComLog log = new ComLog();
					log.setObjectId(payment.getPaymentId());
					log.setObjectType("PAY_PAYMENT");
					log.setParentId(payment.getObjectId());
					log.setParentType("ORD_ORDER");
					log.setLogType("PAYMENT_TYPE_OFFLINE");
					log.setLogName("其他线下支付");
					log.setOperatorName(this.getOperatorName());
					log.setContent("由操作人员" + getOperatorName() + "使用"
							+ payment.getPayWayZh() + "线下支付方式支付");
					log.setCreateTime(new Date());
					comLogService.addComLog(log);
					resourceMessageProducer.sendMsg(MessageFactory
							.newPaymentSuccessCallMessage(paymentId));
				}
				result.put("paymentId", paymentId);
			} else {
				result.put("code", "1");
				result.put("msg", "已存在的对账流水号,请重新输入!");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}

	/**
	 * 处理支付详情数据
	 * 
	 * @author ZHANG Nan
	 * @return 支付详情对象
	 */
	private PayPaymentDetail processPayPaymentDetail() {
		PayPaymentDetail payPaymentDetail = new PayPaymentDetail();
		if (StringUtils.isNotBlank(receivingCompanyId)) {
			payPaymentDetail.setReceivingCompanyId(Long
					.parseLong(receivingCompanyId));
		}
		if (StringUtils.isNotBlank(receivingBankCardNo)) {
			PayReceivingBank payReceivingBank = payReceivingBankService
					.selectReceivingBankByBankCardNo(receivingBankCardNo);
			payPaymentDetail.setReceivingBankId(payReceivingBank
					.getReceivingBankId());
		}
		if (StringUtils.isNotBlank(receivingPerson)) {
			payPaymentDetail.setReceivingPerson(receivingPerson);
		}
		if (StringUtils.isNotBlank(paymentAccount)) {
			payPaymentDetail.setPaymentAccount(paymentAccount);
		}
		if (StringUtils.isNotBlank(paymentBankName)) {
			payPaymentDetail.setPaymentBankName(paymentBankName);
		}
		if (StringUtils.isNotBlank(paymentBankCardNo)) {
			payPaymentDetail.setPaymentBankCardNo(paymentBankCardNo);
		}
		if (StringUtils.isNotBlank(belongCenter)) {
			payPaymentDetail.setBelongCenter(belongCenter);
		}
		if (StringUtils.isNotBlank(belongDepartment)) {
			payPaymentDetail.setBelongDepartment(belongDepartment);
		}
		if (StringUtils.isNotBlank(detailName)) {
			payPaymentDetail.setDetailName(detailName);
		}
		if (StringUtils.isNotBlank(summary)) {
			payPaymentDetail.setSummary(summary);
		}

		if (Constant.PAYMENT_GATEWAY_OTHER_MANUAL.CASH.name().equalsIgnoreCase(
				paymentGateway)) {
			// 现金网关 默认为 未解款
			payPaymentDetail
					.setCashAuditStatus(Constant.PAYMENT_DETAIL_CASH_AUDIT_STATUS.UNLIBERATED
							.name());
		} else {
			// 非现金网关 默认为 未确认
			payPaymentDetail
					.setOtherAuditStatus(Constant.PAYMENT_DETAIL_OTHER_AUDIT_STATUS.UNCONFIRMED
							.name());
		}
		return payPaymentDetail;
	}

	private boolean checkParam() {
		PayPaymentGatewayElement payPaymentGatewayElement = payPaymentGatewayElementService
				.selectPaymentGatewayElementByGateway(paymentGateway);
		if (payPaymentGatewayElement != null
				&& Constant.PAYMENT_GATEWAY_ELEMENT_STATUS.ENABLE.name()
						.equals(payPaymentGatewayElement.getStatus())
				&& paymentGateway.equals(payPaymentGatewayElement.getGateway())) {
			if (Boolean.TRUE.toString().toUpperCase()
					.equals(payPaymentGatewayElement.getIsPaymentTradeNo())
					&& StringUtils.isBlank(paymentTradeNo)) {
				return errorOutput("对账流水号不可以为空!");
			}
			if (Boolean.TRUE.toString().toUpperCase()
					.equals(payPaymentGatewayElement.getIsGatewayTradeNo())
					&& StringUtils.isBlank(gatewayTradeNo)) {
				return errorOutput("网关交易号不可以为空!");
			}
			if (Boolean.TRUE.toString().toUpperCase()
					.equals(payPaymentGatewayElement.getIsRefundSerial())
					&& StringUtils.isBlank(refundSerial)) {
				return errorOutput("退款流水号不可以为空!");
			}
			if (Boolean.TRUE.toString().toUpperCase()
					.equals(payPaymentGatewayElement.getIsReceivingCompany())
					&& StringUtils.isBlank(receivingCompanyId)) {
				return errorOutput("收款公司不可以为空!");
			}
			if (Boolean.TRUE.toString().toUpperCase()
					.equals(payPaymentGatewayElement.getIsReceivingPerson())
					&& StringUtils.isBlank(receivingPerson)) {
				return errorOutput("收款人不可以为空!");
			}
			if (Boolean.TRUE.toString().toUpperCase()
					.equals(payPaymentGatewayElement.getIsCallbackTime())
					&& callbackTime == null) {
				return errorOutput("交易时间不可以为空!");
			}
			if (Boolean.TRUE.toString().toUpperCase()
					.equals(payPaymentGatewayElement.getIsPaymentAccount())
					&& StringUtils.isBlank(paymentAccount)) {
				return errorOutput("对方银行户名不可以为空!");
			}
			if (Boolean.TRUE.toString().toUpperCase()
					.equals(payPaymentGatewayElement.getIsPaymentBankName())
					&& StringUtils.isBlank(paymentBankName)) {
				return errorOutput("对方银行名称不可以为空!");
			}
			if (Boolean.TRUE.toString().toUpperCase()
					.equals(payPaymentGatewayElement.getIsPaymentBankCardNo())
					&& StringUtils.isBlank(paymentBankCardNo)) {
				return errorOutput("对方银行账号不可以为空!");
			}
			if (Boolean.TRUE.toString().toUpperCase()
					.equals(payPaymentGatewayElement.getIsBelongCenter())
					&& StringUtils.isBlank(belongCenter)) {
				return errorOutput("所属中心不可以为空!");
			}
			if (Boolean.TRUE.toString().toUpperCase()
					.equals(payPaymentGatewayElement.getIsBelongDepartment())
					&& StringUtils.isBlank(belongDepartment)) {
				return errorOutput("所属部门不可以为空!");
			}
			if (Boolean.TRUE.toString().toUpperCase()
					.equals(payPaymentGatewayElement.getIsDetailName())
					&& StringUtils.isBlank(detailName)) {
				return errorOutput("名称不可以为空!");
			}
			if (Boolean.TRUE.toString().toUpperCase()
					.equals(payPaymentGatewayElement.getIsSummary())
					&& StringUtils.isBlank(summary)) {
				return errorOutput("摘要不可以为空!");
			}
		} else {
			if (StringUtils.isBlank(paymentTradeNo)) {
				return errorOutput("对账流水号不可以为空!");
			}
			if (StringUtils.isBlank(gatewayTradeNo)) {
				return errorOutput("银行交易流水号不可以为空!");
			}
			if (StringUtils.isBlank(refundSerial)) {
				return errorOutput("退款流水号不可以为空!");
			}
			if (callbackTime == null) {
				return errorOutput("交易时间不可以为空!");
			}
		}
		return true;
	}

	/**
	 * 输出错误消息
	 * 
	 * @author ZHANG Nan
	 * @param errorMessage
	 *            错误信息
	 * @return 始终返回false已让主程序终止
	 */
	private boolean errorOutput(String errorMessage) {
		JSONResult result = new JSONResult();
		result.put("code", "1");
		result.put("msg", errorMessage);
		result.output(getResponse());
		return false;
	}

	/**
	 * 初始化收款公司数据
	 * 
	 * @author ZHANG Nan
	 */
	public void initReceivingCompany() {
		Map<String, String> receivingCompanyParamMap = new HashMap<String, String>();
		receivingCompanyParamMap.put("orderby", "CREATE_TIME");
		receivingCompanyParamMap.put("order", "ASC");
		receivingCompanyParamMap.put("status", "ENABLE");
		payReceivingCompanyList = payReceivingCompanyService
				.selectPayReceivingCompanyByParamMap(receivingCompanyParamMap);
	}

	@Action(value = "/offlinePay/searchUserName")
	public void searchUserName() {
		JSONArray array = new JSONArray();
		List<PermUser> permUserList = permUserService
				.selectListByUserNameOrRealName(search);
		for (PermUser permUser : permUserList) {
			JSONObject obj = new JSONObject();
			obj.put("id", permUser.getUserName());
			obj.put("text",permUser.getUserName()+permUser.getRealName());
			array.add(obj);
		}
		JSONOutput.writeJSON(getResponse(), array);
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public PayPaymentService getPayPaymentService() {
		return payPaymentService;
	}

	public void setPayPaymentService(PayPaymentService payPaymentService) {
		this.payPaymentService = payPaymentService;
	}

	public String getPaymentGateway() {
		return paymentGateway;
	}

	public void setPaymentGateway(String paymentGateway) {
		this.paymentGateway = paymentGateway;
	}

	public String getPaymentTradeNo() {
		return paymentTradeNo;
	}

	public void setPaymentTradeNo(String paymentTradeNo) {
		this.paymentTradeNo = paymentTradeNo;
	}

	public float getActualPayFloat() {
		return actualPayFloat;
	}

	public void setActualPayFloat(float actualPayFloat) {
		this.actualPayFloat = actualPayFloat;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setResourceMessageProducer(
			TopicMessageProducer resourceMessageProducer) {
		this.resourceMessageProducer = resourceMessageProducer;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}

	public Date getCallbackTime() {
		return callbackTime;
	}

	public void setCallbackTime(Date callbackTime) {
		this.callbackTime = callbackTime;
	}

	public Long getPaymentId() {
		return paymentId;
	}

	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}

	public String getRefundSerial() {
		return refundSerial;
	}

	public void setRefundSerial(String refundSerial) {
		this.refundSerial = refundSerial;
	}

	public PayPayment getPayment() {
		return payment;
	}

	public void setPayment(PayPayment payment) {
		this.payment = payment;
	}

	public String getGatewayTradeNo() {
		return gatewayTradeNo;
	}

	public void setGatewayTradeNo(String gatewayTradeNo) {
		this.gatewayTradeNo = gatewayTradeNo;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public List<PayPaymentGateway> getPayPaymentGatewayList() {
		return payPaymentGatewayList;
	}

	public void setPayPaymentGatewayList(
			List<PayPaymentGateway> payPaymentGatewayList) {
		this.payPaymentGatewayList = payPaymentGatewayList;
	}

	public void setPayPaymentGatewayService(
			PayPaymentGatewayService payPaymentGatewayService) {
		this.payPaymentGatewayService = payPaymentGatewayService;
	}

	public void setPayPaymentGatewayElementService(
			PayPaymentGatewayElementService payPaymentGatewayElementService) {
		this.payPaymentGatewayElementService = payPaymentGatewayElementService;
	}

	public PayPaymentGatewayElement getPayPaymentGatewayElement() {
		return payPaymentGatewayElement;
	}

	public void setPayPaymentGatewayElement(
			PayPaymentGatewayElement payPaymentGatewayElement) {
		this.payPaymentGatewayElement = payPaymentGatewayElement;
	}

	public String getReceivingCompanyId() {
		return receivingCompanyId;
	}

	public void setReceivingCompanyId(String receivingCompanyId) {
		this.receivingCompanyId = receivingCompanyId;
	}

	public String getReceivingPerson() {
		return receivingPerson;
	}

	public void setReceivingPerson(String receivingPerson) {
		this.receivingPerson = receivingPerson;
	}

	public TopicMessageProducer getResourceMessageProducer() {
		return resourceMessageProducer;
	}

	public ComLogService getComLogService() {
		return comLogService;
	}

	public PayPaymentGatewayService getPayPaymentGatewayService() {
		return payPaymentGatewayService;
	}

	public PayPaymentGatewayElementService getPayPaymentGatewayElementService() {
		return payPaymentGatewayElementService;
	}

	public PayReceivingCompanyService getPayReceivingCompanyService() {
		return payReceivingCompanyService;
	}

	public void setPayReceivingCompanyService(
			PayReceivingCompanyService payReceivingCompanyService) {
		this.payReceivingCompanyService = payReceivingCompanyService;
	}

	public List<PayReceivingCompany> getPayReceivingCompanyList() {
		return payReceivingCompanyList;
	}

	public void setPayReceivingCompanyList(
			List<PayReceivingCompany> payReceivingCompanyList) {
		this.payReceivingCompanyList = payReceivingCompanyList;
	}

	public PermUserService getPermUserService() {
		return permUserService;
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getReceivingBankCardNo() {
		return receivingBankCardNo;
	}

	public void setReceivingBankCardNo(String receivingBankCardNo) {
		this.receivingBankCardNo = receivingBankCardNo;
	}

	public PayReceivingBankService getPayReceivingBankService() {
		return payReceivingBankService;
	}

	public void setPayReceivingBankService(
			PayReceivingBankService payReceivingBankService) {
		this.payReceivingBankService = payReceivingBankService;
	}

	public String getPaymentAccount() {
		return paymentAccount;
	}

	public void setPaymentAccount(String paymentAccount) {
		this.paymentAccount = paymentAccount;
	}

	public String getPaymentBankName() {
		return paymentBankName;
	}

	public void setPaymentBankName(String paymentBankName) {
		this.paymentBankName = paymentBankName;
	}

	public String getPaymentBankCardNo() {
		return paymentBankCardNo;
	}

	public void setPaymentBankCardNo(String paymentBankCardNo) {
		this.paymentBankCardNo = paymentBankCardNo;
	}

	public String getOtherAuditStatus() {
		return otherAuditStatus;
	}

	public void setOtherAuditStatus(String otherAuditStatus) {
		this.otherAuditStatus = otherAuditStatus;
	}

	public String getBelongCenter() {
		return belongCenter;
	}

	public void setBelongCenter(String belongCenter) {
		this.belongCenter = belongCenter;
	}

	public String getBelongDepartment() {
		return belongDepartment;
	}

	public void setBelongDepartment(String belongDepartment) {
		this.belongDepartment = belongDepartment;
	}

	public String getDetailName() {
		return detailName;
	}

	public void setDetailName(String detailName) {
		this.detailName = detailName;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

}
