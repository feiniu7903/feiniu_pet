package com.lvmama.finance.common.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lvmama.finance.common.ibatis.dao.AutocompleteDAO;
import com.lvmama.finance.common.ibatis.vo.AutocompleteItem;

@Service
public class AutocompleteServiceImpl implements AutocompleteService {

	@Autowired
	private AutocompleteDAO autocompleteDAO;
	
	@Override
	public List<AutocompleteItem> supplier(String term) {
		return autocompleteDAO.supplier(term);
	}
	
	public List<AutocompleteItem> searchAutocompleteItems(String sqlName, String term){
		return autocompleteDAO.searchAutocompleteItems(sqlName, term);
	}

}
