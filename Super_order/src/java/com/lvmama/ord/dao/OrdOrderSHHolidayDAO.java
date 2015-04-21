package com.lvmama.ord.dao;

import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ord.OrdOrderSHHoliday;

public class OrdOrderSHHolidayDAO extends BaseIbatisDAO {

	 public OrdOrderSHHolidayDAO() {
	        super();
	 }
	 
	 public Long insert(OrdOrderSHHoliday record) {
	        Object newKey = super.insert("ORD_ORDER_SHHOLIDAY.insert", record);
	        return (Long) newKey;
	 }

    public OrdOrderSHHoliday selectByObjectIdAndObjectType(OrdOrderSHHoliday osh) {
    	if(osh.getObjectId()!=null && 0==osh.getObjectId()){
    		osh.setObjectId(null);
    	}
    	if(StringUtils.isEmpty(osh.getContent())){
    		osh.setContent(null);
    	}
    	OrdOrderSHHoliday record = (OrdOrderSHHoliday) super.queryForObject("ORD_ORDER_SHHOLIDAY.selectByObjectIdAndObjectType", osh);
        return record;
    }
    
    
    public List<OrdOrderSHHoliday> selectByPara(OrdOrderSHHoliday osh) {
    	if(osh.getObjectId()!=null && 0==osh.getObjectId()){
    		osh.setObjectId(null);
    	}
    	if(StringUtils.isEmpty(osh.getContent())){
    		osh.setContent(null);
    	}
    	List<OrdOrderSHHoliday> record =  super.queryForList("ORD_ORDER_SHHOLIDAY.selectByObjectIdAndObjectType", osh);
        return record;
    }
    
    public void updateByObjectIdAndObjectType(OrdOrderSHHoliday osh){
    	 super.update("ORD_ORDER_SHHOLIDAY.updateByObjectIdAndObjectType", osh);
    }
}
