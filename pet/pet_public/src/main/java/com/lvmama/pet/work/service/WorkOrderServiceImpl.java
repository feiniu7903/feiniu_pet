/*
 * 版权声明 .
 * 此文档的版权归上海景域文化传播有限公司所有
 * Powered By [AIPSEE-framework]
 */

package com.lvmama.pet.work.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.work.WorkGroupUser;
import com.lvmama.comm.pet.po.work.WorkOrder;
import com.lvmama.comm.pet.service.work.WorkOrderService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.pet.work.dao.WorkGroupUserDAO;
import com.lvmama.pet.work.dao.WorkOrderDAO;
import com.lvmama.pet.work.dao.WorkOrderTypeDAO;

/**
 * WorkOrder 的基本的业务流程逻辑的接口
 * 
 * @author ruanxiequan
 * @version 1.0
 * @since 1.0
 */

public class WorkOrderServiceImpl implements WorkOrderService {
	@Autowired
	private WorkOrderDAO workOrderDAO;
	@Autowired
	private WorkOrderTypeDAO workOrderTypeDAO;
	@Autowired
	private WorkGroupUserDAO workGroupUserDAO;

	@Override
	public Long insert(WorkOrder workOrder) {
		return workOrderDAO.insert(workOrder);
	}

	@Override
	public WorkOrder getWorkOrderById(Long id) {
		return workOrderDAO.getWorkOrderById(id);
	}

	@Override
	public int update(WorkOrder workOrder) {
		return workOrderDAO.update(workOrder);
	}

	/**
	 * 获取接收用户
	 * 
	 * @param receiveGroupUserId
	 *            接收任务的组用户id
	 * @param receiveGroupId
	 *            接收组id
	 * @param orderId
	 *            订单id
	 * @param mobileNumber
	 *            游客手机号
	 * @param createPermUserId
	 *            创建人id
	 * @param processUserId
	 *            处理人id
	 * @param typeName
	 *            工单类型
	 * @return WorkGroupUser
	 */
	public WorkGroupUser getFitUser(Long receiveGroupUserId,
			Long receiveGroupId, Long orderId, String mobileNumber,
			Long createPermUserId, Long processUserId,String typeCode) {
		WorkGroupUser wgu = null;
		if (receiveGroupUserId != null) {
			wgu = workGroupUserDAO.getWorkGroupUserById(receiveGroupUserId);
			if (null != wgu && "ONLINE".equalsIgnoreCase(wgu.getWorkStatus())) {
				return wgu;
			}
		}
		if (receiveGroupId != null) {
			List<WorkGroupUser> workUserList = workGroupUserDAO
					.queryWorkGroupUserByGroupId(receiveGroupId);
			List<WorkGroupUser> userFitList = new ArrayList<WorkGroupUser>();
			if (CollectionUtils.isNotEmpty(workUserList)) {
				List<WorkGroupUser> leaderList = new ArrayList<WorkGroupUser>();
				Iterator<WorkGroupUser> it = workUserList.iterator();
				while (it.hasNext()) {
					WorkGroupUser user = it.next();
					if ("true".equalsIgnoreCase(user.getLeader())) {
						if ("ONLINE".equalsIgnoreCase(user.getWorkStatus())) {
							leaderList.add(user);
						}
						it.remove();
					} else {
						if ("ONLINE".equalsIgnoreCase(user.getWorkStatus())) {
							userFitList.add(user);
						}
					}
				}
				if (CollectionUtils.isEmpty(userFitList) && CollectionUtils.isEmpty(leaderList)) {
					userFitList.addAll(workUserList);
				}
				if (orderId != null) { // 一单到底
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("orderId", orderId);
					params.put("workGroupId", receiveGroupId);
					wgu = getSpecialUser(
							workUserList,
							userFitList,
							workGroupUserDAO
									.queryWorkGroupUserByGroupIdAndOrderOrUserName(params),
							createPermUserId, processUserId);
					if (wgu != null)
						return wgu;
				}
				if (StringUtils.isNotEmpty(mobileNumber)) { // 一手机号到底
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("orderId", orderId);
					params.put("mobileNumber", mobileNumber);
					wgu = getSpecialUser(
							workUserList,
							userFitList,
							workGroupUserDAO
									.queryWorkGroupUserByGroupIdAndOrderOrUserName(params),
							createPermUserId, processUserId);
					if (wgu != null)
						return wgu;
				}
				long taskCount = 0L;
				long taskTotalCount = 0L;
				WorkGroupUser tempUser = null;
				for (int index = 0; index < userFitList.size(); index++) {
					WorkGroupUser user = userFitList.get(index);
					if (user.getWorkGroupUserId().equals(processUserId))
						continue;
					//未完成的工单数
					Long tempCount = workGroupUserDAO
							.queryWorkGroupUserTaskCountByUserId(user.getPermUserId());
					//当天的工单总数
					Long tempTotalCount = workGroupUserDAO
							.queryWorkGroupUserTaskTotalCountByUserId(user.getPermUserId(),DateUtil.formatDate(new Date(), "yyyy-MM-dd"));
					if (index == 0) {
						tempUser = user;
						taskCount = tempCount;
						taskTotalCount=tempTotalCount;
					}
					/*if (taskCount >= tempCount) {
						tempUser = user;
						taskCount = tempCount;
						if (taskCount == 0l)
							break;
					}*/
					//首先比较未完成工单的数量，优先分给未完成工单数量少的人
					//如果未完成的工单数量相等，则比较当天的工单总数，优先分给当天工单数量少的人
					if (taskCount >= tempCount) {
						if(StringUtils.isNotBlank(typeCode) && typeCode.equalsIgnoreCase("QDGD")){
							if(taskCount==tempCount && taskTotalCount>=tempTotalCount){
								tempUser = user;
								taskTotalCount = tempTotalCount;
								if (taskTotalCount == 0l)
									break;
							}else{
								tempUser = user;
								taskCount = tempCount;
							}
						}else{
							tempUser = user;
							taskCount = tempCount;
						}
						if (taskCount == 0l)
							break;
					}
				}
				wgu = tempUser;
				// 领导中平均分配
				if (wgu == null) {
					for (int index = 0; index < leaderList.size(); index++) {
						WorkGroupUser user = leaderList.get(index);
						if (user.getWorkGroupUserId().equals(processUserId))
							continue;
						Long tempCount = workGroupUserDAO
								.queryWorkGroupUserTaskCountByUserId(user.getPermUserId());
						if (index == 0) {
							tempUser = user;
							taskCount = tempCount;
						}
						if (taskCount >= tempCount) {
							tempUser = user;
							taskCount = tempCount;
							if (taskCount == 0l)
								break;
						}
					}
					wgu = tempUser;
				}
			}
		}
		return wgu;
	}

