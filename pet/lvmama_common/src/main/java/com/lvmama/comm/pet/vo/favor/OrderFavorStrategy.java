/**
 * 
 */
package com.lvmama.comm.pet.vo.favor;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.lvmama.comm.bee.po.ord.OrdOrder;
import com.lvmama.comm.pet.po.mark.MarkCoupon;
import com.lvmama.comm.pet.po.mark.MarkCouponCode;

/**
 * 订单类的优惠策略抽象类
 * @author liuyi
 * 此抽象类是所有作用在订单上的优惠策略的父类。
 */
public abstract class OrderFavorStrategy extends AbstractFavorStrategy {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -739312917772715611L;
	
	/**
	 * 优惠券批次
	 */
	protected MarkCoupon markCoupon;
	
	/**
	 * 优惠券号码
	 */
	protected MarkCouponCode markCouponCode;
	
	/**
	 * 特定优惠金额。优先于优惠批次的设置，针对每个产品单独设置的优惠金额。
	 */
	protected Long discountAmount;
	/**
	 * 构造器
	 * @param markCoupon
	 */
	public OrderFavorStrategy(MarkCoupon markCoupon, MarkCouponCode markCouponCode) {
		super();
		this.markCoupon = markCoupon;
		this.markCouponCode = markCouponCode;
	}
	
	/**
	 * 是否使用
	 * @param order 订单信息
	 * @param discountAmount 已需要优惠的金额
	 * @return
	 * <p>订单信息保存的是原始的订单信息，经过一系列转化后，订单的应付金额会发生变化，故需要综合考虑此策略之前对订单价格产生影响的因素.<code>discountAmount</code>就是此策略之前的策略对订单价格的影响之和</p>
	 */
	public abstract boolean isApply(OrdOrder order, Long discountAmount);
	
	/**
	 * 策略的优惠金额
	 * @param order 订单信息
	 * @param discountAmount 已需要优惠的金额
	 * @return
	 * <p>订单信息保存的是原始的订单信息，经过一系列转化后，订单的应付金额会发生变化，故需要综合考虑此策略之前对订单价格产生影响的因素.<code>discountAmount</code>就是此策略之前的策略对订单价格的影响之和</p>
	 */	
	public abstract Long getDiscountAmount(OrdOrder order, Long discountAmount);
	
	/**
	 * 当不满足策略使用条件时，返回的提示语句
	 * @return
	 */
	public abstract String getInvalidDesc();
	
	/**
	 * 设置特定的优惠金额
	 * @param specialDiscountAmount 特定的优惠金额
	 * <p>{@link specialDiscountAmount}</p>
	 */
	public void setSpecialDiscountAmount(Long discountAmount) {
		this.discountAmount = discountAmount;
	}
	
	/**
	 * 返回X值
	 * @return
	 */
	public Long getArgumentX() {
		return markCoupon.getArgumentX();
	}
	
	/**
	 * 返回Y值
	 * @return
	 */
	public Long getArgumentY() {
		return markCoupon.getArgumentY();
	}
	
	/**
	 * 返回Z值
	 * @return
	 */
	public Long getArgumentZ() {
		return markCoupon.getArgumentZ();
	}
	
	public MarkCoupon getMarkCoupon() {
		return markCoupon;
	}

	public MarkCouponCode getMarkCouponCode() {
		return markCouponCode;
	}
	
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("favorType", getFavorType())
				.append("argumentX", this.getArgumentX()).append("argumentY", this.getArgumentY())
				.append("argumentZ", this.getArgumentZ()).append("discountAmount", discountAmount)
				.toString();
	}
}
