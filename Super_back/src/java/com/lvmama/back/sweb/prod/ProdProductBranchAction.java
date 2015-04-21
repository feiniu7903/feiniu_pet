package com.lvmama.back.sweb.prod;

import com.lvmama.back.sweb.BranchItemException;
import com.lvmama.back.sweb.TimePriceException;
import com.lvmama.back.utils.StringUtil;
import com.lvmama.back.web.upload.UploadCtrl;
import com.lvmama.comm.bee.po.meta.MetaProduct;
import com.lvmama.comm.bee.po.meta.MetaProductBranch;
import com.lvmama.comm.bee.po.meta.MetaProductTicket;
import com.lvmama.comm.bee.po.meta.MetaProductTraffic;
import com.lvmama.comm.bee.po.prod.*;
import com.lvmama.comm.bee.service.meta.MetaProductBranchService;
import com.lvmama.comm.bee.service.meta.MetaProductService;
import com.lvmama.comm.bee.service.prod.ProdProductBranchService;
import com.lvmama.comm.bee.service.prod.ProdProductPlaceService;
import com.lvmama.comm.bee.service.prod.ProdProductRelationService;
import com.lvmama.comm.bee.service.prod.ProdTrainService;
import com.lvmama.comm.jms.MessageFactory;
import com.lvmama.comm.pet.po.place.PlaceFlight;
import com.lvmama.comm.pet.po.pub.CodeItem;
import com.lvmama.comm.pet.po.pub.CodeSet;
import com.lvmama.comm.pet.po.sup.SupBCertificateTarget;
import com.lvmama.comm.pet.po.sup.SupPerformTarget;
import com.lvmama.comm.pet.po.sup.SupSettlementTarget;
import com.lvmama.comm.pet.service.place.PlaceFlightService;
import com.lvmama.comm.pet.service.sup.BCertificateTargetService;
import com.lvmama.comm.pet.service.sup.PerformTargetService;
import com.lvmama.comm.pet.service.sup.SettlementTargetService;
import com.lvmama.comm.utils.CopyUtil;
import com.lvmama.comm.utils.DateUtil;
import com.lvmama.comm.utils.ProductUtil;
import com.lvmama.comm.utils.json.JSONResult;
import com.lvmama.comm.utils.json.JSONResultException;
import com.lvmama.comm.utils.json.ResultHandle;
import com.lvmama.comm.utils.json.ResultHandleT;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Direction;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.util.Assert;

import java.io.File;
import java.util.*;

/**
 *销售产品类别
 * @author yuzhibing
 */
@Results({
	@Result(name = "toProductBranch", location = "/WEB-INF/pages/back/prod/branch/prod_branch.jsp"),
	@Result(name = "auditingShow", location = "/WEB-INF/pages/back/prod/auditing/prod_branch_auditing_show.jsp"),
	@Result(name = "toBranchItem",location = "/WEB-INF/pages/back/prod/branch/branch_item.jsp"),
	@Result(name = "toBranchItemAuditingShow",location = "/WEB-INF/pages/back/prod/auditing/branch_item_auditing_show.jsp"),
	@Result(name = "toBranch", location = "toProductBranch.do",type="redirect",params = {"productId","${productId}"})
	})
public class ProdProductBranchAction extends ProductAction{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2660765714936055586L;
	private ProdProductBranchService prodProductBranchService;
	private ProdProductRelationService prodProductRelationService;
	private MetaProductBranchService metaProductBranchService;
	private MetaProductService metaProductService;
	private SettlementTargetService settlementTargetService;
	private PerformTargetService performTargetService;
	private BCertificateTargetService bCertificateTargetService;
	private ProdTrainService prodTrainService;
	private List<ProdProductBranch> productBranchList;
	private List<ProdProductBranchItem> branchItemList;
	private ProdProductBranchItem branchItem;
	private ProdProductBranch branch;
	private MetaProduct needAddMetaProduct;
	private Long prodBranchId;
	private Long branchItemId;
	private String direction;
	private Long branchProductId;
	private final long BRANCH_COUNT=2L;
	private File file;
	private String fileContentType;
	private String fileFileName;

	public ProdProductBranchAction() {
		super();
		setMenuType("branch");
	}

    @Action(value="/prod/toProductBranchAuditingShow")
    public String toProductBranchAuditingShow(){
        this.goEdit();
        return "auditingShow";
    }

	@Override	
	@Action(value="/prod/toProductBranch")
	public String goEdit(){
		if(!doBefore()){
			return PRODUCT_EXCEPTION_PAGE;
		}
		
		productBranchList=prodProductBranchService.getProductBranchByProductId(product.getProductId(),null,null);
		return "toProductBranch";
	}
	
