package com.lvmama.pet.mark.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mark.MarkCoupon;

public class MarkCouponDAO extends BaseIbatisDAO {

//    /**
//     * 根据主键删除
//     * @param couponId
//     * @return
//     */
//    public int deleteByPrimaryKey(Long couponId) {
//        MarkCoupon key = new MarkCoupon();
//        key.setCouponId(couponId);
//        int rows = getSqlMapClientTemplate().delete("MARK_COUPON.deleteByPrimaryKey", key);
//        return rows;
//    }

    /**
     * 插入数据
     * @param record
     * @return
     */
    public MarkCoupon insert(MarkCoupon markCoupon) {
    	super.insert("MARK_COUPON.insert", markCoupon);
        return markCoupon;
    }

    /**
     * 根据主键筛选数据
     * @param couponId
     * @return
     */
    public MarkCoupon selectByPrimaryKey(Long couponId) {
        MarkCoupon key = new MarkCoupon();
        key.setCouponId(couponId);
        MarkCoupon record = (MarkCoupon) super.queryForObject("MARK_COUPON.selectByPrimaryKey", key);
        return record;
    }

    /**
     * 根据主键更新数据
     * @param record
     * @return
     */
    public int updateByPrimaryKey(MarkCoupon record) {
        int rows = super.update("MARK_COUPON.updateByPrimaryKeySelective", record);
        return rows;
    }
    
    /**
     * 根据map参数筛选数据
     * @param param
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<MarkCoupon> selectByParam(Map<String,Object> param){
    	return super.queryForList("MARK_COUPON.selectByParam",param);
    }
    
    /**
     * 根据map参数筛选数据
     * @param param
     * @return
     */
    public Integer selectRowCount(Map<String,Object> param){
		Integer count = 0;
		count = (Integer) super.queryForObject("MARK_COUPON.selectCountByParam",param);
		return count;
	}
    /**
     * 通过产品ID，子产品类型查询出所有的优惠券或优惠活动 (优惠绑定类型为：PRODUCT)
     * @param productId 产品ID
     * @param subProductType 子产品类型
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<MarkCoupon> selectProductCanUseMarkCoupon(Map<String,Object> map){
    	// modify by yanggan 2012-03-01
//    	Map<String,Object> map = new HashMap<String,Object>();
//    	map.put("productId", productId);
//    	map.put("subProductType", subProductType);
    	return super.queryForList("MARK_COUPON.selectProductCanUseMarkCoupon",map);
    }
    /**
     * 通过产品ID，子产品类型查询查询出所有的优惠券或优惠活动(产品设置为可使用优惠券)
     * @param productId 产品ID
     * @param subProductType 子产品类型
     */
    @SuppressWarnings("unchecked")
    public List<MarkCoupon> selectProductValidMarkCoupon(Map<String,Object> map){
    	return super.queryForList("MARK_COUPON.selectProductValidMarkCoupon",map);
    }
    /**
     * 获得全场通用优惠券/优惠活动
     * @param map
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<MarkCoupon> selectAllCanUseMarkCoupon(Map<String,Object> map){
    	List<MarkCoupon> list = super.queryForList("MARK_COUPON.selectAllCanUseMarkCoupon",map);
    	return list;
    }
    
    
//   
//    public Long countCodeByProductId(Map<String,Object> param){
//    	return (Long) this.getSqlMapClientTemplate().queryForObject("MARK_COUPON.countCodeByProductId",param);
//    }
    /**
     * 通过产品ID，子产品类型查询出所有的优惠券或优惠活动 
     * @param productId 产品ID
     * @param subProductType 子产品类型
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<MarkCoupon> selectOrderMarkCoupon(Long productId,String subProductType){
    	// modify by yanggan 2012-03-02
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("productId", productId);
    	map.put("subProductType", subProductType);
    	return super.queryForList("MARK_COUPON.selectAllCanUseMarkCoupon",map);
    }
    @SuppressWarnings("unchecked")
	public List<MarkCoupon> selectOrderMarkCoupon(List<Long> productIds,List<String> subProductTypes) {
    	Map<String,Object> map = new HashMap<String,Object>();
    	map.put("productIds", productIds);
    	map.put("subProductTypes", subProductTypes);
    	return super.queryForList("MARK_COUPON.selectAllCanUseMarkCoupon",map);
    }
    
    /**
     * 根据主键更新已经使用的优惠金额数据
     * @param map
     * @return
     */
    public int updateUsedCouponByPK(Map<String,Object> map) {
        int rows = super.update("MARK_COUPON.updateUsedCouponByPK", map);
        return rows;
    }
    /**
     * 查询优惠的最大优惠有效期间(区间),用于构造TAG
     * @param param
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<MarkCoupon> selectValidDate(Map<String, Object> param) {
		return super.queryForList("MARK_COUPON.selectValidDate",param);
	}
}