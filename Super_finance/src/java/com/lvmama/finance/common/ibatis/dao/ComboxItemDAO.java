package com.lvmama.finance.common.ibatis.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.lvmama.finance.base.BaseDAO;
import com.lvmama.finance.common.ibatis.po.ComboxItem;

@Repository
@SuppressWarnings("unchecked")
public class ComboxItemDAO extends BaseDAO{
	/**
	 * 查询下拉框项
	 * @param sqlName
	 * @return
	 */
	public List<ComboxItem> getComboxItem(String sqlName){
		return queryForList(sqlName);
	}
}
