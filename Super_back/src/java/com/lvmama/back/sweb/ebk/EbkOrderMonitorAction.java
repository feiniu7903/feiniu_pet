package com.lvmama.back.sweb.ebk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ebooking.EbkTask;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaTime;
import com.lvmama.comm.bee.po.ord.OrdOrderMemo;
import com.lvmama.comm.bee.service.ebooking.CertificateService;
import com.lvmama.comm.bee.service.ebooking.EbkCertificateService;
import com.lvmama.comm.bee.service.ebooking.EbkTaskService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

@Results({
		@Result(name = "ebkOrdToprocessList", location = "/WEB-INF/pages/back/ebk/ebkOrdToprocessList.jsp"),
		@Result(name = "ebkOrdToprocessDetail", location = "/WEB-INF/pages/back/ebk/ebkOrdToprocessDetail.jsp"),
        @Result(name = "ebkOrdProcessedList", location = "/WEB-INF/pages/back/ebk/ebkOrdProcessedList.jsp"),
		@Result(name = "ebkOrdProcessedDetail", location = "/WEB-INF/pages/back/ebk/ebkOrdProcessedDetail.jsp"),
		@Result(name = "ebkOrdProcessedRouteDetail", location = "/WEB-INF/pages/back/ebk/ebkOrdProcessedRouteDetail.jsp")
})
/**
 * Ebooking酒店订单监控类
 * 
 * @author nixianjun
 * @version 酒店订单管理系统EBooking第一版 12/12/10
 * @since  
 */
@ParentPackage("json-default")
public class EbkOrderMonitorAction extends BackBaseAction {

	private static final long serialVersionUID = 145141651065156L;

	private List<ComLog> logList;
	private EbkCertificate ebkCertificate = new EbkCertificate();
	private EbkCertificateItem ebkCertificateItem = new EbkCertificateItem() ;
	private EbkTask ebkTask = new EbkTask();
	private Page<EbkTask> ebkTaskPage = new Page<EbkTask>();
//	private Page<EbkOrderDetail> ebkTaskPage = new Page<EbkOrderDetail>();

	private EbkTaskService ebkTaskService;

	private OrderService orderServiceProxy;

	private ComLogService comLogRemoteService; 
	
	private SupplierService supplierService; 
	
	private EbkCertificateService ebkCertificateService;
	
	private CertificateService certificateServiceProxy;
	// 订单号
	private Long ebkTaskId;
	// 订单号
	private Long orderItemMetaId;
	// 订单号
	private String orderId;
	//确认状态
	private String confirmStatus;
	// 订单提交开始时间
	private String createTimeStart;
	// 订单提交结束时间
	private String createTimeEnd;
	// 供应商确认开始时间
	private String confirmTimeStart;
	// 供应商确认结束时间
	private String confirmTimeEnd;
	// 游玩开始时间
	private String visitTimeStart;
	// 游玩结束时间
	private String visitTimeEnd;
	// 订单状态
	private String taskType;
	private String orderStatus;
	// 消耗时间
	private String consumeTime;//
	// 支付状态
	private String paymentStatus;
	// 供应商id
	private Long supplierId;
	// 供应商
	private String supplierName;
	// 酒店确认人
	private String confirmUser;
	// 确认状态
	private String status;
	//跟进人
	private String followUserId;
	//不定期订单激活状态
	private String useStatus;
	
	private List<OrdOrderItemMetaTime> time;
	
	private PermUserService permUserService;

	/**
	 * 重发备注
	 */
	private String reMemo;
	/**
	 * 与酒店沟通结果（如果成功，将记录传真备注）
	 */
	private String faxMemo;
	
	//当前登录用户
	private String currentUser;
	
	private boolean hasCancelOrder;
	/**
	 * 订单备注
	 */
	private List<OrdOrderMemo> memoList;

