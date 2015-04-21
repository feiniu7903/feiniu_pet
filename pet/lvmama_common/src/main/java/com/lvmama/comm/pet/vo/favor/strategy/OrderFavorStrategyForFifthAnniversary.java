package com.lvmama.comm.pet.vo.favor.strategy;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.vo.favor.OrderFavorStrategy;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.comm.vo.Constant;

/**
 * 五周年的优惠策略
 * @author Administrator
 *
 */
public class OrderFavorStrategyForFifthAnniversary extends OrderFavorStrategy {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2716968116137731088L;
	
	public OrderFavorStrategyForFifthAnniversary(MarkCoupon markCoupon,
			MarkCouponCode markCouponCode) {
		super(new MarkCoupon(), new MarkCouponCode());
		this.markCoupon.setCouponId(3463L);
		this.markCouponCode.setCouponCodeId(26803347L);
		this.markCouponCode.setCouponId(3463L);
		this.markCouponCode.setCouponCode("A196898388280494");
	}

	@Override
	public String getFavorType() {
		return Constant.FAVOR_TYPE.AMOUNT_AMOUNT_WHOLE.name();
	}

	@Override
	public boolean isApply(OrdOrder order, Long discountAmount) {
		return true;
	}

	@Override
	public Long getDiscountAmount(OrdOrder order, Long discountAmount) {
		long offerPrice = 0L;
		for (OrdOrderItemProd prod : order.getOrdOrderItemProds()) {
			long subOfferPrice = 0L;
			ProdProductService prodProductService = (ProdProductService) SpringBeanProxy.getBean("prodProductService");
			ProdProduct product = prodProductService.getProdProductById(prod.getProductId());
			if (null != product && product.isAnniveraryProduct()) {
				for (OrdOrderItemMeta meta : prod.getOrdOrderItemMetas()) {
					subOfferPrice += meta.getProductQuantity() * (meta.getSettlementPrice() +  (meta.getSettlementPrice() % 10000 > 0 ? (meta.getSettlementPrice() / 10000 + 1) * 100 : meta.getSettlementPrice() / 10000 * 100));
				}
				subOfferPrice = (subOfferPrice < prod.getPrice() ? subOfferPrice : prod.getPrice()) * prod.getQuantity();
			} else {
				subOfferPrice = prod.getPrice() * prod.getQuantity();
			}
			offerPrice += subOfferPrice;
		}
		return order.getOughtPay() - offerPrice; 
	}

	@Override
	public String getInvalidDesc() {
		return "此产品不参加周年庆活动";
	}
}
