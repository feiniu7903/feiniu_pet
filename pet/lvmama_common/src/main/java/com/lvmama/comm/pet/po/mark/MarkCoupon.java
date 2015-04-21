package com.lvmama.comm.pet.po.mark;

import java.io.Serializable;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
/**
 * 优惠券(活动). 
 */
public class MarkCoupon implements Serializable {
	/**
	 * 序列化
	 */
	private static final long serialVersionUID = -7573143682289834375L;	
	/**
	 * 是否有效:有效.
	 */
	public static final String VALID_TRUE = "true";
	/**
	 * 是否有效:无效.
	 */
	public static final String VALID_FALSE = "false";
	/**
	 * 有效期类型:固定有效期
	 */
	public static final String FIXED_VALID = "FIXED";
	/**
	 * 有效期类型:非固定有效期
	 */
	public static final String UNFIXED_VALID = "UNFIXED";

    //标识
 	private Long couponId;
 	//发行渠道 
 	private Long channelId;
    //名字
	private String couponName;
	//描述  
	private String description;
	//针对订单(ORDER)或产品(PRODUCT)优惠. 取值为 com.lvmama.comm.vo.COUPON_TARGET中定义的值其中之一.
	private String couponTarget = "ORDER";
	//优惠券类型,A类(规定时间无限次使用),B类(规定时间只能使用一次). 取值为com.lvmama.comm.vo.COUPON_TYPE中定义的值其中之一.
	private String couponType = "B";
    //优惠券还是活动，true代表优惠券，false代表活动
	private String withCode = "true";
	//优惠券code码的开头字符串 
	private String firstCode;
	//有效期类型
	private String validType = FIXED_VALID;
	//有效期-开始时间.
	private Date beginTime;
	
	//有效期-开始时间.
	private Date endTime;
	//有效期 
 	private Long termOfValidity;
 	//是否有效 
 	private String valid = VALID_TRUE;
 	//创建时间
	private Date createTime;
	//优惠策略 
	private String favorType = Constant.FAVOR_TYPE.AMOUNT_AMOUNT_WHOLE.name();
	//优惠参数:超过的基数值
	private Long argumentX;
	//优惠参数:每满值,
	private Long argumentY;
	//优惠参数: 优惠金额或百分比数,
	private Long argumentZ;
	//支付渠道
	private String paymentChannel = "false";
	//渠道名称
	private String channelName;
	//备注
	private String memo;
	//最大优惠金额
	private Long maxCoupon;
	//已经优惠金额
	private Long usedCoupon;
	private Long productId;
	
	public Long getCouponId() {
		return couponId;
	}
	
	/**
	 * 优惠券批次是否过期
	 * @return 是否过期
	 * <p>判断当前时间是否落在开始时间和结束时间之间，且批次是属于上线状态的.当优惠券批次的有效期类型是非固定的时候，此方法失效，请使用{@link com.lvmama.comm.pet.po.mark。MarkCouponCode.isOverDue()}</p>
	 */
	public boolean isOverDue() {
		if (VALID_TRUE.equals(this.getValid())) {
			Date now = new Date(System.currentTimeMillis());
			
			return !(FIXED_VALID.equals(validType) && now.after(this.getBeginTime()) && now.before(this.getEndTime()));
		}
		return true;
	}
	
	/**
	 * 上下线此优惠券(活动).
	 */
	public void reverseValidStatus() {
		if (VALID_TRUE.equals(this.valid)) {
			this.valid = VALID_FALSE;
			return;
		}
		if (VALID_FALSE.equals(this.valid)) {
			this.valid = VALID_TRUE;
			return;
		}
	}

	public Date getEndTime() {
		if (null != this.endTime) {
			return endTime;
		} else {
			if (null == this.beginTime || null == this.termOfValidity) {
				return null;
			} else {
				endTime = DateUtil.getDateAfterDays(beginTime,
						termOfValidity.intValue());
				endTime.setHours(23);
				endTime.setMinutes(59);
				endTime.setSeconds(59);
				return endTime;
			}
		}
	}
	
	/**
	 * 优惠方式，是按照金额还是按照折扣
	 * @return
	 */
	public String getFavorMode() {
		if (StringUtils.isNotBlank(getFavorType())) {
			String[] result = favorType.split("_");
			return result[0];
		} else {
			return null;
		}
	}
	
