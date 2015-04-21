package com.lvmama.back.sweb.distribution;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.axis.utils.StringUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderBatch;
import com.lvmama.comm.bee.po.pass.PassCode;
import com.lvmama.comm.bee.po.pass.PassPortCode;
import com.lvmama.comm.bee.service.ord.OrderBatchService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.pass.PassCodeService;
import com.lvmama.comm.jms.TopicMessageProducer;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.ComLog;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ExcelImport;
import com.lvmama.comm.utils.ResourceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

@Results({ @Result(name = "getAbandonOrderSearchPage", location = "/WEB-INF/pages/back/distribution/batchAbandonOrder/searchBatchAbandonOrder.jsp"),
		@Result(name = "batchList", location = "/WEB-INF/pages/back/distribution/batchAbandonOrder/batchList.jsp"),
		@Result(name = "upLoad", location = "/WEB-INF/pages/back/distribution/batchAbandonOrder/importExcel.jsp"),
		@Result(name = "batchCancelIndex", location = "/WEB-INF/pages/back/distribution/batchAbandonOrder/batchCancelIndex.jsp"),
		@Result(name = "batchCancelOrderPage" , location = "/WEB-INF/pages/back/distribution/batchAbandonOrder/batchCancelOrder.jsp")})
public class BatchOrderManagementAction extends BaseAction {
	private static final long serialVersionUID = 4158919605327893335L;
	private OrderBatchService orderBatchService;
	private OrderService orderServiceProxy;
	private PassCodeService passCodeService;
	private transient TopicMessageProducer orderMessageProducer;
	private ComLogService comLogService;
	private String creator;
	private String productName;
	private String productId;
	private String branchId;
	private Long batchId;
	private String addCode;
	private String contactMan;
	private String contactPhone;
	private String createTimeStart;
	private String createTimeEnd;
	private String usedTimeStart;
	private String usedTimeEnd;
	private String orderStatus;
	private String performStatus;
	private Long orderId;
	private String orderIds;
	private int orderCount;
	private String cancelReason;
	private File file;
	private Page<OrdOrderBatch> batchList = new Page<OrdOrderBatch>();
	
	private Map<String,Object> result= new HashMap<String,Object>();

	private Long page = 1L;
	
	public final String BATCH_ORDER_CODE_FILE="/WEB-INF/resources/template/batchOrderExcel.xml";

	@Action("/distribution/getAbandonOrderSearchPage")
	public String getAbandonOrderSearchPage() {
		Map params = checkSearchCondition();
		params.put("currentPage", this.page);
		params.put("pageSize", Long.valueOf(this.batchList.getPageSize()));
		this.batchList = this.orderBatchService.listAbandonOrder(params);
		batchList.buildUrl(getRequest());
		batchList.setCurrentPage(this.page);
		return "getAbandonOrderSearchPage";
	}
	
	@Action("/distribution/batch/batchCancelOrderPage")
	public String batchCancelOrderPage(){
		String[] orderArray = orderIds.split(",");
		orderCount = orderArray.length;
		return "batchCancelOrderPage";
	}
	
