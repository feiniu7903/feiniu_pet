package com.lvmama.pet.pub.dao;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComProvince;

public class ComProvinceDAO extends BaseIbatisDAO{

	public List<ComProvince> getAllProvince(){
		List<ComProvince> provinceList = super.queryForList("COM_PROVINCE.selectAll");
		ComProvince pro = new ComProvince();
		pro.setProvinceName("请选择");
		provinceList.add(0, pro);
		return provinceList;
	}

	public ComProvince selectByPrimaryKey(String provinceId) {
		ComProvince key = new ComProvince();
		key.setProvinceId(provinceId);
		ComProvince record = (ComProvince) super
				.queryForObject("COM_PROVINCE.selectByPrimaryKey",
						key);
		return record;
	}
	
	public ComProvince selectByProvinceName(String provinceName){
		List<ComProvince> record = super
				.queryForList("COM_PROVINCE.selectByProvinceName",
						provinceName);
		if(CollectionUtils.isNotEmpty(record)){
			return record.get(0);
		}
		return null;
	}

}