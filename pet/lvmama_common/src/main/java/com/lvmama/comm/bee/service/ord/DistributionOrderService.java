package com.lvmama.comm.bee.service.ord;

import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.vo.ord.BuyInfo;

public interface DistributionOrderService {

	public OrdOrder createOrderByCouponCode(final BuyInfo buyInfo,List<String> couponCodes);
}
