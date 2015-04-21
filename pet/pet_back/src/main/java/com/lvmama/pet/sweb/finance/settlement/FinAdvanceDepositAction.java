package com.lvmama.pet.sweb.finance.settlement;

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
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.fin.FinAdvanceDeposit;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.po.fin.SetSettlementPayment;
import com.lvmama.comm.pet.service.fin.FinanceAdvanceDepositService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.Pagination;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.FIN_CURRENCY;
import com.lvmama.comm.vst.service.VstSuppSupplierService;
import com.lvmama.comm.vst.vo.VstSuppSupplierSettlementVo;
import com.lvmama.comm.vst.vo.VstSuppSupplierVo;

/**
 * 预存款管理/预警
 * @author zhangwenjun
 *
 */
@Results(value={
		@Result(name="index", location="/WEB-INF/pages/back/finance/settlement/fin_advance_deposit/fin_advance_deposit_index.ftl"),
		@Result(name="warning", location="/WEB-INF/pages/back/finance/settlement/fin_advance_deposit/fin_advance_deposit_warning.ftl"),
		@Result(name="record", location="/WEB-INF/pages/back/finance/settlement/fin_advance_deposit/fin_advance_deposit_record.ftl"),
		@Result(name="settlementRecord", location="/WEB-INF/pages/back/finance/settlement/fin_advance_deposit/fin_advance_deposit_settlement_record.ftl")
})
@Namespace(value="/finance/advanceDeposit")
public class FinAdvanceDepositAction extends FinancePageAction{
	private static final long serialVersionUID = 1L;
	@Autowired
	private FinanceAdvanceDepositService financeAdvanceDepositService;
	@Autowired
	private VstSuppSupplierService vstSuppSupplierService;
	
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
		Page<FinSupplierMoney> page = financeAdvanceDepositService.searchAdvanceDepositWarning(this.initSearchParameter());
		if(null!=page && null!=page.getItems() && !page.getItems().isEmpty()){
			for(FinSupplierMoney finSupplierMoney:page.getItems()){
				Long supplierId = finSupplierMoney.getSupplierId();
				VstSuppSupplierVo vstSuppSupplierVo =  vstSuppSupplierService.findVstSuppSupplierById(supplierId);
				if(null!=vstSuppSupplierVo){
					finSupplierMoney.setSupplierName(vstSuppSupplierVo.getSupplierName());
				}
			}
		}
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
		Page<FinSupplierMoney> page = financeAdvanceDepositService.searchAdvanceDeposit(this.initSearchParameter());
		if(null!=page && null!=page.getItems() && !page.getItems().isEmpty()){
			for(FinSupplierMoney finSupplierMoney:page.getItems()){
				Long supplierId = finSupplierMoney.getSupplierId();
				VstSuppSupplierVo vstSuppSupplierVo =  vstSuppSupplierService.findVstSuppSupplierById(supplierId);
				if(null!=vstSuppSupplierVo){
					finSupplierMoney.setSupplierName(vstSuppSupplierVo.getSupplierName());
				}
			}
		}
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
		Long totalResultSize = financeAdvanceDepositService.searchFinAdvanceDepositCount(params);
		finAdvanceDepositPage.setTotalResultSize(totalResultSize);
		finAdvanceDepositPage.setCurrentPage(this.page);
		//初始化分页信息
		finAdvanceDepositPage.buildUrl(getRequest());
		params.put("skipResults", finAdvanceDepositPage.getStartRows() - 1);
		params.put("maxResults", finAdvanceDepositPage.getEndRows());
		finAdvanceDepositPage.setItems(financeAdvanceDepositService.searchFinAdvanceDeposit(params));
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
		List<SetSettlementPayment> result = financeAdvanceDepositService.searchSettlementPayment(supplierId);
		if(null!=result && !result.isEmpty()){
			for(SetSettlementPayment setSettlementPayment:result){
				Long targetId = setSettlementPayment.getTargetId();
				VstSuppSupplierSettlementVo vstSuppSupplierSettlementVo =  vstSuppSupplierService.findSuppSupplierSettlementById(targetId);
				if(null!=vstSuppSupplierSettlementVo){
					setSettlementPayment.setSupplierName(vstSuppSupplierSettlementVo.getAccountName());
				}
			}
		}
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
		finAdvancedDeposit.setBusinessName(Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name());
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
		financeAdvanceDepositService.addAdvanceDeposit(finAdvancedDeposit);
		// 更新供应商的预存款币种
		financeAdvanceDepositService.updateSupplierCurrency(finAdvancedDeposit);
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
		finAdvancedDeposit.setBusinessName(Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name());
		financeAdvanceDepositService.shiftout2Deposit(finAdvancedDeposit);
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
		FinSupplierMoney simpleSupplier = financeAdvanceDepositService.searchSupplier(supplierId);
		this.sendAjaxResultByJson(JSONObject.fromObject(simpleSupplier).toString());
	}
}
