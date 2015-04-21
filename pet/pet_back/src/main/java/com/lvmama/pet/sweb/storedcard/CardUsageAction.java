package com.lvmama.pet.sweb.storedcard;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.pet.po.money.StoredCardUsage;
import com.lvmama.comm.pet.service.money.StoredCardService;

/**
 * 
 * @author liwenzhan
 *
 */
@Results({
	@Result(name = "card_usage", location = "/WEB-INF/pages/back/stored/card_usage.jsp")
})
public class CardUsageAction extends BackBaseAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7870714363369757648L;
    /**
     * 储值卡ID.
     */
	private Long cardId;
	/**
	 * 消费记录LIST.
	 */
	private List<StoredCardUsage> usageList;
	/**
	 * 储值卡Service.
	 */
	private StoredCardService storedCardService;
	
	/**
	 * 储值卡的消费记录.
	 * @return
	 */
	@Action("/usage/showCardUsage")
	public String showCardUsage() { 
		Map<String,Object> param=buildParameter();
		usageList=storedCardService.queryUsageByParam(param);
         return "card_usage";
	}
	
	
	 /**
     * 包装查询条件.
     * @return
     */
    private Map<String,Object> buildParameter(){
		Map<String,Object> map=new HashMap<String, Object>();
		if (this.cardId!=null&&this.cardId>0) {
			map.put("cardId", cardId);
		}
		return map;
	}


	public Long getCardId() {
		return cardId;
	}
	public void setCardId(Long cardId) {
		this.cardId = cardId;
	}
	public List<StoredCardUsage> getUsageList() {
		return usageList;
	}
	public void setUsageList(List<StoredCardUsage> usageList) {
		this.usageList = usageList;
	}
	public StoredCardService getStoredCardService() {
		return storedCardService;
	}
	public void setStoredCardService(StoredCardService storedCardService) {
		this.storedCardService = storedCardService;
	}
	
}