	/**
	 * 获取特殊接收用户（一单到底，一游客到底）
	 * 
	 * @param allUser
	 * @param userFitList
	 * @param proUserList
	 * @param createPermUserId
	 * @param processUserId
	 * @return
	 */
	private WorkGroupUser getSpecialUser(List<WorkGroupUser> allUser,
			List<WorkGroupUser> userFitList, List<WorkGroupUser> proUserList,
			Long createPermUserId, Long processUserId) {
		if (CollectionUtils.isNotEmpty(proUserList)) {
			for (WorkGroupUser temp : proUserList) {
				if (!temp.getPermUserId().equals(createPermUserId)
						&& !temp.getWorkGroupUserId().equals(processUserId)) {
					for (WorkGroupUser gu : allUser) {
						if (temp.getWorkGroupUserId().equals(
								gu.getWorkGroupUserId())
								&& "ONLINE"
										.equalsIgnoreCase(gu.getWorkStatus())) {
							return gu;
						}
					}
				}
			}
		}
		return null;
	}

	/**
	 * 系统重新分配获取接收用户
	 * 
	 * @param receiveGroupId
	 *            接收组id
	 * @param orderId
	 *            订单id
	 * @param mobileNumber
	 *            用户手机号
	 * @param createUserId
	 *            创建人id
	 * @return WorkGroupUser
	 */
	public WorkGroupUser getFitUser(Long receiveGroupId, Long orderId,
			String mobileNumber, Long createUserId) {
		WorkGroupUser wgu = null;
		Map<String, Object> guMap = new HashMap<String, Object>();
		guMap.put("workGroupId", receiveGroupId);
		guMap.put("workStatus", "ONLINE");
		List<WorkGroupUser> workUserList = workGroupUserDAO
				.queryWorkGroupUserByParams(guMap);
		WorkGroupUser createrUser = workGroupUserDAO
				.getWorkGroupUserById(createUserId);
		// 无人在线，返回null
		if (workUserList == null || workUserList.size() == 0)
			return null;
		// 移除发送人
		Iterator<WorkGroupUser> it = workUserList.iterator();
		if (createrUser != null) {
			while (it.hasNext()) {
				WorkGroupUser gu = it.next();
				if (gu.getPermUserId().equals(createrUser.getPermUserId())) {
					it.remove();
					break;
				}
			}
		}
		// 当还有其他用户在线的时候移除领导
		List<WorkGroupUser> leader = workGroupUserDAO
				.getGroupLeaderByGroupId(receiveGroupId);
		if (leader != null && workUserList.size() > leader.size()) {
			if (leader != null) {
				it = workUserList.iterator();
				while (it.hasNext()) {
					WorkGroupUser gu = it.next();
					for (WorkGroupUser gl : leader) {
						if (gu.getWorkGroupUserId().equals(
								gl.getWorkGroupUserId())) {
							it.remove();
						}
					}
				}
			}
		}
		// 无人在线（只有发送人在线），返回null
		if (workUserList.size() == 0)
			return null;
		// 只有一个满足分配条件的用户时，返回用户
		if (workUserList.size() == 1)
			return workUserList.get(0);

		if (orderId != null) { // 一单到底
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderId", orderId);
			params.put("workGroupId", receiveGroupId);
			wgu = getSpecialUser(
					workUserList,
					workGroupUserDAO
							.queryWorkGroupUserByGroupIdAndOrderOrUserName(params));
			if (wgu != null)
				return wgu;
		}
		if (StringUtils.isNotEmpty(mobileNumber)) { // 一手机号到底
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("orderId", orderId);
			params.put("mobileNumber", mobileNumber);
			wgu = getSpecialUser(
					workUserList,
					workGroupUserDAO
							.queryWorkGroupUserByGroupIdAndOrderOrUserName(params));
			if (wgu != null)
				return wgu;
		}
		long taskCount = 0L;
		for (int index = 0; index < workUserList.size(); index++) {
			WorkGroupUser user = workUserList.get(index);
			Long tempCount = workGroupUserDAO
					.queryWorkGroupUserTaskCountByUserId(user.getPermUserId());
			if (tempCount == 0l) {
				wgu = user;
				break;
			}
			if (index == 0) {
				wgu = user;
				taskCount = tempCount;
			}
			if (taskCount >= tempCount) {
				wgu = user;
				taskCount = tempCount;
				if (taskCount == 0l)
					break;
			}
		}
		return wgu;
	}

