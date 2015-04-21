package com.lvmama.finance.settlement.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lvmama.comm.vo.Currency;
import com.lvmama.finance.base.BaseAction;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.base.annotation.PageSearch;
import com.lvmama.finance.settlement.ibatis.po.FincForegifts;
import com.lvmama.finance.settlement.ibatis.vo.SimpleSupplier;
import com.lvmama.finance.settlement.service.ForegiftsService;

/**
 * 押金管理Action
 * 
 * @author yanggan
 * 
 */
@Controller
@RequestMapping("/settlement/foregifts")
public class ForegiftsAction extends BaseAction {
	
	@Autowired
	private ForegiftsService foregiftsService;

	/**
	 * 进入押金管理页面
	 * 
	 * @return 管理页面
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String goregifts(Model model) {
		List<Currency> currencyList = foregiftsService.searchAllCurrency();
		model.addAttribute("currencyList", currencyList);
		return "settlement/foregifts/manage";
	}

	/**
	 * 查询供应商押金、担保函
	 * 
	 * @param supplier
	 *            查询的供应商ID
	 * @param type
	 *            类型 押金 OR 担保函
	 * 
	 * @return 结果页面
	 */
	@PageSearch(autobind = true)
	@RequestMapping(value = "/search")
	public Page<SimpleSupplier> search(@RequestParam("type") String type) {
		Page<SimpleSupplier> page = null;
		if ("CASH".equals(type)) {// 查询供应商押金
			page = foregiftsService.searchForegifts();
		} else if ("GUARANTEE".equals(type)) {// 查询供应商担保函
			page = foregiftsService.searchGuaranteeLimit();
		}
		return page;
	}
	/**
	 * 进入押金预警页面
	 * @return
	 */
	@RequestMapping(value = "/alert", method = RequestMethod.GET)
	public String alert(){
		return "settlement/foregifts/alert";
	}
	/**
	 * 查询供应商押金预警
	 * 
	 * @return 结果页面
	 */
	@PageSearch(autobind = true)
	@RequestMapping(value = "/alert/search")
	public Page<SimpleSupplier> alertSearch() {
		return foregiftsService.searchForegiftsAlert();
	}
	

	/**
	 * 查询流水记录
	 * 
	 * @param supplierId
	 *            供应商ID
	 * 
	 * @return 流水记录页面
	 */
	@RequestMapping(value = "/record/{supplierId}/{type}", method = RequestMethod.GET)
	public String record(@PathVariable("supplierId") Integer supplierId, @PathVariable("type") String type, Model model) {
		List<FincForegifts> result = foregiftsService.searchFincForegifts(type, supplierId);
		model.addAttribute("result", result);
		return "settlement/foregifts/record";
	}

	/**
	 * 添加押金
	 * 
	 * @param fincForegifts
	 *            押金信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public FincForegifts add(FincForegifts fincForegifts) {
		foregiftsService.addForegifts(fincForegifts);
		// 更新供应商的预存款币种
		foregiftsService.updateSupplierCurrency(fincForegifts.getSupplierId(), fincForegifts.getDepositCurrency());
		return fincForegifts;
	}

	/**
	 * 转为预存款
	 * 
	 * @param fincForegifts
	 *            押金信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/shiftout", method = RequestMethod.POST)
	public FincForegifts shiftout(FincForegifts fincForegifts) {
		foregiftsService.shiftout2Advancedeposits(fincForegifts);
		return fincForegifts;
	}
	/**
	 * 
	 * 查询供应商的押金余额
	 * 
	 * @param supplierId
	 * 
	 * @param type
	 * 
	 * @param model
	 */
	@RequestMapping(value = "/getamount/{type}/{supplierId}", method = RequestMethod.GET)
	public SimpleSupplier getAmount(@PathVariable("supplierId") Long supplierId, Model model) {
		SimpleSupplier simpleSupplier = foregiftsService.searchSupplier(supplierId);
		return simpleSupplier;
	}
	
	
}
