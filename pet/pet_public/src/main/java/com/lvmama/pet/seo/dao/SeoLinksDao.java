package com.lvmama.pet.seo.dao;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.BaseIbatisDAO;
import com.lvmama.comm.pet.po.seo.SeoLinks;

public class SeoLinksDao extends BaseIbatisDAO {
	public void insert(SeoLinks seoLinks)  {
        super.insert("SEO_LINKS.insertSeoLinks", seoLinks);
    }

	public void update(SeoLinks seoLinks) {
		super.update("SEO_LINKS.update", seoLinks);
	}

	public void batchInsertSeoLinks(List<SeoLinks> seoLinksList) {
		super.batchInsert("SEO_LINKS.insertSeoLinks", seoLinksList);
	}

	public void batchDeleteSeoLinks(List<SeoLinks> seoLinksList) {
		super.batchDelete("SEO_LINKS.deleteSeoLinks", seoLinksList);
		
	}

	public void deleteSeoLinksId(SeoLinks seoLinks) {
		super.delete("SEO_LINKS.deleteSeoLinks", seoLinks);
	}

	public Long getCountSeoLinksByParam(Map<String, Object> map) {
		return (Long) super.queryForObject("SEO_LINKS.getCountSeoLinksByParam",map);
	}
	
	public SeoLinks querySeoBySeoID(SeoLinks seoLinks){
	    return (SeoLinks)super.queryForObject("SEO_LINKS.querySeoBySeoID",seoLinks);
	}

	public List<SeoLinks> querySeoLinksByParamForReport(Map<String, Object> map) {
 		return super.queryForListForReport("SEO_LINKS.batchQuerySeoLinksByParam", map);
	}

	/**
	 * 通过链接和placeId 查询数据
	 * @param map
	 * @return
	 * @author nixianjun 2013-8-19
	 */
	public List<SeoLinks> querySeoLinksByParam(Map<String, Object> map) {
		 
		return super.queryForList("SEO_LINKS.querySeoLinksByParam", map);
	}

}
