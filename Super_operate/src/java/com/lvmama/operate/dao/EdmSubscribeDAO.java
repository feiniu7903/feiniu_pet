package com.lvmama.operate.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.edm.EdmSubscribe;

public class EdmSubscribeDAO extends BaseIbatisDAO{
	public static final String EMD_SUBSCRIBE_SPACE="EDM_SUBSCRIBE.";
	public static final String QUERY=EMD_SUBSCRIBE_SPACE+"query";
	public static final String COUNT=EMD_SUBSCRIBE_SPACE+"count";
	public static final String INSERT=EMD_SUBSCRIBE_SPACE+"insert";
	public static final String UDPATE=EMD_SUBSCRIBE_SPACE+"update";
	public static final String CHECK_SUBSCRIBE=EMD_SUBSCRIBE_SPACE+"searchEmailIsSubscribe";
	public static final String SEARCH_SUBSCRIBE_BY_EMAIL=EMD_SUBSCRIBE_SPACE+"searchEmailbyEmail";
	public static final String SEARCH_SUBSCRIBE = EMD_SUBSCRIBE_SPACE+"searchSubscribe";
	/**
	 * 根据参数查询订阅用户列表
	 * @param params<email:用户邮箱,beginCreateDate:用户订阅开始时间,endCreateDate:用户订阅结束时间,type:订阅类型>
	 * @return
	*/ 
	@SuppressWarnings("unchecked")
	public List<EdmSubscribe> query(Map<String,Object> params){
		return (List<EdmSubscribe>)super.queryForListForReport(QUERY, params);
	}
	/**
	 * 根据参数查询订阅用户总数
	 * @param params<email:用户邮箱,beginCreateDate:用户订阅开始时间,endCreateDate:用户订阅结束时间,type:订阅类型>
	 * @return
	*/
	public Long count(Map<String,Object> params){
		return (Long)super.queryForObject(COUNT, params);
	}
	/**
	 * 根据参数创建一条订阅用户数据
	 * @param obj
	 * @return
	 */
	public EdmSubscribe insert(EdmSubscribe obj){
	    Long id = (Long)super.insert(INSERT, obj);
	    obj.setId(id);
		return obj;
	}
	/**
	 * 根据参数修改一条订阅用户数据
	 * @param obj
	 * @return
	 */
	public int update(EdmSubscribe obj){
		return super.update(UDPATE, obj);
	}
	
	/**
	 * 检查邮箱是否已订阅
	 * @param email
	 * @return
	 */
	public EdmSubscribe searchEmailIsSubscribe(String email){
		return (EdmSubscribe)super.queryForObject(CHECK_SUBSCRIBE,email);
	}
	/**
	 * 根据邮箱查询订阅信息
	 * @param email
	 * @return
	 */
	public EdmSubscribe searchEmailbyEmail(String email){
		return (EdmSubscribe)super.queryForObject(SEARCH_SUBSCRIBE_BY_EMAIL,email);
	}
	/**
	 *根据条件查询单条订阅信息
	 */
	public EdmSubscribe searchSubscribe(Map<String,Object> parameters){
		return (EdmSubscribe)super.queryForObject(SEARCH_SUBSCRIBE,parameters);
	}
}
