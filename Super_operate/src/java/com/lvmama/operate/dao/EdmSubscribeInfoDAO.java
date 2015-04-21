package com.lvmama.operate.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.edm.EdmSubscribeInfo;


public class EdmSubscribeInfoDAO extends BaseIbatisDAO{
	
	private static final String EMD_SUBSCRIBE_INFO_SPACE="EDM_SUBSCRIBE_INFO.";
	private static final String QUERY = EMD_SUBSCRIBE_INFO_SPACE+"query";
	private static final String SEARCH_EDM_INFO = EMD_SUBSCRIBE_INFO_SPACE+"searchEdmInfo";
	private static final String COUNT = EMD_SUBSCRIBE_INFO_SPACE+"count";
	private static final String INSERT = EMD_SUBSCRIBE_INFO_SPACE+"insert";
	private static final String UPDATE = EMD_SUBSCRIBE_INFO_SPACE+"update";
	/**
	 * 根据传入参数查询订阅信息列表
	 * @param params <beginCancelDate:退订开始时间,endCancelDate：退订结束时间,type：订阅邮件类型>
	 * @return
	 */
	public List<EdmSubscribeInfo> query(Map<String,Object> params){
		return (List<EdmSubscribeInfo>)super.queryForListForReport(QUERY, params);
	}
	/**
	 * 根据订阅ID，或EMAIL查询类型列表
	 * @param params
	 * @return
	 */
	public List<EdmSubscribeInfo> searchEdmInfo(Map<String,Object> params){
		return (List<EdmSubscribeInfo>)super.queryForListForReport(SEARCH_EDM_INFO, params);
	}
	/**
	 * 根据传入参数查询订阅信息总数
	 * @param params <beginCancelDate:退订开始时间,endCancelDate：退订结束时间,type：订阅邮件类型>
	 * @return
	 */
	public Long count(Map<String,Object> params){
		return (Long)super.queryForObject(COUNT, params);
	}
	/**
	 * 根据用户订阅信息创建一条数据
	 * @param obj
	 * @return
	 */
	public EdmSubscribeInfo insert(EdmSubscribeInfo obj){
		Long key= (Long) super.insert(INSERT, obj);
		obj.setId(key);
		return obj;
	}
	/**
	 *  用户退订某类型邮件，记录退订原因
	 * @param obj
	 * @return
	 */
	public int update(EdmSubscribeInfo obj){
		return super.update(UPDATE, obj);
	}
}
