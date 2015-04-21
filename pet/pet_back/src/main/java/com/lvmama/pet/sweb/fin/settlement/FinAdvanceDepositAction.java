package com.lvmama.pet.sweb.fin.settlement;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.fin.FinAdvanceDeposit;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.po.fin.SetSettlementPayment;
import com.lvmama.comm.pet.service.fin.FinAdvanceDepositService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.Pagination;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.FIN_CURRENCY;
import com.lvmama.pet.sweb.fin.common.FinPageAction;

/**
 * 预存款管理/预警
 * @author zhangwenjun
 *
 */
@Results(value={
		@Result(name="index", location="/WEB-INF/pages/back/fin/settlement/fin_advance_deposit/fin_advance_deposit_index.ftl"),
		@Result(name="warning", location="/WEB-INF/pages/back/fin/settlement/fin_advance_deposit/fin_advance_deposit_warning.ftl"),
		@Result(name="record", location="/WEB-INF/pages/back/fin/settlement/fin_advance_deposit/fin_advance_deposit_record.ftl"),
		@Result(name="settlementRecord", location="/WEB-INF/pages/back/fin/settlement/fin_advance_deposit/fin_advance_deposit_settlement_record.ftl")
})
@Namespace(value="/fin/advanceDeposit")
public class FinAdvanceDepositAction extends FinPageAction{
	private static final long serialVersionUID = 1L;

	private FinAdvanceDepositService finAdvanceDepositService;
	
	private Page<FinAdvanceDeposit> finAdvanceDepositPage = new Page<FinAdvanceDeposit>();
	
	/**
	 * 币种
	 */
	private FIN_CURRENCY[] currencyList;

	/**
	 * 交易类型
	 */
	private Constant.ADVANCE_DEPOSIT_TYPE[] typeList;

	public Constant.ADVANCE_DEPOSIT_TYPE[] getTypeList() {
		return typeList;
	}

	public void setTypeList(Constant.ADVANCE_DEPOSIT_TYPE[] typeList) {
		this.typeList = typeList;
	}

	public FIN_CURRENCY[] getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(FIN_CURRENCY[] currencyList) {
		this.currencyList = currencyList;
	}

	@Override
	public Map<String,Object> initRequestParameter() {
		HttpServletRequest request = getRequest();
		Map<String, Object> map = new HashMap<String, Object>();
		extractRequestParam(map,"supplier",request);
		
		return map;
	}
	
	/**
	 * 进入预存款预警页面
	 * @return
	 */
	@Action("warningIndex")
	public String warningIndex(){
		return "warning";
	}
	
	/**
	 * 查询供应商预存款预警
	 * 
	 * @return 结果页面
	 */
	@Action("warningSearch")
	public void warningSearch() {
		Page<FinSupplierMoney> page = finAdvanceDepositService.searchAdvanceDepositWarning(this.initSearchParameter());
		this.sendAjaxResultByJson(JSONObject.fromObject(page).toString());
	}
	
	/**
	 * 进入预存款管理页面
	 * 
	 * @return 管理页面
	 */
	@Action("index")
	public String advancedeposits() {
		currencyList = FIN_CURRENCY.values();
		typeList = Constant.ADVANCE_DEPOSIT_TYPE.values();
		if(null != currencyList){
			getRequest().setAttribute("currencyList", currencyList);
		}
		if(null != typeList){
			getRequest().setAttribute("typeList", typeList);
		}
		return "index";
	}
	
	/**
	 * 查询供应商预存款
	 * 
	 * @return 结果页面
	 */
	@Action("search")
	public void search() {
		Page<FinSupplierMoney> page = finAdvanceDepositService.searchAdvanceDeposit(this.initSearchParameter());
		this.sendAjaxResultByJson(JSONObject.fromObject(page).toString());
	}
	
	/**
	 * 查询流水记录
	 * 
	 * @param supplierId
	 *            供应商ID
	 * 
	 * @return 流水记录页面
	 */
	@Action("record")
	public String record() {
		Long supplierId = Long.parseLong(getRequest().getParameter("supplierId"));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("supplierId", supplierId);
		Long totalResultSize = finAdvanceDepositService.searchFinAdvanceDepositCount(params);
		finAdvanceDepositPage.setTotalResultSize(totalResultSize);
		finAdvanceDepositPage.setCurrentPage(this.page);
		//初始化分页信息
		finAdvanceDepositPage.buildUrl(getRequest());
		params.put("skipResults", finAdvanceDepositPage.getStartRows() - 1);
		params.put("maxResults", finAdvanceDepositPage.getEndRows());
		finAdvanceDepositPage.setItems(finAdvanceDepositService.searchFinAdvanceDeposit(params));
		getRequest().setAttribute("finAdvanceDepositPage", finAdvanceDepositPage);
		String paginationHtml = Pagination.pagination(finAdvanceDepositPage);
		getRequest().setAttribute("paginationHtml", paginationHtml);
		
		return "record";
	}
	
