package com.lvmama.pet.work.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.work.WorkGroup;
import com.lvmama.comm.pet.po.work.WorkGroupUser;
import com.lvmama.comm.pet.po.work.WorkOrder;
import com.lvmama.comm.pet.po.work.WorkOrderType;
import com.lvmama.comm.pet.po.work.WorkTask;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.work.PublicWorkOrderService;
import com.lvmama.comm.pet.service.work.WorkGroupService;
import com.lvmama.comm.pet.service.work.WorkGroupUserService;
import com.lvmama.comm.pet.service.work.WorkOrderService;
import com.lvmama.comm.pet.service.work.WorkOrderTypeService;
import com.lvmama.comm.pet.service.work.WorkTaskService;
import com.lvmama.comm.pet.vo.InvokeResult;
import com.lvmama.comm.pet.vo.WorkOrderCreateParam;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class PublicWorkOrderServiceImpl implements PublicWorkOrderService {
	@Autowired
	private WorkOrderService workOrderService;
	@Autowired
	private WorkOrderTypeService workOrderTypeService;
	@Autowired
	private WorkTaskService workTaskService;
	@Autowired
	private WorkGroupUserService workGroupUserService;
	@Autowired
	private ComLogService comLogService;
	private WorkGroupService workGroupService;

	public InvokeResult createWorkOrder(
			WorkOrderCreateParam workOrderCreateParam) {
		// 参数校验
		String error = "";
		InvokeResult invokeResult = new InvokeResult();
		if (workOrderCreateParam == null) {
			error = error + "param is null;";
			invokeResult.setCode(1);
			invokeResult.setDescription(error);
			return invokeResult;
		}
		if (StringUtil.isEmptyString(workOrderCreateParam
				.getWorkOrderTypeCode())) {
			error = error + "WorkOrderTypeCode is null;";
		}
		if (StringUtil.isEmptyString(workOrderCreateParam.getWorkTaskContent())) {
			error = error + "workTaskContent is null;";
		}
		if (error.length() > 0) {
			invokeResult.setCode(1);
			invokeResult.setDescription(error);
			return invokeResult;
		}
		// 是否成功获取工单类型
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("typeCode", workOrderCreateParam.getWorkOrderTypeCode());
		params.put("start", 1);
		params.put("end", 1);
		List<WorkOrderType> types = workOrderTypeService
				.getWorkOrderTypePage(params);
		if (types == null || types.size() != 1) {
			invokeResult.setCode(1);
			invokeResult
					.setDescription("can not get workOrderType by typeCode");
			return invokeResult;
		}
		WorkOrderType workOrderType = types.get(0);
		invokeResult.setWorkOrderTypeName(workOrderType.getTypeName());
		// 如果未传工单接收组织并且不是发给计调的工单，取工单类型中默认的组织
		if (workOrderCreateParam.getReceiveGroupId() == null && !workOrderCreateParam.isJdGroup()) {
			workOrderCreateParam.setReceiveGroupId(workOrderType
					.getReceiverGroupId());
		}
		if (workOrderCreateParam.getReceiveGroupId() == null) {
			error = error + "receiveGroupId is null;";
		}
		if (StringUtil.isEmptyString(workOrderCreateParam.getReceiveUserName())
				&& workOrderCreateParam.getReceiveGroupId() == null) {
			error = error + "receiveUserName and receiveGroupId both are null;";
		}
		if (StringUtil
				.isEmptyString(workOrderCreateParam.getWorkOrderContent())) {
			workOrderCreateParam
					.setWorkOrderContent(workOrderType.getContent());
		}
		if (StringUtil
				.isEmptyString(workOrderCreateParam.getWorkOrderContent())) {
			error = error + "WorkOrderContent is null;";
		}
		if ("false".equals(workOrderType.getLimitCompleteTime())) {
			if (workOrderCreateParam.getLimitTime() == null
					|| workOrderCreateParam.getLimitTime() <= 0) {
				error = error
						+ "If LimitCompleteTime is false,you have to set LimitTime > 0L;";
			}
		} else {
			if (workOrderCreateParam.getLimitTime() == null) {
				workOrderCreateParam.setLimitTime(workOrderType.getLimitTime());
			}
		}

		if (!StringUtil.isEmptyString(workOrderCreateParam.getSendUserName())) {
			if (null == workOrderCreateParam.getSendGroupId()) {
				if (null != workOrderType.getSendGroupId()) {
					workOrderCreateParam.setSendGroupId(workOrderType
							.getSendGroupId());
				} else {
					error = error
							+ "If SendUserName is not empty,you have to set SendGroupId;";
				}
			}
		}

		if (StringUtils.isEmpty(workOrderCreateParam.getUrl())
				&& StringUtils.isNotEmpty(workOrderType.getUrlTemplate())) {
			Map<String, Object> data = new HashMap<String, Object>();
			if ("true".equalsIgnoreCase(workOrderType.getParamOrderId())) {
				data.put("order_id", workOrderCreateParam.getOrderId());
			}
			if ("true".equalsIgnoreCase(workOrderType.getParamProductId())) {
				data.put("product_id", workOrderCreateParam.getProductId());
			}
			if ("true".equalsIgnoreCase(workOrderType.getParamUserName())) {
				data.put("user_name", workOrderCreateParam.getVisitorUserName());
				data.put("mobile_number",
						workOrderCreateParam.getMobileNumber());
			}
			try {
				workOrderCreateParam.setUrl(StringUtil.composeMessage(
						workOrderType.getUrlTemplate(), data));
			} catch (Exception e) {
				invokeResult.setCode(1);
				invokeResult.setDescription("work_order_type url_template="
						+ workOrderType.getUrlTemplate() + " , replace data: "
						+ data);
				return invokeResult;
			}
		}
		// 获取发送用户
		WorkGroupUser sendWorkGroupUser = null;
		
		if (!StringUtil.isEmptyString(workOrderCreateParam.getSendUserName())
				&& workOrderCreateParam.getSendGroupId() != null) {
			WorkGroup workGroup = workGroupService.getWorkGroupById(workOrderCreateParam.getSendGroupId());
			
			params = new HashMap<String, Object>();
			params.put("userName", workOrderCreateParam.getSendUserName());
			params.put("workGroupId", workOrderCreateParam.getSendGroupId());
			params.put("valid", "Y");
			params.put("workGroupUserValid", "true");
			if(workGroup!=null && "计调".equals(workGroup.getGroupName())){
				params.put("workGroupId", null);
			}
			List<WorkGroupUser> wgu = workGroupUserService
					.getWorkGroupUserByPermUserAndGroup(params);
			if (CollectionUtils.isEmpty(wgu)) {
				error = error
						+ "can't find a send work_group_user by userName="
						+ workOrderCreateParam.getSendUserName()
						+ " and workGroupId="
						+ workOrderCreateParam.getSendGroupId() + ";";
			} else {
				sendWorkGroupUser = wgu.get(0);
			}
				
		}
		if (error.length() > 0) {
			invokeResult.setCode(1);
			invokeResult.setDescription(error);
			return invokeResult;
		}
		// 获取接收用户
		Long receiveGroupUserId = null;
		if (workOrderCreateParam.getReceiveGroupId() != null
				&& !StringUtil.isEmptyString(workOrderCreateParam
						.getReceiveUserName())) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("workGroupId", workOrderCreateParam.getReceiveGroupId());
			map.put("userName", workOrderCreateParam.getReceiveUserName());
			map.put("start", 1);
			map.put("end", 1);
			map.put("valid", "true");
			List<WorkGroupUser> groupUsers = workGroupUserService
					.getWorkGroupUserPage(map);
			if (groupUsers != null && groupUsers.size() > 0) {
				receiveGroupUserId = groupUsers.get(0).getWorkGroupUserId();
			}
		}
		WorkGroupUser receiver =null;
		//自定义用户
		if(workOrderCreateParam.isNotGetFitReceiveUser()){
			params = new HashMap<String, Object>();
			params.put("userName", workOrderCreateParam.getReceiveUserName());
			params.put("workGroupId", workOrderCreateParam.getReceiveGroupId());
			params.put("valid", "Y");
			params.put("workGroupUserValid", "true");
			List<WorkGroupUser> wgu = workGroupUserService
					.getWorkGroupUserByPermUserAndGroup(params);
			if(wgu.size()>0){
				receiver =  wgu.get(0);
			}
		}else{
			//是否系统重新分单  发送给客服的通知 一单到底
			if("false".equals(workOrderType.getSysDistribute())){
				if(Constant.WORK_ORDER_TYPE_AND_SENDGROUP.ZFDDCSTX.getWorkOrderTypeCode().equalsIgnoreCase(workOrderType.getTypeCode())
						||Constant.WORK_ORDER_TYPE_AND_SENDGROUP.CTTZSWTZTX.getWorkOrderTypeCode().equalsIgnoreCase(workOrderType.getTypeCode())
						||Constant.WORK_ORDER_TYPE_AND_SENDGROUP.ZYSHTG.getWorkOrderTypeCode().equalsIgnoreCase(workOrderType.getTypeCode())
						||Constant.WORK_ORDER_TYPE_AND_SENDGROUP.ZFHTX.getWorkOrderTypeCode().equalsIgnoreCase(workOrderType.getTypeCode())
						||Constant.WORK_ORDER_TYPE_AND_SENDGROUP.DDQXTX.getWorkOrderTypeCode().equalsIgnoreCase(workOrderType.getTypeCode())
						||Constant.WORK_ORDER_TYPE_AND_SENDGROUP.QZCLTX.getWorkOrderTypeCode().equalsIgnoreCase(workOrderType.getTypeCode())
						||Constant.WORK_ORDER_TYPE_AND_SENDGROUP.CTTZS.getWorkOrderTypeCode().equalsIgnoreCase(workOrderType.getTypeCode())){
					params = new HashMap<String, Object>();
					params.put("userName", workOrderCreateParam.getTakenOperator());
					params.put("groupName", "客户服务中心预订部");
					params.put("valid", "Y");
					params.put("workGroupUserValid", "true");
					List<WorkGroupUser> wgu = workGroupUserService.getWorkGroupUserByPermUserAndGroup(params);
					if(wgu.size()>0){
						receiver =  wgu.get(0);
					}
				}
			}
		}
		if(receiver==null){
			/**
			 * 根据分配规则获取接收用户
			 */
			receiver = workOrderService.getFitUser(
					receiveGroupUserId, workOrderCreateParam.getReceiveGroupId(),
					workOrderCreateParam.getOrderId(),
					workOrderCreateParam.getMobileNumber(), null, null,null);
		}
		// 是否成功获取接收用户校验
		if (receiver == null) {
			invokeResult.setCode(1);
			invokeResult.setDescription("can not find a task receiver");
			return invokeResult;
		}
		// 创建工单
		WorkOrder workOrder = new WorkOrder();
		workOrder.setContent(workOrderCreateParam.getWorkOrderContent());
		workOrder.setLimitTime(workOrderCreateParam.getLimitTime());
		workOrder.setOrderId(workOrderCreateParam.getOrderId());
		workOrder.setProductId(workOrderCreateParam.getProductId());
		workOrder.setUserName(workOrderCreateParam.getVisitorUserName());
		workOrder.setWorkOrderTypeId(workOrderType.getWorkOrderTypeId());
		workOrder.setCreateUserName(workOrderCreateParam.getSendUserName());
		workOrder.setProcessLevel(workOrderCreateParam.getProcessLevel());
		workOrder.setStatus("UNCOMPLETED");
		workOrder.setCreateTime(new Date());
		workOrder.setUrl(workOrderCreateParam.getUrl());
		Long workOrderId = workOrderService.insert(workOrder);
		comLogService.insert("WORK_ORDER", workOrderId, workOrderId, "SYSTEM",
				"WORK_ORDER", "WORK_ORDER_CREATED", "系统创建工单-->" + receiver.getUserName(), "WORK_ORDER");

		// 创建任务
		WorkTask workTask = new WorkTask();
		workTask.setWorkOrderId(workOrderId);
		workTask.setStatus("UNCOMPLETED");
		workTask.setContent(workOrderCreateParam.getWorkTaskContent());
		workTask.setCreateTime(new Date());
		workTask.setReceiver(receiver.getWorkGroupUserId());
		if (sendWorkGroupUser != null)
			workTask.setCreater(sendWorkGroupUser.getWorkGroupUserId());
		Long workTaskId = workTaskService.insert(workTask);
		comLogService.insert("WORK_ORDER", workTask.getWorkOrderId(),
				workTaskId, "SYSTEM", "WORK_TASK", "WORK_TASK_CREATED",
				"系统创建任务-->" + receiver.getUserName(), "WORK_TASK");
		invokeResult.setWorkTaskId(workTaskId);
		invokeResult.setWorkOrderId(workOrderId);
		invokeResult.setCode(0);
		invokeResult.setDescription("SUCCESS");
		invokeResult.setResult(receiver.getUserName());
		return invokeResult;
	}

	@Override
	public boolean isExists(Long orderId, Long productId, String typeCode,
			String status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		params.put("productId", productId);
		params.put("typeCode", typeCode);
		params.put("status", status);
		List<WorkOrder> lst = workOrderService
				.queryWorkOrderByCondition(params);
		if (CollectionUtils.isEmpty(lst))
			return false;
		return true;
	}

	@Override
	public boolean isExistsForPassport(Long orderId, String typeCode,
			String status) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orderId", orderId);
		params.put("typeCode", typeCode);
		params.put("status", status);
		List<WorkOrder> lst = workOrderService
				.queryWorkOrderByCondition(params);
		if (lst != null && lst.size() > 0)
			return true;
		return false;
	}
	public void setWorkGroupService(WorkGroupService workGroupService) {
		this.workGroupService = workGroupService;
	}
}
