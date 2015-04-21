/**
 * 
 */
package com.lvmama.pet.mark.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.mark.MarkCouponRelateUser;

public class MarkCouponRelateUserDAO extends BaseIbatisDAO {
	private static final Log log = LogFactory.getLog(MarkCouponRelateUserDAO.class);

	public MarkCouponRelateUser selectByCodeId(Long codeId) {
		return (MarkCouponRelateUser)super.queryForObject("MARK_COUPON_RELATE_USER.selectByCodeId", codeId);
	}
	
	public Long insert(MarkCouponRelateUser bindUser) {
		Object newKey = super.insert("MARK_COUPON_RELATE_USER.insert", bindUser);
		return (Long) newKey;
	}
	
	public void deleteByCodeId(Long codeId) {
		super.delete("MARK_COUPON_RELATE_USER.deleteByCodeId", codeId);
	}
	
	 /**
	 * 关联MARK_COUPON_RELATE_USER表查询出符合条件的markcouponcode数据
	 */
	public List<MarkCouponCode> selectByRelateUserId(Map<String,Object> param) {
		return super.queryForList("MARK_COUPON_RELATE_USER.selectByRelateUserId",param);
	}
	
	
	  /**
	  * 关联MARK_COUPON_RELATE_USER表,MARK_COUPON表
	  * 返回满足条件的记录数量
	  */
	 public Long selectCountByRelateUser(Map<String,Object> param){
	 	return (Long)super.queryForObject("MARK_COUPON_RELATE_USER.selectCountByRelateUser",param);
	 }

}