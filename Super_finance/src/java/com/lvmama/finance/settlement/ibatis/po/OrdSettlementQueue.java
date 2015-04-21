package com.lvmama.finance.settlement.ibatis.po;

import java.io.Serializable;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.utils.PriceUtil;

/**
 * OrdSettlementQueue.
 *
 * <pre>
 * </pre>
 *
 * @author wuwei
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public class OrdSettlementQueue implements Serializable {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = -8900995749950710498L;
	/**
	 * 主键.
	 */
	private Long settlementQueueId;
	/**
	 * 采购产品ID.
	 */
	private Long metaProductId;
	/**
	 * 本期结算金额.
	 */
	private Long currentAmount;
	/**
	 * 累计应结算金额.
	 */
	private Long historyAmount;
	/**
	 * 结算对象ID.
	 */
	private Long targetId;

	/**
	 * 采购产品.
	 */
	private MetaProduct metaProduct;
	/**
	 * 结算对象.
	 */
	private SupSettlementTarget supSettlementTarget;

	/**
	 * getSettlementQueueId.
	 *
	 * @return 主键
	 */
	public Long getSettlementQueueId() {
		return settlementQueueId;
	}

	/**
	 * setSettlementQueueId.
	 *
	 * @param settlementQueueId
	 *            主键
	 */
	public void setSettlementQueueId(final Long settlementQueueId) {
		this.settlementQueueId = settlementQueueId;
	}

	/**
	 * getMetaProductId.
	 *
	 * @return 采购产品ID
	 */
	public Long getMetaProductId() {
		return metaProductId;
	}

	/**
	 * setMetaProductId.
	 *
	 * @param metaProductId
	 *            采购产品ID
	 */
	public void setMetaProductId(final Long metaProductId) {
		this.metaProductId = metaProductId;
	}

	/**
	 * getCurrentAmount.
	 *
	 * @return 本期结算金额
	 */
	public Long getCurrentAmount() {
		return currentAmount;
	}
	public float getCurrentAmountYuan(){
		return PriceUtil.convertToYuan(currentAmount.longValue());
	}
	/**
	 * setCurrentAmount.
	 *
	 * @param currentAmount
	 *            本期结算金额
	 */
	public void setCurrentAmount(final Long currentAmount) {
		this.currentAmount = currentAmount;
	}
	
	

	/**
	 * getHistoryAmount.
	 *
	 * @return 累计应结算金额
	 */
	public Long getHistoryAmount() {
		return historyAmount;
	}
	public float getHistoryAmountYuan() {
		return PriceUtil.convertToYuan(historyAmount.longValue());
	}

	/**
	 * setHistoryAmount.
	 *
	 * @param historyAmount
	 *            累计应结算金额
	 */
	public void setHistoryAmount(final Long historyAmount) {
		this.historyAmount = historyAmount;
	}

	/**
	 * getTargetId.
	 *
	 * @return 结算对象ID
	 */
	public Long getTargetId() {
		return targetId;
	}

	/**
	 * setTargetId.
	 *
	 * @param targetId
	 *            结算对象ID
	 */
	public void setTargetId(final Long targetId) {
		this.targetId = targetId;
	}

	/**
	 * getMetaProduct.
	 *
	 * @return 采购产品
	 */
	public MetaProduct getMetaProduct() {
		return metaProduct;
	}

	/**
	 * setMetaProduct.
	 *
	 * @param metaProduct
	 *            采购产品
	 */
	public void setMetaProduct(final MetaProduct metaProduct) {
		this.metaProduct = metaProduct;
	}

	/**
	 * getSupSettlementTarget.
	 *
	 * @return 结算对象
	 */
	public SupSettlementTarget getSupSettlementTarget() {
		return supSettlementTarget;
	}

	/**
	 * setSupSettlementTarget.
	 *
	 * @param supSettlementTarget
	 *            结算对象
	 */
	public void setSupSettlementTarget(
			final SupSettlementTarget supSettlementTarget) {
		this.supSettlementTarget = supSettlementTarget;
	}
	
	/**
	 * 修改待结算队列中的本期结算金额
	 * @param ordSubSettlement	结算子单对象.
	 * @param charge	需要从待结算队列中减去的金额.
	 */
	public void processOrdSubSettlement(OrdSubSettlement ordSubSettlement,long charge) {
		if ((ordSubSettlement.getPayAmount() > 0) || (ordSubSettlement.getReceiveAmount() > 0)) {
			this.currentAmount = this.currentAmount - charge;
		}  
	}
	/**
	 * 从当前待结算队列中删除一个结算队列项,根据订单子子项计算本期结算金额及累计应结算金额.
	 * @param ordOrderItemMeta	结算队列项对应的订单子子项.
	 */
	public void deleteOrdSettlementQueueItem(OrdOrderItemMeta ordOrderItemMeta) {
		if (ordOrderItemMeta == null) {
			return;
		}
		Long actualSettlementPrice = ordOrderItemMeta.getActualSettlementPrice();
		Long productQuantity = ordOrderItemMeta.getProductQuantity();
		Long quantity = ordOrderItemMeta.getQuantity();
		this.currentAmount = this.currentAmount - actualSettlementPrice * productQuantity * quantity;
		this.historyAmount = this.historyAmount - actualSettlementPrice * productQuantity * quantity;
	}
	
}
