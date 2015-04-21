package com.lvmama.finance.common.ibatis.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.lvmama.finance.base.BaseDAO;
import com.lvmama.finance.common.ibatis.vo.AutocompleteItem;

/**
 * INPUT输入框自动提示
 * 
 * @author yanggan
 *
 */
@Repository
public class AutocompleteDAO extends BaseDAO{
	
	/**
	 * 查询供应商
	 * @param term 输入内容
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutocompleteItem> supplier(String term){
		Map<String, String> map = new HashMap<String, String>();
		map.put("label", "%"+term+"%");
		map.put("value", term);
		return queryForList("AUTOCOMPLETE.searchSupplier",map);
	}
	
	/**
	 * @param sqlName	sqlMap中的selectId
	 * @param term	输入内容
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AutocompleteItem> searchAutocompleteItems(String sqlName,String term){
		Map<String, String> map = new HashMap<String, String>();
		map.put("label", "%"+term+"%");
		map.put("value", term);
		return queryForList("AUTOCOMPLETE." + sqlName ,map);
	}
	
}
