package com.lvmama.ebk.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.ebooking.EbkProdProduct;
import com.lvmama.comm.pet.vo.Page;

/**
 * 数据访问对象实现类
 * @since 2013-09-24
 */
public class EbkProdProductDAO extends BaseIbatisDAO {

    /**
     * 插入数据
     * @param ebkProdProductDO
     * @return 插入数据的主键
     */
    public Long insertEbkProdProductDO(EbkProdProduct ebkProdProductDO) {
        Object EBK_PROD_PRODUCT_ID = super.insert("EBK_PROD_PRODUCT.insert", ebkProdProductDO);
        return (Long) EBK_PROD_PRODUCT_ID;
    }

    /**
     * 统计记录数
     * @param ebkProdProductDO
     * @return 查出的记录数
     */
    public Integer countEbkProdProductDOByExample(EbkProdProduct ebkProdProductDO) {
        Integer count = (Integer) super.queryForObject("EBK_PROD_PRODUCT.countByDOExample", ebkProdProductDO);
        return count;
    }

    /**
     * 更新记录
     * @param ebkProdProductDO
     * @return 受影响的行数
     */
    public Integer updateEbkProdProductDO(EbkProdProduct ebkProdProductDO) {
        int result = super.update("EBK_PROD_PRODUCT.update", ebkProdProductDO);
        return result;
    }

    /**
     * 获取对象列表
     * @param ebkProdProductDO
     * @return 对象列表
     */
    @SuppressWarnings("unchecked")
    public List<EbkProdProduct> findListByExample(Map<String, Object> parameters) {
        List<EbkProdProduct> list = super.queryForList("EBK_PROD_PRODUCT.findListByDO", parameters);
        return list;
    }

    /**
     * 根据主键获取ebkProdProductDO
     * @param ebkProdProductId
     * @return ebkProdProductDO
     */
    public EbkProdProduct findEbkProdProductDOByPrimaryKey(Long ebkProdProductId) {
        EbkProdProduct ebkProdProductDO = (EbkProdProduct) super.queryForObject("EBK_PROD_PRODUCT.findByPrimaryKey", ebkProdProductId);
        return ebkProdProductDO;
    }

    /**
     * 删除记录
     * @param ebkProdProductId
     * @return 受影响的行数
     */
    public Integer deleteEbkProdProductDOByPrimaryKey(Long ebkProdProductId) {
        Integer rows = (Integer) super.delete("EBK_PROD_PRODUCT.deleteByPrimaryKey", ebkProdProductId);
        return rows;
    }

    /**
     * 根据供应商ID，产品子类型条件统计EBK产品审核总数
     * @param supplierId
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<Map<String,Object>> queryAllCount(final Long supplierId,final String[] subProductTypes){
    	Map<String,Object> params = new HashMap<String,Object>();
    	params.put("supplierId", supplierId);
    	params.put("subProductTypes", subProductTypes);
    	return (List<Map<String,Object>>)super.queryForList("EBK_PROD_PRODUCT.queryAllCount", params);
    }
    
    @SuppressWarnings("unchecked")
    public Page<EbkProdProduct> query(Map<String, Object> parameters) {
		return super.queryForPage("EBK_PROD_PRODUCT.query", parameters);
	}
    
    /**
     * 根据条件统计产品数量,以产品状态分组
     * @param parameters
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Map<String,Object>> queryCountGroupByStatus(Map<String, Object> parameters){
    	 return (List<Map<String,Object>>)super.queryForList("EBK_PROD_PRODUCT.queryAllCount", parameters);
    }
    
}