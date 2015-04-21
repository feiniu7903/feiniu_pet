package com.lvmama.pet.money.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.money.StoredCard;
import com.lvmama.comm.pet.po.money.StoredCardStock;
import com.lvmama.comm.pet.vo.Page;

/**
 * 
 * @author Libo Wang
 */
public class StoredCardStockDAO extends BaseIbatisDAO {
	/**
     * 查询符合条件的储值卡,为生成入/出库单做准备.
     * @param maps 
     * @return 储值卡集合.
     */
	@SuppressWarnings("unchecked")
	public List<StoredCard> selectCardByParamForCreateStock(Map<String, Object> maps) {
		return (List<StoredCard>)super.queryForList("STORED_CARD.selectCardByParamForCreateStock",maps);
	}
	/**
	 * 插入一条入/出库单记录.
	 * @param stock 入/出库单对象.
	 * @return 插入条数.
	 */
	public Long insert(StoredCardStock stock) {
		return (Long) super.insert("STORED_CARD_STOCK.insert", stock);
	}
	/**
	 * 查询入/出库单数目.
	 * @param maps
	 * @return 数目.
	 */
	public Long selectCardStockCountByParameters(Map<String, Object> maps) {
		return (Long) super.queryForObject("STORED_CARD_STOCK.selectCardStockCountByParameters",
				maps);
	}
	/**
	 * 查询入/出库单集合.
	 * @param maps
	 * @return 入/出库单集合
	 */
	@SuppressWarnings("unchecked")
	public List<StoredCardStock> selectCardStockByParameters(Map<String, Object> maps,Page page) {
		Long totalResultSize = (Long) super.queryForObject("STORED_CARD_STOCK.selectCardStockCountByParameters", maps);

		//分页查询
		page.setTotalResultSize(totalResultSize);
		maps.put("startRows", page.getStartRows());	
		maps.put("endRows", page.getEndRows());
		return (List<StoredCardStock>) super.queryForList(
				"STORED_CARD_STOCK.selectCardStockByParameters", maps);
	}
	/**
	 * 查询单个入/出库单.
	 * @param stockId 入/出库单Id
	 * @return 入/出库单.
	 */
	public StoredCardStock selectCardStockByStockId(Long stockId) {
		return (StoredCardStock)super.queryForObject("STORED_CARD_STOCK.selectCardStockByStockId", stockId);
	}
	/**
     * 修改某条入/出库单记录.
     * @param stock 入/出库单对象.
     */
	public void update(StoredCardStock stock) {
		super.update("STORED_CARD_STOCK.updateByPrimaryKey", stock);
	}
}
