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
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProductBranch;
import com.lvmama.comm.bee.po.prod.ProdProductRelation;
import com.lvmama.comm.bee.po.prod.ProdRoute;
import com.lvmama.comm.bee.po.prod.ProductModelProperty;
import com.lvmama.comm.bee.po.prod.ViewContent;
import com.lvmama.comm.bee.po.prod.ViewJourney;
import com.lvmama.comm.bee.po.prod.ViewMultiJourney;
import com.lvmama.comm.bee.po.prod.ViewPage;
import com.lvmama.comm.bee.service.ebooking.EbkProdBranchService;
import com.lvmama.comm.bee.service.ebooking.EbkProdImportService;
import com.lvmama.comm.bee.service.ebooking.EbkProdPlaceService;
import com.lvmama.comm.bee.service.ebooking.EbkProdProductService;
import com.lvmama.comm.bee.service.ebooking.EbkProdRelationService;
import com.lvmama.comm.bee.service.ebooking.EbkProdSnapshotService;
import com.lvmama.comm.bee.service.ebooking.EbkProdTargetService;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductModelPropertyService;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.service.prod.ProdProductRelationService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.prod.ProductModelPropertyService;
import com.lvmama.comm.bee.service.view.ViewMultiJourneyService;
import com.lvmama.comm.bee.service.view.ViewPageJourneyService;
import com.lvmama.comm.bee.service.view.ViewPageService;
import com.lvmama.comm.pet.po.prod.ProdAssemblyPoint;
import com.lvmama.comm.pet.po.prod.ProdEContract;
import com.lvmama.comm.pet.po.prod.ProdProductModelProperty;
import com.lvmama.comm.pet.po.prod.ProdProductPlace;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.po.pub.ComPicture.PICTURE_OBJECT_TYPE;
import com.lvmama.comm.pet.po.sup.MetaBCertificate;
import com.lvmama.comm.pet.po.sup.MetaSettlement;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.pet.service.sup.SettlementTargetService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.EBK_PRODUCT_AUDIT_STATUS;
import com.lvmama.comm.vo.Constant.EBK_PRODUCT_VIEW_TYPE;
import com.lvmama.comm.vo.Constant.EBK_PROD_PICTURE_TYPE;
import com.lvmama.comm.vo.Constant.ROUTE_BRANCH;
import com.lvmama.comm.vo.Constant.SUB_PRODUCT_TYPE;
import com.lvmama.comm.vo.Constant.VIEW_CONTENT_TYPE;
import com.lvmama.ebk.dao.EbkExtraProdConfigDAO;
import com.lvmama.ebk.dao.EbkMultiJourneyDAO;
import com.lvmama.ebk.dao.EbkProdContentDAO;
import com.lvmama.ebk.dao.EbkProdJourneyDAO;
import com.lvmama.ebk.dao.EbkProdModelPropertyDAO;
import com.lvmama.ebk.dao.EbkProdPlaceDAO;
import com.lvmama.ebk.dao.EbkProdProductDAO;

/**
 * Super线路产品数据导入ebk系统
 * 
 * @author taiqichao
 *
 */
public class EbkRouteProdImportServiceImpl implements EbkProdImportService {
	
	private static final Log LOG=LogFactory.getLog(EbkRouteProdImportServiceImpl.class);
	
	private static final int PAGESIZE=1000;
	
	private SupplierService supplierService;
	
	private MetaProductService metaProductService;
	
	private ProdProductBranchService prodProductBranchService;
	
	private MetaProductBranchService metaProductBranchService;
	
	private ProdProductService prodProductService;
	
	private EbkProdProductDAO ebkProdProductDAO;
	
	private ProdProductPlaceService prodProductPlaceService;
	
	private BCertificateTargetService bCertificateTargetService;
	
	private SettlementTargetService settlementTargetService;
	
	private PerformTargetService performTargetService;
	
	private EbkProdTargetService ebkProdTargetService;
	
	private EbkProdPlaceService ebkProdPlaceService;
	
	private EbkProdPlaceDAO ebkProdPlaceDAO;
	
	private EbkProdBranchService ebkProdBranchService;
	
	private ComPictureService comPictureService;
	
	private EbkProdContentDAO ebkProdContentDAO;
	
	private ViewPageService viewPageService;
	
	private EbkProdJourneyDAO ebkProdJourneyDAO;
	
	private EbkMultiJourneyDAO ebkMultiJourneyDAO;
	
	private ViewPageJourneyService viewPageJourneyService;
	
	private ProdProductModelPropertyService prodProductModelPropertyService;
	
	private EbkProdModelPropertyDAO ebkProdModelPropertyDAO;
	
	private ProductModelPropertyService productModelPropertyService;
	
	private EbkExtraProdConfigDAO ebkExtraProdConfigDAO;
	
	private EbkProdProductService ebkProdProductService;
	
	private EbkProdSnapshotService ebkProdSnapshotService;
	
	private ProdProductRelationService prodProductRelationService;
	
	private EbkProdRelationService ebkProdRelationService;
	
	private ViewMultiJourneyService viewMultiJourneyService;
	
	private final static List<String> SUBPRODUCTTYPELIST = new ArrayList<String>();
	
	//public static String[] subProductTypes = {"FREENESS_FOREIGN","GROUP_FOREIGN","FREENESS","GROUP"};
	public static String[] subProductTypes = {"GROUP_FOREIGN","FREENESS_FOREIGN","GROUP_LONG","FREENESS_LONG","GROUP","SELFHELP_BUS"};
	
	/**
	GROUP("短途跟团游"),
	GROUP_LONG("长途跟团游"),
	GROUP_FOREIGN("出境跟团游"),
	FREENESS("目的地自由行"),
	FREENESS_LONG("长途自由行"),
	FREENESS_FOREIGN("出境自由行"),
	SELFHELP_BUS("自助巴士班"),
	 */
	
