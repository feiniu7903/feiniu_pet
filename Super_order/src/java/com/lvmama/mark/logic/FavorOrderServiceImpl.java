/**
 * 
 */
package com.lvmama.mark.logic;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.service.favor.FavorOrderService;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.mark.MarkCouponUsage;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.pet.vo.favor.FavorProductResult;
import com.lvmama.comm.pet.vo.favor.ProductFavorStrategy;
import com.lvmama.comm.vo.Constant;
import com.lvmama.mark.dao.MarkCouponUsageDAO;
import com.lvmama.order.dao.OrderDAO;
import com.lvmama.order.dao.OrderItemMetaDAO;
import com.lvmama.order.dao.OrderItemProdDAO;
import com.lvmama.order.service.impl.BuildOrderInfoDTO;

/**
 * 优惠系统服务类
 * @author liuyi
 *
 */
public class FavorOrderServiceImpl implements FavorOrderService{
	
	/**
	 * 日志输出器
	 */
	private static final Log LOG = LogFactory.getLog(FavorOrderServiceImpl.class);
	
	private MarkCouponUsageDAO markCouponUsageDAO;
	private OrderItemMetaDAO orderItemMetaDAO;
	private OrderItemProdDAO orderItemProdDAO;
	
	public Long selectCountByParam(Map<String, Object> param){
		return markCouponUsageDAO.selectCountByParam(param);
	}
	
	public List<MarkCouponUsage> selectByParam(Map<String, Object> param){
		return markCouponUsageDAO.selectByParam(param);
	}
	
	public List<MarkCouponUsage> getMarkCouponUsageByObjectIdAndType(Long objectId, String type){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("objectType", type);
		param.put("objectId", objectId);
		return markCouponUsageDAO.selectByParam(param);
	}
	
	public Long getSumUsageAmountByUser(UserUser user){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("userId", user.getUserId());
		param.put("objectType", Constant.OBJECT_TYPE.ORD_ORDER.name());//获取用户的优惠券信息
		return markCouponUsageDAO.selectSumUsageAmount(param);
	}
	
	public Long getSumUsageAmountByCouponCode(MarkCouponCode markCouponCode){
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("couponCodeId", markCouponCode.getCouponCodeId());
		return markCouponUsageDAO.selectSumUsageAmount(param);
	}
	
	public Long getSumUsageAmount(Map<String,Object> param){
		return markCouponUsageDAO.selectSumUsageAmount(param);
	}
	
