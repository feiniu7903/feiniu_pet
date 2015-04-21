package com.lvmama.comm.bee.service.ebooking;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ebooking.EbkCertificate;
import com.lvmama.comm.bee.po.ebooking.EbkFaxSend;
import com.lvmama.comm.bee.po.ebooking.EbkFaxTask;

public interface EbkFaxTaskService {

	/**
	 * 根据凭证查询任务
	 * 
	 * @author: ranlongfei 2013-3-21 下午6:49:38
	 * @param ebkCertificateId
	 * @return
	 */
	EbkFaxTask getByEbkCertificateId(Long ebkCertificateId);
	/**
	 * 生成EBK任务订单
	 * 
	 * @author: ranlongfei 2012-12-4 下午3:49:13
	 * @param task
	 * @return
	 */
	Long insertEbkFaxTask(EbkFaxTask faxTask,String operatorName);
	/**
	 * 条件查询任务
	 * 
	 * @author: ranlongfei 2012-12-5 下午5:33:37
	 * @param param
	 * @return
	 */
	List<EbkFaxTask> selectEbkFaxTaskByParams(Map<String, Object> param);
	/**
	 * 条件查询任务
	 * 
	 * @author: fangweiquan 2012-12-5 下午5:33:37
	 * @param param
	 * @return
	 */
	EbkFaxTask selectEbkFaxTaskByEbkCertificateId(Long certificateId);
	/**
	 * 条件查询任务数
	 * 
	 * @author: ranlongfei 2012-12-6 下午6:35:11
	 * @param params
	 */
	Integer getEbkFaxTaskCountByParams(Map<String, Object> params);
	/**
	 * 更新订单任务
	 * 
	 * @author: ranlongfei 2012-12-5 下午6:11:28
	 * @param logContent 
	 * @param task
	 */
	void updateEbkFaxTask(EbkFaxTask faxTask,String operatorName, String logContent);
	
	/**
	 * 根据主键查询EBK订单
	 * 
	 * @author: ranlongfei 2012-12-13 下午4:23:50
	 * @param EbkFaxTaskId
	 * @return
	 */
	EbkFaxTask getByEbkFaxTaskId(Long taskId);
	/**
	 * 获取ebkCertificate
	 * @param ebkCertificateId
	 */
	EbkCertificate getEbkCertificateById(Long ebkCertificateId);
	/**
	 * 根据传真任务id更新内部备注信息
	 * @param memo
	 * @param ebkFaxTaskIds
	 */
	void updateEbkCertificateByFaxTaskId(String memo, List<Long> ebkFaxTaskIds,String operatorName);
	/**
	 * 更新EbkCertificate信息
	 * @param ebkCertificate
	 */
	void updateEbkCertificate(EbkCertificate ebkCertificate);
	
	void updateEbkSendOrRecvStatus(EbkFaxTask ebkFaxTask, String operatorName);
	/**
	 * 新增传真发送任务.
	 *
	 * @param record
	 *            传真发送任务对象
	 * @return 传真发送任务ID
	 */
	Long insertOrdFaxSend(EbkFaxSend record);
	
	/**
	 * 更新传真服务器回传状态
	 * @param record
	 * @param logContent
	 * @return
	 */
	boolean updateEbkFaxSend(EbkFaxSend record, String logContent);
	/**
	 * 传真发送日志数量
	 * @param params
	 * @return
	 */
	long getEbkFaxSendCountByParam(Map<String, Object> params);
	/**
	 * 传真发送日志列表
	 * @param params
	 * @return
	 */
	List<EbkFaxSend> queryEbkFaxSendByParam(Map<String, Object> params);
	
	EbkFaxTask getEbkFaxTaskByOrderItemMetaId(Long orderItemMetaId);
	/**
	 * 表示凭证有用户特殊要求审核未处理
	 * @param ebkCertificateId
	 * @param updateUserMemoStatus
	 * @return
	 */
	int updateUserMemoStatus(Long orderId,String updateUserMemoStatus);
	List<EbkFaxTask> selectSendFaxTask();
	/**
	 * 向上查询原来的凭证任务
	 * 
	 * @author: ranlongfei 2013-5-2 下午7:41:17
	 * @param ebkFaxTaskId
	 * @return
	 */
	List<EbkFaxTask> selectOldEbkFaxTaskListWithTaskId(Long ebkFaxTaskId);
	
	EbkFaxTask findEbkFaxTaskAndCertificateByPkId(Long ebkFaxTaskId);
	EbkFaxSend selectEbkFaxSendByPrimaryKey(Long ebkFaxSendId);
	/**
	 * 根据传真发送编号查询传真
	 * @author: FangWeiQuan 2013-7-18 下午2:21:17
	 * @param ordFaxRecv
	 */
	EbkFaxTask selectEbkFaxTaskByEbkFaxSendId(Long sendId);
}
