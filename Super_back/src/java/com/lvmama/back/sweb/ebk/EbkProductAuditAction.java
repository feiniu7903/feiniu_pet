package com.lvmama.back.sweb.ebk;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.BeanUtils;

import com.lvmama.comm.BackBaseAction;
import com.lvmama.comm.bee.po.ebooking.EbkMultiJourney;
import com.lvmama.comm.bee.po.ebooking.EbkProdBranch;
import com.lvmama.comm.bee.po.ebooking.EbkProdContent;
import com.lvmama.comm.bee.po.ebooking.EbkProdJourney;
import com.lvmama.comm.bee.po.ebooking.EbkProdPlace;
import com.lvmama.comm.bee.po.ebooking.EbkProdProduct;
import com.lvmama.comm.bee.po.ebooking.EbkProdRejectInfo;
import com.lvmama.comm.bee.po.ebooking.EbkProdRelation;
import com.lvmama.comm.bee.po.ebooking.EbkProdTarget;
import com.lvmama.comm.bee.po.ebooking.EbkProdTimePrice;
import com.lvmama.comm.bee.po.eplace.EbkUser;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.po.prod.ProductModelProperty;
import com.lvmama.comm.bee.service.ebooking.EbkMultiJourneyService;
import com.lvmama.comm.bee.service.ebooking.EbkProdAuditService;
import com.lvmama.comm.bee.service.ebooking.EbkProdBranchService;
import com.lvmama.comm.bee.service.ebooking.EbkProdContentService;
import com.lvmama.comm.bee.service.ebooking.EbkProdJourneyService;
import com.lvmama.comm.bee.service.ebooking.EbkProdModelPropertyService;
import com.lvmama.comm.bee.service.ebooking.EbkProdPlaceService;
import com.lvmama.comm.bee.service.ebooking.EbkProdProductService;
import com.lvmama.comm.bee.service.ebooking.EbkProdRejectInfoService;
import com.lvmama.comm.bee.service.ebooking.EbkProdRelationService;
import com.lvmama.comm.bee.service.ebooking.EbkProdSnapshotService;
import com.lvmama.comm.bee.service.ebooking.EbkProdTargetService;
import com.lvmama.comm.bee.service.ebooking.EbkProdTimePriceService;
import com.lvmama.comm.bee.service.eplace.EbkUserService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.service.prod.ProductModelPropertyService;
import com.lvmama.comm.bee.vo.EbkProdProductModel;
import com.lvmama.comm.bee.vo.ebooking.EbkProdTimePriceAuditCombo;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.place.Place;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.ComPicture;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.pub.ComPictureService;
import com.lvmama.comm.pet.service.pub.ComPlaceService;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.pet.service.sup.SettlementTargetService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.pet.vo.Page;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.EBK_PRODUCT_AUDIT_STATUS;

/**
 * ebk产品审核
 * 
 * @author taiqichao
 * 
 */
@Results(value = { 
		@Result(name = "prodApprovalAuditList", location = "/WEB-INF/pages/back/ebk/prod/prodApprovalAuditList.jsp"),
		@Result(name = "prodAuditedList", location = "/WEB-INF/pages/back/ebk/prod/prodAuditedList.jsp"),
		@Result(name = "prodAuditList", location = "/WEB-INF/pages/back/ebk/prod/prodAuditList.jsp"),
		@Result(name = "prodAuditApplyDetail", location = "/WEB-INF/pages/back/ebk/prod/prodAuditApplyDetail.jsp"),
		@Result(name = "prodAuditApplyDetailBase", location = "/WEB-INF/pages/back/ebk/prod/prodAuditApplyDetailBase.jsp"),
		@Result(name = "prodAuditApplyDetailRecommend", location = "/WEB-INF/pages/back/ebk/prod/prodAuditApplyDetailRecommend.jsp"),
		@Result(name = "prodAuditApplyResult", location = "/WEB-INF/pages/back/ebk/prod/prodAuditApplyResult.jsp"),
		@Result(name = "prodAuditApplyDetailTrip", location = "/WEB-INF/pages/back/ebk/prod/prodAuditApplyDetailTrip.jsp"),
		@Result(name = "prodAuditApplyDetailCost", location = "/WEB-INF/pages/back/ebk/prod/prodAuditApplyDetailCost.jsp"),
		@Result(name = "prodAuditApplyDetailMultiTrip", location = "/WEB-INF/pages/back/ebk/prod/prodAuditApplyMultiTrip.jsp"),
		@Result(name = "prodAuditApplyDetailOther", location = "/WEB-INF/pages/back/ebk/prod/prodAuditApplyDetailOther.jsp"),
		@Result(name = "prodAuditApplyDetailPicture", location = "/WEB-INF/pages/back/ebk/prod/prodAuditApplyDetailPicture.jsp"),
		@Result(name = "prodAuditApplyDetailTraffic", location = "/WEB-INF/pages/back/ebk/prod/prodAuditApplyDetailTraffic.jsp"),
		@Result(name = "prodAuditApplyDetailAuditResult", location = "/WEB-INF/pages/back/ebk/prod/prodAuditApplyDetailAuditResult.jsp"),
		@Result(name = "prodAuditApplyDetailTimePrice", location = "/WEB-INF/pages/back/ebk/prod/prodAuditApplyDetailTimePrice.jsp"),
		@Result(name = "prodAuditApplyDetailRelation", location = "/WEB-INF/pages/back/ebk/prod/prodAuditApplyDetailRelation.jsp")
})
@Namespace("/ebooking/prod")
public class EbkProductAuditAction extends BackBaseAction {
	
	private static final long serialVersionUID = -5418748345822658936L;
	
	private EbkProdSnapshotService ebkProdSnapshotService;
	
	private EbkProdProductService ebkProdProductService;

	private ComPlaceService comPlaceService;
	
	private ProductModelPropertyService productModelPropertyService;
	
	private EbkProdPlaceService ebkProdPlaceService;
	
	private EbkProdModelPropertyService ebkProdModelPropertyService;
	
	private EbkProdTargetService ebkProdTargetService;
	
	private PerformTargetService performTargetService;
	
	private BCertificateTargetService bCertificateTargetService;
	
	private SettlementTargetService settlementTargetService;
	
	private PermUserService permUserService;
	
	private EbkProdContentService ebkProdContentService;
	
	private EbkProdJourneyService ebkProdJourneyService;
	
	private EbkMultiJourneyService ebkMultiJourneyService;
	
	private ComPictureService comPictureService;
	
	private EbkProdRejectInfoService ebkProdRejectInfoService;
	
	private EbkProdBranchService ebkProdBranchService;
	
	private EbkProdTimePriceService ebkProdTimePriceService;
	
	private EbkProdRelationService ebkProdRelationService;
	
	private Page<EbkProdProduct> ebkProdProductPage = new Page<EbkProdProduct>();
	
	
	/**
	 *  ebk产品ID
	 */
	private Long ebkProdProductId;
	/**
	 * ebk产品审核状态
	 */
	private String ebkProdProductStatus;
	
	/**
	 * 出发地
	 */
	private String fromPlaceName;
	/**
	 * 目的地
	 */
	private String toPlaceName;
	
	/**
	 * 出发地(旧版本)
	 */
	private String fromPlaceNameOld;
	/**
	 * 目的地(旧版本)
	 */
	private String toPlaceNameOld;
	/**
	 * 行程内包含景点
	 */
	private String tripScenery;
	
	/**
	 * 行程内包含景点(旧版本)
	 */
	private String tripSceneryOld;
	
	private ProdProductService prodProductService;
	
	private SupplierService supplierService;
	
	private EbkUserService ebkUserService;
	
	
	/**
	 * 履行对象
	 */
	private SupPerformTarget supPerformTarget=new SupPerformTarget();
	/**
	 * 履行对象(旧版本)
	 */
	private SupPerformTarget supPerformTargetOld=new SupPerformTarget();
	/**
	 * 凭证对象
	 */
	private SupBCertificateTarget supBCertificateTarget=new SupBCertificateTarget();
	/**
	 * 凭证对象(旧版本)
	 */
	private SupBCertificateTarget supBCertificateTargetOld=new SupBCertificateTarget();
	/**
	 * 结算对象
	 */
	private SupSettlementTarget supSettlementTarget=new SupSettlementTarget();
	/**
	 * 结算对象(旧版本)
	 */
	private SupSettlementTarget supSettlementTargetOld=new SupSettlementTarget();
	
