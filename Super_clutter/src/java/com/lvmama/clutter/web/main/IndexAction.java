package com.lvmama.clutter.web.main;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.clutter.model.MobileRecommend;
import com.lvmama.clutter.service.IClientRecommendService;
import com.lvmama.clutter.web.BaseAction;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.utils.HttpsUtil;
import com.lvmama.comm.vo.Constant;

@Results({ 
	@Result(name = "index", location = "http://m.lvmama.com", type="redirect"),
	@Result(name = "index_ajax", location = "/WEB-INF/pages/index_ajax.html", type="freemarker"),
	@Result(name = "special", location = "/WEB-INF/pages/specialsubject/${specialName}.html", type="freemarker"),
	@Result(name = "xieyi", location = "/WEB-INF/pages/common/xieyi.html", type="freemarker"),
	@Result(name = "position", location = "/WEB-INF/pages/common/position.html", type="freemarker")
})
@Namespace("/mobile")
public class IndexAction extends BaseAction {

	private static final long serialVersionUID = 1L;
	
	private static final String BAIDU_KEY = "CA152fe66fe4998de14538722d67fc60";
	
	/**
	 * 推荐服务. 
	 */
	IClientRecommendService mobileRecommendService;
	
	private String specialName = "changlong"; // 专题名称
	private int page = 1; // 第几页 ; pageSize每页显示多少行
	private boolean ajax; // 查询 
	
	/**
	 * 首页 
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action("index")
	public String index(){
		List<MobileRecommend>  mrList = null;
		boolean isLastPage = true;
		Map<String,Object> param = new HashMap<String,Object>();
		param.put("method", "api.com.recommend.getFocusRecommend");// 缓存用
		param.put("page", page);
		param.put("isWap", true);
		param.put("count", 3);
		Map<String,Object> m = mobileRecommendService.getFocusRecommend(param);
		getRequest().setAttribute("isLastPage",isLastPage); 
		if(null != m && null != m.get("datas") ) {
			List<Map<String,Object>> recommendMapList = (List<Map<String,Object>>)m.get("datas");
			if(null != recommendMapList && recommendMapList.size() > 0) {
				 mrList = (List<MobileRecommend>)recommendMapList.get(0).get("data");
				 getRequest().setAttribute("isLastPage",recommendMapList.get(0).get("isLastPage")); 
			}
		}
		getRequest().setAttribute("prefixPic", Constant.getInstance().getPrefixPic());
		getRequest().setAttribute("recommendList", mrList);
		if(ajax) {
			return "index_ajax";
		}
		return "index";
	}
	
	/**
	 * 定位
	 * @return
	 */
	@Action("position")
	public String position(){
		getRequest().setAttribute("lat", getRequest().getParameter("lat"));
		getRequest().setAttribute("lon", getRequest().getParameter("lon"));
		return "position";
	}
	
	/**
	 * 专题 - 不用 
	 * @return
	 */
	@Action("special")
	public String specialSubject() {
		if(StringUtils.isEmpty(specialName)) {
			specialName = "changlong";
		}
		return "special";
	}
	/**
	 * 协议
	 * @return
	 */
	@Action("xieyi")
	public String xieyi() {
		getRequest().setAttribute("xieyiType", getRequest().getParameter("xieyiType"));
		return "xieyi";
	}
	
	/**
	 * 根据经度 和 维度从百度地图获取城市信息. 
	 */
	@Action("city_info")
	public void getCityInfoByLatlon() {
		String lat = getRequest().getParameter("lat");
		String lon = getRequest().getParameter("lon");
		String jsons = "{ \"status\":\"ERROR\",\"city\":\"上海\"}";
		if(!StringUtils.isEmpty(lat) && !StringUtils.isEmpty(lon)) {
			// 从百度地图获取数据 . 
			String url = "http://api.map.baidu.com/geocoder?location="+lat+","+lon+"&output=json&key="+BAIDU_KEY;
			jsons =  HttpsUtil.requestGet(url);
		}
		this.sendAjaxResultByJson(jsons);
	}
	
	
    /** 
     * 判断用户是否登录. 
	 * @return
	 */
	@Action("islogin")
	public void islogin() {
		String str  = "{\"code\":-1,\"msg\":\"\"}";;
		try {
			UserUser u = getUser();
			if(null != u) {
				 str  = "{\"code\":1,\"msg\":\"\"}";;
			}
		}catch(Exception e) {
			e.printStackTrace();
			str  = "{\"code\":-1,\"msg\":\""+e.getMessage()+"\"}";
		}
		super.sendAjaxResult(str);
	}
	
	/**
	 * 判断请求类型 。 
	 * @return string android , iphone ,ipad  ,others
	 */
	public String getUserAgent() {
		String ua = getRequest().getHeader("user-agent");
		if (ua.contains("Android")) {
			return "android";
		} else if (ua.contains("iPhone")) {
			return "iphone";
		} else if (ua.contains("iPad")) {
			return "ipad";
		} else {
			return "others";
		}
	}
	
	public void setMobileRecommendService(IClientRecommendService mobileRecommendService) {
		this.mobileRecommendService = mobileRecommendService;
	}
	
	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}
	
	public boolean isAjax() {
		return ajax;
	}

	public void setAjax(boolean ajax) {
		this.ajax = ajax;
	}
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

}
