package com.lvmama.operate.service.impl;

import java.util.List;

import com.lvmama.comm.pet.po.edm.EdmOrderPlaceGuide;
import com.lvmama.operate.dao.OnlineMarketingDAO;
import com.lvmama.operate.service.OnlineMarketingService;

public class OnlineMarketingServiceImpl implements OnlineMarketingService {
     private OnlineMarketingDAO onlineMarketingDAO;
     @Override
     public List<EdmOrderPlaceGuide> getPlaceEmail() {
          return onlineMarketingDAO.getPlaceEmail();
     }
     public OnlineMarketingDAO getOnlineMarketingDAO() {
          return onlineMarketingDAO;
     }
     public void setOnlineMarketingDAO(OnlineMarketingDAO onlineMarketingDAO) {
          this.onlineMarketingDAO = onlineMarketingDAO;
     }

}
