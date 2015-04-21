package com.lvmama.comm.pet.service.conn;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.conn.SensitiveKeys;

/**
 * 敏感词逻辑服务接口
 * @author Administrator
 *
 */
public interface SensitiveKeysService {
	/**
	 * 查询所有
	 * @return
	 */
	List<SensitiveKeys> queryAll();
	/**
	 * 新增
	 * @param keys
	 */
	void save(final String keys);
	
	List<SensitiveKeys> query(final Map<String, Object> parameters);
  
	/**
	  * 根据条件查询敏感词数量
	  * @param parameters 查询条件
	  * @return 数量
	 */
	 Long count(final Map<String, Object> parameters);
}
