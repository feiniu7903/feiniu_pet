package com.lvmama.comm.pet.vo.favor;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.lvmama.comm.bee.po.ord.OrdOrderItemMeta;
import com.lvmama.comm.bee.po.ord.OrdOrderItemProd;
import com.lvmama.comm.pet.po.businessCoupon.BusinessCoupon;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;

/**
 * 产品类的优惠策略抽象类
 * @author Brian
 * 此抽象类是所有作用在销售产品上的优惠策略的父类。
 */
public abstract class ProductFavorStrategy extends AbstractFavorStrategy {
	/**
	 * 序列值
	 */
	private static final long serialVersionUID = -8466855723408659755L;
	
	/**
	 * 产品优惠
	 */
	protected BusinessCoupon businessCoupon;
	
	protected Long specialDiscountAmount;

	/**
	 * 构造器
	 * @param markCoupon
	 */
	public ProductFavorStrategy(BusinessCoupon businessCoupon) {
		super();
		this.businessCoupon = businessCoupon;
	}
	
	/**
	 * 是否使用
	 * @param ordOrderItemProd 销售产品信息
	 * @param discountAmount 已需要优惠的金额
	 * @return
	 * <p>销售产品信息保存的是原始的销售产品信息，经过一系列转化后，产品的应付金额会发生变化</p>
	 */
	public abstract boolean isApply(OrdOrderItemProd ordOrderItemProd, Long discountAmount);
	
	
	/**
	 * 策略的优惠金额
	 * @param ordOrderItemProd 销售产品信息
	 * @param discountAmount 已需要优惠的金额
	 * @return
	 * <p>销售产品信息保存的是原始的销售产品信息，经过一系列转化后，产品的应付金额会发生变化</p>
	 */	
	public abstract Long getDiscountAmount(OrdOrderItemProd ordOrderItemProd, Long discountAmount);
	
	
	/**
	 * 是否使用
	 * @param ordOrderItemProd 采购产品信息
	 * @param discountAmount 已需要优惠的金额
	 * @return
	 * <p>销售产品信息保存的是原始的销售产品信息，经过一系列转化后，产品的应付金额会发生变化</p>
	 */
	public abstract boolean isApply(OrdOrderItemMeta ordOrderItemMeta, Long discountAmount);
	
	/**
	 * 策略的优惠金额
	 * @param ordOrderItemMeta 采购产品信息
	 * @param discountAmount  已需要优惠的金额
	 * @return
	 */
	public abstract Long getDiscountAmount(OrdOrderItemMeta ordOrderItemMeta, Long discountAmount);
	
	
	/**
	 * 在页面上展示的提示语句
	 * @return
	 */
	public abstract String getDisplayInfo(OrdOrderItemProd ordOrderItemProd, Long discountAmount);
	
	/**
	 * 返回X值
	 * @return
	 */
	public Long getArgumentX() {
		return businessCoupon.getArgumentX();
	}
	
	/**
	 * 返回Y值
	 * @return
	 */
	public Long getArgumentY() {
		return businessCoupon.getArgumentY();
	}
	
	/**
	 * 返回Z值
	 * @return
	 */
	public Long getArgumentZ() {
		return businessCoupon.getArgumentZ();
	}
	 
	public BusinessCoupon getBusinessCoupon() {
		return businessCoupon;
	}
	
	/**
	 * 设置特定的优惠金额
	 * @param specialDiscountAmount 特定的优惠金额
	 * <p>{@link specialDiscountAmount}</p>
	 */
	public void setSpecialDiscountAmount(Long discountAmount) {
		this.specialDiscountAmount = discountAmount;
	}
	
	/**
	 * 获取下单日历提示信息参数
	 * @return
	 */
	public abstract String getTimePriceDisplayParams(ProdBranchSearchInfo prodBranchSearchInfo, Long discountAmount);
	
	/**
	 * 获取下单日历提示价格
	 * @return
	 */
	public abstract Float getTimePriceDisplayDiscountAmount();
	
	@Override
	public String toString() {
		return new ToStringBuilder(this).append("favorType", getFavorType())
				.append("argumentX", this.getArgumentX()).append("argumentY", this.getArgumentY())
				.append("argumentZ", this.getArgumentZ()).append("specialDiscountAmount", specialDiscountAmount)
				.toString();
	}
}
