package com.lvmama.back.sweb.ord;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.WebUtils;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderAmountApply;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.po.pub.ComMessage;
import com.lvmama.comm.pet.po.work.WorkTask;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.ComMessageService;
import com.lvmama.comm.pet.service.work.WorkGroupUserService;
import com.lvmama.comm.pet.service.work.WorkTaskService;
import com.lvmama.comm.utils.SynchronizedLock;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.work.builder.WorkOrderFinishedBiz;
import com.lvmama.comm.work.builder.WorkOrderSenderBiz;

/**
 * 订单金额修改申请.
 * 
 * @author liwenzhan
 * @version 20110930 1.0
 * @see com.lvmama.back.sweb.BaseAction;
 * @see com.lvmama.common.ord.po.OrdOrderAmountApply;
 * @see com.lvmama.common.ord.service.OrderService;
 */
@Results({
		@Result(name = "ord_modify_amount_Apply", location = "/WEB-INF/pages/back/ord/modifyOrderAmount/ord_modify_amount_apply.jsp"),
		@Result(name = "ord_modify_amount_Apply_list", location = "/WEB-INF/pages/back/ord/modifyOrderAmount/ord_modify_amount_apply_list.jsp"),
		@Result(name = "query_AmountApplyList", location = "/WEB-INF/pages/back/ord/modifyOrderAmount/query_amount_apply_list.jsp"),
		@Result(name = "ord_amount_price", location = "/WEB-INF/pages/back/ord/ord_amount_price.jsp") })
public class OrderModifyAmountApplyAction extends BaseAction {
	/**
	 * 序列化ID.
	 */
	private static final long serialVersionUID = 467840269861975573L;
	/**
	 * 订单价格修改记录表.
	 */
	private OrdOrderAmountApply ordOrderAmountApply = new OrdOrderAmountApply();
	/**
	 * 订单服务.
	 */
	private OrderService orderServiceProxy;
	/**
	 * 申请修改金额类型.
	 */
	private String applyAmountType;

	/**
	 * 订单ID.
	 */
	private Long orderId;

	/**
	 * 订单金额修改申请状态.
	 */
	private String applyStatus;

	/**
	 * 类型.delete 代表减少 add 代表增加.
	 */
	private String amountType;
	/**
	 * 金额 元.
	 */
	private Long moneyYuan;

	/**
	 * 订单价格修改记录列表.
	 */
	private List<OrdOrderAmountApply> ordOrderAmountApplyList;

	/**
	 * 废单原因
	 */
	private List<CodeItem> orderAmountTypes;
	/**
	 * 消息接口
	 */
	private ComMessageService comMessageService;

	private WorkOrderFinishedBiz workOrderFinishedProxy;

	// 生成发送工单
	private WorkOrderSenderBiz workOrderProxy;

	/**
	 * 发送弹出消息模版
	 */
	private static final String ORDER_MODIFY_ORDER_PAY_MSG_TEMPLATE = "订单[订单号]金额修改申请[申请状态]";

	/**
	 * 应付金额.
	 */
	// private Long oughtPay;
	/**
	 * 实付金额.
	 */
	// private Long actualPay;

	/**
	 * 订单实体
	 */
	private OrdOrder order;
	
	private MetaProductService metaProductService;
	

	/**
	 * 订单修改申请LIST.
	 * 
	 * @return
	 */
	@Action("/ord/queryAmountApplyList")
	public String queryAmountApplyList() {
		initPagination();
		Map<String, Object> parameter = buildParameter();
		if (StringUtils.isNotBlank(this.getApplyAmountType())
				&& "ALL".equalsIgnoreCase(this.getApplyAmountType())) {
			if (!this.getSessionUser().isAdministrator()) {
				parameter.put("orgId", this.getSessionUser().getDepartmentId());
			}
			parameter
					.put("notApplyType",
							"'"
									+ Constant.ORDER_AMOUNT_APPLAY_TYPE.STAFF_DISCOUNT
											.name()
									+ "','"
									+ Constant.ORDER_AMOUNT_APPLAY_TYPE.BUSINESS_PR_DISCOUNT
											.name() + "'");
		} else {
			parameter
					.put("inApplyType",
							"'"
									+ Constant.ORDER_AMOUNT_APPLAY_TYPE.STAFF_DISCOUNT
											.name()
									+ "','"
									+ Constant.ORDER_AMOUNT_APPLAY_TYPE.BUSINESS_PR_DISCOUNT
											.name() + "'");
		}
		long count = orderServiceProxy.queryOrdOrderAmountApplyCount(parameter);
		pagination.setTotalRecords(count);
		if (count > 0L) {
			parameter.put("skipResult", pagination.getFirstRow());
			parameter.put("maxResult", pagination.getLastRow());
			pagination.setRecords(orderServiceProxy
					.queryOrdOrderAmountApply(parameter));
		}
		// 将workTaskId放到session中
		String workTaskId = getRequest().getParameter("workTaskId");
		getRequest().getSession().setAttribute("workTaskId", workTaskId);
		pagination.setActionUrl(WebUtils.getUrl(getRequest()));
		return "query_AmountApplyList";
	}

