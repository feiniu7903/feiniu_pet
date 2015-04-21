package com.lvmama.operate.util;

import java.util.List;

import org.zkoss.zk.ui.Component;
import org.zkoss.zkplus.databind.TypeConverter;

import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.operate.service.PlaceCityService;


public class AddressZKConverter implements TypeConverter {
     
     private static final String AREA_SPLIT_CHAR = ";";
     private static final String AREA_APPEND_CHAR = " ";
     private PlaceCityService placeCityService = (PlaceCityService)SpringBeanProxy.getBean("placeCityService");
     /**
      * 缓存代码集
      */
     private List<ComProvince> provinceList;
     @Override
     public Object coerceToBean(Object arg0, Component arg1) {
          // TODO Auto-generated method stub
          return null;
     }

     @Override
     public Object coerceToUi(Object arg0, Component arg1) {
          if(null!=arg0){
               String[] area = ((String)arg0).split(AREA_SPLIT_CHAR);
               if(null == provinceList || (null != provinceList && provinceList.size() == 0)){
                    provinceList = placeCityService.getProvinceList();
               }
               StringBuffer zhAddress = new StringBuffer();
               for(ComProvince province : provinceList){
                    String code = province.getProvinceId();
                    String name = province.getProvinceName();
                    for(String address : area){
                         if(address.equals(code)){
                              zhAddress.append(name).append(AREA_APPEND_CHAR);
                         }
                    }
               }
               return zhAddress.toString();
          }
          return null;
     }

     public PlaceCityService getPlaceCityService() {
          return placeCityService;
     }

     public void setPlaceCityService(PlaceCityService placeCityService) {
          this.placeCityService = placeCityService;
     }

}
