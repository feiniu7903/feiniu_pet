package com.lvmama.comm.vst.service;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.hessian.RemoteService;
import com.lvmama.comm.pet.po.fin.SetSettlementItem;
import com.lvmama.comm.vo.Constant.EVENT_TYPE;
@RemoteService("settlementService")
public interface SettlementService {
	
	/**
	 * 批量新增或修改结算子项信息
	 * @param setSettlementItems
	 */
	void saveOrUpdateSetSettlementItem(final List<SetSettlementItem> setSettlementItems);
	
	/**
	 * 插入一条结算子项信息
	 * @param setSettlementItem
	 */
	void insertSettlementItem(final SetSettlementItem setSettlementItem,final String businessName);
	
	/**
	 * 新增或修改结算子项一条结算子项信息
	 * @param setSettlementItem
	 */
	void saveSettlementItem(final SetSettlementItem setSettlementItem,final String businessName);
	
	/**
	 * 批量插入结算子项信息
	 * @param setSettlementItem
	 */
	void batchInsertSettlementItem(final List<SetSettlementItem> setSettlementItems,final String businessName);
	
	/**
	 * 根据结算子项ID号修改结算子项信息
	 * @param setSettlementItem
	 */
	int updateSettlementItem(final SetSettlementItem setSettlementItem,final String businessName);
	/**
	 * 批量修改结算子项信息(根据结算子项ID号)
	 * @param setSettlementItem
	 */
	void batchUpdateSettlementItem(final List<SetSettlementItem> setSettlementItems,final String businessName);
	
	/**
	 * 根据参数取消结算子项的结算
	 * @param setSettlementItem
	 * @param businessName
	 * @return
	 */
	int orderCancelSetSettlementItem(final Long orderId,final String businessName);
	
	/**
	 * 订单退款时修改结算
	 * @param setSettlementItem
	 * @param businessName
	 * @return
	 */
	int orderRefundmentUpdateSetSettlement(List<SetSettlementItem> items,final String businessName);
	
	/**
	 * 根据供应商ID，更新预警信息
	 * @param supplierId
	 * @param depositMoney
	 * @param depositTime
	 * @return
	 */
	int updateSupSupplierSettleMoney(Long supplierId,Long depositMoney,Date depositTime);
	
	/**
	 * 根据订单或订单子项ID，系统名称查询结算子项，系统名称不可为空
	 * @param orderId
	 * @param orderItemId
	 * @param businessName
	 * @return
	 */
	List<SetSettlementItem> findSetSettlementItemByParams(final Long orderId,final Long orderItemId,final String businessName );

	/**
	 * 新增或更新订单结算项
	 * 
	 * @param setSettlementItems
	 *            订单结算项
	 * @param messageType
	 *            触发的消息类型
	 */
	void insertOrUpdateSettlementItem(List<SetSettlementItem> setSettlementItems, EVENT_TYPE messageType);
	
	
	/**
	 * 根据订单子子项ID查询是否已经进行结算打款
	 * @param orderItemMetaId 订单子子项ID
	  * @param businessName 业务系统标示
	 * @return true 已经结算打款  false 未结算打款
	 */
	public boolean searchSettlementPayByOrderItemMetaId(Long orderItemMetaId,final String businessName);
	
	
	
}
