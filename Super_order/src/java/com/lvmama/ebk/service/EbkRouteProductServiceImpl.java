package com.lvmama.ebk.service;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.lvmama.comm.bee.po.ebooking.EbkExtraProdConfig;
import com.lvmama.comm.bee.po.ebooking.EbkMultiJourney;
import com.lvmama.comm.bee.po.ebooking.EbkProdBranch;
import com.lvmama.comm.bee.po.ebooking.EbkProdContent;
import com.lvmama.comm.bee.po.ebooking.EbkProdJourney;
import com.lvmama.comm.bee.po.ebooking.EbkProdModelProperty;
import com.lvmama.comm.bee.po.ebooking.EbkProdPlace;
import com.lvmama.comm.bee.po.ebooking.EbkProdProduct;
import com.lvmama.comm.bee.po.ebooking.EbkProdRelation;
import com.lvmama.comm.bee.po.ebooking.EbkProdTarget;
import com.lvmama.comm.bee.po.ebooking.EbkProdTimePrice;
import com.lvmama.comm.bee.po.ebooking.EbkProdTimePrice.OPERATE_STATUS;
import com.lvmama.comm.bee.po.ebooking.EbkProdTimePrice.STOCK_TYPE;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.meta.MetaProductRoute;
import com.lvmama.comm.bee.po.meta.MetaTravelCode;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductBranchItem;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.TimePrice;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewMultiJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.ebooking.EbkMultiJourneyService;
import com.lvmama.comm.bee.service.ebooking.EbkProdRelationService;
import com.lvmama.comm.bee.service.ebooking.EbkProductService;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.meta.MetaTravelCodeService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductModelPropertyService;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.service.prod.ProdProductRelationService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.view.ViewMultiJourneyService;
import com.lvmama.comm.bee.service.view.ViewPageJourneyService;
import com.lvmama.comm.bee.service.view.ViewPageService;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.prod.ProdAssemblyPoint;
import com.lvmama.comm.pet.po.prod.ProdEContract;
import com.lvmama.comm.pet.po.prod.ProdProductModelProperty;
import com.lvmama.comm.pet.po.prod.ProdProductPlace;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.po.pub.ComPicture.PICTURE_OBJECT_TYPE;
import com.lvmama.comm.pet.po.sup.MetaBCertificate;
import com.lvmama.comm.pet.po.sup.MetaPerform;
import com.lvmama.comm.pet.po.sup.MetaSettlement;
import com.lvmama.comm.pet.po.sup.SupContract;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.place.PlaceService;
import com.lvmama.comm.pet.service.pub.ComLogService;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.pet.service.sup.SettlementTargetService;
import com.lvmama.comm.pet.service.sup.SupContractService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.CONTACT_TYPE;
import com.lvmama.comm.vo.Constant.EBK_PRODUCT_AUDIT_STATUS;
import com.lvmama.comm.vo.Constant.EBK_PRODUCT_VIEW_TYPE;
import com.lvmama.comm.vo.Constant.EBK_PROD_PICTURE_TYPE;
import com.lvmama.comm.vo.Constant.ROUTE_BRANCH;
import com.lvmama.comm.vo.Constant.SUB_PRODUCT_TYPE;
import com.lvmama.comm.vo.Constant.VIEW_CONTENT_TYPE;
import com.lvmama.ebk.dao.EbkExtraProdConfigDAO;
import com.lvmama.ebk.dao.EbkProdBranchDAO;
import com.lvmama.ebk.dao.EbkProdContentDAO;
import com.lvmama.ebk.dao.EbkProdJourneyDAO;
import com.lvmama.ebk.dao.EbkProdModelPropertyDAO;
import com.lvmama.ebk.dao.EbkProdPlaceDAO;
import com.lvmama.ebk.dao.EbkProdProductDAO;
import com.lvmama.ebk.dao.EbkProdTargetDAO;
import com.lvmama.ebk.dao.EbkProdTimePriceDAO;
import com.lvmama.prd.dao.MetaTimePriceDAO;
import com.lvmama.prd.dao.ProdTimePriceDAO;

/**
 * EBK供应商线路产品服务
 * 
 * @author taiqichao
 *
 */
public class EbkRouteProductServiceImpl implements EbkProductService {
	
	private static final Log LOG=LogFactory.getLog(EbkRouteProductServiceImpl.class);
	
	Map<Long, Long> rstMap=new HashMap<Long, Long>();
	
	private EbkProdProductDAO ebkProdProductDAO;
	
	private EbkProdTargetDAO ebkProdTargetDAO;
	
	private EbkProdJourneyDAO ebkProdJourneyDAO;
	
	private EbkProdBranchDAO ebkProdBranchDAO;
	
	private EbkProdContentDAO  ebkProdContentDAO;
	
	private EbkProdTimePriceDAO ebkProdTimePriceDAO;
	
	private EbkProdModelPropertyDAO ebkProdModelPropertyDAO;
	
	private EbkProdPlaceDAO ebkProdPlaceDAO;
	
	private EbkExtraProdConfigDAO ebkExtraProdConfigDAO;
	
	private MetaTimePriceDAO metaTimePriceDAO;
	
	private ProdTimePriceDAO prodTimePriceDAO;
	
	private EbkProdRelationService ebkProdRelationService;
	
	private MetaProductService metaProductService;
	
	private MetaProductBranchService metaProductBranchService;
	
	private ProdProductBranchService prodProductBranchService;
	
	private PermUserService permUserService;
	
	private ProdProductService prodProductService;
	
	private BCertificateTargetService bCertificateTargetService;
	
	private PerformTargetService performTargetService;
	
	private SettlementTargetService settlementTargetService;
	
	private ComPictureService comPictureService;
	
	private ProdProductPlaceService prodProductPlaceService;
	
	private SupplierService supplierService;
	
	private ViewPageJourneyService viewPageJourneyService;
	
	private ViewPageService viewPageService;
	
	private MetaTravelCodeService metaTravelCodeService;
	
	private ProdProductModelPropertyService prodProductModelPropertyService;
	
	private static final String OPERATOR_NAME="SYSTEM";
	
	private ProdProductRelationService prodProductRelationService;
	
	private SupContractService supContractService;
	
	private PlaceService placeService;
	
	private ComLogService comLogService;
	
	private ViewMultiJourneyService viewMultiJourneyService;
	
	private EbkMultiJourneyService ebkMultiJourneyService;
	
	public EbkMultiJourneyService getEbkMultiJourneyService() {
		return ebkMultiJourneyService;
	}

	public void setEbkMultiJourneyService(
			EbkMultiJourneyService ebkMultiJourneyService) {
		this.ebkMultiJourneyService = ebkMultiJourneyService;
	}

	@Override
	public void saveProduct(Long ebkProdProductId,Date onlineTime,Date offlineTime,boolean online) throws Exception{
		EbkProdProduct ebkProdProduct=ebkProdProductDAO.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
		if(null==ebkProdProduct){
			LOG.error("Unknown ebk product id:"+ebkProdProductId);
			throw new RuntimeException("Unknown ebk product id:"+ebkProdProductId);
		}
		if(!EBK_PRODUCT_AUDIT_STATUS.THROUGH_AUDIT.name().equals(ebkProdProduct.getStatus())){
			LOG.error("Error ebk product status:"+ebkProdProduct.getStatus());
			throw new RuntimeException("Error ebk product status:"+ebkProdProduct.getStatus());
		}
		SupSupplier supSupplier=supplierService.getSupplier(ebkProdProduct.getSupplierId());
		if(null==supSupplier){
			LOG.error("Unknown ebk sup supplier id:"+ebkProdProduct.getSupplierId());
			throw new RuntimeException("Unknown ebk sup supplier id:"+ebkProdProduct.getSupplierId());
		}
		PermUser user = permUserService.getPermUserByUserId(ebkProdProduct.getManagerId());
		if(null==user){
			LOG.error("Unknown perm User id:"+ebkProdProduct.getManagerId());
			throw new RuntimeException("Unknown perm User id:"+ebkProdProduct.getManagerId());
		}
		if(null==onlineTime){
			onlineTime=new Date();
		}
		if(null==offlineTime){
			offlineTime=new Date();
		}
		
		//保存采购产品
		MetaProduct metaProduct = saveMetaProduct(ebkProdProduct, user);
		
		//保存履行对象，凭证对象，结算对象
		saveTargetObject(ebkProdProduct, metaProduct);
		
		//保存销售产品基本信息
		boolean isAddProdProduct=false;
		if(null!=ebkProdProduct.getProdProductId()){
			ProdProduct prod=prodProductService.getProdProductById(ebkProdProduct.getProdProductId());
			if(null==prod||(null!=prod&&!"Y".equals(prod.getValid()))){
				isAddProdProduct=true;
			}
		}else{
			isAddProdProduct=true;
		}
		
		ProdRoute product = saveProdProduct(ebkProdProduct, metaProduct,onlineTime, offlineTime, online, supSupplier,user,isAddProdProduct);
		
		//保存附加产品
		saveProductRelation(ebkProdProduct, product);
		
		//保存销售产品/标的信息/景点信息
		saveProdPlace(ebkProdProduct, product);
		
		//保存保存采购/销售类别/打包信息
		saveBranch(online, ebkProdProduct, metaProduct,product,isAddProdProduct);
		
		//保存产品图片
		saveProdProductPicture(ebkProdProduct, product);
		
		//保存销售产品行程安排
		saveJourney(ebkProdProduct, product);
		
		//保存销售产品描述信息
		saveProdContent(ebkProdProduct, product,supSupplier);		
		
		//保存销售产品发车信息
		saveProdAssemblyPoint(ebkProdProduct,product);
		
		//保存销售产品其他信息
		saveProductModelProperty(ebkProdProduct,product);
		
		//保存时间价格表
		saveTimePrice(ebkProdProduct, supSupplier, metaProduct, product);
		
		//关联产品
		ebkProdProduct.setMetaProductId(metaProduct.getMetaProductId());
		ebkProdProduct.setProdProductId(product.getProductId());
		ebkProdProductDAO.updateEbkProdProductDO(ebkProdProduct);
		
		//更新销售产品价格信息
		prodProductService.updatePriceByProductId(product.getProductId());
		
		//校验敏感词，做标识
		prodProductService.checkAndUpdateIsHasSensitiveWords(product.getProductId());
	}

