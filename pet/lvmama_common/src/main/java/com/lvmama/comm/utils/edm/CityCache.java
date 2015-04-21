package com.lvmama.comm.utils.edm;


/**
 * 
 */

import java.util.HashMap;
import java.util.Map;

import com.lvmama.comm.pet.po.edm.EdmDicCity;




/**
 * @author yangbin
 *
 */
public class CityCache {

     
     private Map<String, EdmDicCity> cityMap;
     private Map<String, EdmDicCity> capitalMap;
     
     private static CityCache _cityCache;
     
     protected CityCache()
     {
          cityMap=new HashMap<String, EdmDicCity>();
          capitalMap=new HashMap<String, EdmDicCity>();
     }
     
     public static CityCache getInstance()
     {
          if(_cityCache==null)
          {
               _cityCache=new CityCache();
          }
          return _cityCache;
     }
     
     public EdmDicCity getCity(String key)
     {
          if(!cityMap.containsKey(key))
               return null;
          return cityMap.get(key);
     }
     
     public void putCity(EdmDicCity dc)
     {
          if(dc==null)
               return;
          cityMap.put(dc.getId(), dc);          
     }
     
     public EdmDicCity getCapital(String key)
     {
          if(!capitalMap.containsKey(key))
               return null;
          return capitalMap.get(key);
     }
     
     public void putCapital(EdmDicCity dc)
     {
          if(dc==null)
               return;
          capitalMap.put(dc.getId(), dc);
     }
}
