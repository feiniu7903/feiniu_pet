/**
 * 
 */
package com.lvmama.service;

import java.util.List;

import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.vo.SupplierProductInfo;

/**
 * @author yangbin
 *
 */
public interface CheckStockHandle {

	/**
	 * 库存检查接口
	 * @param list
	 * @return
	 */
	List<SupplierProductInfo.Item> check(BuyInfo buyinfo,List<SupplierProductInfo.Item> list);
}
