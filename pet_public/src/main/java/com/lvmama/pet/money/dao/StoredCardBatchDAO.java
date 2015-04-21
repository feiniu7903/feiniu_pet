package com.lvmama.pet.money.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.money.StoredCardBatch;
import com.lvmama.comm.pet.vo.Page;

/**
 * 
 * @author Libo Wang
 *
 */
public class StoredCardBatchDAO extends BaseIbatisDAO {

	public Long insert(StoredCardBatch batch){
		return (Long)super.insert("STORED_CARD_BATCH.insert", batch);
	}
	
	public void update(StoredCardBatch batch){
		super.update("STORED_CARD_BATCH.updateByPrimaryKey", batch);
	}
	
	/**
	 * 根据时间和面额查询CardBatch的记录数.
	 * @param orderId
	 * @return
	 */
	public Long selectBatchCount(String batchNo) {		
		Map<String,Object> map=new HashMap<String, Object>();
		map.put("batchNo", batchNo);
		return (Long)super.queryForObject("STORED_CARD_BATCH.selectBatchCount",map);
	}
	/**
	 * 批次列表分页.
	 * @param parameter
	 * @return
	 */
	public Page<StoredCardBatch> selectByParam(Map<String,Object> para,Page page){
		Long totalResultSize = (Long) super.queryForObject("STORED_CARD_BATCH.selectByParamCount", para);

		//分页查询
		page.setTotalResultSize(totalResultSize);
		para.put("startRows", page.getStartRows());
		para.put("endRows", page.getEndRows());
		page.setItems(super.queryForList("STORED_CARD_BATCH.selectByParam", para));
		return page;
	}
	
	/**
	 * 取批次数量.
	 * @param parameter
	 * @return
	 */
	public Long selectByParamCount(final Map<String, Object> parameter) {
		return (Long) super.queryForObject("STORED_CARD_BATCH.selectByParamCount", parameter);
	}
	
	/**
	 * 批次列表.
	 * @param parameter
	 * @return
	 */
	public List<StoredCardBatch> queryByParam(Map<String,Object> parameter){
		return super.queryForList("STORED_CARD_BATCH.queryByParam", parameter);
	}
	
}