	@Action("/distribution/batch/batchCancelOrder")
	public void batchCancelOrder(){
		
		try{
			String[] orderArray = orderIds.split(",");
			int count = orderArray.length;
			int successCount = 0;
			int failCount = 0;
			StringBuffer jsonResult = new StringBuffer();
			for(String orderId:orderArray){
				boolean flag = this.doOrderCancel(Long.parseLong(orderId));
				if(flag){
					successCount = successCount+1;
					insertComLog("成功废除订单"+orderId,"分销批量废单操作",Constant.COM_LOG_ORDER_EVENT.batchCancelOrder.name(),
							Long.parseLong(orderId),Constant.COM_LOG_OBJECT_TYPE.ORDER_BATCH.getCode());
				}else{
					failCount = failCount + 1;
					insertComLog("失败废除订单"+orderId,"分销批量废单操作",Constant.COM_LOG_ORDER_EVENT.batchCancelOrder.name(),
							Long.parseLong(orderId),Constant.COM_LOG_OBJECT_TYPE.ORDER_BATCH.getCode());
				}
			}
			insertComLog("分销批量废单操作","批量废单",Constant.COM_LOG_ORDER_EVENT.batchCancelOrder.name(),batchId,Constant.COM_LOG_OBJECT_TYPE.ORDER_BATCH.getCode());
			jsonResult.append("需要废除"+count+"笔订单！\\r\\n");
			jsonResult.append("已成功废除"+successCount+"笔订单，"+failCount+"笔订单废除失败！");
			
			sendAjaxResult("{'result':true,'msg':'"+jsonResult.toString()+"'}");
		}catch(Exception e){
			e.printStackTrace();
			sendAjaxResult("{'result':false}");
		}
	}
	@Action("/distribution/batch/updateBatchValid")
	public void updateBatchValid(){
		try{
			
			int needNum=0;
			List<OrdOrderBatch> lists = orderBatchService.selectNeedCreateOrder();
			if(lists!=null){
				for(OrdOrderBatch batch:lists){
					needNum = needNum + batch.getBatchCount();
				}
			}
			if("true".equalsIgnoreCase(cancelReason)){
				int MaxNum = Constant.getInstance().getDistMaxNum();
				OrdOrderBatch  ord = orderBatchService.selectByBatchId(batchId);
				if(ord!=null){
					if((needNum+ord.getBatchCount())>MaxNum){
						sendAjaxResult("开启批次任务失败 ，已经有" + needNum +"单的任务在等待,总量不能超过:"+MaxNum);
						return ;
					}
				}
			}
			Map<Object,Object> map = new HashMap<Object,Object>();
			map.put("batchId",batchId);
			map.put("isValid",cancelReason);
			orderBatchService.updateBatchValid(map);
			String result="开启批次任务成功";
			if("false".equalsIgnoreCase(cancelReason)){
				result = "关闭批次任务成功";
			}
			sendAjaxResult(result);
			insertComLog(result,"开/关任务",Constant.COM_LOG_ORDER_EVENT.batchCancelOrder.name(),batchId,Constant.COM_LOG_OBJECT_TYPE.ORDER_BATCH.getCode());
		}catch(Exception e){
			e.printStackTrace();
			sendAjaxResult("更新异常");
		}
	}

	@Action("/distribution/batch/batchOrderResult")
	public void getBatchOrderResult() {
		Map params = checkSearchCondition();
		params.put("currentPage", new Integer(1));
		params.put("pageSize", new Long(1000));
		Page<OrdOrderBatch> batchPage = this.orderBatchService.listAbandonOrder(params);
		List<OrdOrderBatch> batchOrderList = batchPage.getItems();
		if(batchOrderList.size()>1000){
			sendAjaxResult("导出excel的记录条数大于1000");
			return;
		}
		String template = "/WEB-INF/resources/template/batchOrderTemplate.xls";
		output(batchOrderList, template,"batchOrderList");
	}

	@Action("/distribution/batch/batchList")
	public String batchList() {
		Map<Object, Object> params = new HashMap<Object, Object>();
		params.put("productBranchId", this.branchId);
		params.put("contacts", this.contactMan);
		params.put("contactsPhone", this.contactPhone);
		params.put("creator", this.creator);
		params.put("createtimeStart", StringUtil.isNotEmptyString(this.createTimeStart) ? DateUtil.stringToDate(this.createTimeStart, "yyyy-MM-dd") : null);
		params.put("createtimeEnd", StringUtil.isNotEmptyString(this.createTimeEnd) ? DateUtils.addDays(DateUtil.stringToDate(this.createTimeEnd, "yyyy-MM-dd"), 1) : null);

		params.put("currentPage", this.page);
		params.put("pageSize", Long.valueOf(this.batchList.getPageSize()));
		this.batchList = this.orderBatchService.selectByParams(params);
		batchList.buildUrl(getRequest());
		batchList.setCurrentPage(page);
		return "batchList";
	}

