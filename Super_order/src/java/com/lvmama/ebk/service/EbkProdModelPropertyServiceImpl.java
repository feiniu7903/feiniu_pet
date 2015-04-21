package com.lvmama.ebk.service;

import java.util.List;

import com.lvmama.comm.bee.po.ebooking.EbkProdModelProperty;
import com.lvmama.comm.bee.service.ebooking.EbkProdModelPropertyService;
import com.lvmama.ebk.dao.EbkProdModelPropertyDAO;

public class EbkProdModelPropertyServiceImpl implements EbkProdModelPropertyService {

	private EbkProdModelPropertyDAO ebkProdModelPropertyDAO;

	/**
     * 获取对象列表
     * @param ebkProdModelPropertyDO
     * @return 对象列表
     */
    public List<EbkProdModelProperty> findListByTerm(EbkProdModelProperty ebkProdModelPropertyDO) {
    	return ebkProdModelPropertyDAO.findListByTerm(ebkProdModelPropertyDO);
    }
    
    /**
     * 通过产品获取对象列表
     * @param productId
     * @return 对象列表
     */
    public List<EbkProdModelProperty> findListByProductId(Long productId) {
    	return ebkProdModelPropertyDAO.findListByProductId(productId);
    }
    
    /**
   	 * 新增产品关联属性信息
   	 * 
   	 * 2013-10-10
   	 * 
   	 * @param EbkProdModelProperty
   	 * @return
   	 */
    public void saveEbkProdModelProperty(EbkProdModelProperty ebkProdModelPropertyDO) {
    	ebkProdModelPropertyDAO.insertEbkProdModelPropertyDO(ebkProdModelPropertyDO);
    }
    
    /**
   	 * 删除产品关联所有属性信息
   	 * 
   	 * 2013-10-10
   	 * 
   	 * @param productID
   	 * @return
   	 */
     public void deleteEbkProdModelPropertyByProductID(Long productID){
    	 ebkProdModelPropertyDAO.deleteEbkProdModelPropertyByProductID(productID);
     }
    
	public EbkProdModelPropertyDAO getEbkProdModelPropertyDAO() {
		return ebkProdModelPropertyDAO;
	}

	public void setEbkProdModelPropertyDAO(EbkProdModelPropertyDAO ebkProdModelPropertyDAO) {
		this.ebkProdModelPropertyDAO = ebkProdModelPropertyDAO;
	}
	

}
