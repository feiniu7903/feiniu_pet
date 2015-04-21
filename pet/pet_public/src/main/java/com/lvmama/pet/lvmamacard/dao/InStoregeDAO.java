/**
 * 
 */
package com.lvmama.pet.lvmamacard.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.lvmamacard.StoredCardIn;
import com.lvmama.comm.pet.vo.lvmamacard.LvmamaCardStatistics;
import com.lvmama.comm.vo.Constant;


/**
 * 储值卡入库dao
 * @author nixianjun
 *
 */
public class InStoregeDAO extends BaseIbatisDAO {
	
	public InStoregeDAO()
	{
		super();
	}	

	public Long countByParamForInStorege(Map param) {
 		return (Long) super.queryForObject("STORED_CARD_IN.countByParamForInStorege", param);
	}

	public List<StoredCardIn> queryByParamForInStorege(Map param) {
 		return super.queryForList("STORED_CARD_IN.queryByParamForInStorege", param);
	}

	public String getIncodeForInStorege(Integer amount) {
		String cardPreCode= Constant.CARD_AMOUNT.getCode(amount.toString())+"________";
  		return (String) super.queryForObject("STORED_CARD_IN.getIncodeForInStorege",cardPreCode);
	}

	public void insertStoredCardInForInStorege(StoredCardIn storedCardIn) {
		super.insert("STORED_CARD_IN.insert", storedCardIn);
	}

	public void updateByMap(Map param) {
		super.update("STORED_CARD_IN.updateByParam",param);
	}

	public LvmamaCardStatistics getLvmamaCardStatisticsByInCode(String inCode) {
 		return (LvmamaCardStatistics) super.queryForObject("STORED_CARD_IN.getLvmamaCardStatisticsByInCode",inCode);
	}

	/**
	 * 统计批次卡出库情况
	 * @param param
	 * @return
	 * @author nixianjun 2013-12-3
	 */
	public long countByParamForInStoreAndOutStore(Map param) {
 		return (Long) super.queryForObject("STORED_CARD_IN.countByParamForInStoreAndOutStore", param);
	}

	/**
	 * 统计出库卡批次情况
	 * @param param
	 * @return
	 * @author nixianjun 2013-12-3
	 */
	
	public List<LvmamaCardStatistics> queryByParamForInStoreAndOutStore(
			Map param) {
 		return super.queryForList("STORED_CARD_IN.queryByParamForInStoreAndOutStore", param);
	}

	public Long getCardCountByParamForInStorege(Map param) {
 		return (Long) super.queryForObject("STORED_CARD_IN.getCardCountByParamForInStorege", param);
	}


}
