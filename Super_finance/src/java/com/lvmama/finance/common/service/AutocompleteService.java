package com.lvmama.finance.common.service;

import java.util.List;

import com.lvmama.finance.common.ibatis.vo.AutocompleteItem;

/**
 * input输入框自动提示
 * 
 * @author yanggan
 * 
 */
public interface AutocompleteService {

	/**
	 * 查询供应商
	 * 
	 * @param term
	 *            输入内容
	 * @return
	 */
	List<AutocompleteItem> supplier(String term);

	/**
	 * 查询显示项
	 * @param term
	 *            输入内容
	 * @return
	 */
	List<AutocompleteItem> searchAutocompleteItems(String sqlName, String term);

}
