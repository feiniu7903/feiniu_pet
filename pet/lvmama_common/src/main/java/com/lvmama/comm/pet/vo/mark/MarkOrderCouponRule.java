package com.lvmama.comm.pet.vo.mark;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;

/**
 * 订单优惠券规则实体类
 * @author Brian
 *
 */
public class MarkOrderCouponRule implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3320527453546005489L;
	/**
	 * 标识
	 */
	private Long markOrderCouponRuleId;
	/**
	 * 产品类型，以逗号分隔(TICKET,HOTEL,OTHER...)
	 */
	private String subProductTypes;
	/**
	 * 最小订单额
	 */
	private Long minAmount = 0l;
	/**
	 * 最大订单额
	 */
	private Long maxAmount = 10000l;
	/**
	 * 优惠券批次号
	 */
	private Long couponId;
	/**
	 * 优惠券名称
	 */
	private String couponName;
	/**
	 * 优惠券金额
	 */
	private Long couponAmount;
	/**
	 * 优惠券结束时间
	 */
	private Date couponEndTime;
	
	/*
	 * 获取MapOfSubProductTypes
	 * */
	public Map<String, Object> getMapOfSubProductTypes(){
		String[] partitionStr = getSubProductTypes().split(",");
		Map<String, Object> mapOfSubProductTypes = new HashMap<String, Object>();;
		
		StringBuffer sb = new StringBuffer();
		for(String str : partitionStr){
			if("TICKET".equalsIgnoreCase(str)){
				mapOfSubProductTypes.put("TICKET", "true");
			}else if("HOTEL".equalsIgnoreCase(str)){
				mapOfSubProductTypes.put("HOTEL", "true");
			}else if("OTHER".equalsIgnoreCase(str)){
				mapOfSubProductTypes.put("OTHER", "true");
			}else if("GROUP_FOREIGN".equalsIgnoreCase(str)){
				mapOfSubProductTypes.put("GROUP_FOREIGN", "true");
			}else if("FREENESS_FOREIGN".equalsIgnoreCase(str)){
				mapOfSubProductTypes.put("FREENESS_FOREIGN", "true");
			}else if("GROUP_LONG".equalsIgnoreCase(str)){
				mapOfSubProductTypes.put("GROUP_LONG", "true");
			}else if("FREENESS_LONG".equalsIgnoreCase(str)){
				mapOfSubProductTypes.put("FREENESS_LONG", "true");
			}else if("GROUP".equalsIgnoreCase(str)){
				mapOfSubProductTypes.put("GROUP", "true");
			}else if("SELFHELP_BUS".equalsIgnoreCase(str)){
				mapOfSubProductTypes.put("SELFHELP_BUS", "true");
			}else if("FREENESS".equalsIgnoreCase(str)){
				mapOfSubProductTypes.put("FREENESS", "true");
			}
		}
		
		return mapOfSubProductTypes;
	}
	
	/*
	 * 获取SubProductTypes组合中文(门票,酒店,其他...)
	 * */
	public String getZHOfSubProductTypes(){
		
		if(StringUtil.isEmptyString(subProductTypes)){
			return "";
		}
		
		String[] partitionStr = getSubProductTypes().split(",");
		StringBuffer sb = new StringBuffer();
		for(String str : partitionStr){
			if("TICKET".equalsIgnoreCase(str)){
				sb.append("门票,");
			}else if("HOTEL".equalsIgnoreCase(str)){
				sb.append("酒店,");
			}else if("OTHER".equalsIgnoreCase(str)){
				sb.append("其他,");
			}else if("GROUP_FOREIGN".equalsIgnoreCase(str)){
				sb.append("出境跟团游,");
			}else if("FREENESS_FOREIGN".equalsIgnoreCase(str)){
				sb.append("出境自由行,");
			}else if("GROUP_LONG".equalsIgnoreCase(str)){
				sb.append("长途跟团游,");
			}else if("FREENESS_LONG".equalsIgnoreCase(str)){
				sb.append("长途自由行,");
			}else if("GROUP".equalsIgnoreCase(str)){
				sb.append("短途跟团游,");
			}else if("SELFHELP_BUS".equalsIgnoreCase(str)){
				sb.append("自助巴士班,");
			}else if("FREENESS".equalsIgnoreCase(str)){
				sb.append("目的地自由行,");
			}
		}
		String str = sb.toString();
		String subProductTypes = str.substring(0, str.length()-1);
		
		return subProductTypes;
	}
	
	public Long getMarkOrderCouponRuleId() {
		return markOrderCouponRuleId;
	}

	public void setMarkOrderCouponRuleId(Long markOrderCouponRuleId) {
		this.markOrderCouponRuleId = markOrderCouponRuleId;
	}

	public String getSubProductTypes() {
		return subProductTypes;
	}

	public void setSubProductTypes(String subProductTypes) {
		this.subProductTypes = subProductTypes;
	}

	public void setMinAmount(Long minAmount) {
		this.minAmount = minAmount;
	}

	public void setMaxAmount(Long maxAmount) {
		this.maxAmount = maxAmount;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public Long getMinAmount() {
		return minAmount;
	}

	public Long getMaxAmount() {
		return maxAmount;
	}

	public Long getCouponAmount() {
		return couponAmount;
	}

	public void setCouponAmount(Long couponAmount) {
		this.couponAmount = couponAmount;
	}

	public Float getCouponAmountYuan() {
		return PriceUtil.convertToYuan(this.couponAmount);
	}

	public Date getCouponEndTime() {
		return couponEndTime;
	}

	public void setCouponEndTime(Date couponEndTime) {
		this.couponEndTime = couponEndTime;
	}
	
}
