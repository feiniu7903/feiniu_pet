package com.lvmama.pet.mobile.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.mobile.MobilePushDevice;

public class MobilePushDeviceDAO extends BaseIbatisDAO {

	public MobilePushDeviceDAO() {
		super();
	}

	public int deleteByPrimaryKey(Long mobilePushDeviceId) {
		MobilePushDevice key = new MobilePushDevice();
		key.setMobilePushDeviceId(mobilePushDeviceId);
		int rows = super.delete("MOBILE_PUSH_DEVICE.deleteByPrimaryKey", key);
		return rows;
	}
	
	public int deleteByDeviceTokens(Map<String,Object> param){
		if(param==null || param.isEmpty()){
			throw new RuntimeException("empty param map");
		}
		
		List<String> list = (List<String>) param.get("objectIdList");
		
		if(list==null || list.isEmpty()){
			throw new RuntimeException("empty param value list");
		}
		int rows = super.delete("MOBILE_PUSH_DEVICE.deleteByDeviceTokens", param);
		return rows;
	}
	
	public List<MobilePushDevice> getPushTargetIds(Map<String,Object> param){
		return super.queryForListForReport("MOBILE_PUSH_DEVICE.selectPushIdWithTargetIds",param);
	}

	public Long insert(MobilePushDevice record) {
		return (Long) super.insert("MOBILE_PUSH_DEVICE.insert", record);
	}

	public void insertSelective(MobilePushDevice record) {
		super.insert("MOBILE_PUSH_DEVICE.insertSelective", record);
	}

	public Long selectTotalMobilPushDevice() {
		return (Long) super.queryForObject(
				"MOBILE_PUSH_DEVICE.selectTotalMobilPushDevice");
	}
	public Long selectByObjectId(Map<String, Object> param){
		return (Long) super.queryForObject(
				"MOBILE_PUSH_DEVICE.selectByObjectId",param);
	}
	
	public MobilePushDevice selectMobileDeviceByObjectId(Map<String, Object> param){
		return (MobilePushDevice) super.queryForObject(
				"MOBILE_PUSH_DEVICE.selectMobileDeviceByObjectId",param);
	}
	
	@SuppressWarnings("unchecked")
	public List<MobilePushDevice> pagedQueryMobilPushDevice(
			Map<String, Object> param) {
		List<MobilePushDevice> mobilePushDeviceList = (List<MobilePushDevice>) super
				.queryForList("MOBILE_PUSH_DEVICE.pagedQueryMobilPushDevice",
						param);
		return mobilePushDeviceList;
	}
	
	public Long countQueryMobilPushDevice(Map<String, Object> param){
		return (Long)super.queryForObject("MOBILE_PUSH_DEVICE.countQueryMobilPushDevice",
						param);
	}
	

	public MobilePushDevice selectByPrimaryKey(Long mobilePushDeviceId) {
		MobilePushDevice key = new MobilePushDevice();
		key.setMobilePushDeviceId(mobilePushDeviceId);
		MobilePushDevice record = (MobilePushDevice) super.queryForObject(
				"MOBILE_PUSH_DEVICE.selectByPrimaryKey", key);
		return record;
	}

	public int updateByPrimaryKeySelective(MobilePushDevice record) {
		int rows = super.update(
				"MOBILE_PUSH_DEVICE.updateByPrimaryKeySelective", record);
		return rows;
	}

	public int updateByPrimaryKey(MobilePushDevice record) {
		int rows = super
				.update("MOBILE_PUSH_DEVICE.updateByPrimaryKey", record);
		return rows;
	}
}