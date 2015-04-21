package com.lvmama.clutter.web.place;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.axis.utils.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.clutter.service.IClientOfflineCacheService;
import com.lvmama.clutter.service.IClientSearchService;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.utils.StringUtil;



/**
 * 中国好温泉
 * @author jswangqian
 *
 */
@Results({
	@Result(name="hotspring_index",location="/WEB-INF/pages/hotspring/hotspring_index.html",type="freemarker"),
	@Result(name="hotspring_search",location="/WEB-INF/pages/hotspring/hotspring_search.html",type="freemarker"),
	@Result(name="hotspring_ajax",location="/WEB-INF/pages/hotspring/hotspring_ajax.html",type="freemarker")
})
@Namespace("/mobile/hotspring")
public class HotSpringAction extends BaseAction {
	
	private static final long serialVersionUID = 1L;
	
	IClientOfflineCacheService mobileOfflineCacheService;
	
	/**
	 * 搜索服务 
	 */
	IClientSearchService mobileSearchService;
	
	private int page = 1; // 第几页 
	private String sort;  // 排序
	private String stage;  // 1:目的地（度假）；2：景区；3：酒店；
	private String subjects=" "; // 主题乐园','田园度假', '山水景观', '都市观光'.........
	private String keyword="" ; // 查询关键字 
	private boolean ajax;// 是否ajax 查询
	private String channel = ""; // 渠道

	/**
	 * 中国好温泉首页
	 * @return
	 */
	@Action("hotspring_index")
	public String hotSpringIndex(){
		return "hotspring_index";
	}
	/**
	 *  中国好温泉列表 
	 * @return 
	 */
	@Action("hotspring")
	public String spotticket(){
		try {
			if(!StringUtils.isEmpty(subjects)) {
				subjects = URLDecoder.decode(subjects,"UTF-8");
			}
			if(!StringUtils.isEmpty(keyword)) {
				keyword = URLDecoder.decode(keyword,"UTF-8");
			}
			// 如果是ajax查询 
			if(ajax) {
				initDataList();
				return "hotspring_ajax";
			}
			// 判读是否从移动端请求的 ,且是wap请求
			getRequest().setAttribute("mobileRequest", isMobileRequest() && !isWapChannel());
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return "hotspring_search";
	}
	
	/**
	 * 是否从wap渠道请求
	 * @return
	 */
	public boolean isWapChannel() {
		if(!StringUtils.isEmpty(channel) && "wap".equalsIgnoreCase(channel)) {
			return true;
		}
		return false;
	}
	/**
	 * 初始化查询数据 
	 * @throws UnsupportedEncodingException
	 */
	public void initDataList() throws UnsupportedEncodingException {
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("stage", StringUtil.isEmptyString(stage)?"2":stage); // 默认stage=2； 
			subjects = URLDecoder.decode(subjects,"UTF-8");
			if(!StringUtils.isEmpty(keyword)) {
				keyword = URLDecoder.decode(keyword,"UTF-8");
				param.put("keyword", keyword);
			} 
			param.put("page", page);
			param.put("sort", sort);
			param.put("subject", subjects);
			Map<String,Object> map = new HashMap<String,Object>();
			// 景区 
			if("2".equals(stage)) {
				map = mobileSearchService.placeSearch(param);
			} else if("1".equals(stage)) {// 度假（自由行）
				map = mobileSearchService.routeSearch(param);
			}
			if(null != map && null != map.get("datas")) {
				getRequest().setAttribute("placelist", map.get("datas"));
				getRequest().setAttribute("isLastPage", map.get("isLastPage"));
			}
			// 设置图片前缀 
			this.setImagePrefix();
	}
	public IClientOfflineCacheService getMobileOfflineCacheService() {
		return mobileOfflineCacheService;
	}
	public void setMobileOfflineCacheService(
			IClientOfflineCacheService mobileOfflineCacheService) {
		this.mobileOfflineCacheService = mobileOfflineCacheService;
	}
	public IClientSearchService getMobileSearchService() {
		return mobileSearchService;
	}
	public void setMobileSearchService(IClientSearchService mobileSearchService) {
		this.mobileSearchService = mobileSearchService;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public String getSort() {
		return sort;
	}
	public void setSort(String sort) {
		this.sort = sort;
	}
	public String getStage() {
		return stage;
	}
	public void setStage(String stage) {
		this.stage = stage;
	}
	public String getSubjects() {
		return subjects;
	}
	public void setSubjects(String subjects) {
		this.subjects = subjects;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public boolean isAjax() {
		return ajax;
	}
	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}
	public String getChannel() {
		return channel;
	}
	public void setChannel(String channel) {
		this.channel = channel;
	}
}
