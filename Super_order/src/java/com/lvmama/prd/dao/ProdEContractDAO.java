package com.lvmama.prd.dao;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.prod.ProdEContract;

public class ProdEContractDAO extends BaseIbatisDAO {
	/**
	 * 根据销售产品标识查找电子合同信息
	 * @param productId
	 * @return
	 */
	public ProdEContract selectByProductId(Long productId) {
		return (ProdEContract) super.queryForObject("PROD_ECONTRACT.selectByProductId", productId);
	}
	
	/**
	 * 插入电子合同信息
	 * @param prodEContract
	 */
	public void insert(ProdEContract prodEContract) {
		super.insert("PROD_ECONTRACT.insert", prodEContract);
	}
	
	/**
	 * 更新电子合同信息
	 * @param prodEContract
	 */
	public void updateByProductId(ProdEContract prodEContract) {
		/**
		 * @author yangbin
		 * 方法名字修改为update
		 */
		super.update("PROD_ECONTRACT.updateByProductId", prodEContract);
	}
}
