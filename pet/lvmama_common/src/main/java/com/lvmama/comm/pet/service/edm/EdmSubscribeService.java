package com.lvmama.comm.pet.service.edm;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.edm.EdmSubscribe;

public interface EdmSubscribeService {
	
	/**
	 * 根据参数查询订阅用户总数
	 * @param params<email:用户邮箱,beginCreateDate:用户订阅开始时间,endCreateDate:用户订阅结束时间,type:订阅类型>
	 * @return
	 */
	public Long count(Map<String,Object> params);
	/**
	 * 根据参数查询订阅用户列表
	 * @param params<email:用户邮箱,beginCreateDate:用户订阅开始时间,endCreateDate:用户订阅结束时间,type:订阅类型>
	 * @return
	 */
	public List<EdmSubscribe> query(Map<String,Object> params);
	/**
	 * 根据参数创建一条订阅用户数据
	 * @param obj
	 * @return
	 */
	public EdmSubscribe insert(EdmSubscribe obj);
	/**
	 * 根据参数修改订阅用户数据
	 * @param obj
	 * @return
	 */
	public int update(EdmSubscribe obj);
	/**
	 * 检查邮箱是否已订阅
	 * @param email
	 * @return
	 */
	public EdmSubscribe searchEmailIsSubscribe(String email);
	
	/**
	 *根据条件查询单条订阅信息
	 */
	public EdmSubscribe searchSubscribe(Map<String,Object> parameters);
}