	/**
	 * 排序向上
	 */
	@Action(value="/prod/upProductBranch")
	public String upProductBranch(){
		//获取本branch
		ProdProductBranch productBranch = this.prodProductBranchService.selectProdProductBranchByPK(prodBranchId);
		
		//获取前一个branch
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("productId", productBranch.getProductId());
		params.put("branchSerialNumber", productBranch.getBranchSerialNumber());
		ProdProductBranch preProductBranch = this.prodProductBranchService.getPreProductBranch(params);
		
		//前一个branch为null返回页面
		if(preProductBranch != null){
			//修改前一个branch
			params.clear();
			params.put("prodBranchId", preProductBranch.getProdBranchId());
			params.put("branchSerialNumber", productBranch.getBranchSerialNumber());
			this.prodProductBranchService.updateProductBranchSerialNumber(params);
			
			//修改本branch
			params.clear();
			params.put("prodBranchId", productBranch.getProdBranchId());
			params.put("branchSerialNumber", preProductBranch.getBranchSerialNumber());
			this.prodProductBranchService.updateProductBranchSerialNumber(params);
		}
		
		productMessageProducer.sendMsg(MessageFactory.newProductUpdateEbkMessage(productBranch.getProductId()));
		return "toBranch";
	}
	
	
	/**
	 * 排序向下
	 */
	@Action(value="/prod/downProductBranch")
	public String downProductBranch(){
		//获取本branch
		ProdProductBranch productBranch = this.prodProductBranchService.selectProdProductBranchByPK(prodBranchId);
		
		//获取后一个branch
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("productId", productBranch.getProductId());
		params.put("branchSerialNumber", productBranch.getBranchSerialNumber());
		ProdProductBranch nextProductBranch = this.prodProductBranchService.getNextProductBranch(params);
		
		//后一个branch为null返回页面
		if (nextProductBranch != null) {
			// 修改前一个branch
			params.clear();
			params.put("prodBranchId", nextProductBranch.getProdBranchId());
			params.put("branchSerialNumber",productBranch.getBranchSerialNumber());
			this.prodProductBranchService.updateProductBranchSerialNumber(params);

			// 修改本branch
			params.clear();
			params.put("prodBranchId", productBranch.getProdBranchId());
			params.put("branchSerialNumber",nextProductBranch.getBranchSerialNumber());
			this.prodProductBranchService.updateProductBranchSerialNumber(params);
		}
		productMessageProducer.sendMsg(MessageFactory.newProductUpdateEbkMessage(productBranch.getProductId()));
		return "toBranch";
	}
	