	/**
	 * 优惠规则，是按照订单总额还是订单份数
	 * @return
	 */
	public String getFavorRule() {
		if (StringUtils.isNotBlank(getFavorType())) {
			String[] result = favorType.split("_");
			return result[1];
		} else {
			return null;
		}
	}
	
	/**
	 * 用来控制页面上的"绑定产品类型"超链接是否可用,产品类型返回false表可用,订单类型返回true表不可用.
	 * @return
	 */
	public boolean getShowBoundType() {
		return !this.couponTarget.equals(Constant.COUPON_TARGET.PRODUCT.name());
	}
	
	/**
	 * 将X值设置成"元"单位
	 * @return
	 */
	public float getArgumentXYuan() {
		return PriceUtil.convertToYuan(this.getArgumentX());
	}
	
	/**
	 * 将Y值设置成"元"单位
	 * @return
	 */
	public float getArgumentYYuan() {
		return PriceUtil.convertToYuan(this.getArgumentY());
	}
	
	/**
	 * 将Z值设置成"元"单位
	 * @return
	 */
	public float getArgumentZYuan() {
		return PriceUtil.convertToYuan(this.getArgumentZ());
	}	
	
	public String getCouponTarget() {
		return couponTarget;
	}
	public void setCouponTarget(String couponTarget) {
		this.couponTarget = couponTarget == null ? null : couponTarget.trim();
	}
	public String getTargetZh() {
		return Constant.COUPON_TARGET.PRODUCT.name().equals(this.couponTarget) ? "产品" : "订单";
	} 
	
