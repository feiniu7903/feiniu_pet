package com.lvmama.mark.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mark.MarkCouponUsage;

public class MarkCouponUsageDAO extends BaseIbatisDAO {

    public MarkCouponUsageDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long couponCodeId) {
        MarkCouponUsage key = new MarkCouponUsage();
        key.setCouponCodeId(couponCodeId);
        int rows = super.delete("MARK_COUPON_USAGE.deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(MarkCouponUsage record) {
        super.insert("MARK_COUPON_USAGE.insert", record);
    }

    
    public int updateByPrimaryKey(MarkCouponUsage record) {
        int rows = super.update("MARK_COUPON_USAGE.updateByPrimaryKey", record);
        return rows;
    }
    
    
    public MarkCouponUsage selectByPrimaryKey(Long couponCodeId) {
        MarkCouponUsage key = new MarkCouponUsage();
        key.setCouponCodeId(couponCodeId);
        MarkCouponUsage record = (MarkCouponUsage) super.queryForObject("MARK_COUPON_USAGE.selectByPrimaryKey", key);
        return record;
    }
    
    public Long selectCountByParam(Map<String,Object> param){
    	return (Long) super.queryForObject("MARK_COUPON_USAGE.selectCountByParam",param);
    }
    
    public List<MarkCouponUsage> selectByParam(Map<String,Object> param){
    	List<MarkCouponUsage> list = super.queryForList("MARK_COUPON_USAGE.selectByParam",param);
    	return list;
    }
    
    public Long selectSumUsageAmount(Map<String,Object> param){
    	return (Long)super.queryForObject("MARK_COUPON_USAGE.selectSumUsageAmount", param);
    }
    
}