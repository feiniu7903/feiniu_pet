package com.lvmama.finance.settlement.service;

import java.util.List;

import com.lvmama.finance.base.Page;
import com.lvmama.finance.settlement.ibatis.po.OrdSettlement;
import com.lvmama.finance.settlement.ibatis.po.OrdSettlementChange;
import com.lvmama.finance.settlement.ibatis.po.OrdSubSettlement;
import com.lvmama.finance.settlement.ibatis.po.OrdSubSettlementItem;
import com.lvmama.finance.settlement.ibatis.vo.OrderProductDetail;
import com.lvmama.finance.settlement.ibatis.vo.OrderSearchResult;
import com.lvmama.finance.settlement.ibatis.vo.SimpleOrdSettlement;

/**
 * 结算单管理
 * 
 * @author yanggan
 * 
 */
public interface OrdSettlementService {

	/**
	 * 查询结算单
	 * 
	 * @return 结算单
	 */
	Page<OrdSettlement> searchOrdSettlement();

	/**
	 * 查询结算子单
	 * 
	 * @return
	 */
	Page<OrdSubSettlement> searchOrdSubSettlement();

	/**
	 * 查询结算子单项明细
	 * 
	 * @return
	 */
	Page<OrdSubSettlementItem> searchOrdSubSettlementItem();

	/**
	 * 预存款打款
	 * 
	 * @param OrdSettlement
	 *            打款信息
	 */
	Integer advancedepositsBalpay(OrdSettlement ors);

	/**
	 * 线下打款
	 * 
	 * @param ors
	 *            打款信息
	 * @param bank
	 *            银行名称
	 * @param operatetimes
	 *            打款时间
	 * @param serial
	 *            流水号
	 */
	void bankPay(OrdSettlement ors, String bank, String operatetimes, String serial);

	/**
	 * 结算单确认/结算时查询结算单信息
	 * 
	 * @param id
	 *            结算单ID
	 */
	SimpleOrdSettlement searchSimpleOrdSettlement(Long id);

	/**
	 * 结算单确认/结算时查询原始结算单信息
	 * 
	 * @param id
	 *            结算单ID
	 */
	SimpleOrdSettlement searchInitalOrdSettlement(Long id);

	/**
	 * 修改固话结算对象信息
	 * 
	 * @param id
	 *            结算单ID
	 */
	void updateInitalInfo(Long id, OrdSettlement ors);
	
	/**
	 * 查询结算单信息
	 * 
	 * @param id
	 *            结算单ID
	 * @return
	 */
	OrdSettlement getOrdSettlementById(Long id);

	/**
	 * 结算单确认
	 * 
	 * @param ors
	 *            结算单信息
	 */
	void confirm(OrdSettlement ors);

	/**
	 * 结算单结算
	 * 
	 * @param ors
	 * 
	 * @return 0 结算成 ，-1 低扣款异常
	 */
	void settlement(OrdSettlement ors);

	/**
	 * 删除结算子单
	 * 
	 * @param settlementId
	 *            结算单ID
	 * @param subSettlementId
	 *            结算子单ID
	 * @return 0 删除成功 -1 删除失败 - 打款金额小于结算金额 -2删除失败 - 结算状态不是未结算
	 * 
	 */
	Integer deleteSubSettlement(Long settlementId, Long subSettlementId);

	/**
	 * 查询结算总价
	 * 
	 * @return
	 */
	Double searchSumprice();

	/**
	 * 修改实际结算价
	 * 
	 * @param change
	 */
	Integer modifyPrice(OrdSettlementChange change, Long settlementId, Long subSettlementId);

	/**
	 * 批量修改实际结算价
	 * 
	 * @param settlementId
	 *            结算单ID
	 * @param subSettlementId
	 *            结算子单ID
	 * @param metaProductId
	 *            采购产品ID
	 * @param amount
	 *            结算价
	 * @param remark
	 *            修改原因
	 */
	Integer modifyPrice(Long settlementId, Long subSettlementId, Long metaProductId, Double amount, String remark);

	/**
	 * 查询结算子单项的修改记录
	 * 
	 * @return
	 */
	List<OrdSettlementChange> searchChangeRecord(Integer type, Long id);

	/**
	 * 删除结算子单项
	 * 
	 * @param settlementId
	 *            结算单ID
	 * @param delIds
	 *            子单项ID
	 * @return 0 删除成功 ,-1 删除失败 - 打款金额小于结算金额 ,-2 删除失败 - 结算状态已结算
	 */
	Integer deleteSubSettlementItem(Long settlementId, Long[] subSettlementIds, Long[] delIds, String status);

	/**
	 * 查询订单
	 * 
	 * @param orderId
	 * @return
	 */
	List<OrderSearchResult> searchOrder(Long settlementId, Long orderId);

	/**
	 * 结算单新增订单
	 * 
	 * @param settlementId
	 * 
	 * @param ids
	 */
	Integer addOrder(Long settlementId, List<String> ids);
	
	/**
	 * 导出EXCEL
	 * @return
	 */
	List<OrdSubSettlementItem> exportOrdSubSettlementItem();
	/**
	 * 导出EXCEL
	 * @return
	 */
	public List<OrderProductDetail> exportOrdProductDetail();
	
	/**
	 * 修改结算总价
	 * @param change
	 * @param settlementId
	 * @param subSettlementId
	 * @return
	 */
	public Integer modifyTotalPrice(OrdSettlementChange change, Long settlementId);

	/**
	 * 修改应结金额
	 * @param change
	 * @param settlementId
	 * @param subSettlementId
	 * @return
	 */
	public Integer modifyPayAmount(Long settlementId);
}
