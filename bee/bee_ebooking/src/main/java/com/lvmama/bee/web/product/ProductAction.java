package com.lvmama.bee.web.product;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang3.StringUtils;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Result;
import org.apache.struts2.convention.annotation.Results;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.lvmama.comm.bee.po.ebooking.EbkProdBranch;
import com.lvmama.comm.bee.po.ebooking.EbkProdProduct;
import com.lvmama.comm.bee.po.prod.ProdProduct;
import com.lvmama.comm.bee.service.ebooking.EbkProdAuditService;
import com.lvmama.comm.bee.service.ebooking.EbkProdBranchService;
import com.lvmama.comm.bee.service.ebooking.EbkProdImportService;
import com.lvmama.comm.bee.service.ebooking.EbkProdSnapshotService;
import com.lvmama.comm.bee.service.prod.ProdProductService;
import com.lvmama.comm.bee.vo.EbkProdProductModel;
import com.lvmama.comm.pet.po.perm.PermUser;
import com.lvmama.comm.pet.po.pub.ComMessage;
import com.lvmama.comm.pet.po.sup.SupSupplier;
import com.lvmama.comm.pet.service.perm.PermUserService;
import com.lvmama.comm.pet.service.pub.ComMessageService;
import com.lvmama.comm.pet.service.sup.SupplierService;
import com.lvmama.comm.utils.MemcachedUtil;
import com.lvmama.comm.utils.StringUtil;
import com.lvmama.comm.utils.json.JSONOutput;
import com.lvmama.comm.vo.Constant;
import com.lvmama.comm.vo.Constant.EBK_PRODUCT_AUDIT_STATUS;
import com.lvmama.comm.vo.Constant.EBK_PRODUCT_VIEW_TYPE;
import com.lvmama.comm.vo.Constant.SUB_PRODUCT_TYPE;

@Results({ @Result(name = "queryProduct", location = "/WEB-INF/pages/ebooking/product/queryProduct.jsp")
})
public class ProductAction extends BaseEbkProductAction {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8516430543238905015L;
	@Autowired
	private ProdProductService prodProductService;
	@Autowired
	private PermUserService permUserService;
	@Autowired
	private ComMessageService comMessageService;
	@Autowired
	private SupplierService supplierService;
	@Autowired
	private EbkProdSnapshotService ebkProdSnapshotService;
	@Autowired
	private EbkProdAuditService ebkProdAuditService;
	@Autowired
	private EbkProdImportService ebkProdImportService;
	@Autowired
	private EbkProdBranchService ebkProdBranchService;
	
	private EbkProdProduct ebkProdProduct = new EbkProdProduct();
	private Map<String,Object> parameters = new HashMap<String,Object>();
	private SUB_PRODUCT_TYPE[] subProductTypes;
	private EBK_PRODUCT_AUDIT_STATUS[] ebkProductAuditStatus = Constant.EBK_PRODUCT_AUDIT_STATUS.values();
	private String auditStatus;
	private String subProductType;
	
	@Action(value="/product/query")
	public String query(){
		subProductTypes = Constant.EBK_PRODUCT_VIEW_TYPE.getSubProductTypes(ebkProductViewType);
		return "queryProduct";
	}
	
	/**
	 * 根据供应商ID,产品子类型查询审核产品全部总数
	 */
	@Action(value="/product/queryAllCount")
	public void queryAllCount(){
		JSONObject json=new JSONObject();
		Long supplierId =getCurrentSupplierId();
		if(null==supplierId || null==ebkProductViewType || null==Constant.EBK_PRODUCT_VIEW_TYPE.getSubProductTypeCodes(ebkProductViewType)){
			json.put("success", Boolean.FALSE);
			JSONOutput.writeJSON(getResponse(), json);
		}else{
			Map<String,Object> allCount = ebkProdProductService.queryAllCount(supplierId,Constant.EBK_PRODUCT_VIEW_TYPE.getSubProductTypeCodes(ebkProductViewType));
			json.put("success", Boolean.TRUE);
			json.put("message", allCount);
			JSONOutput.writeJSON(getResponse(), json);
		}
	}
	/**
	 * 查询全部产品显示
	 * @return
	 */
	@Action(value="/product/queryProduct")
	public String queryProduct(){
		init(null);
		return "queryProduct";
	}
	/**
	 * 查询未提交产品显示
	 * @return
	 */
	@Action(value="/product/query/queryUnCommit")
	public String queryUnCommit(){
		init(EBK_PRODUCT_AUDIT_STATUS.UNCOMMIT_AUDIT.name());
		return "queryProduct";
	}
	/**
	 * 查询待审核产品显示
	 * @return
	 */
	@Action(value="/product/query/queryPending")
	public String queryPending(){
		init(EBK_PRODUCT_AUDIT_STATUS.PENDING_AUDIT.name());
		return "queryProduct";
	}
	/**
	 * 查询审核通过产品显示
	 * @return
	 */
	@Action(value="/product/query/queryThrough")
	public String queryThrough(){
		init(EBK_PRODUCT_AUDIT_STATUS.THROUGH_AUDIT.name());
		return "queryProduct";
	}
	/**
	 * 查询审核不通过产品显示
	 * @return
	 */
	@Action(value="/product/query/queryRejected")
	public String queryRejected(){
		init(EBK_PRODUCT_AUDIT_STATUS.REJECTED_AUDIT.name());
		return "queryProduct";
	}
	
