package com.lvmama.comm.pet.po.businessCoupon;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.SeckillRuleVO;

public class BusinessCoupon implements Serializable {
	
	/**
	 * 序列
	 */
	private static final long serialVersionUID = 2773518797399650603L;
	//标识
	private Long businessCouponId;
	//名字
	private String couponName;
	//优惠类型(EARLY"早定早惠"/MORE"多订多惠");取值为 com.lvmama.comm.vo.Constant.BUSINESS_COUPON_TYPE中定义的值其中之一
	private String couponType = "EARLY";
	
	//优惠策略 (PROD"产品"/BRANCH"产品类别");取值为 com.lvmama.comm.vo.Constant.COUPON_FAVOR_MODE中定义的值其中之一
	private String favorType = "AMOUNT_EARLYDAY_QUANTITY_PRE";
	
	//优惠绑定类型(PROD"产品"/BRANCH"产品类别");取值为 com.lvmama.comm.vo.Constant.BUSINESS_COUPON_TARGET中定义的值其中之一
	//绑定产品该需求拿掉了
	private String couponTarget = "BRANCH";
	
	//优惠参数:超过的基数值
	private Long argumentX;
	//优惠参数:每满值,
	private Long argumentY;
	//优惠参数:优惠金额或百分比数,
	private Long argumentZ;
		
	//有效期-开始时间.
	private Date beginTime;
	//有效期-结束时间.
	private Date endTime;
	private Date createTime;
	//游玩日期:开始日期
	private Date playBeginTime;
	//游玩日期:结束日期
	private Date playEndTime;
	
	//描述  
	private String description;
	//是否有效 
	private String valid = VALID_FALSE;
	private String memo;
	//META"采购产品|"SALES"销售产品";取值为 com.lvmama.comm.vo.Constant.BUSINESS_COUPON_META_TYPE中定义的值其中之一
	private String metaType;
		
