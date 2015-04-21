/**
 * 
 */
package com.lvmama.comm.pet.service.seo;

import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.seo.SeoLinks;

/**
 * seo友情链接服务
 * @author nixianjun 2013.8.8
 * 
 */
public interface SeoLinksService {
	/**
	 * 单一插入
	 * @param seoLinks
	 * @author nixianjun
	 */
	public void insert(SeoLinks seoLinks);
	
	/**
	 * 通过id删除 
	 * @param id
	 * @author nixianjun
	 */
	public void deleteBySeoLinksId(long id);
	/**
	 * 批量插入
	 * @param seoLinksList
	 * @author nixianjun
	 */
	public void batchInsert(List<SeoLinks> seoLinksList);
	/**
	 * 批量删除通过参数
	 * @author nixianjun
	 * @param map
	 */
	public void batchDeleteByParam(Map<String, Object> map);
	
	public void batchDeleteSeoLinks(List<SeoLinks> seoLinksList);
	
	/**
	 * 批量查询
	 * @param map
	 * @return
	 */
	public List<SeoLinks> batchQuerySeoLinksByParam(Map<String, Object> map);
	/**
	 * 通过参数获取记录数
	 * @param map
	 * @return
	 */
	public Long getCountSeoLinksByParam(Map<String, Object> map);
	/**
	 * 根据seoId查找seoLinks
	 * @param id
	 * @author zhongshuangxi
	 */
	public SeoLinks querySeoBySeoID(long id);
	/**
     * 单一更新
     * @param seoLinks
     * @author zhongshuangxi
     */
    public void update(SeoLinks seoLinks);

	/**查询 通过链接+placeId
	 * @param map
	 */
	public List<SeoLinks> querySeoLinksByParam(Map<String, Object> map);
}
