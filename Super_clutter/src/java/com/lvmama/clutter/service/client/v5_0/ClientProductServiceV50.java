package com.lvmama.clutter.service.client.v5_0;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;

import com.lvmama.clutter.exception.NotFoundException;
import com.lvmama.clutter.model.MobileProductRoute;
import com.lvmama.clutter.model.MobileProductTitle;
import com.lvmama.clutter.service.client.v4_0.ClientProductServiceV40;
import com.lvmama.clutter.utils.ArgCheckUtils;
import com.lvmama.clutter.utils.ClientUtils;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.pet.po.comment.CmtLatitudeStatistics;
import com.lvmama.comm.pet.po.mobile.MobileFavorite;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.user.UserUser;
import com.lvmama.comm.utils.PriceUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.ProdCProduct;
import com.lvmama.comm.vo.ProductResult;
import com.lvmama.comm.vo.comment.CmtTitleStatisticsVO;
import com.lvmama.comm.vo.comment.PlaceCmtScoreVO;

public class ClientProductServiceV50 extends ClientProductServiceV40{

	@Override
	public Map<String, Object> getProductItems(Map<String, Object> param) {
		// TODO Auto-generated method stub
		Map<String, Object> map = super.getProductItems(param);
		if(Boolean.valueOf(map.get("isEContract").toString())==true){
			map.put("needEcontractEmail", true);
			map.put("econtractDes", "协议将在资源确认且支付后生效。驴妈妈品质保证，请放心预订！");
			Long productId = (Long) map.get("productId");
			if(productId!=null) {
				map.put("xieyiUrl", "http://www.lvmama.com/econtract/"+productId);
				map.put("xieyiName", "旅游预订合同协议");
			}
		}
		
		return map;
	}
	
