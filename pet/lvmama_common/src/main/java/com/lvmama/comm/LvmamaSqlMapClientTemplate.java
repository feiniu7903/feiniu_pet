package com.lvmama.comm;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;
import org.springframework.orm.ibatis.SqlMapClientTemplate;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.lvmama.comm.pet.vo.Page;

public class LvmamaSqlMapClientTemplate<T> extends SqlMapClientTemplate {
	
	private static final Log LOG = LogFactory.getLog(LvmamaSqlMapClientTemplate.class);

	private int maxRows = 1001;
	private int maxRowsForReport = 50000;

	public List<T> queryForList(String statementName) throws DataAccessException {
		return queryForList(statementName, null);
	}
	
	public List<T> queryForList(final String statementName, final Object parameterObject)
			throws DataAccessException {
		return execute(new SqlMapClientCallback<List<T>>() {
			public List<T> doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				List<T> result = executor.queryForList(statementName, parameterObject, 0, maxRows);
				if (result != null && result.size() == maxRows) {
					LOG.error("SQL EXCEPTION: result size is greater than the max rows, " + statementName);
				}
				return result;
			}
		});
	}
	
	public List<T> queryForList(String statementName, int skipResults, int maxResults)
			throws DataAccessException {
		if((maxResults-skipResults)>=maxRows){
			maxResults = skipResults + maxRows;
			LOG.error("SQL EXCEPTION: result size is greater than the max rows, " + statementName);
		}
		
		return queryForList(statementName, null, skipResults, maxResults);
	}

	public List<T> queryForList(final String statementName, final Object parameterObject,
			final int skipResults, final int maxResults) throws DataAccessException {
		return execute(new SqlMapClientCallback<List>() {
			public List doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				int tempMaxResults = maxResults;
				if((maxResults-skipResults)>=maxRows){
					tempMaxResults = skipResults + maxRows;
					LOG.error("SQL EXCEPTION: result size is greater than the max rows, " + statementName);
				}
				return executor.queryForList(statementName, parameterObject, skipResults,
						tempMaxResults);
			}
		});
	}
	
	//数据量比较大的报表导出请用这个接口
	public List<T> queryForListForReport(String statementName) throws DataAccessException {
		return queryForListForReport(statementName, null);
	}
	
	//数据量比较大的报表导出请用这个接口
	public List<T> queryForListForReport(final String statementName, final Object parameterObject)
			throws DataAccessException {
		return execute(new SqlMapClientCallback<List>() {
			public List doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				List result = executor.queryForList(statementName, parameterObject, 0, maxRowsForReport);
				if (result != null && result.size() == maxRowsForReport) {
					LOG.error("SQL EXCEPTION: result size is greater than the max rows, " + statementName);
				}
				return result;
			}
		});
	}
	//数据量比较大的报表导出请用这个接口
	public List<T> queryForList(final String statementName, final Object parameterObject,final boolean isForReportExport)
			throws DataAccessException {
		return execute(new SqlMapClientCallback<List<T>>() {
			public List<T> doInSqlMapClient(SqlMapExecutor executor) throws SQLException {
				
				int maxRowsTemp=maxRows;
				if(isForReportExport)
					maxRowsTemp=maxRowsForReport;

				List<T> result = executor.queryForList(statementName, parameterObject, 0, maxRowsTemp);
				if (result != null && result.size() == maxRowsTemp) {
					LOG.error("SQL EXCEPTION: result size is greater than the max rows, " + statementName);
				}
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
			page.setTotalResultSize(totalResultSize);
			page.setPageSize(pageSize);
			page.setCurrentPage(currentPage);
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
}
