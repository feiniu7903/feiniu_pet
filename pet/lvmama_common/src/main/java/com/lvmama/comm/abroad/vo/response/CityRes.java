package com.lvmama.comm.abroad.vo.response;

import java.util.List;

public class CityRes extends ErrorRes {
	private static final long serialVersionUID = 1L;
	private List<CityBean> cities;

	public List<CityBean> getCities() {
		return cities;
	}

	public void setCities(List<CityBean> cities) {
		this.cities = cities;
	}

}
