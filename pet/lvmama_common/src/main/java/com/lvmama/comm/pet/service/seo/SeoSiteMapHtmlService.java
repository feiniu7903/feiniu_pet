package com.lvmama.comm.pet.service.seo;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.seo.SeoSiteMapHtml;
import com.lvmama.comm.pet.vo.Page;

public interface SeoSiteMapHtmlService {
	/**
	 * 插入网站地图一级或二级菜单.
	 * 
	 * @param seoSiteMapHtml
	 *            传入插入对象.
	 */
	public void insertSeoSiteMapHtml(SeoSiteMapHtml seoSiteMapHtml);

	/**
	 * 更新网站地图菜单菜单名称或链接地址,并记录下最新时间.
	 * 
	 * @param seoSiteMapHtml
	 */
	public void updateSeoSiteMapHtml(SeoSiteMapHtml seoSiteMapHtml);

	/**
	 * 通过主键删除一级菜单,并相对应删除其下面的二级菜单.
	 * 
	 * @param menuId
	 */
	public void deleteSeoSiteMapHtmlByParentId(Long menuId)throws Exception;

	/**
	 * 通过主键删除二级菜单.
	 * 
	 * @param menuId
	 */
	public void deleteSeoSiteMapHtmlById(Long menuId)throws Exception;

	/**
	 * 通过主菜单编号查询到其下面的二级菜单.
	 * 
	 * @param parentId
	 */
	public List<SeoSiteMapHtml> getSeoSiteMapHtmlByParentId(Long parentId) throws SQLException ;

	/**
	 * 获得网站地图中的所有的一级菜单名称.
	 * 
	 * @return
	 */
	public List<SeoSiteMapHtml> queryMainSeoSiteMapHtmlNameAll();

	/**
	 * 通过主键编号获得当前菜单对象.
	 * 
	 * @return
	 */
	public SeoSiteMapHtml getSeoSiteMapHtmlById(Long menuId) throws SQLException ;

	/**
	 * 获得网站地图中的所有的一级菜单.
	 * 
	 * @return
	 */
	public List<SeoSiteMapHtml> queryMainSeoSiteMapHtmlAll();

	/**
	 * 批量更新菜单Seq.
	 * 
	 * @param list
	 */
	public void updateMainMapSeq(List<SeoSiteMapHtml> list);
	
	/**
	 * 获得网站地图总列表
	 */
	public List<Map<String,Object>> getMapList();

	/**
	 * 得到全部景点
	 * 
	 * @param pageSize
	 * @param currentPage
	 * @param param
	 * @return
	 */
	public Page<Place> getAllPlaceJD(int pageSize, int currentPage);
	
	/**
	 * 得到全部国内游
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public Page<Place> getAllPlaceGNY(int pageSize, int currentPage);
	
	/**
	 * 得到全部出境游
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public Page<Place> getAllPlaceCJY(int pageSize, int currentPage);
	
	
	/**
	 * 得到全部特色酒店
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public Page<Place> getAllPlaceHotel(int pageSize, int currentPage);
	
	/**
	 * 得到全部目的地(国内加境外)
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public Page<Place> getAllPlacePlace(int pageSize, int currentPage);
	/**
	 * 得到全部目的地和景点(点评使用)
	 * @param pageSize
	 * @param currentPage
	 * @return
	 */
	public Page<Place> getAllPlaceDP(int pageSize, int currentPage);
}
