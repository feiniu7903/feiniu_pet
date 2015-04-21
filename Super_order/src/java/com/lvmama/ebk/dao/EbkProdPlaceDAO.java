package com.lvmama.ebk.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ebooking.EbkProdPlace;

/**
 * 数据访问对象实现类
 * @since 2013-09-24
 */
public class EbkProdPlaceDAO extends BaseIbatisDAO {

    /**
     * 插入数据
     * @param ebkProdPlaceDO
     * @return 插入数据的主键
     */
    public Long insertEbkProdPlaceDO(EbkProdPlace ebkProdPlaceDO) {
        Object PRODUCT_PLACE_ID = super.insert("EBK_PROD_PLACE.insert", ebkProdPlaceDO);
        return (Long) PRODUCT_PLACE_ID;
    }

    /**
     * 统计记录数
     * @param ebkProdPlaceDO
     * @return 查出的记录数
     */
    public Integer countEbkProdPlaceDOByExample(EbkProdPlace ebkProdPlaceDO) {
        Integer count = (Integer) super.queryForObject("EBK_PROD_PLACE.countByDOExample", ebkProdPlaceDO);
        return count;
    }

    /**
     * 更新记录
     * @param ebkProdPlaceDO
     * @return 受影响的行数
     */
    public Integer updateEbkProdPlaceDO(EbkProdPlace ebkProdPlaceDO) {
        int result = super.update("EBK_PROD_PLACE.update", ebkProdPlaceDO);
        return result;
    }

    /**
     * 获取对象列表
     * @param ebkProdPlaceDO
     * @return 对象列表
     */
    @SuppressWarnings("unchecked")
    public List<EbkProdPlace> findListByTerm(EbkProdPlace ebkProdPlaceDO) {
        List<EbkProdPlace> list = super.queryForList("EBK_PROD_PLACE.findListByDO", ebkProdPlaceDO);
        return list;
    }

    /**
     * 根据主键获取ebkProdPlaceDO
     * @param productPlaceId
     * @return ebkProdPlaceDO
     */
    public EbkProdPlace findEbkProdPlaceDOByPrimaryKey(Long productPlaceId) {
        EbkProdPlace ebkProdPlaceDO = (EbkProdPlace) super.queryForObject("EBK_PROD_PLACE.findByPrimaryKey", productPlaceId);
        return ebkProdPlaceDO;
    }

    /**
     * 删除记录
     * @param productPlaceId
     * @return 受影响的行数
     */
    public Integer deleteEbkProdPlaceDOByPrimaryKey(Long productPlaceId) {
        Integer rows = (Integer) super.delete("EBK_PROD_PLACE.deleteByPrimaryKey", productPlaceId);
        return rows;
    }
    
    public int deleteListByEbkProductId(final Long ebkProductId){
    	 Integer rows = (Integer) super.delete("EBK_PROD_PLACE.deleteListByEbkProductId", ebkProductId);
         return rows;
    }
}