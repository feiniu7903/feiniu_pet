package com.lvmama.pet.lvmamacard.dao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.lvmamacard.LvmamaStoredCard;
import com.lvmama.comm.pet.po.money.StoredCard;
import com.lvmama.comm.vo.Constant;

public class LvmamaStoredCardDao extends BaseIbatisDAO {

	public String getLastCardNoByAmountForLvmamaStoredCard(Integer amount) {
		String precode=Constant.CARD_AMOUNT.getCardPreCode(amount.toString())+"________";
 		return (String) super.queryForObject("LvmamaStoredCard.getLastCardNoByAmountForLvmamaStoredCard", precode);
	}

	public void batchinsertLvmamaStoredCardForLvmamaStoredCard(
			List<LvmamaStoredCard> list) {
 		super.batchInsert("LvmamaStoredCard.batchinsert", list);
	}

	public void inserinsertLvmamaStoredCardt(LvmamaStoredCard lvmamastoredcard) {
		super.insert("LvmamaStoredCard.batchinsert", lvmamastoredcard);
	}

	public long countByParamForLvmamaStoredCard(Map param) {
		return (Long) super.queryForObject("LvmamaStoredCard.countByParamForLvmamaStoredCard", param);
	}

	public List<LvmamaStoredCard> queryByParamForLvmamaStoredCard(Map param) {
		if( param.containsKey("FORREPORT")&&param.get("FORREPORT").equals("true")){
			return super.queryForListForReport("LvmamaStoredCard.queryByParamForLvmamaStoredCard", param);
		}else{
			return super.queryForList("LvmamaStoredCard.queryByParamForLvmamaStoredCard", param);
		}
	}

	public LvmamaStoredCard getOneStoreCardByCardNo(String cardNo) {
		// TODO Auto-generated method stub
		return (LvmamaStoredCard) super.queryForObject("LvmamaStoredCard.getOneStoreCardByCardNo", cardNo);
	}

	public void updateByStoredCard(LvmamaStoredCard lvmamaStoredCard) {
		super.update("LvmamaStoredCard.updateByStoredCard", lvmamaStoredCard);
	}
	
	public void updateByParam(Map map) {
		 super.update("LvmamaStoredCard.updateByParam", map);
	}

	public void updateOutStoredCard(Map<String, Object> param){
		super.update("LvmamaStoredCard.outStoredUpdateByParam",param);
	}
	
	@SuppressWarnings("unchecked")
	public List<LvmamaStoredCard> queryOutStoredBeginNoAndEndNo(Map<String, Object> param) {
		return super.queryForList("LvmamaStoredCard.outStoredByBeginNoAndEndNo",param);
	}
	
	@SuppressWarnings("unchecked")
	public List<LvmamaStoredCard> queryOutStoredCardStatusByOutCode(Map<String, Object> param){
		return super.queryForList("LvmamaStoredCard.outStoredCardStatusByOutCode",param);
	}
	
	/**
	 * 根据卡得ID取卡的信息.
	 * @param storedCardId
	 * @return
	 * @author zhangjie 2013-12-11
	 */
	public LvmamaStoredCard queryStoredCardById(long storedCardId){
		return (LvmamaStoredCard) super.queryForObject(
				"LvmamaStoredCard.queryStoredCardById", storedCardId);
	}

	public List<LvmamaStoredCard> getOverTimeStoredCard(Date paramDate) {
		   Map param=new HashMap<String, Object>();
		   param.put("paramDate", paramDate);
 		   return super.queryForListForReport("LvmamaStoredCard.getOverTimeStoredCard", param);
	}
	
	public List<LvmamaStoredCard> queryUsedLvmamaStoredCardByUserId(Map param) {
		return super.queryForList("LvmamaStoredCard.queryUsedLvmamaStoredCardByUserId", param);
	}
}