	/**
	 * 驴妈妈产品联系人\联系电话
	 */
	private PermUser permUserManager;
	/**
	 * 驴妈妈产品联系人\联系电话(旧版本)
	 */
	private PermUser permUserManagerOld;
	
	/**
	 * 所属公司(旧版本)
	 */
	private String orgNameOld;
	
	/**
	 * 产品推荐\特色
	 */
	private Map<String,String> ebkProdContentMap=new HashMap<String,String>();
	
	/**
	 * tab页审字是否修改集合
	 */
	private Map<String, String> compareTabsChange=new HashMap<String,String>();
	/**
	 * 基础信息页-审字参数修改集合
	 */
	private Map<String, Object> compareEbkProductBase=new HashMap<String,Object>();
	/**
	 * 产品推荐\特色页-审字参数修改集合
	 */
	private Map<String, Object> compareEbkProductRecommend=new HashMap<String,Object>();
	/**
	 * 费用说明页-审字参数修改集合
	 */
	private Map<String, Object> compareEbkProductCost=new HashMap<String,Object>();
	
	/**
	 * 产品图片-小图
	 */
	private ComPicture comPictureSmall=new ComPicture();
	/**
	 * 产品图片-大图
	 */
	private List<ComPicture> comPictureBigList=new ArrayList<ComPicture>();
	
	/**
	 * 其它条款页-审字参数修改集合
	 */
	private Map<String, Object> compareEbkProductOther=new HashMap<String,Object>();
	
	
	/**
	 * ebk产品对象
	 */
	private EbkProdProduct ebkProdProduct;
	/**
	 * 行程描述
	 */
	private List<EbkProdJourney> ebkProdJourneyList=new ArrayList<EbkProdJourney>();
	/**
	 * 多行程
	 */
	private List<EbkMultiJourney> ebkMultiJourneyList=new ArrayList<EbkMultiJourney>();
	/**
	 * 发车信息
	 */
	private List<EbkProdContent> ebkProdContentList=new ArrayList<EbkProdContent>();
	/**
	 * 发车信息(旧版本)
	 */
	private Map<String,List<EbkProdContent>> ebkProdContentListMapOld=new HashMap<String,List<EbkProdContent>>();
	
	private Integer ebkProdJourneyListSize;
	/**
	 * 行程描述(旧版本)
	 */
	private Map<String,List<EbkProdJourney>> ebkProdJourneyListMapOld=new HashMap<String,List<EbkProdJourney>>();
	private Integer ebkProdJourneyListSizeOld;
	
	
	private Map<String,List<EbkMultiJourney>> ebkMultiJourneyListMapOld=new HashMap<String,List<EbkMultiJourney>>();
	
	private List<EbkProdBranch> ebkProdBranchList=new ArrayList<EbkProdBranch>();
	/**
	 * 时间价格表类型Id
	 */
	private Long ebkProdBranchProdBranchId;
	/**
	 * 时间价格表
	 */
	private List<EbkProdTimePrice> ebkProdTimePriceNewList=new ArrayList<EbkProdTimePrice>();
	/**
	 * 时间价格表(旧版本)
	 */
	private List<EbkProdTimePrice> ebkProdTimePriceList=new ArrayList<EbkProdTimePrice>();
	/**
	 * 审核-时间价格表-VO对象专用
	 */
	private List<EbkProdTimePriceAuditCombo> ebkProdTimePriceAuditComboList=new ArrayList<EbkProdTimePriceAuditCombo>();
	
	/**
	 * 关联销售产品
	 */
	private List<EbkProdRelation> ebkProdRelationList=new ArrayList<EbkProdRelation>();
	/**
	 * 关联销售产品(旧版本)
	 */
	private Map<String,List<EbkProdRelation>> ebkProdRelationListMapOld=new HashMap<String,List<EbkProdRelation>>();
	/**
	 * 当前日期
	 */
	private Date currPageDate;
	/**
	 * 时间价格表  上一月\下一月标识
	 */
	private String monthType;
	
	/**
	 * 审核通过 是否上线
	 */
	private String isOnline;
	/**
	 * 审核通过 上线开始时间
	 */
	private String onlineDateBegin;
	/**
	 * 审核通过 上线结束时间
	 */
	private String onlineDateEnd;
	
	/**
	 * 审核不通过-不通过理由
	 */
	private List<EbkProdRejectInfo> ebkProdRejectInfoList=new ArrayList<EbkProdRejectInfo>();
	
	
	private String search;
	/**提交申请开始时间**/
	private String sumitDateBegin;
	/**提交申请结束时间**/
	private String sumitDateEnd;
	
	private EbkProdAuditService ebkProdAuditService;
	
	private boolean flag=false;
	
	
	/**
	 * 搜索产品经理
	 */
	@Action(value = "/searchPermUser") 
	public void searchPermUser(){ 
		JSONArray array=new JSONArray(); 
		List<PermUser> permUserList=permUserService.selectListByUserNameOrRealName(search); 
		for (PermUser permUser : permUserList) { 
			JSONObject obj=new JSONObject(); 
			obj.put("id", permUser.getUserId()); 
			obj.put("text", permUser.getRealName()+"("+permUser.getUserName()+")"); 
			array.add(obj); 
		} 
		JSONOutput.writeJSON(getResponse(), array);
	}
	
	/**
	 * 转到待审核列表页
	 * 
	 * @return
	 */
	@Action("prodApprovalAuditList")
	public String prodApprovalAuditList() {
		
		Map<String, Object> parameters=new HashMap<String, Object>();
		
		parameters.put("status", EBK_PRODUCT_AUDIT_STATUS.PENDING_AUDIT.name());//待审核
		
		queryProduct(parameters);
		
		return "prodApprovalAuditList";
	}

	
	/**
	 * 转到已审核列表页
	 * 
	 * @return
	 */
	@Action("prodAuditedList")
	public String prodAuditedList() {
		
		Map<String, Object> parameters=new HashMap<String, Object>();
		
		if(null==ebkProdProduct){
			ebkProdProduct=new EbkProdProduct();
		}
		
		if(StringUtils.isNotBlank(ebkProdProduct.getStatus())){
			parameters.put("status", ebkProdProduct.getStatus());
		}else{
			parameters.put("statusIn", new String[]{EBK_PRODUCT_AUDIT_STATUS.THROUGH_AUDIT.name(),EBK_PRODUCT_AUDIT_STATUS.REJECTED_AUDIT.name()});
		}
		
		this.queryProduct(parameters);
		
		return "prodAuditedList";
	}
	
	/**
	 * 转到全部产品列表
	 * @return
	 */
	@Action("prodAuditList")
	public String prodAuditList() {
		
		Map<String, Object> parameters=new HashMap<String, Object>();
		
		if(null==ebkProdProduct){
			ebkProdProduct=new EbkProdProduct();
		}
		
		if(StringUtils.isNotBlank(ebkProdProduct.getStatus())){
			parameters.put("status", ebkProdProduct.getStatus());
		}else{
			parameters.put("statusIn", new String[]{
					EBK_PRODUCT_AUDIT_STATUS.PENDING_AUDIT.name(),
					EBK_PRODUCT_AUDIT_STATUS.THROUGH_AUDIT.name(),
					EBK_PRODUCT_AUDIT_STATUS.REJECTED_AUDIT.name()});
		}
		
		this.queryProduct(parameters);
		return "prodAuditList";
	}
	
	/**
	 * 统计数量
	 */
	private void countStatus() {
		Map<String, Object> countMap=new HashMap<String, Object>();
		List<Map<String,Object>> countMapList=ebkProdProductService.queryCountGroupByStatus(countMap);
		Map<String, Integer> statusCountMap=new HashMap<String, Integer>();
		int totalCounts=0;
		for (Map<String, Object> map : countMapList) {
			String statusKey=(String) map.get("STATUS");
			Integer statusCounts=Integer.parseInt(String.valueOf(map.get("CT")));
			if(!EBK_PRODUCT_AUDIT_STATUS.UNCOMMIT_AUDIT.name().equals(statusKey)){//不计算未提交的数量
				totalCounts+=statusCounts;
			}
			statusCountMap.put(statusKey, statusCounts);
		}
		statusCountMap.put("ALL", totalCounts);
		//处理没有的情况
		for (EBK_PRODUCT_AUDIT_STATUS status : EBK_PRODUCT_AUDIT_STATUS.values()) {
			if(!statusCountMap.containsKey(status.getCode())){
				statusCountMap.put(status.getCode(), 0);
			}
		}
		getRequest().setAttribute("statusCountMap", statusCountMap);
	}
	
