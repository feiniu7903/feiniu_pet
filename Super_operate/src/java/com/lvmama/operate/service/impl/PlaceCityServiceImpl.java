package com.lvmama.operate.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.operate.dao.ComCityDAO;
import com.lvmama.operate.dao.ComProvinceDAO;
import com.lvmama.operate.service.PlaceCityService;

public class PlaceCityServiceImpl implements PlaceCityService {          
          private ComProvinceDAO comProvinceDAO; 
          
          private ComCityDAO comCityDAO;
          
          public List<ComProvince> getProvinceList() {
               List<ComProvince> provinceList = comProvinceDAO.getAllProvince();
               provinceList.get(0).setChecked("true");
               return provinceList;
          }
          
          public List<ComProvince> getProvinceListSelected(String cityId) {
               List<ComProvince> provinceList = comProvinceDAO.getAllProvince();
               if (cityId!=null) {
                    ComCity city = comCityDAO.selectByPrimaryKey(cityId);
                    for(int i=1;i<provinceList.size();i++) {
                         if (city.getProvinceId().equals(provinceList.get(i).getProvinceId()))
                              provinceList.get(i).setChecked("true");
                    }
               }
               return provinceList;
          }

          public ComProvince selectByPrimaryKey(String provinceId) {
               return comProvinceDAO.selectByPrimaryKey(provinceId);
          }

          public List<ComCity> getCityListByProvinceId(String provinceId) {
               List<ComCity> cityList = comCityDAO.selectCityByProvinceId(provinceId);
               cityList.get(0).setChecked("true");
               return cityList;
          }


          public List<ComCity> getCityListSelected(String cityId) {
               if (cityId!=null) {
                    ComCity city = comCityDAO.selectByPrimaryKey(cityId);
                    List<ComCity> cityList = comCityDAO.selectCityByProvinceId(city.getProvinceId());
                    for(int i=1;i<cityList.size();i++) {
                         if (cityId.equals(cityList.get(i).getCityId()))
                              cityList.get(i).setChecked("true");
                    }
                    return cityList;
               }
               return new ArrayList<ComCity>();
          }

          public void setComProvinceDAO(ComProvinceDAO comProvinceDAO) {
               this.comProvinceDAO = comProvinceDAO;
          }

          public void setComCityDAO(ComCityDAO comCityDAO) {
               this.comCityDAO = comCityDAO;
          }

}
