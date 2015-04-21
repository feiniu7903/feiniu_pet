package com.lvmama.bee.web.ebooking;


import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import com.lvmama.bee.web.EbkBaseAction;
import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ebooking.EbkTask;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaAperiodic;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaTime;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.ebooking.CertificateService;
import com.lvmama.comm.bee.service.ebooking.EbkCertificateService;
import com.lvmama.comm.bee.service.ebooking.EbkTaskService;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.ord.OrderItemMetaAperiodicService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.pet.service.work.PublicWorkOrderService;
import com.lvmama.comm.pet.vo.InvokeResult;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.pet.vo.WorkOrderCreateParam;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ExcelUtils;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.EBK_TASK_REASON;
/**
 * EBK订单任务
 * @author ranlongfei 2012-12-7
 * @version
 */
@Results(value={
		@Result(name="ebkTaskLog",location="/WEB-INF/pages/ebooking/task/ebkTaskLog.jsp"),
		@Result(name="confirmTaskList",location="/WEB-INF/pages/ebooking/task/confirmTaskList.jsp"),
		@Result(name="todayVisitTaskList",location="/WEB-INF/pages/ebooking/task/todayVisitTaskList.jsp"),
		@Result(name="todayTaskList",location="/WEB-INF/pages/ebooking/task/todayTaskList.jsp"),
		@Result(name="allTaskList",location="/WEB-INF/pages/ebooking/task/allTaskList.jsp"),
		@Result(name="aperiodicTaskList",location="/WEB-INF/pages/ebooking/task/aperiodicTaskList.jsp"),
		@Result(name="orderyd",location="/WEB-INF/pages/ebooking/task/orderyd.jsp"),
		@Result(name="orderqx",location="/WEB-INF/pages/ebooking/task/orderqx.jsp"),
		@Result(name="orderck",location="/WEB-INF/pages/ebooking/task/orderck.jsp"),
		@Result(name="unUserOrderck",location="/WEB-INF/pages/ebooking/task/unUserOrderck.jsp"),
		@Result(name="userOrderck",location="/WEB-INF/pages/ebooking/task/userOrderck.jsp"),
		@Result(name="orderPrint",location="/WEB-INF/pages/ebooking/task/orderPrint.jsp"),
		@Result(name="orderbg",location="/WEB-INF/pages/ebooking/task/orderbg.jsp")
	})
