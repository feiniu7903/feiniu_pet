package com.lvmama.pet.pub.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.pub.ComMessage;

public class ComMessageDAO extends BaseIbatisDAO {
	public ComMessage getComMessageByPk(Long messageId){
		return (ComMessage)super.queryForObject("COM_MESSAGE.selectByPk", messageId);
	}
	public List<ComMessage> queryComMessageByParamBeginTimeDesc(Map param){
		return super.queryForList(
				"COM_MESSAGE.selectByParamBeginTimeDesc", param);
	}
	public void updateComMessage(ComMessage comMessage){
		super.update("COM_MESSAGE.updateByPrimaryKey", comMessage);
	}
	public Long insertComMessage(ComMessage comMessage){
		return (Long)super.insert("COM_MESSAGE.insert", comMessage);
	}
	public Long queryComMessageByParamCount(Map param){
		return (Long)super.queryForObject("COM_MESSAGE.selectByParamCount", param);
	}
	public List<ComMessage> queryComMessageByParam(Map param){
		return super.queryForList(
				"COM_MESSAGE.selectByParam", param);
	}
}