package com.lvmama.comm.vo.train.product;

import java.util.List;

import com.lvmama.comm.vo.train.RspVo;

public class CityStationRspVo extends RspVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 12394719347234912L;
	
	public CityStationRspVo(){}
	public CityStationRspVo(List<CityStationInfo> cityStationInfos){
		this.cityStationInfos = cityStationInfos;
	}
	
	/**
	 * 城市车站信息
	 */
	List<CityStationInfo> cityStationInfos;
	public List<CityStationInfo> getCityStationInfos() {
		return cityStationInfos;
	}
	public void setCityStationInfos(List<CityStationInfo> cityStationInfos) {
		this.cityStationInfos = cityStationInfos;
	}

}
