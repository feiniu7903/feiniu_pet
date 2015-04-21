package com.lvmama.pet.businessCoupon.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.businessCoupon.BusinessCoupon;

public class BusinessCouponDAO extends BaseIbatisDAO {
	/**
     * 插入数据
     * @param record
     * @return
     */
    public BusinessCoupon insertBusinessCoupon(BusinessCoupon businessCoupon) {
    	super.insert("BUSINESS_COUPON.insert", businessCoupon);
        return businessCoupon;
    }
    /**
     * 根据主键更新数据
     * @param record
     * @return
     */
    public Integer updateByPrimaryKey(BusinessCoupon record) {
    	Integer rows = super.update("BUSINESS_COUPON.updateByPrimaryKeySelective", record);
        return rows;
    }
    
    /**
     * 根据map参数筛选数据
     * @param param
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<BusinessCoupon> selectByParam(Map<String,Object> param){
    	return super.queryForList("BUSINESS_COUPON.selectByParam",param);
    }
   
    /**
     * 根据map参数筛选数据
     * @param param
     * @return
     */
    public Integer selectRowCount(Map<String,Object> param){
		Integer count = 0;
		count = (Integer) super.queryForObject("BUSINESS_COUPON.selectCountByParam",param);
		return count;
	}
    
    /**
     * 根据策略id集合查询所有优惠策略
     * @param ids
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<BusinessCoupon> selectByIds(Map<String,Object> param){
    	return super.queryForList("BUSINESS_COUPON.selectByIds",param);
    }
    
    /**
     * 查询带有产品信息的策略集合
     * @param param
     * @return
     */
	@SuppressWarnings("unchecked")
	public List<BusinessCoupon> selectWithProdInfo(Map<String, Object> param) {
		return super.queryForList("BUSINESS_COUPON.selectWithProdInfo",param);
	}
	 /**
     * 查询优惠的最大优惠有效期间(区间)
     * @param param
     * @return
     */
	public BusinessCoupon selectValidDate(Map<String, Object> param) {
		return (BusinessCoupon) super.queryForObject("BUSINESS_COUPON.selectValidDate",param);
	}
    /**删除特卖优惠*/
	public Integer delelteFromBusinessCoupon(Long businessCouponId){
		return super.delete("BUSINESS_COUPON.deleteFromBusinessCoupon", businessCouponId);
	}
}
