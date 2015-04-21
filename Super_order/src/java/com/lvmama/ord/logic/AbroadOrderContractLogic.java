package com.lvmama.ord.logic;

import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.prd.dao.MetaProductDAO;
import com.lvmama.prd.dao.ProdProductItemDAO;

public class AbroadOrderContractLogic implements OrderContractLogic {
	private ProdProductItemDAO prodProductItemDAO;
	private MetaProductDAO metaProductDAO;
	@Override
	public void continueData(final OrdOrder order, final ProdProduct product,
			final Map<String, Object> result) {
		
	}

	@Override
	public String getContractNoPreffic() {
		return "abroad";
	}

	public ProdProductItemDAO getProdProductItemDAO() {
		return prodProductItemDAO;
	}

	public void setProdProductItemDAO(ProdProductItemDAO prodProductItemDAO) {
		this.prodProductItemDAO = prodProductItemDAO;
	}

	public MetaProductDAO getMetaProductDAO() {
		return metaProductDAO;
	}

	public void setMetaProductDAO(MetaProductDAO metaProductDAO) {
		this.metaProductDAO = metaProductDAO;
	}
 
}
