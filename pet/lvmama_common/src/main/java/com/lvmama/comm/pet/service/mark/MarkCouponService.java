package com.lvmama.comm.pet.service.mark;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.vo.view.MarkCouponUserInfo;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;
import com.lvmama.comm.pet.po.prod.ProdProductTag;
import com.lvmama.comm.pet.po.user.UserUser;

public interface MarkCouponService {
//	/**
//	 * 检查对于同一优惠活动,检查同一产品是否同时绑定了产品ID和产品子类型.
//	 * <br/>1:绑定产品ID时，检查此产品所属的产品子类型是否已绑定当前活动.
//	 * <br/>2.绑定产品子类型时，检查属于此产品子类型的产品ID是否已绑定当前活动.
//	 * @param mcp MarkCouponProduct对象.
//	 * @return 返回ProductId列表或产品子类型.
//	 */
//	public String checkProductIdOrSubProductTypeAgainBound(MarkCouponProduct mcp);
//	
//	/**
//	 * 保存单个的MarkCouponProduct对象.
//	 * @param mcp
//	 */
//	public void saveSingleProductCoupon(MarkCouponProduct mcp) ;
//	/**
//	 * 根据优惠活动ID与产品子类型删除MarkCouponProduct.
//	 * @param couponId 优惠活动ID.
//	 * @param subProductType 产品子类型.
//	 */
//	public void deleteByCouponIdAndSubProductType(Long couponId,String subProductType);
//	
//	void saveMarkCoupon(MarkCoupon markCoupon,List<String> codeList);
//	List<MarkChannel> loadChannel();
	
//	MarkCoupon loadMarkCouponByPk(Long id);
//	void updateMarkCoupon(MarkCoupon markCoupon);
//	/**
//	 * 生成code
//	 * @param id
//	 * @param codeList
//	 */
//	void saveCode(Long id,List<String> codeList);
//	
	
//	Long selectCountByCouponId(Long couponId);
//	/**
//	 * 批量保存MarkCouponProduct对象.
//	 * @param couponId
//	 * @param productId
//	 */
//	void saveProductCoupon(Long couponId,List<Long> productId);
//	List<MarkCouponProduct> selectMarkCouponProduct(Long couponId);
//	List<CouponProduct> selectCouponProduct(Map<String,Object> parameters);
//	Long selectCouponProductCount(Map<String,Object> parameters);
//	/**
//	 *删除一个优惠活动与产品的关系
//	 */
//	void deleteCouponProduct(Long couponProductId);
//	/**
//	 * 删除所有查询出来的绑定产品
//	 */
//	void deleteCouponProductAll(Map<String,Object> parameters);
//	/**
//	 * 保存渠道
//	 * @param mdc
//	 */
//	Long saveMarkDistChannel(MarkChannel mdc);
//	/**
//	 * 修改优惠券发放渠道
//	 * @param mdc
//	 */
//	void updateMarkDistChannel(MarkChannel mdc);
//	/**
//	 * 主键查找渠道
//	 * @param channelId
//	 * @return
//	 */
//	MarkChannel loadMarkDistChannelByPk(Long channelId);
//	
//	/**
//	 * 统计优惠活动绑定的产品个数
//	 * @param couponId
//	 * @return
//	 */
//	Long countProductByCouponId(Long couponId);
//	/**
//	 * 统计优惠活动产生的优惠券个数
//	 * @param couponId
//	 * @return
//	 */
//	Long countHasCode(Long couponId);
//	MarkCouponProduct selectByCouponIdAndProductId(Long couponId, Long productId);
//	MarkCouponProduct selectMarkCouponProductByPk(Long id);
//	/**
//	 * 通过code 和couponId 查询一条MarkCouponCode记录
//	 * @param couponId
//	 * @param couponCode
//	 * @return
//	 */
//	List<MarkCouponCode> selectCouponCodeByCodeAndCouponId(Long couponId,String couponCode);
//	/**
//	 * 查询优惠券统计使用数据
//	 * @param param
//	 * @return
//	 * @throws
//	 * @author  
//	 */
//	List<MarkCouponUsage> selectCouponUsedByParam(Map<String,Object> param);
//	MarkCouponCode loadMarkCouponCodeByPk(Long id);
//	/**
//	 * 通过子项查找使用优惠活动集合
//	 * @param objectId
//	 * @return
//	 */
//	MarkCouponUsage selectProdCouponUsege(Long objectId);
//	/**
//	 * 通过子订单查找使用优惠活动集合
//	 * @param objectId
//	 * @return
//	 */
//	MarkCouponUsage selectOrdCouponUsege(Long objectId);

