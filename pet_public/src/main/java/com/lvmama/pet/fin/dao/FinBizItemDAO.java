package com.lvmama.pet.fin.dao;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Repository;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.fin.FinBizItem;
import com.lvmama.comm.pet.vo.Page;

/**
 * 数据访问对象实现类
 * @author taiqichao
 * @since 2014-02-24
 */
@Repository
public class FinBizItemDAO extends BaseIbatisDAO{

    /**
     * 插入数据
     * @param finBizItemDO
     * @return 插入数据的主键
     */
    public Long insertFinBizItemDO(FinBizItem finBizItemDO) {
        Object BIZ_ITEM_ID = super.insert("FIN_BIZ_ITEM.insert", finBizItemDO);
        return (Long) BIZ_ITEM_ID;
    }

    /**
     * 统计记录数
     * @param finBizItemDO
     * @return 查出的记录数
     */
    public Integer countFinBizItemDOByExample(FinBizItem finBizItemDO) {
        Integer count = (Integer) super.queryForObject("FIN_BIZ_ITEM.countByDOTerm", finBizItemDO);
        return count;
    }

    /**
     * 更新记录
     * @param finBizItemDO
     * @return 受影响的行数
     */
    public Integer updateFinBizItemDO(FinBizItem finBizItemDO) {
        int result = super.update("FIN_BIZ_ITEM.update", finBizItemDO);
        return result;
    }
    
    
    
    /**
     * 批量更新财务流水状态
     * @param param
     * @return
     */
    public Integer batchUpdateBizStatus(Map<String, Object> param) {
        int result = super.update("FIN_BIZ_ITEM.batchUpdateBizStatus", param);
        return result;
    }

    /**
     * 获取对象列表
     * @param finBizItemDO
     * @return 对象列表
     */
    @SuppressWarnings("unchecked")
    public List<FinBizItem> findListByTerm(FinBizItem finBizItemDO) {
        List<FinBizItem> list = super.queryForList("FIN_BIZ_ITEM.findListByDO", finBizItemDO);
        return list;
    }

    /**
     * 根据主键获取finBizItemDO
     * @param bizItemId
     * @return finBizItemDO
     */
    public FinBizItem findFinBizItemDOByPrimaryKey(Long bizItemId) {
    	FinBizItem finBizItemDO = (FinBizItem) super.queryForObject("FIN_BIZ_ITEM.findByPrimaryKey", bizItemId);
        return finBizItemDO;
    }

    /**
     * 删除记录
     * @param bizItemId
     * @return 受影响的行数
     */
    public Integer deleteFinBizItemDOByPrimaryKey(Long bizItemId) {
        Integer rows = (Integer) super.delete("FIN_BIZ_ITEM.deleteByPrimaryKey", bizItemId);
        return rows;
    }
    
    /**
     * 分页查询
     * @param parameters
     * @return
     */
    @SuppressWarnings("unchecked")
    public Page<FinBizItem> query(Map<String, Object> parameters) {
		return super.queryForPage("FIN_BIZ_ITEM.query", parameters);
	}
    
    /**
     * 查询对账流水记录(单次1000条)
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<FinBizItem> selectBizItemListForBatch(){
    	return super.queryForList("FIN_BIZ_ITEM.selectBizItemListForBatch",null);
    }
    
    /**
     * 根据查询参数计算总行数
     * @author lvhao
     * @param paramMap 查询参数
     * @return 总行数
     */
    public Long selectFinBizItemListByParasCount(Map<String,String> paramMap){
    	return (Long) super.queryForObject("FIN_BIZ_ITEM.selectFinBizItemListByParasCount",paramMap);
    }
    
    @SuppressWarnings("unchecked")
	public List<FinBizItem> selectFinBizItemListByParas(Map<String,String> paramMap){
    	return super.queryForList("FIN_BIZ_ITEM.selectFinBizItemListByParas",paramMap);
    }
    
    @SuppressWarnings("unchecked")
	public Map<String,BigDecimal> selectTransactionAmountByParamMap(Map<String, String> paramMap){
		return (Map<String,BigDecimal>) super.queryForObject("FIN_BIZ_ITEM.selectTransactionAmountByParamMap", paramMap);
	}

}