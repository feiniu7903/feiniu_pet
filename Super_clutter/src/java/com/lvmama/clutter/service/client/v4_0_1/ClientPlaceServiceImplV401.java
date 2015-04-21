package com.lvmama.clutter.service.client.v4_0_1;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

import com.lvmama.clutter.model.MobileBranch;
import com.lvmama.clutter.model.MobilePlace;
import com.lvmama.clutter.model.MobilePlaceActivity;
import com.lvmama.clutter.service.impl.ClientPlaceServiceImpl;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.pet.po.place.PlaceActivity;
import com.lvmama.comm.pet.po.place.PlacePhoto;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.service.place.PlaceActivityService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.enums.PlacePhotoTypeEnum;

public class ClientPlaceServiceImplV401 extends ClientPlaceServiceImpl {

	/**
	 * 景点活动 
	 */
	PlaceActivityService placeActivityService;
	
	@Override
	public MobilePlace getPlace(Map<String, Object> param) {
		MobilePlace mp = super.getPlace(param);
		if(null != mp) {
			// 景点活动
			try {
				Long placeId = Long.valueOf(param.get("placeId")+"");
				mp.setPlaceActivity(getMobilePlaceActivity(placeId));
				if(null != mp.getPlaceActivity() && mp.getPlaceActivity().size() > 0 ) {
					mp.setHasActivity(true);
				}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		return mp;
	}
	
	// 获取branch详情
	@Override
	public Map<String,Object> getBranchDetail(Map<String, Object> param) {
		
		ArgCheckUtils.validataRequiredArgs("productId","branchId",param);
		Long productId = Long.valueOf(param.get("productId")+"");
		Long branchId = Long.valueOf(param.get("branchId")+"");
		Map<String,Object> resultMap = new HashMap<String,Object>();
		getViewPage(productId,branchId,resultMap);
		return resultMap;
	}
	
	/**
	 * 获取orderTips
	 * @param branchId
	 * @return
	 */
	public String getOrderTips(Long branchId) {
		ProdBranchSearchInfo branchSearch = productSearchInfoService.getProdBranchSearchInfoByBranchId(branchId);
		if(null != branchSearch) {
			MobileBranch mb = copyTicketBranch(branchSearch);
			this.setTodayOrderTips2(mb,false);
			return null == mb.getTodayOrderTips()?"": mb.getTodayOrderTips();
		}
		
		return "";
	}
	
	/**
	 * 属性复制 
	 * @param pbsi
	 * @return
	 */
	public  MobileBranch copyTicketBranch(ProdBranchSearchInfo pbsi){
		MobileBranch mb = new MobileBranch();
		mb.setCanOrderToday(pbsi.todayOrderAble());
		mb.setCanOrderTodayCurrentTime(pbsi.canOrderTodayCurrentTime());
		mb.setBranchId(pbsi.getProdBranchId());
		mb.setProductId(pbsi.getProductId());
		mb.setTodayOrderLastOrderTime(pbsi.getTodayOrderAbleTime());
		return mb;
	}

	
	/**
	 * 一些文案信息  
	 * @param productId
	 * @param resultMap
	 */
	public void getViewPage(Long productId,Long branchId ,Map<String,Object> resultMap) {
		ViewPage viewPage = viewPageService.selectByProductId(productId);
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		Map<String,Object> obj = new HashMap<String,Object>();
		if (viewPage!=null&&viewPage.getPageId()!=null) {
			viewPage = viewPageService.getViewPage(viewPage.getPageId());
			List<ViewContent> contentList = viewPage.getContentList();
			/*resultMap.put("care", "开发的同学注意： 费用说明包括：费用包含（constcontain）和费用不包含（noConstcontain）；" +
					"重要提示包括：行前须知（actionToKnow），预定须知（orderToKnown），退款说明（refundsExplanAtion）和服务保障（serviceGuarantee）；" +
					"O(∩_∩)O~");*/
			for (int i = 0; i < contentList.size(); i++) {
				ViewContent vc = contentList.get(i);
				Map<String,Object> map = new HashMap<String,Object>();
				
				// 费用包含 
				if(Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name().equals(vc.getContentType())){
					map.put("title", "费用包含");
					map.put("content", ClientUtils.filterOutHTMLTags(vc.getContent()));
					map.put("type", "constcontain");
					obj.put("COSTCONTAIN", map);
				}
				// 费用不包含
				if(Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name().equals(vc.getContentType())){
					map.put("title", "费用不包含");
					map.put("content", ClientUtils.filterOutHTMLTags(vc.getContent()));
					map.put("type", "noConstcontain");
					obj.put("NOCOSTCONTAIN", map);
				}
				
				/**
				 *   重要提示  包括
                 *    ACITONTOKNOW("行前须知"), 
				 *    ORDERTOKNOWN("预订须知"),   
				 *    REFUNDSEXPLANATION("退款说明"), 
				 *    SERVICEGUARANTEE("服务保障"), 
				 */
				//行前须知 
				if(Constant.VIEW_CONTENT_TYPE.ACITONTOKNOW.name().equals(vc.getContentType())){
					map.put("title", "行前须知");
					map.put("content", ClientUtils.filterOutHTMLTags(vc.getContent()));
					map.put("type", "actionToKnow");
					obj.put("ACITONTOKNOW", map);
				}
				
				//预订须知
				if(Constant.VIEW_CONTENT_TYPE.ORDERTOKNOWN.name().equals(vc.getContentType())){
					map.put("title", "预订须知");
					map.put("content",ClientUtils.filterOutHTMLTags(vc.getContent()));
					map.put("type", "orderToKnown");
					obj.put("ORDERTOKNOWN", map);
				}
				
				// 退款说明
				if(Constant.VIEW_CONTENT_TYPE.REFUNDSEXPLANATION.name().equals(vc.getContentType())){
					map.put("title", "退款说明");
					map.put("content",ClientUtils.filterOutHTMLTags(vc.getContent()));
					map.put("type", "refundsExplanAtion");
					obj.put("REFUNDSEXPLANATION", map);
				}
				// 服务保障
				if(Constant.VIEW_CONTENT_TYPE.SERVICEGUARANTEE.name().equals(vc.getContentType())){
					map.put("title", "服务保障");
					map.put("content", ClientUtils.filterOutHTMLTags(vc.getContent()));
					map.put("type", "serviceGuarantee");
					obj.put("SERVICEGUARANTEE", map);
				}
			}
			
		}
		
		String orderTips = getOrderTips(branchId);
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("title", "今日预定");
		map.put("content",orderTips);
		map.put("type", "orderTips");
		list.add(map);
		resultMap.put("datas", sortList(list,obj));
	}
	
	public List<Map<String,Object>> sortList(List<Map<String,Object>> list,Map<String,Object> maps) {
		String[] sort = {"ACITONTOKNOW","ORDERTOKNOWN","REFUNDSEXPLANATION","SERVICEGUARANTEE","COSTCONTAIN","NOCOSTCONTAIN"};
		
		if(null != maps && !maps.isEmpty()) {
			for(int i = 0 ; i < sort.length;i++) {
				String key = sort[i];
				Map<String,Object> map = (Map<String, Object>) maps.get(key);
				if(null != map && !map.isEmpty()) {
					list.add(map);
				}
			}
		}
		return list;
	}
	
	/**
	 * 景点图片 
	 * @param placeId
	 * @return
	 */
	public List<String> getPlaceImgList(Long placeId) {
		List<String> imageList = new ArrayList<String>();
		if(null != placeId) {
			PlacePhoto pp = new PlacePhoto();
			pp.setType(PlacePhotoTypeEnum.LARGE.name());
			pp.setPlaceId(placeId);
			List<PlacePhoto> ppList =  this.placePhotoService.queryByPlacePhoto(pp);
			if (ppList!=null && ppList.size()!=0) {
				for (PlacePhoto placePhoto : ppList) {
					if(!StringUtil.isEmptyString(placePhoto.getImagesUrl())){
						imageList.add(placePhoto.getImagesUrl());
					}
				}
			}
		}
		return imageList;
	}
	
	/**
	 * 景点活动
	 * @param place
	 * @return mobilePlaceActivity 
	 */
	public List<MobilePlaceActivity> getMobilePlaceActivity(Long placeId) {
		List<MobilePlaceActivity> actList = new ArrayList<MobilePlaceActivity>();
		List<PlaceActivity> placeActList = placeActivityService.queryPlaceActivityListByPlaceId(placeId);
		if(null != placeActList && placeActList.size() > 0) {
			for(int i = 0; i < placeActList.size();i++) {
				PlaceActivity pa = placeActList.get(i);
				if(null != pa && "Y".equals(pa.getIsValid()) && StringUtils.isNotBlank(pa.getTitle())) {
					// 
					if(null != pa.getStartTime() && pa.getStartTime().getTime() > System.currentTimeMillis()) {
						continue;
					}
					
					if(null != pa.getEndTime() && pa.getEndTime().getTime() < System.currentTimeMillis()) {
						continue;
					}
					MobilePlaceActivity mp = new MobilePlaceActivity();
					mp.setTitle(pa.getTitle());
					mp.setPlaceId(pa.getPlaceId());
					mp.setPlaceActivityId(pa.getPlaceActivityId());
					mp.setContentUrl("/html5/placeActivity.htm?placeActivityId="+pa.getPlaceActivityId());
					actList.add(mp);
				}
				if(actList.size() == 2) {
					return actList;
				}
			}
		}
		return actList;
	}
	

	
	public void setPlaceActivityService(PlaceActivityService placeActivityService) {
		this.placeActivityService = placeActivityService;
	}

}
