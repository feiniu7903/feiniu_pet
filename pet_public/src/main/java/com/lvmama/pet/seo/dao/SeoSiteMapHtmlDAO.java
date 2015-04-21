package com.lvmama.pet.seo.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.seo.SeoSiteMapHtml;

public class SeoSiteMapHtmlDAO extends BaseIbatisDAO{

	@SuppressWarnings("unchecked")
	public List<SeoSiteMapHtml> querySeoSiteMapHtmlByParam(Map<String,Object> param) {
		return  (List<SeoSiteMapHtml>)super.queryForList("SEO_SITE_MAP_HTML.querySeoSiteMapHtmlByParam", param);
	}

	@SuppressWarnings("unchecked")
	public List<SeoSiteMapHtml> queryMainSeoSiteMapHtmlNameAll() {
		return  (List<SeoSiteMapHtml>)super.queryForList("SEO_SITE_MAP_HTML.queryMainSeoSiteMapHtmlNameAll");
	}

	@SuppressWarnings("unchecked")
	public List<SeoSiteMapHtml> queryMainSeoSiteMapHtmlAll() {
		return  (List<SeoSiteMapHtml>)super.queryForList("SEO_SITE_MAP_HTML.queryMainSeoSiteMapHtmlAll");
	}

	public void insertSeoSiteMapHtml(SeoSiteMapHtml seoSiteMapHtml) {
		 super.insert("SEO_SITE_MAP_HTML.insertSeoSiteMapHtml", seoSiteMapHtml);
	}
	
	public void deleteSeoSiteMapHtmlByParam(Map<String,Object> param) {
		super.delete("SEO_SITE_MAP_HTML.deleteSeoSiteMapHtmlByParam", param);
	}
	
	public void updateMainMapSeq(List<SeoSiteMapHtml> list) {
		for (SeoSiteMapHtml html : list) {
			this.updateSeoSiteMapHtml(html);
		}
	}
	
	public void updateSeoSiteMapHtml(SeoSiteMapHtml seoSiteMapHtml) {
		 super.update("SEO_SITE_MAP_HTML.updateSeoSiteMapHtml", seoSiteMapHtml);
	}
	
}