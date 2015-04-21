package com.lvmama.pet.seo.service;

import java.util.List;
import java.util.Map;


import com.lvmama.comm.pet.po.seo.SeoLinks;
import com.lvmama.comm.pet.service.seo.SeoLinksService;
import com.lvmama.pet.seo.dao.SeoLinksDao;

public class SeoLinksServiceImpl implements SeoLinksService {
	private SeoLinksDao seoLinksDao;
	@Override
	public void insert(SeoLinks seoLinks) {
		 
		seoLinksDao.insert(seoLinks);
	}
	@Override
	public void update(SeoLinks seoLinks) {
		seoLinksDao.update(seoLinks);
		
	}
	@Override
	public void deleteBySeoLinksId(long seoLinksId) {
		SeoLinks seoLinks=new SeoLinks();
		seoLinks.setSeoLinksId(seoLinksId);
		seoLinksDao.deleteSeoLinksId(seoLinks);
		
	}
	@Override
	public void batchInsert(List<SeoLinks> seoLinksList) {
		seoLinksDao.batchInsertSeoLinks(seoLinksList);
		
	}
	@Override
	public void batchDeleteByParam(Map<String, Object> map) {
		List<SeoLinks> seoLinksList=seoLinksDao.querySeoLinksByParamForReport(map);
		if(!seoLinksList.isEmpty()){
			seoLinksDao.batchDeleteSeoLinks(seoLinksList);
		}
	}
	@Override
	public void batchDeleteSeoLinks(List<SeoLinks> seoLinksList) {
		seoLinksDao.batchDeleteSeoLinks(seoLinksList);
	}

	@Override
	public List<SeoLinks> batchQuerySeoLinksByParam(Map<String, Object> map) {
		return seoLinksDao.querySeoLinksByParamForReport(map);
	}
	@Override
	public Long getCountSeoLinksByParam(Map<String, Object> map) {
		return seoLinksDao.getCountSeoLinksByParam(map);
	}
	
	
	@Override
	public SeoLinks querySeoBySeoID(long id) {
	    SeoLinks seoLinks=new SeoLinks();
        seoLinks.setSeoLinksId(id);
	    return seoLinksDao.querySeoBySeoID(seoLinks);
	}
	
	@Override
	public List<SeoLinks> querySeoLinksByParam(Map<String, Object> map) {
		
		return this.seoLinksDao.querySeoLinksByParam(map);
	}
	
	/**
     * @param seoLinksDao the seoLinksDao to set
     */
    public void setSeoLinksDao(SeoLinksDao seoLinksDao) {
        this.seoLinksDao = seoLinksDao;
    }

  
}