	/**
	 * 根据EBK产品ID提交审核
	 */
	@Action(value="/product/audit/auditCommit")
	public void auditCommit(){
		if(!isSupplierEbkProductJson()){
			return;
		}
		JSONObject json=new JSONObject();
		try{
			
			EbkProdBranch branch = new EbkProdBranch();
			branch.setProdProductId(ebkProdProductId);
			List<EbkProdBranch> branchs  = ebkProdBranchService.query(branch);
			if(branchs!=null&&branchs.size()>0){
				for(EbkProdBranch bch : branchs){
					if(Constant.ROUTE_BRANCH.VIRTUAL.name().equalsIgnoreCase(bch.getBranchType())){
						if(StringUtils.isBlank(bch.getVirtualBranchIds())){
							json.put("success", Boolean.FALSE);
							json.put("message", -555);
							JSONOutput.writeJSON(getResponse(), json);
							return;
						}
					}
				}
			}
			
			EbkProdProduct product = ebkProdProductService.findEbkProdAllByPrimaryKey(ebkProdProductId);
			ebkProdSnapshotService.saveProdSnapshot(product);
			boolean isNeedAudit=ebkProdAuditService.isNeedAudit(ebkProdProductId);
			if(isNeedAudit){//提交审核
				ebkProdProductService.auditCommit(ebkProdProductId,getSessionUser().getUserId());
			}else{//免审
				ebkProdAuditService.auditPassBySystem(ebkProdProductId,getSessionUser().getUserId());
			}
			PermUser manage  = permUserService.getPermUserByUserId(product.getManagerId());
			Long supplierId = getCurrentSupplierId();
			SupSupplier supplier = supplierService.getSupplier(supplierId);
			json.put("success", Boolean.TRUE);
			ComMessage comMessage = new ComMessage();
			comMessage.setCreateTime(new Date());
			if(null!=manage){
				comMessage.setReceiver(manage.getUserName());
			}else{
				comMessage.setReceiver("SYSTEM");
			}
			comMessage.setSender(super.getSessionUserName());
			comMessage.setStatus("CREATE");
			if(isNeedAudit){
				comMessage.setContent(supplier.getSupplierName()+"供应商 "+super.getSessionUserName()+" 编辑EBK产品(EBK产品编码:"+ebkProdProductId+")成功，申请审核");
			}else{
				comMessage.setContent(supplier.getSupplierName()+"供应商 "+super.getSessionUserName()+" 编辑EBK产品(EBK产品编码:"+ebkProdProductId+")成功，免审核，直接通过");
			}
			comMessageService.insertComMessage(comMessage);
			if(isNeedAudit){
				comLogService.insert("EBK_PROD_PRODUCT",ebkProdProductId, ebkProdProductId, super.getSessionUserName(), "auditCommitEbkProdProduct","提交审核", super.getSessionUserName()+"将"+product.getMetaName()+"产品提交审核", "EBK_PROD_PRODUCT");
			}else{
				comLogService.insert("EBK_PROD_PRODUCT",ebkProdProductId, ebkProdProductId, super.getSessionUserName(), "auditCommitEbkProdProduct","提交审核", super.getSessionUserName()+"将"+product.getMetaName()+"产品提交审核,免审直接通过", "EBK_PROD_PRODUCT");
			}
		}catch(Exception e){
			json.put("success", Boolean.FALSE);
			json.put("message", -500);
			e.printStackTrace();
		}
		JSONOutput.writeJSON(getResponse(), json);
	}
	
