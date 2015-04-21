package com.lvmama.businessreply.job;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;

import com.lvmama.businessreply.po.EtaoProduct;
import com.lvmama.businessreply.utils.etao.GroupBuyBuildFactory;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.pet.po.prod.ProdProductPlace;
import com.lvmama.comm.pet.po.prod.ProdProductTag;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;

import com.lvmama.comm.bee.service.prod.PageService;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.service.prod.ProdProductTagService;

public class EtaoAutoBuildXmlJob  implements Runnable {
	private static final Logger LOG = Logger.getLogger(EtaoAutoBuildXmlJob.class);
	private ProdProductPlaceService prodProductPlaceService;
	private ProductSearchInfoService productSearchInfoService;
	private PageService pageService;
	private ComPictureService comPictureService;
	private ProdProductTagService prodProductTagService;
	@Override
	public void run() {
		if (Constant.getInstance().isJobRunnable()) {
			String basePath = Constant.getInstance().getProperty("etao_path");
			
			Map<String,Object> parameters = new HashMap<String,Object>();
			parameters.put("onLine", "true");
			parameters.put("isValid", "Y");
		    parameters.put("channel", Constant.CHANNEL.FRONTEND.name());
		    String[] productTypes = {Constant.PRODUCT_TYPE.TICKET.name(),Constant.PRODUCT_TYPE.ROUTE.name(),Constant.PRODUCT_TYPE.HOTEL.name()};
		    parameters.put("productTypes", productTypes);
    		parameters.put("productIds","Y");
			Integer count = productSearchInfoService.countProductSearchInfoByParam(parameters).intValue();
			Integer rowCount = 900;
			Integer pageCount = getTotalPages(count, rowCount);
			for(int curPage =1;curPage<=pageCount;curPage++){
				int startRow = (curPage - 1) * rowCount + 1;
				int endRow = curPage * rowCount;
				parameters.put("startRows", startRow);
				parameters.put("endRows", endRow);
				List<EtaoProduct> results = getEtaoProductInfo(parameters);
				GroupBuyBuildFactory.buildXMLForEtao(results, pageCount, curPage, basePath);
			}
		}
	}
	private List<EtaoProduct> getEtaoProductInfo(final Map<String,Object> parameters){
		List<ProductSearchInfo> prodList=productSearchInfoService.queryProductSearchInfoByParam(parameters);
		List<EtaoProduct> gList = new ArrayList<EtaoProduct>();
		for (ProductSearchInfo p : prodList) {
			EtaoProduct obj = initBaseYitaoInfo(p);
			if(null!=obj){
				// 标签
				ininTagList(p.getProductId(),obj);
				gList.add(obj);
			}
		}
		return gList;
	}
	/**
	 * 获取产品标签
	 * @param prodProduct
	 * @return
	 */
	private void ininTagList(final Long productId,final EtaoProduct obj) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("productId", productId);
		List<ProdProductTag> tagList = prodProductTagService.selectByParams(param);
		List<ProdProductPlace> placeList = prodProductPlaceService.selectByProductId(productId);
		List<String> strList = new ArrayList<String>();
		if(null!=placeList && !placeList.isEmpty()){
			for(int i=0;i<placeList.size()&&i<3;i++){
				strList.add(placeList.get(i).getPlaceName());
			}
		}
		if(tagList != null && !tagList.isEmpty()) {
			for(int i=0;i<tagList.size()&&i<2;i++){
				strList.add(tagList.get(i).getTagName());
			}
		}else{
			strList.add("旅游");
			strList.add("驴妈妈");
		}
		obj.setTags(strList);
	}
	/**
	 * 获取一淘所需产品的基本信息
	 *
	 */
	private EtaoProduct initBaseYitaoInfo(ProductSearchInfo prodProduct) {
		EtaoProduct etaoProduct = new EtaoProduct();
		etaoProduct.setProductId(prodProduct.getProductId());
		etaoProduct.setProductName(prodProduct.getProductName());
		etaoProduct.setSellPrice(prodProduct.getSellPriceInteger());
		etaoProduct.setSubProductType(prodProduct.getSubProductType());
		String recommendInfo = prodProduct.getRecommendInfoSecond();
		if(StringUtil.isEmptyString(recommendInfo)){
			recommendInfo =prodProduct.getCostcontain();
		}
		if(StringUtil.isEmptyString(recommendInfo)){
			recommendInfo = prodProduct.getRecommendInfoFirst();
		}
		if(StringUtil.isEmptyString(recommendInfo)){
			recommendInfo = prodProduct.getRecommendInfoThird();
		}
		etaoProduct.setRecommandInfo(recommendInfo);
		Long productId = prodProduct.getProductId();
		ViewPage viewPage = pageService.getViewPageByproductId(productId);
		if(null!=viewPage){
			Long pageId = viewPage.getPageId();
			List<ComPicture> pictures = comPictureService.getPictureByPageId(pageId);
			if(null!=pictures && !pictures.isEmpty() && pictures.size()>0){
				etaoProduct.setPictureUrl(pictures.get(0).getAbsoluteUrl());
				etaoProduct.setPictures(pictures);
				return etaoProduct;
			}
			if(StringUtil.isEmptyString(recommendInfo)){
				Map<String,Object> contents = viewPage.getContents();
				if(null!=contents && !contents.isEmpty() && contents.size()>0){
					Object obj = contents.get("MANAGERRECOMMEND");
					if(null!=obj){
						recommendInfo = ((ViewContent)obj).getContent();
					}
				}
			}
		}
		return null;
	}

	/**
	 * @param totalResultSize
	 * @param pageSize
	 * @return
	 */
	public int getTotalPages(int totalResultSize, int pageSize) {
		if (totalResultSize % pageSize > 0)
			return totalResultSize / pageSize + 1;
		else
			return totalResultSize / pageSize;
	}

	public void setProdProductTagService(ProdProductTagService prodProductTagService) {
		this.prodProductTagService = prodProductTagService;
	}
	public void setPageService(PageService pageService) {
		this.pageService = pageService;
	}
	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}
	public void setProdProductPlaceService(
			ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}
	public void setProductSearchInfoService(
			ProductSearchInfoService productSearchInfoService) {
		this.productSearchInfoService = productSearchInfoService;
	}
	
}