	/**
	 * 景点相关自由行 
	 */
	@Override
	public List<MobileProductTitle> getPlaceRoutes(Map<String, Object> param) {
		ArgCheckUtils.validataRequiredArgs("placeId",param);
		Map<String,Object> queryMap  = new HashMap<String,Object>();
		queryMap.put("size", 1000);
		queryMap.put("placeId", param.get("placeId"));
		queryMap.put("channel", Constant.CHANNEL.CLIENT.name());
		List<ProductSearchInfo> searchInfoList =  productSearchInfoService.getProductByFromPlaceIdAndDestId(param);
		// 初始化数据 
		List<MobileProductTitle> mpList = this.inintPlaceRoutesInfo(searchInfoList);
		return mpList;
	}
	
	
	/**
	 * 查询线路（自由行）详情 .
	 * @throws Exception 
	 */
	@Override
	public MobileProductRoute getRouteDetail(Map<String, Object> params) throws Exception {
		ArgCheckUtils.validataRequiredArgs("productId",params);
		Long productId = Long.valueOf(params.get("productId") + "");
		MobileProductRoute mpr = new MobileProductRoute(); // 自由行线路. 
		// 检测产品是否存在.
		ProductResult pr=pageService.findProduct(productId);
		if(!pr.isExists()) {
			throw new NotFoundException("产品不可售");
		}
		// 获取产品信息. 
		Map<String, Object>  data = pageService.getProdCProductInfo(productId, false);
		
		// 加载产品图片. 
		if(data != null && data.get("viewPage") != null) {
			ViewPage record = (ViewPage)data.get("viewPage");
			//图片列表. 
			List<ComPicture> list = this.comPictureService.getPictureByPageId(record.getPageId());
			List<String> imageList = new ArrayList<String>();
			if (list!=null) {
				for (ComPicture comPicture : list) {
					imageList.add(comPicture.getPictureUrl());
				}
			}
			mpr.setImageList(imageList);
			
			// 产品经理推荐 
			for (int i=0;i<record.getContentList().size();i++) {
				ViewContent viewContent  = record.getContentList().get(i);
				if(viewContent.getContentType().equals(Constant.VIEW_CONTENT_TYPE.MANAGERRECOMMEND.name())) {
					//mpr.setManagerRecommend(StringUtil.filterOutHTMLTags(viewContent.getContent()));
					mpr.setManagerRecommend(viewContent.getContent());
				} else if(viewContent.getContentType().equals(Constant.VIEW_CONTENT_TYPE.ANNOUNCEMENT.name())){
					mpr.setAnnouncement(StringUtil.filterOutHTMLTags(viewContent.getContent()));
				}
			}
		}
		
		// 相关产品推荐信息
		ProdCProduct prodCProduct = (ProdCProduct)data.get("prodCProduct");
		
		if(!prodCProduct.getProdProduct().isOnLine()){
			throw new NotFoundException("产品不可售");
		}
		
		ProdRoute route = prodCProduct.getProdRoute(); // 路线
		// 出发地 /目的地
		if(null != route) {
			mpr.setVisitDay(null == route.getDays()?"":route.getDays()+""); // 天数
			Place from = prodCProduct.getFrom(); // 出发地
			if(null != prodCProduct.getProdProduct() &&
					(Constant.SUB_PRODUCT_TYPE.FREENESS.getCode().equals(prodCProduct.getProdProduct().getSubProductType())
					||Constant.SUB_PRODUCT_TYPE.FREENESS_FOREIGN.getCode().equals(prodCProduct.getProdProduct().getSubProductType())
					||Constant.SUB_PRODUCT_TYPE.FREENESS_LONG.getCode().equals(prodCProduct.getProdProduct().getSubProductType())
			        )) {
				mpr.setFromDest("各地");
			} else {
				mpr.setFromDest(route.getRouteFrom());
				if(StringUtils.isEmpty(mpr.getFromDest()) && null != from ) {
					mpr.setFromDest(from.getName());
				}
			}
			
			from = prodCProduct.getTo(); // 目的 地
			mpr.setToDest(route.getRouteTo());
			if(StringUtils.isEmpty(mpr.getToDest()) && null != from ) {
				mpr.setToDest(from.getName());
			}
		}
		// 产品 
		ProdProduct product = prodCProduct.getProdProduct(); // 产品 
		if(null != product) {
			mpr.setProductId(product.getProductId());
			mpr.setProductName(product.getProductName());
			mpr.setMarketPrice(product.getMarketPrice());
			mpr.setSellPrice(product.getSellPrice());
			//mpr.setManagerRecommend(product.getManagerRecommend());;
			mpr.setSmallImage(product.getSmallImage());
			// 是否收藏.
			mpr.setHasIn(false); // 默认false 
			
			// V3.1.0
			mpr.setSubProductType(product.getSubProductType());// 主题类型
			
			if(params.get("userNo")!=null){
				String userId  = params.get("userNo").toString();
				if(!StringUtil.isEmptyString(userId)){
					UserUser user =  userUserProxy.getUserUserByUserNo(userId);
					if(user != null){
						Map<String,Object> p = new HashMap<String,Object>();
						p.put("objectId", product.getProductId());
						p.put("userId", user.getId());
						List<MobileFavorite> queryMobileFavoriteLis = mobileFavoriteService.queryMobileFavoriteList(p);
						if(null != queryMobileFavoriteLis && queryMobileFavoriteLis.size() > 0) {
							mpr.setHasIn(true);
						}
					}	
				}
			}
		}
		// 点评信息
		CmtTitleStatisticsVO productCommentStatistics = cmtTitleStatistisService.getCmtTitleStatisticsByProductId(productId);
		if(null != productCommentStatistics) {
			mpr.setAvgScore(Float.valueOf(productCommentStatistics.getAvgScoreStr()));
			mpr.setCmtNum(Integer.valueOf(null == productCommentStatistics.getCommentCount()?"":productCommentStatistics.getCommentCount()+""));
		}
		
		//获得某个产品的点评维度统计平均分
		Map<String,Object> parameters = new HashMap<String, Object>();
		parameters.put("productId", productId);
		List<CmtLatitudeStatistics> cmtLatitudeStatisticsList = cmtLatitudeStatistisService.getLatitudeStatisticsList(parameters);
		List<PlaceCmtScoreVO> pcsVoList = new ArrayList<PlaceCmtScoreVO>();
		for (CmtLatitudeStatistics cmtLatitudeStatistics : cmtLatitudeStatisticsList) {
		    	PlaceCmtScoreVO pcv = new PlaceCmtScoreVO();
		    	pcv.setName(cmtLatitudeStatistics.getLatitudeName());
		    	pcv.setScore(null == cmtLatitudeStatistics.getAvgScore()?"":cmtLatitudeStatistics.getAvgScore()+"");
		    	if(cmtLatitudeStatistics.getLatitudeId().equals("FFFFFFFFFFFFFFFFFFFFFFFFFFFF")){
		    		pcv.setMain(true);
		    	}
		    	pcsVoList.add(pcv);
		}	    
		mpr.setPlaceCmtScoreList(pcsVoList);
		ProdProductBranch ppb = prodProductService.getProductDefaultBranchByProductId(productId);
		mpr.setBranchId(ppb.getProdBranchId());	
		/********** v3.1 ******************/
		// 初始化tag标签信息  
		Map<String, Object> param =new HashMap<String,Object>();
		param.put("productId",productId);
		List<ProductSearchInfo> productSearchInfoList=productSearchInfoService.queryProductSearchInfoByParam(param);
		//优惠信息
		ClientUtils.initProductSearchInfos(productSearchInfoList, mpr);
		//返现信息 
		mpr.setMaxCashRefund(product.getMaxCashRefund());
		// 是否需要签证 - 只对境外线路有效
		mpr.setVisa(ClientUtils.needVisa(prodCProduct));
		mpr.setProductType(product.getProductType());
		
		
		return mpr;
	}

	
	
	
	/**
	 * 初始化景点自由行列表 
	 * @param searchInfoList
	 * @return 
	 */
	protected List<MobileProductTitle> inintPlaceRoutesInfo(List<ProductSearchInfo> searchInfoList) {
		List<MobileProductTitle> mpList = new ArrayList<MobileProductTitle>();
		for (ProductSearchInfo productSearchInfo : searchInfoList) {
			if(productSearchInfo.hasAperiodic()){
				/**
				 * 过滤期票
				 */
				continue;
			}
			if("true".equals(productSearchInfo.getSelfPack())){
				continue;
			}
			MobileProductTitle mp = new MobileProductTitle();
			
			BeanUtils.copyProperties(productSearchInfo, mp);
			mp.setMarketPriceYuan(PriceUtil.convertToYuan(productSearchInfo.getMarketPrice()));
			mp.setSellPriceYuan(PriceUtil.convertToYuan(productSearchInfo.getSellPrice()));
			mp.setSmallImage(productSearchInfo.getSmallImage());
			mp.setProductId(productSearchInfo.getProductId());
			
			/***** V3.1 *****/
			mp.setMaxCashRefund(StringUtils.isEmpty(productSearchInfo.getCashRefund())?0l:PriceUtil.convertToFen(productSearchInfo.getCashRefund()));
			//mp.setHasBusinessCoupon(ClientUtils.hasBusinessCoupon(productSearchInfo)); // 优惠 
			// 初始化优惠，抵扣等信息 
			ClientUtils.initMobileProductTitle4V50(productSearchInfo,mp);
			mpList.add(mp);
		}
		
		return mpList;
	}

}