	/**
	 * 根据EBK产品撤消审核
	 */
	@Action(value="/product/audit/auditRevoke")
	public void auditRevoke(){
		if(!isSupplierEbkProductJson()){
			return;
		}
		JSONObject json=new JSONObject();
		try{
			int result = ebkProdProductService.auditRevoke(ebkProdProductId);
			if(result>0){
				EbkProdProduct product = ebkProdProductService.findEbkProdProductAndBaseByPrimaryKey(ebkProdProductId);
				ebkProdSnapshotService.deleteProdSnapshotByLast(ebkProdProductId);
				
				json.put("success", Boolean.TRUE);
				comLogService.insert("EBK_PROD_PRODUCT",ebkProdProductId, ebkProdProductId, super.getSessionUserName(), "auditRevokeEbkProdProduct","撤消审核", super.getSessionUserName()+"将"+product.getMetaName()+"产品撤消审核", "EBK_PROD_PRODUCT");
			}else{
				json.put("success", Boolean.FALSE);
				json.put("message", -404);
			}
		}catch(Exception e){
			json.put("success", Boolean.FALSE);
			json.put("message", -500);
			e.printStackTrace();
		}
		JSONOutput.writeJSON(getResponse(), json);
	}
	
	/**
	 * 根据EBK产品重新编辑
	 */
	@Action(value="/product/audit/auditRecover")
	public void auditRecover(){
		if(!isSupplierEbkProductJson()){
			return;
		}
		JSONObject json=new JSONObject();
		try{
			int result = ebkProdProductService.auditRecover(ebkProdProductId);
			if(result>0){
				EbkProdProduct product = ebkProdProductService.findEbkProdProductAndBaseByPrimaryKey(ebkProdProductId);
				json.put("success", Boolean.TRUE);
				comLogService.insert("EBK_PROD_PRODUCT",ebkProdProductId, ebkProdProductId, super.getSessionUserName(), "auditRecover","重新编辑", super.getSessionUserName()+"将"+product.getMetaName()+"产品重新编辑", "EBK_PROD_PRODUCT");
			}else{
				json.put("success", Boolean.FALSE);
				json.put("message", -404);
			}
		}catch(Exception e){
			json.put("success", Boolean.FALSE);
			json.put("message", -500);
			e.printStackTrace();
		}
		JSONOutput.writeJSON(getResponse(), json);
	}
	private void init(final String status){
		String[] paramKeys = {"metaName","prodName","prodProductId","onLine","status","commitTimeStart","commitTimeEnd","auditTimeStart","auditTimeEnd"};
		Map<String, Object> paraMap = getParams(paramKeys);
		if(null==paraMap){
			return;
		}
		initPage();
		paraMap.put("orderBy","UPDATE_DATE");
		paraMap.put("descOrAsc","DESC");
		paraMap.put("currentPage",pagination.getCurrentPage());
		paraMap.put("pageSize", pagination.getPageSize());
		if(null!=status){
			paraMap.put("status",status);
			this.auditStatus = status;
		}
		pagination = ebkProdProductService.queryProduct(paraMap);
		pagination.buildUrl(getRequest());
		if(null!=pagination){
			List<EbkProdProduct> result = (List<EbkProdProduct>)pagination.getItems();
			
			for(int i=0;i<result.size();i++){
				EbkProdProduct ebkProdProduct = result.get(i);
				ProdProduct prodProduct = prodProductService.getProdProductById(ebkProdProduct.getProdProductId());
				String onLine=null;
				if(null==prodProduct){
					prodProduct = new ProdProduct();
				}else{
					if(!"Y".equalsIgnoreCase(prodProduct.getValid())){
						onLine="false";
					}else{
						onLine=prodProduct.getOnLine();
					}
				}
				EbkProdProductModel model = new EbkProdProductModel();
				BeanUtils.copyProperties(ebkProdProduct, model);
				PermUser user = permUserService.getPermUserByUserId(ebkProdProduct.getManagerId());
				if(null==user){
					user = new PermUser();
				}
				model.setOfflineTime(prodProduct.getOfflineTime());
				model.setOnlineTime(prodProduct.getOnlineTime());
				model.setOnLine(onLine);
				model.setManagerName(user.getRealName());
				result.set(i, model);
			}
		}
	}
	private Map<String,Object> getParams(final String[] paramKeys){
		if(StringUtil.isEmptyString(ebkProductViewType) && StringUtil.isEmptyString(subProductType)){
			return null;
		}

		subProductTypes = Constant.EBK_PRODUCT_VIEW_TYPE.getSubProductTypes(ebkProductViewType);
		Long supplierId =getCurrentSupplierId();
		if(null==supplierId){
			return null;
		}
		parameters.put("supplierId", supplierId);
		parameters.put("subProductTypeIn", subProductTypes);
		if(!StringUtil.isEmptyString(subProductType)){
			parameters.put("subProductType", subProductType);
		}
		for(String key:paramKeys){
			setParamsIsNotNull(key);
		}
		return parameters;
	}
	private void setParamsIsNotNull(final String key){
		String value = getRequestParameter(key);
		if(StringUtils.isNotEmpty(value)){
			parameters.put(key, value);
		}
	}
	
	
	
	
	/**
	 * 导入指定类别
	 * 
	 * @throws Exception
	 */
	@Action(value = "/product/importProdByType")
	public void importProdByType() throws Exception {
		
		JSONObject json=new JSONObject();
		
		Long supplierId =getCurrentSupplierId();
		if(null==supplierId ||null==ebkProductViewType){
			json.put("success", Boolean.FALSE);
			JSONOutput.writeJSON(getResponse(), json);
			return;
		}
		
		String key="EBK_PRODUCT_IMPORT_ACTION_LOCK_"+supplierId;
		String lock=(String) MemcachedUtil.getInstance().get(key);
		if(null!=lock){
			LOG.info("supplier id:"+supplierId+",import locked it!");
			json.put("success", Boolean.FALSE);
			json.put("message", "系统导入中，请稍后");
			JSONOutput.writeJSON(getResponse(), json);
			return;
		}else{
			LOG.info("supplier id:"+supplierId+",product type:"+ebkProductViewType+",import start...");
			try{
				//锁30分钟
				MemcachedUtil.getInstance().set(key, 30*60, key);
				ebkProdImportService.importProductsByProductType(supplierId, EBK_PRODUCT_VIEW_TYPE.valueOf(ebkProductViewType));
				LOG.info("supplier id:"+supplierId+",product type:"+ebkProductViewType+",import end...");
				json.put("success", Boolean.TRUE);
			}catch(Exception e){
				json.put("success", Boolean.FALSE);
				json.put("message", -500);
				e.printStackTrace();
			}finally{
				MemcachedUtil.getInstance().remove(key);
			}
			JSONOutput.writeJSON(getResponse(), json);
		}
	}
	