	//产品ID
	private Long productId;
	//类别ID
	private Long branchId;
	//当前绑定的所有产品名称
	private String currentBindBranchNames;
	//特卖类型，NORMAL_TUANGOU:普通团购,SECKILL:秒杀,TRAVEL_AROUND:周游客,VIP_DAY:会员日
	private String saleType;
	private List<SeckillRuleVO> seckillRuleVOs;
	/**
	 * 是否有效:有效.
	 */
	public static final String VALID_TRUE = "true";
	/**
	 * 是否有效:无效.
	 */
	public static final String VALID_FALSE = "false";
	 
	
	/**
	 * 上下线此优惠(活动).
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
	
	public void setValid(String valid) {
		this.valid = valid == null ? null : valid.trim();
	}
	public String getStateZh() {
		String stateZh="";
		Date currentTime = new Date();
		if(VALID_FALSE.equals(valid)){
			stateZh="关闭";
		}else if(currentTime.compareTo(beginTime)>=0 && currentTime.compareTo(endTime)<=0){
			stateZh="进行中";
		}else if(currentTime.compareTo(endTime)>0){
			stateZh="已结束";
		}else if(currentTime.compareTo(beginTime)<0){
			stateZh="未开始";
		}
		
		return stateZh;
	}
	
	public String getSaleType() {
		return saleType;
	}

	public void setSaleType(String saleType) {
		this.saleType = saleType;
	}

	public String getFavorModeZh() {
		return this.getFavorMode().equals(Constant.COUPON_FAVOR_MODE.AMOUNT.name()) ? Constant.COUPON_FAVOR_MODE.AMOUNT.getCnName():Constant.COUPON_FAVOR_MODE.DISCOUNT.getCnName();
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
	
	public void setFavorMode(String favorMode) {
	} 
	
	public void setFavorRule(String favorRule) {
	}
	
	public void setCouponName(String couponName) {
		this.couponName = couponName == null ? null : couponName.trim();
	}
	
	public void setCouponType(String couponType) {
		this.couponType = couponType == null ? null : couponType.trim();
	}
	
	public String getCouponTypeZh() {
		String zh="";
		if(this.getCouponType().equals(Constant.BUSINESS_COUPON_TYPE.EARLY.name())){
			zh=Constant.BUSINESS_COUPON_TYPE.EARLY.getCnName();
		}else if(this.getCouponType().equals(Constant.BUSINESS_COUPON_TYPE.MORE.name())){
			zh=Constant.BUSINESS_COUPON_TYPE.MORE.getCnName();
		}else{
			zh=Constant.BUSINESS_COUPON_TYPE.SALE.getCnName();
		}
		return zh;
	}
	
	public String getFormatterPlayBeginTime() {
		if(playBeginTime != null)
		{
			return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(playBeginTime);
		}
		else
		{
			return null;
		}
	}
	
	public String getFormatterPlayEndTime() {
		if(playEndTime != null)
		{
			return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(playEndTime);
		}
		else
		{
			return null;
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
		}else if(Constant.FAVOR_TYPE.AMOUNT_EARLYDAY_QUANTITY_PRE.name().equals(this.getFavorType())){
			favorTypeDescription = Constant.FAVOR_TYPE.AMOUNT_EARLYDAY_QUANTITY_PRE.getCnName();
			if(this.getArgumentX() != null){
				favorTypeDescription = favorTypeDescription.replaceAll("\\{X\\}", String.valueOf(this.getArgumentX()));
			}else if(this.getPlayBeginTime() != null && this.getPlayEndTime() != null){
				favorTypeDescription = "游玩时间从" + this.getFormatterPlayBeginTime() + "到" + this.getFormatterPlayEndTime()+", 每份(间夜)，价格优惠金额{Y/Z}元";
			}
			if("SALES".equalsIgnoreCase(this.getMetaType())){
				favorTypeDescription = favorTypeDescription.replaceAll("\\{Y/Z\\}", String.valueOf(this.getArgumentYYuan()));
			}else if("META".equalsIgnoreCase(this.getMetaType())){
				favorTypeDescription = favorTypeDescription.replaceAll("\\{Y/Z\\}", String.valueOf(this.getArgumentZYuan()));
			}
		}else if(Constant.FAVOR_TYPE.DISCOUNT_EARLYDAY_QUANTITY_PRE.name().equals(this.getFavorType())){
			favorTypeDescription = Constant.FAVOR_TYPE.DISCOUNT_EARLYDAY_QUANTITY_PRE.getCnName();
			favorTypeDescription = favorTypeDescription.replaceAll("\\{X\\}", String.valueOf(this.getArgumentX()));
			if("SALES".equalsIgnoreCase(this.getMetaType())){
				favorTypeDescription = favorTypeDescription.replaceAll("\\{Y/Z\\}", String.valueOf(this.getArgumentY().floatValue()/10));
			}else if("META".equalsIgnoreCase(this.getMetaType())){
				favorTypeDescription = favorTypeDescription.replaceAll("\\{Y/Z\\}", String.valueOf(this.getArgumentZ().floatValue()/10));
			}
		}else if(Constant.FAVOR_TYPE.DISCOUNT_MORE_QUANTITY_INTERVAL.name().equals(this.getFavorType())){
			favorTypeDescription = Constant.FAVOR_TYPE.DISCOUNT_MORE_QUANTITY_INTERVAL.getCnName();
			favorTypeDescription = favorTypeDescription.replaceAll("\\{X\\}", String.valueOf(this.getArgumentX()));
			if("SALES".equalsIgnoreCase(this.getMetaType())){
				favorTypeDescription = favorTypeDescription.replaceAll("\\{Y/Z\\}", String.valueOf(this.getArgumentY().floatValue()/10));
			}else if("META".equalsIgnoreCase(this.getMetaType())){
				favorTypeDescription = favorTypeDescription.replaceAll("\\{Y/Z\\}", String.valueOf(this.getArgumentZ().floatValue()/10));
			}
		}else if(Constant.FAVOR_TYPE.DISCOUNT_MORE_QUANTITY_PRE.name().equals(this.getFavorType())){
			favorTypeDescription = Constant.FAVOR_TYPE.DISCOUNT_MORE_QUANTITY_PRE.getCnName();
			favorTypeDescription = favorTypeDescription.replaceAll("\\{X\\}", String.valueOf(this.getArgumentX()));
			if("SALES".equalsIgnoreCase(this.getMetaType())){
				favorTypeDescription = favorTypeDescription.replaceAll("\\{Y/Z\\}", String.valueOf(this.getArgumentY().floatValue()/10));
			}else if("META".equalsIgnoreCase(this.getMetaType())){
				favorTypeDescription = favorTypeDescription.replaceAll("\\{Y/Z\\}", String.valueOf(this.getArgumentZ().floatValue()/10));
			}
		}else if(Constant.FAVOR_TYPE.AMOUNT_MORE_QUANTITY_INTERVAL.name().equals(this.getFavorType())){
			favorTypeDescription = Constant.FAVOR_TYPE.AMOUNT_MORE_QUANTITY_INTERVAL.getCnName();
			favorTypeDescription = favorTypeDescription.replaceAll("\\{X\\}", String.valueOf(this.getArgumentX()));
			if("SALES".equalsIgnoreCase(this.getMetaType())){
				favorTypeDescription = favorTypeDescription.replaceAll("\\{Y/Z\\}", String.valueOf(this.getArgumentYYuan()));
			}else if("META".equalsIgnoreCase(this.getMetaType())){
				favorTypeDescription = favorTypeDescription.replaceAll("\\{Y/Z\\}", String.valueOf(this.getArgumentZYuan()));
			}
		}else if(Constant.FAVOR_TYPE.AMOUNT_MORE_QUANTITY_PRE.name().equals(this.getFavorType())){
			favorTypeDescription = Constant.FAVOR_TYPE.AMOUNT_MORE_QUANTITY_PRE.getCnName();
			favorTypeDescription = favorTypeDescription.replaceAll("\\{X\\}", String.valueOf(this.getArgumentX()));
			if("SALES".equalsIgnoreCase(this.getMetaType())){
				favorTypeDescription = favorTypeDescription.replaceAll("\\{Y/Z\\}", String.valueOf(this.getArgumentYYuan()));
			}else if("META".equalsIgnoreCase(this.getMetaType())){
				favorTypeDescription = favorTypeDescription.replaceAll("\\{Y/Z\\}", String.valueOf(this.getArgumentZYuan()));
			}
		}else if(Constant.FAVOR_TYPE.AMOUNT_MORE_QUANTITY_WHOLE.name().equals(this.getFavorType())){
			favorTypeDescription = Constant.FAVOR_TYPE.AMOUNT_MORE_QUANTITY_WHOLE.getCnName();
			favorTypeDescription = favorTypeDescription.replaceAll("\\{X\\}", String.valueOf(this.getArgumentX()));
			favorTypeDescription = favorTypeDescription.replaceAll("\\{Y\\}", String.valueOf(this.getArgumentY()));
			if("SALES".equalsIgnoreCase(this.getMetaType())){
				favorTypeDescription = favorTypeDescription.replaceAll("\\{Y/Z\\}", String.valueOf(this.getArgumentZYuan()));
			}else if("META".equalsIgnoreCase(this.getMetaType())){
				favorTypeDescription = favorTypeDescription.replaceAll("\\{Y/Z\\}", String.valueOf(this.getArgumentZYuan()));
			}
		}else{
			favorTypeDescription = "";
		}
		
		return favorTypeDescription;
	}
	 
	/**
	 *  ------------------------------------  get and set property -------------------------------------------
	 */
	
