package com.lvmama.back.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.lvmama.comm.bee.po.ebooking.EbkTask;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderMemo;
import com.lvmama.comm.bee.service.ebooking.EbkTaskService;
import com.lvmama.comm.bee.service.ord.OrderService;
import com.lvmama.comm.jms.Message;
import com.lvmama.comm.jms.MessageProcesser;
import com.lvmama.comm.vo.Constant;

public class EbkResourceConfirmTaskProcesser implements MessageProcesser {

	protected Logger log = Logger.getLogger(this.getClass());
	private EbkTaskService ebkTaskService;
	private OrderService orderServiceProxy;
	/**
	 * 根据消息生成EBK资源确认任务
	 */
	@Override
	public void process(Message message) {
		if(message.isOrderCancelMsg()) {
			processCancel(message);
		} else if(message.isOrderCreateMsg()) {
			processCreate(message);
		} else if(message.isOrderPaymentMsg()) {
			processPayment(message);
		} else if(message.isOrderApproveMsg()) {
			processApprove(message);
		}
	}
	/**
	 * 订单创建消息处理
	 * @author: ranlongfei 2013-1-9 上午11:53:11
	 * @param message
	 */
	private void processCreate(Message message) {
		log.debug("receive order create message " + message.getObjectId());
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
		//强制需预授权 ，不能进入
		if(order == null || "true".equals(order.getNeedPrePay())) {
			return;
		}
		createEbkTask(message, order);
//		//如果需要资源审核并且是已经资源确认的订单，更改状态
//		//保留房
//		if(order.isNeedResourceConfirm()&&order.isPayToLvmama()&&order.isApprovePass()){
//			processApprove(message);
//		}
	}
	/**
	 * 订单支付消息处理
	 * @author: ranlongfei 2013-1-9 上午11:53:11
	 * @param message
	 */
	private void processPayment(Message message) {
		log.debug("receive order payment message " + message.getObjectId());
		OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
		//不强制需预授权 ， 不能进入
		if(order == null || !"true".equals(order.getNeedPrePay())) {
			return;
		}
		createEbkTask(message, order);
	}
	/**
	 * 订单取消消息处理
	 * <br>1,如果还没有任务，则不生成任务
	 * <br>2,如果是存在已确认的任务，则生成取消任务
	 * <br>3,如果是存在未确认的任务，则将原任务的状态取消
	 * @author: ranlongfei 2013-1-9 上午11:49:58
	 * @param message
	 */
	private void processCancel(Message message) {
		log.debug("receive order cancel message " + message.getObjectId());
		////TODO:修改为新的订单取消生成任务
		/*OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
		if(order == null) {
			return;
		}
		List<EbkTask> taskList = ebkTaskService.findEbkTaskByOrderId(message.getObjectId());
		//没有确认任务，也不存在取消任务
		if(taskList == null || taskList.size() <= 0) {
			return;
		}
		// 创建的任务状态是未处理，那此任务直接取消
		List<EbkTask> updateEbkTaskList = new ArrayList<EbkTask>();
		Set<Long> orderTask = new HashSet<Long>();
		for(EbkTask t : taskList) {
			if(Constant.EBK_TASK_TYPE.RESOURCE_CONFIRM.name().equals(t.getTaskType()) && Constant.EBK_TASK_STATUS.CREATE.name().equals(t.getStatus())) {
				t.setStatus(Constant.EBK_TASK_STATUS.CANCEL.name());
				t.setOrderUpdate("false");
				updateEbkTaskList.add(t);
				orderTask.add(t.getOrdItemMetaId());
			}
			if("true".equals(t.getOrderUpdate())) {
				t.setOrderUpdate("false");
				updateEbkTaskList.add(t);
			}
		}
		
		// 创建任务
		List<OrdOrderItemMeta> metas = order.getAllOrdOrderItemMetas();
		List<EbkTask> newEbkTaskList = new ArrayList<EbkTask>();
		for(OrdOrderItemMeta meta : metas) {
			// 不含直接取消任务
			if(orderTask.contains(meta.getOrderItemMetaId())) {
				EbkTask updateTask = findEbkTaskWithList(updateEbkTaskList, meta.getOrderItemMetaId());
				if(updateTask!=null && Constant.ORDER_RESOURCE_STATUS.LACK.name().equals(meta.getResourceStatus())){
					updateTask.setStatus(Constant.EBK_TASK_STATUS.REJECT.name());
				}
				continue;
			}
			List<EbkTask> mTaskList = ebkTaskService.findEbkTaskByOrdItemMetaId(meta.getOrderItemMetaId());
			if(mTaskList != null && mTaskList.size() > 0) {
				// 最新一个
				EbkTask last = mTaskList.get(0);
				if(Constant.EBK_TASK_STATUS.REJECT.name().equals(last.getStatus())) {
					// 没有不接受预定的订单不用生成取消单
					continue;
				}
			}
			EbkTask task = fillEbkTaskInfo(meta, Constant.EBK_TASK_TYPE.CANCEL_CONFIRM.name());
			if(task != null) {
				newEbkTaskList.add(task);
			}
		}
		if(updateEbkTaskList.size() > 0) {
			this.ebkTaskService.updateList(updateEbkTaskList);
		}
		if(newEbkTaskList.size() > 0) {
			this.ebkTaskService.insertList(newEbkTaskList);
		}*/
	}
	private EbkTask findEbkTaskWithList(List<EbkTask> updateEbkTaskList, Long orderItemMetaId) {
		//TODO:修改为新的EBK任务查询
		/*for(EbkTask t : updateEbkTaskList) {
			if(t.getOrdItemMetaId().equals(orderItemMetaId)) {
				return t;
			}
		}*/
		return null;
	}
	/**
	 * 订单资源确认消息处理
	 * 
	 * @author: ranlongfei 2013-1-9 下午1:38:51
	 * @param message
	 */
	private void processApprove(Message message) {
		log.debug("receive order Approve message " + message.getObjectId());
		//TODO:修改为新的订单资源确认消息处理
		/*OrdOrder order = orderServiceProxy.queryOrdOrderByOrderId(message.getObjectId());
		if(order == null) {
			return;
		}
		List<EbkTask> taskList = ebkTaskService.findEbkTaskByOrderId(message.getObjectId());
		//没有确认任务，也不存在取消任务
		if(taskList == null || taskList.size() <= 0) {
			return;
		}
		// 创建的任务状态是未处理，那此任务直接取消
		List<EbkTask> updateEbkTaskList = new ArrayList<EbkTask>();
		Set<Long> orderTask = new HashSet<Long>();
		for(EbkTask t : taskList) {
			if(!Constant.EBK_TASK_TYPE.RESOURCE_CONFIRM.name().equals(t.getTaskType())) {
				continue;
			}
			if(!Constant.EBK_TASK_STATUS.CREATE.name().equals(t.getStatus())) {
				continue;
			}
			OrdOrderItemMeta item = orderServiceProxy.queryOrdOrderItemMetaBy(t.getOrdItemMetaId());
			if(item == null) {
				continue;
			}
			//不是创建任务并且不是资源已确认的跳过
			if(!message.isOrderCreateMsg()&&!Constant.ORDER_RESOURCE_STATUS.AMPLE.name().equals(item.getResourceStatus())) {
			    continue;
			}
			t.setStatus(Constant.EBK_TASK_STATUS.ACCEPT.name());
			t.setOrderUpdate("false");
			t.setMemo("经您方同意，客服操作，接受预订");
			t.setConfirmTime(new Date());
			t.setConfirmUser("SYSTEM");
			updateEbkTaskList.add(t);
			orderTask.add(t.getOrdItemMetaId());
		}
		if(updateEbkTaskList.size() > 0) {
			this.ebkTaskService.updateList(updateEbkTaskList);
		}*/
	}
	/**
	 * 创建EBK订单
	 * @param message
	 * @param order
	 */
	private void createEbkTask(Message message, OrdOrder order) {
		if(order == null || order.isCanceled()) {
			return;
		}
		//TODO:修改为新的EBK任务创建
		/*List<OrdOrderItemMeta> metas = order.getAllOrdOrderItemMetas();
		List<EbkTask> ebkTaskList = new ArrayList<EbkTask>();
		for(OrdOrderItemMeta meta : metas) {
			EbkTask task = fillEbkTaskInfo(meta, Constant.EBK_TASK_TYPE.RESOURCE_CONFIRM.name());
			if(task != null) {
				if(hasOrderMemo(order)) {
					task.setOrderUpdate("true");
				}
				ebkTaskList.add(task);
			}
		}
		if(ebkTaskList.size() > 0) {
			this.ebkTaskService.insertList(ebkTaskList);
		}*/
	}
	/**
	 * 创建订单时是否有用户备注
	 * 
	 * @author: ranlongfei 2013-1-29 上午11:52:16
	 * @param order
	 * @return
	 */
	private boolean hasOrderMemo(OrdOrder order) {
		if(order.getUserMemo() != null && !"".equals(order.getUserMemo())) {
			return true;
		}
		List<OrdOrderMemo> memoList = orderServiceProxy.queryMemoByOrderId(order.getOrderId());
		if(memoList != null && memoList.size() > 0) {
			for(OrdOrderMemo m : memoList) {
				if("true".equals(m.getUserMemo())) {
					return true;
				}
			}
		}
		return false;
	}
	/**
	 * 创建一个EBK任务单信息
	 * 
	 * @author: ranlongfei 2013-1-9 下午1:23:31
	 * @param meta
	 * @return
	 */
	private EbkTask fillEbkTaskInfo(OrdOrderItemMeta meta, String taskType) {
		// 不是酒店产品的订单子子项
		if(!Constant.PRODUCT_TYPE.HOTEL.name().equals(meta.getProductType())) {
			return null;
		}
		if(!"true".equals(meta.getSupplierFlag())) {
			return null;
		}
		EbkTask task = new EbkTask();
		task.setCreateTime(new Date());
		task.setOrderId(meta.getOrderId());
		//TODO:修改为新的EBK任务查询
		/*task.setOrdItemMetaId(meta.getOrderItemMetaId());
		task.setStatus(Constant.EBK_TASK_STATUS.CREATE.name());
		task.setTaskType(taskType);
		task.setSupplierId(meta.getSupplierId());*/
		return task;
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

	/**
	 * 
	 * @author: ranlongfei 2012-12-3 下午3:34:11
	 * @param args
	 */
	public static void main(String[] args) {

	}

}
