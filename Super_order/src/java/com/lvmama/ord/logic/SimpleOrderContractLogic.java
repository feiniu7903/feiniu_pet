package com.lvmama.ord.logic;

import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProduct;

public class SimpleOrderContractLogic implements OrderContractLogic{

	@Override
	public void continueData(OrdOrder order, ProdProduct product,
			Map<String, Object> result) {
		
	}

	@Override
	public String getContractNoPreffic() {
		return "";
	}

}