public class EbkTaskAction extends EbkBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7149773095882848067L;
	
	private Page<EbkTask> ebkTaskPage = new Page<EbkTask>();
	private EbkTaskService ebkTaskService;
	private EbkCertificateService ebkCertificateService;
	private OrderService orderServiceProxy;	
	private MetaProductBranchService metaProductBranchService; 	
	private ComLogService comLogRemoteService; 	
	private List<MetaProductBranch> ebkMetaBranchList;	
	private EbkTask task;
	private List<OrdOrderItemMetaTime> time;	
	private List<ComLog> logList;
	
	private Long ebkTaskId;
	private Long orderId;
	private String travellerName;
	private String mobile;
	private String taskType;
	private String certType;
	private String orderStatus;
	private String metaBranchId;
	private String memo;
	private String confirmStatus;
	private String reason;
	private String supplierOrderNo;
	private String paymentStatus;
	private String confirmUser;
	private String createTimeStart;
	private String createTimeEnd;
	private String visitTimeStart;
	private String visitTimeEnd;
	//密码券号
	private String passwordCertificate;
	private String useStatus;
	//游客姓名
	private String visitPerName;

	//订单处理
	private String certificateStatus;
	private Long version;
	private String supplierNo;
	private String resourceConfirm;
	
	private EbkTask ebkTask;
	private ProdProductService prodProductService;
	private PermUserService permUserService;
	private PublicWorkOrderService publicWorkOrderService;
	private SupplierService supplierService;
	private CertificateService certificateServiceProxy;
	
	private OrderItemMetaAperiodicService orderItemMetaAperiodicService;
	private List<String> validContentList;
	
	public EBK_TASK_REASON[] getReasonList() {
		return EBK_TASK_REASON.values();
	}

	@Action("/ebooking/task/downTodayVisitTaskList")
	public void downTodayVisitTaskList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", this.getCurrentSupplierId());
		params.put("certificateStatus", Constant.EBK_TASK_STATUS.ACCEPT.name());	//今日入住：凭证已经被接受
		params.put("ebkCertificateType", Constant.EBK_CERTIFICATE_TYPE.CONFIRM.name());	//今日入住：确认凭证
		params.put("orderId", orderId);
		params.put("travellerName", travellerName);
		params.put("supplierOrderNo", supplierOrderNo);
		params.put("confirmUser", confirmUser);
		params.put("paymentStatus", paymentStatus);
		params.put("metaBranchId", metaBranchId);
		params.put("visitTimeStart", DateFormatUtils.format(new Date(), "yyyy-MM-dd")+" 00:00:00");
		params.put("visitTimeEnd", DateFormatUtils.format(new Date(), "yyyy-MM-dd")+" 23:59:59");
		params.put("productType", Constant.PRODUCT_TYPE.HOTEL);
		params.put("userMemoStatus", "true");
		// 分页
		ebkTaskPage.setPage(1L);
		ebkTaskPage.setPageSize(65535L);
		ebkTaskPage = ebkCertificateService.queryEbkTaskPageListSQL(this.page, ebkTaskPage.getPageSize(), params);
		try {
			String fileName = ExcelUtils.writeXlsFile(ebkTaskPage.getItems(), "/WEB-INF/resources/template/ebkTaskOrderDetailTemplate.xls");
			this.writeAttachment(fileName, "todayVisitOrder_"+DateFormatUtils.format(new Date(), "yyyyMMddHHmm"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Action("/ebooking/task/todayVisitTaskList")
	public String todayVisitTaskList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", this.getCurrentSupplierId());
		params.put("certificateStatus", Constant.EBK_TASK_STATUS.ACCEPT.name());	//今日入住：凭证已经被接受
		params.put("ebkCertificateType", Constant.EBK_CERTIFICATE_TYPE.CONFIRM.name());	//今日入住：确认凭证
		params.put("orderBy", "ordCreateTimeDesc");
		params.put("orderId", orderId);
		params.put("travellerName", travellerName);
		params.put("supplierOrderNo", supplierOrderNo);
		params.put("confirmUser", confirmUser);
		params.put("paymentStatus", paymentStatus);
		params.put("metaBranchId", metaBranchId);
		params.put("visitTimeStart", DateFormatUtils.format(new Date(), "yyyy-MM-dd")+" 00:00:00");
		params.put("visitTimeEnd", DateFormatUtils.format(new Date(), "yyyy-MM-dd")+" 23:59:59");
		params.put("productType", Constant.PRODUCT_TYPE.HOTEL);
		params.put("userMemoStatus", "true");
		ebkTaskPage = ebkCertificateService.queryEbkTaskPageListSQL(this.page, ebkTaskPage.getPageSize(), params);
		ebkTaskPage.buildUrl(getRequest());
		
		initProductBranch();
		return "todayVisitTaskList";
	}
	@Action("/ebooking/task/downTodayTaskList")
	public void downTodayTaskList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", this.getCurrentSupplierId());
 	    params.put("orderBy", "ordCreateTimeDesc");
		String certType = StringUtils.contains(confirmStatus, "_")?confirmStatus.split("_")[0]:null;
		String certStatus = StringUtils.contains(confirmStatus, "_")?confirmStatus.split("_")[1]:confirmStatus;
		params.put("certificateStatus", certStatus);
		params.put("ebkCertificateType", certType);
		params.put("orderStatus", orderStatus);		
		params.put("orderId", orderId);
		params.put("travellerName", travellerName);
		params.put("supplierOrderNo", supplierOrderNo);
		params.put("confirmUser", confirmUser);
		params.put("paymentStatus", paymentStatus);
		params.put("metaBranchId", metaBranchId);
		params.put("confirmTimeStart", DateFormatUtils.format(new Date(), "yyyy-MM-dd")+" 00:00:00");
		params.put("confirmTimeEnd", DateFormatUtils.format(new Date(), "yyyy-MM-dd")+" 23:59:59");
		params.put("productType", Constant.PRODUCT_TYPE.HOTEL);
		// 分页
		ebkTaskPage.setPage(1L);
		ebkTaskPage.setPageSize(65535L);
		params.put("userMemoStatus", "true");
		ebkTaskPage = ebkCertificateService.queryEbkTaskPageListSQL(this.page, ebkTaskPage.getPageSize(), params);
		try {
			String fileName = ExcelUtils.writeXlsFile(ebkTaskPage.getItems(), "/WEB-INF/resources/template/ebkTaskOrderDetailTemplate.xls");
			this.writeAttachment(fileName, "todayOrderList_"+DateFormatUtils.format(new Date(), "yyyyMMddHHmm"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Action("/ebooking/task/todayTaskList")
	public String todayTaskList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", this.getCurrentSupplierId());
		params.put("orderBy", "ordCreateTimeDesc");
		String certType = StringUtils.contains(confirmStatus, "_")?confirmStatus.split("_")[0]:null;
		String certStatus = StringUtils.contains(confirmStatus, "_")?confirmStatus.split("_")[1]:confirmStatus;
		params.put("certificateStatus", certStatus);
		params.put("ebkCertificateType", certType);
		
		if(StringUtils.isEmpty(certStatus)){
			//今日已处理凭证：非（CREATE）未处理状态
			params.put("EBK_TASK_STATUS_LIST", new String[]{Constant.EBK_TASK_STATUS.ACCEPT.name(), 
											  Constant.EBK_TASK_STATUS.REJECT.name(), 
											  Constant.EBK_TASK_STATUS.CANCEL.name()});
		}
		params.put("orderStatus", orderStatus);
		
		params.put("orderId", orderId);
		params.put("travellerName", travellerName);
		params.put("supplierOrderNo", supplierOrderNo);
		params.put("confirmUser", confirmUser);
		params.put("paymentStatus", paymentStatus);
		params.put("metaBranchId", metaBranchId);
		params.put("confirmTimeStart", DateFormatUtils.format(new Date(), "yyyy-MM-dd"));
		params.put("confirmTimeEnd", DateFormatUtils.format(new Date(), "yyyy-MM-dd")+" 23:59:59");
		params.put("productType", Constant.PRODUCT_TYPE.HOTEL);
		params.put("userMemoStatus", "true");
		ebkTaskPage = ebkCertificateService.queryEbkTaskPageListSQL(this.page, ebkTaskPage.getPageSize(), params);
		ebkTaskPage.buildUrl(getRequest());
		initProductBranch();
		return "todayTaskList";
	}
	@Action("/ebooking/task/downAllTaskList")
	public void downAllTaskList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", this.getCurrentSupplierId());
 	    params.put("orderBy", "ordCreateTimeDesc");
		String certType = StringUtils.contains(confirmStatus, "_")?confirmStatus.split("_")[0]:null;
		String certStatus = StringUtils.contains(confirmStatus, "_")?confirmStatus.split("_")[1]:confirmStatus;
		params.put("certificateStatus", certStatus);
		params.put("ebkCertificateType", certType);
		params.put("isnotcreate", "true");
		params.put("orderStatus", orderStatus);		
		params.put("orderId", orderId);
		params.put("travellerName", travellerName);
		params.put("supplierOrderNo", supplierOrderNo);
		params.put("confirmUser", confirmUser);
		params.put("paymentStatus", paymentStatus);
		params.put("metaBranchId", metaBranchId);
		params.put("visitTimeStart", visitTimeStart);
		if(!StringUtil.isEmptyString(visitTimeEnd)) {
			params.put("visitTimeEnd", visitTimeEnd+" 23:59:59");
		}
		params.put("orderCreateTimeStart", createTimeStart);
		if(!StringUtil.isEmptyString(createTimeEnd)) {
			params.put("orderCreateTimeEnd", createTimeEnd+" 23:59:59");
		}
		params.put("productType", Constant.PRODUCT_TYPE.HOTEL);
		// 分页
		ebkTaskPage.setPage(1L);
		ebkTaskPage.setPageSize(65535L);
		params.put("userMemoStatus", "true");
		ebkTaskPage = ebkCertificateService.queryEbkTaskPageListSQL(this.page, ebkTaskPage.getPageSize(), params);
		try {
			String fileName = ExcelUtils.writeXlsFile(ebkTaskPage.getItems(), "/WEB-INF/resources/template/ebkTaskOrderDetailTemplate.xls");
			this.writeAttachment(fileName, "allOrderList_"+DateFormatUtils.format(new Date(), "yyyyMMddHHmm"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Action("/ebooking/task/allTaskList")
	public String allTaskList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", this.getCurrentSupplierId());
 	    params.put("orderBy", "ordCreateTimeDesc");
		String certType = StringUtils.contains(confirmStatus, "_")?confirmStatus.split("_")[0]:null;
		String certStatus = StringUtils.contains(confirmStatus, "_")?confirmStatus.split("_")[1]:confirmStatus;
		params.put("certificateStatus", certStatus);
		params.put("ebkCertificateType", certType);
		params.put("isnotcreate", "true");
		params.put("orderStatus", orderStatus);		
		params.put("orderId", orderId);
		params.put("travellerName", travellerName);
		params.put("supplierOrderNo", supplierOrderNo);
		params.put("confirmUser", confirmUser);
		params.put("paymentStatus", paymentStatus);
		params.put("metaBranchId", metaBranchId);
		params.put("visitTimeStart", visitTimeStart);
		if(!StringUtil.isEmptyString(visitTimeEnd)) {
			params.put("visitTimeEnd", visitTimeEnd+" 23:59:59");
		}
		params.put("orderCreateTimeStart", createTimeStart);
		if(!StringUtil.isEmptyString(createTimeEnd)) {
			params.put("orderCreateTimeEnd", createTimeEnd+" 23:59:59");
		}
		params.put("productType", Constant.PRODUCT_TYPE.HOTEL);
		
		// 分页
		params.put("userMemoStatus", "true");
		ebkTaskPage = ebkCertificateService.queryEbkTaskPageListSQL(this.page, ebkTaskPage.getPageSize(), params);
		ebkTaskPage.buildUrl(getRequest());
		initProductBranch();
		
		return "allTaskList";
	}
	@Action("/ebooking/task/aperiodicTaskList")
	public String aperiodicTaskList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", this.getCurrentSupplierId());
		params.put("orderId", orderId);
		params.put("travellerName", travellerName);
		params.put("metaBranchId", metaBranchId);
		params.put("orderCreateTimeStart", createTimeStart);
		params.put("orderCreateTimeEnd", createTimeEnd);
		params.put("certificateStatus", Constant.EBK_TASK_STATUS.CREATE.name());
		params.put("paymentStatus", Constant.PAYMENT_STATUS.PAYED.name());
		params.put("productType", Constant.PRODUCT_TYPE.HOTEL);
		params.put("passwordCertificate", passwordCertificate);
		params.put("isAperiodic", Constant.TRUE_FALSE.TRUE.getAttr1());
		params.put("useStatus", Constant.APERIODIC_ACTIVATION_STATUS.UNACTIVATED.name());
		
		// 分页
		params.put("userMemoStatus", "true");
		ebkTaskPage = ebkCertificateService.queryEbkTaskPageListSQL(this.page, ebkTaskPage.getPageSize(), params);
		ebkTaskPage.buildUrl(getRequest());
		initProductBranch();
		
		return "aperiodicTaskList";
	}
	@Action("/ebooking/task/downAperiodicList")
	public void downAperiodicList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", this.getCurrentSupplierId());
		params.put("orderId", orderId);
		params.put("travellerName", travellerName);
		params.put("metaBranchId", metaBranchId);
		params.put("orderCreateTimeStart", createTimeStart);
		params.put("orderCreateTimeEnd", createTimeEnd);
		params.put("passwordCertificate", passwordCertificate);
		params.put("certificateStatus", Constant.EBK_TASK_STATUS.CREATE.name());
		params.put("paymentStatus", Constant.PAYMENT_STATUS.PAYED.name());
		params.put("productType", Constant.PRODUCT_TYPE.HOTEL);
		params.put("isAperiodic", Constant.TRUE_FALSE.TRUE.getAttr1());
		params.put("useStatus", Constant.APERIODIC_ACTIVATION_STATUS.UNACTIVATED.name());
		// 分页
		ebkTaskPage.setPage(1L);
		ebkTaskPage.setPageSize(65535L);
		params.put("userMemoStatus", "true");
		ebkTaskPage = ebkCertificateService.queryEbkTaskPageListSQL(this.page, ebkTaskPage.getPageSize(), params);
		try {
			String fileName = ExcelUtils.writeXlsFile(ebkTaskPage.getItems(), "/WEB-INF/resources/template/ebkTaskOrderDetailTemplate.xls");
			this.writeAttachment(fileName, "allAperiodicList_"+DateFormatUtils.format(new Date(), "yyyyMMddHHmm"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Action("/ebooking/task/downConfirmTaskList")
	public void downConfirmTaskList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", this.getCurrentSupplierId());
 	    params.put("orderBy", "ordCreateTimeDesc");
 	    params.put("ebkCertificateType", certType);
		params.put("certificateStatus", Constant.EBK_TASK_STATUS.CREATE.name());
		params.put("orderId", orderId);
		params.put("travellerName", travellerName);
		params.put("metaBranchId", metaBranchId);
		params.put("productType", Constant.PRODUCT_TYPE.HOTEL);
		params.put("isAperiodic", Constant.TRUE_FALSE.FALSE.getAttr1());
		ebkTaskPage.buildUrl(getRequest());
		ebkTaskPage.setCurrentPage(this.page);
		// 分页
		params.put("start", ebkTaskPage.getStartRows());
		params.put("end", ebkTaskPage.getEndRows());
		params.put("certificateStatus", Constant.EBK_TASK_STATUS.CREATE);
		params.put("productType", Constant.PRODUCT_TYPE.HOTEL);
		
		
		// 分页
		ebkTaskPage.setPage(1L);
		ebkTaskPage.setPageSize(65535L);
		params.put("userMemoStatus", "true");
		ebkTaskPage = ebkCertificateService.queryEbkTaskPageListSQL(this.page, ebkTaskPage.getPageSize(), params);
		//List<EbkTask>
		try {
			String fileName = ExcelUtils.writeXlsFile(ebkTaskPage.getItems(), "/WEB-INF/resources/template/ebkTaskOrderDetailTemplate.xls");
			this.writeAttachment(fileName, "allConfirmOrderList_"+DateFormatUtils.format(new Date(), "yyyyMMddHHmm"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	/**
	 * 待处理EBK订单任务
	 * 
	 * @author: ranlongfei 2012-12-7 下午3:10:01
	 * @return
	 */
	@Action("/ebooking/task/confirmTaskList")
	public String confirmTaskList() {

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", this.getCurrentSupplierId());
		params.put("orderStatus", orderStatus);
		params.put("orderId", orderId);
		params.put("travellerName", travellerName);
		params.put("ebkCertificateType", certType);
		params.put("metaBranchId", metaBranchId);
		
		params.put("certificateStatus", Constant.EBK_TASK_STATUS.CREATE);
		params.put("productType", Constant.PRODUCT_TYPE.HOTEL);
		params.put("orderBy", "ordCreateTimeAsc");
		params.put("userMemoStatus", "true");
		params.put("isAperiodic", Constant.TRUE_FALSE.FALSE.getAttr1());
		ebkTaskPage	= ebkCertificateService.queryEbkTaskPageListSQL(this.page, ebkTaskPage.getPageSize(), params);
		ebkTaskPage.buildUrl(getRequest());
		initProductBranch();
		
		return "confirmTaskList";
	
	}
	
	private void initProductBranch() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", this.getCurrentSupplierId());
		params.put("productType", Constant.PRODUCT_TYPE.HOTEL.name());	
		
		params.put("isSearchEbookingProduct", "YES");
		ebkMetaBranchList = this.metaProductBranchService.getEbkMetaBranch(params);
	}
	/**
	 * 订单详情查询页面
	 * 
	 * @author: ranlongfei 2012-12-14 下午4:58:26
	 * @return
	 */
	@Action("/ebooking/task/orderDetail")
	public String orderDetail() {
		ebkTask = this.ebkTaskService.findEbkTaskAndCertificateAndGetContentByPkId(ebkTaskId);
		if(ebkTask == null || ebkTask.getEbkCertificate() == null){
			ebkTask = null;
			return "orderck";
		}
		//如果为不定期订单
		if(checkCanTOActivate(ebkTask)){
			validContentList = getValidContentList(ebkTask.getOrderId(), ebkTask.getEbkCertificate().getSupplierId());
			//未激活不定期订单查看
			if(Constant.APERIODIC_ACTIVATION_STATUS.UNACTIVATED.name().equalsIgnoreCase(ebkTask.getEbkCertificate().getUseStatus())){
				return "unUserOrderck";
			}else{
				return "userOrderck";
			}
		}
		if(!Constant.EBK_TASK_STATUS.CREATE.name().equals(ebkTask.getEbkCertificate().getCertificateStatus())) {
			return "orderck";
		}
		if(Constant.EBK_CERTIFICATE_TYPE.CANCEL.name().equals(ebkTask.getEbkCertificate().getEbkCertificateType())) {
			return "orderqx";
		} else if(Constant.EBK_CERTIFICATE_TYPE.CONFIRM.name().equals(ebkTask.getEbkCertificate().getEbkCertificateType())) {
			return "orderyd";
		}else if(Constant.EBK_CERTIFICATE_TYPE.CHANGE.name().equals(ebkTask.getEbkCertificate().getEbkCertificateType())) {
			return "orderbg";
		}		
		return "orderck";
	}
	
	private List<String> getValidContentList(Long orderId, Long supplierId) {
		List<String> resultList = null;
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		if(order != null) {
			resultList = new ArrayList<String>();
			for(OrdOrderItemMeta item : order.getAllOrdOrderItemMetas()){
				if (item.getSupplierId().longValue() != supplierId.longValue()) {
					continue;
				}
				Long metaId = item.getOrderItemMetaId();
				OrdOrderItemMetaAperiodic aperiodic = orderItemMetaAperiodicService.selectOrderAperiodicByOrderItemMetaId(metaId);
				if(aperiodic != null) {
					String validContent = item.getZhBranchName() + "     " + DateUtil.formatDate(aperiodic.getValidBeginTime(), "yyyy-MM-dd") + "至" + DateUtil.formatDate(aperiodic.getValidEndTime(), "yyyy-MM-dd");
					if(org.apache.commons.lang3.StringUtils.isNotEmpty(aperiodic.getInvalidDateMemo())) {
						validContent += "("+aperiodic.getInvalidDateMemo()+")";
					}
					resultList.add(validContent);
				}
			}
		}
		return resultList;
	}
	
	private boolean checkCanTOActivate(EbkTask ebkTask){
		//判断是否为不定期订单
		if(!Constant.TRUE_FALSE.TRUE.getAttr1().equalsIgnoreCase(ebkTask.getEbkCertificate().getIsAperiodic())){
			return false;
		}
		//判断订单状态是否为已支付
		if(!Constant.PAYMENT_STATUS.PAYED.name().equalsIgnoreCase(ebkTask.getPaymentStatus())){
			return false;
		}
		//判断订单状态为CREATE或ACCEPT
		if(!Constant.EBK_TASK_STATUS.CREATE.name().equalsIgnoreCase(ebkTask.getEbkCertificate().getCertificateStatus())&&!Constant.EBK_TASK_STATUS.ACCEPT.name().equalsIgnoreCase(ebkTask.getEbkCertificate().getCertificateStatus())){
			return false;
		}
		return true;
	}

	/**
	 * 查询订单
	 * 
	 * @author: ranlongfei 2012-12-17 下午4:19:15
	 * @return
	 */
	@Action("/ebooking/task/orderck")
	public String orderck() {
		ebkTask = this.ebkTaskService.findEbkTaskAndCertificateAndGetContentByPkId(ebkTaskId);
		return "orderck";
	}
	
	/**
	 * 记录操作日志
	 * 
	 * @author: ranlongfei 2012-12-17 下午2:06:24
	 * @param task
	 * @param logType
	 * @param content
	 */
	private void logRecord(EbkTask task, String logType, String content) {
		try{
			for(EbkCertificateItem item : task.getEbkCertificate().getEbkCertificateItemList()) {
				comLogRemoteService.insert(Constant.COM_LOG_OBJECT_TYPE.EBK_ORDER_TASK.name(),
						task.getOrderId(), item.getOrderItemMetaId(), this.getSessionUserName(),
						task.getEbkCertificate().getCertificateTypeStatus(),
						logType, content, Constant.COM_LOG_OBJECT_TYPE.ORD_ORDER.name());/**/
			}
		} catch (Exception e) {
			e.printStackTrace();
			//this.sendAjaxResultByJson(JSONObject.fromObject(new ResultHandle(e.getMessage())).toString());
		}
	}
	/**
	 * 确认资源  fxj
	 * 
	 * @author: ranlongfei 2012-12-14 下午5:16:21
	 */
	@Action("/ebooking/task/confirm")
	public void confirm() {
		ResultHandle result= new ResultHandle();
		EbkTask ebkTask= ebkTaskService.findEbkTaskAndCertificateByPkId(ebkTaskId);
		if(ebkTask == null || ebkTask.getEbkCertificate() == null) {
			result.setMsg("订单已经删除，请刷新再处理");
			this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
			return;
		}
		if("false".equals(ebkTask.getEbkCertificate().getUserMemoStatus())) {
			result.setMsg("客户信息有待核实，请稍后处理");
			this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
			return;
		}
		if("false".equals(ebkTask.getEbkCertificate().getUserMemoStatus())) {
			result.setMsg("客户信息有待核实，请稍后处理");
			this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
			return;
		}
		result= ebkTaskService.updateEbk(ebkTaskId, certificateStatus, null, memo, this.getSessionUserName(), Constant.EBK_TASK_REASON.getCnName(reason), version);
		if(result.isFail()) {
			this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
			return;
		}

		for(EbkCertificateItem item : ebkTask.getEbkCertificate().getEbkCertificateItemList()){
			orderServiceProxy.updateCertificateStatusAndTypeOrConfirmChannel(
					item.getOrderItemMetaId(),
					certificateStatus, 
					ebkTask.getEbkCertificate().getEbkCertificateType(),
					Constant.EBK_CERTIFICATE_CONFIRM_CHANNEL.EBK.name()
					);
		}
		if("true".equals(ebkTask.getResourceConfirm())&&Constant.EBK_TASK_STATUS.ACCEPT.name().equals(certificateStatus)){
			for(EbkCertificateItem ebkCertificateItem:ebkTask.getEbkCertificate().getEbkCertificateItemList()){
				boolean bo=this.orderServiceProxy.resourceAmple(ebkCertificateItem.getOrderItemMetaId(),this.getSessionUserName(),memo,null);
				if(bo==false){
					result.setMsg("资源确认错误");
					this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
					return;
				}
			}
		}
		if(Constant.EBK_TASK_STATUS.REJECT.name().equals(certificateStatus)) {
			this.logRecord(ebkTask, "不接受预订", "不接受预订，原因是："+Constant.EBK_TASK_REASON.getCnName(reason));
		}else {
			reason = "";
			this.logRecord(ebkTask, "接受预订", "已接受预订");
		}
		
		if(!StringUtil.isEmptyString(supplierNo)){
			ebkTaskService.updateEbkCertificateItemSupplierNo(supplierNo,ebkTaskId);
		}
//		创建工单
		this.createWorkOrder(ebkTask,certificateStatus,reason, memo);
		this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	}
	/**
	 * 确认取消
	 * 
	 * @author: ranlongfei 2012-12-14 下午5:16:40
	 */
	@Action("/ebooking/task/cancel")
	public void cancel() {
		EbkTask ebktask = this.ebkTaskService.findEbkTaskAndCertificateByPkId(ebkTaskId);
		ResultHandle result= ebkTaskService.updateEbk(ebkTaskId, certificateStatus, null, memo, this.getSessionUserName(),null, version);
		this.logRecord(ebktask, "确认取消", "已确认取消");
		this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	} 
	/**
	 * 更新
	 * 
	 * @author: ranlongfei 2012-12-14 下午5:16:53
	 */

	//private String ebkCertificateId;
	@Action("/ebooking/task/update")
	public void update() {
		EbkTask ebktask = this.ebkTaskService.findEbkTaskAndCertificateByPkId(ebkTaskId);
		ResultHandle result=ebkTaskService.updateEbkCertificateItemSupplierNo(supplierNo,ebkTaskId);
		this.logRecord(ebktask, "修改订单", "更改酒店确认号为" + supplierNo);
		this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	} 
	@Action("/ebooking/task/updatebg")
	public void updatebg() {
		ResultHandle result = new ResultHandle();
		EbkTask ebkTask = this.ebkTaskService.findEbkTaskAndCertificateByPkId(ebkTaskId);
		if(ebkTask == null || ebkTask.getEbkCertificate() == null) {
			result.setMsg("订单已经删除，请刷新再处理");
			this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
			return;
		}
		if(null!=ebkTask.getEbkCertificate().getUserMemoStatus()&&"false".equals(ebkTask.getEbkCertificate().getUserMemoStatus())) {
			result.setMsg("客户信息有待核实，请稍后处理");
			this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
			return;
		}
		result = ebkTaskService.updateEbk(ebkTaskId, certificateStatus, null, memo, this.getSessionUserName(),Constant.EBK_TASK_REASON.getCnName(reason), version);	
		if(result.isFail()) {
			this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
			return;
		}
		for(EbkCertificateItem item:ebkTask.getEbkCertificate().getEbkCertificateItemList()){
			orderServiceProxy.updateCertificateStatusAndTypeOrConfirmChannel(
				item.getOrderItemMetaId(),
				certificateStatus, 
				ebkTask.getEbkCertificate().getEbkCertificateType(),
				Constant.EBK_CERTIFICATE_CONFIRM_CHANNEL.EBK.name()
			);
		}
		if(!StringUtil.isEmptyString(supplierNo)) {
			result=ebkTaskService.updateEbkCertificateItemSupplierNo(supplierNo,ebkTaskId);
		}
		if(Constant.EBK_TASK_STATUS.REJECT.name().equals(certificateStatus)) {
			this.logRecord(ebkTask, "不接受变更", "不接受变更，原因是："+Constant.EBK_TASK_REASON.getCnName(reason));
		}else {
			this.logRecord(ebkTask, "接受变更", "已接受变更");
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
//		创建工单
		this.createWorkOrder(ebkTask,certificateStatus,reason, memo);
	} 
	/**
	 * 任务日志
	 * 
	 * @author: ranlongfei 2012-12-17 上午10:45:53
	 * @return
	 */
	@Action("/ebooking/task/ebkTaskLog")
	public String ebkTaskLog() {
		EbkTask task = this.ebkTaskService.findEbkTaskAndCertificateByPkId(ebkTaskId);
		if(task != null && task.getEbkCertificate().getEbkCertificateItemList().size() > 0) {
			logList = comLogRemoteService.queryByObjectId(Constant.COM_LOG_OBJECT_TYPE.EBK_ORDER_TASK.name(), task.getEbkCertificate().getEbkCertificateItemList().get(0).getOrderItemMetaId());
		}
		return "ebkTaskLog";
	}
	/**
	 * 新建工单
	 * @param ebkTask
	 * @param memo
	 * @return
	 */
	private boolean createWorkOrder(EbkTask ebkTask,String status,String reason,String memo){
		if(!Constant.EBK_TASK_STATUS.REJECT.name().equals(status)){
			return false;
		}
		OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(ebkTask.getOrderId());
		String errMsg="";
		try{
			SupSupplier supSupplier=supplierService.getSupplier(this.getSessionUser().getSupplierId());
			String taskContent="供应商名称："+this.getSessionUser().getSupplierName()+"，<br/>供应商电话："+supSupplier.getTelephone()+"，<br/>" +
					"类型："+ ebkTask.getEbkCertificate().getZhEbkCertificateType() +"，<br/>确认状态："
					+Constant.EBK_CERTIFICATE_TYPE_AND_STATUS.getCnName(ebkTask.getEbkCertificate().getEbkCertificateType()+"_"+Constant.EBK_TASK_STATUS.REJECT.name())
					+"，<br/>供应商理由："+Constant.EBK_TASK_REASON.getCnName(reason)+"<br/>"+memo;
			ProdProduct prodProduct=prodProductService.getProdProduct(order.getMainProduct().getProductId());
			PermUser permUser=permUserService.getPermUserByUserId(prodProduct.getManagerId());
			WorkOrderCreateParam param = EbkRejectWorkOrder.initParam(order, ebkTask, prodProduct, permUser, supSupplier, taskContent);
			if(param==null){
				log.info("EbkRejectWorkOrder init param error");
				return false;
			}else{
				InvokeResult ivokeresult = publicWorkOrderService.createWorkOrder(param); 
				if(ivokeresult!=null &&ivokeresult.getCode() == 0){ 
					log.debug("success created work order:workOrderId="+ivokeresult.getWorkOrderId()+", workTaskId="+ivokeresult.getWorkTaskId());
					return true;
				}else{
					if(ivokeresult!=null)
					{
						errMsg=ivokeresult.getDescription();
						log.error(ivokeresult.getDescription());
					}
				}
			}
		}catch(Exception ex){
			log.error(ex);
		}
		comLogRemoteService.insert("ORD_ORDER", null, order.getOrderId(), this.getSessionUserName(), "WORK_ORDER_SEND_ERROR",
				"WORK_ORDER_SEND_ERROR", "发送EBK不接受工单异常"+errMsg,null);
		return false;
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

	public MetaProductBranchService getMetaProductBranchService() {
		return metaProductBranchService;
	}
	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}
	public List<MetaProductBranch> getEbkMetaBranchList() {
		return ebkMetaBranchList;
	}

	public void setEbkMetaBranchList(List<MetaProductBranch> ebkMetaBranchList) {
		this.ebkMetaBranchList = ebkMetaBranchList;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getTravellerName() {
		return travellerName;
	}

	public void setTravellerName(String travellerName) {
		try {
			this.travellerName = new String(java.net.URLDecoder.decode(travellerName, "utf-8").getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getMetaBranchId() {
		return metaBranchId;
	}

	public void setMetaBranchId(String metaBranchId) {
		this.metaBranchId = metaBranchId;
	}

	public Long getEbkTaskId() {
		return ebkTaskId;
	}

	public void setEbkTaskId(Long ebkTaskId) {
		this.ebkTaskId = ebkTaskId;
	}

	public EbkTask getTask() {
		return task;
	}

	public void setTask(EbkTask task) {
		this.task = task;
	}
	public List<OrdOrderItemMetaTime> getTime() {
		return time;
	}

	public void setTime(List<OrdOrderItemMetaTime> time) {
		this.time = time;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getReason() {
		return reason;
	}
	public void setReason(String reason) {
		this.reason = reason;
	}
	public String getSupplierOrderNo() {
		return supplierOrderNo;
	}
	public void setSupplierOrderNo(String supplierOrderNo) {
		this.supplierOrderNo = supplierOrderNo;
	}
	
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public String getConfirmUser() {
		return confirmUser;
	}
	public void setConfirmUser(String confirmUser) {
		try {
			this.confirmUser = new String(java.net.URLDecoder.decode(confirmUser, "utf-8").getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	
	public ComLogService getComLogRemoteService() {
		return comLogRemoteService;
	}
	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
	}
	public List<ComLog> getLogList() {
		return logList;
	}
	public void setLogList(List<ComLog> logList) {
		this.logList = logList;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
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
	public String getConfirmStatus() {
		return confirmStatus;
	}
	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
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


	public EbkCertificateService getEbkCertificateService() {
		return ebkCertificateService;
	}
	public void setEbkCertificateService(EbkCertificateService ebkCertificateService) {
		this.ebkCertificateService = ebkCertificateService;
	}

	public EbkTask getEbkTask() {
		return ebkTask;
	}

	public void setEbkTask(EbkTask ebkTask) {
		this.ebkTask = ebkTask;
	}

	public String getCertificateStatus() {
		return certificateStatus;
	}

	public void setCertificateStatus(String certificateStatus) {
		this.certificateStatus = certificateStatus;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public String getSupplierNo() {
		return supplierNo;
	}

	public void setSupplierNo(String supplierNo) {
		this.supplierNo = supplierNo;
	}

	public String getResourceConfirm() {
		return resourceConfirm;
	}

	public void setResourceConfirm(String resourceConfirm) {
		this.resourceConfirm = resourceConfirm;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}

	public void setPublicWorkOrderService(
			PublicWorkOrderService publicWorkOrderService) {
		this.publicWorkOrderService = publicWorkOrderService;
	}

	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public Page<EbkTask> getEbkTaskPage() {
		return ebkTaskPage;
	}

	public void setEbkTaskPage(Page<EbkTask> ebkTaskPage) {
		this.ebkTaskPage = ebkTaskPage;
	}
	
	public String getPasswordCertificate() {
		return passwordCertificate;
	}

	public void setPasswordCertificate(String passwordCertificate) {
		this.passwordCertificate = passwordCertificate;
	}

	public String getUseStatus() {
		return useStatus;
	}

	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	

	public String getVisitPerName() {
		return visitPerName;
	}

	public void setVisitPerName(String visitPerName) {
		this.visitPerName = visitPerName;
	}

	public OrderItemMetaAperiodicService getOrderItemMetaAperiodicService() {
		return orderItemMetaAperiodicService;
	}

	public void setOrderItemMetaAperiodicService(
			OrderItemMetaAperiodicService orderItemMetaAperiodicService) {
		this.orderItemMetaAperiodicService = orderItemMetaAperiodicService;
	}

	public CertificateService getCertificateServiceProxy() {
		return certificateServiceProxy;
	}

	public void setCertificateServiceProxy(
			CertificateService certificateServiceProxy) {
		this.certificateServiceProxy = certificateServiceProxy;
	}

	public List<String> getValidContentList() {
		return validContentList;
	}

}