	/**
	 * 上传小图
	 */
	@Action(value="/prod/uploadIcon")
	public void uploadHotelIcon(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(prodBranchId,"类别不存在");
			
			if(file==null||StringUtils.isEmpty(fileContentType)||StringUtils.isEmpty(fileFileName)){
				throw new Exception("上传内容为空");
			}
			
			UploadCtrl uc=new UploadCtrl();
			if(uc.checkImgSize(file, 50)){
				throw new Exception("图片大小需要小于50K");
			}
			
			uc.processImg(file);
			String filename=uc.postToRemote(file, fileFileName);
			ResultHandle handle = prodProductBranchService.changeIcon(prodBranchId,filename);
			if(handle.isSuccess()){
				result.put("image", filename);
			}else{
				result.raise(handle.getMsg());
			}
		}catch(Exception ex){
			result.raise(ex);
		}		 
		result.output(getResponse());
	}
	
	
	/**
	 * 设置默认值
	 */
	@Action("/prod/changeDefBranch")
	public void changeDefBranch(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(prodBranchId,"类别信息不存在");
			
			ResultHandle handle = prodProductBranchService.changeDef(prodBranchId,getOperatorNameAndCheck());
			ProdProductBranch productBranch = this.prodProductBranchService.selectProdProductBranchByPK(prodBranchId);
			if(handle.isSuccess()){
				sendChangeItemMsg(prodBranchId,productBranch.getProductId());
			}else{
				result.raise(handle.getMsg());
			}
		}catch(Exception ex){
			result.raise(ex);			
		}
		result.output(getResponse());
	}
	
	
	@Action("/prod/changeOnlineBranch")
	public void changeOnlineBranch(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(prodBranchId,"类型信息不存在");
			//如果当前产品已经是上线状态,做类别上线操作前需校验被打包的采购产品的供应商合同是否已审核 add by shihui
			ProdProduct prod = prodProductService.selectProductByProdBranchId(prodBranchId);
			if(prod.isOnLine()) {
				ProdProductBranch branch = prodProductBranchService.selectProdProductBranchByPK(prodBranchId);
				if(!branch.hasOnline()) {
					ResultHandle handler = prodProductBranchService.checkMetaSupplierContractStatus(prodBranchId);
					if(handler.isFail()) {
						throw new Exception(handler.getMsg());
					}
				}
			}
			
			ResultHandle handle = prodProductBranchService.changeOnline(prodBranchId,getOperatorNameAndCheck());
			if(handle.isSuccess()){
				branch=prodProductBranchService.selectProdProductBranchByPK(prodBranchId);
				if(branch != null) {
					productMessageProducer.sendMsg(MessageFactory.newProductBranchOnOffLineMessage(branch.getProductId(), branch.getProdBranchId()));
					
					productMessageProducer.sendMsg(MessageFactory.newProductUpdateEbkMessage(branch.getProductId()));
				}
				/** 在类别上线时如果该类别是附加类别则向附加产品表中增加一条数据,在类别下线时如果该类别是附加类别则删除附加产品表中的对应记录  zx 2012-03-05*/
				if(branch.hasOnline() && branch.hasAdditional()){//表示此次做的是附加类别上线操作
					prodProductRelationService.addRelation(branchProductId,branch,getOperatorNameAndCheck());
				}else if(!branch.hasOnline() && branch.hasAdditional()){//表示此次做的是附加类别下线操作
					ProdProductRelation relationProduct = prodProductRelationService.getProdRelation(branchProductId, branch.getProdBranchId());
					if(relationProduct != null)
						prodProductRelationService.deleteRelation(relationProduct.getRelationId(),getOperatorNameAndCheck());
				}
				/***/
				
				result.put("online", branch.hasOnline());
			}else{
				result.raise(handle.getMsg());
			}
		}catch(Exception ex){
			result.raise(new JSONResultException(ex.getMessage()));
		}
		result.output(getResponse());
	}
	
	@Action("/prod/getProdBranchListJSON")
	public void getBranchListJSON(){
		JSONResult result=new JSONResult();
		try{
			productBranchList = prodProductBranchService.getProductBranchByProductId(productId,null,"true");
			boolean findFlag=false;
			if(CollectionUtils.isNotEmpty(productBranchList)){
				findFlag=true;
				JSONArray array=new JSONArray();
				for(ProdProductBranch b:productBranchList){
					JSONObject obj=new JSONObject();
					obj.put("branchId", b.getProdBranchId());
					obj.put("branchName", b.getBranchName());
					array.add(obj);
				}
				result.put("list", array);
			}
			
			result.put("find", findFlag);
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	@Action("/prod/getBranch")
	public void branchDetail(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(prodBranchId,"类型信息不存在");
			branch=prodProductBranchService.selectProdProductBranchByPK(prodBranchId);
			if(branch==null){
				throw new Exception("类型不存在");
			}
			
			result.put("branch",JSONObject.fromObject(branch));
		}catch(Exception ex){
			result.raise(new JSONResultException(ex.getMessage()));
		}		
		result.output(getResponse());
	}

    @Action("/prod/getBranchPackAuditingShow")
    public String getBranchPackAuditingShow() {
        getBranchPack();
        return "toBranchItemAuditingShow";
    }

    /**
	 * 读取一个类别的打包类弄表.
	 * @return
	 */
	@Action("/prod/getBranchPack")
	public String getBranchPack(){
		branchItemList = prodProductBranchService
				.selectBranchItemByBranchId(prodBranchId);
				
		if(CollectionUtils.isNotEmpty(branchItemList)){
			//初始化内部的数据
			for(ProdProductBranchItem item:branchItemList){
				init(item);
			}
		}
		
		return "toBranchItem";
	}
	
	private ProdProductBranchItem init(ProdProductBranchItem item){
		item.setMetaBranch(metaProductBranchService.getMetaBranch(item.getMetaBranchId()));
		if(item.getMetaBranch().getMetaProductId()!=null){
			item.setMetaProduct(metaProductService.getMetaProduct(item.getMetaBranch().getMetaProductId()));
		}
		return item;
	}
	private PlaceFlightService placeFlightService;
	private ProdProductPlaceService prodProductPlaceService;
	/**
	 * 添加一个打包的采购产品类别，并且返回的json数据当中需要返回item.
	 */
	@Action("/prod/addBranchItem")
	public void addBranchItem(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(branchItem.getMetaBranchId(),"采购产品类别信息不存在");
			Assert.notNull(branchItem.getQuantity(),"数量不可以为空");
			Assert.isTrue(branchItem.getQuantity()>0,"数量不可以小于1");
			Direction d=Direction.None;
			if(StringUtils.equals("true", direction)){
				d=Direction.TRUE;
			}else if(StringUtils.equals("false", direction)){
				d=Direction.FALSE;
			}
			
			ProdProductBranch branch = this.prodProductBranchService.selectProdProductBranchByPK(branchItem.getProdBranchId());
			//检查是否可以添加
			branchItem = this.checkAddItem(branchItem,branch, d ,getOperatorNameAndCheck());
			ProdProductBranchItem res=prodProductBranchService.addItem(branchItem,branch,this.getSessionUser().getUserName());
			
			updateTodayOrderAble(branch);
			
			if(needUpdateTraffic){
				prodProductService.updateTraffic(branch.getProductId(),res.getMetaProductId());
				MetaProductTraffic mpt=(MetaProductTraffic)needAddMetaProduct;
				PlaceFlight pf=placeFlightService.queryPlaceFlight(mpt.getGoFlight());
				if(pf!=null){
					prodProductPlaceService.insertOrUpdateTrafficPlace(branch.getProductId(),pf.getStartPlaceId(),pf.getArrivePlaceId());
				}
			}
			sendMetaBranchChangeMsg(branchItem.getProdBranchId(), branch.getProductId());
			
			//sendChangeItemMsg(branchItem.getProdBranchId());
			res=init(res);
			JsonConfig js=new JsonConfig();
			js.setExcludes(new String[]{"metaBranch","metaProduct"});
			JSONObject rootjs=JSONObject.fromObject(res, js);
			JSONObject mpObj=new JSONObject();
			try{
				mpObj.put("productName", res.getMetaProduct().getProductName());
				mpObj.put("metaProductId", res.getMetaProduct().getMetaProductId());
			}catch(NullPointerException nullex){
				
			}
			rootjs.put("metaProduct", mpObj);
			
			JSONObject mbObj=new JSONObject();
			try{
				mbObj.put("branchName", res.getMetaBranch().getBranchName());
				mbObj.put("metaBranchId",res.getMetaBranch().getMetaBranchId());				
				mbObj.put("adultQuantity", res.getMetaBranch().getAdultQuantity());
				mbObj.put("childQuantity", res.getMetaBranch().getChildQuantity());
			}catch(NullPointerException nullex){
				
			}
			rootjs.put("metaBranch", mbObj);
			result.put("item", rootjs);
			
		}catch(TimePriceException ex){//需要咨询用户是否需要删除多余的数据
			result.raise(new JSONResultException(2, "是否要删除多余的时间价格表."));
		}catch(Exception ex){
			result.raise(ex);
		}
		
		result.output(getResponse());
	}
	
	
	private ProdProductBranchItem checkAddItem(ProdProductBranchItem item,ProdProductBranch branch,
			Direction direction,
			String operatorName) throws BranchItemException,TimePriceException {
		
		Assert.notNull(branch, "销售产品类别不存在");
		MetaProductBranch metaBranch = this.metaProductBranchService.getMetaBranch(item.getMetaBranchId());
		Assert.notNull(metaBranch, "采购产品类别不存在");
		Assert.isTrue(metaBranch.hasValid(), "采购类别不可以添加");
		
		//正常产品和不定期产品不能交叉绑定
		boolean prodIsAperiodic = prodProductService.getProdProduct(branch.getProductId()).IsAperiodic();
		boolean metaIsAperiodic = metaProductService.getMetaProductByBranchId(item.getMetaBranchId()).IsAperiodic();
		if(prodIsAperiodic) {
			if(metaIsAperiodic){
				//不定期采购只能打包一份
				/*if(item.getQuantity() > 1) {
					throw new BranchItemException("不定期产品打包数量不能大于1"); 
				}*/
			} else {
				throw new BranchItemException("不定期销售产品不能打包正常采购产品"); 
			}
		} else {
			if(metaIsAperiodic){
				throw new BranchItemException("正常销售产品不能打包不定期采购产品"); 
			}
		}

		// 判断本产品是否已经存在
		if (existsProductBranchItem(item.getMetaBranchId(), item
				.getProdBranchId())) {
			throw new BranchItemException("该产品类别存在，不能重复添加");
		}
		
		//在该产品已在线和该类别已在线的状态下,校验打包的采购产品对应的供应商的合同审核状态
		ProdProduct prod = prodProductService.getProdProduct(branch.getProductId());
		if(prod.isOnLine()) {
			if(branch.hasOnline()) {
				ResultHandle handle = prodProductBranchService.checkBranchItemSupplierContractStatus(branchItem.getMetaBranchId());
				if(handle.isFail()) {
					throw new BranchItemException(handle.getMsg());
				}
			}
		}
		
		List<SupSettlementTarget> supSettlementTargets = settlementTargetService.getSuperSupSettlementTargetByMetaProductId(metaBranch.getMetaProductId());
		if (supSettlementTargets == null || supSettlementTargets.size() < 1) {
			throw new BranchItemException("该采购产品无\"结算对象\"!");
		}
		
		List<SupPerformTarget> metaPerformList = this.performTargetService.findSuperSupPerformTargetByMetaProductId(metaBranch.getMetaProductId());
		if (metaPerformList == null || metaPerformList.size() < 1) {
			throw new BranchItemException("该采购产品无\"履行对象\"!");
		}

		List<SupBCertificateTarget> supBCertificateTarget = this.bCertificateTargetService.selectSuperMetaBCertificateByMetaProductId(metaBranch
				.getMetaProductId());
		if (supBCertificateTarget == null || supBCertificateTarget.size() < 1) {
			throw new BranchItemException("该采购产品无\"凭证对象\"!");
		}

		
		
		needAddMetaProduct = this.metaProductService.getMetaProductByMetaProductId(metaBranch.getMetaProductId());
		if(needAddMetaProduct.isPaymentToSupplier()){
			if(item.getQuantity()>1){
				throw new BranchItemException("对于支付给供应商的采购产品，打包数量不能大于1");
			}
			List<ProdProductBranchItem> list=prodProductBranchService.selectBranchItemByBranchId(item.getProdBranchId());
			if(CollectionUtils.isNotEmpty(list)){
				throw new BranchItemException("对于支付给供应商的产品，只能打包一个");
			}
			
		}
		
		boolean payToLvmama = false;
		boolean payToSupplier= false;
		List<MetaProduct> metas = this.metaProductService.getMetaProductByProductId(branch.getProductId());
		for (MetaProduct metaProduct : metas) {			
			if (metaProduct.isPaymentToLvmama()) {
				payToLvmama = true;
			}else if(metaProduct.isPaymentToSupplier()){
				payToSupplier = true;
			}
		}
		if ((needAddMetaProduct.isPaymentToSupplier() && payToLvmama)||(needAddMetaProduct.isPaymentToLvmama()&&payToSupplier)) {
			throw new BranchItemException("只能打包同一种支付方式的产品！");			
		}
		ProdProduct product=this.prodProductService.getProdProduct(branch.getProductId());
		
		if(product.isFlight()&&needAddMetaProduct.isFlight()){
			ProdTraffic pt=(ProdTraffic)product;
			MetaProductTraffic mpt=(MetaProductTraffic)needAddMetaProduct;
			long len=prodProductService.selectProdProductBranchItemCount(product.getProductId());
			if(len>0){
				if(pt.getGoFlightId()!=mpt.getGoFlight()||
						!StringUtils.equals(pt.getDirection(),mpt.getDirection())||
						pt.getBackFlightId()!=mpt.getBackFlight()||
						pt.getDays()!=mpt.getDays()){
					throw new BranchItemException("机票产品打包的只能是同一种航班、同样天数的采购产品");
				}
			}else{
				needUpdateTraffic=true;
			}
		}
		
		Date today=DateUtil.getDayStart(new Date());
		
		
		Date tomorrow=DateUtils.addDays(today, 1);
		Date nextTime=DateUtils.addMonths(tomorrow, 6);
		boolean flag = this.prodProductBranchService.selectIntersectionMetaProduct(
				item.getProdBranchId(), tomorrow.getTime(),
				nextTime.getTime(), item
						.getMetaBranchId());
		if (!flag) {
			throw new BranchItemException("打包的产品与现有产品在“6”月内时间价格表无交集");			
		}
		
		//如果是None或TRUE时读取时间价格表.
		if(direction==Direction.None||direction==Direction.TRUE){
			List<Date> dates=this.prodProductBranchService.checkTimePriceContain(today, item.getProdBranchId(), item.getMetaBranchId());
			
			if(CollectionUtils.isNotEmpty(dates)){//存在时间价格
				if(direction==Direction.None){
						throw new TimePriceException();
					
				}else if(direction==Direction.TRUE){//在该值的情况下删除多余的时间价格表.
					this.prodProductBranchService.deleteTimePrice(dates,item.getProdBranchId(), operatorName);
				}
			}
		}
		 
		productMessageProducer.sendMsg(MessageFactory.newProductUpdateEbkMessage(branch.getProductId()));
		return item;
	}
	private boolean needUpdateTraffic=false;
	
	/**
	 * 判断一个打包是否存在
	 * 
	 * @param metaBranchId
	 *            采购类别
	 * @param prodBranchId
	 *            销售类别
	 * @return
	 */
	public boolean existsProductBranchItem(Long metaBranchId, Long prodBranchId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("metaBranchId", metaBranchId);
		map.put("prodBranchId", prodBranchId);

		return CollectionUtils.isNotEmpty(this.prodProductBranchService.selectItemListByParam(map));
	}

	public void setProdProductBranchService(
			ProdProductBranchService prodProductBranchService) {
		this.prodProductBranchService = prodProductBranchService;
	}

	public List<ProdProductBranch> getProductBranchList() {
		return productBranchList;
	}

	


	/**
	 * @param branch the branch to set
	 */
	public void setBranch(ProdProductBranch branch) {
		this.branch = branch;
	}
	
	

	/**
	 * @return the branch
	 */
	public ProdProductBranch getBranch() {
		return branch;
	}

	/**
	 * 保存一个销售产品类别.
	 */
	@Override
	@Action("/prod/editProductBranch")
	public void save() {
		JSONResult result = new JSONResult();
		try {
			Assert.notNull(branch.getProductId(), "产品信息不存在");
			Assert.hasLength(branch.getBranchName(),"类别名称不可以为空");
			if(StringUtil.hasIllegalCharacter(branch.getBranchName())){
				throw new Exception("类别名称不可包含'<','>','&'");
			}
			if(branch.getAdultQuantity()==null&&branch.getChildQuantity()==null){
				throw new Exception("成人数儿童数不可以全部为空");
			}
			long quantity=0;
			if(branch.getAdultQuantity()!=null){
				quantity+=branch.getAdultQuantity();
			}else{
				branch.setAdultQuantity(0L);
			}
			if(branch.getChildQuantity()!=null){
				quantity+=branch.getChildQuantity();
			}else{
				branch.setChildQuantity(0L);
			}
			
			if(quantity==0){
				throw new Exception("成人数+儿童数必须大于0.");
			}		
			
			if(branch.getMinimum()==null||branch.getMinimum()<0){
				throw new Exception("最小起订时不可以小于0");
			}
			if(branch.getMaximum()==null||branch.getMaximum()<1){
				throw new Exception("最大起订单不可以小于1");
			}
			if(branch.getMinimum()>branch.getMaximum()){
				throw new Exception("最小量不可以大于最大起订量");
			}
			product=prodProductService.getProdProduct(branch.getProductId());
			if(product==null){
				throw new Exception("产品不存在");
			}
			if(product.isTraffic()){
				if(quantity>1){
					throw new Exception("成人数+儿童数必须等于1.");
				}
			}
			if(branch.getProdBranchId()!=null){//编辑的操作时需要对数据复制
				ProdProductBranch ppbEntity=prodProductBranchService.selectProdProductBranchByPK(branch.getProdBranchId());
				if(ppbEntity==null){
					throw new Exception("更新的类别不存在");
				}
				if(ppbEntity.hasDefault()){
					if(!branch.hasVisible() && !Constant.PRODUCT_TYPE.OTHER.name().equals(product.getProductType())){
						throw new Exception("该类别是默认类别，是否前台显示属性不能为否");
					}					
					if(branch.hasAdditional()){
						throw new Exception("该类别是默认类别，是否附加属性不能为是");
					}				
				}
				branch=CopyUtil.copy(ppbEntity, branch, getRequest().getParameterNames(), "branch.");
			}
			
			if (product.hasSelfPack()) {
				List<ProdProductBranch> list = prodProductBranchService
						.getProductBranchByProductId(product.getProductId(),null,null);
				if (CollectionUtils.isNotEmpty(list)) {
					if (branch.getProdBranchId() == null) {
						if (list.size() >= BRANCH_COUNT) {
							throw new Exception("超级自由行类别只能创建" + BRANCH_COUNT
									+ "个");
						}
					} else {
							ProdProductBranch branchEntity = (ProdProductBranch) CollectionUtils
									.find(list, new Predicate() {

										@Override
									public boolean evaluate(Object arg0) {
										ProdProductBranch ppb = (ProdProductBranch) arg0;
										return StringUtils.equals(ppb.getBranchType(), branch.getBranchType())
												&& !branch.getProdBranchId().equals(ppb.getProdBranchId());
									}

									});

							if (branchEntity != null
									&& !branchEntity.getProdBranchId().equals(
											branch.getProdBranchId())) {
								throw new Exception("超级自由行一个类别类型只能创建1个");
							}
						}
					}
			}
			branch.setProdProduct(product);
			ResultHandleT<ProdProductBranch> handle=prodProductBranchService.saveBranch(branch,getOperatorNameAndCheck());
			
			if(handle.isSuccess()){		
				//读取详细的数据给客户端 
				branch=prodProductBranchService.selectProdProductBranchByPK(handle.getReturnContent().getProdBranchId());
				
				if(!Constant.PRODUCT_TYPE.OTHER.name().equals(product.getProductType())){
					/** 保存或修改类别时如果是上线的并且是附加的类别，此时向附加产品表中增加记录 zx 2012-03-05 start*/
					ProdProductRelation relationProduct = prodProductRelationService.getProdRelation(handle.getReturnContent().getProductId(), handle.getReturnContent().getProdBranchId());
					if(branch.hasAdditional() && branch.hasOnline()){
						if(relationProduct == null)
							prodProductRelationService.addRelation(handle.getReturnContent().getProductId(),handle.getReturnContent(),getOperatorNameAndCheck());
					}else{
						if(relationProduct != null)
							prodProductRelationService.deleteRelation(relationProduct.getRelationId(),getOperatorNameAndCheck());
					}
					/** end*/
				}
				
				prodProductService.markProductSensitive(branch.getProductId(), hasSensitiveWord);
				//发送产品类别修改消息
				productMessageProducer.sendMsg(MessageFactory.newProductBranchEditMessage(branch.getProdBranchId()));
				
				productMessageProducer.sendMsg(MessageFactory.newProductUpdateEbkMessage(branch.getProductId()));
				
			}else{
				result.raise(handle.getMsg());
			}
			
		} catch (Exception ex) {
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	
	@Action("/prod/deleteBranchItem")
	public void deleteBranchItem(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(branchItemId,"操作的对象不存在");
			ProdProductBranchItem item=prodProductBranchService.selectBranchItemByPK(branchItemId);
			Assert.notNull(item,"打包项不存在");
			ResultHandle handle = prodProductBranchService.deleteItem(branchItemId,getOperatorNameAndCheck());
			
			Long prodBranchId = item.getProdBranchId();
			ProdProductBranch branch = prodProductBranchService.selectProdProductBranchByPK(prodBranchId);
			updateTodayOrderAble(branch);
			if(handle.isSuccess()){
				sendMetaBranchChangeMsg(item.getProdBranchId(), branch.getProductId());
			}else{
				result.raise(handle.getMsg());
			}
			//sendChangeItemMsg(item.getProdBranchId());
		}catch(Exception ex){
			result.raise(ex);
		}
		
		result.output(getResponse());
	}
	
	//更新门票是否当天可预订
	public void updateTodayOrderAble(ProdProductBranch branch) {
		if(branch != null) {
			ProdProduct product = prodProductService.getProdProduct(branch.getProductId());
			if(product != null) {
				if(product.isTicket()) {
					String todayOrderAble = "true";
					//查找销售类别关联的采购类别
					List<MetaProductBranch> metaProductBranchList = metaProductBranchService.getMetaProductBranchByProdBranchId(branch.getProdBranchId());
					if(!metaProductBranchList.isEmpty()) {
						for (int i = 0; i < metaProductBranchList.size(); i++) {
							Long metaId = metaProductBranchList.get(i).getMetaProductId();
							MetaProduct metaProduct = metaProductService.getMetaProduct(metaId, Constant.PRODUCT_TYPE.TICKET.name());
							if(metaProduct != null) {
								MetaProductTicket mpt = (MetaProductTicket)metaProduct;
								if(!mpt.hasTodayOrderAble()) {
									todayOrderAble = "false";
									break;
								}
							} else {
								todayOrderAble = "false";
								break;
							}
						}
					} else {
						todayOrderAble = "false";
					}
					branch.setTodayOrderAble(todayOrderAble);
					prodProductBranchService.updateByPrimaryKeySelective(branch);
				}
			}
		}
	}
	
	@Action("/prod/deleteBranch")
	public void deleteBranch(){
		JSONResult result=new JSONResult();
		try{
			Assert.notNull(prodBranchId,"操作的对象不存在");
			ResultHandle handle=prodProductBranchService.deleteBranchByLogic(prodBranchId, getOperatorNameAndCheck());
			
			prodProductService.markProductSensitive(productId, null);
			if(handle.isFail()){
				result.raise(handle.getMsg());
			}
		}catch(Exception ex){
			result.raise(ex);
		}
		result.output(getResponse());
	}
	
	/**
	 * @param prodBranchId the prodBranchId to set
	 */
	public void setProdBranchId(Long prodBranchId) {
		this.prodBranchId = prodBranchId;
	}

	/**
	 * @return the prodBranchId
	 */
	public Long getProdBranchId() {
		return prodBranchId;
	}

	public List<CodeItem> getBranchCodeSetList(){		
		if(product!=null&&product.hasSelfPack()){
			return CodeSet.getInstance().getCodeListAndBlank(ProductUtil.getBranchSetByType("SELF_PACK"));
		}else if(product.isTraffic()&&Constant.SUB_PRODUCT_TYPE.TRAIN.name().equals(product.getSubProductType())){
			return ProductUtil.getTrainBranchTypeList(true);
		}else{
			return CodeSet.getInstance().getCodeListAndBlank(ProductUtil.getBranchSetByType(productType));
		}
	}
	
	public List<CodeItem> getBranch_1CodeSetList(){		
		if(product.isTraffic()&&Constant.SUB_PRODUCT_TYPE.TRAIN.name().equals(product.getSubProductType())){
			ProdTraffic traffic = (ProdTraffic)product;
			List<LineStationStation> list= prodTrainService.selectStationStationByLineInfo(traffic.getLineInfoId());
			List<CodeItem> result = new ArrayList<CodeItem>();
			result.add(new CodeItem("","请选择"));
			for(LineStationStation lss:list){
				LineStationStation lss2=prodTrainService.getStationStationDetailById(lss.getStationStationId());
				result.add(new CodeItem(String.valueOf(lss2.getStationStationId()),lss2.getDepartureStation().getStationName()+"-"+lss2.getArrivalStation().getStationName()));
			}
			return result;
		}else{
			return CodeSet.getInstance().getCodeListAndBlank(ProductUtil.getBranchSetByType(productType) + "_1");
		}
	}

	/**
	 * @param metaProductBranchService the metaProductBranchService to set
	 */
	public void setMetaProductBranchService(
			MetaProductBranchService metaProductBranchService) {
		this.metaProductBranchService = metaProductBranchService;
	}

	/**
	 * @return the branchItem
	 */
	public ProdProductBranchItem getBranchItem() {
		return branchItem;
	}

	/**
	 * @param branchItem the branchItem to set
	 */
	public void setBranchItem(ProdProductBranchItem branchItem) {
		this.branchItem = branchItem;
	}

	

	/**
	 * @return the branchItemList
	 */
	public List<ProdProductBranchItem> getBranchItemList() {
		return branchItemList;
	}

	/**
	 * @param branchItemId the branchItemId to set
	 */
	public void setBranchItemId(Long branchItemId) {
		this.branchItemId = branchItemId;
	}

	/**
	 * @param metaProductService the metaProductService to set
	 */
	public void setMetaProductService(MetaProductService metaProductService) {
		this.metaProductService = metaProductService;
	}
	

	/**
	 * 发送变更销售价的消息.
	 * @param prodBranchId
	 */
	private void sendChangeItemMsg(final Long prodBranchId,final Long productId){
		productMessageProducer.sendMsg(MessageFactory
				.newProductSellPriceMessage(prodBranchId));
		
		productMessageProducer.sendMsg(MessageFactory.newProductUpdateEbkMessage(productId));
	}
	
	/**
	 * 发送类别打包消息,删除，添加都需要发送该消息
	 * @param prodBranchId
	 */
	private void sendMetaBranchChangeMsg(final Long prodBranchId, final Long productId){
		productMessageProducer.sendMsg(MessageFactory
				.newProductBranchItemChangeMessage(prodBranchId));
		
		productMessageProducer.sendMsg(MessageFactory.newProductUpdateEbkMessage(productId));
	}

	public List<CodeItem> getBreakfastList(){
		return CodeSet.getInstance().getCodeList(Constant.CODE_TYPE.BREAKFAST_TYPE.name());
	}
	
	public List<CodeItem> getBroadbandList(){
		return CodeSet.getInstance().getCodeList(Constant.CODE_TYPE.BROADBAND_TYPE.name());
	}

	/**
	 * @param file the file to set
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * @param fileContentType the fileContentType to set
	 */
	public void setFileContentType(String fileContentType) {
		this.fileContentType = fileContentType;
	}

	/**
	 * @param fileFileName the fileFileName to set
	 */
	public void setFileFileName(String fileFileName) {
		this.fileFileName = fileFileName;
	}

	/**
	 * @param direction the direction to set
	 */
	public void setDirection(String direction) {
		this.direction = direction;
	}

	public Long getBranchProductId() {
		return branchProductId;
	}

	public void setBranchProductId(Long branchProductId) {
		this.branchProductId = branchProductId;
	}

	public ProdProductRelationService getProdProductRelationService() {
		return prodProductRelationService;
	}

	public void setProdProductRelationService(
			ProdProductRelationService prodProductRelationService) {
		this.prodProductRelationService = prodProductRelationService;
	}
	public void setSettlementTargetService(
			SettlementTargetService settlementTargetService) {
		this.settlementTargetService = settlementTargetService;
	}

	public void setPerformTargetService(PerformTargetService performTargetService) {
		this.performTargetService = performTargetService;
	}

	public void setbCertificateTargetService(
			BCertificateTargetService bCertificateTargetService) {
		this.bCertificateTargetService = bCertificateTargetService;
	}

	public void setPlaceFlightService(PlaceFlightService placeFlightService) {
		this.placeFlightService = placeFlightService;
	}

	public void setProdProductPlaceService(
			ProdProductPlaceService prodProductPlaceService) {
		this.prodProductPlaceService = prodProductPlaceService;
	}

	public void setProdTrainService(ProdTrainService prodTrainService) {
		this.prodTrainService = prodTrainService;
	}

}
