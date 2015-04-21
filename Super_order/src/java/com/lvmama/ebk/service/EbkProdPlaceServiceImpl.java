package com.lvmama.ebk.service;

import java.util.List;

import com.lvmama.comm.bee.po.ebooking.EbkProdPlace;
import com.lvmama.comm.bee.service.ebooking.EbkProdPlaceService;
import com.lvmama.ebk.dao.EbkProdPlaceDAO;

public class EbkProdPlaceServiceImpl implements EbkProdPlaceService {

	private EbkProdPlaceDAO ebkProdPlaceDAO;

	
	/**
     * 获取对象列表
     * @param ebkProdPlaceDO
     * @return 对象列表
     */
    public List<EbkProdPlace> findListByTerm(EbkProdPlace ebkProdPlaceDO) {
    	return ebkProdPlaceDAO.findListByTerm(ebkProdPlaceDO);
    }
	@Override
	public int deleteListByProductId(Long ebkProductId) {
		return ebkProdPlaceDAO.deleteListByEbkProductId(ebkProductId);
	}

	@Override
	public EbkProdPlace insert(EbkProdPlace ebkProdPlaceDO) {
		Long key = ebkProdPlaceDAO.insertEbkProdPlaceDO(ebkProdPlaceDO);
		ebkProdPlaceDO.setProductPlaceId(key);
		return ebkProdPlaceDO;
	}
	public EbkProdPlaceDAO getEbkProdPlaceDAO() {
		return ebkProdPlaceDAO;
	}

	public void setEbkProdPlaceDAO(EbkProdPlaceDAO ebkProdPlaceDAO) {
		this.ebkProdPlaceDAO = ebkProdPlaceDAO;
	}
	
}
