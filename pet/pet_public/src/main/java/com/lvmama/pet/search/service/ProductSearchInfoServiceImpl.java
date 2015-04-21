package com.lvmama.pet.search.service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.place.PlaceHotelNotice;
import com.lvmama.comm.pet.po.place.PlaceHotelOtherRecommend;
import com.lvmama.comm.pet.po.place.PlaceHotelRoom;
import com.lvmama.comm.pet.po.prod.ProdTag;
import com.lvmama.comm.pet.po.search.ProdBranchSearchInfo;
import com.lvmama.comm.pet.po.search.ProductPlaceSearchInfo;
import com.lvmama.comm.pet.po.search.ProductPropertySearchInfo;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.po.search.Shantou;
import com.lvmama.comm.pet.service.search.ProductSearchInfoService;
import com.lvmama.comm.pet.vo.ProductList;
import com.lvmama.comm.utils.ProductPropertySearchInfoUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.pet.place.dao.PlaceDAO;
import com.lvmama.pet.prod.dao.ProductProductPlaceDAO;
import com.lvmama.pet.search.dao.ProdBranchSearchInfoDAO;
import com.lvmama.pet.search.dao.ProductPropertySearchInfoDAO;
import com.lvmama.pet.search.dao.ProductSearchInfoDAO;

public class ProductSearchInfoServiceImpl implements ProductSearchInfoService {
	/**
	 * 日志输出器
	 */
	private static Logger LOG = Logger.getLogger(ProductSearchInfoServiceImpl.class);
	@Autowired
	private PlaceDAO placeDAO;
	@Autowired
	private ProductProductPlaceDAO productProductPlaceDAO;
	@Autowired
	private ProductSearchInfoDAO productSearchInfoDAO;
	@Autowired
	private ProdBranchSearchInfoDAO prodBranchSearchInfoDAO;
	@Autowired
	private ProductPropertySearchInfoDAO productPropertySearchInfoDAO;
	@Override
	public List<ProductSearchInfo> queryProductSearchInfoByParam(Map<String, Object> param){
		if (null == param || param.isEmpty()) {
			return null;
		}
		if (param.containsKey("fromPlaceId") || param.containsKey("placeId")) {
			param.put("productIds", "1L");
		}
		
		List<ProductSearchInfo> productList =  productSearchInfoDAO.queryProductSearchInfoByParam(param);
		//取ProductSeachInfo上的tag信息由于页面显示
		for(ProductSearchInfo productSearchInfo : productList){
			initTags(productSearchInfo);
		}
		return productList;
	}
	
	@Override
	public Long countProductSearchInfoByParam(Map<String, Object> param) {
		return productSearchInfoDAO.countProductSearchInfoByParam(param);
	}
	
	/**
	 * 批量修改PLACE关联产品的SEQ
	 * 
	 * @param placePhoto
	 * @throws SQLException 
	 */
	@Override
	public void batchSaveProductSeq(String placeProductIds){
		if(StringUtils.isNotBlank(placeProductIds)){
			String[] items=placeProductIds.split(",");
			if(items.length>0){
				for(String item:items){
					String[] product=item.split("_");			
					ProductSearchInfo productSearchInfo = new ProductSearchInfo();
					productSearchInfo.setProductId(Long.parseLong(product[0]));
					productSearchInfo.setSeq(Long.parseLong(product[1]));					
					
					productSearchInfoDAO.updateProductSearchInfo(productSearchInfo);
				}
				
			}
		}
	}
	
	/**
	 * 更新所有点评数量
	 */
	@Override
	public void updateProductSearchInfoCmtNum(){
		productSearchInfoDAO.updateProductSearchInfoCmtNum();
	}

	@Override
	public List<ProductSearchInfo> getProductByFromPlaceIdAndDestId(Map<String,Object> param) {
		if(param==null || param.isEmpty())
			return null;
		param.put("productType", Constant.PRODUCT_TYPE.ROUTE.getCode());
		param.put("isHid", "Y");
		param.put("channel", "FRONTEND");
		List<ProductSearchInfo> list=queryProductSearchInfoByParam(param);
		for(ProductSearchInfo productSearchInfo:list){
			ProductPropertySearchInfo productPropertySearchInfo=productPropertySearchInfoDAO.getProductPropertySearchInfoByProductId(productSearchInfo.getProductId());
			if(productPropertySearchInfo!=null){
				productSearchInfo.setHotelType(ProductPropertySearchInfoUtil.parseProperty(productPropertySearchInfo.getHotelType()));
				productSearchInfo.setPlayNum(ProductPropertySearchInfoUtil.parseProperty(productPropertySearchInfo.getPlayNum()));
				//productSearchInfo.setTravelTime(productPropertySearchInfo.getTravelTime());
			}
			
		}
		return list;
	}

