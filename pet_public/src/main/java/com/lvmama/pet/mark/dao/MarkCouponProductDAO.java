package com.lvmama.pet.mark.dao;


import java.util.List;
import java.util.Map;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mark.MarkCouponProduct;

public class MarkCouponProductDAO extends BaseIbatisDAO {

	public MarkCouponProductDAO() {
		super();
	}
	
	public void delete(MarkCouponProduct markCouponProduct){
		super.delete("MARK_COUPON_PRODUCT.delete", markCouponProduct);
	}
	
	public void deleteMarkCouponProdByMap(Map<String,Object> parameters){
		super.delete("MARK_COUPON_PRODUCT.deleteByMap", parameters);
	}
	
	public Long insert(MarkCouponProduct markCouponProduct) {
		Object newKey = super.insert("MARK_COUPON_PRODUCT.insert", markCouponProduct);
		return (Long) newKey;
	}
	
	public int update(MarkCouponProduct markCouponProduct) {
		int rows = super.update("MARK_COUPON_PRODUCT.update", markCouponProduct);
		return rows;
	}

	public List<MarkCouponProduct> select(final Map<String,Object> params) {
		return super.queryForList("MARK_COUPON_PRODUCT.select", params);
	}
	
	public Long selectCount(final Map<String,Object> params){
		return (Long) super.queryForObject("MARK_COUPON_PRODUCT.selectCount",params);
	}
	
	/**
	 * 对于同一优惠活动,检查同一产品是否同时绑定了产品ID和产品子类型.
	 * <br/>1:绑定产品ID时，检查此产品所属的产品子类型是否已绑定当前活动.
	 * <br/>2.绑定产品子类型时，检查属于此产品子类型的产品ID是否已绑定当前活动.
	 * @param mcp MarkCouponProduct对象.
	 * @return 返回ProductId列表或产品子类型.
	 */
	public String checkProductIdOrSubProductTypeAgainBound(MarkCouponProduct mcp) {
		String result = "";
		//如果产品ID等于null,说明当前是在绑定产品子类型.
		if (mcp.getProductId() == null) {
			List<String> subProductTypeList = super.queryForList("MARK_COUPON_PRODUCT.checkByCouponIdAndSubProductType", mcp);
			if (!subProductTypeList.isEmpty()) {
				result = subProductTypeList.toString();
			}
		//如果产品ID不等于null,说明当前是在绑定产品ID.
		} else {
			result = (String)super.queryForObject("MARK_COUPON_PRODUCT.checkByCouponIdAndProductId", mcp); 
		}
		return result;
	}
}