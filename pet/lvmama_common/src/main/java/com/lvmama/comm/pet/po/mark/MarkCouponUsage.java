package com.lvmama.comm.pet.po.mark;

import java.io.Serializable;
import java.util.Date;

public class MarkCouponUsage implements Serializable{
    /**
	 * 
	 */
	private static final long serialVersionUID = 9166220865510411697L;

	private Long couponCodeId;

    //用来存放订单ID
    private Long objectId;

    //对象类型，订单还是销售产品还是采购产品
    private String objectType;

    private Date createTime;

    private Long usageId;
    
    //子对象ID，一般用来存放销售产品ID或采购产品ID
    private Long subObjectIdA;
    
    //子对象ID，一般用来存放类别ID
    private Long subObjectIdB;
   

	//使用了哪种策略
    private String strategy;
    
    //优惠了多少钱
    private Long amount;

    public Long getCouponCodeId() {
        return couponCodeId;
    }

    public void setCouponCodeId(Long couponCodeId) {
        this.couponCodeId = couponCodeId;
    }

    public Long getObjectId() {
        return objectId;
    }

    public void setObjectId(Long objectId) {
        this.objectId = objectId;
    }

    public String getObjectType() {
        return objectType;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType == null ? null : objectType.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getUsageId() {
        return usageId;
    }

    public void setUsageId(Long usageId) {
        this.usageId = usageId;
    }

	public Long getSubObjectIdA() {
		return subObjectIdA;
	}

	public void setSubObjectIdA(Long subObjectIdA) {
		this.subObjectIdA = subObjectIdA;
	}

	public String getStrategy() {
		return strategy;
	}

	public void setStrategy(String strategy) {
		this.strategy = strategy;
	}

	public Long getAmount() {
		return amount;
	}

	public void setAmount(Long amount) {
		this.amount = amount;
	}
    
    public Long getSubObjectIdB() {
		return subObjectIdB;
	}

	public void setSubObjectIdB(Long subObjectIdB) {
		this.subObjectIdB = subObjectIdB;
	}
}