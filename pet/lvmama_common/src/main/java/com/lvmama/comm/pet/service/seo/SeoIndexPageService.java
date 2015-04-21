package com.lvmama.comm.pet.service.seo;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.seo.SeoIndexPage;

public interface SeoIndexPageService {
	/**
	 * 获取tkd规则列表
	 * @param seoIndexPage
	 * @return
	 */
	public List<SeoIndexPage> querySeoIndexPageByParam(final Map<String, Object> parameters);
	
	/**
	 * 获取TDK数
	 * @param parameters
	 * @return
	 */
	public Long getSeoIndexPageCount(final Map<String, Object> parameters);
	
	/**
	 * 通过主键编号获得当前tkd对象.
	 * 
	 * @return
	 */
	public SeoIndexPage getSeoIndexPageById(Long seoIndexPageId);
	/**
	 * 通过pageCode获取tkd对象
	 * @param pageCode
	 * @return
	 */
	public SeoIndexPage getSeoIndexPageByPageCode(String pageCode);
	/**
	 * 更新SeoIndexPage
	 * @param seoIndexPage
	 */
	public void updateSeoIndexPage(SeoIndexPage seoIndexPage);
}
