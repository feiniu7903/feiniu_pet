package com.lvmama.comm.vo.train.product;

import java.util.List;

import com.lvmama.comm.vo.train.RspVo;

public class StationRspVo extends RspVo {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3491732491737123123L;
	
	public StationRspVo(){}
	public StationRspVo(List<StationInfo> stationInfos){
		this.stationInfos = stationInfos;
	}
	
	/**
	 * 车站信息
	 */
	List<StationInfo> stationInfos;
	public List<StationInfo> getStationInfos() {
		return stationInfos;
	}
	public void setStationInfos(List<StationInfo> stationInfos) {
		this.stationInfos = stationInfos;
	}
}
