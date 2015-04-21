package com.lvmama.pet.seo.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.seo.SeoIndexPage;
import com.lvmama.comm.pet.service.seo.SeoIndexPageService;
import com.lvmama.pet.seo.dao.SeoIndexPageDAO;

public class SeoIndexPageServiceImpl implements SeoIndexPageService{
	private SeoIndexPageDAO seoIndexPageDAO;

	@Override
	public List<SeoIndexPage> querySeoIndexPageByParam(final Map<String, Object> parameters) {
		return seoIndexPageDAO.querySeoIndexPageByParam(parameters);
	}
	
	@Override
	public Long getSeoIndexPageCount(final Map<String, Object> parameters){
		return seoIndexPageDAO.getSeoIndexPageCount(parameters);
	}

	@Override
	public SeoIndexPage getSeoIndexPageById(Long seoIndexPageId) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("seoIndexPageId", seoIndexPageId);
		 List<SeoIndexPage> seoIndexPageList=seoIndexPageDAO.querySeoIndexPageByParam(param);
		 if(seoIndexPageList!=null && seoIndexPageList.size()>0){
			 return seoIndexPageList.get(0);
		 }
		return new SeoIndexPage();
	}

	@Override
	public void updateSeoIndexPage(SeoIndexPage seoIndexPage) {
		seoIndexPageDAO.updateSeoIndexPage(seoIndexPage);
	}

	@Override
	public SeoIndexPage getSeoIndexPageByPageCode(String pageCode) {
		return seoIndexPageDAO.getSeoIndexPageByPageCode(pageCode);
	}

	public void setSeoIndexPageDAO(SeoIndexPageDAO seoIndexPageDAO) {
		this.seoIndexPageDAO = seoIndexPageDAO;
	}
	
}
