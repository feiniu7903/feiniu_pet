package com.lvmama.pet.prod.dao;

import java.util.List;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.prod.ProdProductTag;

public class ProdProductTagDAO extends BaseIbatisDAO{
	/**
	 * 根据productId获取该产品所有的tag
	 * @param productId
	 * @return
	 */
	public List<ProdProductTag> selectProductTagByProductId(Long productId) {
	    return (List<ProdProductTag>)super.queryForList("PROD_PRODUCT_TAG.selectProductTagByProductId", productId);
	}
}
