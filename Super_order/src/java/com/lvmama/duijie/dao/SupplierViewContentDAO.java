package com.lvmama.duijie.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.duijie.SupplierViewContent;

/**
 * 供应商产品内容DAO
 * @author yanzhirong
 */
public class SupplierViewContentDAO  extends BaseIbatisDAO{

	public void insert(SupplierViewContent content){
		super.insert("SUPPLIER_VIEW_CONTENT.insert",content);
	}
	
	public void update(SupplierViewContent content){
		super.update("SUPPLIER_VIEW_CONTENT.update", content);
	}
	
	public List<SupplierViewContent> selectSupplierViewContentByCondition(Map<String,Object> params){
		return (List<SupplierViewContent>)super.queryForList("SUPPLIER_VIEW_CONTENT.selectSupplierViewContentByCondition",params);
	}
}
