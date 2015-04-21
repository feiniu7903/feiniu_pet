package com.lvmama.back.sweb.fax;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkCertificateItem;
import com.lvmama.comm.bee.po.ebooking.EbkFaxSend;
import com.lvmama.comm.bee.po.ebooking.EbkFaxTask;
import com.lvmama.comm.bee.po.fax.OrdFaxRecv;
import com.lvmama.comm.bee.service.ebooking.EbkFaxTaskService;
import com.lvmama.comm.bee.service.fax.OrdFaxRecvService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.vo.Constant;

@Results(value={
		@Result(name="success",location="/WEB-INF/pages/back/fax/ebkFax.jsp"),
		@Result(name="loadOldEbkFaxTask",location="/WEB-INF/pages/back/fax/ebkFaxTaskList.jsp"),
		@Result(name="showFaxSend",location="/WEB-INF/pages/back/fax/faxSend.jsp"),
		@Result(name="faxIndex",type="redirect",location="fax/ebkFax.do?ebkFaxTaskTab=${ebkFaxTaskTab}")
		})
public class EbkFaxAction  extends BackBaseAction{
	private static final long serialVersionUID = -3692873197399682150L;
	private EbkFaxTaskService ebkFaxTaskService;
	private OrdFaxRecvService ordFaxRecvService;
	private Page<EbkFaxSend> ebkFaxSendPage = new Page<EbkFaxSend>();
	private Page<EbkFaxTask> ebkFaxTaskPage = new Page<EbkFaxTask>();
	private List<OrdFaxRecv> ordFaxRecvList;
	private EbkCertificate ebkCertificate = new EbkCertificate();
	private EbkCertificateItem ebkCertificateItem = new EbkCertificateItem();
	private EbkFaxTask ebkFaxTask = new EbkFaxTask();
	private String ebkFaxTaskIds;
	private String ebkFaxTaskTab;
	private Long isNoSendCount;
	private Long isNoRepliedCount;
	private String planTimeStart;//计划发送时间
	private String planTimeEnd;
    
	private String visitTimeStart;//游玩时间
	private String visitTimeEnd;
    
	private String sendTimeStart;//实际发送时间
	private String sendTimeEnd;

	private ComLogService comLogService;
	
