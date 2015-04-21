package com.lvmama.comm.pet.po.mark;

import java.io.Serializable;
import java.util.Date;
/**
 * 优惠码.
 */
public class MarkCouponCode implements Serializable {
    /**
	 * 序列化
	 */
	private static final long serialVersionUID = -7355233704912493130L;

	private Long couponCodeId;

    private Long couponId;
    //优惠码,  couponCode = MarkCoupon.couponType + couponType.firstCode;
    private String couponCode;

    private String used = "false";
    
    private Date beginTime;
    
    private Date endTime;
    
    private Date createTime;
    
	/**
	 * 优惠券号码是否过期
	 * @return 是否过期
	 * <p>判断此优惠券号码是否在有效时间之内,<code>true</code>代表过期，<code>false</code>代表未过期</p>
	 */
	public boolean isOverDue() {
		if (null != beginTime && null != endTime) {
			Date now = new Date(System.currentTimeMillis());
			return !(now.after(beginTime) && now.before(endTime));
		}
		return true;
	}    
    

    public String getUsed() {
        return used;
    }
    public void setUsed(String used) {
        this.used = used == null ? null : used.trim();
    }
    public String getZhUsed(){
    	return "false".equals(this.used)?"未使用":"已使用";
    }
    
    public Long getCouponCodeId() {
        return couponCodeId;
    }

    public void setCouponCodeId(Long couponCodeId) {
        this.couponCodeId = couponCodeId;
    }

    public Long getCouponId() {
        return couponId;
    }
    
    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode == null ? null : couponCode.trim();
    }

	public Date getBeginTime() {
		return beginTime;
	}

	public void setBeginTime(Date beginTime) {
		this.beginTime = beginTime;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreatetime(Date createTime) {
		this.createTime = createTime;
	}
	
	public void setValidTimeByCouponDefination(MarkCoupon markCoupon){
		if(markCoupon!= null && "FIXED".equals(markCoupon.getValidType()) && markCoupon.getBeginTime() != null && markCoupon.getEndTime() != null){
			beginTime = markCoupon.getBeginTime();
			endTime = markCoupon.getEndTime();
		}
	}
}