	public BusinessCoupon(){
	}
	
	public Long getBusinessCouponId() {
		return businessCouponId;
	}
	public void setBusinessCouponId(Long businessCouponId) {
		this.businessCouponId = businessCouponId;
	}
	public String getCouponName() {
		return couponName;
	}
	public String getCouponType() {
		return couponType;
	}
	public Date getBeginTime() {
		return beginTime;
	}
	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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
	public String getValid() {
		return valid;
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public Date getPlayBeginTime() {
		return playBeginTime;
	}
	public void setPlayBeginTime(Date playBeginTime) {
		this.playBeginTime = playBeginTime;
	}
 
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getBranchId() {
		return branchId;
	}
	public void setBranchId(Long branchId) {
		this.branchId = branchId;
	}
	public String getCouponTarget() {
		return couponTarget;
	}
	public void setCouponTarget(String couponTarget) {
		this.couponTarget = couponTarget;
	}
 
	public Date getEndTime() {
		if (null != this.endTime) {
				endTime.setHours(23);
				endTime.setMinutes(59);
				endTime.setSeconds(59);
				return endTime;
		}else{
			return null;
		}
	}
	
	public Long getArgumentZ() {
		return argumentZ;
	}
	public void setArgumentZ(Long argumentZ) {
		this.argumentZ = argumentZ;
	}
	
	public Date getPlayEndTime() {
		if (null != this.playEndTime) {
			playEndTime.setHours(23);
			playEndTime.setMinutes(59);
			playEndTime.setSeconds(59);
			return playEndTime;
		}else{
			return null;
		}
	}
	public void setPlayEndTime(Date playEndTime) {
		this.playEndTime = playEndTime;
	}
	
	public void setArgumentX(String argumentX) {
		this.argumentX = Long.parseLong(argumentX);
	}
	
	public void setArgumentY(String argumentY) {
		this.argumentY = Long.parseLong(argumentY);
	}
	
	public void setArgumentZ(String argumentZ) {
		this.argumentZ = Long.parseLong(argumentZ);
	}

	public String getMetaType() {
		return metaType;
	}

	public void setMetaType(String metaType) {
		this.metaType = metaType;
	}

	public String getCurrentBindBranchNames() {
		return currentBindBranchNames;
	}

	public void setCurrentBindBranchNames(String currentBindBranchNames) {
		this.currentBindBranchNames = currentBindBranchNames;
	}

	public List<SeckillRuleVO> getSeckillRuleVOs() {
		return seckillRuleVOs;
	}

	public void setSeckillRuleVOs(List<SeckillRuleVO> seckillRuleVOs) {
		this.seckillRuleVOs = seckillRuleVOs;
	}

}
