/**
 * 
 */
package com.lvmama.pet.lvmamacard.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.lvmamacard.StoredCardOutDetails;


/**
 * 储值卡出库明细
 * @author yifan
 *
 */
public class OutStoregeDetailsDAO extends BaseIbatisDAO {
	@SuppressWarnings("unchecked")
	public List<StoredCardOutDetails> queryByParamForOutStoregeDetails(Map<String, Object> param) {
 		return super.queryForList("STORED_CARD_OUT_DETAILS.queryByParamForOutStoregeDetails", param);
	}

	public void insertOutStoregeDetails(List<StoredCardOutDetails> list) {
		super.batchInsert("STORED_CARD_OUT_DETAILS.insert", list);
	}
	
	public void updateOutStoregeDetails(StoredCardOutDetails details) {
		super.update("STORED_CARD_OUT_DETAILS.update", details);
	}

	public void deleteOutStoregeDetails(String outCode) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("outCode", outCode);
		super.delete("STORED_CARD_OUT_DETAILS.delete", param);
	}
}