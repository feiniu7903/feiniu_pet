package com.lvmama.comm.bee.po.pass;

import org.apache.commons.lang3.time.DateFormatUtils;

import com.lvmama.comm.bee.vo.ord.AbstractDetail;


/**
 * 通关明细.
 * 
 * @author tom
 * @version Super二期 10/10/11
 * @since Super二期
 */
public final class PassPortDetail extends AbstractDetail {
	/**
	 * serialVersionUID.
	 */
	private static final long serialVersionUID = 3324688908791375535L;

	private String operate;

	public String getStrUsedTime() {
		if (this.getUsedTime() != null) {
			return DateFormatUtils.format(this.getUsedTime(), "yyyy-MM-dd HH:mm");
		}
		return "";
	}

	
	public String getStrDeadlineTime() {
		if (this.getDeadlineTime() != null) {
			return DateFormatUtils.format(this.getDeadlineTime(), "yyyy-MM-dd");
		}
		return "";
	}

	public String getOperate() {
		if (this.isCanceled()) {
			operate = "取消";
		} else {
			if (this.getIsPass()) {
				operate = "已通关";
			} else {
				operate = "未通关";
			}
		}
		return operate;
	}

	public void setOperate(String operate) {
		this.operate = operate;
	}

}