	/**
	 * 保存附加附加产品
	 * @param ebkProdProduct
	 * @param product
	 */
	private void saveProductRelation(EbkProdProduct ebkProdProduct, ProdRoute product) {
		//先删除之前的
		List<ProdProductRelation> productList = prodProductRelationService.getRelatProduct(product.getProductId());
		for (ProdProductRelation prodProductRelation : productList) {
			prodProductRelationService.deleteRelation(prodProductRelation.getRelationId(), OPERATOR_NAME);
		}
		//添加保险附加产品
		addInsuranceExtProd(ebkProdProduct,product);
		//针对出境代理产品添加签证产品
		if(EBK_PRODUCT_VIEW_TYPE.ABROAD_PROXY.name().equals(ebkProdProduct.getProductType())){
			EbkProdRelation term=new EbkProdRelation();
			term.setEbkProductId(ebkProdProduct.getEbkProdProductId());
			List<EbkProdRelation> ebkProdRelationList=ebkProdRelationService.findListByTerm(term);
			for (EbkProdRelation ebkProdRelation : ebkProdRelationList) {
				addProdProductRelation(ebkProdRelation.getRelateProdBranchId(),ebkProdRelation.getSaleNumType(),product.getProductId());
			}
		}
	}

	/**
	 * 添加保险附加产品
	 * @param ebkProdProduct
	 * @param product
	 */
	private void addInsuranceExtProd(EbkProdProduct ebkProdProduct,ProdRoute product) {
		EbkExtraProdConfig term=new EbkExtraProdConfig();
		term.setDaysTrip(product.getDays().intValue());//行程天数
		term.setEbkProductType(ebkProdProduct.getProductType());//ebk产品大类
		List<EbkExtraProdConfig> extProdConfigList=ebkExtraProdConfigDAO.findListByTerm(term);
		if(null!=extProdConfigList&&extProdConfigList.size()>0){
			for (EbkExtraProdConfig ebkExtraProdConfig : extProdConfigList) {
				addProdProductRelation(ebkExtraProdConfig.getProdBranchId(),ebkExtraProdConfig.getSaleNumType(),product.getProductId());
			}
		}
	}
	
	/**
	 * 添加销售产品关联产品
	 * @param prodBranchId 销售产品类别id
	 * @param saleNumType 类型
	 * @param prodProductId 当前销售产品id
	 */
	private void addProdProductRelation(Long prodBranchId,String saleNumType,Long prodProductId){
		ProdProductBranch branch=prodProductBranchService.selectProdProductBranchByPK(prodBranchId);
		if(branch==null){
			LOG.error("Unknown ProdProductBranch id:"+prodBranchId);
			throw new RuntimeException("Unknown ProdProductBranch id:"+prodBranchId);
		}
		if(branch.getProductId().equals(prodProductId)){
			LOG.error("Can't packed itself :"+prodProductId);
			throw new RuntimeException("Can't packed itself :"+prodProductId);
		}
		ProdProductRelation relation=prodProductRelationService.addRelation(prodProductId,branch,OPERATOR_NAME);
		prodProductRelationService.updateSaleNumType(relation.getRelationId(), saleNumType);
	}
	

	/**
	 * 保存销售产品出发地目的地/景点信息
	 * @param ebkProdProduct
	 * @param product
	 */
	private void saveProdPlace(EbkProdProduct ebkProdProduct, ProdRoute product) {
		List<ProdProductPlace> prodProductPlaceList=prodProductPlaceService.selectByProductId(product.getProductId());
		for (ProdProductPlace prodProductPlace : prodProductPlaceList) {
			prodProductPlaceService.delete(prodProductPlace.getProductPlaceId(), OPERATOR_NAME);
		}
		EbkProdPlace ebkProdPlaceDO=new EbkProdPlace();
		ebkProdPlaceDO.setProductId(ebkProdProduct.getEbkProdProductId());
		List<EbkProdPlace> ebkProdPlaceList=ebkProdPlaceDAO.findListByTerm(ebkProdPlaceDO);
		for (EbkProdPlace ebkProdPlace : ebkProdPlaceList) {
			ProdProductPlace prodPlace=new ProdProductPlace();
			prodPlace.setPlaceId(ebkProdPlace.getPlaceId());
			prodPlace.setProductId(product.getProductId());
			Place place = placeService.queryPlaceByPlaceId(ebkProdPlace.getPlaceId());
			if(null!=place){
				prodPlace.setPlaceName(place.getName());
			}
			prodProductPlaceService.insert(prodPlace, OPERATOR_NAME);
		}
		
		if(null!=ebkProdProduct.getFromPlaceId()&&null!=ebkProdProduct.getToPlaceId()){
			//出发地目的地为同一个
			if(ebkProdProduct.getFromPlaceId().equals(ebkProdProduct.getToPlaceId())){
				ProdProductPlace place=new ProdProductPlace();
				Place pc = placeService.queryPlaceByPlaceId(ebkProdProduct.getFromPlaceId());
				if(null!=pc){
					place.setPlaceName(pc.getName());
				}
				place.setFrom("true");
				place.setTo("true");
				place.setPlaceId(ebkProdProduct.getFromPlaceId());
				place.setProductId(product.getProductId());
				prodProductPlaceService.insert(place,OPERATOR_NAME);	
			}else{
				//出发地
				ProdProductPlace fromPlace=new ProdProductPlace();
				Place pc = placeService.queryPlaceByPlaceId(ebkProdProduct.getFromPlaceId());
				if(null!=pc){
					fromPlace.setPlaceName(pc.getName());
				}
				fromPlace.setFrom("true");
				fromPlace.setPlaceId(ebkProdProduct.getFromPlaceId());
				fromPlace.setProductId(product.getProductId());
				prodProductPlaceService.insert(fromPlace,OPERATOR_NAME);
				
				//目的地
				ProdProductPlace toPlace=new ProdProductPlace();
				Place pcto = placeService.queryPlaceByPlaceId(ebkProdProduct.getToPlaceId());
				if(null!=pcto){
					toPlace.setPlaceName(pcto.getName());
				}
				toPlace.setTo("true");
				toPlace.setPlaceId(ebkProdProduct.getToPlaceId());
				toPlace.setProductId(product.getProductId());
				prodProductPlaceService.insert(toPlace,OPERATOR_NAME);
			}
		}
		
	}

	/**
	 * 保存销售产品其他信息
	 * @param product
	 */
	private void saveProductModelProperty(EbkProdProduct ebkProdProduct,ProdRoute product) {
		prodProductModelPropertyService.clearProdProductModelPropertyByProductId(product.getProductId());
		EbkProdModelProperty ebkProdModelPropertyDO=new EbkProdModelProperty();
		ebkProdModelPropertyDO.setProductId(ebkProdProduct.getEbkProdProductId());
		List<EbkProdModelProperty> ebkProdModelPropertyList=ebkProdModelPropertyDAO.findListByTerm(ebkProdModelPropertyDO);
		List<ProdProductModelProperty> prodProductModelPropertyList=new ArrayList<ProdProductModelProperty>();
		for (EbkProdModelProperty ebkProdModelProperty : ebkProdModelPropertyList) {
			ProdProductModelProperty prodProductModelProperty=new ProdProductModelProperty();
			prodProductModelProperty.setIsMaintain("Y");
			prodProductModelProperty.setModelPropertyId(ebkProdModelProperty.getModelPropertyId());
			prodProductModelProperty.setProductId(product.getProductId());
			prodProductModelPropertyList.add(prodProductModelProperty);
		}
		prodProductModelPropertyService.saveProdProductModelProperty(prodProductModelPropertyList);
	}

	/**
	 * 保存发车信息
	 * @param product
	 */
	private void saveProdAssemblyPoint(EbkProdProduct ebkProdProduct,ProdRoute product) {
		List<ProdAssemblyPoint> prodAssemblyPointList=prodProductService.queryAssembly(product.getProductId());
		for (ProdAssemblyPoint prodAssemblyPoint : prodAssemblyPointList) {
			prodProductService.delAssembly(prodAssemblyPoint.getAssemblyPointId(), OPERATOR_NAME);
		}
		EbkProdContent ebkProdContentTerm=new EbkProdContent();
		ebkProdContentTerm.setProductId(ebkProdProduct.getEbkProdProductId());
		ebkProdContentTerm.setContentType(VIEW_CONTENT_TYPE.TRAFFICEBKINFO.name());
		List<EbkProdContent> contents = ebkProdContentDAO.findListByTerm(ebkProdContentTerm);
		for (EbkProdContent ebkProdContent : contents) {
			ProdAssemblyPoint ap = new ProdAssemblyPoint();
			ap.setProductId(product.getProductId());
			ap.setAssemblyPoint(ebkProdContent.getContent());
			prodProductService.saveAssembly(ap, OPERATOR_NAME);
		}
	}
	
