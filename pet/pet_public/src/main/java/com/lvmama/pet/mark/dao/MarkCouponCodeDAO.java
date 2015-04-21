package com.lvmama.pet.mark.dao;



import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;

public class MarkCouponCodeDAO extends BaseIbatisDAO {
//    /**
//     * 将code入mark_code_temp表
//     */
//    public void insertCodeTemp(String code){
//    	this.getSqlMapClientTemplate().insert("MARK_COUPON_CODE.insertCodeTemp",code);
//    	
//    }
//    
//    /**
//     * 返回mark_code_temp表的数据量
//     */
//    public Long selectCodeTempCount(){
//    	return (Long) this.getSqlMapClientTemplate().queryForObject("MARK_COUPON_CODE.selectCodeTempCount");
//    }
//    
//    /**
//     * 删除mark_code_temp表的重复数据
//     */
//    public void deleteCodeTempRepeat(){
//    	this.getSqlMapClientTemplate().delete("MARK_COUPON_CODE.deleteCodeTempRepeat");
//    }
//    
//    /**
//     * mark_code_temp表有数据，往mark_coupon_code插入数据
//     */
//    public void mergeTempData(String couponId){
//    	this.getSqlMapClientTemplate().queryForObject("MARK_COUPON_CODE.mergeTempData",couponId);
//    }
//    
//    /**
//     * 批量插入mark_coupon_code
//     */
//    public <T> void insertMarkCouponCodeBatch(final List<MarkCouponCode> couponCodeList) {
//    	this.getSqlMapClientTemplate().execute(new SqlMapClientCallback<T>() {
//
//			@Override
//			public T doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
//				executor.startBatch();
//				for (MarkCouponCode couponCode : couponCodeList) {
//					executor.insert("MARK_COUPON_CODE.insertMarkCouponCode",couponCode);
//				}
//				executor.executeBatch();
//				return null;
//			}
//		});
//    }
//    
//    /**
//     * 清空mark_code_temp
//     */
//    public int deleteAllCodeTemp(){
//    	int rows = getSqlMapClientTemplate().delete("MARK_COUPON_CODE.deleteAllCodeTemp");
//        return rows;
//    }
//    
//    /**
//     * 单条插入mark_coupon_code
//     */
//    public Long insert(MarkCouponCode record) {
//        Object newKey = getSqlMapClientTemplate().insert("MARK_COUPON_CODE.insert", record);
//        return (Long) newKey;
//    }
//    
//    /**
//     * 单条插入mark_coupon_code
//     * 返回：couponCodeId 序列号
//     */
//    public Long insertSelective(MarkCouponCode record) {
//        Object newKey = getSqlMapClientTemplate().insert("MARK_COUPON_CODE.insertSelective", record);
//        return (Long) newKey;
//    }
//    
//    /**
//     * 根据couponCodeId返回优惠券code信息
//     */
//    public MarkCouponCode selectByPrimaryKey(Long couponCodeId) {
//        MarkCouponCode key = new MarkCouponCode();
//        key.setCouponCodeId(couponCodeId);
//        MarkCouponCode record = (MarkCouponCode) getSqlMapClientTemplate().queryForObject("MARK_COUPON_CODE.selectByPrimaryKey", key);
//        return record;
//    }
//    
//    
//    
//    /**
//     * 根据couponid,#_startRow#,#_endRow#
//     * 查询mark_coupon_code表,用于分页
//     */
//    public List<MarkCouponCode> selectByCouponId(Map<String,Object> param){
//    	return this.getSqlMapClientTemplate().queryForList("MARK_COUPON_CODE.selectByCouponId",param);
//    }
//    
//    /**
//     * 根据couponid
//     * 查询mark_coupon_code表
//     */
//    public List<MarkCouponCode> selectCouponCodeByCouponId(Long couponId){
//    	return this.getSqlMapClientTemplate().queryForList("MARK_COUPON_CODE.selectCouponCodeByCouponId",couponId);
//    }
//    
//    /**
//     * 查询mark_coupon_code表中指定couponid的数量
//     */
//    public Long selectCountByCouponId(Long couponId){
//    	return (Long) this.getSqlMapClientTemplate().queryForObject("MARK_COUPON_CODE.selectCountByCouponId",couponId);
//    }
//    
//    /**
//     * 查询mark_coupon_code表中指定couponid的数量
//     * 判断是否mark_coupon_code是否存在指定couponid的数据
//     */
//    public Long countHasCode(Long couponId){
//    	return (Long) this.getSqlMapClientTemplate().queryForObject("MARK_COUPON_CODE.countHasCode",couponId);
//    }
//    
//    /**
//     * 查询mark_coupon_code表中指定couponcode的数据
//     */
//    public  List<MarkCouponCode> selectByCouponCode(String code){
//    	return this.getSqlMapClientTemplate().queryForList("MARK_COUPON_CODE.selectByCouponCode",code);
//    }
//    
//    /**
//     * 查询mark_coupon_code表中指定couponid对应的所有markcouponcode数据
//     */
//    public List<MarkCouponCode> selectAllCodeByCouponId(Long couponId){
//    	List<MarkCouponCode> list = this.getSqlMapClientTemplate().queryForList("MARK_COUPON_CODE.selectAllCodesByCouponId",couponId);
//    	return list; 
//    }
//    
//    /**
//     * 根据couponid和couponcode查询mark_coupon_code表中所有符合条件的mark_coupon_code数据
//     */
//    public Long selectCountByCode(Long couponId,String couponCode){
//    	Map<String,Object> param = new HashMap<String, Object>();
//    	param.put("couponId", couponId);
//    	param.put("couponCode", couponCode);
//    	return (Long) this.getSqlMapClientTemplate().queryForObject("MARK_COUPON_CODE.countCouponCodeByCodeAndCouponId",param);
//    }
//    
//    /**
//     * 根据传入的map中的couponid和couponcode对应值查询mark_coupon_code表中所有符合条件的mark_coupon_code数量
//     */
//    public Long countCouponCodeByCodeAndCouponId(Map param){
//    	return (Long) this.getSqlMapClientTemplate().queryForObject("MARK_COUPON_CODE.countCouponCodeByCodeAndCouponId",param);
//    }
//    
//    /**
//     * 根据传入的map中的couponid和couponcode对应值查询mark_coupon_code表中所有符合条件的mark_coupon_code数据
//     */
//    public List<MarkCouponCode> selectCouponCodeByCodeAndCouponId(Long couponId,String couponCode){
//    	Map<String,Object> param = new HashMap<String, Object>();
//    	param.put("couponId", couponId);
//    	param.put("couponCode", couponCode);
//    	return this.getSqlMapClientTemplate().queryForList("MARK_COUPON_CODE.selectCouponCodeByCodeAndCouponId",param);
//    }    
//    
//    
//    /**
//     * 关联MARK_COUPON_RELATE_USER表查询出符合条件的markcouponcode数量
//     */
//    public Long countByRelateUserId(String userId) {
//    	return (Long)this.getSqlMapClientTemplate().queryForObject("MARK_COUPON_CODE.countByRelateUserId",userId);
//    }
//    
//    /**
//     * 关联MARK_COUPON_RELATE_USER表,MARK_COUPON表
//     * 返回MARK_COUPON_RELATE_USER.USER_ID,USE_COUNT,USED_COUNT,USE_AMOUNT,USED_AMOUNT
//     */
//    public List<Map<String,Object>> selectByRelateUserAmount(Map<String,Object> param){
//    	return this.getSqlMapClientTemplate().queryForList("MARK_COUPON_CODE.selectByRelateUserAmount",param);
//    }
//    
	
	
	public MarkCouponCode saveMarkCouponCode(final MarkCouponCode markCouponCode) {
		super.insert("MARK_COUPON_CODE.insert", markCouponCode);
		return markCouponCode;
	}
	
