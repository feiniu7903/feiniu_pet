/**
 * 
 */
package com.lvmama.order.service;

import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.SupplierProductInfo;

/**
 * @author yangbin
 *
 */
public interface OrderSockCheckService {

	/**
	 * 计算预产生的订单数据是否资源满足
	 * @return result.fail表示整个过程失败
	 */
	ResultHandleT<SupplierProductInfo> calcProductSell(BuyInfo buyInfo);	
}
