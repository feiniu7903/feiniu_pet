package com.lvmama.operate.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComProvince;

public class ComProvinceDAO extends BaseIbatisDAO{

	public List<ComProvince> getAllProvince(){
		List<ComProvince> provinceList = super.queryForList("OPERATE_PLACE.selectProvinceAll");
		ComProvince pro = new ComProvince();
		pro.setProvinceName("请选择");
		provinceList.add(0, pro);
		return provinceList;
	}

	public ComProvince selectByPrimaryKey(String provinceId) {
		ComProvince key = new ComProvince();
		key.setProvinceId(provinceId);
		ComProvince record = (ComProvince) super.queryForObject("OPERATE_PLACE.selectByProvinceKey",
						key);
		return record;
	}
}