	/**
	 * 重新分配获取特殊接收用户（一单到底，一游客到底）
	 * 
	 * @param onlineUser
	 * @param proUserList
	 * @return
	 */
	private WorkGroupUser getSpecialUser(List<WorkGroupUser> onlineUser,
			List<WorkGroupUser> proUserList) {
		if (CollectionUtils.isNotEmpty(proUserList)) {
			for (WorkGroupUser temp : onlineUser) {
				for (WorkGroupUser gu : proUserList) {
					if (temp.getWorkGroupUserId().equals(
							gu.getWorkGroupUserId())) {
						return temp;
					}
				}
			}
		}
		return null;
	}

	@Override
	public List<WorkOrder> queryWorkOrderByCondition(Map<String, Object> params) {
		return workOrderDAO.queryWorkOrderByCondition(params);
	}
	@Override
	public List<WorkOrder> queryWorkOrderByParam(Map<String, Object> params){
		return workOrderDAO.queryWorkOrderByParam(params);
	}
	@Override
	public WorkOrder getWorkOrderByOrderId(Long orderId) {
		return workOrderDAO.getWorkOrderByOrderId(orderId);
	}
	
	public List<WorkOrder> queryReceiverByTypeCode(Map<String, Object> params){
		return workOrderDAO.queryReceiverByTypeCode(params);
	}
}
