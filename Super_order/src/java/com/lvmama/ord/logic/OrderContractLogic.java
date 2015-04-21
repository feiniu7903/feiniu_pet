package com.lvmama.ord.logic;
/**
 * 各合同接口
 */
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProduct;

public interface OrderContractLogic {
	static final String PROD_ECONTRACT="prodEContract.";
	/**
	 * 取得各合同差异数据
	 * @param order
	 * @param product
	 * @param result
	 */
	void continueData(OrdOrder order, ProdProduct product,
			Map<String, Object> result) ;
	/**
	 * 取得合同编号前缀
	 * @return
	 */
	String getContractNoPreffic();
}