	@Override
	public long countProductByFromPlaceIdAndDestId(Map<String,Object> param) {
		if(param==null||param.isEmpty())
			return 0L;
		param.put("productType", Constant.PRODUCT_TYPE.ROUTE.getCode());
		param.put("isHid", "Y");
		param.put("channel", "FRONTEND");
		if (param.containsKey("fromPlaceId") || param.containsKey("placeId")) {
			param.put("productIds", "1L");
		}
		return countProductSearchInfoByParam(param);
	}

	//自由行
	@Override
	public List<ProductSearchInfo> selectFreenessProductsOfHotel(Long hotelPlaceId) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("productType", "ROUTE");
		param.put("subProductTypes",new String[]{Constant.SUB_PRODUCT_TYPE.FREENESS.name()});
		param.put("channel", "FRONTEND");
		param.put("placeId", hotelPlaceId);
		return queryProductSearchInfoByParam(param);
	}
	//单房型
	public List<ProductSearchInfo> queryProductBranchByPlaceId(Long placeId,String channel,int size){
		List<ProductSearchInfo> result = new ArrayList<ProductSearchInfo>();
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("placeId", placeId);
		param.put("isTicket", "2");
		param.put("channel", channel);
		param.put("orderField", "sellerPrice_asc");
		param.put("startRows", 0);
		param.put("endRows", size);
		//单房型 
		param.put("subProductTypes",new String[]{Constant.SUB_PRODUCT_TYPE.SINGLE_ROOM.name()});
		List<ProductSearchInfo> productSearchInfoList=queryProductSearchInfoByParam(param);
		for (ProductSearchInfo productSearchInfoHotel : productSearchInfoList) {
			Long productId = productSearchInfoHotel.getProductId();
			List<ProdBranchSearchInfo> prodBranchList = prodBranchSearchInfoDAO.getProductBranchByProduct(productId, Boolean.FALSE.toString(), "true","true");
			productSearchInfoHotel.setProdBranchSearchInfoList(prodBranchList);
			result.add(productSearchInfoHotel);
		}
		return result;
	}
	
	//度假酒店 套餐 与 自由行 
	public List<ProductSearchInfo> selectProductsOfHotel(Long hotelPlaceId,String channel){
		Map<String,Object> param=new HashMap<String,Object>();
		//产品为线咱 与 酒店
		param.put("productTypes", new String[]{"ROUTE","HOTEL"});
		//产品类别目的地自由行 与 酒店套餐 
		param.put("subProductTypes",new String[]{Constant.SUB_PRODUCT_TYPE.FREENESS.name(),Constant.SUB_PRODUCT_TYPE.HOTEL_SUIT.name()});
		//销售渠道为 驴妈妈前台 与 团购
		param.put("channel", channel);
		param.put("placeId", hotelPlaceId);
		return queryProductSearchInfoByParam(param);
	}
	
	
	@Override
	public List<ProductSearchInfo> getProductByPlaceIdAndType(long pageSize, long currentPage, long placeId, String isTicket, String stage, String channel) {
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("placeId", placeId);
		param.put("isTicket", isTicket);
		param.put("channel", channel);
		param.put("startRows", currentPage);
		param.put("endRows", pageSize);
		List<ProductSearchInfo> productSearchInfoList=queryProductSearchInfoByParam(param);
		if (productSearchInfoList != null && productSearchInfoList.size() > 0) {
			// 下面是取门票产品的产品类型
			for (ProductSearchInfo viewProductSearchInfo : productSearchInfoList) {
				List<ProdBranchSearchInfo> prodBranchList = prodBranchSearchInfoDAO.getProductBranchByProduct(viewProductSearchInfo.getProductId(), Boolean.FALSE.toString(), "true","true");
				viewProductSearchInfo.setProdBranchSearchInfoList(prodBranchList);
			}
		}
		return productSearchInfoList;
	}
	
	/**
	 * 根据placeiD获取4种不同类型的产品列表
	 * isTicket取值及含义：1：门票             2：酒店              3：自由行             4：跟团游
	 */
	@Override
	public ProductList getProductByPlaceIdAnd4Type(long placeId, long size, String channel) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("placeId", placeId);
		param.put("isTicket", "1");
		param.put("channel", channel);
		param.put("startRows", 0);
		param.put("endRows", size);
		
		ProductList productList = new ProductList();
		List<ProductSearchInfo> productTicket = queryProductSearchInfoByParam(param);
		
		param.put("isTicket", "2");
		productList.setProductTicketList(productTicket);
		List<ProductSearchInfo> productHotel = queryProductSearchInfoByParam(param);
		
		param.put("isTicket", "4");
		productList.setProductHotelList(productHotel);
		List<ProductSearchInfo> productRoute = queryProductSearchInfoByParam(param);
		
		param.put("isTicket", "3");
		productList.setProductRouteList(productRoute);
		List<ProductSearchInfo> productSince = queryProductSearchInfoByParam(param);
		productList.setProductSinceList(productSince);
		return productList;
	}
	
	/**
	 * 门票取的是类别产品的类型,各个类型混合在一起.
	 * 目前发现前台只用到门票和自由行；如果用到hotel和route等在这里添加
	 * 增加一个跟团
	 * @author nixianjun
	 * @param placeId
	 * @param size
	 * @param channel
	 * @deprecated
	 * @return
	 */
	@Override
	public ProductList getIndexProductByPlaceIdAnd4TypeAndTicketBranch(long placeId, long size, String channel) {
		//门票
		ProductList productList = new ProductList();
		List<ProdBranchSearchInfo> productBranchTicket = productSearchInfoDAO.getProductBranchByPlaceIdAndTicket(placeId, "1",channel);
		productList.setProdBranchTicketList(productBranchTicket);
		//门票主产品
		Map<String, Object> pm = new HashMap<String, Object>();
		pm.put("placeId", placeId);
		pm.put("isTicket", "1");
		pm.put("channel", channel);
		List<ProductSearchInfo> productTicketList = productSearchInfoDAO.queryProductSearchInfoByParam(pm);
		productList.setProductTicketList(productTicketList);
		//自由行
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("placeId", placeId);
		param.put("isTicket", "3");
		param.put("channel", channel);
		param.put("startRows", 0);
		param.put("endRows", size);
		List<ProductSearchInfo> productSince = queryProductSearchInfoByParam(param);
		productList.setProductSinceList(productSince);
		//跟团产品
		param.put("isTicket", "4");
		List<ProductSearchInfo> productRouteList= queryProductSearchInfoByParam(param);
		productList.setProductRouteList(productRouteList);
		return productList;
	}
	
	/**
	 *  通过placeid ，产品类型， 产品渠道获取 子产品信息
	 * @author nixianjun
	 * @param placeId
	 * @param isTicket
	 * @param channel
	 * @return
	 */
	@Override
	public List<ProdBranchSearchInfo> getProdBranchSearchInfoByParam(long placeId,String isTicket, String channel) {
		//门票
 		List<ProdBranchSearchInfo> prodBranchSearchInfo = productSearchInfoDAO.getProductBranchByPlaceIdAndTicket(placeId, isTicket,channel);
 		return prodBranchSearchInfo;
 	}

	@Override
	public List<ProductSearchInfo> getEnjoyProductList(Long productId, Long limitRows) {
		if(productId==null) {
			return new ArrayList<ProductSearchInfo>();
		}
		ProductSearchInfo productSearchInfo = productSearchInfoDAO.queryProductSearchInfoByProductId(productId);
		// 1. 根据省份城市查询目的地 2.景区类型 3.酒店
		String channel = Constant.CHANNEL.FRONTEND.name();
		Place comPlace = this.placeDAO.getToDestByProductId(productId);
		if(comPlace==null || comPlace.getPlaceId()==null) {
			return new ArrayList<ProductSearchInfo>();
		}
		
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("placeId", comPlace.getPlaceId());
		param.put("isTicket", productSearchInfo.getIsTicket());
		param.put("channel", channel);
		param.put("startRows", 0);
		param.put("endRows", limitRows);
		return  queryProductSearchInfoByParam(param);
	}

	/**
	 * 取容器产品
	 */
	@Override
    public List<ProductSearchInfo> getContainerProductList(String containerCode, Long fromPlaceId, String toPlaceId, String productType, String[] subProductType, int startRow, int endRow) {
		String subProductTypeStr=StringUtil.arrToStr(subProductType);
		List<ProductSearchInfo> list=productSearchInfoDAO.getContainerProductList(containerCode, fromPlaceId, toPlaceId, productType, subProductTypeStr, startRow, endRow);
		return list;
	}
	

	@Override
	public List<ProductSearchInfo> selectTopSalesList(Long fromPlaceId,String[] subProductType, int topLimit) {
	    String subProductTypeStr = StringUtil.arrToStr(subProductType);
        List<ProductSearchInfo> topSalesList = this.productSearchInfoDAO.selectTopSalesList(fromPlaceId, subProductTypeStr, topLimit);
		return topSalesList;
	}

	/**
	 * 取一个酒店下面有多少产品，每个产品下面有哪些类别的房型；
	 */
	@Override
	public List<ProductSearchInfo> getProductHotelByPlaceIdAndType(long placeId, int size, String channel, boolean reverse) {
		List<ProductSearchInfo> result = new ArrayList<ProductSearchInfo>();
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("placeId", placeId);
		param.put("isTicket", "2");
		param.put("channel", channel);
		if(reverse){
			param.put("orderField", "sellerPrice_desc");
		}else{
			param.put("orderField", "sellerPrice_asc");
		}
		param.put("startRows", 0);
		param.put("endRows", size);
		List<ProductSearchInfo> productSearchInfoList=queryProductSearchInfoByParam(param);
		for (ProductSearchInfo productSearchInfoHotel : productSearchInfoList) {
			Long productId = productSearchInfoHotel.getProductId();
			List<ProdBranchSearchInfo> prodBranchList = prodBranchSearchInfoDAO.getProductBranchByProduct(productId, Boolean.FALSE.toString(), "true","true");
			productSearchInfoHotel.setProdBranchSearchInfoList(prodBranchList);
			result.add(productSearchInfoHotel);
		}
		return result;
	}
	
	@Override
	public List<Shantou> selectShantouListByParam(Map<String, Object> parameters) {
		return  productSearchInfoDAO.queryProductShanTouList(parameters);
	}

	@Override
	public Long countSelectShantouListByParam(Map<String, Object> parameters){
		return productSearchInfoDAO.queryCountByProductShantou(parameters);
	}
	@Override
	public ProductSearchInfo queryProductSearchInfoByProductId(Long productId){
		ProductSearchInfo productSearchInfo = productSearchInfoDAO.queryProductSearchInfoByProductId(productId);
		initTags(productSearchInfo);
		return productSearchInfo;
	}
	
	/**
	 * 查询景点门票表
	 */
	@Override
	public List<ProductPlaceSearchInfo> queryPlaceProdctSearchInfoByParam(Map<String,Object> parameters) {
		return productSearchInfoDAO.queryProductPlaceList(parameters);
	}
	
	/**
	 * 景点门票总数量
	 */
	@Override
	public Long queryPlaceProdctSearchInfoByCount() {
		return productSearchInfoDAO.queryProductPlaceCount();
	}
	
	
	
	/**
	 * 初始化标签信息
	 * @param productSearchInfo
	 */
	private void initTags(ProductSearchInfo productSearchInfo){
		if(productSearchInfo !=null ){
			List<ProdTag>tagList = new ArrayList<ProdTag>();
			Map<String,List<ProdTag>>tagGroupMap =new HashMap<String, List<ProdTag>>();
			String tagsNameStr = productSearchInfo.getTagsName();
			if(StringUtils.isNotBlank(tagsNameStr)){
				String[] tagsName = tagsNameStr.split(",");
				String[] tagsDescript = productSearchInfo.getTagsDescript().split(",");
				String[] tagsCss= productSearchInfo.getTagsCss().split(",");
				String[] tagsGroup= productSearchInfo.getTagsGroup().split(",");
				if(tagsName.length == tagsDescript.length && tagsName.length == tagsCss.length && tagsName.length == tagsGroup.length){
					for(int i = 0 ;i < tagsName.length;i ++){
						String tagName = tagsName[i];
						//去掉TAGNAME后面拼接的"~拼音"
						if(tagsName[i].indexOf("~")!=-1){
							tagName=tagsName[i].substring(0,tagsName[i].indexOf("~"));
						}
						ProdTag pt = new ProdTag();
						pt.setTagName(tagName);
						String description = tagsDescript[i];
						if(StringUtils.isNotBlank(description)){
							pt.setDescription(description);
						}else{
							pt.setDescription("");
						}
						pt.setCssId(tagsCss[i]);
						String tagGroup=tagsGroup[i];
						pt.setTagGroupName(tagGroup);
						List<ProdTag> tagGroupList = tagGroupMap.get(tagGroup);
						if( tagGroupList == null ){
							tagGroupList = new ArrayList<ProdTag>();
							tagGroupMap.put(tagGroup,tagGroupList);
						}
						tagGroupList.add(pt);
						tagList.add(pt);
					}
				}else{
					LOG.warn("product's tags length is error. productid:"+productSearchInfo.getProductId());
				}
			}
			productSearchInfo.setTagList(tagList);
			productSearchInfo.setTagGroupMap(tagGroupMap);
		}
	}
	
	public ProdBranchSearchInfo getProdBranchSearchInfoByBranchId(Long branchId) {
		return this.productSearchInfoDAO.getProdBranchSearchInfoByBranchId(branchId);
	}
	
	@Override
	public List<PlaceHotelRoom> getPlaceHotelRoom(Long placeId) {
		return this.productSearchInfoDAO.getPlaceHotelRoom(placeId);
	}

	@Override
	public List<PlaceHotelNotice> getPlaceHotelNotice(Long placeId) {
		return this.productSearchInfoDAO.getPlaceHotelNotice(placeId);
	}

	@Override
	public List<PlaceHotelOtherRecommend> getPlaceHotelRecommend(Long placeId) {
		return this.productSearchInfoDAO.getPlaceHotelRecommend(placeId);
	}


}
