package com.lvmama.prd.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.Assert;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ProdProductBranchItem;

public class ProdProductBranchItemDAO extends BaseIbatisDAO {

    public ProdProductBranchItemDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long branchItemId) {
        ProdProductBranchItem key = new ProdProductBranchItem();
        key.setBranchItemId(branchItemId);
        int rows = super.delete("PROD_PRODUCT_BRANCH_ITEM.deleteByPrimaryKey", key);
        return rows;
    }

    public Long insert(ProdProductBranchItem record) {
        Long pk=(Long)super.insert("PROD_PRODUCT_BRANCH_ITEM.insert", record);
        return pk;
    }

    public void insertSelective(ProdProductBranchItem record) {
        super.insert("PROD_PRODUCT_BRANCH_ITEM.insertSelective", record);
    }

    public ProdProductBranchItem selectByPrimaryKey(Long branchItemId) {
        ProdProductBranchItem key = new ProdProductBranchItem();
        key.setBranchItemId(branchItemId);
        ProdProductBranchItem record = (ProdProductBranchItem) super.queryForObject("PROD_PRODUCT_BRANCH_ITEM.selectByPrimaryKey", key);
        return record;
    }
 
    public List<ProdProductBranchItem> selectByParam(Map<String,Object> param){
    	Assert.notEmpty(param);
    	return super.queryForList("PROD_PRODUCT_BRANCH_ITEM.selectByParam",param);
    }
    
	@SuppressWarnings("unchecked")
	public List<ProdProductBranchItem> selectBranchItemByProdBranchId(Long prodBranchId){
    	return super.queryForList("PROD_PRODUCT_BRANCH_ITEM.selectProductBranchItemByPbid",prodBranchId);
    }

    public int updateByPrimaryKeySelective(ProdProductBranchItem record) {
        int rows = super.update("PROD_PRODUCT_BRANCH_ITEM.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ProdProductBranchItem record) {
        int rows = super.update("PROD_PRODUCT_BRANCH_ITEM.updateByPrimaryKey", record);
        return rows;
    }
    
    /**
     * 用采购产品类别ID查询打包的所有的销售类别，去掉重复.
     * 
     * @param metaBranchId
     * @return
     */
    public List<ProdProductBranchItem> selectUniqueBranchIdByMeta(Long metaBranchId){
    	ProdProductBranchItem record=new ProdProductBranchItem();
    	record.setMetaBranchId(metaBranchId);
    	return super.queryForList("PROD_PRODUCT_BRANCH_ITEM.selectUniqueBranchIdByMeta",record);
    }
    
    public List<ProdProductBranchItem> selectProductBranchItemByBranchId(Long prodBranchId){
    	return super.queryForList("PROD_PRODUCT_BRANCH_ITEM.selectProductBranchItemByPbid",prodBranchId);
    }
    
    /**
     * 查询一个类别的打包项数.
     * @param prodBranchId
     * @return
     */
    public Long countProductItem(Long prodBranchId){
    	Map<String,Object> map=new HashMap<String, Object>();
    	map.put("prodBranchId", prodBranchId);
    	return (Long)super.queryForObject("PROD_PRODUCT_BRANCH_ITEM.countProductItem",prodBranchId);
    }
    /**
     * 查询一个销售产品打包的项数
     * @param productId
     * @return
     */
    public Long countProductItemByProductId(Long productId){
    	return (Long)super.queryForObject("PROD_PRODUCT_BRANCH_ITEM.countProductItemByProductId",productId);
    }
    
    public List<ProdProductBranchItem> selectItemsByMetaProductId(Long metaProductId) {
    	return super.queryForList("PROD_PRODUCT_BRANCH_ITEM.selectItemsByMetaProductId", metaProductId);
    }
}