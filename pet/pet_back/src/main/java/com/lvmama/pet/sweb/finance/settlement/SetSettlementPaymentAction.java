package com.lvmama.pet.sweb.finance.settlement;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONObject;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.fin.SetSettlementPayment;
import com.lvmama.comm.pet.service.fin.FinDepositService;
import com.lvmama.comm.pet.service.fin.SetSettlementPaymentService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.FIN_CURRENCY;
import com.lvmama.comm.vo.Constant.PAYMENT_BANK;
import com.lvmama.comm.vst.service.VstSuppSupplierService;
import com.lvmama.comm.vst.vo.VstSuppSupplierSettlementVo;
import com.lvmama.comm.vst.vo.VstSuppSupplierVo;

/**
 * 打款记录
 * @author zhangwenjun
 */
@Results(value={
		@Result(name="index", location="/WEB-INF/pages/back/finance/settlement/set_settlement_payment.ftl")
})
@Namespace(value="/finance/set/payment")
public class SetSettlementPaymentAction extends FinancePageAction{
	
	private static final long serialVersionUID = 1L;
	@Autowired
	private SetSettlementPaymentService setSettlementPaymentService;
	@Autowired
	private FinDepositService finDepositService;
	@Autowired
	private VstSuppSupplierService vstSuppSupplierService;
	/**
	 * 币种
	 */
	private FIN_CURRENCY[] currencyList;
	/**
	 * 支付平台
	 */
	private Constant.PAYMENT_BANK[] bankList;

	public Constant.PAYMENT_BANK[] getBankList() {
		return bankList;
	}

	public void setBankList(Constant.PAYMENT_BANK[] bankList) {
		this.bankList = bankList;
	}

	public FIN_CURRENCY[] getCurrencyList() {
		return currencyList;
	}

	public void setCurrencyList(FIN_CURRENCY[] currencyList) {
		this.currencyList = currencyList;
	}

	@Action("index")
	public String paymentHistory(){
		currencyList = FIN_CURRENCY.values();
		bankList = PAYMENT_BANK.values();
		getRequest().setAttribute("currency", currencyList);
		getRequest().setAttribute("bankList", bankList);
		return "index";
	}

	@Override
	public Map<String, Object> initRequestParameter() {
		HttpServletRequest request = getRequest();
		Map<String, Object> map = new HashMap<String, Object>();
		extractRequestParam(map,"groupId",request);
		extractRequestParam(map,"settlementId",request);
		extractRequestParam(map,"supplier",request);
		extractRequestParam(map,"platform",request);
		extractRequestParam(map,"currency",request);
		extractRequestParam(map,"payTimeStart",request);
		extractRequestParam(map,"payTimeEnd",request);
		
		return map;
	}
	
	@Action("searchPaymentHistory")
	public void searchPaymentHistory(){
		Page<SetSettlementPayment> page = setSettlementPaymentService.search(this.initSearchParameter());
		if(null!=page && null!=page.getItems() && !page.getItems().isEmpty()){
			for(SetSettlementPayment setSettlementPayment:page.getItems()){
				Long targetId = setSettlementPayment.getTargetId();
				VstSuppSupplierSettlementVo vstSuppSupplierSettlementVo =vstSuppSupplierService.findSuppSupplierSettlementById(targetId);
				if(null!=vstSuppSupplierSettlementVo){
					setSettlementPayment.setTargetName(vstSuppSupplierSettlementVo.getAccountName());
				}
				if(null!=setSettlementPayment.getSupplierId()){
					Long supplierId = Long.parseLong(setSettlementPayment.getSupplierId());
					VstSuppSupplierVo vstSuppSupplierVo = vstSuppSupplierService.findVstSuppSupplierById(supplierId);
					if(null!=vstSuppSupplierVo){
						setSettlementPayment.setSupplierName(vstSuppSupplierVo.getSupplierName());
					}
				}
			}
		}
		this.sendAjaxResultByJson(JSONObject.fromObject(page).toString());
	}
	
}
