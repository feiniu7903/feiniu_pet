package com.lvmama.pet.search.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.pet.po.prod.ProdContainer;
import com.lvmama.comm.pet.po.prod.ProdTag;
import com.lvmama.comm.pet.po.search.PlaceSearchInfo;
import com.lvmama.comm.pet.po.search.ProductSearchInfo;
import com.lvmama.comm.pet.service.search.PlaceSearchInfoService;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.PlaceSearchInfoDTO;
import com.lvmama.pet.prod.dao.ProdContainerDAO;
import com.lvmama.pet.search.dao.PlaceSearchInfoDAO;
import com.lvmama.pet.search.dao.ProductSearchInfoDAO;

public class PlaceSearchInfoServiceImpl implements PlaceSearchInfoService {
	/**
	 * 日志输出器
	 */
	private static Logger LOG = Logger.getLogger(ProductSearchInfoServiceImpl.class);
	@Autowired
	private PlaceSearchInfoDAO placeSearchInfoDAO;
	@Autowired
	private ProductSearchInfoDAO productSearchInfoDAO;
	@Autowired
	private ProdContainerDAO prodContainerDAO;
	
	/**
	 * 获取周边游频道的map数据
	 */
	public Map<String, Object> getAroundChannelData(Long fromPlaceId, String toPlaceId){
		Map<String, Object> map = new HashMap<String, Object>();
		List<ProductSearchInfo> productListAroundOnSale = productSearchInfoDAO.getContainerProductList("ON_SALE", fromPlaceId,null, "ROUTE", StringUtil.arrToStr(new String[]{"GROUP", "SELFHELP_BUS"}), 1, 10);
		List<ProductSearchInfo> productListAroundNewArrival = productSearchInfoDAO.getContainerProductList("NEW_ARRIVAL",  fromPlaceId,null, "ROUTE", StringUtil.arrToStr(new String[]{"GROUP", "SELFHELP_BUS"}), 1, 10);
		List<ProductSearchInfo> aroundSalesList = selectTopSalesList(fromPlaceId, new String[]{"GROUP", "SELFHELP_BUS"}, 10);
		List<ProdContainer> kxlxPlaceList = prodContainerDAO.selectToPlaces("KXLX", fromPlaceId, "3548", true);
		String kxlxDefaultToPlace = null;
		if (!kxlxPlaceList.isEmpty()) {
			 kxlxDefaultToPlace = kxlxPlaceList.get(0).getToPlaceId();
		}
		map.put("productListAroundOnSale", productListAroundOnSale);
		map.put("productListAroundNewArrival", productListAroundNewArrival);
		map.put("aroundSalesList", aroundSalesList);
		map.put("kxlxPlaceList", kxlxPlaceList);
		map.put("kxlxDefaultToPlace", kxlxDefaultToPlace);
		return map;
	}
	/**
	 * 获取门票频道页中数据
	 */
	@Override
	public Map<String, Object> getTicketChannelData() {
		Map<String,Object> result = new HashMap<String, Object>();
		result.put("ticketPlaceListShanghai",getPlacesOrderByCmt(null, 79L, "1", "2", 10, 10, "FRONTEND"));
		result.put("ticketPlaceListZhejiang", getPlacesOrderByCmt(96L, null, "1", "2", 10, 10, "FRONTEND"));
		result.put("ticketPlaceListJiangsu", getPlacesOrderByCmt(80L, null, "1", "2", 10, 10, "FRONTEND"));
		result.put("ticketPlaceListGuangdong", getPlacesOrderByCmt(228L, null, "1", "2", 10, 10, "FRONTEND"));
		result.put("ticketPlaceListSichuan", getPlacesOrderByCmt(278L, null, "1", "2", 10, 10, "FRONTEND"));
		result.put("ticketPlaceListBeijing", getPlacesOrderByCmt(null, 1L, "1", "2", 10, 10, "FRONTEND"));
		result.put("ticketPlaceListAnhui", getPlacesOrderByCmt(118L, null, "1", "2", 10, 10, "FRONTEND"));
		result.put("productSearchInfoList",productSearchInfoDAO.selectTicketProductTopData());
		return result;
	}
	private List<PlaceSearchInfoDTO> getPlacesOrderByCmt(Long provinceId, Long cityId, String isTicket, String stage, int plSize, int pdSize, String channel) {
		List<PlaceSearchInfoDTO> dtoList = new ArrayList<PlaceSearchInfoDTO>();
		List<PlaceSearchInfo> placeSearchInfoList = placeSearchInfoDAO.queryPlaceSearchInfoListByCmt(provinceId, cityId, stage, 100);
		if(placeSearchInfoList!=null && placeSearchInfoList.size() > 0){
			for (PlaceSearchInfo placeSearchInfo : placeSearchInfoList) {
				//初始化标签信息
				initTags(placeSearchInfo);
				long shortId = placeSearchInfo.getPlaceId();
				processAvgScore(placeSearchInfo);
				
				Map<String, Object> param = new HashMap<String, Object>();
				param.put("placeId", shortId);
				param.put("isTicket", isTicket);
				param.put("channel", channel);
				param.put("startRows", 0);
				param.put("endRows", pdSize);
				List<ProductSearchInfo> productSearchInfoList = productSearchInfoDAO.queryProductSearchInfoByParam(param);
				
				PlaceSearchInfoDTO placeSearchInfoDTO = new PlaceSearchInfoDTO();
				placeSearchInfoDTO.setPlaceSearchInfo(placeSearchInfo);
				placeSearchInfoDTO.setProductSearchInfoList(productSearchInfoList);
				dtoList.add(placeSearchInfoDTO);
				if (dtoList.size() == plSize) {
					break;
				}
			}
		}
		return dtoList;
	}
	private void processAvgScore(PlaceSearchInfo placeSearchInfo){
		if(placeSearchInfo.getCmtAvgScore()!=null){
			if(placeSearchInfo.getCmtAvgScore().floatValue()==0.5){
				placeSearchInfo.setCmtAvgScoreStr("05");
			}else if(placeSearchInfo.getCmtAvgScore().floatValue()==1){
				placeSearchInfo.setCmtAvgScoreStr("1");
			}else if(placeSearchInfo.getCmtAvgScore().floatValue()==1.5){
				placeSearchInfo.setCmtAvgScoreStr("15");
			}else if(placeSearchInfo.getCmtAvgScore().floatValue()==2){
				placeSearchInfo.setCmtAvgScoreStr("2");
			}else if(placeSearchInfo.getCmtAvgScore().floatValue()==2.5){
				placeSearchInfo.setCmtAvgScoreStr("25");
			}else if(placeSearchInfo.getCmtAvgScore().floatValue()==3){
				placeSearchInfo.setCmtAvgScoreStr("3");
			}else if(placeSearchInfo.getCmtAvgScore().floatValue()==3.5){
				placeSearchInfo.setCmtAvgScoreStr("35");
			}else if(placeSearchInfo.getCmtAvgScore().floatValue()==4){
				placeSearchInfo.setCmtAvgScoreStr("4");
			}else if(placeSearchInfo.getCmtAvgScore().floatValue()==4.5){
				placeSearchInfo.setCmtAvgScoreStr("45");
			}else if(placeSearchInfo.getCmtAvgScore().floatValue()==5){
				placeSearchInfo.setCmtAvgScoreStr("5");
			}
		}
	}
	public List<ProductSearchInfo> selectTopSalesList(Long fromPlaceId,String[] subProductType, int topLimit) {
	    String subProductTypeStr = StringUtil.arrToStr(subProductType);
        List<ProductSearchInfo> topSalesList = this.productSearchInfoDAO.selectTopSalesList(fromPlaceId,subProductTypeStr, topLimit);
		return topSalesList;
	}
	
