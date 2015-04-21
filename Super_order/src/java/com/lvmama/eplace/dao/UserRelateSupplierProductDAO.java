package com.lvmama.eplace.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.pass.UserRelateSupplierProduct;

@SuppressWarnings("unchecked")
public class UserRelateSupplierProductDAO extends BaseIbatisDAO {

	public void deleteUserRelateSupplierProduct(Long passportUserId) {
		 super.delete(
				"USER_RELATE_SUPPLIER_PRODUCT.deleteByPrimaryKey", passportUserId);
	}

	
	public Long addUserRelateSupplierProduct(UserRelateSupplierProduct userRelateSupplierProduct) {
		Long supplyId = (Long) super.insert(
				"USER_RELATE_SUPPLIER_PRODUCT.insert", userRelateSupplierProduct);
		return supplyId;
	}

	public Long findUserRelateMenuByMapCount(Map param) {
		Long count = 0l;
		count = (Long) super.queryForObject(
				"USER_RELATE_SUPPLIER_PRODUCT.selectFullCount", param);
		return count;
	}

	public List<UserRelateSupplierProduct> findUserRelateSupplierProductByMap(Map param) {
		if (param.get("_startRow") == null) {
			param.put("_startRow", 0);
		}
		if (param.get("_endRow") == null) {
			param.put("_endRow", 20);
		}
		return super.queryForList(
				"USER_RELATE_SUPPLIER_PRODUCT.selectFull", param);
	}
	/**
	 * 查询供应商用户对应得采购产品信息
	 * @param userId
	 * @return
	 */
	public List<UserRelateSupplierProduct> getSupplierRelateProductByUserId(Long userId) {
		List<UserRelateSupplierProduct> supplierRelateProducts =super.queryForList(
				"USER_RELATE_SUPPLIER_PRODUCT.selectSupplierUser", userId);
		return supplierRelateProducts;
	}
	/**
	 * 通过用户编号查询到集合.展示其详情的所属履行对象.
	 * @param userId
	 * @return
	 */
	public List<UserRelateSupplierProduct> getSupplierUserListByTargetId(Long userId) {
		return super.queryForList("USER_RELATE_SUPPLIER_PRODUCT.selectSupplierUserForTargetId",userId);
	}
	
	/**
	 * 查询供应商用户
	 * @param userId
	 * @return
	 */
	public UserRelateSupplierProduct getSupplierUserForTargetId(Long userId) {
		List<UserRelateSupplierProduct> list = super.queryForList(
				"USER_RELATE_SUPPLIER_PRODUCT.selectSupplierUserForTargetId", userId);
		if (list!=null && list.size()>0) {
			UserRelateSupplierProduct userRelateSupplierProduct = list.get(0);
			return userRelateSupplierProduct;
		}
		return null;
	}
	
	/**
	 * 查询供应商用户对应的履行对象个数
	 * @param userId
	 * @return
	 */
	public long getSupplierUserTargetIdTotal(Long userId) {
		Long total= (Long)super.queryForObject(
				"USER_RELATE_SUPPLIER_PRODUCT.selectSupplierUserTargetIdTotal", userId);
		return total;
	}
	
	/**
	 * 查询供应商用户对应的产品
	 * @param userId
	 * @return
	 */
	public List<UserRelateSupplierProduct> getSupplierUserProductByUserId(Long userId) {
		Map<String,Object> parm=new HashMap<String,Object>();
		parm.put("userId", userId);
		return super.queryForList(
				"USER_RELATE_SUPPLIER_PRODUCT.selectSupplierUserProductByUserId", parm);
	}
}