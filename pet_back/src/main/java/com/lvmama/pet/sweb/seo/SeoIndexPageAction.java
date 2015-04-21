package com.lvmama.pet.sweb.seo;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.pet.po.seo.SeoIndexPage;
import com.lvmama.comm.pet.service.seo.SeoIndexPageService;
import com.lvmama.comm.utils.ExeSh;
import com.lvmama.comm.utils.FileUtil;
import com.lvmama.comm.vo.Constant;
@Results({ 
	@Result(name = "success", location = "/WEB-INF/pages/back/seo/index_page.jsp")
	})
public class SeoIndexPageAction extends com.lvmama.comm.BackBaseAction{
	private static final long serialVersionUID = 1343838032206811583L;
	private SeoIndexPageService seoIndexPageService;
	
	private List<SeoIndexPage> seoIndexPageList;
	private SeoIndexPage seoIndexPage;
	private String pageName;

	@SuppressWarnings("unchecked")
	@Action("/seo/index")
	public String execute() {
		Map<String,Object> param = new HashMap<String, Object>();
		if(StringUtils.isNotEmpty(pageName))
		{
			param.put("pageName", pageName);
		}
		pagination = initPage();
		param.put("_startRow", pagination.getStartRows());
		param.put("_endRow", pagination.getEndRows());
		pagination.setTotalResultSize(seoIndexPageService.getSeoIndexPageCount(param));
		pagination.setItems(seoIndexPageService.querySeoIndexPageByParam(param));
		pagination.buildUrl(getRequest());
		return SUCCESS;
	}
	
	public void getSeoIndexPageById() {
		if(seoIndexPage!=null&& seoIndexPage.getSeoIndexPageId()!=null){
			seoIndexPage = seoIndexPageService.getSeoIndexPageById(seoIndexPage.getSeoIndexPageId());
			JSONArray jsonArray=JSONArray.fromObject(seoIndexPage);
			this.responseWrite(jsonArray.toString());
		}
	}
	
	public void updateIndexPageSeo() {
		String json = "";
		try {
			seoIndexPageService.updateSeoIndexPage(seoIndexPage);
			json = "{\"flag\":\"true\"}";
		} catch (Exception e) {
			json = "{\"flag\":\"false\"}";
		}

		try {
			this.getResponse().setContentType("text/html; charset=utf-8");
			this.getResponse().getWriter().write(json);
		} catch (Exception e) {
			log.info(e.getMessage());
		}
	}

	@Action("/seo/single/index/synFriendLinkFile")
	public void synFriendLinkFile() throws IOException {
		ExeSh.exeSh(Constant.getSeoFriendLinkScy());
		this.getResponse().getWriter().write("{\"flag\":\"true\"}");
	}
	@Action("/seo/single/index/createFriendLinkFile")
	public void createFriendLinkFile() throws IOException {
		SeoIndexPage seoIndexPageSeoContent = seoIndexPageService.getSeoIndexPageById(seoIndexPage.getSeoIndexPageId());
		if(seoIndexPageSeoContent != null && StringUtils.isNotEmpty(seoIndexPageSeoContent.getSeoContent())){
			FileUtil.writeFile(seoIndexPageSeoContent.getFooterFileName().trim(), Constant.getFriendLinkPath().trim(), seoIndexPageSeoContent.getSeoContent());
			this.getResponse().getWriter().write("{\"flag\":\"true\"}");
		}else {
			this.getResponse().getWriter().write("{\"flag\":\"false\"}");
		}
	}
	
	private void responseWrite(String info){
		try {
			this.getResponse().setContentType("text/html; charset=utf-8");
			this.getResponse().getWriter().write(info);
		} catch (Exception e) {
			log.info(" com.lvmama.pet.sweb.seo:"+e.getMessage());
		}
	}
	
	public void setSeoIndexPageService(SeoIndexPageService seoIndexPageService) {
		this.seoIndexPageService = seoIndexPageService;
	}
	
	public SeoIndexPage getSeoIndexPage() {
		return seoIndexPage;
	}

	public void setSeoIndexPage(SeoIndexPage seoIndexPage) {
		this.seoIndexPage = seoIndexPage;
	}

	public List<SeoIndexPage> getSeoIndexPageList() {
		return seoIndexPageList;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

}
