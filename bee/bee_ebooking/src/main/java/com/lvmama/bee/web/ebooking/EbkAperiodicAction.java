package com.lvmama.bee.web.ebooking;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.bee.web.EbkBaseAction;
import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ebooking.EbkTask;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaAperiodic;
import com.lvmama.comm.bee.service.ebooking.CertificateService;
import com.lvmama.comm.bee.service.ebooking.EbkTaskService;
import com.lvmama.comm.bee.service.ord.OrderItemMetaAperiodicService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.vo.Constant;

/**
 * EBK不定期操作类
 * @author shihui 2013-07-23
 * @version
 */
@Results(value={
		@Result(name="activateOrderShow",location="/WEB-INF/pages/ebooking/task/activateOrderShow.jsp"),
		@Result(name = "activateRouteOrderShow", location = "/WEB-INF/pages/ebooking/task/activateRouteOrderShow.jsp")
	})
public class EbkAperiodicAction extends EbkBaseAction {
	
	private static final long serialVersionUID = 5470355622645025227L;

	private EbkTaskService ebkTaskService;
	private OrderService orderServiceProxy;	
	private ComLogService comLogRemoteService; 	
	private Long ebkTaskId;
	private Long orderId;
	private String mobile;
	private String memo;
	private String supplierOrderNo;
	private String visitTimeStart;
	//密码券号
	private String passwordCertificate;
	//游客姓名
	private String visitPerName;
	//订单处理
	private Long version;
	private EbkTask ebkTask;
	private CertificateService certificateServiceProxy;
	private OrderItemMetaAperiodicService orderItemMetaAperiodicService;
	private List<String> validContentList;
	private String type;

