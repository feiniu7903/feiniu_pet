package com.lvmama.comm.pet.service.fin;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.hessian.RemoteService;
import com.lvmama.comm.pet.po.fin.SetSettlementItem;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.SETTLEMENT_TYPE;

/**
 * 订单结算项Service
 * 
 * <pre>
 * <b>此接口提供所有对订单结算项的操作和与订单结算项相关的服务</b>
 * </pre>
 * 
 * @author yanggan
 * @version 结算重构 12/01/2012
 * 
 */
@RemoteService("setSettlementItemService")
public interface SetSettlementItemService {
	/**
	 * 查询订单结算项
	 * 
	 * @param map
	 *            查询参数
	 * @return 分页数据
	 */
	public Page<SetSettlementItem> searchItemList(Map<String, Object> map);
	/**
	 * 查询订单结算项
	 * 
	 * @param map
	 *            查询参数
	 * @return 分页数据
	 */
	public Page<SetSettlementItem> searchList(Map<String, Object> map);

	/**
	 * 不结算
	 * 
	 * @param settlementItemIds
	 *            订单子子项ID
	 * @param operatorName
	 *            操作人员
	 */
	void noSettle(List<Long> settlementItemIds, String operatorName);

	/**
	 * 根据订单号查询订单结算项
	 * 
	 * @param orderId
	 *            订单号
	 * @return 订单结算项
	 */
	List<SetSettlementItem> searchSettlementItemByOrderId(Long orderId);

	/**
	 * 根据选择的订单结算项生成结算单
	 * 
	 * @param settlementItemIds
	 *            订单结算项ID
	 * @param settlementType
	 *            结算类型（ORDER OR GROUP）
	 * @param operatorName
	 *            操作人员
	 * @return key=newSettlement 创建的新结算的ID;key=mergeSettlement 合并的旧结算单的ID
	 */
	Map<String, Object> settle(List<Long> settlementItemIds, Constant.SETTLEMENT_TYPE settlementType, String operatorName);

	/**
	 * 根据查询条件生成结算单
	 * 
	 * @param searchParams
	 *            查询条件
	 * @param settlementType
	 *            结算类型（ORDER OR GROUP）
	 * @param operatorName
	 *            操作人员
	 * @return key=newSettlement 创建的新结算的ID;key=mergeSettlement
	 *         合并的旧结算单的ID;key=orderItemMetaIds已生成/合并结算单的订单子子项ID
	 */
	public Map<String, Object> settleAll(Map<String, Object> searchParams, SETTLEMENT_TYPE settlementType, String operatorName);

	/**
	 * 新增或更新订单结算项
	 * 
	 * @param setSettlementItems
	 *            订单结算项
	 * @param messageType
	 *            触发的消息类型
	 */
	public void insertOrUpdateSettlementItem(List<SetSettlementItem> setSettlementItems, Constant.EVENT_TYPE messageType);
	
	/**
	 * 修改结算价
	 * 
	 * @param setSettlementItems
	 *            订单结算项
	 * @param operatorName
	 *            操作人
	 * @param messageType
	 *            触发的消息类型            
	 */
	public void updateSettlementPrice(List<SetSettlementItem> setSettlementItems, String operatorName, Constant.EVENT_TYPE messageType);
	/**
	 * 根据结算单号查询订单子子项项ID
	 * 
	 * @param settlementId
	 *            结算单ID
	 * @return
	 */
	public List<Long> searchOrderItemMetaIdsBySettlementId(long settlementId);

	/**
	 * 根据订单结算项ID查询订单子子项ID
	 * 
	 * @param settlementItemIds
	 *            订单结算项ID
	 * @return
	 */
	public List<Long> searchOrderItemMetaIdsBySettlementItemId(List<Long> settlementItemIds);

	/**
	 * 查询导出的Excel数据
	 * 
	 * @param settlementIds
	 *            结算单号
	 * @return
	 */
	public List<SetSettlementItem> searchItemExcelData1(List<Long> settlementIds);

	/**
	 * 查询结算单的订单明细
	 * 
	 * @param searchParameter
	 *            查询参数
	 * @return 分页数据
	 */
	public Page<SetSettlementItem> searchItemDetailList(Map<String, Object> searchParameter);

	/**
	 * 查询结算总价
	 * 
	 * @param searchParameter
	 *            查询参数
	 * @return
	 */
	public Long searchSumprice(Map<String, Object> searchParameter);

	/**
	 * 导出结算单的订单明细
	 * 
	 * @param searchParameter
	 *            查询参数
	 */
	public List<SetSettlementItem> searchItemExcelData2(Map<String, Object> searchParameter);

	/**
	 * 从结算单中移除订单结算项
	 * 
	 * @param settlementId
	 *            结算单
	 * @param settlementItemIds
	 *            订单结算项
	 * @param operatorName
	 *            操作人
	 */
	public int removeSettlementItem(long settlementId, List<Long> settlementItemIds, String operatorName);

	/**
	 * 根据订单号查询订单结算项
	 * 
	 * @param orderId
	 *            订单号
	 * @param targetId
	 *            结算对象ID
	 * @param 结算单类型
	 *            ORDER OR GROUP        
	 * @return
	 */
	public List<SetSettlementItem> searchListByOrderId(Long orderId, Long targetId,String settlementType);

	/**
	 * 增加订单结算项到结算单中
	 * 
	 * @param settlementId
	 *            结算单
	 * @param settlementItemIds
	 *            订单结算项
	 * @param operatorName
	 *            操作人
	 */
	public Integer addOrder(long settlementId, List<Long> settlementItemIds, String operatorName);

	/**
	 * 修改结算总价
	 * 
	 * @param settlementItemId
	 *            结算项ID
	 * @param settlementId
	 *            结算单ID
	 * @param settlementPrice
	 *            结算价
	 * @param remark
	 *            备注
	 * @param type
	 *            total 结算总价 single 结算单价
	 * @param operatorName
	 *            操作人
	 */
	public Integer modifySettlementPrice(Long settlementItemId, Long settlementPrice, Long settlementId, String remark, String type, String operatorName);

	/**
	 * 批量修改结算价
	 * 
	 * @param metaProductId
	 *            采购产品ID 结算项ID
	 * @param settlementId
	 *            结算单ID
	 * @param settlementPrice
	 *            结算价
	 * @param remark
	 *            备注
	 * @param operatorName
	 *            操作人
	 */
	public Integer batchModifySettlementPrice(Long settlementItemId, Long metaProductId, Long settlementId, String remark, String operatorName);
	
	/**
	 * 根据订单子子项ID查询是否已经进行结算打款
	 * @param orderItemMetaId 订单子子项ID
	 * @return true 已经结算打款  false 未结算打款
	 */
	public boolean searchSettlementPayByOrderItemMetaId(Long orderItemMetaId);
	/**
	 * 根据订单ID更新结算项中的订单结算总额
	 * @param orderId
	 * @param countSettleAmount
	 * @return
	 */
	public int updateSettlementItem(final Long orderId,final Long countSettleAmount);
	
	public void updateSettlement(SetSettlementItem existsItem, SetSettlementItem newItem);
	
	/**
	 * 只用于新系统对接
	 * @param targetItemMap
	 * @param settlementType
	 * @param operatorName
	 * @param settlementId
	 * @param target
	 * @return
	 */
	public Map<String,Object> settleByTarget(Map<String,List<SetSettlementItem>> targetItemMap, Constant.SETTLEMENT_TYPE settlementType, String operatorName,Long settlementId,Map<String,SupSettlementTarget> targetMap);
}
