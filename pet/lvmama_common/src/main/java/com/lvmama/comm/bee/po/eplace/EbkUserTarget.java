package com.lvmama.comm.bee.po.eplace;

import java.io.Serializable;

/**
 * Ebk用户和履行对象的绑定关系.
 * 
 * @author zhangkexing
 */
public class EbkUserTarget implements Serializable {
	private static final long serialVersionUID = -5946719679614974850L;
	private Long userTargetId;
	private Long userId;
	private Long supPerformTargetId;

	public Long getUserTargetId() {
		return userTargetId;
	}

	public void setUserTargetId(Long userTargetId) {
		this.userTargetId = userTargetId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public Long getSupPerformTargetId() {
		return supPerformTargetId;
	}

	public void setSupPerformTargetId(Long supPerformTargetId) {
		this.supPerformTargetId = supPerformTargetId;
	}

}
