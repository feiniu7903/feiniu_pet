package com.lvmama.pet.shop.dao;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.shop.ShopCooperationCoupon;

public class ShopCooperationCouponDAO extends BaseIbatisDAO {
	public static final String SPACE = "SHOP_COOPERATION_COUPON.";
	/**
	 * 批量插入合作网站优惠券
	 * @param list
	 * @return
	 */
	public int batchInsertCoupon(final List<ShopCooperationCoupon> list) {
		 return (Integer) super.execute(new SqlMapClientCallback<Object>() { 
		        public Object doInSqlMapClient(SqlMapExecutor executor) throws SQLException { 
		            executor.startBatch(); 
		            for (ShopCooperationCoupon coupon : list) { 
		                executor.insert(SPACE+"insert", coupon); 
		            } 
		            return executor.executeBatch(); 
		        } 
		    }); 
	}
	/**
	 * 根据条件删除合作网站优惠券
	 * @param list
	 * @return
	 */
	public int batchDeleteCoupon(final Map<String, Object> parameters) {
		return super.update(SPACE+"delete",parameters);
	}
	/**
	 * 根据条件查询合作网站优惠券列表
	 * @param list
	 * @return
	 */
	public List<ShopCooperationCoupon> query(final Map<String, Object> parameters) {
		return super.queryForList(SPACE+"query", parameters);
	}
	/**
	 * 根据条件查询合作网站优惠券总数
	 * @param list
	 * @return
	 */
	public Long count(final Map<String, Object> parameters) {
		return (Long)super.queryForObject(SPACE+"count", parameters);
	}
	/**
	 * 根据条件查询合作网站优惠券
	 * @param list
	 * @return
	 */
	public ShopCooperationCoupon queryByParameters(
		final Map<String, Object> parameters) {
		return (ShopCooperationCoupon)super.queryForObject(SPACE+"query", parameters);
	}
	/**
	 * 根据主键查询合作网站优惠券
	 * @param list
	 * @return
	 */
	public ShopCooperationCoupon queryCouponByKey(final Long key) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("couponId", key);
		return queryByParameters(map);
	}
}
