package com.lvmama.prd.dao;

import java.util.List;

import org.springframework.util.Assert;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.bee.po.prod.ProdProductJourneyPack;

public class ProdJourneyPackDAO extends BaseIbatisDAO {

	public ProdJourneyPackDAO() {
        super();
    }
	
	public Long savePack(ProdProductJourneyPack prodProductJourneyPack) {
		Assert.notNull(prodProductJourneyPack);	
		if(prodProductJourneyPack.getProdJourneyPackId()!=null){
			super.update("PROD_JOURNEY_PACK.updatePack", prodProductJourneyPack);			
			return prodProductJourneyPack.getProdJourneyPackId(); 
		}
		Long packId = (Long)super.insert("PROD_JOURNEY_PACK.insertPack", prodProductJourneyPack);		
		return packId;
	}

	@SuppressWarnings("unchecked")
	public List<ProdProductJourneyPack> queryJourneyPackByProductId(Long productId) {
		Assert.notNull(productId);
    	return super.queryForList("PROD_JOURNEY_PACK.queryPackListByProductId", productId);
	}

	public ProdProductJourneyPack queryProductJourneyPackByPackId(Long prodJourneyPackId) {
		Assert.notNull(prodJourneyPackId);
		ProdProductJourneyPack pjp = (ProdProductJourneyPack)super.queryForObject("PROD_JOURNEY_PACK.findPackByPackId", prodJourneyPackId);
    	return pjp;
	}

	public void deletePack(Long prodJourneyPackId) {
		super.delete("PROD_JOURNEY_PACK.deletePack", prodJourneyPackId);
	}

	public void updatePackOnLine(ProdProductJourneyPack prodProductJourneyPack) {
		Assert.notNull(prodProductJourneyPack);	
		super.update("PROD_JOURNEY_PACK.updatePack", prodProductJourneyPack);
	}
}
