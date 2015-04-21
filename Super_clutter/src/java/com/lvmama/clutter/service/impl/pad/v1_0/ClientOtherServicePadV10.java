package com.lvmama.clutter.service.impl.pad.v1_0;

import java.util.Map;

import com.lvmama.clutter.model.MobileVersion;
import com.lvmama.clutter.service.impl.ClientOtherServiceImpl;
import com.lvmama.comm.pet.service.mobile.MobileClientService;

public class ClientOtherServicePadV10 extends ClientOtherServiceImpl{

	@Override
	public void subSuggest(Map<String, Object> param) {
		// TODO Auto-generated method stub
		super.subSuggest(param);
	}

	@Override
	public Map<String, Object> getNameByLocation(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getNameByLocation(param);
	}

	@Override
	public boolean hasTicket(String name) {
		// TODO Auto-generated method stub
		return super.hasTicket(name);
	}

	@Override
	public Map<String, Object> getGroupOnCities(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getGroupOnCities(param);
	}

	@Override
	public MobileVersion checkVersion(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		return super.checkVersion(param);
	}

	@Override
	public Map<String, Object> rollMarkCoupon(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.rollMarkCoupon(param);
	}

	@Override
	public Map<String, Object> getRemainOperateNum(Map<String, Object> param) {
		// TODO Auto-generated method stub
		return super.getRemainOperateNum(param);
	}

	@Override
	public Map<String, Object> addMobilePushDevice(Map<String, Object> param)
			throws Exception {
		// TODO Auto-generated method stub
		return super.addMobilePushDevice(param);
	}



}
