package com.lvmama.ord.service;

import java.util.List;

import com.lvmama.comm.bee.po.ord.OrdOrderSHHoliday;
import com.lvmama.comm.bee.service.ord.OrdOrderSHHolidayService;
import com.lvmama.ord.dao.OrdOrderSHHolidayDAO;

public class OrdOrderSHHolidayServiceImpl implements OrdOrderSHHolidayService {

	private OrdOrderSHHolidayDAO ordOrderSHHolidayDAO;
		
	 public Long insert(OrdOrderSHHoliday record){
		 return ordOrderSHHolidayDAO.insert(record);
	 }
	 
	 public OrdOrderSHHoliday selectByObjectIdAndObjectType(OrdOrderSHHoliday osh){
		 return ordOrderSHHolidayDAO.selectByObjectIdAndObjectType(osh);
	 }

	 public List<OrdOrderSHHoliday> selectByPara(OrdOrderSHHoliday osh){
		 return ordOrderSHHolidayDAO.selectByPara(osh);
	 }
	 
	 public void insertOrUpdate(OrdOrderSHHoliday osh){
		String content = osh.getContent();
		osh.setContent(null);
		OrdOrderSHHoliday sh1 = ordOrderSHHolidayDAO.selectByObjectIdAndObjectType(osh);
		if (sh1 == null) {
			osh.setContent(content);
			ordOrderSHHolidayDAO.insert(osh);
		} else {
			sh1.setContent(content);
			ordOrderSHHolidayDAO.updateByObjectIdAndObjectType(sh1);
		}
	 }
	public void setOrdOrderSHHolidayDAO(OrdOrderSHHolidayDAO ordOrderSHHolidayDAO) {
		this.ordOrderSHHolidayDAO = ordOrderSHHolidayDAO;
	}
	 
	 
}
