package com.lvmama.passport.processor.impl.client.gulangyu.model;

import java.util.List;

/**
 * 鼓浪屿查询景区返回的对象
 * 
 * @author lipengcheng
 * 
 */
public class ScenicSpotResponse {

	private List<ScenicSpot> scenicSpotList;

	public List<ScenicSpot> getScenicSpotList() {
		return scenicSpotList;
	}

	public void setScenicSpotList(List<ScenicSpot> scenicSpotList) {
		this.scenicSpotList = scenicSpotList;
	}

}
