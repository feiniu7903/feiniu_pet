package com.lvmama.back.sweb.ord;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.back.sweb.BaseAction;
import com.lvmama.back.utils.StringUtil;
import com.lvmama.comm.bee.po.ebooking.EbkFaxTask;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderMemo;
import com.lvmama.comm.bee.service.ebooking.CertificateService;
import com.lvmama.comm.bee.service.ebooking.EbkFaxTaskService;
import com.lvmama.comm.bee.service.fax.FaxService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.po.work.WorkGroupUser;
import com.lvmama.comm.pet.po.work.WorkOrder;
import com.lvmama.comm.pet.po.work.WorkOrderType;
import com.lvmama.comm.pet.po.work.WorkTask;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.pet.service.work.WorkGroupUserService;
import com.lvmama.comm.pet.service.work.WorkOrderService;
import com.lvmama.comm.pet.service.work.WorkOrderTypeService;
import com.lvmama.comm.pet.service.work.WorkTaskService;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.work.builder.WorkOrderFinishedBiz;

@ParentPackage("json-default")
@Results( {
	@Result(name = "showOrderMemoCheck", location = "/WEB-INF/pages/back/ord/fax/showOrderMemoCheck.jsp"),
	@Result(name = "memo", location = "/WEB-INF/pages/back/ord/fax/ord_faxMemo_edit.jsp") 
})
/**
 * 订单传真备注操作类
 * 
 * @author huangl
 */
