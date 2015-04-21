package com.lvmama.ebk.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ebooking.EbkProdRelation;

public class EbkProdRelationDAO extends BaseIbatisDAO {

	/**
     * 插入数据
     * @param EbkProdRelationDO
     * @return 插入数据的主键
     */
    public Long insertEbkProdRelationDO(EbkProdRelation EbkProdRelationDO) {
        Object PRODUCT_PLACE_ID = super.insert("EBK_PROD_RELATION.insert", EbkProdRelationDO);
        return (Long) PRODUCT_PLACE_ID;
    }

    /**
     * 统计记录数
     * @param EbkProdRelationDO
     * @return 查出的记录数
     */
    public Integer countEbkProdRelationDOByExample(EbkProdRelation EbkProdRelationDO) {
        Integer count = (Integer) super.queryForObject("EBK_PROD_RELATION.countByDOExample", EbkProdRelationDO);
        return count;
    }

    /**
     * 获取对象列表
     * @param EbkProdRelationDO
     * @return 对象列表
     */
    @SuppressWarnings("unchecked")
    public List<EbkProdRelation> findListByTerm(EbkProdRelation EbkProdRelationDO) {
        List<EbkProdRelation> list = super.queryForList("EBK_PROD_RELATION.findListByDO", EbkProdRelationDO);
        return list;
    }

    /**
     * 根据主键获取EbkProdRelationDO
     * @param productPlaceId
     * @return EbkProdRelationDO
     */
    public EbkProdRelation findEbkProdRelationDOByPrimaryKey(Long ebkProdRelationId) {
        EbkProdRelation EbkProdRelationDO = (EbkProdRelation) super.queryForObject("EBK_PROD_RELATION.findByPrimaryKey", ebkProdRelationId);
        return EbkProdRelationDO;
    }

    /**
     * 删除记录
     * @param productPlaceId
     * @return 受影响的行数
     */
    public Integer deleteEbkProdRelationDOByPrimaryKey(Long ebkProdRelationId) {
        Integer rows = (Integer) super.delete("EBK_PROD_RELATION.deleteByPrimaryKey", ebkProdRelationId);
        return rows;
    }
}
