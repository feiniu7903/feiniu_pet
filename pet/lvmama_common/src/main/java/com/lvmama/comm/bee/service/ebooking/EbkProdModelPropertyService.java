package com.lvmama.comm.bee.service.ebooking;

import java.util.List;

import com.lvmama.comm.bee.po.ebooking.EbkProdModelProperty;


public interface EbkProdModelPropertyService {
	
	/**
     * 获取对象列表
     * @param ebkProdModelPropertyDO
     * @return 对象列表
     */
    public List<EbkProdModelProperty> findListByTerm(EbkProdModelProperty ebkProdModelPropertyDO) ;
    
    /**
     * 通过产品获取对象列表
     * @param productId
     * @return 对象列表
     */
    public List<EbkProdModelProperty> findListByProductId(Long productId) ;
    
    /**
	 * 新增产品关联属性信息
	 * 
	 * 2013-10-10
	 * 
	 * @param EbkProdModelProperty
	 * @return
	 */
    public void saveEbkProdModelProperty(EbkProdModelProperty ebkProdModelProperty);
    
    /**
   	 * 删除产品关联所有属性信息
   	 * 
   	 * 2013-10-10
   	 * 
   	 * @param productID
   	 * @return
   	 */
       public void deleteEbkProdModelPropertyByProductID(Long productID);
}
