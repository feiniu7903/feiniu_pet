package com.lvmama.ebk.dao;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ebooking.EbkProdTimePrice;

/**
 * 数据访问对象实现类
 * @since 2013-09-24
 */
public class EbkProdTimePriceDAO extends BaseIbatisDAO {

    /**
     * 插入数据
     * @param ebkProdTimePriceDO
     * @return 插入数据的主键
     */
    public Long insertEbkProdTimePriceDO(EbkProdTimePrice ebkProdTimePriceDO) {
        Object TIME_PRICE_ID = super.insert("EBK_PROD_TIME_PRICE.insert", ebkProdTimePriceDO);
        return (Long) TIME_PRICE_ID;
    }
    
    public void insertBatch(List<EbkProdTimePrice> ebkProdTimePrices) throws SQLException{
    	super.getSqlMapClient().startBatch();
    	for(EbkProdTimePrice eptp:ebkProdTimePrices){
    		insertEbkProdTimePriceDO(eptp);
    	}
    	super.getSqlMapClient().executeBatch();
    }
    public void updateBatch(List<EbkProdTimePrice> ebkProdTimePrices) throws SQLException{
    	super.getSqlMapClient().startBatch();
    	for(EbkProdTimePrice eptp:ebkProdTimePrices){
    		updateEbkProdTimePriceDO(eptp);
    	}
    	super.getSqlMapClient().executeBatch();
    }
    /**
     * 统计记录数
     * @param ebkProdTimePriceDO
     * @return 查出的记录数
     */
    public Integer countEbkProdTimePriceDOByExample(EbkProdTimePrice ebkProdTimePriceDO) {
        Integer count = (Integer) super.queryForObject("EBK_PROD_TIME_PRICE.countByDOExample", ebkProdTimePriceDO);
        return count;
    }

    /**
     * 更新记录
     * @param ebkProdTimePriceDO
     * @return 受影响的行数
     */
    public Integer updateEbkProdTimePriceDO(EbkProdTimePrice ebkProdTimePriceDO) {
        int result = super.update("EBK_PROD_TIME_PRICE.update", ebkProdTimePriceDO);
        return result;
    }

    /**
     * 获取对象列表
     * @param ebkProdTimePriceDO
     * @return 对象列表
     */
    @SuppressWarnings("unchecked")
    public List<EbkProdTimePrice> findListByTerm(EbkProdTimePrice ebkProdTimePriceDO) {
        List<EbkProdTimePrice> list = super.queryForListForReport("EBK_PROD_TIME_PRICE.findListByDO", ebkProdTimePriceDO);
        return list;
    }
    
    public List<EbkProdTimePrice> findListByParams(Map<String,Object> parameters){
    	List<EbkProdTimePrice> list = super.queryForList("EBK_PROD_TIME_PRICE.findListByParams", parameters);
        return list;
    }
    public List<EbkProdTimePrice> queryMetaAndProdTimePrice(Map<String, Object> params) {
    	List<EbkProdTimePrice> list = super.queryForList("EBK_PROD_TIME_PRICE.queryMetaAndProdTimePrice", params);
        return list;
	}
    public List<EbkProdTimePrice> queryVirtualMetaAndProdTimePrice(Map<String, Object> params) {
    	List<EbkProdTimePrice> list = super.queryForList("EBK_PROD_TIME_PRICE.queryMetaTimePrice", params);
    	return list;
    }
	public List<EbkProdTimePrice> queryRelationProductTimePrice(Map<String, Object> params) {
    	List<EbkProdTimePrice> list = super.queryForList("EBK_PROD_TIME_PRICE.queryRelationProductTimePrice", params);
        return list;
	}
    /**
     * 查询时间价格按照时间升序排序
     * @param ebkProdTimePriceDO
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<EbkProdTimePrice> findListByTermOrderByDateASC(EbkProdTimePrice ebkProdTimePriceDO) {
        List<EbkProdTimePrice> list = super.queryForListForReport("EBK_PROD_TIME_PRICE.findListByTermOrderByDateASC", ebkProdTimePriceDO);
        return list;
    }
    

    /**
     * 根据主键获取ebkProdTimePriceDO
     * @param timePriceId
     * @return ebkProdTimePriceDO
     */
    public EbkProdTimePrice findEbkProdTimePriceDOByPrimaryKey(Long timePriceId) {
        EbkProdTimePrice ebkProdTimePriceDO = (EbkProdTimePrice) super.queryForObject("EBK_PROD_TIME_PRICE.findByPrimaryKey", timePriceId);
        return ebkProdTimePriceDO;
    }

    /**
     * 删除记录
     * @param timePriceId
     * @return 受影响的行数
     */
    public Integer deleteEbkProdTimePriceDOByPrimaryKey(Long timePriceId) {
        Integer rows = (Integer) super.delete("EBK_PROD_TIME_PRICE.deleteByPrimaryKey", timePriceId);
        return rows;
    }
    public int update(Map<String, Object> params) {
    	Integer rows = (Integer) super.update("EBK_PROD_TIME_PRICE.updateByParams", params);
	    return rows;
	}
	public int delete(Map<String, Object> params) {
		Integer rows = (Integer) super.delete("EBK_PROD_TIME_PRICE.deleteByParams", params);
	    return rows;
	}
}