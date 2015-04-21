package com.lvmama.pet.pub.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComMessageReceivers;

public class ComMessageReceiverDAO extends BaseIbatisDAO {
	public Long queryComMessageReceiverByParamCount(Map param){
		return (Long) super.queryForObject(
				"COM_MESSAGE_RECEIVERS.selectByParamCount", param);
	}
	public List<ComMessageReceivers> queryComMessageReceiverByParam(Map param) {
		return super.queryForList(
				"COM_MESSAGE_RECEIVERS.selectByParam", param);
	}
	
	public void updateComMessageReceiversByPK(ComMessageReceivers comMessageReceivers) {
		super.update("COM_MESSAGE_RECEIVERS.updateByPrimaryKey",
				comMessageReceivers);
	}

	public Long insert(ComMessageReceivers comMessageReceiver) {
		return (Long) super.insert(
				"COM_MESSAGE_RECEIVERS.insert", comMessageReceiver);
	}

}