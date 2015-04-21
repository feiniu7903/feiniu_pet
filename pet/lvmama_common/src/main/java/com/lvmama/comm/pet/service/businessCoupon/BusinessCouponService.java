package com.lvmama.comm.pet.service.businessCoupon;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.businessCoupon.BusinessCoupon;
import com.lvmama.comm.pet.po.prod.ProdProductTag;

public interface BusinessCouponService {
	
	/**
	 * 根据查询条件统计优惠券批次总数
	 * @param param 查询条件
	 * @return 条目数
	 */
	Integer selectBusinessCouponRowCount(Map<String,Object> param);
	
	/**
	 * 根据查询条件返回所有符合条件的优惠策略
	 * @param param 查询条件
	 * @return 
	 */
	List<BusinessCoupon> selectByParam(Map<String,Object> param);
	/**
	 * 保存记录
	 * @param entity 保存对象
	 * @return void
	 */
	BusinessCoupon insertBusinessCoupon(BusinessCoupon entity);
    /**
     * 根据主键更新记录
     * @param record 更新对象
     * @return 返回更新条数
     */
	Integer updateByPrimaryKey(BusinessCoupon record);
	/**
	 *同时保存优惠策略和绑定的产品
	 * @param entity
	 * @param productId
	 * @param branchIds
	 * @return void
	 */
	List<Long> saveBusinessCouponAndBusinessCoupon(BusinessCoupon entity,Long productId,String branchIds);
	/**
	 *同时更新优惠策略和绑定的产品
	 * @param record
	 * @param productId
	 * @param branchIds
	 * @return void
	 */
	void updateBusinessCouponAndBusinessCouponProduct(BusinessCoupon record);
    
    /**
     * 根据策略id集合查询所有策略
     * @param ids
     * @return  优惠策略集合
     */
    List<BusinessCoupon> selectByIDs(Map<String,Object> param);
    
    /**
     * 查询带有产品信息的策略集合
     * @param param
     *  <p>调用此接口要分两步，第一是产品param中产品id和类别id都作为参数查询，第二是只传产品id，
     *     因为优惠策略既可以定义在产品id上，也可以定义在产品某个类别id上，这时候关联表的类别id字段则无值
     *  </P>
     * @return 优惠策略集合
     */
    List<BusinessCoupon> selectWithProdInfo(Map<String,Object> param);

    /**
     * 根据主键获取记录
     * @param record 对象
     * @return 返回记录
     */
	BusinessCoupon selectByPK(Long businessCouponId);

	 /**
	  * 插入优惠前检查条件
	  * <p> 备注返回值为：couponTypeMutex:类型互斥; true:满足条件;
	  * MoreCouponTypeTimeMutex:多订多惠下单有效期互斥;  "":branchIds为空
	  * </P>
	  * @param businessCoupon
	  * @param branchIds("56744,4567")
	  * @return
	  */
	String checkOptionBeforeSaveBusinessCoupon(BusinessCoupon businessCoupon, String branchIds);
	
	/**
	 * 判断是否满足打tag的条件
	 * <p>
	 *	 如果满足则直接返回ProdProductTag,如果不满足则直接返回NULL
	 * </p>
	 * @param  couponType
	 * @param  productId
	 * @return ProdProductTag
	 */
	ProdProductTag checkProductTag(String couponType,Long productId);
	/**删除特卖活动*/
	Integer deleteFromBusinessCoupon(Long businessCouponId);
}
