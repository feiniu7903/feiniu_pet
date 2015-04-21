package com.lvmama.clutter.service.client.v5_0;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lvmama.clutter.service.client.v4_0_1.ClientPlaceServiceImplV401;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.pet.po.prod.ProdTag;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

public class ClientPlaceServiceV50 extends ClientPlaceServiceImplV401 {

	// 获取branch详情
		@Override
		public Map<String,Object> getBranchDetail(Map<String, Object> param) {
			
			ArgCheckUtils.validataRequiredArgs("productId","branchId",param);
			Long productId = Long.valueOf(param.get("productId")+"");
			Long branchId = Long.valueOf(param.get("branchId")+"");
			Map<String,Object> resultMap = new HashMap<String,Object>();
			this.getViewPage(productId,branchId,resultMap);
			return resultMap;
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
			
			// initTagsDesc 初始化 惠 和 抵 
			this.initTagsDesc(resultMap,productId);
			
			String orderTips = getOrderTips(branchId);
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("title", "今日预定");
			map.put("content",orderTips);
			map.put("type", "orderTips");
			list.add(map);
			resultMap.put("contentList", sortList(list,obj));
			//resultMap.put("data", resultMap2);
			
		}
		
		/**
		 * 惠： 对应的优惠内容介绍。 
         * 抵： 对应的奖金抵扣内容介绍。 （如果同时奖金抵扣和积分抵用，请合并显示）
		 */
		public void initTagsDesc(Map<String,Object> resultMap,Long productId) {
			resultMap.put("youhuiTagsDesc","");
			resultMap.put("dikouTagsDesc","");
			if(null == productId) {
				return;
			}
			//惠： 对应的优惠内容介绍。 
			//抵： 对应的奖金抵扣内容介绍。 （如果同时奖金抵扣和积分抵用，请合并显示）
			ProductSearchInfo psi = productSearchInfoService.queryProductSearchInfoByProductId(productId);
			Map<String, List<ProdTag>> tagGroupMap =psi.getTagGroupMap();
			/**
			 *  mainRight.ftl   17行 
			 */
			if(null != tagGroupMap ) {
				if(null != tagGroupMap.get("优惠")) {
					StringBuffer sb = new StringBuffer("");
					for(ProdTag pt:tagGroupMap.get("优惠")) {
						sb.append(pt.getTagName()).append(": ").append(pt.getDescription()).append("\n");
					}
					resultMap.put("youhuiTagsDesc", StringUtil.filterOutHTMLTags(sb.toString().replaceAll("<br>", "\n")));
				}
				
               if(null != tagGroupMap.get("抵扣")) {
            	   StringBuffer sb = new StringBuffer("");
					for(ProdTag pt:tagGroupMap.get("抵扣")) {
						sb.append(pt.getTagName()).append(": ").append(pt.getDescription()).append("\n");
					}
					resultMap.put("dikouTagsDesc", StringUtil.filterOutHTMLTags(sb.toString().replaceAll("<br>", "\n")));
				}
			}
			
			/*if(null != psi) {
				resultMap.put("tagsDesc", psi.getTagsDescript());
			} */
		}
		
		
}
