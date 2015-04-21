package com.lvmama.ord.logic;

import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProduct;

public class BJONEOrderContractLogic implements OrderContractLogic {
	@Override
	public void continueData(OrdOrder order, ProdProduct product,
			Map<String, Object> result) {
		Object traveller=result.get("traveller");
		Object orderPersonCount=result.get("orderPersonCount");
		boolean isAddCount=false;
		if(null!=orderPersonCount){
			String count=orderPersonCount.toString();
			Long amount=Long.parseLong(count);
			if(amount>1){
				isAddCount=true;
			}
		}
		if(null!=traveller && isAddCount){
			result.put("traveller", traveller+"(等共"+orderPersonCount+"人)");
		}
		//团号：计调中订单所对应的团号
		result.put("groupNo", order.getTravelGroupCode());
	}

	@Override
	public	String getContractNoPreffic() {
		return "bj";
	}

}
