package com.lvmama.ebk.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.ebooking.EbkProdRelation;
import com.lvmama.comm.bee.service.ebooking.EbkProdRelationService;
import com.lvmama.ebk.dao.EbkProdRelationDAO;

public class EbkProdRelationServiceImpl implements EbkProdRelationService {
	
	@Autowired
	private EbkProdRelationDAO ebkProdRelationDAO;
	
	@Override
	public Long insertEbkProdRelationDO(EbkProdRelation EbkProdRelationDO) {
		return ebkProdRelationDAO.insertEbkProdRelationDO(EbkProdRelationDO);
	}

	@Override
	public Integer countEbkProdRelationDOByExample(
			EbkProdRelation EbkProdRelationDO) {
		return ebkProdRelationDAO.countEbkProdRelationDOByExample(EbkProdRelationDO);
	}

	@Override
	public List<EbkProdRelation> findListByTerm(
			EbkProdRelation EbkProdRelationDO) {
		return ebkProdRelationDAO.findListByTerm(EbkProdRelationDO);
	}

	@Override
	public EbkProdRelation findEbkProdRelationDOByPrimaryKey(Long ebkProdRelationId) {
		return ebkProdRelationDAO.findEbkProdRelationDOByPrimaryKey(ebkProdRelationId);
	}

	@Override
	public Integer deleteEbkProdRelationDOByPrimaryKey(Long ebkProdRelationId) {
		return ebkProdRelationDAO.deleteEbkProdRelationDOByPrimaryKey(ebkProdRelationId);
	}

}
