package com.lvmama.comm.bee.service.ord;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMetaPrice;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.ord.OrdSettlementPriceRecord;
import com.lvmama.comm.vo.Constant.ORD_SETTLEMENT_PRICE_CHANGE_TYPE;
import com.lvmama.comm.vo.Constant.ORD_SETTLEMENT_PRICE_REASON;

/**
 * 修改结算价Service
 * 
 * @author zhangwenjun
 * 
 */
public interface ModifySettlementPriceService {
	public Integer selectRowCount(Map<String, Object> searchConds);

	public List<OrdOrderItemMetaPrice> selectByParms(Map<String, Object> map);

	public Integer queryHistoryRecordCount(Map<String, Object> searchConds);

	public List<OrdOrderItemMetaPrice> queryHistoryRecordList(
			Map<String, Object> map);

	public List<OrdOrderItemMetaPrice> exportHistoryRecordList(
			Map<String, Object> map);
	public boolean updateSettlementPrice(Long orderItemMetaId,
			ORD_SETTLEMENT_PRICE_CHANGE_TYPE changeType,
			ORD_SETTLEMENT_PRICE_REASON reason, String remark,
			Long settlementPrice, String creator);
	public boolean updateSettlementPrice(Long orderItemMetaId,
			ORD_SETTLEMENT_PRICE_CHANGE_TYPE changeType,
			ORD_SETTLEMENT_PRICE_REASON reason, String remark,
			Long settlementPrice, String creator,boolean updateFlag );

	public boolean insertSettlementRecord(
			OrdSettlementPriceRecord ordSettlementPriceRecord);

	public OrdOrderItemMetaPrice selectByPrimaryKey(Long ordOrderItemMetaId);

	public Integer queryVerifyListCount(Map<String, Object> searchConds);

	public List<OrdOrderItemMetaPrice> queryVerifyList(Map<String, Object> map);

	public boolean doVerify(Map<String, Object> map);

	public OrdSettlementPriceRecord queryHistoryRecordById(Long recordId);

	public boolean updateHistoryRecordById(Map<String, Object> map);

	public boolean ifExistRefundment(Long orderItemMetaId);

	public boolean updateSettlementPriceForVedify(
			OrdOrderItemMetaPrice ordOrderItemMetaPrice);

	public OrdOrderItemMeta selectByOrderItemMetaId(Long orderItemMetaId);

	public boolean searchUnverifiedRecord(Long orderItemMetaId);

	public OrdOrderItemProd selectItemByOrderItemProdId(Long orderItemProdId);
	public boolean updateOrderItemMetaVirtual(Long orderItemMetaId,String virtual);
}
