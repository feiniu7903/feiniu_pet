package com.lvmama.front.web.buy;

import java.text.SimpleDateFormat;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.InterceptorRef;
import org.apache.struts2.convention.annotation.InterceptorRefs;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.service.favor.FavorService;
import com.lvmama.comm.pet.service.mark.MarkCouponService;
import com.lvmama.comm.pet.vo.mark.ValidateCodeInfo;
import com.lvmama.front.web.BaseAction;
@ParentPackage("json-default")
@Results({	
	@Result(name="home",location="http://www.lvmama.com",type="redirect")
})

@InterceptorRefs({
    @InterceptorRef("defaultStack")
})
public class CheckMinAndMaxAmtAction extends BaseAction{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1705608398547970702L;
	private ValidateCodeInfo info;
	private String productId;
	private String code;
	private String couponId;
	private String withCode;
	private String couponCodeId;
	private Long orderPrice;
	//private Long couponPay;
	private Long orderQuantity;
	private String jsonMsg;
	/**优惠券绑定的子产品类型 */
	private String subProductType;
	
	/**
	 * 优惠券远程调用逻辑接口
	 */
	private FavorService favorService;

	
	
	SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");


	
	/**
	 * 检查优惠券并加入购物对象
	 * @return
	 */
	@Action(value="/check/validateCouponCodeOrOrder",results=@Result(type="json",name="validateCouponCodeOrOrder",params={"includeProperties","info.*"}))
	public String validateCouponCodeOrOrder() {
//		if (StringUtils.isNotBlank(productId)) {
//			info = favorService.validateCoupon(Long.valueOf(productId), code, this.getUserId(), orderPrice, orderQuantity, this.subProductType);
//		}
//		try {
//			Long prodId=null;
//			if(this.productId!=null&&!"".equals(this.productId)){
//				prodId=Long.valueOf(this.productId);
//				//this.info = couponService.validateCoupon(productId, code, this.getUserId(), orderPrice, orderQuantity,subProductType);
//				//mod by ljp 20120518
//				this.info = this.couponService.validateCoupon(Long.valueOf(productId), code, this.getUserId(), orderPrice, orderQuantity, this.subProductType);
//				//this.info = this.couponService.validateCoupon(Long.valueOf(productId), code, this.getUserId(), orderPrice, couponPay, orderQuantity, this.subProductType);
//				//this.info = this.couponService.validateCoupon(Long.valueOf(productId), code, this.getUserId(), couponPay, orderQuantity, this.subProductType);
//			}
//
//			//this.info = couponService.validateCoupon(prodId,(this.couponId==null || "".equals(this.couponId))?null:Long.valueOf(couponId), code, this.getUserId(), orderPrice, orderQuantity);			
//			
//
//		}catch(Exception ex){
//			StackOverFlowUtil.printErrorStack(getRequest(), getResponse(), ex);
//		}
		return "validateCouponCodeOrOrder";
	}
	

//	@Action(value="/check/removeCoupon",results=@Result(type="json",name="removeCoupon",params={"includeProperties","jsonMsg.*"}))
//	public String removeCoupon(){
//		this.jsonMsg = "ok";
//		return "removeCoupon";
//	}


	public ValidateCodeInfo getInfo() {
		return info;
	}


	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}



	public String getCouponId() {
		return couponId;
	}

	public void setCouponId(String couponId) {
		this.couponId = couponId;
	}

	public String getWithCode() {
		return withCode;
	}

	public void setWithCode(String withCode) {
		this.withCode = withCode;
	}	

	public String getJsonMsg() {
		return jsonMsg;
	}

	public void setJsonMsg(String jsonMsg) {
		this.jsonMsg = jsonMsg;
	}

	public String getCouponCodeId() {
		return couponCodeId;
	}

	public void setCouponCodeId(String couponCodeId) {
		this.couponCodeId = couponCodeId;
	}
	

	public Long getOrderPrice() {
		return orderPrice;
	}

	public void setOrderPrice(Long orderPrice) {
		this.orderPrice = orderPrice;
	}
	
	public void setOrderPrice(String[] orderPrices) {
		if (orderPrices != null && orderPrices.length > 0 && (this.orderPrice == null || this.orderPrice.longValue() == 0)) {
			for (String op : orderPrices) {
				try {
					 this.orderPrice = Long.valueOf(op);
				} catch (NumberFormatException nfe) {
					continue;
				}
			}
		}
	}
	
	public Long getOrderQuantity() {
		return orderQuantity;
	}

	public void setOrderQuantity(Long orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}


	public void setFavorService(final FavorService favorService) {
		this.favorService = favorService;
	}


}