	/**
	 * 保存时间价格表
	 * @param ebkProdProduct ebk商品
	 * @param supSupplier 供应商
	 * @param metaProduct 采购产品
	 * @param product 销售产品
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private void saveTimePrice(EbkProdProduct ebkProdProduct,
		SupSupplier supSupplier, MetaProduct metaProduct, ProdRoute product) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		
		//查询所有时间价格
		EbkProdTimePrice ebkProdTimePriceTerm=new EbkProdTimePrice();
		ebkProdTimePriceTerm.setProductId(ebkProdProduct.getEbkProdProductId());
		ebkProdTimePriceTerm.setStockType(null);
		List<EbkProdTimePrice> ebkProdTimePriceList=ebkProdTimePriceDAO.findListByTermOrderByDateASC(ebkProdTimePriceTerm);
		
		//查询所有产品类别
		EbkProdBranch ebkProdBranchTerm=new EbkProdBranch();
		ebkProdBranchTerm.setProdProductId(ebkProdProduct.getEbkProdProductId());
		List<EbkProdBranch> ebkProdBranchList=ebkProdBranchDAO.findListByTerm(ebkProdBranchTerm);
		//组装类别下时间价格表
		for (EbkProdTimePrice ebkProdTimePrice : ebkProdTimePriceList) {
			for (EbkProdBranch ebkProdBranch : ebkProdBranchList) {
				if(ebkProdBranch.getProdBranchId().equals(ebkProdTimePrice.getProdBranchId())){
					ebkProdBranch.getEbkProdTimePrices().add(ebkProdTimePrice);
				}
			}
		}
		for (EbkProdBranch ebkProdBranch : ebkProdBranchList) {
			MetaProductBranch metaProductBranch=metaProductBranchService.getMetaBranch(ebkProdBranch.getMetaProdBranchId());
			if(ROUTE_BRANCH.VIRTUAL.name().equals(ebkProdBranch.getBranchType())){//虚拟类别没有销售价格
				//循环添加类别
				for (EbkProdTimePrice ebkProdTimePrice : ebkProdBranch.getEbkProdTimePrices()) {
					//采购时间价格
					saveMetaProductBranchTimePrice(metaProductBranch, ebkProdTimePrice);
					
					/**
					 *  旧数据废弃掉  update by yangjie  20140609
					 */
					//团号信息
					//saveMetaTravelCode(ebkProdTimePrice,product.getBizcode());
				}
				//采购的日志
				comLogService.insert(null, metaProductBranch.getMetaBranchId(), 
						metaProductBranch.getMetaBranchId(), OPERATOR_NAME,
						"EBK_PRODCUT_IMPORT","系统同步", "系统审核通过产品修改，系统同步时间价格表", "META_TIME_PRICE");
			}else{
				ProdProductBranch prodProductBranch=prodProductBranchService.selectProdProductBranchByPK(ebkProdBranch.getProdProductBranchId());
				//循环添加类别
				for (EbkProdTimePrice ebkProdTimePrice : ebkProdBranch.getEbkProdTimePrices()) {
					//采购时间价格
					TimePrice metaTimePrice = saveMetaProductBranchTimePrice(metaProductBranch, ebkProdTimePrice);
					/**
					 *  旧数据废弃掉  update by yangjie  20140609
					 */
					//团号信息
					//saveMetaTravelCode(ebkProdTimePrice,product.getBizcode());
					//销售时间价格
					saveProductTimePrice(prodProductBranch,ebkProdTimePrice,metaTimePrice);
				}
				prodProductBranchService.updatePriceByBranchId(prodProductBranch.getProdBranchId());
				//采购的日志
				comLogService.insert(null, metaProductBranch.getMetaBranchId(), metaProductBranch.getMetaBranchId(),
						OPERATOR_NAME, "EBK_PRODCUT_IMPORT","系统同步", "系统审核通过产品修改，系统同步时间价格表", "META_TIME_PRICE");
				//销售的日志
				comLogService.insert(null, prodProductBranch.getProdBranchId(), prodProductBranch.getProdBranchId(),
						OPERATOR_NAME,"EBK_PRODCUT_IMPORT", "系统同步", "系统审核通过产品修改，系统同步时间价格表", "PROD_TIME_PRICE");
			}
		}
	}

	/**
	 * 保存销售产品行程安排
	 * @param ebkProdProduct ebk产品
	 * @param product 销售产品
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private void saveJourney(EbkProdProduct ebkProdProduct, ProdRoute product) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		//先删除之前老的
		List<ViewJourney> viewJourneyList=viewPageJourneyService.getViewJourneysByProductId(product.getProductId());
		for (ViewJourney viewJourney : viewJourneyList) {
			//删除行程图片
			List<ComPicture> journeyPicList=comPictureService.getPictureByObjectIdAndType(viewJourney.getJourneyId(), PICTURE_OBJECT_TYPE.VIEW_JOURNEY.name());
			for(ComPicture pic : journeyPicList){
				comPictureService.deletePicture(pic.getPictureId());
			}
			viewPageJourneyService.deleteViewJourney(viewJourney.getJourneyId(), OPERATOR_NAME);
		}
		
		
		ViewPage viewPage=viewPageService.getViewPageByProductId(product.getProductId());
		if(null==viewPage){
			viewPage=new ViewPage();
			viewPage.setProductId(product.getProductId());
			viewPageService.addViewPage(viewPage);
		}
		
		
		
		if ("Y".equals(ebkProdProduct.getIsMultiJourney())) {//多行程
			
			
			
			//删除之前的多行程
			List<ViewMultiJourney> viewMultiJourneyList=viewMultiJourneyService.getAllMultiJourneyDetailByProductId(product.getProductId());
			for (ViewMultiJourney viewMultiJourney : viewMultiJourneyList) {
				viewMultiJourneyService.delete(viewMultiJourney.getMultiJourneyId());
			}
			
			//查询所有多行程
			Map<String, Object> paraMap=new HashMap<String, Object>();
			paraMap.put("ebkProdProductId",ebkProdProduct.getEbkProdProductId());
			List<EbkMultiJourney> ebkMultiJourneyList=ebkMultiJourneyService.queryMultiJourneyByParams(paraMap);
			
			//新增多行程
			for (EbkMultiJourney ebkMultiJourney : ebkMultiJourneyList) {
				
				//保存多行程
				ViewMultiJourney mJourney=new ViewMultiJourney();
				mJourney.setCreateTime(new Date());
				mJourney.setContent(ebkMultiJourney.getContent());
				mJourney.setDays(ebkMultiJourney.getDays());
				mJourney.setJourneyName(ebkMultiJourney.getJourneyName());				
				mJourney.setNights(ebkMultiJourney.getNights());
				mJourney.setProductId(product.getProductId());
				mJourney.setValid(ebkMultiJourney.getValid());
				mJourney.setSpecDate(ebkMultiJourney.getSpecDate());
				viewMultiJourneyService.insert(mJourney,OPERATOR_NAME);
				
				//保存多行程的费用说明
				EbkProdContent ebkProdContentTerm=new EbkProdContent();
				ebkProdContentTerm.setProductId(ebkProdProduct.getEbkProdProductId());
				ebkProdContentTerm.setMultiJourneyId(ebkMultiJourney.getMultiJourneyId());
				List<EbkProdContent> ebkProdContentList=ebkProdContentDAO.findListByTerm(ebkProdContentTerm);
				List<ViewContent> contentList=new ArrayList<ViewContent>();
				for (EbkProdContent ebkProdContent : ebkProdContentList) {
					ViewContent content=new ViewContent();
					content.setPageId(product.getProductId());
					content.setContentType(ebkProdContent.getContentType());
					content.setContent(ebkProdContent.getContent());
					content.setMultiJourneyId(mJourney.getMultiJourneyId());
					contentList.add(content);
				}
				viewPage.setContentList(contentList);
				viewPageService.saveViewContent(viewPage,OPERATOR_NAME);
				
				
				//查询行程描述
				EbkProdJourney eJourney = new EbkProdJourney();
				eJourney.setProductId(ebkProdProduct.getEbkProdProductId());
				eJourney.setMultiJourneyId(ebkMultiJourney.getMultiJourneyId());
				List<EbkProdJourney> ebkProdJourneyList=ebkProdJourneyDAO.findListByTerm(eJourney);
				//保存行程描述
				for (EbkProdJourney ebkProdJourney : ebkProdJourneyList) {
					ViewJourney journey=new ViewJourney();
					journey.setPageId(product.getProductId());
					journey.setProductId(product.getProductId());
					journey.setSeq(ebkProdJourney.getDayNumber());
					journey.setContent(ebkProdJourney.getContent());
					journey.setDinner(ebkProdJourney.getDinner());
					journey.setHotel(ebkProdJourney.getHotel());
					journey.setTitle(ebkProdJourney.getTitle());
					journey.setTraffic(ebkProdJourney.getTraffic());
					journey.setMultiJourneyId(mJourney.getMultiJourneyId());
					viewPageJourneyService.insertMultiViewJourney(journey,OPERATOR_NAME);
					//行程图片
					List<ComPicture> ebkProdJourneyPicList=comPictureService.getPictureByObjectIdAndType(ebkProdJourney.getJourneyId(), EBK_PROD_PICTURE_TYPE.EBK_PROD_JOURNEY.name());
					for(ComPicture pic : ebkProdJourneyPicList){
						ComPicture picture = new ComPicture();
							PropertyUtils.copyProperties(picture, pic);
							picture.setPictureId(null);
							picture.setIsNew(true);
							picture.setPictureObjectId(journey.getJourneyId());
							picture.setPictureObjectType(PICTURE_OBJECT_TYPE.VIEW_JOURNEY.name());
							comPictureService.savePicture(picture);
					}
				}
			}
		}else {// 单行程
			//新增行程安排
			EbkProdJourney ebkProdJourneyTerm=new EbkProdJourney();
			ebkProdJourneyTerm.setProductId(ebkProdProduct.getEbkProdProductId());
			List<EbkProdJourney> ebkProdJourneyList=ebkProdJourneyDAO.findListByTerm(ebkProdJourneyTerm);
			for (EbkProdJourney ebkProdJourney : ebkProdJourneyList) {
				ViewJourney journey=new ViewJourney();
				journey.setPageId(product.getProductId());
				journey.setProductId(product.getProductId());
				journey.setSeq(ebkProdJourney.getDayNumber());
				journey.setContent(ebkProdJourney.getContent());
				journey.setDinner(ebkProdJourney.getDinner());
				journey.setHotel(ebkProdJourney.getHotel());
				journey.setTitle(ebkProdJourney.getTitle());
				journey.setTraffic(ebkProdJourney.getTraffic());
				viewPageJourneyService.insertViewJourney(journey,OPERATOR_NAME);
				//行程图片
				List<ComPicture> ebkProdJourneyPicList=comPictureService.getPictureByObjectIdAndType(ebkProdJourney.getJourneyId(), EBK_PROD_PICTURE_TYPE.EBK_PROD_JOURNEY.name());
				for(ComPicture pic : ebkProdJourneyPicList){
					ComPicture picture = new ComPicture();
						PropertyUtils.copyProperties(picture, pic);
						picture.setPictureId(null);
						picture.setIsNew(true);
						picture.setPictureObjectId(journey.getJourneyId());
						picture.setPictureObjectType(PICTURE_OBJECT_TYPE.VIEW_JOURNEY.name());
						comPictureService.savePicture(picture);
				}
			}
		}
	}

	/**
	 * 保存销售产品描述信息
	 * @param ebkProdProduct ebk产品
	 * @param product 销售产品
	 * ebk-->super
	 */
	private void saveProdContent(EbkProdProduct ebkProdProduct,
			ProdRoute product,SupSupplier supSupplier) {
		List<ViewContent> contentList=new ArrayList<ViewContent>();
		//查询默认配置
		EbkProdContent defEbkProdContentTerm=new EbkProdContent();
		defEbkProdContentTerm.setProductId(0L);
		defEbkProdContentTerm.setProductType(ebkProdProduct.getProductType());
		if(EBK_PRODUCT_VIEW_TYPE.ABROAD_PROXY.name().equals(ebkProdProduct.getProductType())){//出境产品需要区分小类
			defEbkProdContentTerm.setSubProductType(ebkProdProduct.getSubProductType());
		}
		List<EbkProdContent> defEbkProdContentList=ebkProdContentDAO.findListByTerm(defEbkProdContentTerm);
		for (EbkProdContent ebkProdContent : defEbkProdContentList) {
			ViewContent content=new ViewContent();
			content.setPageId(product.getProductId());
			content.setContentType(ebkProdContent.getContentType());
			if(EBK_PRODUCT_VIEW_TYPE.SURROUNDING_GROUP.name().equals(ebkProdProduct.getProductType())
					&&VIEW_CONTENT_TYPE.SERVICEGUARANTEE.name().equals(ebkProdContent.getContentType())){//周边游产品的旅游服务保障
				//替换关键字
				Map<String,Object> data=new HashMap<String,Object>();
				data.put("supplierName", supSupplier.getSupplierName());
				content.setContent(StringUtil.composeMessage(ebkProdContent.getContent(),data));
			}else{//其他的默认取读出来的
				content.setContent(ebkProdContent.getContent());
			}
			contentList.add(content);
		}
		
		EbkProdContent ebkProdContentTerm=new EbkProdContent();
		ebkProdContentTerm.setProductId(ebkProdProduct.getEbkProdProductId());
		List<EbkProdContent> ebkProdContentList=ebkProdContentDAO.findListByTerm(ebkProdContentTerm);
		for (EbkProdContent ebkProdContent : ebkProdContentList) {
			//不处理发车信息和多行程费用说明
			if(VIEW_CONTENT_TYPE.TRAFFICEBKINFO.name().equals(ebkProdContent.getContentType())
					||null!=ebkProdContent.getMultiJourneyId()){
				continue;
			}
			ViewContent content=new ViewContent();
			content.setPageId(product.getProductId());
			content.setContentType(ebkProdContent.getContentType());
			content.setContent(ebkProdContent.getContent());
			contentList.add(content);
		}
		
		ViewPage viewPage=viewPageService.getViewPageByProductId(product.getProductId());
		if(null==viewPage){
			viewPage=new ViewPage();
			viewPage.setProductId(product.getProductId());
			viewPageService.addViewPage(viewPage);
		}
		viewPage.setContentList(contentList);
		viewPageService.saveViewContent(viewPage,OPERATOR_NAME);
	}

	/**
	 * 保存销售产品图片信息
	 * @param ebkProdProduct ebk产品
	 * @param product 销售产品
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 */
	private void saveProdProductPicture(EbkProdProduct ebkProdProduct, ProdRoute product) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		//先删除之前的
		List<ComPicture> oldImageList=comPictureService.getPictureByObjectIdAndType(product.getProductId(), PICTURE_OBJECT_TYPE.VIEW_PAGE.name());
		for (ComPicture comPicture : oldImageList) {
			comPictureService.deletePicture(comPicture.getPictureId());
		}
		//后新增
		List<ComPicture> allComPictureList=new ArrayList<ComPicture>();
		List<ComPicture> ebkProdBigPicList=comPictureService.getPictureByObjectIdAndType(ebkProdProduct.getEbkProdProductId(), Constant.EBK_PROD_PICTURE_TYPE.EBK_PROD_PRODUCT_BIG.name());
		List<ComPicture> ebkProdSmallPicList=comPictureService.getPictureByObjectIdAndType(ebkProdProduct.getEbkProdProductId(), Constant.EBK_PROD_PICTURE_TYPE.EBK_PROD_PRODUCT_SMALL.name());
		allComPictureList.addAll(ebkProdBigPicList);
		allComPictureList.addAll(ebkProdSmallPicList);
		for (ComPicture ebkPic : allComPictureList) {
			ComPicture picture = new ComPicture();
			PropertyUtils.copyProperties(picture, ebkPic);
			if(ebkPic.getPictureObjectType().equals(Constant.EBK_PROD_PICTURE_TYPE.EBK_PROD_PRODUCT_SMALL.name())){
				picture.setPictureName("小图");
				product.setSmallImage(picture.getPictureUrl());
				prodProductService.updateByPrimaryKey(product, OPERATOR_NAME);
				continue;//不需要保存,只需要更新到产品上
			}
			picture.setPictureId(null);
			picture.setIsNew(true);// 标识图片是新建产生的
			picture.setPictureObjectId(product.getProductId());
			picture.setPictureObjectType(PICTURE_OBJECT_TYPE.VIEW_PAGE.name());
			comPictureService.savePicture(picture);
		}
	}

	/**
	 * 保存采购/销售类别/打包信息
	 * @param online 是否上线
	 * @param ebkProdProduct ebk产品
	 * @param metaProduct 采购产品
	 * @param product 销售产品
	 */
	private void saveBranch(boolean online,EbkProdProduct ebkProdProduct, MetaProduct metaProduct,ProdRoute product,boolean isAddProdProduct) {
		
		EbkProdBranch ebkProdBranchTerm=new EbkProdBranch();
		ebkProdBranchTerm.setProdProductId(ebkProdProduct.getEbkProdProductId());
		List<EbkProdBranch> ebkProdBranchList=ebkProdBranchDAO.findListByTerm(ebkProdBranchTerm);
		
		//处理采购产品删除类别
		Map<Long, MetaProductBranch> metaProductBranchMap=new HashMap<Long, MetaProductBranch>();
		
		List<MetaProductBranch> metaBranchList=metaProductBranchService.selectBranchListByProductId(metaProduct.getMetaProductId());
		
		List<MetaProductBranch> leavedMetaBranchList=new ArrayList<MetaProductBranch>();
		
		List<MetaProductBranch> removedMetaBranchList=new ArrayList<MetaProductBranch>();
		
		removedMetaBranchList.addAll(metaBranchList);
		
		for (MetaProductBranch metaProductBranch : metaBranchList) {
			metaProductBranchMap.put(metaProductBranch.getMetaBranchId(), metaProductBranch);
			for (EbkProdBranch ebkProdBranch : ebkProdBranchList) {
				if(null!=ebkProdBranch.getMetaProdBranchId()
						&&ebkProdBranch.getMetaProdBranchId().equals(metaProductBranch.getMetaBranchId())){//存在
					leavedMetaBranchList.add(metaProductBranch);
					break;
				}
			}
		}
		removedMetaBranchList.removeAll(leavedMetaBranchList);
		
		
		//处理销售产品删除类别
		Map<Long, ProdProductBranch> prodProductBranchMap=new HashMap<Long, ProdProductBranch>();
		List<ProdProductBranch> prodProductBranchList=prodProductBranchService.getProductBranchByProductId(product.getProductId());
		List<ProdProductBranch> leavedProductBranchList=new ArrayList<ProdProductBranch>();
		List<ProdProductBranch> removedProductBranchList=new ArrayList<ProdProductBranch>();
		removedProductBranchList.addAll(prodProductBranchList);
		for (ProdProductBranch prodProductBranch : prodProductBranchList) {
			prodProductBranchMap.put(prodProductBranch.getProdBranchId(), prodProductBranch);
			for (EbkProdBranch ebkProdBranch : ebkProdBranchList) {
				if(null!=ebkProdBranch.getProdProductBranchId()
						&&ebkProdBranch.getProdProductBranchId().equals(prodProductBranch.getProdBranchId())){//存在
					leavedProductBranchList.add(prodProductBranch);
					break;
				}
			}
		}
		removedProductBranchList.removeAll(leavedProductBranchList);
		
		//新增/修改采购和销售类别 
		List<MetaProductBranch> currentMetaProductBranchList=new ArrayList<MetaProductBranch>();
		List<ProdProductBranch> currentProdProductBranchList=new ArrayList<ProdProductBranch>();
		
		EbkProdBranch virtualEbkProdBranch=null;//虚拟类别
		
		for (EbkProdBranch ebkProdBranch : ebkProdBranchList) {
			if(ROUTE_BRANCH.VIRTUAL.name().equals(ebkProdBranch.getBranchType())){//虚拟类别不在这里添加，单独处理
				virtualEbkProdBranch=ebkProdBranch;
				continue;
			}
			//采购 类别
			MetaProductBranch metaProductBranch=null;
			if(null==ebkProdBranch.getMetaProdBranchId()){//新增
				metaProductBranch=new MetaProductBranch();
				metaProductBranch.setMetaProductId(metaProduct.getMetaProductId());//采购产品id
				metaProductBranch.setProductIdSupplier(String.valueOf(ebkProdProduct.getEbkProdProductId()));//供应商产品ID
				metaProductBranch.setProductTypeSupplier(ebkProdBranch.getBranchType());//供应商产品类别
				metaProductBranch.setAdditional("false");
				metaProductBranch.setCreateTime(new Date());
				metaProductBranch.setSendFax("true");
				metaProductBranch.setTotalDecrease("false");
				metaProductBranch.setVirtual("false");
				metaProductBranch.setValid("Y");
			}else{
				 metaProductBranch = metaProductBranchMap.get(ebkProdBranch.getMetaProdBranchId());
			}
			metaProductBranch.setTotalStock(null);
			metaProductBranch.setBranchName(ebkProdBranch.getBranchName());
			metaProductBranch.setBranchType(ebkProdBranch.getBranchType());
			metaProductBranch.setAdultQuantity(ebkProdBranch.getAdultQuantity());
			metaProductBranch.setChildQuantity(ebkProdBranch.getChildQuantity());
			metaProductBranchService.save(metaProductBranch, OPERATOR_NAME);
			
			//销售产品类别
			ProdProductBranch prodProductBranch=prodProductBranchMap.get(ebkProdBranch.getProdProductBranchId());
			if(null==ebkProdBranch.getProdProductBranchId()||isAddProdProduct||null==prodProductBranch){//新增的
				prodProductBranch=new ProdProductBranch();
				// 新增产品类别为房差的，是否附加，改为是
				if (Constant.ROUTE_BRANCH.FANGCHA.name().equals(ebkProdBranch.getBranchType())) {
					prodProductBranch.setAdditional("true");
				} else {
					prodProductBranch.setAdditional("false");
				}
				prodProductBranch.setCreateTime(new Date());
				prodProductBranch.setPriceUnit("人");
				prodProductBranch.setValid("Y");
				prodProductBranch.setVisible("true");
				prodProductBranch.setMinimum(0L);
				prodProductBranch.setMaximum(100L);
				prodProductBranch.setProductId(product.getProductId());
			}
			
			prodProductBranch.setBranchName(ebkProdBranch.getBranchName());
			prodProductBranch.setBranchType(ebkProdBranch.getBranchType());
			prodProductBranch.setAdultQuantity(ebkProdBranch.getAdultQuantity());
			prodProductBranch.setDefaultBranch(ebkProdBranch.getDefaultBranch());
			prodProductBranch.setChildQuantity(ebkProdBranch.getChildQuantity());
			//房差设置
			if (Constant.ROUTE_BRANCH.FANGCHA.name().equals(ebkProdBranch.getBranchType())) {
				prodProductBranch.setAdditional("true");
			} else {
				prodProductBranch.setAdditional("false");
			}
			
			ResultHandleT<ProdProductBranch> childBranch = prodProductBranchService.saveBranch(prodProductBranch, OPERATOR_NAME);
			prodProductBranch=childBranch.getReturnContent();
			
			//默认类别校验
			if("true".equalsIgnoreCase(ebkProdBranch.getDefaultBranch())){
				prodProductBranchService.changeDefEBK(prodProductBranch, OPERATOR_NAME);
			}
			
			//更新关联
			ebkProdBranch.setMetaProdBranchId(metaProductBranch.getMetaBranchId());
			ebkProdBranch.setProdProductBranchId(prodProductBranch.getProdBranchId());
			ebkProdBranchDAO.updateEbkProdBranchDO(ebkProdBranch);
			
			currentMetaProductBranchList.add(metaProductBranch);
			currentProdProductBranchList.add(prodProductBranch);
			
			//是否附加
			if (Constant.ROUTE_BRANCH.FANGCHA.name().equals(ebkProdBranch.getBranchType())) {
				ProdProductRelation pr =prodProductRelationService.getProdRelation(product.getProductId(),prodProductBranch.getProdBranchId());
				if (pr==null) {
					prodProductRelationService.addRelation(product.getProductId(), prodProductBranch, OPERATOR_NAME);
				}
			}
		}
		//处理删除的采购产品类别
		for (MetaProductBranch removedMetaProductBranch:removedMetaBranchList) {
			metaProductBranchService.deleteMetaProductBranch(removedMetaProductBranch.getMetaBranchId(),OPERATOR_NAME);
		}
		//处理删除的销售产品类别
		for(ProdProductBranch prodProductBranch: removedProductBranchList){
			prodProductBranchService.deleteBranchByLogicForEBK(prodProductBranch.getProdBranchId(), OPERATOR_NAME);
		}
		
		//打包采购
		for(ProdProductBranch prodBranch : currentProdProductBranchList){
			for(MetaProductBranch metaBranch : currentMetaProductBranchList){
				if(prodBranch.getBranchName().equals(metaBranch.getBranchName())){
					//首选判断是否已经打包,不存在新增
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("metaBranchId", metaBranch.getMetaBranchId());
					map.put("prodBranchId", prodBranch.getProdBranchId());
					List<ProdProductBranchItem> list=prodProductBranchService.selectItemListByParam(map);
					if(null==list||list.size()==0){
						ProdProductBranchItem item = new ProdProductBranchItem();
						item.setCreateTime(new Date());
						item.setMetaProductId(metaBranch.getMetaProductId());
						item.setMetaBranchId(metaBranch.getMetaBranchId());
						item.setProdBranchId(prodBranch.getProdBranchId());
						item.setQuantity(1L);
						prodProductBranchService.addItem(item,prodBranch,OPERATOR_NAME);
					}
				}
			}
		}
		
		//删除已删除的采购打包
		for (MetaProductBranch removedMetaProductBranch:removedMetaBranchList) {
			//取一个采购商品对应的打包的所有打包项.
			List<ProdProductBranchItem> packItemList=prodProductBranchService.selectItemListByMetaBranch(removedMetaProductBranch.getMetaBranchId());
			for (ProdProductBranchItem prodProductBranchItem : packItemList) {
				prodProductBranchService.deleteItem(prodProductBranchItem.getBranchItemId(), OPERATOR_NAME);//删除
			}
		}
		
		//虚拟类别处理
		if(null!=virtualEbkProdBranch){
			MetaProductBranch virtualMetaProductBranch=null;
			//保存虚拟采购类别
			if(null==virtualEbkProdBranch.getMetaProdBranchId()){//新增
				virtualMetaProductBranch=new MetaProductBranch();
				virtualMetaProductBranch.setMetaProductId(metaProduct.getMetaProductId());//采购产品id
				virtualMetaProductBranch.setProductIdSupplier(String.valueOf(ebkProdProduct.getEbkProdProductId()));//供应商产品ID
				virtualMetaProductBranch.setProductTypeSupplier(virtualEbkProdBranch.getBranchType());//供应商产品类别
				virtualMetaProductBranch.setAdditional("false");
				virtualMetaProductBranch.setCreateTime(new Date());
				virtualMetaProductBranch.setSendFax("true");
				virtualMetaProductBranch.setTotalDecrease("false");
				virtualMetaProductBranch.setVirtual("true");
				virtualMetaProductBranch.setValid("Y");
			}else{//修改
				virtualMetaProductBranch = metaProductBranchMap.get(virtualEbkProdBranch.getMetaProdBranchId());
				//先删除之前的打包
				List<ProdProductBranchItem> packItemList=prodProductBranchService.selectItemListByMetaBranch(virtualMetaProductBranch.getMetaBranchId());
				for (ProdProductBranchItem prodProductBranchItem : packItemList) {
					prodProductBranchService.deleteItem(prodProductBranchItem.getBranchItemId(), OPERATOR_NAME);//删除
				}
			}
			virtualMetaProductBranch.setTotalStock(null);
			virtualMetaProductBranch.setBranchName(virtualEbkProdBranch.getBranchName());
			virtualMetaProductBranch.setBranchType(virtualEbkProdBranch.getBranchType());
			virtualMetaProductBranch.setAdultQuantity(virtualEbkProdBranch.getAdultQuantity());
			virtualMetaProductBranch.setChildQuantity(virtualEbkProdBranch.getChildQuantity());
			metaProductBranchService.save(virtualMetaProductBranch, OPERATOR_NAME);
			//更新关联
			virtualEbkProdBranch.setMetaProdBranchId(virtualMetaProductBranch.getMetaBranchId());
			virtualEbkProdBranch.setProdProductBranchId(0L);//没有对应的销售类别，所以设置为0
			ebkProdBranchDAO.updateEbkProdBranchDO(virtualEbkProdBranch);
			
			//需要打包的销售类别
			List<Long> prodBranchIds=new ArrayList<Long>();
			String[] virtualBranchIds=virtualEbkProdBranch.getVirtualBranchIds().split(",");
			for (String id : virtualBranchIds) {
				for (EbkProdBranch branch : ebkProdBranchList) {
					if(branch.getProdBranchId().toString().equals(id)){
						prodBranchIds.add(branch.getProdProductBranchId());
					}
				}
			}
			
			//将虚拟采购类别打包到销售类别上
			for(ProdProductBranch prodBranch : currentProdProductBranchList){
				if(!prodBranchIds.contains(prodBranch.getProdBranchId())){//需要打包的
					continue;
				}
				//首选判断是否已经打包,不存在新增
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("metaBranchId", virtualMetaProductBranch.getMetaBranchId());
				map.put("prodBranchId", prodBranch.getProdBranchId());
				List<ProdProductBranchItem> list=prodProductBranchService.selectItemListByParam(map);
				if(null==list||list.size()==0){
					ProdProductBranchItem item = new ProdProductBranchItem();
					item.setCreateTime(new Date());
					item.setMetaProductId(virtualMetaProductBranch.getMetaProductId());
					item.setMetaBranchId(virtualMetaProductBranch.getMetaBranchId());
					item.setProdBranchId(prodBranch.getProdBranchId());
					item.setQuantity(1L);
					prodProductBranchService.addItem(item,prodBranch,OPERATOR_NAME);
				}
			}
		}
		
		if(online){//更新销售产品为上线
			for(ProdProductBranch productBranch : currentProdProductBranchList){
				productBranch.setOnline(String.valueOf(online));
				prodProductBranchService.updateByPrimaryKeySelective(productBranch);
			}
		}
		
	}

	/**
	 * 保存销售产品基本信息
	 * @param ebkProdProduct ebk产品
	 * @param metaProduct 采购产品
	 * @param onlineTime 上线时间
	 * @param offlineTime 下线时间
	 * @param online 是否上线
	 * @param user 操作员
	 * @return
	 */
	private ProdRoute saveProdProduct(EbkProdProduct ebkProdProduct,
			MetaProduct metaProduct, Date onlineTime, Date offlineTime,
			boolean online,SupSupplier supSupplier,PermUser user,boolean isAdd) {
		ProdRoute product =new ProdRoute();
		String[] channels =null;
		ProdEContract eContract=new ProdEContract();
		if(!isAdd){//修改
			ProdProduct prod=prodProductService.getProdProduct(ebkProdProduct.getProdProductId());
			product=(ProdRoute)prod;
		}else{//新增
			//以下属性均为默认属性，或者只有新增需要增加的信息
			product.setCreateTime(new Date());
			product.setIsAperiodic("false");//是否为不定期产品
			product.setShowSaleDays(180);//显示几天价格~~可显示的销售时间价格表天数
			//提前确定成团小时数   最少成团人数
			if(EBK_PRODUCT_VIEW_TYPE.SURROUNDING_GROUP.name().equals(ebkProdProduct.getProductType())){//周边跟团游
				product.setInitialNum(16L);//最少成团人数
				product.setShowSaleDays(60);//显示几天价格
				product.setAheadConfirmHours(72);//提前确定成团小时数
				channels=new String[]{Constant.CHANNEL.BACKEND.name(),Constant.CHANNEL.FRONTEND.name(),Constant.CHANNEL.CLIENT.name()};//销售渠道：驴妈妈后台，驴妈妈前台，客户端
				product.setCouponActivity("true");//是否可以参加优惠活动:是
				product.setCouponAble("true");//是否可以使用优惠券：是
			}else if(EBK_PRODUCT_VIEW_TYPE.DOMESTIC_LONG.name().equals(ebkProdProduct.getProductType())){//国内长线
				product.setInitialNum(1L);//最少成团人数
				product.setShowSaleDays(180);//显示几天价格
				product.setAheadConfirmHours(72);//提前确定成团小时数
				channels=new String[]{Constant.CHANNEL.BACKEND.name(),Constant.CHANNEL.FRONTEND.name(),Constant.CHANNEL.CLIENT.name()};//销售渠道：驴妈妈后台，驴妈妈前台，客户端
				product.setCouponActivity("false");//是否可以参加优惠活动:否
				product.setCouponAble("true");//是否可以使用优惠券：是
			}else if(EBK_PRODUCT_VIEW_TYPE.ABROAD_PROXY.name().equals(ebkProdProduct.getProductType())){//出境代理
				if(null!=ebkProdProduct.getInitialNum()){
					product.setInitialNum(ebkProdProduct.getInitialNum().longValue());//最少成团人数
				}
				product.setAheadConfirmHours(0);//提前确定成团小时数
				channels=new String[]{Constant.CHANNEL.BACKEND.name(),Constant.CHANNEL.FRONTEND.name(),Constant.CHANNEL.CLIENT.name()};//销售渠道：驴妈妈后台，驴妈妈前台，客户端
				product.setCouponActivity("false");//是否可以参加优惠活动:否
				product.setCouponAble("false");//是否可以使用优惠券：否
				product.setRegionName(ebkProdProduct.getRegionName());
				product.setVisaType(ebkProdProduct.getVisaType());
				product.setCountry(ebkProdProduct.getCountry());
				product.setCity(ebkProdProduct.getCity());
			}
			
			product.setTravelGroupCode(product.getBizcode());//团号前缀,和所定义的产品编号一致
			product.setGroupType(Constant.GROUP_TYPE.AGENCY.name());//组团方式
			
			//电子合同
			eContract=prodProductService.getProdEContractByProductId(product.getProductId());
			if(null==eContract){
				eContract=new ProdEContract();
			}
			if(EBK_PRODUCT_VIEW_TYPE.ABROAD_PROXY.name().equals(ebkProdProduct.getProductType())){
				eContract.setEContractTemplate(ebkProdProduct.getEcontractTemplate());
			}else{
				eContract.setEContractTemplate(Constant.ECONTRACT_TEMPLATE.GROUP_ECONTRACT.name());//默认国内合同
				eContract.setGuideService(Constant.GUIDE_SERVICE.LOCAL_GUIDE.name());//导游服务：地陪
			}
			eContract.setAgency(supSupplier.getSupplierName());
			eContract.setProductId(product.getProductId());
			eContract.setTravelFormalities(Constant.TRAVEL_FORMALITIES.OTHERS.name());//    旅游手续：其他
			eContract.setGroupType(Constant.GROUP_TYPE.AGENCY.name());//组团方式：委托组团

		}
		
		
		//同步行程天数
		String isMultiJourney=ebkProdProduct.getIsMultiJourney();
		System.out.println(ebkProdProduct.getTourDays());
		Long days = StringUtils.isNotBlank(ebkProdProduct.getTourDays())==true?Long.valueOf(ebkProdProduct.getTourDays()):0L;
		product.setDays(days);
		if (("Y").equals(isMultiJourney)) {
			Map<String, Object> paraMap=new HashMap<String, Object>();
			paraMap.put("ebkProdProductId",ebkProdProduct.getEbkProdProductId());
			/*List<EbkMultiJourney> ebkMultiJourneyList=ebkMultiJourneyService.queryMultiJourneyByParams(paraMap);
			if (!ebkMultiJourneyList.isEmpty()) {
				product.setDays(ebkMultiJourneyList.get(0).getDays());
			}*/
			
		}else {
			EbkProdJourney ebkProdJourneyTerm=new EbkProdJourney();
			ebkProdJourneyTerm.setProductId(ebkProdProduct.getEbkProdProductId());
			List<EbkProdJourney> ebkProdJourneyList=ebkProdJourneyDAO.findListByTerm(ebkProdJourneyTerm);
			Map<Long, EbkProdJourney> ebkProdJourneyMap = new HashMap<Long, EbkProdJourney>();
			for (int i = 0; i < ebkProdJourneyList.size(); i++) {
				EbkProdJourney jo = ebkProdJourneyList.get(i);
				EbkProdJourney journey = ebkProdJourneyMap.get(jo.getProductId());
				if (journey == null) {
					ebkProdJourneyMap.put(jo.getProductId(), jo);
				} else {
					if (jo.getDayNumber() > journey.getDayNumber()) {
						ebkProdJourneyMap.put(jo.getProductId(), jo);
					}
				}
			}
			ebkProdJourneyList = new ArrayList<EbkProdJourney>(ebkProdJourneyMap.values());
			/*if (!ebkProdJourneyList.isEmpty()) {
				product.setDays(ebkProdJourneyList.get(0).getDayNumber());
			}.*/
		}
		
		product.setProductType(Constant.PRODUCT_TYPE.ROUTE.name());
		product.setBizcode(metaProduct.getBizCode());//这里保持跟采购一致
		product.setManagerId(ebkProdProduct.getManagerId());
		product.setOrgId(user.getDepartmentId());
		product.setAdditional("false");
		product.setWrapPage("false");
		product.setValid("Y");
		product.setIsForegin("N" );//是否为境外
		product.setTravellerInfoOptions("NAME,CARD_NUMBER,F_NAME,F_CARD_NUMBER,F_MOBILE");//游客必填信息
		product.seteContract(Constant.ECONTRACT_TYPE.NEED_ECONTRACT.name());//是否需要电子合同
		product.setPrePaymentAble("N");//是否使用预授权支付("Y", "N")
		product.setProductName(ebkProdProduct.getProdName());
		product.setFilialeName(ebkProdProduct.getOrgId());
		product.setSubProductType(ebkProdProduct.getSubProductType());//产品子类型
		//上下线控制
		product.setOnlineTime(onlineTime);
		product.setOfflineTime(offlineTime);
		product.setOnLine(String.valueOf(online));
		product.setRecommendInfoSecond(ebkProdProduct.getRecommend());//一句话推荐放在第二个
		product.setIsMultiJourney(ebkProdProduct.getIsMultiJourney());
		if(!isAdd){//修改基本信息
			prodProductService.updateProdProduct(product, channels, OPERATOR_NAME, null);
		}else{//新增
			ProdProduct prodProduct = prodProductService.addProductChannel(product, channels,OPERATOR_NAME);
			//更新支付对象信息
			Map<String,Object> param = new HashMap<String,Object>();
			param.put("productId", prodProduct.getProductId());
			param.put("payToLvmama", "true");
			param.put("payToSupplier", "false");
			prodProductService.updatePaymentTarget(param);
		}
		eContract.setProductId(product.getProductId());
		prodProductService.saveEContract(eContract);
		
		//更新一句话推荐
		prodProductService.updateProdRecommendWord(product);
		
		//上线下线控制
		Map<String,Object> params=new HashMap<String, Object>();
		params.put("productId", product.getProductId());
		params.put("onLine", String.valueOf(online));
		params.put("productName", product.getProductName());
		params.put("onLineStr", product.getStrOnLine());
		prodProductService.markIsSellable(params, OPERATOR_NAME);
		
		return product;
	}

	/**
	 * 保存履行对象，凭证对象，结算对象
	 * @param ebkProdProduct
	 * @param metaProduct
	 */
	private void saveTargetObject(EbkProdProduct ebkProdProduct,MetaProduct metaProduct) {
		EbkProdTarget targetTerm=new EbkProdTarget();
		targetTerm.setProductId(ebkProdProduct.getEbkProdProductId());
		List<EbkProdTarget> ebkProdTargetList=ebkProdTargetDAO.findListByTerm(targetTerm);
		String settlmentTargetId=null;
		String performTargetId=null;
		String bcertificateTargetId=null;
		for (EbkProdTarget ebkProdTarget : ebkProdTargetList) {
			if(CONTACT_TYPE.SUP_SETTLEMENT_TARGET.name().equals(ebkProdTarget.getTargetType())){
				settlmentTargetId=String.valueOf(ebkProdTarget.getTargetId());
				continue;
			}
			if(CONTACT_TYPE.SUP_PERFORM_TARGET.name().equals(ebkProdTarget.getTargetType())){
				performTargetId=String.valueOf(ebkProdTarget.getTargetId());
				continue;
			}
			if(CONTACT_TYPE.SUP_B_CERTIFICATE_TARGET.name().equals(ebkProdTarget.getTargetType())){
				bcertificateTargetId=String.valueOf(ebkProdTarget.getTargetId());
				continue;
			}
		}
		if(StringUtils.isBlank(settlmentTargetId)){
			LOG.error("The settlement target is blank.");
			throw new RuntimeException("The settlement target is blank.");
		}
		if(StringUtils.isBlank(performTargetId)){
			LOG.error("The perform target is blank.");
			throw new RuntimeException("The perform target is blank.");
		}
		if(StringUtils.isBlank(bcertificateTargetId)){
			LOG.error("The B's certificate target is blank.");
			throw new RuntimeException("The B's certificate target is blank.");
		}
		//凭证对象
		MetaBCertificate bcertificate=bCertificateTargetService.findMetaBCertificateByByMetaProductId(metaProduct.getMetaProductId());
		
		if(null!=bcertificate&&!bcertificate.getTargetId().equals(Long.valueOf(bcertificateTargetId))){//更新凭证对象
			bCertificateTargetService.deleteMetaRelation(bcertificate, OPERATOR_NAME);
			bcertificate=new MetaBCertificate();
			bcertificate.setTargetId(Long.valueOf(bcertificateTargetId));
			bcertificate.setMetaProductId(metaProduct.getMetaProductId());
			bCertificateTargetService.insertSuperMetaBCertificate(bcertificate, OPERATOR_NAME);
		}else if(null==bcertificate){//新增凭证对象
			bcertificate=new MetaBCertificate();
			bcertificate.setTargetId(Long.valueOf(bcertificateTargetId));
			bcertificate.setMetaProductId(metaProduct.getMetaProductId());
			bCertificateTargetService.insertSuperMetaBCertificate(bcertificate, OPERATOR_NAME);
		}
		
		//结算对象
		MetaSettlement metaSettlement=settlementTargetService.findMetaSettlementByMetaProductId(metaProduct.getMetaProductId());
		if(null!=metaSettlement&&!Long.valueOf(settlmentTargetId).equals(metaSettlement.getTargetId())){//更新结算对象
			settlementTargetService.deleteMetaRelation(metaSettlement, OPERATOR_NAME);
			metaSettlement= new MetaSettlement();
			metaSettlement.setMetaProductId(metaProduct.getMetaProductId());
			metaSettlement.setTargetId(Long.valueOf(settlmentTargetId));
			settlementTargetService.addMetaRelation(metaSettlement, OPERATOR_NAME);
		}else if(null==metaSettlement){//新增凭证对象
			metaSettlement= new MetaSettlement();
			metaSettlement.setMetaProductId(metaProduct.getMetaProductId());
			metaSettlement.setTargetId(Long.valueOf(settlmentTargetId));
			settlementTargetService.addMetaRelation(metaSettlement, OPERATOR_NAME);
		}
		
		
		//履行对象
		List<SupPerformTarget> supPerformTargetList=performTargetService.findSuperSupPerformTargetByMetaProductId(metaProduct.getMetaProductId());
		if(null!=supPerformTargetList&&supPerformTargetList.size()>0){
			SupPerformTarget supPerformTarget=supPerformTargetList.get(0);
			if(!supPerformTarget.getTargetId().equals(Long.valueOf(performTargetId))){//更新履行对象
				//删除
				MetaPerform delTerm = new MetaPerform();
				delTerm.setMetaProductId(metaProduct.getMetaProductId());
				delTerm.setTargetId(supPerformTarget.getTargetId());
				performTargetService.deleteMetaRelation(delTerm, OPERATOR_NAME);
				
				MetaPerform perform = new MetaPerform();
				perform.setMetaProductId(metaProduct.getMetaProductId());
				perform.setTargetId(Long.valueOf(performTargetId));
				performTargetService.addMetaRelation(perform, OPERATOR_NAME);
			}
		}else if(null==supPerformTargetList||supPerformTargetList.size()==0){//新增履行对象
			MetaPerform perform = new MetaPerform();
			perform.setMetaProductId(metaProduct.getMetaProductId());
			perform.setTargetId(Long.valueOf(performTargetId));
			performTargetService.addMetaRelation(perform, OPERATOR_NAME);
		}
	}

	/**
	 * 保存采购产品信息
	 * @param ebkProdProduct
	 * @param user
	 * @return
	 */
	private MetaProduct saveMetaProduct(EbkProdProduct ebkProdProduct, PermUser user) {
		//采购产品信息
		MetaProduct metaProduct = new MetaProductRoute();
		if(null!=ebkProdProduct.getMetaProductId()){//已经存在,修改
			metaProduct =metaProductService.getMetaProduct(ebkProdProduct.getMetaProductId());
		}else{//新增
			metaProduct.setBizCode(StringUtil.getRandomLetterString(2)+String.valueOf(ebkProdProduct.getEbkProdProductId()));//产品编号：由随机组合的2个字母+001
			metaProduct.setCurrencyType(Constant.FIN_CURRENCY.CNY.name());//币种
			metaProduct.setIsResourceSendFax("false"); //EBK/传真任务生成时间
			metaProduct.setPayToLvmama("true");//是否支付给驴妈妈
			metaProduct.setPayToSupplier("false");
			metaProduct.setProductType(Constant.PRODUCT_TYPE.ROUTE.name());
			metaProduct.setValid("Y");
			metaProduct.setSupplierChannel(Constant.SUPPLIER_CHANNEL.EBK.name());
			metaProduct.setIsAperiodic("false");//是否为不定期
			
			//默认子类    产品分类
			if(EBK_PRODUCT_VIEW_TYPE.SURROUNDING_GROUP.name().equals(ebkProdProduct.getProductType())){//周边跟团游产品
				metaProduct.setSubProductType(Constant.ROUTE_SUB_PRODUCT_TYPE.GROUP.name());//产品分类默认：境内跟团游
			}else if(EBK_PRODUCT_VIEW_TYPE.DOMESTIC_LONG.name().equals(ebkProdProduct.getProductType())){//国内长线
				if(SUB_PRODUCT_TYPE.GROUP_LONG.name().equals(ebkProdProduct.getSubProductType())){
					metaProduct.setSubProductType(Constant.ROUTE_SUB_PRODUCT_TYPE.GROUP.name());//境内跟团游
				}else if(SUB_PRODUCT_TYPE.FREENESS_LONG.name().equals(ebkProdProduct.getSubProductType())){
					metaProduct.setSubProductType(Constant.ROUTE_SUB_PRODUCT_TYPE.FREENESS.name());//自由行
				}
			}else if(EBK_PRODUCT_VIEW_TYPE.ABROAD_PROXY.name().equals(ebkProdProduct.getProductType())){//出境代理产品
				if(SUB_PRODUCT_TYPE.GROUP_FOREIGN.name().equals(ebkProdProduct.getSubProductType())){
					metaProduct.setSubProductType(Constant.ROUTE_SUB_PRODUCT_TYPE.GROUP_FOREIGN.name());//境外跟团游
				}else if(SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name().equals(ebkProdProduct.getSubProductType())){
					metaProduct.setSubProductType(Constant.ROUTE_SUB_PRODUCT_TYPE.FREENESS_FOREIGN.name());//境外自由行
				}
			}
		}

		metaProduct.setFilialeName(ebkProdProduct.getOrgId());//EBK建采购产品和销售产品时，供应商录入的所属分公司的值，会赋到采购主体字段
		metaProduct.setSupplierId(ebkProdProduct.getSupplierId());
		metaProduct.setProductName(ebkProdProduct.getMetaName());
		metaProduct.setManagerId(ebkProdProduct.getManagerId());
		metaProduct.setOrgId(user.getDepartmentId());//操作员所属部门
		//采购合同
		List<SupContract> list=supContractService.selectContractBySupplierId(ebkProdProduct.getSupplierId());
		if(null==list||list.size()==0){
			metaProduct.setContractId(null);
		}else{
			metaProduct.setContractId(list.get(0).getContractId());
		}
		
		if(null!=ebkProdProduct.getMetaProductId()){
			metaProductService.updateMetaProduct(metaProduct, OPERATOR_NAME);
		}else{
			Long metaProductId=metaProductService.addMetaProduct(metaProduct, OPERATOR_NAME);
			metaProduct.setMetaProductId(metaProductId);
			metaProduct.setValid("Y");
			Map<String,Object> map=new HashMap<String, Object>();
			map.put("metaProductId", metaProductId);
			map.put("valid", "Y");
			map.put("validStr", metaProduct.getStrValid());
			map.put("productName", metaProduct.getProductName());
			metaProductService.changeMetaProductValid(map, OPERATOR_NAME);
		}
		return metaProduct;
	}
	
	
	/**
	 * 初始化更新采购时间价格表
	 * @param metaProductBranch
	 * @param ebkProdTimePrice
	 * @return
	 */
	private TimePrice saveMetaProductBranchTimePrice(MetaProductBranch metaProductBranch, EbkProdTimePrice ebkProdTimePrice) {
		Date specDate=DateUtil.stringToDate(DateUtil.formatDate(ebkProdTimePrice.getSpecDate(), "yyyy-MM-dd"), "yyyy-MM-dd");
		TimePrice dbTimePrice = metaProductService.getMetaTimePriceByIdAndDate(metaProductBranch.getMetaBranchId(),specDate);
		//已经存在，并且关班，删除
		if(null != dbTimePrice&&"true".equals(ebkProdTimePrice.getForbiddenSell())){
			metaTimePriceDAO.deleteByPK(dbTimePrice.getTimePriceId());
			return null;
		}
		if(OPERATE_STATUS.ADD_OPERATE.getCode().equals(ebkProdTimePrice.getOperateStatus())&&null==dbTimePrice){//新增
			dbTimePrice= new TimePrice();
			dbTimePrice.setProductId(metaProductBranch.getMetaProductId());
			dbTimePrice.setMetaBranchId(metaProductBranch.getMetaBranchId());
			dbTimePrice.setSpecDate(specDate);
			dbTimePrice.setOverSale(ebkProdTimePrice.getOverSale());
			dbTimePrice.setResourceConfirm(ebkProdTimePrice.getResourceConfirm());
			dbTimePrice.setTotalDayStock(0L);
			dbTimePrice.setAheadHour(ebkProdTimePrice.getAheadHour());
			dbTimePrice.setCancelStrategy(ebkProdTimePrice.getCancelStrategy());
			if(null!=ebkProdTimePrice.getSettlementPrice()){
				dbTimePrice.setSettlementPrice(ebkProdTimePrice.getSettlementPrice());
			}
			if(null!=ebkProdTimePrice.getMarketPrice()){
				dbTimePrice.setMarketPrice(ebkProdTimePrice.getMarketPrice());
			}
			if(STOCK_TYPE.UNLIMITED_STOCK.getCode().equals(ebkProdTimePrice.getStockType())){
				dbTimePrice.setDayStock(-1L);//不限
			}else if(STOCK_TYPE.FIXED_STOCK.getCode().equals(ebkProdTimePrice.getStockType())){
				dbTimePrice.setDayStock(ebkProdTimePrice.getDayStock());//固定
			}
			metaProductService.insertTimePrice(dbTimePrice);
		}else if(OPERATE_STATUS.UPDATE_OPERATE.getCode().equals(ebkProdTimePrice.getOperateStatus())&&null!=dbTimePrice){//修改
			dbTimePrice.setOverSale(ebkProdTimePrice.getOverSale());
			dbTimePrice.setResourceConfirm(ebkProdTimePrice.getResourceConfirm());
			dbTimePrice.setAheadHour(ebkProdTimePrice.getAheadHour());
			dbTimePrice.setCancelStrategy(ebkProdTimePrice.getCancelStrategy());
			if(null!=ebkProdTimePrice.getSettlementPrice()){
				dbTimePrice.setSettlementPrice(ebkProdTimePrice.getSettlementPrice());
			}
			if(null!=ebkProdTimePrice.getMarketPrice()){
				dbTimePrice.setMarketPrice(ebkProdTimePrice.getMarketPrice());
			}
			
			if (dbTimePrice.getDayStock()!=-1&&EbkProdTimePrice.STOCK_TYPE.ADD_STOCK.getCode().equals(ebkProdTimePrice.getStockType())) {
				Long dsDayStock = dbTimePrice.getDayStock()+ ebkProdTimePrice.getDayStock();
				if (dsDayStock < 0L) {
					dsDayStock = 0L;
				}
				dbTimePrice.setDayStock(dsDayStock);
			}else if(dbTimePrice.getDayStock()!=-1&&EbkProdTimePrice.STOCK_TYPE.MINUS_STOCK.getCode().equals(ebkProdTimePrice.getStockType())){//减少
				Long dsDayStock = dbTimePrice.getDayStock()- ebkProdTimePrice.getDayStock();
				if (dsDayStock < 0L) {
					dsDayStock = 0L;
				}
				dbTimePrice.setDayStock(dsDayStock);
			} else if(STOCK_TYPE.UNLIMITED_STOCK.getCode().equals(ebkProdTimePrice.getStockType())){
				dbTimePrice.setDayStock(-1L);
			} else if(STOCK_TYPE.FIXED_STOCK.getCode().equals(ebkProdTimePrice.getStockType())){
				dbTimePrice.setDayStock(ebkProdTimePrice.getDayStock());
			}
			
			metaProductService.updateDynamicTimePrice(dbTimePrice);
		}
		return dbTimePrice;
	}
	
	/**
	 * 更新销售时间价格表
	 * @param prodProductBranch
	 * @param ebkProdTimePrice
	 * @param metaTimePrice
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws Exception
	 */
	private void saveProductTimePrice(ProdProductBranch prodProductBranch, EbkProdTimePrice ebkProdTimePrice, TimePrice metaTimePrice) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		Long productId = prodProductBranch.getProductId();
		Long prodBranchId = prodProductBranch.getProdBranchId();
		Long price = ebkProdTimePrice.getPrice();
		Date specDate=DateUtil.stringToDate(DateUtil.formatDate(ebkProdTimePrice.getSpecDate(), "yyyy-MM-dd"), "yyyy-MM-dd");
		TimePrice dbTimePrice = prodProductService.getTimePriceByProdId(productId, prodBranchId, specDate);
		//已经存在，并且关班，删除
		if(null != dbTimePrice&&"true".equals(ebkProdTimePrice.getForbiddenSell())){
			prodTimePriceDAO.deleteByPrimaryKey(dbTimePrice.getTimePriceId());
			return;
		}
		if(OPERATE_STATUS.ADD_OPERATE.getCode().equals(ebkProdTimePrice.getOperateStatus())&&null==dbTimePrice){//新增
			//新增销售时间价格时发现采购的时间价格已经不在了，那就没必要新增销售时间价格了
			if(null==metaTimePrice){
				LOG.info("When add product time price,the meta product time price could be found,date:"+specDate);
				return;
			}
			dbTimePrice= new TimePrice();
			dbTimePrice.setProductId(productId);
			dbTimePrice.setProdBranchId(prodBranchId);
			dbTimePrice.setSpecDate(specDate);
			dbTimePrice.setAheadHour(ebkProdTimePrice.getAheadHour());
			dbTimePrice.setPriceType(Constant.PRICE_TYPE.FIXED_PRICE.name());
			if(null!=price){
				dbTimePrice.setPrice(price);
			}
			dbTimePrice.setAheadHour(ebkProdTimePrice.getAheadHour());
			if(STOCK_TYPE.UNLIMITED_STOCK.getCode().equals(ebkProdTimePrice.getStockType())){//不限
				dbTimePrice.setDayStock(-1L);
			}else if(STOCK_TYPE.FIXED_STOCK.getCode().equals(ebkProdTimePrice.getStockType())){//固定
				dbTimePrice.setDayStock(ebkProdTimePrice.getDayStock());
			}
			prodProductService.insertTimePrice(dbTimePrice, metaTimePrice);
		}else if(OPERATE_STATUS.UPDATE_OPERATE.getCode().equals(ebkProdTimePrice.getOperateStatus())&&null!=dbTimePrice){//修改
			
			//ebk中的库存维护导致，采购时间价格表被关班了但是销售的时间价格表里还没删除，这里将对应的销售也删除即可
			if(null==metaTimePrice){
				LOG.info("The meta product time price could be found,date:"+dbTimePrice.getSpecDate());
				prodTimePriceDAO.deleteByPrimaryKey(dbTimePrice.getTimePriceId());
				return;
			}
			
			dbTimePrice.setAheadHour(ebkProdTimePrice.getAheadHour());
			dbTimePrice.setPriceType(Constant.PRICE_TYPE.FIXED_PRICE.name());
			if(null!=price){
				dbTimePrice.setPrice(price);
			}
			dbTimePrice.setAheadHour(ebkProdTimePrice.getAheadHour());
			
			if (dbTimePrice.getDayStock()!=-1&&EbkProdTimePrice.STOCK_TYPE.ADD_STOCK.getCode().equals(ebkProdTimePrice.getStockType())) {
				Long dsDayStock = dbTimePrice.getDayStock()+ ebkProdTimePrice.getDayStock();
				if (dsDayStock < 0L) {
					dsDayStock = 0L;
				}
				dbTimePrice.setDayStock(dsDayStock);
			}else if(dbTimePrice.getDayStock()!=-1&&EbkProdTimePrice.STOCK_TYPE.MINUS_STOCK.getCode().equals(ebkProdTimePrice.getStockType())){
				Long dsDayStock = dbTimePrice.getDayStock()- ebkProdTimePrice.getDayStock();
				if (dsDayStock < 0L) {
					dsDayStock = 0L;
				}
				dbTimePrice.setDayStock(dsDayStock);
			} else if(STOCK_TYPE.UNLIMITED_STOCK.getCode().equals(ebkProdTimePrice.getStockType())){
				dbTimePrice.setDayStock(-1L);
			}else if(STOCK_TYPE.FIXED_STOCK.getCode().equals(ebkProdTimePrice.getStockType())){
				dbTimePrice.setDayStock(ebkProdTimePrice.getDayStock());
			}
			
			prodProductService.updateDynamicTimePrice(dbTimePrice, metaTimePrice);
		}
	}
	
	/**
	 * 更新团号
	 * @param ebkProdTimePrice ebk时间价格
	 * @param teamNo 团号
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalAccessException 
	 * @throws Exception
	 */
	private void saveMetaTravelCode(EbkProdTimePrice ebkProdTimePrice,String teamNo) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		MetaTravelCode metaTravelCode = metaTravelCodeService.selectBySuppAndDate(String.valueOf(ebkProdTimePrice.getProductId()), ebkProdTimePrice.getSpecDate());
		MetaTravelCode bean = new MetaTravelCode();
		if(null == metaTravelCode){
			bean.setSpecDate(ebkProdTimePrice.getSpecDate());
			bean.setSupplierProductId(String.valueOf(ebkProdTimePrice.getProductId()));
			bean.setTravelCode(teamNo);
			bean.setTravelCodeId(teamNo);
			metaTravelCodeService.insert(bean);
		}else{
			PropertyUtils.copyProperties(bean, metaTravelCode);
			bean.setTravelCode(teamNo);
			bean.setTravelCodeId(teamNo);
			metaTravelCodeService.updateByPrimaryKeySelective(bean);
		}
	}
	
	
	public void setEbkProdProductDAO(EbkProdProductDAO ebkProdProductDAO) {
		this.ebkProdProductDAO = ebkProdProductDAO;
	}
	public void setEbkProdTargetDAO(EbkProdTargetDAO ebkProdTargetDAO) {
		this.ebkProdTargetDAO = ebkProdTargetDAO;
	}
	public void setEbkProdJourneyDAO(EbkProdJourneyDAO ebkProdJourneyDAO) {
		this.ebkProdJourneyDAO = ebkProdJourneyDAO;
	}
	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}
	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
	public void setbCertificateTargetService(
			BCertificateTargetService bCertificateTargetService) {
		this.bCertificateTargetService = bCertificateTargetService;
	}
	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}
	public void setSettlementTargetService(
			SettlementTargetService settlementTargetService) {
		this.settlementTargetService = settlementTargetService;
	}
	public void setEbkProdBranchDAO(EbkProdBranchDAO ebkProdBranchDAO) {
		this.ebkProdBranchDAO = ebkProdBranchDAO;
	}
	public void setEbkProdContentDAO(EbkProdContentDAO ebkProdContentDAO) {
		this.ebkProdContentDAO = ebkProdContentDAO;
	}
	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}
	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}
	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}
	public void setProdProductPlaceService(
			ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}
	public void setViewPageJourneyService(
			ViewPageJourneyService viewPageJourneyService) {
		this.viewPageJourneyService = viewPageJourneyService;
	}
	public void setViewPageService(ViewPageService viewPageService) {
		this.viewPageService = viewPageService;
	}
	public void setEbkProdTimePriceDAO(EbkProdTimePriceDAO ebkProdTimePriceDAO) {
		this.ebkProdTimePriceDAO = ebkProdTimePriceDAO;
	}
	public void setMetaTimePriceDAO(MetaTimePriceDAO metaTimePriceDAO) {
		this.metaTimePriceDAO = metaTimePriceDAO;
	}
	public void setProdTimePriceDAO(ProdTimePriceDAO prodTimePriceDAO) {
		this.prodTimePriceDAO = prodTimePriceDAO;
	}
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}
	public void setMetaTravelCodeService(MetaTravelCodeService metaTravelCodeService) {
		this.metaTravelCodeService = metaTravelCodeService;
	}

	public void setEbkProdModelPropertyDAO(
			EbkProdModelPropertyDAO ebkProdModelPropertyDAO) {
		this.ebkProdModelPropertyDAO = ebkProdModelPropertyDAO;
	}

	public void setProdProductModelPropertyService(
			ProdProductModelPropertyService prodProductModelPropertyService) {
		this.prodProductModelPropertyService = prodProductModelPropertyService;
	}

	public void setEbkProdPlaceDAO(EbkProdPlaceDAO ebkProdPlaceDAO) {
		this.ebkProdPlaceDAO = ebkProdPlaceDAO;
	}

	public void setEbkExtraProdConfigDAO(EbkExtraProdConfigDAO ebkExtraProdConfigDAO) {
		this.ebkExtraProdConfigDAO = ebkExtraProdConfigDAO;
	}

	public void setProdProductRelationService(
			ProdProductRelationService prodProductRelationService) {
		this.prodProductRelationService = prodProductRelationService;
	}

	public void setSupContractService(SupContractService supContractService) {
		this.supContractService = supContractService;
	}

	public void setPlaceService(PlaceService placeService) {
		this.placeService = placeService;
	}

	public void setComLogService(ComLogService comLogService) {
		this.comLogService = comLogService;
	}

	public void setEbkProdRelationService(
			EbkProdRelationService ebkProdRelationService) {
		this.ebkProdRelationService = ebkProdRelationService;
	}

	public void setViewMultiJourneyService(
			ViewMultiJourneyService viewMultiJourneyService) {
		this.viewMultiJourneyService = viewMultiJourneyService;
	}
	
}
