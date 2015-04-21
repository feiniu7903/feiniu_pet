
package com.lvmama.front.web.group;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;

import com.lvmama.comm.bee.service.GroupDreamService;
import com.lvmama.comm.pet.client.RecommendInfoClient;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.utils.RecommendBlockProperties;
import com.lvmama.comm.utils.ServletUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.front.web.home.BaseHomeAction;

/**
 * 
 * 团购首页Actin
 * @author songlianjun
 *
 */
@Results( { 
	@Result(name = "groupIndex", location = "/WEB-INF/pages/group/groupIndex.ftl", type = "freemarker")
	,@Result(name = "redirectUrl", location = "${redirectUrl}", type = "redirect")
})
public class IndexAction extends BaseHomeAction {
	
	private static Logger logger = Logger.getLogger(IndexAction.class);
	private List<Map> groupPrdList = new ArrayList<Map>();
	private GroupDreamService groupDreamService;
	private String station;
	private ComPictureService comPictureService;
	private String redirectUrl;
	private Map<String,List<RecommendInfo>> groupMap;
	private Map<Long,Object> recommendPrdMap;
	private RecommendInfoClient recommendInfoClient;
	/**
	 * 查看今日团购产品
	 */
	@Action("/group/index")
	public String execute() throws Exception {
		//查询今日团购旅游产品
		station=this.getStationValue();
		Long blockId= Long.valueOf((String)RecommendBlockProperties.getBlockProperties("group."+station));
		groupMap = recommendInfoClient.getRecommendProductByBlockIdAndStation(blockId,station); 
		
		groupPrdList = new ArrayList<Map>();
		recommendPrdMap = new  HashMap<Long,Object>();
		List<RecommendInfo> topList  = groupMap.get(station+"_"+Constant.GROUP_TOP_PREFIX);
		//处理团购推荐产品
		if(topList!=null && topList.size()>0){
			for(RecommendInfo  rec :topList){
				//查询产品信息 
				Map<String,Object>  returnMap  = groupDreamService.getTodayGroupProduct(Long.valueOf(rec.getRecommObjectId()));
				 if(returnMap != null){
					 //单独加载图片
					 if(returnMap.get("pageId") != null) {
						returnMap.put("comPictureList", comPictureService.getPictureByPageId((Long)returnMap.get("pageId")));
					 }
					 returnMap.put("viewRecommendInfo", rec);
					 returnMap.put("isRecommendPrd", true);
					 recommendPrdMap.put(Long.valueOf(rec.getRecommObjectId()), rec.getRecommObjectId());
					 groupPrdList.add(returnMap);
				 }
			}
		}
		//处理团购其他产品
		List<RecommendInfo> otherList  = groupMap.get(station+"_"+Constant.GROUP_OTHER_PREFIX);
		if(otherList!=null&& otherList.size()>0){
			for(RecommendInfo  rec :otherList){
				 Map<String,Object>  returnMap  = groupDreamService.getTodayGroupProduct(Long.valueOf(rec.getRecommObjectId()));
				 if(returnMap!=null){
					 if(returnMap.get("pageId") != null) { 
						 returnMap.put("comPictureList", comPictureService.getPictureByPageId((Long)returnMap.get("pageId"))); 
					 }
					 returnMap.put("isRecommendPrd", false);
					 returnMap.put("viewRecommendInfo", rec);
					 groupPrdList.add(returnMap);
				 }
			}
		}
		
		return "groupIndex";
	}

	@Action("/change/changeLocation")
	public String changeLocation(){
		String targ = this.getRequest().getParameter("t");
		if (targ != null) {
			ServletUtil.addCookie(this.getResponse(), "CLOCATION", targ, 365);
		} else {
			ServletUtil.addCookie(this.getResponse(), "CLOCATION", "www", 365);
		}
		redirectUrl = this.getRedirectUrl(this.getRequest());
		return "redirectUrl";
	}
	
	private String getRedirectUrl(HttpServletRequest request){
		String url = request.getHeader("Referer");
		if(url == null)
		{
			url = "http://www.lvmama.com";
		} 
		return url;
	}
	
	public List<Map> getGroupPrdList() {
		return groupPrdList;
	}
	public void setGroupPrdList(List<Map> tuanPrdList) {
		this.groupPrdList = tuanPrdList;
	}
 
	public void setGroupDreamService(GroupDreamService groupDreamService) {
		this.groupDreamService = groupDreamService;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getStation() {
		return station;
	}

	public boolean isRecommendPrd(Long productId) {
		return recommendPrdMap.containsKey(productId);
	}

	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}
	
	public String getRedirectUrl() {
		return redirectUrl;
	}

	public void setRecommendInfoClient(RecommendInfoClient recommendInfoClient) {
		this.recommendInfoClient = recommendInfoClient;
	}
	
}
