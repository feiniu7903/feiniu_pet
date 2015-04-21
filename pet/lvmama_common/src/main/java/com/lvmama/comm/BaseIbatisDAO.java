package com.lvmama.comm;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.sql.DataSource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapClient;
import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lvmama.comm.pet.vo.Page;

/**
 * Ibatis型读dao基类
 */
public class BaseIbatisDAO extends SqlMapClientTemplate {

	private static final Log LOG = LogFactory.getLog(BaseIbatisDAO.class);

	private int maxRows = 1001;
	private int maxRowsForReport = 50000;
	private int allRows = 5000000;
	
	/**
	 * Set the JDBC DataSource to be used by this DAO.
	 * Not required: The SqlMapClient might carry a shared DataSource.
	 * @see #setSqlMapClient
	 */
	@Resource
	public final void setDataSource(DataSource dataSource) {
		super.setDataSource(dataSource);
	}

	/**
	 * Set the iBATIS Database Layer SqlMapClient to work with.
	 * Either this or a "sqlMapClientTemplate" is required.
	 * @see #setSqlMapClientTemplate
	 */
	@Resource
	public final void setSqlMapClient(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
	}


	public List queryForList(String statementName) throws DataAccessException {
		return queryForList(statementName, null);
	}
	
	public List queryForList(final String statementName, final Object parameterObject)
			throws DataAccessException {
		return execute(new SqlMapClientCallback<List >() {
			public List  doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				List  result = executor.queryForList(statementName, parameterObject, 0, maxRows);
				if (result != null && result.size() == maxRows) {
					LOG.error("SQL Exception: result size is greater than the max rows, " + statementName+"  parameterObject:"+parameterObject);
				}
				return result;
			}
		});
	}
	
	public List queryForList(String statementName, int skipResults, int maxResults)
			throws DataAccessException {
		if((maxResults-skipResults)>=maxRows){
			maxResults = skipResults + maxRows;
			LOG.error("SQL Exception: result size is greater than the max rows, " + statementName);
		}
		
		return queryForList(statementName, null, skipResults, maxResults);
	}

	public List  queryForList(final String statementName, final Object parameterObject,
			final int skipResults, final int maxResults) throws DataAccessException {
		return execute(new SqlMapClientCallback<List>() {
			public List doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				int tempMaxResults = maxResults;
				if((maxResults-skipResults)>=maxRows){
					tempMaxResults = skipResults + maxRows;
					LOG.error("SQL Exception: result size is greater than the max rows, " + statementName);
				}
				return executor.queryForList(statementName, parameterObject, skipResults,
						tempMaxResults);
			}
		});
	}
	
	//数据量比较大的报表导出请用这个接口
	public List  queryForListForReport(String statementName) throws DataAccessException {
		return queryForListForReport(statementName, null);
	}
	
	//数据量比较大的报表导出请用这个接口
	public List  queryForListForReport(final String statementName, final Object parameterObject)
			throws DataAccessException {
		return execute(new SqlMapClientCallback<List>() {
			public List doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				List result = executor.queryForList(statementName, parameterObject, 0, maxRowsForReport);
				if (result != null && result.size() == maxRowsForReport) {
					LOG.error("SQL Exception: result size is greater than the max rows, " + statementName);
				}
				return result;
			}
		});
	}
	//数据量比较大的报表导出请用这个接口
	public List  queryForList(final String statementName, final Object parameterObject,final boolean isForReportExport)
			throws DataAccessException {
		return execute(new SqlMapClientCallback<List >() {
			public List  doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				
				int maxRowsTemp=maxRows;
				if(isForReportExport)
					maxRowsTemp=maxRowsForReport;

				List  result = executor.queryForList(statementName, parameterObject, 0, maxRowsTemp);
				if (result != null && result.size() == maxRowsTemp) {
					LOG.error("SQL Exception: result size is greater than the max rows, " + statementName);
				}
				return result;
			}
		});
	}
	
	//数据量比较大的报表导出请用这个接口
		public List  queryForListNoMax(final String statementName, final Object parameterObject)
				throws DataAccessException {
			return execute(new SqlMapClientCallback<List >() {
				public List  doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
					List  result = executor.queryForList(statementName, parameterObject, 0,allRows);
					return result;
				}
			});
		}
	
	/**
	 * 查询分页数据
	 * 
	 * @param statementName
	 *            查询数据的SQL
	 * @param countStatementName
	 *            查询总数量的SQL
	 * @param parameterObject
	 *            查询参数
	 * @param page
	 *            分页信息
	 * @return 包含查询结果的分页信息
	 */
	public Page queryForPage(String statementName,String countStatementName,Map parameterObject){
		Page page = new Page();
		//查询总数
		Long totalResultSize = (Long) this.queryForObject(countStatementName, parameterObject);
		//分页查询
		if(totalResultSize > 0) {
			long currentPage = Long.parseLong(parameterObject.get("currentPage").toString());
			long pageSize = Long.parseLong(parameterObject.get("pageSize").toString());
			page.setPageSize(pageSize);
			page.setCurrentPage(currentPage);
			page.setTotalResultSize(totalResultSize);
			parameterObject.put("start", page.getStartRows());
			parameterObject.put("end", page.getEndRows());
			List items = this.queryForList(statementName, parameterObject);
			page.setItems(items);
		}
		return page;
	}
	public Page queryForPage(String statementName,Map parameterObject){
		return this.queryForPage(statementName,statementName+"Count", parameterObject);
	}
	
	/**
	 * 批量插入
	 * @param statementName
	 * @param list
	 * @author nixianjun 2013.8.8
	 */
	public void batchInsert(final String statementName, final List list) {
		try {
			if (list != null) {
				execute(new SqlMapClientCallback<List>() {
					public List doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						for (int i = 0, n = list.size(); i < n; i++) {
							executor.insert(statementName, list.get(i));
						}
						executor.executeBatch();
						return null;
					}
				});
			}
		} catch (Exception e) {
			if (LOG.isDebugEnabled()) {
				e.printStackTrace();
				LOG.debug("batchInsert error: id [" + statementName
						+ "], parameterObject [" + list + "].  Cause: "
						+ e.getMessage());
			}
		}
	}

	/**
	 * 批量删除
	 * 
	 * @param statementName
	 * @param list
	 * @author nixianjun 2013.8.8
	 */
	public void batchDelete(final String statementName, final List list) {
		try {
			if (list != null) {
				execute(new SqlMapClientCallback<List>() {
					public List doInSqlMapClient(SqlMapExecutor executor)
							throws SQLException {
						executor.startBatch();
						for (int i = 0, n = list.size(); i < n; i++) {
							executor.delete(statementName, list.get(i));
						}
						executor.executeBatch();
						return null;
					}
				});
			}

		} catch (Exception e) {
			if (LOG.isDebugEnabled()) {
				e.printStackTrace();
				LOG.debug("batchDelete error: id [" + statementName
						+ "], parameterObject [" + list + "].  Cause: "
						+ e.getMessage());
			}
		}
	}


}
