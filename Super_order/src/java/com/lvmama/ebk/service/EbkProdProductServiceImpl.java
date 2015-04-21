package com.lvmama.ebk.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.ebooking.EbkMultiJourney;
import com.lvmama.comm.bee.po.ebooking.EbkProdBranch;
import com.lvmama.comm.bee.po.ebooking.EbkProdContent;
import com.lvmama.comm.bee.po.ebooking.EbkProdJourney;
import com.lvmama.comm.bee.po.ebooking.EbkProdModelProperty;
import com.lvmama.comm.bee.po.ebooking.EbkProdPlace;
import com.lvmama.comm.bee.po.ebooking.EbkProdProduct;
import com.lvmama.comm.bee.po.ebooking.EbkProdRejectInfo;
import com.lvmama.comm.bee.po.ebooking.EbkProdRelation;
import com.lvmama.comm.bee.po.ebooking.EbkProdTarget;
import com.lvmama.comm.bee.po.ebooking.EbkProdTimePrice;
import com.lvmama.comm.bee.service.ebooking.EbkMultiJourneyService;
import com.lvmama.comm.bee.service.ebooking.EbkProdBranchService;
import com.lvmama.comm.bee.service.ebooking.EbkProdContentService;
import com.lvmama.comm.bee.service.ebooking.EbkProdJourneyService;
import com.lvmama.comm.bee.service.ebooking.EbkProdModelPropertyService;
import com.lvmama.comm.bee.service.ebooking.EbkProdPlaceService;
import com.lvmama.comm.bee.service.ebooking.EbkProdProductService;
import com.lvmama.comm.bee.service.ebooking.EbkProdRejectInfoService;
import com.lvmama.comm.bee.service.ebooking.EbkProdRelationService;
import com.lvmama.comm.bee.service.ebooking.EbkProdTargetService;
import com.lvmama.comm.bee.service.ebooking.EbkProdTimePriceService;
import com.lvmama.comm.bee.service.ebooking.EbkProductService;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.vo.Constant;
import com.lvmama.ebk.dao.EbkProdProductDAO;

public class EbkProdProductServiceImpl implements EbkProdProductService {
	@Autowired
	private EbkProdProductDAO ebkProdProductDAO;
	@Autowired
	private EbkProdPlaceService ebkProdPlaceService;
	@Autowired
	private EbkProdTargetService ebkProdTargetService;
	@Autowired
	private EbkProdModelPropertyService ebkProdModelPropertyService;
	@Autowired
	private EbkProdJourneyService ebkProdJourneyService;
	@Autowired
	private EbkProdContentService ebkProdContentService;
	@Autowired
	private EbkMultiJourneyService ebkMultiJourneyService;
	@Autowired
	private EbkProductService ebkProductService;
	@Autowired
	private EbkProdRejectInfoService ebkProdRejectInfoService;
	@Autowired
	private EbkProdTimePriceService ebkProdTimePriceService;
	@Autowired
	private EbkProdBranchService ebkProdBranchService;
	@Autowired
	private EbkProdRelationService ebkProdRelationService;
	@Autowired
	private ComPictureService comPictureService;

	/**
	 * 保存EBK产品信息
	 * @param EbkProdProduct
	 * @return
	 */
	@Override
	public int saveEbkProdProduct(EbkProdProduct ebkProdProduct){
		Long productID = ebkProdProduct.getEbkProdProductId();
		if(productID==null ||productID.longValue()==0){
			productID = ebkProdProductDAO.insertEbkProdProductDO(ebkProdProduct);
		}else{
			ebkProdProductDAO.updateEbkProdProductDO(ebkProdProduct);
		}
		
		//插入属性相关信息，先删除该产品所有属性
		ebkProdModelPropertyService.deleteEbkProdModelPropertyByProductID(productID);
		List<EbkProdModelProperty> propertys = ebkProdProduct.getEbkProdModelPropertys();
		for(EbkProdModelProperty property : propertys){
			property.setProductId(productID);
			ebkProdModelPropertyService.saveEbkProdModelProperty(property);
		}
		
		
		//插入相关对象信息，先删除该产品所有对象
		ebkProdTargetService.deleteEbkProdTargetByProductID(productID);
		List<EbkProdTarget> ebkProdTargets = ebkProdProduct.getEbkProdTargets();
		for(EbkProdTarget ebkProdTarget : ebkProdTargets){
			ebkProdTarget.setProductId(productID);
			ebkProdTargetService.saveEbkProdTarget(ebkProdTarget);
		}
		//插入相关景点信息，先删除该产品所属景点
		ebkProdPlaceService.deleteListByProductId(productID);
		if(null!=ebkProdProduct.getEbkProdPlaces() && !ebkProdProduct.getEbkProdPlaces().isEmpty()){
			for(EbkProdPlace ebkProdPlace:ebkProdProduct.getEbkProdPlaces()){
				ebkProdPlace.setProductId(productID);
				ebkProdPlaceService.insert(ebkProdPlace);
			}
		}
		return productID.intValue();
	}
	
