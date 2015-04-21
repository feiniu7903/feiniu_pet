package com.lvmama.operate.service;

import java.util.List;

import com.lvmama.comm.pet.po.pub.ComProvince;


public interface PlaceCityService {
     
     List<ComProvince> getProvinceList();
     
     List<ComProvince> getProvinceListSelected(String cityId);
     
     ComProvince selectByPrimaryKey(String provinceId) ;
}
