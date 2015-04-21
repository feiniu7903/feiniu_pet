package com.lvmama.operate.web.statistics;

import org.apache.commons.lang3.StringUtils;
import org.zkoss.zul.Listcell;
import org.zkoss.zul.Listitem;
import org.zkoss.zul.ListitemRenderer;

import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.spring.SpringBeanProxy;
import com.lvmama.operate.service.EdmUsersService;
import com.lvmama.operate.util.CityCache;

/**
 * @author yangbin
 *
 */
public class CityRenderer implements ListitemRenderer {

     private EdmUsersService edmUsersService;
     
     public CityRenderer()
     {
          edmUsersService=(EdmUsersService)SpringBeanProxy.getBean("edmUsersService");
     }
     
     @Override
     public void render(Listitem item, Object arg1) throws Exception {
          // TODO Auto-generated method stub
    	 UserUser user=(UserUser)arg1;
          if(user!=null)
          {
               Listcell cell=new Listcell();
               cell.setLabel(user.getUserName());
               cell.setParent(item);
               
               Listcell cellCapital=new Listcell();               
               Listcell cellCity=new Listcell();
               ComCity city=CityCache.getInstance().getCity(user.getCityId());
               if(city==null)
               {
                    city=edmUsersService.getCity(user.getCityId());
                    CityCache.getInstance().putCity(city);
               }
               if(city!=null)
               {
                    cellCity.setLabel(city.getCityName());
                    ComProvince capital=CityCache.getInstance().getCapital(city.getProvinceId());
                    if(capital==null)
                    {
                         capital=edmUsersService.getProvince(city.getProvinceId());
                         CityCache.getInstance().putCapital(capital);
                    }
                    if(capital!=null)
                         cellCapital.setLabel(capital.getProvinceName());
               }
               cellCapital.setParent(item);
               cellCity.setParent(item);
               cell=new Listcell();
               if(StringUtils.isNotEmpty(user.getGender()))
               {
                    cell.setLabel(user.getGender().equalsIgnoreCase("F")?"女":"男");
               }
               cell.setParent(item);
               
               cell=new Listcell(user.getEmail());               
               cell.setParent(item);
               
               cell=new Listcell(user.getMobileNumber());
               cell.setParent(item);
          }
     }

     
     
}
