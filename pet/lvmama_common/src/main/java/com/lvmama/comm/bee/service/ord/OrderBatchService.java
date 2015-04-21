package com.lvmama.comm.bee.service.ord;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrderBatch;
import com.lvmama.comm.bee.po.ord.OrdOrderBatchOrder;
import com.lvmama.comm.pet.vo.Page;

public interface OrderBatchService {
	/**
	 * 保存批次信息
	 * @param orderBatch
	 * @return
	 */
	Long insert(OrdOrderBatch orderBatch);
	
	/**
	 * 产销批次信息
	 * @param batchId
	 * @return
	 */
	OrdOrderBatch selectByBatchId(Long batchId);

	/**
	 * 获取批次列表
	 * @param params
	 * @return
	 */
	Page<OrdOrderBatch> selectByParams(Map<Object, Object> params);
	
	/**
	 * 保存批次和订单的关系
	 * @param batchOrder
	 */
	void inserBatchOrder(OrdOrderBatchOrder batchOrder);
	
	/**
	 * 导出订单和辅助码
	 * @param batchId
	 * @return
	 */
	List<OrdOrderBatch> listBatchPassCode(Long batchId);
	
	/**
	 * 查询可取消的订单
	 * @param batchId
	 * @return
	 */
	List<OrdOrderBatch> queryBatchOrdersForCancel(Long batchId);
	
	/**
	 * 查询批次运行的结果，包括订单已经生成量、申码成功量
	 * @return
	 */
	Map<String, Integer> getBatchResult(Long batchId);
	
	/**
	 * 查询批次订单当前情况，包括订单已经生成量、履行数量、废单数量、可废单数量
	 * @return
	 */
	Map<String, Object> getBatchCount(Long batchId);
	
	/**
	 * 批量任务完成后，更新批次状态
	 * @param params
	 * @return
	 */
	boolean updateBatchStatus(Map<Object,Object> params);
	
	public boolean updateBatchValid(Map<Object,Object> params);
	
	/**
	 * 批量废单查询
	 * @param params
	 * @return
	 */
	public Page<OrdOrderBatch> listAbandonOrder(Map<Object,Object> params);

	/**
	 * 
	 * @return
	 */
	public List<OrdOrderBatch> selectNeedCreateOrder();
}
