package com.lvmama.pet.money.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.money.StoredCard;
import com.lvmama.comm.pet.vo.Page;

/**
 * 
 * @author Libo Wang
 *
 */
public class StoredCardDAO extends BaseIbatisDAO {
	
	public Long insert(StoredCard card){
		return (Long)super.insert("STORED_CARD.insert", card);
	}
	
	
	public void update(StoredCard card){
		super.update("STORED_CARD.updateByPrimaryKey", card);
	}
	
	/**
	 * 根据卡得ID取卡的信息.
	 * @param storedCardId
	 * @return
	 */
	public StoredCard queryStoredCardById(long storedCardId){
		return (StoredCard) super.queryForObject(
				"STORED_CARD.queryStoredCardById", storedCardId);
	}
	
	public StoredCard lastStoredCard(Map<String, Object> param) {
		return (StoredCard)super.queryForObject("STORED_CARD.lastStoredCard", param);
	}
	
	/**
	 * 取符合查询条件的数量.
	 * @param parameter
	 * @return
	 */
	public Long selectByParamCount(Map<String, Object> param) {
		return (Long) super.queryForObject("STORED_CARD.selectByParamCount", param);
	}	
	/**
	 * 取符合查询条件的数据.
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<StoredCard> selectByParam(Map<String, Object> para){
		return super.queryForList("STORED_CARD.selectByParam", para);
	}
	
	/**
	 * 取符合查询条件的数据.
	 * @param parameter
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Page<StoredCard> selectByParam(Map<String, Object> para,Page page){
		Long totalResultSize = (Long) super.queryForObject("STORED_CARD.selectByParamCount", para);

		//分页查询
		page.setTotalResultSize(totalResultSize);				
		para.put("startRows", page.getStartRows());				
		para.put("endRows", page.getEndRows());

		page.setItems(super.queryForList("STORED_CARD.selectByParam", para));
		
		return page;
	}

	/**
	 * 根据卡号取相应的卡信息.
	 * 
	 * @param cardNo
	 * @return
	 */
	public StoredCard queryByCardNo(Map<String, Object> param) {
		List<StoredCard> list=super.queryForList("STORED_CARD.queryByParam", param);
		StoredCard card=new StoredCard();
		if(list!=null&&list.size()>0){
			card=list.get(0);
		}
		return card;
	}
	
	/**
	 * 同批次卡的数据.
	 * @param param
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<StoredCard> queryByParam(Map<String, Object> param){
		return super.queryForList("STORED_CARD.queryByParam", param);
	}
	
	
	
}