	private void queryProduct(Map<String, Object> parameters) {
		
		if(null==this.page||this.page<0){
			this.page=1L;
		}
		
		parameters.put("currentPage", this.page);
		
		parameters.put("pageSize", ebkProdProductPage.getPageSize());
		
		//按照提交时间降序
		parameters.put("orderBy", "SUMIT_DATE");
		parameters.put("descOrAsc", "DESC");
		//parameters.put("subProductTypeIn", EBK_PRODUCT_VIEW_TYPE.getSubProductTypeCodes(EBK_PRODUCT_VIEW_TYPE.SURROUNDING_GROUP.name()));
		
		if(null==ebkProdProduct){
			ebkProdProduct=new EbkProdProduct();
		}
		
		if(StringUtils.isNotBlank(ebkProdProduct.getMetaName())){
			parameters.put("metaName", ebkProdProduct.getMetaName().trim());
		}
		if(StringUtils.isNotBlank(ebkProdProduct.getProdName())){
			parameters.put("prodName", ebkProdProduct.getProdName().trim());
		}
		if(null!=ebkProdProduct.getProdProductId()&&ebkProdProduct.getProdProductId()>0){
			parameters.put("prodProductId", ebkProdProduct.getProdProductId());
		}
		if(null!=ebkProdProduct.getSupplierId()&&ebkProdProduct.getSupplierId()>0){
			parameters.put("supplierId", ebkProdProduct.getSupplierId());
		}
		if(StringUtils.isNotBlank(sumitDateBegin)){
			parameters.put("commitTimeStart", sumitDateBegin);
		}
		if(StringUtils.isNotBlank(sumitDateEnd)){
			parameters.put("commitTimeEnd", sumitDateEnd);
		}
		if(null!=ebkProdProduct.getManagerId()&&ebkProdProduct.getManagerId()>0){
			parameters.put("managerId", ebkProdProduct.getManagerId());
		}
		if(StringUtils.isNotBlank(ebkProdProduct.getSubProductType())){
			parameters.put("subProductType", ebkProdProduct.getSubProductType());
		}
		
		ebkProdProductPage=ebkProdProductService.queryProduct(parameters);
		
		for (int i=0;i< ebkProdProductPage.getItems().size();i++) {
			
			EbkProdProduct ebkProd=ebkProdProductPage.getItems().get(i);
			
			EbkProdProductModel model = new EbkProdProductModel();
			
			BeanUtils.copyProperties(ebkProd, model);
			
			//上线状态
			if(null!=ebkProd.getProdProductId()){
				ProdProduct prodProduct = prodProductService.getProdProductById(ebkProd.getProdProductId());
				if(null!=prodProduct){
					if(!"Y".equalsIgnoreCase(prodProduct.getValid())){
						model.setOnLine("false");
					}else{
						model.setOnLine(prodProduct.getOnLine());
					}
				}
			}
			
			//驴妈妈产品经理
			if(null!=ebkProd.getManagerId()){
				PermUser user = permUserService.getPermUserByUserId(ebkProd.getManagerId());
				if(null!=user){
					model.setManagerName(user.getRealName());
				}
			}
			
			//供应商名称
			if(null!=ebkProd.getSupplierId()){
				SupSupplier supSupplier=supplierService.getSupplier(ebkProd.getSupplierId());
				if(null!=supSupplier){
					model.setSupSupplierName(supSupplier.getSupplierName());
				}
			}
			
			//申请人
			if(null!=ebkProd.getUpdateUserId()){
				EbkUser ebkUser=ebkUserService.getEbkUserById(ebkProd.getUpdateUserId());
				if(null!=ebkUser){
					model.setApplyAuditUserName(ebkUser.getUserName());
				}else{
					model.setApplyAuditUserName("系统");
				}
			}
			
			ebkProdProductPage.getItems().set(i, model);
		}
		
		ebkProdProductPage.buildUrl(getRequest());
		
		getRequest().setAttribute("pageView", ebkProdProductPage.pagination(14));
		
		//统计数量
		countStatus();
	}
	
	
	/**
	 * 审核-详情页面-版本比较返回TAB页是否有修改
	 * @author ZHANG Nan
	 * @return
	 */
	@Action("prodAuditApplyDetail")
	public String prodAuditApplyDetail(){
		//基础信息
		ebkProdProduct=ebkProdProductService.findEbkProdProductAndBaseByPrimaryKey(ebkProdProductId);
		compareTabsChange=ebkProdSnapshotService.compareTabsChange(ebkProdProductId);
		return "prodAuditApplyDetail";
	}
	
	/**
	 * 审核-基础信息页面
	 * @author ZHANG Nan
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Action("prodAuditApplyDetailBase")
	public String prodAuditApplyDetailBase(){
		//版本比较结果
		compareEbkProductBase=ebkProdSnapshotService.compareEbkProdProductBase(ebkProdProductId);
		//基础信息
		ebkProdProduct=ebkProdProductService.findEbkProdProductAndBaseByPrimaryKey(ebkProdProductId);
		//出发地
		fromPlaceName=getPlace(ebkProdProduct.getFromPlaceId());
		//出发地(旧版本)
		fromPlaceNameOld=getPlace(compareEbkProductBase.get("fromPlaceId")+"");
		
		//目的地
		toPlaceName=getPlace(ebkProdProduct.getToPlaceId());
		//目的地(旧版本)
		toPlaceNameOld=getPlace(compareEbkProductBase.get("toPlaceId")+"");
		
		//行程内包含景点
		tripScenery=getTripScenery(ebkProdProduct.getEbkProdPlaces());
		if(compareEbkProductBase.containsKey("ebkProdPlace")){
			//行程内包含景点(旧版本)
			tripSceneryOld=getTripScenery((List<EbkProdPlace>) compareEbkProductBase.get("ebkProdPlace"));	
		}
		
		//处理其他属性
		List<ProductModelProperty> productModelPropertyList=ebkProdSnapshotService.getModelPropertyList(ebkProdProduct.getSubProductType());
		getRequest().setAttribute("modelPropertyList", productModelPropertyList);
		getRequest().setAttribute("ebkProdModelPropertys", ebkProdProduct.getEbkProdModelPropertys());
		
		
		List<EbkProdTarget> ebkProdTargetList=ebkProdProduct.getEbkProdTargets();
		Map<String,Object> ebkProdTargetMap=getEbkProdTarget(ebkProdTargetList);
		//获取履行对象
		if(ebkProdTargetMap.containsKey(Constant.CONTACT_TYPE.SUP_PERFORM_TARGET.name())){
			supPerformTarget=(SupPerformTarget) ebkProdTargetMap.get(Constant.CONTACT_TYPE.SUP_PERFORM_TARGET.name());	
		}
		//获取履行对象(旧版本)
		if(compareEbkProductBase.containsKey("supPerformTarget")){
			Map<String,Object> supPerformTargetMap=getEbkProdTarget((EbkProdTarget)compareEbkProductBase.get("supPerformTarget"));
			supPerformTargetOld=(SupPerformTarget)supPerformTargetMap.get(Constant.CONTACT_TYPE.SUP_PERFORM_TARGET.name());
		}
		//获取凭证对象
		if(ebkProdTargetMap.containsKey(Constant.CONTACT_TYPE.SUP_B_CERTIFICATE_TARGET.name())){
			supBCertificateTarget=(SupBCertificateTarget) ebkProdTargetMap.get(Constant.CONTACT_TYPE.SUP_B_CERTIFICATE_TARGET.name());	
		}
		//获取凭证对象(旧版本)
		if(compareEbkProductBase.containsKey("supBCertificateTarget")){
			Map<String,Object> supPerformTargetMap=getEbkProdTarget((EbkProdTarget)compareEbkProductBase.get("supBCertificateTarget"));
			supBCertificateTargetOld=(SupBCertificateTarget)supPerformTargetMap.get(Constant.CONTACT_TYPE.SUP_B_CERTIFICATE_TARGET.name());
		}
		//获取结算对象
		if(ebkProdTargetMap.containsKey(Constant.CONTACT_TYPE.SUP_SETTLEMENT_TARGET.name())){
			supSettlementTarget=(SupSettlementTarget) ebkProdTargetMap.get(Constant.CONTACT_TYPE.SUP_SETTLEMENT_TARGET.name());	
		}
		//获取结算对象(旧版本)
		if(compareEbkProductBase.containsKey("supSettlementTarget")){
			Map<String,Object> supPerformTargetMap=getEbkProdTarget((EbkProdTarget)compareEbkProductBase.get("supSettlementTarget"));
			supSettlementTargetOld=(SupSettlementTarget)supPerformTargetMap.get(Constant.CONTACT_TYPE.SUP_SETTLEMENT_TARGET.name());
		}
		
		//获取驴妈妈产品联系人\联系电话
		permUserManager=permUserService.getPermUserByUserId(ebkProdProduct.getManagerId());
		
		//获取驴妈妈产品联系人\联系电话(旧版本)
		if(compareEbkProductBase.containsKey("managerId")){
			permUserManagerOld=permUserService.getPermUserByUserId(Long.parseLong(compareEbkProductBase.get("managerId")+""));	
		}
		
		//获取所属公司(旧版本)
		if(compareEbkProductBase.containsKey("orgId")){
			orgNameOld=Constant.FILIALE_NAME.getCnName(compareEbkProductBase.get("orgId")+"");
		}
		return "prodAuditApplyDetailBase";
	}
	/**
	 * 审核-产品推荐及特色页面
	 * @author ZHANG Nan
	 * @return
	 */
	@Action("prodAuditApplyDetailRecommend")
	public String prodAuditApplyDetailRecommend(){
		ebkProdProduct=ebkProdProductService.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
		//版本比较结果
		compareEbkProductRecommend=ebkProdSnapshotService.compareEbkProdProductRecommend(ebkProdProductId);
		EbkProdContent ebkProdContentDO=new EbkProdContent();
		ebkProdContentDO.setProductId(ebkProdProductId);
		List<EbkProdContent> ebkProdContentList=ebkProdContentService.findListByTerm(ebkProdContentDO);
		for (EbkProdContent ebkProdContent : ebkProdContentList) {
			ebkProdContentMap.put(ebkProdContent.getContentType(), ebkProdContent.getContent());
		}
		return "prodAuditApplyDetailRecommend";
	}
	
