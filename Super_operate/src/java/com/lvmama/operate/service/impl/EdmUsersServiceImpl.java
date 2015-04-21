package com.lvmama.operate.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.edm.EdmExport;
import com.lvmama.comm.pet.po.pub.ComCity;
import com.lvmama.comm.pet.po.pub.ComProvince;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.operate.dao.EdmExportDAO;
import com.lvmama.operate.dao.ReportUsrUsersDAO;
import com.lvmama.operate.service.ComCityService;
import com.lvmama.operate.service.EdmUsersService;

/**
 * @author yangbin
 *
 */
public class EdmUsersServiceImpl implements EdmUsersService {

     private ComCityService comCityService;
     private EdmExportDAO edmExportDAO;
     private ReportUsrUsersDAO reportUsrUsersDAO;
     
     public void setEdmExportDAO(EdmExportDAO edmExportDAO) {
          this.edmExportDAO = edmExportDAO;
     }
     
          
     public void setReportUsrUsersDAO(ReportUsrUsersDAO reportUsrUsersDAO) {
          this.reportUsrUsersDAO = reportUsrUsersDAO;
     }
     /* (non-Javadoc)
      * @see com.lvmama.report.service.impl.EdmUsersService#selectCapitals()
      */
     public List<ComProvince> selectProvinceList()
     {
          List<ComProvince> list=comCityService.selectProvinceList();
          return list;
     }
     
     /* (non-Javadoc)
      * @see com.lvmama.report.service.impl.EdmUsersService#selectCities(java.lang.String)
      */
     public List<ComCity> selectCitiesByProvinceId(String provinceId)
     {
    	 ComProvince province=new ComProvince();
    	 province.setProvinceId(provinceId);
          
          List<ComCity> list=comCityService.selectCitiesByProvinceId(province);
          return list;
     }
     
     
     /* (non-Javadoc)
      * @see com.lvmama.report.service.impl.EdmUsersService#selectUsers(java.util.Map)
      */
     public List<UserUser> selectUsers(Map parameters)
     {
          return reportUsrUsersDAO.selectEdmList(parameters);
     }
     /* (non-Javadoc)
      * @see com.lvmama.report.service.impl.EdmUsersService#selectUsersCount(java.util.Map)
      */
     public long selectUsersCount(Map parameters)
     {
          
          HashMap map=(HashMap)((HashMap)(parameters)).clone();
          if(map.containsKey("_startRow"))
          {
               map.remove("_startRow");
          }
          if(map.containsKey("_endRow"))
          {
               map.remove("_endRow");
          }
          return reportUsrUsersDAO.selectEdmCount(map);
     }
     
     
     
     @Override
     public ComProvince getProvince(String id) {
    	 ComProvince dc=new ComProvince();
          dc.setProvinceId(id);
          return comCityService.getProvince(dc);
     }
     @Override
     public ComCity getCity(String id) {
          ComCity dc=new ComCity();
          dc.setCityId(id);
          return comCityService.getCity(dc);
     }
     @Override
     public void saveNote(String loginName, long total) {
          // TODO Auto-generated method stub
          EdmExport ee=new EdmExport();
          ee.setCount((int)total);
          ee.setLoginName(loginName);
          edmExportDAO.insert(ee);
     }
     @Override
     public long selectEdmExportCount(Map<String, Object> parameters) {
          HashMap map=(HashMap)((HashMap)(parameters)).clone();
          if(map.containsKey("_startRow"))
          {
               map.remove("_startRow");
          }
          if(map.containsKey("_endRow"))
          {
               map.remove("_endRow");
          }
          return edmExportDAO.selectCountByDate(parameters);
     }
     @Override
     public List<EdmExport> selectEdmExportList(Map<String, Object> parameters) {
          return edmExportDAO.selectListByDate(parameters);
     }


	public void setComCityService(ComCityService comCityService) {
		this.comCityService = comCityService;
	}

}
