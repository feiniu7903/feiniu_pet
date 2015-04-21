package com.lvmama.finance.settlement.action;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lvmama.comm.vo.Currency;
import com.lvmama.finance.base.BaseAction;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.base.annotation.PageSearch;
import com.lvmama.finance.settlement.ibatis.po.FincAdvancedeposits;
import com.lvmama.finance.settlement.ibatis.po.OrdSettlementPayment;
import com.lvmama.finance.settlement.ibatis.vo.SimpleSupplier;
import com.lvmama.finance.settlement.service.AdvancedepositsService;

/**
 * 预存管理Action
 * 
 * @author yanggan
 * 
 */
@Controller
@RequestMapping("/settlement/advancedeposits/")
public class AdvancedepositsAction extends BaseAction{

	@Autowired
	private AdvancedepositsService advancedepositsService;
	
	/**
	 * 进入押金管理页面
	 * 
	 * @return 管理页面
	 */
	@RequestMapping(value = "/index", method = RequestMethod.GET)
	public String advancedeposits(Model model) {
		List<Currency> currencyList = advancedepositsService.searchAllCurrency();
		model.addAttribute("currencyList", currencyList);
		return "settlement/advancedeposits/manage";
	}
	
	/**
	 * 查询供应商预存款
	 * 
	 * @return 结果页面
	 */
	@PageSearch(autobind = true)
	@RequestMapping(value = "/search")
	public Page<SimpleSupplier> search() {
		return advancedepositsService.searchAdvancedeposits();
	}
	/**
	 * 进入预存款预警页面
	 * @return
	 */
	@RequestMapping(value = "/alert", method = RequestMethod.GET)
	public String alert(){
		return "settlement/advancedeposits/alert";
	}
	/**
	 * 查询供应商预存款预警
	 * 
	 * @return 结果页面
	 */
	@PageSearch(autobind = true)
	@RequestMapping(value = "/alert/search")
	public Page<SimpleSupplier> alertSearch() {
		return advancedepositsService.searchAdvancedepositsAlert();
	}
	
	/**
	 * 查询流水记录
	 * 
	 * @param supplierId
	 *            供应商ID
	 * 
	 * @return 流水记录页面
	 */
	@RequestMapping(value = "/record/{supplierId}", method = RequestMethod.GET)
	public String record(@PathVariable("supplierId") Long supplierId,Model model) {
		List<FincAdvancedeposits> result = advancedepositsService.searchFincAdvancedeposits(supplierId);
		model.addAttribute("result", result);
		return "settlement/advancedeposits/record";
	}
	
	/**
	 * 查询预存款结算记录
	 * 
	 * @param supplierId
	 *            供应商ID
	 * 
	 * @return 结算记录页面
	 */
	@RequestMapping(value = "/settlementrecord/{supplierId}", method = RequestMethod.GET)
	public String settlementrecord(@PathVariable("supplierId") Long supplierId,Model model) {
		List<OrdSettlementPayment> result = advancedepositsService.searchSettlementPayment(supplierId);
		model.addAttribute("result", result);
		return "settlement/advancedeposits/settlement_record";
	}
	
	/**
	 * 添加预存款
	 * 
	 * @param fincAdvancedeposits 预存款信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public FincAdvancedeposits add(FincAdvancedeposits fincAdvancedeposits){
		advancedepositsService.addAdvancedeposits(fincAdvancedeposits);
		// 更新供应商的预存款币种
		advancedepositsService.updateSupplierCurrency(fincAdvancedeposits.getSupplierId(), fincAdvancedeposits.getAdvCurrency());
		return fincAdvancedeposits;
	}
	
	/**
	 * 转为押金
	 * 
	 * @param fincForegifts 押金信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/shiftout", method = RequestMethod.POST)
	public FincAdvancedeposits shiftout(FincAdvancedeposits fincAdvancedeposits){
		advancedepositsService.shiftout2Foregifts(fincAdvancedeposits);
		return fincAdvancedeposits;
	}
	
	/**
	 * 
	 * 查询供应商的预存款余额
	 * 
	 * @param supplierId
	 * 
	 * 
	 * @param model
	 */
	
	@RequestMapping(value = "/getamount/{supplierId}", method = RequestMethod.GET)
	public SimpleSupplier getAmount(@PathVariable("supplierId") Long supplierId, Model model) {
		SimpleSupplier simpleSupplier = advancedepositsService.searchSupplier(supplierId);
		return simpleSupplier;
	}
	
	
	/**
	 * 打款时，根据币种和供应商ID查询预存款余额
	 * @param supplierId
	 * @param currency
	 * @param model
	 */
	@RequestMapping(value = "/getamount/{supplierId}/{currency}", method = RequestMethod.GET)
	public void getAmount(@PathVariable("supplierId") Long supplierId,@PathVariable("currency") String currency, Model model) {
		Double amount = advancedepositsService.searchAmount( supplierId ,currency);
		amount = amount == null ? 0 : amount;
		model.addAttribute(amount);
	}
}