	/**
	 * 封装订单修改申请对象.
	 * 
	 * @return
	 */
	private Map<String, Object> buildParameter() {
		Map<String, Object> map = new HashMap<String, Object>();
		if (orderId != null && orderId > 0) {
			map.put("orderId", orderId);
		}
		if (StringUtils.isNotEmpty(applyStatus)) {
			if (!"ALL".equals(applyStatus)) {
				map.put("applyStatus", applyStatus);
			}
		}
		return map;
	}

	/**
	 * 订单历史页面加载.
	 * 
	 * @return
	 */
	@Action("/ord/loadModifyAmountApply")
	public String loadModifyAmountApply() {
		try {
			order = initOrdOrder();
			initPagination();
			Map<String, Object> parameter = buildParameter();
			ordOrderAmountApplyList = orderServiceProxy
					.queryOrdOrderAmountApply(parameter);
			this.orderAmountTypes = CodeSet.getInstance().getCachedCodeList(
					Constant.CODE_TYPE.ORDER_AMOUNT_APPLAY_TYPE.name());
		} catch (Exception e) {
			// e.printStackTrace();
			errorMessage(e.getMessage());
		}
		return "ord_amount_price";
	}

	/**
	 * 根据订单的ID查询订单金额修改申请列表.
	 * 
	 * @return ordOrderAmountApplyList 单金额修改申请列表.
	 */
	@Action("/ord/modifyAmountApplyList")
	public String modifyAmountApplyList() {
		initPagination();
		Map<String, Object> parameter = buildParameter();
		if (!this.getSessionUser().isAdministrator()) {
			parameter.put("orgId", this.getSessionUser().getDepartmentId());
		}
		ordOrderAmountApplyList = orderServiceProxy
				.queryOrdOrderAmountApply(parameter);
		pagination.setActionUrl(WebUtils.getUrl(getRequest()));
		return "ord_modify_amount_Apply_list";
	}

	/**
	 * 客服提交申请的订单修改记录.
	 */
	@Action("/ord/saveModifyAmountApply")
	public void saveModifyAmountApply() {
		JSONResult result = new JSONResult();
		try {
			order = initOrdOrder();
			if (isModifyAmount(order, moneyYuan, amountType)) {
				ordOrderAmountApply
						.setApplyStatus(Constant.ORDER_AMOUNT_MODIFY_STATUS.UNVERIFIED
								.name());
				ordOrderAmountApply.setApplyUser(this.getOperatorName());
				ordOrderAmountApply.setCreateTime(new Date());
				ordOrderAmountApply.setOrderId(orderId);
				orderServiceProxy
						.insertModifyOrderAmountApply(ordOrderAmountApply);
				//add by zhushuying 发价格变更提醒工单
				// 是长途跟团游,长途自由行,出境跟团游,出境自由行				
				if(Constant.SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equalsIgnoreCase(order.getOrderType())
						||Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equalsIgnoreCase(order.getOrderType())){
					workOrderProxy.sendWorkOrder(order,
						Constant.WORK_ORDER_TYPE_AND_SENDGROUP.JGBGTX
							.getWorkOrderTypeCode(),
						"/super_back/ord/queryAmountApplyList.do?applyAmountType=ALL&orderId="
								+ orderId, Boolean.TRUE, Boolean.TRUE, null,
						this.getSessionUser().getUserName(), null, null,null,false);
				}
				//end by zhushuying
			}
		} catch (Exception ex) {
			result.raise(new JSONResultException(ex));
		}
		result.output(getResponse());
	}

