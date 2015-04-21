
package com.lvmama.prd.logic;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.prd.dao.MetaProductDAO;
import com.lvmama.prd.dao.MetaTimePriceDAO;

public class ProductResourceConfirmLogic {
 	private MetaTimePriceDAO metaTimePriceDAO;
 	private MetaProductDAO metaProductDAO;
	
	/**
	 * 判断是否需要资源确认.
	 * @param metaBranch
	 * @param visitDate
	 * @return
	 */
	public boolean isResourceConfirm(MetaProductBranch metaBranch,Date visitDate){
		MetaProduct metaProduct = metaProductDAO.getMetaProductByBranchId(metaBranch.getMetaBranchId());
		//不定期,默认不需要资源确认
		if(metaProduct != null && metaProduct.IsAperiodic()) {
			return false;
		}
		boolean flag=true;
		TimePrice timePrice=metaTimePriceDAO.getMetaTimePriceByIdAndDate(metaBranch.getMetaBranchId(), visitDate);
		if(timePrice!=null){
			if(metaBranch.isTotalDecrease()&&metaBranch.getTotalStock()>0){
				flag=StringUtils.equals("true", timePrice.getResourceConfirm());
			}else{
				flag=timePrice.isNeedResourceConfirm();
			}
		}		
		return flag;
	}

	public void setMetaTimePriceDAO(MetaTimePriceDAO metaTimePriceDAO) {
		this.metaTimePriceDAO = metaTimePriceDAO;
	}

	public void setMetaProductDAO(MetaProductDAO metaProductDAO) {
		this.metaProductDAO = metaProductDAO;
	}

	
}
