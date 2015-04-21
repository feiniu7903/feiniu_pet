package com.lvmama.prd.dao;

import java.util.List;
import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ProdProductRoyalty;

/**
 * 产品分润数据访问层
 * 
 * @author taiqichao
 *
 */
public class ProdProductRoyaltyDAO extends BaseIbatisDAO {

	/**
	 * 新增产品分润信息
	 * 
	 * @param prodProductRoyalty
	 */
	public void insert(ProdProductRoyalty prodProductRoyalty) {
		super.insert("PROD_PRODUCT_ROYALTY.insert", prodProductRoyalty);
	}

	/**
	 * 删除产品分润信息
	 * 
	 * @param royaltyId
	 *            分润ID
	 * @return 受影响行数
	 */
	public int deleteByPrimaryKey(Long royaltyId) {
		return super.delete("PROD_PRODUCT_ROYALTY.deleteByPrimaryKey", royaltyId);
	}

	/**
	 * 更新产品分润信息
	 * 
	 * @param prodProductRoyalty
	 * @return
	 */
	public int updateByPrimaryKey(ProdProductRoyalty prodProductRoyalty) {
		return super.update("PROD_PRODUCT_ROYALTY.updateByPrimaryKey",prodProductRoyalty);
	}

	/**
	 * 查询产品分润信息
	 * @param productId 产品ID
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<ProdProductRoyalty> selectAllByProductId(Long productId){
		return super.queryForList("PROD_PRODUCT_ROYALTY.selectAllByProductId", productId);
	}
	
	/**
	 * 查询所有分润产品id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<Long> selectAllProdProductIds(){
		return super.queryForList("PROD_PRODUCT_ROYALTY.selectAllProdProductIds");
	}
	
}
