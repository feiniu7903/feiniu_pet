package com.lvmama.pet.sweb.seo;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.seo.SeoSiteMapHtml;
import com.lvmama.comm.pet.service.seo.SeoSiteMapHtmlService;

/**
 * 网站地图一级或子菜单增删修改.
 * 
 * @author zuozhengpeng
 */
@Results({ 
	@Result(name = "jumpMenuIndex", location = "/WEB-INF/pages/back/seo/mapMenu/menu_index.jsp"),
	@Result(name = "jumpMenuChild", location = "/WEB-INF/pages/back/seo/mapMenu/menu_child.jsp"),
	@Result(name = "jumpMenuMap", location = "/WEB-INF/pages/back/seo/mapMenu/menu_map.jsp"),
	@Result(name = "jumpMenuIndexDo", type="redirect", location = "/seo/jumpMenuIndex.do"),
	@Result(name = "jumpMenuChildDo", type="redirect", location = "/seo/jumpMenuChild.do?htmlMenuId=${htmlMenuId}")
	})
public class SeoSiteMapHtmlAction extends com.lvmama.comm.BackBaseAction {
	private static final long serialVersionUID = 7186239527854957499L;
	private SeoSiteMapHtmlService seoSiteMapHtmlService;
	/**
	 * 一级菜单list集合.
	 */
	private List<SeoSiteMapHtml> mainMapMenuList;
	/**
	 * 子菜单.
	 */
	private List<SeoSiteMapHtml> childMapMenuList;
	/**
	 * 一级菜单集合(显示菜单名称,便于子菜单供选择上级菜单).
	 */
	private List<SeoSiteMapHtml> mapMenuNameList;
	/**
	 * 菜单对象.
	 */
	private SeoSiteMapHtml seoSiteMapHtml;
	/**
	 * 菜单编号.
	 */
	private Long htmlMenuId;
	private Long[] mapMenuSeq;
	private Long[] mapMenuId;
	private String returnUrl;

	/**
	 * 修改主菜单排序值.
	 * 
	 * @return
	 */
	@Action("/seo/updateMainMapSeq")
	public String updateMainMapSeq() {
		List<SeoSiteMapHtml> seqList = new ArrayList<SeoSiteMapHtml>();
		if (mapMenuId != null && mapMenuId.length > 0) {
			SeoSiteMapHtml seoSiteMapHtml =null;
			for (int i = 0; i < mapMenuId.length; i++) {
				seoSiteMapHtml = new SeoSiteMapHtml();
				seoSiteMapHtml.setSeoSiteMapHtmlId(mapMenuId[i]);
				seoSiteMapHtml.setSeq(mapMenuSeq[i]);
				seqList.add(seoSiteMapHtml);
			}
			this.seoSiteMapHtmlService.updateMainMapSeq(seqList);
			this.setHtmlMenuId(htmlMenuId);
		}
		return returnUrl;
	}