	private List<EbkFaxTask> ebkFaxTaskList;
	private Long fatherEbkFaxTaskId;
	private Long ebkFaxSendId;
	private String innerListFlag;
	private String orderId;
	@Action("/fax/ebkFax")
	@Override
	public String execute() throws Exception {
		
		//加载传真列表
		Map<String,Object> params = this.createParamsMap();
		ebkFaxTaskPage.setTotalResultSize(ebkFaxTaskService.getEbkFaxTaskCountByParams(params));
		ebkFaxTaskPage.buildUrl(getRequest());
		ebkFaxTaskPage.setCurrentPage(super.page);
		params.put("start", ebkFaxTaskPage.getStartRows());
		params.put("end", ebkFaxTaskPage.getEndRows());
		//直接给出列表，以便页面使用
		innerListFlag = "false";
		ebkFaxTaskList = ebkFaxTaskService.selectEbkFaxTaskByParams(params);
		//ebkFaxTaskPage.setItems(ebkFaxTaskList);
		
		//加载列表显示数量
		this.queryEbkFaxListCount();
		
		String workTaskId = getRequest().getParameter("workTaskId");
		getRequest().getSession().setAttribute("workTaskId", workTaskId);
		return SUCCESS;
	}
	@Action("/fax/loadOldEbkFaxTask")
	public String loadOldEbkFaxTask(){
		ebkFaxTaskList = this.ebkFaxTaskService.selectOldEbkFaxTaskListWithTaskId(fatherEbkFaxTaskId);
		innerListFlag = "true";
		return "loadOldEbkFaxTask";
	}
	@Action("/fax/loadEbkCertificate")
	public void loadEbkCertificateItem(){
		ebkCertificate = ebkFaxTaskService.getEbkCertificateById(ebkCertificate.getEbkCertificateId());
		try {
			String itemJson="{\"ebkCertificateId\":\""+ebkCertificate.getEbkCertificateId()+"\",\"memo\":\""+ebkCertificate.getMemo()+"\"}";
			this.outputToClient(itemJson);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}
	
	/**
	 * 更新内部备注
	 * @return
	 * @throws Exception
	 */
	@Action("/fax/updateFaxMemo")
	public void updateFaxMemo() throws Exception{
		JSONResult result = new JSONResult();
		try{
		    String[] ebkFaxTaskIdsStr = ebkFaxTaskIds.split("\\s*,\\s*");
			if(ebkFaxTaskIdsStr != null && ebkFaxTaskIdsStr.length>0){
				List<Long> ebkFaxTaskIdList = new ArrayList<Long>();
				for(int i=0;i<ebkFaxTaskIdsStr.length;i++){
					if(!StringUtil.isEmptyString(ebkFaxTaskIdsStr[i])){
					  ebkFaxTaskIdList.add(Long.valueOf(ebkFaxTaskIdsStr[i]));
					}
				}
				if(ebkFaxTaskIdList.size()>0){
				    ebkFaxTaskService.updateEbkCertificateByFaxTaskId(ebkCertificate.getMemo(),ebkFaxTaskIdList,getSessionUserName());
				}
			}
			if(ebkCertificate.getEbkCertificateId()!=null){
				ebkFaxTaskService.updateEbkCertificate(ebkCertificate);
				comLogService.insert("EBK_FAX_TASK", null, ebkFaxTask.getEbkFaxTaskId(),
						this.getSessionUserName(),
						Constant.COM_LOG_EBK_FAX_TASK_EVENT.updateFaxMemo.name(), 
						"修改内部备注", ebkCertificate.getMemo(),
						null);
			}
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	/**
	 * 修改传真任务信息
	 */
	@Action("/fax/updateFaxTask")
	public void updateFaxInfo(){
		String logContent = null;
		
		EbkFaxTask tempEbkFaxTaskebk = ebkFaxTaskService.getByEbkFaxTaskId(ebkFaxTask.getEbkFaxTaskId());
		if(Constant.EBK_FAX_TASK_STATUS.FAX_SEND_STATUS_DEFAULT.getStatus().equals(tempEbkFaxTaskebk.getSendStatus())) {
			if("true".equals(ebkFaxTask.getDisableSend())) {
				logContent="修改传真任务为不发送传真";
				ebkFaxTask.setSendStatus(Constant.EBK_FAX_TASK_STATUS.FAX_TASK_STATUS_PAUSE.getStatus());
			}
			if("false".equals(ebkFaxTask.getDisableSend())) {
				logContent="修改传真任务为发送传真";
			}
			if("true".equals(ebkFaxTask.getAutoSend())) {
				logContent="修改传真任务改为自动发送";
			}
			if("false".equals(ebkFaxTask.getAutoSend())) {
				logContent="修改传真任务改为非自动发送";
			}
			ebkFaxTaskService.updateEbkFaxTask(ebkFaxTask,getSessionUserName(), logContent);
			
			try {
				this.outputToClient("{\"flag\":\"true\"}");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			try {
				this.outputToClient("{\"flag\":\"false\"}");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 查看传真发送日志
	 * @return
	 */
	@Action("/fax/showFaxSend")
	public String showFaxSend(){
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("ebkFaxTaskId", ebkFaxTask.getEbkFaxTaskId());
		ebkFaxSendPage.setTotalResultSize(ebkFaxTaskService.getEbkFaxSendCountByParam(params));
		ebkFaxSendPage.buildUrl(getRequest());
		ebkFaxSendPage.setCurrentPage(super.page);
		params.put("start", ebkFaxSendPage.getStartRows());
		params.put("end", ebkFaxSendPage.getEndRows());
		ebkFaxSendPage.setItems(ebkFaxTaskService.queryEbkFaxSendByParam(params));
		
		//加载传真回传文件
		ordFaxRecvList = ordFaxRecvService.queryOrdFaxRecvCertificateId(ebkCertificate.getEbkCertificateId());
		return "showFaxSend";
	}
	
	/**
	 * 列表显示数量
	 * @param listCount
	 */
	private void queryEbkFaxListCount(){
		Map<String, Object> countParams = new HashMap<String, Object>();
		countParams.put("ebkFaxTaskTab", Constant.EBK_FAX_TASK_TAB.NOREPLIED.getCode());
		countParams.put("faxSendRecvStatusIsNull", "true");
		countParams.put("ebkCertificateItemValid", "true");
		countParams.put("ebkCertificateValid", "true");
		countParams.put("newSend", "true");
		countParams.put("disableSend", "false");
		isNoRepliedCount=(long)ebkFaxTaskService.getEbkFaxTaskCountByParams(countParams);	
		countParams = new HashMap<String, Object>();
		countParams.put("ebkFaxTaskTab", Constant.EBK_FAX_TASK_TAB.NOSEND.getCode());
		countParams.put("planTimeEnd", DateUtil.getDayEnd(new Date()));
		countParams.put("ebkCertificateItemValid", "true");
		countParams.put("ebkCertificateValid", "true");
		countParams.put("disableSend", "false");
		isNoSendCount=(long)ebkFaxTaskService.getEbkFaxTaskCountByParams(countParams);
	}
	
	private Map<String,Object> createParamsMap(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("ebkFaxSendId", ebkFaxSendId);
		params.put("ebkCertificateId", ebkCertificate.getEbkCertificateId());
		params.put("ebkFaxTaskTab", ebkFaxTaskTab);
		params.put("targetId", ebkCertificate.getTargetId());
		params.put("travellerName", ebkCertificate.getTravellerName());
		params.put("mobile", ebkCertificate.getMobile());
		if (!StringUtil.isEmptyString(this.orderId)) {
			params.put("orderId", Long.valueOf(this.orderId.trim()));
		}
		params.put("metaProductId", ebkCertificateItem.getMetaProductId());
		params.put("metaProductName", ebkCertificateItem.getMetaProductName());
		params.put("productName", ebkCertificateItem.getProductName());
		params.put("sort", "planTime");
		if(getIsAllFax()){
			params.put("sort", "createTimeDesc");
		}
		if(!StringUtil.isEmptyString(ebkCertificate.getOrderType())){
			ebkCertificate.setOrderTypeVo(Arrays.asList(ebkCertificate.getOrderType().split("\\s*,\\s*")));
			params.put("orderType", ebkCertificate.getOrderTypeVo());
		}
		if(!StringUtil.isEmptyString(ebkCertificate.getSubProductType())){
			ebkCertificate.setSubProductTypeVo(Arrays.asList(ebkCertificate.getSubProductType().split("\\s*,\\s*")));
			params.put("subProductType", ebkCertificate.getSubProductTypeVo());
		}
		if(!StringUtil.isEmptyString(ebkCertificate.getProductType())){
			ebkCertificate.setProductTypeVo(Arrays.asList(ebkCertificate.getProductType().split("\\s*,\\s*")));			
			params.put("productType", ebkCertificate.getProductTypeVo());
		}
		if(params.containsKey("subProductType")||params.containsKey("productType") || params.containsKey("orderType")){
			params.put("hasProductType", "true");
		}
		params.put("supplierId", ebkCertificate.getSupplierId());
		params.put("ebkCertificateType", ebkCertificate.getEbkCertificateType());
		params.put("filialeName", ebkCertificate.getFilialeName());
		params.put("autoSend", ebkFaxTask.getAutoSend());
		//params.put("faxSendRecvStatus", ebkFaxTask.getFaxSendRecvStatus());
		if(!StringUtil.isEmptyString(ebkFaxTask.getFaxSendRecvStatus())){
			ebkFaxTask.setFaxSendRecvStatusVo(Arrays.asList(ebkFaxTask.getFaxSendRecvStatus().split("\\s*,\\s*")));
			params.put("faxSendRecvStatus", ebkFaxTask.getFaxSendRecvStatusVo());
		}
		if(getIsNoSend()){
			params.put("ebkCertificateValid", "true");
			params.put("disableSend", "false");
			if(planTimeEnd == null && this.page == null) {
				planTimeEnd = DateUtil.formatDate(DateUtil.getDayEnd(new Date()), "yyyy-MM-dd HH:mm");
			}
		}
		if(getIsNoReplied()){
			params.put("faxSendRecvStatusIsNull", "true");
			params.put("newSend", "true");
			params.put("ebkCertificateValid", "true");
			params.put("disableSend", "false");
			if(planTimeStart == null && this.page == null) {
				planTimeStart =DateUtil.formatDate( DateUtil.getDayStart(new Date()), "yyyy-MM-dd HH:mm");
			}
		}
		
		if(!StringUtil.isEmptyString(ebkFaxTask.getSendStatus())){
			ebkFaxTask.setSendStatusVo(Arrays.asList(ebkFaxTask.getSendStatus().split("\\s*,\\s*")));	
			if(ebkFaxTask.getSendStatusVo().contains(Constant.EBK_FAX_TASK_STATUS.FAX_TASK_STATUS_PAUSE.getStatus())) {
				params.put("disableSend", "true");
			}else{
				params.put("sendStatus", ebkFaxTask.getSendStatusVo());
			}
		}	
		params.put("ebkCertificateItemValid", "true");
		if(!StringUtil.isEmptyString(planTimeEnd)){
			params.put("planTimeEnd",   DateUtil.stringToDate(planTimeEnd, "yyyy-MM-dd HH:mm"));
		} 
		if(!StringUtil.isEmptyString(planTimeStart)){
			params.put("planTimeStart", DateUtil.stringToDate(planTimeStart, "yyyy-MM-dd HH:mm"));
		} 
		if(!StringUtil.isEmptyString(visitTimeStart)){
			params.put("visitTimeStart", DateUtil.stringToDate(visitTimeStart, "yyyy-MM-dd HH:mm"));
		}
		
		if(!StringUtil.isEmptyString(visitTimeEnd)){
			params.put("visitTimeEnd", DateUtil.stringToDate(visitTimeEnd, "yyyy-MM-dd HH:mm"));
		}
		
		if(!StringUtil.isEmptyString(sendTimeStart)){
			params.put("sendTimeStart", DateUtil.stringToDate(sendTimeStart, "yyyy-MM-dd HH:mm"));
		}
		if(!StringUtil.isEmptyString(sendTimeEnd)){
			params.put("sendTimeEnd", DateUtil.stringToDate(sendTimeEnd, "yyyy-MM-dd HH:mm"));
		}
		return params;
	}

	public Boolean getIsNoSend(){
		return Constant.EBK_FAX_TASK_TAB.NOSEND.getCode().equals(ebkFaxTaskTab)?true:false;
	}
	
	public Boolean getIsNoReplied(){
		return Constant.EBK_FAX_TASK_TAB.NOREPLIED.getCode().equals(ebkFaxTaskTab)?true:false;
	}
	
	public Boolean getIsAllFax(){
		return (Constant.EBK_FAX_TASK_TAB.ALLFAX.getCode().equals(ebkFaxTaskTab)||StringUtil.isEmptyString(ebkFaxTaskTab))?true:false;
	}

	public void setEbkFaxTaskService(EbkFaxTaskService ebkFaxTaskService) {
		this.ebkFaxTaskService = ebkFaxTaskService;
	}

	public void setOrdFaxRecvService(OrdFaxRecvService ordFaxRecvService) {
		this.ordFaxRecvService = ordFaxRecvService;
	}

	public Page<EbkFaxTask> getEbkFaxTaskPage() {
		return ebkFaxTaskPage;
	}

	public String getEbkFaxTaskTab() {
		return ebkFaxTaskTab;
	}
	
	public Page<EbkFaxSend> getEbkFaxSendPage() {
		return ebkFaxSendPage;
	}

	public void setEbkFaxSendPage(Page<EbkFaxSend> ebkFaxSendPage) {
		this.ebkFaxSendPage = ebkFaxSendPage;
	}

	public void setEbkFaxTaskTab(String ebkFaxTaskTab) {
		this.ebkFaxTaskTab = ebkFaxTaskTab;
	}

	public Long getIsNoSendCount() {
		return isNoSendCount;
	}

	public Long getIsNoRepliedCount() {
		return isNoRepliedCount;
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

	public EbkFaxTask getEbkFaxTask() {
		return ebkFaxTask;
	}

	public void setEbkFaxTask(EbkFaxTask ebkFaxTask) {
		this.ebkFaxTask = ebkFaxTask;
	}

	public String getPlanTimeStart() {
		return planTimeStart;
	}

	public void setPlanTimeStart(String planTimeStart) {
		this.planTimeStart = planTimeStart;
	}

	public String getPlanTimeEnd() {
		return planTimeEnd;
	}

	public void setPlanTimeEnd(String planTimeEnd) {
		this.planTimeEnd = planTimeEnd;
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

	public String getSendTimeStart() {
		return sendTimeStart;
	}

	public void setSendTimeStart(String sendTimeStart) {
		this.sendTimeStart = sendTimeStart;
	}

	public String getSendTimeEnd() {
		return sendTimeEnd;
	}

	public void setSendTimeEnd(String sendTimeEnd) {
		this.sendTimeEnd = sendTimeEnd;
	}

	public void setEbkFaxTaskIds(String ebkFaxTaskIds) {
		this.ebkFaxTaskIds = ebkFaxTaskIds;
	}

	public List<OrdFaxRecv> getOrdFaxRecvList() {
		return ordFaxRecvList;
	}

	public ComLogService getComLogService() {
		return comLogService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}
	
	public Constant.EBK_FAX_TASK_STATUS[]  getEbkFaxTaskStatusList() {
		return Constant.EBK_FAX_TASK_STATUS.values();
	}
	public List<EbkFaxTask> getEbkFaxTaskList() {
		return ebkFaxTaskList;
	}
	public void setEbkFaxTaskList(List<EbkFaxTask> ebkFaxTaskList) {
		this.ebkFaxTaskList = ebkFaxTaskList;
	}
	public Long getFatherEbkFaxTaskId() {
		return fatherEbkFaxTaskId;
	}
	public void setFatherEbkFaxTaskId(Long fatherEbkFaxTaskId) {
		this.fatherEbkFaxTaskId = fatherEbkFaxTaskId;
	}
	public String getInnerListFlag() {
		return innerListFlag;
	}
	public void setInnerListFlag(String innerListFlag) {
		this.innerListFlag = innerListFlag;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Long getEbkFaxSendId() {
		return ebkFaxSendId;
	}
	public void setEbkFaxSendId(Long ebkFaxSendId) {
		this.ebkFaxSendId = ebkFaxSendId;
	}
}