	@Action("/ebooking/task/activateOrderShow")
	public String activateOrderShow() {
		ebkTask = this.ebkTaskService.findEbkTaskAndCertificateAndGetContentByPkId(ebkTaskId);
		validContentList = getValidContentList(ebkTask.getOrderId(), ebkTask.getEbkCertificate().getSupplierId());
		if(type.equalsIgnoreCase("HOTEL")) {
			return "activateOrderShow";
		} else {
			return "activateRouteOrderShow";
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
	
	/**
	 * 密码券激活码校验
	 * 
	 * @author: zhangjie 2013-5-3
	 * @return
	 */
	@Action("/ebooking/task/checkPasswordCertificate")
	public void checkPasswordCertificate() {
		Map<String, Object> resultMap = orderItemMetaAperiodicService.checkPasswordCertificate(orderId,this.getCurrentSupplierId(),passwordCertificate, visitTimeStart);
		resultMap.put("success", resultMap.get("message") != null ? false : true);
		this.sendAjaxResultByJson(JSONObject.fromObject(resultMap).toString());
	}
	
	/**
	 * 密码券激活及修改
	 * 
	 * @author: zhangjie 2013-5-3
	 * @return
	 */
	@Action("/ebooking/task/taskImportPassword")
	public void taskImportPassword() {
		ebkTask = this.ebkTaskService.findEbkTaskAndCertificateAndGetContentByPkId(ebkTaskId);
		ResultHandle result=this.updateAperiodicOrderUseStatus(ebkTask,visitPerName,mobile,visitTimeStart,supplierOrderNo,Constant.APERIODIC_ACTIVATION_STATUS.ACTIVATED.name(), version);
		if(result.isSuccess()){
			this.logRecord(ebkTask, "密码券使用", "密码券使用成功，修改游玩时间为"+visitTimeStart);
		}else{
			this.logRecord(ebkTask, "密码券使用", "激活失败，原因是："+result.getMsg());
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	}
	
	/**
	 * 密码券激活取消
	 * 
	 * @author: zhangjie 2013-5-3
	 * @return
	 */
	@Action("/ebooking/task/cancelTaskPassword")
	public void cancelTaskPassword() {
		ebkTask = this.ebkTaskService.findEbkTaskAndCertificateAndGetContentByPkId(ebkTaskId);
		ResultHandle result=this.cancelTaskPasswordUseStatus(ebkTask,version);
		if(result.isSuccess()){
			this.logRecord(ebkTask, "密码券使用撤销", "密码券使用撤销成功");
		}else{
			this.logRecord(ebkTask, "密码券使用撤销", "撤销失败，原因是："+result.getMsg());
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(result).toString());
	}
	
	private ResultHandle cancelTaskPasswordUseStatus(EbkTask ebkTask, Long version) {
		ResultHandle result = new ResultHandle();
		if(!ebkTask.getEbkCertificate().getVersion().equals(version)) {
			result.setMsg("数据有更新，请刷新再操作");
			return result;
		}
		List<EbkCertificateItem> items = ebkTask.getEbkCertificate().getEbkCertificateItemList();
		List<Long> orderItemMetaIds = new ArrayList<Long>();
		for (EbkCertificateItem item : items) {
			orderItemMetaIds.add(item.getOrderItemMetaId());
		}
		//修改密码券使用状态
		result = orderItemMetaAperiodicService.updateAperiodicOrderUseStatus(ebkTask.getOrderId(),"","","",Constant.APERIODIC_ACTIVATION_STATUS.UNACTIVATED.name(),this.getSessionUser().getUserName(),orderItemMetaIds);
		if(result.isFail()){
			return result;
		}		
		//修改凭证相关信息
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(ebkTask.getOrderId());
		String orderItemMetaIdList = getOrderItemMetaIdList(orderItemMetaIds);
		certificateServiceProxy.createSupplierCertificate(ordOrder,CertificateService.UPDATE_APERIODIC_ORDER,"", orderItemMetaIdList);
		return result;
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
	 * 不定期订单修改
	 * 
	 * @author: zhangjie 2013-5-2
	 * @param ebkTask EBK       
	 * @param visitPerName       客人姓名
	 * @param mobile              客人电话号码
	 * @param visitTimeStart      入住时间
	 * @param supplierOrderNo     酒店确认号
	 * @param passwordUseStatus   激活状态
	 * @param version         版本号
	 * @return
	 */
	private ResultHandle updateAperiodicOrderUseStatus(EbkTask task,
			String visitPerName, String mobile, String visitTimeStart, String supplierOrderNo,
			String passwordUseStatus, Long version) {
		ResultHandle result = new ResultHandle();
		if(!task.getEbkCertificate().getVersion().equals(version)) {
			result.setMsg("数据有更新，请刷新再操作");
			return result;
		}
		List<EbkCertificateItem> items = ebkTask.getEbkCertificate().getEbkCertificateItemList();
		List<Long> orderItemMetaIds = new ArrayList<Long>();
		for (EbkCertificateItem item : items) {
			orderItemMetaIds.add(item.getOrderItemMetaId());
		}
		
		//修改密码券使用状态
		result = orderItemMetaAperiodicService.updateAperiodicOrderUseStatus(task.getOrderId(),visitPerName,mobile,visitTimeStart,passwordUseStatus,this.getSessionUser().getUserName(),orderItemMetaIds);
		if(result.isFail()){
			return result;
		}
		//修改凭证相关信息
		OrdOrder ordOrder = orderServiceProxy.queryOrdOrderByOrderId(task.getOrderId());
		String orderItemMetaIdList = getOrderItemMetaIdList(orderItemMetaIds);
		certificateServiceProxy.createSupplierCertificate(ordOrder,CertificateService.UPDATE_APERIODIC_ORDER,"", orderItemMetaIdList);
		
		this.ebkTaskService.updateTaskConfirmStatus(task.getEbkTaskId(),DateUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"),this.getSessionUserName(),Constant.EBK_TASK_STATUS.ACCEPT.name(),memo.trim());
		result=ebkTaskService.updateEbkCertificateItemSupplierNo(supplierOrderNo,task.getEbkTaskId());
		if(result.isFail()){
			return result;
		}
		
		return result;
	}
	
	public String getOrderItemMetaIdList(List<Long> orderItemMetaIds) { 
		if(orderItemMetaIds != null && orderItemMetaIds.size()>0) { 
			StringBuffer sb = new StringBuffer(); 
			for(Long id : orderItemMetaIds){ 
				sb.append(id.toString()+","); 
			} 
			String ids = sb.substring(0, sb.lastIndexOf(",")); 
			return ids; 
		} 
		return ""; 
	}

	public Long getEbkTaskId() {
		return ebkTaskId;
	}

	public void setEbkTaskId(Long ebkTaskId) {
		this.ebkTaskId = ebkTaskId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public EbkTask getEbkTask() {
		return ebkTask;
	}

	public void setEbkTask(EbkTask ebkTask) {
		this.ebkTask = ebkTask;
	}

	public List<String> getValidContentList() {
		return validContentList;
	}

	public void setEbkTaskService(EbkTaskService ebkTaskService) {
		this.ebkTaskService = ebkTaskService;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public void setComLogRemoteService(ComLogService comLogRemoteService) {
		this.comLogRemoteService = comLogRemoteService;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setSupplierOrderNo(String supplierOrderNo) {
		this.supplierOrderNo = supplierOrderNo;
	}

	public void setVisitTimeStart(String visitTimeStart) {
		this.visitTimeStart = visitTimeStart;
	}

	public void setPasswordCertificate(String passwordCertificate) {
		this.passwordCertificate = passwordCertificate;
	}

	public void setVisitPerName(String visitPerName) {
		this.visitPerName = visitPerName;
	}

	public void setCertificateServiceProxy(
			CertificateService certificateServiceProxy) {
		this.certificateServiceProxy = certificateServiceProxy;
	}

	public void setOrderItemMetaAperiodicService(
			OrderItemMetaAperiodicService orderItemMetaAperiodicService) {
		this.orderItemMetaAperiodicService = orderItemMetaAperiodicService;
	}

	public void setType(String type) {
		this.type = type;
	}
}
