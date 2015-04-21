package com.lvmama.comm.bee.service.ebooking;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkTask;
import com.lvmama.comm.utils.json.ResultHandle;

public interface EbkTaskService {
	/**
	 * 根据凭证查询任务
	 * 
	 * @author: ranlongfei 2013-3-21 下午6:49:38
	 * @param ebkCertificateId
	 * @return
	 */
	EbkTask selectByEbkCertificateId(Long ebkCertificateId);
	/**
	 * 生成EBK任务订单
	 * 
	 * @author: ranlongfei 2012-12-4 下午3:49:13
	 * @param task
	 * @return
	 */
	Long insert(EbkTask task);
	/**
	 * 批量插入订单任务
	 * 
	 * @author: ranlongfei 2012-12-5 下午4:59:07
	 * @param taskList
	 */
	void insertList(List<EbkTask> taskList);
	/**
	 * 根据订单号查询任务
	 * 
	 * @author: ranlongfei 2012-12-5 下午5:31:23
	 * @param orderId
	 * @return
	 */
	List<EbkTask> findEbkTaskByOrderId(Long orderId);
	/**
	 * 条件查询任务
	 * 
	 * @author: ranlongfei 2012-12-5 下午5:33:37
	 * @param param
	 * @return
	 */
	List<EbkTask> findEbkTaskByExample(Map<String, Object> param);
	/**
	 * 批量更新订单任务
	 * 
	 * @author: ranlongfei 2012-12-5 下午6:10:11
	 * @param updateTaskList
	 */
	void updateList(List<EbkTask> updateTaskList);
	/**
	 * 更新订单任务
	 * 
	 * @author: ranlongfei 2012-12-5 下午6:11:28
	 * @param task
	 */
	void update(EbkTask task);
	/**
	 * 更新EBK订单任务的订单支付状态和订单状态
	 * 
	 * @author: ranlongfei 2013-4-9 下午2:11:28
	 * @param task
	 */
	void updateEbkTaskSynOrder(Long orderId, String paymentStatus, String orderStatus);
	/**
	 * 条件查询任务数
	 * 
	 * @author: ranlongfei 2012-12-6 下午6:35:11
	 * @param params
	 */
	Integer findEbkTaskCountByExample(Map<String, Object> params);
	
	/**
	 * 根据主键查询EBK订单
	 * 
	 * @author: ranlongfei 2012-12-13 下午4:23:50
	 * @param ebkTaskId
	 * @return
	 */
	EbkTask findEbkTaskByPkId(Long ebkTaskId);
	/**
	 * 根据订单子子项查询EBK订单列表
	 * @param ordItemMetaId
	 * @return
	 */
	List<EbkTask> findEbkTaskByOrdItemMetaId(Long ordItemMetaId);
	/**
	 * 根据主键查询EBK订单，包括凭证及其子项
	 * 
	 * @author: ranlongfei 2013-3-28 上午9:59:24
	 * @param ebkTaskId
	 * @return
	 */
	EbkTask findEbkTaskAndCertificateByPkId(Long ebkTaskId);
	/**
	 * 
	 * @param ebkTaskId
	 * @param certificateStatus
	 * @param waitTime
	 * @param memo
	 * @param confirmUser
	 * @param version
	 * @return
	 */
	ResultHandle updateEbk(Long ebkTaskId, String certificateStatus, Date waitTime, String memo, String confirmUser,String reason, Long version);
	ResultHandle updateEbkCertificateItemSupplierNo(String supplierNo, Long ebkTaskId);
	/**
	 * 查询子子项的最后一个任务
	 * 
	 * @author: ranlongfei 2013-4-2 上午9:22:28
	 * @param ordItemMetaId
	 * @return
	 */
	EbkTask selectNearbyEbkTaskByOrderItemMetaId(Long ordItemMetaId);
	/**
	 * 根据主键查询EBK订单，包括凭证接收数据及其子项
	 * 
	 * @author: FangWeiQuan 2013-4-17 上午9:59:24
	 * @param ebkTaskId
	 * @return
	 */
	EbkTask findEbkTaskAndCertificateAndGetContentByPkId(Long ebkTaskId);
	/**
	 * 修改Task确认信息
	 * 
	 * @author: zhangjie 2013-5-22 
	 * @param ebkTaskId 
	 * @param date          确认时间
	 * @param confirmUser	确认人
	 * @param status		确认状态
	 * @param memo			备注
	 * @return
	 */
	void updateTaskConfirmStatus(Long ebkTaskId,String date,String confirmUser,String status,String memo);
}
