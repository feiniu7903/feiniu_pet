package com.lvmama.pet.fin.dao;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;
import com.ibatis.sqlmap.client.SqlMapClient;
import com.lvmama.comm.LvmamaSqlMapClientTemplate;
import com.lvmama.comm.pet.vo.Page;

@SuppressWarnings({ "rawtypes", "unchecked" })
public class FinBaseDAO extends LvmamaSqlMapClientTemplate{

	@Override
	@Resource
	public void setSqlMapClient(SqlMapClient sqlMapClient) {
		super.setSqlMapClient(sqlMapClient);
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
