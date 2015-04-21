package com.lvmama.prd.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ProductGroup;

public class ProductGroupDAO extends BaseIbatisDAO {

    public ProductGroupDAO() {
        super();
    }

    public int deleteByPrimaryKey(Long groupId) {
        ProductGroup key = new ProductGroup();
        key.setGroupId(groupId);
        int rows = super.delete("PRODUCT_GROUP.deleteByPrimaryKey", key);
        return rows;
    }

    public void insert(ProductGroup record) {
        super.insert("PRODUCT_GROUP.insert", record);
    }

    public void insertSelective(ProductGroup record) {
        super.insert("PRODUCT_GROUP.insertSelective", record);
    }

    public ProductGroup selectByPrimaryKey(Long groupId) {
        ProductGroup key = new ProductGroup();
        key.setGroupId(groupId);
        ProductGroup record = (ProductGroup) super.queryForObject("PRODUCT_GROUP.selectByPrimaryKey", key);
        return record;
    }

    public int updateByPrimaryKeySelective(ProductGroup record) {
        int rows = super.update("PRODUCT_GROUP.updateByPrimaryKeySelective", record);
        return rows;
    }

    public int updateByPrimaryKey(ProductGroup record) {
        int rows = super.update("PRODUCT_GROUP.updateByPrimaryKey", record);
        return rows;
    }
    
    public List<ProductGroup> selectAll() {
    	return super.queryForList("PRODUCT_GROUP.selectAll");
    }
    public List<ProductGroup> selectGroup(String typeName){
    	return super.queryForList("PRODUCT_GROUP.selectByTypeName",typeName);
    	
    }
    
}