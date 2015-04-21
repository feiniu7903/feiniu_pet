package com.lvmama.comm.bee.service.ebooking;

import java.util.List;

import com.lvmama.comm.bee.po.ebooking.EbkProdTarget;

public interface EbkProdTargetService {

	/**
	 * 新增产品关联对象信息
	 * 
	 * 2013-9-29
	 * 
	 * @param EbkProdTarget
	 * @return
	 */
	int saveEbkProdTarget(EbkProdTarget ebkProdTarget);
	
	/**
     * 获取对象列表
     * @param ebkProdTargetDO
     * @return 对象列表
     */
    public List<EbkProdTarget> findListByTerm(EbkProdTarget ebkProdTargetDO);
    
    /**
     * 通过产品ID获取对象列表
     * @param productId
     * @return 对象列表
     */
    public List<EbkProdTarget> findListByProductId(Long productId);
    
    /**
   	 * 删除产品关联所有对象信息
   	 * 
   	 * 2013-10-10
   	 * 
   	 * @param productID
   	 * @return
   	 */
       public void deleteEbkProdTargetByProductID(Long productID);

}