	public String getValid() {
		return valid;
	}
	public void setValid(String valid) {
		this.valid = valid == null ? null : valid.trim();
	}
	public String getStateZh() {
		return VALID_TRUE.equals(this.valid) ? "开启" : "关闭";
	}
	
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName == null ? null : couponName.trim();
	}

	public String getCouponType() {
		return couponType;
	}

	public void setCouponType(String couponType) {
		this.couponType = couponType == null ? null : couponType.trim();
	}

	public String getWithCode() {
		return withCode;
	}

	public void setWithCode(String withCode) {
		this.withCode = withCode == null ? null : withCode.trim();
	}

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public String getFirstCode() {
		if(firstCode == null){
			return "";
		}
		return firstCode;
	}

	public void setFirstCode(String firstCode) {
		this.firstCode = firstCode == null ? null : firstCode.trim();
	}

	public Long getChannelId() {
		return channelId;
	}

	public void setChannelId(Long channelId) {
		this.channelId = channelId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getPaymentChannel() {
		return paymentChannel;
	}

	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}
	
	public boolean getIsChosedPaymentChannel(){
		if(this.paymentChannel!=null&&!"".equals(this.paymentChannel)){
			return true;
		}
		return false;
	}

	public Long getTermOfValidity() {
		return termOfValidity;
	}

	public void setTermOfValidity(Long termOfValidity) {
		this.termOfValidity = termOfValidity;
	}

	public String getFavorType() {
		return favorType;
	}

	public void setFavorType(String favorType) {
		this.favorType = favorType;
	}

	public Long getArgumentX() {
		return argumentX;
	}

	public void setArgumentX(Long argumentX) {
		this.argumentX = argumentX;
	}

	public Long getArgumentY() {
		return argumentY;
	}

	public void setArgumentY(Long argumentY) {
		this.argumentY = argumentY;
	}

	public Long getArgumentZ() {
		return argumentZ;
	}

	public void setArgumentZ(Long argumentZ) {
		this.argumentZ = argumentZ;
	}

	public String getValidType() {
		return validType;
	}

	public void setValidType(String validType) {
		this.validType = validType;
	}
	
	public String getChannelName() {
		return channelName;
	}

	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}

	public void setFavorRule(String favorRule) {
		
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public String getFavorModeZh() {
		return this.getFavorMode().equals(Constant.COUPON_FAVOR_MODE.AMOUNT.name()) ? Constant.COUPON_FAVOR_MODE.AMOUNT.getCnName():Constant.COUPON_FAVOR_MODE.DISCOUNT.getCnName();
	}

	public String getFavorRuleZh() {
		return this.getFavorRule().equals(Constant.COUPON_FAVOR_RULE.AMOUNT.name()) ? Constant.COUPON_FAVOR_RULE.AMOUNT.getCnName():Constant.COUPON_FAVOR_RULE.QUANTITY.getCnName();
	}
	
	/**
	 * 获得优惠单位
	 * @return
	 */
	public String getFavorUnit(){
		if(this.getFavorType().startsWith("DISCOUNT")){
			return "DISCOUNT";
		}else{
			return "AMOUNT";
		}
	}
	
	/**
	 * 获得该优惠券描述
	 * @return
	 */
	public String getFavorTypeDescription(){
		String favorTypeDescription = "";
		if(Constant.FAVOR_TYPE.AMOUNT_AMOUNT_WHOLE.name().equals(this.getFavorType())){
			favorTypeDescription = Constant.FAVOR_TYPE.AMOUNT_AMOUNT_WHOLE.getCnName();
			favorTypeDescription = favorTypeDescription.replaceAll("\\{X\\}", String.valueOf(this.getArgumentXYuan()));
			favorTypeDescription = favorTypeDescription.replaceAll("\\{Y\\}", String.valueOf(this.getArgumentYYuan()));
		}else if(Constant.FAVOR_TYPE.AMOUNT_AMOUNT_INTERVAL.name().equals(this.getFavorType())){
			favorTypeDescription = Constant.FAVOR_TYPE.AMOUNT_AMOUNT_INTERVAL.getCnName();
			favorTypeDescription = favorTypeDescription.replaceAll("\\{X\\}", String.valueOf(this.getArgumentXYuan()));
			favorTypeDescription = favorTypeDescription.replaceAll("\\{Y\\}", String.valueOf(this.getArgumentYYuan()));
			favorTypeDescription = favorTypeDescription.replaceAll("\\{Z\\}", String.valueOf(this.getArgumentZYuan()));
		}else if(Constant.FAVOR_TYPE.AMOUNT_QUANTITY_WHOLE.name().equals(this.getFavorType())){
			favorTypeDescription = Constant.FAVOR_TYPE.AMOUNT_QUANTITY_WHOLE.getCnName();
			favorTypeDescription = favorTypeDescription.replaceAll("\\{X\\}", String.valueOf(this.getArgumentX()));
			favorTypeDescription = favorTypeDescription.replaceAll("\\{Y\\}", String.valueOf(this.getArgumentYYuan()));
		}else if(Constant.FAVOR_TYPE.AMOUNT_QUANTITY_INTERVAL.name().equals(this.getFavorType())){
			favorTypeDescription = Constant.FAVOR_TYPE.AMOUNT_QUANTITY_INTERVAL.getCnName();
			favorTypeDescription = favorTypeDescription.replaceAll("\\{X\\}", String.valueOf(this.getArgumentX()));
			favorTypeDescription = favorTypeDescription.replaceAll("\\{Y\\}", String.valueOf(this.getArgumentY()));
			favorTypeDescription = favorTypeDescription.replaceAll("\\{Z\\}", String.valueOf(this.getArgumentZYuan()));
		}else if(Constant.FAVOR_TYPE.AMOUNT_QUANTITY_PRE.name().equals(this.getFavorType())){
			favorTypeDescription = Constant.FAVOR_TYPE.AMOUNT_QUANTITY_PRE.getCnName();
			favorTypeDescription = favorTypeDescription.replaceAll("\\{X\\}", String.valueOf(this.getArgumentX()));
			favorTypeDescription = favorTypeDescription.replaceAll("\\{Y\\}", String.valueOf(this.getArgumentYYuan()));
		}else if(Constant.FAVOR_TYPE.DISCOUNT_AMOUNT_WHOLE.name().equals(this.getFavorType())){
			favorTypeDescription = Constant.FAVOR_TYPE.DISCOUNT_AMOUNT_WHOLE.getCnName();
			favorTypeDescription = favorTypeDescription.replaceAll("\\{X\\}", String.valueOf(this.getArgumentXYuan()));
			favorTypeDescription = favorTypeDescription.replaceAll("\\{Y\\}", String.valueOf(this.getArgumentY().floatValue()/10));
		}else if(Constant.FAVOR_TYPE.DISCOUNT_QUANTITY_WHOLE.name().equals(this.getFavorType())){
			favorTypeDescription = Constant.FAVOR_TYPE.DISCOUNT_QUANTITY_WHOLE.getCnName();
			favorTypeDescription = favorTypeDescription.replaceAll("\\{X\\}", String.valueOf(this.getArgumentX()));
			favorTypeDescription = favorTypeDescription.replaceAll("\\{Y\\}", String.valueOf(this.getArgumentY().floatValue()/10));
		}else if(Constant.FAVOR_TYPE.DISCOUNT_QUANTITY_PRE.name().equals(this.getFavorType())){
			favorTypeDescription = Constant.FAVOR_TYPE.DISCOUNT_QUANTITY_PRE.getCnName();
			favorTypeDescription = favorTypeDescription.replaceAll("\\{X\\}", String.valueOf(this.getArgumentX()));
			favorTypeDescription = favorTypeDescription.replaceAll("\\{Y\\}", String.valueOf(this.getArgumentY()));
			favorTypeDescription = favorTypeDescription.replaceAll("\\{Z\\}", String.valueOf(this.getArgumentZ().floatValue()/10));
		}else{
			favorTypeDescription = "";
		}
		return favorTypeDescription;
	}
	
	/**
	 * 获得该优惠券优惠金额或折扣
	 * @return
	 */
	public Long getFavorTypeAmount(){
		Long favorTypeAmount = 0l;
		if(Constant.FAVOR_TYPE.AMOUNT_AMOUNT_WHOLE.name().equals(this.getFavorType())){
			favorTypeAmount = this.getArgumentY();
		}else if(Constant.FAVOR_TYPE.AMOUNT_AMOUNT_INTERVAL.name().equals(this.getFavorType())){
			favorTypeAmount=this.getArgumentZ();
		}else if(Constant.FAVOR_TYPE.AMOUNT_QUANTITY_WHOLE.name().equals(this.getFavorType())){
			favorTypeAmount=this.getArgumentY();
		}else if(Constant.FAVOR_TYPE.AMOUNT_QUANTITY_INTERVAL.name().equals(this.getFavorType())){
			favorTypeAmount=this.getArgumentZ();
		}else if(Constant.FAVOR_TYPE.AMOUNT_QUANTITY_PRE.name().equals(this.getFavorType())){
			favorTypeAmount=this.getArgumentY();
		}else if(Constant.FAVOR_TYPE.DISCOUNT_AMOUNT_WHOLE.name().equals(this.getFavorType())){
			favorTypeAmount=this.getArgumentY();
		}else if(Constant.FAVOR_TYPE.DISCOUNT_QUANTITY_WHOLE.name().equals(this.getFavorType())){
			favorTypeAmount=this.getArgumentY();
		}else if(Constant.FAVOR_TYPE.DISCOUNT_QUANTITY_PRE.name().equals(this.getFavorType())){
			favorTypeAmount=this.getArgumentZ();
		}
		return favorTypeAmount;
	}

	public void setFavorMode(String favorMode) {
		
	}
	
	public boolean isBCoupon(){
		if (Constant.COUPON_TYPE.B.name().equals(this.getCouponType())) {
			return true;
		}
		return false;
	}

	public String getMemo() {
		return memo;
	}

	public static String getValidTrue() {
		return VALID_TRUE;
	}

	public static String getValidFalse() {
		return VALID_FALSE;
	}

	public static String getFixedValid() {
		return FIXED_VALID;
	}

	public static String getUnfixedValid() {
		return UNFIXED_VALID;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Long getMaxCoupon() {
		return maxCoupon;
	}

	public void setMaxCoupon(Long maxCoupon) {
		this.maxCoupon = maxCoupon;
	}

	public Long getUsedCoupon() {
		return usedCoupon;
	}

	public void setUsedCoupon(Long usedCoupon) {
		this.usedCoupon = usedCoupon;
	}
	/**
	 * 最大优惠金额--元
	 * @return
	 */
	public Float getMaxCouponYuan() {
		return PriceUtil.convertToYuan(this.getMaxCoupon());
	}
	/**
	 * 已经使用优惠金额--元
	 * @return
	 */
	public Float getUsedCouponYuan() {
		return PriceUtil.convertToYuan(this.getUsedCoupon());
	}

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}
	
}