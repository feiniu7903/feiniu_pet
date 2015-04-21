package com.lvmama.finance.base;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.orm.ibatis.SqlMapClientTemplate;
import org.springframework.stereotype.Repository;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.lvmama.comm.BaseIbatisDAO;

/**
 * 基础DAO
 * 
 * @author yanggan
 * 
 */
@Repository
public class BaseDAO extends BaseIbatisDAO {

	/**
	 * 批量插入
	 * 
	 * @param sqlMap
	 * @param list
	 */
	@SuppressWarnings("rawtypes")
	public void insertList(String sqlMap, List list) {
		SqlMapClient sqlMapClient = this.getSqlMapClient();
		try {
			sqlMapClient.startBatch();
			for (Object t : list) {
				sqlMapClient.insert(sqlMap, t);
			}
			sqlMapClient.executeBatch();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

}
