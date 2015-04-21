package com.lvmama.pet.shop.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.lvmama.comm.pet.po.shop.ShopCooperationCoupon;
import com.lvmama.comm.pet.po.shop.ShopProduct;
import com.lvmama.comm.pet.service.shop.ShopCooperationCouponService;
import com.lvmama.comm.pet.service.shop.ShopLogService;
import com.lvmama.pet.shop.dao.ShopCooperationCouponDAO;
import com.lvmama.pet.shop.dao.ShopProductDAO;

public class ShopCooperationCouponServiceImpl implements
		ShopCooperationCouponService {
	private final Logger LOG = Logger.getLogger(ShopCooperationCouponServiceImpl.class);
	private ShopCooperationCouponDAO shopCooperationCouponDAO;
	private ShopProductDAO shopProductDAO;
	private ShopLogService shopLogService;
	
	@Override
	public int batchInsertCoupon(final List<ShopCooperationCoupon> list,final boolean isClean,final Long productId,final String operatorId) {
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("productId", productId);
		int oldData = 0;
		if(isClean){
			oldData = shopCooperationCouponDAO.batchDeleteCoupon(parameters);
		}
		int result =shopCooperationCouponDAO.batchInsertCoupon(list);
		updateProductStock(parameters,productId);
		shopLogService.insert(getLog(list,isClean,oldData), productId, "SHOP_PRODUCT", "PRODUCT_UPDATE", operatorId);
		return result;
	}

	@Override
	public int batchDeleteCoupon(final Map<String, Object> parameters) {
		return shopCooperationCouponDAO.batchDeleteCoupon(parameters);
	}

	@Override
	public List<ShopCooperationCoupon> query(final Map<String, Object> parameters) {
		return shopCooperationCouponDAO.query(parameters);
	}

	@Override
	public Long count(final Map<String, Object> parameters) {
		return shopCooperationCouponDAO.count(parameters);
	}
	
	@Override
	public ShopCooperationCoupon queryByParameters(
			final Map<String, Object> parameters) {
		return shopCooperationCouponDAO.queryByParameters(parameters);
	}

	@Override
	public ShopCooperationCoupon queryCouponByKey(final Long key) {
		return shopCooperationCouponDAO.queryCouponByKey(key);
	}

	/**
	 * 根据产品编号取出一条合作网站优惠券信息，并把它设置为已使用
	 * @param productId
	 * @return
	 */
	@Override
	public List<String> subtractStock(final Long productId,final int quantity){
		Map<String, Object> parameters = new HashMap<String,Object>();
		parameters.put("productId", productId);
		parameters.put("_endRow", 1+quantity);
		parameters.put("_startRow", 1);
		List<String> info=new ArrayList<String>();
		List<ShopCooperationCoupon> list = shopCooperationCouponDAO.query(parameters);
		if(null != list && !list.isEmpty() && quantity == list.size() ){
			List<Long> idList = new ArrayList<Long>();
			for(ShopCooperationCoupon coupon:list){
				idList.add(coupon.getId());
				info.add(coupon.getCouponInfo());
			}
			parameters.clear();
			parameters.put("idList",idList);
			int i=shopCooperationCouponDAO.batchDeleteCoupon(parameters);

			parameters.clear();
			parameters.put("productId", productId);
			updateProductStock(parameters,productId);
			if(i!=quantity){
				LOG.info("productId="+productId+" "+" quatity="+quantity+" delete result number is "+i);
			}
		}
		return info;
	}
	/**
	 * 修改产品信息上的库存
	 * @param parameters
	 * @param productId
	 */
	private void updateProductStock(final Map<String,Object> parameters,final Long productId){
		Long count = shopCooperationCouponDAO.count(parameters);
		ShopProduct shopProduct =shopProductDAO.queryByPk(productId);
		shopProduct.setStocks(count);
		shopProductDAO.update(shopProduct);
	}
	
	private String getLog(final List<ShopCooperationCoupon> list,final boolean isClean,final int oldData){
		StringBuffer result = new StringBuffer();
		if(isClean){
			result.append("删除原库存").append(oldData).append("条，");
		}
		result.append("新增库存"+list.size()+"条");
		return result.toString();
	}
	public ShopCooperationCouponDAO getShopCooperationCouponDAO() {
		return shopCooperationCouponDAO;
	}

	public void setShopCooperationCouponDAO(
			ShopCooperationCouponDAO shopCooperationCouponDAO) {
		this.shopCooperationCouponDAO = shopCooperationCouponDAO;
	}

	public ShopProductDAO getShopProductDAO() {
		return shopProductDAO;
	}

	public void setShopProductDAO(ShopProductDAO shopProductDAO) {
		this.shopProductDAO = shopProductDAO;
	}

	public ShopLogService getShopLogService() {
		return shopLogService;
	}

	public void setShopLogService(ShopLogService shopLogService) {
		this.shopLogService = shopLogService;
	}

}
