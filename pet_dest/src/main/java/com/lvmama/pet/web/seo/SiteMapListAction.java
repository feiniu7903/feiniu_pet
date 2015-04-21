package com.lvmama.pet.web.seo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.service.seo.SeoSiteMapHtmlService;
import com.lvmama.comm.pet.vo.Page;
/**
 * @author zuozhengpeng
 *
 */
@Results({ 
	@Result(name = "maplist", type="freemarker",location = "/WEB-INF/pages/seo/htmlmap/maplist.ftl"),
	@Result(name = "menpiao", type="freemarker", location = "/WEB-INF/pages/seo/htmlmap/menpiao.ftl"),
	@Result(name = "guonei", type="freemarker", location = "/WEB-INF/pages/seo/htmlmap/guonei.ftl"),
	@Result(name = "ziyouxing", type="freemarker", location = "/WEB-INF/pages/seo/htmlmap/ziyouxing.ftl"),
	@Result(name = "chujing", type="freemarker", location = "/WEB-INF/pages/seo/htmlmap/chujing.ftl"),
	@Result(name = "jiudian", type="freemarker", location = "/WEB-INF/pages/seo/htmlmap/jiudian.ftl"),
	@Result(name = "dianping", type="freemarker", location = "/WEB-INF/pages/seo/htmlmap/dianping.ftl"),
	@Result(name = "guoneigonglue", type="freemarker", location = "/WEB-INF/pages/seo/htmlmap/guoneigonglue.ftl"),
	@Result(name = "chujinggonglue", type="freemarker", location = "/WEB-INF/pages/seo/htmlmap/chujinggonglue.ftl"),
	@Result(name = "lvyouzhinan", type="freemarker", location = "/WEB-INF/pages/seo/htmlmap/lvyouzhinan.ftl"),
	@Result(name = "jingdianzhinan", type="freemarker", location = "/WEB-INF/pages/seo/htmlmap/jingdianzhinan.ftl"),
	@Result(name = "error", type="dispatcher", location = "/404.jsp")
	})
public class SiteMapListAction extends BaseAction {
	private SeoSiteMapHtmlService seoSiteMapHtmlService;
	private static final long serialVersionUID = 1L;
	private List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
	private int pageSize = 600;
	private int currentPage;
	private String currentPageString;
	private Page<Place> placeList;
	
	@Action("/sitemap/siteMapListAction")
	public String execute() throws Exception {
		return super.execute();
	}

	public String getAllMapList() {
		log.info("into getAllMapList............");
		try {
			mapList = seoSiteMapHtmlService.getMapList();
		} catch (Exception e) {
			log.error(e);
		}
		return "maplist";
	}

	/**
	 * 得到更多景点门票
	 * @return
	 */
	public String getMorePlaceTicket() {
		if (currentPage <= 0) {
			currentPage = 1;
		}
		placeList = seoSiteMapHtmlService.getAllPlaceJD(pageSize, currentPage);
		if(currentPage>placeList.getCurrentPage()){
			return "error";
		}
		return "menpiao";
	}


	/**
	 * 得到更多景区点评
	 * @return
	 */
	public String getMoreComment(){
		if (currentPage <= 0) {
			currentPage = 1;
		}
		placeList = seoSiteMapHtmlService.getAllPlaceDP(pageSize, currentPage);
		if(currentPage>placeList.getCurrentPage()){
			return "error";
		}
		return "dianping";
	}
	/**
	 * 得到更多国内游
	 * @return
	 */
	public String getMoreGny(){
		
		if (currentPage <= 0) {
			currentPage = 1;
		}
		placeList = seoSiteMapHtmlService.getAllPlaceGNY(pageSize, currentPage);
		if(currentPage>placeList.getCurrentPage()){
			return "error";
		}
		return "guonei";
	}
	
	/**
	 * 更多自由行
	 * @return
	 */
	public String getMoreFreeTour(){
		if (currentPage <= 0) {
			currentPage = 1;
		}
		placeList = seoSiteMapHtmlService.getAllPlacePlace(pageSize, currentPage);
		if(currentPage>placeList.getCurrentPage()){
			return "error";
		}
		return "ziyouxing";
	}
	
	
	/**
	 * 更多景点指南
	 */
	public String getMorePlaceGuide(){
		pageSize = 80;// 页面大小
		if (currentPage <= 0) {
			currentPage = 1;
		}
		placeList = seoSiteMapHtmlService.getAllPlaceJD(pageSize, currentPage);
		if(currentPage>placeList.getCurrentPage()){
			return "error";
		}
		return "jingdianzhinan";
	}
	
	
	/**
	 * 更多旅游指南(国内加境外目的地)
	 */
	
	public String getMorePlacePlace(){
		pageSize = 80;// 页面大小
		if (currentPage <= 0) {
			currentPage = 1;
		}
		placeList = seoSiteMapHtmlService.getAllPlacePlace(pageSize, currentPage);
		if(currentPage>placeList.getCurrentPage()){
			return "error";
		}
		return "lvyouzhinan";
	}
	
	/**
	 * 更多酒店
	 * @return
	 */
	public String getMoreHotel() {
		if (currentPage <= 0) {
			currentPage = 1;
		}
		placeList = seoSiteMapHtmlService.getAllPlaceHotel(pageSize, currentPage);
		if(currentPage>placeList.getCurrentPage()){
			return "error";
		}
		return "jiudian";
	}

	
	/**
	 * 更多出境游
	 * 
	 * @return
	 */
	
	public String getMoreCjy() {
		if (currentPage <= 0) {
			currentPage = 1;
		}
		placeList = seoSiteMapHtmlService.getAllPlaceCJY(pageSize, currentPage);
		if(currentPage>placeList.getCurrentPage()){
			return "error";
		}
		return "chujing";
	}
	
	/**
	 * 更多国内攻略
	 * @return
	 */
	public String getMoreGnGuide(){
		if (currentPage <= 0) {
			currentPage = 1;
		}
		placeList = seoSiteMapHtmlService.getAllPlaceGNY(pageSize, currentPage);
		if(currentPage>placeList.getCurrentPage()){
			return "error";
		}
		return "guoneigonglue";
	}
	
	
	/**
	 * 更多出境攻略
	 * @return
	 */
	public String getMoreCjGuide(){
		if (currentPage <= 0) {
			currentPage = 1;
		}
		placeList = seoSiteMapHtmlService.getAllPlaceCJY(pageSize, currentPage);
		if(currentPage>placeList.getCurrentPage()){
			return "error";
		}
		return "chujinggonglue";
	}
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getCurrentPageString() {
		return currentPageString;
	}

	public void setCurrentPageString(String currentPageString) {
		this.currentPageString = currentPageString;
		this.currentPage=Integer.parseInt(new String (currentPageString.substring(currentPageString.indexOf("_")+1)));
	}

	public List<Map<String, Object>> getMapList() {
		return mapList;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public Page<Place> getPlaceList() {
		return placeList;
	}

	public void setPlaceList(Page<Place> placeList) {
		this.placeList = placeList;
	}

	public void setSeoSiteMapHtmlService(SeoSiteMapHtmlService seoSiteMapHtmlService) {
		this.seoSiteMapHtmlService = seoSiteMapHtmlService;
	}

}