	/**
	 * 计算总页数
	 * @param totalRowCount 总记录数
	 * @param pageSize 每页行数
	 * @return 总页数
	 */
	public int getTotalPages(Integer totalRowCount,Integer pageSize) {
		int totalPages=0;
		if (totalRowCount % pageSize == 0){
			totalPages = (int)totalRowCount / pageSize;
		}else{
			totalPages = 1 + (int)totalRowCount / pageSize;
		}
		return totalPages;
	}
	
	
	/**
	 * 根据ebk大类获取要同步的super采购产品子类
	 * @param ebkProductViewType ebk大类
	 * @return super采购产品子类
	 */
	private List<String> getSubProductTypes(EBK_PRODUCT_VIEW_TYPE ebkProductViewType){
		List<String> subProductTypeList = new ArrayList<String>();
		if(EBK_PRODUCT_VIEW_TYPE.SURROUNDING_GROUP.equals(ebkProductViewType)){//周边跟团游产品
			subProductTypeList.add(SUB_PRODUCT_TYPE.GROUP.getCode());//境内跟团游
		}else if(EBK_PRODUCT_VIEW_TYPE.DOMESTIC_LONG.equals(ebkProductViewType)){//国内长线
			subProductTypeList.add(SUB_PRODUCT_TYPE.GROUP.getCode());//境内跟团游
			subProductTypeList.add(SUB_PRODUCT_TYPE.FREENESS.getCode());//自由行
		}else if(EBK_PRODUCT_VIEW_TYPE.ABROAD_PROXY.equals(ebkProductViewType)){//出境代理产品
			subProductTypeList.add(SUB_PRODUCT_TYPE.GROUP_FOREIGN.getCode());//境外跟团游
			subProductTypeList.add(SUB_PRODUCT_TYPE.FREENESS_FOREIGN.getCode());//境外自由行
		}
		return subProductTypeList;
	}
	
	
	@Override
	public void importProductsByProductType(Long supplierId,EBK_PRODUCT_VIEW_TYPE ebkProductViewType) throws Exception {
		SupSupplier supSupplier=supplierService.getSupplier(supplierId);
		if(null==supSupplier){
			LOG.error("Unknown ebk sup supplier id:"+supplierId);
			throw new RuntimeException("Unknown ebk sup supplier id:"+supplierId);
		}
		//查询供应商所有的线路类采购产品
		Map<String, Object> metaQueryTerms=new HashMap<String, Object>();
		metaQueryTerms.put("productType", Constant.PRODUCT_TYPE.ROUTE.name());
		List<String> subProductTypeList=this.getSubProductTypes(ebkProductViewType);
		if(null!=subProductTypeList&&subProductTypeList.size()>0){
			metaQueryTerms.put("subProductTypeList", subProductTypeList);
		}
		metaQueryTerms.put("valid","Y");
		metaQueryTerms.put("supplierId", supplierId);
		Integer totalRowCount=metaProductService.selectRowCount(metaQueryTerms);
		LOG.info("supplier id:"+supplierId+",total product count:"+totalRowCount);
		int totalPages=getTotalPages(totalRowCount,PAGESIZE);//每页1000条,总页数
		for(int page=1;page<=totalPages;page++){//分页查询导入
			int startRow=(page - 1) * PAGESIZE + 1;
			int endRow=0;
			if(page==totalPages){
				endRow=totalRowCount;
			}else{
				endRow=page*PAGESIZE;
			}
			metaQueryTerms.put("_startRow",startRow);
			metaQueryTerms.put("_endRow", endRow);
			List<MetaProduct>  metaProductList=metaProductService.findMetaProduct(metaQueryTerms);
			LOG.info("supplier id:"+supplierId+",current page metaProduct size:"+metaProductList.size());
			//批量导入
			batchImport(metaProductList,ebkProductViewType);
		}
	}
	
	/**
	 * 批量导入
	 * @param metaProductList 采购产品集合
	 * @param ebkProductViewType ebk产品大类
	 * @throws Exception
	 */
	private void batchImport(List<MetaProduct> metaProductList,EBK_PRODUCT_VIEW_TYPE ebkProductViewType) throws Exception{
		if(null==metaProductList||metaProductList.size()==0){
			return;
		}
		for (MetaProduct metaProduct : metaProductList) {
			ProdProduct prodProduct=getProductId(metaProduct,ebkProductViewType);
			if(null!=prodProduct){//校验是否符合导入条件
				importProduct(metaProduct,prodProduct);
			}
		}
	}
	
	
	/**
	 * 单个从Super产品库导入ebk系统
	 * @param prodProductId 销售产品id
	 * @throws Exception
	 */
	public void importProdProduct(Long prodProductId) throws Exception{
		//查询采购产品的类型
				ProdProduct prodProduct=prodProductService.getProdProduct(prodProductId);
				if(prodProduct == null){
					LOG.error("Unknown ebk product id:"+prodProduct);
					throw new RuntimeException("Unknown ebk product id:"+prodProduct);
				}
				//大类匹配线路类型
				if(!Constant.PRODUCT_TYPE.ROUTE.name().equals(prodProduct.getProductType())){
					return;
				}
				//子类校验
				//if(!subProductTypes.contains(prodProduct.getSubProductType())){
				//	return;
				//}
				boolean isImport=false;
				for (int i = 0; i < subProductTypes.length; i++) {
					if (subProductTypes[i].equals(prodProduct.getSubProductType())) {
						isImport=true;
					}
				}
				if (isImport) {
					MetaProduct metaProduct = this.getProductIdbyProdProduct(prodProduct, subProductTypes);
					if(null!=metaProduct){//校验是否符合导入条件
						importProduct(metaProduct, prodProduct);
					}
				}
				
				
	}
	/**
	 * 单个从Super产品库导入ebk系统
	 * @param metaProductId 采购产品id
	 * @throws Exception
	 */
	@Override
	public void importMetaProduct(Long metaProductId) throws Exception {
		//查询采购产品的类型
		MetaProduct metaProduct = metaProductService.getMetaProductByMetaProductId(metaProductId);
		//metaProductService.getMetaProduct(metaProductId);
		if(metaProduct == null){
			LOG.error("Unknown ebk product id:"+metaProductId);
			throw new RuntimeException("Unknown ebk product id:"+metaProductId);
		}
		//大类匹配线路类型
		if(!Constant.PRODUCT_TYPE.ROUTE.name().equals(metaProduct.getProductType())){
			return;
		}
		//子类校验
		//if(!SUBPRODUCTTYPELIST.contains(metaProduct.getSubProductType())){
			//return;
		//}
		boolean blog=false;
		for (int i = 0; i < subProductTypes.length; i++) {
			if (subProductTypes[i].equals(metaProduct.getSubProductType())) {
				blog=true;
			}
		}
		if (blog) {
			ProdProduct prodProduct = this.getProductIdbyMetaProduct(metaProduct, subProductTypes);
			if(null!=prodProduct){//校验是否符合导入条件
				importProduct(metaProduct, prodProduct);
			}
		}
		
	}
	
	/**
	 * 获取对应销售产品
	 * @param metaProduct 采购产品
	 * @param ebkProductViewType EBK 大类
	 * @return
	 */
	private ProdProduct getProductIdbyMetaProduct(MetaProduct metaProduct, String[] subProductTypes){
		//所有采购类别
		List<MetaProductBranch> metaBranchList=metaProductBranchService.selectBranchListByProductId(metaProduct.getMetaProductId());
		if(null==metaBranchList||metaBranchList.size()==0){
			return null;
		}
		boolean isImport=true;
		List<Long> productIds=new ArrayList<Long>();
		for (MetaProductBranch metaProductBranch : metaBranchList) {
			if(ROUTE_BRANCH.VIRTUAL.name().equals(metaProductBranch.getBranchType())){//排除虚拟类别
				continue;
			}
			List<ProdProductBranch> prodBranchList=prodProductBranchService.getProductBranchByMetaProdBranchId(metaProductBranch.getMetaBranchId());
			if(null==prodBranchList||prodBranchList.size()==0){//没有被打包
				isImport=false;
				break;
			}
			if(prodBranchList.size()>1){//被打包多次
				isImport=false;
				break;
			}
			ProdProductBranch prodProductBranch=prodBranchList.get(0);
			if(!productIds.contains(prodProductBranch.getProductId())){
				productIds.add(prodProductBranch.getProductId());
			}
			if(productIds.size()>1){//打包对应有多个销售产品
				isImport=false;
				break;
			}
		}
		if(isImport){
			ProdProduct prodProduct=prodProductService.getProdProduct(productIds.get(0));
			
			if(null==prodProduct||"N".equals(prodProduct.getValid())){//不存在或者不可用的不要
				return null;
			}
			
			//如果ebk已经存在的产品，只修改状态为已审核通过的产品,其他状态不处理
			Map<String,Object> term=new HashMap<String,Object>();
			term.put("metaProductId", metaProduct.getMetaProductId());
			term.put("prodProductId", prodProduct.getProductId());
			List<EbkProdProduct> ebkProdProductList=ebkProdProductDAO.findListByExample(term);
			if(null!=ebkProdProductList&&ebkProdProductList.size()>0){
				if(!EBK_PRODUCT_AUDIT_STATUS.THROUGH_AUDIT.name().equals(ebkProdProductList.get(0).getStatus())){
					return null;
				}
			}
			
			
			boolean hits=false;//当前销售产品是否命中要同步的销售子类
			
			for(String subProdType:subProductTypes){
				if(subProdType.equals(prodProduct.getSubProductType())){
					hits=true;
					break;
				}
			}
			
			if(!hits){
				return null;
			}
			
			return prodProduct;
		}
		
		return null;
	}
	
