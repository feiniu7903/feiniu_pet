package com.lvmama.pet.conn.dao;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.conn.SensitiveKeys;

/**
 * 敏感词数据库操作
 * @author Administrator
 *
 */
public class SensitiveKeysDAO extends BaseIbatisDAO {
	/**
	 * 返回所有的敏感词
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<SensitiveKeys> queryAll() {
		return (List<SensitiveKeys>) super.queryForListForReport("SENSITIVE_KEYS.queryAll");
	}
	
	/**
	 * 新增
	 * @param keys
	 */
	public void save(final String keys) {
		if (StringUtils.isNotEmpty(keys)) {
			super.insert("SENSITIVE_KEYS.insert", keys);
		}
	}
	
	/**
	 * 根据条件查询敏感词
	 * @param parameters 查询条件
	 * @return 列表
	 */
	@SuppressWarnings("unchecked")
	public List<SensitiveKeys> query(final Map<String, Object> parameters) {
		if(parameters != null && parameters.size() > 0 ){
			return (List<SensitiveKeys>) super.queryForList("SENSITIVE_KEYS.query", parameters);
		}else{
			return null;
		}
	}
  
  /**
	  * 根据条件查询敏感词数量
	  * @param parameters 查询条件
	  * @return 数量
	 */
	public Long count(final Map<String, Object> parameters) {
		return (Long) super.queryForObject("SENSITIVE_KEYS.count", parameters);

	}
	
}
