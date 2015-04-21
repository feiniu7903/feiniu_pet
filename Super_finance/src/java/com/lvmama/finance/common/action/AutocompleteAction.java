package com.lvmama.finance.common.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.lvmama.finance.base.BaseAction;
import com.lvmama.finance.common.ibatis.vo.AutocompleteItem;
import com.lvmama.finance.common.service.AutocompleteService;

/**
 * 输入框自动提示
 * 
 * @author yanggan
 * 
 */
@Controller
@RequestMapping("/autocomplete")
public class AutocompleteAction extends BaseAction {

	@Autowired
	private AutocompleteService autocompleteService;

	@RequestMapping(value = "/supplier")
	public List<AutocompleteItem> suppliter(@RequestParam("term") String term) {
		
		return autocompleteService.supplier(term);
		
	}
	
	/**
	 * 结算对象
	 * @param term
	 * @return
	 */
	@RequestMapping(value = "/settlement_target")
	public List<AutocompleteItem> searchSettlementTarget(@RequestParam("term") String term) {
		return autocompleteService.searchAutocompleteItems("searchSettlementTarget", term);
	}
	
	/**
	 * 采购产品
	 * @param term
	 * @return
	 */
	@RequestMapping(value = "/meta_product")
	public List<AutocompleteItem> searchMetaProduct(@RequestParam("term") String term) {
		return autocompleteService.searchAutocompleteItems("searchMetaProduct", term);
	}
	
	/**
	 * 销售产品
	 * @param term
	 * @return
	 */
	@RequestMapping(value = "/product")
	public List<AutocompleteItem> searchProduct(@RequestParam("term") String term) {
		return autocompleteService.searchAutocompleteItems("searchProduct", term);
	}
}
