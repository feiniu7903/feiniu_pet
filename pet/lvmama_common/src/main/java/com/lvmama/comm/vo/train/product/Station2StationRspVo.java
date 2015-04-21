package com.lvmama.comm.vo.train.product;

import java.util.List;

import com.lvmama.comm.vo.train.RspVo;

public class Station2StationRspVo extends RspVo {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4568795216L;
	
	public Station2StationRspVo(){}
	public Station2StationRspVo(List<Station2StationInfo> station2StationInfos){
		this.station2StationInfos = station2StationInfos;
	}
	
	/**
	 * 站站信息
	 */
	private List<Station2StationInfo> station2StationInfos;
	public List<Station2StationInfo> getStation2StationInfos() {
		return station2StationInfos;
	}
	public void setStation2StationInfos(
			List<Station2StationInfo> station2StationInfos) {
		this.station2StationInfos = station2StationInfos;
	}

}