	/**
	 * 获取对应销售产品
	 * @param metaProduct 采购产品
	 * @param ebkProductViewType EBK 大类
	 * @return
	 */
	private MetaProduct getProductIdbyProdProduct(ProdProduct prodProduct, String[] subProductTypes){
		//所有采购类别
		List<ProdProductBranch> prodBranchList=prodProductBranchService.getProductBranchByProductId(prodProduct.getProductId());
		if(null==prodBranchList||prodBranchList.size()==0){
			return null;
		}
		boolean isImport=true;
		List<Long> productIds=new ArrayList<Long>();
		for (ProdProductBranch prodProductBranch : prodBranchList) {
			if(ROUTE_BRANCH.VIRTUAL.name().equals(prodProductBranch.getBranchType())){//排除虚拟类别
				continue;
			}
			List<MetaProductBranch> metaBranchList=metaProductBranchService.getMetaProductBranchByProdBranchId(prodProductBranch.getProdBranchId());
			if(null==metaBranchList||metaBranchList.size()==0){//没有被打包
				isImport=false;
				break;
			}
			if(metaBranchList.size()>1){//被打包多次
				isImport=false;
				break;
			}
			MetaProductBranch metaProductBranch=metaBranchList.get(0);
			if(!productIds.contains(metaProductBranch.getMetaProductId())){
				productIds.add(metaProductBranch.getMetaProductId());
			}
			if(productIds.size()>1){//打包对应有多个销售产品
				isImport=false;
				break;
			}
		}
		if(isImport){
			MetaProduct metaProduct=metaProductService.getMetaProduct(productIds.get(0));
			if(null==metaProduct||"N".equals(metaProduct.getValid())){//不存在或者不可用的不要
				return null;
			}
			
			//如果ebk已经存在的产品，只修改状态为已审核通过的产品,其他状态不处理
			Map<String,Object> term=new HashMap<String,Object>();
			term.put("metaProductId", metaProduct.getMetaProductId());
			term.put("prodProductId", prodProduct.getProductId());
			List<EbkProdProduct> ebkProdProductList=ebkProdProductDAO.findListByExample(term);
			if(null!=ebkProdProductList&&ebkProdProductList.size()>0){
				if(!EBK_PRODUCT_AUDIT_STATUS.THROUGH_AUDIT.name().equals(ebkProdProductList.get(0).getStatus())){
					return null;
				}
			}
			
			
			boolean hits=false;//当前销售产品是否命中要同步的销售子类
			
			for(String subProdType:subProductTypes){
				if(subProdType.equals(prodProduct.getSubProductType())){
					hits=true;
					break;
				}
			}
			
			if(!hits){
				return null;
			}
			
			return metaProduct;
		}
		
		return null;
	}
	
	/**
	 * 获取对应销售产品
	 * @param metaProduct 采购产品
	 * @param ebkProductViewType EBK 大类
	 * @return
	 */
	private ProdProduct getProductId(MetaProduct metaProduct,EBK_PRODUCT_VIEW_TYPE ebkProductViewType){
		//所有采购类别
		List<MetaProductBranch> metaBranchList=metaProductBranchService.selectBranchListByProductId(metaProduct.getMetaProductId());
		if(null==metaBranchList||metaBranchList.size()==0){
			return null;
		}
		boolean isImport=true;
		List<Long> productIds=new ArrayList<Long>();
		for (MetaProductBranch metaProductBranch : metaBranchList) {
			if(ROUTE_BRANCH.VIRTUAL.name().equals(metaProductBranch.getBranchType())){//排除虚拟类别
				continue;
			}
			List<ProdProductBranch> prodBranchList=prodProductBranchService.getProductBranchByMetaProdBranchId(metaProductBranch.getMetaBranchId());
			if(null==prodBranchList||prodBranchList.size()==0){//没有被打包
				isImport=false;
				break;
			}
			if(prodBranchList.size()>1){//被打包多次
				isImport=false;
				break;
			}
			ProdProductBranch prodProductBranch=prodBranchList.get(0);
			if(!productIds.contains(prodProductBranch.getProductId())){
				productIds.add(prodProductBranch.getProductId());
			}
			if(productIds.size()>1){//打包对应有多个销售产品
				isImport=false;
				break;
			}
		}
		if(isImport){
			ProdProduct prodProduct=prodProductService.getProdProduct(productIds.get(0));
			
			if(null==prodProduct||"N".equals(prodProduct.getValid())){//不存在或者不可用的不要
				return null;
			}
			
			//如果ebk已经存在的产品，只修改状态为已审核通过的产品,其他状态不处理
			Map<String,Object> term=new HashMap<String,Object>();
			term.put("metaProductId", metaProduct.getMetaProductId());
			term.put("prodProductId", prodProduct.getProductId());
			List<EbkProdProduct> ebkProdProductList=ebkProdProductDAO.findListByExample(term);
			if(null!=ebkProdProductList&&ebkProdProductList.size()>0){
				if(!EBK_PRODUCT_AUDIT_STATUS.THROUGH_AUDIT.name().equals(ebkProdProductList.get(0).getStatus())){
					return null;
				}
			}
			
			
			boolean hits=false;//当前销售产品是否命中要同步的销售子类
			for(SUB_PRODUCT_TYPE subProdType:ebkProductViewType.getSubProductTypes()){
				if(subProdType.getCode().equals(prodProduct.getSubProductType())){
					hits=true;
					break;
				}
			}
			
			if(!hits){
				return null;
			}
			
			return prodProduct;
		}
		
		return null;
	}
	
	
	/**
	 * 导入一个产品
	 * @param metaProduct
	 */
	private void importProduct(MetaProduct metaProduct,ProdProduct prodProduct) throws Exception{
		
		if(null!=metaProduct&&null!=prodProduct){
			LOG.info("ImportProduct metaProduct id:"+metaProduct.getMetaProductId()+",prodProduct id:"+prodProduct.getProductId()+",start...");
		}
		
		//保存产品基本信息
		EbkProdProduct ebkProdProduct = saveEbkProd(metaProduct, prodProduct);
		
		//保存履行对象，凭证对象，结算对象
		saveTarget(metaProduct, ebkProdProduct);
		
		//保存景点信息
		savePlace(prodProduct, ebkProdProduct);
		
		//保存类别信息
		saveBranch(metaProduct, ebkProdProduct);
		
		//保存图片信息
		saveProdPicture(prodProduct, ebkProdProduct);
		
		//保存产品描述信息
		saveProdContent(prodProduct, ebkProdProduct);
		
		//保存产品行程安排
		saveJourney(prodProduct, ebkProdProduct);
		
		//保存发车信息
		saveProdAssemblyPoint(prodProduct, ebkProdProduct);
		
		//保存产品其他信息
		saveProdModelProperty(prodProduct, ebkProdProduct);
		
		//针对出境代理产品同步签证产品
		saveRelation(prodProduct, ebkProdProduct);
		
		//生成快照
		EbkProdProduct product = ebkProdProductService.findEbkProdAllByPrimaryKey(ebkProdProduct.getEbkProdProductId());
		ebkProdSnapshotService.saveProdSnapshot(product);
		
		if(null!=metaProduct&&null!=prodProduct){
			LOG.info("ImportProduct metaProduct id:"+metaProduct.getMetaProductId()+",prodProduct id:"+prodProduct.getProductId()+",end...");
		}
	}

