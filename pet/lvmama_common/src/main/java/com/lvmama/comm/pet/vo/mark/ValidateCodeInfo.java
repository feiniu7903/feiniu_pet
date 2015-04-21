package com.lvmama.comm.pet.vo.mark;
import java.io.Serializable;

import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class ValidateCodeInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1088933359196014995L;
	private String uuid;
	private String productName;
	private String productId;
	private String code;
	private String key;
	private String value;
	private Long couponId;
	private Long youhuiAmount=0L;
	private String paymentChannel;
	
	private boolean valid;
	
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getValue() {
		/**Modified by caokun*/
		if(!"ERROR_INFO".equals(this.key)&&!Constant.COUPON_INFO.OK.name().equals(this.key)){
			return CodeSet.getInstance().getCodeName(Constant.CODE_TYPE.COUPON_INFO.name(), this.key);
			
		} else if("OK".equals(this.key)){
			return "您使用优惠抵扣了 ￥"+PriceUtil.convertToYuan(this.youhuiAmount)+"订单金额";
		}
		else{
		  return value;
		}
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	public float getOrderYouhuiAmountYuan(){
		return PriceUtil.convertToYuan(this.youhuiAmount);	

	}

	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public boolean isValid() {
		return valid;
	}
	public void setValid(boolean valid) {
		this.valid = valid;
	}
	public Long getCouponId() {
		return couponId;
	}
	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}
	public Long getYouhuiAmount() {
		return youhuiAmount;
	}
	public void setYouhuiAmount(Long youhuiAmount) {
		this.youhuiAmount = youhuiAmount;
	}
	public String getPaymentChannel() {
		return paymentChannel;
	}
	public void setPaymentChannel(String paymentChannel) {
		this.paymentChannel = paymentChannel;
	}
	@Override
	public String toString() {
		return "ValidateCodeInfo [uuid=" + uuid + ", productName="
				+ productName + ", productId=" + productId + ", code=" + code
				+ ", key=" + key + ", value=" + value + ", couponId="
				+ couponId + ", youhuiAmount=" + youhuiAmount
				+ ", paymentChannel=" + paymentChannel + ", valid=" + valid
				+ "]";
	}
	
}
