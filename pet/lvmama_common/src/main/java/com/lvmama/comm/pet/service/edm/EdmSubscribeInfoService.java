package com.lvmama.comm.pet.service.edm;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.edm.EdmSubscribeInfo;

public interface EdmSubscribeInfoService {
	/**
	 * 根据传入参数查询订阅信息列表
	 * @param params <beginCancelDate:退订开始时间,endCancelDate：退订结束时间,type：订阅邮件类型>
	 * @return
	 */
	public List<EdmSubscribeInfo> query(Map<String,Object> params);
	/**
	 * 根据传入参数查询订阅信息总数
	 * @param params <beginCancelDate:退订开始时间,endCancelDate：退订结束时间,type：订阅邮件类型>
	 * @return
	 */
	public Long count(Map<String,Object> params);
	/**
	 * 根据订阅ID，或EMAIL查询类型列表
	 * @param params 
	 * @return
	 */
	public List<EdmSubscribeInfo> searchEdmInfo(Map<String, Object> params);
	/**
	 * 根据用户订阅信息创建一条数据
	 * @param obj
	 * @return
	 */
	public EdmSubscribeInfo insert(EdmSubscribeInfo obj);
	/**
	 *  用户退订某类型邮件，记录退订原因
	 * @param obj
	 * @return
	 */
	public int update(EdmSubscribeInfo obj);
}
