package com.lvmama.pet.sweb.work;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.work.WorkDepartment;
import com.lvmama.comm.pet.po.work.WorkGroup;
import com.lvmama.comm.pet.po.work.WorkGroupUser;
import com.lvmama.comm.pet.po.work.WorkOrder;
import com.lvmama.comm.pet.po.work.WorkOrderType;
import com.lvmama.comm.pet.po.work.WorkTask;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.work.WorkDepartmentService;
import com.lvmama.comm.pet.service.work.WorkGroupService;
import com.lvmama.comm.pet.service.work.WorkGroupUserService;
import com.lvmama.comm.pet.service.work.WorkOrderTypeService;
import com.lvmama.comm.pet.service.work.WorkTaskService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

@Namespace("/work/task")
@Results({
	@Result(name="undo_list",location="/WEB-INF/pages/back/work/work_task_undo_list.jsp"),
	@Result(name="my_list",location="/WEB-INF/pages/back/work/work_task_my_list.jsp"),
	@Result(name="work_task_detail",location="/WEB-INF/pages/back/work/work_task_detail.jsp"),
	@Result(name="callcenter_workorder_query",location="/WEB-INF/pages/back/work/work_task_monitor_callcenter.jsp"),
	@Result(name="monitor",location="/WEB-INF/pages/back/work/work_task_monitor.jsp"),
	@Result(name="ord_monitor",location="/WEB-INF/pages/back/work/work_order_monitor.jsp"),
	@Result(name="error",location="/WEB-INF/pages/back/work/error.jsp")
})
public class WorkTaskAction extends BackBaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private WorkTaskService workTaskService;
	private WorkOrderTypeService workOrderTypeService;
	private WorkDepartmentService workDepartmentService;
	private WorkGroupUserService workGroupUserService;
	private WorkGroupService workGroupService;
	private ProdProductService prodProductService;
	private UserUserProxy userUserProxy;
	private OrderService orderServiceProxy;

	private String permId;
	private Long receiveDept;
	private Long receiveGroup;
	private String receiveUser;
	private String workOrderId;
	private String orderId;
	private Long productId;
	private String productName;
	private String workOrderUserName;
	private String workOrderMobileNumber;
	private String createTimeStart;
	private String createTimeEnd;
	private String limitTimeStart;
	private String limitTimeEnd;
	private String workOrderType;
	private String workOrderTypeName;
	private String status;
	private String selectType;
	private String workTaskId;
	private String taskSeqNo;
	private String completeTimeOutFlag;
	private String emergencyFlag;
	private String repeatFlag;
	private String createUserName;
	
	private List<WorkDepartment> departmentList;
	private List<WorkGroup> workGroupList;
	private List<WorkGroupUser> workGroupUserList;
	
	/**
	 * 未处理工单
	 * @return
	 */
	@Action("undo_list")
	public String undoList(){
		PermUser user = getSessionUser();
		if(user == null){
			setRequestAttribute("error", "无法获取用户，请重新登录");
			return "error";
		}
		Page<WorkTask> workTaskPage = new Page<WorkTask>();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("workOrderType", workOrderType);
		params.put("status", "UNCOMPLETED");
		params.put("receiverUserName", user.getUserName());
		workTaskPage.setTotalResultSize(workTaskService.getWorkTaskPageCount(params));
		workTaskPage.buildUrl(getRequest());
		workTaskPage.setCurrentPage(super.page);
		params.put("start", workTaskPage.getStartRows());
		params.put("end", workTaskPage.getEndRows());
		params.put("orderbyType", 1);
		workTaskPage.setItems(this.getWorkTaskPageByParams(params));
		setRequestAttribute("workTaskPage", workTaskPage);
		Long dataVersion = (Long)MemcachedUtil.getInstance().get(Constant.KEY_WORK_TASK_DATA_VERSION + getSessionUserName());
		dataVersion = (dataVersion == null)?0L:dataVersion;
		setRequestAttribute("dataVersion", dataVersion);
		setRequestAttribute("loginFrom", getSession(Constant.SESSION_LOGIN_FROM));
		return "undo_list";
	}
	/**
	 * 获取未处理工单数据版本号
	 */
	@Action("get_data_version")
	public void getDataVersion(){
		Long dataVersion = (Long)MemcachedUtil.getInstance().get(Constant.KEY_WORK_TASK_DATA_VERSION + getSessionUserName());
		if(dataVersion == null){
			dataVersion=workGroupUserService.getMaxTaskIdByPermUserId(getSessionUser().getUserId());
			MemcachedUtil.getInstance().set(Constant.KEY_WORK_TASK_DATA_VERSION + getSessionUserName(),dataVersion);
		}
		sendAjaxMsg(String.valueOf(dataVersion));
	}
	/**
	 * 工单类型下拉suggest下拉列表
	 */
	@Action("get_work_order_type_list")
	public void getWorkOrderTypeList(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("typeName", getRequestParameter("typeName"));
		params.put("start", 1);
		params.put("end", 10);
		List<WorkOrderType> types = workOrderTypeService.queryWorkOrderTypePage(params);
		sendAjaxResultByJson(JSONArray.fromObject(types).toString());
	}
	/**
	 * 我的所有工单
	 * @return
	 */
	@Action("my_list")
	public String myAllList(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("workOrderId", workOrderId==null?null:workOrderId.replace("G", ""));
		params.put("orderId", orderId);
		params.put("productName", productName);
		params.put("workOrderUserName", workOrderUserName);
		params.put("workOrderMobileNumber", workOrderMobileNumber);
		params.put("receiveGroup", receiveGroup);
		params.put("receiverUserName", receiveUser);
		params.put("taskSeqNo", taskSeqNo);
		if(!StringUtil.isEmptyString(createTimeStart)){
			params.put("createTimeStart", DateUtil.stringToDate(createTimeStart, "yyyy-MM-dd"));
		}
		if(!StringUtil.isEmptyString(createTimeEnd)){
			params.put("createTimeEnd", DateUtil.stringToDate(createTimeEnd, "yyyy-MM-dd"));
		}
		params.put("workOrderType", workOrderType);
		params.put("status", status);
		params.put("userName", getSessionUserName());
		params.put("selectType", selectType == null?"1":selectType);
		Page<WorkTask> workTaskPage = new Page<WorkTask>();
		workTaskPage.setTotalResultSize(workTaskService.getWorkTaskPageCount(params));
		workTaskPage.buildUrl(getRequest());
		workTaskPage.setCurrentPage(super.page);
		params.put("start", workTaskPage.getStartRows());
		params.put("end", workTaskPage.getEndRows());
		params.put("orderbyType", 2);
		workTaskPage.setItems(this.getWorkTaskPageByParams(params));
		setRequestAttribute("workTaskPage", workTaskPage);
		setRequestAttribute("loginFrom", getSession(Constant.SESSION_LOGIN_FROM));

		this.getWorkTaskDepartAndGroup();
		return "my_list";
	}
	
	/**
	 * 任务监控
	 * @return
	 */
	@Action("monitor")
	public String monitor(){
		/*Map<String, Object> params = workOrderMsg();
		if(!StringUtil.isEmptyString(createTimeStart)){
			params.put("createTimeStart", DateUtil.stringToDate(createTimeStart, "yyyy-MM-dd"));
		}
		if(!StringUtil.isEmptyString(createTimeEnd)){
			params.put("createTimeEnd", DateUtil.stringToDate(createTimeEnd, "yyyy-MM-dd"));
		}
		params.put("status", status);
		params.put("orderbyType", 3);*/
		
		this.getWorkTaskDepartAndGroup();
		return "monitor";
	}
	/**
	 * 任务监控
	 * @return
	 */
	@Action("monitorList")
	public String monitorList(){
		Map<String, Object> params = workOrderMsg();
		if(!StringUtil.isEmptyString(createTimeStart)){
			params.put("createTimeStart", DateUtil.stringToDate(createTimeStart, "yyyy-MM-dd"));
		}
		if(!StringUtil.isEmptyString(createTimeEnd)){
			params.put("createTimeEnd", DateUtil.stringToDate(createTimeEnd, "yyyy-MM-dd"));
		}
		params.put("status", status);
		params.put("orderbyType", 3);
		workOrderMessage(params);
		return "monitor";
	}
	/**
	 * 工单监控
	 * @return
	 */
	@Action("orderMonitor")
	public String ordMonitor(){
		Map<String, Object> params = workOrderMsg();
		if(!StringUtil.isEmptyString(limitTimeStart)){
			params.put("limitTimeStart", DateUtil.stringToDate(limitTimeStart, "yyyy-MM-dd HH:mm"));
		}
		if(!StringUtil.isEmptyString(limitTimeEnd)){
			params.put("limitTimeEnd", DateUtil.stringToDate(limitTimeEnd, "yyyy-MM-dd HH:mm"));
		}
		if (Constant.WORK_ORDER_PROCESS_LEVEL.PROMPTLY.getCode().equals(emergencyFlag) 
				&& Constant.WORK_ORDER_PROCESS_LEVEL.REPEAT.getCode().equals(repeatFlag)) {
			params.put("bothEmergencyRepeat", true);
		} else {
			params.put("emergency", emergencyFlag);
			params.put("repeat", repeatFlag);
		}
		params.put("status", Constant.WORK_TASK_STATUS.UNCOMPLETED.getCode()); //未完成状态
		//判断排序
		String submitTimeSort = this.getRequestParameter("submitTimeSort");
		String limitTimeSort = this.getRequestParameter("limitTimeSort");
		if (StringUtils.isNotBlank(submitTimeSort)) {
			params.put("orderbyType", submitTimeSort);
		} else if (StringUtils.isNotBlank(limitTimeSort)) {
			params.put("orderbyType", limitTimeSort);
		} else {
			params.put("orderbyType", 3);
		}
		workOrderMessage(params);
		return "ord_monitor";
	}
	private Map<String, Object> workOrderMsg() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("workOrderId", workOrderId==null?null:workOrderId.replace("G", ""));
		params.put("orderId", orderId);
		params.put("productId", productId);
		params.put("workOrderUserName", workOrderUserName);
		params.put("workOrderMobileNumber", workOrderMobileNumber);
		params.put("receiveGroup", receiveGroup);
		params.put("receiverUserName", receiveUser);
		params.put("taskSeqNo", taskSeqNo);
		params.put("createUserName", createUserName);
		params.put("workOrderType", workOrderType);
		params.put("userName", getSessionUserName());
		params.put("workTaskId",workTaskId);
		params.put("completeTimeOut", completeTimeOutFlag);
		return params;
	}
	/**
	 * 分页查询工单信息
	 * @param params
	 */
	private void workOrderMessage(Map<String, Object> params) {
		Page<WorkTask> workTaskPage = new Page<WorkTask>();
		workTaskPage.setTotalResultSize(workTaskService.getWorkTaskPageCount(params));
		workTaskPage.buildUrl(getRequest());
		workTaskPage.setCurrentPage(super.page);
		params.put("start", workTaskPage.getStartRows());
		params.put("end", workTaskPage.getEndRows());
		workTaskPage.setItems(this.getWorkTaskPageByParams(params));
		setRequestAttribute("workTaskPage", workTaskPage);
		
		this.getWorkTaskDepartAndGroup();
	}
	/**
	 * 去来电页面未处理工单查看
	 * @return
	 */
	@Action("monitor_callcenter")
	public String monitor_callcenter(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("workOrderMobileNumber", workOrderMobileNumber);
		params.put("status", status);
		params.put("orderbyType", 3);
		workOrderMessage(params);
		return "callcenter_workorder_query";
	}
	@Action("work_task_detail")
	public String workTaskDetail(){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("workTaskId", getRequestParameter("workTaskId"));
		params.put("start", 1);
		params.put("end", 1);
		List<WorkTask> workTasks = this.getWorkTaskPageByParams(params);
		WorkTask workTask = workTasks.get(0);
		if(StringUtils.isNotEmpty(workTask.getWorkOrder().getUserName())){
			workTask.getWorkOrder().setUserUser(userUserProxy.getUsersByMobOrNameOrEmailOrCard(workTask.getWorkOrder().getUserName()));
		}
		setRequestAttribute("workTask", workTask);
		params.clear();
		params.put("workOrderId", workTask.getWorkOrderId());
		params.put("start", 1);
		params.put("end", workTaskService.getWorkTaskPageCount(params));
		params.put("orderbyType", 4);
		setRequestAttribute("workTaskList", workTaskService.getWorkTaskPage(params));
		return "work_task_detail";
	}
	
	/**
	 * 获取工单列表及产品名称
	 * @param params
	 * @return
	 */
	private List<WorkTask> getWorkTaskPageByParams(Map<String, Object> params){
		List<WorkTask> workTaskList = workTaskService.getWorkTaskPage(params);
		for(WorkTask workTask: workTaskList){
			WorkOrder workOrder=workTask.getWorkOrder();
			if(workOrder!=null && workOrder.getProductId()!=null){
				ProdProduct prod=prodProductService.getProdProduct(workOrder.getProductId());
				if(workOrder.getOrderItemMetaId()!=null 
						&& (Constant.WORK_ORDER_TYPE_AND_SENDGROUP.ZYSH.getWorkOrderTypeCode().equals(workTask.getWorkOrderType().getTypeCode())
								||Constant.WORK_ORDER_TYPE_AND_SENDGROUP.DDQXTXJD.getWorkOrderTypeCode().equals(workTask.getWorkOrderType().getTypeCode()))){
					OrdOrderItemMeta ordOrderItemMeta= orderServiceProxy.getOrdOrderItemMeta(workOrder.getOrderItemMetaId());
					if(ordOrderItemMeta!=null){
						workOrder.setProductName(ordOrderItemMeta.getProductName());
					}
				}else{
					workOrder.setProductName(prod==null?null:prod.getProductName());
				}
			}
		}
		return workTaskList;
	}
	
	/**
	 * 获取部门及组织
	 */
	private void getWorkTaskDepartAndGroup() {
		departmentList=workDepartmentService.getAllWorkDepartment();
		workGroupList = new ArrayList<WorkGroup>();
		workGroupUserList = new ArrayList<WorkGroupUser>();
		if(receiveDept!=null){
			Map<String,Object> groupParams=new HashMap<String,Object>();
			groupParams.put("workDepartmentId", receiveDept);
			groupParams.put("valid", "true");
			workGroupList.addAll(workGroupService.getWorkGroupWithDepartment(groupParams));
			if(receiveGroup!=null){
				workGroupUserList.addAll(workGroupUserService.queryWorkGroupUserByGroupId(receiveGroup));
			}
		}
	}
	
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
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
	public void setWorkDepartmentService(WorkDepartmentService workDepartmentService) {
		this.workDepartmentService = workDepartmentService;
	}
	public void setWorkGroupService(WorkGroupService workGroupService) {
		this.workGroupService = workGroupService;
	}
	public List<WorkGroupUser> getWorkGroupUserList() {
		return workGroupUserList;
	}
	public void setWorkGroupUserService(WorkGroupUserService workGroupUserService) {
		this.workGroupUserService = workGroupUserService;
	}
	public String getWorkOrderId() {
		return workOrderId;
	}
	public void setWorkOrderId(String workOrderId) {
		this.workOrderId = workOrderId;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getWorkOrderUserName() {
		return workOrderUserName;
	}
	public void setWorkOrderUserName(String workOrderUserName) {
		this.workOrderUserName = workOrderUserName;
	}
	public String getWorkOrderMobileNumber() {
		return workOrderMobileNumber;
	}
	public void setWorkOrderMobileNumber(String workOrderMobileNumber) {
		this.workOrderMobileNumber = workOrderMobileNumber;
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
	public String getWorkOrderType() {
		return workOrderType;
	}
	public void setWorkOrderType(String workOrderType) {
		this.workOrderType = workOrderType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSelectType() {
		return selectType;
	}
	public void setSelectType(String selectType) {
		this.selectType = selectType;
	}
	public String getWorkOrderTypeName() {
		return workOrderTypeName;
	}
	public void setWorkOrderTypeName(String workOrderTypeName) {
		this.workOrderTypeName = workOrderTypeName;
	}
	public String getWorkTaskId() {
		return workTaskId;
	}
	public void setWorkTaskId(String workTaskId) {
		this.workTaskId = workTaskId;
	}
	
	
	public String getCompleteTimeOutFlag() {
		return completeTimeOutFlag;
	}
	public void setCompleteTimeOutFlag(String completeTimeOutFlag) {
		this.completeTimeOutFlag = completeTimeOutFlag;
	}
	public String getEmergencyFlag() {
		return emergencyFlag;
	}
	public void setEmergencyFlag(String emergencyFlag) {
		this.emergencyFlag = emergencyFlag;
	}
	public String getRepeatFlag() {
		return repeatFlag;
	}
	public void setRepeatFlag(String repeatFlag) {
		this.repeatFlag = repeatFlag;
	}
	public Long getReceiveDept() {
		return receiveDept;
	}
	public void setReceiveDept(Long receiveDept) {
		this.receiveDept = receiveDept;
	}
	public Long getReceiveGroup() {
		return receiveGroup;
	}
	public void setReceiveGroup(Long receiveGroup) {
		this.receiveGroup = receiveGroup;
	}
	public String getReceiveUser() {
		return receiveUser;
	}
	public void setReceiveUser(String receiveUser) {
		this.receiveUser = receiveUser;
	}
	public List<WorkDepartment> getDepartmentList() {
		return departmentList;
	}
	public List<WorkGroup> getWorkGroupList() {
		return workGroupList;
	}
	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}
	public String getTaskSeqNo() {
		return taskSeqNo;
	}
	public void setTaskSeqNo(String taskSeqNo) {
		this.taskSeqNo = taskSeqNo;
	}
	public String getPermId() {
		return permId;
	}
	public void setPermId(String permId) {
		this.permId = permId;
	}
	public String getLimitTimeStart() {
		return limitTimeStart;
	}
	public void setLimitTimeStart(String limitTimeStart) {
		this.limitTimeStart = limitTimeStart;
	}
	public String getLimitTimeEnd() {
		return limitTimeEnd;
	}
	public void setLimitTimeEnd(String limitTimeEnd) {
		this.limitTimeEnd = limitTimeEnd;
	}
	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}
	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
}
