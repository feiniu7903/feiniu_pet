package com.lvmama.ebk.service;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.itextpdf.tool.xml.css.CSS.Value;
import com.lvmama.comm.bee.po.ebooking.EbkMultiJourney;
import com.lvmama.comm.bee.po.ebooking.EbkProdBranch;
import com.lvmama.comm.bee.po.ebooking.EbkProdContent;
import com.lvmama.comm.bee.po.ebooking.EbkProdJourney;
import com.lvmama.comm.bee.po.ebooking.EbkProdModelProperty;
import com.lvmama.comm.bee.po.ebooking.EbkProdPlace;
import com.lvmama.comm.bee.po.ebooking.EbkProdProduct;
import com.lvmama.comm.bee.po.ebooking.EbkProdRelation;
import com.lvmama.comm.bee.po.ebooking.EbkProdSnapshot;
import com.lvmama.comm.bee.po.ebooking.EbkProdTarget;
import com.lvmama.comm.bee.po.ebooking.EbkProdTimePrice;
import com.lvmama.comm.bee.po.prod.ProductModelProperty;
import com.lvmama.comm.bee.po.prod.ProductModelType;
import com.lvmama.comm.bee.service.ebooking.EbkMultiJourneyService;
import com.lvmama.comm.bee.service.ebooking.EbkProdProductService;
import com.lvmama.comm.bee.service.ebooking.EbkProdSnapshotService;
import com.lvmama.comm.bee.service.prod.ProductModelPropertyService;
import com.lvmama.comm.bee.service.prod.ProductModelTypeService;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.utils.JsonUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.EBK_CHANGED_ITEM;
import com.lvmama.ebk.dao.EbkProdSnapshotDAO;

public class EbkProdSnapshotServiceImpl implements EbkProdSnapshotService{
	
	
	private EbkProdSnapshotDAO ebkProdSnapshotDAO;
	
	private ProductModelPropertyService productModelPropertyService;
	
	private ProductModelTypeService productModelTypeService;
	
	private EbkMultiJourneyService ebkMultiJourneyService;
	
	private static Map<String,Class<?>> EBK_PROD_CHILD_LIST=new HashMap<String,Class<?>>();
	
	public EbkProdSnapshotServiceImpl(){
		if(EBK_PROD_CHILD_LIST.isEmpty()){
			EBK_PROD_CHILD_LIST.put("ebkProdContents", EbkProdContent.class);
			EBK_PROD_CHILD_LIST.put("ebkProdPlaces", EbkProdPlace.class);
			EBK_PROD_CHILD_LIST.put("ebkProdTargets", EbkProdTarget.class);
			EBK_PROD_CHILD_LIST.put("ebkProdJourneys", EbkProdJourney.class);
			EBK_PROD_CHILD_LIST.put("ebkMultiJourneys", EbkMultiJourney.class);
			EBK_PROD_CHILD_LIST.put("ebkProdModelPropertys", EbkProdModelProperty.class);
			EBK_PROD_CHILD_LIST.put("ebkProdBranchs", EbkProdBranch.class);
			EBK_PROD_CHILD_LIST.put("ebkProdTimePrices", EbkProdTimePrice.class);
			EBK_PROD_CHILD_LIST.put("comPictureJourneyList", ComPicture.class);
			EBK_PROD_CHILD_LIST.put("ebkProdRelations", EbkProdRelation.class);
		}
	}
	
