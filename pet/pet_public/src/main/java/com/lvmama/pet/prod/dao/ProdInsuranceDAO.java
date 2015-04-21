package com.lvmama.pet.prod.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.prod.ProdInsurance;

public class ProdInsuranceDAO extends BaseIbatisDAO {

	/**
	 * 新增保险产品
	 * @param insurance 保险产品
	 * @return 被保存的保险产品
	 */
	public ProdInsurance insert(ProdInsurance insurance) {
		if (null != insurance) {
			super.insert("PROD_INSUANCE.insert", insurance);
			return insurance;
		} else {
			return null;
		}
	}
	
	/**
	 * 更新保险产品
	 * @param insurance 保险产品
	 * @return 被更新的保险产品
	 */
	public ProdInsurance update(ProdInsurance insurance) {
		if (null != insurance && null != insurance.getProductId()) {
			super.insert("PROD_INSUANCE.update", insurance);
			return insurance;
		} else {
			return null;
		}
	}
	
	/**
	 * 根据查询条件返回记录数
	 * @param param 查询条件
	 * @return 记录数
	 * <p>根据传入的查询条件统计符合条件的记录数</p>
	 */
	public Long selectRowCount(Map<String, Object> param) {
		return (Long) super.queryForObject("PROD_INSUANCE.count", param);
	}
	
	/**
	 * 根据查询条件返回符合条件的记录列表
	 * @param param 查询条件
	 * @return 记录列表
	 * <p>根据传入的查询条件返回符合条件的记录</p>
	 */
	@SuppressWarnings("unchecked")
	public List<ProdInsurance> queryProdInsuranceByMap(Map<String, Object> param) {
		return super.queryForList("PROD_INSUANCE.query", param);
	}
	
	/**
	 * 根据产品标识查询保险产品，请特别注意这里不是根据<code>insurance_id</code>进行查询的
	 * @param productId 产品标识
	 * @return 保险产品
	 */
	public ProdInsurance queryProdInsuranceByPK(Long productId) {
		return (ProdInsurance) super.queryForObject("PROD_INSUANCE.queryByPrimayKey", productId);
	}
}