	public EbkProdProduct getEbkProdProduct() {
		return ebkProdProduct;
	}
	public void setEbkProdProduct(EbkProdProduct ebkProdProduct) {
		this.ebkProdProduct = ebkProdProduct;
	}
	public Map<String, Object> getParameters() {
		return parameters;
	}
	public void setParameters(Map<String, Object> parameters) {
		this.parameters = parameters;
	}
	public SUB_PRODUCT_TYPE[] getSubProductTypes() {
		return subProductTypes;
	}
	public void setSubProductTypes(SUB_PRODUCT_TYPE[] subProductTypes) {
		this.subProductTypes = subProductTypes;
	}
	public EBK_PRODUCT_AUDIT_STATUS[] getEbkProductAuditStatus() {
		return ebkProductAuditStatus;
	}
	public void setEbkProductAuditStatus(
			EBK_PRODUCT_AUDIT_STATUS[] ebkProductAuditStatus) {
		this.ebkProductAuditStatus = ebkProductAuditStatus;
	}
	public String getAuditStatus() {
		return auditStatus;
	}
	public void setAuditStatus(String auditStatus) {
		this.auditStatus = auditStatus;
	}

	public String getSubProductType() {
		return subProductType;
	}

	public void setSubProductType(String subProductType) {
		this.subProductType = subProductType;
	}

	public void setEbkProdAuditService(EbkProdAuditService ebkProdAuditService) {
		this.ebkProdAuditService = ebkProdAuditService;
	}
	
}
