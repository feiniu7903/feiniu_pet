/**
 * 
 */
package com.lvmama.comm.pet.po.mark;

import java.io.Serializable;
import java.util.Date;

/**
 * 积分兑换优惠券
 * @author liuyi
 *
 */
public class MarkCouponPointChange  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8441155000641622865L;
	
	private Long markCouponPointChangeId;
	
	//兑换积分
	private Long point;
	
	//产品子类型
	private String subProductType;
	
	private Long couponId;
	
 	//创建时间
	private Date createTime;
	
	//优惠券名称
	private String couponName;
	
	//描述，用于显示（需要外部设入，数据库不存储）
	private String description;

	public Long getMarkCouponPointChangeId() {
		return markCouponPointChangeId;
	}

	public void setMarkCouponPointChangeId(Long markCouponPointChangeId) {
		this.markCouponPointChangeId = markCouponPointChangeId;
	}

	public Long getPoint() {
		return point;
	}

	public void setPoint(Long point) {
		this.point = point;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public Long getCouponId() {
		return couponId;
	}

	public void setCouponId(Long couponId) {
		this.couponId = couponId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getCouponName() {
		return couponName;
	}

	public void setCouponName(String couponName) {
		this.couponName = couponName;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
