package com.lvmama.comm.bee.service.prod;

import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProductRoyalty;

public interface ProdProductRoyaltyService {
	
	/**
	 * 获取产品分润信息
	 * @param productId 产品ID
	 * @return 分润信息集合
	 */
	List<ProdProductRoyalty> getProdProductRoyaltys(Long productId);
	
	/**
	 * 获得产品分润集合
	 * @param productId 产品ID
	 * @param order 订单信息
	 * @return （收款方Email1^金额1^备注1|收款方Email2^金额2^备注2）
	 */
	String getRoyaltysParamers(Long productId,OrdOrder order);
	
	/**
	 * 获得分润产品id集合
	 * @return 产品id集合
	 */
	List<Long> getRoyaltyProductIds();

}
