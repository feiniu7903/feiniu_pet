/**
 * 
 */
package com.lvmama.pet.mark.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponProduct;
import com.lvmama.comm.pet.service.mark.MarkCouponProductService;
import com.lvmama.pet.mark.dao.MarkCouponDAO;
import com.lvmama.pet.mark.dao.MarkCouponProductDAO;

/**
 * 产品和优惠关联servcie
 * @author liuyi
 *
 */
public class MarkCouponProductServiceImpl implements MarkCouponProductService {

	private MarkCouponProductDAO markCouponProductDAO;
	private MarkCouponDAO markCouponDAO;
	
	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.mark.MarkCouponProductService#selectMarkCouponProductRowCount(java.util.Map)
	 */
	@Override
	public Long selectMarkCouponProductRowCount(Map<String, Object> param) {
		return markCouponProductDAO.selectCount(param);
	}

	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.mark.MarkCouponProductService#selecMarkCouponProductByParam(java.util.Map)
	 */
	@Override
	public List<MarkCouponProduct> selectMarkCouponProductByParam(Map<String, Object> param) {
		return markCouponProductDAO.select(param);
	}
	
	/**
	 * 根据PK条件返回优惠券和产品对应关系
	 * @param  couponProductId(PK)
	 * @return 优惠券和产品对应关系列表
	 */
	public MarkCouponProduct selectMarkCouponProdByPK(Long couponProductId){
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("couponProductId", couponProductId);
		List<MarkCouponProduct> markCouponProductList = markCouponProductDAO.select(parameters);
		
		if(markCouponProductList != null && markCouponProductList.size() == 1){
			return markCouponProductList.get(0);
		}else{
			return null;
		}
	}
	
	/**
	 * 根据优惠ID(couponId)条件返回所有符合条件的优惠券和产品对应关系
	 * @param param 查询条件
	 * @return 优惠券和产品对应关系列表
	 */
	public List<MarkCouponProduct> selectMarkCouponProdByCouponId(Long couponId){
		Map<String,Object> parameters = new HashMap<String,Object>();
		parameters.put("couponId", couponId);
		return markCouponProductDAO.select(parameters);
	}
	
	/* (non-Javadoc)
	 * @see com.lvmama.comm.pet.service.mark.MarkCouponProductService#getSuitableMarkCouponProduct(java.lang.Long, java.lang.Long, java.lang.String)
	 */
	@Override
	public MarkCouponProduct getSuitableMarkCouponProduct(Long markCouponId, Long productId, String subProductType) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("couponId", markCouponId);
		param.put("productId", productId);
		param.put("subProductType", subProductType);
		List<MarkCouponProduct> markCouponProductList = markCouponProductDAO.select(param);
		if(markCouponProductList != null && markCouponProductList.size() == 1){
			return markCouponProductList.get(0);
		}else{
			return null;
		}
	}
	
	public Long insert(MarkCouponProduct markCouponProduct){
		return markCouponProductDAO.insert(markCouponProduct);
	}

	public void delete(MarkCouponProduct markCouponProduct){
		markCouponProductDAO.delete(markCouponProduct);
	}
	
	public void deleteMarkCouponProdByMap(Map<String,Object> parameters){
		markCouponProductDAO.deleteMarkCouponProdByMap(parameters);
	}
	
	public void update(MarkCouponProduct markCouponProduct){
		markCouponProductDAO.update(markCouponProduct);
	}
	
	public String checkProductIdOrSubProductTypeAgainBound(MarkCouponProduct mcp){
		return markCouponProductDAO.checkProductIdOrSubProductTypeAgainBound(mcp);
	}
	
	
	public void saveProductCoupon(Long couponId, List<Long> productIdList) {
		for (Long pid : productIdList) {
			Map<String, Object> param = new HashMap<String, Object>();
			param.put("couponId", couponId);
			param.put("productId", pid);
			MarkCouponProduct mcp = this.getSuitableMarkCouponProduct(couponId, pid, null);
			MarkCoupon mc = this.markCouponDAO.selectByPrimaryKey(couponId);
			if (mcp == null && mc != null) {
				mcp = new MarkCouponProduct();
				mcp.setAmount(null);
				mcp.setCouponId(couponId);
				mcp.setProductId(pid);
				mcp.setAmount(mc.getFavorTypeAmount());
				this.markCouponProductDAO.insert(mcp);
			}
		}
	}

	public void setMarkCouponProductDAO(MarkCouponProductDAO markCouponProductDAO) {
		this.markCouponProductDAO = markCouponProductDAO;
	}

	public void setMarkCouponDAO(MarkCouponDAO markCouponDAO) {
		this.markCouponDAO = markCouponDAO;
	}
}