	/**
	 * 保存针对出境代理产品同步签证产品
	 * @param prodProduct
	 * @param ebkProdProduct
	 */
	@SuppressWarnings("deprecation")
	private void saveRelation(ProdProduct prodProduct,
			EbkProdProduct ebkProdProduct) {
		if(EBK_PRODUCT_VIEW_TYPE.ABROAD_PROXY.name().equals(ebkProdProduct.getProductType())){
			//排除默认的配置关联
			ProdRoute prodRoute=(ProdRoute)prodProduct;
			EbkExtraProdConfig term=new EbkExtraProdConfig();
			term.setDaysTrip(prodRoute.getDays().intValue());
			term.setEbkProductType(ebkProdProduct.getProductType());
			List<EbkExtraProdConfig> extProdConfigList=ebkExtraProdConfigDAO.findListByTerm(term);
			Map<Long, EbkExtraProdConfig> prodBranchMap=new HashMap<Long, EbkExtraProdConfig>();
			for (EbkExtraProdConfig ebkExtraProdConfig : extProdConfigList) {
				prodBranchMap.put(ebkExtraProdConfig.getProdBranchId(), ebkExtraProdConfig);
			}
			//先删除
			EbkProdRelation rterm=new EbkProdRelation();
			rterm.setEbkProductId(ebkProdProduct.getEbkProdProductId());
			List<EbkProdRelation> ebkProdRelationList=ebkProdRelationService.findListByTerm(rterm);
			for (EbkProdRelation ebkProdRelation : ebkProdRelationList) {
				ebkProdRelationService.deleteEbkProdRelationDOByPrimaryKey(ebkProdRelation.getRelationId());
			}
			List<ProdProductRelation> productList = prodProductRelationService.getRelatProduct(ebkProdProduct.getProdProductId());
			for (ProdProductRelation prodProductRelation : productList) {
				if(!prodBranchMap.containsKey(prodProductRelation.getProdBranchId())){//非默认关联
					EbkProdRelation ebkProdRelation=new EbkProdRelation();
					ebkProdRelation.setEbkProductId(ebkProdProduct.getEbkProdProductId());
					ebkProdRelation.setRelateProdBranchId(prodProductRelation.getProdBranchId());
					ProdProductBranch prodProductBranch=prodProductBranchService.selectProdProductBranchByPK(prodProductRelation.getProdBranchId());
					if(null!=prodProductBranch){
						ebkProdRelation.setRelateProdBranchName(prodProductBranch.getBranchName());
					}
					ebkProdRelation.setRelateProductId(prodProductRelation.getRelatProductId());
					ProdProduct prod=prodProductService.getProdProduct(prodProductRelation.getRelatProductId());
					if(null!=prod){
						ebkProdRelation.setRelateProductName(prod.getProductName());
						ebkProdRelation.setRelateProductType(prod.getSubProductType());
						if(!SUB_PRODUCT_TYPE.VISA.name().equals(prod.getSubProductType())){//只要签证
							continue;
						}
					}
					ebkProdRelation.setSaleNumType(prodProductRelation.getSaleNumType());
					ebkProdRelationService.insertEbkProdRelationDO(ebkProdRelation);
				}
			}
		}
	}

	/**
	 * 保存产品其他信息
	 * @param prodProduct
	 * @param ebkProdProduct
	 */
	private void saveProdModelProperty(ProdProduct prodProduct,
			EbkProdProduct ebkProdProduct) {
		ebkProdModelPropertyDAO.deleteEbkProdModelPropertyByProductID(ebkProdProduct.getEbkProdProductId());
		List<ProdProductModelProperty> prodProductModelPropertyList=prodProductModelPropertyService.getProdProductModelPropertyByProductId(String.valueOf(prodProduct.getProductId()));
		for (ProdProductModelProperty prodProductModelProperty : prodProductModelPropertyList) {
			EbkProdModelProperty ebkProdModelPropertyDO=new EbkProdModelProperty();
			ebkProdModelPropertyDO.setModelPropertyId(Integer.valueOf(prodProductModelProperty.getModelPropertyId()+""));
			ebkProdModelPropertyDO.setProductId(ebkProdProduct.getEbkProdProductId());
			Map<String, Object> params=new HashMap<String, Object>();
			params.put("id",prodProductModelProperty.getModelPropertyId());
			List<ProductModelProperty> productModelPropertyList=productModelPropertyService.select(params);
			if(null!=productModelPropertyList&&productModelPropertyList.size()>0){
				ebkProdModelPropertyDO.setEbkPropertyType(String.valueOf(productModelPropertyList.get(0).getSecondModelId()));
			}
			ebkProdModelPropertyDAO.insertEbkProdModelPropertyDO(ebkProdModelPropertyDO);
		}
	}

	/**
	 * 保存发车信息
	 * @param prodProduct
	 * @param ebkProdProduct
	 */
	private void saveProdAssemblyPoint(ProdProduct prodProduct,
			EbkProdProduct ebkProdProduct) {
		EbkProdContent ebkProdContentTerm=new EbkProdContent();
		ebkProdContentTerm.setProductId(ebkProdProduct.getEbkProdProductId());
		ebkProdContentTerm.setContentType(VIEW_CONTENT_TYPE.TRAFFICEBKINFO.name());
		List<EbkProdContent> contents = ebkProdContentDAO.findListByTerm(ebkProdContentTerm);
		for (EbkProdContent ebkProdContent : contents) {
			ebkProdContentDAO.deleteEbkProdContentDOByPrimaryKey(ebkProdContent.getContentId());
		}
		List<ProdAssemblyPoint> prodAssemblyPointList=prodProductService.queryAssembly(prodProduct.getProductId());
		for (ProdAssemblyPoint prodAssemblyPoint : prodAssemblyPointList) {
			EbkProdContent ebkppt=new EbkProdContent();
			ebkppt.setContentType(VIEW_CONTENT_TYPE.TRAFFICEBKINFO.name());
			ebkppt.setProductId(ebkProdProduct.getEbkProdProductId());
			ebkppt.setContent(prodAssemblyPoint.getAssemblyPoint());
			ebkProdContentDAO.insertEbkProdContentDO(ebkppt);
		}
	}

	/**
	 * 保存产品行程安排
	 * @param prodProduct
	 * @param ebkProdProduct
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 *  super -->ebk
	 */
	private void saveJourney(ProdProduct prodProduct,
			EbkProdProduct ebkProdProduct) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		EbkProdJourney ebkProdJourneyTerm=new EbkProdJourney();
		