	/**
	 * 新增优惠批次
	 * @param markCoupon 需要新增的优惠批次
	 * @return 新增的优惠批次
	 */
	MarkCoupon insertMarkCoupon(MarkCoupon markCoupon);
	
	/**
	 * 更新优惠批次
	 * @param markCoupon 更新后的优惠批次
	 * @return 优惠批次
	 */
	MarkCoupon updateMarkCoupon(MarkCoupon markCoupon);
	/**
	 * 根据查询条件统计优惠券批次总数
	 * @param param 查询条件
	 * @return 条目数
	 */
	Integer selectMarkCouponRowCount(Map<String,Object> param);
	
	/**
	 * 根据查询条件返回所有符合条件的优惠券批次
	 * @param param 查询条件
	 * @return 优惠券批次列表
	 */
	List<MarkCoupon> selectMarkCouponByParam(Map<String,Object> param);	
	
	/**
	 * 根据主键查找优惠券批次
	 * @param couponId 主键标识
	 * @return 优惠券批次
	 */
	MarkCoupon selectMarkCouponByPk(Long couponId);
	
	/**
	 * 根据优惠码来查询优惠券的批次
	 * @param code 优惠券号码
	 * @param ignoreValid 忽略优惠券号码和优惠券批次的有效性
	 * @return 优惠券批次
	 * <p>根据优惠券号码<code>code<code>来获得所对应的优惠券批次，当<code>ignoreValid = false</code>时，只能查询出有效的优惠券批次，优惠号码已使用，优惠号码已过期或者优惠批次已过期将不会被查询出来</p>
	 */
	MarkCoupon selectMarkCouponByCouponCode(String code, boolean ignoreValid);

	/**
	 * 更新优惠码
	 * @param markCoupon 更新后的优惠码
	 * @return 更新后的优惠码
	 * 此更新会校验优惠号码是否已经存在，如果存在，将更新失败返回null,否则返回更新后的优惠码
	 */
	MarkCouponCode  updateMarkCouponCode(MarkCouponCode markCouponCode, boolean changeCode);
	
	/**
	 * 根据查询条件统计优惠码总数
	 * @param param 查询条件
	 * @return 条目数
	 */
	Integer selectMarkCouponCodeRowCount(Map<String,Object> param);
	
	/**
	 * 根据查询条件查询优惠码
	 * @param param 查询条件
	 * @return 优惠码列表
	 */
	List<MarkCouponCode> selectMarkCouponCodeByParam(Map<String,Object> param);
	
	/**
	 * 根据coupon id和coupon code获取优惠码
	 * @param couponId
	 * @param couponCode
	 * @return
	 */
	MarkCouponCode getMarkCouponCodeByCouponIdAndCode(Long couponId,String couponCode);
	
