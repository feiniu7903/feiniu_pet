package com.lvmama.pet.seo.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.seo.SeoSiteMapHtml;
import com.lvmama.comm.pet.service.seo.SeoSiteMapHtmlService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.pet.place.dao.PlaceDAO;
import com.lvmama.pet.seo.dao.SeoSiteMapHtmlDAO;

public class SeoSiteMapHtmlServiceImpl implements SeoSiteMapHtmlService {
	private SeoSiteMapHtmlDAO seoSiteMapHtmlDAO;
	private PlaceDAO placeDAO;
	
	/**
     * 删除下级以及本级菜单
     * @param menuId
     */
	@Override
	public void deleteSeoSiteMapHtmlByParentId(Long menuId) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("parentId", menuId);
		seoSiteMapHtmlDAO.deleteSeoSiteMapHtmlByParam(param);
		
		param=new HashMap<String,Object>();
		param.put("seoSiteMapHtmlId", menuId);
		seoSiteMapHtmlDAO.deleteSeoSiteMapHtmlByParam(param);
	}

	@Override
	public SeoSiteMapHtml getSeoSiteMapHtmlById(Long seoSiteMapHtmlId) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("seoSiteMapHtmlId", seoSiteMapHtmlId);
		List<SeoSiteMapHtml> seoSiteMapHtmlList=seoSiteMapHtmlDAO.querySeoSiteMapHtmlByParam(param);
		if(seoSiteMapHtmlList!=null && seoSiteMapHtmlList.size()>0){
			return seoSiteMapHtmlList.get(0);
		}
		return new SeoSiteMapHtml();
	}

	@Override
	public List<SeoSiteMapHtml> getSeoSiteMapHtmlByParentId(Long parentId) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("parentId", parentId);
		return seoSiteMapHtmlDAO.querySeoSiteMapHtmlByParam(param);
	}

	@Override
	public List<SeoSiteMapHtml> queryMainSeoSiteMapHtmlAll() {
		return seoSiteMapHtmlDAO.queryMainSeoSiteMapHtmlAll();
	}

	@Override
	public List<SeoSiteMapHtml> queryMainSeoSiteMapHtmlNameAll() {
		return seoSiteMapHtmlDAO.queryMainSeoSiteMapHtmlNameAll();
	}

	@Override
	public List<Map<String, Object>> getMapList() {
		List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
		List<SeoSiteMapHtml> mainMenus = this.queryMainSeoSiteMapHtmlAll();
		for (SeoSiteMapHtml mainMenu : mainMenus) {
			List<Object[]> lst=new ArrayList<Object[]>();
			List<SeoSiteMapHtml> secondMenus = this.getSeoSiteMapHtmlByParentId(mainMenu.getSeoSiteMapHtmlId());
			if(secondMenus!=null && secondMenus.size()>0){
				for(SeoSiteMapHtml item : secondMenus){
					Object[] obj=new Object[2];
					obj[0]=item.getHtmlMenuName();
					obj[1]=item.getHtmlMenuUrl();
					lst.add(obj);
				}
			}
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("parent", (String) mainMenu.getHtmlMenuName());
			data.put("item", lst);
			mapList.add(data);
		}
		return mapList;
	}
	
	@Override
	public void insertSeoSiteMapHtml(SeoSiteMapHtml htmlMapMenu) {
		seoSiteMapHtmlDAO.insertSeoSiteMapHtml(htmlMapMenu);
	}

	@Override
	public void updateSeoSiteMapHtml(SeoSiteMapHtml htmlMapMenu) {
		seoSiteMapHtmlDAO.updateSeoSiteMapHtml(htmlMapMenu);
	}
	
	public void updateMainMapSeq(List<SeoSiteMapHtml> list) {
		this.seoSiteMapHtmlDAO.updateMainMapSeq(list);
	}

	@Override
	public void deleteSeoSiteMapHtmlById(Long menuId) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("seoSiteMapHtmlId", menuId);
		seoSiteMapHtmlDAO.deleteSeoSiteMapHtmlByParam(param);
	}
	
	@Override
	public Page<Place> getAllPlaceJD(int pageSize, int currentPage) {
		return placeDAO.getAllPlaceJD(pageSize, currentPage);
	}

	@Override
	public Page<Place> getAllPlaceGNY(int pageSize, int currentPage) {
		return placeDAO.getAllPlaceGNY(pageSize, currentPage);
	}

	@Override
	public Page<Place> getAllPlaceCJY(int pageSize, int currentPage) {
		return placeDAO.getAllPlaceCJY(pageSize, currentPage);
	}

	@Override
	public Page<Place> getAllPlaceHotel(int pageSize, int currentPage) {
		return placeDAO.getAllPlaceHotel(pageSize, currentPage);
	}

	@Override
	public Page<Place> getAllPlacePlace(int pageSize, int currentPage) {
		return placeDAO.getAllPlacePlace(pageSize, currentPage);
	}

	@Override
	public Page<Place> getAllPlaceDP(int pageSize, int currentPage) {
		return placeDAO.getAllPlaceDP(pageSize, currentPage);
	}

	public void setSeoSiteMapHtmlDAO(SeoSiteMapHtmlDAO seoSiteMapHtmlDAO) {
		this.seoSiteMapHtmlDAO = seoSiteMapHtmlDAO;
	}

	public void setPlaceDAO(PlaceDAO placeDAO) {
		this.placeDAO = placeDAO;
	}
	
}
