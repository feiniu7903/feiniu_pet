package com.lvmama.comm.bee.service.ord;

import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrderSHHoliday;

public interface OrdOrderSHHolidayService {

	 public Long insert(OrdOrderSHHoliday record);
	 public OrdOrderSHHoliday selectByObjectIdAndObjectType(OrdOrderSHHoliday osh);
	 public void insertOrUpdate(OrdOrderSHHoliday osh);
	 
	 public List<OrdOrderSHHoliday> selectByPara(OrdOrderSHHoliday osh);
}