	@Action("/distribution/batch/getBatchResult")
	public void getBatchResult() {
		if(this.batchId == null){
			sendAjaxResult("无效访问");
			return;
		}
		
		Map<Object, Object> params = new HashMap<Object, Object>();
		params.put("currentPage", this.page);
		params.put("pageSize", Long.valueOf(this.batchList.getPageSize()));
		params.put("batchId", this.batchId);
		Page<OrdOrderBatch> batchPage = this.orderBatchService.selectByParams(params);
		List<OrdOrderBatch> batchList = batchPage.getItems();
		
		OrdOrderBatch batch = null;
		if(batchList!=null && batchList.size()>0){
			batch = batchList.get(0);
		}
		
		if(Constant.ORDER_BATCH_STATUS.BATCHWAITTING.name().equals(batch.getStatus())){
			sendAjaxResult("批量订单任务等待中，请稍候 ... ");
			return;
		}
		
		Map<String, Integer> result = this.orderBatchService.getBatchResult(batchId);
		StringBuffer json = new StringBuffer();
		
		if(Constant.ORDER_BATCH_STATUS.BATCHINIG.name().equals(batch.getStatus())){
			json.append("批量订单生成中，您已成功的生成了"+result.get("ordCount")+"笔订单！<br>");
		}else{
			json.append("批量任务完成，您已成功的生成了"+result.get("ordCount")+"笔订单！<br>");
		}
		json.append("其中申码成功："+result.get("passCodeCount"));
		sendAjaxResult(json.toString());
	}
	
	@Action("/distribution/batch/batchCancelAllOrder")
	public void batchCancelAllOrder(){
		this.cancelReason = "团购分销-批次废除所有符合条件的订单";
		Map params = checkSearchCondition();
		//params.put("performStatus", Constant.PASSCODE_USE_STATUS.UNUSED);
		params.put("dimPerformStatus", Constant.PASSCODE_USE_STATUS.UNUSED);
		params.put("currentPage", new Integer(1));
		params.put("pageSize", new Long(1000));
		Page<OrdOrderBatch> batchPage = this.orderBatchService.listAbandonOrder(params);
		List<OrdOrderBatch> batchOrderList = batchPage.getItems();
		if(batchOrderList.size()>1000){
			sendAjaxResult("{'result':false,'msg':'结果集大于1000条，请缩小查询范围！'}");
			return;
		}
		StringBuffer jsonResult = new StringBuffer();
		int count = batchOrderList.size();
		int successCount = 0;
		int failCount = 0;
		for(OrdOrderBatch o : batchOrderList){
			boolean flag = this.doOrderCancel(o.getOrderId());
			if(flag){
				successCount = successCount+1;
				insertComLog("成功废除订单"+o.getOrderId(),"分销批量废单操作",Constant.COM_LOG_ORDER_EVENT.batchCancelOrder.name(),
						o.getOrderId(),Constant.COM_LOG_OBJECT_TYPE.ORDER_BATCH.getCode());
			}else{
				failCount = failCount + 1;
				insertComLog("失败废除订单"+o.getOrderId(),"分销批量废单操作",Constant.COM_LOG_ORDER_EVENT.batchCancelOrder.name(),
						o.getOrderId(),Constant.COM_LOG_OBJECT_TYPE.ORDER_BATCH.getCode());
			}
		}
		
		jsonResult.append("废除"+count+"笔订单！\\r\\n");
		jsonResult.append("已成功废除"+successCount+"笔订单，"+failCount+"笔订单废除失败！");
		sendAjaxResult("{'result':true,'msg':'"+jsonResult.toString()+"'}");
	}
	