	/**
	 * 审核　更新订单价格修改记录.
	 */
	@Action("/ord/approveApply")
	public void approveApply() {
		JSONResult result = new JSONResult();
		String sync_key="ord_order_amount_apply_"+ordOrderAmountApply.getAmountApplyId();
		try {
			int rows = 0;
			String content = "";
			OrdOrderAmountApply orderAmountApply = null;
			if(!SynchronizedLock.isOnDoingMemCached(sync_key)) {
				orderAmountApply = this.orderServiceProxy
						.findOrderAmountApplyById(ordOrderAmountApply
								.getAmountApplyId());
				if (orderAmountApply == null) {
					result.put("code", 1);
					result.put("msg", "申请信息不存在!");
					result.output(getResponse());
					return;
				}
				if (!Constant.ORDER_AMOUNT_MODIFY_STATUS.UNVERIFIED.name()
						.equals(orderAmountApply.getApplyStatus())) {
					result.put("code", 1);
					result.put("msg", "该申请已经处理过不可以再次处理!");
					result.output(getResponse());
					return;
				}
				orderId = orderAmountApply.getOrderId();
				OrdOrder order = orderServiceProxy
						.queryOrdOrderByOrderId(orderId);
				orderAmountApply.setApplyStatus(ordOrderAmountApply
						.getApplyStatus());
				orderAmountApply.setApproveUser(this.getOperatorName());
				orderAmountApply.setApproveMemo(ordOrderAmountApply
						.getApproveMemo());
				orderAmountApply.setApproveTime(new Date());
				if (ordOrderAmountApply.getApplyStatus().equals(
						Constant.ORDER_AMOUNT_MODIFY_STATUS.PASS.name())) {
					// 判断加减类型.
					boolean key = false;
					if (orderAmountApply.getAmount() > 0) {
						key = true;
					} else {
						key = order.getOughtPay() - order.getActualPay() > Math
								.abs(orderAmountApply.getAmount());
					}
					if (order.isPaymentSucc()) {
						result.put("code", 1);
						result.put("msg", "该订单已经支付完成,不允许再修改订单金额!");
						result.output(getResponse());
						return;
					}
					if (!key) {
						result.put("code", 1);
						result.put("msg", "该订单调价后的应付金额小于等于实付金额,不允许修改订单金额!");
						result.output(getResponse());
						return;
					}
					if (!order.isPaymentSucc() && key) {
						rows = orderServiceProxy
								.updateOrderModifyAmountApplyOrder(orderAmountApply);
						content = ORDER_MODIFY_ORDER_PAY_MSG_TEMPLATE
								.replace("订单号",
										String.valueOf(order.getOrderId()))
								.replace(
										"申请状态",
										orderAmountApply
												.getOrderAmountApplyStatusStr());
					}
				} else {
					rows = orderServiceProxy
							.updateOrderModifyAmountApply(orderAmountApply);
					content = ORDER_MODIFY_ORDER_PAY_MSG_TEMPLATE.replace(
							"订单号", String.valueOf(order.getOrderId())).replace(
							"申请状态",
							orderAmountApply.getOrderAmountApplyStatusStr());
				}
				//add by zhushuying 完成当前任务同时新建任务发送给客服
				if (null != getSession().getAttribute("workTaskId")) {
					String paramId = getSession().getAttribute("workTaskId")
							.toString();
					workOrderFinishedProxy.createWorkTask(paramId,
							ordOrderAmountApply.getApproveMemo(), this
									.getSessionUser().getUserName());
					getSession().removeAttribute("workTaskId");
				}
			}
			if (rows > 0) {
				insertMsg(content, orderAmountApply.getApplyUser());
			}
		} catch (Exception ex) {
			result.raise(ex);
		} finally{
			SynchronizedLock.releaseMemCached(sync_key);
		}
		result.output(getResponse());
	}

	/**
	 * 写入消息.
	 * 
	 * @param receiver
	 * @param content
	 * @param date
	 */
	private void insertMsg(final String content, final String receiver) {
		comMessageService.insertComMessage(markComMessage(content, receiver));
	}

	/**
	 * 包装消息.
	 * 
	 * @return
	 */
	private ComMessage markComMessage(final String content,
			final String receiver) {
		ComMessage msg = new ComMessage();
		msg.setCreateTime(new Date());
		msg.setContent(content);
		msg.setReceiver(receiver);
		msg.setSender(Constant.SYSTEM_USER);
		msg.setStatus("CREATE");
		return msg;
	}

	/**
	 * 判断是否可以修改订单金额.
	 * 
	 * @return
	 */
	private boolean isModifyAmount(OrdOrder order, Long moneyYuan,
			String amountType) {
		boolean payType = true;
		try {
			// this.oughtPay=order.getOughtPay();
			// this.actualPay=order.getActualPay();
			if (moneyYuan < 1) {
				throw new Exception("金额不能小于  1 !");
			}
			if (order.isPaymentSucc()) {
				throw new Exception("订单已经支付完成,不能修改金额!!");
			}
			// 客服提交申请修改订单时 判断amountType的类型是增加金额还是减少金额
			if (Constant.ORDER_AMOUNT_MODIFY_TYPE.REDUCE.name().equals(
					amountType)) {
				if (order.getOughtPay() - order.getActualPay() >= moneyYuan * 100) {
					ordOrderAmountApply.setAmount(-moneyYuan * 100);
				} else {
					throw new Exception("本次订单减少费用超出未支付金额,故操作不成功!!");
				}
			} else {
				ordOrderAmountApply.setAmount(moneyYuan * 100);
			}
		} catch (Exception ex) {
			payType = false;
		}
		return payType;
	}

