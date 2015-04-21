package com.lvmama.ebk.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ebooking.EbkProdTarget;

/**
 * 数据访问对象实现类
 * @since 2013-09-24
 */
public class EbkProdTargetDAO extends BaseIbatisDAO {

    /**
     * 插入数据
     * @param ebkProdTargetDO
     * @return 插入数据的主键
     */
    public Long insertEbkProdTargetDO(EbkProdTarget ebkProdTargetDO) {
        Object PRODUCT_TARGET_ID = super.insert("EBK_PROD_TARGET.insert", ebkProdTargetDO);
        return (Long) PRODUCT_TARGET_ID;
    }

    /**
     * 统计记录数
     * @param ebkProdTargetDO
     * @return 查出的记录数
     */
    public Integer countEbkProdTargetDOByExample(EbkProdTarget ebkProdTargetDO) {
        Integer count = (Integer) super.queryForObject("EBK_PROD_TARGET.countByDOExample", ebkProdTargetDO);
        return count;
    }

    /**
     * 更新记录
     * @param ebkProdTargetDO
     * @return 受影响的行数
     */
    public Integer updateEbkProdTargetDO(EbkProdTarget ebkProdTargetDO) {
        int result = super.update("EBK_PROD_TARGET.update", ebkProdTargetDO);
        return result;
    }

    /**
     * 获取对象列表
     * @param ebkProdTargetDO
     * @return 对象列表
     */
    @SuppressWarnings("unchecked")
    public List<EbkProdTarget> findListByTerm(EbkProdTarget ebkProdTargetDO) {
        List<EbkProdTarget> list = super.queryForList("EBK_PROD_TARGET.findListByDO", ebkProdTargetDO);
        return list;
    }
    /**
     * 通过产品ID获取对象列表
     * @param productId
     * @return 对象列表
     */
    @SuppressWarnings("unchecked")
    public List<EbkProdTarget> findListByProductId(Long productId) {
    	List<EbkProdTarget> list = super.queryForList("EBK_PROD_TARGET.findListByProductId", productId);
    	return list;
    }

    /**
     * 根据主键获取ebkProdTargetDO
     * @param productTargetId
     * @return ebkProdTargetDO
     */
    public EbkProdTarget findEbkProdTargetDOByPrimaryKey(Long productTargetId) {
        EbkProdTarget ebkProdTargetDO = (EbkProdTarget) super.queryForObject("EBK_PROD_TARGET.findByPrimaryKey", productTargetId);
        return ebkProdTargetDO;
    }

    /**
     * 删除记录
     * @param productTargetId
     * @return 受影响的行数
     */
    public Integer deleteEbkProdTargetDOByPrimaryKey(Long productTargetId) {
        Integer rows = (Integer) super.delete("EBK_PROD_TARGET.deleteByPrimaryKey", productTargetId);
        return rows;
    }
    
    /**
   	 * 删除产品关联所有对象信息
   	 * 
   	 * 2013-10-10
   	 * 
   	 * @param productID
   	 * @return
   	 */
    public Integer deleteEbkProdTargetByProductID(Long productID) {
    	Integer rows = (Integer) super.delete("EBK_PROD_TARGET.deleteByProductID", productID);
    	return rows;
    }

}