	@Override
	public List<PlaceSearchInfo> queryPlaceSearchInfoByParam(Map<String, Object> param) {
		if (null == param || param.isEmpty()) {
			return null;
		}
		List<PlaceSearchInfo> list =  placeSearchInfoDAO.queryPlaceSearchInfoByParam(param);
		for(PlaceSearchInfo placeSearchInfo : list){
			initTags(placeSearchInfo);
		}
		return list;
	}
	
	@Override
	public PlaceSearchInfo getPlaceSearchInfoByPlaceId(Long placeId)
	{
		PlaceSearchInfo placeSearchInfo =  placeSearchInfoDAO.getPlaceSearchInfoByPlaceId(placeId);
		initTags(placeSearchInfo);
		return placeSearchInfo;
	}

	@Override
	public List<PlaceSearchInfo> getPlaceInfoFor360(String placeName) {
		return placeSearchInfoDAO.getPlaceInfoFor360(placeName);
	}
	
	private void initTags(PlaceSearchInfo placeSearchInfo){
		if(placeSearchInfo!=null){
			List<ProdTag>tagList = new ArrayList<ProdTag>();
			Map<String,List<ProdTag>>tagGroupMap =new HashMap<String, List<ProdTag>>();
			String tagsNameStr = placeSearchInfo.getDestTagsName();
			if(StringUtils.isNotBlank(tagsNameStr)){
				String[] tagsName = tagsNameStr.split(",");
				String[] tagsDescript = placeSearchInfo.getDestTagsDescript().split(",");
				String[] tagsCss= placeSearchInfo.getDestTagsCss().split(",");
				String[] tagsGroup= placeSearchInfo.getDestTagsGroup().split(",");
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
					LOG.warn("place tags length is error. placeid:"+placeSearchInfo.getPlaceId());
				}
			}
			placeSearchInfo.setTagList(tagList);
			placeSearchInfo.setTagGroupMap(tagGroupMap);
		}
	}
}
