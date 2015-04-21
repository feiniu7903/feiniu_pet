package com.lvmama.prd.logic;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductBranchItem;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.prd.dao.MetaProductBranchDAO;
import com.lvmama.prd.dao.MetaTimePriceDAO;
import com.lvmama.prd.dao.ProdProductBranchItemDAO;

public class ProductSellableLogic {

	private MetaProductBranchDAO metaProductBranchDAO;
	private MetaTimePriceDAO metaTimePriceDAO;
	private ProdProductBranchItemDAO prodProductBranchItemDAO;
	/**
	 * @author yangbin
	 * change:2012-2-23 上午11:04:02
	 * @param prodBranchId 类别
	 * @param quantity
	 * @param visitTime
	 * @return
	 */
	public boolean isProductSellable(Long prodBranchId,Long quantity,Date visitTime) {
		List<ProdProductBranchItem> list=prodProductBranchItemDAO.selectBranchItemByProdBranchId(prodBranchId);
		boolean flag=true;
		for(ProdProductBranchItem item:list){
			MetaProductBranch metaBranch=metaProductBranchDAO.selectBrachByPrimaryKey(item.getMetaBranchId());			
			TimePrice timePrice=metaTimePriceDAO.getMetaTimePriceByIdAndDate(item.getMetaBranchId(), visitTime);
			if(metaBranch==null||timePrice==null){
				return false;
			}
			
			Long decreaseStock=quantity*item.getQuantity();
			if(metaBranch.isTotalDecrease()){
				flag=metaBranch.isSellable(decreaseStock,timePrice);
			}else{
				flag=timePrice.isSellable(decreaseStock);
			}
			if(!flag){
				return flag;
			}
		}
		return flag;
	}
	
	/**
	 * 判断一个采购类别指定的日期能否销售
	 * @param metaBranch
	 * @param decreaseStock 数量
	 * @param visitTime
	 * @return
	 */
	public boolean isMetaProductSellable(final MetaProductBranch metaBranch,Long decreaseStock,Date visitTime){
		TimePrice timePrice=metaTimePriceDAO.getMetaTimePriceByIdAndDate(metaBranch.getMetaBranchId(), visitTime);
		if(timePrice==null){
			return false;
		}		
		boolean flag=false;
		if(metaBranch.isTotalDecrease()){
			flag=metaBranch.isSellable(decreaseStock,timePrice);
		}else{
			flag=timePrice.isSellable(decreaseStock);
		}		
		return flag;
	}
	
	public void setMetaTimePriceDAO(MetaTimePriceDAO metaTimePriceDAO) {
		this.metaTimePriceDAO = metaTimePriceDAO;
	}

	/**
	 * @param prodProductBranchItemDAO the prodProductBranchItemDAO to set
	 */
	public void setProdProductBranchItemDAO(
			ProdProductBranchItemDAO prodProductBranchItemDAO) {
		this.prodProductBranchItemDAO = prodProductBranchItemDAO;
	}

	/**
	 * @param metaProductBranchDAO the metaProductBranchDAO to set
	 */
	public void setMetaProductBranchDAO(MetaProductBranchDAO metaProductBranchDAO) {
		this.metaProductBranchDAO = metaProductBranchDAO;
	}
	
	
}
