/**
 * 
 */
package com.lvmama.order.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductBranchItem;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.vo.ord.BuyInfo;
import com.lvmama.comm.vo.SupplierProductInfo;
import com.lvmama.prd.dao.MetaProductBranchDAO;
import com.lvmama.prd.dao.MetaTimePriceDAO;
import com.lvmama.prd.dao.ProdProductBranchDAO;
import com.lvmama.prd.dao.ProdProductBranchItemDAO;
import com.lvmama.prd.dao.ProdProductDAO;

/**
 * 订单价格计算、库存检查服务基类
 * @author yangbin
 *
 */
public abstract class OrderCheckService {
	
	protected ProdProductDAO prodProductDAO;
	protected ProdProductBranchDAO prodProductBranchDAO;
	protected ProdProductBranchItemDAO prodProductBranchItemDAO;
	protected MetaProductBranchDAO metaProductBranchDAO;
	protected MetaTimePriceDAO metaTimePriceDAO;
	
	
	/**
	 * 初始化订单相关的订单子项
	 * @param buyInfo
	 * @param order
	 * @return
	 * @exception RuntimeException产品ID与类别上的产品Id不一置时异常
	 */
	protected Map<Long, OrdOrderItemProd> initialOrdItemProdList(final BuyInfo buyInfo, OrdOrder order) 
	{
		List<BuyInfo.Item> itemList=buyInfo.getItemList();
		boolean selfPack=buyInfo.hasSelfPack();
		if (itemList == null || itemList.size() == 0)
			return null;

		Map<Long, OrdOrderItemProd> ordOrderItemProdMap = new HashMap<Long, OrdOrderItemProd>();
		List<OrdOrderItemProd> itemProdList = new ArrayList<OrdOrderItemProd>();
		for (BuyInfo.Item item : itemList) 
		{
			OrdOrderItemProd itemProd = new OrdOrderItemProd();
			List<OrdOrderItemMeta> itemMetaList = new ArrayList<OrdOrderItemMeta>();
			ProdProduct pp = prodProductDAO.selectByPrimaryKey(item.getProductId());
			ProdProductBranch branch=prodProductBranchDAO.selectByPrimaryKey(item.getProductBranchId());
			if(!branch.getProductId().equals(pp.getProductId())){
				//不相同的时候错误处理
				throw new RuntimeException("订单产品类别与产品不一置productId:"+item.getProductId()+",branch productId:"+item.getProductBranchId());
			}
			itemProd.setProductId(pp.getProductId());
			itemProd.setProdBranchId(item.getProductBranchId());
			if(selfPack){
				itemProd.setJourneyProductId(item.getJourneyProductId());
			}
			/**
			 * 统计订单人数时候必须设置订单产品类型
			 */
			itemProd.setProductType(pp.getProductType());
			itemProd.setSubProductType(pp.getSubProductType());
			itemProd.setAdditional(branch.getAdditional());
			itemProd.setQuantity(new Long(item.getQuantity()));
			itemProd.setVisitTime(item.getVisitTime());
			itemProd.setIsDefault(item.getIsDefault());
			itemProd.setTimeInfoList(item.getTimeInfoList()!=null?item.getTimeInfoList():null);

			List<ProdProductBranchItem> ppbiList=prodProductBranchItemDAO.selectBranchItemByProdBranchId(item.getProductBranchId());
			for (ProdProductBranchItem prodProductItem : ppbiList) {
				OrdOrderItemMeta itemMeta = new OrdOrderItemMeta();
				MetaProductBranch metaBranch=metaProductBranchDAO.selectBrachByPrimaryKey(prodProductItem.getMetaBranchId());
				TimePrice timePrice=metaTimePriceDAO.getMetaTimePriceByIdAndDate(prodProductItem.getMetaBranchId(), item.getVisitTime());

				/**
				 * 设置购买数量
				 */
				itemMeta.setQuantity(itemProd.getQuantity());
				itemMeta.setMetaBranchId(metaBranch.getMetaBranchId());
				itemMeta.setMetaProductId(metaBranch.getMetaProductId());
				itemMeta.setProductQuantity(prodProductItem.getQuantity());
				itemMeta.setAdultQuantity(metaBranch.getAdultQuantity());
				itemMeta.setChildQuantity(metaBranch.getChildQuantity());
				if(!hasEmptyAble(buyInfo, metaBranch)){
					itemMeta.setActualSettlementPrice(timePrice.getSettlementPrice());
					itemMeta.setSettlementPrice(timePrice.getSettlementPrice());
				}
				itemMetaList.add(itemMeta);
			}
		
			itemProd.setOrdOrderItemMetas(itemMetaList);
			ordOrderItemProdMap.put(item.getKey(), itemProd);
			itemProdList.add(itemProd);
			
			if(order.getVisitTime() == null) 
			{
				order.setVisitTime(item.getVisitTime());
			} 
			else if(order.getVisitTime().getTime() > item.getVisitTime().getTime()) 
			{
				order.setVisitTime(item.getVisitTime());
			}
		}
		order.setOrdOrderItemProds(itemProdList);
		return ordOrderItemProdMap;
	}

	/**
	 * 允许时间价格表为空的条件
	 * @param buyInfo
	 * @param branch
	 * @return
	 */
	protected boolean hasEmptyAble(BuyInfo buyInfo,MetaProductBranch branch){
		return (buyInfo.hasNotLocalCheck()&&SupplierProductInfo.HANDLE.TRAIN.name().equals(branch.getCheckStockHandle()));
	}
	
	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}


	public void setProdProductBranchDAO(ProdProductBranchDAO prodProductBranchDAO) {
		this.prodProductBranchDAO = prodProductBranchDAO;
	}


	public void setProdProductBranchItemDAO(
			ProdProductBranchItemDAO prodProductBranchItemDAO) {
		this.prodProductBranchItemDAO = prodProductBranchItemDAO;
	}


	public void setMetaProductBranchDAO(MetaProductBranchDAO metaProductBranchDAO) {
		this.metaProductBranchDAO = metaProductBranchDAO;
	}

	public void setMetaTimePriceDAO(MetaTimePriceDAO metaTimePriceDAO) {
		this.metaTimePriceDAO = metaTimePriceDAO;
	}
	
	
}