	/**
	 * 保存产品快照
	 */
	public Long saveProdSnapshot(EbkProdProduct ebkProdProduct){
		try {
			String ebkProdProductJSON=JsonUtil.getJsonString4JavaPOJO(ebkProdProduct);
			EbkProdSnapshot ebkProdSnapshotDO=new EbkProdSnapshot();
			ebkProdSnapshotDO.setProductId(ebkProdProduct.getEbkProdProductId());
			ebkProdSnapshotDO.setContent(ebkProdProductJSON);
			return ebkProdSnapshotDAO.insertEbkProdSnapshotDO(ebkProdSnapshotDO);	
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 删除最近的一个产品快照
	 */
	public int deleteProdSnapshotByLast(Long productId){
		EbkProdSnapshot ebkProdSnapshotDO=new EbkProdSnapshot();
		ebkProdSnapshotDO.setProductId(productId);
		List<EbkProdSnapshot> ebkProdSnapshotList=ebkProdSnapshotDAO.findListByDOAndOrderByProdSnapshotId(ebkProdSnapshotDO);
		if(ebkProdSnapshotList!=null && ebkProdSnapshotList.size()>0){
			return ebkProdSnapshotDAO.deleteEbkProdSnapshotDOByPrimaryKey(ebkProdSnapshotList.get(0).getProdSnapshotId());
		}
		return 0;	
	}
	/**
	 * 获取最近的一个产品快照
	 */
	public EbkProdSnapshot getProdSnapshotByLast(Long productId){
		EbkProdSnapshot ebkProdSnapshotDO=new EbkProdSnapshot();
		ebkProdSnapshotDO.setProductId(productId);
		List<EbkProdSnapshot> ebkProdSnapshotList=ebkProdSnapshotDAO.findListByDOAndOrderByProdSnapshotId(ebkProdSnapshotDO);
		if(ebkProdSnapshotList!=null && ebkProdSnapshotList.size()>0){
			return ebkProdSnapshotList.get(0);
		}
		return new EbkProdSnapshot();
	}
	
	/**
	 * 比较产品审核TAB页数据
	 */
	public Map<String,String> compareTabsChange(Long productId){
		Map<String,String> compareTabsMap=new HashMap<String,String>();
		//基础信息是否有修改
		List<EbkProdProduct> ebkProdProductList=getEbkProdProductVersionObj(productId);
		if(!ebkProdProductList.isEmpty()){
			compareTabsMap.put(Constant.EBK_AUDIT_TABS_NAME.EBK_AUDIT_TAB_BASE.name(), !compareEbkProdProductBase(ebkProdProductList).isEmpty()+"");
			compareTabsMap.put(Constant.EBK_AUDIT_TABS_NAME.EBK_AUDIT_TAB_RECOMMEND.name(), !compareEbkProdProductRecommend(ebkProdProductList).isEmpty()+"");
			compareTabsMap.put(Constant.EBK_AUDIT_TABS_NAME.EBK_AUDIT_TAB_MULTITRIP.name(), !compareEbkProdProductMultiTrip(ebkProdProductList).isEmpty()+"");
			compareTabsMap.put(Constant.EBK_AUDIT_TABS_NAME.EBK_AUDIT_TAB_TRIP.name(), !compareEbkProdProductTrip(ebkProdProductList).isEmpty()+"");
			compareTabsMap.put(Constant.EBK_AUDIT_TABS_NAME.EBK_AUDIT_TAB_COST.name(), !compareEbkProdProductCost(ebkProdProductList).isEmpty()+"");
			compareTabsMap.put(Constant.EBK_AUDIT_TABS_NAME.EBK_AUDIT_TAB_OTHER.name(), !compareEbkProdProductOther(ebkProdProductList).isEmpty()+"");
			compareTabsMap.put(Constant.EBK_AUDIT_TABS_NAME.EBK_AUDIT_TAB_TRAFFIC.name(), !compareEbkProdProductTraffic(ebkProdProductList).isEmpty()+"");
			compareTabsMap.put(Constant.EBK_AUDIT_TABS_NAME.EBK_AUDIT_TAB_TIME_PRICE.name(), !compareEbkProdProductTimePrice(ebkProdProductList).isEmpty()+"");
			compareTabsMap.put(Constant.EBK_AUDIT_TABS_NAME.EBK_AUDIT_TAB_RELATION.name(), !compareEbkProdProductRelation(ebkProdProductList).isEmpty()+"");
			compareTabsMap.put(Constant.EBK_AUDIT_TABS_NAME.EBK_AUDIT_TAB_PICTURE.name(), !compareEbkProdProductPics(ebkProdProductList).isEmpty()+"");
			
		}
		return compareTabsMap;
	} 
	
	/**
	 * 审核-基础信息比较
	 */
	public Map<String,Object> compareEbkProdProductBase(Long productId){
		return compareEbkProdProductBase(getEbkProdProductVersionObj(productId));
	}
	/**
	 * 审核-基础信息比较
	 */
	public Map<String,Object> compareEbkProdProductBase(List<EbkProdProduct> ebkProdProductList){
		Map<String,Object> changeMap=new HashMap<String,Object>();
		EbkProdProduct ebkProdProductNew=ebkProdProductList.get(0);
		EbkProdProduct ebkProdProduct=ebkProdProductList.get(1);
		if(ebkProdProductNew!=null && ebkProdProduct!=null){
			//比较基础信息
			
			//设置免比较参数
			List<String> ignoreList=new ArrayList<String>();
			ignoreList.add("prodProductId");
			ignoreList.add("metaProductId");
			ignoreList.add("sumitDate");
			ignoreList.add("updateDate");
			ignoreList.add("examineDate");
			ignoreList.add("status");
			ignoreList.add("statusCN");
			ignoreList.add("comPictures");
			
			changeMap.putAll(compare(ebkProdProductNew,ebkProdProduct,ignoreList));
			//比较行程内包含景点
			changeMap.putAll(compareEbkProdPlace(ebkProdProductNew.getEbkProdPlaces(), ebkProdProduct.getEbkProdPlaces()));
			//比较销售产品属性二
			changeMap.putAll(compareModelProperty(ebkProdProductNew,ebkProdProduct,ebkProdProductNew.getEbkProdModelPropertys(),ebkProdProduct.getEbkProdModelPropertys()));
			//比较结算凭证信息
			changeMap.putAll(compareProdTarget(ebkProdProductNew.getEbkProdTargets(), ebkProdProduct.getEbkProdTargets()));
		}
		return changeMap;
	}

	/**
	 * 审核-产品推荐及特色比较
	 */
	public Map<String,Object> compareEbkProdProductRecommend(Long productId){
		return compareEbkProdProductRecommend(getEbkProdProductVersionObj(productId));
	}
	/**
	 * 审核-产品推荐比较(产品特色不比较)
	 */
	public Map<String,Object> compareEbkProdProductRecommend(List<EbkProdProduct> ebkProdProductList){
		Map<String,Object> changeMap=new HashMap<String,Object>();
		EbkProdProduct ebkProdProductNew=ebkProdProductList.get(0);
		EbkProdProduct ebkProdProduct=ebkProdProductList.get(1);
		if(ebkProdProductNew!=null && ebkProdProduct!=null){
			List<EbkProdContent> ebkProdContentListNew=ebkProdProductNew.getEbkProdContents();
			List<EbkProdContent> ebkProdContentList=ebkProdProduct.getEbkProdContents();
			Map<String,EbkProdContent> ebkProdContentMap=new HashMap<String,EbkProdContent>();
			for (EbkProdContent ebkProdContent : ebkProdContentList) {
				ebkProdContentMap.put(ebkProdContent.getContentType(), ebkProdContent);
			}
			for (EbkProdContent ebkProdContentNew : ebkProdContentListNew) {
				if(Constant.VIEW_CONTENT_TYPE.MANAGERRECOMMEND.name().equals(ebkProdContentNew.getContentType())){
					EbkProdContent ebkProdContent=ebkProdContentMap.get(ebkProdContentNew.getContentType());
					if(ebkProdContent==null || !ebkProdContentNew.getContent().equals(ebkProdContent.getContent())){
						changeMap.put("managerrecommend", ebkProdContent!=null?ebkProdContent.getContent():"");
					}
				}else if(Constant.VIEW_CONTENT_TYPE.FEATURES.name().equals(ebkProdContentNew.getContentType())){
					EbkProdContent ebkProdContent=ebkProdContentMap.get(ebkProdContentNew.getContentType());
					if(ebkProdContent==null || !ebkProdContentNew.getContent().equals(ebkProdContent.getContent())){
						changeMap.put("features", ebkProdContent!=null?ebkProdContent.getContent():"");
					}
				}
			}
		}
		return changeMap;
	}
	/**
	 * 审核-多行程比较
	 */
	public Map<String, List<EbkMultiJourney>> compareEbkProdProductMultiTrip(Long productId){
		return compareEbkProdProductMultiTrip(getEbkProdProductVersionObj(productId));
	}
	
	/**
	 * 审核-多行程比较
	 */
	public Map<String, List<EbkMultiJourney>> compareEbkProdProductMultiTrip(List<EbkProdProduct> ebkProdProductList){
		Map<String,List<EbkMultiJourney>> changeMap=new HashMap<String,List<EbkMultiJourney>>();
		EbkProdProduct ebkProdProductNew=ebkProdProductList.get(0);
		EbkProdProduct ebkProdProduct=ebkProdProductList.get(1);
		if(ebkProdProductNew!=null && ebkProdProduct!=null){
			List<EbkMultiJourney> ebkMultiJourneyListNew=ebkProdProductNew.getEbkMultiJourneys();
			List<EbkMultiJourney> ebkMultiJourneyList=ebkProdProduct.getEbkMultiJourneys();
			
			/*resMap.put("ebkProdProductId", ebkProdProductNew.getEbkProdProductId());
			List<EbkMultiJourney> ebkMultiJourneyListNew=ebkMultiJourneyService.queryMultiJourneyByParams(resMap);
			resMap.clear();
			resMap.put("ebkProdProductId", ebkProdProduct.getEbkProdProductId());
			List<EbkMultiJourney> ebkMultiJourneyList=ebkMultiJourneyService.queryMultiJourneyByParams(resMap);*/
			
			if(ebkMultiJourneyListNew==null){
				ebkMultiJourneyListNew=new ArrayList<EbkMultiJourney>();
			}
			if(ebkMultiJourneyList==null){
				ebkMultiJourneyList=new ArrayList<EbkMultiJourney>();
			}
			if(ebkMultiJourneyListNew.size()!=ebkMultiJourneyList.size()){
				changeMap.put("tripNumber", ebkMultiJourneyList);	
			}
			for (EbkMultiJourney ebkMultiJourneyNew : ebkMultiJourneyListNew) {
				for (EbkMultiJourney ebkMultiJourney : ebkMultiJourneyList) {
					ebkMultiJourneyNew.setMultiJourneyId(0l);
					ebkMultiJourney.setMultiJourneyId(0l);
					ebkMultiJourney.setCreateTime(null);
					ebkMultiJourneyNew.setCreateTime(null);
					
					/*for (int i = 0; i < ebkMultiJourney.getViewJourneyList().size(); i++) {
						ebkMultiJourney.getViewJourneyList().get(i).setJourneyId(0l);
						ebkMultiJourney.getViewJourneyList().get(i).setMultiJourneyId(0l);
					}
					for (int i = 0; i < ebkMultiJourneyNew.getViewJourneyList().size(); i++) {
						ebkMultiJourneyNew.getViewJourneyList().get(i).setJourneyId(0l);
						ebkMultiJourneyNew.getViewJourneyList().get(i).setMultiJourneyId(0l);
					}*/
				}
			}
			String newPo=JsonUtil.getJsonString4List(ebkMultiJourneyListNew);
			String oldPo=JsonUtil.getJsonString4List(ebkMultiJourneyList);
			if (!oldPo.equals(newPo)) {
				changeMap.put("ebkMultiJourneyListOld", ebkMultiJourneyList);	
			}
		}
		return changeMap;
	}
	
	public void setEbkMultiJourneyService(
			EbkMultiJourneyService ebkMultiJourneyService) {
		this.ebkMultiJourneyService = ebkMultiJourneyService;
	}

	/**
	 * 审核-行程描述比较
	 */
	public Map<String, List<EbkProdJourney>> compareEbkProdProductTrip(Long productId){
		return compareEbkProdProductTrip(getEbkProdProductVersionObj(productId));
	}
	/**
	 * 审核-行程描述比较
	 */
	public Map<String, List<EbkProdJourney>> compareEbkProdProductTrip(List<EbkProdProduct> ebkProdProductList){
		Map<String,List<EbkProdJourney>> changeMap=new HashMap<String,List<EbkProdJourney>>();
		EbkProdProduct ebkProdProductNew=ebkProdProductList.get(0);
		EbkProdProduct ebkProdProduct=ebkProdProductList.get(1);
		if(ebkProdProductNew!=null && ebkProdProduct!=null){
			List<EbkProdJourney> ebkProdJourneyListNew=ebkProdProductNew.getEbkProdJourneys();
			List<EbkProdJourney> ebkProdJourneyList=ebkProdProduct.getEbkProdJourneys();
			if(ebkProdJourneyListNew==null){
				ebkProdJourneyListNew=new ArrayList<EbkProdJourney>();
			}
			if(ebkProdJourneyList==null){
				ebkProdJourneyList=new ArrayList<EbkProdJourney>();
			}
			if(ebkProdJourneyListNew.size()!=ebkProdJourneyList.size()){
				changeMap.put("tripNumber", ebkProdJourneyList);	
			}
			
			List<EbkProdJourney> changeEbkProdJourney=new ArrayList<EbkProdJourney>();
			for (EbkProdJourney ebkProdJourneyNew : ebkProdJourneyListNew) {
				for (EbkProdJourney ebkProdJourney : ebkProdJourneyList) {
					if(ebkProdJourney.getDayNumber().longValue()==ebkProdJourneyNew.getDayNumber().longValue()){
						Map<String, Object> resultMap=compare(ebkProdJourneyNew, ebkProdJourney);
						if(!resultMap.isEmpty()){
							EbkProdJourney ebkProdJourneyTemp=new EbkProdJourney();
							
							ebkProdJourneyTemp.setDayNumber(ebkProdJourney.getDayNumber()); 
							if(resultMap.containsKey("journeyId")){
								ebkProdJourneyTemp.setJourneyId(Long.parseLong(resultMap.get("journeyId")+""));	
							}
							if(resultMap.containsKey("productId")){
								ebkProdJourneyTemp.setProductId(Long.parseLong(resultMap.get("productId")+""));	
							}
							if(resultMap.containsKey("title")){
								ebkProdJourneyTemp.setTitle(resultMap.get("title")+"");	
							}
							if(resultMap.containsKey("content")){
								ebkProdJourneyTemp.setContent(resultMap.get("content")+"");	
							}
							if(resultMap.containsKey("dinner")){
								ebkProdJourneyTemp.setDinner(resultMap.get("dinner")+"");	
							}
							if(resultMap.containsKey("hotel")){
								ebkProdJourneyTemp.setHotel(resultMap.get("hotel")+"");	
							}
							if(resultMap.containsKey("traffic")){
								ebkProdJourneyTemp.setTraffic(resultMap.get("traffic")+"");	
							}
							changeEbkProdJourney.add(ebkProdJourneyTemp);
						}
					}
				}
			}
			if(!changeEbkProdJourney.isEmpty()){
				changeMap.put("ebkProdJourneyListOld", changeEbkProdJourney);	
			} 
		}
		return changeMap;
	}
	
	/**
	 * 审核-费用说明比较
	 */
	public Map<String,Object> compareEbkProdProductCost(Long productId){
		return compareEbkProdProductCost(getEbkProdProductVersionObj(productId));
	}
	/**
	 * 审核-费用说明比较
	 */
	public Map<String,Object> compareEbkProdProductCost(List<EbkProdProduct> ebkProdProductList){
		Map<String,Object> changeMap=new HashMap<String,Object>();
		EbkProdProduct ebkProdProductNew=ebkProdProductList.get(0);
		EbkProdProduct ebkProdProduct=ebkProdProductList.get(1);
		if(ebkProdProductNew!=null && ebkProdProduct!=null){
			List<EbkProdContent> ebkProdContentListNew=ebkProdProductNew.getEbkProdContents();
			List<EbkProdContent> ebkProdContentList=ebkProdProduct.getEbkProdContents();
			Map<String,EbkProdContent> ebkProdContentMap=new HashMap<String,EbkProdContent>();
			for (EbkProdContent ebkProdContent : ebkProdContentList) {
				ebkProdContentMap.put(ebkProdContent.getContentType(), ebkProdContent);
			}
			for (EbkProdContent ebkProdContentNew : ebkProdContentListNew) {
				if(Constant.VIEW_CONTENT_TYPE.COSTCONTAIN.name().equals(ebkProdContentNew.getContentType())){
					EbkProdContent ebkProdContent=ebkProdContentMap.get(ebkProdContentNew.getContentType());
					if(ebkProdContent==null || !ebkProdContentNew.getContent().equals(ebkProdContent.getContent())){
						changeMap.put("costcontain", ebkProdContent!=null?ebkProdContent.getContent():"");
					}
				}
				else if(Constant.VIEW_CONTENT_TYPE.NOCOSTCONTAIN.name().equals(ebkProdContentNew.getContentType())){
					EbkProdContent ebkProdContent=ebkProdContentMap.get(ebkProdContentNew.getContentType());
					if(ebkProdContent==null || !ebkProdContentNew.getContent().equals(ebkProdContent.getContent())){
						changeMap.put("nocostcontain", ebkProdContent!=null?ebkProdContent.getContent():"");
					}
				}
			}
		}
		return changeMap;
	}
	/**
	 * 审核-发车信息比较
	 */
	public Map<String, List<EbkProdContent>> compareEbkProdProductTraffic(Long productId){
		return compareEbkProdProductTraffic(getEbkProdProductVersionObj(productId));
	}
	/**
	 * 审核-发车信息比较
	 */
	public Map<String, List<EbkProdContent>> compareEbkProdProductTraffic(List<EbkProdProduct> ebkProdProductList){
		Map<String,List<EbkProdContent>> changeMap=new HashMap<String,List<EbkProdContent>>();
		EbkProdProduct ebkProdProductNew=ebkProdProductList.get(0);
		EbkProdProduct ebkProdProduct=ebkProdProductList.get(1);
		if(ebkProdProductNew!=null && ebkProdProduct!=null){
			List<EbkProdContent> ebkProdContentListNew=ebkProdProductNew.getEbkProdContents();
			List<EbkProdContent> ebkProdContentList=ebkProdProduct.getEbkProdContents();
			if(ebkProdContentListNew==null){
				ebkProdContentListNew=new ArrayList<EbkProdContent>();
			}
			if(ebkProdContentList==null){
				ebkProdContentList=new ArrayList<EbkProdContent>();
			}
			
			Map<Long,String> ebkProdContentMap=new HashMap<Long,String>();
			for (EbkProdContent ebkProdContent : ebkProdContentList) {
				if(ebkProdContent!=null && Constant.VIEW_CONTENT_TYPE.TRAFFICEBKINFO.name().equals(ebkProdContent.getContentType())){
					ebkProdContentMap.put(ebkProdContent.getContentId(), ebkProdContent.getContent());	
				}
			}
			
			List<EbkProdContent> changeEbkProdContent=new ArrayList<EbkProdContent>();
			for (EbkProdContent ebkProdContentNew : ebkProdContentListNew) {
				if(ebkProdContentNew!=null && Constant.VIEW_CONTENT_TYPE.TRAFFICEBKINFO.name().equals(ebkProdContentNew.getContentType())){
					
					EbkProdContent ebkProdContentTemp=new EbkProdContent();
					String content=ebkProdContentMap.get(ebkProdContentNew.getContentId());
					if(StringUtils.isBlank(content)){
						ebkProdContentTemp.setContentId(ebkProdContentNew.getContentId());
						ebkProdContentTemp.setContent("");
					}
					else{
						if(!content.equals(ebkProdContentNew.getContent())){
							ebkProdContentTemp.setContentId(ebkProdContentNew.getContentId());
							ebkProdContentTemp.setContent(content);
						}
					}
					if(ebkProdContentTemp.getContentId()!=null && ebkProdContentTemp.getContentId()>0){
						changeEbkProdContent.add(ebkProdContentTemp);	
					}
				}
			}
			if(!changeEbkProdContent.isEmpty()){
				changeMap.put("ebkProdContentListOld", changeEbkProdContent);
			}
		}
		return changeMap;
	}
	/**
	 * 审核-其它条款比较
	 */
	public Map<String,Object> compareEbkProdProductOther(Long productId){
		return compareEbkProdProductOther(getEbkProdProductVersionObj(productId));
	}
	/**
	 * 审核-其它条款比较
	 */
	public Map<String,Object> compareEbkProdProductOther(List<EbkProdProduct> ebkProdProductList){
		Map<String,Object> changeMap=new HashMap<String,Object>();
		EbkProdProduct ebkProdProductNew=ebkProdProductList.get(0);
		EbkProdProduct ebkProdProduct=ebkProdProductList.get(1);
		if(ebkProdProductNew!=null && ebkProdProduct!=null){
			List<EbkProdContent> ebkProdContentListNew=ebkProdProductNew.getEbkProdContents();
			List<EbkProdContent> ebkProdContentList=ebkProdProduct.getEbkProdContents();
			Map<String,EbkProdContent> ebkProdContentMap=new HashMap<String,EbkProdContent>();
			for (EbkProdContent ebkProdContent : ebkProdContentList) {
				ebkProdContentMap.put(ebkProdContent.getContentType(), ebkProdContent);
			}
			for (EbkProdContent ebkProdContentNew : ebkProdContentListNew) {
				if(Constant.VIEW_CONTENT_TYPE.ACITONTOKNOW.name().equals(ebkProdContentNew.getContentType())
				||Constant.VIEW_CONTENT_TYPE.ORDERTOKNOWN.name().equals(ebkProdContentNew.getContentType())
				||Constant.VIEW_CONTENT_TYPE.RECOMMENDPROJECT.name().equals(ebkProdContentNew.getContentType())
				||Constant.VIEW_CONTENT_TYPE.SHOPPINGEXPLAIN.name().equals(ebkProdContentNew.getContentType())
						){
					EbkProdContent ebkProdContent=ebkProdContentMap.get(ebkProdContentNew.getContentType());
					if(ebkProdContent==null || !ebkProdContentNew.getContent().equals(ebkProdContent.getContent())){
						changeMap.put(ebkProdContentNew.getContentType(), ebkProdContent!=null?ebkProdContent.getContent():"");
					}
				}
			}
		}
		return changeMap;
	}
	/**
	 * 审核-价格\库存比较
	 */
	public Map<String,Object> compareEbkProdProductTimePrice(Long productId){
		return compareEbkProdProductTimePrice(getEbkProdProductVersionObj(productId));
	}
	/**
	 * 审核-价格\库存比较
	 */
	public Map<String,Object> compareEbkProdProductTimePrice(List<EbkProdProduct> ebkProdProductList){
		Map<String,Object> changeMap=new HashMap<String,Object>();
		changeMap.put("timePriceChange", true);
		EbkProdProduct ebkProdProductNew=ebkProdProductList.get(0);
		EbkProdProduct ebkProdProduct=ebkProdProductList.get(1);
		if(ebkProdProductNew!=null && ebkProdProduct!=null){
			List<EbkProdTimePrice> ebkProdTimePriceListNew=ebkProdProductNew.getEbkProdTimePrices();
			List<EbkProdTimePrice> ebkProdTimePriceList=ebkProdProduct.getEbkProdTimePrices();
			Map<String,EbkProdTimePrice> ebkProdTimePriceMap=new HashMap<String,EbkProdTimePrice>();
			for (EbkProdTimePrice ebkProdTimePrice : ebkProdTimePriceList) {
				ebkProdTimePriceMap.put(ebkProdTimePrice.getProductId()+"-"+ebkProdTimePrice.getProdBranchId()+"-"+ebkProdTimePrice.getSpecDate(), ebkProdTimePrice);
			}
			
			for (EbkProdTimePrice ebkProdTimePriceNew : ebkProdTimePriceListNew) {
				EbkProdTimePrice ebkProdTimePriceOld=ebkProdTimePriceMap.get(ebkProdTimePriceNew.getProductId()+"-"+ebkProdTimePriceNew.getProdBranchId()+"-"+ebkProdTimePriceNew.getSpecDate());
				if(ebkProdTimePriceOld!=null){
					if(ebkProdTimePriceNew.getPrice().longValue()!=ebkProdTimePriceOld.getPrice().longValue()){
						return changeMap;	
					}
					if(ebkProdTimePriceNew.getMarketPrice().longValue()!=ebkProdTimePriceOld.getMarketPrice().longValue()){
						return changeMap;	
					}
					if(ebkProdTimePriceNew.getSettlementPrice().longValue()!=ebkProdTimePriceOld.getSettlementPrice().longValue()){
						return changeMap;	
					}
					if(ebkProdTimePriceNew.getDayStock().longValue()!=ebkProdTimePriceOld.getDayStock().longValue()){
						return changeMap;	
					}
					if(!ebkProdTimePriceNew.getResourceConfirm().equals(ebkProdTimePriceOld.getResourceConfirm())){
						return changeMap;	
					}
					if(!ebkProdTimePriceNew.getOverSale().equals(ebkProdTimePriceOld.getOverSale())){
						return changeMap;	
					}
					if(ebkProdTimePriceNew.getStockType().equals(ebkProdTimePriceOld.getStockType())){
						return changeMap;	
					}
					if(ebkProdTimePriceNew.getAheadHour().longValue()!=ebkProdTimePriceOld.getAheadHour().longValue()){
						return changeMap;	
					}
					if(!ebkProdTimePriceNew.getCancelStrategy().equals(ebkProdTimePriceOld.getCancelStrategy())){
						return changeMap;	
					}
					if(!ebkProdTimePriceNew.getForbiddenSell().equals(ebkProdTimePriceOld.getForbiddenSell())){
						return changeMap;	
					}
					if(ebkProdTimePriceNew.getBreakfastCount().longValue()!=ebkProdTimePriceOld.getBreakfastCount().longValue()){
						return changeMap;	
					}
				}
				else{
					return changeMap;
				}
			}
		}
		return new HashMap<String,Object>();
	}
	/**
	 * 审核-关联销售产品
	 */
	public Map<String,List<EbkProdRelation>> compareEbkProdProductRelation(Long productId){
		return compareEbkProdProductRelation(getEbkProdProductVersionObj(productId));
	}
	/**
	 * 审核-关联销售产品
	 */
	public Map<String,List<EbkProdRelation>> compareEbkProdProductRelation(List<EbkProdProduct> ebkProdProductList){
		Map<String,List<EbkProdRelation>> changeMap=new HashMap<String,List<EbkProdRelation>>();
		EbkProdProduct ebkProdProductNew=ebkProdProductList.get(0);
		EbkProdProduct ebkProdProduct=ebkProdProductList.get(1);
		if(ebkProdProductNew!=null && ebkProdProduct!=null){
			List<EbkProdRelation> ebkProdRelationsNew = ebkProdProductNew.getEbkProdRelations();
			List<EbkProdRelation> ebkProdRelationList = ebkProdProduct.getEbkProdRelations();
			if (ebkProdRelationsNew == null) {
				ebkProdRelationsNew = new ArrayList<EbkProdRelation>();
			}
			if (ebkProdRelationList == null) {
				ebkProdRelationList = new ArrayList<EbkProdRelation>();
			}
			Map<String, EbkProdRelation> ebkProdRelationMap = new HashMap<String, EbkProdRelation>();
			for (EbkProdRelation ebkProdRelation : ebkProdRelationList) {
				ebkProdRelationMap.put(ebkProdRelation.getEbkProductId() + "-"+ ebkProdRelation.getRelateProdBranchId(), ebkProdRelation);
			}
			for (int i = 0; i < ebkProdRelationsNew.size(); i++) {
				EbkProdRelation ebkProdRelationNew = ebkProdRelationsNew.get(i);
				EbkProdRelation oldEbkProdRelation =ebkProdRelationMap.remove(ebkProdRelationNew.getEbkProductId()+ "-" + ebkProdRelationNew.getRelateProdBranchId());
				if(oldEbkProdRelation!=null){
					ebkProdRelationsNew.remove(i);
					i--;
				}
			}
			if (!ebkProdRelationsNew.isEmpty() || !ebkProdRelationMap.isEmpty()) {
				changeMap.put("ebkProdRelationListOld", ebkProdRelationList);
			}
		}
		return changeMap;
	}
	/**
	 * 比较结算凭证信息
	 * @author ZHANG Nan
	 * @param ebkProdTargetListNew
	 * @param ebkProdTargetList
	 * @return
	 */
	private Map<String,Object> compareProdTarget(List<EbkProdTarget> ebkProdTargetListNew,List<EbkProdTarget> ebkProdTargetList){
		Map<String,Object> changeMap=new HashMap<String,Object>();
		Map<String,EbkProdTarget> prodTargetMap=new HashMap<String,EbkProdTarget>();
		for (EbkProdTarget ebkProdTarget : ebkProdTargetList) {
			prodTargetMap.put(ebkProdTarget.getTargetType(), ebkProdTarget);
		}
		for (EbkProdTarget ebkProdTargetNew : ebkProdTargetListNew) {
			if(Constant.CONTACT_TYPE.SUP_PERFORM_TARGET.name().equals(ebkProdTargetNew.getTargetType())){
				//比较履行对象
				EbkProdTarget ebkProdTarget=prodTargetMap.get(ebkProdTargetNew.getTargetType());
				if(ebkProdTarget==null || ebkProdTargetNew.getTargetId().longValue()!=ebkProdTarget.getTargetId().longValue()){
					changeMap.put("supPerformTarget", ebkProdTarget);
				}
			}
			else if(Constant.CONTACT_TYPE.SUP_B_CERTIFICATE_TARGET.name().equals(ebkProdTargetNew.getTargetType())){
				//比较凭证对象
				EbkProdTarget ebkProdTarget=prodTargetMap.get(ebkProdTargetNew.getTargetType());
				if(ebkProdTarget==null || ebkProdTargetNew.getTargetId().longValue()!=ebkProdTarget.getTargetId().longValue()){
					changeMap.put("supBCertificateTarget", ebkProdTarget);
				}
			}
			else if(Constant.CONTACT_TYPE.SUP_SETTLEMENT_TARGET.name().equals(ebkProdTargetNew.getTargetType())){
				//比较结算对象
				EbkProdTarget ebkProdTarget=prodTargetMap.get(ebkProdTargetNew.getTargetType());
				if(ebkProdTarget==null || ebkProdTargetNew.getTargetId().longValue()!=ebkProdTarget.getTargetId().longValue()){
					changeMap.put("supSettlementTarget", ebkProdTarget);
				}
			}
		}
		return changeMap;
	}
	
	@Override
	public Map<String, List<ComPicture>> compareEbkProdProductPics(List<EbkProdProduct> ebkProdProductList) {
		Map<String,List<ComPicture>> changeMap=new HashMap<String,List<ComPicture>>();
		List<ComPicture> oldPicturesList=null;
		List<ComPicture> newPicturesList=null;
		if(null!=ebkProdProductList.get(0)){
			oldPicturesList=ebkProdProductList.get(0).getComPictures();
		}
		if(null!=ebkProdProductList.get(1)){
			newPicturesList=ebkProdProductList.get(1).getComPictures();
		}
		if(null!=oldPicturesList&&null!=newPicturesList){
			String oldPicJson=JsonUtil.getJsonString4List(oldPicturesList);
			String newPicJson=JsonUtil.getJsonString4List(newPicturesList);
			if(!oldPicJson.equals(newPicJson)){//修改了
				changeMap.put(EBK_CHANGED_ITEM.PICTURE_CHANGED.name(), newPicturesList);
			}
		}
		return changeMap;
	}	
	
	
	
	/**
	 * 去掉一级模块类型并且二级模块类型相同的模块属性
	 * @param mpList
	 * @return
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	private List<ProductModelProperty> removeSameModelProperty(List<ProductModelProperty> mpList) {
		List<ProductModelProperty> pmpList = new ArrayList<ProductModelProperty>(); 
		for (ProductModelProperty mp : mpList) {
			boolean flag = true;
			for (ProductModelProperty pmp : pmpList) {
				if (pmp.getFirstModelId().longValue() == mp.getFirstModelId().longValue()
						&& pmp.getSecondModelId().longValue() == mp.getSecondModelId()) {
					flag = false;
					break;
				}
			}
			if (flag) {
				if(mp.getIsMaintain()!=null&&!"".equals(mp.getIsMaintain())&&"Y".equals(mp.getIsMaintain()))
					pmpList.add(mp);
			}
		}
		Comparator comp = new Comparator(){
          public int compare(Object o1,Object o2) {
              ProductModelProperty p1=(ProductModelProperty)o1;
              ProductModelProperty p2=(ProductModelProperty)o2;  
             if(p1.getSeq()<p2.getSeq())
                 return 0;
             else
                 return 1;
             }
        };
        Collections.sort(pmpList, comp);
        List<ProductModelProperty> returnList = new ArrayList<ProductModelProperty>(); 
        for(ProductModelProperty pmp : pmpList){
        	for(ProductModelProperty mp : mpList){
        		if("Y".equals(mp.getIsValid())&&mp.getSecondModelId().longValue()==pmp.getSecondModelId().longValue()){
        			returnList.add(pmp);
        			break;
        		}
        	}
        }
		return returnList;
	}
	private void filterProperty(List<ProductModelProperty> modelList,List<ProductModelType> modelTypeList) {
		for (ProductModelProperty a : modelList) {
			if(!StringUtil.isEmptyString(a.getProperty())){
				a.setProperty(a.getProperty().replaceAll("\\s*|\t|\r|\n", ""));
			}
			for(ProductModelType type:modelTypeList){
				if(type.getId().longValue()==a.getSecondModelId().longValue()){
					a.setIsMultiChoice(type.getIsMultiChoice());
				}
			}
		}
	}
	
	
	public List<ProductModelProperty> getModelPropertyList(String subProductType){
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("isGroupSql", "( PRODUCT_TYPE like '%"+subProductType+";' or PRODUCT_TYPE like '%;"+subProductType+"' or PRODUCT_TYPE like '"+subProductType+";%' or PRODUCT_TYPE like '%;"+subProductType+";%' or  PRODUCT_TYPE ='"+subProductType+"')");	
		List<ProductModelProperty> modelPropertyList = productModelPropertyService.select(params);
		List<ProductModelType> modelTypeList = productModelTypeService.select(new HashMap<String,Object>());
		if(null!=modelPropertyList){
			modelPropertyList = removeSameModelProperty(modelPropertyList);
			filterProperty(modelPropertyList,modelTypeList);
		}
		return modelPropertyList;
	}
	
	
	
	private Map<String,Object> compareModelProperty(EbkProdProduct ebkProdProductNew,EbkProdProduct ebkProdProduct,List<EbkProdModelProperty> ebkProdModelPropertyListNew,List<EbkProdModelProperty> ebkProdModelPropertyList){
		Map<String,Object> changeMap=new HashMap<String,Object>();
		if(!ebkProdProductNew.getSubProductType().equals(ebkProdProduct.getSubProductType())){//直接更换了产品类型
			changeMap.put("MODEL_PROPERTY_ALL_CHANGED", "产品类型由"+ebkProdProduct.getSubProductTypeZh()+"更改为:"+ebkProdProductNew.getSubProductTypeCh());
		}else{
			//查新产品类型对应的所有二级类别
			List<ProductModelProperty> modelPropertyList=this.getModelPropertyList(ebkProdProductNew.getSubProductType());
			//按照二级类别分组比较
			for (ProductModelProperty productModelProperty : modelPropertyList) {
				List<Integer> newList=getModelPropertyIdOrderBy(ebkProdModelPropertyListNew,productModelProperty.getSecondModelId());
				List<Integer> oldList=getModelPropertyIdOrderBy(ebkProdModelPropertyList,productModelProperty.getSecondModelId());
				if(!newList.isEmpty() && !newList.toString().equals(oldList.toString())){
					changeMap.put("MODEL_PROPERTY_"+productModelProperty.getSecondModelId(), getModelProperty(ebkProdModelPropertyList,productModelProperty.getSecondModelId()));
				}
			}
		}
		return changeMap;
	}
	
	
	private String getModelProperty(List<EbkProdModelProperty> ebkProdModelPropertyList,Long secondModelId){
		String modelPropertyIds="";
		String modelPropertyName="";
		for (EbkProdModelProperty ebkProdModelProperty : ebkProdModelPropertyList) {
			if(String.valueOf(secondModelId).equals(ebkProdModelProperty.getEbkPropertyType())){
				modelPropertyIds+="'"+ebkProdModelProperty.getModelPropertyId()+"',";
			}
		}
		if(!modelPropertyIds.isEmpty()){
			if(StringUtils.isNotBlank(modelPropertyIds) && modelPropertyIds.length()>1){
				modelPropertyIds=modelPropertyIds.substring(0,modelPropertyIds.length()-1);
			}
			Map<String,Object> params=new HashMap<String,Object>();
			params.put("ids", modelPropertyIds);
			List<ProductModelProperty> productModelPropertyList=productModelPropertyService.select(params);
			for (ProductModelProperty productModelProperty : productModelPropertyList) {
				modelPropertyName+=productModelProperty.getProperty()+",";
			}
			if(StringUtils.isNotBlank(modelPropertyName) && modelPropertyName.length()>1){
				modelPropertyName=modelPropertyName.substring(0,modelPropertyName.length()-1);
			}	
		}
		return modelPropertyName;
	}
	
	
	
	private List<Integer> getModelPropertyIdOrderBy(List<EbkProdModelProperty> ebkProdModelPropertyList,Long secondModelId){
		List<Integer> modelPropertyIds=new ArrayList<Integer>();
		if(ebkProdModelPropertyList!=null && !ebkProdModelPropertyList.isEmpty()){
			for (EbkProdModelProperty ebkProdModelProperty : ebkProdModelPropertyList) {
				if(String.valueOf(secondModelId).equals(ebkProdModelProperty.getEbkPropertyType())){//二级类是同一类
					modelPropertyIds.add(ebkProdModelProperty.getModelPropertyId());	
				}
			}
			Collections.sort(modelPropertyIds);
		}
		return modelPropertyIds;
	}

	private Map<String,Object> compareEbkProdPlace(List<EbkProdPlace> ebkProdPlaceListNew,List<EbkProdPlace> ebkProdPlaceList){
		List<Long> placeIdListNew=getPlaceIdOrderBy(ebkProdPlaceListNew);
		List<Long> placeIdList=getPlaceIdOrderBy(ebkProdPlaceList);
		if(!placeIdListNew.isEmpty() && !placeIdListNew.toString().equals(placeIdList.toString())){
			Map<String,Object> changeMap=new HashMap<String,Object>();
			changeMap.put("ebkProdPlace", ebkProdPlaceList);
			return changeMap;
		}
		return new HashMap<String,Object>();
	}
	private List<Long> getPlaceIdOrderBy(List<EbkProdPlace> ebkProdPlaceList){
		List<Long> placeIds=new ArrayList<Long>();
		if(ebkProdPlaceList!=null && !ebkProdPlaceList.isEmpty()){
			for (EbkProdPlace ebkProdPlace : ebkProdPlaceList) {
				placeIds.add(ebkProdPlace.getPlaceId());
			}
			Collections.sort(placeIds);
		}
		return placeIds;
	}
	
	/**
	 * 获取产品最近的两个快照
	 * @author ZHANG Nan
	 * @param productId 产品ID
	 * @return 产品最近的两个快照
	 */
	@SuppressWarnings("rawtypes")
	public List<EbkProdProduct> getEbkProdProductVersionObj(Long productId){
		List<EbkProdProduct> ebkProdProductList=new ArrayList<EbkProdProduct>();
		EbkProdSnapshot ebkProdSnapshotDO=new EbkProdSnapshot();
		ebkProdSnapshotDO.setProductId(productId);
		List<EbkProdSnapshot> ebkProdSnapshotList=ebkProdSnapshotDAO.findListByDOAndOrderByProdSnapshotId(ebkProdSnapshotDO);
		if(ebkProdSnapshotList!=null && ebkProdSnapshotList.size()>0){
			EbkProdSnapshot ebkProdSnapshotNew=ebkProdSnapshotList.get(0);	
			EbkProdSnapshot ebkProdSnapshot=new EbkProdSnapshot(); 
			if(ebkProdSnapshotList.size()>1 && null!=ebkProdSnapshotList.get(1)){
				ebkProdSnapshot=ebkProdSnapshotList.get(1);	
			}
			//设置ebkProdProduct对象子对象
			Map<String,Class> childMap=new HashMap<String,Class>();
			childMap.putAll(EBK_PROD_CHILD_LIST);
			try {
				EbkProdProduct ebkProdProductNew=(EbkProdProduct) JsonUtil.getObject4JsonString(ebkProdSnapshotNew.getContent(),EbkProdProduct.class,childMap);
				ebkProdProductList.add(ebkProdProductNew);
				
				EbkProdProduct ebkProdProduct=(EbkProdProduct) JsonUtil.getObject4JsonString(ebkProdSnapshot.getContent(),EbkProdProduct.class,childMap);
				ebkProdProductList.add(ebkProdProduct);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return ebkProdProductList;
	}
	/**
	 * 比较两个对象中属性参数值是否相同
	 * @author ZHANG Nan
	 * @param objectNew 新对象
	 * @param object 老对象
	 * @return 值不相同的参数
	 */
	private Map<String,Object> compare(Object objectNew,Object object){
		return compare(objectNew, object, new ArrayList<String>());
	}
	/**
	 * 比较两个对象中属性参数值是否相同
	 * @author ZHANG Nan
	 * @param objectNew 新对象
	 * @param object 老对象
	 * @param ignoreList 免比较参数
	 * @return 值不相同的参数
	 */
	private Map<String,Object> compare(Object objectNew,Object object,List<String> ignoreList){
		try {
			Map<String,Object> changeMap=new HashMap<String,Object>();
			if(objectNew!=null && object!=null){
				Class<?> clazz=objectNew.getClass();
				Class<?> clazz2=object.getClass();
				Method[] methods=clazz.getMethods();
				for (Method method : methods) {
					if(method.getName().indexOf("get")>=0){
						Type [] types=method.getGenericParameterTypes();
						if(types==null || types.length==0){
							Method mtd = clazz.getMethod(method.getName(),new Class[]{});
							Method mtd2= clazz2.getMethod(method.getName(),new Class[]{});
							Object value = mtd.invoke(objectNew);
							Object value2 = mtd2.invoke(object);
							
							if(value!=null && value2!=null){
								if(!value.equals(value2)){
									String field=method.getName().substring(3,method.getName().length());
									String fieldName=field.substring(0,1).toLowerCase()+field.substring(1,field.length());
									//过滤免比较参数
									if(ignoreProcess(ignoreMapToList(EBK_PROD_CHILD_LIST), fieldName) && ignoreProcess(ignoreList, fieldName)){
										changeMap.put(fieldName, value2.toString());	
									}
								}
							}
						}
					}
				}
			}
			return changeMap;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return new HashMap<String,Object>();
	}
	private List<String> ignoreMapToList(Map<String,Class<?>> ignoreMap){
		List<String> ignoreList=new ArrayList<String>();
		if(ignoreMap!=null && !ignoreMap.isEmpty()){
			Set<Entry<String, Class<?>>> set=ignoreMap.entrySet();
			for (Entry<String, Class<?>> entry : set) {
				ignoreList.add(entry.getKey());
			}
		}
		return ignoreList;
	}
	/**
	 * 判断免比较参数
	 */
	private boolean ignoreProcess(List<String> ignoreList,String fieldName){
		boolean flag=true;
		if(ignoreList!=null && ignoreList.size()>0){
			for (String ignore : ignoreList) {
				if(ignore.equals(fieldName)){
					return false;
				}
			}
		}
		return flag;
	}
	
	/**
	 * 获取时间价格表 旧版本
	 * @author ZHANG Nan
	 * @param ebkProdProductId
	 * @return
	 */
	public List<EbkProdTimePrice> getOldEbkProdTimePrice(Long ebkProdProductId){
		//获取两个最新版本
		List<EbkProdProduct> ebkProdProductList=getEbkProdProductVersionObj(ebkProdProductId);
		if(ebkProdProductList!=null && ebkProdProductList.size()>=2){
			EbkProdProduct ebkProdProduct=ebkProdProductList.get(1);
			if(ebkProdProduct!=null){
				return ebkProdProduct.getEbkProdTimePrices();
			}	
		}
		return new ArrayList<EbkProdTimePrice>();
	}
	
	
	@SuppressWarnings("unused")
	private Date toCSTDate(String value){
		SimpleDateFormat sdf=new SimpleDateFormat("EEE MMM dd HH:mm:ss 'CST' yyyy",Locale.ENGLISH);
		try {
			return sdf.parse(value);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public EbkProdSnapshotDAO getEbkProdSnapshotDAO() {
		return ebkProdSnapshotDAO;
	}

	public void setEbkProdSnapshotDAO(EbkProdSnapshotDAO ebkProdSnapshotDAO) {
		this.ebkProdSnapshotDAO = ebkProdSnapshotDAO;
	}

	public void setProductModelPropertyService(
			ProductModelPropertyService productModelPropertyService) {
		this.productModelPropertyService = productModelPropertyService;
	}

	public void setProductModelTypeService(
			ProductModelTypeService productModelTypeService) {
		this.productModelTypeService = productModelTypeService;
	}

	
	
}