	/**
	 * 查询预存款结算记录
	 * 
	 * @param supplierId
	 *            供应商ID
	 * 
	 * @return 结算记录页面
	 */
	@Action("settlementRecord")
	public String settlementrecord() {
		Long supplierId = Long.parseLong(getRequest().getParameter("supplierId"));
		List<SetSettlementPayment> result = finAdvanceDepositService.searchSettlementPayment(supplierId);
		getRequest().setAttribute("result", result);
		
		return "settlementRecord";
	}
	
	/**
	 * 添加预存款
	 * 
	 * @param finAdvancedDeposit 预存款信息
	 * 
	 * @return
	 */
	@Action(value = "addAdvancedDeposit")
	public void addAdvancedDeposit(){
		FinAdvanceDeposit finAdvancedDeposit = new FinAdvanceDeposit();
		Long supplierId = Long.parseLong(getRequest().getParameter("supplierId"));
		Long amount = PriceUtil.convertToFen(getRequest().getParameter("amount"));
		String type = getRequest().getParameter("type");
		String advCurrency = getRequest().getParameter("advCurrency");
		String bank = getRequest().getParameter("bank");
		String operatetimes = getRequest().getParameter("operatetimes");
		String serial = getRequest().getParameter("serial");
		String remark = getRequest().getParameter("remark");
		finAdvancedDeposit.setSupplierId(supplierId);
		finAdvancedDeposit.setType(type);
		finAdvancedDeposit.setAmount(amount);
		finAdvancedDeposit.setCurrency(advCurrency);
		finAdvancedDeposit.setBank(bank);
		finAdvancedDeposit.setCreator(getSessionUserName());
		Date timeDate;
		try {
			timeDate = new SimpleDateFormat("yyyy-MM-dd HH:mm").parse(operatetimes);
			finAdvancedDeposit.setOperatetime(timeDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		finAdvancedDeposit.setSerial(serial);
		finAdvancedDeposit.setRemark(remark);
		
		// 新增记录
		finAdvanceDepositService.addAdvanceDeposit(finAdvancedDeposit);
		// 更新供应商的预存款币种
		finAdvanceDepositService.updateSupplierCurrency(finAdvancedDeposit);
	}
	
	/**
	 * 转为押金
	 * 
	 * @param fincForegifts 押金信息
	 * 
	 * @return
	 */
	@Action("shiftout")
	public void shiftout(){
		FinAdvanceDeposit finAdvancedDeposit = new FinAdvanceDeposit();
		Long supplierId = Long.parseLong(getRequest().getParameter("supplierId"));
		Long amount = PriceUtil.convertToFen(getRequest().getParameter("amount"));
		String advCurrency = getRequest().getParameter("advCurrency");
		finAdvancedDeposit.setSupplierId(supplierId);
		finAdvancedDeposit.setAmount(amount);
		finAdvancedDeposit.setCurrency(advCurrency);
		finAdvancedDeposit.setCreator(getSessionUserName());
		finAdvanceDepositService.shiftout2Deposit(finAdvancedDeposit);
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
	@Action("getamount")
	public void getAmount() {
		Long supplierId = Long.parseLong(getRequest().getParameter("supplierId"));
		FinSupplierMoney simpleSupplier = finAdvanceDepositService.searchSupplier(supplierId);
		this.sendAjaxResultByJson(JSONObject.fromObject(simpleSupplier).toString());
	}

	public void setFinAdvanceDepositService(FinAdvanceDepositService finAdvanceDepositService) {
		this.finAdvanceDepositService = finAdvanceDepositService;
	}
	
//	
//	/**
//	 * 打款时，根据币种和供应商ID查询预存款余额
//	 * @param supplierId
//	 * @param currency
//	 * @param model
//	 */
//	@Action("getamount/{supplierId}/{currency}")
//	public void getAmount(@PathVariable("supplierId") Long supplierId,@PathVariable("currency") String currency, Model model) {
//		Double amount = finAdvancedDepositService.searchAmount( supplierId ,currency);
//		amount = amount == null ? 0 : amount;
//		model.addAttribute(amount);
//	}
	
	
}
