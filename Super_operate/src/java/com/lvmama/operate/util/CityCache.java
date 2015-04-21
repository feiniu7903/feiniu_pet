package com.lvmama.operate.util;

/**
 * 
 */

import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;

/**
 * @author yangbin
 *
 */
public class CityCache {

     
     private Map<String, ComCity> cityMap;
     private Map<String, ComProvince> capitalMap;
     
     private static CityCache _cityCache;
     
     protected CityCache()
     {
          cityMap=new HashMap<String, ComCity>();
          capitalMap=new HashMap<String, ComProvince>();
     }
     
     public static CityCache getInstance()
     {
          if(_cityCache==null)
          {
               _cityCache=new CityCache();
          }
          return _cityCache;
     }
     
     public ComCity getCity(String key)
     {
          if(!cityMap.containsKey(key))
               return null;
          return cityMap.get(key);
     }
     
     public void putCity(ComCity dc)
     {
          if(dc==null)
               return;
          cityMap.put(dc.getCityId(), dc);          
     }
     
     public ComProvince getCapital(String key)
     {
          if(!capitalMap.containsKey(key))
               return null;
          return capitalMap.get(key);
     }
     
     public void putCapital(ComProvince dc)
     {
          if(dc==null)
               return;
          capitalMap.put(dc.getProvinceId(), dc);
     }
}