	private String orderType;
	/**
	 * 发送传真
	 * @return
	 */
	@Action(value ="/ebooking/order/sendFax")
	public void doSendFax(){
		Long id = Long.valueOf(this.getRequest().getParameter("id"));
		boolean result = this.certificateServiceProxy.createCertificateEbkFaxTaskWithCertId(id, this.getSessionUserName());
		if(result) {
			this.sendAjaxMsg("SUCCESS");
		} else {
			this.sendAjaxMsg("发送失败");
		}
	}
	/**
	 * 已处理订单(查Super订单)
	 * @return
	 */
	@Action("/ebooking/order/ebkOrdProcessedList")
	public String ebkOrdProcessedList() {
		if(orderId == null && page == null) {
			return "ebkOrdProcessedList";
		}
		Map<String, Object> params = new HashMap<String, Object>();
 	    params.put("orderBy", "ordCreateTimeDesc");
 	    String ebkCertificateType = StringUtils.contains(confirmStatus, "_")?confirmStatus.split("_")[0]:null;
		String certificateStatus = StringUtils.contains(confirmStatus, "_")?confirmStatus.split("_")[1]:confirmStatus;
		params.put("certificateStatus", certificateStatus);
		params.put("ebkCertificateType", ebkCertificateType);
		params.put("userMemoStatus", "true");
		/*订单状态*/
		if (!StringUtil.isEmptyString(this.ebkTask.getOrderStatus())) {
			params.put("orderStatus", this.ebkTask.getOrderStatus());
		}
		/*订单ID*/
		if (!StringUtil.isEmptyString(this.orderId)) {
			params.put("orderId", this.orderId.trim());
		}
		/*供应商名称*/
		if (this.ebkCertificate.getSupplierId()!=null) {
			params.put("supplierId", this.ebkCertificate.getSupplierId());
		}
		/*确认人*/
		if (!StringUtil.isEmptyString(this.ebkTask.getConfirmUser())) {
			params.put("confirmUser", this.ebkTask.getConfirmUser().trim());
		}
		/*支付状态*/
		if (!StringUtil.isEmptyString(this.ebkTask.getPaymentStatus())) {
			params.put("paymentStatus",this.ebkTask.getPaymentStatus());
			System.out.println(this.ebkTask.getPaymentStatus());
		}
		/*采购产品名称*/
		if (!StringUtil.isEmptyString(this.ebkCertificateItem.getMetaProductName())) {
			params.put("metaProductName", this.ebkCertificateItem.getMetaProductName().trim());
		}
		/*凭证类型*/
		if (!StringUtil.isEmptyString(this.ebkCertificate.getEbkCertificateType())) {
			params.put("ebkCertificateType", this.ebkCertificate.getEbkCertificateType());
		}
		/*跟进人*/
		if (!StringUtil.isEmptyString(this.ebkTask.getFollowUser())) {
			params.put("followUser", this.ebkTask.getFollowUser());
		}
		/*产品类型*/
		if(!StringUtil.isEmptyString(this.ebkCertificate.getProductType())){
			params.put("productType", this.ebkCertificate.getProductType());
		}
		/*订单提交起始时间*/
		if(!StringUtil.isEmptyString(createTimeStart)){
			params.put("orderCreateTimeStart", createTimeStart);
		}
		/*订单提交结束时间*/
		if(!StringUtil.isEmptyString(createTimeEnd)){
			params.put("orderCreateTimeEnd", createTimeEnd+" 23:59:59");
		}
		/*订单确认起始时间*/
		if(!StringUtil.isEmptyString(confirmTimeStart)){
			params.put("confirmTimeStart", confirmTimeStart);
		}
		/*订单确认结束时间*/
		if(!StringUtil.isEmptyString(confirmTimeEnd)){
			params.put("confirmTimeEnd", confirmTimeEnd+" 23:59:59");
		}
		/*游玩时间*/
		if(!StringUtil.isEmptyString(visitTimeStart)){
			params.put("visitTimeStart", visitTimeStart);
		}
		/*游玩时间*/
		if(!StringUtil.isEmptyString(visitTimeEnd)){
			params.put("visitTimeEnd", visitTimeEnd+" 23:59:59");
		}
		/*订单类型*/
		if(!StringUtil.isEmptyString(orderType)){
			params.put("orderType", orderType);
		}
		/*耗时*/
		if (!StringUtil.isEmptyString(this.consumeTime)) {
			if ("FIVE_MIN".equalsIgnoreCase(consumeTime)) {
				params.put("consumeTimeEnd", 5);
			} else if ("FIVE_TEN_MIN".equalsIgnoreCase(this.consumeTime)) {
				params.put("consumeTimeStart", 5);
				params.put("consumeTimeEnd", 10);
			} else if ("TEN_MIN".equalsIgnoreCase(this.consumeTime)) {
				params.put("consumeTimeStart", 10);
			}
		}
		//如果查询不定期订单
		if(!StringUtil.isEmptyString(useStatus)){
			params.put("isAperiodic", Constant.TRUE_FALSE.TRUE.getAttr1());
			params.put("useStatus", useStatus);
		}
		ebkTaskPage = ebkCertificateService.queryEbkTaskPageListSQL(this.page, ebkTaskPage.getPageSize(), params);
		ebkTaskPage.buildUrl(getRequest());
		return "ebkOrdProcessedList";
	}
	
