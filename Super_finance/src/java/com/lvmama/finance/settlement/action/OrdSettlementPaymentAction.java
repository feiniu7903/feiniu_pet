package com.lvmama.finance.settlement.action;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lvmama.comm.vo.Currency;
import com.lvmama.finance.base.BaseAction;
import com.lvmama.finance.base.Page;
import com.lvmama.finance.base.annotation.PageSearch;
import com.lvmama.finance.settlement.ibatis.po.OrdSettlementPayment;
import com.lvmama.finance.settlement.service.OrdSettlementPaymentService;

@Controller
@RequestMapping(value="/settlement/ordSettlementPayment")
public class OrdSettlementPaymentAction extends BaseAction{
	@Autowired
	private OrdSettlementPaymentService ordSettlementPaymentService;
	
	@RequestMapping(value="paymentHistory",method=RequestMethod.GET)
	public String paymentHistory(Model model){
		List<Currency> currencyList = ordSettlementPaymentService.searchAllCurrency();
		model.addAttribute("currency", currencyList);
		return "settlement/payment/payment_history";
	}
	
	@PageSearch(autobind = true)
	@RequestMapping(value="searchPaymentHistory")
	public Page<OrdSettlementPayment> searchPaymentHistory(@RequestParam(required = false,value = "supplier") String supplierId, 
				@RequestParam("platform") String platform, 
				@RequestParam(required = false,value = "settlementId") String settlementId,
				@RequestParam(required = false,value = "payTimeStart") String payTimeStart,
				@RequestParam(required = false,value = "payTimeEnd") String payTimeEnd,
				@RequestParam(required = false,value = "groupId") String groupId,
				@RequestParam(required = false,value = "currency") String currency){
		Map<String, Object> map = new HashMap<String, Object>();
		if(supplierId != null && supplierId.trim().length() > 0){
			map.put("supplierId", supplierId);
		}
		if(platform != null && platform.trim().length() > 0){
			map.put("platform", "%" + platform + "%");
		}
		if(settlementId != null && settlementId.trim().length() > 0){
			map.put("settlementId", settlementId);
		}
		if(payTimeStart != null && payTimeStart.trim().length() > 0){
			map.put("payTimeStart", payTimeStart);
		}
		if(payTimeEnd != null && payTimeEnd.trim().length() > 0){
			map.put("payTimeEnd", payTimeEnd);
		}
		// 设置查询条件--团号
		if(groupId != null && groupId.trim().length() > 0){
			map.put("groupId", groupId);
		}
		// 设置查询条件--币种
		if(currency != null && currency.trim().length() > 0){
			map.put("currency", currency);
		}
		return ordSettlementPaymentService.getOrdSettlementPayments(map);
	}
	
}
