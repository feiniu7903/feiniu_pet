package com.lvmama.front.web.group;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.struts2.convention.annotation.Action;

import com.lvmama.comm.bee.service.GroupDreamService;
import com.lvmama.comm.pet.client.RecommendInfoClient;
import com.lvmama.comm.pet.po.seo.RecommendInfo;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.utils.RecommendBlockProperties;
import com.lvmama.comm.vo.Constant;
import com.lvmama.front.web.home.BaseHomeAction;
public class OtherProductAction extends BaseHomeAction{
	private List<Map<String,Object>> otherPrdList;
	protected GroupDreamService groupDreamService;
	protected Long productId;
	private static long limitRows=4;
	private static Logger logger = Logger.getLogger(OtherProductAction.class);
	private ComPictureService comPictureService;
	private RecommendInfoClient recommendInfoClient;
	/**
	 * 查看团购产品详情
	 * @return
	 * @throws Exception
	 */
	@Action("/group/loadOtherGroupPrdList")
	public String loadOtherPrdList(){
		String station=this.getStationValue();
		if(station==null || station.trim().length()==0){
			station="main";
		}
		Long blockId= Long.valueOf((String)RecommendBlockProperties.getBlockProperties("group."+station));
		Map<String,List<RecommendInfo>>  groupMap = recommendInfoClient.getRecommendProductByBlockIdAndStation(blockId,station); 
		
		otherPrdList = new ArrayList<Map<String,Object>>();
		List<RecommendInfo> topList  = groupMap.get(station+"_"+Constant.GROUP_TOP_PREFIX);
		//处理团购推荐产品
		if(topList!=null && topList.size()>0){
			processGroupPrdList(topList);
		}
		if(otherPrdList.size()<limitRows){
			//处理团购其他产品
			List<RecommendInfo> otherList  = groupMap.get(station+"_"+Constant.GROUP_OTHER_PREFIX);
			if(otherList!=null&& otherList.size()>0){
				processGroupPrdList(otherList);
			}
		}
		return "todayOtherGroupPrd";
	}
	private void processGroupPrdList(List<RecommendInfo> otherList) {
		for(RecommendInfo  rec :otherList){
			if(productId!=null && productId.equals(Long.valueOf(rec.getRecommObjectId()))) {
				continue;
			}
			if(otherPrdList.size()>=limitRows){
				break;
			}
			Map<String,Object>  returnMap  = groupDreamService.getTodayGroupProduct(Long.valueOf(rec.getRecommObjectId()));
			 if(returnMap==null){
				 //logger.error("none recommendPrd is not valid :"+ToStringBuilder.reflectionToString(rec));
			 }else{
				 //单独加载图片
				 if(returnMap.get("pageId") != null) {
					returnMap.put("comPictureList", comPictureService.getPictureByPageId((Long)returnMap.get("pageId")));
				 }
				 returnMap.put("viewRecommendInfo", rec);
				 otherPrdList.add(returnMap);
			 }
		}
	}
	public List<Map<String,Object>> getOtherPrdList() {
		return otherPrdList;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}
	public ComPictureService getComPictureService() {
		return comPictureService;
	}
	public void setRecommendInfoClient(RecommendInfoClient recommendInfoClient) {
		this.recommendInfoClient = recommendInfoClient;
	}
	public void setGroupDreamService(GroupDreamService groupDreamService) {
		this.groupDreamService = groupDreamService;
	}
 
}
