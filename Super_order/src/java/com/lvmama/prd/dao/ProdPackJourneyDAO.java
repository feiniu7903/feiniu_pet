package com.lvmama.prd.dao;

import java.util.List;

import org.springframework.util.Assert;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ProdPackJourneyProduct;

public class ProdPackJourneyDAO extends BaseIbatisDAO {
	
	public ProdPackJourneyDAO(){
		super();
	}

	public void save(List<ProdPackJourneyProduct> lists,Long prodJourneyPackId){
		Assert.notNull(lists);
		Assert.notNull(prodJourneyPackId);
		super.delete("PROD_PACK_JOURNEY.deletePackProduct", prodJourneyPackId);
		for(ProdPackJourneyProduct prod:lists){
			prod.setProdJourneyPackId(prodJourneyPackId);
			super.insert("PROD_PACK_JOURNEY.insertPackProduct", prod);
		}
	}
	
	public List<ProdPackJourneyProduct> queryByPackJourneyId(Long prodJourneyPackId){
		List<ProdPackJourneyProduct> list = super.queryForList("PROD_PACK_JOURNEY.queryPackProductByPackId",prodJourneyPackId);
		return list;
	}
	
	public void delete(Long prodJourneyPackId){
		super.update("PROD_PACK_JOURNEY.deletePackProduct", prodJourneyPackId);
	}
	
}
