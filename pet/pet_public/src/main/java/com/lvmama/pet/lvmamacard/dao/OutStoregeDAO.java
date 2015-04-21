/**
 * 
 */
package com.lvmama.pet.lvmamacard.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.lvmamacard.StoredCardOut;


/**
 * 储值卡出库
 * @author yifan
 *
 */
public class OutStoregeDAO extends BaseIbatisDAO {
	public Long countByParamForOutStorege(Map<String, Object> param) {
 		return (Long) super.queryForObject("STORED_CARD_OUT.countByParamForOutStorege", param);
	}

	@SuppressWarnings("unchecked")
	public List<StoredCardOut> queryByParamForOutStorege(Map<String, Object> param) {
 		return super.queryForList("STORED_CARD_OUT.queryByParamForOutStorege", param);
	}

	public void insertStoredCardOutForOutStorege(StoredCardOut storedCardOut) {
		super.insert("STORED_CARD_OUT.insert", storedCardOut);
	}

	public void updateStoredCardOutForOutStorege(StoredCardOut storedCardOut) {
		super.update("STORED_CARD_OUT.update", storedCardOut);
	}
	
	public void deleteStoredCardOutForOutStorege(StoredCardOut storedCardOut) {
		super.delete("STORED_CARD_OUT.delete", storedCardOut);
	}
	
	public String getIncodeForOutStorege() {
  		return (String) super.queryForObject("STORED_CARD_OUT.getOutcodeForOutStorege");
	}
	
	public StoredCardOut queryOutStoregeSum(Map<String, Object> param){
		return (StoredCardOut) super.queryForObject("STORED_CARD_OUT.queryOutStoregeSum",param);
	}
	
	@SuppressWarnings("unchecked")
	public List<StoredCardOut> queryOutStoregeExcel(Map<String, Object> param){
		return super.queryForList("STORED_CARD_OUT.queryOutStoregeExcel",param);
	}
}