	/**
	 * 获取订单价格记录对象.
	 * 
	 * @return ordOrderAmountApply 订单价格记录对象.
	 */
	public OrdOrderAmountApply getOrdOrderAmountApply() {
		return ordOrderAmountApply;
	}

	/**
	 * 设置订单价格记录对象.
	 * 
	 * @param ordOrderAmountApply
	 *            订单价格记录对象.
	 */
	public void setOrdOrderAmountApply(OrdOrderAmountApply ordOrderAmountApply) {
		this.ordOrderAmountApply = ordOrderAmountApply;
	}

	/**
	 * 获取订单ID.
	 * 
	 * @return orderId 订单ID.
	 */
	public Long getOrderId() {
		return orderId;
	}

	/**
	 * 设置订单价格修改记录列表.
	 * 
	 * @param orderId
	 *            订单ID.
	 */
	public void setOrderId(String orderId) {
		if (StringUtils.isNotEmpty(orderId)) {
			this.orderId = NumberUtils.toLong(orderId.trim());
		}
	}

	/**
	 * 设置订单价格修改记录列表.
	 * 
	 * @param orderServiceProxy
	 *            订单服务.
	 */
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	/**
	 * 获取订单价格修改记录列表.
	 * 
	 * @return ordOrderAmountApplyList 修改记录列表.
	 */
	public List<OrdOrderAmountApply> getOrdOrderAmountApplyList() {
		return ordOrderAmountApplyList;
	}

	/**
	 * 获取属性.
	 * 
	 * @return
	 */
	public String getAmountType() {
		return amountType;
	}

	/**
	 * 设置属性.
	 * 
	 * @param amountType
	 */
	public void setAmountType(String amountType) {
		this.amountType = amountType;
	}

	/**
	 * 
	 * @return
	 */
	public Long getMoneyYuan() {
		return moneyYuan;
	}

	/**
	 * 
	 * @param moneyYuan
	 */
	public void setMoneyYuan(Long moneyYuan) {
		this.moneyYuan = moneyYuan;
	}

	/**
	 * 获取订单信息.
	 * 
	 * @return
	 */
	public OrdOrder initOrdOrder() {
		if (orderId == null || orderId < 0) {
			throw new NullPointerException("订单号 ：" + orderId + "不存在!!");
		}
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if (order == null) {
			throw new NullPointerException("订单号 ：" + orderId + "不存在!!");
		}
		// this.oughtPay=order.getOughtPay();
		// this.actualPay=order.getActualPay();
		return order;
	}

	/**
	 * 获取订单金额修改申请状态.
	 * 
	 * @return
	 */
	public String getApplyStatus() {
		return applyStatus;
	}

	/**
	 * 设置订单金额修改申请状态.
	 * 
	 * @param applyStatus
	 */
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}

	/**
	 * 设置消息接口.
	 * 
	 * @param comMessageService
	 */
	public void setComMessageService(ComMessageService comMessageService) {
		this.comMessageService = comMessageService;
	}

	/**
	 * @return the order
	 */
	public OrdOrder getOrder() {
		return order;
	}

	/**
	 * 判断该订单是否是可以修改的订单
	 * 
	 * @return
	 */
	public boolean hasModifyOrder() {
		if (order == null)
			return false;

		return order.isGroupForeign() || order.isGroupLong()
				|| order.isFreenessLong() || order.isFreenessForeign()
				|| order.isSelfhelpBus();
	}

	public List<CodeItem> getOrderAmountTypes() {
		return orderAmountTypes;
	}

	public void setOrderAmountTypes(List<CodeItem> orderAmountTypes) {
		this.orderAmountTypes = orderAmountTypes;
	}

	public String getApplyAmountType() {
		return applyAmountType;
	}

	public void setApplyAmountType(String applyAmountType) {
		this.applyAmountType = applyAmountType;
	}

	public WorkOrderSenderBiz getWorkOrderProxy() {
		return workOrderProxy;
	}

	public void setWorkOrderProxy(WorkOrderSenderBiz workOrderProxy) {
		this.workOrderProxy = workOrderProxy;
	}

	public WorkOrderFinishedBiz getWorkOrderFinishedProxy() {
		return workOrderFinishedProxy;
	}

	public void setWorkOrderFinishedProxy(
			WorkOrderFinishedBiz workOrderFinishedProxy) {
		this.workOrderFinishedProxy = workOrderFinishedProxy;
	}

	public MetaProductService getMetaProductService() {
		return metaProductService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public void setOrdOrderAmountApplyList(
			List<OrdOrderAmountApply> ordOrderAmountApplyList) {
		this.ordOrderAmountApplyList = ordOrderAmountApplyList;
	}
}
