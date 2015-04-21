package com.lvmama.pet.conn.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.conn.SensitiveKeys;
import com.lvmama.comm.pet.service.conn.SensitiveKeysService;
import com.lvmama.pet.conn.dao.SensitiveKeysDAO;

public class SensitiveKeysServiceImpl implements SensitiveKeysService {
	@Autowired
	private SensitiveKeysDAO sensitiveKeysDAO;

	@Override
	public List<SensitiveKeys> queryAll() {
		return sensitiveKeysDAO.queryAll();
	}
	
	@Override
	public void save(final String keys) {
		try {
			sensitiveKeysDAO.save(keys);
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}
	
	public List<SensitiveKeys> query(final Map<String, Object> parameters) {
		return sensitiveKeysDAO.query(parameters);
	}
  
  /**
	  * 根据条件查询敏感词数量
	  * @param parameters 查询条件
	  * @return 数量
	 */
	public Long count(final Map<String, Object> parameters) {
		return sensitiveKeysDAO.count(parameters);
	}
}