	/**
	 * 审核-行程描述页面
	 * @author ZHANG Nan
	 * @return
	 */
	@Action("prodAuditApplyDetailTrip")
	public String prodAuditApplyDetailTrip(){
		ebkProdProduct=ebkProdProductService.findEbkProdProductAndTripByPrimaryKey(ebkProdProductId);
		ebkProdJourneyList=ebkProdProduct.getEbkProdJourneys();
		for (EbkProdJourney ebkProdJourney : ebkProdJourneyList) {
			List<ComPicture> comPictureJourneyList=comPictureService.getComPictureByObjectIdAndTypeOrderBySeqNum(ebkProdJourney.getJourneyId(), Constant.EBK_PROD_PICTURE_TYPE.EBK_PROD_JOURNEY.name());
			ebkProdJourney.setComPictureJourneyList(comPictureJourneyList);
		}
		ebkProdJourneyListSize=ebkProdJourneyList.size();
		 
		//版本比较结果
		ebkProdJourneyListMapOld=ebkProdSnapshotService.compareEbkProdProductTrip(ebkProdProductId);
		
		if(ebkProdJourneyListMapOld.containsKey("tripNumber")){
			ebkProdJourneyListSizeOld=ebkProdJourneyListMapOld.get("tripNumber").size();
		}
		return "prodAuditApplyDetailTrip";
	}
	
	/**
	 * 审核-多行程页面
	 * @author haofeifei
	 * @return
	 */
	@Action("prodAuditApplyDetailMultiTrip")
	public String prodAuditApplyDetailMultiTrip(){
		ebkProdProduct=ebkProdProductService.findEbkProdProductAndTripByPrimaryKey(ebkProdProductId);  //产品对象
		Map<String,Object> resultMap=new HashMap<String,Object>();
		resultMap.put("ebkProdProductId", ebkProdProduct.getEbkProdProductId());
		ebkMultiJourneyList=ebkMultiJourneyService.queryMultiJourneyByParams(resultMap);             //多行程List
		for (EbkMultiJourney ebkMultiJourney : ebkMultiJourneyList) {
			ebkProdJourneyList=ebkProdJourneyService.getViewJourneyByMultiJourneyId(ebkMultiJourney.getMultiJourneyId());   //行程描述
			for (EbkProdJourney ebkProdJourney : ebkProdJourneyList) { //图片
				List<ComPicture> comPictureJourneyList=comPictureService.getComPictureByObjectIdAndTypeOrderBySeqNum(ebkProdJourney.getJourneyId(), Constant.EBK_PROD_PICTURE_TYPE.EBK_PROD_JOURNEY.name());
				ebkProdJourney.setComPictureJourneyList(comPictureJourneyList);
			}
			//ebkProdContentList=ebkProdContentService.getEbkContentByMultiJourneyId(ebkMultiJourney.getMultiJourneyId(),"");
			EbkProdContent ebkProdContentDO=new EbkProdContent();
			ebkProdContentDO.setMultiJourneyId(ebkMultiJourney.getMultiJourneyId());
			List<EbkProdContent> ebkProdContentList=ebkProdContentService.findListByTerm(ebkProdContentDO);   //费用说明
			ebkMultiJourney.setViewJourneyList(ebkProdJourneyList);     //把费用说明、行程描述和多行程联系起来
			ebkMultiJourney.setEbkProdContentList(ebkProdContentList);
		}
		//版本比较结果
		//ebkMultiJourneyListMapOld=ebkProdSnapshotService.compareEbkProdProductMultiTrip(ebkProdProductId);
		/*ebkProdJourneyListMapOld=ebkProdSnapshotService.compareEbkProdProductTrip(ebkProdProductId);
		List<EbkMultiJourney> ebkMulitiJourneyListOld=ebkMultiJourneyListMapOld.get("ebkMultiJourneyListOld");
		if (ebkMulitiJourneyListOld!=null) {
			for (int i = 0; i < ebkMultiJourneyList.size(); i++) {
				for (int j = 0; j < ebkMulitiJourneyListOld.size(); j++) {
					if (ebkMulitiJourneyListOld.get(j).getContent()!=ebkMultiJourneyList.get(i).getContent()) {
						flag=true;
					}
					if (ebkMulitiJourneyListOld.get(j).getDays()!=ebkMultiJourneyList.get(i).getDays()) {
						flag=true;
					}
					if (ebkMulitiJourneyListOld.get(j).getJourneyName()!=ebkMultiJourneyList.get(i).getJourneyName()) {
						flag=true;
					}
					if (ebkMulitiJourneyListOld.get(j).getNights()!=ebkMultiJourneyList.get(i).getNights()) {
						flag=true;
					}
					if (ebkMulitiJourneyListOld.get(j).getValid()!=ebkMultiJourneyList.get(i).getValid()) {
						flag=true;
					}
					
				}
			}
		}*/
		
		/**ebkProdJourneyListMapOld=ebkProdSnapshotService.compareEbkProdProductTrip(ebkProdProductId);
		
		if(ebkProdJourneyListMapOld.containsKey("tripNumber")){
			ebkProdJourneyListSizeOld=ebkProdJourneyListMapOld.get("tripNumber").size();
		}
		if(ebkMultiJourneyListMapOld.containsKey("tripNumber")){
			ebkProdJourneyListSizeOld=ebkProdJourneyListMapOld.get("tripNumber").size();
		}*/
		return "prodAuditApplyDetailMultiTrip";
	}
	

