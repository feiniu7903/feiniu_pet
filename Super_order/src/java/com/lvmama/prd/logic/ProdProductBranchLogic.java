package com.lvmama.prd.logic;

import java.util.Date;

import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.utils.ord.TimePriceUtil;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.dao.ProdProductBranchItemDAO;
import com.lvmama.prd.dao.ProdProductDAO;

public class ProdProductBranchLogic {

	private ProdProductBranchItemDAO prodProductBranchItemDAO;
	private ProductTimePriceLogic productTimePriceLogic;
	private ProdProductDAO prodProductDAO;
	private ProdProductBranchDAO prodProductBranchDAO;
	
	/**
	 * 需要检查最晚取消时间在废单时间一小时之前的使用该方法
	 * @param branch
	 * @param visitTime
	 * @param initialProduct 是否需要初始化产品信息
	 * @param checkCancelHour 是否需要检查当前时间在最晚废单时间前一小时
	 * @return
	 */
	public ProdProductBranch fill(ProdProductBranch branch,Date visitTime,boolean initialProduct,boolean checkOnline,boolean checkCancelHour,boolean todayOrderAble){
		if(initialProduct){
			ProdProduct product=prodProductDAO.selectProductDetailByPrimaryKey(branch.getProductId());
			branch.setProdProduct(product);
			
		}
		if(branch.getProdProduct()==null||branch.getProdProduct().isDisabled()){
			return null;
		}		
		if(checkOnline){
			if(!branch.getProdProduct().isOnLine()||!branch.hasOnline()){
				return null;
			}
		}
		//只针对非超级自由行
		if (!branch.getProdProduct().hasSelfPack()&&prodProductBranchItemDAO.countProductItem(branch.getProdBranchId())==0){// 如果产品进行了打包才加入到有效的相关产品列表中
			return null;
		}
		if(visitTime!=null){//如果时间为空跳过时间及库存		
			if(!branch.getProdProduct().hasSelfPack()){
				TimePrice price = null;
				if(todayOrderAble) {
					price = productTimePriceLogic.calcCurrentProdTimePrice(branch.getProdBranchId(),visitTime);
				} else {
					price = productTimePriceLogic.calcProdTimePrice(branch.getProdBranchId(),visitTime);
				}
				if (price == null) {
					return null;
				}
				
				if(checkCancelHour&& !TimePriceUtil.checkLastCancel(price)){
					return null;
				}
				branch.setSellPrice(price.getPrice());			
				branch.setMarketPrice(price.getMarketPrice());
				branch.setStock(price.getDayStock());
				if(branch.getStock()==0L&&price.isOverSaleAble()){//能超卖时显示有库存
					branch.setStock(-1);
				}
			}
			//只针对非超级自由行
			if(!branch.getProdProduct().hasSelfPack()&&branch.getStock()!=-1&&(branch.getStock()<1L||branch.getStock()<branch.getMinimum())){
				return null;
			}
		}
			
		return branch;
	}
	
	
	/**
	 * 不需要检查最晚取消时间在废单时间一小时之前的使用该方法
	 * @param branch
	 * @param visitTime
	 * @param initialProduct 是否需要初始化产品信息
	 * @return
	 */
	public ProdProductBranch fill(ProdProductBranch branch,Date visitTime,boolean initialProduct,boolean checkOnline){
		return fill(branch, visitTime,initialProduct,checkOnline,false,false);
	}
	
	public ProdProductBranch testFill(ProdProductBranch branch,Date visitTime){
		return fill(branch,visitTime,true,false);
	}
	
	public ProdProductBranch fill(ProdProductBranch branch,Date visitTime){
		return fill(branch,visitTime,true,true);
	}
	
	/**
	 * 查询产品类别，顺便初始化产品
	 * @param prodBranchId
	 * @return
	 */
	public ProdProductBranch getProdProductBranch(final Long prodBranchId){
		ProdProductBranch ppb = prodProductBranchDAO.selectByPrimaryKey(prodBranchId);
		if(ppb!=null){
			ppb.setProdProduct(prodProductDAO.selectProductDetailByPrimaryKey(ppb.getProductId()));
		}
		return ppb;
	}

	/**
	 * @param prodProductBranchItemDAO the prodProductBranchItemDAO to set
	 */
	public void setProdProductBranchItemDAO(
			ProdProductBranchItemDAO prodProductBranchItemDAO) {
		this.prodProductBranchItemDAO = prodProductBranchItemDAO;
	}

	/**
	 * @param productTimePriceLogic the productTimePriceLogic to set
	 */
	public void setProductTimePriceLogic(ProductTimePriceLogic productTimePriceLogic) {
		this.productTimePriceLogic = productTimePriceLogic;
	}

	/**
	 * @param prodProductDAO the prodProductDAO to set
	 */
	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}


	public void setProdProductBranchDAO(ProdProductBranchDAO prodProductBranchDAO) {
		this.prodProductBranchDAO = prodProductBranchDAO;
	}
	
	
}
