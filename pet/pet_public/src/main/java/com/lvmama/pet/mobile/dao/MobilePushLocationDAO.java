package com.lvmama.pet.mobile.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobilePushLocation;

public class MobilePushLocationDAO extends BaseIbatisDAO {

    public MobilePushLocationDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long mobilePushLocationId) {
        MobilePushLocation key = new MobilePushLocation();
        key.setMobilePushLocationId(mobilePushLocationId);
        int rows = super.delete("MOBILE_PUSH_LOCATION.deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(MobilePushLocation record) {
    	super.insert("MOBILE_PUSH_LOCATION.insert", record);
    }

    public void insertSelective(MobilePushLocation record) {
    	super.insert("MOBILE_PUSH_LOCATION.insertSelective", record);
    }

    public MobilePushLocation selectByLocationId(Long locationId) {
    	return this.selectByPrimaryKey(locationId);
    }
    
    public List<MobilePushLocation> selectByDeviceId(Long mobilePushDeviceId) {
        MobilePushLocation key = new MobilePushLocation();
        key.setMobilePushDeviceId(mobilePushDeviceId);
//        MobilePushLocation record = (MobilePushLocation) super.queryForObject("MOBILE_PUSH_LOCATION.selectByDeviceId", key);
        List<MobilePushLocation> record = (List<MobilePushLocation>) super.queryForList("MOBILE_PUSH_LOCATION.selectByDeviceId", key);
        return record;
    }
    
    public MobilePushLocation selectByPrimaryKey(Long mobilePushLocationId) {
        MobilePushLocation key = new MobilePushLocation();
        key.setMobilePushLocationId(mobilePushLocationId);
        MobilePushLocation record = (MobilePushLocation) super.queryForObject("MOBILE_PUSH_LOCATION.selectByPrimaryKey", key);
        return record;
    }


    public List<MobilePushLocation> selectbyParmas(Map<String,Object> params){
     	if(params==null||params.isEmpty()){
     		throw new RuntimeException("params not been null value");
     	}
     	List<MobilePushLocation> record = (List<MobilePushLocation>) super.queryForList("MOBILE_PUSH_LOCATION.selectByParams", params);
     	return record;
     }
    
    public int updateByPrimaryKeySelective(MobilePushLocation record) {
        int rows = super.update("MOBILE_PUSH_LOCATION.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(MobilePushLocation record) {
        int rows = super.update("MOBILE_PUSH_LOCATION.updateByPrimaryKey", record);
        return rows;
    }
}