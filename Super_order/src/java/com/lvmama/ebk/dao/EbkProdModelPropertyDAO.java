package com.lvmama.ebk.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ebooking.EbkProdModelProperty;

/**
 * 数据访问对象实现类
 * @since 2013-09-24
 */
public class EbkProdModelPropertyDAO extends BaseIbatisDAO {

    /**
     * 插入数据
     * @param ebkProdModelPropertyDO
     * @return 插入数据的主键
     */
    public void insertEbkProdModelPropertyDO(EbkProdModelProperty ebkProdModelPropertyDO) {
        super.insert("EBK_PROD_MODEL_PROPERTY.insert", ebkProdModelPropertyDO);
    }

    /**
     * 统计记录数
     * @param ebkProdModelPropertyDO
     * @return 查出的记录数
     */
    public Integer countEbkProdModelPropertyDOByExample(EbkProdModelProperty ebkProdModelPropertyDO) {
        Integer count = (Integer) super.queryForObject("EBK_PROD_MODEL_PROPERTY.countByDOExample", ebkProdModelPropertyDO);
        return count;
    }

    /**
     * 更新记录
     * @param ebkProdModelPropertyDO
     * @return 受影响的行数
     */
    public Integer updateEbkProdModelPropertyDO(EbkProdModelProperty ebkProdModelPropertyDO) {
        int result = super.update("EBK_PROD_MODEL_PROPERTY.update", ebkProdModelPropertyDO);
        return result;
    }

    /**
     * 获取对象列表
     * @param ebkProdModelPropertyDO
     * @return 对象列表
     */
    @SuppressWarnings("unchecked")
    public List<EbkProdModelProperty> findListByTerm(EbkProdModelProperty ebkProdModelPropertyDO) {
        List<EbkProdModelProperty> list = super.queryForList("EBK_PROD_MODEL_PROPERTY.findListByDO", ebkProdModelPropertyDO);
        return list;
    }
    
    /**
     * 通过产品获取对象列表
     * @param productId
     * @return 对象列表
     */
    @SuppressWarnings("unchecked")
    public List<EbkProdModelProperty> findListByProductId(Long productId)  {
    	List<EbkProdModelProperty> list = super.queryForList("EBK_PROD_MODEL_PROPERTY.findListByProductId", productId);
    	return list;
    }

    /**
     * 根据主键获取ebkProdModelPropertyDO
     * @param modelPropertyId
     * @return ebkProdModelPropertyDO
     */
    public EbkProdModelProperty findEbkProdModelPropertyDOByPrimaryKey(Integer modelPropertyId, Long productId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("modelPropertyId", modelPropertyId);
        params.put("productId", productId);
        EbkProdModelProperty ebkProdModelPropertyDO = (EbkProdModelProperty) super.queryForObject("EBK_PROD_MODEL_PROPERTY.findByPrimaryKey", params);
        return ebkProdModelPropertyDO;
    }

    /**
     * 根据产品ID删除记录
     * @param productId
     * @return 受影响的行数
     */
    public Integer deleteEbkProdModelPropertyByProductID(Long productId) {
    	int rows = (Integer) super.delete("EBK_PROD_MODEL_PROPERTY.deleteByProductID", productId);
    	return rows;
    }
    
    /**
     * 删除记录
     * @param modelPropertyId
     * @return 受影响的行数
     */
    public Integer deleteEbkProdModelPropertyDOByPrimaryKey(Integer modelPropertyId, Long productId) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put("modelPropertyId", modelPropertyId);
        params.put("productId", productId);
        int rows = (Integer) super.delete("EBK_PROD_MODEL_PROPERTY.deleteByPrimaryKey", params);
        return rows;
    }

}