	@Override
	public Map<String, Object> queryAllCount(Long supplierId,
			String[] subProductTypes) {
		List<Map<String,Object>> result = ebkProdProductDAO.queryAllCount(supplierId, subProductTypes);
		Integer all =0;
		Map<String,Object> resultMap =new HashMap<String,Object>();
		for(Map<String,Object> map:result){
			Iterator<String> iterator = map.keySet().iterator();
			String tempKey = null,tempValue=null;
			while(iterator.hasNext()){
				String key = (String)iterator.next();
				Object value = map.get(key);
				if(null!=value && !StringUtil.isEmptyString(String.valueOf(value))){
					if("STATUS".equalsIgnoreCase(key)){
						tempKey = String.valueOf(value);
						
					}
					if("CT".equalsIgnoreCase(key)){
						tempValue = String.valueOf(value);
						all +=Integer.valueOf(String.valueOf(value));
					}
				}
			}
			if(null!=tempKey && null!=tempValue){
				resultMap.put(tempKey, tempValue);
			}
		}
		resultMap.put("ALL", String.valueOf(all));
		return resultMap;
	}

	@Override
	public Page<EbkProdProduct> queryProduct(Map<String, Object> parameters) {
		return ebkProdProductDAO.query(parameters);
	}

	/**
	 * 根据EBK产品ID提交审核
	 * @param ebkProdProductId
	 * @return
	 */
	@Override
	public int auditCommit(Long ebkProdProductId,Long updateUserId) {
		EbkProdProduct oldEbkProdProduct  = ebkProdProductDAO.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
		if(null!=oldEbkProdProduct && Constant.EBK_PRODUCT_AUDIT_STATUS.UNCOMMIT_AUDIT.name().equalsIgnoreCase(oldEbkProdProduct.getStatus())){
			oldEbkProdProduct.setStatus(Constant.EBK_PRODUCT_AUDIT_STATUS.PENDING_AUDIT.name());
			oldEbkProdProduct.setSumitDate(new Date());
			oldEbkProdProduct.setUpdateDate(new Date());
			oldEbkProdProduct.setUpdateUserId(updateUserId);
			return ebkProdProductDAO.updateEbkProdProductDO(oldEbkProdProduct);
		}
		return 0;
	}

	/**
	 * 根据EBK产品ID撤消审核
	 * @param ebkProdProductId
	 * @return
	 */
	@Override
	public int auditRevoke(Long ebkProdProductId) {
		EbkProdProduct oldEbkProdProduct  = ebkProdProductDAO.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
		if(null!=oldEbkProdProduct && Constant.EBK_PRODUCT_AUDIT_STATUS.PENDING_AUDIT.name().equalsIgnoreCase(oldEbkProdProduct.getStatus())){
			oldEbkProdProduct.setStatus(Constant.EBK_PRODUCT_AUDIT_STATUS.UNCOMMIT_AUDIT.name());
			oldEbkProdProduct.setUpdateDate(new Date());
			return ebkProdProductDAO.updateEbkProdProductDO(oldEbkProdProduct);
		}
		return 0;
	}
	
	/**
	 * 根据EBK产品ID把审核状态变成未提交
	 * @param ebkProdProductId
	 * @return
	 */
	@Override
	public int auditRecover(Long ebkProdProductId) {
		EbkProdProduct oldEbkProdProduct  = ebkProdProductDAO.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
		if(null!=oldEbkProdProduct && !Constant.EBK_PRODUCT_AUDIT_STATUS.PENDING_AUDIT.name().equalsIgnoreCase(oldEbkProdProduct.getStatus())){
			Map<String,Object> params = new HashMap<String,Object>();
			params.put("productId", ebkProdProductId);
			if(oldEbkProdProduct.getStatus().equals(Constant.EBK_PRODUCT_AUDIT_STATUS.THROUGH_AUDIT.name())){
				ebkProdTimePriceService.delete(params);
			}
			oldEbkProdProduct.setUpdateDate(new Date());
			oldEbkProdProduct.setStatus(Constant.EBK_PRODUCT_AUDIT_STATUS.UNCOMMIT_AUDIT.name());
			int i= ebkProdProductDAO.updateEbkProdProductDO(oldEbkProdProduct);
			return i;
		}
		return 0;
	}
	
