package com.lvmama.ebk.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ebooking.EbkProdContent;

/**
 * 数据访问对象实现类
 * @since 2013-09-24
 */
public class EbkProdContentDAO extends BaseIbatisDAO {

    /**
     * 插入数据
     * @param ebkProdContentDO
     * @return 插入数据的主键
     */
    public Long insertEbkProdContentDO(EbkProdContent ebkProdContentDO) {
        Object CONTENT_ID = super.insert("EBK_PROD_CONTENT.insert", ebkProdContentDO);
        return (Long) CONTENT_ID;
    }

    public Long insertEbkProdContentCopy(EbkProdContent ebkProdContentDO) {
        Object CONTENT_ID = super.insert("EBK_PROD_CONTENT.insertCopy", ebkProdContentDO);
        return (Long) CONTENT_ID;
    }
    
    /**
     * 统计记录数
     * @param ebkProdContentDO
     * @return 查出的记录数
     */
    public Integer countEbkProdContentDOByExample(EbkProdContent ebkProdContentDO) {
        Integer count = (Integer) super.queryForObject("EBK_PROD_CONTENT.countByDOExample", ebkProdContentDO);
        return count;
    }

    /**
     * 更新记录
     * @param ebkProdContentDO
     * @return 受影响的行数
     */
    public Integer updateEbkProdContentDO(EbkProdContent ebkProdContentDO) {
        int result = super.update("EBK_PROD_CONTENT.update", ebkProdContentDO);
        return result;
    }

    /**
     * 获取对象列表
     * @param ebkProdContentDO
     * @return 对象列表
     */
    @SuppressWarnings("unchecked")
    public List<EbkProdContent> findListByTerm(EbkProdContent ebkProdContentDO) {
        List<EbkProdContent> list = super.queryForList("EBK_PROD_CONTENT.findListByDO", ebkProdContentDO);
        return list;
    }
    
    /**
     * 获取对象列表
     * @param ebkProdContentDO
     * @return 对象列表
     */
    @SuppressWarnings("unchecked")
    public List<EbkProdContent> findListByProductId(Long productId) {
    	List<EbkProdContent> list = super.queryForList("EBK_PROD_CONTENT.findListByProductId", productId);
    	return list;
    }

    /**
     * 根据主键获取ebkProdContentDO
     * @param contentId
     * @return ebkProdContentDO
     */
    public EbkProdContent findEbkProdContentDOByPrimaryKey(Long contentId) {
        EbkProdContent ebkProdContentDO = (EbkProdContent) super.queryForObject("EBK_PROD_CONTENT.findByPrimaryKey", contentId);
        return ebkProdContentDO;
    }
    
    /**
     * 根据条件获取对象列表
     * @param contentId
     * @return ebkProdContentDO
     */
	@SuppressWarnings("unchecked")
    public List<EbkProdContent> findEbkProdContentDOByIdAndType(Map<String,Object> params) {
    	return super.queryForList("EBK_PROD_CONTENT.findByByParams", params);
    }

    /**
     * 删除记录
     * @param contentId
     * @return 受影响的行数
     */
    public Integer deleteEbkProdContentDOByPrimaryKey(Long contentId) {
        Integer rows = (Integer) super.delete("EBK_PROD_CONTENT.deleteByPrimaryKey", contentId);
        return rows;
    }
    
}