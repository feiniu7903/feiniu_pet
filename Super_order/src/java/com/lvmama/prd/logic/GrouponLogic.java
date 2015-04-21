package com.lvmama.prd.logic;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.prd.dao.ProdProductDAO;

public class GrouponLogic {
	private ProdProductDAO prodProductDAO;
	private MarkCouponService markCouponService;
	
	public Map<String,Object> getTodayGroupProduct(Long productId) {
		
		Map<String,Object> paramMap  = new HashMap<String,Object>();
		paramMap.put("productId", productId);
		paramMap.put("productChannel","TUANGOU");
		Long now = System.currentTimeMillis();
		Map<String,Object> returnMap   = null;
		
		Map rowMap = prodProductDAO.queryOnlineProductByProductId(paramMap);
		if(rowMap!=null){
			returnMap = new HashMap<String,Object>();
			ProdProduct  pp = new ProdProduct();
			pp.setProductId(((Long)rowMap.get("PRODUCT_ID")));
			pp.setProductName((String)rowMap.get("PRODUCT_NAME"));
			pp.setSellPrice(rowMap.get("SELL_PRICE")==null?null:((Long)rowMap.get("SELL_PRICE")));
			pp.setMarketPrice(rowMap.get("MARKET_PRICE")==null?null:((Long)rowMap.get("MARKET_PRICE")));
			pp.setSubProductType((String)rowMap.get("SUB_PRODUCT_TYPE"));
			pp.setOfflineTime((Date)rowMap.get("OFFLINE_TIME"));
			pp.setProductType((String)rowMap.get("PRODUCT_TYPE"));
			pp.setSmallImage((String)rowMap.get("SMALL_IMAGE"));
			Long pageId = (Long)rowMap.get("PAGE_ID");
			
			
			long offlineTime = 0;
			
			returnMap.put("prodProduct", pp);
			
			//String mgrRec = ReplaceEnter.replaceEnterRn(rowMap.get("MANAGERRECOMMEND")==null?"":(String)rowMap.get("MANAGERRECOMMEND"),"MANAGERRECOMMEND");
			returnMap.put("MANAGERRECOMMEND", rowMap.get("MANAGERRECOMMEND"));
			returnMap.put("TAG_NAME", rowMap.get("TAG_NAME"));
			returnMap.put("CITY", rowMap.get("CITY"));
			returnMap.put("MIN_GROUP_SIZE", rowMap.get("MIN_GROUP_SIZE"));
			returnMap.put("IMPORTMENT_CLEW", rowMap.get("IMPORTMENT_CLEW"));
			
			returnMap.put("orderCount", rowMap.get("ORDER_COUNT"));
			if(pp.getMarketPriceYuan()>0){
				returnMap.put("discount",new BigDecimal(pp.getSellPriceYuan()/pp.getMarketPriceYuan()*10).setScale(1,BigDecimal.ROUND_FLOOR).doubleValue() );
			}
			if(pp.getOfflineTime()!=null){
				offlineTime = pp.getOfflineTime().getTime();
			}else{
				offlineTime=-1;
			}
			returnMap.put("diff", offlineTime - now);
			returnMap.put("pageId", pageId);
			//获取子产品类型 用户查询产品优惠券/活动 add by yanggan  20120301
			String subProductType = pp.getSubProductType();
	    	Map<String,Object> map = new HashMap<String,Object>();
	    	map.put("productId", productId);
	    	map.put("subProductType", subProductType);
			List<MarkCoupon> productCouponList = markCouponService.selectProductCanUseMarkCoupon(map);
			returnMap.put("productCouponList", productCouponList);
		}
		return returnMap;
	}

	public ProdProductDAO getProdProductDAO() {
		return prodProductDAO;
	}

	public void setProdProductDAO(ProdProductDAO prodProductDAO) {
		this.prodProductDAO = prodProductDAO;
	}

	public void setMarkCouponService(MarkCouponService markCouponService) {
		this.markCouponService = markCouponService;
	}

	
}
