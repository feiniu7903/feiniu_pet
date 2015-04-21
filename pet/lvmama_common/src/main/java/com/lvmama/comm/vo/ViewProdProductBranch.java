package com.lvmama.comm.vo;

import com.lvmama.comm.bee.po.prod.ProdProductBranch;
/**
 * @author yangbin
 *
 */
public class ViewProdProductBranch extends ProdProductBranch{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3419621392300609474L;
	private TimeInfo timeInfo;

	/**
	 * @return the timeInfo
	 */
	public TimeInfo getTimeInfo() {
		return timeInfo;
	}

	/**
	 * @param timeInfo the timeInfo to set
	 */
	public void setTimeInfo(TimeInfo timeInfo) {
		this.timeInfo = timeInfo;
	}
	
	
	
}