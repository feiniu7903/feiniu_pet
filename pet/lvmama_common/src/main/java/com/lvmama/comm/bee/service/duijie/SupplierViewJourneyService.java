package com.lvmama.comm.bee.service.duijie;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.bee.po.duijie.SupplierViewJourney;

/**
 * 供应商行程服务接口 
 * @author yanzhirong
 */
public interface SupplierViewJourneyService {
	public void insert(SupplierViewJourney journey);
	
	public void update(SupplierViewJourney journey);
	
	public List<SupplierViewJourney> selectSupplierViewJourneyByCondition(Map<String,Object> params);
}
