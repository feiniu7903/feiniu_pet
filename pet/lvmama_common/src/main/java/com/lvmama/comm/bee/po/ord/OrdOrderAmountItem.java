package com.lvmama.comm.bee.po.ord;

import java.io.Serializable;
import java.util.Date;

import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;

public class OrdOrderAmountItem implements Serializable {
    /**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 4485761735581987525L;

	private Long orderAmountItemId;

    private String itemName;

    private Long itemAmount;

    private Date createTime;

    private Long orderId;

    private String oderAmountType;
    

	public Long getOrderAmountItemId() {
        return orderAmountItemId;
    }

    public void setOrderAmountItemId(Long orderAmountItemId) {
        this.orderAmountItemId = orderAmountItemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public Long getItemAmount() {
        return itemAmount;
    }

    public void setItemAmount(Long itemAmount) {
        this.itemAmount = itemAmount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getOderAmountType() {
        return oderAmountType;
    }

    public void setOderAmountType(String oderAmountType) {
        this.oderAmountType = oderAmountType;
    }
    
    public float getAmountYuan() {
    	return PriceUtil.convertToYuan(itemAmount.longValue());
    }
    
    public boolean isCouponItem() {
    	return Constant.ORDER_AMOUNT_TYPE.ORDER_COUPON_AMOUNT.name().equals(oderAmountType);
    }
    
    public boolean isOrderItem() {
    	return Constant.ORDER_AMOUNT_TYPE.ORDER_AMOUNT.name().equals(oderAmountType);
    }
    
}