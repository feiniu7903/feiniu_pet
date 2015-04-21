package com.lvmama.pet.sweb.work;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.po.work.*;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.user.UserUserProxy;
import com.lvmama.comm.pet.service.work.*;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.work.builder.WorkOrderFinishedBiz;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.*;

import java.util.*;

@Namespace("/work/order")
@Results({
        @Result(name = "invalid.token", location = "/WEB-INF/pages/back/work/repeat_submit.jsp"),
		@Result(name = "my_list", location = "/WEB-INF/pages/back/work/work_order_my_list.jsp"),
		@Result(name = "add", location = "/WEB-INF/pages/back/work/work_order_add.jsp"),
		@Result(name = "add_inner", location = "/WEB-INF/pages/back/work/work_order_add_inner.jsp"),
		@Result(name = "process", location = "/WEB-INF/pages/back/work/work_order_process.jsp"),
		@Result(name = "undo_list", location = "/WEB-INF/pages/back/work/work_task_undo_list.jsp"),
		@Result(name = "transition", location = "/WEB-INF/pages/back/work/work_task_add.jsp") })
public class WorkOrderAction extends BackBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8146372479548522361L;
	private WorkOrderService workOrderService;
	private WorkTaskService workTaskService;
	private ComLogService comLogService;
	private ProdProductService prodProductService;
	private UserUserProxy userUserProxy;
	private WorkOrderTypeService workOrderTypeService;
	private WorkDepartmentService workDepartmentService;
	private WorkGroupService workGroupService;
	private WorkGroupUserService workGroupUserService;
	private OrderService orderServiceProxy;

	private Long workOrderTypeId;
	private Long orderId;
	private Long productId;
	private String productName;
	private String workUserId;
	private String userName;
	private String userNameHid;
	private Long limitTime;
	private String content;
	private String mobileNumber;
	private Long receiveDept;
	private Long receiveGroup;
	private Long receiveUser;
	private String workStatus;
	private String taskContent;
	private Long receiveGroupValue;
	private Long createWorkGroup;
	private Long workTaskId;
	private String search;
	private Long maxResults;
	private int flag;
	private String sourceFlag;
	private String processLevel;

	private List<WorkDepartment> departmentList;
	private List<WorkGroup> workGroupList;
	private List<WorkOrderType> workOrderTypeList;
	private List<WorkGroupUser> workGroupUserList;
	private List<WorkGroupUser> createWorkGroupUserList;
	private List<WorkTask> workTaskList;
	private List<String> processLevelArr;
	private List<String> agentUsers;

	private WorkGroup workGroup;
	private WorkDepartment workDepartment;
	private WorkOrderType workOrderType;

	private String permId;
	private WorkTask workTask;
	private WorkOrder workOrder;
	private WorkGroupUser workGroupUser;
	private WorkGroupUser firstCreateUser;
	private WorkOrderFinishedBiz workOrderFinishedProxy;

	/**
	 * 新增工单入口
	 * 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action("add")
	public String add() {
		PermUser user = getSessionUser();
		if (user == null) {
			String lvsessionid = getRequestParameter("lvsessionid");
			Map<String, Object> map = null;
			if (lvsessionid != null) {
				map = (Map<String, Object>) MemcachedUtil.getInstance().get(
						lvsessionid);
				user = (PermUser) map.get(Constant.SESSION_BACK_USER);
			}
			if (map == null || user == null) {
				setRequestAttribute("error", "无法获取用户，请重新登录");
				return "error";
			}
			MemcachedUtil.getInstance().set(
					(String) getRequestAttribute(Constant.LV_SESSION_ID), map); // 设置session数据
			MemcachedUtil.getInstance().remove(lvsessionid);// 删除旧session
		}
		initWorkOrderTypeList();
		return "add";
	}

	/**
	 * 新增工单
	 * 
	 * @return
	 */
	@Action("add_inner")
	public String add_inner() {
		initWorkOrderTypeList();
		if (productId != null) {
			ProdProduct prodProduct = prodProductService
					.getProdProductById(productId);
			if (prodProduct != null) {
				productName = prodProduct.getProductName();
			} else {
				log.info("prodProduct is not exists，productId=" + productId);
				productId = null;
			}
		}
		if (workOrderTypeId != null) {
			workOrderType = workOrderTypeService
					.getWorkOrderTypeById(workOrderTypeId);
			workGroup = workGroupService.getWorkGroupById(workOrderType
					.getReceiverGroupId());
			workDepartment = workDepartmentService
					.getWorkDepartmentById(workGroup.getWorkDepartmentId());
			receiveGroup = workGroup.getWorkGroupId();
			receiveGroupValue = receiveGroup;
			content = workOrderType.getContent();
			receiveDept = workDepartment.getWorkDepartmentId();
			// add by zhangwengang 2013/08/08 特殊手工工单时间回复设置 start
			limitTime = this.getSpcLimitTime(workOrderType.getLimitTime(),
					workOrderType.getTypeCode(), "false");
			// add by zhangwengang 2013/08/08 特殊手工工单时间回复设置 end
			if (workOrderType.getReceiverEditable().equalsIgnoreCase("true")) {
				departmentList = workDepartmentService.getAllWorkDepartment();
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("workDepartmentId", receiveDept);
				params.put("valid", "true");
				workGroupList = workGroupService
						.getWorkGroupWithDepartment(params);
			} else {
				departmentList = new ArrayList<WorkDepartment>();
				workGroupList = new ArrayList<WorkGroup>();
				departmentList.add(workDepartment);
				workGroupList.add(workGroup);
			}
			if (workGroup.getValid().equalsIgnoreCase("true")) {
				workGroupUserList = onlineWorkGroupUser(receiveGroup);
			}
		}

		if (departmentList == null) {
			departmentList = new ArrayList<WorkDepartment>();
		}
		if (workGroupList == null) {
			workGroupList = new ArrayList<WorkGroup>();
		}
		if (workGroupUserList == null) {
			workGroupUserList = new ArrayList<WorkGroupUser>();
		}
		return "add_inner";
	}

	/**
	 * 保存工单
	 * 
	 * @return
	 */
    @Action(value = "save", interceptorRefs = {@InterceptorRef("token"),@InterceptorRef("defaultStack")})
	public String save() {
		workOrderType = workOrderTypeService
				.getWorkOrderTypeById(workOrderTypeId);
		WorkGroupUser receiveGroupUser = workOrderService.getFitUser(
				receiveUser, receiveGroup != null ? receiveGroup
						: receiveGroupValue, orderId, mobileNumber,
				getSessionUser().getUserId(), null,workOrderType.getTypeCode());
		if (receiveGroupUser != null) {
			WorkOrder workOrder = new WorkOrder();
			workOrder.setContent(content);
			workOrder.setCreateTime(new Date());
			workOrder.setCreateUserName(this.getSessionUserName());
			workOrder.setLimitTime(limitTime);
			// add by zhangwengang 2013/08/08 特殊手工工单，19：30-次日09:00提交的工单处理时间统一修改 start 暂时不用
			/*if (Constant.WORK_ORDER_TYPE_AND_SENDGROUP.CXXL
					.getWorkOrderTypeCode().equals(workOrderType.getTypeCode())
					|| Constant.WORK_ORDER_TYPE_AND_SENDGROUP.CXGD
							.getWorkOrderTypeCode().equals(
									workOrderType.getTypeCode())
					|| Constant.WORK_ORDER_TYPE_AND_SENDGROUP.CJXL
							.getWorkOrderTypeCode().equals(
									workOrderType.getTypeCode())
					|| Constant.WORK_ORDER_TYPE_AND_SENDGROUP.CJGD
							.getWorkOrderTypeCode().equals(
									workOrderType.getTypeCode())) {
				workOrder.setLimitTime(this.getSpcLimitTime(limitTime));
			} else {
				workOrder.setLimitTime(limitTime);
			}*/
			// add by zhangwengang 2013/08/08 特殊手工工单，19：30-次日9:00提交的工单处理时间统一修改 end 暂时不用
			workOrder.setOrderId(orderId);
			if (null != orderId && null == productId) {
				OrdOrder ordOrder = orderServiceProxy
						.queryOrdOrderByOrderId(orderId);
				if (null != ordOrder) {
					productId = ordOrder.getOrdOrderItemProds().get(0)
							.getProductId();
					workOrder.setProductId(productId);
				}
			}
			workOrder.setStatus(Constant.WORK_ORDER_STATUS.UNCOMPLETED
					.getCode());
			workOrder.setWorkOrderTypeId(workOrderTypeId);
			HashMap<String, Object> param = new HashMap<String, Object>();
			if (StringUtils.isNotBlank(mobileNumber)) {
				workOrder.setMobileNumber(mobileNumber.trim());
				param.put("mobileNumber", mobileNumber.trim());
				param.put("_endRow", "2");
				List<UserUser> userList = userUserProxy
						.queryUserUserKeyWordsLike(param);
				if (userList != null && userList.size() == 1) {
					workOrder.setUserName(userList.get(0).getUserName());
				}
			}
			if ("true".equals(processLevel)) {
				workOrder
						.setProcessLevel(Constant.WORK_ORDER_PROCESS_LEVEL.PROMPTLY
								.getCode());
			}
			String agentUserStr = "";
			if (CollectionUtils.isNotEmpty(agentUsers)
					&& "true".equals(workOrderType.getUseAgent())) {
				for (String au : agentUsers) {
					if (!agentUserStr.equals(""))
						agentUserStr += ",";
					agentUserStr += au;
				}
			}
			workOrder.setAgentUserName(agentUserStr);
			if (StringUtils.isNotEmpty(workOrderType.getUrlTemplate())) {
				Map<String, Object> data = new HashMap<String, Object>();
				if ("true".equalsIgnoreCase(workOrderType.getParamOrderId())) {
					data.put("order_id", orderId);
				}
				if ("true".equalsIgnoreCase(workOrderType.getParamProductId())) {
					data.put("product_id", productId);
				}
				if ("true".equalsIgnoreCase(workOrderType.getParamUserName())) {
					data.put("user_name", userNameHid);
				}
				try {
					workOrder.setUrl(StringUtil.composeMessage(
							workOrderType.getUrlTemplate(), data));
				} catch (Exception e) {
					log.error("work_order_type url_template="
							+ workOrderType.getUrlTemplate()
							+ " , replace data: " + data);
					log.error("replace work_order url error:", e);
				}
			}
			Long workOrderId = workOrderService.insert(workOrder);
			comLogService.insert("WORK_ORDER", workOrderId, workOrderId,
					getSessionUser().getUserName(), "WORK_ORDER",
					"WORK_ORDER_CREATED", "创建工单-->" + receiveGroupUser.getUserName(), "WORK_ORDER");

			WorkTask workTask = new WorkTask();
			workTask.setContent(taskContent);

			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", this.getSessionUser().getUserId());
			params.put("workGroupId", createWorkGroup);
			WorkGroupUser creater = workGroupUserService
					.getWorkGroupUserByPermUserAndGroup(params).get(0);
			workTask.setCreater(creater.getWorkGroupUserId());

			workTask.setCreateTime(new Date());
			workTask.setStatus(Constant.WORK_TASK_STATUS.UNCOMPLETED.getCode());
			workTask.setWorkOrderId(workOrderId);
			workTask.setReceiver(receiveGroupUser.getWorkGroupUserId());
			Long workTaskId = workTaskService.insert(workTask);
			refreshVersion(receiveGroupUser);

			comLogService.insert("WORK_ORDER", workTask.getWorkOrderId(),
					workTaskId, getSessionUser().getUserName(), "WORK_TASK",
					"WORK_TASK_CREATED", "创建任务-->" + receiveGroupUser.getUserName(), "WORK_TASK");
			flag = 1;
		} else {
			flag = -1;
			log.error("Unable to get to the receiveGroupUser! Please check receiveGroup and receiveUser,"
					+ "receiveGroup=" + receiveGroup != null ? receiveGroup
					: receiveGroupValue + ",receiveUser=" + receiveUser);
		}
		initWorkOrderTypeList();
		return "add";
	}

	/**
	 * 处理任务入口
	 * 
	 * @return
	 */
	@Action("process")
	public String process() {
		workTask = workTaskService.getWorkTaskById(workTaskId);
		workOrder = workOrderService
				.getWorkOrderById(workTask.getWorkOrderId());
		workOrderType = workOrderTypeService.getWorkOrderTypeById(workOrder
				.getWorkOrderTypeId());
		workGroupUser = workGroupUserService.getWorkGroupUserById(workTask
				.getReceiver());
		if (workOrder.getProductId() != null) {
			ProdProduct prod = prodProductService.getProdProduct(workOrder
					.getProductId());
			workOrder.setProductName(prod == null ? null : prod
					.getProductName());
		}

		workTask.setWorkOrder(workOrder);
		workTask.setWorkOrderType(workOrderType);
		workTask.setWorkGroupUser(workGroupUser);
		if (StringUtils.isNotEmpty(workOrder.getUserName())) {
			workOrder.setUserUser(userUserProxy
					.getUsersByMobOrNameOrEmailOrCard(workOrder.getUserName()));
		}

		WorkGroupUser creater = workGroupUserService
				.getWorkGroupUserById(workTask.getCreater());
		if (creater != null) {
			workTask.setCreaterName(creater.getUserName() + "/"
					+ creater.getRealName());
		}

		workGroupUser.setDepartmentName(workDepartmentService
				.getWorkDepartmentById(workGroupUser.getWorkDepartmentId())
				.getDepartmentName());
		workGroupUser.setWorkGroupName(workGroupService.getWorkGroupById(
				workGroupUser.getWorkGroupId()).getGroupName());

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("workOrderId", workOrder.getWorkOrderId());
		params.put("status", Constant.WORK_TASK_STATUS.COMPLETED);
		workTaskList = workTaskService.queryWorkTaskByParam(params);
		for (WorkTask item : workTaskList) {
			WorkGroupUser myWorkGroupUser = workGroupUserService
					.getWorkGroupUserById(item.getReceiver());
			WorkGroupUser myCreater = workGroupUserService
					.getWorkGroupUserById(item.getCreater());
			item.setWorkOrder(workOrder);
			item.setWorkOrderType(workOrderType);
			item.setWorkGroupUser(myWorkGroupUser);

			if (myCreater != null) {
				item.setCreaterName(myCreater.getUserName() + "/"
						+ myCreater.getRealName());
			}
			myWorkGroupUser.setDepartmentName(workDepartmentService
					.getWorkDepartmentById(
							myWorkGroupUser.getWorkDepartmentId())
					.getDepartmentName());
			myWorkGroupUser.setWorkGroupName(workGroupService.getWorkGroupById(
					myWorkGroupUser.getWorkGroupId()).getGroupName());
			if (item.getTaskSeqNo().equals(1L) && item.getCreater() != null) {
				firstCreateUser = workGroupUserService
						.getWorkGroupUserAllInfoById(item.getCreater());
			}
		}
		return "process";
	}

	/**
	 * 完成任务
	 */
	@Action("finish")
	public void finish() {
		JSONObject json = new JSONObject();
		String userName = getSessionUser().getUserName();
		Map<String, Object> retMap = workOrderFinishedProxy.finishWorkOrder(
				workTaskId, taskContent, userName);
		String status = (String) retMap.get("status");
		String msg = (String) retMap.get("msg");
		json.put("status", status);
		json.put("msg", msg);
		sendAjaxMsg(json.toString());
	}

	/**
	 * 回复任务
	 */
	@Action("reply")
	public void reply() {
		JSONObject json = new JSONObject();
		workTask = workTaskService.getWorkTaskById(workTaskId);
		if (workTask.getStatus().equals(
				Constant.WORK_TASK_STATUS.COMPLETED.getCode())) {
			json.put("status", "FAILED");
			json.put("msg", "任务状态错误");
			sendAjaxMsg(json.toString());
			log.warn("Unable to reply the work task! "
					+ "Please check work task status,work_order.id="
					+ workTask.getWorkOrderId() + ", work_task.seq="
					+ workTask.getTaskSeqNo() + ", work_task.status="
					+ workTask.getStatus());
			return;
		}
		Map<String, Object> firstWorkTaskParams = new HashMap<String, Object>();
		firstWorkTaskParams.put("workOrderId", workTask.getWorkOrderId());
		firstWorkTaskParams.put("taskSeqNo", 1);
		WorkTask firstWorkTask = workTaskService.queryWorkTaskByParam(
				firstWorkTaskParams).get(0);
		if (firstWorkTask.getCreater() == null) {
			json.put("status", "FAILED");
			json.put("msg", "无法回复，工单发起人错误");
			sendAjaxMsg(json.toString());
			log.warn("Unable to reply the work task! "
					+ "Please check work order creater,work_order.id="
					+ workTask.getWorkOrderId() + ", work_task.seq="
					+ workTask.getTaskSeqNo() + ", work_task.status="
					+ workTask.getStatus());
			return;
		}
		workTask.setCompleteTime(new Date());
		workTask.setStatus(Constant.WORK_TASK_STATUS.COMPLETED.getCode());
		workTask.setReplyContent(taskContent);
		workTaskService.update(workTask);
		refreshVersion(workGroupUserService.getWorkGroupUserById(workTask
				.getReceiver()));

		workOrder = workOrderService
				.getWorkOrderById(workTask.getWorkOrderId());
		WorkTask newWorkTask = new WorkTask();
		newWorkTask.setContent(taskContent);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", this.getSessionUser().getUserId());
		params.put(
				"workGroupId",
				workGroupUserService.getWorkGroupUserById(
						workTask.getReceiver()).getWorkGroupId());
		newWorkTask.setCreater(workGroupUserService
				.getWorkGroupUserByPermUserAndGroup(params).get(0)
				.getWorkGroupUserId());
		// 判断是否回复给备选人
		WorkGroupUser workGroupUser = workGroupUserService
				.getWorkGroupUserById(firstWorkTask.getCreater());
		if (workGroupUser != null
				&& !"ONLINE".equals(workGroupUser.getWorkStatus())
				&& StringUtils.isNotEmpty(workOrder.getAgentUserName())) {
			String[] permUserNames = workOrder.getAgentUserName().split(",");
			long tempCount = 0;
			WorkGroupUser tempUser = null;
			for (int i = 0; i < permUserNames.length; i++) {
				String un = permUserNames[i];
				Map<String, Object> parmTmp = new HashMap<String, Object>();
				parmTmp.put("userName", un);
				parmTmp.put("workStatus", "ONLINE");
				parmTmp.put("workGroupId", workGroupUser.getWorkGroupId());
				List<WorkGroupUser> agentUserList = workGroupUserService
						.getWorkGroupUserByPermUserAndGroup(parmTmp);
				if (CollectionUtils.isNotEmpty(agentUserList)) {
					long count = workGroupUserService
							.queryWorkGroupUserTaskCountByUserId(agentUserList
									.get(0).getWorkGroupUserId());
					if (count == 0l) {
						tempUser = agentUserList.get(0);
						break;
					}
					if (tempUser == null) {
						tempUser = agentUserList.get(0);
						tempCount = count;
					} else {
						if (tempCount > count) {
							tempUser = agentUserList.get(0);
							tempCount = count;
						}
					}
				}
			}
			if (tempUser != null) {
				newWorkTask.setReceiver(tempUser.getWorkGroupUserId());
			}
		}
		if (newWorkTask.getReceiver() == null) {
			newWorkTask.setReceiver(firstWorkTask.getCreater());
		}
		newWorkTask.setStatus(Constant.WORK_TASK_STATUS.UNCOMPLETED.getCode());
		newWorkTask.setWorkOrderId(workTask.getWorkOrderId());
		newWorkTask.setCreateTime(new Date());
		long ret = workTaskService.insert(newWorkTask);
		comLogService.insert("WORK_ORDER", workTask.getWorkOrderId(),
				workTask.getWorkTaskId(), getSessionUser().getUserName(),
				"WORK_TASK", "WORK_TASK_REPLY", "回复任务", "WORK_TASK");
		refreshVersion(workGroupUserService.getWorkGroupUserById(workTask
				.getReceiver()));
		if (ret > 0) {
			json.put("status", "SUCCESS");
			sendAjaxMsg(json.toString());
		} else {
			json.put("status", "FAILED");
			json.put("msg", "回复任务失败");
			sendAjaxMsg(json.toString());
		}
	}

	/**
	 * 转单入口
	 * 
	 * @return
	 */
	@Action("transition")
	public String transition() {
		workTask = workTaskService.getWorkTaskById(workTaskId);
		workGroupUser = workGroupUserService.getWorkGroupUserById(workTask
				.getReceiver());
		workGroup = workGroupService.getWorkGroupById(workGroupUser
				.getWorkGroupId());
		receiveGroup = workGroupUser.getWorkGroupId();
		receiveDept = workGroupUser.getWorkDepartmentId();
		workOrderType = workOrderTypeService
				.getWorkOrderTypeById(workOrderService.getWorkOrderById(
						workTask.getWorkOrderId()).getWorkOrderTypeId());

		departmentList = workDepartmentService.getAllWorkDepartment();

		Map<String, Object> params = new HashMap<String, Object>();
		params.put("workDepartmentId", workGroupUser.getWorkDepartmentId());
		params.put("valid", "true");
		workGroupList = workGroupService.getWorkGroupWithDepartment(params);

		if (workGroup.getValid().equalsIgnoreCase("true")) {
			workGroupUserList = onlineWorkGroupUser(receiveGroup);
		}
		if (departmentList == null) {
			departmentList = new ArrayList<WorkDepartment>();
		}
		if (workGroupList == null) {
			workGroupList = new ArrayList<WorkGroup>();
		}
		if (workGroupUserList == null) {
			workGroupUserList = new ArrayList<WorkGroupUser>();
		}
		return "transition";
	}

	/**
	 * 转单->创建新任务
	 * 
	 * @return
	 */
	@Action("create_task")
	public void create_task() {
		JSONObject json = new JSONObject();
		json.put("sourceFlag", sourceFlag);
		workTask = workTaskService.getWorkTaskById(workTaskId);
		if (workTask.getStatus().equals(
				Constant.WORK_TASK_STATUS.COMPLETED.getCode())) {
			json.put("status", "FAILED");
			json.put("msg", "任务状态错误");
			sendAjaxMsg(json.toString());
			log.warn("Unable to transition the work task! "
					+ "Please check work task status,work_order.id="
					+ workTask.getWorkOrderId() + ", work_task.seq="
					+ workTask.getTaskSeqNo() + ", work_task.status="
					+ workTask.getStatus());
			return;
		}
		WorkGroupUser receiveGroupUser = workOrderService.getFitUser(
				receiveUser, receiveGroup, orderId, mobileNumber,
				getSessionUser().getUserId(), workTask.getReceiver(),null);
		if (receiveGroupUser == null) {
			json.put("status", "FAILED");
			json.put("msg", "未找到合适的接收人");
			sendAjaxMsg(json.toString());
			log.warn("Unable to get the receiveGroupUser! "
					+ "Please check receiveGroup and receiveUser,receiveGroup="
					+ receiveGroup + ",receiveUser=" + receiveUser
					+ ", work_order.id=" + workTask.getWorkOrderId()
					+ ", work_task.seq=" + workTask.getTaskSeqNo()
					+ ", work_task.status=" + workTask.getStatus());
			return;
		}
		workTask.setCompleteTime(new Date());
		workTask.setStatus(Constant.WORK_TASK_STATUS.COMPLETED.getCode());
		workTask.setReplyContent(taskContent);
		workTaskService.update(workTask);
		refreshVersion(workGroupUserService.getWorkGroupUserById(workTask
				.getReceiver()));
		comLogService.insert("WORK_ORDER", workTask.getWorkOrderId(),
				workTask.getWorkTaskId(), getSessionUser().getUserName(),
				"WORK_TASK", "WORK_TASK_TRANSITION", "(转出)转单"
						+ workTask.getWorkGroupUser().getUserName() + "-->"
						+ receiveGroupUser.getUserName(), "WORK_TASK");

		WorkTask newWorkTask = new WorkTask();
		newWorkTask.setContent(taskContent);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("userId", this.getSessionUser().getUserId());
		params.put(
				"workGroupId",
				workGroupUserService.getWorkGroupUserById(
						workTask.getReceiver()).getWorkGroupId());
		newWorkTask.setCreater(workGroupUserService
				.getWorkGroupUserByPermUserAndGroup(params).get(0)
				.getWorkGroupUserId());
		newWorkTask.setReceiver(receiveGroupUser.getWorkGroupUserId());
		newWorkTask.setStatus(Constant.WORK_TASK_STATUS.UNCOMPLETED.getCode());
		newWorkTask.setWorkOrderId(workTask.getWorkOrderId());
		newWorkTask.setCreateTime(new Date());
		Long newWorkTaskId = workTaskService.insert(newWorkTask);
		comLogService.insert("WORK_ORDER", newWorkTask.getWorkOrderId(),
				newWorkTaskId, getSessionUser().getUserName(), "WORK_TASK",
				"WORK_TASK_TRANSITION", "(转入)转单"
						+ workTask.getWorkGroupUser().getUserName() + "-->"
						+ receiveGroupUser.getUserName(), "WORK_TASK");
		refreshVersion(receiveGroupUser);
		json.put("status", "SUCCESS");
		sendAjaxMsg(json.toString());
	}

	/**
	 * 
	 */
	@Action("changeProcessLevel")
	public void changeProcessLevel() {
		if (CollectionUtils.isNotEmpty(processLevelArr)) {
			for (String pl : processLevelArr) {
				String[] arr = pl.split(",");
				workOrder = workOrderService.getWorkOrderById(Long
						.parseLong(arr[0]));
				if (workOrder.getProcessLevel() == null) {
					workOrder.setProcessLevel(arr[1]);
				} else {
					if (!workOrder.getProcessLevel().equals(arr[1])) {
						workOrder
								.setProcessLevel(Constant.WORK_ORDER_PROCESS_LEVEL.REPEAT_PROMPTLY
										.getCode());
					}
				}
				workOrderService.update(workOrder);
				// 刷新版本号
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("workOrderId", workOrder.getWorkOrderId());
				params.put("status",
						Constant.WORK_TASK_STATUS.UNCOMPLETED.getCode());
				List<WorkTask> workTaskList = workTaskService
						.queryWorkTaskByParam(params);
				if (workTaskList != null && workTaskList.size() > 0) {
					WorkGroupUser workGroupUser = workGroupUserService
							.getWorkGroupUserById(workTaskList.get(0)
									.getReceiver());
					if (workGroupUser != null)
						refreshVersion(workGroupUser);
				}
			}
		}
		JSONObject json = new JSONObject();
		json.put("status", "SUCCESS");
		sendAjaxMsg(json.toString());
	}

	/**
	 * 通过部门获取组织
	 */
	@Action("getWorkGroupByDeptId")
	public void getWorkGroupByDeptId() {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("workDepartmentId", receiveDept);
		params.put("valid", "true");
		workGroupList = workGroupService.getWorkGroupWithDepartment(params);
		JSONArray arr = new JSONArray();
		for (WorkGroup wg : workGroupList) {
			JSONObject json = new JSONObject();
			json.put("workGroupId", wg.getWorkGroupId());
			json.put("groupName", wg.getGroupName());
			arr.add(json);
		}
		JSONOutput.writeJSON(this.getResponse(), arr);
	}

	/**
	 * 通过组织获取在线用户
	 */
	@Action("getWorkGroupUserByGroupId")
	public void getWorkGroupUserByGroupId() {
		Map<String, Object> params = new HashMap<String, Object>();
		JSONArray arr = new JSONArray();
		params.put("workGroupId", receiveGroup);
		if (search != null && !"".equals(search)) {
			params.put("userName", "%" + search + "%");
		}
		if (StringUtils.isNotEmpty(workStatus) && !"ALL".equals(workStatus)) {
			params.put("workStatus", workStatus);
		}
		workGroupUserList = workGroupUserService
				.queryWorkGroupUserByParams(params);

		for (WorkGroupUser wgu : workGroupUserList) {
			JSONObject json = new JSONObject();
			json.put("workGroupUserId", wgu.getWorkGroupUserId());
			json.put("leader", wgu.getLeader());
			json.put("realName", wgu.getRealName());
			json.put("userName", wgu.getUserName());
			json.put("text", wgu.getUserName());// 工单监控中的在线用户
			arr.add(json);
		}
		JSONOutput.writeJSON(this.getResponse(), arr);
	}

	/**
	 * 模糊查询产品
	 */
	@Action("queryProductList")
	public void queryProductList() {
		JSONArray array = new JSONArray();
		Map<String, Object> searchConds = new HashMap<String, Object>();
		if (StringUtils.isNotEmpty(search)) {
			searchConds.put("sProductName", search);
			searchConds.put("_startRow", 0L);
			searchConds.put("_endRow", 10L);
			List<ProdProduct> productList = prodProductService
					.selectProductByParms(searchConds);
			if (CollectionUtils.isNotEmpty(productList)) {
				for (ProdProduct pp : productList) {
					JSONObject obj = new JSONObject();
					obj.put("id", pp.getProductId());
					obj.put("text", pp.getProductName());
					array.add(obj);
				}
			}
		}
		JSONOutput.writeJSON(getResponse(), array);
	}

	/**
	 * 模糊查询用户
	 */
	@Action("queryUserList")
	public void queryUserList() {
		JSONArray array = new JSONArray();
		HashMap<String, Object> param = new HashMap<String, Object>();
		if (search != null && !"".equals(search)) {
			param.put("mobileNumber", search);
			param.put("_endRow", "10");
			List<UserUser> userList = userUserProxy
					.queryUserUserKeyWordsLike(param);
			if (CollectionUtils.isNotEmpty(userList)) {
				for (UserUser uu : userList) {
					JSONObject obj = new JSONObject();
					obj.put("id", uu.getMobileNumber());
					obj.put("text", uu.getMobileNumber());
					obj.put("extra", "用户名:" + uu.getUserName());
					obj.put("data", uu.getUserName());
					array.add(obj);
				}
			}
		}
		JSONOutput.writeJSON(getResponse(), array);
	}

	/**
	 * 获取用户状态信息
	 */
	@Action("getWorkGroupUserInfo")
	public void getWorkGroupUserInfo() {
		JSONObject json = new JSONObject();
		WorkGroupUser workGroupUser = workGroupUserService
				.getWorkGroupUserById(Long.parseLong(workUserId));
		if (workGroupUser != null) {
			json.put("flag", "SUCCESS");
			json.put("workGroupUserId", workGroupUser.getWorkGroupUserId());
			json.put("workStatus", workGroupUser.getWorkStatus());
		} else {
			json.put("flag", "FALSE");
		}
		sendAjaxMsg(json.toString());
	}

	/**
	 * 刷新任务版本
	 * 
	 * @param workGroupUser
	 */
	private void refreshVersion(WorkGroupUser workGroupUser) {
		MemcachedUtil.getInstance().set(
				Constant.KEY_WORK_TASK_DATA_VERSION
						+ workGroupUser.getUserName(),
				System.currentTimeMillis());
	}

	private void initWorkOrderTypeList() {
		workOrderTypeList = workOrderTypeService
				.queryWorkOrderTypeByPermUserId(this.getSessionUser()
						.getUserId());
		if (workOrderTypeList != null) {
			Iterator<WorkOrderType> it = workOrderTypeList.iterator();
			while (it.hasNext()) {
				if ("true".equals(it.next().getSystem())) {
					it.remove();
				}
			}
		}
		if (workOrderTypeId != null) {
			workOrderType = workOrderTypeService
					.getWorkOrderTypeById(workOrderTypeId);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("userId", this.getSessionUser().getUserId());
			params.put("workDepartmentId",
					workOrderType.getCreatorDepartmentId());
			params.put("workGroupId", workOrderType.getSendGroupId());
			List<WorkGroupUser> tmpWorkGroupUserList = workGroupUserService
					.getWorkGroupUserByPermUserAndGroup(params);
			createWorkGroupUserList = new ArrayList<WorkGroupUser>();
			if (CollectionUtils.isNotEmpty(tmpWorkGroupUserList)) {
				// mod by zhangwengang 2013/05/13去除组织类型为不可用的记录 start
				for (int i = 0; i < tmpWorkGroupUserList.size(); i++) {
					WorkGroupUser gu = tmpWorkGroupUserList.get(i);
					WorkGroup wg = workGroupService.getWorkGroupById(gu
							.getWorkGroupId());
					if (null != wg) {
						if ("true".equals(wg.getValid())) {
							gu.setWorkGroupName(wg.getGroupName());
							createWorkGroupUserList.add(gu);
						}
					}
				}
				// mod by zhangwengang 2013/05/13去除组织类型为不可用的记录 end
			}
		}
		if (CollectionUtils.isEmpty(createWorkGroupUserList)) {
			createWorkGroupUserList = new ArrayList<WorkGroupUser>();
		}
		if (CollectionUtils.isEmpty(workOrderTypeList)) {
			workOrderTypeList = new ArrayList<WorkOrderType>();
		}
	}

	private List<WorkGroupUser> onlineWorkGroupUser(Long groupId) {
		List<WorkGroupUser> lst = workGroupUserService
				.queryWorkGroupUserByGroupId(groupId);
		// add by zhangwengang 交接班工单显示全部人员 2013-06-05 add
		if (null != workOrderType
				&& !Constant.WORK_ORDER_TYPE_AND_SENDGROUP.JJBGD
						.getWorkOrderTypeCode().equals(
								workOrderType.getTypeCode())) {
			Iterator<WorkGroupUser> it = lst.iterator();
			while (it.hasNext()) {
				WorkGroupUser e = it.next();
				if (!"ONLINE".equalsIgnoreCase(e.getWorkStatus())) {
					it.remove();
				}
			}
		}
		// add by zhangwengang 特殊工单显示全部人员 2013-06-05 end
		return lst;
	}

	/**
	 * 特殊工单回复时间初始化
	 * */
	private Long getSpcLimitTime(Long paraLimitTime, String workOrderTypeCode, String paraProcessLevel) {
		if (Constant.WORK_ORDER_TYPE_AND_SENDGROUP.CXXL.getWorkOrderTypeCode()
				.equals(workOrderTypeCode)
				|| Constant.WORK_ORDER_TYPE_AND_SENDGROUP.CXGD
						.getWorkOrderTypeCode().equals(workOrderTypeCode)
				|| Constant.WORK_ORDER_TYPE_AND_SENDGROUP.CJXL
						.getWorkOrderTypeCode().equals(workOrderTypeCode)
				|| Constant.WORK_ORDER_TYPE_AND_SENDGROUP.CJGD
						.getWorkOrderTypeCode().equals(workOrderTypeCode)) {
			paraLimitTime = getSpcLimitTime(paraLimitTime, paraProcessLevel);
		}
		return paraLimitTime;
	}
	
	private Long getSpcLimitTime(Long paraLimitTime, String paraProcessLevel) {
		Calendar cal = Calendar.getInstance();
		String strDate = DateUtil.formatDate(cal.getTime(), "yyyy-MM-dd");
		String strTime = DateUtil.formatDate(cal.getTime(), "HH:mm");
		if ("true".equals(paraProcessLevel)) {
			strDate += " 10:00:00";
		} else {
			strDate += " 11:00:00";
		}
		if (strTime.compareTo("19:30") > 0) {
			Date endDate = DateUtil.stringToDate(strDate, "yyyy-MM-dd HH:mm:ss");
			Long needAddMin = DateUtil.getMinBetween(cal.getTime(),
					DateUtil.dsDay_Date(endDate, 1));
			paraLimitTime = needAddMin;
		} else if (strTime.compareTo("09:00") < 0) {
			Date endDate = DateUtil.stringToDate(strDate, "yyyy-MM-dd HH:mm:ss");
			Long needAddMin = DateUtil.getMinBetween(cal.getTime(), endDate);
			paraLimitTime = needAddMin;
		} else {
			if ("true".equals(paraProcessLevel)) {
				paraLimitTime = 30L;
			}
		}
		return paraLimitTime;
	}
	
	/**
	 * 通过组织获取在线用户
	 */
	@Action("setupLimitTime")
	public void setupLimitTime() {
		Long time = getSpcLimitTime(0L, "true");
		JSONObject json = new JSONObject();
		json.put("time", time);
		sendAjaxMsg(json.toString());
	}

	public WorkOrderService getWorkOrderService() {
		return workOrderService;
	}

	public void setWorkOrderService(WorkOrderService workOrderService) {
		this.workOrderService = workOrderService;
	}

	public Long getWorkOrderTypeId() {
		return workOrderTypeId;
	}

	public void setWorkOrderTypeId(Long workOrderTypeId) {
		this.workOrderTypeId = workOrderTypeId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Long getLimitTime() {
		return limitTime;
	}

	public void setLimitTime(Long limitTime) {
		this.limitTime = limitTime;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
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

	public Long getReceiveUser() {
		return receiveUser;
	}

	public void setReceiveUser(Long receiveUser) {
		this.receiveUser = receiveUser;
	}

	public String getTaskContent() {
		return taskContent;
	}

	public void setTaskContent(String taskContent) {
		this.taskContent = taskContent;
	}

	public List<WorkDepartment> getDepartmentList() {
		return departmentList;
	}

	public void setDepartmentList(List<WorkDepartment> departmentList) {
		this.departmentList = departmentList;
	}

	public List<WorkGroup> getWorkGroupList() {
		return workGroupList;
	}

	public void setWorkGroupList(List<WorkGroup> workGroupList) {
		this.workGroupList = workGroupList;
	}

	public List<WorkOrderType> getWorkOrderTypeList() {
		return workOrderTypeList;
	}

	public void setWorkOrderTypeList(List<WorkOrderType> workOrderTypeList) {
		this.workOrderTypeList = workOrderTypeList;
	}

	public void setWorkOrderTypeService(
			WorkOrderTypeService workOrderTypeService) {
		this.workOrderTypeService = workOrderTypeService;
	}

	public void setWorkDepartmentService(
			WorkDepartmentService workDepartmentService) {
		this.workDepartmentService = workDepartmentService;
	}

	public void setWorkGroupService(WorkGroupService workGroupService) {
		this.workGroupService = workGroupService;
	}

	public WorkOrderType getWorkOrderType() {
		return workOrderType;
	}

	public void setWorkOrderType(WorkOrderType workOrderType) {
		this.workOrderType = workOrderType;
	}

	public WorkGroup getWorkGroup() {
		return workGroup;
	}

	public void setWorkGroup(WorkGroup workGroup) {
		this.workGroup = workGroup;
	}

	public WorkDepartment getWorkDepartment() {
		return workDepartment;
	}

	public void setWorkDepartment(WorkDepartment workDepartment) {
		this.workDepartment = workDepartment;
	}

	public List<WorkGroupUser> getWorkGroupUserList() {
		return workGroupUserList;
	}

	public void setWorkGroupUserList(List<WorkGroupUser> workGroupUserList) {
		this.workGroupUserList = workGroupUserList;
	}

	public void setWorkGroupUserService(
			WorkGroupUserService workGroupUserService) {
		this.workGroupUserService = workGroupUserService;
	}

	public void setWorkTaskService(WorkTaskService workTaskService) {
		this.workTaskService = workTaskService;
	}

	public Long getReceiveGroupValue() {
		return receiveGroupValue;
	}

	public void setReceiveGroupValue(Long receiveGroupValue) {
		this.receiveGroupValue = receiveGroupValue;
	}

	public Long getWorkTaskId() {
		return workTaskId;
	}

	public void setWorkTaskId(Long workTaskId) {
		this.workTaskId = workTaskId;
	}

	public WorkTask getWorkTask() {
		return workTask;
	}

	public void setWorkTask(WorkTask workTask) {
		this.workTask = workTask;
	}

	public WorkOrder getWorkOrder() {
		return workOrder;
	}

	public void setWorkOrder(WorkOrder workOrder) {
		this.workOrder = workOrder;
	}

	public WorkGroupUser getWorkGroupUser() {
		return workGroupUser;
	}

	public void setWorkGroupUser(WorkGroupUser workGroupUser) {
		this.workGroupUser = workGroupUser;
	}

	public List<WorkTask> getWorkTaskList() {
		return workTaskList;
	}

	public Long getMaxResults() {
		return maxResults;
	}

	public void setMaxResults(Long maxResults) {
		this.maxResults = maxResults;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public String getSearch() {
		return search;
	}

	public void setSearch(String search) {
		this.search = search;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getUserNameHid() {
		return userNameHid;
	}

	public void setUserNameHid(String userNameHid) {
		this.userNameHid = userNameHid;
	}

	public void setUserUserProxy(UserUserProxy userUserProxy) {
		this.userUserProxy = userUserProxy;
	}

	public String getWorkUserId() {
		return workUserId;
	}

	public void setWorkUserId(String workUserId) {
		this.workUserId = workUserId;
	}

	public Long getCreateWorkGroup() {
		return createWorkGroup;
	}

	public void setCreateWorkGroup(Long createWorkGroup) {
		this.createWorkGroup = createWorkGroup;
	}

	public List<WorkGroupUser> getCreateWorkGroupUserList() {
		return createWorkGroupUserList;
	}

	public void setWorkStatus(String workStatus) {
		this.workStatus = workStatus;
	}

	public int getFlag() {
		return flag;
	}

	public void setFlag(int flag) {
		this.flag = flag;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public String getSourceFlag() {
		return sourceFlag;
	}

	public void setSourceFlag(String sourceFlag) {
		this.sourceFlag = sourceFlag;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public void setMobileNumber(String mobile) {
		this.mobileNumber = mobile;
	}

	public List<String> getAgentUsers() {
		return agentUsers;
	}

	public void setAgentUsers(List<String> agentUsers) {
		this.agentUsers = agentUsers;
	}

	public String getProcessLevel() {
		return processLevel;
	}

	public void setProcessLevel(String processLevel) {
		this.processLevel = processLevel;
	}

	public WorkGroupUser getFirstCreateUser() {
		return firstCreateUser;
	}

	public void setProcessLevelArr(List<String> processLevelStr) {
		this.processLevelArr = processLevelStr;
	}

	public String getPermId() {
		return permId;
	}

	public void setPermId(String permId) {
		this.permId = permId;
	}

	public WorkOrderFinishedBiz getWorkOrderFinishedProxy() {
		return workOrderFinishedProxy;
	}

	public void setWorkOrderFinishedProxy(
			WorkOrderFinishedBiz workOrderFinishedProxy) {
		this.workOrderFinishedProxy = workOrderFinishedProxy;
	}

	public OrderService getOrderServiceProxy() {
		return orderServiceProxy;
	}

	public void setOrderServiceProxy(OrderService orderServiceProxy) {
		this.orderServiceProxy = orderServiceProxy;
	}

}