	/**
	 * 更新优惠后的订单销售产品子项价格
	 */
	public void updateOrderItemMetaPriceByCoupon(Long orderId, List<FavorProductResult> favorProductResultList){
		List<OrdOrderItemProd> ordOrderItemProdList = orderItemProdDAO.selectByOrderId(orderId);
		for(OrdOrderItemProd ordOrderItemProd: ordOrderItemProdList){//遍历销售产品项
			List<OrdOrderItemMeta> ordOrderItemMetaList = orderItemMetaDAO.selectByOrderItemId(ordOrderItemProd.getOrderItemProdId());
			long sumSettlementPrice = 0L;
			for(OrdOrderItemMeta ordOrderItemMeta : ordOrderItemMetaList){//遍历采购产品项
				for(int i = 0; i < favorProductResultList.size(); i++){//遍历，获取对应的优惠策略结果，然后先计算出优惠后的结算价，总结算价
					FavorProductResult favorProductResult = favorProductResultList.get(i);
					if(ordOrderItemMeta.getMetaProductId().equals(favorProductResult.getProductId()) 
							&& ordOrderItemMeta.getMetaBranchId().equals(favorProductResult.getProductBranchId())){
						
						Long discountAmount = favorProductResult.getDiscountAmount(ordOrderItemMeta, 0, true);
						LOG.info("meta discount: "+discountAmount);
						
						//设置优惠后的采购销售价
						//ordOrderItemMeta.setSettlementPrice(ordOrderItemMeta.getSettlementPrice() -  discountAmount/ (ordOrderItemMeta.getQuantity() * ordOrderItemMeta.getProductQuantity()));
						ordOrderItemMeta.setActualSettlementPrice(ordOrderItemMeta.getSettlementPrice() -  discountAmount/ (ordOrderItemMeta.getQuantity() * ordOrderItemMeta.getProductQuantity()));
						
						//设置优惠后的采购总销售价
						ordOrderItemMeta.setTotalSettlementPrice(ordOrderItemMeta.getActualSettlementPrice()*ordOrderItemMeta.getQuantity()*ordOrderItemMeta.getProductQuantity());
						
						for(ProductFavorStrategy productFavorStrategy: favorProductResult.getFavorStrategyList()){
							MarkCouponUsage markCouponUsage = new MarkCouponUsage();
							markCouponUsage.setCouponCodeId(productFavorStrategy.getBusinessCoupon().getBusinessCouponId());
							markCouponUsage.setObjectId(orderId);
							markCouponUsage.setSubObjectIdA(ordOrderItemMeta.getMetaProductId());//设置优惠对应的产品ID
							markCouponUsage.setSubObjectIdB(ordOrderItemMeta.getMetaBranchId());//设置优惠对应的产品类别ID
							markCouponUsage.setCreateTime(new Date());
							markCouponUsage.setObjectType(Constant.OBJECT_TYPE.ORD_ORDER_ITEM_META.name());
							markCouponUsage.setStrategy(productFavorStrategy.getFavorType());
							markCouponUsage.setAmount(discountAmount);
							this.saveCouponUsage(markCouponUsage);
						}					
					}
				}
				
				// 累加结算价
				sumSettlementPrice += ordOrderItemMeta.getActualSettlementPrice()* ordOrderItemMeta.getProductQuantity();
				ordOrderItemProd.setSumSettlementPrice(sumSettlementPrice);
			}
			for(OrdOrderItemMeta ordOrderItemMeta : ordOrderItemMetaList){//遍历采购产品项，进行价格的最后更新
				
				//设置优惠后的采购产品销售价
				long sellPrice = markSellPrice(
						ordOrderItemProd.getSumSettlementPrice(),
						ordOrderItemProd.getPrice(), ordOrderItemMeta);
				ordOrderItemMeta.setSellPrice(sellPrice);
				
				LOG.info("set order meta: "+ordOrderItemMeta.getOrderItemMetaId()+","+ordOrderItemMeta.getSellPrice()+","+ordOrderItemMeta.getSettlementPrice()+","+ordOrderItemMeta.getActualSettlementPriceYuan());
				
				//更新采购子项信息
				orderItemMetaDAO.updateByPrimaryKey(ordOrderItemMeta);
				
			}
		}
	}
	
	private long markSellPrice(final long sumSettlementPrice,
				final long orderItemProdPrice,
				final OrdOrderItemMeta ordOrderItemMeta) {
			long sellPrice = 0L;
			// 总结算金额大于0
			if (sumSettlementPrice > 0) {
				// 之所以这么计算的原因可能是正常情况下与销售产品单价相等，特殊情况下不等
				sellPrice = Math
						.round((ordOrderItemMeta.getActualSettlementPrice()*ordOrderItemMeta.getProductQuantity() * orderItemProdPrice)
								/ sumSettlementPrice);
			}
			return sellPrice;
		}
	
	public void saveCouponUsage(MarkCouponUsage markCouponUsage) {
		markCouponUsageDAO.insert(markCouponUsage);
	}
	
	public void updateCouponUsage(MarkCouponUsage markCouponUsage) {
		markCouponUsageDAO.updateByPrimaryKey(markCouponUsage);
	}

	public void setMarkCouponUsageDAO(MarkCouponUsageDAO markCouponUsageDAO) {
		this.markCouponUsageDAO = markCouponUsageDAO;
	}

	public void setOrderItemMetaDAO(OrderItemMetaDAO orderItemMetaDAO) {
		this.orderItemMetaDAO = orderItemMetaDAO;
	}

	public void setOrderItemProdDAO(OrderItemProdDAO orderItemProdDAO) {
		this.orderItemProdDAO = orderItemProdDAO;
	}
}
