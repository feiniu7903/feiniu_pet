package com.lvmama.order.logic;

import java.util.Date;
import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.prd.dao.MetaTimePriceDAO;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.dao.ProdTimePriceDAO;

/**
 * 奖金返现逻辑
 * 
 * @author taiqichao
 *
 */
public class BonusReturnLogic {
	
	private ProdProductService prodProductService;
	
	private MetaTimePriceDAO metaTimePriceDAO;
	
	private ProdProductBranchDAO prodProductBranchDAO;
	
	private ProdTimePriceDAO prodTimePriceDAO;
	
	/**
	 * 获取订单返现金额
	 * @param ordOrder
	 * @return 返现金额(分)
	 */
	public long getOrderBonusReturnAmount(OrdOrder ordOrder){
		//返现总金额(分)
		Long totalBounsReturnAmount=0L;
		List<OrdOrderItemProd> prodItems=ordOrder.getOrdOrderItemProds();
		for (OrdOrderItemProd ordOrderItemProd : prodItems) {
			ProdProduct prod=prodProductService.getProdProductById(ordOrderItemProd.getProductId());
			//支付给驴妈妈并且可返现
			if(prod.isPaymentToLvmama()&&"Y".equals(prod.getIsRefundable())){
				
				//产品返现金额
				Long returnAmount=0L;
				
				//手动返现
				if("Y".equals(prod.getIsManualBonus())&&prod.getMaxCashRefund()!=null
						&&prod.getMaxCashRefund().longValue()>0){
					//返现金额=单个产品销售产品固定返现金额*购买数量
					returnAmount=prod.getMaxCashRefund()*ordOrderItemProd.getQuantity();
				}else{//自动返现
					//结算总价(分)
					Long totalSettlementAmount=0L;
					List<OrdOrderItemMeta> metaItems=ordOrderItemProd.getOrdOrderItemMetas();
					for (OrdOrderItemMeta ordOrderItemMeta : metaItems) {
						//单个产品结算总价=结算价*打包数量
						Long itemSettlementAmount=ordOrderItemMeta.getSettlementPrice()*ordOrderItemMeta.getQuantity()*ordOrderItemMeta.getProductQuantity();
						//累加采购结算价
						totalSettlementAmount+=itemSettlementAmount;
					}
					
					//销售总价=单个产品售价*购买数量
					long totalSellAmount=ordOrderItemProd.getPrice()*ordOrderItemProd.getQuantity();
					
					//毛利(分)=销售价总价-结算价总价
					Long profit=totalSellAmount-totalSettlementAmount;
					if(profit<=0){//负毛利,不返现
						continue;
					}
					
					//产品返现金额(分)=毛利*返现比
					returnAmount=(long)Math.floor(profit*prod.getBounsScaleDouble());
				}
				
				//累加返现金额
				totalBounsReturnAmount+=returnAmount;
				
			}
		}
		
		//处理含有小数的情况，比如:2.5元向上取整数，返2元
		totalBounsReturnAmount=(long)Math.floor(totalBounsReturnAmount/100)*100;
		
		return totalBounsReturnAmount;
	}

	/**
	 * 获取销售产品最低可返现金额
	 * @param product 销售产品
	 * @param defaultBranchId 默认类别id
	 * @param nearDate 最近一天可售日期
	 * @return 最低可返现金额(分)
	 */
	public long getProductBonusReturnAmount(ProdProduct product,Long defaultBranchId,Date nearDate){
		//返现金额
		Long bonusReturnAmount = 0L;
		if (product.isPaymentToLvmama()&&"Y".equals(product.getIsRefundable())) {//支付给驴妈妈并且可返现
			if("Y".equals(product.getIsManualBonus())&&product.getMaxCashRefund()!=null&&product.getMaxCashRefund().longValue()>0){//手动返现
				bonusReturnAmount = product.getMaxCashRefund();
			}else{//自动返现
				if (product.getSellPrice() != null) {
					//取最近可售时间最低售价
					TimePrice timePrice = prodTimePriceDAO.selectLowestPriceByBranchId(defaultBranchId,product.hasSelfPack(),nearDate,product.getShowSaleDays());
					if(null==timePrice){
						return 0L;
					}
					//查询销售类别该天打包的采购产品结算总价
					Long totalSettlementPrice=metaTimePriceDAO.selectTotalSettlementPriceForProdByDate(defaultBranchId, timePrice.getSpecDate());
					//毛利=销售价-结算价
					Long profit=product.getSellPrice()-totalSettlementPrice;
					if(profit<=0){//负毛利,不返现
						return 0L;
					}
					//产品返现金额(分)=毛利*返现比
					bonusReturnAmount=(long)Math.floor(profit*product.getBounsScaleDouble());
				}
			}
		}
		//处理含有小数的情况，比如:2.5元向上取整数，返2元
		bonusReturnAmount=(long)(Math.floor(bonusReturnAmount/100)*100);
		return  bonusReturnAmount;
	}
	
	
	/**
	 * 获取销售产品最低可返现金额
	 * @param product 销售产品
	 * @return 最低可返现金额(分)
	 */
	public long getProductBonusReturnAmount(ProdProduct product){
		//获取默认类比
		ProdProductBranch branch = prodProductBranchDAO.getProductDefaultBranchByProductId(product.getProductId());
		if(null==branch){
			return 0L;
		}
		//取销售产品下类别最近可售日期
		Date nearDate = prodTimePriceDAO.selectNearBranchTimePriceByBranchId(branch.getProdBranchId());
		if(null==nearDate){
			return 0L;
		}
		
		return getProductBonusReturnAmount(product,branch.getProdBranchId(),nearDate);
	}
	
	

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setMetaTimePriceDAO(MetaTimePriceDAO metaTimePriceDAO) {
		this.metaTimePriceDAO = metaTimePriceDAO;
	}

	public void setProdProductBranchDAO(ProdProductBranchDAO prodProductBranchDAO) {
		this.prodProductBranchDAO = prodProductBranchDAO;
	}

	public void setProdTimePriceDAO(ProdTimePriceDAO prodTimePriceDAO) {
		this.prodTimePriceDAO = prodTimePriceDAO;
	}
	
	
	
	
	
	
}