	/**
	 * 审核-费用说明页面
	 * @author ZHANG Nan
	 * @return
	 */
	@Action("prodAuditApplyDetailCost")
	public String prodAuditApplyDetailCost(){
		ebkProdProduct=ebkProdProductService.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
		//版本比较结果
		compareEbkProductCost=ebkProdSnapshotService.compareEbkProdProductCost(ebkProdProductId);
		EbkProdContent ebkProdContentDO=new EbkProdContent();
		ebkProdContentDO.setProductId(ebkProdProductId);
		List<EbkProdContent> ebkProdContentList=ebkProdContentService.findListByTerm(ebkProdContentDO);
		for (EbkProdContent ebkProdContent : ebkProdContentList) {
			ebkProdContentMap.put(ebkProdContent.getContentType(), ebkProdContent.getContent());
		}
		return "prodAuditApplyDetailCost";
	}
	/**
	 * 审核-产品图片页面
	 * @author ZHANG Nan
	 * @return
	 */
	@Action("prodAuditApplyDetailPicture")
	public String prodAuditApplyDetailPicture(){
		ebkProdProduct=ebkProdProductService.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
		List<ComPicture> comPictureSmallList=comPictureService.getComPictureByObjectIdAndTypeOrderBySeqNum(ebkProdProductId, Constant.EBK_PROD_PICTURE_TYPE.EBK_PROD_PRODUCT_SMALL.name());
		if(comPictureSmallList!=null && comPictureSmallList.size()>0){
			comPictureSmall=comPictureSmallList.get(0);
		}
		comPictureBigList=comPictureService.getComPictureByObjectIdAndTypeOrderBySeqNum(ebkProdProductId, Constant.EBK_PROD_PICTURE_TYPE.EBK_PROD_PRODUCT_BIG.name());
		return "prodAuditApplyDetailPicture";
	}
	/**
	 * 审核-发车信息页面
	 * @author ZHANG Nan
	 * @return
	 */
	@Action("prodAuditApplyDetailTraffic")
	public String prodAuditApplyDetailTraffic(){
		ebkProdProduct=ebkProdProductService.findEbkProdProductAndContentByPrimaryKey(ebkProdProductId);
		ebkProdContentList=ebkProdProduct.getEbkProdContents();
		
		 
		//版本比较结果
		ebkProdContentListMapOld=ebkProdSnapshotService.compareEbkProdProductTraffic(ebkProdProductId);
		
		return "prodAuditApplyDetailTraffic";
	}
	
	/**
	 * 审核-其它条款页面
	 * @author ZHANG Nan
	 * @return
	 */
	@Action("prodAuditApplyDetailOther")
	public String prodAuditApplyDetailOther(){
		ebkProdProduct=ebkProdProductService.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
		//版本比较结果
		compareEbkProductOther=ebkProdSnapshotService.compareEbkProdProductOther(ebkProdProductId);
		EbkProdContent ebkProdContentDO=new EbkProdContent();
		ebkProdContentDO.setProductId(ebkProdProductId);
		List<EbkProdContent> ebkProdContentList=ebkProdContentService.findListByTerm(ebkProdContentDO);
		for (EbkProdContent ebkProdContent : ebkProdContentList) {
			ebkProdContentMap.put(ebkProdContent.getContentType(), ebkProdContent.getContent());
		}
		return "prodAuditApplyDetailOther";
	}
	/**
	 * 审核-价格\库存页面
	 * @author ZHANG Nan
	 * @return
	 */
	@Action("prodAuditApplyDetailTimePrice")
	public String prodAuditApplyDetailTimePrice(){
		ebkProdProduct=ebkProdProductService.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
		EbkProdBranch ebkProdBranch=new EbkProdBranch();
		ebkProdBranch.setProdProductId(ebkProdProductId);
		ebkProdBranchList=ebkProdBranchService.query(ebkProdBranch);
		if(ebkProdBranchList!=null && ebkProdBranchList.size()>0 && ebkProdBranchProdBranchId==null){
			ebkProdBranchProdBranchId=ebkProdBranchList.get(0).getProdBranchId();
		}
		
		
		Map<String, Object> map=new HashMap<String,Object>();
		if ("UP".equals(monthType)) {
			map = DateUtil.getBeginAndEndDateByDate(DateUtil.getTheMiddle(currPageDate, -1));
		} else if ("DOWN".equals(monthType)) {
			map = DateUtil.getBeginAndEndDateByDate(DateUtil.getTheMiddle(currPageDate, 1));
		} else {
			if (currPageDate == null) {
				currPageDate = new Date();
			}
			map = DateUtil.getBeginAndEndDateByDate(currPageDate);
		}
		currPageDate = (Date) (map.get("currPageDate"));
		Date beginDate = (Date)map.get("beginDate");
		Date endDate =(Date)map.get("endDate");
		
		Map<String,Object> param=new HashMap<String,Object>();
		param.put("productId", ebkProdProductId);
		param.put("prodBranchId", ebkProdBranchProdBranchId);
		param.put("beginDate", beginDate);
		param.put("endDate", endDate);
		param.put("orderby", "SPEC_DATE");
		param.put("order", "ASC");
		
		ebkProdTimePriceNewList=ebkProdTimePriceService.query(param);
		
		ebkProdTimePriceList=ebkProdSnapshotService.getOldEbkProdTimePrice(ebkProdProductId);
		
		Map<String,EbkProdTimePrice> ebkProdTimePriceMap=new HashMap<String,EbkProdTimePrice>();
		for (EbkProdTimePrice ebkProdTimePrice : ebkProdTimePriceList) {
			ebkProdTimePriceMap.put(ebkProdTimePrice.getProductId()+"-"+ebkProdTimePrice.getProdBranchId()+"-"+ebkProdTimePrice.getSpecDate(), ebkProdTimePrice);
		}
		
		Map<Date,EbkProdTimePriceAuditCombo> ebkProdTimePriceAuditComboMap=new HashMap<Date,EbkProdTimePriceAuditCombo>();
		for (EbkProdTimePrice ebkProdTimePriceNew : ebkProdTimePriceNewList) {
			EbkProdTimePriceAuditCombo ebkProdTimePriceAuditCombo=new EbkProdTimePriceAuditCombo();
			ebkProdTimePriceAuditCombo.setDay(ebkProdTimePriceNew.getSpecDate());
			ebkProdTimePriceAuditCombo.setEbkProdTimePriceNew(ebkProdTimePriceNew);
			ebkProdTimePriceAuditCombo.setEbkProdTimePrice(ebkProdTimePriceMap.get(ebkProdTimePriceNew.getProductId()+"-"+ebkProdTimePriceNew.getProdBranchId()+"-"+ebkProdTimePriceNew.getSpecDate()));
			ebkProdTimePriceAuditComboMap.put(ebkProdTimePriceNew.getSpecDate(), ebkProdTimePriceAuditCombo);
		}
		
		
		long quot = ((endDate.getTime()-beginDate.getTime()) / 1000 / 60 / 60 / 24)+1;
		ebkProdTimePriceAuditComboList=new ArrayList<EbkProdTimePriceAuditCombo>(Integer.parseInt(quot+""));
		Calendar calendar =Calendar.getInstance();
		for (int i=0;i<quot;i++) {
			calendar.setTime(beginDate);
			calendar.set(Calendar.DATE, calendar.get(Calendar.DATE)+Integer.parseInt(i+""));
			EbkProdTimePriceAuditCombo ebkProdTimePriceAuditCombo=ebkProdTimePriceAuditComboMap.get(calendar.getTime());
			if(ebkProdTimePriceAuditCombo==null){
				ebkProdTimePriceAuditCombo=new EbkProdTimePriceAuditCombo();
				ebkProdTimePriceAuditCombo.setDay(calendar.getTime());
			}
			ebkProdTimePriceAuditComboList.add(ebkProdTimePriceAuditCombo);
		}
		return "prodAuditApplyDetailTimePrice";
	}
	
	/**
	 * 审核-关联销售产品页面
	 * @author shangzhengyuan
	 * @return
	 */
	@Action("prodAuditApplyDetailRelation")
	public String prodAuditApplyDetailRelation(){
		ebkProdProduct=ebkProdProductService.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
		EbkProdRelation ebkProdRelation = new EbkProdRelation();
		ebkProdRelation.setEbkProductId(ebkProdProductId);
		ebkProdRelationList = ebkProdRelationService.findListByTerm(ebkProdRelation);
		//版本比较结果
		ebkProdRelationListMapOld=ebkProdSnapshotService.compareEbkProdProductRelation(ebkProdProductId);
		if(null!=ebkProdRelationListMapOld && ebkProdRelationListMapOld.containsKey("ebkProdRelationListOld")
		&& null!=ebkProdRelationList && ebkProdRelationList.size()<ebkProdRelationListMapOld.get("ebkProdRelationListOld").size()){
			for(int i=0;i<ebkProdRelationListMapOld.get("ebkProdRelationListOld").size()-ebkProdRelationList.size();i++){
				ebkProdRelationList.add(new EbkProdRelation());
			}
		}
		return "prodAuditApplyDetailRelation";
	}
	