	public EbkProdProduct findEbkProdProductDOByPrimaryKey(Long ebkProdProductId) {
		return ebkProdProductDAO.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
	}
	
	/**
	 * 根据产品Id获取产品对象和基础信息页数据
	 * @author ZHANG Nan
	 * @param ebkProdProductId
	 * @return
	 */
	public EbkProdProduct findEbkProdProductAndBaseByPrimaryKey(Long ebkProdProductId) {
		//获取基础信息
		EbkProdProduct ebkProdProduct=ebkProdProductDAO.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
		
		//加入目的地信息
		EbkProdPlace ebkProdPlaceDO=new EbkProdPlace();
		ebkProdPlaceDO.setProductId(ebkProdProduct.getEbkProdProductId());
		List<EbkProdPlace> ebkProdPlaceList=ebkProdPlaceService.findListByTerm(ebkProdPlaceDO);
		ebkProdProduct.setEbkProdPlaces(ebkProdPlaceList);
		//加入销售产品第二属性信息
		List<EbkProdModelProperty> ebkProdModelPropertyList=ebkProdModelPropertyService.findListByProductId(ebkProdProduct.getEbkProdProductId());
		ebkProdProduct.setEbkProdModelPropertys(ebkProdModelPropertyList);
		
		//加入结算凭证信息
		EbkProdTarget ebkProdTargetDO=new EbkProdTarget();
		ebkProdTargetDO.setProductId(ebkProdProduct.getEbkProdProductId());
		List<EbkProdTarget> ebkProdTargetList=ebkProdTargetService.findListByTerm(ebkProdTargetDO);
		ebkProdProduct.setEbkProdTargets(ebkProdTargetList);
		
		return ebkProdProduct;
	}
	/**
	 * 根据产品Id获取产品对象和其它所有关联数据
	 * @author ZHANG Nan
	 * @param ebkProdProductId
	 * @return
	 */
	public EbkProdProduct findEbkProdAllByPrimaryKey(Long ebkProdProductId) {
		//获取基础信息
		EbkProdProduct ebkProdProduct=ebkProdProductDAO.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
		EbkProdPlace ebkProdPlace=new EbkProdPlace();
		ebkProdPlace.setProductId(ebkProdProduct.getEbkProdProductId());
		List<EbkProdPlace> ebkProdPlaces = ebkProdPlaceService.findListByTerm(ebkProdPlace);
		List<EbkProdModelProperty> ebkProdModelPropertys = ebkProdModelPropertyService.findListByProductId(ebkProdProductId);
		List<EbkProdTarget> ebkProdTargets = ebkProdTargetService.findListByProductId(ebkProdProductId);
		
		
		List<EbkMultiJourney> ebkMultiJourneys=new ArrayList<EbkMultiJourney>();
		List<EbkProdJourney> ebkProdJourneys =new ArrayList<EbkProdJourney>();
		List<EbkProdContent> ebkProdContents=new ArrayList<EbkProdContent>();//产品特色及推荐，其他条款
		List<EbkProdContent> ebkProdContent=new ArrayList<EbkProdContent>();//费用说明
		EbkProdJourney ebkProdJourney = new EbkProdJourney();
		ebkProdContents = ebkProdContentService.findListByProductId(ebkProdProductId);
		
		String isMultiJourney=ebkProdProduct.getIsMultiJourney();
		if ("Y".equals(isMultiJourney)) {
			//多行程
			Map<String,Object> resultMap=new HashMap<String,Object>();
			resultMap.put("ebkProdProductId",ebkProdProductId);
			ebkMultiJourneys=ebkMultiJourneyService.queryMultiJourneyByParams(resultMap);
			for (int i = 0; i < ebkMultiJourneys.size(); i++) {
				//形成描述
				List<EbkProdJourney> ebkProdJourneyList=ebkProdJourneyService.getViewJourneyByMultiJourneyId(ebkMultiJourneys.get(i).getMultiJourneyId());
				for(EbkProdJourney journey:ebkProdJourneyList){
					List<ComPicture> ebkProdJourneycomPictures = comPictureService.getPictureByObjectIdAndType(journey.getJourneyId(), Constant.EBK_PROD_PICTURE_TYPE.EBK_PROD_JOURNEY.name());
					journey.setComPictureJourneyList(ebkProdJourneycomPictures);
				}
				ebkMultiJourneys.get(i).setViewJourneyList(ebkProdJourneyList);
				//费用说明
				ebkProdContent=ebkProdContentService.getEbkContentByMultiJourneyId(ebkMultiJourneys.get(i).getMultiJourneyId(),"");
					ebkMultiJourneys.get(i).setEbkProdContentList(ebkProdContent);
			}
		}else{
			//单行程
			ebkProdJourney.setProductId(ebkProdProductId);
			ebkProdJourneys= ebkProdJourneyService.findListByTerm(ebkProdJourney);
			for(EbkProdJourney journey:ebkProdJourneys){
				List<ComPicture> ebkProdJourneycomPictures = comPictureService.getPictureByObjectIdAndType(journey.getJourneyId(), Constant.EBK_PROD_PICTURE_TYPE.EBK_PROD_JOURNEY.name());
				journey.setComPictureJourneyList(ebkProdJourneycomPictures);
			}
			//费用说明
			//ebkProdContents = ebkProdContentService.findListByProductId(ebkProdProductId);
		}
		
		EbkProdTimePrice ebkProdTimePrice = new EbkProdTimePrice();
		ebkProdTimePrice.setProductId(ebkProdProductId);
		List<EbkProdTimePrice> ebkProdTimePrices = ebkProdTimePriceService.findListByTerm(ebkProdTimePrice);
		EbkProdBranch ebkProdBranch =new EbkProdBranch();
		ebkProdBranch.setProdProductId(ebkProdProductId);
		List<EbkProdBranch> ebkProdBranchs = ebkProdBranchService.query(ebkProdBranch);
		List<ComPicture> prodComPictures = new ArrayList<ComPicture>();
		prodComPictures.addAll(comPictureService.getPictureByObjectIdAndType(ebkProdProductId, Constant.EBK_PROD_PICTURE_TYPE.EBK_PROD_PRODUCT_BIG.getCode()));
		prodComPictures.addAll(comPictureService.getPictureByObjectIdAndType(ebkProdProductId, Constant.EBK_PROD_PICTURE_TYPE.EBK_PROD_PRODUCT_SMALL.getCode()));
		EbkProdRelation ebkProdRelation = new EbkProdRelation();
		ebkProdRelation.setEbkProductId(ebkProdProductId);
		List<EbkProdRelation> ebkProdRelations = ebkProdRelationService.findListByTerm(ebkProdRelation);
		ebkProdProduct.setEbkProdBranchs(ebkProdBranchs);
		ebkProdProduct.setEbkProdPlaces(ebkProdPlaces);
		ebkProdProduct.setEbkProdModelPropertys(ebkProdModelPropertys);
		ebkProdProduct.setEbkProdTargets(ebkProdTargets);
		ebkProdProduct.setEbkProdTimePrices(ebkProdTimePrices);
		ebkProdProduct.setComPictures(prodComPictures);
		ebkProdProduct.setEbkProdRelations(ebkProdRelations);
		
		if (("Y").equals(ebkProdProduct.getIsMultiJourney())) {//多行程
			ebkProdProduct.setEbkMultiJourneys(ebkMultiJourneys);
			ebkProdProduct.setEbkProdContents(ebkProdContent);
		}else{//单行程
			ebkProdProduct.setEbkProdJourneys(ebkProdJourneys);
		}
		ebkProdProduct.setEbkProdContents(ebkProdContents);
		return ebkProdProduct;
	}
	/**
	 * 根据产品Id删除未提交产品的产品对象和基础信息页数据
	 * @author shangzhengyuan
	 * @param ebkProdProductId
	 * @return
	 */
	public int deleteUnCommitAudit(Long ebkProdProductId){
		EbkProdProduct ebkProdProduct = findEbkProdProductDOByPrimaryKey(ebkProdProductId);
		if(null!=ebkProdProduct && Constant.EBK_PRODUCT_AUDIT_STATUS.UNCOMMIT_AUDIT.name().equalsIgnoreCase(ebkProdProduct.getStatus())){
			return ebkProdProductDAO.deleteEbkProdProductDOByPrimaryKey(ebkProdProductId);
		}
		return 0;
	}
	/**
	 * 根据产品Id获取产品对象和EBK_PROD_CONTENT表数据
	 * @author ZHANG Nan
	 * @param ebkProdProductId
	 * @return
	 */
	public EbkProdProduct findEbkProdProductAndContentByPrimaryKey(Long ebkProdProductId) {
		//获取基础信息
		EbkProdProduct ebkProdProduct=ebkProdProductDAO.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
		
		//加入行程描述信息
		EbkProdContent ebkProdContentDO=new EbkProdContent();
		ebkProdContentDO.setProductId(ebkProdProduct.getEbkProdProductId());
		List<EbkProdContent> ebkProdContentList=ebkProdContentService.findListByTerm(ebkProdContentDO);
		ebkProdProduct.setEbkProdContents(ebkProdContentList);
		return ebkProdProduct;
	}
	/**
	 * 根据产品Id获取产品对象和行程描述页数据
	 * @author ZHANG Nan
	 * @param ebkProdProductId
	 * @return
	 */
	public EbkProdProduct findEbkProdProductAndTripByPrimaryKey(Long ebkProdProductId) {
		//获取基础信息
		EbkProdProduct ebkProdProduct=ebkProdProductDAO.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
		
		//加入行程描述信息
		EbkProdJourney ebkProdJourneyDO=new EbkProdJourney();
		ebkProdJourneyDO.setProductId(ebkProdProduct.getEbkProdProductId());
		List<EbkProdJourney> ebkProdJourneyList=ebkProdJourneyService.findListOrderDayNumberByDO(ebkProdJourneyDO);
		ebkProdProduct.setEbkProdJourneys(ebkProdJourneyList);
		return ebkProdProduct;
	}
	
	
	@Override
	public List<Map<String, Object>> queryCountGroupByStatus(
			Map<String, Object> parameters) {
		return ebkProdProductDAO.queryCountGroupByStatus(parameters);
	}
	
