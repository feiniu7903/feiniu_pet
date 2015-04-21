package com.lvmama.finance.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

/**
 * 分页基础DAO
 * 
 * @author yanggan
 * 
 */
@Repository
public class PageDao extends BaseDAO {

	/**
	 * 查询分页数据
	 * 
	 * @param queryName
	 *            sqlmap的查询ID
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page queryForPage(String queryName) {
		Map map = null;
		return this.queryForPageFin(queryName, map);
	}

	/**
	 * 查询分页数据
	 * 
	 * @param queryName
	 *            sqlmap的查询ID
	 * @param params
	 *            参数Map
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public Page queryForPageFin(String queryName, Map params) {
		return this.queryForPageFin(queryName, queryName + "Count", params);
	}

	/**
	 * 查询分页数据
	 * 
	 * @param queryName
	 *            sqlmap的查询数据ID
	 * @param queryCountName
	 *            sqlmap的查询数量ID
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	public Page queryForPageFin(String queryName, String queryCountName) {
		return this.queryForPageFin(queryName, queryCountName, null);
	}

	/**
	 * 查询分页数据
	 * 
	 * @param queryName
	 *            sqlmap的查询数据ID
	 * @param queryCountName
	 *            sqlmap的查询数量ID
	 * @param params
	 *            参数Map
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Page queryForPageFin(String queryName, String queryCountName, Map params) {
		if (params == null) {
			params = new HashMap();
		}
		Page page = new Page();
		params.putAll(FinanceContext.getPageSearchContext().getPageinfo());
		page.setPagesize(FinanceContext.getPageSearchContext().getPageSize());
		page.setCurrpage(FinanceContext.getPageSearchContext().getCurrpage());
		List result = queryForList(queryName, params);
		page.setInvdata(result);
		if (!(result.size() == 0)) {
			Integer count = (Integer) queryForObject(queryCountName, params);
			page.setTotalrecords(count);
		} else {
			page.setTotalrecords(0);
		}
		return page;
	}

}
