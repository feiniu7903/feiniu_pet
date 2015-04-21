package com.lvmama.comm.pet.service.client;

import java.util.List;

import com.lvmama.comm.bee.po.client.ComClientLog;
import com.lvmama.comm.pet.po.client.ClientOrderReport;

public interface ComClientService {
	
    public Long countUdidOrder(String udid);
    
    public Long insert(ComClientLog record);
    
    /**
     * 
     * @param udid
     * @return
     */
    List<ClientOrderReport> getTodayOrderByUdid(String udid);
}
