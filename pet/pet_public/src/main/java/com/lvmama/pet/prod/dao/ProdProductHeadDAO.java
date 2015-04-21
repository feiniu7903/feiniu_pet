package com.lvmama.pet.prod.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.prod.ProdProductHead;

public class ProdProductHeadDAO extends BaseIbatisDAO {
	/**
	 * 获取prod_product_head_seq序列值的最新值
	 * @return 序列值
	 */
	public Long generateProductId() {
		return (Long) super.queryForObject("PROD_PRODUCT_HEAD.generateProductId");
	}
	
	/**
	 * 根据产品主键查找产品头
	 * @param productId 产品主建
	 * @return 产品头
	 */
	public ProdProductHead getProdProductHeadByProductId(final Long productId) {
		return (ProdProductHead) super.queryForObject("PROD_PRODUCT_HEAD.selectByPrimaryKey",productId);
	}
	
	/**
	 * 保存产品头信息
	 * @param head 产品头信息
	 * @return 产品头
	 */
	public ProdProductHead save(final ProdProductHead head) {
		super.insert("PROD_PRODUCT_HEAD.insert", head);
		return head;
	}
	
	/**
	 * 更新产品头信息
	 * @param head 产品头信息
	 * @return 产品头
	 */
	public ProdProductHead update(final ProdProductHead head) {
		super.update("PROD_PRODUCT_HEAD.update", head);
		return head;
	}
	
	/**
	 * 
	 * 根据查询条件查询产品头列表
	 * @param prodProductHead 查询信息
	 * @return 查询列表
	 */
	@SuppressWarnings("unchecked")
	public List<ProdProductHead> query(Map<String, Object> param){
		return (List<ProdProductHead>) super.queryForList("PROD_PRODUCT_HEAD.query",param);
	}

}