		if ("Y".equals(ebkProdProduct.getIsMultiJourney())) {
			// 多行程
			// 删除原先数据
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("ebkProdProductId", ebkProdProduct.getEbkProdProductId());
			List<EbkMultiJourney> ebkMultiJourneyList = ebkMultiJourneyDAO.queryMultiJourneyByParams(params);
			for (EbkMultiJourney ebkMultiJourney : ebkMultiJourneyList) {
				ebkProdJourneyTerm.setMultiJourneyId(ebkMultiJourney.getMultiJourneyId());
				ebkProdJourneyTerm.setProductId(ebkProdProduct.getEbkProdProductId());
				List<EbkProdJourney> ebkProdJourneyList=ebkProdJourneyDAO.findListByTerm(ebkProdJourneyTerm);
				for (EbkProdJourney ebkProdJourney : ebkProdJourneyList) {
					ebkProdJourneyDAO.deleteEbkProdJourneyDOByPrimaryKey(ebkProdJourney.getJourneyId());
					List<ComPicture> ebkProdJourneyPicList=comPictureService.getPictureByObjectIdAndType(ebkProdJourney.getJourneyId(), EBK_PROD_PICTURE_TYPE.EBK_PROD_JOURNEY.name());
					for (ComPicture comPicture : ebkProdJourneyPicList) {
						comPictureService.deletePicture(comPicture.getPictureId());
					}
				}
				ebkMultiJourneyDAO.deleteByPrimaryKey(ebkMultiJourney.getMultiJourneyId());
			}
			
			// 建数据
			Map<String, Object> multiJourneyParams = new HashMap<String, Object>();
			multiJourneyParams.put("productId", prodProduct.getProductId());
			//多行程
			List<ViewMultiJourney> viewMultiJourneyList = viewMultiJourneyService.queryMultiJourneyByParams(multiJourneyParams);
			for (ViewMultiJourney viewMultiJourney : viewMultiJourneyList) {
				
				EbkMultiJourney tmpEbkMultiJourney = new EbkMultiJourney();
				tmpEbkMultiJourney.setCreateTime(new Date());
				tmpEbkMultiJourney.setContent(viewMultiJourney.getContent());
				tmpEbkMultiJourney.setDays(viewMultiJourney.getDays());
				tmpEbkMultiJourney.setJourneyName(viewMultiJourney.getJourneyName());				
				tmpEbkMultiJourney.setNights(viewMultiJourney.getNights());
				tmpEbkMultiJourney.setProductId(ebkProdProduct.getEbkProdProductId());
				tmpEbkMultiJourney.setValid(viewMultiJourney.getValid());
				tmpEbkMultiJourney.setSpecDate(viewMultiJourney.getSpecDate());
				ebkMultiJourneyDAO.insert(tmpEbkMultiJourney);
				
				//多行程内的费用说明
				List<ViewContent> contentList=viewPageService.getViewContentByMultiJourneyId(viewMultiJourney.getMultiJourneyId());
				for (ViewContent cnt : contentList) {
					if(null==cnt.getMultiJourneyId()){
						continue;
					}
					EbkProdContent ebkProdContent=new EbkProdContent();
					ebkProdContent.setContent(cnt.getContent());
					ebkProdContent.setContentType(cnt.getContentType());
					ebkProdContent.setMultiJourneyId(tmpEbkMultiJourney.getMultiJourneyId());
					ebkProdContent.setProductId(ebkProdProduct.getEbkProdProductId());
					ebkProdContentDAO.insertEbkProdContentDO(ebkProdContent);
				}
				
				//多行程内的行程明细
				List<ViewJourney> viewJourneyList=viewPageJourneyService.getViewJourneyByMultiJourneyId(viewMultiJourney.getMultiJourneyId());
				for (ViewJourney viewJourney : viewJourneyList) {
					EbkProdJourney ebkJy=new EbkProdJourney();
					ebkJy.setContent(viewJourney.getContent());
					ebkJy.setDayNumber(viewJourney.getSeq());
					ebkJy.setDinner(viewJourney.getDinner());
					ebkJy.setHotel(viewJourney.getHotel());
					ebkJy.setProductId(ebkProdProduct.getEbkProdProductId());
					ebkJy.setTitle(viewJourney.getTitle());
					ebkJy.setTraffic(viewJourney.getTraffic());
					ebkJy.setMultiJourneyId(tmpEbkMultiJourney.getMultiJourneyId());
					ebkProdJourneyDAO.insertEbkProdJourneyDO(ebkJy);
					List<ComPicture> journeyPicList=comPictureService.getPictureByObjectIdAndType(viewJourney.getJourneyId(), PICTURE_OBJECT_TYPE.VIEW_JOURNEY.name());
					for (ComPicture comPicture : journeyPicList) {
						ComPicture picture = new ComPicture();
						PropertyUtils.copyProperties(picture, comPicture);
						picture.setPictureId(null);
						picture.setIsNew(true);
						picture.setPictureObjectId(ebkJy.getJourneyId());
						picture.setPictureObjectType(EBK_PROD_PICTURE_TYPE.EBK_PROD_MULTIJOURNEY.name());
						comPictureService.savePicture(picture);
					}
				}
				
				
				
			}
			
		}else{
			// 单行程
			ebkProdJourneyTerm.setProductId(ebkProdProduct.getEbkProdProductId());
			List<EbkProdJourney> ebkProdJourneyList=ebkProdJourneyDAO.findListByTerm(ebkProdJourneyTerm);
			
			for (EbkProdJourney ebkProdJourney : ebkProdJourneyList) {
				ebkProdJourneyDAO.deleteEbkProdJourneyDOByPrimaryKey(ebkProdJourney.getJourneyId());
				List<ComPicture> ebkProdJourneyPicList=comPictureService.getPictureByObjectIdAndType(ebkProdJourney.getJourneyId(), EBK_PROD_PICTURE_TYPE.EBK_PROD_JOURNEY.name());
				for (ComPicture comPicture : ebkProdJourneyPicList) {
					comPictureService.deletePicture(comPicture.getPictureId());
				}
			}
			List<ViewJourney> viewJourneyList=viewPageJourneyService.getViewJourneysByProductId(prodProduct.getProductId());
			for (ViewJourney viewJourney : viewJourneyList) {
				EbkProdJourney ebkJy=new EbkProdJourney();
				ebkJy.setContent(viewJourney.getContent());
				ebkJy.setDayNumber(viewJourney.getSeq());
				ebkJy.setDinner(viewJourney.getDinner());
				ebkJy.setHotel(viewJourney.getHotel());
				ebkJy.setProductId(ebkProdProduct.getEbkProdProductId());
				ebkJy.setTitle(viewJourney.getTitle());
				ebkJy.setTraffic(viewJourney.getTraffic());
				ebkProdJourneyDAO.insertEbkProdJourneyDO(ebkJy);
				List<ComPicture> journeyPicList=comPictureService.getPictureByObjectIdAndType(viewJourney.getJourneyId(), PICTURE_OBJECT_TYPE.VIEW_JOURNEY.name());
				for (ComPicture comPicture : journeyPicList) {
					ComPicture picture = new ComPicture();
					PropertyUtils.copyProperties(picture, comPicture);
					picture.setPictureId(null);
					picture.setIsNew(true);
					picture.setPictureObjectId(ebkJy.getJourneyId());
					picture.setPictureObjectType(EBK_PROD_PICTURE_TYPE.EBK_PROD_JOURNEY.name());
					comPictureService.savePicture(picture);
				}
			}
		}
		
		
	}

	/**
	 * 保存产品描述信息
	 * @param prodProduct
	 * @param ebkProdProduct
	 * super -->ebk
	 */
	private void saveProdContent(ProdProduct prodProduct,
			EbkProdProduct ebkProdProduct) {
		EbkProdContent ebkProdContentTerm=new EbkProdContent();
		ebkProdContentTerm.setProductId(ebkProdProduct.getEbkProdProductId());
		List<EbkProdContent> ebkProdContentList=ebkProdContentDAO.findListByTerm(ebkProdContentTerm);
		for (EbkProdContent ebkProdContent : ebkProdContentList) {
			if(VIEW_CONTENT_TYPE.TRAFFICEBKINFO.name().equals(ebkProdContent.getContentType())){
				continue;
			}
			ebkProdContentDAO.deleteEbkProdContentDOByPrimaryKey(ebkProdContent.getContentId());
		}
		ViewPage viewPage=viewPageService.getViewPageByProductId(prodProduct.getProductId());
		if(null!=viewPage&&viewPage.getContentList().size()>0){
			List<ViewContent> viewList=viewPage.getContentList();
			for (ViewContent cnt : viewList) {
				if(VIEW_CONTENT_TYPE.TRAFFICEBKINFO.name().equals(cnt.getContentType())||null!=cnt.getMultiJourneyId()){
					LOG.info("ViewContent ContentType TRAFFICEBKINFO ");
					continue;
				}
				EbkProdContent ebkProdContent=new EbkProdContent();
				ebkProdContent.setContent(cnt.getContent());
				ebkProdContent.setContentType(cnt.getContentType());
				//ebkProdContent.setMultiJourneyId(cnt.getMultiJourneyId());
				ebkProdContent.setProductId(ebkProdProduct.getEbkProdProductId());
				ebkProdContentDAO.insertEbkProdContentDO(ebkProdContent);
			}
		} else {
			LOG.info("saveProdContent ----- ProdProduct id = " + prodProduct.getProductId() + ", name = " + prodProduct.getProductName() + ", viewPage is null ");
		}
	}

	/**
	 * 保存图片信息
	 * @param prodProduct
	 * @param ebkProdProduct
	 * @throws IllegalAccessException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 */
	private void saveProdPicture(ProdProduct prodProduct,
			EbkProdProduct ebkProdProduct) throws IllegalAccessException,
			InvocationTargetException, NoSuchMethodException {
		//先删除
		List<ComPicture> ebkProdBigPicList=comPictureService.getPictureByObjectIdAndType(ebkProdProduct.getEbkProdProductId(), Constant.EBK_PROD_PICTURE_TYPE.EBK_PROD_PRODUCT_BIG.name());
		for (ComPicture comPicture : ebkProdBigPicList) {
			comPictureService.deletePicture(comPicture.getPictureId());
		}
		List<ComPicture> ebkProdSmallPicList=comPictureService.getPictureByObjectIdAndType(ebkProdProduct.getEbkProdProductId(), Constant.EBK_PROD_PICTURE_TYPE.EBK_PROD_PRODUCT_SMALL.name());
		for (ComPicture comPicture : ebkProdSmallPicList) {
			comPictureService.deletePicture(comPicture.getPictureId());
		}
		List<ComPicture> superProdImageList=comPictureService.getPictureByObjectIdAndType(prodProduct.getProductId(), PICTURE_OBJECT_TYPE.VIEW_PAGE.name());
		//处理大图
		for (ComPicture comPicture : superProdImageList) {
			ComPicture picture = new ComPicture();
			picture.setSeq(comPicture.getSeq());
			picture.setPictureName(comPicture.getPictureName());
			picture.setPictureObjectId(ebkProdProduct.getEbkProdProductId());
			picture.setPictureObjectType(Constant.EBK_PROD_PICTURE_TYPE.EBK_PROD_PRODUCT_BIG.name());
			picture.setPictureId(null);
			picture.setPictureUrl(comPicture.getPictureUrl());
			picture.setIsNew(true);
			comPictureService.savePicture(picture);
		}
		//处理小图
		if(StringUtils.isNotBlank(prodProduct.getSmallImage())){
			ComPicture picture = new ComPicture();
			picture.setPictureId(null);
			picture.setPictureName("小图");
			picture.setPictureUrl(prodProduct.getSmallImage());
			picture.setSeq(0);
			picture.setIsNew(true);
			picture.setPictureObjectId(ebkProdProduct.getEbkProdProductId());
			picture.setPictureObjectType(Constant.EBK_PROD_PICTURE_TYPE.EBK_PROD_PRODUCT_SMALL.name());
			comPictureService.savePicture(picture);
		}
		
	}

	/**
	 * 保存类别信息
	 * @param metaProduct
	 * @param ebkProdProduct
	 */
	private void saveBranch(MetaProduct metaProduct,
			EbkProdProduct ebkProdProduct) {
		
		//查询super的类别
		List<MetaProductBranch> metaBranchList=metaProductBranchService.selectBranchListByProductId(metaProduct.getMetaProductId());
		
		List<EbkProdBranch> allBranchList=new ArrayList<EbkProdBranch>();
		
		List<EbkProdBranch> leftBranchList=new ArrayList<EbkProdBranch>();
		
		Map<Long, EbkProdBranch> leftBranchMapping=new HashMap<Long, EbkProdBranch>();
		
		//查询ebk的类别
		EbkProdBranch ebkProdBranchTerm =new EbkProdBranch();
		ebkProdBranchTerm.setProdProductId(ebkProdProduct.getEbkProdProductId());
		List<EbkProdBranch> ebkProdBranchs = ebkProdBranchService.query(ebkProdBranchTerm);
		
		//帅选删除类别
		for(EbkProdBranch epb:ebkProdBranchs){
			allBranchList.add(epb);
			for(MetaProductBranch mpb:metaBranchList){
				if(null!=epb.getMetaProdBranchId()&&epb.getMetaProdBranchId().equals(mpb.getMetaBranchId())){
					leftBranchList.add(epb);
					leftBranchMapping.put(mpb.getMetaBranchId(), epb);
					break;
				}
			}
		}
		
		//排除剩下的就是删除的
		allBranchList.removeAll(leftBranchList);
		
		//将删除的删除
		for (EbkProdBranch item : allBranchList) {
			ebkProdBranchService.delete(item.getProdBranchId());
		}
		
		MetaProductBranch virtualMetaProductBranch=null;
		for (MetaProductBranch metaProductBranch : metaBranchList) {
			if(ROUTE_BRANCH.VIRTUAL.name().equals(metaProductBranch.getBranchType())){//排除虚拟类别
				virtualMetaProductBranch=metaProductBranch;
				continue;
			}
			List<ProdProductBranch> prodBranchList=prodProductBranchService.getProductBranchByMetaProdBranchId(metaProductBranch.getMetaBranchId());
			if (prodBranchList.size()==0) {
				continue;
			}
			ProdProductBranch prodProductBranch=prodBranchList.get(0);
			
			EbkProdBranch ebkProdBranch=leftBranchMapping.get(metaProductBranch.getMetaBranchId());
			if(null==ebkProdBranch){
				ebkProdBranch=new EbkProdBranch();
			}
			ebkProdBranch.setAdultQuantity(metaProductBranch.getAdultQuantity());
			ebkProdBranch.setBranchName(metaProductBranch.getBranchName());
			ebkProdBranch.setBranchType(metaProductBranch.getBranchType());
			ebkProdBranch.setChildQuantity(metaProductBranch.getChildQuantity());
			ebkProdBranch.setCreateTime(new Date());
			if(StringUtils.isBlank(prodProductBranch.getDefaultBranch())){
				ebkProdBranch.setDefaultBranch("false");
			}else{
				ebkProdBranch.setDefaultBranch(prodProductBranch.getDefaultBranch());
			}
			ebkProdBranch.setMetaProdBranchId(metaProductBranch.getMetaBranchId());
			ebkProdBranch.setProdProductBranchId(prodProductBranch.getProdBranchId());
			ebkProdBranch.setProdProductId(ebkProdProduct.getEbkProdProductId());
			if(null==ebkProdBranch.getProdBranchId()){
				ebkProdBranchService.insert(ebkProdBranch);
			}else{
				ebkProdBranchService.update(ebkProdBranch);
			}
		}
		
		//处理虚拟类别
		if(null!=virtualMetaProductBranch){
			StringBuilder virtualBranchIdsBuilder=new StringBuilder();
			//找到虚拟类别打包销售
			List<ProdProductBranch> prodBranchList=prodProductBranchService.getProductBranchByMetaProdBranchId(virtualMetaProductBranch.getMetaBranchId());
			for (ProdProductBranch prodProductBranch : prodBranchList) {
				//查询super中销售类别对应ebb的类别
				EbkProdBranch term=new EbkProdBranch();
				term.setProdProductBranchId(prodProductBranch.getProdBranchId());
				List<EbkProdBranch> ebkProdBranchList=ebkProdBranchService.query(term);
				if(null!=ebkProdBranchList&&ebkProdBranchList.size()>0){
					virtualBranchIdsBuilder.append(ebkProdBranchList.get(0).getProdBranchId()).append(",");
				}
			}
			
			EbkProdBranch ebkProdBranch=leftBranchMapping.get(virtualMetaProductBranch.getMetaBranchId());
			if(null==ebkProdBranch){
				ebkProdBranch=new EbkProdBranch();
			}
			ebkProdBranch.setVirtualBranchIds(virtualBranchIdsBuilder.toString());
			ebkProdBranch.setAdultQuantity(virtualMetaProductBranch.getAdultQuantity());
			ebkProdBranch.setBranchName(virtualMetaProductBranch.getBranchName());
			ebkProdBranch.setBranchType(virtualMetaProductBranch.getBranchType());
			ebkProdBranch.setChildQuantity(virtualMetaProductBranch.getChildQuantity());
			ebkProdBranch.setCreateTime(new Date());
			ebkProdBranch.setDefaultBranch("false");
			ebkProdBranch.setMetaProdBranchId(virtualMetaProductBranch.getMetaBranchId());
			ebkProdBranch.setProdProductBranchId(0L);
			ebkProdBranch.setProdProductId(ebkProdProduct.getEbkProdProductId());
			if(null==ebkProdBranch.getProdBranchId()){
				ebkProdBranchService.insert(ebkProdBranch);
			}else{
				ebkProdBranchService.update(ebkProdBranch);
			}
		}
		
	}

	/**
	 * 保存景点信息
	 * @param prodProduct
	 * @param ebkProdProduct
	 */
	private void savePlace(ProdProduct prodProduct,
			EbkProdProduct ebkProdProduct) {
		List<ProdProductPlace> prodProductPlaceList=prodProductPlaceService.selectByProductId(prodProduct.getProductId());
		ebkProdPlaceService.deleteListByProductId(ebkProdProduct.getEbkProdProductId());
		for (ProdProductPlace prodProductPlace : prodProductPlaceList) {
			if("true".equalsIgnoreCase(prodProductPlace.getFrom())
					||"true".equalsIgnoreCase(prodProductPlace.getTo())){//不处理出发地和目的地
				continue;
			}
			EbkProdPlace term=new EbkProdPlace();
			term.setPlaceId(prodProductPlace.getPlaceId());
			term.setProductId(ebkProdProduct.getEbkProdProductId());
			Integer counts=ebkProdPlaceDAO.countEbkProdPlaceDOByExample(term);
			if(null!=counts&&counts>0){
				continue;
			}
			EbkProdPlace ebkProdPlace=new EbkProdPlace();
			ebkProdPlace.setPlaceId(prodProductPlace.getPlaceId());
			ebkProdPlace.setProductId(ebkProdProduct.getEbkProdProductId());
			ebkProdPlaceService.insert(ebkProdPlace);
		}
	}

	/**
	 * 保存履行对象，凭证对象，结算对象
	 * @param metaProduct
	 * @param ebkProdProduct
	 */
	private void saveTarget(MetaProduct metaProduct,
			EbkProdProduct ebkProdProduct) {
		List<EbkProdTarget> ebkProdTargets =new ArrayList<EbkProdTarget>();
		//凭证对象
		MetaBCertificate bcertificate=bCertificateTargetService.findMetaBCertificateByByMetaProductId(metaProduct.getMetaProductId());
		if(null!=bcertificate){
			EbkProdTarget ebkProdTarget=new EbkProdTarget();
			ebkProdTarget.setTargetType(Constant.CONTACT_TYPE.SUP_B_CERTIFICATE_TARGET.name());
			ebkProdTarget.setProductId(ebkProdProduct.getEbkProdProductId());
			ebkProdTarget.setTargetId(bcertificate.getTargetId());
			ebkProdTargets.add(ebkProdTarget);
		}
		//结算对象
		MetaSettlement metaSettlement=settlementTargetService.findMetaSettlementByMetaProductId(metaProduct.getMetaProductId());
		if(null!=metaSettlement){
			EbkProdTarget ebkProdTarget=new EbkProdTarget();
			ebkProdTarget.setTargetType(Constant.CONTACT_TYPE.SUP_SETTLEMENT_TARGET.name());
			ebkProdTarget.setProductId(ebkProdProduct.getEbkProdProductId());
			ebkProdTarget.setTargetId(metaSettlement.getTargetId());
			ebkProdTargets.add(ebkProdTarget);
		}
		
		//履行对象
		List<SupPerformTarget> supPerformTargetList=performTargetService.findSuperSupPerformTargetByMetaProductId(metaProduct.getMetaProductId());
		if(null!=supPerformTargetList&&supPerformTargetList.size()>0){
			SupPerformTarget supPerformTarget=supPerformTargetList.get(0);
			EbkProdTarget ebkProdTarget=new EbkProdTarget();
			ebkProdTarget.setTargetType(Constant.CONTACT_TYPE.SUP_PERFORM_TARGET.name());
			ebkProdTarget.setProductId(ebkProdProduct.getEbkProdProductId());
			ebkProdTarget.setTargetId(supPerformTarget.getTargetId());
			ebkProdTargets.add(ebkProdTarget);
		}
		ebkProdTargetService.deleteEbkProdTargetByProductID(ebkProdProduct.getEbkProdProductId());
		for(EbkProdTarget ebkProdTarget : ebkProdTargets){
			ebkProdTargetService.saveEbkProdTarget(ebkProdTarget);
		}
	}

	/**
	 * 保存产品基本信息
	 * @param metaProduct
	 * @param prodProduct
	 * @return
	 */
	private EbkProdProduct saveEbkProd(MetaProduct metaProduct,
			ProdProduct prodProduct) {
		Map<String,Object> term=new HashMap<String,Object>();
		term.put("metaProductId", metaProduct.getMetaProductId());  
		term.put("prodProductId", prodProduct.getProductId());
		List<EbkProdProduct> ebkProdProductList=ebkProdProductDAO.findListByExample(term);
		EbkProdProduct ebkProdProduct=null;
		if(null!=ebkProdProductList&&ebkProdProductList.size()>0){
			ebkProdProduct=ebkProdProductList.get(0);
		}
		if(null==ebkProdProduct){
			ebkProdProduct=new EbkProdProduct();
			ebkProdProduct.setCreateDate(new Date());
			ebkProdProduct.setCreateUserId(0L);///TODO
			ebkProdProduct.setMetaProductId(metaProduct.getMetaProductId());
			ebkProdProduct.setProdProductId(prodProduct.getProductId());
			ebkProdProduct.setStatus(EBK_PRODUCT_AUDIT_STATUS.THROUGH_AUDIT.name());//审核通过
		}
		ebkProdProduct.setMetaProductId(metaProduct.getMetaProductId());
		ebkProdProduct.setProdProductId(prodProduct.getProductId());
		ebkProdProduct.setUpdateDate(new Date());
		ebkProdProduct.setProdName(prodProduct.getProductName());
		ebkProdProduct.setMetaName(metaProduct.getProductName());
		ebkProdProduct.setManagerId(metaProduct.getManagerId());
		ebkProdProduct.setOrgId(prodProduct.getFilialeName());
		ebkProdProduct.setSupplierId(metaProduct.getSupplierId());
		ebkProdProduct.setSubProductType(prodProduct.getSubProductType());
		ebkProdProduct.setProductType(EBK_PRODUCT_VIEW_TYPE.getProductType(prodProduct.getSubProductType()).name());
		ebkProdProduct.setSumitDate(new Date());
		
		
		//行程天数
		ProdRoute prodRoute=(ProdRoute)prodProduct;
		ebkProdProduct.setTourDays(prodRoute.getDays()+"");
		//是否是多行程
		ebkProdProduct.setIsMultiJourney(prodRoute.getIsMultiJourney());
		//出境产品特殊属性
		if(EBK_PRODUCT_VIEW_TYPE.ABROAD_PROXY.name().equals(ebkProdProduct.getProductType())){
//			ProdRoute prodRoute=(ProdRoute)prodProduct;
			if(null!=prodRoute.getInitialNum()){
				ebkProdProduct.setInitialNum(prodRoute.getInitialNum().intValue());
			}
			ebkProdProduct.setRegionName(prodRoute.getRegionName());
			ebkProdProduct.setVisaType(prodRoute.getVisaType());
			ebkProdProduct.setCountry(prodRoute.getCountry());
			ebkProdProduct.setCity(prodRoute.getCity());
			//电子合同
			ProdEContract eContract=prodProductService.getProdEContractByProductId(prodRoute.getProductId());
			if(null!=eContract){
				ebkProdProduct.setEcontractTemplate(eContract.getEContractTemplate());
			}
		}
		
		
		//出发地目的地
		List<ProdProductPlace> prodProductPlaceList=prodProductPlaceService.selectByProductId(prodProduct.getProductId());
		boolean hasFrom=false;
		boolean hasTo=false;
		for (ProdProductPlace prodProductPlace : prodProductPlaceList) {
			if("true".equalsIgnoreCase(prodProductPlace.getFrom())){
				hasFrom=true;
				ebkProdProduct.setFromPlaceId(prodProductPlace.getPlaceId());
			}
			if("true".equalsIgnoreCase(prodProductPlace.getTo())){
				hasTo=true;
				ebkProdProduct.setToPlaceId(prodProductPlace.getPlaceId());
			}
		}
		if(!hasFrom){
			ebkProdProduct.setFromPlaceId(0L);
		}
		if(!hasTo){
			ebkProdProduct.setToPlaceId(0L);
		}
		
		//一句话推荐
		ebkProdProduct.setRecommend(prodProduct.getRecommendInfoSecond());
		
		if(null==ebkProdProduct.getEbkProdProductId()){
			ebkProdProductDAO.insertEbkProdProductDO(ebkProdProduct);
		}else{
			ebkProdProductDAO.updateEbkProdProductDO(ebkProdProduct);
		}
		
		
		return ebkProdProduct;
	}
	
	
	@Override
	public void importProduct(Long ebkProductId) throws Exception {
		EbkProdProduct ebkProdProduct=ebkProdProductDAO.findEbkProdProductDOByPrimaryKey(ebkProductId);
		if(null==ebkProdProduct){
			LOG.error("Unknown ebk product id:"+ebkProductId);
			throw new RuntimeException("Unknown ebk product id:"+ebkProductId);
		}
		if(null==ebkProdProduct.getMetaProductId()||null==ebkProdProduct.getProdProductId()){
			LOG.error("The product can't found in super system,id:"+ebkProductId);
			throw new RuntimeException("The product can't found in super system,id:"+ebkProductId);
		}
		MetaProduct metaProduct=metaProductService.getMetaProduct(ebkProdProduct.getMetaProductId());
		if(null==metaProduct){
			LOG.error("The meta product can't found in super system,id:"+ebkProdProduct.getMetaProductId());
			throw new RuntimeException("The meta product can't found in super system,id:"+ebkProdProduct.getMetaProductId());
		}
		ProdProduct prodProduct=prodProductService.getProdProduct(ebkProdProduct.getProdProductId());
		if(null==prodProduct){
			LOG.error("The prod product can't found in super system,id:"+ebkProdProduct.getProdProductId());
			throw new RuntimeException("The prod product can't found in super system,id:"+ebkProdProduct.getProdProductId());
		}
		importProduct(metaProduct,prodProduct);
	}
	
	
	
	
	
	
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}

	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}

	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}

	public void setEbkProdProductDAO(EbkProdProductDAO ebkProdProductDAO) {
		this.ebkProdProductDAO = ebkProdProductDAO;
	}

	public void setProdProductPlaceService(
			ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}

	public void setbCertificateTargetService(
			BCertificateTargetService bCertificateTargetService) {
		this.bCertificateTargetService = bCertificateTargetService;
	}

	public void setSettlementTargetService(
			SettlementTargetService settlementTargetService) {
		this.settlementTargetService = settlementTargetService;
	}

	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}

	public void setEbkProdTargetService(EbkProdTargetService ebkProdTargetService) {
		this.ebkProdTargetService = ebkProdTargetService;
	}

	public void setEbkProdPlaceService(EbkProdPlaceService ebkProdPlaceService) {
		this.ebkProdPlaceService = ebkProdPlaceService;
	}

	public void setEbkProdBranchService(EbkProdBranchService ebkProdBranchService) {
		this.ebkProdBranchService = ebkProdBranchService;
	}

	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}

	public void setEbkProdContentDAO(EbkProdContentDAO ebkProdContentDAO) {
		this.ebkProdContentDAO = ebkProdContentDAO;
	}

	public void setViewPageService(ViewPageService viewPageService) {
		this.viewPageService = viewPageService;
	}

	public void setEbkProdJourneyDAO(EbkProdJourneyDAO ebkProdJourneyDAO) {
		this.ebkProdJourneyDAO = ebkProdJourneyDAO;
	}

	public void setViewPageJourneyService(
			ViewPageJourneyService viewPageJourneyService) {
		this.viewPageJourneyService = viewPageJourneyService;
	}

	public void setProdProductModelPropertyService(
			ProdProductModelPropertyService prodProductModelPropertyService) {
		this.prodProductModelPropertyService = prodProductModelPropertyService;
	}

	public void setEbkProdModelPropertyDAO(
			EbkProdModelPropertyDAO ebkProdModelPropertyDAO) {
		this.ebkProdModelPropertyDAO = ebkProdModelPropertyDAO;
	}

	public void setProductModelPropertyService(
			ProductModelPropertyService productModelPropertyService) {
		this.productModelPropertyService = productModelPropertyService;
	}


	public void setEbkProdPlaceDAO(EbkProdPlaceDAO ebkProdPlaceDAO) {
		this.ebkProdPlaceDAO = ebkProdPlaceDAO;
	}

	public void setEbkProdProductService(EbkProdProductService ebkProdProductService) {
		this.ebkProdProductService = ebkProdProductService;
	}

	public void setEbkProdSnapshotService(
			EbkProdSnapshotService ebkProdSnapshotService) {
		this.ebkProdSnapshotService = ebkProdSnapshotService;
	}

	public void setEbkExtraProdConfigDAO(EbkExtraProdConfigDAO ebkExtraProdConfigDAO) {
		this.ebkExtraProdConfigDAO = ebkExtraProdConfigDAO;
	}

	public void setProdProductRelationService(
			ProdProductRelationService prodProductRelationService) {
		this.prodProductRelationService = prodProductRelationService;
	}

	public void setEbkProdRelationService(
			EbkProdRelationService ebkProdRelationService) {
		this.ebkProdRelationService = ebkProdRelationService;
	}


	public EbkMultiJourneyDAO getEbkMultiJourneyDAO() {
		return ebkMultiJourneyDAO;
	}


	public void setEbkMultiJourneyDAO(EbkMultiJourneyDAO ebkMultiJourneyDAO) {
		this.ebkMultiJourneyDAO = ebkMultiJourneyDAO;
	}


	public ViewMultiJourneyService getViewMultiJourneyService() {
		return viewMultiJourneyService;
	}


	public void setViewMultiJourneyService(
			ViewMultiJourneyService viewMultiJourneyService) {
		this.viewMultiJourneyService = viewMultiJourneyService;
	}
	
}