	@Action("/distribution/batch/batchCancelOneOrder")
	public void batchCancelOneOrder(){
		try {
			this.cancelReason = URLDecoder.decode(cancelReason, "UTF-8");
			boolean flag =this.doOrderCancel(orderId);
			insertComLog("废单"+orderId+",flag:"+flag,"分销订单废单操作",Constant.COM_LOG_ORDER_EVENT.batchCancelOneOrder.name(),
				orderId,Constant.COM_LOG_OBJECT_TYPE.ORDER_BATCH.getCode());
	
			if(flag){
				sendAjaxResult("{result:true}");
			}else{
				sendAjaxResult("{result:false}");
			}
		}catch(Exception e) {
			e.printStackTrace();
			sendAjaxResult("{result:false}");
		}
	}
	
	
	private void insertComLog(String content,String logName,String logType,Long objectId,String objectType){
		PermUser user = getSessionUser();
		ComLog comlog = new ComLog();
		comlog.setContent(content);
		comlog.setContentType(Constant.COM_LOG_CONTENT_TYPE.VARCHAR.name());
		comlog.setCreateTime(new Date());
		comlog.setLogName(logName);
		comlog.setLogType(logType);
		comlog.setObjectId(objectId);
		comlog.setObjectType(objectType);
		comlog.setOperatorName(user.getUserName());
		comLogService.addComLog(comlog);
	}

	
	@Action("/distribution/batch/batchCancelIndex")
	public String batchCancelIndex(){
		OrdOrderBatch oob = orderBatchService.selectByBatchId(batchId);
		result.put("beginDate", DateUtil.formatDate(oob.getValidBeginDate(),"yyyy-MM-dd"));
		result.put("endDate", DateUtil.formatDate(oob.getValidEndDate(),"yyyy-MM-dd"));
		result.putAll(orderBatchService.getBatchCount(batchId));
		return "batchCancelIndex";
	}
	
	@Action("/distribution/batch/batchCancelOrders")
	public void batchCancelOrders(){
		List<OrdOrderBatch> oobs = orderBatchService.queryBatchOrdersForCancel(batchId);
		orderIds = "";
		if(oobs.size()>0){
			for(OrdOrderBatch ob:oobs){
				orderIds = orderIds + ob.getOrderId()+",";
			}
			orderIds = orderIds.substring(0, orderIds.length()-1);
		}
		if(!StringUtils.isEmpty(orderIds)){
			batchCancelOrder();
		}else{
			sendAjaxResult("{'result':true,'msg':'没有订单被废'}");
		}
	}
	
