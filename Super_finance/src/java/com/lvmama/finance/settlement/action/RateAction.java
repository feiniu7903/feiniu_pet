package com.lvmama.finance.settlement.action;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lvmama.finance.base.BaseAction;
import com.lvmama.finance.group.ibatis.po.FinExchangeRate;
import com.lvmama.finance.settlement.ibatis.po.ExchangeRate;
import com.lvmama.finance.settlement.service.RateSetService;

@Controller
@RequestMapping(value="/rateset/")
public class RateAction extends BaseAction {

	@Autowired
	private RateSetService rateSetService;
	
	/**
	 * 页面初始化，显示汇率设置页面
	 * @return
	 */
	@RequestMapping(value="/index", method = RequestMethod.GET)
	public String showRatePage(Model model){
		List<ExchangeRate> rateList = rateSetService.search();
		model.addAttribute("rateList", rateList);
		return "settlement/rateset/rateSet";
	}

	/**
	 * 新增或是修改汇率
	 */
	@RequestMapping(value="/doSaveOrUpdateRate")
	public void doSaveOrUpdateRate(@RequestParam("modifyRate") Double[] modifyRate, @RequestParam("foreignCurrency") String[] foreignCurrency,Model model){
		// 用来标识新增或是修改汇率时是否成功
		boolean flag = true;
		
 		if(modifyRate.length > 0  && foreignCurrency.length > 0 && modifyRate.length == foreignCurrency.length ){
 			for(int i=0; i<modifyRate.length; i++){
 				Double rate = modifyRate[i];
 				String currency = foreignCurrency[i];

 				// 根据币种查询修改前的汇率信息
 				FinExchangeRate finExchangeRate = rateSetService.queryByCurrency(currency);
 				
 				/**
 				 *  根据修改前的汇率判断该汇率是否修改过
 				 *  	null != finExchangeRate && finExchangeRate.getRate() != rate 表示有修改，执行更新
 				 */
 				if(null != finExchangeRate){
 					if(null == finExchangeRate.getRate()){
 						finExchangeRate.setRate(new Double(0));
 					}

 	 				if(null == rate){
 	 					rate = new Double(0);
 	 				}
 	 				
 					if(finExchangeRate.getRate() != rate && !finExchangeRate.getRate().equals(rate)){
 						// 重新设置汇率
 	 					finExchangeRate.setRate(rate);
 	 					// 执行更新
 	 					flag = rateSetService.updateRate(finExchangeRate);
 	 					// 如果返回值为false则表示出现异常，中断操作。
 	 					if(flag == false){
 	 						return;
 	 					}
 					}
 				} 
 				
 				/**
 				 * 执行新增
 				 */
 				else if(null == finExchangeRate && null != rate){
 					finExchangeRate = new FinExchangeRate();
 					// 币种
 					finExchangeRate.setForeignCurrency(currency);
 					// 修改时间
 					finExchangeRate.setCreateDate(new Date());
 					// 汇率
 					finExchangeRate.setRate(rate);
 					// 执行新增
 					flag = rateSetService.insertRate(finExchangeRate);
 					// 如果返回值为false则表示出现异常，中断操作。
 					if(flag == false){
 						return;
 					}
 				}
 			}
 		}else{
 			flag = false;
			if(flag == false){
				model.addAttribute(flag);
			}
 		}

		model.addAttribute(flag);
	}
	
}
