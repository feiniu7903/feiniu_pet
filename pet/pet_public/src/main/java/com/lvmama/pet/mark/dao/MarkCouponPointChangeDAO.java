/**
 * 
 */
package com.lvmama.pet.mark.dao;

import java.util.List;
import java.util.Map;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mark.MarkCouponPointChange;

/**
 * 积分兑换优惠券DAO
 * @author liuyi
 *
 */
public class MarkCouponPointChangeDAO extends BaseIbatisDAO {
	
	
	/**
	 * 查询
	 * @param param
	 * @return
	 */
	public List<MarkCouponPointChange> selectByParam(Map<String,Object> param){
		return super.queryForList("MARK_COUPON_POINT_CHANGE.selectByParam", param);
	}
	
	public Long countByParam(Map<String,Object> param){
		return (Long)super.queryForObject("MARK_COUPON_POINT_CHANGE.selectCountByParam", param);
	}
	
	
    /**
     * 插入数据
     * @param record
     * @return
     */
    public MarkCouponPointChange insert(MarkCouponPointChange markCouponPointChange) {
    	super.insert("MARK_COUPON_POINT_CHANGE.insert", markCouponPointChange);
        return markCouponPointChange;
    }
    
    
    /**
     * 根据主键更新数据
     * @param record
     * @return
     */
    public int updateByPrimaryKey(MarkCouponPointChange markCouponPointChange) {
        int rows = super.update("MARK_COUPON_POINT_CHANGE.updateByPrimaryKey", markCouponPointChange);
        return rows;
    }

    /**
     * 根据主键删除数据
     * @param record
     * @return
     */
	public int deleteByPrimaryKey(Long markCouponPointChangeId) {
	  int rows = super.delete("MARK_COUPON_POINT_CHANGE.deleteByPrimaryKey", markCouponPointChangeId);
	  return rows;
	}
}
