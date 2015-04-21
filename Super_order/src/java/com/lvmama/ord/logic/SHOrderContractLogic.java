package com.lvmama.ord.logic;

import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProduct;
@Deprecated
public class SHOrderContractLogic implements OrderContractLogic {

	public void continueData(OrdOrder order, ProdProduct product,
			Map<String, Object> result) {

	}

	public  String getContractNoPreffic() {
		return "sh";
	}

}