public class OrdFaxMemoAction extends BaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4531837914262307282L;
	/**
	 * 订单服务接口
	 */
	private OrderService orderServiceProxy;
	/**
	 * 订单id
	 */
	private Long orderId;
	private Long orderMemoId;
	/**
	 * 订单对像.
	 */
	private OrdOrder ordOrder;

	private List<OrdOrderMemo> orderMemosList;
	
	private OrdOrderMemo orderMemo;
	private EbkFaxTaskService ebkFaxTaskService;
	private WorkOrderTypeService workOrderTypeService;
	private WorkOrderType workOrderType;
	private SupSupplier supSupplier;
	private CertificateService certificateServiceProxy;
	private FaxService faxServiceProxy;
	private String TheOrdPerson;
	private String ordOrderItemProdName;
	private SupplierService supplierService;
	private Map<Long,SupSupplier> supplierMap;
	private String memoSoureFlag;
	private String content;
	private Long workTaskId;
	private String creatorComplete;//接受返回值，是否是系统触发完成
	private WorkOrderFinishedBiz workOrderFinishedProxy;
	
	
	private WorkOrder workOrder;
	private WorkOrderService workOrderService;
	private WorkTask workTask;
	private WorkTaskService workTaskService;
	/**
	 * 工单操作、人工操作
	 */
	private String operateType;
	private Long[] ordItemIds;
	private String[] faxMemos;
	public String getOperateType() {
		return operateType;
	}
	public String getMemoSoureFlag() {
		return memoSoureFlag;
	}
	public void setMemoSoureFlag(String memoSoureFlag) {
		this.memoSoureFlag = memoSoureFlag;
	}
	/**
	 * 审核任务
	 * @return
	 */
	
	@Action("/ord/onlySaveResult")
	public void onlySaveResult() {
		try {
			saveOrderMemo();
			returnMessage(true);
		} catch (Exception e) {
			e.printStackTrace();
			this.sendAjaxResult("false");
		}
	} 
	/**
	 * EBK订单生成
	 * @return
	 */
	
	@Action(value ="/ord/EbkSendOrder")
	public void doEbkSendOrder(){
		
		this.LOG.info("EbkSendOrder OrderId : " + this.orderId);
		boolean flag = true;
		saveOrderMemo();
		if("workOrder".equalsIgnoreCase(this.operateType)){
			flag = false;
			List<OrdOrderMemo> memoList = orderServiceProxy.queryMemoByOrderId(orderId);
			OrdOrderMemo o = null;
			for (int i = 0; i < memoList.size(); i++) {
				o = memoList.get(i);
				if(o.hasUserMemoApprove()){
					o.setStatus("true");
					orderServiceProxy.updateMemo(o, getOperatorName());
					flag = true;
				}
			}
			if(flag){
				this.faxServiceProxy.updateUserMemoStatus(orderId, "");
			}
		}
		if(flag){
			String orderItemMetaId = "";
			if(ordItemIds != null && ordItemIds.length > 0) {
				for (int i = 0; i < ordItemIds.length; i++) {
					Long ordOrderItemMetaId = ordItemIds[i];
					orderServiceProxy.updateFaxMemo(Long.valueOf(ordOrderItemMetaId), faxMemos[i], getOperatorName());
					orderItemMetaId += ordOrderItemMetaId+",";
				}
				orderItemMetaId = (orderItemMetaId+",").replace(",,", "");
				flag = certificateServiceProxy.createSupplierCertificate(orderId,CertificateService.ORDER_MEMO_CHANGE,null, orderItemMetaId);
			}
			if(flag) {
				//add by zhushuying 完成工单
				if (null != workTaskId && !"".equals(workTaskId)) {
					workOrderFinishedProxy.finishWorkOrder(workTaskId, getSessionUser().getUserName());
					this.faxServiceProxy.updateUserMemoStatus(orderId, "");
				}
				this.sendAjaxMsg("SUCCESS");
				return;
			} 
			this.sendAjaxResult("{result:false,errorMsg:'EBK生成订单出现异常'}");
			return;
		} 
		this.sendAjaxResult("{result:false,errorMsg:'沟通记录保存出现异常'}");
		return;
	}
	
	/**
	 * 是否确定要结束工单
	 * 
	 * @author zhushuying
	 */
	@Action("/ord/complete")
	public void complete(){
		this.faxServiceProxy.updateUserMemoStatus(orderId, "");
		//完成工单
		if (null != workTaskId && !"".equals(workTaskId)) {
			workOrderFinishedProxy.finishWorkOrder(workTaskId, getSessionUser().getUserName());
		}
		this.sendAjaxMsg("SUCCESS");
	}
	
	private void saveOrderMemo() {
		if(StringUtils.isNotEmpty(content)){
			orderMemo=new OrdOrderMemo();
			orderMemo.setOrderId(orderId);
			orderMemo.setContent(content);
			orderMemo.setUserMemo("false");
			orderMemo.setOperatorName(getOperatorName());
			orderMemo.setCreateTime(new Date());
			orderMemo.setType("M1");		
			orderServiceProxy.saveMemo(orderMemo, getOperatorName());
		}
	}
	 
	@Action("/ord/showOrderMemoCheck")
	public String showOrderMemoCheck() {
		// 查询订单
		ordOrder = orderServiceProxy.queryOrdOrderByOrderId(orderId);
		Map<Long, String> faxStatusMap = new HashMap<Long, String>();
		for(OrdOrderItemMeta ooim : ordOrder.getAllOrdOrderItemMetas()) {
			EbkFaxTask ebkFaxTask =ebkFaxTaskService.getEbkFaxTaskByOrderItemMetaId(ooim.getOrderItemMetaId());
			if (null != ebkFaxTask && !StringUtil.isEmptyString(ebkFaxTask.getSendStatus())) {
				faxStatusMap.put(ooim.getOrderItemMetaId(), Constant.EBK_FAX_STATUS.getCnName("FAX_STATUS_" + ebkFaxTask.getSendStatus()));
			}
		}
		this.getRequest().setAttribute("faxStatusMap", faxStatusMap);
		// 备注
		if(orderMemoId!=null)
		{
			orderMemo   = orderServiceProxy.selectMemo(orderMemoId);
			if(orderMemosList==null){
				orderMemosList = new ArrayList<OrdOrderMemo>();
			}
			orderMemosList.add(orderMemo);			
		}else
		{
			orderMemosList = orderServiceProxy.queryMemoByOrderId(orderId);
		}
		List<Long> supplierId = new ArrayList<Long>();
		for (int i = 0; i < ordOrder.getAllOrdOrderItemMetas().size(); i++) {
			supplierId.add(ordOrder.getAllOrdOrderItemMetas().get(i).getSupplierId());
		}
		supplierMap = supplierService.getSupSupplierBySupplierId(supplierId);
		return "showOrderMemoCheck";
	} 
	
	/**
	 * 返回操作成功信息
	 */
	private void returnMessage(boolean flag) {
		try {
			if (flag) {
				this.getResponse().getWriter().write("{result:true}");
			} else {
				this.getResponse().getWriter().write("{result:false}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
	/**
	 * 查看，显示该该资源的详细内容，利用异步返回数据
	 */
	@Action("/ordItemFax/showOrderItemDeal")
	public String showOrderItemDeal() {
		if (orderId != null) {
			this.ordOrder = orderServiceProxy
					.queryOrdOrderByOrderId(orderId);
		}
		return "memo";
	}
	
	/**
	 * 新增或修改订单备注信息
	 */
	@Action("/ordItemFax/saveOrUpdateMemo")
	public void saveOrUpdateMemo() {
		try {
			String ordOrderItemMetaId=this.getRequest().getParameter("ordItemId");
			String faxMemo=this.getRequest().getParameter("faxMemo");
			if(!StringUtil.isEmptyString(ordOrderItemMetaId)){
				this.getOrderServiceProxy().updateFaxMemo(Long.valueOf(ordOrderItemMetaId), faxMemo, getOperatorName());
			}
			this.sendAjaxResult("true");
		} catch (Exception e) {
			e.printStackTrace();
			this.sendAjaxResult("false");
		}
	}
  
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}
	public OrdOrder getOrdOrder() {
		return ordOrder;
	}

	public String getTheOrdPerson() {
		return TheOrdPerson;
	}

	public void setTheOrdPerson(String theOrdPerson) {
		TheOrdPerson = theOrdPerson;
	}

	public String getOrdOrderItemProdName() {
		return ordOrderItemProdName;
	}

	public void setOrdOrderItemProdName(String ordOrderItemProdName) {
		this.ordOrderItemProdName = ordOrderItemProdName;
	}

	public SupSupplier getSupSupplier() {
		return supSupplier;
	}

	public void setSupSupplier(SupSupplier supSupplier) {
		this.supSupplier = supSupplier;
	}

	public List<OrdOrderMemo> getOrderMemosList() {
		return orderMemosList;
	}

	public void setOrderMemosList(List<OrdOrderMemo> orderMemosList) {
		this.orderMemosList = orderMemosList;
	}
	public Map<Long, SupSupplier> getSupplierMap() {
		return supplierMap;
	}
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}
	public Long getOrderMemoId() {
		return orderMemoId;
	}
	public void setOrderMemoId(Long orderMemoId) {
		this.orderMemoId = orderMemoId;
	}
	public CertificateService getCertificateServiceProxy() {
		return certificateServiceProxy;
	}
	public void setCertificateServiceProxy(
			CertificateService certificateServiceProxy) {
		this.certificateServiceProxy = certificateServiceProxy;
	}

	public EbkFaxTaskService getEbkFaxTaskService() {
		return ebkFaxTaskService;
	}

	public void setEbkFaxTaskService(EbkFaxTaskService ebkFaxTaskService) {
		this.ebkFaxTaskService = ebkFaxTaskService;
	}
	public void setFaxServiceProxy(FaxService faxServiceProxy) {
		this.faxServiceProxy = faxServiceProxy;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public void setOperateType(String operateType) {
		this.operateType = operateType;
	}
	public Long[] getOrdItemIds() {
		return ordItemIds;
	}
	public void setOrdItemIds(Long[] ordItemIds) {
		this.ordItemIds = ordItemIds;
	}
	public String[] getFaxMemos() {
		return faxMemos;
	}
	public void setFaxMemos(String[] faxMemos) {
		this.faxMemos = faxMemos;
	}
	public String getCreatorComplete() {
		return creatorComplete;
	}
	public void setCreatorComplete(String creatorComplete) {
		this.creatorComplete = creatorComplete;
	}
	public WorkOrderFinishedBiz getWorkOrderFinishedProxy() {
		return workOrderFinishedProxy;
	}
	public void setWorkOrderFinishedProxy(
			WorkOrderFinishedBiz workOrderFinishedProxy) {
		this.workOrderFinishedProxy = workOrderFinishedProxy;
	}
	public Long getWorkTaskId() {
		return workTaskId;
	}
	public void setWorkTaskId(Long workTaskId) {
		this.workTaskId = workTaskId;
	}
	
	public WorkOrder getWorkOrder() {
		return workOrder;
	}
	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}
	public WorkOrderService getWorkOrderService() {
		return workOrderService;
	}
	public void setWorkOrderService(WorkOrderService workOrderService) {
		this.workOrderService = workOrderService;
	}
	public WorkTask getWorkTask() {
		return workTask;
	}
	public void setWorkTask(WorkTask workTask) {
		this.workTask = workTask;
	}
	public WorkTaskService getWorkTaskService() {
		return workTaskService;
	}
	public void setWorkTaskService(WorkTaskService workTaskService) {
		this.workTaskService = workTaskService;
	}
	public WorkOrderTypeService getWorkOrderTypeService() {
		return workOrderTypeService;
	}
	public void setWorkOrderTypeService(WorkOrderTypeService workOrderTypeService) {
		this.workOrderTypeService = workOrderTypeService;
	}
	public WorkOrderType getWorkOrderType() {
		return workOrderType;
	}
	public void setWorkOrderType(WorkOrderType workOrderType) {
		this.workOrderType = workOrderType;
	}
	
}