	/**
	 * 审核-审核结果页面
	 * @author ZHANG Nan
	 * @return
	 */
	@Action("prodAuditApplyDetailAuditResult")
	public String prodAuditApplyDetailAuditResult(){
		ebkProdProduct=ebkProdProductService.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
		EbkProdRejectInfo ebkProdRejectInfo=new EbkProdRejectInfo();
		ebkProdRejectInfo.setProductId(ebkProdProductId);
		ebkProdRejectInfoList=ebkProdRejectInfoService.query(ebkProdRejectInfo);
		return "prodAuditApplyDetailAuditResult";
	}
	
	
	
	
	/**
	 * 审核-提交审核结果show
	 * @author ZHANG Nan
	 * @return
	 */
	@Action("prodAuditApplyResult")
	public String prodAuditApplyResult(){
		ebkProdProduct=ebkProdProductService.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
		return "prodAuditApplyResult";
	}

	/**
	 * 审核-审核通过
	 * @author ZHANG Nan
	 * @return
	 */
	@Action("auditResultSubmitPass")
	public void auditResultSubmitPass(){
		try {
			if(auditSubmitBeforeCheck()){
				ebkProdAuditService.auditPassByUser(ebkProdProductId,
						StringUtils.isNotBlank(onlineDateBegin) ? DateUtil.toDate(onlineDateBegin, "yyyy-MM-dd") : new Date(),
						StringUtils.isNotBlank(onlineDateBegin) ? DateUtil.toDate(onlineDateEnd, "yyyy-MM-dd") : new Date(),false);
				this.getResponse().getWriter().write("{result:'true'}");
			}else{
				this.getResponse().getWriter().write("{result:'当前产品状态不为待审核'}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				this.getResponse().getWriter().write("{result:'操作失败'}");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	
	/**
	 * 审核-审核不通过
	 * @author ZHANG Nan
	 * @return
	 */
	@Action("auditResultSubmitNoPass")
	public void auditResultSubmitNoPass(){
		try {
			if(auditSubmitBeforeCheck()){
				for (int i=0;i<ebkProdRejectInfoList.size();i++) {
					EbkProdRejectInfo ebkProdRejectInfo=ebkProdRejectInfoList.get(i);
					ebkProdRejectInfo.setProductId(ebkProdProductId);
					if(StringUtils.isBlank(ebkProdRejectInfo.getType()) || ebkProdRejectInfo.getType()=="null"){
						ebkProdRejectInfoList.remove(i);
						i--;
					}
				}
				ebkProdProductService.prodProductAuditNoPass(ebkProdProductId, ebkProdRejectInfoList);
				this.getResponse().getWriter().write("{result:'true'}");
			}
			else{
				this.getResponse().getWriter().write("{result:'当前产品状态不为待审核'}");
			}
		} catch (Exception e) {
			e.printStackTrace();
			try {
				this.getResponse().getWriter().write("{result:'操作失败!'}");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	/**
	 * 审核提交前检查当前产品是否为待审核状态
	 * @author ZHANG Nan
	 * @return
	 */
	private Boolean auditSubmitBeforeCheck(){
		EbkProdProduct ebkProdProduct=ebkProdProductService.findEbkProdProductDOByPrimaryKey(ebkProdProductId);
		if(ebkProdProduct!=null && Constant.EBK_PRODUCT_AUDIT_STATUS.PENDING_AUDIT.name().equals(ebkProdProduct.getStatus())){
			return true;
		}
		return false;
	}
	
	private String getPlace(String placeId){
		try {
			if(!"null".equals(placeId) && StringUtils.isNotBlank(placeId)){
				return getPlace(Long.parseLong(placeId));	
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	private String getPlace(Long placeId){
		Place place=comPlaceService.load(placeId);
		if(place!=null){
			return place.getName();
		}
		return "";
	}
	private String getTripScenery(List<EbkProdPlace> ebkProdPlaceList){
		List<Long> placeIdList=new ArrayList<Long>();
		String tripSceneryName="";
		for (EbkProdPlace ebkProdPlace : ebkProdPlaceList) {
			placeIdList.add(ebkProdPlace.getPlaceId());
		}
		if(!placeIdList.isEmpty()){
			List<Place> placeList=comPlaceService.selectByPlaceIds(placeIdList);
			for (Place place : placeList) {
				tripSceneryName+=place.getName()+",";
			}
			if(StringUtils.isNotBlank(tripSceneryName) && tripSceneryName.length()>1){
				tripSceneryName=tripSceneryName.substring(0,tripSceneryName.length()-1);
			}	
		}
		return tripSceneryName;
	}
	
	private Map<String,Object> getEbkProdTarget(List<EbkProdTarget> ebkProdTargetList){
		Map<String,Object> ebkProdTargetMap=new HashMap<String,Object>();
		for (EbkProdTarget ebkProdTarget : ebkProdTargetList) {
			if(Constant.CONTACT_TYPE.SUP_PERFORM_TARGET.name().equals(ebkProdTarget.getTargetType())){
				SupPerformTarget supPerformTargetTemp=performTargetService.getSupPerformTarget(ebkProdTarget.getTargetId());
				ebkProdTargetMap.put(Constant.CONTACT_TYPE.SUP_PERFORM_TARGET.name(), supPerformTargetTemp);
			}
			else if(Constant.CONTACT_TYPE.SUP_B_CERTIFICATE_TARGET.name().equals(ebkProdTarget.getTargetType())){
				SupBCertificateTarget supBCertificateTargetTemp=bCertificateTargetService.getBCertificateTargetByTargetId(ebkProdTarget.getTargetId());
				ebkProdTargetMap.put(Constant.CONTACT_TYPE.SUP_B_CERTIFICATE_TARGET.name(), supBCertificateTargetTemp);
			}
			else if(Constant.CONTACT_TYPE.SUP_SETTLEMENT_TARGET.name().equals(ebkProdTarget.getTargetType())){
				SupSettlementTarget supSettlementTargetTemp=settlementTargetService.getSettlementTargetById(ebkProdTarget.getTargetId());
				ebkProdTargetMap.put(Constant.CONTACT_TYPE.SUP_SETTLEMENT_TARGET.name(), supSettlementTargetTemp);
			}
		}
		return ebkProdTargetMap;
	}
	private Map<String,Object> getEbkProdTarget(EbkProdTarget ebkProdTarget){
		List<EbkProdTarget> ebkProdTargetList =new ArrayList<EbkProdTarget>();
		ebkProdTargetList.add(ebkProdTarget);
		return getEbkProdTarget(ebkProdTargetList);
	}
	
	public Long getEbkProdProductId() {
		return ebkProdProductId;
	}	public void setEbkProdProductId(Long ebkProdProductId) {
		this.ebkProdProductId = ebkProdProductId;
	}
	public EbkProdSnapshotService getEbkProdSnapshotService() {
		return ebkProdSnapshotService;
	}
	public void setEbkProdSnapshotService(EbkProdSnapshotService ebkProdSnapshotService) {
		this.ebkProdSnapshotService = ebkProdSnapshotService;
	}
	public EbkProdProductService getEbkProdProductService() {
		return ebkProdProductService;
	}
	public void setEbkProdProductService(EbkProdProductService ebkProdProductService) {
		this.ebkProdProductService = ebkProdProductService;
	}
	public Map<String, String> getCompareTabsChange() {
		return compareTabsChange;
	}
	public void setCompareTabsChange(Map<String, String> compareTabsChange) {
		this.compareTabsChange = compareTabsChange;
	}
	public Map<String, Object> getCompareEbkProductBase() {
		return compareEbkProductBase;
	}
	public void setCompareEbkProductBase(Map<String, Object> compareEbkProductBase) {
		this.compareEbkProductBase = compareEbkProductBase;
	}
	public EbkProdProduct getEbkProdProduct() {
		return ebkProdProduct;
	}
	public void setEbkProdProduct(EbkProdProduct ebkProdProduct) {
		this.ebkProdProduct = ebkProdProduct;
	}
	public void setProdProductService(ProdProductService prodProductService) {
		this.prodProductService = prodProductService;
	}
	public void setSupplierService(SupplierService supplierService) {
		this.supplierService = supplierService;
	}
	public void setEbkUserService(EbkUserService ebkUserService) {
		this.ebkUserService = ebkUserService;
	}
	public ComPlaceService getComPlaceService() {
		return comPlaceService;
	}
	public void setComPlaceService(ComPlaceService comPlaceService) {
		this.comPlaceService = comPlaceService;
	}
	public String getFromPlaceName() {
		return fromPlaceName;
	}
	public void setFromPlaceName(String fromPlaceName) {
		this.fromPlaceName = fromPlaceName;
	}
	public String getToPlaceName() {
		return toPlaceName;
	}
	public void setToPlaceName(String toPlaceName) {
		this.toPlaceName = toPlaceName;
	}
	public String getFromPlaceNameOld() {
		return fromPlaceNameOld;
	}
	public void setFromPlaceNameOld(String fromPlaceNameOld) {
		this.fromPlaceNameOld = fromPlaceNameOld;
	}
	public String getToPlaceNameOld() {
		return toPlaceNameOld;
	}
	public void setToPlaceNameOld(String toPlaceNameOld) {
		this.toPlaceNameOld = toPlaceNameOld;
	}
	public String getTripScenery() {
		return tripScenery;
	}
	public void setTripScenery(String tripScenery) {
		this.tripScenery = tripScenery;
	}
	public String getTripSceneryOld() {
		return tripSceneryOld;
	}
	public void setTripSceneryOld(String tripSceneryOld) {
		this.tripSceneryOld = tripSceneryOld;
	}
	public ProductModelPropertyService getProductModelPropertyService() {
		return productModelPropertyService;
	}
	public void setProductModelPropertyService(ProductModelPropertyService productModelPropertyService) {
		this.productModelPropertyService = productModelPropertyService;
	}
	public EbkProdPlaceService getEbkProdPlaceService() {
		return ebkProdPlaceService;
	}
	public void setEbkProdPlaceService(EbkProdPlaceService ebkProdPlaceService) {
		this.ebkProdPlaceService = ebkProdPlaceService;
	}
	public EbkProdModelPropertyService getEbkProdModelPropertyService() {
		return ebkProdModelPropertyService;
	}
	public void setEbkProdModelPropertyService(EbkProdModelPropertyService ebkProdModelPropertyService) {
		this.ebkProdModelPropertyService = ebkProdModelPropertyService;
	}
	public Page<EbkProdProduct> getEbkProdProductPage() {
		return ebkProdProductPage;
	}
	public void setEbkProdProductPage(Page<EbkProdProduct> ebkProdProductPage) {
		this.ebkProdProductPage = ebkProdProductPage;
	}
	public EbkProdTargetService getEbkProdTargetService() {
		return ebkProdTargetService;
	}
	public void setEbkProdTargetService(EbkProdTargetService ebkProdTargetService) {
		this.ebkProdTargetService = ebkProdTargetService;
	}
	public PerformTargetService getPerformTargetService() {
		return performTargetService;
	}
	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}
	public BCertificateTargetService getbCertificateTargetService() {
		return bCertificateTargetService;
	}
	public void setbCertificateTargetService(BCertificateTargetService bCertificateTargetService) {
		this.bCertificateTargetService = bCertificateTargetService;
	}
	public SettlementTargetService getSettlementTargetService() {
		return settlementTargetService;
	}
	public void setSettlementTargetService(SettlementTargetService settlementTargetService) {
		this.settlementTargetService = settlementTargetService;
	}
	public SupPerformTarget getSupPerformTarget() {
		return supPerformTarget;
	}
	public void setSupPerformTarget(SupPerformTarget supPerformTarget) {
		this.supPerformTarget = supPerformTarget;
	}
	public SupBCertificateTarget getSupBCertificateTarget() {
		return supBCertificateTarget;
	}
	public void setSupBCertificateTarget(SupBCertificateTarget supBCertificateTarget) {
		this.supBCertificateTarget = supBCertificateTarget;
	}
	public SupSettlementTarget getSupSettlementTarget() {
		return supSettlementTarget;
	}
	public void setSupSettlementTarget(SupSettlementTarget supSettlementTarget) {
		this.supSettlementTarget = supSettlementTarget;
	}
	public SupPerformTarget getSupPerformTargetOld() {
		return supPerformTargetOld;
	}
	public void setSupPerformTargetOld(SupPerformTarget supPerformTargetOld) {
		this.supPerformTargetOld = supPerformTargetOld;
	}
	public SupBCertificateTarget getSupBCertificateTargetOld() {
		return supBCertificateTargetOld;
	}
	public void setSupBCertificateTargetOld(SupBCertificateTarget supBCertificateTargetOld) {
		this.supBCertificateTargetOld = supBCertificateTargetOld;
	}
	public SupSettlementTarget getSupSettlementTargetOld() {
		return supSettlementTargetOld;
	}
	public void setSupSettlementTargetOld(SupSettlementTarget supSettlementTargetOld) {
		this.supSettlementTargetOld = supSettlementTargetOld;
	}
	public PermUserService getPermUserService() {
		return permUserService;
	}
	public void setPermUserService(PermUserService permUserService) {
		this.permUserService = permUserService;
	}
	public PermUser getPermUserManager() {
		return permUserManager;
	}
	public void setPermUserManager(PermUser permUserManager) {
		this.permUserManager = permUserManager;
	}
	public PermUser getPermUserManagerOld() {
		return permUserManagerOld;
	}
	public void setPermUserManagerOld(PermUser permUserManagerOld) {
		this.permUserManagerOld = permUserManagerOld;
	}
	public String getOrgNameOld() {
		return orgNameOld;
	}
	public void setOrgNameOld(String orgNameOld) {
		this.orgNameOld = orgNameOld;
	}
	public EbkProdContentService getEbkProdContentService() {
		return ebkProdContentService;
	}
	public void setEbkProdContentService(EbkProdContentService ebkProdContentService) {
		this.ebkProdContentService = ebkProdContentService;
	}
	public Map<String, String> getEbkProdContentMap() {
		return ebkProdContentMap;
	}
	public void setEbkProdContentMap(Map<String, String> ebkProdContentMap) {
		this.ebkProdContentMap = ebkProdContentMap;
	}
	public Map<String, Object> getCompareEbkProductRecommend() {
		return compareEbkProductRecommend;
	}
	public void setCompareEbkProductRecommend(Map<String, Object> compareEbkProductRecommend) {
		this.compareEbkProductRecommend = compareEbkProductRecommend;
	}
	public EbkProdJourneyService getEbkProdJourneyService() {
		return ebkProdJourneyService;
	}
	public void setEbkProdJourneyService(EbkProdJourneyService ebkProdJourneyService) {
		this.ebkProdJourneyService = ebkProdJourneyService;
	}
	public List<EbkProdJourney> getEbkProdJourneyList() {
		return ebkProdJourneyList;
	}
	public void setEbkProdJourneyList(List<EbkProdJourney> ebkProdJourneyList) {
		this.ebkProdJourneyList = ebkProdJourneyList;
	}
	public Integer getEbkProdJourneyListSize() {
		return ebkProdJourneyListSize;
	}
	public void setEbkProdJourneyListSize(Integer ebkProdJourneyListSize) {
		this.ebkProdJourneyListSize = ebkProdJourneyListSize;
	}
	public Map<String, List<EbkProdJourney>> getEbkProdJourneyListMapOld() {
		return ebkProdJourneyListMapOld;
	}
	public void setEbkProdJourneyListMapOld(Map<String, List<EbkProdJourney>> ebkProdJourneyListMapOld) {
		this.ebkProdJourneyListMapOld = ebkProdJourneyListMapOld;
	}
	public Integer getEbkProdJourneyListSizeOld() {
		return ebkProdJourneyListSizeOld;
	}
	public void setEbkProdJourneyListSizeOld(Integer ebkProdJourneyListSizeOld) {
		this.ebkProdJourneyListSizeOld = ebkProdJourneyListSizeOld;
	}
	public String getSearch() {
		return search;
	}
	public void setSearch(String search) {
		this.search = search;
	}
	public void setSumitDateBegin(String sumitDateBegin) {
		this.sumitDateBegin = sumitDateBegin;
	}
	public void setSumitDateEnd(String sumitDateEnd) {
		this.sumitDateEnd = sumitDateEnd;
	}
	public List<CodeItem> getSubProductTypeList() {
		return ProductUtil.getSubProductTypeList(Constant.PRODUCT_TYPE.ROUTE.name(),true);
	}
	
	public Map<String, Object> getCompareEbkProductCost() {
		return compareEbkProductCost;
	}
	public void setCompareEbkProductCost(Map<String, Object> compareEbkProductCost) {
		this.compareEbkProductCost = compareEbkProductCost;
	}
	public ProdProductService getProdProductService() {
		return prodProductService;
	}
	public SupplierService getSupplierService() {
		return supplierService;
	}
	public EbkUserService getEbkUserService() {
		return ebkUserService;
	}
	public Map<String, Object> getCompareEbkProductOther() {
		return compareEbkProductOther;
	}
	public void setCompareEbkProductOther(Map<String, Object> compareEbkProductOther) {
		this.compareEbkProductOther = compareEbkProductOther;
	}
	public ComPictureService getComPictureService() {
		return comPictureService;
	}
	public void setComPictureService(ComPictureService comPictureService) {
		this.comPictureService = comPictureService;
	}
	public String getSumitDateBegin() {
		return sumitDateBegin;
	}
	public String getSumitDateEnd() {
		return sumitDateEnd;
	}
	public ComPicture getComPictureSmall() {
		return comPictureSmall;
	}
	public void setComPictureSmall(ComPicture comPictureSmall) {
		this.comPictureSmall = comPictureSmall;
	}
	public List<ComPicture> getComPictureBigList() {
		return comPictureBigList;
	}
	public void setComPictureBigList(List<ComPicture> comPictureBigList) {
		this.comPictureBigList = comPictureBigList;
	}
	public List<EbkProdContent> getEbkProdContentList() {
		return ebkProdContentList;
	}
	public void setEbkProdContentList(List<EbkProdContent> ebkProdContentList) {
		this.ebkProdContentList = ebkProdContentList;
	}
	public Map<String, List<EbkProdContent>> getEbkProdContentListMapOld() {
		return ebkProdContentListMapOld;
	}
	public void setEbkProdContentListMapOld(Map<String, List<EbkProdContent>> ebkProdContentListMapOld) {
		this.ebkProdContentListMapOld = ebkProdContentListMapOld;
	}
	public String getIsOnline() {
		return isOnline;
	}
	public void setIsOnline(String isOnline) {
		this.isOnline = isOnline;
	}
	public String getOnlineDateBegin() {
		return onlineDateBegin;
	}
	public void setOnlineDateBegin(String onlineDateBegin) {
		this.onlineDateBegin = onlineDateBegin;
	}
	public String getOnlineDateEnd() {
		return onlineDateEnd;
	}
	public void setOnlineDateEnd(String onlineDateEnd) {
		this.onlineDateEnd = onlineDateEnd;
	}
	public List<EbkProdRejectInfo> getEbkProdRejectInfoList() {
		return ebkProdRejectInfoList;
	}
	public void setEbkProdRejectInfoList(List<EbkProdRejectInfo> ebkProdRejectInfoList) {
		this.ebkProdRejectInfoList = ebkProdRejectInfoList;
	}
	public String getEbkProdProductStatus() {
		return ebkProdProductStatus;
	}
	public void setEbkProdProductStatus(String ebkProdProductStatus) {
		this.ebkProdProductStatus = ebkProdProductStatus;
	}
	public EbkProdRejectInfoService getEbkProdRejectInfoService() {
		return ebkProdRejectInfoService;
	}
	public void setEbkProdRejectInfoService(EbkProdRejectInfoService ebkProdRejectInfoService) {
		this.ebkProdRejectInfoService = ebkProdRejectInfoService;
	}
	public EbkProdBranchService getEbkProdBranchService() {
		return ebkProdBranchService;
	}
	public void setEbkProdBranchService(EbkProdBranchService ebkProdBranchService) {
		this.ebkProdBranchService = ebkProdBranchService;
	}
	public List<EbkProdBranch> getEbkProdBranchList() {
		return ebkProdBranchList;
	}
	public void setEbkProdBranchList(List<EbkProdBranch> ebkProdBranchList) {
		this.ebkProdBranchList = ebkProdBranchList;
	}
	public Long getEbkProdBranchProdBranchId() {
		return ebkProdBranchProdBranchId;
	}
	public void setEbkProdBranchProdBranchId(Long ebkProdBranchProdBranchId) {
		this.ebkProdBranchProdBranchId = ebkProdBranchProdBranchId;
	}
	public EbkProdTimePriceService getEbkProdTimePriceService() {
		return ebkProdTimePriceService;
	}
	public void setEbkProdTimePriceService(EbkProdTimePriceService ebkProdTimePriceService) {
		this.ebkProdTimePriceService = ebkProdTimePriceService;
	}
	public List<EbkProdTimePrice> getEbkProdTimePriceList() {
		return ebkProdTimePriceList;
	}
	public void setEbkProdTimePriceList(List<EbkProdTimePrice> ebkProdTimePriceList) {
		this.ebkProdTimePriceList = ebkProdTimePriceList;
	}
	public List<EbkProdTimePriceAuditCombo> getEbkProdTimePriceAuditComboList() {
		return ebkProdTimePriceAuditComboList;
	}
	public void setEbkProdTimePriceAuditComboList(List<EbkProdTimePriceAuditCombo> ebkProdTimePriceAuditComboList) {
		this.ebkProdTimePriceAuditComboList = ebkProdTimePriceAuditComboList;
	}
	public List<EbkProdTimePrice> getEbkProdTimePriceNewList() {
		return ebkProdTimePriceNewList;
	}
	public void setEbkProdTimePriceNewList(List<EbkProdTimePrice> ebkProdTimePriceNewList) {
		this.ebkProdTimePriceNewList = ebkProdTimePriceNewList;
	}
	public Date getCurrPageDate() {
		return currPageDate;
	}
	public void setCurrPageDate(Date currPageDate) {
		this.currPageDate = currPageDate;
	}
	public String getMonthType() {
		return monthType;
	}
	public void setMonthType(String monthType) {
		this.monthType = monthType;
	}

	public void setEbkProdAuditService(EbkProdAuditService ebkProdAuditService) {
		this.ebkProdAuditService = ebkProdAuditService;
	}

	public List<EbkProdRelation> getEbkProdRelationList() {
		return ebkProdRelationList;
	}

	public void setEbkProdRelationList(List<EbkProdRelation> ebkProdRelationList) {
		this.ebkProdRelationList = ebkProdRelationList;
	}

	public Map<String, List<EbkProdRelation>> getEbkProdRelationListMapOld() {
		return ebkProdRelationListMapOld;
	}

	public void setEbkProdRelationListMapOld(
			Map<String, List<EbkProdRelation>> ebkProdRelationListMapOld) {
		this.ebkProdRelationListMapOld = ebkProdRelationListMapOld;
	}

	public void setEbkProdRelationService(
			EbkProdRelationService ebkProdRelationService) {
		this.ebkProdRelationService = ebkProdRelationService;
	}	
	
	public EbkMultiJourneyService getEbkMultiJourneyService() {
		return ebkMultiJourneyService;
	}

	public void setEbkMultiJourneyService(
			EbkMultiJourneyService ebkMultiJourneyService) {
		this.ebkMultiJourneyService = ebkMultiJourneyService;
	}

	public List<EbkMultiJourney> getEbkMultiJourneyList() {
		return ebkMultiJourneyList;
	}

	public void setEbkMultiJourneyList(List<EbkMultiJourney> ebkMultiJourneyList) {
		this.ebkMultiJourneyList = ebkMultiJourneyList;
	}

	public Map<String, List<EbkMultiJourney>> getEbkMultiJourneyListMapOld() {
		return ebkMultiJourneyListMapOld;
	}

	public void setEbkMultiJourneyListMapOld(
			Map<String, List<EbkMultiJourney>> ebkMultiJourneyListMapOld) {
		this.ebkMultiJourneyListMapOld = ebkMultiJourneyListMapOld;
	}
	
	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}
}
