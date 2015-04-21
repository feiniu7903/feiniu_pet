package com.lvmama.pet.sweb.finance.settlement;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.fin.FinDeposit;
import com.lvmama.comm.pet.po.fin.FinSupplierMoney;
import com.lvmama.comm.pet.service.fin.FinanceAdvanceDepositService;
import com.lvmama.comm.pet.service.fin.FinanceDepositService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.Pagination;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.FIN_CURRENCY;
import com.lvmama.comm.vst.service.VstSuppSupplierService;
import com.lvmama.comm.vst.vo.VstSuppSupplierVo;

/**
 * 押金管理/预警
 * @author zhangwenjun
 *
 */
@Results(value={
		@Result(name="index", location="/WEB-INF/pages/back/finance/settlement/fin_deposit/fin_deposit_index.ftl"),
		@Result(name="warning", location="/WEB-INF/pages/back/finance/settlement/fin_deposit/fin_deposit_warning.ftl"),
		@Result(name="record", location="/WEB-INF/pages/back/finance/settlement/fin_deposit/fin_deposit_record.ftl")
})
@Namespace(value="/finance/deposit")
public class FinDepositAction extends FinancePageAction{

	private static final long serialVersionUID = 1L;
	@Autowired
	private FinanceDepositService financeDepositService;
	@Autowired
	private FinanceAdvanceDepositService financeAdvanceDepositService;
	@Autowired
	private VstSuppSupplierService vstSuppSupplierService;
	private Page<FinDeposit> finForegiftPage = new Page<FinDeposit>();
	
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
		extractRequestParam(map,"type",request);
		
		return map;
	}
	
	/**
	 * 进入押金管理页面
	 * 
	 */
	@Action("index")
	public String deposit() {
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
	 * 查询供应商押金、担保函押金
	 * 
	 */
	@Action("search")
	public void search() {
		String type = getRequest().getParameter("type");
		Page<FinSupplierMoney> page = null;
		if ("CASH".equals(type)) {
			// 查询供应商押金
			page = financeDepositService.searchDeposit(this.initSearchParameter());
		} else if ("GUARANTEE".equals(type)) {
			// 查询供应商担保函
			page = financeDepositService.searchGuaranteeLimit(this.initSearchParameter());
		}
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
	 * 进入押金预警页面
	 */
	@Action("warningIndex")
	public String alert(){
		return "warning";
	}
	
	/**
	 * 查询供应商押金预警
	 */
	@Action("warningSearch")
	public void warningSearch() {
		Page<FinSupplierMoney> page = financeDepositService.searchForegiftWarning(this.initSearchParameter());
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
	 */
	@Action("record")
	public String record() {
		Long supplierId = Long.parseLong(getRequest().getParameter("supplierId"));
		String type = getRequest().getParameter("type");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("supplierId", supplierId);
		map.put("type", type);
		Long totalResultSize = financeDepositService.searchForegiftRecordCount(map);
		finForegiftPage.setTotalResultSize(totalResultSize);
		finForegiftPage.setCurrentPage(this.page);
		//初始化分页信息
		finForegiftPage.buildUrl(getRequest());
		map.put("skipResults", finForegiftPage.getStartRows() - 1);
		map.put("maxResults", finForegiftPage.getEndRows());
		finForegiftPage.setItems(financeDepositService.searchForegiftRecord(map));
		getRequest().setAttribute("finForegiftPage", finForegiftPage);
		String paginationHtml = Pagination.pagination(finForegiftPage);
		getRequest().setAttribute("paginationHtml", paginationHtml);
		
		return "record";
	}

	/**
	 * 转为预存款
	 * 
	 */
	@Action("shiftout")
	public void shiftout() {
		FinDeposit finDeposit = new FinDeposit();
		Long supplierId = Long.parseLong(getRequest().getParameter("supplierId"));
		Long amount = PriceUtil.convertToFen(getRequest().getParameter("amount"));
		String depositCurrency = getRequest().getParameter("depositCurrency");
		finDeposit.setSupplierId(supplierId);
		finDeposit.setAmount(amount);
		finDeposit.setCurrency(depositCurrency);
		finDeposit.setCreator(this.getSessionUserName());
		finDeposit.setCreatorName(this.getSessionUserName());
		finDeposit.setBusinessName(Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name());
		financeAdvanceDepositService.shiftout2AdvanceDeposit(finDeposit);
	}

	/**
	 * 添加押金
	 * 
	 */
	@Action("addForegift")
	public void addForegift() {
		FinDeposit finDeposit = new FinDeposit();
		Long supplierId = Long.parseLong(getRequest().getParameter("supplierId"));
		String kind = getRequest().getParameter("kind");
		String type = getRequest().getParameter("type");
		Long amount = PriceUtil.convertToFen(getRequest().getParameter("amount"));
		String currency = getRequest().getParameter("depositCurrency");
		String bank = getRequest().getParameter("bank");
		String operatetime = getRequest().getParameter("operatetimes");
		String serial = getRequest().getParameter("serial");
		String remark = getRequest().getParameter("remark");
		finDeposit.setSupplierId(supplierId);
		finDeposit.setKind(kind);
		finDeposit.setType(type);
		finDeposit.setAmount(amount);
		finDeposit.setCurrency(currency);
		finDeposit.setBank(bank);
		Date timeDate = DateUtil.stringToDate(operatetime, "yyyy-MM-dd HH:mm");
		finDeposit.setOperatetime(timeDate);
		finDeposit.setSerial(serial);
		finDeposit.setRemark(remark);
		finDeposit.setCreator(this.getSessionUserName());
		finDeposit.setBusinessName(Constant.SET_SETTLEMENT_BUSINESS_NAME.NEW_SUPPLIER_BUSINESS.name());
		financeDepositService.addDeposit(finDeposit);
		// 更新供应商的押金币种
		financeDepositService.updateSupplierCurrency(finDeposit);
	}
	
	/**
	 * 
	 * 查询供应商的押金余额
	 * 
	 */
	@Action("getamount")
	public void getAmount() {
		Long supplierId = Long.parseLong(getRequest().getParameter("supplierId"));
		FinSupplierMoney finSupplierMoney = financeDepositService.searchSupplier(supplierId);
		if(null!=finSupplierMoney){
			VstSuppSupplierVo vstSuppSupplierVo =  vstSuppSupplierService.findVstSuppSupplierById(supplierId);
			if(null!=vstSuppSupplierVo){
				finSupplierMoney.setSupplierName(vstSuppSupplierVo.getSupplierName());
			}
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(finSupplierMoney).toString());
	}
	
}
