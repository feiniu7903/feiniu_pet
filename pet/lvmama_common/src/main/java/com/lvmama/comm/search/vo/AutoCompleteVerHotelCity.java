package com.lvmama.comm.search.vo;

import java.io.Serializable;

public class AutoCompleteVerHotelCity extends AutoCompleteVerHotel implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6842606662629984092L;
	
	/**
	 * 是否是热门城市
	 */
	private boolean isHot;

	public boolean isHot() {
		return isHot;
	}

	public void setHot(boolean isHot) {
		this.isHot = isHot;
	}

	
}
