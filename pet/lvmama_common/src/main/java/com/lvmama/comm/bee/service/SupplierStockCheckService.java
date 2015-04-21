/**
 * 
 */
package com.lvmama.comm.bee.service;

import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.SupplierProductInfo;

/**
 * 负责向第三方供应商
 * @author yangbin
 *
 */
public interface SupplierStockCheckService {

	/**
	 * 库存检查，并且返回库存检查无库存不足的产品
	 * @param info
	 * @return
	 */
	ResultHandleT<SupplierProductInfo> checkStock(BuyInfo buyinfo,SupplierProductInfo info);
}
