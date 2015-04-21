package com.lvmama.ebk.service;

import java.util.List;

import com.lvmama.comm.bee.po.ebooking.EbkProdTarget;
import com.lvmama.comm.bee.service.ebooking.EbkProdTargetService;
import com.lvmama.ebk.dao.EbkProdTargetDAO;

public class EbkProdTargetServiceImpl implements EbkProdTargetService {

	private EbkProdTargetDAO ebkProdTargetDAO;

	/**
	 * 新增产品关联对象信息
	 * 
	 * 2013-9-29
	 * 
	 * @param EbkProdTarget
	 * @return
	 */
	@Override
	public int saveEbkProdTarget(EbkProdTarget ebkProdTarget) {
		Long ebkProdTargetId = ebkProdTargetDAO.insertEbkProdTargetDO(ebkProdTarget);
		return ebkProdTargetId.intValue();
	}
	
	
	/**
     * 获取对象列表
     * @param ebkProdTargetDO
     * @return 对象列表
     */
    public List<EbkProdTarget> findListByTerm(EbkProdTarget ebkProdTargetDO) {
    	return ebkProdTargetDAO.findListByTerm(ebkProdTargetDO);
    }

    /**
     * 通过产品ID获取对象列表
     * @param productId
     * @return 对象列表
     */
    public List<EbkProdTarget> findListByProductId(Long productId){
    	return ebkProdTargetDAO.findListByProductId(productId);
    }
    
    /**
   	 * 删除产品关联所有对象信息
   	 * 
   	 * 2013-10-10
   	 * 
   	 * @param productID
   	 * @return
   	 */
     public void deleteEbkProdTargetByProductID(Long productID){
    	 ebkProdTargetDAO.deleteEbkProdTargetByProductID(productID);
     }

    
	public EbkProdTargetDAO getEbkProdTargetDAO() {
		return ebkProdTargetDAO;
	}

	public void setEbkProdTargetDAO(EbkProdTargetDAO ebkProdTargetDAO) {
		this.ebkProdTargetDAO = ebkProdTargetDAO;
	}

}
