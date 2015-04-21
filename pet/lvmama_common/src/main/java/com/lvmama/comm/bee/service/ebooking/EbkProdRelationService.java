package com.lvmama.comm.bee.service.ebooking;

import java.util.List;

import com.lvmama.comm.bee.po.ebooking.EbkProdRelation;

public interface EbkProdRelationService {
	/**
     * 插入数据
     * @param EbkProdRelationDO
     * @return 插入数据的主键
     */
    public Long insertEbkProdRelationDO(EbkProdRelation EbkProdRelationDO);

    /**
     * 统计记录数
     * @param EbkProdRelationDO
     * @return 查出的记录数
     */
    public Integer countEbkProdRelationDOByExample(EbkProdRelation EbkProdRelationDO);

    /**
     * 获取对象列表
     * @param EbkProdRelationDO
     * @return 对象列表
     */
    public List<EbkProdRelation> findListByTerm(EbkProdRelation EbkProdRelationDO) ;

    /**
     * 根据主键获取EbkProdRelationDO
     * @param productPlaceId
     * @return EbkProdRelationDO
     */
    public EbkProdRelation findEbkProdRelationDOByPrimaryKey(Long ebkProdRelationId);

    /**
     * 删除记录
     * @param productPlaceId
     * @return 受影响的行数
     */
    public Integer deleteEbkProdRelationDOByPrimaryKey(Long ebkProdRelationId);
}
