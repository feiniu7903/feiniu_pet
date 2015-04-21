package com.lvmama.pet.sweb.lvmamacard;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.service.lvmamacard.LvmamacardService;
import com.lvmama.comm.pet.vo.lvmamacard.LvmamaCardStatistics;

@Results({
	
    @Result(name="input",location="/WEB-INF/pages/back/lvmamacard/inStorageCard_viewpass.jsp")
})
public class ViewPassCardAction extends CardBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1437634L;
	
	private String inCode;
	private String cardNo;
	private String outNo;
	private String outSaleComp;
	private String outSalePerson;
	private Integer amount;
	@Autowired
	private LvmamacardService lvmamacardService;
	private LvmamaCardStatistics lvmamaCardStatistics;

	@Action("/inStorageCard/initviewpass")
	public String initviewpass(){
		
		Map param=new HashMap();
		if(StringUtils.isBlank(inCode)||null==amount){
			return "error";
		}else{
			if(StringUtils.isNotBlank(inCode)){
				param.put("inCode", inCode);
			}
			if( null!=amount){
				param.put("amount", amount);
			}
			if(StringUtils.isNotBlank(cardNo)){
				param.put("cardNo", cardNo);
			}
			if(StringUtils.isNotBlank(outNo)){
				param.put("outCode", outNo);
			}
			if(StringUtils.isNotBlank(outSaleComp)){
				param.put("saleToCompany", outSaleComp);
			}
			if(StringUtils.isNotBlank(outSalePerson)){
				param.put("salePerson", outSalePerson);
			}
		    pagination=initPage();
		    pagination.setCurrentPage(pagination.getCurrentPage());
			pagination.setTotalResultSize(lvmamacardService.countByParamForInStoreAndOutStore(param));
			pagination.setPageSize(10L);
			if(pagination.getTotalResultSize()>0){
				 param.put("start", pagination.getStartRows());
				 param.put("end", pagination.getEndRows());
 				 pagination.setAllItems(lvmamacardService.queryByParamForInStoreAndOutStore(param));
			}
			pagination.buildUrl(getRequest()); 
			lvmamaCardStatistics=lvmamacardService.getLvmamaCardStatisticsByInCode(inCode);
		}
		return "input";
	}
	
	public String getInCode() {
		return inCode;
	}
	public void setInCode(String inCode) {
		this.inCode = inCode;
	}
	public String getCardNo() {
		return cardNo;
	}
	public void setCardNo(String cardNo) {
		this.cardNo = cardNo;
	}
	public String getOutNo() {
		return outNo;
	}
	public void setOutNo(String outNo) {
		this.outNo = outNo;
	}
	public String getOutSaleComp() {
		return outSaleComp;
	}
	public void setOutSaleComp(String outSaleComp) {
		this.outSaleComp = outSaleComp;
	}
	public String getOutSalePerson() {
		return outSalePerson;
	}
	public void setOutSalePerson(String outSalePerson) {
		this.outSalePerson = outSalePerson;
	}

	public LvmamaCardStatistics getLvmamaCardStatistics() {
		return lvmamaCardStatistics;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

}
