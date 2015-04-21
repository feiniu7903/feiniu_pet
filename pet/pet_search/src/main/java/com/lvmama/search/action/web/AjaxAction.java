package com.lvmama.search.action.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;

import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.BaseAction;
import com.lvmama.comm.pet.po.comment.CmtLatitudeStatistics;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.service.comment.CmtLatitudeStatistisService;
import com.lvmama.comm.pet.service.comment.CmtTitleStatistisService;
import com.lvmama.comm.pet.service.seo.RecommendInfoService;
import com.lvmama.comm.search.vo.HotelSearchVO;
import com.lvmama.comm.search.vo.PlaceHotelBean;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.homePage.HomePageUtils;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.search.service.HotelSearchService;
import com.lvmama.search.util.PageConfig;

/**
 * 处理所有的AJAX请求
 * 
 * @author YangGan
 *
 */
@Results({
	@Result(name="cmt",location="/WEB-INF/ftl/ajax/cmt.ftl",type="freemarker")
})
@Namespace("/ajax")
public class AjaxAction extends BaseAction{
	
	private static final long serialVersionUID = 1L;
	//景区点评统计服务接口
	@Resource
	private CmtTitleStatistisService cmtTitleStatistisService;
	//点评维度统计服务接口
	@Resource
	private CmtLatitudeStatistisService cmtLatitudeStatistisService;
	//推荐数据服务接口
	@Resource
	private RecommendInfoService recommendInfoService;
	private Long placeId;
	
	@Autowired
	private  HotelSearchService hotelSearchService;
	
	/**
	 * 热门关键词
	 * @return
	 */
	@Action("recommendword")
	public void loadrecommendWord(){
		Map map = new HashMap();
		String code = getRequest().getParameter("code");
		if(StringUtil.isEmptyString(code)){
			this.sendAjaxMsg("");
		}else{
			Long parentREcommendBlockId = HomePageUtils.FILIALE_PARENT_RECOMMEND_BLOCK_ID_MAPPING.get(code);
			if(parentREcommendBlockId == null){
				this.sendAjaxMsg("");
			}else{
				String key = "topRecommend_around"+parentREcommendBlockId;
				List<RecommendInfo> list  = (List<RecommendInfo>) MemcachedUtil.getInstance().get(key);
				if(list == null || list.size() == 0){
					map.put("parentRecommendBlockId", parentREcommendBlockId);
					map.put("dataCode", "topRecommend_around");
					list = recommendInfoService.queryRecommendInfoByParam(map);
					MemcachedUtil.getInstance().set(key, MemcachedUtil.TWO_HOUR,list);
				}
				if(list!=null && list.size() > 0){
					 String json = JSONArray.fromObject(list).toString();
					 this.sendAjaxResultByJson(json);
				}else{
					 this.sendAjaxMsg("");
				}
			}
		}
	}
	/**
	 * 加载点评信息
	 * @return
	 */
	@Action("cmt")
	public String loadCmt(){
		// 获取该景点的点评统计信息
		CmtTitleStatisticsVO cmtTitleStatisticsVO = fillCmtTitleStatisticsVO(placeId);
		getRequest().setAttribute("cmtTitleStatisticsVO", cmtTitleStatisticsVO);
		// 获得某个景点的4个基本点评维度统计平均分
		List<CmtLatitudeStatistics> cmtLatitudeStatisticsList = fillCmtLatitudeStatisticsList(placeId);
		getRequest().setAttribute("cmtLatitudeStatisticsList", cmtLatitudeStatisticsList);
		return "cmt";
	}

	protected List<CmtLatitudeStatistics> fillCmtLatitudeStatisticsList(Long placeId){
		Object cacheCmtLatitudeStat = MemcachedUtil.getInstance().get(
				"placePageInfo_cmtLatitudeStat_" + placeId);
		if (null != cacheCmtLatitudeStat) {
			LOG.debug("从MemCache中获取景点 " + placeId + " 基本点评维度统计信息");
		} else {
			LOG.debug("MemCache中获取景点 " + placeId + " 基本点评维度统计信息不存在或已经过期，需要重新获取");
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("placeId", placeId);
			List<CmtLatitudeStatistics> cmtLatitudeStatisticsList = cmtLatitudeStatistisService.getFourAvgLatitudeScoreList(params);
			cacheCmtLatitudeStat = cmtLatitudeStatisticsList;
			if (null != cacheCmtLatitudeStat) {
				MemcachedUtil.getInstance().setWithDis("placePageInfo_cmtLatitudeStat_" + placeId,MemcachedUtil.ONE_HOUR,cacheCmtLatitudeStat,"获得某个景点的4个基本点评维度统计平均分："+placeId);
			}
		}
		return (List<CmtLatitudeStatistics>) cacheCmtLatitudeStat;
	}
	
	/**
	 * 获取该景点的点评统计信息
	 * @param placeId
	 */
	protected CmtTitleStatisticsVO fillCmtTitleStatisticsVO(Long placeId){
		Object cacheCmtTitleStatis = MemcachedUtil.getInstance().get("placePageInfo_cmtTitleStatis_" + placeId);
		if (null != cacheCmtTitleStatis) {
			LOG.debug("从MemCache中获取景点 " + placeId + " 点评统计信息");
		} else {
			LOG.debug("MemCache中获取景点 " + placeId + " 点评统计信息不存在或已经过期，需要重新获取");
			CmtTitleStatisticsVO cmtTitleStatisticsVO = cmtTitleStatistisService.getCmtTitleStatisticsByPlaceId(placeId);
			cacheCmtTitleStatis = cmtTitleStatisticsVO;
			if (null != cacheCmtTitleStatis) {
				MemcachedUtil.getInstance().setWithDis("placePageInfo_cmtTitleStatis_" + placeId,MemcachedUtil.ONE_HOUR,cacheCmtTitleStatis,"获取该景点的点评统计信息："+placeId);
			}
		}
		return (CmtTitleStatisticsVO)cacheCmtTitleStatis;
	}
	
	public Long getPlaceId() {
		return placeId;
	}

	public void setPlaceId(Long placeId) {
		this.placeId = placeId;
	}
}
