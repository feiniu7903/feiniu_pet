package com.lvmama.eplace.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.pass.SupplierRelateProduct;

@SuppressWarnings("unchecked")
public class SupplierRelateProductDAO extends BaseIbatisDAO {
	public List<Long> findSupplierRelateProductIds(Long productId) {
		List list = super.queryForList(
				"SUPPLIER_RELATE_PRODUCT.selectBySupplierId", productId);
		return list;
	}
	
	public List<SupplierRelateProduct> findSupplierRelateProductByMap(Map map) {
		List list = super.queryForList(
				"SUPPLIER_RELATE_PRODUCT.selectByFull", map);
		return list;
	}

	public void deleteSupplierRelateProduct(Long eplaceSupplierId) {
		super.delete(
				"SUPPLIER_RELATE_PRODUCT.deleteByPrimaryKey", eplaceSupplierId);
	}

	public void addSupplierRelateProduct(
			SupplierRelateProduct supplierRelateProduct) {
		super.insert(
				"SUPPLIER_RELATE_PRODUCT.insert", supplierRelateProduct);
	}
	/**
	 * 通过供应商用户id查询供应商产品信息
	 * @param userId
	 * @return
	 */
	public List<SupplierRelateProduct> selectSupplierProductByUserId(Long userId) {
		Map<String,Object> parm=new HashMap<String,Object>();
		parm.put("userId", userId);
		List list = super.queryForList(
				"SUPPLIER_RELATE_PRODUCT.selectSupplierProductByUserId", parm);
		return list;
	}
}