	@Action("/distribution/batch/downloadPasscode")
	public void downloadPasscode(){
		if(batchId == null)
			return ;
		
		List<OrdOrderBatch> passcodeList = orderBatchService.listBatchPassCode(batchId);
		String template = "/WEB-INF/resources/template/batchPasscodeTemplate.xls";
		output(passcodeList, template,"passcodeList");
		
		PermUser user = getSessionUser();
		
		ComLog comlog = new ComLog();
		comlog.setContent("批量辅助码下载");
		comlog.setContentType("VARCHAR");
		comlog.setCreateTime(new Date());
		comlog.setLogName("批量辅助码下载");
		comlog.setLogType(Constant.COM_LOG_ORDER_EVENT.batchOrderDownPASSCODE.name());
		comlog.setObjectId(batchId);
		comlog.setObjectType(Constant.COM_LOG_OBJECT_TYPE.ORDER_BATCH.getCode());
		comlog.setOperatorName(user.getUserName());
		comLogService.addComLog(comlog);
	}
	@Action("/distribution/batch/upload")
	public String openUpLoad(){
		return "upLoad";
	}
	@Action("/distribution/batch/upCodeAndCancelOrder")
	public void upLoadPasscodeAndCancelOrder(){
		if(this.file==null){
			this.sendAjaxMsg("上传文件为Null");
    		return;
		}
		List<String> list;
		try {
			list = ExcelImport.excImport(new FileInputStream(file));
			if(list!=null && list.size()>0){
				orderIds = "";
				for(String oob:list){
					//Map params = new HashMap();
					//params.put("addCode", oob);
					//PassCode pc = passCodeService.getPassCodeByParams(params);
					//if(pc!=null && orderIds.indexOf(pc.getOrderId()+"")==-1){
					if(orderIds.indexOf(oob)==-1){
						//orderIds = orderIds + pc.getOrderId()+",";
						orderIds = orderIds + oob+",";
					}
				}
				if(!StringUtils.isEmpty(orderIds)){
					orderIds = orderIds.substring(0, orderIds.length()-1);
					this.cancelReason = "批次废除所有符合条件的订单";
					batchCancelOrder();
				}
			}else{
				sendAjaxResult("{'result':true,'msg':'没有订单被废'}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			sendAjaxResult("{'result':true,'msg':'操作失败,废单异常'}");
		}
	}
	private Map checkSearchCondition(){
		Map params = new HashMap();
		if (!StringUtil.isEmptyString(this.productName)) {
			params.put("productName", this.productName.trim());
		}

		if (!StringUtil.isEmptyString(this.productId)) {
			params.put("productId", this.productId.trim());
		}

		if (!StringUtil.isEmptyString(this.branchId)) {
			params.put("branchId", this.branchId.trim());
		}

		if (this.batchId != null) {
			params.put("batchId", this.batchId);
		}

		if (!StringUtil.isEmptyString(this.addCode)) {
			params.put("addCode", this.addCode.trim());
		}

		if (!StringUtil.isEmptyString(this.contactMan)) {
			params.put("contacts", this.contactMan.trim());
		}

		if (!StringUtil.isEmptyString(this.contactPhone)) {
			params.put("contactsPhone", this.contactPhone.trim());
		}

		if (!StringUtil.isEmptyString(this.createTimeStart)) {
			params.put("createtimeStart", StringUtil.isNotEmptyString(this.createTimeStart) ? DateUtil.stringToDate(this.createTimeStart, "yyyy-MM-dd") : null);
		}

		if (!StringUtil.isEmptyString(this.createTimeEnd)) {
			params.put("createtimeEnd", StringUtil.isNotEmptyString(this.createTimeEnd) ? DateUtil.getDateAfterDays(DateUtil.stringToDate(this.createTimeEnd, "yyyy-MM-dd"),1) : null);
		}
		
		if (!StringUtil.isEmptyString(this.usedTimeStart)) {
			params.put("usedtimeStart", StringUtil.isNotEmptyString(this.usedTimeStart) ? DateUtil.stringToDate(this.usedTimeStart, "yyyy-MM-dd") : null);
		}

		if (!StringUtil.isEmptyString(this.usedTimeEnd)) {
			params.put("usedtimeEnd", StringUtil.isNotEmptyString(this.usedTimeEnd) ? DateUtil.getDateAfterDays(DateUtil.stringToDate(this.usedTimeEnd, "yyyy-MM-dd"),1) : null);
		}
		
		if(!StringUtil.isEmptyString(this.performStatus)){
			params.put("performStatus", this.performStatus);
		}
		
		if(!StringUtil.isEmptyString(this.orderStatus)){
			params.put("orderStatus", this.orderStatus);
		}
		return params;
	}
	
	private void output(List list, String template,String key) {
		FileInputStream fin = null;
		OutputStream os = null;
		try {
			File templateResource = ResourceUtil.getResourceFile(template);
			Map beans = new HashMap();
			beans.put(key, list);
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			beans.put("dateFormat", dateFormat);
			XLSTransformer transformer = new XLSTransformer();
			File destFileName = new File(Constant.getTempDir() + "/excel" + new Date().getTime() + ".xls");
			transformer.transformXLS(templateResource.getAbsolutePath(), beans, destFileName.getAbsolutePath());
			getResponse().setContentType("application/vnd.ms-excel");
			getResponse().setHeader("Content-Disposition", "attachment; filename=" + destFileName.getName());
			os = getResponse().getOutputStream();
			fin = new FileInputStream(destFileName);
			IOUtils.copy(fin, os);
			os.flush();
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			IOUtils.closeQuietly(fin);
			IOUtils.closeQuietly(os);
		}
	}
	

	
	private boolean doOrderCancel(Long orderId){
		try {
			OrdOrder order=orderServiceProxy.queryOrdOrderByOrderId(orderId);
			//该位置做废单判断，如果已经过了最晚时间不做处理，只许超级废单直接处理
			if(order==null||!order.isCancelAble() || order.isCanceled()){
				return false;
			}
			
			List<PassPortCode> passPortCodeList = passCodeService.searchPassPortByOrderId(orderId);
			PassPortCode passPortCode = null;
			if(!passPortCodeList.isEmpty() && passPortCodeList.size() >0){
				passPortCode = passPortCodeList.get(0);
			}
			//该位置做废单判断，如果已经过了最晚时间不做处理，只许超级废单直接处理
			if( passPortCode != null && passPortCode.getStatus().equals(Constant.PASSCODE_USE_STATUS.USED)){
				return false;
			}
			boolean flag = orderServiceProxy.cancelOrder(orderId, cancelReason.trim(), getOperatorName());
			LOG.info("flag:"+flag+",batchCancelOrder:"+orderId);
			return flag;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void setOrderBatchService(OrderBatchService orderBatchService) {
		this.orderBatchService = orderBatchService;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public void setBranchId(String branchId) {
		this.branchId = branchId;
	}

	public void setAddCode(String addCode) {
		this.addCode = addCode;
	}
	public String getAddCode() {
		return addCode;
	}

	public void setContactMan(String contactMan) {
		this.contactMan = contactMan;
	}

	public void setContactPhone(String contactPhone) {
		this.contactPhone = contactPhone;
	}

	public void setCreateTimeStart(String createTimeStart) {
		this.createTimeStart = createTimeStart;
	}

	public void setCreateTimeEnd(String createTimeEnd) {
		this.createTimeEnd = createTimeEnd;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public Page<OrdOrderBatch> getBatchList() {
		return this.batchList;
	}

	public Long getPage() {
		return page;
	}

	public void setPage(Long page) {
		this.page = page;
	}

	public String getCreator() {
		return creator;
	}

	public String getProductName() {
		return productName;
	}

	public String getProductId() {
		return productId;
	}

	public String getBranchId() {
		return branchId;
	}

	public String getContactMan() {
		return contactMan;
	}

	public String getContactPhone() {
		return contactPhone;
	}

	public String getCreateTimeStart() {
		return createTimeStart;
	}

	public String getCreateTimeEnd() {
		return createTimeEnd;
	}

	public Long getBatchId() {
		return batchId;
	}

	public void setBatchId(Long batchId) {
		this.batchId = batchId;
	}
	
	public String getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(String orderStatus) {
		this.orderStatus = orderStatus;
	}

	public String getPerformStatus() {
		return performStatus;
	}

	public void setPerformStatus(String performStatus) {
		this.performStatus = performStatus;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	
	public void setOrderMessageProducer(
			final TopicMessageProducer orderMessageProducer) {
		this.orderMessageProducer = orderMessageProducer;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getOrderIds() {
		return orderIds;
	}

	public void setOrderIds(String orderIds) {
		this.orderIds = orderIds;
	}

	public String getCancelReason() {
		return cancelReason;
	}

	public void setCancelReason(String cancelReason) {
		this.cancelReason = cancelReason;
	}

	public void setPassCodeService(PassCodeService passCodeService) {
		this.passCodeService = passCodeService;
	}

	public int getOrderCount() {
		return orderCount;
	}

	public void setOrderCount(int orderCount) {
		this.orderCount = orderCount;
	}

	public Map<String, Object> getResult() {
		return result;
	}

	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

	public void setBatchList(Page<OrdOrderBatch> batchList) {
		this.batchList = batchList;
	}

	public File getFile() {
		return file;
	}

	public void setFile(File file) {
		this.file = file;
	}
	public String getUsedTimeStart() {
		return usedTimeStart;
	}

	public void setUsedTimeStart(String usedTimeStart) {
		this.usedTimeStart = usedTimeStart;
	}

	public String getUsedTimeEnd() {
		return usedTimeEnd;
	}

	public void setUsedTimeEnd(String usedTimeEnd) {
		this.usedTimeEnd = usedTimeEnd;
	}
	
}