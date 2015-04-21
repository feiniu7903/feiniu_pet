package com.lvmama.passport.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.pass.PassProduct;
/**
 * 通关产品
 * @author chenlinjun
 *
 */
public class PassProductDAO extends BaseIbatisDAO{
	/**
	 * 按条件查询
	 * 
	 * @param 查询参数
	 */
	public PassProduct selectPassProductByParams(Map<String, Object> params) {
		return (PassProduct)super.queryForObject(
				"PASS_PRODUCT.selectPassProduct", params);
	}
	
	public Long insertPassProduct(PassProduct passProduct){
		return (Long)super.insert("PASS_PRODUCT.insertSelective", passProduct);
		
	}
	
	@SuppressWarnings("unchecked")
	public List<PassProduct> queryPassProduct(Map params){
		return super.queryForList("PASS_PRODUCT.queryPassProduct", params);
		
	}

	public void updatePassProduct(PassProduct passProduct) {
		super.update("PASS_PRODUCT.updateByPrimaryKeySelective", passProduct);
		
	}

	public void deletePassProduct(Long passProdId) {
		super.delete("PASS_PRODUCT.deletePassProduct", passProdId);
		
	}

	public Integer selectRowCount(Map<String, Object> queryOption) {
		return (Integer) super.queryForObject("PASS_PRODUCT.selectRowCount", queryOption);
	}
}
