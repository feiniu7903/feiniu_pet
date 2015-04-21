package com.lvmama.pet.mobile.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobileHotelOrderVisitor;

public class MobileHotelOrderVisitorDAO extends BaseIbatisDAO {

    public MobileHotelOrderVisitorDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long mobileHotelOrderVisitorId) {
        MobileHotelOrderVisitor key = new MobileHotelOrderVisitor();
        key.setMobileHotelOrderVisitorId(mobileHotelOrderVisitorId);
        int rows = super.delete("MOBILE_HOTEL_ORDER_VISITOR.deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(MobileHotelOrderVisitor record) {
    	super.insert("MOBILE_HOTEL_ORDER_VISITOR.insert", record);
    }

    public void insertSelective(MobileHotelOrderVisitor record) {
    	super.insert("MOBILE_HOTEL_ORDER_VISITOR.insertSelective", record);
    }

    public MobileHotelOrderVisitor selectByPrimaryKey(Long mobileHotelOrderVisitorId) {
        MobileHotelOrderVisitor key = new MobileHotelOrderVisitor();
        key.setMobileHotelOrderVisitorId(mobileHotelOrderVisitorId);
        MobileHotelOrderVisitor record = (MobileHotelOrderVisitor) super.queryForObject("MOBILE_HOTEL_ORDER_VISITOR.selectByPrimaryKey", key);
        return record;
    }
    
    @SuppressWarnings("unchecked")
	public List<MobileHotelOrderVisitor> selectByParams(MobileHotelOrderVisitor record) {
		List<MobileHotelOrderVisitor> list = super.queryForList("MOBILE_HOTEL_ORDER_VISITOR.selectByParams", record);
        return list;
    }

    public int updateByPrimaryKeySelective(MobileHotelOrderVisitor record) {
        int rows = super.update("MOBILE_HOTEL_ORDER_VISITOR.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MobileHotelOrderVisitor record) {
        int rows = super.update("MOBILE_HOTEL_ORDER_VISITOR.updateByPrimaryKey", record);
        return rows;
    }
}