	/**
	 * 审核通过-导入到super系统
	 * @author ZHANG Nan
	 * @param ebkProdProductId EBK产品ID
	 * @param onlineTime 上线开始时间
	 * @param offlineTime 上线结束时间
	 * @param online 是否上线
	 * @return 导入是否成功
	 * @throws Exception 
	 */
	public void prodProductAuditPass(Long ebkProdProductId,Date onlineTime,Date offlineTime,Boolean online) throws Exception{
		//更新审核状态为审核通过
		updateAuditEbkProdProduct(ebkProdProductId, Constant.EBK_PRODUCT_AUDIT_STATUS.THROUGH_AUDIT.name());
		//导入
		ebkProductService.saveProduct(ebkProdProductId, onlineTime, offlineTime, online);
	}
	/**
	 * 审核不通过-记录审核不通过信息
	 * @author ZHANG Nan
	 * @param ebkProdProductId EBK产品ID
	 * @param ebkProdRejectInfoList 审核不通过信息集合
	 * @return 是否成功
	 */
	public void prodProductAuditNoPass(Long ebkProdProductId,List<EbkProdRejectInfo> ebkProdRejectInfoList){
		ebkProdRejectInfoService.insertList(ebkProdProductId,ebkProdRejectInfoList);
		//更新审核状态为审核不通过
		updateAuditEbkProdProduct(ebkProdProductId, Constant.EBK_PRODUCT_AUDIT_STATUS.REJECTED_AUDIT.name());
	}
	private void updateAuditEbkProdProduct(Long ebkProdProductId,String status){
		EbkProdProduct ebkProdProduct=findEbkProdProductAndBaseByPrimaryKey(ebkProdProductId);
		ebkProdProduct.setStatus(status);
		ebkProdProduct.setExamineDate(new Date());
		ebkProdProductDAO.updateEbkProdProductDO(ebkProdProduct);
	}

	@Override
	public void updateEbkProdProductDO(EbkProdProduct ebkProdProduct) {
		ebkProdProductDAO.updateEbkProdProductDO(ebkProdProduct);
	}
	
	/**
     * 获取对象列表
     * @param ebkProdProductDO
     * @return 对象列表
     */
    public List<EbkProdProduct> findListByExample(Map<String, Object> parameters) {
    	return ebkProdProductDAO.findListByExample(parameters);
    }

	@Override
	public EbkProdProduct findEbkProdByProductId(Long prodProductId) {
		EbkProdProduct ebkProd = new EbkProdProduct();
		Map<String, Object> parameters = new HashMap<String, Object>();
		parameters.put("prodProductId", prodProductId);
		List<EbkProdProduct> ebkProdList = ebkProdProductDAO.findListByExample(parameters);
		if (ebkProdList.size() > 0) {
			ebkProd = ebkProdList.get(0);
		}
		return ebkProd;
	}
	
}