	/**
	 *  
	 */
	@Action("/ebooking/order/ebkOrdProcessedDetail")
	public String ebkOrdProcessedDetail() {
		queryEbookingOrderDetail();
		if(ebkTask != null && ebkTask.getEbkCertificate() != null && Constant.PRODUCT_TYPE.HOTEL.name().equals(ebkTask.getEbkCertificate().getProductType())){
			return "ebkOrdProcessedDetail";
		}else{
			return "ebkOrdProcessedRouteDetail";
		}
	}
	/**
	 * 
	 * 
	 * @author: ranlongfei 2013-4-2 上午9:18:04
	 * @return
	 */
	@Action("/ebooking/order/ebkOrdMetaEbkCertDetail")
	public String ebkOrdMetaEbkCertDetail() {
		EbkTask task = ebkTaskService.selectNearbyEbkTaskByOrderItemMetaId(orderItemMetaId);
		if(task != null) {
			this.ebkTaskId = task.getEbkTaskId(); 
		}
		return ebkOrdProcessedDetail();
	}
	private void queryEbookingOrderDetail() {
		ebkTask=ebkTaskService.findEbkTaskAndCertificateAndGetContentByPkId(ebkTaskId);
		// 查询日志
		if(ebkTask != null && ebkTask.getEbkCertificate()!=null&&ebkTask.getEbkCertificate().getEbkCertificateItemList().size() > 0) {
			logList = comLogRemoteService.queryByObjectId(Constant.COM_LOG_OBJECT_TYPE.EBK_ORDER_TASK.name(), ebkTask.getEbkCertificate().getEbkCertificateItemList().get(0).getOrderItemMetaId());
			//查询订单备注
			memoList = orderServiceProxy.queryMemoByOrderId(ebkTask.getOrderId());
		}
 	}
	public List<ComLog> getLogList() {
		return logList;
	}
	public void setLogList(List<ComLog> logList) {
		this.logList = logList;
	}
	public Page<EbkTask> getEbkTaskPage() {
		return ebkTaskPage;
	}
	public void setEbkTaskPage(Page<EbkTask> ebkTaskPage) {
		this.ebkTaskPage = ebkTaskPage;
	}
	public EbkTaskService getEbkTaskService() {
		return ebkTaskService;
	}
	public void setEbkTaskService(EbkTaskService ebkTaskService) {
		this.ebkTaskService = ebkTaskService;
	}
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public ComLogService getComLogRemoteService() {
		return comLogRemoteService;
	}
	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
	}
	public Long getEbkTaskId() {
		return ebkTaskId;
	}
	public void setEbkTaskId(Long ebkTaskId) {
		this.ebkTaskId = ebkTaskId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTaskType() {
		return taskType;
	}
	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}
	public String getConsumeTime() {
		return consumeTime;
	}
	public void setConsumeTime(String consumeTime) {
		this.consumeTime = consumeTime;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Long getSupplierId() {
		return supplierId;
	}
	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
	public String getSupplierName() {
		return supplierName;
	}
	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}
	public String getConfirmUser() {
		return confirmUser;
	}
	public void setConfirmUser(String confirmUser) {
		this.confirmUser = confirmUser;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<OrdOrderItemMetaTime> getTime() {
		return time;
	}
	public void setTime(List<OrdOrderItemMetaTime> time) {
		this.time = time;
	}
	public String getReMemo() {
		return reMemo;
	}
	public void setReMemo(String reMemo) {
		this.reMemo = reMemo;
	}
	public SupplierService getSupplierService() {
		return supplierService;
	}
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCurrentUser() {
		return currentUser;
	}
	public void setCurrentUser(String currentUser) {
		this.currentUser = currentUser;
	}
	public String getFollowUserId() {
		return followUserId;
	}
	public void setFollowUserId(String followUserId) {
		this.followUserId = followUserId;
	}
	public PermUserService getPermUserService() {
		return permUserService;
	}
	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}
	public List<OrdOrderMemo> getMemoList() {
		return memoList;
	}
	public String getFaxMemo() {
		return faxMemo;
	}
	public void setFaxMemo(String faxMemo) {
		this.faxMemo = faxMemo;
	}
	public boolean isHasCancelOrder() {
		return hasCancelOrder;
	}
	public void setHasCancelOrder(boolean hasCancelOrder) {
		this.hasCancelOrder = hasCancelOrder;
	}
	public EbkCertificate getEbkCertificate() {
		return ebkCertificate;
	}
	public void setEbkCertificate(EbkCertificate ebkCertificate) {
		this.ebkCertificate = ebkCertificate;
	}
	public EbkCertificateItem getEbkCertificateItem() {
		return ebkCertificateItem;
	}
	public void setEbkCertificateItem(EbkCertificateItem ebkCertificateItem) {
		this.ebkCertificateItem = ebkCertificateItem;
	}
	public EbkTask getEbkTask() {
		return ebkTask;
	}
	public void setEbkTask(EbkTask ebkTask) {
		this.ebkTask = ebkTask;
	}
	public String getConfirmStatus() {
		return confirmStatus;
	}
	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	public String getCreateTimeStart() {
		return createTimeStart;
	}
	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}
	public String getCreateTimeEnd() {
		return createTimeEnd;
	}
	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}
	public String getConfirmTimeStart() {
		return confirmTimeStart;
	}
	public void setConfirmTimeStart(String confirmTimeStart) {
		this.confirmTimeStart = confirmTimeStart;
	}
	public String getConfirmTimeEnd() {
		return confirmTimeEnd;
	}
	public void setConfirmTimeEnd(String confirmTimeEnd) {
		this.confirmTimeEnd = confirmTimeEnd;
	}
	public EbkCertificateService getEbkCertificateService() {
		return ebkCertificateService;
	}
	public void setEbkCertificateService(EbkCertificateService ebkCertificateService) {
		this.ebkCertificateService = ebkCertificateService;
	}

	public Long getOrderItemMetaId() {
		return orderItemMetaId;
	}

	public void setOrderItemMetaId(Long orderItemMetaId) {
		this.orderItemMetaId = orderItemMetaId;
	}
	public CertificateService getCertificateServiceProxy() {
		return certificateServiceProxy;
	}
	public void setCertificateServiceProxy(CertificateService certificateServiceProxy) {
		this.certificateServiceProxy = certificateServiceProxy;
	}
	public String getUseStatus() {
		return useStatus;
	}
	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}
	public String getOrderType() {
		return orderType;
	}
	public void setOrderType(String orderType) {
		this.orderType = orderType;
	}
	public String getVisitTimeStart() {
		return visitTimeStart;
	}
	public void setVisitTimeStart(String visitTimeStart) {
		this.visitTimeStart = visitTimeStart;
	}
	public String getVisitTimeEnd() {
		return visitTimeEnd;
	}
	public void setVisitTimeEnd(String visitTimeEnd) {
		this.visitTimeEnd = visitTimeEnd;
	}

}
