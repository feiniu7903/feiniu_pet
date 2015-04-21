package com.lvmama.ord.logic;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdPerson;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.utils.IdentityCardUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.prd.dao.MetaProductDAO;
import com.lvmama.prd.dao.ProdProductItemDAO;
@Deprecated
public class SCOrderContractLogic implements OrderContractLogic {
	private ProdProductItemDAO prodProductItemDAO;
	private MetaProductDAO metaProductDAO;
	public void continueData(OrdOrder order, ProdProduct product,
			Map<String, Object> result) {
		//1.性别
		String sex=Constant.E_CONTRACT_DEFAULT_VALUE;
		//2.国籍
		String nationality=Constant.E_CONTRACT_DEFAULT_VALUE;
		List<OrdPerson> personList=order.getPersonList();
		for(OrdPerson ordPerson:personList ){
			if("CONTACT".equals(ordPerson.getPersonType())){
				//取得证件号
				String  certNo=ordPerson.getCertNo();
				if(null!=certNo&&IdentityCardUtil.verify(certNo)){
					sex=IdentityCardUtil.getSex(certNo);
					sex=sex.replace("M","男").replace("F", "女");
					if(sex=="X"){
						sex=Constant.E_CONTRACT_DEFAULT_VALUE;
					}
					nationality="中国";
				}
				result.put("sex", sex);
				result.put("nationality", nationality);
	    	}
		}
		result.put("certType", Constant.E_CONTRACT_DEFAULT_VALUE);
		result.put("certNo", Constant.E_CONTRACT_DEFAULT_VALUE);
	}

	public String getContractNoPreffic() {
		return "sc";
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
