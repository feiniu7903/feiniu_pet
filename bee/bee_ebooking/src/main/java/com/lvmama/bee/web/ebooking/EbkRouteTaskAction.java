package com.lvmama.bee.web.ebooking;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.bee.web.EbkBaseAction;
import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ebooking.EbkTask;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaAperiodic;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.GroupAdviceNoteService;
import com.lvmama.comm.bee.service.ebooking.CertificateService;
import com.lvmama.comm.bee.service.ebooking.EbkCertificateService;
import com.lvmama.comm.bee.service.ebooking.EbkTaskService;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.ord.OrderItemMetaAperiodicService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.perm.PermUser;
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
/**
 * EBK线路订单任务
 * @author Alex.z 2013-03-27
 * @version
 */
@ParentPackage("json-default")
@Results(value={
		@Result(name="confirmRouteTaskList",location="/WEB-INF/pages/ebooking/task/confirmRouteTaskList.jsp"),
		@Result(name="groupAdviceNoteList",location="/WEB-INF/pages/ebooking/task/groupAdviceNoteList.jsp"),
		@Result(name="allRouteTaskList",location="/WEB-INF/pages/ebooking/task/allRouteTaskList.jsp"),
		@Result(name="aperiodicRouteTaskList",location="/WEB-INF/pages/ebooking/task/aperiodicRouteTaskList.jsp"),
		@Result(name = "enquireSeatOrderTask", location = "/WEB-INF/pages/ebooking/task/allEbkCertificateItemList.jsp"),
		@Result(name = "enquireSeatOrderView", location = "/WEB-INF/pages/ebooking/task/allEbkCertificateView.jsp")
	
})
public class EbkRouteTaskAction extends EbkBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 7149773095882848067L;
	
	private Page<EbkTask> ebkTaskPage = new Page<EbkTask>();
	private List<EbkTask> ebkTaskList= new ArrayList<EbkTask>();
	private EbkTaskService ebkTaskService;
	private EbkCertificateService ebkCertificateService;
	private OrderService orderServiceProxy;	
	private MetaProductBranchService metaProductBranchService;
	private CertificateService certificateServiceProxy;
	private GroupAdviceNoteService groupAdviceNoteService;

	private Long orderId;
	private String metaProductId;
	private String metaProductName;
	private String travellerName;
	private String certType;
	private String metaBranchId;
	private String orderStatus;
	private String visitTimeStart;
	private String visitTimeEnd;
	private String createTime;
	//密码券号
	private String passwordCertificate;
	private String useStatus;

	private String confirmStatus;
	private String orderBy;
	
	private EbkCertificate ebkCertificate;
	private EbkTask ebkTask;
	private Long ebkTaskId;
	private Long version;
	private String memo;
	private String certificateStatus;
	private String waitTime;
	private String txtWaitTime;
	private ComLogService comLogRemoteService; 	
	private ProdProductService prodProductService;
	private PermUserService permUserService;
	private PublicWorkOrderService publicWorkOrderService;
	private SupplierService supplierService;
	private OrderItemMetaAperiodicService orderItemMetaAperiodicService;
	private MetaProductService metaProductService;
	private List<String> validContentList;
	private String groupWordStatus;
	private String operatorName = this.getSessionUserName();

	private Map<String,Object> ebkCertificateData = new HashMap<String, Object>();
	@Action("/ebooking/task/enquireSeatOrderTask")
	public String enquireSeatOrderTask(){
		ebkTask=ebkTaskService.findEbkTaskAndCertificateAndGetContentByPkId(ebkTaskId);
		if(ebkTask == null || ebkTask.getEbkCertificate() == null) {
			ebkTask = null;
			return "enquireSeatOrderView";
		}
		if(!ebkTask.getEbkCertificate().hasCertificateStatusCreate()) {
			return "enquireSeatOrderView";
		}
		return "enquireSeatOrderTask";
	}
	
	@Action("/ebooking/task/enquireSeatOrderView")
	public String enquireSeatOrderView(){
		ebkTask=ebkTaskService.findEbkTaskAndCertificateAndGetContentByPkId(ebkTaskId);
		return "enquireSeatOrderView";
	}
	

	@Action("/ebooking/task/enquireAperiodicOrderView")
	public String enquireAperiodicOrderView(){
		ebkTask=ebkTaskService.findEbkTaskAndCertificateAndGetContentByPkId(ebkTaskId);
		if(!checkCanTOActivate(ebkTask)){
			return "enquireSeatOrderView";
		}
		validContentList = getValidContentList(ebkTask.getOrderId(), ebkTask.getEbkCertificate().getSupplierId());
		//未激活不定期订单查看
		if(Constant.APERIODIC_ACTIVATION_STATUS.UNACTIVATED.name().equalsIgnoreCase(ebkTask.getEbkCertificate().getUseStatus())){
			return "unUserRouteOrderck";
		}else{
			return "userRouteOrderck";
		}
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
	
	@Action("/ebooking/task/confirmRouteTaskList")
	public String confirmRouteTaskList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", this.getCurrentSupplierId());
		params.put("orderBy", orderBy);
		if(StringUtils.isEmpty(this.orderBy)) {
			params.put("orderBy", "orderbyPayedEnqId");
		}
		params.put("orderId", orderId);
		params.put("metaProductId", metaProductId);
		params.put("metaProductName", metaProductName);
		params.put("certificateStatus", Constant.EBK_TASK_STATUS.CREATE);
		params.put("productType", Constant.PRODUCT_TYPE.ROUTE);
		params.put("visitTimeStart", visitTimeStart);
		if(!StringUtil.isEmptyString(visitTimeEnd)) {
			params.put("visitTimeEnd", visitTimeEnd+" 23:59:59");
		}
		params.put("userMemoStatus", "true");
		params.put("isAperiodic", Constant.TRUE_FALSE.FALSE.getAttr1());
		ebkTaskPage	= ebkCertificateService.queryEbkTaskPageListSQL(this.page, ebkTaskPage.getPageSize(), params);
		ebkTaskPage.buildUrl(getRequest());
		
		return "confirmRouteTaskList";
	}
	
	@Action("/ebooking/task/groupAdviceNoteList")
	public String groupAdviceNoteList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", this.getCurrentSupplierId());
		params.put("orderBy", orderBy);
		if(StringUtils.isEmpty(this.orderBy)) {
			params.put("orderBy", "visitTimeAsc");
		}
		params.put("orderId", orderId);
		params.put("metaProductId", metaProductId);
		params.put("metaProductName", metaProductName);
		params.put("productType", Constant.PRODUCT_TYPE.ROUTE);
		params.put("visitTimeStart", visitTimeStart);
		if(!StringUtil.isEmptyString(visitTimeEnd)) {
			params.put("visitTimeEnd", visitTimeEnd+" 23:59:59");
		}
		params.put("userMemoStatus", "true");
		params.put("groupNotice", "groupNotice");
		params.put("groupWordStatus", groupWordStatus);
		ebkTaskPage	= ebkCertificateService.queryEbkTaskPageListSQL(this.page, ebkTaskPage.getPageSize(), params);
		ebkTaskPage.buildUrl(getRequest());
		
		return "groupAdviceNoteList";
	}
	
	@Action("/ebooking/task/allRouteTaskList")
	public String allRouteTaskList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", this.getCurrentSupplierId());
 	    params.put("orderBy", orderBy);
		params.put("orderId", orderId);
		params.put("metaProductId", metaProductId);
		params.put("metaProductName",metaProductName);
		params.put("visitTimeStart", visitTimeStart);
		if(!StringUtil.isEmptyString(visitTimeEnd)) {
			params.put("visitTimeEnd", visitTimeEnd+" 23:59:59");
		}
		if(!StringUtil.isEmptyString(createTime)) {
			params.put("createTimeStart", createTime);
			params.put("createTimeEnd", createTime+" 23:59:59");
		}
		params.put("orderStatus", orderStatus);		
		params.put("isnotcreate", "true");//全部订单不查询未处理的订单
		String certType = StringUtils.contains(confirmStatus, "_")?confirmStatus.split("_")[0]:null;
		String certStatus = StringUtils.contains(confirmStatus, "_")?confirmStatus.split("_")[1]:confirmStatus;
		params.put("certificateStatus", certStatus);
		params.put("ebkCertificateType", certType);
		params.put("productType", Constant.PRODUCT_TYPE.ROUTE);
		params.put("userMemoStatus", "true");
		params.put("groupWordStatus", groupWordStatus);
		ebkTaskPage	= ebkCertificateService.queryEbkTaskPageListSQL(this.page, ebkTaskPage.getPageSize(), params);
		ebkTaskPage.buildUrl(getRequest());
		return "allRouteTaskList";
	}
	@Action("/ebooking/task/aperiodicRouteTaskList")
	public String aperiodicRouteTaskList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", this.getCurrentSupplierId());
		params.put("orderId", orderId);
		params.put("metaProductName",metaProductName);
		params.put("visitTimeStart", visitTimeStart);
		params.put("visitTimeEnd", visitTimeEnd);
		params.put("passwordCertificate", passwordCertificate);
		params.put("certificateStatus", Constant.EBK_TASK_STATUS.CREATE.name());
		params.put("paymentStatus", Constant.PAYMENT_STATUS.PAYED.name());
		params.put("productType", Constant.PRODUCT_TYPE.ROUTE);
		params.put("useStatus", Constant.APERIODIC_ACTIVATION_STATUS.UNACTIVATED.name());
		params.put("isAperiodic", Constant.TRUE_FALSE.TRUE.getAttr1());
		// 分页
		params.put("userMemoStatus", "true");
		ebkTaskPage	= ebkCertificateService.queryEbkTaskPageListSQL(this.page, ebkTaskPage.getPageSize(), params);
		ebkTaskPage.buildUrl(getRequest());
		return "aperiodicRouteTaskList";
	}
	
	@Action("/ebooking/task/downAperiodicRouteTaskList")
	public void downAperiodicRouteTaskList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", this.getCurrentSupplierId());
		params.put("orderId", orderId);
		params.put("metaProductName",metaProductName);
		params.put("visitTimeStart", visitTimeStart);
		params.put("visitTimeEnd", visitTimeEnd);
		params.put("passwordCertificate", passwordCertificate);
		params.put("certificateStatus", Constant.EBK_TASK_STATUS.CREATE.name());
		params.put("paymentStatus", Constant.PAYMENT_STATUS.PAYED.name());
		params.put("productType", Constant.PRODUCT_TYPE.ROUTE);
		params.put("useStatus", Constant.APERIODIC_ACTIVATION_STATUS.UNACTIVATED.name());
		params.put("isAperiodic", Constant.TRUE_FALSE.TRUE.getAttr1());
		// 分页
		ebkTaskPage.setPage(1L);
		ebkTaskPage.setPageSize(65535L);
		ebkTaskPage = ebkCertificateService.queryEbkTaskPageListSQL(this.page, ebkTaskPage.getPageSize(), params);
		try {
			ebkTaskList = ebkTaskPage.getItems();
			for(EbkTask ebkTask:ebkTaskList){
				EbkCertificate ebkCertificate=this.ebkCertificateService.selectEbkCertDetailAndGetContentByPrimaryKey(ebkTask.getEbkCertificateId());
				ebkTask.setEbkCertificate(ebkCertificate);	
			}
			// 导出csv
			writeCsvFile(ebkTaskList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Action("/ebooking/task/downConfirmRouteTaskList")
	public void downConfirmRouteTaskList() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", this.getCurrentSupplierId());
 	    params.put("orderBy", orderBy);
		params.put("certificateStatus", Constant.EBK_TASK_STATUS.CREATE.name());
		params.put("productType", Constant.PRODUCT_TYPE.ROUTE);
 	    params.put("orderBy", "ordCreateTimeDesc");
		params.put("orderId", orderId);
		params.put("metaProductId", metaProductId);
		params.put("metaProductName", metaProductName);
		params.put("visitTimeStart", visitTimeStart);
		if(!StringUtil.isEmptyString(visitTimeEnd)) {
			params.put("visitTimeEnd", visitTimeEnd+" 23:59:59");
		}
		params.put("isAperiodic", Constant.TRUE_FALSE.FALSE.getAttr1());
		// 分页
		ebkTaskPage.setPage(1L);
		ebkTaskPage.setPageSize(65535L);
		ebkTaskPage = ebkCertificateService.queryEbkTaskPageListSQL(this.page, ebkTaskPage.getPageSize(), params);
		try {
			ebkTaskList = ebkTaskPage.getItems();
			for(EbkTask ebkTask:ebkTaskList){
				EbkCertificate ebkCertificate=this.ebkCertificateService.selectEbkCertDetailAndGetContentByPrimaryKey(ebkTask.getEbkCertificateId());
				ebkTask.setEbkCertificate(ebkCertificate);	
			}
			// 导出csv
			writeCsvFile(ebkTaskList);
			//this.writeAttachment(fileName, "allConfirmRouteTaskList_"+DateFormatUtils.format(new Date(), "yyyyMMddHHmm"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Action("/ebooking/task/downAllRouteTaskList")
	public void downAllRouteTaskList() {
		Map<String, Object> params = new HashMap<String, Object>();
		String certType = StringUtils.contains(confirmStatus, "_")?confirmStatus.split("_")[0]:null;
		String certStatus = StringUtils.contains(confirmStatus, "_")?confirmStatus.split("_")[1]:confirmStatus;
		params.put("certificateStatus", certStatus);
 	    params.put("orderBy", "visitTimeDesc");
		params.put("ebkCertificateType", certType);
		params.put("supplierId", this.getCurrentSupplierId());
		params.put("orderId", orderId);
		params.put("isnotcreate", "true");//全部订单不查询未处理的订单
		params.put("metaProductId", metaProductId);
		params.put("metaProductName", metaProductName);
		params.put("visitTimeStart", visitTimeStart);
		if(!StringUtil.isEmptyString(visitTimeEnd)) {
			params.put("visitTimeEnd", visitTimeEnd+" 23:59:59");
		}
		params.put("orderStatus", orderStatus);		
		params.put("productType", Constant.PRODUCT_TYPE.ROUTE);
		// 分页
		ebkTaskPage.setPage(1L);
		ebkTaskPage.setPageSize(65535L);
		ebkTaskPage = ebkCertificateService.queryEbkTaskPageListSQL(this.page, ebkTaskPage.getPageSize(), params);
		try {
			ebkTaskList = ebkTaskPage.getItems();
			for(EbkTask ebkTask:ebkTaskList){
				EbkCertificate ebkCertificate=this.ebkCertificateService.selectEbkCertDetailAndGetContentByPrimaryKey(ebkTask.getEbkCertificateId());
				ebkTask.setEbkCertificate(ebkCertificate);	
			}
			// 导出csv
			writeCsvFile(ebkTaskList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	@Action("/ebooking/task/orderTaskSave")
	public void orderTaskSave() {
		try {			
			ResultHandle result=new ResultHandle();
			Date wait = null;
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
			if(Constant.EBK_TASK_STATUS.ACCEPT.name().equals(certificateStatus)&&
				Constant.EBK_CERTIFICATE_TYPE.ENQUIRY.name().equals(ebkTask.getEbkCertificate().getEbkCertificateType())) {
				if(StringUtil.isEmptyString(txtWaitTime)) {
					result.setMsg("支付等待时间不能为空");
					this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
					return;
				}
				if(!StringUtil.isEmptyString(waitTime)) {
					wait = DateUtil.stringToDate(waitTime, "yyyy-MM-dd HH:mm");
					if(wait.before(new Date())) {	
						result.setMsg("支付等待时间应大于当前时间");
						this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
						return;
					}
				}
			}
			result = ebkTaskService.updateEbk(ebkTaskId, certificateStatus, wait, memo, this.getSessionUserName(),null, version);
			if(result.isFail()){
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
			if("true".equals(ebkTask.getResourceConfirm())
					&&Constant.EBK_CERTIFICATE_TYPE.ENQUIRY.name().equals(ebkTask.getEbkCertificate().getEbkCertificateType())
					&&Constant.EBK_TASK_STATUS.ACCEPT.name().equals(certificateStatus)){
				for(EbkCertificateItem ebkCertificateItem:ebkTask.getEbkCertificate().getEbkCertificateItemList()){
					boolean bo=this.orderServiceProxy.resourceAmple(ebkCertificateItem.getOrderItemMetaId(),this.getSessionUserName(),memo,wait);
					if(bo==false){
						result.setMsg("资源确认错误");
						this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
						return;
					}
				}
			}
			if(Constant.EBK_TASK_STATUS.REJECT.name().equals(certificateStatus)) {
				this.logRecord(ebkTask, "不接受"+ebkTask.getEbkCertificate().getZhEbkCertificateType(), "不接受"+ebkTask.getEbkCertificate().getZhEbkCertificateType()+"，原因是："+memo);
			} else {
				this.logRecord(ebkTask, "接受"+ebkTask.getEbkCertificate().getZhEbkCertificateType(), "已接受"+ebkTask.getEbkCertificate().getZhEbkCertificateType());
			}
			//新增工单
			createWorkOrder(ebkTask,certificateStatus,memo);
			this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
		} catch (Exception e) {
			e.printStackTrace();
			this.sendAjaxResultByJson(JSONObject.fromObject(new ResultHandle(e.getMessage())).toString());
		}
	}
	/**
	 * 新建工单
	 * @param ebkTask
	 * @param memo
	 * @return
	 */
	private boolean createWorkOrder(EbkTask ebkTask,String status,String memo){
		if(!Constant.EBK_TASK_STATUS.REJECT.name().equals(status)){
			return false;
		}
		String errMsg="";
		OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(ebkTask.getOrderId());
		try{
			SupSupplier supSupplier=supplierService.getSupplier(this.getSessionUser().getSupplierId());
			String taskContent="供应商名称："+this.getSessionUser().getSupplierName()+"，<br/>供应商电话："+supSupplier.getTelephone()+"，<br/>" +
					"类型："+ ebkTask.getEbkCertificate().getZhEbkCertificateType() +"，<br/>确认状态："
					+Constant.EBK_CERTIFICATE_TYPE_AND_STATUS.getCnName(ebkTask.getEbkCertificate().getEbkCertificateType()+"_"+Constant.EBK_TASK_STATUS.REJECT.name())
					+"，<br/>供应商理由："+memo;
			ProdProduct prodProduct=prodProductService.getProdProduct(order.getMainProduct().getProductId());
			PermUser permUser=permUserService.getPermUserByUserId(prodProduct.getManagerId());
			WorkOrderCreateParam param = EbkRejectWorkOrder.initParam(order, ebkTask, prodProduct, permUser, supSupplier, taskContent);
			//add by zhushuying 通过匹配采购产品中所在组织获取接收组织id
			if (null != prodProduct) {
				Long metaProductId = order.getAllOrdOrderItemMetas().get(0)
						.getMetaProductId();
				MetaProduct metaProdcut = metaProductService
						.getMetaProduct(metaProductId);
				if (null != metaProdcut
						&& StringUtils.isNotBlank(metaProdcut
								.getWorkGroupId())) {
					Long receiveGroupId = Long.valueOf(metaProdcut
							.getWorkGroupId());
					param.setReceiveGroupId(receiveGroupId);  //获取接收组织Id
				}
			}
			//end by zhushuying
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
	
	@Action("/ebooking/task/changeGroupWordStatus")
	public void changeGroupWordStatus() {
		JSONObject json = new JSONObject();
		try {
			groupAdviceNoteService.updateOrderGroupWordStatus(orderId,
					groupWordStatus);
			comLogRemoteService.insert(
					Constant.COM_LOG_OBJECT_TYPE.EBK_ORDER_TASK.name(),
					orderId, null, this.getSessionUserName(), "", "修改发送状态",
					"修改发送状态:更新为已发送",
					Constant.COM_LOG_OBJECT_TYPE.ORD_ORDER.name());
			json.put("flag", "success");
		} catch (Exception e) {
			json.put("flag", "fail");
			json.put("msg", e.getMessage());
		}
		sendAjaxMsg(json.toString());
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
	public EbkTaskService getEbkTaskService() {
		return ebkTaskService;
	}
	public void setEbkTaskService(EbkTaskService ebkTaskService) {
		this.ebkTaskService = ebkTaskService;
	}
	public EbkCertificateService getEbkCertificateService() {
		return ebkCertificateService;
	}
	public void setEbkCertificateService(EbkCertificateService ebkCertificateService) {
		this.ebkCertificateService = ebkCertificateService;
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
	public void setMetaProductBranchService(MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
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
	public String getMetaProductId() {
		return metaProductId;
	}
	public void setMetaProductId(String metaProductId) {
		this.metaProductId = metaProductId;
	}
	public String getMetaProductName() {
		return metaProductName;
	}
	public void setMetaProductName(String metaProductName) {
		try {
			this.metaProductName = new String(java.net.URLDecoder.decode(metaProductName, "utf-8").getBytes("iso-8859-1"),"utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	public String getTravellerName() {
		return travellerName;
	}
	public void setTravellerName(String travellerName) {
		this.travellerName = travellerName;
	}
	public String getCertType() {
		return certType;
	}
	public void setCertType(String certType) {
		this.certType = certType;
	}
	public String getMetaBranchId() {
		return metaBranchId;
	}
	public void setMetaBranchId(String metaBranchId) {
		this.metaBranchId = metaBranchId;
	}
	public String getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}
	public String getConfirmStatus() {
		return confirmStatus;
	}
	public void setConfirmStatus(String confirmStatus) {
		this.confirmStatus = confirmStatus;
	}
	public String getOrderBy() {
		return orderBy;
	}
	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}
	public Page<EbkTask> getEbkTaskPage() {
		return ebkTaskPage;
	}
	public void setEbkTaskPage(Page<EbkTask> ebkTaskPage) {
		this.ebkTaskPage = ebkTaskPage;
	}
	
	public EbkCertificate getEbkCertificate() {
		return ebkCertificate;
	}

	public void setEbkCertificate(EbkCertificate ebkCertificate) {
		this.ebkCertificate = ebkCertificate;
	}

	public EbkTask getEbkTask() {
		return ebkTask;
	}

	public void setEbkTask(EbkTask ebkTask) {
		this.ebkTask = ebkTask;
	}
	public Long getEbkTaskId() {
		return ebkTaskId;
	}

	public void setEbkTaskId(Long ebkTaskId) {
		this.ebkTaskId = ebkTaskId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCertificateStatus() {
		return certificateStatus;
	}

	public void setCertificateStatus(String certificateStatus) {
		this.certificateStatus = certificateStatus;
	}

	public String getWaitTime() {
		return waitTime;
	}

	public void setWaitTime(String waitTime) {
		this.waitTime = waitTime;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}
	
	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
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

	public String getTxtWaitTime() {
		return txtWaitTime;
	}

	public void setTxtWaitTime(String txtWaitTime) {
		this.txtWaitTime = txtWaitTime;
	}

	public Map<String, Object> getEbkCertificateData() {
		return ebkCertificateData;
	}

	public void setEbkCertificateData(Map<String, Object> ebkCertificateData) {
		this.ebkCertificateData = ebkCertificateData;
	}


	public List<EbkTask> getEbkTaskList() {
		return ebkTaskList;
	}

	public void setEbkTaskList(List<EbkTask> ebkTaskList) {
		this.ebkTaskList = ebkTaskList;
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

	public MetaProductService getMetaProductService() {
		return metaProductService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public List<String> getValidContentList() {
		return validContentList;
	}

	public String getGroupWordStatus() {
		return groupWordStatus;
	}

	public void setGroupWordStatus(String groupWordStatus) {
		this.groupWordStatus = groupWordStatus;
	}

	public void setGroupAdviceNoteService(
			GroupAdviceNoteService groupAdviceNoteService) {
		this.groupAdviceNoteService = groupAdviceNoteService;
	}

	public String getOperatorName() {
		return operatorName;
	}

	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	private void writeCsvFile(List<EbkTask> list){
		try{
			String header = "订单号,订单状态,类型,确认状态,支付状态,产品ID,产品名称,游客姓名,出发时间,成人数,儿童数,订购份数,结算单价,结算总价,供应商确认人,确认时间\r\n";
			getResponse().setHeader("Content-Disposition", "attachment; filename=downloadfile.csv");
			getResponse().setContentType("application/vnd.ms-excel");
			OutputStreamWriter ow = new OutputStreamWriter(getResponse().getOutputStream(),"UTF-16LE");
			header= header.replaceAll(",","\t");
			ow.write(0xFEFF);
			ow.write(header);
			for (int i = 0; i < list.size(); i++) {
				EbkTask ebkTask = list.get(i);
				String content = ebkTask.toString() + "\r\n";
				ow.write(content);
				ow.flush();
			}
			ow.close();
			getResponse().getWriter().flush();
			getResponse().getWriter().close();
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
