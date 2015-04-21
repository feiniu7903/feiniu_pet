package com.lvmama.operate.web.statistics;

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.edm.EdmExport;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.operate.service.EdmUsersService;
import com.lvmama.operate.web.BaseAction;

/**
 * @author yangbin
 *
 */
public class EdmLogAction extends BaseAction {

     /**
      * 
      */
     private static final long serialVersionUID = 4964737442557258089L;
     private List<EdmExport> logLists=Collections.emptyList();
     private Map<String,Object> searchCons=new HashMap<String, Object>();
     private EdmUsersService edmUsersService;
     
     public void search()
     {      
          buildSearchCond();
          searchCons = initialPageInfoByMap(edmUsersService.selectEdmExportCount(searchCons), searchCons);
          logLists = edmUsersService.selectEdmExportList(searchCons);    
     }
     
     void buildSearchCond()
     {
          if(searchCons.containsKey("startTime"))
          {
               Date date=(Date)searchCons.get("startTime");
               searchCons.put("startTime", DateUtil.getDayStart(date));
          }
          if(searchCons.containsKey("endTime"))
          {
               Date date=(Date)searchCons.get("endTime");
               searchCons.put("endTime", DateUtil.getDayEnd(date));
          }
     }
     public Map<String, Object> getSearchCons() {
          return searchCons;
     }

     public void setSearchCons(Map<String, Object> searchCons) {
          this.searchCons = searchCons;
     }

     public List<EdmExport> getLogLists() {
          return logLists;
     }

    public EdmUsersService getEdmUsersService() {
          return edmUsersService;
    }
    
    public void setEdmUsersService(EdmUsersService edmUsersService) {
          this.edmUsersService = edmUsersService;
    }
    
    public void setLogLists(List<EdmExport> logLists) {
          this.logLists = logLists;
    }
     
     
}