    /**
     * 根据主键couponCodeId删除数据
     */
    public void deleteByPrimaryKey(final Long couponCodeId) {
    	super.delete("MARK_COUPON_CODE.deleteByPrimaryKey", couponCodeId);
    }
    
    /**
     * 按coupon_code_id
     * 更新mark_coupon_code表字段
     * 返回更新条数
     */
    public MarkCouponCode updateByPrimaryKey(MarkCouponCode markCouponCode) {
    	super.update("MARK_COUPON_CODE.updateByPrimaryKey", markCouponCode);
    	return markCouponCode;
    }    
    
    /**
     * 根据标识查询优惠码
     * @param couponCodeId 标识
     * @return 优惠码
     */
    public MarkCouponCode selectByPrimaryKey(final Long couponCodeId) {
    	return (MarkCouponCode) super.queryForObject("MARK_COUPON_CODE.queryByPrimaryKey",couponCodeId);
    }
    
    /**
     * 根据传入的优惠券号码查找所对应的优惠券批次的标识列表
     * @param couponCode 优惠券号码
     * @return 优惠券批次的标识列表
     * 将传入的优惠券号码 作like操作，查找所有还有此类优惠券号码的优惠券批次的标识号。此方法大量运用于通过优惠券号找优惠券批次及渠道。
     */
    @SuppressWarnings("unchecked")
	public List<Long> selectCouponIdByCouponCode(final String couponCode) {
    	if (StringUtils.isBlank(couponCode)) {
    		return null;
    	} else {
    		return (List<Long>) super.queryForList("MARK_COUPON_CODE.selectCouponIdByCouponCode",couponCode);
    	}
    }
    
    /**
     * 根据查询条件统计符合的优惠码列表
     * @param param 查询条件
     * @return 优惠码列表
     */
    @SuppressWarnings("unchecked")
	public List<MarkCouponCode> selectByParam(final Map<String, Object> param) {
    	return super.queryForList("MARK_COUPON_CODE.selectByParam", param);
    }
 
    /**
     * 根据查询条件统计符合的优惠码总数
     * @param param 查询条件
     * @return 优惠码总数
     */
	public Integer selectRowCount(final Map<String, Object> param) {
    	return (Integer) super.queryForObject("MARK_COUPON_CODE.selectCountByParam", param);
    }
	
	/**
    * 批量插入mark_coupon_code
    */
   public <T> void insertMarkCouponCodeBatch(final List<MarkCouponCode> couponCodeList) {
	   super.execute(new SqlMapClientCallback<T>() {
 			@Override
 			public T doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
 				executor.startBatch();
 				for (MarkCouponCode couponCode : couponCodeList) {
 					executor.insert("MARK_COUPON_CODE.insert",couponCode);
 				}
 				executor.executeBatch();
 				return null;
 			}
 		});
   }
   
   /**
    * 根据用户id和优惠活动id查询优惠券
    * @param param 查询条件
    * @return 优惠码列表
    */
   @SuppressWarnings("unchecked")
	public List<MarkCouponCode> queryByUserAndCoupon(Map<String, Object> param) {
   		return super.queryForList("MARK_COUPON_CODE.queryByUserAndCoupon", param);
   }
	
}