	/**
	 * 删除子级菜单.
	 * 
	 * @return
	 */
	@Action("/seo/deleteChildMapMenu")
	public String deleteChildMapMenu() {
		try {
			seoSiteMapHtmlService.deleteSeoSiteMapHtmlById(seoSiteMapHtml.getSeoSiteMapHtmlId());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "jumpMenuChildDo";
	}

	/**
	 * 修改子级菜单.
	 * 
	 * @return
	 */
	@Action("/seo/editChildMapMenu")
	public String editChildMapMenu() {
		seoSiteMapHtml.setUpdateTime(new Date());
		seoSiteMapHtmlService.updateSeoSiteMapHtml(seoSiteMapHtml);
		this.setHtmlMenuId(htmlMenuId);
		return "jumpMenuChildDo";
	}

	/**
	 * 新增子级菜单.
	 * 
	 * @return
	 */
	@Action("/seo/addChildMapMenu")
	public String addChildMapMenu() {
		SeoSiteMapHtml htmlMap = new SeoSiteMapHtml();
		htmlMap.setCreateTime(new Date());
		htmlMap.setHtmlMenuName(seoSiteMapHtml.getHtmlMenuName());
		htmlMap.setHtmlMenuUrl(seoSiteMapHtml.getHtmlMenuUrl());
		htmlMap.setSeq(seoSiteMapHtml.getSeq());
		htmlMap.setUpdateTime(new Date());
		htmlMap.setParentId(htmlMenuId);
		seoSiteMapHtmlService.insertSeoSiteMapHtml(htmlMap);
		this.setHtmlMenuId(htmlMenuId);
		return "jumpMenuChildDo";
	}

	/**
	 * 删除一级菜单.
	 * 
	 * @return
	 */
	@Action("/seo/deleteMainMapMenu")
	public void deleteMainMapMenu() {
		try {
			List<SeoSiteMapHtml> seoSiteMapHtmlList=seoSiteMapHtmlService.getSeoSiteMapHtmlByParentId(Long.valueOf(htmlMenuId));
			if(seoSiteMapHtmlList!=null && seoSiteMapHtmlList.size()>0){
				this.responseWrite("{\"flag\":\"false\"}");
			}else{
			    seoSiteMapHtmlService.deleteSeoSiteMapHtmlByParentId(Long.valueOf(htmlMenuId));
			    this.responseWrite("{\"flag\":\"true\"}");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 修改一级菜单.
	 * 
	 * @return
	 * @throws SQLException 
	 */
	@Action("/seo/editMainMapMenu")
	public String editMainMapMenu() throws SQLException {
		seoSiteMapHtml.setUpdateTime(new Date());
		seoSiteMapHtmlService.updateSeoSiteMapHtml(seoSiteMapHtml);
		return "jumpMenuIndexDo";
	}

	/**
	 * 新增一级菜单.
	 * 
	 * @return
	 */
	@Action("/seo/addMainMapMenu")
	public String addMainMapMenu() {
		SeoSiteMapHtml htmlMap = new SeoSiteMapHtml();
		htmlMap.setHtmlMenuName(seoSiteMapHtml.getHtmlMenuName());
		htmlMap.setHtmlMenuUrl(seoSiteMapHtml.getHtmlMenuUrl());
		htmlMap.setSeq(seoSiteMapHtml.getSeq());
		htmlMap.setCreateTime(new Date());
		htmlMap.setUpdateTime(new Date());
		htmlMap.setParentId(null);
		seoSiteMapHtmlService.insertSeoSiteMapHtml(htmlMap);
		return "jumpMenuIndexDo";
	}

	/**
	 * 跳转至html地图一级菜单管理.
	 * 
	 * @return
	 */
	@Action("/seo/jumpMenuIndex")
	public String jumpMenuIndex() {
		mainMapMenuList = this.seoSiteMapHtmlService.queryMainSeoSiteMapHtmlAll();
		return "jumpMenuIndex";
	}

	/**
	 * 跳转至html地图子菜单管理.
	 * 
	 * @return
	 * @throws SQLException 
	 */
	@Action("/seo/jumpMenuChild")
	public String jumpMenuChild() throws SQLException {
		this.childMapMenuList = this.seoSiteMapHtmlService.getSeoSiteMapHtmlByParentId(Long.valueOf(htmlMenuId));
		this.mapMenuNameList = this.seoSiteMapHtmlService.queryMainSeoSiteMapHtmlNameAll();
		this.setHtmlMenuId(htmlMenuId);
		return "jumpMenuChild";
	}

	/**
	 * 跳转至xml地图管理.
	 * 
	 * @return
	 */
	@Action("/seo/jumpMenuMap")
	public String jumpMenuMap() {
		return "jumpMenuMap";
	}
	
	private void responseWrite(String info){
		try {
			this.getResponse().setContentType("text/html; charset=utf-8");
			this.getResponse().getWriter().write(info);
		} catch (Exception e) {
			log.info("com.lvmama.pet.sweb.seo:"+e.getMessage());
		}
	}

	public void setSeoSiteMapHtmlService(
			SeoSiteMapHtmlService seoSiteMapHtmlService) {
		this.seoSiteMapHtmlService = seoSiteMapHtmlService;
	}

	public SeoSiteMapHtml getSeoSiteMapHtml() {
		return seoSiteMapHtml;
	}

	public void setSeoSiteMapHtml(SeoSiteMapHtml seoSiteMapHtml) {
		this.seoSiteMapHtml = seoSiteMapHtml;
	}

	public void setReturnUrl(String returnUrl) {
		this.returnUrl = returnUrl;
	}

	public void setMapMenuId(Long[] mapMenuId) {
		this.mapMenuId = mapMenuId;
	}

	public Long getHtmlMenuId() {
		return htmlMenuId;
	}

	public void setHtmlMenuId(Long htmlMenuId) {
		this.htmlMenuId = htmlMenuId;
	}

	public Long[] getMapMenuSeq() {
		return mapMenuSeq;
	}

	public void setMapMenuSeq(Long[] mapMenuSeq) {
		this.mapMenuSeq = mapMenuSeq;
	}

	public List<SeoSiteMapHtml> getMainMapMenuList() {
		return mainMapMenuList;
	}

	public List<SeoSiteMapHtml> getChildMapMenuList() {
		return childMapMenuList;
	}

	public List<SeoSiteMapHtml> getMapMenuNameList() {
		return mapMenuNameList;
	}

}