	/**
	 * 根据主键查找优惠码
	 * @param couponCodeId 主键标识
	 * @return 优惠券批次
	 */
	MarkCouponCode selectMarkCouponCodeByPk(Long couponCodeId);	
	
//	/**
//	 * 
//	 * @param param
//	 * @return
//	 */
//	Integer selectCountCouponUsageByParam(Map<String,Object> param);
//	Long selectCountByCode(Long couponId,String code);
//	/**
//	 * 优惠券码生成接口
//	 * @param couponId
//	 * @return
//	 */
//	String saveMarkcouponCode(Long couponId);
//	
//	/**
//	 * 通过优惠券代码查找CouponId
//	 */
//	Long selectCouponIdByCouponCode(String CouponCode);
//	/**
//	 * 查询当前选择的产品的参与的活动以及所有的活动
//	 * @param list
//	 * @return
//	 */
//	public List<MarkCoupon> loadAllOrderMarkCoupon(Long productId,String subProductType);
//	public List<MarkCoupon> loadAllOrderMarkCoupon(List<Long> productIds,List<String> subProductTypes);
//	ValidateCodeInfo validateCoupon(Long productId,String couponCode,String userId,Long orderPrice,Long orderQuantity, String subProductType);

//	/**
//	 * 修改产品优惠券关联
//	 * @param mcp
//	 */
//	void updateMarkCouponProduct(MarkCouponProduct mcp);
//	
//	/**
//	 * 查询可被会员卡绑定的优惠券
//	 * @param parameters
//	 * @return
//	 */
//	List<MarkCoupon> loadAllMarkCouponCanBeBindedToMembershipCard(Map<String,Object> parameters);
//	
//	void saveMoreCode(String[] array);
//	/**
//	 * 删除临时存储的优惠券码
//	 */
//	void deleteAllCodeTemp();
//	/**
//	 * 查询生成的临时code数量
//	 * @return
//	 */
//	 Long selectCodeTempCount();
//	 /**
//	  * 导入优惠券数据
//	  * @param couponId
//	  */
//	 void mergeTempCodeData(String couponId);
//	 List<MarkCouponCode> loadAllCodesByCouponId(Long couponId);
	
	/**
	 * 根据优惠券批次号生成单一优惠码
	 * @param couponId 批次标识
	 * @return 优惠码
	 */
	MarkCouponCode generateSingleMarkCouponCodeByCouponId(Long couponId);
	 
	 /**
	  * 批量生成优惠码
	  * @param couponId 批次标识
	  * @param number 优惠码列表
	  * @param couponGenerateMode ("force"字符强制生成code)
	  */
	 List<MarkCouponCode> generateMarkCouponCodeByCouponId(Long couponId, int number, String couponGenerateMode);
	 
	 /**
	  * 为用户批量生成优惠券
	  * @param userList
	  * @param couponId
	  */
	 void generateAndBindCouponCodeForUserList(List<UserUser> userList, final Long couponId);
	 
	 /**
	  * 为用户绑定优惠券CODE
	  * @param user
	  * @param code
	  * @return
	  */
	 Long bindingUserAndCouponCode(final UserUser user, String code);
	 
	 
    /**
	 * 查询指定产品的可用优惠券/优惠活动
	 */
	List<MarkCoupon> selectProductCanUseMarkCoupon(Map<String,Object> map);
	
	/**
	 * 查询全场通用的可用优惠券/优惠活动
	 * @param map
	 * @return
	 */
	List<MarkCoupon> selectAllCanUseMarkCoupon(Map<String,Object> map);
	
	 /**
	  * 查询全场通用的可用优惠券/优惠活动以及指定产品的可用优惠券/优惠活动的去重合集
	  * @param map
	  * @return
	  */
	List<MarkCoupon> selectAllCanUseAndProductCanUseMarkCoupon(Map<String,Object> map);
	
	/**
	 * 手机客户端查询优惠券接口
	 * @param userId
	 * @return
	 */
	List<MarkCouponUserInfo> queryMobileUserCouponInfoByUserId(Long userId);
	/**
	 * 更新已优惠金额
	 * @param map 更新优惠金额及其条件
	 * @return 优惠批次
	 */
	Integer updateUsedCouponByMarkCoupon(Map<String,Object> map);
	/**
	 * 判断是否满足打tag的条件
	 * <p>
	 *	 如果满足则直接返回ProdProductTag List,如果不满足则直接返回NULL
	 * </p>
	 * @param  couponId
	 * @param  productId
	 * @return List<ProdProductTag>
	 */
	List<ProdProductTag> checkProductTag(List<Long> productIds);
	/**
	 * 根据用户id和优惠活动id查询优惠券
	 * @param param
	 * @return
	 */
	public List<MarkCouponCode> queryByUserAndCoupon(Map<String, Object> param);
	
}
