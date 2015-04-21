package com.lvmama.pet.seo.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.seo.SeoIndexPage;

public class SeoIndexPageDAO extends BaseIbatisDAO{

	@SuppressWarnings("unchecked")
	public List<SeoIndexPage> querySeoIndexPageByParam(final Map<String, Object> param) {
		return (List<SeoIndexPage>) super.queryForList("SEO_INDEX_PAGE.querySeoIndexPageByParam", param);
	}
	
	public Long getSeoIndexPageCount(final Map<String, Object> parameters){
		return (Long) super.queryForObject("SEO_INDEX_PAGE.seoIndexPageCount", parameters);
	}

	public void updateSeoIndexPage(SeoIndexPage seoIndexPage) {
		super.update("SEO_INDEX_PAGE.updateSeoIndexPage", seoIndexPage);
	}
	
    public int deleteByPrimaryKey(Long seoIndexPageId) {
        SeoIndexPage seoIndexPage = new SeoIndexPage();
        seoIndexPage.setSeoIndexPageId(seoIndexPageId);
        int rows = super.delete("SEO_INDEX_PAGE.deleteByPrimaryKey", seoIndexPage);
        return rows;
    }

    public void insertSelective(SeoIndexPage record)  {
        super.insert("SEO_INDEX_PAGE.insertSelective", record);
    }

    public SeoIndexPage getSeoIndexPageByPageCode(String pageCode){
    	return (SeoIndexPage)super.queryForObject("SEO_INDEX_PAGE.getSeoIndexPageByPageCode",pageCode);
    }

}