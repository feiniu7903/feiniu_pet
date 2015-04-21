package com.lvmama.ord.logic;

import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProduct;

@Deprecated
public class GZOrderContractLogic implements OrderContractLogic {

	public void continueData(OrdOrder order, ProdProduct product,
			Map<String, Object> result) {
		//团号
		 result.put("groupNo", result.get("productName"));
		 if(null!=result.get(PROD_ECONTRACT+"agency")){
			 result.put(PROD_ECONTRACT+"isAgency", "同意");
		 }else{
			 result.put(PROD_ECONTRACT+"isAgency", "不同意");
		 }
	}

	public String getContractNoPreffic() {
		return "gz";
	}
}
